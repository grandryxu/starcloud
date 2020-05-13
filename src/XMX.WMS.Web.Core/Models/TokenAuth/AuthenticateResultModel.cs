using System;
using System.Collections.Generic;

namespace XMX.WMS.Models.TokenAuth
{
    public class AuthenticateResultModel
    {
        public string AccessToken { get; set; }

        public string EncryptedAccessToken { get; set; }

        public int ExpireInSeconds { get; set; }

        public long UserId { get; set; }

        public string loginLanguage { get; set; }

        public virtual Guid? companyId { get; set; }

        public List<string> Permissions { get; set; }
    }
}
