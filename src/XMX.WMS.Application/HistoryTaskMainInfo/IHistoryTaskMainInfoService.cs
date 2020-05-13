using Abp.Application.Services;
using System;
using XMX.WMS.HistoryTaskMainInfo.Dto;

namespace XMX.WMS.HistoryTaskMainInfo
{
    public interface IHistoryTaskMainInfoService : IAsyncCrudAppService<HistoryTaskMainInfoDto, Guid, HistoryTaskMainInfoPagedRequest, HistoryTaskMainInfoCreatedDto, HistoryTaskMainInfoUpdatedDto>
    {
    }
}
