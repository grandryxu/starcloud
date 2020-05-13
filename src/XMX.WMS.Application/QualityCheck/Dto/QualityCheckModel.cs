using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.QualityCheck.Dto
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-03-20 14:23:53
    /// 描 述：
    ///</summary>
    #region 查询参数传入Dto
    public class QualityCheckPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 抽检单据
        /// </summary>
        public string check_bill { get; set; }
        /// <summary>
        /// 单据类型
        /// </summary>
        public string bill_type { get; set; }
        /// <summary>
        /// 物料名称
        /// </summary>
        public string check_goods_name { get; set; }
        /// <summary>
        /// 批次
        /// </summary>
        public string check_batch_no { get; set; }
        /// <summary>
        /// 原质量状态
        /// </summary>
        public string origin_quality_status { get; set; }
        /// <summary>
        /// 检测后质量状态
        /// </summary>
        public string checked_quality_status { get; set; }
        /// <summary>
        /// 检测后质量状态id
        /// </summary>
        public Guid checked_quality_status_id { get; set; }
        /// <summary>
        /// 检测日期
        /// </summary>
        public DateTime check_time { get; set; }
        /// <summary>
        /// 页面查询起始时间
        /// </summary>
        public string check_time_b { get; set; }
        /// <summary>
        /// 页面查询截止时间
        /// </summary>
        public string check_time_e { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public virtual Guid? inventory_warehouse_id { get; set; }
    }

    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(QualityCheck))]
    public class QualityCheckCreateDto : BaseCreateDto
    {
        #region  属性
        /// <summary>
        /// 抽检单据
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string check_bill { get; set; }
        /// <summary>
        /// 单据类型
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string bill_type { get; set; }
        /// <summary>
        /// 物料编码
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string check_goods_code { get; set; }
        /// <summary>
        /// 物料名称
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string check_goods_name { get; set; }
        /// <summary>
        /// 批次
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string check_batch_no { get; set; }
        /// <summary>
        /// 原质量状态
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string origin_quality_status { get; set; }
        /// <summary>
        /// 检测后质量状态
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string checked_quality_status { get; set; }
        /// <summary>
        /// 检测后质量状态id
        /// </summary>
        public Guid checked_quality_status_id { get; set; }
        /// <summary>
        /// 检测日期
        /// </summary>
        public DateTime check_time { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string remark { get; set; }
        /// <summary>
        /// 入库量
        /// </summary>
        [Required]
        public decimal stock_num { get; set; }
        /// <summary>
        /// 抽检量
        /// </summary>
        public decimal check_num { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 库存信息
        /// </summary>
        public virtual Guid? check_inventory_id { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public virtual Guid? inventory_warehouse_id { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(QualityCheck))]
    public class QualityCheckUpdateDto : BaseUpdateDto
    {
        #region  属性
        /// <summary>
        /// 抽检单据
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string check_bill { get; set; }
        /// <summary>
        /// 单据类型
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string bill_type { get; set; }
        /// <summary>
        /// 物料编码
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string check_goods_code { get; set; }
        /// <summary>
        /// 物料名称
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string check_goods_name { get; set; }
        /// <summary>
        /// 批次
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string check_batch_no { get; set; }
        /// <summary>
        /// 原质量状态
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string origin_quality_status { get; set; }
        /// <summary>
        /// 检测后质量状态
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string checked_quality_status { get; set; }
        /// <summary>
        /// 检测后质量状态id
        /// </summary>
        public Guid checked_quality_status_id { get; set; }
        /// <summary>
        /// 检测日期
        /// </summary>
        public DateTime check_time { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string remark { get; set; }
        /// <summary>
        /// 入库量
        /// </summary>
        [Required]
        public decimal stock_num { get; set; }
        /// <summary>
        /// 抽检量
        /// </summary>
        public decimal check_num { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 库存信息
        /// </summary>
        public virtual Guid? check_inventory_id { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public virtual Guid? inventory_warehouse_id { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出Dto
    [AutoMapFrom(typeof(QualityCheck))]
    public class QualityCheckDto : EntityDto<Guid>
    {
        #region  属性
        /// <summary>
        /// 抽检单据
        /// </summary>
        public string check_bill { get; set; }
        /// <summary>
        /// 单据类型
        /// </summary>
        public string bill_type { get; set; }
        /// <summary>
        /// 物料编码
        /// </summary>
        public string check_goods_code { get; set; }
        /// <summary>
        /// 物料名称
        /// </summary>
        public string check_goods_name { get; set; }
        /// <summary>
        /// 批次
        /// </summary>
        public string check_batch_no { get; set; }
        /// <summary>
        /// 原质量状态
        /// </summary>
        public string origin_quality_status { get; set; }
        /// <summary>
        /// 检测后质量状态
        /// </summary>
        public string checked_quality_status { get; set; }
        /// <summary>
        /// 检测后质量状态id
        /// </summary>
        public Guid checked_quality_status_id { get; set; }
        /// <summary>
        /// 检测日期
        /// </summary>
        public DateTime check_time { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string remark { get; set; }
        /// <summary>
        /// 入库量
        /// </summary>
        public decimal stock_num { get; set; }
        /// <summary>
        /// 抽检量
        /// </summary>
        public decimal check_num { get; set; }
        /// <summary>
        /// 是否生成出库单 0 未生成 ；1 生成
        /// </summary>
        public int exist_out_bill { get; set; }
        /// <summary>
        /// 生成出库单的单号
        /// </summary>
        public string quality_check_export_code { get; set; }
        /// <summary>
        /// 是否已经检测放行 0 未放行；1 已经放行
        /// </summary>
        public int check_released { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 库存信息
        /// </summary>
        public virtual Guid? check_inventory_id { get; set; }
        [ForeignKey("check_inventory_id")]
        public virtual InventoryInfo.InventoryInfo check_inventory_info { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public virtual Guid? inventory_warehouse_id { get; set; }
        [ForeignKey("inventory_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        #endregion
    }
    #endregion

    #region 查询inventory 输出批次，物料Dto
    public class BatchGoodsDto
    {
        #region 属性
        /// <summary>
        /// 批次号
        /// </summary>
        public string inventory_batch_no { get; set; }
        /// <summary>
        /// 物料号
        /// </summary>
        public string goods_code { get; set; }
        /// <summary>
        /// 物料名
        /// </summary>
        public string goods_name { get; set; }
        /// <summary>
        /// 物料的原质量状态
        /// </summary>
        public string quality_status { get; set; }
        /// <summary>
        /// 质量状态对应的id
        /// </summary>
        public Guid? quality_status_id { get; set; }
        #endregion
        public BatchGoodsDto(string inventory_batch_no, string goods_code, string goods_name, string quality_status, Guid quality_status_id)
        {
            this.inventory_batch_no = inventory_batch_no;
            this.goods_code = goods_code;
            this.goods_name = goods_name;
            this.quality_status = quality_status;
            this.quality_status_id = quality_status_id;
        }
    }
    #endregion

    #region inventory 查询参数
    public class InventoryQueryParam
    {
        #region 属性
        /// <summary>
        /// 库存批次号
        /// </summary>
        public string inventory_batch_no { get; set; }
        /// <summary>
        /// 检测后质量状态id
        /// </summary>
        public Guid checked_quality_status_id { get; set; }
        #endregion
    }
    #endregion
}
