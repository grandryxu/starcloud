using System.Data.Common;
using Microsoft.EntityFrameworkCore;

namespace XMX.WMS.EntityFrameworkCore
{
    public static class WMSDbContextConfigurer
    {
        public static void Configure(DbContextOptionsBuilder<WMSDbContext> builder, string connectionString)
        {
            //Oracle����
            //builder.UseOracle(connectionString);
            //sql server ���ݿ������
            builder.UseSqlServer(connectionString);

        }

        public static void Configure(DbContextOptionsBuilder<WMSDbContext> builder, DbConnection connection)
        {
            //Oracle����
            //builder.UseOracle(connection);
            //sql server ���ݿ������
            builder.UseSqlServer(connection);
        }
    }
}
