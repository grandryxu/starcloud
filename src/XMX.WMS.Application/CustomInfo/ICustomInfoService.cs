using Abp.Application.Services;
using System;
using XMX.WMS.CustomInfo.Dto;

namespace XMX.WMS.CustomInfo
{
    public interface ICustomInfoService : IAsyncCrudAppService<CustomInfoDto, Guid, CustomInfoPagedRequest, CustomInfoCreatedDto, CustomInfoUpdatedDto>
    {
    }
}
