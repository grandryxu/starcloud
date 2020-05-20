using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.ExportBillbody.Dto
{
    #region 查询参数传入dto
    public class ExportBillbodyPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 单据条码
        /// </summary>
        public string expbody_bill_bar { get; set; }
        /// <summary>
        /// 表头ID
        /// </summary>
        public Guid? expbody_head_id { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(ExportBillbody))]
    public class ExportBillbodyCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 序号
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string expbody_list_id { get; set; }
        /// <summary>
        /// 外部系统序号
        /// </summary>
        [StringLength(BaseVerification.column60)]
        public string expbody_external_listid { get; set; }
        /// <summary>
        /// 大批次
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string expbody_batch_no { get; set; }
        /// <summary>
        /// 小批次
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string expbody_lots_no { get; set; }
        /// <summary>
        /// 生产日期
        /// </summary>
        public DateTime expbody_product_date { get; set; }
        /// <summary>
        /// 生产线
        /// </summary>
        [StringLength(BaseVerification.column60)]
        public string expbody_product_lineid { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string expbody_remark { get; set; }
        /// <summary>
        /// 单据条码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string expbody_bill_bar { get; set; }
        /// <summary>
        /// 失效日期
        /// </summary>
        public DateTime expbody_vaildate_date { get; set; }
        /// <summary>
        /// 复检日期
        /// </summary>
        public DateTime expbody_recheck_date { get; set; }
        /// <summary>
        /// 计划数量
        /// </summary>
        [Required]
        public decimal expbody_plan_quantity { get; set; }
        /// <summary>
        /// 绑定数量
        /// </summary>
        public decimal expbody_binding_quantity { get; set; }
        /// <summary>
        /// 完成数量
        /// </summary>
        public decimal expbody_fulfill_quantity { get; set; }
        /// <summary>
        /// 执行标志(1未执行；2执行中；3已完成)
        /// </summary>
        [Required]
        public ExecuteFlag expbody_execute_flag { get; set; }
        /// <summary>
        /// 审核标志(1未审核；2已审核)
        /// </summary>
        public AuditFlag expbody_audit_flag { get; set; }
        /// <summary>
        /// 审核人
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string expbody_audit_uid { get; set; }
        /// <summary>
        /// 审核时间
        /// </summary>
        public DateTime expbody_audit_datetime { get; set; }
        /// <summary>
        /// 作废标志(1未作废；2已作废)
        /// </summary>
        [Required]
        public NousedFlag expbody_noused_flag { get; set; }
        /// <summary>
        /// 作废人
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string expbody_noused_uid { get; set; }
        /// <summary>
        /// 作废时间
        /// </summary>
        public DateTime expbody_noused_datetime { get; set; }
        /// <summary>
        /// 上传标志(1未上传；2已上传)
        /// </summary>
        public UploadFlag expbody_upload_flag { get; set; }
        /// <summary>
        /// 上传时间
        /// </summary>
        public DateTime expbody_upload_datetime { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled expbody_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? expbody_company_id { get; set; }
        /// <summary>
        /// 表头ID
        /// </summary>
        public virtual Guid? expbody_head_id { get; set; }
        /// <summary>
        /// 物料ID
        /// </summary>
        public virtual Guid? expbody_goods_id { get; set; }
        /// <summary>
        /// 质量状态
        /// </summary>
        public virtual Guid? expbody_quality_status { get; set; }
        #endregion
    }
    #endregion

    #region 创建CreateDto主从
    public class ExportBillbodyorderCreatedDto
    {
        public ExportBillbodyCreatedDto body { get; set; }
        public List<ExportOrder.Dto.ExportOrderCreatedDto> createList { get; set; }
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(ExportBillbody))]
    public class ExportBillbodyUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 序号
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string expbody_list_id { get; set; }
        /// <summary>
        /// 外部系统序号
        /// </summary>
        [StringLength(BaseVerification.column60)]
        public string expbody_external_listid { get; set; }
        /// <summary>
        /// 大批次
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string expbody_batch_no { get; set; }
        /// <summary>
        /// 小批次
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string expbody_lots_no { get; set; }
        /// <summary>
        /// 生产日期
        /// </summary>
        public DateTime expbody_product_date { get; set; }
        /// <summary>
        /// 生产线
        /// </summary>
        [StringLength(BaseVerification.column60)]
        public string expbody_product_lineid { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string expbody_remark { get; set; }
        /// <summary>
        /// 单据条码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string expbody_bill_bar { get; set; }
        /// <summary>
        /// 失效日期
        /// </summary>
        public DateTime expbody_vaildate_date { get; set; }
        /// <summary>
        /// 复检日期
        /// </summary>
        public DateTime expbody_recheck_date { get; set; }
        /// <summary>
        /// 计划数量
        /// </summary>
        [Required]
        public decimal expbody_plan_quantity { get; set; }
        /// <summary>
        /// 绑定数量
        /// </summary>
        public decimal expbody_binding_quantity { get; set; }
        /// <summary>
        /// 完成数量
        /// </summary>
        public decimal expbody_fulfill_quantity { get; set; }
        /// <summary>
        /// 执行标志(1未执行；2执行中；3已完成)
        /// </summary>
        [Required]
        public ExecuteFlag expbody_execute_flag { get; set; }
        /// <summary>
        /// 审核标志(1未审核；2已审核)
        /// </summary>
        public AuditFlag expbody_audit_flag { get; set; }
        /// <summary>
        /// 审核人
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string expbody_audit_uid { get; set; }
        /// <summary>
        /// 审核时间
        /// </summary>
        public DateTime expbody_audit_datetime { get; set; }
        /// <summary>
        /// 作废标志(1未作废；2已作废)
        /// </summary>
        [Required]
        public NousedFlag expbody_noused_flag { get; set; }
        /// <summary>
        /// 作废人
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string expbody_noused_uid { get; set; }
        /// <summary>
        /// 作废时间
        /// </summary>
        public DateTime expbody_noused_datetime { get; set; }
        /// <summary>
        /// 上传标志(1未上传；2已上传)
        /// </summary>
        public UploadFlag expbody_upload_flag { get; set; }
        /// <summary>
        /// 上传时间
        /// </summary>
        public DateTime expbody_upload_datetime { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled expbody_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? expbody_company_id { get; set; }
        /// <summary>
        /// 表头ID
        /// </summary>
        public virtual Guid? expbody_head_id { get; set; }
        /// <summary>
        /// 物料ID
        /// </summary>
        public virtual Guid? expbody_goods_id { get; set; }
        /// <summary>
        /// 质量状态
        /// </summary>
        public virtual Guid? expbody_quality_status { get; set; }
        #endregion
    }
    #endregion

    #region 创建UpdateDto主从
    public class ExportBillbodyorderUpdateDto
    {
        public ExportBillbodyUpdatedDto body { get; set; }
        public List<ExportOrder.Dto.ExportOrderCreatedDto> createList { get; set; }
        public List<ExportOrder.Dto.ExportOrderUpdatedDto> updateList { get; set; }
        public List<Guid> idList;
    }
    #endregion

    #region 批量ListDto
    public class ExportBillbodySaveListDto
    {
        public List<ExportBillbodyCreatedDto> createList;
        public List<ExportBillbodyUpdatedDto> updateList;
        public List<Guid> idList;
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(ExportBillbody))]
    public class ExportBillbodyDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 序号
        /// </summary>
        public string expbody_list_id { get; set; }
        /// <summary>
        /// 外部系统序号
        /// </summary>
        public string expbody_external_listid { get; set; }
        /// <summary>
        /// 大批次
        /// </summary>
        public string expbody_batch_no { get; set; }
        /// <summary>
        /// 小批次
        /// </summary>
        public string expbody_lots_no { get; set; }
        /// <summary>
        /// 生产日期
        /// </summary>
        public DateTime expbody_product_date { get; set; }
        /// <summary>
        /// 生产线
        /// </summary>
        public string expbody_product_lineid { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string expbody_remark { get; set; }
        /// <summary>
        /// 单据条码
        /// </summary>
        public string expbody_bill_bar { get; set; }
        /// <summary>
        /// 失效日期
        /// </summary>
        public DateTime expbody_vaildate_date { get; set; }
        /// <summary>
        /// 复检日期
        /// </summary>
        public DateTime expbody_recheck_date { get; set; }
        /// <summary>
        /// 计划数量
        /// </summary>
        public decimal expbody_plan_quantity { get; set; }
        /// <summary>
        /// 绑定数量
        /// </summary>
        public decimal expbody_binding_quantity { get; set; }
        /// <summary>
        /// 完成数量
        /// </summary>
        public decimal expbody_fulfill_quantity { get; set; }
        /// <summary>
        /// 执行标志(1未执行；2执行中；3已完成)
        /// </summary>
        public ExecuteFlag expbody_execute_flag { get; set; }
        /// <summary>
        /// 审核标志(1未审核；2已审核)
        /// </summary>
        public AuditFlag expbody_audit_flag { get; set; }
        /// <summary>
        /// 审核人
        /// </summary>
        public string expbody_audit_uid { get; set; }
        /// <summary>
        /// 审核时间
        /// </summary>
        public DateTime expbody_audit_datetime { get; set; }
        /// <summary>
        /// 作废标志(1未作废；2已作废)
        /// </summary>
        public NousedFlag expbody_noused_flag { get; set; }
        /// <summary>
        /// 作废人
        /// </summary>
        public string expbody_noused_uid { get; set; }
        /// <summary>
        /// 作废时间
        /// </summary>
        public DateTime expbody_noused_datetime { get; set; }
        /// <summary>
        /// 上传标志(1未上传；2已上传)
        /// </summary>
        public UploadFlag expbody_upload_flag { get; set; }
        /// <summary>
        /// 上传时间
        /// </summary>
        public DateTime expbody_upload_datetime { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled expbody_is_enable { get; set; }
        /// <summary>
        ///创建时间
        /// </summary>
        public DateTime CreationTime { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? expbody_company_id { get; set; }
        [ForeignKey("expbody_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        /// <summary>
        /// 表头ID
        /// </summary>
        public virtual Guid? expbody_head_id { get; set; }
        [ForeignKey("expbody_head_id")]
        public virtual ExportBillhead.ExportBillhead ExportBillhead { get; set; }
        /// <summary>
        /// 物料ID
        /// </summary>
        public virtual Guid? expbody_goods_id { get; set; }
        [ForeignKey("expbody_goods_id")]
        public virtual GoodsInfo.GoodsInfo Goods { get; set; }
        /// <summary>
        /// 质量状态
        /// </summary>
        public virtual Guid? expbody_quality_status { get; set; }
        [ForeignKey("expbody_quality_status")]
        public virtual QualityInfo.QualityInfo Quality { get; set; }
        #endregion
    }
    #endregion
}
