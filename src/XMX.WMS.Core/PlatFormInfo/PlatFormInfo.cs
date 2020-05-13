using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.PlatFormInfo
{
    /// <summary>
    /// 月台信息
    /// </summary>
    public class PlatFormInfo : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 编码
        /// </summary>
        public string platform_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string platform_name { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string platform_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled platform_is_enable { get; set; }
        /// <summary>
        /// 月台状态
        /// </summary>
        public PlatformState platform_state { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? platform_warehouse_id { get; set; }
        [ForeignKey("platform_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        #endregion
    }
}
