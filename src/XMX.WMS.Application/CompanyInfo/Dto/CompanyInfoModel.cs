using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.CompanyInfo.Dto
{
    #region 查询参数传入dto
    public class CompanyInfoSerchRequest
    {
        /// <summary>
        /// 用来过滤的公司主键
        /// </summary>
        public Guid? companyId { get; set; }
    }
    public class CompanyInfoPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 公司名
        /// </summary>
        public string Name { get; set; }

        /// <summary>
        /// 负责人
        /// </summary>
        public string ManagerName { get; set; }
        /// <summary>
        /// 父级
        /// </summary>
        public Guid? ParentId { get; set; }
        /// <summary>
        /// 类型
        /// </summary>
        public string Type { get; set; }
    }
    #endregion

    #region CreateDto
    [AutoMapTo(typeof(CompanyInfo))]
    public class CompanyCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 公司名
        /// </summary>
        public string Name { get; set; }
        /// <summary>
        /// 公司名简称
        /// </summary>
        public string ShortName { get; set; }
        /// <summary>
        /// 负责人
        /// </summary>
        public string ManagerName { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string Remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled IsEnable { get; set; }
        /// <summary>
        /// 地址信息
        /// </summary>
        public string Address { get; set; }
        /// <summary>
        /// 地址明细信息
        /// </summary>
        public string AddressDetail { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 上级公司Id
        /// </summary>
        public virtual Guid? ParentId { get; set; }
        #endregion
    }
    #endregion

    #region UpdateDto
    [AutoMapTo(typeof(CompanyInfo))]
    public class CompanyUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 公司名
        /// </summary>
        public string Name { get; set; }
        /// <summary>
        /// 公司名简称
        /// </summary>
        public string ShortName { get; set; }
        /// <summary>
        /// 负责人
        /// </summary>
        public string ManagerName { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string Remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled IsEnable { get; set; }
        /// <summary>
        /// 地址信息
        /// </summary>
        public string Address { get; set; }
        /// <summary>
        /// 地址明细信息
        /// </summary>
        public string AddressDetail { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 上级公司Id
        /// </summary>
        public virtual Guid? ParentId { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(CompanyInfo))]
    public class CompanyInfoDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 公司名
        /// </summary>
        public string Name { get; set; }

        /// <summary>
        /// 公司名简称
        /// </summary>
        public string ShortName { get; set; }

        /// <summary>
        /// 负责人
        /// </summary>
        public string ManagerName { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string Remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled IsEnable { get; set; }


        /// <summary>
        /// 地址信息
        /// </summary>
        public string Address { get; set; }
        /// <summary>
        /// 地址明细信息
        /// </summary>
        public string AddressDetail { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 上级公司Id
        /// </summary>
        public virtual Guid? ParentId { get; set; }
        /// <summary>
        /// 上级公司
        /// </summary>
        [ForeignKey("ParentId")]
        public virtual CompanyInfo Parent { get; set; }
        #endregion
    }
    #endregion

    #region 下拉列表输出dto
    [AutoMapFrom(typeof(CompanyInfo))]
    public class CompanyInfoSelectedListDto : EntityDto<Guid>
    {
        /// <summary>
        /// 名称
        /// </summary>
        public string Name { get; set; }
    }
    #endregion
}
