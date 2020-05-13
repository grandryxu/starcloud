using Abp.Application.Services;
using System;
using XMX.WMS.PortInfo.Dto;

namespace XMX.WMS.PortInfo
{
    public interface IPortInfoService : IAsyncCrudAppService<PortInfoDto, Guid, PortInfoPagedRequest, PortInfoCreatedDto, PortInfoUpdatedDto>
    {
    }
}
