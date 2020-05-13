using Abp.Application.Services;
using System;
using XMX.WMS.ExportBillSyncLog.Dto;
using XMX.WMS.ImportApplyLog.Dto;
using XMX.WMS.ImportBillbody.Dto;

namespace XMX.WMS.ExportBillSyncLog
{
    public interface IExportBillSyncLogService : IAsyncCrudAppService<ExportBillSyncLogDto, Guid, ExportBillSyncLogPagedRequest, ExportBillSyncLogCreatedDto, ExportBillSyncLogUpdatedDto>
    {

    }
}
