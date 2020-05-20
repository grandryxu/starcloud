using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.TunnelPort.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using Abp.Application.Services.Dto;
using System.Threading.Tasks;
using XMX.WMS.WMSOptLogInfo;
using System.Collections.Generic;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using Abp.UI;
using XMX.WMS.Base.Session;
using Newtonsoft.Json;

namespace XMX.WMS.TunnelPort
{
    public class TunnelPortService : AsyncCrudAppService<TunnelPort, TunnelPortDto, Guid, TunnelPortPagedRequest, TunnelPortCreatedDto, TunnelPortUpdatedDto>, ITunnelPortService
    {

        private readonly IRepository<CompanyInfo.CompanyInfo, Guid> _cRepository;
        private readonly IRepository<PortInfo.PortInfo, Guid> _pRepository;
        private readonly IRepository<TunnelInfo.TunnelInfo, Guid> _tRepository;
        private readonly Guid UserCompanyId;
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public TunnelPortService(IRepository<TunnelPort, Guid> repository,
             IRepository<CompanyInfo.CompanyInfo, Guid> cRepository,
            IRepository<PortInfo.PortInfo, Guid> pRepository,
            IRepository<TunnelInfo.TunnelInfo, Guid> tRepository) : base(repository)
        {

            _cRepository = cRepository;
            _pRepository = pRepository;
            _tRepository = tRepository;

            UserCompanyId = AbpSession.GetCompanyId();
            LogContext = DynamicDbContext.GetInstance(string.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                CompanyId = UserCompanyId,
                OptPath = "XMX.WMS.TunnelPort.TunnelPortService.",
                OptModule = "巷道口号关联表"
            };
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        protected override IQueryable<TunnelPort> CreateFilteredQuery(TunnelPortPagedRequest input)
        {
            return Repository.GetAll();
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
        /// <summary>
        /// 批量新增巷道口号关联
        /// </summary>
        /// <param name="inputList"></param>
        /// <returns></returns>
        public async Task<ListResultDto<TunnelPortDto>>CreateList(List<TunnelPortCreatedDto> inputList)
        {
            List<TunnelPortDto> list = new List<TunnelPortDto>();
            foreach (TunnelPortCreatedDto input in inputList)
            {
                var is_recode = Repository.GetAll().Where(x => x.tunnelPort_tunnel_id == input.tunnelPort_tunnel_id)
                                                   .Where(x => x.tunnelPort_port_id == input.tunnelPort_port_id)
                                                   .Where(x => x.tunnelPort_company_id == input.tunnelPort_company_id)
                                                   .Where(x => !x.IsDeleted).Any();

                if (is_recode)
                {
                    var cname = _cRepository.FirstOrDefault(x => x.Id == input.tunnelPort_company_id).Name;
                    var wname = _pRepository.FirstOrDefault(x => x.Id == input.tunnelPort_port_id).port_name;
                    var tname = _tRepository.FirstOrDefault(x => x.Id == input.tunnelPort_tunnel_id).tunnel_name;
                    throw new UserFriendlyException("["+ cname + "][" + wname + "][" + tname + "]已存在相同数据，请勿重复添加");
                }
               
                list.Add(await base.Create(input));
            }
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "CreateList", WMSOptLogInfo.WMSOptLogInfo.ADD, "批量新增", JsonConvert.SerializeObject(list), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return new ListResultDto<TunnelPortDto>(list);
        }

    }
}
