using Abp.Application.Services.Dto;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;
using System;
using Abp.AutoMapper;

namespace XMX.WMS.ImportApplyLog.Dto
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-05-12 16:17:52
    /// 描 述：
    ///</summary>
    #region 查询参数输入
    public class ImportApplyLogPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 入库信息
        /// </summary>
        public string import_info { get; set; }

        /// <summary>
        /// 入库单创建时间
        /// </summary>
        public DateTime import_creat_datetime { get; set; }

        /// <summary>
        /// 入库结果
        /// </summary>
        public string import_result { get; set; }

        /// <summary>
        /// 查询时间范围
        /// </summary>
        public string DateRange { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(ImportApplyLog))]
    public class ImportApplyLogCreatedDto : BaseCreateDto
    {



    }
    #endregion


    #region 修改UpdateDto
    [AutoMapTo(typeof(ImportApplyLog))]
    public class ImportApplyLogUpdatedDto : BaseUpdateDto
    {
    

    }
    #endregion


    #region 查询输出dto
    [AutoMapFrom(typeof(ImportApplyLog))]
    public class ImportApplyLogDto : EntityDto<Guid>
    {
        /// <summary>
        /// 入库信息
        /// </summary>
        public string import_info { get; set; }

        /// <summary>
        /// 入库单创建时间
        /// </summary>
        public DateTime import_creat_datetime { get; set; }

        /// <summary>
        /// 入库结果
        /// </summary>
        public string import_result { get; set; }
    }
    #endregion


}
