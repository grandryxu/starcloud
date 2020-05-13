using System.Collections.Generic;

namespace XMX.WMS.Roles.Dto
{
    public class UpdateRolePermissionsInput
    {
        public int RoleId { get; set; }
        public List<string> GrantedPermissionNames { get; set; }
    }
}
