using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.ImportBillbody.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using System.Collections.Generic;
using System.Threading.Tasks;
using Abp.Application.Services.Dto;
using Abp.Authorization;
using XMX.WMS.Authorization;
using XMX.WMS.Authorization.Users;
using Abp.UI;

namespace XMX.WMS.ImportBillbody
{
    [AbpAuthorize(PermissionNames.ImportBillBodyManage)]
    public class ImportBillbodyService : AsyncCrudAppService<ImportBillbody, ImportBillbodyDto, Guid, ImportBillbodyPagedRequest, ImportBillbodyCreatedDto, ImportBillbodyUpdatedDto>, IImportBillbodyService
    {
        private readonly IRepository<ImportOrder.ImportOrder, Guid> _ioRepository;
        private readonly IRepository<ImportBillhead.ImportBillhead, Guid> _ihRepository;
        private readonly UserManager _userManager;
        public ImportBillbodyService(IRepository<ImportBillbody, Guid> repository,
            IRepository<ImportOrder.ImportOrder, Guid> ioRepository,
            IRepository<ImportBillhead.ImportBillhead, Guid> ihRepository,
            UserManager userManager) : base(repository)
        {
            _ioRepository = ioRepository;
            _ihRepository = ihRepository;
            _userManager = userManager;
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.ImportBillBodyManage_Get)]
        protected override IQueryable<ImportBillbody> CreateFilteredQuery(ImportBillbodyPagedRequest input)
        {
            return Repository.GetAllIncluding(x => x.GoodsInfo, x => x.GoodsInfo.Unit)
                .WhereIf(!input.impbody_bill_bar.IsNullOrWhiteSpace(), x => x.impbody_bill_bar.Contains(input.impbody_bill_bar))
                .WhereIf(input.impbody_imphead_id.HasValue, x => x.impbody_imphead_id == input.impbody_imphead_id);
        }

