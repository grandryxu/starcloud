using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.ImportBillbody.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using System.Collections.Generic;
using System.Threading.Tasks;
using Abp.Application.Services.Dto;
using Abp.Authorization;
using XMX.WMS.Authorization;
using XMX.WMS.Authorization.Users;
using Abp.UI;
using XMX.WMS.ImportApplyLog.Dto;

namespace XMX.WMS.ImportApplyLog
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-05-12 16:40:39
    /// 描 述：
    ///</summary>
   public class ImportApplyLogService : AsyncCrudAppService<ImportApplyLog, ImportApplyLogDto, Guid, ImportApplyLogPagedRequest, ImportApplyLogCreatedDto, ImportApplyLogUpdatedDto>, IImportApplyLogService
    {
        public ImportApplyLogService(IRepository<ImportApplyLog, Guid> repository) : base(repository)
        {
            
        }

        protected override IQueryable<ImportApplyLog> CreateFilteredQuery(ImportApplyLogPagedRequest input)
        {
            string[] dt = input.DateRange?.Split("/");
            return Repository.GetAllIncluding().
                WhereIf(dt?.Length == 2, x => DateTime.Compare(Convert.ToDateTime(x.CreationTime.ToString("yyyy-MM-dd")), Convert.ToDateTime(dt[0])) >= 0
                 && DateTime.Compare(Convert.ToDateTime(x.CreationTime.ToString("yyyy-MM-dd")), Convert.ToDateTime(dt[1])) <= 0);
        }
    }
}
