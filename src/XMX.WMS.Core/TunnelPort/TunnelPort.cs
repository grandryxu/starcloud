using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.TunnelPort
{
    /// <summary>
    /// 货物
    /// </summary>
    public class TunnelPort : FullAuditedEntity<Guid>
    {
        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? tunnelPort_company_id { get; set; }
        [ForeignKey("tunnelPort_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        /// <summary>
        /// 巷道
        /// </summary>
        public virtual Guid? tunnelPort_tunnel_id { get; set; }
        [ForeignKey("tunnelPort_tunnel_id")]
        public virtual TunnelInfo.TunnelInfo Tunnel { get; set; }
        /// <summary>
        /// 出入口
        /// </summary>
        public virtual Guid? tunnelPort_port_id { get; set; }
        [ForeignKey("tunnelPort_port_id")]
        public virtual PortInfo.PortInfo Port { get; set; }
        #endregion
    }
}
