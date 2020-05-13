using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.CustomTypeInfo.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using System.Threading.Tasks;
using Abp.UI;
using Newtonsoft.Json.Linq;
using System.Collections.Generic;
using XMX.WMS.Authorization;
using Abp.Authorization;
using Abp.Application.Services.Dto;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using XMX.WMS.WMSOptLogInfo;
using Newtonsoft.Json;

namespace XMX.WMS.CustomTypeInfo
{
    [AbpAuthorize(PermissionNames.CustomerCategoryInfo)]
    public class CustomTypeInfoService : AsyncCrudAppService<CustomTypeInfo, CustomTypeInfoDto, Guid, CustomTypeInfoPagedRequest, CustomTypeInfoCreatedDto, CustomTypeInfoUpdatedDto>, ICustomTypeInfoService
    {
        private readonly IRepository<CustomInfo.CustomInfo, Guid> _customInfoRepository;
        //日志
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;

        public CustomTypeInfoService(IRepository<CustomTypeInfo, Guid> repository, IRepository<CustomInfo.CustomInfo, Guid> customInfoRepository) : base(repository)
        {
            _customInfoRepository = customInfoRepository;
            LogContext = DynamicDbContext.GetInstance(string.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                OptPath = "XMX.WMS.CustomTypeInfo.CustomTypeInfoService.",
                OptModule = "客户类别信息"
            };
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.CustomTypeInfo_Get)]
        protected override IQueryable<CustomTypeInfo> CreateFilteredQuery(CustomTypeInfoPagedRequest input)
        {
            return Repository.GetAllIncluding()
                .WhereIf(!input.customtype_name.IsNullOrWhiteSpace(), x => x.customtype_name.Contains(input.customtype_name))
                .WhereIf(!input.customtype_code.IsNullOrWhiteSpace(), x => x.customtype_code.Contains(input.customtype_code));
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.CustomTypeInfo_Get)]
        public override Task<CustomTypeInfoDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }
        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.CustomTypeInfo_Add)]
        public override async Task<CustomTypeInfoDto> Create(CustomTypeInfoCreatedDto input)
        {
            var is_recode = Repository.GetAll().Where(x => x.customtype_code == input.customtype_code).Where(x => !x.IsDeleted).Any();
            var is_rename = Repository.GetAll().Where(x => x.customtype_name == input.customtype_name).Where(x => !x.IsDeleted).Any();
            if (is_recode || is_rename)
                throw new UserFriendlyException("编号或名称已存在！");
            CustomTypeInfoDto dto = await base.Create(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Create", WMSOptLogInfo.WMSOptLogInfo.ADD, "", JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }
        /// <summary>
        /// 修改更新
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.CustomTypeInfo_Update)]
        public override async Task<CustomTypeInfoDto> Update(CustomTypeInfoUpdatedDto input)
        {
            var query = Repository.GetAll().Where(x => x.Id != input.Id);
            var is_rename_or_recode = query.Where(x => x.customtype_code == input.customtype_code || x.customtype_name == input.customtype_name).Where(x => !x.IsDeleted).Any();
            if (is_rename_or_recode)
            {
                WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, "", "", WMSOptLogInfo.WMSOptLogInfo.FAIL);
                LogContext.WMSOptLogInfo.Add(logInfoEntity);
                LogContext.SaveChanges();
                throw new UserFriendlyException("编号或名称已存在！");
            }

            CustomTypeInfo oldEntity = Repository.FirstOrDefault(x => x.Id == input.Id);
            string oldval = JsonConvert.SerializeObject(oldEntity);
            CustomTypeInfoDto dto = await base.Update(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, oldval, JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 批量删除
        /// </summary>
        /// <param name="idList"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.CustomTypeInfo_Delete)]
        public Task CreateDropAll(JObject idList)
        {
            dynamic jsonValues = idList;
            JArray jsonInput = jsonValues.idList;

            List<Guid> list = jsonInput.ToObject<List<Guid>>();
            if (null == list)
                throw new UserFriendlyException("参数解析异常，请联系管理员！");
            var used = _customInfoRepository.GetAll().Where(x => x.CustomType.Id.IsIn(list.ToArray<Guid>())).Any();
            if(used)
                throw new UserFriendlyException("存在已被关联的客户类型，请核实后再删除！");
            return Repository.DeleteAsync(x => x.Id.IsIn(list.ToArray<Guid>()));
        }

        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.CustomTypeInfo_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Delete", WMSOptLogInfo.WMSOptLogInfo.DELETE, input.Id.ToString(), "", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            await Repository.DeleteAsync(x => x.Id == input.Id);
        }
    }
}
