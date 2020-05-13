using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.Equipment
{
    /// <summary>
    /// RGV设备
    /// </summary>
    public class RGVInfo : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 编码
        /// </summary>
        public string rgv_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string rgv_name { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string rgv_remark { get; set; }
        /// <summary>
        /// 在线状态
        /// </summary>
        public OnlineState online_state { get; set; }
        /// <summary>
        /// 报警状态
        /// </summary>
        public AlarmState alarm_state { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所在仓库
        /// </summary>
        public virtual Guid rgv_warehouse_id { get; set; }
        [ForeignKey("rgv_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        #endregion
    }
}
