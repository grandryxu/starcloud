using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using Abp.Application.Services.Dto;
using Abp.Authorization.Roles;
using Abp.AutoMapper;
using XMX.WMS.Authorization.Roles;

namespace XMX.WMS.Roles.Dto
{
    public class RoleDto : EntityDto<int>
    {
        [Required]
        [StringLength(AbpRoleBase.MaxNameLength)]
        public string Name { get; set; }
        
        [Required]
        [StringLength(AbpRoleBase.MaxDisplayNameLength)]
        public string DisplayName { get; set; }

        public string NormalizedName { get; set; }
        
        [StringLength(Role.MaxDescriptionLength)]
        public string Description { get; set; }

        public List<string> GrantedPermissions { get; set; }


        /// <summary>
        /// 创建时间
        /// </summary>

        public DateTime CreationTime { get; set; }


        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled IsEnable { get; set; }

        /// <summary>
        /// 角色类型
        /// </summary>
        public WMSRoleType roleType { get; set; }
    }
}