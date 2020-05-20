using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.QualityInfo.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using System.Threading.Tasks;
using Abp.UI;
using System.Collections.Generic;
using XMX.WMS.Authorization;
using Abp.Authorization;
using Abp.Application.Services.Dto;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using XMX.WMS.WMSOptLogInfo;
using Newtonsoft.Json;
using XMX.WMS.Base.Session;

namespace XMX.WMS.QualityInfo
{
    [AbpAuthorize(PermissionNames.MaterialQualityStatus)]
    public class QualityInfoService : AsyncCrudAppService<QualityInfo, QualityInfoDto, Guid, QualityInfoPagedRequest, QualityInfoCreatedDto, QualityInfoUpdatedDto>, IQualityInfoService
    {
        private readonly Guid UserCompanyId;
        //日志
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public QualityInfoService(IRepository<QualityInfo, Guid> repository) : base(repository)
        {
            UserCompanyId = AbpSession.GetCompanyId();
            LogContext = DynamicDbContext.GetInstance(string.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                CompanyId = UserCompanyId,
                OptPath = "XMX.WMS.QualityInfo.QualityInfoService.",
                OptModule = "物料质量状态"
            };
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.MaterialQualityStatus_Get)]
        protected override IQueryable<QualityInfo> CreateFilteredQuery(QualityInfoPagedRequest input)
        {
            return Repository.GetAllIncluding(x => x.Company)
                        .WhereIf(AbpSession.UserId != 1, x => x.quality_company_id == UserCompanyId)
                        .WhereIf(!input.quality_name.IsNullOrWhiteSpace(), x => x.quality_name.Contains(input.quality_name))
                        .WhereIf(input.quality_company_id.HasValue, x => x.quality_company_id == input.quality_company_id)
                        ;
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.MaterialQualityStatus_Get)]
        public override Task<QualityInfoDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.MaterialQualityStatus_Add)]
        public override async Task<QualityInfoDto> Create(QualityInfoCreatedDto input)
        {
            var is_rename = Repository.GetAll().Where(x => x.quality_name == input.quality_name).Any();
            if (is_rename)
                throw new UserFriendlyException("质量状态名称已存在！");
            input.quality_company_id = UserCompanyId;
            QualityInfoDto dto = await base.Create(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Create", WMSOptLogInfo.WMSOptLogInfo.ADD, "", JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 更新
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.MaterialQualityStatus_Update)]
        public override async Task<QualityInfoDto> Update(QualityInfoUpdatedDto input)
        {
            var query = Repository.GetAll().Where(x => x.Id != input.Id);
            var is_rename = query.Where(x => x.quality_name == input.quality_name).Any();
            if (is_rename)
                throw new UserFriendlyException("质量状态名称已存在！");
            QualityInfo oldEntity = Repository.Get(input.Id);
            string oldval = JsonConvert.SerializeObject(oldEntity);
            QualityInfoDto dto = await base.Update(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, oldval, JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 批量删除
        /// </summary>
        /// <param name="idList"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.MaterialQualityStatus_Delete)]
        public Task DelteList(List<Guid> idList)
        {
            //dynamic jsonValues = idList;
            //JArray jsonInput = jsonValues.idList;
            //List<Guid> list = jsonInput.ToObject<List<Guid>>();
            if (null == idList)
                throw new UserFriendlyException("参数解析异常，请联系管理员！");
            return Repository.DeleteAsync(x => x.Id.IsIn(idList.ToArray<Guid>()));
        }

        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.MaterialQualityStatus_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Delete", WMSOptLogInfo.WMSOptLogInfo.DELETE, input.Id.ToString(), "", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            await Repository.DeleteAsync(x => x.Id == input.Id);
        }
    }
}
