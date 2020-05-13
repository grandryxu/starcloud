﻿using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.ExportOrder.Dto
{
    #region 查询参数传入dto
    public class ExportOrderPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 单据条码
        /// </summary>
        public string exporder_bill_bar { get; set; }
        /// <summary>
        /// 批次
        /// </summary>
        public Guid? exporder_body_id { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(ExportOrder))]
    public class ExportOrderCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 大批号
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string exporder_batch_no { get; set; }
        /// <summary>
        /// 小批号
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string exporder_lots_no { get; set; }
        /// <summary>
        /// 生产日期
        /// </summary>
        public DateTime exporder_product_date { get; set; }
        /// <summary>
        /// 生产线
        /// </summary>
        [StringLength(BaseVerification.column60)]
        public string exporder_product_lineid { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string exporder_remark { get; set; }
        /// <summary>
        /// 单据条码
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string exporder_bill_bar { get; set; }
        /// <summary>
        /// 失效日期
        /// </summary>
        public DateTime exporder_vaildate_date { get; set; }
        /// <summary>
        /// 复检日期
        /// </summary>
        public DateTime exporder_recheck_date { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        [Required]
        public decimal exporder_quantity { get; set; }
        /// <summary>
        /// 箱码
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string exporder_box_code { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string exporder_stock_code { get; set; }
        /// <summary>
        /// 执行标志(1未执行；2执行中；3已完成；4已复核)
        /// </summary>
        [Required]
        public ExecuteFlag exporder_execute_flag { get; set; }
        /// <summary>
        /// 作废标志(1未作废；2已作废)
        /// </summary>
        [Required]
        public NousedFlag exporder_noused_flag { get; set; }
        /// <summary>
        /// 作废人
        /// </summary>
        [StringLength(BaseVerification.column50)] 
        public string exporder_noused_uid { get; set; }
        /// <summary>
        /// 作废时间
        /// </summary>
        public DateTime exporder_noused_datetime { get; set; }
        /// <summary>
        /// 上传标志(1未上传；2已上传)
        /// </summary>
        public UploadFlag exporder_upload_flag { get; set; }
        /// <summary>
        /// 上传时间
        /// </summary>
        public DateTime exporder_upload_datetime { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled exporder_is_enable { get; set; }

        #endregion

        #region 关联
        /// <summary>
        /// 历史任务
        /// </summary>
        public virtual Guid? history_task_id { get; set; }
        /// <summary>
        /// 立库任务
        /// </summary>
        public virtual Guid? task_id { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid exporder_slot_code { get; set; }
        /// <summary>
        /// 口号
        /// </summary>
        public virtual Guid? exporder_port_id { get; set; }
        /// <summary>
        /// 月台
        /// </summary>
        public virtual Guid? exporder_platform_id { get; set; }
        /// <summary>
        /// 批次
        /// </summary>
        public virtual Guid exporder_body_id { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(ExportOrder))]
    public class ExportOrderUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 大批号
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string exporder_batch_no { get; set; }
        /// <summary>
        /// 小批号
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string exporder_lots_no { get; set; }
        /// <summary>
        /// 生产日期
        /// </summary>
        public DateTime exporder_product_date { get; set; }
        /// <summary>
        /// 生产线
        /// </summary>
        [StringLength(BaseVerification.column60)]
        public string exporder_product_lineid { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string exporder_remark { get; set; }
        /// <summary>
        /// 单据条码
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string exporder_bill_bar { get; set; }
        /// <summary>
        /// 失效日期
        /// </summary>
        public DateTime exporder_vaildate_date { get; set; }
        /// <summary>
        /// 复检日期
        /// </summary>
        public DateTime exporder_recheck_date { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        [Required]
        public decimal exporder_quantity { get; set; }
        /// <summary>
        /// 箱码
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string exporder_box_code { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string exporder_stock_code { get; set; }
        /// <summary>
        /// 执行标志(1未执行；2执行中；3已完成；4已复核)
        /// </summary>
        [Required]
        public ExecuteFlag exporder_execute_flag { get; set; }
        /// <summary>
        /// 作废标志(1未作废；2已作废)
        /// </summary>
        [Required]
        public NousedFlag exporder_noused_flag { get; set; }
        /// <summary>
        /// 作废人
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string exporder_noused_uid { get; set; }
        /// <summary>
        /// 作废时间
        /// </summary>
        public DateTime exporder_noused_datetime { get; set; }
        /// <summary>
        /// 上传标志(1未上传；2已上传)
        /// </summary>
        public UploadFlag exporder_upload_flag { get; set; }
        /// <summary>
        /// 上传时间
        /// </summary>
        public DateTime exporder_upload_datetime { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled exporder_is_enable { get; set; }

        #endregion

        #region 关联
        /// <summary>
        /// 历史任务
        /// </summary>
        public virtual Guid? history_task_id { get; set; }
        /// <summary>
        /// 立库任务
        /// </summary>
        public virtual Guid? task_id { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid exporder_slot_code { get; set; }
        /// <summary>
        /// 口号
        /// </summary>
        public virtual Guid? exporder_port_id { get; set; }
        /// <summary>
        /// 月台
        /// </summary>
        public virtual Guid? exporder_platform_id { get; set; }
        /// <summary>
        /// 批次
        /// </summary>
        public virtual Guid exporder_body_id { get; set; }
        #endregion
    }
    #endregion

    #region 批量ListDto
    public class ExportOrderSaveListDto
    {
        public List<ExportOrderCreatedDto> createList;
        public List<ExportOrderUpdatedDto> updateList;
        public List<Guid> idList;
    }
    #endregion

    #region 复核数量ReviewDto
    public class ExportOrderReviewDto
    {
        // 批次ID
        public Guid order_id;
        // 托盘
        public string stock_code;
        // 物料id
        public Guid goods_id;
        // 批次编号
        public string batch_no;
        // 复核数量
        public decimal quantity;
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(ExportOrder))]
    public class ExportOrderDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 大批号
        /// </summary>
        public string exporder_batch_no { get; set; }
        /// <summary>
        /// 小批号
        /// </summary>
        public string exporder_lots_no { get; set; }
        /// <summary>
        /// 生产日期
        /// </summary>
        public DateTime exporder_product_date { get; set; }
        /// <summary>
        /// 生产线
        /// </summary>
        public string exporder_product_lineid { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string exporder_remark { get; set; }
        /// <summary>
        /// 单据条码
        /// </summary>
        public string exporder_bill_bar { get; set; }
        /// <summary>
        /// 失效日期
        /// </summary>
        public DateTime exporder_vaildate_date { get; set; }
        /// <summary>
        /// 复检日期
        /// </summary>
        public DateTime exporder_recheck_date { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public decimal exporder_quantity { get; set; }
        /// <summary>
        /// 箱码
        /// </summary>
        public string exporder_box_code { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        public string exporder_stock_code { get; set; }
        /// <summary>
        /// 执行标志(1未执行；2执行中；3已完成；4已复核)
        /// </summary>
        public ExecuteFlag exporder_execute_flag { get; set; }
        /// <summary>
        /// 作废标志(1未作废；2已作废)
        /// </summary>
        public NousedFlag exporder_noused_flag { get; set; }
        /// <summary>
        /// 作废人
        /// </summary>
        public string exporder_noused_uid { get; set; }
        /// <summary>
        /// 作废时间
        /// </summary>
        public DateTime exporder_noused_datetime { get; set; }
        /// <summary>
        /// 上传标志(1未上传；2已上传)
        /// </summary>
        public UploadFlag exporder_upload_flag { get; set; }
        /// <summary>
        /// 上传时间
        /// </summary>
        public DateTime exporder_upload_datetime { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled exporder_is_enable { get; set; }

        #endregion

        #region 关联
        /// <summary>
        /// 历史任务
        /// </summary>
        public virtual Guid? history_task_id { get; set; }
        [ForeignKey("history_task_id")]
        public virtual HistoryTaskMainInfo.HistoryTaskMainInfo HistoryTask { get; set; }
        /// <summary>
        /// 立库任务
        /// </summary>
        public virtual Guid? task_id { get; set; }
        [ForeignKey("task_id")]
        public virtual TaskMainInfo.TaskMainInfo Task { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid exporder_slot_code { get; set; }
        [ForeignKey("exporder_slot_code")]
        public virtual SlotInfo.SlotInfo Slot { get; set; }
        /// <summary>
        /// 口号
        /// </summary>
        public virtual Guid? exporder_port_id { get; set; }
        [ForeignKey("exporder_port_id")]
        public virtual PortInfo.PortInfo Port { get; set; }
        /// <summary>
        /// 月台
        /// </summary>
        public virtual Guid? exporder_platform_id { get; set; }
        [ForeignKey("exporder_platform_id")]
        public virtual PlatFormInfo.PlatFormInfo PlatForm { get; set; }
        /// <summary>
        /// 批次
        /// </summary>
        public virtual Guid exporder_body_id { get; set; }
        [ForeignKey("exporder_body_id")]
        public virtual ExportBillbody.ExportBillbody ExportBillbody { get; set; }
        #endregion
    }
    #endregion
}
