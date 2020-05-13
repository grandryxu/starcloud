using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.StrategyMonitor.Dto;
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

namespace XMX.WMS.StrategyMonitor
{
    [AbpAuthorize(PermissionNames.StrategyMonitorManage)]
    public class StrategyMonitorService : AsyncCrudAppService<StrategyMonitor, StrategyMonitorDto, Guid, StrategyMonitorPagedRequest, StrategyMonitorCreatedDto, StrategyMonitorUpdatedDto>, IStrategyMonitorService
    {
        private readonly IRepository<GoodsInfo.GoodsInfo, Guid> _gRepository;
        private readonly UserManager _userManager;

        //日志
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public StrategyMonitorService(IRepository<StrategyMonitor, Guid> repository,
                                      IRepository<GoodsInfo.GoodsInfo, Guid> gRepository,
                                      UserManager userManager) : base(repository)
        {
            _gRepository = gRepository;
            _userManager = userManager;

            LogContext = DynamicDbContext.GetInstance(String.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                OptPath = "XMX.WMS.StrategyMonitor.StrategyMonitorService.",
                OptModule = "监控策略管理"
            };
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.StrategyMonitorManage_Get)]
        protected override IQueryable<StrategyMonitor> CreateFilteredQuery(StrategyMonitorPagedRequest input)
        {
            return Repository.GetAllIncluding()
                .WhereIf(!input.monitor_name.IsNullOrWhiteSpace(), x => x.monitor_name.Contains(input.monitor_name))
                ;
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.StrategyMonitorManage_Get)]
        public override Task<StrategyMonitorDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.StrategyMonitorManage_Add)]
        public override async Task<StrategyMonitorDto> Create(StrategyMonitorCreatedDto input)
        {
            //公司ID
            User loginuser = _userManager.GetUserByIdAsync(AbpSession.UserId.Value).Result;
            input.monitor_company_id = loginuser.CompanyId;
            var flag = Repository.GetAll().Where(x => x.monitor_name == input.monitor_name).Any();
            if (flag)
                throw new UserFriendlyException("名称已存在！");
            StrategyMonitorDto dto = await base.Create(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Create", WMSOptLogInfo.WMSOptLogInfo.ADD, "", JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 编辑
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.StrategyMonitorManage_Update)]
        public override async Task<StrategyMonitorDto> Update(StrategyMonitorUpdatedDto input)
        {
            var flag = Repository.GetAll().Where(x => x.monitor_name == input.monitor_name).Where(x => x.Id != input.Id).Any();
            if (flag)
                throw new UserFriendlyException("名称已存在！");
            StrategyMonitor oldEntity = Repository.FirstOrDefault(x => x.Id == input.Id);
            string oldval = JsonConvert.SerializeObject(oldEntity);
            StrategyMonitorDto dto = await base.Update(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, oldval, JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.StrategyMonitorManage_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
            var flag = _gRepository.GetAll().Where(x => x.goods_warehousing_id == input.Id).Any();
            if (flag)
                throw new UserFriendlyException("数据占用，无法删除！");
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Delete", WMSOptLogInfo.WMSOptLogInfo.DELETE, input.Id.ToString(), "", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            await Repository.DeleteAsync(x => x.Id == input.Id);
        }

        /// <summary>
        /// 批量删除
        /// </summary>
        /// <param name="idList"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.StrategyMonitorManage_Delete)]
        public Task DelteList(List<Guid> idList)
        {
            var flag = _gRepository.GetAll().Where(x => x.goods_warehousing_id.Value.IsIn(idList.ToArray<Guid>())).Any();
            if (flag)
                throw new UserFriendlyException("数据占用，无法删除！");
            if (null == idList)
                throw new UserFriendlyException("参数解析异常，请联系管理员！");
            return Repository.DeleteAsync(x => x.Id.IsIn(idList.ToArray<Guid>()));
        }
    }
}
