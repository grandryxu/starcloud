using Abp.Application.Services;
using System;
using XMX.WMS.TunnelInfo.Dto;

namespace XMX.WMS.TunnelInfo
{
    public interface ITunnelInfoService : IAsyncCrudAppService<TunnelInfoDto, Guid, TunnelInfoPagedRequest, TunnelInfoCreatedDto, TunnelInfoUpdatedDto>
    {
    }
}
