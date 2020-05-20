using Abp.Application.Services.Dto;
using Abp.Runtime.Validation;
using System;

namespace XMX.WMS.Base.Dto
{
    /// <summary>
    /// 验证
    /// </summary>
    public abstract class BaseVerification
    {
        public const int column6 = 6;
        public const int column8 = 8;
        public const int column10 = 10;
        public const int column20 = 20;
        public const int column30 = 30;
        public const int column40 = 40;
        public const int column50 = 50;
        public const int column60 = 60;
        public const int column70 = 70;
        public const int column80 = 80;
        public const int column90 = 90;
        public const int column100 = 100;
        public const int column200 = 200;
    }

    /// <summary>
    /// 基础创建的DTO
    /// </summary>
    public class BaseCreateDto : IShouldNormalize
    {
        //// <summary>
        //// 创建时间
        //// </summary>
        public DateTime? CreationTime { get; set; }

        //// <summary>
        //// 创建人
        //// </summary>
        public long? CreatorUserId { get; set; }

        public void Normalize()
        {
            if (!CreationTime.HasValue) CreationTime = DateTime.Now;
        }
    }

    /// <summary>
    /// 基础更新的DTO
    /// </summary>
    public class BaseUpdateDto : EntityDto<Guid>
    {
        /// <summary>
        /// 修改原因
        /// </summary>
        //public string ModificationReamrk { get; set; }

        //// <summary>
        //// 上次修改时间
        //// </summary>
        public DateTime? LastModificationTime { get; set; }

        //// <summary>
        //// 修改人
        //// </summary>
        public long? LastModifierUserId { get; set; }

        public virtual void Normalize()
        {
            if (!LastModificationTime.HasValue)
            {
                LastModificationTime = DateTime.Now;
            }
        }
    }

    public class GetNumDto
    { 
        /// <summary>
        /// 统计数据条数
        /// </summary>
        public int listCount { get; set; }
    }
}
