using Abp.Application.Services.Dto;
using XMX.WMS.Base.Dto;
using System;
using Abp.AutoMapper;

namespace XMX.WMS.ExportBillSyncLog.Dto
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-05-12 16:49:20
    /// 描 述：
    ///</summary>
    #region 查询参数输入
    public class ExportBillSyncLogPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 出库单据信息
        /// </summary>
        public string expbill_info { get; set; }
        /// <summary>
        /// 出库单据创建时间
        /// </summary>
        public DateTime expbill_creat_datetime { get; set; }

        /// <summary>
        /// 出库结果
        /// </summary>
        public string expbill_result { get; set; }

        /// <summary>
        /// 查询时间范围
        /// </summary>
        public string DateRange { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(ExportBillSyncLog))]
    public class ExportBillSyncLogCreatedDto : BaseCreateDto
    {



    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(ExportBillSyncLog))]
    public class ExportBillSyncLogUpdatedDto : BaseUpdateDto
    {


    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(ExportBillSyncLog))]
    public class ExportBillSyncLogDto : EntityDto<Guid>
    {
        /// <summary>
        /// 出库单据信息
        /// </summary>
        public string expbill_info { get; set; }
        /// <summary>
        /// 出库单据创建时间
        /// </summary>
        public DateTime expbill_creat_datetime { get; set; }

        /// <summary>
        /// 出库结果
        /// </summary>
        public string expbill_result { get; set; }
        /// <summary>
        ///创建时间
        /// </summary>
        public DateTime CreationTime { get; set; }
    }
    #endregion
}
