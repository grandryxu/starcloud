using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.Extensions.Configuration;
using XMX.WMS.Configuration;
using XMX.WMS.Web;

namespace XMX.WMS.EntityFrameworkCore.Dynamic
{
    /// <summary>
    /// 动态分表上下文
    /// </summary>
    public class DynamicDbContext : DbContext
    {
        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder) {
            var configuration = AppConfigurations.Get(WebContentDirectoryFinder.CalculateContentRootFolder());
            optionsBuilder.UseSqlServer(configuration.GetConnectionString(WMSConsts.ConnectionStringName));
            optionsBuilder.ReplaceService<IModelCacheKeyFactory, DynamicModelCacheKeyFactory>();
        }

        /// <summary>
        /// 动态表名
        /// </summary>
        public string TableName { get; set; }

        /// <summary>
        /// 获取指定分表上下文实例
        /// </summary>
        /// <param name="tableName"></param>
        /// <returns></returns>
        public static DynamicDbContext GetInstance(string tableName)
        {
            DynamicDbContext context = new DynamicDbContext
            {
                TableName = tableName
            };
            return context;
        }

        /// <summary>
        /// 动态注册
        /// </summary>
        /// <param name="modelBuilder"></param>
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            if (TableName.StartsWith("Fenbiao")) {
                modelBuilder.Entity<Fenbiao.Fenbiao>(b =>
                {
                    //将splittable动态切换到指定规则的分表中
                    //b.ToTable($"{TableName}{DateTime.Now.ToString("yyyy-MM")}");
                    b.ToTable(TableName);
                    b.HasKey(p => p.Id);
                });
            }else if (TableName.StartsWith("WMSOptLogInfo"))
            {
                modelBuilder.Entity<WMSOptLogInfo.WMSOptLogInfo>(b =>
                {   
                    b.ToTable(TableName);
                    b.HasKey(p => p.Id);
                });
            }
        }

        /// <summary>
        /// 分表测试表
        /// </summary>
        public virtual DbSet<Fenbiao.Fenbiao> Fenbiao { get; set; }
        /// <summary>
        /// 操作日志分表
        /// </summary>
        public virtual DbSet<WMSOptLogInfo.WMSOptLogInfo> WMSOptLogInfo { get; set; }
    }
}
