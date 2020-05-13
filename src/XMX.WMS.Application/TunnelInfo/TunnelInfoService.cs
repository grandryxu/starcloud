using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.TunnelInfo.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using Abp.Application.Services.Dto;
using System.Threading.Tasks;

namespace XMX.WMS.TunnelInfo
{
    public class TunnelInfoService : AsyncCrudAppService<TunnelInfo, TunnelInfoDto, Guid, TunnelInfoPagedRequest, TunnelInfoCreatedDto, TunnelInfoUpdatedDto>, ITunnelInfoService
    {
        public TunnelInfoService(IRepository<TunnelInfo, Guid> repository) : base(repository)
        {

        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        protected override IQueryable<TunnelInfo> CreateFilteredQuery(TunnelInfoPagedRequest input)
        {
            return Repository.GetAllIncluding()
                .WhereIf(!input.tunnel_name.IsNullOrWhiteSpace(), x => x.tunnel_name.Contains(input.tunnel_name))
                ;
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override Task<TunnelInfoDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }
    }
}
