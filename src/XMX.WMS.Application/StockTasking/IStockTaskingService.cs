using Abp.Application.Services;
using System;
using XMX.WMS.StockTasking.Dto;

namespace XMX.WMS.StockTasking
{
    public interface IStockTaskingService : IAsyncCrudAppService<StockTaskingDto, Guid, StockTaskingPagedRequest, StockTaskingCreatedDto, StockTaskingUpdatedDto>
    {
    }
}
