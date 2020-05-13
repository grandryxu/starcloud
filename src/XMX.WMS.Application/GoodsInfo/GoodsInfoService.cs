using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.GoodsInfo.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using System.Collections.Generic;
using System.Threading.Tasks;
using Abp.UI;
using XMX.WMS.Authorization;
using Abp.Authorization;
using Abp.Application.Services.Dto;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using Newtonsoft.Json;

namespace XMX.WMS.GoodsInfo
{
    [AbpAuthorize(PermissionNames.MaterialBasisInfo)]
    public class GoodsInfoService : AsyncCrudAppService<GoodsInfo, GoodsInfoDto, Guid, GoodsInfoPagedRequest, GoodsInfoCreatedDto, GoodsInfoUpdatedDto>, IGoodsInfoService
    {
        private readonly IRepository<AreaInfo.AreaInfo, Guid> _areaInfo;
        private readonly IRepository<WarehouseInfo.WarehouseInfo, Guid> _warehouseInfo;
        private readonly IRepository<ImportBillbody.ImportBillbody, Guid> _ibRepository;
        private readonly IRepository<ImportOrder.ImportOrder, Guid> _ioRepository;
        private readonly IRepository<ExportBillbody.ExportBillbody, Guid> _ebRepository;
        private readonly IRepository<ExportOrder.ExportOrder, Guid> _eoRepository;
        private readonly IRepository<InventoryInfo.InventoryInfo, Guid> _iiRepository;

        //日志
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public GoodsInfoService(IRepository<GoodsInfo, Guid> repository,
             IRepository<AreaInfo.AreaInfo, Guid> areatRepository,
            IRepository<WarehouseInfo.WarehouseInfo, Guid> warehouseRepository,
            IRepository<ImportBillbody.ImportBillbody, Guid> ibRepository,
            IRepository<ImportOrder.ImportOrder, Guid> ioRepository,
            IRepository<ExportBillbody.ExportBillbody, Guid> ebRepository,
            IRepository<ExportOrder.ExportOrder, Guid> eoRepository,
            IRepository<InventoryInfo.InventoryInfo, Guid> iiRepository) : base(repository)
        {
            _areaInfo = areatRepository;
            _warehouseInfo = warehouseRepository;
            _ibRepository = ibRepository;
            _ioRepository = ioRepository;
            _ebRepository = ebRepository;
            _eoRepository = eoRepository;
            _iiRepository = iiRepository;

            LogContext = DynamicDbContext.GetInstance(String.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                OptPath = "XMX.WMS.GoodsInfo.GoodsInfoService.",
                OptModule = "物料基础信息"
            };

        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override Task<GoodsInfoDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 查询该仓库下的物料
        /// </summary>
        /// <param name="guid"></param>
        /// <returns></returns>
        public List<WarehousematerialsInfoDto> GetWarehousematerials(Guid guid)
        {
            var list = (from a in Repository.GetAll()
                        join b in _areaInfo.GetAll() on new { Id = a.Area.Id } equals new { Id = b.Id }
                        join c in _warehouseInfo.GetAll() on new { Id = b.Warehouse.Id } equals new { Id = c.Id }

                        where
                          c.Id == guid
                        select new
                        {
                            a.Id,
                            a.goods_code,
                            a.goods_name,
                        }).ToList();
            var relist = new List<WarehousematerialsInfoDto>();
            foreach (var item in list)
                relist.Add(new WarehousematerialsInfoDto { Id = item.Id, goods_code = item.goods_code, goods_name = item.goods_name });
            return relist;
        }


        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.MaterialBasisInfo_Get)]
        protected override IQueryable<GoodsInfo> CreateFilteredQuery(GoodsInfoPagedRequest input)
        {
            return Repository.GetAllIncluding()
                .WhereIf(!input.goods_code.IsNullOrWhiteSpace(), x => x.goods_code.Contains(input.goods_code))
                .WhereIf(!input.goods_name.IsNullOrWhiteSpace(), x => x.goods_name.Contains(input.goods_name))
                .WhereIf(!input.goods_area_id.IsNullOrWhiteSpace(), x => x.goods_area_id.ToString().Contains(input.goods_area_id))
                .WhereIf(!input.goods_monitor_id.IsNullOrWhiteSpace(), x => x.goods_monitor_id.ToString().Contains(input.goods_monitor_id))
                .WhereIf(!input.goods_warehousing_id.IsNullOrWhiteSpace(), x => x.goods_warehousing_id.ToString().Contains(input.goods_warehousing_id))
                .WhereIf(!input.goods_distribution_id.IsNullOrWhiteSpace(), x => x.goods_distribution_id.ToString().Contains(input.goods_distribution_id))
                ;
        }

