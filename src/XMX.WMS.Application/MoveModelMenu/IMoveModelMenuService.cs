using Abp.Application.Services;
using System;
using XMX.WMS.MoveModelMenu.Dto;

namespace XMX.WMS.MoveModelMenu
{
    public interface IMoveModelMenuService : IAsyncCrudAppService<MoveModelMenuDto, Guid, MoveModelMenuPagedRequest, MoveModelMenuCreatedDto, MoveModelMenuUpdatedDto>
    {
    }
}
