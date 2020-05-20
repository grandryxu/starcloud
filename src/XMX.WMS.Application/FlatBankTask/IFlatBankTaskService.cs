using Abp.Application.Services;
using System;
using System.Collections.Generic;
using System.Text;
using XMX.WMS.FlatBankTask.Dto;

namespace XMX.WMS.FlatBankTask
{
   public  interface IFlatBankTaskService : IAsyncCrudAppService<FlatBankTaskDto, Guid, FlatBankTaskPagedRequest, FlatBankTaskCreatedDto, FlatBankTaskUpdatedDto>
    {

    }
}
