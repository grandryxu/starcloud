using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.BillInfo.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using System.Threading.Tasks;
using System.Collections.Generic;
using Abp.UI;
using XMX.WMS.Authorization;
using Abp.Authorization;
using Abp.Application.Services.Dto;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using Newtonsoft.Json;
using XMX.WMS.Base.Session;

namespace XMX.WMS.BillInfo
{
    [AbpAuthorize(PermissionNames.BillTypeInfo)]
    public class BillInfoService : AsyncCrudAppService<BillInfo, BillInfoDto, Guid, BillInfoPagedRequest, BillInfoCreatedDto, BillInfoUpdatedDto>, IBillInfoService
    {
        private readonly Guid UserCompanyId;
        //日志
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public BillInfoService(IRepository<BillInfo, Guid> repository) : base(repository)
        {
            UserCompanyId = AbpSession.GetCompanyId();
            LogContext = DynamicDbContext.GetInstance(string.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                CompanyId = UserCompanyId,
                OptPath = "XMX.WMS.BillInfo.BillInfoService.",
                OptModule = "单据类型信息"
            };
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.BillTypeInfo_Get)]
        protected override IQueryable<BillInfo> CreateFilteredQuery(BillInfoPagedRequest input)
        {
            return Repository.GetAll()
                    .WhereIf(AbpSession.UserId != 1, x => x.bill_company_id == UserCompanyId)
                    .WhereIf(!input.bill_name.IsNullOrWhiteSpace(), x => x.bill_name.Contains(input.bill_name))
                    .WhereIf(input.bill_type.HasValue, x => x.bill_type == input.bill_type)
                    ;
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.BillTypeInfo_Get)]
        public override Task<BillInfoDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.BillTypeInfo_Add)]
        public override async Task<BillInfoDto> Create(BillInfoCreatedDto input)
        {
            var isrename = Repository.GetAll().Where(x => x.bill_name == input.bill_name).Any();
            if (!isrename)
            {
                input.bill_company_id = UserCompanyId;
                BillInfoDto dto = await base.Create(input);
                WMSOptLogInfo.WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Create", WMSOptLogInfo.WMSOptLogInfo.ADD, "", JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
                LogContext.WMSOptLogInfo.Add(logInfoEntity);
                LogContext.SaveChanges();
                return dto;
            }
            else
            {
                throw new Abp.UI.UserFriendlyException("单据类型名称！");
            }
        }

        /// <summary>
        /// 更新
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.BillTypeInfo_Update)]
        public override async Task<BillInfoDto> Update(BillInfoUpdatedDto input)
        {
            var query = Repository.GetAll().Where(x => x.Id != input.Id);
            var isrename = query.Where(x => x.bill_name == input.bill_name).Any();
            if (!isrename)
            {
                BillInfo oldEntity = Repository.Get(input.Id);
                string oldval = JsonConvert.SerializeObject(oldEntity);
                BillInfoDto dto = await base.Update(input);
                WMSOptLogInfo.WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, oldval, JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
                LogContext.WMSOptLogInfo.Add(logInfoEntity);
                LogContext.SaveChanges();
                return dto;
            }
            else
            {
                WMSOptLogInfo.WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, "", "", WMSOptLogInfo.WMSOptLogInfo.FAIL);
                LogContext.WMSOptLogInfo.Add(logInfoEntity);
                LogContext.SaveChanges();
                throw new Abp.UI.UserFriendlyException("单据类型名称！");
            }
        }

        /// <summary>
        /// 批量删除
        /// </summary>
        /// <param name="idList"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.BillTypeInfo_Delete)]
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
        [AbpAuthorize(PermissionNames.BillTypeInfo_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
            WMSOptLogInfo.WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Delete", WMSOptLogInfo.WMSOptLogInfo.DELETE, input.Id.ToString(), "", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            await Repository.DeleteAsync(x => x.Id == input.Id);
        }

    }
}
