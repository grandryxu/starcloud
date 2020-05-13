using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.ExportBillSyncLog
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-05-12 14:57:17
    /// 描 述：出库单据同步日志
    ///</summary>
    public class ExportBillSyncLog : FullAuditedEntity<Guid>
    {
        /// <summary>
        /// 出库单据id
        /// </summary>
        public Guid? expbill_id { get; set; }

        /// <summary>
        /// 出库单据信息
        /// </summary>
        public string expbill_info { get; set; }
        /// <summary>
        /// 出库单据创建时间
        /// </summary>
        public DateTime expbill_creat_datetime { get; set; }

        /// <summary>
        /// 出库结果
        /// </summary>
        public string expbill_result { get; set; }
    }
}
