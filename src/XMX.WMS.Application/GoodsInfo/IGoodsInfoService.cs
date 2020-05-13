using Abp.Application.Services;
using System;
using XMX.WMS.GoodsInfo.Dto;

namespace XMX.WMS.GoodsInfo
{
    public interface IGoodsInfoService : IAsyncCrudAppService<GoodsInfoDto, Guid, GoodsInfoPagedRequest, GoodsInfoCreatedDto, GoodsInfoUpdatedDto>
    {
    }
}
