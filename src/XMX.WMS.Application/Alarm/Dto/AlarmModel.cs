using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;


namespace XMX.WMS.Alarm.Dto
{
    #region 查询参数传入dto
    public class AlarmPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 物料名称
        /// </summary>
        public string goods_name { get; set; }
        /// <summary>
        /// 批次
        /// </summary>
        public string batch_no { get; set; }

        /// <summary>
        /// 类型（1.呆滞库存报警 2.效期库存报警 3.库存阈值报警）
        /// </summary>
        [Required]
        public WarningType type { get; set; }

    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(Alarm))]
    public class AlarmCreatedDto : BaseCreateDto
    {
       
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(Alarm))]
    public class AlarmUpdatedDto : BaseUpdateDto
    {
       
    }
    #endregion

    #region 查询输出dto  
    public class AlarmDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 报警名称
        /// </summary>
        public string alarm_name { get; set; }
        /// <summary>
        /// 报警值
        /// </summary>
        public int alarm_value { get; set; }
        /// <summary>
        /// 单据条码
        /// </summary>
        public string impbody_bill_bar { get; set; }
        /// <summary>
        /// 阈值
        /// </summary>
        public int thresholdz_value { get; set; }
        /// <summary>
        /// 报警时间
        /// </summary>
        public DateTime CreationTime { get; set; }
        /// <summary>
        /// 大批号
        /// </summary>
        public string inventory_batch_no { get; set; }
        /// <summary>
        /// 物料名称
        /// </summary>
        public string goods_name { get; set; }
        /// <summary>
        /// 物料编码
        /// </summary>
        public string goods_code { get; set; }
        /// <summary>
        /// 托盘码
        /// </summary>
        public string stock_code { get; set; }
        /// <summary>
        /// 库位码
        /// </summary>
        public string slot_code { get; set; }
        #endregion     
    }
    #endregion
}
