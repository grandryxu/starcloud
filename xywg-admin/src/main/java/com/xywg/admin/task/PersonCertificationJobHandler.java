package com.xywg.admin.task;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xywg.admin.modular.company.service.IPersonalCertificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 人员资格证书
 * @author wangcw
 */
@JobHandler(value="PersonCertificationJobHandler")
@Component
public class PersonCertificationJobHandler extends IJobHandler {


    @Autowired
    private IPersonalCertificationsService personalCertificationsService;


	@Override
    public ReturnT<String> execute(String param) throws Exception {
        personalCertificationsService.updateStatus();
		return SUCCESS;
	}

}