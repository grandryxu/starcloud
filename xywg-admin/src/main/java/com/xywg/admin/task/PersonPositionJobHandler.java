package com.xywg.admin.task;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xywg.admin.modular.device.service.IPersonPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 安全帽管理
 * @author wangcw
 */
@JobHandler(value="PersonPositionJobHandler")
@Component
public class PersonPositionJobHandler extends IJobHandler {


    @Autowired
    private IPersonPositionService personService;

	@Override
    public ReturnT<String> execute(String param) throws Exception {
        personService.updatePersonPositionInfo();
        personService.insertRecord();
		return SUCCESS;
	}

}