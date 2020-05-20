using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.StockTaskingDetail.Dto;
using Abp.Extensions;
using System.Threading.Tasks;
using Newtonsoft.Json.Linq;
using System.Collections.Generic;
using Abp.UI;

namespace XMX.WMS.StockTaskingDetail
{
    public class StockTaskingDetailService : AsyncCrudAppService<StockTaskingDetail, StockTaskingDetailDto, Guid, StockTaskingDetailPagedRequest, StockTaskingDetailCreatedDto, StockTaskingDetailUpdatedDto>, IStockTaskingDetailService
    {
        public StockTaskingDetailService(IRepository<StockTaskingDetail, Guid> repository) : base(repository)
        {

        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        protected override IQueryable<StockTaskingDetail> CreateFilteredQuery(StockTaskingDetailPagedRequest input)
        {
            return Repository.GetAll();
        }

        /// <summary>
        /// 批量删除
        /// </summary>
        /// <param name="idList"></param>
        /// <returns></returns>
        public Task CreateDropAll(JObject idList)
        {
            dynamic jsonValues = idList;
            JArray jsonInput = jsonValues.idList;

            List<Guid> list = jsonInput.ToObject<List<Guid>>();
            if (null == list)
                throw new UserFriendlyException("参数解析异常，请联系管理员！");
            return Repository.DeleteAsync(x => x.Id.IsIn(list.ToArray<Guid>()));
        }
    }
}
