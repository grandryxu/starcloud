using System;
using System.Collections.Generic;

namespace XMX.WMS.SystemMenuInfo.Dto
{
    public class SystemMenuInfoTreeList
    {
        public Guid id { get; set; }
        public string label { get; set; }
        public List<SystemMenuInfoNode> children { get; set; }
    }
}
