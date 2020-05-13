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
        /// ����
        /// </summary>
        public Guid? DepartmentId { get; set; }

        /// <summary>
        /// ��ע
        /// </summary>
        public string Remark { get; set; }

        /// <summary>
        /// �ϼ�����
        /// </summary>
        [ForeignKey("CompanyId")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }

        /// <summary>
        /// �ϼ�����
        /// </summary>
        [ForeignKey("DepartmentId")]
        public virtual DepartmentInfo.DepartmentInfo Department { get; set; }

        // <summary>
        /// ��˾ID
        /// </summary>
        public virtual Guid? CompanyId { get; set; }
    }
}
