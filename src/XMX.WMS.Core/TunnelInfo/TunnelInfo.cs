using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.TunnelInfo
{
    /// <summary>
    /// 巷道表
    /// </summary>
    public class TunnelInfo : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 巷道名
        /// </summary>
        public string tunnel_name { get; set; }
        /// <summary>
        /// 巷道入库锁定 1 锁定 2 未锁定
        /// </summary>
        public LockType tunnel_in_state { get; set; }
        /// <summary>
        /// 巷道出库锁定 1 锁定 2 未锁定
        /// </summary>
        public LockType tunnel_out_state { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled tunnel_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 库位排
        /// </summary>
        public virtual Guid? slot_row_id { get; set; }
        [ForeignKey("slot_row_id")]
        public virtual RowInfo.RowInfo Row { get; set; }
        #endregion
    }
}
