using Microsoft.AspNetCore.Antiforgery;
using XMX.WMS.Controllers;

namespace XMX.WMS.Web.Host.Controllers
{
    public class AntiForgeryController : WMSControllerBase
    {
        private readonly IAntiforgery _antiforgery;

        public AntiForgeryController(IAntiforgery antiforgery)
        {
            _antiforgery = antiforgery;
        }

        public void GetToken()
        {
            _antiforgery.SetCookieTokenAndHeader(HttpContext);
        }
    }
}
