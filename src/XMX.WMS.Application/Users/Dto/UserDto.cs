using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Abp.Application.Services.Dto;
using Abp.Authorization.Users;
using Abp.AutoMapper;
using XMX.WMS.Authorization.Users;

namespace XMX.WMS.Users.Dto
{
    [AutoMapFrom(typeof(User))]
    public class UserDto : EntityDto<long>
    {
        [Required]
        [StringLength(AbpUserBase.MaxUserNameLength)]
        public string UserName { get; set; }

        [Required]
        [StringLength(AbpUserBase.MaxNameLength)]
        public string Name { get; set; }

        [Required]
        [StringLength(AbpUserBase.MaxSurnameLength)]
        public string Surname { get; set; }

        [EmailAddress]
        [StringLength(AbpUserBase.MaxEmailAddressLength)]
        public string EmailAddress { get; set; }

        public bool IsActive { get; set; }

        public string FullName { get; set; }

        public DateTime? LastLoginTime { get; set; }

        public DateTime CreationTime { get; set; }

        public string[] RoleNames { get; set; }



        /// <summary>
        /// 部门
        /// </summary>
        public Guid? DepartmentId { get; set; }

        /// <summary>
        /// 备注
        /// </summary>
        public string Remark { get; set; }

        /// <summary>
        /// 上级部门
        /// </summary>
        [ForeignKey("CompanyId")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }

        /// <summary>
        /// 上级部门
        /// </summary>
        [ForeignKey("DepartmentId")]
        public virtual DepartmentInfo.DepartmentInfo Department { get; set; }

        // <summary>
        /// 公司ID
        /// </summary>
        public virtual Guid? CompanyId { get; set; }
    }
}
