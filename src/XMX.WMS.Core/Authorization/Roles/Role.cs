using System.ComponentModel.DataAnnotations;
using Abp.Authorization.Roles;
using XMX.WMS.Authorization.Users;

namespace XMX.WMS.Authorization.Roles
{
    public class Role : AbpRole<User>
    {
        public const int MaxDescriptionLength = 5000;

        public Role()
        {
        }

        public Role(int? tenantId, string displayName)
            : base(tenantId, displayName)
        {
        }

        public Role(int? tenantId, string name, string displayName)
            : base(tenantId, name, displayName)
        {
        }

        [StringLength(MaxDescriptionLength)]
        public string Description {get; set;}
        /// <summary>
        /// 所属公司
        /// </summary>
        public CompanyInfo.CompanyInfo Company { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string Remark { get; set; }
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
