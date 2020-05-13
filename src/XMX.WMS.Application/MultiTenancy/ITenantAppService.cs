using Abp.Application.Services;
using Abp.Application.Services.Dto;
using XMX.WMS.MultiTenancy.Dto;

namespace XMX.WMS.MultiTenancy
{
    public interface ITenantAppService : IAsyncCrudAppService<TenantDto, int, PagedTenantResultRequestDto, CreateTenantDto, TenantDto>
    {
    }
}

