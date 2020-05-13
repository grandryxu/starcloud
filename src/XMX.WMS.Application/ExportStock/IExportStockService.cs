using Abp.Application.Services;
using System;
using XMX.WMS.ExportStock.Dto;

namespace XMX.WMS.ExportStock
{
    public interface IExportStockService : IAsyncCrudAppService<ExportStockDto, Guid, ExportStockPagedRequest, ExportStockCreatedDto, ExportStockUpdatedDto>
    {
    }
}
