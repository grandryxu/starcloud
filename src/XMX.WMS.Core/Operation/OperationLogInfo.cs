using Abp.Domain.Entities.Auditing;
using System;

namespace XMX.WMS.Operation
{
    /// <summary>
    /// 操作日志
    /// </summary>
    public class OperationLogInfo : FullAuditedEntity<Guid>
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
}
