using Abp.Application.Services;
using Abp.Application.Services.Dto;
using Abp.Auditing;
using Abp.Authorization;
using Abp.Authorization.Users;
using Abp.Configuration.Startup;
using Abp.Domain.Entities;
using Abp.Domain.Repositories;
using Abp.Extensions;
using Abp.IdentityFramework;
using Abp.Linq.Extensions;
using Abp.Localization;
using Abp.ObjectMapping;
using Abp.Runtime.Session;
using Abp.UI;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using XMX.WMS.Authorization;
using XMX.WMS.Authorization.Roles;
using XMX.WMS.Authorization.Users;
using XMX.WMS.Roles.Dto;
using XMX.WMS.Users.Dto;

namespace XMX.WMS.Users
{
    [AbpAuthorize(PermissionNames.Pages_Users)]
    public class UserAppService : AsyncCrudAppService<User, UserDto, long, PagedUserResultRequestDto, CreateUserDto, UserDto>, IUserAppService
    {
        private readonly UserManager _userManager;
        private readonly RoleManager _roleManager;
        private readonly IRepository<Role> _roleRepository;
        private readonly IPasswordHasher<User> _passwordHasher;
        private readonly IAbpSession _abpSession;
        private readonly LogInManager _logInManager;
        protected IRepository<UserLoginAttempt, long> _userLoginAttemptRepository;
        protected IRepository<AuditLog, long> _auditLogRepository;
        private readonly IObjectMapper _objectMapper;
        private readonly ILocalizationConfiguration _localizationConfiguration;
        public UserAppService(
            IRepository<User, long> repository,
            UserManager userManager,
            RoleManager roleManager,
            IRepository<Role> roleRepository,
            IPasswordHasher<User> passwordHasher,
            IAbpSession abpSession,
            LogInManager logInManager,
            IRepository<UserLoginAttempt, long> userLoginAttemptRepository,
            IRepository<AuditLog, long> auditLogRepository,
            IObjectMapper objectMapper,
            ILocalizationConfiguration localizationConfiguration)
            : base(repository)
        {
            _userManager = userManager;
            _roleManager = roleManager;
            _roleRepository = roleRepository;
            _passwordHasher = passwordHasher;
            _abpSession = abpSession;
            _logInManager = logInManager;
            _userLoginAttemptRepository = userLoginAttemptRepository;
            _auditLogRepository = auditLogRepository;
            _objectMapper = objectMapper;
            _localizationConfiguration = localizationConfiguration;
        }

        public override async Task<UserDto> Create(CreateUserDto input)
        {
            CheckCreatePermission();

            var user = ObjectMapper.Map<User>(input);
            var loginuser = await _userManager.GetUserByIdAsync(AbpSession.GetUserId());

            user.TenantId = AbpSession.TenantId;
            user.IsEmailConfirmed = true;
            user.DepartmentId = input.DepartmentId;
            //user.CompanyId = loginuser.CompanyId == null ? input.CompanyId : loginuser.CompanyId;
            user.CompanyId = loginuser.CompanyId;
            await _userManager.InitializeOptionsAsync(AbpSession.TenantId);

            CheckErrors(await _userManager.CreateAsync(user, input.Password));

            if (input.RoleNames != null)
            {
                CheckErrors(await _userManager.SetRoles(user, input.RoleNames));
            }

            CurrentUnitOfWork.SaveChanges();

            return MapToEntityDto(user);
        }

        public override async Task<UserDto> Update(UserDto input)
        {
            CheckUpdatePermission();

            var user = await _userManager.GetUserByIdAsync(input.Id);

            MapToEntity(input, user);

            CheckErrors(await _userManager.UpdateAsync(user));

            if (input.RoleNames != null)
            {
                CheckErrors(await _userManager.SetRoles(user, input.RoleNames));
            }

            return await Get(input);
        }

        public override async Task Delete(EntityDto<long> input)
        {
            var user = await _userManager.GetUserByIdAsync(input.Id);
            await _userManager.DeleteAsync(user);
        }

