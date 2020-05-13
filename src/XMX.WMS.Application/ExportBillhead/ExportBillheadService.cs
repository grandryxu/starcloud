using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.ExportBillhead.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using System.Collections.Generic;
using Abp.UI;
using System.Threading.Tasks;
using Abp.Authorization;
using XMX.WMS.Authorization;
using Abp.Application.Services.Dto;
using XMX.WMS.Authorization.Users;
using AutoMapper;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using XMX.WMS.WMSOptLogInfo;
using Newtonsoft.Json;

namespace XMX.WMS.ExportBillhead
{
    [AbpAuthorize(PermissionNames.ExportBillManage)]
    public class ExportBillheadService : AsyncCrudAppService<ExportBillhead, ExportBillheadDto, Guid, ExportBillheadPagedRequest, ExportBillheadCreatedDto, ExportBillheadUpdatedDto>, IExportBillheadService
    {
        private readonly IRepository<EncodingRule.EncodingRule, Guid> _erRepository;
        private readonly IRepository<ExportBillbody.ExportBillbody, Guid> _ebRepository;
        //日志
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public ExportBillheadService(IRepository<ExportBillhead, Guid> repository,
            IRepository<EncodingRule.EncodingRule, Guid> erRepository,
            IRepository<ExportBillbody.ExportBillbody, Guid> ebRepository) : base(repository)
        {
            _erRepository = erRepository;
            _ebRepository = ebRepository;

            LogContext = DynamicDbContext.GetInstance(String.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                OptPath = "XMX.WMS.ExportBillhead.ExportBillheadService.",
                OptModule = "出库单据管理"
            };
        }
        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.ExportBillManage_Get)]
        protected override IQueryable<ExportBillhead> CreateFilteredQuery(ExportBillheadPagedRequest input)
        {
            return Repository.GetAllIncluding()
                    .WhereIf(!input.exphead_code.IsNullOrWhiteSpace(), x => x.exphead_code.Contains(input.exphead_code))
                    .WhereIf(!input.exphead_external_code.IsNullOrWhiteSpace(), x => x.exphead_external_code.Contains(input.exphead_external_code))
                    .WhereIf(input.exphead_custom_id.HasValue, x => x.exphead_custom_id == input.exphead_custom_id)
                    .WhereIf(input.exphead_bill_id.HasValue, x => x.exphead_bill_id == input.exphead_bill_id)
                    .WhereIf(input.exphead_execute_flag != null, x => x.exphead_execute_flag == input.exphead_execute_flag)
                    .WhereIf(input.exphead_warehouse_id.HasValue, x => x.exphead_warehouse_id == input.exphead_warehouse_id)
                    .WhereIf(!input.startDate.IsNullOrWhiteSpace(), x => Convert.ToDateTime(input.startDate + " 00:00:00") <= x.exphead_date)
                    .WhereIf(!input.endDate.IsNullOrWhiteSpace(), x => x.exphead_date <= Convert.ToDateTime(input.endDate + " 23:59:59"))
                ;
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ExportBillManage_Get)]
        public override Task<ExportBillheadDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ExportBillManage_Add)]
        public override async Task<ExportBillheadDto> Create(ExportBillheadCreatedDto input)
        {
            var flag = Repository.GetAll().Where(x => x.exphead_code == input.exphead_code).Any();
            if (flag)
            {
                EncodingRule.EncodingRuleService er = new EncodingRule.EncodingRuleService(_erRepository, Repository);
                input.exphead_code = er.GetEncodingRule("CKCode");
            }
            ExportBillheadDto dto = await base.Create(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Create", WMSOptLogInfo.WMSOptLogInfo.ADD, "", JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 新增-主从
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ExportBillManage_Add)]
        public Task<ExportBillheadDto> CreateExportBill(ExportBillheadbodyCreateDto input)
        {
            var flag = Repository.GetAll().Where(x => x.exphead_code == input.head.exphead_code).Any();
            if (flag)
                throw new UserFriendlyException("编码规则已存在！");
            Task<ExportBillheadDto> taskDto = base.Create(input.head);
            if (input.createList != null && input.createList.Count > 0 && !InsertBody(taskDto.Result, input.createList))
                throw new UserFriendlyException("新增批次信息失败！");
            ExportBillheadDto dto = taskDto.Result;
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "CreateExportBill", WMSOptLogInfo.WMSOptLogInfo.ADD, "", JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return taskDto;
        }

        /// <summary>
        /// 编辑
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ExportBillManage_Update)]
        public override async Task<ExportBillheadDto> Update(ExportBillheadUpdatedDto input)
        {
            var flag = Repository.GetAll().Where(x => x.Id != input.Id).Where(x => x.exphead_code == input.exphead_code).Any();
            if (flag)
            {
                throw new UserFriendlyException("编码规则已存在！");
            }
            ExportBillhead oldEntity = Repository.FirstOrDefault(x => x.Id == input.Id);
            string oldval = JsonConvert.SerializeObject(oldEntity);
            ExportBillheadDto dto = await base.Update(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, oldval, JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 编辑-主从
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ExportBillManage_Update)]
        public Task<ExportBillheadDto> UpdateExportBill(ExportBillheadbodyUpdateDto input)
        {
            ExportBillhead head = Mapper.Map<ExportBillhead>(input.head);
            var flag = Repository.GetAll().Where(x => x.Id != head.Id).Where(x => x.exphead_code == head.exphead_code).Any();
            if (flag)
                throw new UserFriendlyException("编码规则已存在！");
            Task<ExportBillheadDto> taskDto = base.Update(input.head);
            if (input.createList != null && input.createList.Count > 0 && !InsertBody(taskDto.Result, input.createList))
                throw new UserFriendlyException("新增批次信息失败！");
            if (input.updateList != null && input.updateList.Count > 0 && !UpdateBody(input.updateList))
                throw new UserFriendlyException("修改批次信息失败！");
            if (input.idList != null && input.idList.Count > 0 && !DeleteBody(input.idList))
                throw new UserFriendlyException("删除批次信息失败！");
            return taskDto;
        }

        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ExportBillManage_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
            var flag = Repository.GetAll().Where(x => x.Id == input.Id && x.exphead_execute_flag != ExecuteFlag.未执行).Any();
            if (flag)
            {
                throw new UserFriendlyException("数据状态异常，无法删除！");
            }  
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Delete", WMSOptLogInfo.WMSOptLogInfo.DELETE, input.Id.ToString(), "", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            await Repository.DeleteAsync(x => x.Id == input.Id);
        }

        /// <summary>
        /// 批量删除
        /// </summary>
        /// <param name="idList"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ExportBillManage_Delete)]
        public Task DelteList(List<Guid> idList)
        {
            var flag = Repository.GetAll().Where(x => x.Id.IsIn(idList.ToArray<Guid>())).Where(x => x.exphead_execute_flag != ExecuteFlag.未执行).Any();
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
        private bool InsertBody(ExportBillheadDto head, List<ExportBillbody.Dto.ExportBillbodyCreatedDto> createList) 
        {
            try
            {
                ExportBillbody.ExportBillbody body;
                foreach (ExportBillbody.Dto.ExportBillbodyCreatedDto bodyDto in createList)
                {
                    body = new ExportBillbody.ExportBillbody();
                    body.expbody_list_id = bodyDto.expbody_list_id;
                    body.expbody_external_listid = bodyDto.expbody_external_listid;
                    body.expbody_batch_no = bodyDto.expbody_batch_no;
                    body.expbody_lots_no = bodyDto.expbody_lots_no;
                    body.expbody_product_date = bodyDto.expbody_product_date;
                    body.expbody_product_lineid = bodyDto.expbody_product_lineid;
                    body.expbody_remark = bodyDto.expbody_remark;
                    body.expbody_vaildate_date = bodyDto.expbody_vaildate_date;
                    body.expbody_recheck_date = bodyDto.expbody_recheck_date;
                    body.expbody_plan_quantity = bodyDto.expbody_plan_quantity;
                    body.expbody_binding_quantity = bodyDto.expbody_binding_quantity;
                    body.expbody_fulfill_quantity = bodyDto.expbody_fulfill_quantity;
                    body.expbody_execute_flag = bodyDto.expbody_execute_flag;
                    body.expbody_audit_flag = bodyDto.expbody_audit_flag;
                    body.expbody_audit_uid = bodyDto.expbody_audit_uid;
                    body.expbody_audit_datetime = bodyDto.expbody_audit_datetime;
                    body.expbody_noused_flag = bodyDto.expbody_noused_flag;
                    body.expbody_noused_uid = bodyDto.expbody_noused_uid;
                    body.expbody_noused_datetime = bodyDto.expbody_noused_datetime;
                    body.expbody_upload_flag = bodyDto.expbody_upload_flag;
                    body.expbody_upload_datetime = bodyDto.expbody_upload_datetime;
                    body.expbody_is_enable = bodyDto.expbody_is_enable;
                    body.expbody_goods_id = bodyDto.expbody_goods_id;
                    body.expbody_bill_bar = head.exphead_code;
                    body.expbody_imphead_id = head.Id;
                    _ebRepository.Insert(body);
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
        private bool UpdateBody(List<ExportBillbody.Dto.ExportBillbodyUpdatedDto> updateList)
        {
            try
            {
                ExportBillbody.ExportBillbody body;
                foreach (ExportBillbody.Dto.ExportBillbodyUpdatedDto bodyDto in updateList)
                {
                    body = _ebRepository.FirstOrDefault(x => x.Id == bodyDto.Id);
                    body.expbody_list_id = bodyDto.expbody_list_id;
                    body.expbody_external_listid = bodyDto.expbody_external_listid;
                    body.expbody_batch_no = bodyDto.expbody_batch_no;
                    body.expbody_lots_no = bodyDto.expbody_lots_no;
                    body.expbody_product_date = bodyDto.expbody_product_date;
                    body.expbody_product_lineid = bodyDto.expbody_product_lineid;
                    body.expbody_remark = bodyDto.expbody_remark;
                    body.expbody_vaildate_date = bodyDto.expbody_vaildate_date;
                    body.expbody_recheck_date = bodyDto.expbody_recheck_date;
                    body.expbody_plan_quantity = bodyDto.expbody_plan_quantity;
                    body.expbody_binding_quantity = bodyDto.expbody_binding_quantity;
                    body.expbody_fulfill_quantity = bodyDto.expbody_fulfill_quantity;
                    body.expbody_execute_flag = bodyDto.expbody_execute_flag;
                    body.expbody_audit_flag = bodyDto.expbody_audit_flag;
                    body.expbody_audit_uid = bodyDto.expbody_audit_uid;
                    body.expbody_audit_datetime = bodyDto.expbody_audit_datetime;
                    body.expbody_noused_flag = bodyDto.expbody_noused_flag;
                    body.expbody_noused_uid = bodyDto.expbody_noused_uid;
                    body.expbody_noused_datetime = bodyDto.expbody_noused_datetime;
                    body.expbody_upload_flag = bodyDto.expbody_upload_flag;
                    body.expbody_upload_datetime = bodyDto.expbody_upload_datetime;
                    body.expbody_is_enable = bodyDto.expbody_is_enable;
                    body.expbody_goods_id = bodyDto.expbody_goods_id;
                    _ebRepository.Update(body);
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
                _ebRepository.DeleteAsync(x => x.Id.IsIn(idList.ToArray<Guid>()));
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        /// <summary>
        /// 设定批次
        /// </summary>
        /// <param name="idList"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ExportBillManage_Add)]
        public bool EditWaveNo(List<Guid> idList)
        {
            if (idList != null && idList.Count > 0)
            {
                List<ExportBillhead> inputList = Repository.GetAllIncluding().Where(x => x.Id.IsIn(idList.ToArray<Guid>())).ToList();
                if (inputList != null && inputList.Count > 0) {
                    EncodingRule.EncodingRuleService er = new EncodingRule.EncodingRuleService(_erRepository, Repository);
                    string erCode = er.GetEncodingRule("WaveCode");
                    if (erCode == null)
                        throw new UserFriendlyException("缺少编号为“WaveCode”的编码规则！");
                    foreach (ExportBillhead input in inputList)
                    {
                        input.exphead_wave_no = erCode;
                        Repository.Update(input);
                    }
                    return true;
                }
            }
            return false;
        }
    }
}
