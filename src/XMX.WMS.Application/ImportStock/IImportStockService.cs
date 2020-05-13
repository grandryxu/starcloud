using Abp.Application.Services;
using System;
using XMX.WMS.ImportStock.Dto;

namespace XMX.WMS.ImportStock
{
    public interface IImportStockService : IAsyncCrudAppService<ImportStockDto, Guid, ImportStockPagedRequest, ImportStockCreatedDto, ImportStockUpdatedDto>
    {
    }
}
