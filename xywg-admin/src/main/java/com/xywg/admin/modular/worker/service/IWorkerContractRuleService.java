package com.xywg.admin.modular.worker.service;

import com.xywg.admin.modular.worker.model.WorkerContractRule;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 项目中工人劳动合同信息 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-08
 */
public interface IWorkerContractRuleService extends IService<WorkerContractRule> {

    /**
     *@param m 
     * @Description: 发送工人合同数据到实名制
     *@Author xieshuaishuai
     *@Date 2018/7/12 10:00
     */
    boolean getWorkeContractorFromLabor(List<Long> ids, Map<String, String> m) throws Exception;
}
