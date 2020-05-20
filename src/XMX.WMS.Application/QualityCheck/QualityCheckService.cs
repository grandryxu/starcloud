using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.QualityCheck.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using System.Threading.Tasks;
using System.Collections.Generic;
using XMX.WMS.InventoryInfo.Dto;
using XMX.WMS.Authorization;
using Abp.Authorization;
using Abp.Application.Services.Dto;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using XMX.WMS.WMSOptLogInfo;
using Newtonsoft.Json;
using XMX.WMS.Base.Session;

namespace XMX.WMS.QualityCheck
{
    ///<summary>
    /// 版 本：
    /// 创建人：lunan
    /// 日 期：2020-05-18 14:23:53
    /// 描 述：抽检
    ///</summary>
    [AbpAuthorize(PermissionNames.QualityCheckout)]
    public class QualityCheckService : AsyncCrudAppService<QualityCheck, QualityCheckDto, Guid, QualityCheckPagedRequest, QualityCheckCreateDto, QualityCheckUpdateDto>, IQualityCheckService
    {
        private readonly IRepository<InventoryInfo.InventoryInfo, Guid> InventoryRepository;
        private readonly IRepository<ExportBillbody.ExportBillbody, Guid> BillBodyRepository;
        private readonly IRepository<ExportBillhead.ExportBillhead, Guid> BillHeadRepository;
        private readonly IRepository<QualityCheckDetail.QualityCheckDetail, Guid> detailRepository;
        private readonly IRepository<QualityInfo.QualityInfo, Guid> QualityRepository;
        private readonly Guid UserCompanyId;
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
            UserCompanyId = AbpSession.GetCompanyId();
            LogContext = DynamicDbContext.GetInstance(string.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                CompanyId = UserCompanyId,
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
            return Repository.GetAllIncluding(x => x.Goods, x => x.Quality1, x => x.Quality2)
                    .WhereIf(AbpSession.UserId != 1, x => x.check_company_id == UserCompanyId)
                    .WhereIf(!input.check_code.IsNullOrWhiteSpace(), x => x.check_code.Contains(input.check_code))
                    .WhereIf(!input.check_batch_no.IsNullOrWhiteSpace(), x => x.check_batch_no.Contains(input.check_batch_no))
                    .WhereIf(!input.check_goods_name.IsNullOrWhiteSpace(), x => x.Goods.goods_name.Contains(input.check_goods_name))
                    .WhereIf(input.check_origin_quality.HasValue, x => x.check_origin_quality == input.check_origin_quality)
                    .WhereIf(input.check_checked_quality.HasValue, x => x.check_checked_quality == input.check_checked_quality)
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
            input.check_company_id = UserCompanyId;
            QualityCheckDto dto = await base.Create(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Create", WMSOptLogInfo.WMSOptLogInfo.ADD, "", JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 按照条件查询抽检托盘对应的库存inventory信息
        /// </summary>
        /// <param name="input"></param>
        public List<InventoryInfo.InventoryInfo> GetCheckInventory(InventoryInfoPagedRequest input)
        {
            return InventoryRepository.GetAllIncluding(x => x.Goods)
                    .WhereIf(AbpSession.UserId != 1, x => x.inventory_company_id == UserCompanyId)
                    .Where(x => x.inventory_stock_status == StockStatus.是)
                    .WhereIf(!input.inventory_bill_bar.IsNullOrWhiteSpace(), x => x.inventory_bill_bar.Contains(input.inventory_bill_bar))
                    .WhereIf(!input.inventory_batch_no.IsNullOrWhiteSpace(), x => x.inventory_batch_no.Contains(input.inventory_batch_no))
                    .WhereIf(!input.inventory_lots_no.IsNullOrWhiteSpace(), x => x.inventory_lots_no.Contains(input.inventory_lots_no)).ToList();

        }

        /// <summary>
        /// 对抽检单据生成出库单
        /// </summary>
        /// <returns></returns>
        public QualityCheck CreateOutBillByChecked(QualityCheckPagedRequest input)
        {
            //通过单据号查询抽检单据实体
            QualityCheck check = Repository.Get(input.check_id.Value);
            //通过抽检单据号查询抽检明细
            List<QualityCheckDetail.QualityCheckDetail> details = detailRepository.GetAll().WhereIf(input.check_id.HasValue, x => x.quality_check_id == input.check_id).ToList();
            List<string> stock_codes = new List<string>();
            details.ForEach(item=> {
                stock_codes.Add(item.inventory_stock_code);
            });
            //通过托盘号查找Inventory表中实体信息。
            List<InventoryInfo.InventoryInfo> infos = this.InventoryRepository.GetAllIncluding(x => x.Slot)
                .WhereIf(AbpSession.UserId != 1, x => x.inventory_company_id == UserCompanyId)
                .WhereIf(input.check_warehouse_id != null && input.check_warehouse_id != Guid.Empty, x => x.Slot.slot_warehouse_id == input.check_warehouse_id)
                .Where(x => x.inventory_stock_code.IsIn(stock_codes.ToArray())).ToList();
            try
            {
                //创建出库单
                //表头
                ExportBillhead.ExportBillhead exportBillhead = new ExportBillhead.ExportBillhead();
                //先用时间戳生成出库单，后期按照规则做调整
                exportBillhead.exphead_code = Convert.ToInt64((DateTime.Now - new DateTime(1970, 1, 1, 0, 0, 0, 0)).TotalSeconds).ToString();
                exportBillhead.exphead_warehouse_id = input.check_warehouse_id.Value;
                exportBillhead = this.BillHeadRepository.Insert(exportBillhead);
                //表体
                ExportBillbody.ExportBillbody exportBillbody = new ExportBillbody.ExportBillbody();
                exportBillbody.expbody_head_id = exportBillhead.Id;
                infos.ForEach(item => {
                    exportBillbody.expbody_quality_status = item.inventory_quality_status;
                    exportBillbody.expbody_goods_id = item.inventory_goods_id;
                    exportBillbody.expbody_lots_no = item.inventory_lots_no;
                    exportBillbody.expbody_remark = item.inventory_remark;
                    exportBillbody.expbody_product_lineid = item.inventory_product_lineid;
                    exportBillbody.expbody_product_date = item.inventory_product_date;
                    exportBillbody.expbody_bill_bar = exportBillhead.exphead_code;
                    exportBillbody.expbody_batch_no = item.inventory_batch_no;
                    BillBodyRepository.Insert(exportBillbody);
                });
                check.check_export_bill = exportBillhead.exphead_code;
                check = Repository.Update(check);
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
            QualityCheck check = this.Repository.Single(x => x.check_code.Equals(input.check_code));
            // 查询批次号对应Inventory
            List<InventoryInfo.InventoryInfo> infos = this.InventoryRepository.GetAllIncluding(x => x.Slot)
                 .WhereIf(AbpSession.UserId != 1, x => x.inventory_company_id == UserCompanyId)
                 .WhereIf(input.check_warehouse_id != null && input.check_warehouse_id != Guid.Empty, x => x.Slot.slot_warehouse_id == input.check_warehouse_id)
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
                    check.check_released_status = CheckReleasedStatus.已放行;
                    check = this.Repository.Update(check);
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
            QualityCheck oldEntity = Repository.Get(input.Id);
            string oldval = JsonConvert.SerializeObject(oldEntity);
            QualityCheckDto dto = await base.Update(input);
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
        [AbpAuthorize(PermissionNames.QualityCheckout_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
             WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Delete", WMSOptLogInfo.WMSOptLogInfo.DELETE, input.Id.ToString(), "", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            await Repository.DeleteAsync(x => x.Id == input.Id);
        }
    }
         
}
