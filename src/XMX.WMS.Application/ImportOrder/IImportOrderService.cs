using Abp.Application.Services;
using System;
using XMX.WMS.ImportOrder.Dto;

namespace XMX.WMS.ImportOrder
{
    public interface IImportOrderService : IAsyncCrudAppService<ImportOrderDto, Guid, ImportOrderPagedRequest, ImportOrderCreatedDto, ImportOrderUpdatedDto>
    {
    }
}
