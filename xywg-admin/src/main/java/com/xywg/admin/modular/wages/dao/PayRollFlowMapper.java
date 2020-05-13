package com.xywg.admin.modular.wages.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.modular.wages.model.PayRollFlow;
import com.xywg.admin.modular.wages.model.PayRollFlowVo;
import com.xywg.admin.modular.wages.model.Settlement;
import com.xywg.admin.modular.wages.model.SettlementDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 工资流水表 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-01
 */
public interface PayRollFlowMapper extends BaseMapper<PayRollFlow> {

    List<PayRollFlowVo> getPayRollFlowByIdCardAndIdNumber(@Param("idCardType") String idCardType, @Param("idCardNumber") String idCardNumber);

    void  insertBySettlement(@Param("list")List<SettlementDetail> list,@Param("createUser")String createUser);
}
