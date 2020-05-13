using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.GoodsInfo
{
    /// <summary>
    /// 货物
    /// </summary>
    public class GoodsInfo : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 编码
        /// </summary>
        public string goods_code { get; set; }
        /// <summary>
        /// 外部系统ID
        /// </summary>
        public string goods_external_id { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string goods_name { get; set; }
        /// <summary>
        /// 简称
        /// </summary>
        public string goods_short_name { get; set; }
        /// <summary>
        /// 英文名
        /// </summary>
        public string goods_en_name { get; set; }
        /// <summary>
        /// 物料描述
        /// </summary>
        public string goods_describe { get; set; }
        /// <summary>
        /// 规格
        /// </summary>
        public string goods_standard { get; set; }
        /// <summary>
        /// 型号
        /// </summary>
        public string goods_model { get; set; }
        /// <summary>
        /// 单价
        /// </summary>
        public decimal? goods_price { get; set; }
        /// <summary>
        /// 单件重量
        /// </summary>
        public decimal? goods_weight { get; set; }
        /// <summary>
        /// 最大托盘数量
        /// </summary>
        public decimal? goods_stock_qty { get; set; }
        /// <summary>
        /// 小包装数量
        /// </summary>
        public decimal? goods_small_qty { get; set; }
        /// <summary>
        /// 中包装数量
        /// </summary>
        public decimal? goods_medium_qty { get; set; }
        /// <summary>
        /// 大包装数量
        /// </summary>
        public decimal? goods_large_qty { get; set; }
        /// <summary>
        /// 外包装长度
        /// </summary>
        public decimal? goods_length { get; set; }
        /// <summary>
        /// 外包装宽度
        /// </summary>
        public decimal? goods_width { get; set; }
        /// <summary>
        /// 外包装高度
        /// </summary>
        public decimal? goods_height { get; set; }
        /// <summary>
        /// 库存上限
        /// </summary>
        public decimal? goods_stock_max { get; set; }
        /// <summary>
        /// 库存下限
        /// </summary>
        public decimal? goods_stock_min { get; set; }
        /// <summary>
        /// 物料类型
        /// </summary>
        public GoodsType? goods_type { get; set; }
        /// <summary>
        /// 最高水位
        /// </summary>
        public decimal? goods_water_high { get; set; }
        /// <summary>
        /// 最低水位
        /// </summary>
        public decimal? goods_water_low { get; set; }
        /// <summary>
        /// 生产厂家
        /// </summary>
        public string goods_factory { get; set; }
        /// <summary>
        /// 有效期
        /// </summary>
        public int goods_expiry_date { get; set; }
        /// <summary>
        /// 复检期
        /// </summary>
        public int goods_recheck_date { get; set; }
        /// <summary>
        /// ABC分类
        /// </summary>
        public string goods_ABC_class { get; set; }
        /// <summary>
        /// 物料图片
        /// </summary>
        public string goods_picture { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled goods_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? goods_company_id { get; set; }
        [ForeignKey("goods_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        /// <summary>
        /// 单位
        /// </summary>
        public virtual Guid? goods_unit { get; set; }
        [ForeignKey("goods_unit")]
        public virtual UnitInfo.UnitInfo Unit { get; set; }
        /// <summary>
        /// 辅助单位
        /// </summary>
        public virtual Guid? goods_unit2 { get; set; }
        [ForeignKey("goods_unit2")]
        public virtual UnitInfo.UnitInfo Unit2 { get; set; }
        /// <summary>
        /// 存放区域
        /// </summary>
        public virtual Guid? goods_area_id { get; set; }
        [ForeignKey("goods_area_id")]
        public virtual AreaInfo.AreaInfo Area { get; set; }
        /// <summary>
        /// 监控策略
        /// </summary>
        public virtual Guid? goods_monitor_id { get; set; }
        [ForeignKey("goods_monitor_id")]
        public virtual StrategyMonitor.StrategyMonitor StrategyMonitor { get; set; }
        /// <summary>
        /// 上架策略
        /// </summary>
        public virtual Guid? goods_warehousing_id { get; set; }
        [ForeignKey("goods_warehousing_id")]
        public virtual StrategyWarehousing.StrategyWarehousing StrategyWarehousing { get; set; }
        /// <summary>
        /// 分配策略
        /// </summary>
        public virtual Guid? goods_distribution_id { get; set; }
        [ForeignKey("goods_distribution_id")]
        public virtual StrategyDistribution.StrategyDistribution StrategyDistribution { get; set; }
        /// <summary>
        /// 垛型
        /// </summary>
        public virtual Guid? goods_pack_id { get; set; }
        [ForeignKey("goods_pack_id")]
        public virtual PackInfo.PackInfo Pack { get; set; }
        #endregion
    }
}
