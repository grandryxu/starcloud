using Abp.Application.Services;
using System;
using XMX.WMS.SystemMenuInfo.Dto;

namespace XMX.WMS.SystemMenuInfo
{
    public interface ISystemMenuInfoService : IAsyncCrudAppService<SystemMenuInfoDto, Guid, SystemMenuInfoPagedRequest, SystemMenuInfoCreatedDto, SystemMenuInfoUpdatedDto>
    {
    }
}
