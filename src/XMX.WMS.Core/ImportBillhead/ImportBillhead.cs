using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.ImportBillhead
{
    /// <summary>
    /// 表单头
    /// </summary>
    public class ImportBillhead : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 单号
        /// </summary>
        public string imphead_code { get; set; }
        /// <summary>
        /// 外部单据ID
        /// </summary>
        public string imphead_external_id { get; set; }
        /// <summary>
        /// 外部单据号
        /// </summary>
        public string imphead_external_code { get; set; }
        /// <summary>
        /// 入库日期
        /// </summary>
        public DateTime imphead_date { get; set; }
        /// <summary>
        /// 整单执行标志(1, 未执行；2执行中；3已完成)
        /// </summary>
        public ExecuteFlag imphead_execute_flag { get; set; }
        /// <summary>
        /// 整单作废标志(1未作废；2已作废)
        /// </summary>
        public NousedFlag imphead_noused_flag { get; set; }
        /// <summary>
        /// 整单审核标志(1未审核；2已审核)
        /// </summary>
        public AuditFlag imphead_audit_flag { get; set; }
        /// <summary>
        /// 整单回传标志(1未回传；2已回传)
        /// </summary>
        public UploadFlag imphead_upload_flag { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string imphead_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled imphead_is_enable { get; set; }
        ///// <summary>
        ///// 分表后缀
        ///// </summary>
        //public string imphead_datestr { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 物料所属公司
        /// </summary>
        public virtual Guid? imphead_company_id { get; set; }
        [ForeignKey("imphead_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        /// <summary>
        /// 单据类型
        /// </summary>
        public virtual Guid? imphead_bill_id { get; set; }
        [ForeignKey("imphead_bill_id")]
        public virtual BillInfo.BillInfo BillInfo { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? imphead_warehouse_id { get; set; }
        [ForeignKey("imphead_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo WarehouseInfo { get; set; }
        /// <summary>
        /// 上下游客户ID
        /// </summary>
        public virtual Guid? imphead_custom_id { get; set; }
        [ForeignKey("imphead_custom_id")]
        public virtual CustomInfo.CustomInfo CustomInfo { get; set; }
        #endregion
    }
}
