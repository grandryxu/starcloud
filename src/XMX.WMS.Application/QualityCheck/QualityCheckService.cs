using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.QualityCheck.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using System.Threading.Tasks;
using Abp.UI;
using System.Collections.Generic;
using XMX.WMS.InventoryInfo.Dto;
using XMX.WMS.Authorization;
using Abp.Authorization;
using Abp.Application.Services.Dto;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using XMX.WMS.WMSOptLogInfo;
using Newtonsoft.Json;

namespace XMX.WMS.QualityCheck
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-03-20 15:27:25
    /// 描 述：
    ///</summary>
    [AbpAuthorize(PermissionNames.QualityCheckout)]
    public class QualityCheckService : AsyncCrudAppService<QualityCheck, QualityCheckDto, Guid, QualityCheckPagedRequest, QualityCheckCreateDto, QualityCheckUpdateDto>, IQualityCheckService
    {
        private readonly IRepository<InventoryInfo.InventoryInfo, Guid> InventoryRepository;
        private readonly IRepository<ExportBillbody.ExportBillbody, Guid> BillBodyRepository;
        private readonly IRepository<ExportBillhead.ExportBillhead, Guid> BillHeadRepository;
        private readonly IRepository<QualityCheckDetail.QualityCheckDetail, Guid> detailRepository;
        private readonly IRepository<QualityInfo.QualityInfo, Guid> QualityRepository;

        //日志
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public QualityCheckService(IRepository<QualityCheck, Guid> repository,
            IRepository<InventoryInfo.InventoryInfo, Guid> InventoryRepository,
            IRepository<ExportBillbody.ExportBillbody, Guid> BillBodyRepository,
            IRepository<ExportBillhead.ExportBillhead, Guid> BillHeadRepository,
            IRepository<QualityCheckDetail.QualityCheckDetail, Guid> detailRepository,
            IRepository<QualityInfo.QualityInfo, Guid> QualityRepository) : base(repository)
        {
            this.InventoryRepository = InventoryRepository;
            this.BillBodyRepository = BillBodyRepository;
            this.BillHeadRepository = BillHeadRepository;
            this.detailRepository = detailRepository;
            this.QualityRepository = QualityRepository;

            LogContext = DynamicDbContext.GetInstance(String.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                OptPath = "XMX.WMS.QualityCheck.QualityCheckService.",
                OptModule = "质量抽检管理"
            };

        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.QualityCheckout_Get)]
        protected override IQueryable<QualityCheck> CreateFilteredQuery(QualityCheckPagedRequest input)
        {
            return Repository.GetAllIncluding()
                .WhereIf(!input.check_bill.IsNullOrWhiteSpace(), x => x.check_bill.Contains(input.check_bill))
                .WhereIf(!input.check_batch_no.IsNullOrWhiteSpace(), x => x.check_batch_no.Contains(input.check_batch_no))
                .WhereIf(!input.check_goods_name.IsNullOrWhiteSpace(), x => x.check_goods_name.Contains(input.check_goods_name))
                .WhereIf(!input.checked_quality_status.IsNullOrWhiteSpace(), x => x.checked_quality_status.Contains(input.checked_quality_status))
                .WhereIf(!input.origin_quality_status.IsNullOrWhiteSpace(), x => x.origin_quality_status.Contains(input.origin_quality_status))
                .WhereIf((!input.check_time_b.IsNullOrWhiteSpace() && !input.check_time_e.IsNullOrWhiteSpace()), x => Convert.ToDateTime(input.check_time_b + " 00:00:00") <= x.CreationTime && x.CreationTime <= Convert.ToDateTime(input.check_time_e + "  23:59:59"))
                ;

        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override Task<QualityCheckDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }


        /// <summary>
        /// 新增保存
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.QualityCheckout_Add)]
        public override async Task<QualityCheckDto> Create(QualityCheckCreateDto input)
        {
            QualityCheckDto dto = await base.Create(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Create", WMSOptLogInfo.WMSOptLogInfo.ADD, "", JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 获取Inventory表中去重的批次物料信息
        /// </summary>
        /// <returns></returns>
        public List<BatchGoodsDto> GetBatchGoods(Guid inventory_warehouse_id)
        {
            List<InventoryInfo.InventoryInfo> iiList = InventoryRepository.GetAllIncluding(x => x.Goods, x => x.Quality)
                .WhereIf(inventory_warehouse_id!=null&&inventory_warehouse_id!=Guid.Empty,x=>x.inventory_warehouse_id==inventory_warehouse_id)
                .Where(x => x.inventory_batch_no != null && x.inventory_goods_id != null&&x.inventory_goods_id!=Guid.Empty)
                .Where(x=>x.inventory_quality_status!=null)
                .ToList();
            List<BatchGoodsDto> list = new List<BatchGoodsDto>();
            if (iiList.Count > 0)
                list = iiList.Select(x => new BatchGoodsDto(x.inventory_batch_no, x.Goods == null ? "空值" : x.Goods.goods_code, x.Goods == null ? "空值" : x.Goods.goods_name,
                    x.Quality == null ? "空值" : x.Quality.quality_name, x.Quality == null ? Guid.Empty : x.Quality.Id)).GroupBy(g => new { g.inventory_batch_no, g.goods_code, g.goods_name, g.quality_status, g.quality_status_id }).Select(m => m.First()).ToList();
            return list;
        }

        /// <summary>
        /// 按照条件查询抽检托盘对应的库存inventory信息
        /// </summary>
        /// <param name="input"></param>
        public List<InventoryInfo.InventoryInfo> GetCheckInventory(InventoryInfoPagedRequest input)
        {
            return InventoryRepository.GetAllIncluding(x => x.Goods)
                .Where(x => x.inventory_stock_status == StockStatus.是)
                .WhereIf(!input.inventory_bill_bar.IsNullOrWhiteSpace(), x => x.inventory_bill_bar.Contains(input.inventory_bill_bar))
                .WhereIf(!input.inventory_batch_no.IsNullOrWhiteSpace(), x => x.inventory_batch_no.Contains(input.inventory_batch_no))
                .WhereIf(!input.inventory_lots_no.IsNullOrWhiteSpace(), x => x.inventory_lots_no.Contains(input.inventory_lots_no)).ToList();

        }

        /// <summary>
        /// 抽检完成后将抽检物料状态回写到库存
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        /// 此操作暂时不用，后期需要再放开
        //public InventoryInfo.InventoryInfo UpdateInventoryQuality(InventoryQueryParam input)
        //{
        //    //先根据批次号查询到实体
        //    InventoryInfo.InventoryInfo inventoryInfo = InventoryRepository.Single(x => x.inventory_batch_no.Contains(input.inventory_batch_no));
        //    if (inventoryInfo == null)
        //        return null;
        //    //更新
        //    inventoryInfo.inventory_quality_status = input.checked_quality_status_id;
        //    InventoryRepository.Update(inventoryInfo);
        //    return inventoryInfo;
        //}


        /// <summary>
        /// 对抽检单据生成出库单
        /// </summary>
        /// <returns></returns>
        public QualityCheck CreateOutBillByChecked(QualityCheckPagedRequest input)
        {
            //通过单据号查询抽检单据实体
            QualityCheck check = this.Repository.Single(x => x.check_bill.Equals(input.check_bill));
            //通过抽检单据号查询抽检明细
            List<QualityCheckDetail.QualityCheckDetail> details = this.detailRepository.GetAllIncluding().WhereIf(!input.check_bill.IsNullOrWhiteSpace(),x=>x.check_bill_code.Equals(input.check_bill)).ToList();
            List<string> stock_codes = new List<string>();
            details.ForEach(item=> {
                stock_codes.Add(item.inventory_stock_code);
            });

            //通过托盘号查找Inventory表中实体信息。
            List<InventoryInfo.InventoryInfo> infos = this.InventoryRepository.GetAllIncluding()
                .WhereIf(input.inventory_warehouse_id != null && input.inventory_warehouse_id != Guid.Empty, x => x.inventory_warehouse_id == input.inventory_warehouse_id)
                .Where(x => x.inventory_stock_code.IsIn(stock_codes.ToArray())).ToList();
     
            try
            {
                //创建出库单
                //表头
                ExportBillhead.ExportBillhead exportBillhead = new ExportBillhead.ExportBillhead();
                //先用时间戳生成出库单，后期按照规则做调整
                exportBillhead.exphead_code = Convert.ToInt64((DateTime.Now - new DateTime(1970, 1, 1, 0, 0, 0, 0)).TotalSeconds).ToString();
                exportBillhead.exphead_warehouse_id = input.inventory_warehouse_id.Value;
                exportBillhead = this.BillHeadRepository.Insert(exportBillhead);
                //表体
                ExportBillbody.ExportBillbody exportBillbody = new ExportBillbody.ExportBillbody();
                exportBillbody.expbody_imphead_id = exportBillhead.Id;
                infos.ForEach(item => {
                    exportBillbody.expbody_quality_status = item.inventory_quality_status;
                    exportBillbody.expbody_goods_id = item.inventory_goods_id;
                    exportBillbody.expbody_lots_no = item.inventory_lots_no;
                    exportBillbody.expbody_remark = item.inventory_remark;
                    exportBillbody.expbody_product_lineid = item.inventory_product_lineid;
                    exportBillbody.expbody_product_date = item.inventory_product_date;
                    exportBillbody.expbody_bill_bar = exportBillhead.exphead_code;
                    exportBillbody.expbody_batch_no = item.inventory_batch_no;
                    this.BillBodyRepository.Insert(exportBillbody);
                });
                check.quality_check_export_code= exportBillhead.exphead_code;
                check = this.Repository.Update(check);
                return check;
            }
            catch (Exception e)
            {
                throw new Exception("保存出错");
                
            }
         
            
        }
        /// <summary>
        /// 检测放行操作
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public QualityCheck CheckReleased(QualityCheckPagedRequest input)
        {
            //通过单据号查询抽检单据实体
            QualityCheck check = this.Repository.Single(x => x.check_bill.Equals(input.check_bill));
            // 查询批次号对应Inventory
            List<InventoryInfo.InventoryInfo> infos = this.InventoryRepository.GetAllIncluding()
                 .WhereIf(input.inventory_warehouse_id != null && input.inventory_warehouse_id != Guid.Empty, x => x.inventory_warehouse_id == input.inventory_warehouse_id)
                 .Where(x => x.inventory_batch_no.Equals(check.check_batch_no)).ToList();
            try
            {
                //获取质量状态表中质量名为“合格”的质量状态
                QualityInfo.QualityInfo info = this.QualityRepository.Single(x => x.quality_name.Equals("合格"));
                if (info!=null)
                {  //更新当前批次号所有物料质量状态
                    infos.ForEach(item => {
                        this.InventoryRepository.Update(item.Id, x => x.inventory_quality_status = info.Id);
                    });
                    check.check_released = 1;
                  check=this.Repository.Update(check);
                    return check;

                }
                return null;
            }
            catch (Exception e)
            {
                throw new Exception("放行操作服务器出错");
            }
    


        }

        /// <summary>
        /// 更新
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override async Task<QualityCheckDto> Update(QualityCheckUpdateDto input)
        {
            QualityCheck oldEntity = Repository.FirstOrDefault(x => x.Id == input.Id);
            string oldval = JsonConvert.SerializeObject(oldEntity);
            QualityCheckDto dto = await base.Update(input);
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
        [AbpAuthorize(PermissionNames.QualityCheckout_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
             WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Delete", WMSOptLogInfo.WMSOptLogInfo.DELETE, input.Id.ToString(), "", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            await Repository.DeleteAsync(x => x.Id == input.Id);
        }
    }
         
}
