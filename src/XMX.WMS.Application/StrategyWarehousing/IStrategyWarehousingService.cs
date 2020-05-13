using Abp.Application.Services;
using System;
using XMX.WMS.StrategyWarehousing.Dto;

namespace XMX.WMS.StrategyWarehousing
{
    public interface IStrategyWarehousingService : IAsyncCrudAppService<StrategyWarehousingDto, Guid, StrategyWarehousingPagedRequest, StrategyWarehousingCreatedDto, StrategyWarehousingUpdatedDto>
    {
    }
}
