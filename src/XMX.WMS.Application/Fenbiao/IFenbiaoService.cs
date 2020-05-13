using Abp.Application.Services;
using System;
using XMX.WMS.Fenbiao.Dto;

namespace XMX.WMS.Fenbiao
{
    public interface IFenbiaoService : IAsyncCrudAppService<FenbiaoDto, Guid, FenbiaoPagedRequest, FenbiaoCreatedDto, FenbiaoUpdatedDto>
    {
    }
}
