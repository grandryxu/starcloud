using Abp.Application.Services;
using System;
using XMX.WMS.ExportOrder.Dto;

namespace XMX.WMS.ExportOrder
{
    public interface IExportOrderService : IAsyncCrudAppService<ExportOrderDto, Guid, ExportOrderPagedRequest, ExportOrderCreatedDto, ExportOrderUpdatedDto>
    {
    }
}
