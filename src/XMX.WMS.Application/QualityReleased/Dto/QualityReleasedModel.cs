using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.QualityReleased.Dto
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-03-19 11:14:24
    /// 描 述：
    ///</summary>
    #region 查询参数传入Dto
    public class QualityReleasedPagedRequest : PagedResultRequestDto
    {
        public string quare_stock_in { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(QualityReleased))]
    public class QualityReleasedCreateDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 物料编码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string quare_goods_code { get; set; }
        /// <summary>
        /// 物料名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string quare_goods_name { get; set; }
        /// <summary>
        /// 批次
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string quare_batch_no { get; set; }
        /// <summary>
        /// 入库单号
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string quare_stock_in_code { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 质量信息
        /// </summary>
        public virtual Guid? quare_quality_id { get; set; }
        [ForeignKey("quare_quality_id")]
        public virtual QualityInfo.QualityInfo quare_quality_info { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(QualityReleased))]
    public class QualityReleasedUpdateDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 物料编码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string quare_goods_code { get; set; }
        /// <summary>
        /// 物料名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string quare_goods_name { get; set; }
        /// <summary>
        /// 批次
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string quare_batch_no { get; set; }
        /// <summary>
        /// 入库单号
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string quare_stock_in_code { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 质量信息
        /// </summary>
        public virtual Guid? quare_quality_id { get; set; }
        [ForeignKey("quare_quality_id")]
        public virtual QualityInfo.QualityInfo quare_quality_info { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出Dto
    [AutoMapFrom(typeof(QualityReleased))]
    public class QualityReleasedDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 物料编码
        /// </summary>
        public string quare_goods_code { get; set; }
        /// <summary>
        /// 物料名称
        /// </summary>
        public string quare_goods_name { get; set; }
        /// <summary>
        /// 批次
        /// </summary>
        public string quare_batch_no { get; set; }
        /// <summary>
        /// 入库单号
        /// </summary>
        public string quare_stock_in_code { get; set; }
        /// <summary>
        ///创建时间
        /// </summary>
        public DateTime CreationTime { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 质量信息
        /// </summary>
        public virtual Guid? quare_quality_id { get; set; }
        [ForeignKey("quare_quality_id")]
        public virtual QualityInfo.QualityInfo quare_quality_info { get; set; }
        #endregion
    }
    #endregion

    #region 检测放行Inventory输入参数
    public class ReleasedInventoryRequestDto : PagedResultRequestDto
    {
        /// <summary>
        /// 单据条码
        /// </summary>
        public string inventory_bill_bar { get; set; }
        /// <summary>
        /// 大批号
        /// </summary>
        public string inventory_batch_no { get; set; }
        /// <summary>
        /// 小批号
        /// </summary>
        public string inventory_lots_no { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string goods_name { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string quality_name { get; set; }
        /// <summary>
        /// 库位id
        /// </summary>
        public Guid? inventory_slot_code { get; set; }

        /// <summary>
        /// 质量状态id
        /// </summary>
        public Guid? inventory_quality_status { get; set; }

    }
    #endregion
}
