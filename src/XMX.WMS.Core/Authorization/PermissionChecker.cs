using Abp.Authorization;
using XMX.WMS.Authorization.Roles;
using XMX.WMS.Authorization.Users;
using System.Threading.Tasks;
using Abp;
using System.Linq;

namespace XMX.WMS.Authorization
{
    public class PermissionChecker : PermissionChecker<Role, User>
    {
        private readonly UserManager _usermanager;
        private readonly RoleManager _roleManager;
        public PermissionChecker(UserManager userManager, RoleManager roleManager)
            : base(userManager)
        {
            _usermanager = userManager;
            _roleManager = roleManager;
        }

        public override async Task<bool> IsGrantedAsync(string permissionName)
        {
            if (AbpSession == null || AbpSession.UserId == null)
                return false;
            return await IsGrantedAsync(AbpSession.UserId.Value, permissionName);
        }

        public override async Task<bool> IsGrantedAsync(long userId, string permissionName)
        {
            //如果当前用户具有web角色，则跳过所有权限检测。
            var loginuser =await  _usermanager.GetUserByIdAsync(userId);
            var r =await _usermanager.GetRolesAsync(loginuser);
            var roles = r.ToArray();
            foreach (string roleName in roles)
            {
                var rr = await _roleManager.GetRoleByNameAsync(roleName);
                if (rr.roleType == WMSRoleType.Web角色)
                    return true;

            }
            return await base.IsGrantedAsync(userId,permissionName);
        }


        public override async Task<bool> IsGrantedAsync(UserIdentifier user, string permissionName)
        {
            return await IsGrantedAsync(user.UserId, permissionName);
        }



    }
}
