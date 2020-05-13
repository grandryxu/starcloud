using Abp.Application.Services;
using Abp.Application.Services.Dto;
using Abp.Authorization;
using Abp.Domain.Repositories;
using Abp.Extensions;
using Abp.Linq.Extensions;
using Abp.Runtime.Session;
using Abp.UI;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using XMX.WMS.Authorization;
using XMX.WMS.Authorization.Users;
using XMX.WMS.WarehouseInfo.Dto;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using XMX.WMS.WMSOptLogInfo;
using Newtonsoft.Json;

namespace XMX.WMS.WarehouseInfo
{
    [AbpAuthorize(PermissionNames.WarehouseBaseInfo)]
    public class WarehouseInfoService : AsyncCrudAppService<WarehouseInfo, WarehouseInfoDto, Guid, WarehouseInfoPagedRequest, WarehouseInfoCreatedDto, WarehouseInfoUpdatedDto>, IWarehouseInfoService
    {
        private readonly UserManager _userManager;
        private readonly IRepository<AreaInfo.AreaInfo, Guid> _areaRepository;

        //日志
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public WarehouseInfoService(IRepository<WarehouseInfo, Guid> repository, 
                                    IRepository<AreaInfo.AreaInfo, Guid> ibRepository,
                                    UserManager userManager) : base(repository)
        {
            _areaRepository = ibRepository;
            _userManager = userManager;

            LogContext = DynamicDbContext.GetInstance(String.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                OptPath = "XMX.WMS.WarehouseInfo.WarehouseInfoService.",
                OptModule = "仓库基础信息"
            };
        }

        /// <summary>
        /// 获取仓库区域列表
        /// </summary>
        /// <param name="WarehouseInfoSerchRequest"></param>
        /// <returns></returns>
        public List<object> GetWarehouseAreaList(WarehouseInfoPagedRequest input)
        {
            //仓库列表
            var olist = Repository.GetAll().Select(item => new { item.Id, item.warehouse_code, item.warehouse_name })
                .WhereIf(!input.warehouse_code.IsNullOrWhiteSpace(), x => x.warehouse_code == input.warehouse_code);
            var relist = new List<object>();
            foreach (var item in olist)
            {
                //根据仓库获取区域列表
                var master = _areaRepository.GetAllList(x => x.area_warehouse_id == item.Id)
                    .Select(masteritem => new { masteritem.Id, masteritem.area_name });
                List<object> areaList = new List<object>();
                foreach (var index in master)
                    areaList.Add(new { value = index.Id, label = index.area_name });
                if (areaList.ToList().Count > 0)
                    relist.Add(new { value = item.Id, label = item.warehouse_name, children = areaList });
            }
            return relist;
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.WarehoueInfo_Get)]
        protected override IQueryable<WarehouseInfo> CreateFilteredQuery(WarehouseInfoPagedRequest input)
        {
            return Repository.GetAllIncluding()
                .WhereIf(!input.warehouse_name.IsNullOrWhiteSpace(), x => x.warehouse_name.Contains(input.warehouse_name))
                .WhereIf(input.warehouse_type.HasValue, x => x.warehouse_type == input.warehouse_type)
                  .WhereIf(!input.warehouse_code.IsNullOrWhiteSpace(), x => x.warehouse_code.Contains(input.warehouse_code))
                ;
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override Task<WarehouseInfoDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.WarehoueInfo_Add)]
        public override async Task<WarehouseInfoDto> Create(WarehouseInfoCreatedDto input)
        {
            var isrename = Repository.GetAll().Where(x => x.warehouse_name == input.warehouse_name).Any();
            var isrecode = Repository.GetAll().Where(x => x.warehouse_code == input.warehouse_code).Any();
            if (!isrename && !isrecode)
            {
                User loginuser = _userManager.GetUserByIdAsync(AbpSession.UserId.Value).Result;
                input.warehouse_company_id = loginuser.CompanyId;
                WarehouseInfoDto dto = await base.Create(input);
                WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Create", WMSOptLogInfo.WMSOptLogInfo.ADD, "", JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
                LogContext.WMSOptLogInfo.Add(logInfoEntity);
                LogContext.SaveChanges();
                return dto;
            }
            else
            {
                if (isrename && isrecode)
                {
                    throw new Abp.UI.UserFriendlyException("仓库名、仓库编码重复！");
                }
                else if (isrename)
                {
                    throw new Abp.UI.UserFriendlyException("仓库名重复！");
                }
                else
                {
                    throw new Abp.UI.UserFriendlyException("仓库编码重复！");
                }
            }
        }

        /// <summary>
        /// 更新
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.WarehoueInfo_Update)]
        public override async Task<WarehouseInfoDto> Update(WarehouseInfoUpdatedDto input)
        {
            var ishave = Repository.GetAll().Where(x => x.Id == input.Id).Any();
            if (!ishave)
            {
                throw new Abp.UI.UserFriendlyException("该条数据不存在！");
            }
            var query = Repository.GetAll().Where( x => x.Id != input.Id);
            var isrenamecode = query.Where(x => x.warehouse_code == input.warehouse_code || x.warehouse_name == input.warehouse_name).Any();
            if (isrenamecode)
            {
                throw new Abp.UI.UserFriendlyException("仓库名、仓库编码重复！");
            }
            else
            {
                WarehouseInfo oldEntity = Repository.FirstOrDefault(x => x.Id == input.Id);
                string oldval = JsonConvert.SerializeObject(oldEntity);
                WarehouseInfoDto dto = await base.Update(input);
                WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, oldval, JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
                LogContext.WMSOptLogInfo.Add(logInfoEntity);
                LogContext.SaveChanges();
                return dto;
            }
        }

        /// <summary>
        /// 仓库下拉列表
        /// </summary>
        /// <returns></returns>
        public List<WarehouseListDto> GetWarehousList()
        {
            User loginuser = _userManager.GetUserByIdAsync(AbpSession.UserId.Value).Result;
            var list = Repository.GetAll().Where(x => x.warehouse_company_id == loginuser.CompanyId).Select(item => new { item.Id, item.warehouse_name }).ToList();
            var relist = new List<WarehouseListDto>();
            foreach (var item in list)
            {
                relist.Add(new WarehouseListDto { Id = item.Id, warehouse_name = item.warehouse_name });
            }
            return relist;
        }

        /// <summary>
        /// 批量删除
        /// </summary>
        /// <param name="idList"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.WarehoueInfo_Delete)]
        public Task DelteList(List<Guid> idList)
        {
            //dynamic jsonValues = idList;
            //JArray jsonInput = jsonValues.idList;
            //List<Guid> list = jsonInput.ToObject<List<Guid>>();
            if (null == idList)
                throw new UserFriendlyException("参数解析异常，请联系管理员！");
            return Repository.DeleteAsync(x => x.Id.IsIn(idList.ToArray<Guid>()));
        }

        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.WarehoueInfo_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Delete", WMSOptLogInfo.WMSOptLogInfo.DELETE, input.Id.ToString(), "", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            await Repository.DeleteAsync(x => x.Id == input.Id);
        }

    }
}

