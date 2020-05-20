using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.AGVTask.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using System.Threading.Tasks;
using Abp.UI;
using XMX.WMS.Localization;

namespace XMX.WMS.AGVTask
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-05-14 11:09:51
    /// 描 述：
    ///</summary>
    public class AGVTaskService : AsyncCrudAppService<AGVTask, AGVTaskDto, Guid, AGVTaskPagedRequest, AGVTaskCreatedDto, AGVTaskUpdatedDto>, IAGVTaskService
    {
        private readonly IRepository<EncodingRule.EncodingRule, Guid> _erRepository;
        private readonly IRepository<TaskMainInfo.TaskMainInfo, Guid> _taskRepository;
        private readonly IRepository<SlotInfo.SlotInfo, Guid> _sRepository;
        public AGVTaskService(IRepository<AGVTask, Guid> repository,
              IRepository<EncodingRule.EncodingRule, Guid> erRepository,
              IRepository<TaskMainInfo.TaskMainInfo, Guid> taskRepository,
               IRepository<SlotInfo.SlotInfo, Guid> sRepository) : base(repository)
        {
            _erRepository = erRepository;
            _taskRepository = taskRepository;
            _sRepository = sRepository;
        }

        protected override IQueryable<AGVTask> CreateFilteredQuery(AGVTaskPagedRequest input)
        {
            return Repository.GetAllIncluding(x => x.Port1, x => x.Slot1, x => x.Slot1.Warehouse)
                .WhereIf(!input.agv_no.IsNullOrWhiteSpace(), x => x.agv_no == input.agv_no)
                .WhereIf(input.agv_creat_datetime != null, x => x.CreationTime == input.agv_creat_datetime)
                .WhereIf(input.agv_manual_flag!=null,x=>x.agv_manual_flag==input.agv_manual_flag);
        }


        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override async Task<AGVTaskDto> Create(AGVTaskCreatedDto input)
        {
            var slot = _sRepository.GetAll()
                .WhereIf(input.warehouse_id != null && input.warehouse_id != Guid.Empty, x => x.slot_warehouse_id == input.warehouse_id)
                .WhereIf(!input.slot_code_code.IsNullOrWhiteSpace(), x => x.slot_code.Contains(input.slot_code_code)).FirstOrDefault();
            if (slot == null)
                throw new UserFriendlyException(WMSLocalizationConfigurer.L("AGVTask_NoSlot"));
            input.agv_slot_code = slot.Id;
            var flag = Repository.GetAll().Where(x => x.agv_no == input.agv_no).Any();
            if (flag)
            {
                EncodingRule.EncodingRuleService er = new EncodingRule.EncodingRuleService(_erRepository, _taskRepository);
                input.agv_no = er.GetEncodingRule("TaskCode");
            }
            try
            {
                return await base.Create(input);
            }
            catch (Exception e)
            {
                string cc = e.Message;
                throw new UserFriendlyException("保存异常");
            }

        }

        /// <summary>
        /// 更新
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override async Task<AGVTaskDto> Update(AGVTaskUpdatedDto input)
        {
            var slot = _sRepository.GetAll()
                .WhereIf(input.warehouse_id != null && input.warehouse_id != Guid.Empty, x => x.slot_warehouse_id == input.warehouse_id)
                .WhereIf(!input.slot_code_code.IsNullOrWhiteSpace(), x => x.slot_code.Contains(input.slot_code_code)).FirstOrDefault();
            if (slot == null)
                throw new UserFriendlyException(WMSLocalizationConfigurer.L("AGVTask_NoSlot"));
            input.agv_slot_code = slot.Id;
            return await base.Update(input);
        }
    }
}
