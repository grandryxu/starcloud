using Abp.AutoMapper;
using Abp.Modules;
using Abp.Reflection.Extensions;
using XMX.WMS.Authorization;

namespace XMX.WMS
{
    [DependsOn(
        typeof(WMSCoreModule), 
        typeof(AbpAutoMapperModule))]
    public class WMSApplicationModule : AbpModule
    {
        public override void PreInitialize()
        {
            Configuration.Authorization.Providers.Add<WMSAuthorizationProvider>();
            //注入仓库设备管理权限标识符集
            Configuration.Authorization.Providers.Add<WarehouseEquipmentAuthorizationProvider>();
            //注入基础信息管理权限标识符集
            Configuration.Authorization.Providers.Add<BasicInfoManageAuthorizationProvider>();
            //注入系统管理权限标识符集
            Configuration.Authorization.Providers.Add<SysManageAuthorizationProvider>();
            //仓库管理权限标识符集(包含出入库)
            Configuration.Authorization.Providers.Add<WarehouseManageAuthorizationProvider>();
            //业务管理权限标识符集(任务管理中心、业务管理中心、日志管理中心、质量管理等)
            Configuration.Authorization.Providers.Add<BusinessManageAuthorizationProvider>();
        }

        public override void Initialize()
        {
            var thisAssembly = typeof(WMSApplicationModule).GetAssembly();

            IocManager.RegisterAssemblyByConvention(thisAssembly);

            Configuration.Modules.AbpAutoMapper().Configurators.Add(
                // Scan the assembly for classes which inherit from AutoMapper.Profile
                cfg => cfg.AddMaps(thisAssembly)
            );
        }
    }
}
