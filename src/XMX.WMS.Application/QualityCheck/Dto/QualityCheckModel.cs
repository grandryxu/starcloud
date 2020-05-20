using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.QualityCheck.Dto
{
    ///<summary>
    /// 版 本：
    /// 创建人：lunan
    /// 日 期：2020-05-18 14:23:53
    /// 描 述：抽检
    ///</summary>
    #region 查询参数传入Dto
    public class QualityCheckPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 抽检单据
        /// </summary>
        public Guid? check_id { get; set; }
        /// <summary>
        /// 抽检单据
        /// </summary>
        public string check_code { get; set; }
        /// <summary>
        /// 单据类型
        /// </summary>
        public CheckType check_type { get; set; }
        /// <summary>
        /// 物料名称
        /// </summary>
        public string check_goods_name { get; set; }
        /// <summary>
        /// 批次
        /// </summary>
        public string check_batch_no { get; set; }
        /// <summary>
        /// 单据号
        /// </summary>
        public string check_bill_bar { get; set; }
        /// <summary>
        /// 原质量状态
        /// </summary>
        public Guid? check_origin_quality { get; set; }
        /// <summary>
        /// 检测后质量状态
        /// </summary>
        public Guid? check_checked_quality { get; set; }
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
        public virtual Guid? check_warehouse_id { get; set; }
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
        public string check_code { get; set; }
        /// <summary>
        /// 单据类型
        /// </summary>
        public CheckType check_type { get; set; }
        /// <summary>
        /// 批次
        /// </summary>
        public string check_batch_no { get; set; }
        /// <summary>
        /// 单据号
        /// </summary>
        public string check_bill_bar { get; set; }
        /// <summary>
        /// 检测日期
        /// </summary>
        public DateTime check_time { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string check_remark { get; set; }
        /// <summary>
        /// 抽检量
        /// </summary>
        public decimal check_num { get; set; }
        /// <summary>
        /// 是否生成出库单 0 未生成 ；1 已生成
        /// </summary>
        public CheckBillStatus check_bill_status { get; set; }
        /// <summary>
        /// 是否已经检测放行 0 未放行；1 已放行
        /// </summary>
        public CheckReleasedStatus check_released_status { get; set; }
        /// <summary>
        /// 生成出库单的单号
        /// </summary>
        public string check_export_bill { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 公司
        /// </summary>
        public virtual Guid? check_company_id { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public virtual Guid? check_warehouse_id { get; set; }
        /// <summary>
        /// 物料
        /// </summary>
        public virtual Guid? check_goods_id { get; set; }
        /// <summary>
        /// 原质量状态
        /// </summary>
        public virtual Guid? check_origin_quality { get; set; }
        /// <summary>
        /// 检测后质量状态
        /// </summary>
        public virtual Guid? check_checked_quality { get; set; }
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
        public string check_code { get; set; }
        /// <summary>
        /// 单据类型
        /// </summary>
        public CheckType check_type { get; set; }
        /// <summary>
        /// 批次
        /// </summary>
        public string check_batch_no { get; set; }
        /// <summary>
        /// 单据号
        /// </summary>
        public string check_bill_bar { get; set; }
        /// <summary>
        /// 检测日期
        /// </summary>
        public DateTime check_time { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string check_remark { get; set; }
        /// <summary>
        /// 抽检量
        /// </summary>
        public decimal check_num { get; set; }
        /// <summary>
        /// 是否生成出库单 0 未生成 ；1 已生成
        /// </summary>
        public CheckBillStatus check_bill_status { get; set; }
        /// <summary>
        /// 是否已经检测放行 0 未放行；1 已放行
        /// </summary>
        public CheckReleasedStatus check_released_status { get; set; }
        /// <summary>
        /// 生成出库单的单号
        /// </summary>
        public string check_export_bill { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 公司
        /// </summary>
        public virtual Guid? check_company_id { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public virtual Guid? check_warehouse_id { get; set; }
        /// <summary>
        /// 物料
        /// </summary>
        public virtual Guid? check_goods_id { get; set; }
        /// <summary>
        /// 原质量状态
        /// </summary>
        public virtual Guid? check_origin_quality { get; set; }
        /// <summary>
        /// 检测后质量状态
        /// </summary>
        public virtual Guid? check_checked_quality { get; set; }
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
        public string check_code { get; set; }
        /// <summary>
        /// 单据类型
        /// </summary>
        public CheckType check_type { get; set; }
        /// <summary>
        /// 批次
        /// </summary>
        public string check_batch_no { get; set; }
        /// <summary>
        /// 单据号
        /// </summary>
        public string check_bill_bar { get; set; }
        /// <summary>
        /// 检测日期
        /// </summary>
        public DateTime check_time { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string check_remark { get; set; }
        /// <summary>
        /// 抽检量
        /// </summary>
        public decimal check_num { get; set; }
        /// <summary>
        /// 是否生成出库单 0 未生成 ；1 已生成
        /// </summary>
        public CheckBillStatus check_bill_status { get; set; }
        /// <summary>
        /// 是否已经检测放行 0 未放行；1 已放行
        /// </summary>
        public CheckReleasedStatus check_released_status { get; set; }
        /// <summary>
        /// 生成出库单的单号
        /// </summary>
        public string check_export_bill { get; set; }
        /// <summary>
        ///创建时间
        /// </summary>
        public DateTime CreationTime { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 公司
        /// </summary>
        public virtual Guid? check_company_id { get; set; }
        [ForeignKey("check_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public virtual Guid? check_warehouse_id { get; set; }
        [ForeignKey("check_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        /// <summary>
        /// 物料
        /// </summary>
        public virtual Guid? check_goods_id { get; set; }
        [ForeignKey("check_goods_id")]
        public virtual GoodsInfo.GoodsInfo Goods { get; set; }
        /// <summary>
        /// 原质量状态
        /// </summary>
        public virtual Guid? check_origin_quality { get; set; }
        [ForeignKey("check_origin_quality")]
        public virtual QualityInfo.QualityInfo Quality1 { get; set; }
        /// <summary>
        /// 检测后质量状态
        /// </summary>
        public virtual Guid? check_checked_quality { get; set; }
        [ForeignKey("check_checked_quality")]
        public virtual QualityInfo.QualityInfo Quality2 { get; set; }
        #endregion

        public string check_quality_name 
        {
            get 
            {
                if (Quality2 != null)
                    return Quality2.quality_name;
                return check_quality_name;
            }
            set 
            {
                if (Quality2 != null)
                    check_quality_name = Quality2.quality_name;
            }
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
