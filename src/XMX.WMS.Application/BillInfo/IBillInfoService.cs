using Abp.Application.Services;
using System;
using XMX.WMS.BillInfo.Dto;

namespace XMX.WMS.BillInfo
{
    public interface IBillInfoService : IAsyncCrudAppService<BillInfoDto, Guid, BillInfoPagedRequest, BillInfoCreatedDto, BillInfoUpdatedDto>
    {
    }
}
