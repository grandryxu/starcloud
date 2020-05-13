using Abp.Application.Services;
using System;
using XMX.WMS.CustomTypeInfo.Dto;

namespace XMX.WMS.CustomTypeInfo
{
    public interface ICustomTypeInfoService : IAsyncCrudAppService<CustomTypeInfoDto, Guid, CustomTypeInfoPagedRequest, CustomTypeInfoCreatedDto, CustomTypeInfoUpdatedDto>
    {
    }
}
