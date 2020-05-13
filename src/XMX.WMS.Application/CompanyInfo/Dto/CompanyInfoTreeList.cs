using System;
using System.Collections.Generic;

namespace XMX.WMS.CompanyInfo.Dto
{
    public class CompanyInfoTreeList
    {
        public Guid id { get; set; }
        public string label { get; set; }
        public List<CompanyInfoNode> children { get; set; }
        public Guid value { get; set; }
    }
}
