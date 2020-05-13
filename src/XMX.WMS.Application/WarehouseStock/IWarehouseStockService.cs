using Abp.Application.Services;
using System;
using XMX.WMS.WarehouseStock.Dto;

namespace XMX.WMS.WarehouseStock
{
    public interface IWarehouseStockService : IAsyncCrudAppService<WarehouseStockDto, Guid, WarehouseStockPagedRequest, WarehouseStockCreatedDto, WarehouseStockUpdatedDto>
    {
    }
}
