using Abp.Application.Services;
using System;
using XMX.WMS.StrategyMonitor.Dto;

namespace XMX.WMS.StrategyMonitor
{
    public interface IStrategyMonitorService : IAsyncCrudAppService<StrategyMonitorDto, Guid, StrategyMonitorPagedRequest, StrategyMonitorCreatedDto, StrategyMonitorUpdatedDto>
    {
    }
}
