using Abp.Domain.Entities.Auditing;
using System;

namespace XMX.WMS.Equipment
{
    public class EquipmentLogInfo : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 设备编码
        /// </summary>
        public string equipment_code { get; set; }
        /// <summary>
        /// 设备名称
        /// </summary>
        public string equipment_name { get; set; }
        /// <summary>
        /// 日志内容
        /// </summary>
        public string equipment_log_content { get; set; }
        /// <summary>
        /// 预留备注信息
        /// </summary>
        public string equipment_log_remark { get; set; }
        /// <summary>
        /// 操作人
        /// </summary>
        public string opt_user_name { get; set; }
        /// <summary>
        /// 设备类型
        /// </summary>
        public EquipmentType equipment_type { get; set; }
        /// <summary>
        /// 设备日志类型
        /// </summary>
        public EquipmentLogType equipment_log_type { get; set; }
        /// <summary>
        /// 设备执行状态
        /// </summary>
        public EquipmentExecutionState equipment_execution_state { get; set; }
        #endregion
    }
}
