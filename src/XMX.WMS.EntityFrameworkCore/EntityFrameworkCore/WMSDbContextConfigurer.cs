using System.Data.Common;
using Microsoft.EntityFrameworkCore;

namespace XMX.WMS.EntityFrameworkCore
{
    public static class WMSDbContextConfigurer
    {
        public static void Configure(DbContextOptionsBuilder<WMSDbContext> builder, string connectionString)
        {
            //Oracle配置
            //builder.UseOracle(connectionString);
            //sql server 数据库的配置
            builder.UseSqlServer(connectionString);

        }

        public static void Configure(DbContextOptionsBuilder<WMSDbContext> builder, DbConnection connection)
        {
            //Oracle配置
            //builder.UseOracle(connection);
            //sql server 数据库的配置
            builder.UseSqlServer(connection);
        }
    }
}
