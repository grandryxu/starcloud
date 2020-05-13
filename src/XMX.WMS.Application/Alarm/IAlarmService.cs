using Abp.Application.Services;
using System;
using XMX.WMS.Alarm.Dto;

namespace XMX.WMS.Alarm
{
   
    public interface IAlarmService : IAsyncCrudAppService<AlarmDto, Guid, AlarmPagedRequest, AlarmCreatedDto, AlarmUpdatedDto>
    {
    }
}
