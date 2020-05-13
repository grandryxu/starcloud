using System;

namespace XMX.WMS.CompanyInfo.Dto
{
    /// <summary>
    /// 公司树形节点信息
    /// </summary>
    public class CompanyInfoNode
    {
        public Guid id { get; set; }
        public string label { get; set; }
        public Guid value { get; set; }
    }
}
