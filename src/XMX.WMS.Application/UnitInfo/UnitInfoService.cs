using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.UnitInfo.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using Abp.UI;
using System.Threading.Tasks;
using System.Collections.Generic;
using Newtonsoft.Json.Linq;
using Abp.Application.Services.Dto;
using XMX.WMS.Authorization;
using Abp.Authorization;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using XMX.WMS.WMSOptLogInfo;
using Newtonsoft.Json;

namespace XMX.WMS.UnitInfo
{
    [AbpAuthorize(PermissionNames.MaterialMeasureUnit)]
    public class UnitInfoService : AsyncCrudAppService<UnitInfo, UnitInfoDto, Guid, UnitInfoPagedRequest, UnitInfoCreatedDto, UnitInfoUpdatedDto>, IUnitInfoService
    {
        private readonly IRepository<GoodsInfo.GoodsInfo, Guid> _goodsInfoRepository;

        //日志
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public UnitInfoService(IRepository<UnitInfo, Guid> repository, IRepository<GoodsInfo.GoodsInfo, Guid> goodsInfoRepository) : base(repository)
        {
            _goodsInfoRepository = goodsInfoRepository;
            LogContext = DynamicDbContext.GetInstance(String.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                OptPath = "XMX.WMS.UnitInfo.UnitInfoService.",
                OptModule = "物料计量单位"
            };
        }





        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.MaterialMeasureUnit_Get)]
        protected override IQueryable<UnitInfo> CreateFilteredQuery(UnitInfoPagedRequest input)
        {
            return Repository.GetAllIncluding()
                .WhereIf(!input.unit_name.IsNullOrWhiteSpace(), x => x.unit_name.Contains(input.unit_name))
                .WhereIf(input.unit_is_enable.HasValue, x => x.unit_is_enable == input.unit_is_enable)
                ;
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override Task<UnitInfoDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.MaterialMeasureUnit_Add)]
        public override async Task<UnitInfoDto> Create(UnitInfoCreatedDto input)
        {
            var is_rename = Repository.GetAll().Where(x => x.unit_name == input.unit_name).Where(x => !x.IsDeleted).Any();
            if (is_rename)
                throw new UserFriendlyException("单位名称已存在！");
            UnitInfoDto dto = await base.Create(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Create", WMSOptLogInfo.WMSOptLogInfo.ADD, "", JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 更新
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.MaterialMeasureUnit_Update)]
        public override async Task<UnitInfoDto> Update(UnitInfoUpdatedDto input)
        {
            var query = Repository.GetAll().Where(x => x.Id != input.Id);
            var is_rename = query.Where(x => x.unit_name == input.unit_name).Where(x => !x.IsDeleted).Any();
            if (is_rename)
                throw new UserFriendlyException("单位名称已存在！");
            var edit = Repository.GetAll().Where(x => x.Id == input.Id && x.unit_is_enable!=input.unit_is_enable).Any();
            if (edit)
            {
                var used = _goodsInfoRepository.GetAll().Where(x => x.goods_unit == input.Id || x.goods_unit2 == input.Id).Any();
                if (used)
                    throw new UserFriendlyException("在物料基本信息中存在已被关联物料计量单位,无法禁用");
            }
            UnitInfo oldEntity = Repository.FirstOrDefault(x => x.Id == input.Id);
            string oldval = JsonConvert.SerializeObject(oldEntity);
            UnitInfoDto dto = await base.Update(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, oldval, JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 批量删除
        /// </summary>
        /// <param name="idList"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.MaterialMeasureUnit_Delete)]
        public Task DelteList(List<Guid> idList)
        {
            if (null == idList)
                throw new UserFriendlyException("参数解析异常，请联系管理员！");
            var used = _goodsInfoRepository.GetAll().Where(x => idList.Contains(x.Unit.Id)).Any();
            if (used)
                throw new UserFriendlyException("在物料基本信息中存在已被关联物料计量单位，请核实后再删除！");
            return Repository.DeleteAsync(x => x.Id.IsIn(idList.ToArray<Guid>()));
        }
        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.MaterialMeasureUnit_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Delete", WMSOptLogInfo.WMSOptLogInfo.DELETE, input.Id.ToString(), "", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            await Repository.DeleteAsync(x => x.Id == input.Id);
        }
    }
}
