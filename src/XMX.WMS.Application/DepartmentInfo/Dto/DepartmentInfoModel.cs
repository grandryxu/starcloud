using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.DepartmentInfo.Dto
{
    #region 查询参数传入dto

    public class DepartmentInfoPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 部门编号
        /// </summary>
        public string DepartNo { get; set; }
        /// <summary>
        /// 部门名
        /// </summary>
        public string Name { get; set; }

        /// <summary>
        /// 负责人
        /// </summary>
        public string ManagerName { get; set; }

        /// <summary>
        /// 公司ID
        /// </summary>
        public Guid? CompanyId { get; set; }
    }
    #endregion

    #region CreateDto
    [AutoMapTo(typeof(DepartmentInfo))]
    public class DepartmentCreatedModel : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 部门编号
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string DepartNo { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string Name { get; set; }
        /// <summary>
        /// 负责人
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string ManagerName { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string Remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        [Required]
        public WMSIsEnabled IsEnable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 公司ID
        /// </summary>
        public virtual Guid CompanyId { get; set; }
        /// <summary>
        /// 上级部门ID
        /// </summary>
        public virtual Guid? DepartmentId { get; set; }
        #endregion
    }
    #endregion

    #region UpdateDto
    [AutoMapTo(typeof(DepartmentInfo))]
    public class DepartmentUpdatedModel : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 部门编号
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string DepartNo { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string Name { get; set; }
        /// <summary>
        /// 负责人
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string ManagerName { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string Remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        [Required]
        public WMSIsEnabled IsEnable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 公司ID
        /// </summary>
        public virtual Guid CompanyId { get; set; }
        /// <summary>
        /// 上级部门ID
        /// </summary>
        public virtual Guid? DepartmentId { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(DepartmentInfo))]
    public class DepartmentInfoDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 部门编号
        /// </summary>
        public string DepartNo { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string Name { get; set; }
        /// <summary>
        /// 负责人
        /// </summary>
        public string ManagerName { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string Remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled IsEnable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 公司ID
        /// </summary>
        public virtual Guid CompanyId { get; set; }
        /// <summary>
        /// 公司
        /// </summary>
        [ForeignKey("CompanyId")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        /// <summary>
        /// 上级部门ID
        /// </summary>
        public virtual Guid? DepartmentId { get; set; }
        /// <summary>
        /// 上级部门
        /// </summary>
        [ForeignKey("DepartmentId")]
        public virtual DepartmentInfo Department { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(DepartmentInfo))]
    public class DepartmentParentInfoDto : EntityDto<Guid>
    {
        /// <summary>
        /// 名称
        /// </summary>
        public string Name { get; set; }
    }
    #endregion

    #region 父级查询参数
    public class ParentDepartmentSearchParam
    {
        public Guid? CompanyId { get; set; }
    }
    #endregion
}
