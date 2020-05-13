using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.Operation.Dto
{
    public class OperationLogInfoPagedRequest : PagedResultRequestDto
    {
        #region 属性
        /// <summary>
        /// 查询内容
        /// </summary>
        public string operation_search_content { get; set; }
        /// <summary>
        /// 操作类型
        /// </summary>
        public string operation_type_name { get; set; }
        /// <summary>
        /// 操作模块
        /// </summary>
        public string operation_module_name { get; set; }
        /// <summary>
        /// 操作日期范围
        /// </summary>
        public string operation_date_range { get; set; }
        #endregion
    }

    #region 创建CreateDto
    [AutoMapTo(typeof(OperationLogInfo))]
    public class OperationLogInfoCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 修改前数据
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string operation_modify_pre_data { get; set; }
        /// <summary>
        /// 修改后数据
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column200)]
        public string operation_modify_final_data { get; set; }
        /// <summary>
        /// 查询内容
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string operation_search_content { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string operation_remark { get; set; }
        /// <summary>
        /// 操作类型
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string operation_type_name { get; set; }
        /// <summary>
        /// 操作模块
        /// </summary>
        [StringLength(BaseVerification.column100)]
        public string operation_module_name { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(OperationLogInfo))]
    public class OperationLogInfoUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 修改前数据
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string operation_modify_pre_data { get; set; }
        /// <summary>
        /// 修改后数据
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column200)]
        public string operation_modify_final_data { get; set; }
        /// <summary>
        /// 查询内容
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string operation_search_content { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string operation_remark { get; set; }
        /// <summary>
        /// 操作类型
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string operation_type_name { get; set; }
        /// <summary>
        /// 操作模块
        /// </summary>
        [StringLength(BaseVerification.column100)]
        public string operation_module_name { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(OperationLogInfo))]
    public class OperationLogInfoDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 修改前数据
        /// </summary>
        public string operation_modify_pre_data { get; set; }
        /// <summary>
        /// 修改后数据
        /// </summary>
        public string operation_modify_final_data { get; set; }
        /// <summary>
        /// 查询内容
        /// </summary>
        public string operation_search_content { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string operation_remark { get; set; }
        /// <summary>
        /// 操作类型
        /// </summary>
        public string operation_type_name { get; set; }
        /// <summary>
        /// 操作模块
        /// </summary>
        public string operation_module_name { get; set; }
        #endregion
    }
    #endregion

    #region 查询WMSOptLogInfo输入Dto 
    public class WMSOptLogInfoDto:PagedResultRequestDto
    {
        /// <summary>
        /// 操作类型
        /// </summary>
        public string OptAction { get; set; }
        /// <summary>
        /// 操作模块
        /// </summary>
        public string OptModule { get; set; }
        /// <summary>
        /// 查询时间范围
        /// </summary>
        public string DateRange { get; set; }
        /// <summary>
        /// 查询内容
        /// </summary>
        public string Content { get; set; }
    }
    #endregion
}
