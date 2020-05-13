using Abp.Application.Services;
using Abp.Application.Services.Dto;
using Abp.Auditing;
using Abp.Authorization.Users;
using System.Threading.Tasks;
using XMX.WMS.Roles.Dto;
using XMX.WMS.Users.Dto;

namespace XMX.WMS.Users
{
    public interface IUserAppService : IAsyncCrudAppService<UserDto, long, PagedUserResultRequestDto, CreateUserDto, UserDto>
    {
        Task<ListResultDto<RoleDto>> GetRoles();

        Task ChangeLanguage(ChangeUserLanguageDto input);

        Task<PagedResultDto<UserLoginAttempt>> GetUserLoginAttemptList(PagedUserLoginAttemptRequestDto input);

        Task<PagedResultDto<AuditLog>> GetAuditLogList(PagedAuditLogRequestDto input);

        Task<AuditLog> GetAuditLog(long id);
    }
}
