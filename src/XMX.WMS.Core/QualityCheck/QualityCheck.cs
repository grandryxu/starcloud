using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.QualityCheck
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-03-20 13:58:12
    /// 描 述：
    ///</summary>
    public class QualityCheck : FullAuditedEntity<Guid>
    {
        #region  属性
        /// <summary>
        /// 抽检单据
        /// </summary>
        public string check_bill { get; set; }
        /// <summary>
        /// 单据类型
        /// </summary>
        public string bill_type { get; set; }
        /// <summary>
        /// 物料编码
        /// </summary>
        public string check_goods_code { get; set; }
        /// <summary>
        /// 物料名称
        /// </summary>
        public string check_goods_name { get; set; }
        /// <summary>
        /// 批次
        /// </summary>
        public string check_batch_no { get; set; }
        /// <summary>
        /// 原质量状态
        /// </summary>
        public string origin_quality_status { get; set; }
        /// <summary>
        /// 检测后质量状态
        /// </summary>
        public string checked_quality_status { get; set; }
        /// <summary>
        /// 检测后质量状态id
        /// </summary>
        public Guid checked_quality_status_id { get; set; }
        /// <summary>
        /// 检测日期
        /// </summary>
        public DateTime check_time { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string remark { get; set; }
        /// <summary>
        /// 入库量
        /// </summary>
        public decimal stock_num { get; set; }
        /// <summary>
        /// 抽检量
        /// </summary>
        public decimal check_num { get; set; }
        /// <summary>
        /// 是否生成出库单 0 未生成 ；1 生成
        /// </summary>
        public int exist_out_bill { get; set; }
        /// <summary>
        /// 是否已经检测放行 0 未放行；1 已经放行
        /// </summary>
        public int check_released { get; set; }
        /// <summary>
        /// 生成出库单的单号
        /// </summary>
        public string quality_check_export_code { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 库存信息
        /// </summary>
        public virtual Guid? check_inventory_id { get; set; }
        [ForeignKey("check_inventory_id")]
        public virtual InventoryInfo.InventoryInfo check_inventory_info { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public virtual Guid? inventory_warehouse_id { get; set; }
        [ForeignKey("inventory_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        #endregion
    }
}
