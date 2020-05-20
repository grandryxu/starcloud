using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.Equipment.Dto
{
    public class RGVInfoPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 编码
        /// </summary>
        public string rgv_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string rgv_name { get; set; }
        /// <summary>
        /// 在线状态
        /// </summary>
        public OnlineState? online_state { get; set; }
        /// <summary>
        /// 报警状态
        /// </summary>
        public AlarmState? alarm_state { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public virtual Guid? rgv_warehouse_id { get; set; }
    }

    #region 创建CreateDto
    [AutoMapTo(typeof(RGVInfo))]
    public class RGVInfoCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 编码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string rgv_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string rgv_name { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string rgv_remark { get; set; }
        /// <summary>
        /// 在线状态
        /// </summary>
        [Required]
        public OnlineState online_state { get; set; }
        /// <summary>
        /// 报警状态
        /// </summary>
        [Required]
        public AlarmState alarm_state { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所在仓库
        /// </summary>
        public virtual Guid rgv_warehouse_id { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(RGVInfo))]
    public class RGVInfoUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 编码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string rgv_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string rgv_name { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string rgv_remark { get; set; }
        /// <summary>
        /// 在线状态
        /// </summary>
        [Required]
        public OnlineState online_state { get; set; }
        /// <summary>
        /// 报警状态
        /// </summary>
        [Required]
        public AlarmState alarm_state { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所在仓库
        /// </summary>
        public virtual Guid rgv_warehouse_id { get; set; }
        [ForeignKey("rgv_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(RGVInfo))]
    public class RGVInfoDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 编码
        /// </summary>
        public string rgv_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string rgv_name { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string rgv_remark { get; set; }
        /// <summary>
        /// 在线状态
        /// </summary>
        public OnlineState online_state { get; set; }
        /// <summary>
        /// 报警状态
        /// </summary>
        public AlarmState alarm_state { get; set; }
        /// <summary>
        ///创建时间
        /// </summary>
        public DateTime CreationTime { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所在仓库
        /// </summary>
        public virtual Guid rgv_warehouse_id { get; set; }
        [ForeignKey("rgv_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        #endregion
    }
    #endregion
}
