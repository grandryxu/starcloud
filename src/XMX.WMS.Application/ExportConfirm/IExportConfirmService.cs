using Abp.Application.Services;
using System;
using XMX.WMS.ExportConfirm.Dto;

namespace XMX.WMS.ExportConfirm
{
    public interface IExportConfirmService : IAsyncCrudAppService<ExportConfirmDto, Guid, ExportConfirmPagedRequest, ExportConfirmCreatedDto, ExportConfirmUpdatedDto>
    {
    }
}
