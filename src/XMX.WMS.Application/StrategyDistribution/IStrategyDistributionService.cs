using Abp.Application.Services;
using System;
using XMX.WMS.StrategyDistribution.Dto;

namespace XMX.WMS.StrategyDistribution
{
    public interface IStrategyDistributionService : IAsyncCrudAppService<StrategyDistributionDto, Guid, StrategyDistributionPagedRequest, StrategyDistributionCreatedDto, StrategyDistributionUpdatedDto>
    {
    }
}
