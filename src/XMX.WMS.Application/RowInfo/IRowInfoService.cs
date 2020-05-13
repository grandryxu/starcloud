using Abp.Application.Services;
using System;
using XMX.WMS.RowInfo.Dto;

namespace XMX.WMS.RowInfo
{
    public interface IRowInfoService : IAsyncCrudAppService<RowInfoDto, Guid, RowInfoPagedRequest, RowInfoCreatedDto, RowInfoUpdatedDto>
    {
    }
}
