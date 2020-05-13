using Abp.Application.Services;
using System;
using XMX.WMS.Equipment.Dto;

namespace XMX.WMS.Equipment
{
    public interface IRGVInfoService : IAsyncCrudAppService<RGVInfoDto, Guid, RGVInfoPagedRequest, RGVInfoCreatedDto, RGVInfoUpdatedDto>
    {
    }
}
