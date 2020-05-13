package com.xywg.admin.modular.wages.service;


import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.wages.model.PayRollFlow;
import com.xywg.admin.modular.wages.model.PayRollFlowVo;
import com.xywg.admin.modular.wages.model.Settlement;
import com.xywg.admin.modular.wages.model.SettlementDetail;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 工资流水表 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-10
 */
public interface IPayRollFlowService extends IService<PayRollFlow> {

    Map<String, List<PayRollFlowVo>> getPayRollFlowByIdCardAndIdNumber(String idCard, String idNumber);

    void  insertBySettlement(List<SettlementDetail> list,String userName);
}
