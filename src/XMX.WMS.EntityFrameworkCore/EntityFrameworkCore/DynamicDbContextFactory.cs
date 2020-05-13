using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;

namespace XMX.WMS.EntityFrameworkCore.Dynamic
{
    public class DynamicModelCacheKeyFactory: IModelCacheKeyFactory
    {
        public object Create(DbContext context)
           => context is DynamicDbContext dynamicContext
               ? (context.GetType(), dynamicContext.TableName)
               : (object)context.GetType();
    }
}
