using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.ExportBillhead
{
    /// <summary>
    /// 出库表头表
    /// </summary>
    public class ExportBillhead : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 波次号
        /// </summary>
        public string exphead_wave_no { get; set; }
        /// <summary>
        /// 单号
        /// </summary>
        public string exphead_code { get; set; }
        /// <summary>
        /// 外部单据ID
        /// </summary>
        public string exphead_external_id { get; set; }
        /// <summary>
        /// 外部单据号
        /// </summary>
        public string exphead_external_code { get; set; }
        /// <summary>
        /// 单据日期
        /// </summary>
        public DateTime exphead_date { get; set; }
        /// <summary>
        /// 整单执行标志
        /// </summary>
        public ExecuteFlag exphead_execute_flag { get; set; }
        /// <summary>
        /// 整单作废标志
        /// </summary>
        public NousedFlag exphead_noused_flag { get; set; }
        /// <summary>
        /// 整单审核标志
        /// </summary>
        public AuditFlag exphead_audit_flag { get; set; }
        /// <summary>
        /// 整单上传标志
        /// </summary>
        public UploadFlag exphead_upload_flag { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string exphead_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled exphead_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 单据类型
        /// </summary>
        public virtual Guid exphead_bill_id { get; set; }
        [ForeignKey("exphead_bill_id")]
        public virtual BillInfo.BillInfo Bill { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? exphead_warehouse_id { get; set; }
        [ForeignKey("exphead_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        /// <summary>
        /// 上下游客户ID
        /// </summary>
        public virtual Guid? exphead_custom_id { get; set; }
        [ForeignKey("exphead_custom_id")]
        public virtual CustomInfo.CustomInfo Custom { get; set; }
        #endregion
    }
}
