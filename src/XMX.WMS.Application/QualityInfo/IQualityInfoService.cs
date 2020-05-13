using Abp.Application.Services;
using System;
using XMX.WMS.QualityInfo.Dto;

namespace XMX.WMS.QualityInfo
{
    public interface IQualityInfoService : IAsyncCrudAppService<QualityInfoDto, Guid, QualityInfoPagedRequest, QualityInfoCreatedDto, QualityInfoUpdatedDto>
    {
    }
}
