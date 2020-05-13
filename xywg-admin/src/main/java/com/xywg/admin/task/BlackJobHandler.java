package com.xywg.admin.task;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xywg.admin.modular.bad.service.IWorkerBlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 黑名单
 * @author wangcw
 */
@JobHandler(value="BlackJobHandler")
@Component
public class BlackJobHandler extends IJobHandler {


    @Autowired
    private IWorkerBlackListService workerBlackListService;

	@Override
    public ReturnT<String> execute(String param) throws Exception {
        this.workerBlackListService.updateBlack();
		return SUCCESS;
	}

}