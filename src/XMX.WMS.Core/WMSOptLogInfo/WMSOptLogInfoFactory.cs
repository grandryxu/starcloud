using System;

namespace XMX.WMS.WMSOptLogInfo
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-05-07 16:22:01
    /// 描 述：
    ///</summary>
    public class WMSOptLogInfoFactory
    {
        /// <summary>
        /// 构造日志实体
        /// </summary>
        /// <param name="info"></param>
        /// <param name="UserId"></param>
        /// <param name="OptPathMethodName"></param>
        /// <param name="optAction"></param>
        /// <param name="oldVal"></param>
        /// <param name="newVal"></param>
        /// <returns></returns>
        public static WMSOptLogInfo CreateWMSOptLogInfo(WMSOptLogInfo info, Guid companyId, long UserId, string OptPathMethodName, string optAction, string oldVal, string newVal)
        {
            if (info == null)
                info = new WMSOptLogInfo();
            info.CompanyId = companyId;
            info.CreatorUserId = UserId;
            info.OptPath = string.Concat(info.OptPath, OptPathMethodName);
            info.OptAction = optAction;
            info.OldVal = oldVal;
            info.NewVal = newVal;
            return info;
        }

        public static WMSOptLogInfo CreateWMSOptLogInfo(WMSOptLogInfo info, Guid companyId, long UserId, string OptPathMethodName, string optAction)
        {
            if (info == null)
                info = new WMSOptLogInfo();
            info.CompanyId = companyId;
            info.CreatorUserId = UserId;
            info.OptPath = string.Concat(info.OptPath, OptPathMethodName);
            info.OptAction = optAction;
            return info;
        }

        public static WMSOptLogInfo CreateWMSOptLogInfo(WMSOptLogInfo info, Guid companyId, string oldVal, string newVal)
        {
            if (info == null)
                info = new WMSOptLogInfo();
            info.CompanyId = companyId;
            info.OldVal = oldVal;
            info.NewVal = newVal;
            return info;
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="info">实体信息</param>
        /// <param name="UserId">操作者Id</param>
        /// <param name="OptPathMethodName">调用的接口方法名</param>
        /// <param name="optAction">操作行为</param>
        /// <param name="oldVal">旧值</param>
        /// <param name="newVal">新值</param>
        /// <param name="optResult">操作结果</param>
        /// <returns></returns>
        public static WMSOptLogInfo CreateWMSOptLogInfo(WMSOptLogInfo info, Guid companyId, long UserId, string OptPathMethodName, string optAction, string oldVal, string newVal, string optResult)
        {
            if (info == null)
                info = new WMSOptLogInfo();
            info.CompanyId = companyId;
            info.CreatorUserId = UserId;
            info.OptPath = string.Concat(info.OptPath, OptPathMethodName);
            info.OptAction = optAction;
            info.OldVal = oldVal;
            info.NewVal = newVal;
            info.OptResult = optResult;
            return info;
        }
    }
}
