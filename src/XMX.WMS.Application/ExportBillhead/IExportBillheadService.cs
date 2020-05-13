using Abp.Application.Services;
using System;
using XMX.WMS.ExportBillhead.Dto;

namespace XMX.WMS.ExportBillhead
{
    public interface IExportBillheadService : IAsyncCrudAppService<ExportBillheadDto, Guid, ExportBillheadPagedRequest, ExportBillheadCreatedDto, ExportBillheadUpdatedDto>
    {
    }
}
