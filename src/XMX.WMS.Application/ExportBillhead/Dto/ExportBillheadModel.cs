using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;
using XMX.WMS.ExportOrder.Dto;

namespace XMX.WMS.ExportBillhead.Dto
{
    #region 查询参数传入dto
    public class ExportBillheadPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 单号
        /// </summary>
        public string exphead_code { get; set; }
        /// <summary>
        /// 合同号
        /// </summary>
        public string exphead_external_code { get; set; }
        /// <summary>
        /// 上下游客户ID
        /// </summary>
        public Guid? exphead_custom_id { get; set; }
        /// <summary>
        /// 单据类型
        /// </summary>
        public Guid? exphead_bill_id { get; set; }
        /// <summary>
        /// 整单执行标志(1, 未执行；2执行中；3已完成)
        /// </summary>
        public ExecuteFlag? exphead_execute_flag { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public Guid? exphead_warehouse_id { get; set; }
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
    [AutoMapTo(typeof(ExportBillhead))]
    public class ExportBillheadCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 波次号
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string exphead_wave_no { get; set; }
        /// <summary>
        /// 单号
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string exphead_code { get; set; }
        /// <summary>
        /// 外部单据ID
        /// </summary>
        [StringLength(BaseVerification.column60)]
        public string exphead_external_id { get; set; }
        /// <summary>
        /// 外部单据号
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string exphead_external_code { get; set; }
        /// <summary>
        /// 单据日期
        /// </summary>
        public DateTime exphead_date { get; set; }
        /// <summary>
        /// 整单执行标志
        /// </summary>
        [Required]
        public ExecuteFlag exphead_execute_flag { get; set; }
        /// <summary>
        /// 整单作废标志
        /// </summary>
        [Required]
        public NousedFlag exphead_noused_flag { get; set; }
        /// <summary>
        /// 整单审核标志
        /// </summary>
        public AuditFlag exphead_audit_flag { get; set; }
        /// <summary>
        /// 整单上传标志
        /// </summary>
        public UploadFlag exphead_upload_flag { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string exphead_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled exphead_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? exphead_company_id { get; set; }
        /// <summary>
        /// 单据类型
        /// </summary>
        public virtual Guid? exphead_bill_id { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? exphead_warehouse_id { get; set; }
        /// <summary>
        /// 上下游客户ID
        /// </summary>
        public virtual Guid? exphead_custom_id { get; set; }
        #endregion
    }
    #endregion

    #region 创建CreateDto主从
    //主从表保存
    public class ExportBillheadbodyCreateDto
    {
        /// <summary>
        /// 表头
        /// </summary>
        public ExportBillheadCreatedDto head { get; set; }
        /// <summary>
        /// 表体
        /// </summary>
        public List<ExportBillbody.Dto.ExportBillbodyCreatedDto> createList { get; set; }
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(ExportBillhead))]
    public class ExportBillheadUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 波次号
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string exphead_wave_no { get; set; }
        /// <summary>
        /// 单号
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string exphead_code { get; set; }
        /// <summary>
        /// 外部单据ID
        /// </summary>
        [StringLength(BaseVerification.column60)]
        public string exphead_external_id { get; set; }
        /// <summary>
        /// 外部单据号
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string exphead_external_code { get; set; }
        /// <summary>
        /// 单据日期
        /// </summary>
        public DateTime exphead_date { get; set; }
        /// <summary>
        /// 整单执行标志
        /// </summary>
        [Required]
        public ExecuteFlag exphead_execute_flag { get; set; }
        /// <summary>
        /// 整单作废标志
        /// </summary>
        [Required]
        public NousedFlag exphead_noused_flag { get; set; }
        /// <summary>
        /// 整单审核标志
        /// </summary>
        public AuditFlag exphead_audit_flag { get; set; }
        /// <summary>
        /// 整单上传标志
        /// </summary>
        public UploadFlag exphead_upload_flag { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string exphead_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled exphead_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? exphead_company_id { get; set; }
        /// <summary>
        /// 单据类型
        /// </summary>
        public virtual Guid? exphead_bill_id { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? exphead_warehouse_id { get; set; }
        /// <summary>
        /// 上下游客户ID
        /// </summary>
        public virtual Guid? exphead_custom_id { get; set; }
        #endregion
    }
    #endregion

    #region 创建UpdateDto主从
    //主从表保存
    public class ExportBillheadbodyUpdateDto
    {
        /// <summary>
        /// 表头
        /// </summary>
        public ExportBillheadUpdatedDto head { get; set; }
        /// <summary>
        /// 表体
        /// </summary>
        public List<ExportBillbody.Dto.ExportBillbodyCreatedDto> createList { get; set; }
        /// <summary>
        /// 表体
        /// </summary>
        public List<ExportBillbody.Dto.ExportBillbodyUpdatedDto> updateList { get; set; }
        /// <summary>
        /// 删除
        /// </summary>
        public List<Guid> idList;
    }
    #endregion

    #region 手动任务流水TaskDto
    public class ExportBillCreateTaskDto
    {
        public Guid headId { get; set; }
        public List<ExportOrderUpdatedDto> orderList { get; set; }
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(ExportBillhead))]
    public class ExportBillheadDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 波次号
        /// </summary>
        public string exphead_wave_no { get; set; }
        /// <summary>
        /// 单号
        /// </summary>
        public string exphead_code { get; set; }
        /// <summary>
        /// 外部单据ID
        /// </summary>
        public string exphead_external_id { get; set; }
        /// <summary>
        /// 外部单据号
        /// </summary>
        public string exphead_external_code { get; set; }
        /// <summary>
        /// 单据日期
        /// </summary>
        public DateTime exphead_date { get; set; }
        /// <summary>
        /// 整单执行标志
        /// </summary>
        public ExecuteFlag exphead_execute_flag { get; set; }
        /// <summary>
        /// 整单作废标志
        /// </summary>
        public NousedFlag exphead_noused_flag { get; set; }
        /// <summary>
        /// 整单审核标志
        /// </summary>
        public AuditFlag exphead_audit_flag { get; set; }
        /// <summary>
        /// 整单上传标志
        /// </summary>
        public UploadFlag exphead_upload_flag { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string exphead_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled exphead_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? exphead_company_id { get; set; }
        [ForeignKey("exphead_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        /// <summary>
        /// 单据类型
        /// </summary>
        public virtual Guid? exphead_bill_id { get; set; }
        [ForeignKey("exphead_bill_id")]
        public virtual BillInfo.BillInfo Bill { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? exphead_warehouse_id { get; set; }
        [ForeignKey("exphead_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        /// <summary>
        /// 上下游客户ID
        /// </summary>
        public virtual Guid? exphead_custom_id { get; set; }
        [ForeignKey("exphead_custom_id")]
        public virtual CustomInfo.CustomInfo Custom { get; set; }
        #endregion
    }
    #endregion
}
