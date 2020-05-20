using Abp.Application.Services;
using System;
using System.Collections.Generic;
using System.Text;
using XMX.WMS.PickingTask.Dto;

namespace XMX.WMS.PickingTask
{
    public interface IPickingTaskService : IAsyncCrudAppService<PickingTaskDto, Guid, PickingTaskPagedRequest, PickingTaskCreatedDto, PickingTaskUpdatedDto>
    {
    }
}
