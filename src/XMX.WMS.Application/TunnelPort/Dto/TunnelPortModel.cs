using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.TunnelPort.Dto
{
    #region 查询参数传入dto
    public class TunnelPortPagedRequest : PagedResultRequestDto
    {
        
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(TunnelPort))]
    public class TunnelPortCreatedDto : BaseCreateDto
    {
        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? tunnelPort_company_id { get; set; }
        /// <summary>
        /// 巷道
        /// </summary>
        public virtual Guid? tunnelPort_tunnel_id { get; set; }
        /// <summary>
        /// 出入口
        /// </summary>
        public virtual Guid? tunnelPort_port_id { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(TunnelPort))]
    public class TunnelPortUpdatedDto : BaseUpdateDto
    {
        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? tunnelPort_company_id { get; set; }
        /// <summary>
        /// 巷道
        /// </summary>
        public virtual Guid? tunnelPort_tunnel_id { get; set; }
        /// <summary>
        /// 出入口
        /// </summary>
        public virtual Guid? tunnelPort_port_id { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(TunnelPort))]
    public class TunnelPortDto : EntityDto<Guid>
    {
        /// <summary>
        ///创建时间
        /// </summary>
        public DateTime CreationTime { get; set; }

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
    #endregion
}
