using Abp.Application.Services;
using Abp.Application.Services.Dto;
using Abp.Authorization;
using Abp.Domain.Repositories;
using Abp.Linq.Extensions;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using XMX.WMS.Authorization;
using XMX.WMS.Base.Session;
using XMX.WMS.WarehouseStock.Dto;

namespace XMX.WMS.WarehouseStock
{
    [AbpAuthorize(PermissionNames.WarehouseBaseInfo)]
    public class WarehouseStockService : AsyncCrudAppService<WarehouseStock, WarehouseStockDto, Guid, WarehouseStockPagedRequest, WarehouseStockCreatedDto, WarehouseStockUpdatedDto>, IWarehouseStockService
    {
        private readonly IRepository<WarehouseInfo.WarehouseInfo, Guid> _wRepository;
        private readonly Guid UserCompanyId;
        public WarehouseStockService(IRepository<WarehouseStock, Guid> repository,
            IRepository<WarehouseInfo.WarehouseInfo, Guid> wRepository) : base(repository)
        {
            _wRepository = wRepository;
            UserCompanyId = AbpSession.GetCompanyId();
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.WarehoueInfo_Get)]
        protected override IQueryable<WarehouseStock> CreateFilteredQuery(WarehouseStockPagedRequest input)
        {
            return Repository.GetAllIncluding(x => x.Warehouse)
                    .WhereIf(AbpSession.UserId != 1, x => x.Warehouse.warehouse_company_id == UserCompanyId)
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
            List<WarehouseInfo.WarehouseInfo> wlist = _wRepository.GetAll()
                .WhereIf(AbpSession.UserId != 1, x => x.warehouse_company_id == UserCompanyId).ToList();
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
            return null;
        }

        /// <summary>
        /// 更新
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.WarehoueInfo_Update)]
        public override async Task<WarehouseStockDto> Update(WarehouseStockUpdatedDto input)
        {
            return null;
        }

        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.WarehoueInfo_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
        }

    }
}

