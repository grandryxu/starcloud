package com.xywg.admin.task;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xywg.admin.modular.company.service.ISubContractorCertificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 企业资格证书
 * @author wangcw
 */
@JobHandler(value="SubContractorCertificationJobHandler")
@Component
public class SubContractorCertificationJobHandler extends IJobHandler {
    @Autowired
    private ISubContractorCertificationsService subContractorCertificationsService;


	@Override
    public ReturnT<String> execute(String param) throws Exception {
        subContractorCertificationsService.updateStatus();
		return SUCCESS;
	}

}