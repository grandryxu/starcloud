﻿using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.ImportBillbody.Dto
{
    #region 查询参数传入dto
    public class ImportBillbodyPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 单据条码
        /// </summary>
        public string impbody_bill_bar { get; set; }
        /// <summary>
        /// 表头ID
        /// </summary>
        public Guid? impbody_imphead_id { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(ImportBillbody))]
    public class ImportBillbodyCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 流水号
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string impbody_list_id { get; set; }
        /// <summary>
        /// 外部系统序号
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string impbody_external_listid { get; set; }
        /// <summary>
        /// 大批次
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string impbody_batch_no { get; set; }
        /// <summary>
        /// 小批次
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string impbody_lots_no { get; set; }
        /// <summary>
        /// 生产日期
        /// </summary>
        public DateTime impbody_product_date { get; set; }
        /// <summary>
        /// 生产线
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string impbody_product_lineid { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string impbody_stock_code { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string impbody_remark { get; set; }
        /// <summary>
        /// 单据条码
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string impbody_bill_bar { get; set; }
        /// <summary>
        /// 失效日期
        /// </summary>
        public DateTime impbody_vaildate_date { get; set; }
        /// <summary>
        /// 复检日期
        /// </summary>
        public DateTime imbody_recheck_date { get; set; }
        /// <summary>
        /// 计划数量
        /// </summary>
        [Required]
        public int impbody_plan_quantity { get; set; }
        /// <summary>
        /// 绑定数量
        /// </summary>
        public int impbody_binding_quantity { get; set; }
        /// <summary>
        /// 完成数量
        /// </summary>
        public int impbody_fulfill_quantity { get; set; }
        /// <summary>
        /// 执行标志(1未执行；2执行中；3已完成)
        /// </summary>
        [Required]
        public ExecuteFlag impbody_execute_flag { get; set; }
        /// <summary>
        /// 审核标志(1未审核；2已审核)
        /// </summary>
        public AuditFlag impbody_audit_flag { get; set; }
        /// <summary>
        /// 审核人
        /// </summary>
        public string impbody_audit_uid { get; set; }
        /// <summary>
        /// 审核时间
        /// </summary>
        public DateTime impbody_audit_datetime { get; set; }
        /// <summary>
        /// 作废标志(1正常；2作废)
        /// </summary>
        [Required]
        public NousedFlag impbody_noused_flag { get; set; }
        /// <summary>
        /// 作废人
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string impbody_noused_uid { get; set; }
        /// <summary>
        /// 作废时间
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string impbody_noused_datetime { get; set; }
        /// <summary>
        /// 上传标志(1未上传；2已上传)
        /// </summary>
        public UploadFlag impbody_upload_flag { get; set; }
        /// <summary>
        /// 上传时间
        /// </summary>
        public DateTime impbody_upload_datetime { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled impbody_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 物料所属公司
        /// </summary>
        public virtual Guid? impbody_company_id { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? impbody_warehouse_id { get; set; }
        /// <summary>
        /// 表头ID
        /// </summary>
        public virtual Guid? impbody_imphead_id { get; set; }
        /// <summary>
        /// 物料ID
        /// </summary>
        public virtual Guid? impbody_goods_id { get; set; }
        /// <summary>
        /// 上传质量状态
        /// </summary>
        public string impbody_upload_quantity { get; set; }
        /// <summary>
        /// 质量状态
        /// </summary>
        public virtual Guid? impbody_quality_status { get; set; }
        #endregion
    }
    #endregion

    #region 创建CreateDto主从
    public class ImportBillbodyorderCreatedDto
    {
        public ImportBillbodyCreatedDto body { get; set; }
        public List<ImportOrder.Dto.ImportOrderCreatedDto> createList { get; set; }
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(ImportBillbody))]
    public class ImportBillbodyUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 流水号
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string impbody_list_id { get; set; }
        /// <summary>
        /// 外部系统序号
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string impbody_external_listid { get; set; }
        /// <summary>
        /// 大批次
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string impbody_batch_no { get; set; }
        /// <summary>
        /// 小批次
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string impbody_lots_no { get; set; }
        /// <summary>
        /// 生产日期
        /// </summary>
        public DateTime impbody_product_date { get; set; }
        /// <summary>
        /// 生产线
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string impbody_product_lineid { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string impbody_stock_code { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string impbody_remark { get; set; }
        /// <summary>
        /// 单据条码
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string impbody_bill_bar { get; set; }
        /// <summary>
        /// 失效日期
        /// </summary>
        public DateTime impbody_vaildate_date { get; set; }
        /// <summary>
        /// 复检日期
        /// </summary>
        public DateTime imbody_recheck_date { get; set; }
        /// <summary>
        /// 计划数量
        /// </summary>
        [Required]
        public int impbody_plan_quantity { get; set; }
        /// <summary>
        /// 绑定数量
        /// </summary>
        public int impbody_binding_quantity { get; set; }
        /// <summary>
        /// 完成数量
        /// </summary>
        public int impbody_fulfill_quantity { get; set; }
        /// <summary>
        /// 执行标志(1未执行；2执行中；3已完成)
        /// </summary>
        [Required]
        public ExecuteFlag impbody_execute_flag { get; set; }
        /// <summary>
        /// 审核标志(1未审核；2已审核)
        /// </summary>
        public AuditFlag impbody_audit_flag { get; set; }
        /// <summary>
        /// 审核人
        /// </summary>
        public string impbody_audit_uid { get; set; }
        /// <summary>
        /// 审核时间
        /// </summary>
        public DateTime impbody_audit_datetime { get; set; }
        /// <summary>
        /// 作废标志(1正常；2作废)
        /// </summary>
        [Required]
        public NousedFlag impbody_noused_flag { get; set; }
        /// <summary>
        /// 作废人
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string impbody_noused_uid { get; set; }
        /// <summary>
        /// 作废时间
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string impbody_noused_datetime { get; set; }
        /// <summary>
        /// 上传标志(1未上传；2已上传)
        /// </summary>
        public UploadFlag impbody_upload_flag { get; set; }
        /// <summary>
        /// 上传时间
        /// </summary>
        public DateTime impbody_upload_datetime { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled impbody_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 物料所属公司
        /// </summary>
        public virtual Guid? impbody_company_id { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? impbody_warehouse_id { get; set; }
        /// <summary>
        /// 表头ID
        /// </summary>
        public virtual Guid? impbody_imphead_id { get; set; }
        /// <summary>
        /// 物料ID
        /// </summary>
        public virtual Guid? impbody_goods_id { get; set; }
        /// <summary>
        /// 上传质量状态
        /// </summary>
        public string impbody_upload_quantity { get; set; }
        /// <summary>
        /// 质量状态
        /// </summary>
        public virtual Guid? impbody_quality_status { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto主从
    public class ImportBillbodyorderUpdatedDto
    {
        public ImportBillbodyUpdatedDto body { get; set; }
        public List<ImportOrder.Dto.ImportOrderCreatedDto> createList { get; set; }
        public List<ImportOrder.Dto.ImportOrderUpdatedDto> updateList { get; set; }
        public List<Guid> idList { get; set; }
    }
    #endregion

    #region 批量ListDto
    public class ImportBillbodySaveListDto
    {
        public List<ImportBillbodyCreatedDto> createList;
        public List<ImportBillbodyUpdatedDto> updateList;
        public List<Guid> idList;
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(ImportBillbody))]
    public class ImportBillbodyDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 流水号
        /// </summary>
        public string impbody_list_id { get; set; }
        /// <summary>
        /// 外部系统序号
        /// </summary>
        public string impbody_external_listid { get; set; }
        /// <summary>
        /// 大批次
        /// </summary>
        public string impbody_batch_no { get; set; }
        /// <summary>
        /// 小批次
        /// </summary>
        public string impbody_lots_no { get; set; }
        /// <summary>
        /// 生产日期
        /// </summary>
        public DateTime impbody_product_date { get; set; }
        /// <summary>
        /// 生产线
        /// </summary>
        public string impbody_product_lineid { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        public string impbody_stock_code { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string impbody_remark { get; set; }
        /// <summary>
        /// 单据条码
        /// </summary>
        public string impbody_bill_bar { get; set; }
        /// <summary>
        /// 失效日期
        /// </summary>
        public DateTime impbody_vaildate_date { get; set; }
        /// <summary>
        /// 复检日期
        /// </summary>
        public DateTime imbody_recheck_date { get; set; }
        /// <summary>
        /// 计划数量
        /// </summary>
        public int impbody_plan_quantity { get; set; }
        /// <summary>
        /// 绑定数量
        /// </summary>
        public int impbody_binding_quantity { get; set; }
        /// <summary>
        /// 完成数量
        /// </summary>
        public int impbody_fulfill_quantity { get; set; }
        /// <summary>
        /// 执行标志(1未执行；2执行中；3已完成)
        /// </summary>
        public ExecuteFlag impbody_execute_flag { get; set; }
        /// <summary>
        /// 审核标志(1未审核；2已审核)
        /// </summary>
        public AuditFlag impbody_audit_flag { get; set; }
        /// <summary>
        /// 审核人
        /// </summary>
        public string impbody_audit_uid { get; set; }
        /// <summary>
        /// 审核时间
        /// </summary>
        public DateTime impbody_audit_datetime { get; set; }
        /// <summary>
        /// 作废标志(1正常；2作废)
        /// </summary>
        public NousedFlag impbody_noused_flag { get; set; }
        /// <summary>
        /// 作废人
        /// </summary>
        public string impbody_noused_uid { get; set; }
        /// <summary>
        /// 作废时间
        /// </summary>
        public string impbody_noused_datetime { get; set; }
        /// <summary>
        /// 上传标志(1未上传；2已上传)
        /// </summary>
        public UploadFlag impbody_upload_flag { get; set; }
        /// <summary>
        /// 上传时间
        /// </summary>
        public DateTime impbody_upload_datetime { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled impbody_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 物料所属公司
        /// </summary>
        public virtual Guid? impbody_company_id { get; set; }
        [ForeignKey("impbody_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? impbody_warehouse_id { get; set; }
        [ForeignKey("impbody_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo WarehouseInfo { get; set; }
        /// <summary>
        /// 表头ID
        /// </summary>
        public virtual Guid? impbody_imphead_id { get; set; }
        [ForeignKey("impbody_imphead_id")]
        public virtual ImportBillhead.ImportBillhead ImportBillhead { get; set; }
        /// <summary>
        /// 物料ID
        /// </summary>
        public virtual Guid? impbody_goods_id { get; set; }
        [ForeignKey("impbody_goods_id")]
        public virtual GoodsInfo.GoodsInfo GoodsInfo { get; set; }
        /// <summary>
        /// 上传质量状态
        /// </summary>
        public string impbody_upload_quantity { get; set; }
        [ForeignKey("impbody_upload_quantity")]
        public virtual QualityInfo.QualityInfo UploadQualityInfo { get; set; }
        /// <summary>
        /// 质量状态
        /// </summary>
        public virtual Guid? impbody_quality_status { get; set; }
        [ForeignKey("impbody_quality_status")]
        public virtual QualityInfo.QualityInfo QualityInfo { get; set; }
        #endregion
    }
    #endregion
}
