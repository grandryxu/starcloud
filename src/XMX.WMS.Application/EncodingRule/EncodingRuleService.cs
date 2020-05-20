using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.EncodingRule.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using System.Threading.Tasks;
using Abp.UI;
using Abp.Application.Services.Dto;
using Abp.Authorization;
using XMX.WMS.Authorization;
using System.Collections.Generic;
using XMX.WMS.Authorization.Users;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using XMX.WMS.WMSOptLogInfo;
using Newtonsoft.Json;
using XMX.WMS.Base.Session;

namespace XMX.WMS.EncodingRule
{
    [AbpAuthorize(PermissionNames.EncodingRuleManage)]
    public class EncodingRuleService : AsyncCrudAppService<EncodingRule, EncodingRuleDto, Guid, EncodingRulePagedRequest, EncodingRuleCreatedDto, EncodingRuleUpdatedDto>, IEncodingRuleService
    {
        private readonly IRepository<ImportBillhead.ImportBillhead, Guid> _iRepository;
        private readonly IRepository<ExportBillhead.ExportBillhead, Guid> _eRepository;
        private readonly IRepository<QualityCheck.QualityCheck, Guid> _cRepository;
        private readonly IRepository<TaskMainInfo.TaskMainInfo, Guid> _tRepository;
        private readonly Guid UserCompanyId;
        //日志
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public EncodingRuleService(IRepository<EncodingRule, Guid> repository,
            IRepository<ImportBillhead.ImportBillhead, Guid> iRepository,
            IRepository<ExportBillhead.ExportBillhead, Guid> eRepository,
            IRepository<QualityCheck.QualityCheck, Guid> cRepository,
            IRepository<TaskMainInfo.TaskMainInfo, Guid> tRepository) : base(repository)
        {
            _iRepository = iRepository;
            _eRepository = eRepository;
            _cRepository = cRepository;
            _tRepository = tRepository;
            UserCompanyId = AbpSession.GetCompanyId();
            LogContext = DynamicDbContext.GetInstance(string.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                CompanyId = UserCompanyId,
                OptPath = "XMX.WMS.EncodingRule.EncodingRuleService.",
                OptModule = "编码规则管理"
            };
        }

        /// <summary>
        /// CKCode,WaveCode
        /// </summary>
        /// <param name="repository"></param>
        /// <param name="eRepository"></param>
        public EncodingRuleService(IRepository<EncodingRule, Guid> repository,
            IRepository<ExportBillhead.ExportBillhead, Guid> eRepository) : base(repository)
        {
            _eRepository = eRepository;
            UserCompanyId = AbpSession.GetCompanyId();
        }

        /// <summary>
        /// RKCode
        /// </summary>
        /// <param name="repository"></param>
        /// <param name="iRepository"></param>
        public EncodingRuleService(IRepository<EncodingRule, Guid> repository,
            IRepository<ImportBillhead.ImportBillhead, Guid> iRepository) : base(repository)
        {
            _iRepository = iRepository;
            UserCompanyId = AbpSession.GetCompanyId();
        }

