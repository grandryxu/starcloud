using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.WarehouseStock.Dto
{
    #region 查询参数传入dto
    public class WarehouseStockPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 仓库
        /// </summary>
        public Guid? warehouse_id { get; set; }
        /// <summary>
        /// 日期范围
        /// </summary>
        public int days_count { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(WarehouseStock))]
    public class WarehouseStockCreatedDto : BaseCreateDto
    {
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(WarehouseStock))]
    public class WarehouseStockUpdatedDto : BaseUpdateDto
    {
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(WarehouseStock))]
    public class WarehouseStockDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 库存数量
        /// </summary>
        public decimal warehouse_stock { get; set; }
        /// <summary>
        /// 库存日期
        /// </summary>
        public string warehouse_date { get; set; }
        /// <summary>
        ///创建时间
        /// </summary>
        public DateTime CreationTime { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid warehouse_id { get; set; }
        [ForeignKey("warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        #endregion
    }
    #endregion
}
