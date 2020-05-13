using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.FlatBankTask
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-05-12 15:22:29
    /// 描 述：平库任务管理
    ///</summary>
    public class FlatBankTask : FullAuditedEntity<Guid>
    {
        //所有字段复制老版本WMS，表名：task_flat_info 具体含义不知。
        #region 属性
        public string flat_id { get; set; }
        public int flat_no { get; set; }    
        public int flat_priority { get; set; }
        public int flat_mode { get; set; }
        public string flat_platform_id { get; set; }
        public string flat_slot_code { get; set; }
        public string flat_stock_code { get; set; }
        public int flat_execute_flag { get; set; }
        public int flat_manual_flag { get; set; }
        public string flat_creat_uid { get; set; }
        public DateTime flat_creat_datetime { get; set; }
        public string flat_modify_uid { get; set; }
        public string flat_modify_datetime { get; set; }

        #endregion
    }
}
