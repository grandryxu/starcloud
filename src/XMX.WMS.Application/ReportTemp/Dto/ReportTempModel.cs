using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.Collections.Generic;
using System.Text;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.ReportTemp.Dto
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-05-18 14:59:04
    /// 描 述：
    ///</summary>
    #region 查询输入参数
    public class ReportTempPagedRequest : PagedResultRequestDto
    {
        #region 属性
        /// <summary>
        /// 模板id，字符串类型
        /// </summary>
        public string TempId { get; set; }
        /// <summary>
        /// 报表名称
        /// </summary>
        public string FullName { get; set; }
        /// <summary>
        ///报表编号
        /// </summary>
        public string EnCode { get; set; }
        /// <summary>
        /// 报表分类
        /// </summary>
        public string TempCategory { get; set; }
        /// <summary>
        /// 报表风格
        /// </summary>
        public string TempStyle { get; set; }
        /// <summary>
        /// 图表类型
        /// </summary>
        public string TempType { get; set; }
        /// <summary>
        /// 报表介绍
        /// </summary>
        public string Description { get; set; }
        /// <summary>
        /// 报表参数Json
        /// </summary>
        public string ParamJson { get; set; }
        /// <summary>
        /// 排序码
        /// </summary>
        public int? SortCode { get; set; }
        /// <summary>
        /// 有效标志
        /// </summary>
        public int? EnabledMark { get; set; }
        #endregion
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(ReportTemp))]
    public class ReportTempCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 模板id，字符串类型
        /// </summary>
        public string TempId { get; set; }
        /// <summary>
        /// 报表名称
        /// </summary>
        public string FullName { get; set; }
        /// <summary>
        ///报表编号
        /// </summary>
        public string EnCode { get; set; }
        /// <summary>
        /// 报表分类
        /// </summary>
        public string TempCategory { get; set; }
        /// <summary>
        /// 报表风格
        /// </summary>
        public string TempStyle { get; set; }
        /// <summary>
        /// 图表类型
        /// </summary>
        public string TempType { get; set; }
        /// <summary>
        /// 报表介绍
        /// </summary>
        public string Description { get; set; }
        /// <summary>
        /// 报表参数Json
        /// </summary>
        public string ParamJson { get; set; }
        /// <summary>
        /// 排序码
        /// </summary>
        public int? SortCode { get; set; }
        /// <summary>
        /// 有效标志
        /// </summary>
        public int? EnabledMark { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(ReportTemp))]
    public class ReportTempUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 模板id，字符串类型
        /// </summary>
        public string TempId { get; set; }
        /// <summary>
        /// 报表名称
        /// </summary>
        public string FullName { get; set; }
        /// <summary>
        ///报表编号
        /// </summary>
        public string EnCode { get; set; }
        /// <summary>
        /// 报表分类
        /// </summary>
        public string TempCategory { get; set; }
        /// <summary>
        /// 报表风格
        /// </summary>
        public string TempStyle { get; set; }
        /// <summary>
        /// 图表类型
        /// </summary>
        public string TempType { get; set; }
        /// <summary>
        /// 报表介绍
        /// </summary>
        public string Description { get; set; }
        /// <summary>
        /// 报表参数Json
        /// </summary>
        public string ParamJson { get; set; }
        /// <summary>
        /// 排序码
        /// </summary>
        public int? SortCode { get; set; }
        /// <summary>
        /// 有效标志
        /// </summary>
        public int? EnabledMark { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(ReportTemp))]
    public class ReportTempDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 模板id，字符串类型
        /// </summary>
        public string TempId { get; set; }
        /// <summary>
        /// 报表名称
        /// </summary>
        public string FullName { get; set; }
        /// <summary>
        ///报表编号
        /// </summary>
        public string EnCode { get; set; }
        /// <summary>
        /// 报表分类
        /// </summary>
        public string TempCategory { get; set; }
        /// <summary>
        /// 报表风格
        /// </summary>
        public string TempStyle { get; set; }
        /// <summary>
        /// 图表类型
        /// </summary>
        public string TempType { get; set; }
        /// <summary>
        /// 报表介绍
        /// </summary>
        public string Description { get; set; }
        /// <summary>
        /// 报表参数Json
        /// </summary>
        public string ParamJson { get; set; }
        /// <summary>
        /// 排序码
        /// </summary>
        public int? SortCode { get; set; }
        /// <summary>
        /// 有效标志
        /// </summary>
        public int? EnabledMark { get; set; }
        /// <summary>
        ///创建时间
        /// </summary>
        public DateTime CreationTime { get; set; }
        #endregion
    }
    #endregion
}
