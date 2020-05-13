using Abp.Application.Services;
using System;
using XMX.WMS.ImportBillhead.Dto;

namespace XMX.WMS.ImportBillhead
{
    public interface IImportBillheadService : IAsyncCrudAppService<ImportBillheadDto, Guid, ImportBillheadPagedRequest, ImportBillheadCreatedDto, ImportBillheadUpdatedDto>
    {
    }
}
