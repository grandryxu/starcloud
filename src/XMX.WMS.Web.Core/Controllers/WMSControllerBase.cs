using Abp.AspNetCore.Mvc.Controllers;
using Abp.IdentityFramework;
using Microsoft.AspNetCore.Identity;

namespace XMX.WMS.Controllers
{
    public abstract class WMSControllerBase: AbpController
    {
        protected WMSControllerBase()
        {
            LocalizationSourceName = WMSConsts.LocalizationSourceName;
        }

        protected void CheckErrors(IdentityResult identityResult)
        {
            identityResult.CheckErrors(LocalizationManager);
        }
    }
}
