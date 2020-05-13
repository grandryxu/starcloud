using Abp.Application.Services;
using System;
using XMX.WMS.Equipment.Dto;

namespace XMX.WMS.Equipment
{
    public interface IStackerInfoService : IAsyncCrudAppService<StackerInfoDto, Guid, StackerInfoPagedRequest, StackerInfoCreatedDto, StackerInfoUpdatedDto>
    {
    }
}
