using System.Threading.Tasks;
using Abp.Application.Services;
using XMX.WMS.Sessions.Dto;

namespace XMX.WMS.Sessions
{
    public interface ISessionAppService : IApplicationService
    {
        Task<GetCurrentLoginInformationsOutput> GetCurrentLoginInformations();
    }
}
