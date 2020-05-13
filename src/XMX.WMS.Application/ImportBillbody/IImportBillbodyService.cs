using Abp.Application.Services;
using System;
using XMX.WMS.ImportBillbody.Dto;

namespace XMX.WMS.ImportBillbody
{
    public interface IImportBillbodyService : IAsyncCrudAppService<ImportBillbodyDto, Guid, ImportBillbodyPagedRequest, ImportBillbodyCreatedDto, ImportBillbodyUpdatedDto>
    {
    }
}
