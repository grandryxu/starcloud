using System.Threading.Tasks;
using Abp.Authorization;
using Abp.Runtime.Session;
using XMX.WMS.Configuration.Dto;

namespace XMX.WMS.Configuration
{
    [AbpAuthorize]
    public class ConfigurationAppService : WMSAppServiceBase, IConfigurationAppService
    {
        public async Task ChangeUiTheme(ChangeUiThemeInput input)
        {
            await SettingManager.ChangeSettingForUserAsync(AbpSession.ToUserIdentifier(), AppSettingNames.UiTheme, input.Theme);
        }
    }
}
