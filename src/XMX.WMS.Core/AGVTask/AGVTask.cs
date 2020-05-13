using Abp.Domain.Entities.Auditing;
using System;

namespace XMX.WMS.AGVTask
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-05-12 15:17:58
    /// 描 述：AGV任务管理
    ///</summary>
    public class AGVTask : FullAuditedEntity<Guid>
    {
        //所有字段复制老版本WMS，表名：task_agv_info 具体含义不知。
        #region 属性
        public string agv_id { get; set; }

        public int agv_no { get; set; }

        public int agv_priority { get; set; }

        public string agv_port_id { get; set; }

        public string agv_platform_id { get; set; }

        public string agv_port_id2 { get; set; }

        public string agv_stock_code { get; set; }

        public string agv_malfunction { get; set; }

        public int agv_execute_flag { get; set; }

        public int agv_manual_flag { get; set; }

        public string agv_creat_uid { get; set; }

        public DateTime agv_creat_datetime { get; set; }

        public string agv_modify_uid { get; set; }

        public string agv_modify_datetime { get; set; }
        #endregion
    }
}
