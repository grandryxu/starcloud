using Abp.Application.Services;
using System;
using System.Collections.Generic;
using System.Text;
using XMX.WMS.CompanyInfo.Dto;

namespace XMX.WMS.CompanyInfo
{
    public interface ICompanyInfoAppService : IAsyncCrudAppService<CompanyInfoDto, Guid, CompanyInfoPagedRequest, CompanyCreatedDto, CompanyUpdatedDto>
    {
    }
}
