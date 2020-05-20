using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.ExportConfirm.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using XMX.WMS.Base.Session;
using System.Threading.Tasks;
using Abp.Application.Services.Dto;

namespace XMX.WMS.ExportConfirm
{
    public class ExportConfirmService : AsyncCrudAppService<ExportConfirm, ExportConfirmDto, Guid, ExportConfirmPagedRequest, ExportConfirmCreatedDto, ExportConfirmUpdatedDto>, IExportConfirmService
    {
        private readonly Guid UserCompanyId;
        public ExportConfirmService(IRepository<ExportConfirm, Guid> repository) : base(repository)
        {
            UserCompanyId = AbpSession.GetCompanyId();
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        protected override IQueryable<ExportConfirm> CreateFilteredQuery(ExportConfirmPagedRequest input)
        {
            return Repository.GetAll()
                    .WhereIf(AbpSession.UserId != 1, x => x.confirm_company_id == UserCompanyId)
                    .WhereIf(!input.confirm_bill_bar.IsNullOrWhiteSpace(), x => x.confirm_bill_bar.Contains(input.confirm_bill_bar))
                    ;
        }

        public override Task<ExportConfirmDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override async Task<ExportConfirmDto> Create(ExportConfirmCreatedDto input)
        {
            return null;
        }

        /// <summary>
        /// 编辑
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override async Task<ExportConfirmDto> Update(ExportConfirmUpdatedDto input)
        {
            return null;
        }

        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override async Task Delete(EntityDto<Guid> input)
        {
            
        }
    }
}
