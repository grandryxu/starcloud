using System;
using System.Collections.Generic;
using System.Text;
using Abp.Domain.Entities.Auditing;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace XMX.WMS.WMSOptLogInfo
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-05-07 9:24:30
    /// 描 述：操作日志
    ///</summary>
    public class WMSOptLogInfo : FullAuditedEntity<long>
    {

        public const string ADD = "新增", GET = "查询", UPDATE = "更新", DELETE = "删除";
        public const string SUCCESS = "成功", FAIL = "失败";
        #region 属性
        /// <summary>
        /// 主键id
        /// </summary>
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public override long Id { get; set; }
        /// <summary>
        /// 操作者id
        /// 父类 CreatorUserId
        /// </summary> 

        /// <summary>
        /// 操作时间
        /// 父类 CreationTime
        /// </summary> 

        /// <summary>
        /// 操作行为
        /// </summary>
        public string OptAction { get; set; }
        /// <summary>
        /// 操作结果
        /// </summary>
        public string OptResult { get; set; }
        /// <summary>
        /// 操作路径
        /// </summary>
        public string OptPath { get; set; }
        /// <summary>
        /// 旧值
        /// </summary>
        public string OldVal { get; set; }
        /// <summary>
        /// 新值
        /// </summary>
        public string NewVal { get; set; }
        /// <summary>
        /// 操作模块
        /// </summary>
        public string OptModule { get; set; }
        #endregion
    }
}
