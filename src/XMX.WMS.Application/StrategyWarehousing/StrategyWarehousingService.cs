using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.StrategyWarehousing.Dto;
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

namespace XMX.WMS.StrategyWarehousing
{
    [AbpAuthorize(PermissionNames.StrategyPutawayManage)]
    public class StrategyWarehousingService : AsyncCrudAppService<StrategyWarehousing, StrategyWarehousingDto, Guid, StrategyWarehousingPagedRequest, StrategyWarehousingCreatedDto, StrategyWarehousingUpdatedDto>, IStrategyWarehousingService
    {
        private readonly IRepository<GoodsInfo.GoodsInfo, Guid> _gRepository;

        //日志
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        private readonly UserManager _userManager;
        public StrategyWarehousingService(IRepository<StrategyWarehousing, Guid> repository,
                                          IRepository<GoodsInfo.GoodsInfo, Guid> gRepository,
                                          UserManager userManager) : base(repository)
        {
            _gRepository = gRepository;
            _userManager = userManager;

            LogContext = DynamicDbContext.GetInstance(String.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                OptPath = "XMX.WMS.StrategyWarehousing.StrategyWarehousingService.",
                OptModule = "上架策略管理"
            };
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.StrategyPutawayManage_Get)]
        protected override IQueryable<StrategyWarehousing> CreateFilteredQuery(StrategyWarehousingPagedRequest input)
        {
            return Repository.GetAllIncluding()
                .WhereIf(!input.warehousing_name.IsNullOrWhiteSpace(), x => x.warehousing_name.Contains(input.warehousing_name))
                ;
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.StrategyPutawayManage_Get)]
        public override Task<StrategyWarehousingDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.StrategyPutawayManage_Add)]
        public override async Task<StrategyWarehousingDto> Create(StrategyWarehousingCreatedDto input)
        {
            //公司ID
            User loginuser = _userManager.GetUserByIdAsync(AbpSession.UserId.Value).Result;
            input.warehousing_company_id = loginuser.CompanyId;
            var flag = Repository.GetAll().Where(x => x.warehousing_name == input.warehousing_name).Any();
            if (flag)
                throw new UserFriendlyException("名称已存在！");
            StrategyWarehousingDto dto = await base.Create(input);
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
        [AbpAuthorize(PermissionNames.StrategyPutawayManage_Update)]
        public override async Task<StrategyWarehousingDto> Update(StrategyWarehousingUpdatedDto input)
        {
            var flag = Repository.GetAll().Where(x => x.warehousing_name == input.warehousing_name).Where(x => x.Id != input.Id).Any();
            if (flag)
                throw new UserFriendlyException("名称已存在！");
            StrategyWarehousing oldEntity = Repository.FirstOrDefault(x => x.Id == input.Id);
            string oldval = JsonConvert.SerializeObject(oldEntity);
            StrategyWarehousingDto dto = await base.Update(input);
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
        [AbpAuthorize(PermissionNames.StrategyPutawayManage_Delete)]
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
        [AbpAuthorize(PermissionNames.StrategyPutawayManage_Delete)]
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
