using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.ExportBillbody.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using Abp.Application.Services.Dto;
using System.Collections.Generic;
using System.Threading.Tasks;
using Abp.UI;
using Abp.Authorization;
using XMX.WMS.Authorization;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using XMX.WMS.WMSOptLogInfo;
using Newtonsoft.Json;
using XMX.WMS.Base.Session;

namespace XMX.WMS.ExportBillbody
{
    [AbpAuthorize(PermissionNames.ExportBillBodyManage)]
    public class ExportBillbodyService : AsyncCrudAppService<ExportBillbody, ExportBillbodyDto, Guid, ExportBillbodyPagedRequest, ExportBillbodyCreatedDto, ExportBillbodyUpdatedDto>, IExportBillbodyService
    {
        private readonly IRepository<ExportOrder.ExportOrder, Guid> _eoRepository;
        private readonly Guid UserCompanyId;
        //日志
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public ExportBillbodyService(IRepository<ExportBillbody, Guid> repository,
                                     IRepository<ExportOrder.ExportOrder, Guid> eoRepository) : base(repository)
        {
            _eoRepository = eoRepository;
            UserCompanyId = AbpSession.GetCompanyId();
            LogContext = DynamicDbContext.GetInstance(string.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                CompanyId = UserCompanyId,
                OptPath = "XMX.WMS.ExportBillbody.ExportBillbodyService.",
                OptModule = "出库流水管理"
            };
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.ExportBillBodyManage_Get)]
        protected override IQueryable<ExportBillbody> CreateFilteredQuery(ExportBillbodyPagedRequest input)
        {
            return Repository.GetAll()
                .WhereIf(AbpSession.UserId != 1, x => x.expbody_company_id == UserCompanyId)
                .WhereIf(!input.expbody_bill_bar.IsNullOrWhiteSpace(), x => x.expbody_bill_bar.Contains(input.expbody_bill_bar))
                .WhereIf(input.expbody_head_id.HasValue, x => x.expbody_head_id == input.expbody_head_id)
                ;
        }

