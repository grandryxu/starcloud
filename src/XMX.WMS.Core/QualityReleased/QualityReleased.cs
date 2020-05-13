using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.QualityReleased
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-03-18 17:43:32
    /// 描 述：
    ///</summary>
    public class QualityReleased : FullAuditedEntity<Guid> 
    {
        #region 属性
        /// <summary>
        /// 物料编码
        /// </summary>
        public string quare_goods_code { get; set; }
        /// <summary>
        /// 物料名称
        /// </summary>
        public string quare_goods_name { get; set; }
        /// <summary>
        /// 批次
        /// </summary>
        public string quare_batch_no { get; set; }
        /// <summary>
        /// 入库单号
        /// </summary>
        public string quare_stock_in_code { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 质量信息
        /// </summary>
        public virtual Guid? quare_quality_id { get; set; }
        [ForeignKey("quare_quality_id")]
        public virtual QualityInfo.QualityInfo quare_quality_info { get; set; }
        #endregion

    }
}
