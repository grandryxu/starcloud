using Abp.Application.Services;
using Microsoft.AspNetCore.Http;
using System;
using System.Threading.Tasks;
using XMX.WMS.PackInfo.Dto;

namespace XMX.WMS.PackInfo
{
    public interface IPackInfoService : IAsyncCrudAppService<PackInfoDto, Guid, PackInfoPagedRequest, PackInfoCreatedDto, PackInfoUpdatedDto>
    {
        Task<string> UploadImage(IFormFile file);
    }
}
