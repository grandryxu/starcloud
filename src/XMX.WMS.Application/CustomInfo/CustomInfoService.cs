using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.CustomInfo.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using System.Threading.Tasks;
using Newtonsoft.Json.Linq;
using System.Collections.Generic;
using Abp.UI;
using XMX.WMS.Authorization;
using Abp.Authorization;
using Abp.Application.Services.Dto;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using XMX.WMS.WMSOptLogInfo;
using Newtonsoft.Json;
using XMX.WMS.Base.Session;

namespace XMX.WMS.CustomInfo
{
    [AbpAuthorize(PermissionNames.CustomerBaseInfo)]
    public class CustomInfoService : AsyncCrudAppService<CustomInfo, CustomInfoDto, Guid, CustomInfoPagedRequest, CustomInfoCreatedDto, CustomInfoUpdatedDto>, ICustomInfoService
    {
        private readonly Guid UserCompanyId;
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public CustomInfoService(IRepository<CustomInfo, Guid> repository) : base(repository)
        {
            UserCompanyId = AbpSession.GetCompanyId();
            LogContext = DynamicDbContext.GetInstance(string.Concat("WMSOptLogInfo",DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                CompanyId = UserCompanyId,
                OptPath = "XMX.WMS.CustomInfo.CustomInfoService.",
                OptModule="客户基础信息"
            };
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.CustomInfo_Get)]
        protected override IQueryable<CustomInfo> CreateFilteredQuery(CustomInfoPagedRequest input)
        {
            return Repository.GetAllIncluding(s => s.CustomType)
                    .WhereIf(AbpSession.UserId != 1, x => x.custom_company_id == UserCompanyId)
                    .WhereIf(!input.custom_name.IsNullOrWhiteSpace(), x => x.custom_name.Contains(input.custom_name))
                    .WhereIf(input.custom_type_id.HasValue, x => x.custom_type_id == input.custom_type_id)
                    .WhereIf(!input.custom_code.IsNullOrWhiteSpace(), x => x.custom_code.Contains(input.custom_code));
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.CustomInfo_Get)]
        public override Task<CustomInfoDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 客户基础信息 新增做客户编码重复判断
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.CustomInfo_Add)]
        public override async Task<CustomInfoDto> Create(CustomInfoCreatedDto input)
        {
            var isrename = Repository.GetAll().Where(x => x.custom_name == input.custom_name).Any();
            var isrecode = Repository.GetAll().Where(x => x.custom_code == input.custom_code).Any();
            if (!isrename && !isrecode)
            {
                input.custom_company_id = UserCompanyId;
                CustomInfoDto dto = await base.Create(input);
                WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Create", WMSOptLogInfo.WMSOptLogInfo.ADD, "", JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
                LogContext.WMSOptLogInfo.Add(logInfoEntity);
                LogContext.SaveChanges();
                return dto;

            }
            else
            {
                if (isrename && isrecode)
                {
                    throw new Abp.UI.UserFriendlyException("客户名称、客户编码重复！");
                }
                else if (isrename)
                {
                    throw new Abp.UI.UserFriendlyException("客户名称重复！");
                }
                else
                {
                    throw new Abp.UI.UserFriendlyException("客户编码重复！");
                }
            }
        }

        /// <summary>
        /// 客户基础信息 编辑做客户编码重复判断
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.CustomInfo_Update)]
        public override async Task<CustomInfoDto> Update(CustomInfoUpdatedDto input)
        {
            var query = Repository.GetAll().Where(x => x.Id != input.Id);
            var isrename = query.Where(x => x.custom_name == input.custom_name).Any();
            var isrecode = query.Where(x => x.custom_code == input.custom_code).Any();
            if (!isrename && !isrecode)
            {
                CustomInfo oldEntity = Repository.Get(input.Id);
                string oldval = JsonConvert.SerializeObject(oldEntity);
                CustomInfoDto dto = await base.Update(input);
                WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, oldval, JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
                LogContext.WMSOptLogInfo.Add(logInfoEntity);
                LogContext.SaveChanges();
                return dto;
            }
            else
            {
                if (isrename && isrecode)
                {
                    throw new Abp.UI.UserFriendlyException("客户名称、客户编码重复！");
                }
                else if (isrename)
                {
                    throw new Abp.UI.UserFriendlyException("客户名称重复！");
                }
                else
                {
                    throw new Abp.UI.UserFriendlyException("客户编码重复！");
                }
            }
        }

        /// <summary>
        /// 批量删除
        /// </summary>
        /// <param name="idList"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.CustomInfo_Delete)]
        public Task CreateDropAll(JObject idList)
        {
            dynamic jsonValues = idList;
            JArray jsonInput = jsonValues.idList;

            List<Guid> list = jsonInput.ToObject<List<Guid>>();
            if (null == list)
            {
                WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "CreateDropAll", WMSOptLogInfo.WMSOptLogInfo.DELETE,"","", WMSOptLogInfo.WMSOptLogInfo.FAIL);
                LogContext.WMSOptLogInfo.Add(logInfoEntity);
                LogContext.SaveChanges();
                throw new UserFriendlyException("参数解析异常，请联系管理员！");
            }
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "CreateDropAll", WMSOptLogInfo.WMSOptLogInfo.DELETE, "", "", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return Repository.DeleteAsync(x => x.Id.IsIn(list.ToArray<Guid>()));
        }

        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.CustomInfo_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
             await Repository.DeleteAsync(x => x.Id == input.Id);
        }

    }
}
