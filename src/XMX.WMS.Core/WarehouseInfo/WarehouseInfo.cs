using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.WarehouseInfo
{
    /// <summary>
    /// 仓库信息
    /// </summary>
    public class WarehouseInfo : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 编码
        /// </summary>
        public string warehouse_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string warehouse_name { get; set; }
        /// <summary>
        /// 仓库类型(1立库；2平库；3密集库)
        /// </summary>
        public WarehouseType warehouse_type { get; set; }
        /// <summary>
        /// 库位类型(1层列排；2排列层)
        /// </summary>
        public SlotType warehouse_slot_type { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled warehouse_is_enable { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string warehouse_remark { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid warehouse_company_id { get; set; }
        [ForeignKey("warehouse_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        #endregion
    }
}
