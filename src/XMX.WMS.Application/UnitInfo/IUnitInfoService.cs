using Abp.Application.Services;
using System;
using XMX.WMS.UnitInfo.Dto;

namespace XMX.WMS.UnitInfo
{
    public interface IUnitInfoService : IAsyncCrudAppService<UnitInfoDto, Guid, UnitInfoPagedRequest, UnitInfoCreatedDto, UnitInfoUpdatedDto>
    {
    }
}