        /// <summary>
        /// 重写排序方式
        /// </summary>
        /// <param name="query"></param>
        /// <param name="input"></param>
        /// <returns></returns>
        protected override IQueryable<ImportBillbody> ApplySorting(IQueryable<ImportBillbody> query, ImportBillbodyPagedRequest input)
        {
            return query.OrderBy(r => r.CreationTime); 
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ImportBillBodyManage_Get)]
        public override Task<ImportBillbodyDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ImportBillBodyManage_Add)]
        public override async Task<ImportBillbodyDto> Create(ImportBillbodyCreatedDto input)
        {
            //公司ID
            User loginuser = _userManager.GetUserByIdAsync(AbpSession.UserId.Value).Result;
            input.impbody_company_id = loginuser.CompanyId;
            return await base.Create(input);
        }

        /// <summary>
        /// 新增-主从
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ImportBillBodyManage_Add)]
        public Task<ImportBillbodyDto> CreateImportBody(ImportBillbodyorderCreatedDto input)
        {
            try
            {
                //公司ID
                User loginuser = _userManager.GetUserByIdAsync(AbpSession.UserId.Value).Result;
                input.body.impbody_company_id = loginuser.CompanyId;
                if (input.createList != null && input.createList.Count > 0)
                {
                    input.body.impbody_execute_flag = ExecuteFlag.执行中;
                    ImportBillhead.ImportBillhead head = _ihRepository.FirstOrDefault(x => x.Id == input.body.impbody_imphead_id);
                    if (head.imphead_execute_flag == ExecuteFlag.未执行)
                    {
                        head.imphead_execute_flag = ExecuteFlag.执行中;
                        _ihRepository.Update(head);
                    }
                }
                Task<ImportBillbodyDto> taskDto = base.Create(input.body);
                if (input.createList != null && input.createList.Count > 0 && !InsertOrder(taskDto.Result, input.createList))
                    throw new UserFriendlyException("新增流水信息失败！");
                return taskDto;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                throw new UserFriendlyException("修改信息失败！");
            }
        }

        /// <summary>
        /// 编辑
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ImportBillBodyManage_Update)]
        public override async Task<ImportBillbodyDto> Update(ImportBillbodyUpdatedDto input)
        {
            return await base.Update(input);
        }

        /// <summary>
        /// 编辑-主从
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ImportBillBodyManage_Update)]
        public Task<ImportBillbodyDto> UpdateImportBody(ImportBillbodyorderUpdatedDto input)
        {
            if (input.createList != null && input.createList.Count > 0)
            {
                if (input.body.impbody_execute_flag == ExecuteFlag.未执行)
                    input.body.impbody_execute_flag = ExecuteFlag.执行中;
                ImportBillhead.ImportBillhead head = _ihRepository.FirstOrDefault(x => x.Id == input.body.impbody_imphead_id);
                if (head.imphead_execute_flag == ExecuteFlag.未执行)
                {
                    head.imphead_execute_flag = ExecuteFlag.执行中;
                    _ihRepository.Update(head);
                }
            }
            else
            {
                if (input.updateList == null || input.updateList.Count == 0)
                {
                    //body下的其他流水都删除了
                    var orderFlag = _ioRepository.GetAllIncluding().Where(x => x.imporder_body_id == input.body.Id)
                                                                   .Where(x => x.imporder_noused_flag == NousedFlag.正常).Any();
                    if (!orderFlag)
                    {
                        //无正常的流水，修改体状态
                        ImportBillbody body = Repository.FirstOrDefault(x => x.Id == input.body.Id);
                        body.impbody_execute_flag = ExecuteFlag.未执行;
                        Repository.Update(body);
                        var bodyFlag = Repository.GetAllIncluding().Where(x => x.impbody_imphead_id == body.impbody_imphead_id)
                                                                   .Where(x => x.impbody_execute_flag != ExecuteFlag.未执行).Any();
                        if (!bodyFlag)
                        {
                            //无执行中的体，修改头状态
                            ImportBillhead.ImportBillhead head = _ihRepository.FirstOrDefault(x => x.Id == body.impbody_imphead_id);
                            head.imphead_execute_flag = ExecuteFlag.未执行;
                            _ihRepository.Update(head);
                        }
                    }
                }
            }
            Task<ImportBillbodyDto> taskDto = base.Update(input.body);
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
        [AbpAuthorize(PermissionNames.ImportBillBodyManage_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
            var flag = Repository.GetAll().Where(x => x.Id == input.Id).Where(x => x.impbody_execute_flag != ExecuteFlag.未执行).Any();
            if (flag)
                throw new UserFriendlyException("数据状态异常，无法删除！");
            await Repository.DeleteAsync(x => x.Id == input.Id);
        }

        /// <summary>
        /// 批量删除
        /// </summary>
        /// <param name="idList"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ImportBillBodyManage_Delete)]
        public Task DelteList(List<Guid> idList)
        {
            var flag = Repository.GetAll().Where(x => x.Id.IsIn(idList.ToArray<Guid>())).Where(x => x.impbody_execute_flag != ExecuteFlag.未执行).Any();
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
        private bool InsertOrder(ImportBillbodyDto body, List<ImportOrder.Dto.ImportOrderCreatedDto> createList)
        {
            try
            {
                ImportOrder.ImportOrder order;
                foreach (ImportOrder.Dto.ImportOrderCreatedDto orderDto in createList)
                {
                    order = new ImportOrder.ImportOrder();
                    order.imporder_batch_no = orderDto.imporder_batch_no;
                    order.imporder_lots_no = orderDto.imporder_lots_no;
                    order.imporder_product_date = orderDto.imporder_product_date;
                    order.imporder_product_lineid = orderDto.imporder_product_lineid;
                    order.imporder_remark = orderDto.imporder_remark;
                    order.imporder_vaildate_date = orderDto.imporder_vaildate_date;
                    order.imporder_recheck_date = orderDto.imporder_recheck_date;
                    order.imporder_stock_status = orderDto.imporder_stock_status;
                    order.imporder_quantity = orderDto.imporder_quantity;
                    order.imporder_box_code = orderDto.imporder_box_code;
                    order.imporder_stock_code = orderDto.imporder_stock_code;
                    order.imporder_execute_flag = orderDto.imporder_execute_flag;
                    order.imporder_noused_flag = orderDto.imporder_noused_flag;
                    order.imporder_noused_uid = orderDto.imporder_noused_uid;
                    order.imporder_noused_datetime = orderDto.imporder_noused_datetime;
                    order.imporder_upload_flag = orderDto.imporder_upload_flag;
                    order.imporder_upload_datetime = orderDto.imporder_upload_datetime;
                    order.imporder_is_enable = orderDto.imporder_is_enable;
                    order.imporder_stock_flag = orderDto.imporder_stock_flag;
                    order.imporder_goods_id = orderDto.imporder_goods_id;
                    order.imporder_quality_status = orderDto.imporder_quality_status;
                    order.imporder_slot_code = orderDto.imporder_slot_code;
                    order.imporder_warehouse_id = orderDto.imporder_warehouse_id;
                    order.imporder_port_id = orderDto.imporder_port_id;
                    order.imporder_body_id = orderDto.imporder_body_id;
                    order.imporder_company_id = body.impbody_company_id;
                    order.imporder_bill_bar = body.impbody_bill_bar;
                    order.imporder_body_id = body.Id;
                    _ioRepository.Insert(order);
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
        private bool UpdateOrder(List<ImportOrder.Dto.ImportOrderUpdatedDto> updateList)
        {
            try
            {
                ImportOrder.ImportOrder order;
                foreach (ImportOrder.Dto.ImportOrderUpdatedDto orderDto in updateList)
                {
                    order = _ioRepository.FirstOrDefault(x => x.Id == orderDto.Id);
                    order.imporder_batch_no = orderDto.imporder_batch_no;
                    order.imporder_lots_no = orderDto.imporder_lots_no;
                    order.imporder_product_date = orderDto.imporder_product_date;
                    order.imporder_product_lineid = orderDto.imporder_product_lineid;
                    order.imporder_remark = orderDto.imporder_remark;
                    order.imporder_vaildate_date = orderDto.imporder_vaildate_date;
                    order.imporder_recheck_date = orderDto.imporder_recheck_date;
                    order.imporder_stock_status = orderDto.imporder_stock_status;
                    order.imporder_quantity = orderDto.imporder_quantity;
                    order.imporder_box_code = orderDto.imporder_box_code;
                    order.imporder_stock_code = orderDto.imporder_stock_code;
                    order.imporder_execute_flag = orderDto.imporder_execute_flag;
                    order.imporder_noused_flag = orderDto.imporder_noused_flag;
                    order.imporder_noused_uid = orderDto.imporder_noused_uid;
                    order.imporder_noused_datetime = orderDto.imporder_noused_datetime;
                    order.imporder_upload_flag = orderDto.imporder_upload_flag;
                    order.imporder_upload_datetime = orderDto.imporder_upload_datetime;
                    order.imporder_is_enable = orderDto.imporder_is_enable;
                    order.imporder_stock_flag = orderDto.imporder_stock_flag;
                    order.imporder_goods_id = orderDto.imporder_goods_id;
                    order.imporder_quality_status = orderDto.imporder_quality_status;
                    order.imporder_slot_code = orderDto.imporder_slot_code;
                    order.imporder_warehouse_id = orderDto.imporder_warehouse_id;
                    order.imporder_port_id = orderDto.imporder_port_id;
                    order.imporder_body_id = orderDto.imporder_body_id;
                    _ioRepository.Update(order);
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
                _ioRepository.DeleteAsync(x => x.Id.IsIn(idList.ToArray<Guid>()));
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
        [AbpAuthorize(PermissionNames.ImportBillBodyManage_Add)]
        public async Task<ListResultDto<ImportBillbodyDto>> CreateList(List<ImportBillbodyCreatedDto> inputList)
        {
            List<ImportBillbodyDto> list = new List<ImportBillbodyDto>();
            foreach (ImportBillbodyCreatedDto input in inputList) 
            {
                list.Add(await base.Create(input));
            }
            return new ListResultDto<ImportBillbodyDto>(list);
        }

        /// <summary>
        /// 批量修改
        /// </summary>
        /// <param name="inputList"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ImportBillBodyManage_Update)]
        public async Task<ListResultDto<ImportBillbodyDto>> UpdateList(List<ImportBillbodyUpdatedDto> inputList)
        {
            List<ImportBillbodyDto> list = new List<ImportBillbodyDto>();
            foreach (ImportBillbodyUpdatedDto input in inputList)
            {
                list.Add(await base.Update(input));
            }
            return new ListResultDto<ImportBillbodyDto>(list);
        }
    }
}
