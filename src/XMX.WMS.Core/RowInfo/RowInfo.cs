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
        public string row_start_layer { get; set; }
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
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled row_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 仓库ID
        /// </summary>
        public virtual Guid row_warehouse_id { get; set; }
        [ForeignKey("row_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        /// <summary>
        /// 外侧排ID
        /// </summary>
        public virtual Guid? row_out_id { get; set; }
        [ForeignKey("row_out_id")]
        public virtual RowInfo Row { get; set; }
        /// <summary>
        /// 库位对应的巷道ID
        /// </summary>
        public virtual Guid row_tunnel_id { get; set; }
        [ForeignKey("row_tunnel_id")]
        public virtual TunnelInfo.TunnelInfo Tunnel { get; set; }
        /// <summary>
        /// 容积大小
        /// </summary>
        public virtual Guid? row_size_id { get; set; }
        [ForeignKey("row_size_id")]
        public virtual SlotSize.SlotSize Size { get; set; }
        /// <summary>
        /// 区域
        /// </summary>
        public virtual Guid? row_area_id { get; set; }
        [ForeignKey("row_area_id")]
        public virtual AreaInfo.AreaInfo Area { get; set; }
        #endregion
    }
}
