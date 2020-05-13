using Abp.Application.Services;
using System;
using XMX.WMS.StockTaskingDetail.Dto;

namespace XMX.WMS.StockTaskingDetail
{
    public interface IStockTaskingDetailService : IAsyncCrudAppService<StockTaskingDetailDto, Guid, StockTaskingDetailPagedRequest, StockTaskingDetailCreatedDto, StockTaskingDetailUpdatedDto>
    {
    }
}
