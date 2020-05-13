using System.Threading.Tasks;
using XMX.WMS.Configuration.Dto;

namespace XMX.WMS.Configuration
{
    public interface IConfigurationAppService
    {
        Task ChangeUiTheme(ChangeUiThemeInput input);
    }
}
