package com.xywg.admin.modular.wages.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.modular.wages.dao.PayRollFlowMapper;
import com.xywg.admin.modular.wages.model.PayRollFlow;
import com.xywg.admin.modular.wages.model.PayRollFlowVo;
import com.xywg.admin.modular.wages.model.Settlement;
import com.xywg.admin.modular.wages.model.SettlementDetail;
import com.xywg.admin.modular.wages.service.IPayRollFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 工资流水表 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-10
 */
@Service
public class PayRollFlowServiceImpl extends ServiceImpl<PayRollFlowMapper, PayRollFlow> implements IPayRollFlowService {
    @Autowired
    private PayRollFlowMapper payRollFlowMapper;

    @Override
    public Map<String,List<PayRollFlowVo>> getPayRollFlowByIdCardAndIdNumber(String idCard, String idNumber) {
        List<PayRollFlowVo> list = baseMapper.getPayRollFlowByIdCardAndIdNumber(idCard ,idNumber );
        Map<String,List<PayRollFlowVo>> map = new HashMap<String,List<PayRollFlowVo>>();
        for(PayRollFlowVo payRollFlowVo:list){
            if(map.get(payRollFlowVo.getProjectCode()) == null){
                List<PayRollFlowVo> payRollFlowVoList = new ArrayList<PayRollFlowVo>();
                payRollFlowVoList.add(payRollFlowVo);
                map.put(payRollFlowVo.getProjectCode() ,payRollFlowVoList);
            }else{
                map.get(payRollFlowVo.getProjectCode()).add(payRollFlowVo);
            }
        }
        return map;
    }

    @Override
    public void insertBySettlement(List<SettlementDetail> list,String userName) {
        payRollFlowMapper.insertBySettlement(list,userName);
    }
}
