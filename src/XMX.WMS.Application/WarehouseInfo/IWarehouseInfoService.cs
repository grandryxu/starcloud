using Abp.Application.Services;
using System;
using XMX.WMS.WarehouseInfo.Dto;

namespace XMX.WMS.WarehouseInfo
{
    public interface IWarehouseInfoService : IAsyncCrudAppService<WarehouseInfoDto, Guid, WarehouseInfoPagedRequest, WarehouseInfoCreatedDto, WarehouseInfoUpdatedDto>
    {
    }
}
