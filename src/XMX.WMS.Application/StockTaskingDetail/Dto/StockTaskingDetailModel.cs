using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.StockTaskingDetail.Dto
{
    #region 查询参数传入dto
    public class StockTaskingDetailPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 编号
        /// </summary>
        public string stock_code { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public Guid? warehouse_id { get; set; }
        /// <summary>
        /// 状态
        /// </summary>
        public string stock_state { get; set; }
        /// <summary>
        /// 盘点类型 1整库盘点 2动态盘点 3物资盘点
        /// </summary>
        public StockTaskingType? stock_type { get; set; }
        /// <summary>
        /// 单据日期
        /// </summary>
        public DateTime? startDate { get; set; }
        /// <summary>
        /// 单据日期
        /// </summary>
        public DateTime? endDate { get; set; }
        /// <summary>
        /// 物料信息
        /// </summary>
        public string goods_name { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(StockTaskingDetail))]
    public class StockTaskingDetailCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 盘点类型（1初始化2已盘点）
        /// </summary>
        public StockTaskingDetailState task_state { get; set; }
        /// <summary>
        /// 库存数量
        /// </summary>
        public decimal task_count { get; set; }
        /// <summary>
        /// 盘盈数量
        /// </summary>
        public decimal task_acount { get; set; }
        /// <summary>
        /// 盘亏数量
        /// </summary>
        public decimal task_dcount { get; set; }
        /// <summary>
        /// 最后盘点人ID
        /// </summary>
        public string task_operate_person { get; set; }
        /// <summary>
        /// 最后盘点时间
        /// </summary>
        public DateTime task_operate_time { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        public string task_stock_code { get; set; }
        /// <summary>
        /// 批号
        /// </summary>
        public string task_batch_no { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属盘点单
        /// </summary>
        public virtual Guid stock_tasking_id { get; set; }
        /// <summary>
        /// 物料
        /// </summary>
        public virtual Guid task_goods_id { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid task_slot_id { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(StockTaskingDetail))]
    public class StockTaskingDetailUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 盘点类型（1初始化2已盘点）
        /// </summary>
        public StockTaskingDetailState task_state { get; set; }
        /// <summary>
        /// 库存数量
        /// </summary>
        public decimal task_count { get; set; }
        /// <summary>
        /// 盘盈数量
        /// </summary>
        public decimal task_acount { get; set; }
        /// <summary>
        /// 盘亏数量
        /// </summary>
        public decimal task_dcount { get; set; }
        /// <summary>
        /// 最后盘点人ID
        /// </summary>
        public string task_operate_person { get; set; }
        /// <summary>
        /// 最后盘点时间
        /// </summary>
        public DateTime task_operate_time { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        public string task_stock_code { get; set; }
        /// <summary>
        /// 批号
        /// </summary>
        public string task_batch_no { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属盘点单
        /// </summary>
        public virtual Guid stock_tasking_id { get; set; }
        /// <summary>
        /// 物料
        /// </summary>
        public virtual Guid task_goods_id { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid task_slot_id { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(StockTaskingDetail))]
    public class StockTaskingDetailDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 盘点类型（1初始化2已盘点）
        /// </summary>
        public StockTaskingDetailState task_state { get; set; }
        /// <summary>
        /// 库存数量
        /// </summary>
        public decimal task_count { get; set; }
        /// <summary>
        /// 盘盈数量
        /// </summary>
        public decimal task_acount { get; set; }
        /// <summary>
        /// 盘亏数量
        /// </summary>
        public decimal task_dcount { get; set; }
        /// <summary>
        /// 最后盘点人ID
        /// </summary>
        public string task_operate_person { get; set; }
        /// <summary>
        /// 最后盘点时间
        /// </summary>
        public DateTime task_operate_time { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        public string task_stock_code { get; set; }
        /// <summary>
        /// 批号
        /// </summary>
        public string task_batch_no { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属盘点单
        /// </summary>
        public virtual Guid stock_tasking_id { get; set; }
        [ForeignKey("stock_tasking_id")]
        public virtual StockTasking.StockTasking StockTasking { get; set; }
        /// <summary>
        /// 物料
        /// </summary>
        public virtual Guid task_goods_id { get; set; }
        [ForeignKey("task_goods_id")]
        public virtual GoodsInfo.GoodsInfo Goods { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid task_slot_id { get; set; }
        [ForeignKey("task_slot_id")]
        public virtual SlotInfo.SlotInfo Slot { get; set; }
        #endregion
    }
    #endregion
}
