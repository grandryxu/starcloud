using System;
using System.Collections.Generic;

namespace XMX.WMS.DepartmentInfo.Dto
{
    public class DepartmentInfoTreeList
    {
        public Guid id { get; set; }
        public string label { get; set; }
        public List<DepartmentInfoNode> children { get; set; }
    }
}
