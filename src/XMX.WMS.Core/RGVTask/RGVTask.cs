using Abp.Domain.Entities.Auditing;
using System;

namespace XMX.WMS.RGVTask
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-05-12 15:08:32
    /// 描 述：RGV任务管理
    ///</summary>
   public class RGVTask: FullAuditedEntity<Guid>
    {
        //所有字段复制老版本WMS，表名：task_rgv_info 具体含义不知。
        #region 属性
        public string rgv_id { get; set; }

        public int rgv_no { get; set; }

        public int rgv_priority { get; set; }

        public string rgv_port_id { get; set; }

        public string rgv_platform_id { get; set; }

        public string rgv_port_id2 { get; set; }

        public string rgv_stock_code { get; set; }

        public string rgv_malfunction { get; set; }

        public int rgv_execute_flag { get; set; }

        public int rgv_manual_flag { get; set; }

        public string rgv_creat_uid { get; set; }

        public DateTime rgv_creat_datetime { get; set; }

        public string rgv_modify_uid { get; set; }

        public string rgv_modify_datetime { get; set; }
        #endregion
    }
}
