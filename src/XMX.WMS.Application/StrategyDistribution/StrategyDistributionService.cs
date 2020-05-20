using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.StrategyDistribution.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using System.Threading.Tasks;
using Abp.UI;
using Abp.Application.Services.Dto;
using System.Collections.Generic;
using XMX.WMS.Authorization;
using Abp.Authorization;
using XMX.WMS.Authorization.Users;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using XMX.WMS.WMSOptLogInfo;
using Newtonsoft.Json;
using XMX.WMS.Base.Session;

namespace XMX.WMS.StrategyDistribution
{
    [AbpAuthorize(PermissionNames.StrategyDistriManage)]
    public class StrategyDistributionService : AsyncCrudAppService<StrategyDistribution, StrategyDistributionDto, Guid, StrategyDistributionPagedRequest, StrategyDistributionCreatedDto, StrategyDistributionUpdatedDto>, IStrategyDistributionService
    {
        private readonly IRepository<GoodsInfo.GoodsInfo, Guid> _gRepository;
        private readonly Guid UserCompanyId;
        //日志
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public StrategyDistributionService(IRepository<StrategyDistribution, Guid> repository,
                                           IRepository<GoodsInfo.GoodsInfo, Guid> gRepository) : base(repository)
        {
            _gRepository = gRepository;
            UserCompanyId = AbpSession.GetCompanyId();
            LogContext = DynamicDbContext.GetInstance(string.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                CompanyId = UserCompanyId,
                OptPath = "XMX.WMS.StrategyDistribution.StrategyDistributionService.",
                OptModule = "分配策略管理"
            };
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.StrategyDistriManage_Get)]
        protected override IQueryable<StrategyDistribution> CreateFilteredQuery(StrategyDistributionPagedRequest input)
        {
            return Repository.GetAll()
                .WhereIf(AbpSession.UserId != 1, x => x.distribution_company_id == UserCompanyId)
                .WhereIf(!input.distribution_name.IsNullOrWhiteSpace(), x => x.distribution_name.Contains(input.distribution_name))
                ;
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.StrategyDistriManage_Get)]
        public override Task<StrategyDistributionDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.StrategyDistriManage_Add)]
        public override async Task<StrategyDistributionDto> Create(StrategyDistributionCreatedDto input)
        {
            input.distribution_company_id = UserCompanyId;
            var flag = Repository.GetAll().Where(x => x.distribution_name == input.distribution_name).Any();
            if (flag)
                throw new UserFriendlyException("名称已存在！");
            StrategyDistributionDto dto = await base.Create(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Create", WMSOptLogInfo.WMSOptLogInfo.ADD, "", JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 编辑
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.StrategyDistriManage_Update)]
        public override async Task<StrategyDistributionDto> Update(StrategyDistributionUpdatedDto input)
        {
            var flag = Repository.GetAll().Where(x => x.distribution_name == input.distribution_name).Where(x => x.Id != input.Id).Any();
            if (flag)
                throw new UserFriendlyException("名称已存在！");
            StrategyDistribution oldEntity = Repository.Get(input.Id);
            string oldval = JsonConvert.SerializeObject(oldEntity);
            StrategyDistributionDto dto = await base.Update(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, oldval, JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.StrategyDistriManage_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
            var flag = _gRepository.GetAll().Where(x => x.goods_distribution_id == input.Id).Any();
            if (flag)
                throw new UserFriendlyException("数据占用，无法删除！");
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Delete", WMSOptLogInfo.WMSOptLogInfo.DELETE, input.Id.ToString(), "", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            await Repository.DeleteAsync(x => x.Id == input.Id);
        }

        /// <summary>
        /// 批量删除
        /// </summary>
        /// <param name="idList"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.StrategyDistriManage_Delete)]
        public Task DelteList(List<Guid> idList)
        {
            var flag = _gRepository.GetAll().Where(x => x.goods_distribution_id.Value.IsIn(idList.ToArray<Guid>())).Any();
            if (flag)
                throw new UserFriendlyException("数据占用，无法删除！");
            if (null == idList)
                throw new UserFriendlyException("参数解析异常，请联系管理员！");
            return Repository.DeleteAsync(x => x.Id.IsIn(idList.ToArray<Guid>()));
        }
    }
}
