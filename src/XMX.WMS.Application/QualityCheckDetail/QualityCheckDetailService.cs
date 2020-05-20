using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.QualityCheckDetail.Dto;
using System.Threading.Tasks;
using System.Collections.Generic;
using Abp.Application.Services.Dto;
using XMX.WMS.Authorization;
using Abp.Authorization;

namespace XMX.WMS.QualityCheckDetail
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-03-25 15:02:07
    /// 描 述：
    ///</summary>
    [AbpAuthorize(PermissionNames.QualityCheckout)]
    public class QualityCheckDetailService : AsyncCrudAppService<QualityCheckDetail, QualityCheckDetailDto, Guid, QualityCheckDetailPagedRequest, QualityCheckDetailCreateDto, QualityCheckDetailUpdateDto>, IQualityCheckDetailService
    {
        public QualityCheckDetailService(IRepository<QualityCheckDetail, Guid> repository) : base(repository)
        {
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        protected override IQueryable<QualityCheckDetail> CreateFilteredQuery(QualityCheckDetailPagedRequest input)
        {
            return Repository.GetAllIncluding(x => x.Goods);
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override Task<QualityCheckDetailDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        public override async Task<QualityCheckDetailDto> Create(QualityCheckDetailCreateDto input)
        {
            return null;
        }

        public override async Task<QualityCheckDetailDto> Update(QualityCheckDetailUpdateDto input)
        {
            return null;
        }

        public override async Task Delete(EntityDto<Guid> input)
        {
        }

        /// <summary>
        /// 批量新增
        /// </summary>
        /// <param name="inputList"></param>
        /// <returns></returns>
        public async Task<ListResultDto<QualityCheckDetailDto>> CreateList(List<QualityCheckDetailCreateDto> inputList)
        {
            List<QualityCheckDetailDto> list = new List<QualityCheckDetailDto>();
            foreach (QualityCheckDetailCreateDto input in inputList)
            {
                try
                {
                    list.Add(await base.Create(input));
                }
                catch (Exception)
                {
                    return null;
                }
            }
            return new ListResultDto<QualityCheckDetailDto>(list);
        }

    }
}
