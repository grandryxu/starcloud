using Abp.Domain.Entities.Auditing;
using System;

namespace XMX.WMS.SlotSize
{
    /// <summary>
    /// 货物
    /// </summary>
    public class SlotSize : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 名称
        /// </summary>
        public string size_name { get; set; }
        /// <summary>
        /// 长度
        /// </summary>
        public decimal size_length { get; set; }
        /// <summary>
        /// 高度
        /// </summary>
        public decimal size_height { get; set; }
        /// <summary>
        /// 宽度
        /// </summary>
        public decimal size_width { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string size_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled size_is_enable { get; set; }
        #endregion
    }
}
