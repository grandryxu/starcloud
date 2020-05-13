using Abp.Application.Services;
using System;
using XMX.WMS.QualityCheck.Dto;

namespace XMX.WMS.QualityCheck
{
    public interface IQualityCheckService : IAsyncCrudAppService<QualityCheckDto, Guid, QualityCheckPagedRequest, QualityCheckCreateDto, QualityCheckUpdateDto>
    {
    }
}
