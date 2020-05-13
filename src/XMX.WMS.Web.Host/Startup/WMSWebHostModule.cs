using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Abp.Modules;
using Abp.Reflection.Extensions;
using XMX.WMS.Configuration;

namespace XMX.WMS.Web.Host.Startup
{
    [DependsOn(
       typeof(WMSWebCoreModule))]
    public class WMSWebHostModule: AbpModule
    {
        private readonly IHostingEnvironment _env;
        private readonly IConfigurationRoot _appConfiguration;

        public WMSWebHostModule(IHostingEnvironment env)
        {
            _env = env;
            _appConfiguration = env.GetAppConfiguration();
        }

        public override void Initialize()
        {
            IocManager.RegisterAssemblyByConvention(typeof(WMSWebHostModule).GetAssembly());
        }
    }
}
