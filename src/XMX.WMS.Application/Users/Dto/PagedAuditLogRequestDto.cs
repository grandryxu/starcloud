using Abp.Application.Services.Dto;

namespace XMX.WMS.Users.Dto
{
    public class PagedAuditLogRequestDto : PagedResultRequestDto
    {
        public long? UserId { get; set; }
        public string ServiceName { get; set; }
        public string MethodName { get; set; }
        public string Parameters { get; set; }
        public string DateRange { get; set; }
    }
}
