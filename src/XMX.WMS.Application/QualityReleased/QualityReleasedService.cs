using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.QualityReleased.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using System.Collections.Generic;
using XMX.WMS.InventoryInfo.Dto;
using Abp.Application.Services.Dto;
using XMX.WMS.Base.Session;

namespace XMX.WMS.QualityReleased
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-03-19 13:27:36
    /// 描 述：
    ///</summary>
    public class QualityReleasedService : AsyncCrudAppService<QualityReleased, QualityReleasedDto, Guid, QualityReleasedPagedRequest, QualityReleasedCreateDto, QualityReleasedUpdateDto>, IQualityReleasedService
    {

        private readonly IRepository<InventoryInfo.InventoryInfo, Guid> InventoryRepository;
        private readonly IRepository<QualityInfo.QualityInfo, Guid> QualityRepository;
        private readonly Guid UserCompanyId;
        public QualityReleasedService(IRepository<QualityReleased, Guid> repository,
            IRepository<InventoryInfo.InventoryInfo, Guid> inventoryRepository, IRepository<QualityInfo.QualityInfo, Guid> qualityRepository) : base(repository)
        {
            InventoryRepository = inventoryRepository;
            QualityRepository = qualityRepository;
            UserCompanyId = AbpSession.GetCompanyId();
        }
        /// <summary>
        /// 获取待抽检放行的库存
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public PagedResultDto<InventoryInfo.InventoryInfo> GetWaitForReleasedInventory(ReleasedInventoryRequestDto input)
        {
            var results = InventoryRepository.GetAllIncluding(x => x.Goods, x => x.Quality)
                           .WhereIf(AbpSession.UserId != 1, x => x.inventory_company_id == UserCompanyId)
                           .WhereIf(!input.inventory_bill_bar.IsNullOrWhiteSpace(), x => x.inventory_box_code.Contains(input.inventory_bill_bar))
                           .WhereIf(input.inventory_slot_code != Guid.Empty, x => x.inventory_slot_code.ToString().Contains(input.inventory_slot_code.ToString()))
                           .WhereIf(!input.goods_name.IsNullOrWhiteSpace(), x => x.inventory_goods_id != null && x.Goods.goods_name.Contains(input.goods_name))
                           .WhereIf(input.inventory_quality_status != Guid.Empty, x => x.inventory_quality_status == input.inventory_quality_status)
                           .WhereIf(!input.inventory_batch_no.IsNullOrWhiteSpace(), x => x.inventory_batch_no.Contains(input.inventory_batch_no));
            var count = results.Count();
            var objs = results.Skip(input.SkipCount).Take(input.MaxResultCount)
              .ToList();
            return new PagedResultDto<InventoryInfo.InventoryInfo>(count, objs);
        }

        /// <summary>
        /// 将放行后的库存对应的状态更新
        /// </summary>
        /// <param name="guids"></param>
        /// <returns></returns>
        public int UpdateReleasedInventory(List<Guid> guids)
        {
            int updateTotal = 0;
            try
            {
                //获取质量状态表中质量名为“合格”的质量状态
                QualityInfo.QualityInfo info = QualityRepository.FirstOrDefault(x => x.quality_name.Equals("合格") && x.quality_company_id == UserCompanyId);
                if (info != null)
                    guids.ForEach(item =>
                    {
                        InventoryRepository.Update(item, x => x.inventory_quality_status = info.Id);
                        updateTotal++;
                    });
                return updateTotal;
            }
            catch(Exception)
            {
                return -1;
            }
        }

    }
}
