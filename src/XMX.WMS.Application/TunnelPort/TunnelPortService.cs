using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.TunnelPort.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using Abp.Application.Services.Dto;
using System.Threading.Tasks;

namespace XMX.WMS.TunnelPort
{
    public class TunnelPortService : AsyncCrudAppService<TunnelPort, TunnelPortDto, Guid, TunnelPortPagedRequest, TunnelPortCreatedDto, TunnelPortUpdatedDto>, ITunnelPortService
    {
        public TunnelPortService(IRepository<TunnelPort, Guid> repository) : base(repository)
        {

        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        protected override IQueryable<TunnelPort> CreateFilteredQuery(TunnelPortPagedRequest input)
        {
            return Repository.GetAllIncluding();
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override Task<TunnelPortDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }
    }
}
