using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.MoveModelMenu
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-04-29 14:21:45
    /// 描 述：
    ///</summary>
    public class MoveModelMenu : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// URL地址
        /// </summary>
        public string menu_url { get; set; }
        /// <summary>
        /// 类型
        /// </summary>
        public MenuType menu_type { get; set; }
        /// <summary>
        /// 顺序
        /// </summary>
        public int menu_order { get; set; }
        /// <summary>
        /// 功能名称
        /// </summary>
        public string menu_function_name { get; set; }
        /// <summary>
        /// 图标样式
        /// </summary>
        public string menu_icon { get; set; }
        /// <summary>
        /// 是否启用
        /// </summary>
        public WMSIsEnabled menu_is_enable { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string menu_remark { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 父节点
        /// </summary>
        public virtual Guid? menu_parent_id { get; set; }
        [ForeignKey("menu_parent_id")]
        public virtual MoveModelMenu modelMenu { get; set; }
        #endregion

    }
}
