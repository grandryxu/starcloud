using System;
using System.Collections.Generic;
using System.Text;
using Abp.Domain.Entities.Auditing;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Abp.Domain.Entities;

namespace XMX.WMS.DepartmentInfo
{
    /// <summary>
    /// 部分实体类
    /// </summary>
    public class DepartmentInfo : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 部门编号
        /// </summary>
        public string DepartNo { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string Name { get; set; }
        /// <summary>
        /// 负责人
        /// </summary>
        public string ManagerName { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string Remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled IsEnable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 公司ID
        /// </summary>
        public virtual Guid CompanyId { get; set; }
        /// <summary>
        /// 公司
        /// </summary>
        [ForeignKey("CompanyId")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        // <summary>
        /// 上级部门ID
        /// </summary>
        public virtual Guid? DepartmentId { get; set; }
        /// <summary>
        /// 上级部门
        /// </summary>
        [ForeignKey("DepartmentId")]
        public virtual DepartmentInfo Department { get; set; }
        #endregion
    }
}
