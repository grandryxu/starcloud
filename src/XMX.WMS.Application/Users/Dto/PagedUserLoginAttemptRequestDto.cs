using Abp.Application.Services.Dto;
using Abp.Authorization;
using System;
using System.Collections.Generic;

namespace XMX.WMS.Users.Dto
{
    public class PagedUserLoginAttemptRequestDto: PagedResultRequestDto
    {
        public long? UserId { get; set; }
        public string UserNameOrEmailAddress { get; set; }
        public AbpLoginResultType Result { get; set; }
        public string DateRange { get; set; }
    }
}