        public async Task<ListResultDto<RoleDto>> GetRoles()
        {
            var roles = await _roleRepository.GetAllListAsync();
            return new ListResultDto<RoleDto>(ObjectMapper.Map<List<RoleDto>>(roles));
        }

        /// <summary>
        /// 修改语言
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public async Task ChangeLanguage(ChangeUserLanguageDto input)
        {
            string language = "en";
            if (input.LanguageName != null && !"".Equals(input.LanguageName) && !"en".Equals(input.LanguageName))
            {
                if ("zh".Equals(input.LanguageName))
                    language = "zh-Hans";
                else
                    language = input.LanguageName;
            }
            await SettingManager.ChangeSettingForUserAsync(
                AbpSession.ToUserIdentifier(),
                LocalizationSettingNames.DefaultLanguage,
                language
            );
        }

        protected override User MapToEntity(CreateUserDto createInput)
        {
            var user = ObjectMapper.Map<User>(createInput);
            user.SetNormalizedNames();
            return user;
        }

        protected override void MapToEntity(UserDto input, User user)
        {
            ObjectMapper.Map(input, user);
            user.SetNormalizedNames();
        }

        protected override UserDto MapToEntityDto(User user)
        {
            var roles = _roleManager.Roles.Where(r => user.Roles.Any(ur => ur.RoleId == r.Id)).Select(r => r.NormalizedName);
            var userDto = base.MapToEntityDto(user);
            userDto.RoleNames = roles.ToArray();
            return userDto;
        }



        protected override IQueryable<User> CreateFilteredQuery(PagedUserResultRequestDto input)
        {
            return Repository.GetAllIncluding(x => x.Roles, x => x.Company, x => x.Department)
                .WhereIf(!input.SurName.IsNullOrWhiteSpace(), x => x.UserName.Contains(input.SurName))
                .WhereIf(!input.Name.IsNullOrWhiteSpace(), x => x.Name.Contains(input.Name))
                .WhereIf(input.RoleId != 0, x => x.Roles.FirstOrDefault(y => y.RoleId == input.RoleId) != null)
                .WhereIf(input.IsActive.HasValue, x => x.IsActive == input.IsActive)
                .WhereIf(input.IsEnable.HasValue, x => x.IsEnable == input.IsEnable)
                .WhereIf(input.IsEnable.HasValue, x => x.IsEnable == input.IsEnable)
                .Where( x => x.Id.ToString() != "1");
        }



        protected override async Task<User> GetEntityByIdAsync(long id)
        {
            var user = await Repository.GetAllIncluding(x => x.Roles).FirstOrDefaultAsync(x => x.Id == id);

            if (user == null)
            {
                throw new EntityNotFoundException(typeof(User), id);
            }

            return user;
        }

        protected override IQueryable<User> ApplySorting(IQueryable<User> query, PagedUserResultRequestDto input)
        {
            return query.OrderByDescending(r => r.CreationTime);
        }

        protected virtual void CheckErrors(IdentityResult identityResult)
        {
            identityResult.CheckErrors(LocalizationManager);
        }

        public async Task<bool> ChangePassword(ChangePasswordDto input)
        {
            if (_abpSession.UserId == null)
            {
                throw new UserFriendlyException("Please log in before attemping to change password.");
            }
            long userId = _abpSession.UserId.Value;
            var user = await _userManager.GetUserByIdAsync(userId);
            var loginAsync = await _logInManager.LoginAsync(user.UserName, input.CurrentPassword, shouldLockout: false);
            if (loginAsync.Result != AbpLoginResultType.Success)
            {
                throw new UserFriendlyException("您的原密码与记录的密码不匹配。请重试或与管理员联系以获得重置密码的帮助。");
            }
            //if (!new Regex(AccountAppService.PasswordRegex).IsMatch(input.NewPassword))
            //{
            //    throw new UserFriendlyException("Passwords must be at least 8 characters, contain a lowercase, uppercase, and number.");
            //}
            user.Password = _passwordHasher.HashPassword(user, input.NewPassword);
            CurrentUnitOfWork.SaveChanges();
            return true;
        }

