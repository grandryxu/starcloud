using Abp.Application.Services;
using System;
using XMX.WMS.AreaInfo.Dto;

namespace XMX.WMS.AreaInfo
{
    public interface IAreaInfoService : IAsyncCrudAppService<AreaInfoDto, Guid, AreaInfoPagedRequest, AreaInfoCreatedDto, AreaInfoUpdatedDto>
    {
    }
}
