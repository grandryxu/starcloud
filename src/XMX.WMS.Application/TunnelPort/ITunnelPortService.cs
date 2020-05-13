using Abp.Application.Services;
using System;
using XMX.WMS.TunnelPort.Dto;

namespace XMX.WMS.TunnelPort
{
    public interface ITunnelPortService : IAsyncCrudAppService<TunnelPortDto, Guid, TunnelPortPagedRequest, TunnelPortCreatedDto, TunnelPortUpdatedDto>
    {
    }
}
