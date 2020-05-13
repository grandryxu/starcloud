using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.ExportConfirm.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;

namespace XMX.WMS.ExportConfirm
{
    public class ExportConfirmService : AsyncCrudAppService<ExportConfirm, ExportConfirmDto, Guid, ExportConfirmPagedRequest, ExportConfirmCreatedDto, ExportConfirmUpdatedDto>, IExportConfirmService
    {
        public ExportConfirmService(IRepository<ExportConfirm, Guid> repository) : base(repository)
        {

        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        protected override IQueryable<ExportConfirm> CreateFilteredQuery(ExportConfirmPagedRequest input)
        {
            return Repository.GetAllIncluding()
                .WhereIf(!input.confirm_bill_bar.IsNullOrWhiteSpace(), x => x.confirm_bill_bar.Contains(input.confirm_bill_bar))
                ;
        }
    }
}
