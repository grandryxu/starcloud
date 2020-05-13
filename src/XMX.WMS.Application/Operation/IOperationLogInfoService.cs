using Abp.Application.Services;
using System;
using XMX.WMS.Operation.Dto;

namespace XMX.WMS.Operation
{
    public interface IOperationLogInfoService : IAsyncCrudAppService<OperationLogInfoDto, Guid, OperationLogInfoPagedRequest, OperationLogInfoCreatedDto, OperationLogInfoUpdatedDto>
    {
    }
}
