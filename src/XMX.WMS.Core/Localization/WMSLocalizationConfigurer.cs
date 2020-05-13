using Abp.Configuration.Startup;
using Abp.Localization;
using Abp.Localization.Dictionaries;
using Abp.Localization.Dictionaries.Xml;
using Abp.Reflection.Extensions;
using System;

namespace XMX.WMS.Localization
{
    public static class WMSLocalizationConfigurer
    {
        /// <summary>
        /// 获取国际化文档
        /// </summary>
        /// <param name="localizationConfiguration"></param>
        public static void Configure(ILocalizationConfiguration localizationConfiguration)
        {
            localizationConfiguration.Sources.Add(
                new DictionaryBasedLocalizationSource(WMSConsts.LocalizationSourceName,
                    new XmlEmbeddedFileLocalizationDictionaryProvider(
                        typeof(WMSLocalizationConfigurer).GetAssembly(),
                        "XMX.WMS.Localization.SourceFiles"
                    )
                )
            );
        }

        /// <summary>
        /// 获取翻译值
        /// </summary>
        /// <param name="key"></param>
        /// <returns></returns>
        public static string L(string key)
        {
            try
            {
                return LocalizationHelper.GetSource("WMS").GetString(key);
            }
            catch (Exception)
            {
                return "Error: GetSource for key('" + key + "') error!";
            }
        }
    }
}
