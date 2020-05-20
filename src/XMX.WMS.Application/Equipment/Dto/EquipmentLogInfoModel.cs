using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.Equipment.Dto
{
    #region 查询参数
    public class EquipmentLogInfoPagedRequest : PagedResultRequestDto
    {
        #region 查询参数
        /// <summary>
        /// 设备编码
        /// </summary>
        public string equipment_code { get; set; }
        /// <summary>
        /// 设备名称
        /// </summary>
        public string equipment_name { get; set; }
        /// <summary>
        /// 设备类型
        /// </summary>
        public EquipmentType? equipment_type { get; set; }
        /// <summary>
        /// 操作人
        /// </summary>
        public string opt_user_name { get; set; }
        /// <summary>
        /// 日志时间范围
        /// </summary>
        public string date_range { get; set; }
        #endregion
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(EquipmentLogInfo))]
    public class EquipmentLogInfoCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 设备编码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string equipment_code { get; set; }
        /// <summary>
        /// 设备名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string equipment_name { get; set; }
        /// <summary>
        /// 日志内容
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column200)]
        public string equipment_log_content { get; set; }
        /// <summary>
        /// 预留备注信息
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string equipment_log_remark { get; set; }
        /// <summary>
        /// 操作人
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string opt_user_name { get; set; }
        /// <summary>
        /// 设备类型
        /// </summary>
        [Required]
        public EquipmentType equipment_type { get; set; }
        /// <summary>
        /// 设备日志类型
        /// </summary>
        [Required]
        public EquipmentLogType equipment_log_type { get; set; }
        /// <summary>
        /// 设备执行状态
        /// </summary>
        [Required]
        public EquipmentExecutionState equipment_execution_state { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(EquipmentLogInfo))]
    public class EquipmentLogInfoUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 设备编码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string equipment_code { get; set; }
        /// <summary>
        /// 设备名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string equipment_name { get; set; }
        /// <summary>
        /// 日志内容
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column200)]
        public string equipment_log_content { get; set; }
        /// <summary>
        /// 预留备注信息
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string equipment_log_remark { get; set; }
        /// <summary>
        /// 操作人
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string opt_user_name { get; set; }
        /// <summary>
        /// 设备类型
        /// </summary>
        [Required]
        public EquipmentType equipment_type { get; set; }
        /// <summary>
        /// 设备日志类型
        /// </summary>
        [Required]
        public EquipmentLogType equipment_log_type { get; set; }
        /// <summary>
        /// 设备执行状态
        /// </summary>
        [Required]
        public EquipmentExecutionState equipment_execution_state { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(EquipmentLogInfo))]
    public class EquipmentLogInfoDto : EntityDto<Guid>
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
        /// <summary>
        ///创建时间
        /// </summary>
        public DateTime CreationTime { get; set; }
        #endregion
    }
    #endregion
}
