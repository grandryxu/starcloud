using Abp.Domain.Entities.Auditing;
using System;

namespace XMX.WMS.Fenbiao
{
    /// <summary>
    /// 货物
    /// </summary>
    public class Fenbiao : FullAuditedEntity<Guid>
    {
        /// <summary>
        /// 编码
        /// </summary>
        public string code { get; set; }

        public string name { get; set; }
    }
}
