using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.PortInfo.Dto
{
    #region 查询参数传入dto
    public class PortInfoPagedRequest : PagedResultRequestDto
    {
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
        public PortType? port_type { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(PortInfo))]
    public class PortInfoCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 编码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string port_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string port_name { get; set; }
        /// <summary>
        /// 类型(1入；2出；3双向)
        /// </summary>
        [Required]
        public PortType port_type { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        [Required] 
        public WMSIsEnabled port_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? port_company_id { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? port_warehouse_id { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(PortInfo))]
    public class PortInfoUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 编码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string port_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string port_name { get; set; }
        /// <summary>
        /// 类型(1入；2出；3双向)
        /// </summary>
        [Required]
        public PortType port_type { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        [Required] 
        public WMSIsEnabled port_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? port_company_id { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? port_warehouse_id { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(PortInfo))]
    public class PortInfoDto : EntityDto<Guid>
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
        /// <summary>
        ///创建时间
        /// </summary>
        public DateTime CreationTime { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? port_company_id { get; set; }
        [ForeignKey("port_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? port_warehouse_id { get; set; }
        [ForeignKey("port_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        #endregion
    }
    #endregion
}
