using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.WarehouseStock
{
    /// <summary>
    /// 仓库库存
    /// </summary>
    public class WarehouseStock : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 库存数量
        /// </summary>
        public decimal warehouse_stock { get; set; }
        /// <summary>
        /// 库存日期
        /// </summary>
        public string warehouse_date { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid warehouse_id { get; set; }
        [ForeignKey("warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        #endregion
    }
}
