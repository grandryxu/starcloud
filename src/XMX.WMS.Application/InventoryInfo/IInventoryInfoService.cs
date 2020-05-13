using Abp.Application.Services;
using System;
using XMX.WMS.InventoryInfo.Dto;

namespace XMX.WMS.InventoryInfo
{
    public interface IInventoryInfoService : IAsyncCrudAppService<InventoryInfoDto, Guid, InventoryInfoPagedRequest, InventoryInfoCreatedDto, InventoryInfoUpdatedDto>
    {
    }
}
