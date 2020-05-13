using Abp.Application.Services;
using System;
using XMX.WMS.EncodingRule.Dto;

namespace XMX.WMS.EncodingRule 
{
    public interface IEncodingRuleService : IAsyncCrudAppService<EncodingRuleDto, Guid, EncodingRulePagedRequest, EncodingRuleCreatedDto, EncodingRuleUpdatedDto>
    {
    }
}
