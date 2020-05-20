using Abp.Application.Services;
using Abp.Domain.Repositories;
using Abp.Extensions;
using Abp.Linq.Extensions;
using Abp.UI;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using XMX.WMS.Authorization.Users;
using XMX.WMS.RGVTask.Dto;

namespace XMX.WMS.RGVTask
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-05-14 9:53:34
    /// 描 述：
    ///</summary>
    public class RGVTaskService : AsyncCrudAppService<RGVTask, RGVTaskDto, Guid, RGVTaskPagedRequest, RGVTaskCreatedDto, RGVTaskUpdatedDto>, IRGVTaskService
    {
        private readonly IRepository<EncodingRule.EncodingRule, Guid> _erRepository;
        private readonly IRepository<TaskMainInfo.TaskMainInfo, Guid> _taskRepository;
        private readonly IRepository<SlotInfo.SlotInfo, Guid> _sRepository;

        public RGVTaskService(IRepository<RGVTask, Guid> repository,
               IRepository<EncodingRule.EncodingRule, Guid> erRepository,
              IRepository<TaskMainInfo.TaskMainInfo, Guid> taskRepository,
              IRepository<SlotInfo.SlotInfo, Guid> sRepository) : base(repository)
        {
            _erRepository = erRepository;
            _taskRepository = taskRepository;
            _sRepository = sRepository;
        }



        protected override IQueryable<RGVTask> CreateFilteredQuery(RGVTaskPagedRequest input)
        {
            return Repository.GetAllIncluding(x => x.Port1, x => x.Slot1, x => x.Slot1.Warehouse)
                .WhereIf(!input.rgv_no.IsNullOrWhiteSpace(), x => x.rgv_no.Contains(input.rgv_no))
                .WhereIf(input.rgv_creat_datetime != null, x => x.CreationTime == input.rgv_creat_datetime)
                .WhereIf(input.rgv_manual_flag != null, x => x.rgv_manual_flag == input.rgv_manual_flag);
        }

        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override async Task<RGVTaskDto> Create(RGVTaskCreatedDto input)
        {
            var slot = _sRepository.GetAll()
                .WhereIf(input.warehouse_id != null && input.warehouse_id != Guid.Empty, x => x.slot_warehouse_id == input.warehouse_id)
                .WhereIf(!input.slot_code_code.IsNullOrWhiteSpace(), x => x.slot_code.Contains(input.slot_code_code)).FirstOrDefault();
            if (slot == null)
                throw new UserFriendlyException("当前库位不存在，请重新输入");
            input.rgv_slot_code = slot.Id;
            var flag = Repository.GetAll().Where(x => x.rgv_no == input.rgv_no).Any();
            if (flag)
            {
                EncodingRule.EncodingRuleService er = new EncodingRule.EncodingRuleService(_erRepository, _taskRepository);
                input.rgv_no = er.GetEncodingRule("TaskCode");
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
        public override async Task<RGVTaskDto> Update(RGVTaskUpdatedDto input)
        {
            var slot = _sRepository.GetAll()
                .WhereIf(input.warehouse_id != null && input.warehouse_id != Guid.Empty, x => x.slot_warehouse_id == input.warehouse_id)
                .WhereIf(!input.slot_code_code.IsNullOrWhiteSpace(), x => x.slot_code.Contains(input.slot_code_code)).FirstOrDefault();
            if (slot == null)
                throw new UserFriendlyException("当前库位不存在，请重新输入");
            input.rgv_slot_code = slot.Id;
            return await base.Update(input);

        }
    }
}
