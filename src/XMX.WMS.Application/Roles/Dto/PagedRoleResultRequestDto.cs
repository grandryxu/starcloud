using Abp.Application.Services.Dto;
using System;

namespace XMX.WMS.Roles.Dto
{
    public class PagedRoleResultRequestDto : PagedResultRequestDto
    {
        /// <summary>
        /// 角色名
        /// </summary>
        public string Name { get; set; }
        /// <summary>
        /// 是否启用
        /// </summary>
        public WMSIsEnabled? IsEnable { get; set; }
        /// <summary>
        /// 主键
        /// </summary>
        public Guid? Id { get; set; }


    }
}

