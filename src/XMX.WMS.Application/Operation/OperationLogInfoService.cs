using Abp.Application.Services;
using Abp.Domain.Repositories;
using Abp.Extensions;
using Abp.Linq.Extensions;
using System;
using System.Linq;
using XMX.WMS.Operation.Dto;
using XMX.WMS.Authorization;
using Abp.Authorization;
using Abp.Application.Services.Dto;
using XMX.WMS.EntityFrameworkCore.Dynamic;

namespace XMX.WMS.Operation
{
    [AbpAuthorize(PermissionNames.OptLogManage)]
    public class OperationLogInfoService : AsyncCrudAppService<OperationLogInfo, OperationLogInfoDto, Guid, OperationLogInfoPagedRequest, OperationLogInfoCreatedDto, OperationLogInfoUpdatedDto>, IOperationLogInfoService
    {
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public OperationLogInfoService(IRepository<OperationLogInfo, Guid> repository) : base(repository)
        {
            LogContext = DynamicDbContext.GetInstance(String.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        protected override IQueryable<OperationLogInfo> CreateFilteredQuery(OperationLogInfoPagedRequest input)
        {
            var query = Repository.GetAllIncluding()
                .WhereIf(!input.operation_type_name.IsNullOrWhiteSpace(), x => x.operation_type_name.Contains(input.operation_type_name))
                .WhereIf(!input.operation_module_name.IsNullOrWhiteSpace(), x => x.operation_module_name.Contains(input.operation_module_name))
                .WhereIf(!input.operation_search_content.IsNullOrWhiteSpace(), x => x.operation_search_content.Contains(input.operation_search_content));
            string[] dt = input.operation_date_range?.Split("$");
            if (dt?.Length == 2)
            {
                DateTime t1 = Convert.ToDateTime(dt[0]);
                DateTime t2 = Convert.ToDateTime(dt[1]);
                query = query.Where(x => DateTime.Compare(Convert.ToDateTime(x.CreationTime.ToString("yyyy-MM-dd")), t1) >= 0)
                    .Where(x => DateTime.Compare(Convert.ToDateTime(x.CreationTime.ToString("yyyy-MM-dd")), t2) <= 0);
            }
            return query;
        }

        //操作日志分表记录，使用动态Context查询内容，实体改用WMSOptLogInfo
        public PagedResultDto<WMSOptLogInfo.WMSOptLogInfo> GetWMSOptLogInfo(WMSOptLogInfoDto input)
        {
            var results = LogContext.WMSOptLogInfo.Where(x => !x.IsDeleted).OrderByDescending(x => x.CreationTime)
                  .WhereIf(!input.OptAction.IsNullOrWhiteSpace(), x => x.OptAction.Contains(input.OptAction))
                  .WhereIf(!input.OptModule.IsNullOrWhiteSpace(), x => x.OptModule.Contains(input.OptModule))
                  .WhereIf(!input.Content.IsNullOrWhiteSpace(), x => x.NewVal.Contains(input.Content) || x.OldVal.Contains(input.Content));
            string[] dt = input.DateRange?.Split("/");
            if (dt?.Length == 2)
            {
                DateTime t1 = Convert.ToDateTime(dt[0]);
                DateTime t2 = Convert.ToDateTime(dt[1]);
                results = results.Where(x => DateTime.Compare(Convert.ToDateTime(x.CreationTime.ToString("yyyy-MM-dd")), t1) >= 0)
                    .Where(x => DateTime.Compare(Convert.ToDateTime(x.CreationTime.ToString("yyyy-MM-dd")), t2) <= 0);
            }
            var count = results.Count();
            var objs = results.Skip(input.SkipCount).Take(input.MaxResultCount)
              .ToList();

            return new PagedResultDto<WMSOptLogInfo.WMSOptLogInfo>(count, objs);
        }


    }
}
