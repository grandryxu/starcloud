using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.QualityCheck
{
    ///<summary>
    /// 版 本：
    /// 创建人：lunan
    /// 日 期：2020-05-18 14:23:53
    /// 描 述：抽检
    ///</summary>
    public class QualityCheck : FullAuditedEntity<Guid>
    {
        #region  属性
        /// <summary>
        /// 抽检单据
        /// </summary>
        public string check_code { get; set; }
        /// <summary>
        /// 单据类型
        /// </summary>
        public CheckType check_type { get; set; }
        /// <summary>
        /// 批次
        /// </summary>
        public string check_batch_no { get; set; }
        /// <summary>
        /// 单据号
        /// </summary>
        public string check_bill_bar { get; set; }
        /// <summary>
        /// 检测日期
        /// </summary>
        public DateTime check_time { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string check_remark { get; set; }
        /// <summary>
        /// 抽检量
        /// </summary>
        public decimal check_num { get; set; }
        /// <summary>
        /// 是否生成出库单 0 未生成 ；1 已生成
        /// </summary>
        public CheckBillStatus check_bill_status { get; set; }
        /// <summary>
        /// 是否已经检测放行 0 未放行；1 已放行
        /// </summary>
        public CheckReleasedStatus check_released_status { get; set; }
        /// <summary>
        /// 生成出库单的单号
        /// </summary>
        public string check_export_bill { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 公司
        /// </summary>
        public virtual Guid? check_company_id { get; set; }
        [ForeignKey("check_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public virtual Guid? check_warehouse_id { get; set; }
        [ForeignKey("check_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        /// <summary>
        /// 物料
        /// </summary>
        public virtual Guid? check_goods_id { get; set; }
        [ForeignKey("check_goods_id")]
        public virtual GoodsInfo.GoodsInfo Goods { get; set; }
        /// <summary>
        /// 原质量状态
        /// </summary>
        public virtual Guid? check_origin_quality { get; set; }
        [ForeignKey("check_origin_quality")]
        public virtual QualityInfo.QualityInfo Quality1 { get; set; }
        /// <summary>
        /// 检测后质量状态
        /// </summary>
        public virtual Guid? check_checked_quality { get; set; }
        [ForeignKey("check_checked_quality")]
        public virtual QualityInfo.QualityInfo Quality2 { get; set; }
        #endregion
    }
}
