package com.xywg.admin.modular.worker.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.modular.smz.model.WorkerContractorRuleMo;
import com.xywg.admin.modular.worker.model.WorkerContractRule;

import java.util.List;

/**
 * <p>
 * 项目中工人劳动合同信息 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-08
 */
public interface WorkerContractRuleMapper extends BaseMapper<WorkerContractRule> {

    /**
     *@Description: 发送工人合同数据到实名制
     *@Author xieshuaishuai
     *@Date 2018/7/12 9:45
     */
    List<WorkerContractorRuleMo> getWorkeContractorFromLabor(List<Long> ids);

}
