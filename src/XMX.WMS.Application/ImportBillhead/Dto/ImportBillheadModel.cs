using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.ImportBillhead.Dto
{
    #region 查询参数传入dto
    public class ImportBillheadPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 单号
        /// </summary>
        public string imphead_code { get; set; }
        /// <summary>
        /// 合同号
        /// </summary>
        public string imphead_external_code { get; set; }
        /// <summary>
        /// 上下游客户ID
        /// </summary>
        public Guid? imphead_custom_id { get; set; }
        /// <summary>
        /// 单据类型
        /// </summary>
        public Guid? imphead_bill_id { get; set; }
        /// <summary>
        /// 整单执行标志(1, 未执行；2执行中；3已完成)
        /// </summary>
        public ExecuteFlag? imphead_execute_flag { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public Guid? imphead_warehouse_id { get; set; }
        /// <summary>
        /// 入库日期
        /// </summary>
        public string imphead_date { get; set; }
        /// <summary>
        /// 入库日期----开始
        /// </summary>
        public string startDate { get; set; }
        /// <summary>
        /// 入库日期----结束
        /// </summary>
        public string endDate { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(ImportBillhead))]
    public class ImportBillheadCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 单号
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string imphead_code { get; set; }
        /// <summary>
        /// 外部单据ID
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string imphead_external_id { get; set; }
        /// <summary>
        /// 外部单据号
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string imphead_external_code { get; set; }
        /// <summary>
        /// 入库日期
        /// </summary>
        public DateTime imphead_date { get; set; }
        /// <summary>
        /// 整单执行标志(1, 未执行；2执行中；3已完成)
        /// </summary>
        [Required]
        public ExecuteFlag imphead_execute_flag { get; set; }
        /// <summary>
        /// 整单作废标志(1未作废；2已作废)
        /// </summary>
        [Required]
        public NousedFlag imphead_noused_flag { get; set; }
        /// <summary>
        /// 整单审核标志(1未审核；2已审核)
        /// </summary>
        public AuditFlag imphead_audit_flag { get; set; }
        /// <summary>
        /// 整单回传标志(1未回传；2已回传)
        /// </summary>
        public UploadFlag imphead_upload_flag { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string imphead_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled imphead_is_enable { get; set; }
        /// <summary>
        /// 分表后缀
        /// </summary>
        //public string imphead_datestr { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 物料所属公司
        /// </summary>
        public virtual Guid? imphead_company_id { get; set; }
        /// <summary>
        /// 单据类型
        /// </summary>
        public virtual Guid? imphead_bill_id { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? imphead_warehouse_id { get; set; }
        /// <summary>
        /// 上下游客户ID
        /// </summary>
        public virtual Guid? imphead_custom_id { get; set; }
        #endregion
    }
    #endregion

    #region 创建CreateDto主从
    public class ImportBillheadbodyCreateDto
    {
        /// <summary>
        /// 表头
        /// </summary>
        public ImportBillheadCreatedDto head { get; set; }
        /// <summary>
        /// 表体
        /// </summary>
        public List<ImportBillbody.Dto.ImportBillbodyCreatedDto> createList { get; set; }
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(ImportBillhead))]
    public class ImportBillheadUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 单号
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string imphead_code { get; set; }
        /// <summary>
        /// 外部单据ID
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string imphead_external_id { get; set; }
        /// <summary>
        /// 外部单据号
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string imphead_external_code { get; set; }
        /// <summary>
        /// 入库日期
        /// </summary>
        public DateTime imphead_date { get; set; }
        /// <summary>
        /// 整单执行标志(1, 未执行；2执行中；3已完成)
        /// </summary>
        [Required]
        public ExecuteFlag imphead_execute_flag { get; set; }
        /// <summary>
        /// 整单作废标志(1未作废；2已作废)
        /// </summary>
        [Required]
        public NousedFlag imphead_noused_flag { get; set; }
        /// <summary>
        /// 整单审核标志(1未审核；2已审核)
        /// </summary>
        public AuditFlag imphead_audit_flag { get; set; }
        /// <summary>
        /// 整单回传标志(1未回传；2已回传)
        /// </summary>
        public UploadFlag imphead_upload_flag { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string imphead_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled imphead_is_enable { get; set; }
        /// <summary>
        /// 分表后缀
        /// </summary>
        //public string imphead_datestr { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 物料所属公司
        /// </summary>
        public virtual Guid? imphead_company_id { get; set; }
        /// <summary>
        /// 单据类型
        /// </summary>
        public virtual Guid? imphead_bill_id { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? imphead_warehouse_id { get; set; }
        /// <summary>
        /// 上下游客户ID
        /// </summary>
        public virtual Guid? imphead_custom_id { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto主从
    public class ImportBillheadbodyUpdateDto
    {
        /// <summary>
        /// 表头
        /// </summary>
        public ImportBillheadUpdatedDto head { get; set; }
        /// <summary>
        /// 表体
        /// </summary>
        public List<ImportBillbody.Dto.ImportBillbodyCreatedDto> createList { get; set; }
        /// <summary>
        /// 表体
        /// </summary>
        public List<ImportBillbody.Dto.ImportBillbodyUpdatedDto> updateList { get; set; }
        /// <summary>
        /// 删除
        /// </summary>
        public List<Guid> idList { get; set; }
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(ImportBillhead))]
    public class ImportBillheadDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 单号
        /// </summary>
        public string imphead_code { get; set; }
        /// <summary>
        /// 外部单据ID
        /// </summary>
        public string imphead_external_id { get; set; }
        /// <summary>
        /// 外部单据号
        /// </summary>
        public string imphead_external_code { get; set; }
        /// <summary>
        /// 入库日期
        /// </summary>
        public DateTime imphead_date { get; set; }
        /// <summary>
        /// 整单执行标志(1, 未执行；2执行中；3已完成)
        /// </summary>
        public ExecuteFlag imphead_execute_flag { get; set; }
        /// <summary>
        /// 整单作废标志(1未作废；2已作废)
        /// </summary>
        public NousedFlag imphead_noused_flag { get; set; }
        /// <summary>
        /// 整单审核标志(1未审核；2已审核)
        /// </summary>
        public AuditFlag imphead_audit_flag { get; set; }
        /// <summary>
        /// 整单回传标志(1未回传；2已回传)
        /// </summary>
        public UploadFlag imphead_upload_flag { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string imphead_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled imphead_is_enable { get; set; }
        /// <summary>
        /// 分表后缀
        /// </summary>
        //public string imphead_datestr { get; set; }
        /// <summary>
        ///创建时间
        /// </summary>
        public DateTime CreationTime { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 物料所属公司
        /// </summary>
        public virtual Guid? imphead_company_id { get; set; }
        [ForeignKey("imphead_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        /// <summary>
        /// 单据类型
        /// </summary>
        public virtual Guid? imphead_bill_id { get; set; }
        [ForeignKey("imphead_bill_id")]
        public virtual BillInfo.BillInfo BillInfo { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? imphead_warehouse_id { get; set; }
        [ForeignKey("imphead_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo WarehouseInfo { get; set; }
        /// <summary>
        /// 上下游客户ID
        /// </summary>
        public virtual Guid? imphead_custom_id { get; set; }
        [ForeignKey("imphead_custom_id")]
        public virtual CustomInfo.CustomInfo CustomInfo { get; set; }
        #endregion
    }
    #endregion
}
