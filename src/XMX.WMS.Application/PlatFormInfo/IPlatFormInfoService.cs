using Abp.Application.Services;
using System;
using XMX.WMS.PlatFormInfo.Dto;

namespace XMX.WMS.PlatFormInfo
{
    public interface IPlatFormInfoService : IAsyncCrudAppService<PlatFormInfoDto, Guid, PlatFormInfoPagedRequest, PlatFormInfoCreatedDto, PlatFormInfoUpdatedDto>
    {
    }
}
