using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.ImportApplyLog
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-05-12 14:49:20
    /// 描 述：入库申请日志
    ///</summary>
    public class ImportApplyLog : FullAuditedEntity<Guid>
    {
        /// <summary>
        /// 入库单据id
        /// </summary>
        public Guid? import_id { get; set; }

        /// <summary>
        /// 入库信息
        /// </summary>
        public string import_info { get; set; }

        /// <summary>
        /// 入库单创建时间
        /// </summary>
        public DateTime import_creat_datetime { get; set; }

        /// <summary>
        /// 入库结果
        /// </summary>
        public string import_result { get; set; }
    }
}
