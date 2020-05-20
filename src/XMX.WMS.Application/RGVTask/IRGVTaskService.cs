using Abp.Application.Services;
using System;
using System.Collections.Generic;
using System.Text;
using XMX.WMS.RGVTask.Dto;

namespace XMX.WMS.RGVTask
{
    public interface IRGVTaskService : IAsyncCrudAppService<RGVTaskDto, Guid, RGVTaskPagedRequest, RGVTaskCreatedDto, RGVTaskUpdatedDto>
    {
    }
}
