using Abp.Application.Services;
using System;
using System.Collections.Generic;
using System.Text;
using XMX.WMS.DepartmentInfo.Dto;

namespace XMX.WMS.DepartmentInfo
{
    public interface IDepartmentAppService : IAsyncCrudAppService<DepartmentInfoDto, Guid, DepartmentInfoPagedRequest, DepartmentCreatedModel, DepartmentUpdatedModel>
    {

    }
}
