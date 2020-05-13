using System;
using System.Collections.Generic;

namespace XMX.WMS.MoveModelMenu.Dto
{
    public class MoveModelMenuTreeList
    {
        public Guid id { get; set; }
        public string label { get; set; }
        public List<MoveModelMenuNode> children { get; set; }
    }
}
