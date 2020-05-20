using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;
namespace XMX.WMS.QualityCheckDetail.Dto
{
    ///<summary>
    /// 版 本：
    /// 创建人：lunan
    /// 日 期：2020-05-18 14:07:45
    /// 描 述：抽检明细
    ///</summary>

    #region 查询参数传入Dto

    public class QualityCheckDetailPagedRequest : PagedResultRequestDto
    {
        #region 属性
        /// <summary>
        /// 抽检单号
        /// </summary>
        public string check_code { get; set; }
        /// <summary>
        /// 大批号
        /// </summary>
        public string inventory_batch_no { get; set; }
        /// <summary>
        /// 小批号
        /// </summary>
        public string inventory_lots_no { get; set; }
        /// <summary>
        /// 生产日期
        /// </summary>
        public DateTime inventory_product_date { get; set; }
        /// <summary>
        /// 箱码
        /// </summary>
        public string inventory_box_code { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        public string inventory_stock_code { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public string inventory_slot_code { get; set; }
        #endregion
    }

    #endregion

    #region 创建CreateDto
    [AutoMap(typeof(QualityCheckDetail))]
    public class QualityCheckDetailCreateDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 大批号
        /// </summary>
        public string inventory_batch_no { get; set; }
        /// <summary>
        /// 小批号
        /// </summary>
        public string inventory_lots_no { get; set; }
        /// <summary>
        /// 箱码
        /// </summary>
        public string inventory_box_code { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public decimal inventory_quantity { get; set; }
        /// <summary>
        /// 托盘上物料抽检量
        /// </summary>
        public decimal check_quantity { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        public string inventory_stock_code { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 关联QualityCheck
        /// </summary>
        public virtual Guid quality_check_id { get; set; }
        [ForeignKey("quality_check_id")]
        public virtual QualityCheck.QualityCheck qualityCheck { get; set; }
        /// <summary>
        /// 物料代码
        /// </summary>
        public virtual Guid? inventory_goods_id { get; set; }
        [ForeignKey("inventory_goods_id")]
        public virtual GoodsInfo.GoodsInfo Goods { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid? inventory_slot_code { get; set; }
        [ForeignKey("inventory_slot_code")]
        public virtual SlotInfo.SlotInfo Slot { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMap(typeof(QualityCheckDetail))]
    public class QualityCheckDetailUpdateDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 大批号
        /// </summary>
        public string inventory_batch_no { get; set; }
        /// <summary>
        /// 小批号
        /// </summary>
        public string inventory_lots_no { get; set; }
        /// <summary>
        /// 箱码
        /// </summary>
        public string inventory_box_code { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public decimal inventory_quantity { get; set; }
        /// <summary>
        /// 托盘上物料抽检量
        /// </summary>
        public decimal check_quantity { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        public string inventory_stock_code { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 关联QualityCheck
        /// </summary>
        public virtual Guid quality_check_id { get; set; }
        [ForeignKey("quality_check_id")]
        public virtual QualityCheck.QualityCheck qualityCheck { get; set; }
        /// <summary>
        /// 物料代码
        /// </summary>
        public virtual Guid? inventory_goods_id { get; set; }
        [ForeignKey("inventory_goods_id")]
        public virtual GoodsInfo.GoodsInfo Goods { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid? inventory_slot_code { get; set; }
        [ForeignKey("inventory_slot_code")]
        public virtual SlotInfo.SlotInfo Slot { get; set; }
        #endregion
    }

    #endregion

    #region  查询输出Dto
    [AutoMapFrom(typeof(QualityCheckDetail))]
    public class QualityCheckDetailDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 大批号
        /// </summary>
        public string inventory_batch_no { get; set; }
        /// <summary>
        /// 小批号
        /// </summary>
        public string inventory_lots_no { get; set; }
        /// <summary>
        /// 箱码
        /// </summary>
        public string inventory_box_code { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public decimal inventory_quantity { get; set; }
        /// <summary>
        /// 托盘上物料抽检量
        /// </summary>
        public decimal check_quantity { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        public string inventory_stock_code { get; set; }
        /// <summary>
        ///创建时间
        /// </summary>
        public DateTime CreationTime { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 关联QualityCheck
        /// </summary>
        public virtual Guid quality_check_id { get; set; }
        [ForeignKey("quality_check_id")]
        public virtual QualityCheck.QualityCheck qualityCheck { get; set; }
        /// <summary>
        /// 物料代码
        /// </summary>
        public virtual Guid? inventory_goods_id { get; set; }
        [ForeignKey("inventory_goods_id")]
        public virtual GoodsInfo.GoodsInfo Goods { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid? inventory_slot_code { get; set; }
        [ForeignKey("inventory_slot_code")]
        public virtual SlotInfo.SlotInfo Slot { get; set; }
        #endregion
    }
    #endregion
}
