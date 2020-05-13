using Abp.Application.Services;
using System;
using XMX.WMS.TaskMainInfo.Dto;

namespace XMX.WMS.TaskMainInfo
{
    public interface ITaskMainInfoService : IAsyncCrudAppService<TaskMainInfoDto, Guid, TaskMainInfoPagedRequest, TaskMainInfoCreatedDto, TaskMainInfoUpdatedDto>
    {
    }
}
