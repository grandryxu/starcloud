using Abp.Application.Services;
using System;
using XMX.WMS.Equipment.Dto;

namespace XMX.WMS.Equipment
{
    public interface IEquipmentLogInfoService : IAsyncCrudAppService<EquipmentLogInfoDto, Guid, EquipmentLogInfoPagedRequest, EquipmentLogInfoCreatedDto, EquipmentLogInfoUpdatedDto>
    {

    }
}
