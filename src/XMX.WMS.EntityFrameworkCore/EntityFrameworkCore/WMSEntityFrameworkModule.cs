using Abp.EntityFrameworkCore.Configuration;
using Abp.Modules;
using Abp.Reflection.Extensions;
using Abp.Zero.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Logging.Console;
using XMX.WMS.EntityFrameworkCore.Seed;

namespace XMX.WMS.EntityFrameworkCore
{
    [DependsOn(
        typeof(WMSCoreModule), 
        typeof(AbpZeroCoreEntityFrameworkCoreModule))]
    public class WMSEntityFrameworkModule : AbpModule
    {
        /* Used it tests to skip dbcontext registration, in order to use in-memory database of EF Core */
        public bool SkipDbContextRegistration { get; set; }

        public bool SkipDbSeed { get; set; }

        //using Microsoft.Extensions.Logging 2.0
        //using Microsoft.Extensions.Logging.Console 2.0
        public static readonly LoggerFactory MyLoggerFactory 
                                            = new LoggerFactory(new[] {
                                                                new ConsoleLoggerProvider((category, level)
                                                                    => category == DbLoggerCategory.Database.Command.Name
                                                                && level == LogLevel.Information, true)
                                             });

        public override void PreInitialize()
        {
            if (!SkipDbContextRegistration)
            {
                Configuration.Modules.AbpEfCore().AddDbContext<WMSDbContext>(options =>
                {
                    if (options.ExistingConnection != null)
                        WMSDbContextConfigurer.Configure(options.DbContextOptions, options.ExistingConnection);
                    else
                        WMSDbContextConfigurer.Configure(options.DbContextOptions, options.ConnectionString);
                    //SQL日志
                    options.DbContextOptions.UseLoggerFactory(MyLoggerFactory);
                    options.DbContextOptions.EnableSensitiveDataLogging(true);       //logging 不加密 development使用 !
                });
            }
        }

        public override void Initialize()
        {
            IocManager.RegisterAssemblyByConvention(typeof(WMSEntityFrameworkModule).GetAssembly());
        }

        public override void PostInitialize()
        {
            if (!SkipDbSeed)
            {
                SeedHelper.SeedHostDb(IocManager);
            }
        }
    }
}
