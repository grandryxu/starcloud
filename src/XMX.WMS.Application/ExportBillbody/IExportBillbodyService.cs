using Abp.Application.Services;
using System;
using XMX.WMS.ExportBillbody.Dto;

namespace XMX.WMS.ExportBillbody
{
    public interface IExportBillbodyService : IAsyncCrudAppService<ExportBillbodyDto, Guid, ExportBillbodyPagedRequest, ExportBillbodyCreatedDto, ExportBillbodyUpdatedDto>
    {
    }
}
