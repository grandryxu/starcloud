using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using Abp.Authorization.Users;
using Abp.Extensions;

namespace XMX.WMS.Authorization.Users
{
    public class User : AbpUser<User>
    {


        public const string DefaultPassword = "new_wms";

        public static string CreateRandomPassword()
        {
            return Guid.NewGuid().ToString("N").Truncate(16);
        }

        public static User CreateTenantAdminUser(int tenantId, string emailAddress)
        {
            var user = new User
            {
                TenantId = tenantId,
                UserName = AdminUserName,
                Name = AdminUserName,
                Surname = AdminUserName,
                EmailAddress = emailAddress,
                Roles = new List<UserRole>()
            };

            user.SetNormalizedNames();
            return user;
        }

        /// <summary>
        /// 登录id
        /// </summary>
        public string LoginId { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string Remark { get; set; }

        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled IsEnable { get; set; }

        #region 关联


        // <summary>
        /// 公司ID
        /// </summary>
        public virtual Guid CompanyId { get; set; }

        /// <summary>
        /// 上级部门
        /// </summary>
        [ForeignKey("CompanyId")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }




        // <summary>
        /// 部门ID
        /// </summary>
        public virtual Guid? DepartmentId { get; set; }

        /// <summary>
        /// 上级部门
        /// </summary>
        [ForeignKey("DepartmentId")]
        public virtual DepartmentInfo.DepartmentInfo Department { get; set; }


        #endregion
    }
}
