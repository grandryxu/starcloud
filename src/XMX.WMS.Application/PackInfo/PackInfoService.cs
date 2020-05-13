using Abp.Application.Services;
using Abp.Domain.Repositories;
using Abp.Extensions;
using Abp.Linq.Extensions;
using Abp.UI;
using Microsoft.AspNetCore.Http;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using XMX.WMS.PackInfo.Dto;
using XMX.WMS.Authorization;
using Abp.Authorization;
using Abp.Application.Services.Dto;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using XMX.WMS.WMSOptLogInfo;
using Newtonsoft.Json;

namespace XMX.WMS.PackInfo
{
    [AbpAuthorize(PermissionNames.PackBasicInfo)]
    public class PackInfoService : AsyncCrudAppService<PackInfo, PackInfoDto, Guid, PackInfoPagedRequest, PackInfoCreatedDto, PackInfoUpdatedDto>, IPackInfoService
    {
        private readonly IRepository<GoodsInfo.GoodsInfo, Guid> _goodsInfoRepository;
        //日志
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public PackInfoService(IRepository<PackInfo, Guid> repository,
            IRepository<GoodsInfo.GoodsInfo, Guid> goodsInfoRepository) : base(repository)
        {
            _goodsInfoRepository = goodsInfoRepository;

            LogContext = DynamicDbContext.GetInstance(String.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                OptPath = "XMX.WMS.PackInfo.PackInfoService.",
                OptModule = "垛形基础信息"
            };
        }

        /// <summary>
        /// 垛形图片文件上传
        /// </summary>
        /// <param name="file"></param>
        /// <returns></returns>
        public async Task<string> UploadImage(IFormFile file)
        {
            if (file.Length <= 0)
                return "文件异常";
            string filename = Guid.NewGuid().ToString() + file.FileName;
            DirectoryInfo info = System.IO.Directory.CreateDirectory(Path.Combine("wwwroot", "packInfo"));
            var filePath = Path.Combine(info.FullName, filename);
            using (var stream = File.Create(filePath))
                await file.CopyToAsync(stream);
            return Path.Combine("packInfo",filename);
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.PackBasicInfo_Get)]
        protected override IQueryable<PackInfo> CreateFilteredQuery(PackInfoPagedRequest input)
        {
            return Repository.GetAllIncluding()
                .WhereIf(!input.pack_code.IsNullOrWhiteSpace(), x => x.pack_code.Contains(input.pack_code))
                .WhereIf(!input.pack_name.IsNullOrWhiteSpace(), x => x.pack_name.Contains(input.pack_name))
                ;
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override Task<PackInfoDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.PackBasicInfo_Add)]
        public override async Task<PackInfoDto> Create(PackInfoCreatedDto input)
        {
            var is_recode = Repository.GetAll().Where(x => x.pack_code == input.pack_code).Where(x => !x.IsDeleted).Any();
            var is_rename = Repository.GetAll().Where(x => x.pack_name == input.pack_name).Where(x => !x.IsDeleted).Any();
            if (is_recode || is_rename)
                throw new UserFriendlyException("垛型编号或垛型名称已存在！");
            PackInfoDto dto = await base.Create(input);
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
        [AbpAuthorize(PermissionNames.PackBasicInfo_Update)]
        public override async Task<PackInfoDto> Update(PackInfoUpdatedDto input)
        {
            var query = Repository.GetAll().Where(x => x.Id != input.Id);
            var is_rename_or_recode = query.Where(x => x.pack_code == input.pack_code || x.pack_name == input.pack_name).Where(x => !x.IsDeleted).Any();
            if (is_rename_or_recode)
            {
                WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, "", "", WMSOptLogInfo.WMSOptLogInfo.FAIL);
                LogContext.WMSOptLogInfo.Add(logInfoEntity);
                LogContext.SaveChanges();
                throw new UserFriendlyException("垛型编号或垛型名称已存在！");
            }
                
            PackInfo oldEntity = Repository.FirstOrDefault(x => x.Id == input.Id);
            string oldval = JsonConvert.SerializeObject(oldEntity);
            PackInfoDto dto = await base.Update(input);
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
        [AbpAuthorize(PermissionNames.PackBasicInfo_Delete)]
        public Task CreateDropAll(JObject idList)
        {
            dynamic jsonValues = idList;
            JArray jsonInput = jsonValues.idList;
            
            List<Guid> list = jsonInput.ToObject<List<Guid>>();
            List<string> list1 = jsonInput.ToObject<List<string>>();
            if (null == list)
                throw new UserFriendlyException("参数解析异常，请联系管理员！");
            //try
            //{
                //var used = _customInfoRepository.GetAll().Where(x => x.CustomType.Id.IsIn(list.ToArray<Guid>())).Any();
                //var used = _goodsInfoRepository.GetAll().Where (x=>list1.Contains(x.goods_pack_id)).Any();
                var used = _goodsInfoRepository.GetAll().Where(x => list.Contains(x.goods_pack_id.Value)).Any();
                //var used1 = _goodsInfoRepository.GetAll().Where(x => Guid.Parse(x.goods_pack).IsIn(list.ToArray<Guid>())).Any();
                if (used)
                    throw new UserFriendlyException("在物料基本信息中存在已被关联的垛型信息，请核实后再删除！");
            //}
            //catch(Exception ex)
            //{
            //    Logger.ErrorFormat("垛型信息批量删除异常：{0}", ex.Message);
            //    throw new UserFriendlyException("物料信息关联的垛型中存在非法数据！这通常是由于部分物料信息非正常添加导致的。");
            //}
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "CreateDropAll", WMSOptLogInfo.WMSOptLogInfo.DELETE, "批量删除", "批量删除", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return Repository.DeleteAsync(x => x.Id.IsIn(list.ToArray<Guid>()));
        }

        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.PackBasicInfo_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Delete", WMSOptLogInfo.WMSOptLogInfo.DELETE, input.Id.ToString(), "", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            await Repository.DeleteAsync(x => x.Id == input.Id);
        }
    }
}
