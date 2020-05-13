using Abp.Application.Services;
using System;
using XMX.WMS.SlotInfo.Dto;

namespace XMX.WMS.SlotInfo
{
    public interface ISlotInfoService : IAsyncCrudAppService<SlotInfoDto, Guid, SlotInfoPagedRequest, SlotInfoCreatedDto, SlotInfoUpdatedDto>
    {
    }
}
