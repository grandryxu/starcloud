using Abp.Application.Services;
using System;
using System.Collections.Generic;
using System.Text;
using XMX.WMS.ReportTemp.Dto;

namespace XMX.WMS.ReportTemp
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-05-18 14:58:11
    /// 描 述：
    ///</summary>
    public interface IReportTempService : IAsyncCrudAppService<ReportTempDto, Guid, ReportTempPagedRequest, ReportTempCreatedDto, ReportTempUpdatedDto>
    {
    }
}