        /// <summary>
        /// TaskCode
        /// </summary>
        /// <param name="repository"></param>
        /// <param name="tRepository"></param>
        public EncodingRuleService(IRepository<EncodingRule, Guid> repository,
            IRepository<TaskMainInfo.TaskMainInfo, Guid> tRepository) : base(repository)
        {
            _tRepository = tRepository;
            UserCompanyId = AbpSession.GetCompanyId();
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.EncodingRuleManage_Get)]
        protected override IQueryable<EncodingRule> CreateFilteredQuery(EncodingRulePagedRequest input)
        {
            return Repository.GetAll()
                    .WhereIf(AbpSession.UserId != 1, x => x.code_company_id == UserCompanyId)
                    .WhereIf(!input.code_code.IsNullOrWhiteSpace(), x => x.code_code.Contains(input.code_code))
                    .WhereIf(!input.code_name.IsNullOrWhiteSpace(), x => x.code_name.Contains(input.code_name))
                    .WhereIf(!input.code_prefix.IsNullOrWhiteSpace(), x => x.code_prefix.Contains(input.code_prefix))
                    .WhereIf(input.code_date_type.HasValue, x => x.code_date_type==input.code_date_type)
                    ;
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.EncodingRuleManage_Get)]
        public override Task<EncodingRuleDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.EncodingRuleManage_Add)]
        public override async Task<EncodingRuleDto> Create(EncodingRuleCreatedDto input)
        {
            input.code_company_id = UserCompanyId;
            var flag = Repository.GetAll().Where(x => x.code_code == input.code_code).Any();
            if (flag)
                throw new UserFriendlyException("编码规则已存在！");
            EncodingRuleDto dto = await base.Create(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Create", WMSOptLogInfo.WMSOptLogInfo.ADD, "", JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 编辑
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.EncodingRuleManage_Update)]
        public override async Task<EncodingRuleDto> Update(EncodingRuleUpdatedDto input)
        {
            var flag = Repository.GetAll().Where(x => x.Id != input.Id).Where(x => x.code_code == input.code_code).Any();
            if (flag)
            {
                WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, "", "", WMSOptLogInfo.WMSOptLogInfo.FAIL);
                LogContext.WMSOptLogInfo.Add(logInfoEntity);
                LogContext.SaveChanges();
                throw new UserFriendlyException("编码规则已存在！");
            }
            EncodingRule oldEntity = Repository.Get(input.Id);
            string oldval = JsonConvert.SerializeObject(oldEntity);
            EncodingRuleDto dto = await base.Update(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, oldval, JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.EncodingRuleManage_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
            /*var flag = _gRepository.GetAll().Where(x => x.goods_warehousing_id == input.Id).Any();
            if (flag)
                throw new UserFriendlyException("数据占用，无法删除！");*/
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Delete", WMSOptLogInfo.WMSOptLogInfo.DELETE, input.Id.ToString(), "", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            await Repository.DeleteAsync(x => x.Id == input.Id);
        }

        /// <summary>
        /// 批量删除
        /// </summary>
        /// <param name="idList"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.EncodingRuleManage_Delete)]
        public Task DelteList(List<Guid> idList)
        {
            /*var flag = _gRepository.GetAll().Where(x => x.goods_warehousing_id.Value.IsIn(idList.ToArray<Guid>())).Any();
            if (flag)
                throw new UserFriendlyException("数据占用，无法删除！");*/
            if (null == idList)
                throw new UserFriendlyException("参数解析异常，请联系管理员！");
            return Repository.DeleteAsync(x => x.Id.IsIn(idList.ToArray<Guid>()));
        }

        /// <summary>
        /// 获取编号
        /// </summary>
        /// <param name="code"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.EncodingRuleManage_Get)]
        public string GetEncodingRule(string code)
        {
            string erCode = null;
            EncodingRule er = Repository.FirstOrDefault(x => x.code_code == code && x.code_company_id == UserCompanyId);
            if (er != null)
            {
                //前缀
                erCode = er.code_prefix;
                //日期类型 1无；2年月日；3年月日小时分钟秒
                if (er.code_date_type != DateType.无)
                {
                    erCode += DateTime.Now.Year.ToString() + DateTime.Now.Month.ToString().PadLeft(2,'0') + DateTime.Now.Day.ToString().PadLeft(2,'0');
                    if (er.code_date_type == DateType.年月日小时分钟秒)
                        erCode += DateTime.Now.Hour.ToString() + DateTime.Now.Minute.ToString() + DateTime.Now.Second.ToString();
                }
                var MaxNo = "";
                switch (code)
                {
                    case "RKCode":
                        MaxNo = (from s in _iRepository.GetAll().Where(x => x.imphead_company_id == UserCompanyId)
                                                                .WhereIf(!erCode.IsNullOrWhiteSpace(), x => x.imphead_code.Contains(erCode))
                                     select s.imphead_code).Max();
                        break;
                    case "TaskCode":
                        MaxNo = (from s in _tRepository.GetAll().Where(x => x.main_company_id == UserCompanyId)
                                                                .WhereIf(!erCode.IsNullOrWhiteSpace(), x => x.main_no.Contains(erCode))
                                 select s.main_no).Max();
                        break;
                    case "CKCode":
                        MaxNo = (from s in _eRepository.GetAll().Where(x => x.exphead_company_id == UserCompanyId)
                                                                .WhereIf(!erCode.IsNullOrWhiteSpace(), x => x.exphead_code.Contains(erCode))
                                 select s.exphead_code).Max();
                        break;
                    case "WaveCode":
                        MaxNo = (from s in _eRepository.GetAll().Where(x => x.exphead_company_id == UserCompanyId)
                                                                .WhereIf(!erCode.IsNullOrWhiteSpace(), x => x.exphead_wave_no.Contains(erCode))
                                 select s.exphead_wave_no).Max();
                        break;
                    case "CJCode":
                        MaxNo = (from s in _cRepository.GetAll().Where(x => x.check_company_id == UserCompanyId)
                                                                .WhereIf(!erCode.IsNullOrWhiteSpace(), x => x.check_code.Contains(erCode))
                                 select s.check_code).Max();
                        break;
                    case "PDCode":
                        //MaxNo = (from s in _eRepository.GetAll().Where(x => x.exphead_company_id == UserCompanyId)
                        //                                        .WhereIf(!erCode.IsNullOrWhiteSpace(), x => x.exphead_wave_no.Contains(erCode))
                        //         select s.exphead_wave_no).Max();
                        break;
                    default:
                        break;
                }
                if (MaxNo.IsNullOrWhiteSpace())
                    MaxNo = "0";
                else
                    MaxNo = MaxNo.Substring(erCode.Length);
                er.code_record = Int32.Parse(MaxNo);
                //TODO 判断长度超过额定，归0
                //if (code == "TaskCode" && er.code_record == 99999)
                //{
                //    er.code_record = 0;
                //}
                // 后缀序列长度
                int code_suffix_length = er.code_suffix_length;
                // 上次序列号
                int code_record = er.code_record;
                int new_code_record = code_record + 1;
                // 生成的后缀
                string codeEnd = new_code_record.ToString();
                if (codeEnd.Length > code_suffix_length)
                    codeEnd = codeEnd.Substring(codeEnd.Length - code_suffix_length);
                codeEnd = codeEnd.PadLeft(code_suffix_length, '0');
                erCode += codeEnd;
                //修改序列号
                er.code_record = new_code_record;
                Repository.Update(er);
            }
            return erCode;
        }
    }
}
