using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.PortInfo
{
    /// <summary>
    /// 入出口信息
    /// </summary>
    public class PortInfo : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 编码
        /// </summary>
        public string port_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string port_name { get; set; }
        /// <summary>
        /// 类型(1入；2出；3双向)
        /// </summary>
        public PortType port_type { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled port_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? port_warehouse_id { get; set; }
        [ForeignKey("port_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        #endregion
    }
}