        public async Task<bool> ResetPassword(ResetPasswordDto input)
        {
            if (_abpSession.UserId == null)
            {
                throw new UserFriendlyException("Please log in before attemping to reset password.");
            }
            long currentUserId = _abpSession.UserId.Value;
            var currentUser = await _userManager.GetUserByIdAsync(currentUserId);
            //var loginAsync = await _logInManager.LoginAsync(currentUser.UserName, input.AdminPassword, shouldLockout: false);
            //if (loginAsync.Result != AbpLoginResultType.Success)
            //{
            //    throw new UserFriendlyException("Your 'Admin Password' did not match the one on record.  Please try again.");
            //}
            if (currentUser.IsDeleted || !currentUser.IsActive)
            {
                return false;
            }
            var roles = await _userManager.GetRolesAsync(currentUser);
            if (!roles.Contains(StaticRoleNames.Tenants.Admin))
            {
                throw new UserFriendlyException("Only administrators may reset passwords.");
            }

            var user = await _userManager.GetUserByIdAsync(input.UserId);
            if (user != null)
            {
                user.Password = _passwordHasher.HashPassword(user, input.NewPassword);
                CurrentUnitOfWork.SaveChanges();
            }

            return true;
        }
        /// <summary>
        /// 获取用户登录日志，支持按日期范围查询（若有更好的支持数组查询dto参数的方法，欢迎指正并加以修改）
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public async Task<PagedResultDto<UserLoginAttempt>> GetUserLoginAttemptList(PagedUserLoginAttemptRequestDto input)
        {
            var query = _userLoginAttemptRepository.GetAllIncluding().OrderByDescending(x => x.CreationTime)
                .WhereIf(!input.UserNameOrEmailAddress.IsNullOrWhiteSpace(), x => x.UserNameOrEmailAddress.Contains(input.UserNameOrEmailAddress));
            string[] dt = input.DateRange?.Split("/");
            if (dt?.Length == 2)
            {
                DateTime t1 = Convert.ToDateTime(dt[0]);
                DateTime t2 = Convert.ToDateTime(dt[1]);
                query = query.Where(x => DateTime.Compare(Convert.ToDateTime(x.CreationTime.ToString("yyyy-MM-dd")), t1) >= 0)
                    .Where(x => DateTime.Compare(Convert.ToDateTime(x.CreationTime.ToString("yyyy-MM-dd")), t2) <= 0);
            }
            var taskList = await query.PageBy(input).ToListAsync();
            return new PagedResultDto<UserLoginAttempt>(query.CountAsync().Result, _objectMapper.Map<List<UserLoginAttempt>>(taskList));
        }
        /// <summary>
        /// 获取用户操作日志
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public async Task<PagedResultDto<AuditLog>> GetAuditLogList(PagedAuditLogRequestDto input)
        {
            var query = _auditLogRepository.GetAllIncluding()
                .WhereIf(!input.Parameters.IsNullOrWhiteSpace(), x => x.Parameters.Contains(input.Parameters))
                .WhereIf(!input.ServiceName.IsNullOrWhiteSpace(), x => x.ServiceName.Contains(input.ServiceName))
                .WhereIf(!input.MethodName.IsNullOrWhiteSpace(), x => x.MethodName.Contains(input.MethodName));
            string[] dt = input.DateRange?.Split("$");
            if (dt?.Length == 2)
            {
                DateTime t1 = Convert.ToDateTime(dt[0]);
                DateTime t2 = Convert.ToDateTime(dt[1]);
                query = query.Where(x => DateTime.Compare(x.ExecutionTime, t1) >= 0).Where(x => DateTime.Compare(x.ExecutionTime, t2) <= 0);
            }
            var taskList = await query.PageBy(input).ToListAsync();
            return new PagedResultDto<AuditLog>(query.CountAsync().Result, _objectMapper.Map<List<AuditLog>>(taskList));
        }
        /// <summary>
        /// 获取单条用户操作日志
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        public async Task<AuditLog> GetAuditLog(long id)
        {
            var r = await _auditLogRepository.GetAsync(id);
            return r;
        }
    }
}

