using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XMX.WMS.ExportBillSyncLog.Dto;

namespace XMX.WMS.ExportBillSyncLog
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-05-12 16:49:59
    /// 描 述：
    ///</summary>
    public class ExportBillSyncLogService : AsyncCrudAppService<ExportBillSyncLog, ExportBillSyncLogDto, Guid, ExportBillSyncLogPagedRequest, ExportBillSyncLogCreatedDto, ExportBillSyncLogUpdatedDto>, IExportBillSyncLogService
    {
        public ExportBillSyncLogService(IRepository<ExportBillSyncLog, Guid> repository) : base(repository)
        {

        }

        protected override IQueryable<ExportBillSyncLog> CreateFilteredQuery(ExportBillSyncLogPagedRequest input)
        {
            return Repository.GetAll();
        }
    }
}
