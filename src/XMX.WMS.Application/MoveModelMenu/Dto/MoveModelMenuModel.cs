using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.MoveModelMenu.Dto
{
    #region 查询参数传入dto
    public class MoveModelMenuPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 功能名称
        /// </summary>
        public string function_name { get; set; }
        /// <summary>
        /// 链接
        /// </summary>
        public string menu_url { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(MoveModelMenu))]
    public class MoveModelMenuCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// URL地址
        /// </summary>
        public string menu_url { get; set; }
        /// <summary>
        /// 类型
        /// </summary>
        public MenuType menu_type { get; set; }
        /// <summary>
        /// 顺序
        /// </summary>
        public int menu_order { get; set; }
        /// <summary>
        /// 功能名称
        /// </summary>
        public string menu_function_name { get; set; }
        /// <summary>
        /// 图标样式
        /// </summary>
        public string menu_icon { get; set; }
        /// <summary>
        /// 是否启用
        /// </summary>
        public WMSIsEnabled menu_is_enable { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string menu_remark { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 父节点
        /// </summary>
        public virtual Guid? menu_parent_id { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(MoveModelMenu))]
    public class MoveModelMenuUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// URL地址
        /// </summary>
        public string menu_url { get; set; }
        /// <summary>
        /// 类型
        /// </summary>
        public MenuType menu_type { get; set; }
        /// <summary>
        /// 顺序
        /// </summary>
        public int menu_order { get; set; }
        /// <summary>
        /// 功能名称
        /// </summary>
        public string menu_function_name { get; set; }
        /// <summary>
        /// 图标样式
        /// </summary>
        public string menu_icon { get; set; }
        /// <summary>
        /// 是否启用
        /// </summary>
        public WMSIsEnabled menu_is_enable { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string menu_remark { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 父节点
        /// </summary>
        public virtual Guid? menu_parent_id { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(MoveModelMenu))]
    public class MoveModelMenuDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// URL地址
        /// </summary>
        public string menu_url { get; set; }
        /// <summary>
        /// 类型
        /// </summary>
        public MenuType menu_type { get; set; }
        /// <summary>
        /// 顺序
        /// </summary>
        public int menu_order { get; set; }
        /// <summary>
        /// 功能名称
        /// </summary>
        public string menu_function_name { get; set; }
        /// <summary>
        /// 图标样式
        /// </summary>
        public string menu_icon { get; set; }
        /// <summary>
        /// 是否启用
        /// </summary>
        public WMSIsEnabled menu_is_enable { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string menu_remark { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 父节点
        /// </summary>
        public virtual Guid? menu_parent_id { get; set; }
        [ForeignKey("menu_parent_id")]
        public virtual MoveModelMenu modelMenu { get; set; }
        #endregion
    }
    #endregion

    public class SubMenuRequest
    {
        public Guid? parentId { get; set; }

        public MenuType? menu_type { get; set; }
    }
}
