using Abp.Application.Services;
using System;
using XMX.WMS.QualityCheckDetail.Dto;

namespace XMX.WMS.QualityCheckDetail
{
    public interface IQualityCheckDetailService : IAsyncCrudAppService<QualityCheckDetailDto, Guid, QualityCheckDetailPagedRequest, QualityCheckDetailCreateDto, QualityCheckDetailUpdateDto>
    {
    }
}
