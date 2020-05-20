using Abp.Dependency;
using Abp.Runtime.Session;
using Abp.UI;
using System;
using System.Linq;
using System.Security.Claims;

namespace XMX.WMS.Base.Session
{
    public static class WMSSession
    {
        /// <summary>
        /// 扩展获取公司的方法
        /// </summary>
        /// <param name="session"></param>
        /// <returns></returns>
        public static Guid GetCompanyId(this IAbpSession session)
        {
            try
            {
                return Guid.Parse(GetClaimValue(WMSAbpClaimTypes.CompanyId));
            }
            catch (Exception)
            {
                throw new UserFriendlyException("获取公司失败！");
            }
        }

        private static string GetClaimValue(string claimType)
        {
            // 使用IOC容器获取当前用户身份认证信息
            var PrincipalAccessor = IocManager.Instance.Resolve<IPrincipalAccessor>(); 
            var claimsPrincipal = PrincipalAccessor.Principal;
            //var claimsPrincipal = DefaultPrincipalAccessor.Instance.Principal;
            var claim = claimsPrincipal?.Claims.FirstOrDefault(c => c.Type == claimType);
            if (string.IsNullOrEmpty(claim?.Value))
                return null;
            return claim.Value;
        }
    }
}
