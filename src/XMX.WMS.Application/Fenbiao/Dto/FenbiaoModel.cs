using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.Fenbiao.Dto
{
    #region 查询参数传入dto
    public class FenbiaoPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 编码
        /// </summary>
        public string code { get; set; }
        public string name { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(Fenbiao))]
    public class FenbiaoCreatedDto : BaseCreateDto
    {
        /// <summary>
        /// 编码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string code { get; set; }
        public string name { get; set; }
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(Fenbiao))]
    public class FenbiaoUpdatedDto : BaseUpdateDto
    {
        /// <summary>
        /// 编码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string code { get; set; }
        public string name { get; set; }
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(Fenbiao))]
    public class FenbiaoDto : EntityDto<Guid>
    {
        /// <summary>
        /// 编码
        /// </summary>
        public string code { get; set; }
        public string name { get; set; }
    }
    #endregion
}
