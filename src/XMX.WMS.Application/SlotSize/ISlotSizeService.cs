using Abp.Application.Services;
using System;
using XMX.WMS.SlotSize.Dto;

namespace XMX.WMS.SlotSize
{
    public interface ISlotSizeService : IAsyncCrudAppService<SlotSizeDto, Guid, SlotSizePagedRequest, SlotSizeCreatedDto, SlotSizeUpdatedDto>
    {
    }
}
