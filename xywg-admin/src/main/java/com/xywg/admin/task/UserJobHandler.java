package com.xywg.admin.task;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xywg.admin.modular.bad.service.IWorkerBlackListService;
import com.xywg.admin.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 账号失效
 * @author wangcw
 */
@JobHandler(value="UserJobHandler")
@Component
public class UserJobHandler extends IJobHandler {


    @Autowired
    private IUserService userService;

	@Override
    public ReturnT<String> execute(String param) throws Exception {
        this.userService.userExpire();
		return SUCCESS;
	}
}

