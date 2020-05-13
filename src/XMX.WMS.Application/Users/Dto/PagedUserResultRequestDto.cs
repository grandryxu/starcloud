using Abp.Application.Services.Dto;
using System;

namespace XMX.WMS.Users.Dto
{
    public class PagedUserResultRequestDto : PagedResultRequestDto
    {
        //public string Keyword { get; set; }
        public bool? IsActive { get; set; }
        /// <summary>
        /// 用户名
        /// </summary>
        public string SurName { get; set; }
        /// <summary>
        /// 登录名称
        /// </summary>
        public string Name { get; set; }
        /// <summary>
        /// 角色
        /// </summary>
        public int RoleId { get; set; }
        /// <summary>
        /// 是否启用
        /// </summary>
        public WMSIsEnabled? IsEnable { get; set; }
        /// <summary>
        /// id
        /// </summary>
        public Guid? Id { get; set; }
    }
}
