using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.StockTasking.Dto
{
    #region 查询参数传入dto
    public class StockTaskingPagedRequest : PagedResultRequestDto
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
        public StockTaskingState? stock_state { get; set; }
        /// <summary>
        /// 盘点类型 1整库盘点 2动态盘点 3物资盘点
        /// </summary>
        public StockTaskingType? stock_type { get; set; }
        /// <summary>
        /// 单据日期
        /// </summary>
        public DateTime? start_date { get; set; }
        /// <summary>
        /// 单据日期
        /// </summary>
        public DateTime? end_date { get; set; }
        /// <summary>
        /// 物料信息
        /// </summary>
        public string goods_name { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(StockTasking))]
    public class StockTaskingCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 编号
        /// </summary>
        public string task_code { get; set; }
        /// <summary>
        /// 盘点类型 1整库盘点 2动态盘点 3物资盘点
        /// </summary>
        public StockTaskingType task_type { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public decimal task_count { get; set; }
        /// <summary>
        /// 盘点状态
        /// </summary>
        public StockTaskingState task_state { get; set; }
        /// <summary>
        /// 单据日期
        /// </summary>
        public DateTime? task_start_date { get; set; }
        /// <summary>
        /// 单据日期
        /// </summary>
        public DateTime? task_end_date { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string task_remark { get; set; }
        /// <summary>
        /// 最后盘点人ID
        /// </summary>
        public string task_operate_person { get; set; }
        /// <summary>
        /// 最后盘点时间
        /// </summary>
        public DateTime task_operate_time { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid task_warehouse_id { get; set; }
        /// <summary>
        /// 物料
        /// </summary>
        public virtual Guid? task_goods_id { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(StockTasking))]
    public class StockTaskingUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 编号
        /// </summary>
        public string task_code { get; set; }
        /// <summary>
        /// 盘点类型 1整库盘点 2动态盘点 3物资盘点
        /// </summary>
        public StockTaskingType task_type { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public decimal task_count { get; set; }
        /// <summary>
        /// 盘点状态
        /// </summary>
        public StockTaskingState task_state { get; set; }
        /// <summary>
        /// 单据日期
        /// </summary>
        public DateTime? task_start_date { get; set; }
        /// <summary>
        /// 单据日期
        /// </summary>
        public DateTime? task_end_date { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string task_remark { get; set; }
        /// <summary>
        /// 最后盘点人ID
        /// </summary>
        public string task_operate_person { get; set; }
        /// <summary>
        /// 最后盘点时间
        /// </summary>
        public DateTime task_operate_time { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid task_warehouse_id { get; set; }
        /// <summary>
        /// 物料
        /// </summary>
        public virtual Guid? task_goods_id { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(StockTasking))]
    public class StockTaskingDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 编号
        /// </summary>
        public string task_code { get; set; }
        /// <summary>
        /// 盘点类型 1整库盘点 2动态盘点 3物资盘点
        /// </summary>
        public StockTaskingType task_type { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public decimal task_count { get; set; }
        /// <summary>
        /// 盘点状态
        /// </summary>
        public StockTaskingState task_state { get; set; }
        /// <summary>
        /// 单据日期
        /// </summary>
        public DateTime? task_start_date { get; set; }
        /// <summary>
        /// 单据日期
        /// </summary>
        public DateTime? task_end_date { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string task_remark { get; set; }
        /// <summary>
        /// 最后盘点人ID
        /// </summary>
        public string task_operate_person { get; set; }
        /// <summary>
        /// 最后盘点时间
        /// </summary>
        public DateTime task_operate_time { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid task_warehouse_id { get; set; }
        [ForeignKey("task_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        /// <summary>
        /// 物料
        /// </summary>
        public virtual Guid? task_goods_id { get; set; }
        [ForeignKey("task_goods_id")]
        public virtual GoodsInfo.GoodsInfo Goods { get; set; }
        #endregion
    }
    #endregion

    #region 查询库存dto
    public class InventoryBatchDto : EntityDto<Guid>
    {
        /// <summary>
        /// 批号
        /// </summary>
        public string batch_no { get; set; }
        /// <summary>
        /// 物料
        /// </summary>
        public Guid goods_id { get; set; }

        public InventoryBatchDto(string batch_no, Guid goods_id)
        {
            this.batch_no = batch_no;
            this.goods_id = goods_id;
        }
    }
    #endregion
}
