using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc.Filters;
using Abp.Dependency;
using Abp.Authorization;
using Microsoft.AspNetCore.Mvc.Authorization;
using Abp.Runtime.Session;
using XMX.WMS.Authorization.Users;
using XMX.WMS.Authorization.Roles;
using XMX.WMS.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using Abp.Authorization.Roles;
using Abp.EntityFrameworkCore;
using Abp.AspNetCore.Mvc.Authorization;

namespace XMX.WMS.Web.Host.Startup
{
    public class WMSAuthorizationFilter : IAsyncAuthorizationFilter, ITransientDependency
    {
        private readonly IAuthorizationHelper _authorizationHelper;
        private readonly UserManager _userManager;
        private readonly RoleManager _roleManager;

        public IAbpSession AbpSession { get; set; }
        public WMSAuthorizationFilter(IAuthorizationHelper authorizationHelper,
            UserManager userManager,RoleManager roleManager)
        {
            _authorizationHelper = authorizationHelper;
            AbpSession= NullAbpSession.Instance;
            _userManager = userManager;
            _roleManager = roleManager;
        }

        public  async Task OnAuthorizationAsync(AuthorizationFilterContext context)
        {
            if (context.Filters.Any(item => item is IAllowAnonymousFilter))
            {
                return;
            }
              
           

        }

    }
}
