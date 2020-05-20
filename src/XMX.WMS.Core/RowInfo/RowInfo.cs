using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.RowInfo
{
    /// <summary>
    /// 库位排设置
    /// </summary>
    public class RowInfo : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 类型 1 库位 2 巷道
        /// </summary>
        public RowType row_type { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string row_name { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string row_remark { get; set; }
        /// <summary>
        /// 排数
        /// </summary>
        public int row_no { get; set; }
        /// <summary>
        /// 内外侧标志 1 内侧 2 外侧 3单排
        /// </summary>
        public InOutType row_inout_type { get; set; }
        /// <summary>
        /// 排序
        /// </summary>
        public int row_order { get; set; }
        /// <summary>
        /// 起始层
        /// </summary>
        public int row_start_layer { get; set; }
        /// <summary>
        /// 终止层
        /// </summary>
        public int row_end_layer { get; set; }
        /// <summary>
        /// 起始列
        /// </summary>
        public int row_start_column { get; set; }
        /// <summary>
        /// 终止列
        /// </summary>
        public int row_end_column { get; set; }
        /// <summary>
        /// 是否可移动 0 不可移动 1 可移动
        /// </summary>
        public SlotMoveable row_movealbe_status { get; set; }
        /// <summary>
        /// 状态 1 未初始化 2 已初始化
        /// </summary>
        public RowStatus row_status { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? row_company_id { get; set; }
        [ForeignKey("row_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        /// <summary>
        /// 仓库ID
        /// </summary>
        public virtual Guid? row_warehouse_id { get; set; }
        [ForeignKey("row_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        /// <summary>
        /// 外侧排ID
        /// </summary>
        public virtual Guid? row_out_id { get; set; }
        [ForeignKey("row_out_id")]
        public virtual RowInfo Row { get; set; }
        #endregion
    }
}