        /// <summary>
        /// 物料下拉列表
        /// </summary>
        /// <returns></returns>
        public List<GoodsInfoListDto> GetGoodsInfoList()
        {
            var list = Repository.GetAll().Select(item => new { item.Id, item.goods_code, item.goods_name }).ToList();
            var relist = new List<GoodsInfoListDto>();
            foreach (var item in list)
            {
                relist.Add(new GoodsInfoListDto { Id = item.Id, goods_code = item.goods_code, goods_name = item.goods_name });
            }
            return relist;
        }

        /// <summary>
        /// 策略设置 更新状态 1;监控策略配置 2;上架策略配置 3;分配策略配置
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public bool EditStrategy(StrategyRequest input)
        {
            if (input.idList != null && input.idList.Count > 0)
            {
                List<GoodsInfo> goodsInfoList = Repository.GetAllIncluding().Where(x => x.Id.IsIn(input.idList.ToArray<Guid>())).ToList();
                if (goodsInfoList != null && goodsInfoList.Count > 0)
                {
                    foreach (GoodsInfo goodsInfoRow in goodsInfoList)
                    {
                        switch (input.strategy_status)
                        {
                            case 1:
                                goodsInfoRow.goods_monitor_id = new Guid(input.strategy_id);
                                break;
                            case 2:
                                goodsInfoRow.goods_warehousing_id = new Guid(input.strategy_id);
                                break;
                            default:
                                goodsInfoRow.goods_distribution_id = new Guid(input.strategy_id);
                                break;
                        }
                        Repository.Update(goodsInfoRow);
                    }
                    return true;
                }
            }
            return false;
        }
        /// <summary>
        /// 新增 
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.MaterialBasisInfo_Add)]
        public override async Task<GoodsInfoDto> Create(GoodsInfoCreatedDto input)
        {
            var is_recode = Repository.GetAll().Where(x => x.goods_code == input.goods_code).Where(x => !x.IsDeleted).Any();
            if (is_recode)
                throw new UserFriendlyException("物料编码已存在！");
          
            GoodsInfoDto dto= await base.Create(input);
            WMSOptLogInfo.WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Create", WMSOptLogInfo.WMSOptLogInfo.ADD, "", JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }
        /// <summary>
        /// 修改
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.MaterialBasisInfo_Update)]
        public override async Task<GoodsInfoDto> Update(GoodsInfoUpdatedDto input)
        {
            var query = Repository.GetAll().Where(x => x.Id != input.Id);
            var is_recode = query.Where(x => x.goods_code == input.goods_code).Where(x => !x.IsDeleted).Any();
            if (is_recode)
            {
                WMSOptLogInfo.WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, "", "", WMSOptLogInfo.WMSOptLogInfo.FAIL);
                LogContext.WMSOptLogInfo.Add(logInfoEntity);
                LogContext.SaveChanges();
                throw new UserFriendlyException("物料编码已存在！");
            }   
            GoodsInfo oldEntity = Repository.FirstOrDefault(x => x.Id == input.Id);
            string oldval = JsonConvert.SerializeObject(oldEntity);
            GoodsInfoDto dto = await base.Update(input);
            WMSOptLogInfo.WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, oldval, JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 外部调用接口修改
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public async Task<GoodsInfoDto> UpdateExternal(GoodsInfoUpdatedDto input)
        {
            var query = Repository.GetAll().Where(x => x.goods_external_id != input.goods_external_id);
           
            input.Id = Repository.FirstOrDefault(x => x.goods_external_id == input.goods_external_id).Id;
            var is_recode = query.Where(x => x.goods_code == input.goods_code).Where(x => !x.IsDeleted).Any();
            if (is_recode)
                throw new UserFriendlyException("物料编码已存在！");
            return await base.Update(input);
        }




        /// <summary>
        /// 批量删除
        /// </summary>
        /// <param name="idList"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.MaterialBasisInfo_Delete)]
        public Task DelteList(List<Guid> idList)
        {
            //dynamic jsonValues = idList;
            //JArray jsonInput = jsonValues.idList;
            //List<Guid> list = jsonInput.ToObject<List<Guid>>();
            var strName = "";
            List<Guid> idinfolist = new List<Guid>();
            if (null == idList)
                throw new UserFriendlyException("参数解析异常，请联系管理员！");
            foreach (var item in idList)
            {
                var name = Repository.FirstOrDefault(x => x.Id == item).goods_name;
               
                var rkis_recode = _ibRepository.GetAll().Where(x => x.impbody_goods_id == item).Where(x => !x.IsDeleted).Any();
                var rklsis_recode = _ioRepository.GetAll().Where(x => x.imporder_goods_id == item).Where(x => !x.IsDeleted).Any();
                var ckis_recode = _ebRepository.GetAll().Where(x => x.expbody_goods_id == item).Where(x => !x.IsDeleted).Any();
                var cklsis_recode = _eoRepository.GetAllIncluding(x => x.ExportBillbody).Where(x => x.ExportBillbody.expbody_goods_id == item).Where(x => !x.IsDeleted).Any();
                var kcis_recode = _iiRepository.GetAll().Where(x => x.inventory_goods_id == item).Where(x => !x.IsDeleted).Any();


                if (rkis_recode || rklsis_recode || ckis_recode || cklsis_recode || kcis_recode)
                {
                    strName += name+"，";
                }
                else
                {
                    idinfolist.Add(item);
                }
            }
            if (!strName.IsNullOrWhiteSpace())
            {
                //if (idinfolist.Count>0)
                //{
                //    Repository.DeleteAsync(x => x.Id.IsIn(idinfolist.ToArray<Guid>()));
                //}
                throw new UserFriendlyException("" + strName.Substring(0, strName.Length - 1) + "在使用，不能删除！");
            }
            else
            {
                return Repository.DeleteAsync(x => x.Id.IsIn(idinfolist.ToArray<Guid>()));
            }
            




           
        }

        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.MaterialBasisInfo_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
            var code =  Repository.FirstOrDefault(x => x.Id == input.Id).Id;
            var rkis_recode = _ibRepository.GetAll().Where(x => x.impbody_goods_id == input.Id).Where(x => !x.IsDeleted).Any();
            var rklsis_recode = _ioRepository.GetAll().Where(x => x.imporder_goods_id == input.Id).Where(x => !x.IsDeleted).Any();
            var ckis_recode = _ebRepository.GetAll().Where(x => x.expbody_goods_id == input.Id).Where(x => !x.IsDeleted).Any();
            var cklsis_recode = _eoRepository.GetAllIncluding(x => x.ExportBillbody).Where(x => x.ExportBillbody.expbody_goods_id == input.Id).Where(x => !x.IsDeleted).Any();
            var kcis_recode = _iiRepository.GetAll().Where(x => x.inventory_goods_id == input.Id).Where(x => !x.IsDeleted).Any();

            if (rkis_recode || rklsis_recode || ckis_recode || cklsis_recode || kcis_recode)
                throw new UserFriendlyException("物料编码在在使用，不能删除！");
            WMSOptLogInfo.WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Delete", WMSOptLogInfo.WMSOptLogInfo.DELETE, input.Id.ToString(), "", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            await Repository.DeleteAsync(x => x.Id == input.Id);
        }
    }
}
