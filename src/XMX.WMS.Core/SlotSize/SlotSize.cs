using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

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

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? size_company_id { get; set; }
        [ForeignKey("size_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? size_warehouse_id { get; set; }
        [ForeignKey("size_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        #endregion
    }
}
