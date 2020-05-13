using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.SlotSize.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using System.Collections.Generic;

namespace XMX.WMS.SlotSize
{
    public class SlotSizeService : AsyncCrudAppService<SlotSize, SlotSizeDto, Guid, SlotSizePagedRequest, SlotSizeCreatedDto, SlotSizeUpdatedDto>, ISlotSizeService
    {
        public SlotSizeService(IRepository<SlotSize, Guid> repository) : base(repository)
        {

        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        protected override IQueryable<SlotSize> CreateFilteredQuery(SlotSizePagedRequest input)
        {
            return Repository.GetAllIncluding()
                .WhereIf(!input.size_name.IsNullOrWhiteSpace(), x => x.size_name.Contains(input.size_name))
                ;
        }
        /// <summary>
        /// 库位容积大小下拉列表
        /// </summary>
        /// <returns></returns>
        public List<SlotSizeListDto> GetSlotSizeList()
        {
            var list = Repository.GetAll().Select(item => new { item.Id, item.size_name }).ToList();
            var relist = new List<SlotSizeListDto>();
            foreach (var item in list)
            {
                relist.Add(new SlotSizeListDto { Id = item.Id, size_name = item.size_name });
            }
            return relist;
        }
    }
}
