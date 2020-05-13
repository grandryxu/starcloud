using Abp.Application.Services;
using Abp.Application.Services.Dto;
using Abp.Authorization;
using Abp.Domain.Repositories;
using Abp.Extensions;
using Abp.Linq.Extensions;
using Abp.Runtime.Session;
using Abp.UI;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using XMX.WMS.Authorization;
using XMX.WMS.Authorization.Users;
using XMX.WMS.WarehouseStock.Dto;

namespace XMX.WMS.WarehouseStock
{
    [AbpAuthorize(PermissionNames.WarehouseBaseInfo)]
    public class WarehouseStockService : AsyncCrudAppService<WarehouseStock, WarehouseStockDto, Guid, WarehouseStockPagedRequest, WarehouseStockCreatedDto, WarehouseStockUpdatedDto>, IWarehouseStockService
    {
        private readonly IRepository<WarehouseInfo.WarehouseInfo, Guid> _wRepository;
        private readonly UserManager _userManager;
        public WarehouseStockService(IRepository<WarehouseStock, Guid> repository,
            IRepository<WarehouseInfo.WarehouseInfo, Guid> wRepository,
            UserManager userManager) : base(repository)
        {
            _wRepository = wRepository;
            _userManager = userManager;
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.WarehoueInfo_Get)]
        protected override IQueryable<WarehouseStock> CreateFilteredQuery(WarehouseStockPagedRequest input)
        {
            return Repository.GetAllIncluding()
                    .WhereIf(input.warehouse_id.HasValue, x => x.warehouse_id == input.warehouse_id)
                    .OrderByDescending(x => x.CreationTime)
                    ;
        }

        /// <summary>
        /// 库存日期统计
        /// </summary>
        /// <param name="days_count">近n日统计</param>
        /// <returns></returns>
        public List<List<WarehouseStock>> GetList(int days_count)
        {
            User loginuser = _userManager.GetUserByIdAsync(AbpSession.UserId.Value).Result;
            List<WarehouseInfo.WarehouseInfo> wlist = _wRepository.GetAll().Where(x => x.warehouse_company_id == loginuser.CompanyId).ToList();
            if (wlist == null || wlist.Count == 0)
                return null;
            List<List<WarehouseStock>> list = new List<List<WarehouseStock>>();
            foreach (WarehouseInfo.WarehouseInfo warehouse in wlist)
            {
                //获取仓库库存最近n条
                List<WarehouseStock> slist = Repository.GetAllIncluding(x => x.Warehouse).Where(x => x.warehouse_id == warehouse.Id)
                                                        .OrderByDescending(x => x.CreationTime).Take(days_count).ToList();
                list.Add(slist);
            }
            return list;
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override Task<WarehouseStockDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.WarehoueInfo_Add)]
        public override async Task<WarehouseStockDto> Create(WarehouseStockCreatedDto input)
        {
            var isredate = Repository.GetAll().Where(x => x.warehouse_id == input.warehouse_id).Where(x => x.warehouse_date == input.warehouse_date).Any();
            if (!isredate)
            {
                return await base.Create(input);
            }
            else
            {
                throw new Abp.UI.UserFriendlyException("库存日期重复！");
            }
        }

        /// <summary>
        /// 更新
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.WarehoueInfo_Update)]
        public override async Task<WarehouseStockDto> Update(WarehouseStockUpdatedDto input)
        {
            var ishave = Repository.GetAll().Where(x => x.Id == input.Id).Any();
            if (!ishave)
            {
                throw new Abp.UI.UserFriendlyException("该条数据不存在！");
            }
            var isredate = Repository.GetAll().Where( x => x.Id != input.Id).Where(x => x.warehouse_id == input.warehouse_id)
                .Where(x => x.warehouse_date == input.warehouse_date).Any();
            if (!isredate)
            {
                return await base.Update(input);
            }
            else
            {
                throw new Abp.UI.UserFriendlyException("库存日期重复！");
            }
        }

        /// <summary>
        /// 批量删除
        /// </summary>
        /// <param name="idList"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.WarehoueInfo_Delete)]
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
        [AbpAuthorize(PermissionNames.WarehoueInfo_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
            await Repository.DeleteAsync(x => x.Id == input.Id);
        }

    }
}

