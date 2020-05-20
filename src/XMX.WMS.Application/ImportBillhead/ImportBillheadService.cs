using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.ImportBillhead.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using System.Collections.Generic;
using Abp.UI;
using System.Threading.Tasks;
using Abp.Application.Services.Dto;
using Abp.Authorization;
using XMX.WMS.Authorization;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using XMX.WMS.WMSOptLogInfo;
using Newtonsoft.Json;
using XMX.WMS.Base.Session;

namespace XMX.WMS.ImportBillhead
{
    [AbpAuthorize(PermissionNames.ImportBillManage)]
    public class ImportBillheadService : AsyncCrudAppService<ImportBillhead, ImportBillheadDto, Guid, ImportBillheadPagedRequest, ImportBillheadCreatedDto, ImportBillheadUpdatedDto>, IImportBillheadService
    {
        private readonly IRepository<EncodingRule.EncodingRule, Guid> _erRepository;
        private readonly IRepository<ImportBillbody.ImportBillbody, Guid> _ibRepository;
        private readonly Guid UserCompanyId;
        //日志
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public ImportBillheadService(IRepository<ImportBillhead, Guid> repository,
            IRepository<ImportBillbody.ImportBillbody, Guid> ibRepository,
            IRepository<EncodingRule.EncodingRule, Guid> erRepository) : base(repository)
        {
            _ibRepository = ibRepository;
            _erRepository = erRepository;
            UserCompanyId = AbpSession.GetCompanyId();
            LogContext = DynamicDbContext.GetInstance(string.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                CompanyId = UserCompanyId,
                OptPath = "XMX.WMS.ImportBillhead.ImportBillheadService.",
                OptModule = "入库单据管理"
            };
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.ImportBillManage_Get)]
        protected override IQueryable<ImportBillhead> CreateFilteredQuery(ImportBillheadPagedRequest input)
        {
            return Repository.GetAll()
                    .WhereIf(AbpSession.UserId != 1, x => x.imphead_company_id == UserCompanyId)
                    .WhereIf(!input.imphead_code.IsNullOrWhiteSpace(), x => x.imphead_code.Contains(input.imphead_code))
                    .WhereIf(!input.imphead_external_code.IsNullOrWhiteSpace(), x => x.imphead_external_code.Contains(input.imphead_external_code))
                    .WhereIf(input.imphead_custom_id.HasValue, x => x.imphead_custom_id == input.imphead_custom_id)
                    .WhereIf(input.imphead_bill_id.HasValue, x => x.imphead_bill_id == input.imphead_bill_id)
                    .WhereIf(input.imphead_execute_flag != null, x => x.imphead_execute_flag == input.imphead_execute_flag)
                    .WhereIf(input.imphead_warehouse_id.HasValue, x => x.imphead_warehouse_id == input.imphead_warehouse_id)
                    .WhereIf(!input.startDate.IsNullOrWhiteSpace(), x => Convert.ToDateTime(input.startDate + " 00:00:00") <= x.imphead_date)
                    .WhereIf(!input.endDate.IsNullOrWhiteSpace(), x => x.imphead_date <= Convert.ToDateTime(input.endDate + " 23:59:59"))
                ;
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ImportBillManage_Get)]
        public override Task<ImportBillheadDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ImportBillManage_Add)]
        public override async Task<ImportBillheadDto> Create(ImportBillheadCreatedDto input)
        {
            ImportBillheadDto dto = await base.Create(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Create", WMSOptLogInfo.WMSOptLogInfo.ADD, "", JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 入库单据新增（主从）
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ImportBillManage_Add)]
        public Task<ImportBillheadDto> CreateImportBill(ImportBillheadbodyCreateDto input)
        {
            try
            {
                bool flag = Repository.GetAll().Where(x => x.imphead_code == input.head.imphead_code).Any();
                if (flag)
                {
                    EncodingRule.EncodingRuleService er = new EncodingRule.EncodingRuleService(_erRepository, Repository);
                    input.head.imphead_code = er.GetEncodingRule("RKCode");
                }
                input.head.imphead_company_id = UserCompanyId;
                Task<ImportBillheadDto> taskDto = base.Create(input.head);
                if (input.createList != null && input.createList.Count > 0 && !InsertBody(taskDto.Result, input.createList))
                    throw new UserFriendlyException("新增批次信息失败！");
                ImportBillheadDto dto = taskDto.Result;
                WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "CreateImportBill", WMSOptLogInfo.WMSOptLogInfo.ADD, "", JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
                LogContext.WMSOptLogInfo.Add(logInfoEntity);
                LogContext.SaveChanges();
                return taskDto;
            }
            catch (Exception)
            {
                throw new UserFriendlyException("新增信息失败！");
            }

        }

        /// <summary>
        /// 编辑
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ImportBillManage_Update)]
        public override async Task<ImportBillheadDto> Update(ImportBillheadUpdatedDto input)
        {
            ImportBillhead oldEntity = Repository.Get(input.Id);
            string oldval = JsonConvert.SerializeObject(oldEntity);
            ImportBillheadDto dto = await base.Update(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, oldval, JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 入库单据编辑（主从）
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ImportBillManage_Update)]
        public Task<ImportBillheadDto> UpdateImportBill(ImportBillheadbodyUpdateDto input)
        {
            try
            {
                bool flag = Repository.GetAll().Where(x => x.Id != input.head.Id).Where(x => x.imphead_code == input.head.imphead_code).Any();
                if (flag)
                {
                    EncodingRule.EncodingRuleService er = new EncodingRule.EncodingRuleService(_erRepository, Repository);
                    input.head.imphead_code = er.GetEncodingRule("RKCode");
                }
                input.head.imphead_company_id = UserCompanyId;
                Task<ImportBillheadDto> taskDto = base.Update(input.head);
                if (input.createList != null && input.createList.Count > 0 && !InsertBody(taskDto.Result, input.createList))
                    throw new UserFriendlyException("新增批次信息失败！");
                if (input.updateList != null && input.updateList.Count > 0 && !UpdateBody(input.updateList))
                    throw new UserFriendlyException("修改批次信息失败！");
                if (input.idList != null && input.idList.Count > 0 && !DeleteBody(input.idList))
                    throw new UserFriendlyException("删除批次信息失败！");
                return taskDto;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                throw new UserFriendlyException("修改信息失败！");
            }
        }

        /// <summary>
        /// 批量审核
        /// </summary>
        /// <param name="inputList"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ImportBillBodyManage_Update)]
        public async Task<ListResultDto<ImportBillhead>> AuditList(List<Guid> inputList)
        {
            List<ImportBillhead> list = new List<ImportBillhead>();
            foreach (Guid headid in inputList.ToArray<Guid>())
            {
                ImportBillhead importBillhead = Repository.Get(headid);
                importBillhead.imphead_audit_flag = AuditFlag.已审核;
                List<ImportBillbody.ImportBillbody> importBillbody = _ibRepository.GetAll().Where(x => x.ImportBillhead.Id == headid).ToList();
                foreach (ImportBillbody.ImportBillbody billbody in importBillbody)
                {
                    billbody.impbody_audit_flag = AuditFlag.已审核;
                    billbody.impbody_audit_datetime = DateTime.Now;
                    billbody.impbody_audit_uid = AbpSession.UserId.ToString();
                    _ibRepository.Update(billbody);
                }
                list.Add(Repository.Update(importBillhead));
            }
            return new ListResultDto<ImportBillhead>(list);
        }
        /// <summary>
        /// 批量弃审
        /// </summary>
        /// <param name="inputList"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ImportBillBodyManage_Update)]
        public async Task<ListResultDto<ImportBillhead>> NoAuditList(List<Guid> inputList)
        {
            List<ImportBillhead> list = new List<ImportBillhead>();
            foreach (Guid headid in inputList.ToArray<Guid>())
            {
                ImportBillhead importBillhead = Repository.Get(headid);
                bool exist = _ibRepository.GetAll().Where(x => x.ImportBillhead.Id == headid && x.impbody_execute_flag != ExecuteFlag.未执行).Any();
                if (exist)
                    throw new UserFriendlyException("入库单号" + importBillhead.imphead_code + "的单据已经有执行的明细不可以弃审!");               
                List<ImportBillbody.ImportBillbody> importBillbody = _ibRepository.GetAll().Where(x => x.ImportBillhead.Id == headid).ToList();
                foreach (ImportBillbody.ImportBillbody billbody in importBillbody)
                {
                    billbody.impbody_audit_flag = AuditFlag.未审核;
                    billbody.impbody_audit_datetime = DateTime.Now;
                    billbody.impbody_audit_uid = AbpSession.UserId.ToString();
                    _ibRepository.Update(billbody);
                }
                importBillhead.imphead_audit_flag = AuditFlag.未审核;
                list.Add(Repository.Update(importBillhead));
            }
            return new ListResultDto<ImportBillhead>(list);
        }

        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ImportBillManage_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
            bool flag = Repository.GetAll().Where(x => x.Id == input.Id).Where(x => x.imphead_execute_flag != ExecuteFlag.未执行).Any();
            if (flag)
                throw new UserFriendlyException("数据状态异常，无法删除！");
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
        [AbpAuthorize(PermissionNames.ImportBillManage_Delete)]
        public Task DelteList(List<Guid> idList)
        {
            bool flag = Repository.GetAll().Where(x => x.Id.IsIn(idList.ToArray<Guid>())).Where(x => x.imphead_execute_flag != ExecuteFlag.未执行).Any();
            if (flag)
                throw new UserFriendlyException("数据占用，无法删除！");
            if (null == idList)
                throw new UserFriendlyException("数据状态异常，请联系管理员！");
            return Repository.DeleteAsync(x => x.Id.IsIn(idList.ToArray<Guid>()));
        }

        /// <summary>
        /// 新增从表
        /// </summary>
        /// <param name="head"></param>
        /// <param name="createList"></param>
        private bool InsertBody(ImportBillheadDto head, List<ImportBillbody.Dto.ImportBillbodyCreatedDto> createList)
        {
            try
            {
                ImportBillbody.ImportBillbody body;
                foreach (ImportBillbody.Dto.ImportBillbodyCreatedDto bodyDto in createList)
                {
                    body = new ImportBillbody.ImportBillbody();
                    body.impbody_list_id = bodyDto.impbody_list_id;
                    body.impbody_external_listid = bodyDto.impbody_external_listid;
                    body.impbody_batch_no = bodyDto.impbody_batch_no;
                    body.impbody_lots_no = bodyDto.impbody_lots_no;
                    body.impbody_product_date = bodyDto.impbody_product_date;
                    body.impbody_product_lineid = bodyDto.impbody_product_lineid;
                    body.impbody_stock_code = bodyDto.impbody_stock_code;
                    body.impbody_remark = bodyDto.impbody_remark;
                    body.impbody_vaildate_date = bodyDto.impbody_vaildate_date;
                    body.impbody_recheck_date = bodyDto.imbody_recheck_date;
                    body.impbody_plan_quantity = bodyDto.impbody_plan_quantity;
                    body.impbody_binding_quantity = bodyDto.impbody_binding_quantity;
                    body.impbody_fulfill_quantity = bodyDto.impbody_fulfill_quantity;
                    body.impbody_execute_flag = ExecuteFlag.未执行;
                    body.impbody_audit_flag = AuditFlag.未审核;
                    body.impbody_audit_uid = bodyDto.impbody_audit_uid;
                    body.impbody_audit_datetime = bodyDto.impbody_audit_datetime;
                    body.impbody_noused_flag = NousedFlag.正常;
                    body.impbody_noused_uid = bodyDto.impbody_noused_uid;
                    body.impbody_noused_datetime = bodyDto.impbody_noused_datetime;
                    body.impbody_upload_flag = bodyDto.impbody_upload_flag;
                    body.impbody_upload_datetime = bodyDto.impbody_upload_datetime;
                    body.impbody_is_enable = bodyDto.impbody_is_enable;
                    body.impbody_goods_id = bodyDto.impbody_goods_id;
                    body.impbody_quality_status = bodyDto.impbody_quality_status;
                    body.impbody_warehouse_id = head.imphead_warehouse_id;
                    body.impbody_company_id = head.imphead_company_id;
                    body.impbody_bill_bar = head.imphead_code + bodyDto.impbody_list_id;
                    body.impbody_head_id = head.Id;
                    _ibRepository.Insert(body);
                }
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        /// <summary>
        /// 修改从表
        /// </summary>
        /// <param name="updateList"></param>
        private bool UpdateBody(List<ImportBillbody.Dto.ImportBillbodyUpdatedDto> updateList)
        {
            try
            {
                ImportBillbody.ImportBillbody body;
                foreach (ImportBillbody.Dto.ImportBillbodyUpdatedDto bodyDto in updateList)
                {
                    body = _ibRepository.Get(bodyDto.Id);
                    body.impbody_list_id = bodyDto.impbody_list_id;
                    body.impbody_batch_no = bodyDto.impbody_batch_no;
                    body.impbody_lots_no = bodyDto.impbody_lots_no;
                    body.impbody_remark = bodyDto.impbody_remark;
                    body.impbody_vaildate_date = bodyDto.impbody_vaildate_date;
                    body.impbody_plan_quantity = bodyDto.impbody_plan_quantity;
                    body.impbody_binding_quantity = bodyDto.impbody_binding_quantity;
                    body.impbody_fulfill_quantity = bodyDto.impbody_fulfill_quantity;
                    body.impbody_goods_id = bodyDto.impbody_goods_id;
                    body.impbody_quality_status = bodyDto.impbody_quality_status;
                    body.impbody_warehouse_id = bodyDto.impbody_warehouse_id;
                    _ibRepository.Update(body);
                }
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        /// <summary>
        /// 删除从表
        /// </summary>
        /// <param name="idList"></param>
        private bool DeleteBody(List<Guid> idList)
        {
            try
            {
                _ibRepository.DeleteAsync(x => x.Id.IsIn(idList.ToArray<Guid>()));
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }
    }
}
