using Abp.Application.Services;
using System;
using XMX.WMS.Equipment.Dto;

namespace XMX.WMS.Equipment
{
    public interface IPalletizingInfoService : IAsyncCrudAppService<PalletizingInfoDto, Guid, PalletizingInfoPagedRequest, PalletizingInfoCreatedDto, PalletizingInfoUpdatedDto>
    {
    }
}
