using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.ReportTemp
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-05-18 14:07:23
    /// 描 述：自定义报表模板类
    ///</summary>
    public class ReportTemp : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 模板id，字符串类型
        /// </summary>
        public string TempId { get; set; }
        /// <summary>
        /// 报表名称
        /// </summary>
        public string FullName { get; set; }
        /// <summary>
        ///报表编号
        /// </summary>
        public string EnCode { get; set; }
        /// <summary>
        /// 报表分类
        /// </summary>
        public string TempCategory { get; set; }
        /// <summary>
        /// 报表风格  
        /// </summary>
        public string TempStyle { get; set; }
        /// <summary>
        /// 图表类型
        /// </summary>
        public string TempType { get; set; }
        /// <summary>
        /// 报表介绍
        /// </summary>
        public string Description { get; set; }
        /// <summary>
        /// 报表参数Json
        /// </summary>
        public string ParamJson { get; set; }
        /// <summary>
        /// 排序码
        /// </summary>
        public int? SortCode { get; set; }
        /// <summary>
        /// 有效标志
        /// </summary>
        public int? EnabledMark { get; set; }
        #endregion
    }
}
