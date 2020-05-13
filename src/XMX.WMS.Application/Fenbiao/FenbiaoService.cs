using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.Fenbiao.Dto;
using System.Threading.Tasks;
using Abp.UI;
using Abp.Application.Services.Dto;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using System.Collections.Generic;
using Abp.AutoMapper;
using Abp.Extensions;

namespace XMX.WMS.Fenbiao
{
    public class FenbiaoService : AsyncCrudAppService<Fenbiao, FenbiaoDto, Guid, FenbiaoPagedRequest, FenbiaoCreatedDto, FenbiaoUpdatedDto>, IFenbiaoService
    {
        private readonly IRepository<InventoryInfo.InventoryInfo, Guid> _iRepository;
        private readonly IRepository<WarehouseInfo.WarehouseInfo, Guid> _wRepository;
        private readonly IRepository<WarehouseStock.WarehouseStock, Guid> _wsRepository;
        public FenbiaoService(IRepository<Fenbiao, Guid> repository,
                              IRepository<InventoryInfo.InventoryInfo, Guid> iRepository,
                              IRepository<WarehouseInfo.WarehouseInfo, Guid> wRepository,
                              IRepository<WarehouseStock.WarehouseStock, Guid> wsRepository) : base(repository)
        {
            _iRepository = iRepository;
            _wRepository = wRepository;
            _wsRepository = wsRepository;
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        protected override IQueryable<Fenbiao> CreateFilteredQuery(FenbiaoPagedRequest input)
        {
            DynamicDbContext context = DynamicDbContext.GetInstance("Fenbiao202004");
            //获取列表
            return context.Fenbiao.Where(x => !x.IsDeleted);
        }

        /// <summary>
        /// 查询单条
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        protected override async Task<Fenbiao> GetEntityByIdAsync(Guid id)
        {
            DynamicDbContext context = DynamicDbContext.GetInstance("Fenbiao202004");
            return context.Fenbiao.FirstOrDefault(x => x.Id == id && !x.IsDeleted);
        }

        /// <summary>
        /// 新增保存
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override async Task<FenbiaoDto> Create(FenbiaoCreatedDto input)
        {
            DynamicDbContext context = DynamicDbContext.GetInstance("Fenbiao202004");
            var flag = context.Fenbiao.Where(x => !x.IsDeleted).Where(x => x.code == input.code).Any();
            if (flag)
                throw new UserFriendlyException("编号已存在！");
            Fenbiao fenbiao = new Fenbiao();
            fenbiao.code = input.code;
            fenbiao.name = input.name;
            fenbiao = context.Fenbiao.Add(fenbiao).Entity;
            context.SaveChanges();
            return fenbiao.MapTo<FenbiaoDto>();
        }

        /// <summary>
        /// 编辑保存
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override async Task<FenbiaoDto> Update(FenbiaoUpdatedDto input)
        {
            DynamicDbContext context = DynamicDbContext.GetInstance("Fenbiao202004");
            var flag = context.Fenbiao.Where(x => x.Id != input.Id).Where(x => x.code == input.code).Any();
            if (flag)
                throw new UserFriendlyException("编号已存在！");
            Fenbiao fenbiao = context.Fenbiao.FirstOrDefault(x => x.Id == input.Id);
            fenbiao.Id = input.Id;
            fenbiao.LastModificationTime = DateTime.Now;
            fenbiao.code = input.code;
            fenbiao.name = input.name;
            fenbiao = context.Fenbiao.Update(fenbiao).Entity;
            context.SaveChanges();
            return fenbiao.MapTo<FenbiaoDto>();
        }

        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override async Task Delete(EntityDto<Guid> input)
        {
            DynamicDbContext context = DynamicDbContext.GetInstance("Fenbiao202004");
            Fenbiao fenbiao =  context.Fenbiao.FirstOrDefault(x => x.Id == input.Id);
            fenbiao.IsDeleted = true;
            context.Fenbiao.Update(fenbiao);
            context.SaveChanges();
        }

        /// <summary>
        /// 每天生成库存数量统计
        /// </summary>
        /// <returns></returns>
        public bool WarehouseStockEveryDay() 
        {
            string dateStr = DateTime.Now.ToString("yyyy-MM-dd");
            List<WarehouseStock.WarehouseStock> list = new List<WarehouseStock.WarehouseStock>();
            //查询所有的仓库
            var wids = _wRepository.GetAllIncluding().Select(x => new { id = x.Id }).ToList();
            //查询库存里有的仓库
            var wiids = _iRepository.GetAllIncluding(x => x.Slot)
                                    .GroupBy(x => new { id = x.Slot.slot_warehouse_id.Value})                    
                                    .Select(g => new { id = g.Key.id }).ToList();
            //库存里面没有的仓库直接new空数据
            var query2 = wids.Except(wiids).Select(x => new WarehouseStock.WarehouseStock
                                                    {
                                                        warehouse_stock = 0,
                                                        warehouse_date = dateStr,
                                                        warehouse_id = x.id
                                                    }).ToList();
            var query = _iRepository.GetAllIncluding(x => x.Slot)
                                    .GroupBy(x => new {
                                        inventory_warehouse_id= x.Slot.slot_warehouse_id.Value
                                    })
                                    .Select(g => new WarehouseStock.WarehouseStock
                                    {
                                        warehouse_stock = g.Sum(item => item.inventory_quantity),
                                        warehouse_date = dateStr,
                                        warehouse_id = g.Key.inventory_warehouse_id
                                    }).ToList();
            list.AddRange(query);
            list.AddRange(query2);
            bool flag;
            foreach (WarehouseStock.WarehouseStock stock in list)
            {
                flag = _wsRepository.GetAllIncluding(x => x.Warehouse).Where(x => x.warehouse_date.Equals(dateStr))
                                                     .Where(x => x.warehouse_id == stock.warehouse_id).Any();
                if (!flag)
                    _wsRepository.Insert(stock);
            }
            return true;
        }
    }
}
