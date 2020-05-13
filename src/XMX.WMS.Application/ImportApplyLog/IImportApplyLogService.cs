using Abp.Application.Services;
using System;
using XMX.WMS.ImportApplyLog.Dto;
using XMX.WMS.ImportBillbody.Dto;

namespace XMX.WMS.ImportApplyLog
{
    public interface IImportApplyLogService : IAsyncCrudAppService<ImportApplyLogDto, Guid, ImportApplyLogPagedRequest, ImportApplyLogCreatedDto, ImportApplyLogUpdatedDto>
    {

    }
}
