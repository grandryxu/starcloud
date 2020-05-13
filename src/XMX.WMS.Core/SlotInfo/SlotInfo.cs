using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.SlotInfo
{
    /// <summary>
    /// 库位信息
    /// </summary>
    public class SlotInfo : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 库位名称
        /// </summary>
        public string slot_name { get; set; }
        /// <summary>
        /// 库位编码
        /// </summary>
        public string slot_code { get; set; }
        /// <summary>
        /// 层
        /// </summary>
        public int slot_layer { get; set; }
        /// <summary>
        /// 列
        /// </summary>
        public int slot_column { get; set; }
        /// <summary>
        /// 排
        /// </summary>
        public int slot_row { get; set; }
        /// <summary>
        /// 库存状态 0 空闲 1 有库存 2 入库中 3 出库中
        /// </summary>
        public SlotStock slot_stock_status { get; set; }
        /// <summary>
        /// 屏蔽状态 0 正常 1 屏蔽
        /// </summary>
        public SlotClosed slot_closed_status { get; set; }
        /// <summary>
        /// 入库锁定 0 正常 1锁定
        /// </summary>
        public SlotImp slot_imp_status { get; set; }
        /// <summary>
        /// 出库锁定 0 正常 1锁定
        /// </summary>
        public SlotExp slot_exp_status { get; set; }
        /// <summary>
        /// 是否可移动 0 不可移动 1 可移动
        /// </summary>
        public SlotMoveable slot_moveable_status { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled slot_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? slot_company_id { get; set; }
        [ForeignKey("slot_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? slot_warehouse_id { get; set; }
        [ForeignKey("slot_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        /// <summary>
        /// 库区
        /// </summary>
        public virtual Guid? slot_area_id { get; set; }
        [ForeignKey("slot_area_id")]
        public virtual AreaInfo.AreaInfo Area { get; set; }
        /// <summary>
        /// 库位排
        /// </summary>
        public virtual Guid? slot_row_id { get; set; }
        [ForeignKey("slot_row_id")]
        public virtual RowInfo.RowInfo Row { get; set; }
        /// <summary>
        /// 容积大小
        /// </summary>
        public virtual Guid? slot_size_level { get; set; }
        [ForeignKey("slot_size_level")]
        public virtual SlotSize.SlotSize Size { get; set; }
        /// <summary>
        /// 库位对应的巷道ID
        /// </summary>
        public virtual Guid? slot_tunnel_id { get; set; }
        [ForeignKey("slot_tunnel_id")]
        public virtual TunnelInfo.TunnelInfo Tunnel { get; set; }
        #endregion
    }
}
