using Abp.Application.Services;
using System;
using System.Collections.Generic;
using System.Text;
using XMX.WMS.AGVTask.Dto;

namespace XMX.WMS.AGVTask
{
    public interface IAGVTaskService : IAsyncCrudAppService<AGVTaskDto, Guid, AGVTaskPagedRequest, AGVTaskCreatedDto, AGVTaskUpdatedDto>
    {
    }
}