        /// <summary>
        /// 重写排序方式
        /// </summary>
        /// <param name="query"></param>
        /// <param name="input"></param>
        /// <returns></returns>
        protected override IQueryable<ExportBillbody> ApplySorting(IQueryable<ExportBillbody> query, ExportBillbodyPagedRequest input)
        {
            return query.OrderBy(r => r.CreationTime);
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ExportBillBodyManage_Get)]
        public override Task<ExportBillbodyDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ExportBillBodyManage_Add)]
        public override async Task<ExportBillbodyDto> Create(ExportBillbodyCreatedDto input)
        {
            input.expbody_company_id = UserCompanyId;
            ExportBillbodyDto dto = await base.Create(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Create", WMSOptLogInfo.WMSOptLogInfo.ADD, "", JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 新增-主从
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ExportBillBodyManage_Add)]
        public Task<ExportBillbodyDto> CreateExportBody(ExportBillbodyorderCreatedDto input)
        {
            Task<ExportBillbodyDto> taskDto = base.Create(input.body);
            if (input.createList != null && input.createList.Count > 0 && !InsertOrder(taskDto.Result, input.createList))
                throw new UserFriendlyException("新增流水信息失败！");
            return taskDto;
        }

        /// <summary>
        /// 编辑
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ExportBillBodyManage_Update)]
        public override async Task<ExportBillbodyDto> Update(ExportBillbodyUpdatedDto input)
        {
            ExportBillbody oldEntity = Repository.Get(input.Id);
            string oldval = JsonConvert.SerializeObject(oldEntity);
            ExportBillbodyDto dto = await base.Update(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, oldval, JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 编辑-主从
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ExportBillBodyManage_Update)]
        public Task<ExportBillbodyDto> UpdateExportBody(ExportBillbodyorderUpdateDto input)
        {
            Task<ExportBillbodyDto> taskDto = base.Update(input.body);
            if (input.createList != null && input.createList.Count > 0 && !InsertOrder(taskDto.Result, input.createList))
                throw new UserFriendlyException("新增流水信息失败！");
            if (input.updateList != null && input.updateList.Count > 0 && !UpdateOrder(input.updateList))
                throw new UserFriendlyException("修改流水信息失败！");
            if (input.idList != null && input.idList.Count > 0 && !DeleteOrder(input.idList))
                throw new UserFriendlyException("删除流水信息失败！");
            return taskDto;
        }

        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ExportBillBodyManage_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
            var flag = Repository.GetAll().Where(x => x.Id == input.Id).Where(x => x.expbody_execute_flag != ExecuteFlag.未执行).Any();
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
        [AbpAuthorize(PermissionNames.ExportBillBodyManage_Delete)]
        public Task DelteList(List<Guid> idList)
        {
            var flag = Repository.GetAll().Where(x => x.Id.IsIn(idList.ToArray<Guid>())).Where(x => x.expbody_execute_flag != ExecuteFlag.未执行).Any();
            if (flag)
                throw new UserFriendlyException("数据占用，无法删除！");
            if (null == idList)
                throw new UserFriendlyException("数据状态异常，请联系管理员！");
            return Repository.DeleteAsync(x => x.Id.IsIn(idList.ToArray<Guid>()));
        }

        /// <summary>
        /// 新增从表
        /// </summary>
        /// <param name="body"></param>
        /// <param name="createList"></param>
        private bool InsertOrder(ExportBillbodyDto body, List<ExportOrder.Dto.ExportOrderCreatedDto> createList)
        {
            try
            {
                ExportOrder.ExportOrder order;
                foreach (ExportOrder.Dto.ExportOrderCreatedDto orderDto in createList)
                {
                    order = new ExportOrder.ExportOrder();
                    order.exporder_batch_no = orderDto.exporder_batch_no;
                    order.exporder_lots_no = orderDto.exporder_lots_no;
                    order.exporder_product_date = orderDto.exporder_product_date;
                    order.exporder_product_lineid = orderDto.exporder_product_lineid;
                    order.exporder_remark = orderDto.exporder_remark;
                    order.exporder_vaildate_date = orderDto.exporder_vaildate_date;
                    order.exporder_recheck_date = orderDto.exporder_recheck_date;
                    order.exporder_quantity = orderDto.exporder_quantity;
                    order.exporder_box_code = orderDto.exporder_box_code;
                    order.exporder_stock_code = orderDto.exporder_stock_code;
                    order.exporder_execute_flag = orderDto.exporder_execute_flag;
                    order.exporder_noused_flag = orderDto.exporder_noused_flag;
                    order.exporder_noused_uid = orderDto.exporder_noused_uid;
                    order.exporder_noused_datetime = orderDto.exporder_noused_datetime;
                    order.exporder_upload_flag = orderDto.exporder_upload_flag;
                    order.exporder_upload_datetime = orderDto.exporder_upload_datetime;
                    order.exporder_is_enable = orderDto.exporder_is_enable;
                    order.exporder_slot_code = orderDto.exporder_slot_code;
                    order.exporder_port_id = orderDto.exporder_port_id;
                    order.exporder_platform_id = orderDto.exporder_platform_id;
                    order.exporder_bill_bar = body.expbody_bill_bar;
                    order.exporder_company_id = body.expbody_company_id;
                    order.exporder_goods_id = body.expbody_goods_id;
                    order.exporder_quality_status = body.expbody_quality_status;
                    order.exporder_body_id = body.Id;
                    _eoRepository.Insert(order);
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
        private bool UpdateOrder(List<ExportOrder.Dto.ExportOrderUpdatedDto> updateList)
        {
            try
            {
                ExportOrder.ExportOrder order;
                foreach (ExportOrder.Dto.ExportOrderUpdatedDto orderDto in updateList)
                {
                    order = _eoRepository.Get(orderDto.Id);
                    order.exporder_batch_no = orderDto.exporder_batch_no;
                    order.exporder_lots_no = orderDto.exporder_lots_no;
                    order.exporder_product_date = orderDto.exporder_product_date;
                    order.exporder_product_lineid = orderDto.exporder_product_lineid;
                    order.exporder_remark = orderDto.exporder_remark;
                    order.exporder_vaildate_date = orderDto.exporder_vaildate_date;
                    order.exporder_recheck_date = orderDto.exporder_recheck_date;
                    order.exporder_quantity = orderDto.exporder_quantity;
                    order.exporder_box_code = orderDto.exporder_box_code;
                    order.exporder_stock_code = orderDto.exporder_stock_code;
                    order.exporder_execute_flag = orderDto.exporder_execute_flag;
                    order.exporder_noused_flag = orderDto.exporder_noused_flag;
                    order.exporder_noused_uid = orderDto.exporder_noused_uid;
                    order.exporder_noused_datetime = orderDto.exporder_noused_datetime;
                    order.exporder_upload_flag = orderDto.exporder_upload_flag;
                    order.exporder_upload_datetime = orderDto.exporder_upload_datetime;
                    order.exporder_is_enable = orderDto.exporder_is_enable;
                    order.exporder_slot_code = orderDto.exporder_slot_code;
                    order.exporder_port_id = orderDto.exporder_port_id;
                    order.exporder_platform_id = orderDto.exporder_platform_id;
                    _eoRepository.Update(order);
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
        private bool DeleteOrder(List<Guid> idList)
        {
            try
            {
                _eoRepository.DeleteAsync(x => x.Id.IsIn(idList.ToArray<Guid>()));
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        /// <summary>
        /// 批量新增
        /// </summary>
        /// <param name="inputList"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ExportBillBodyManage_Add)]
        public async Task<ListResultDto<ExportBillbodyDto>> CreateList(List<ExportBillbodyCreatedDto> inputList)
        {
            List<ExportBillbodyDto> list = new List<ExportBillbodyDto>();
            foreach (ExportBillbodyCreatedDto input in inputList)
            {
                list.Add(await base.Create(input));
            }
            return new ListResultDto<ExportBillbodyDto>(list);
        }

        /// <summary>
        /// 批量修改
        /// </summary>
        /// <param name="inputList"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ExportBillBodyManage_Update)]
        public async Task<ListResultDto<ExportBillbodyDto>> UpdateList(List<ExportBillbodyUpdatedDto> inputList)
        {
            List<ExportBillbodyDto> list = new List<ExportBillbodyDto>();
            foreach (ExportBillbodyUpdatedDto input in inputList)
            {
                list.Add(await base.Update(input));
            }
            return new ListResultDto<ExportBillbodyDto>(list);
        }
    }
}
