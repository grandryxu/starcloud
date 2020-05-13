using Abp.Application.Services;
using System;
using XMX.WMS.QualityReleased.Dto;

namespace XMX.WMS.QualityReleased
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-03-19 13:20:09
    /// 描 述：
    ///</summary>
    public interface IQualityReleasedService : IAsyncCrudAppService<QualityReleasedDto, Guid, QualityReleasedPagedRequest, QualityReleasedCreateDto, QualityReleasedUpdateDto>
    {

    }

}
