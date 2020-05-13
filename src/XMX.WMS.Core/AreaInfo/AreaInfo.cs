using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.AreaInfo
{
    /// <summary>
    /// 库区信息
    /// </summary>
    public class AreaInfo : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 名称
        /// </summary>
        public string area_name { get; set; }
        /// <summary>
        /// 库区编码
        /// </summary>
        public string area_code { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled area_is_enable { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string area_remark { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid area_warehouse_id { get; set; }
        [ForeignKey("area_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        #endregion
    }
}
