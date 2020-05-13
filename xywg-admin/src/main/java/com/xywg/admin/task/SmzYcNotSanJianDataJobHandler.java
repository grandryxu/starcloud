package com.xywg.admin.task;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xywg.admin.modular.company.service.ISubContractorService;
import com.xywg.admin.modular.project.service.IProjectMasterService;
import com.xywg.admin.modular.project.service.IProjectWorkerService;
import com.xywg.admin.modular.smz.utils.HttpClientUtils;
import com.xywg.admin.modular.team.service.ITeamMasterService;

import net.sf.json.JSONObject;

/**
 *
 * 盐城版企业版
 *
 * @author chupp
 * @date 2018/7/26
 */
@JobHandler(value="SmzYcNotSanJianDataJobHandler")
@Component
public class SmzYcNotSanJianDataJobHandler extends IJobHandler {
	private static Logger log = LoggerFactory.getLogger(SmzYcNotSanJianDataJobHandler.class);
	@Autowired
    private ISubContractorService subContractorService;
    @Autowired
    private IProjectMasterService projectMasterService;

    @Autowired
    private IProjectWorkerService projectWorkerService;

    @Autowired
    private ITeamMasterService teamMasterService;
    
    private static final Log LOG = LogFactory.getLog(SmzYcNotSanJianDataJobHandler.class);
	
	private static Properties systemParams = new Properties();
	
	/**
	 * 加载配置文件
	 */
	static {
		try {
			systemParams.load(SmzYcNotSanJianDataJobHandler.class.getClassLoader().getResourceAsStream("smz.properties"));
		} catch (IOException e) {
			LOG.error("smz.properties" + "配置文件加载失败");
		}
	}
	
	/**
	 * 
	 * @description 获取配置文件具体信息
	 * @author chupp
	 * @date 2018年4月27日
	 * @param key
	 * @return
	 *
	 */
	protected String getSystemStringParam(String key) {
		return systemParams.getProperty(key);
	}
	
	/**
	 * 
	 * @description 登录实名制获取token
	 * @author chupp
	 * @date 2018年4月27日
	 * @return
	 *
	 */
	@SuppressWarnings("unchecked")
	protected Map<String,String> loginSMZ() {
		String httpUrlPrefix = this.getSystemStringParam("httpUrlPrefix");
		String loginUrl = this.getSystemStringParam("loginUrl");
		String userName = this.getSystemStringParam("userName");
		String password = this.getSystemStringParam("password");
		Map<String,Object> map = new HashMap<>();
		map.put("userName", userName);
		map.put("password", password);
		String result = HttpClientUtils.post(httpUrlPrefix + loginUrl, map);
		Map<String,Object> m = (Map<String,Object>) JSONObject.toBean(JSONObject.fromObject(result), Map.class);
		Map<String,String> headers = new HashMap<>();
		headers.put("token", (String) m.get("token"));
		return headers;
	}
	
	/**
	 * 
	 * @description 登录实名制获取token
	 * @author chupp
	 * @date 2018年4月27日
	 * @return
	 *
	 */
	@SuppressWarnings("unchecked")
	protected Map<String,String> loginSMZYC() {
		String httpUrlPrefix = this.getSystemStringParam("httpUrlPrefixYC");
		String loginUrl = this.getSystemStringParam("loginUrlYC");
		String userName = this.getSystemStringParam("userNameYC");
		String password = this.getSystemStringParam("passwordYC");
		Map<String,Object> map = new HashMap<>();
		map.put("userName", userName);
		map.put("password", password);
		String result = HttpClientUtils.post(httpUrlPrefix + loginUrl, map);
		Map<String,Object> m = (Map<String,Object>) JSONObject.toBean(JSONObject.fromObject(result), Map.class);
		Map<String,String> headers = new HashMap<>();
		headers.put("token", (String) m.get("token"));
		return headers;
	}
	
    @Override
    public ReturnT<String> execute(String s) throws Exception {
    	Map<String, String> myc = loginSMZYC();
    	
    	try{subContractorService.saveCompanyFromSMZCompanyYC(myc);}catch(Exception e) {log.error(e.getMessage()); }//企业信息（盐城企业版）
    	try{projectMasterService.saveProjectFromSMZCompanyYC(myc);}catch(Exception e) {log.error(e.getMessage()); }//项目信息（盐城企业版）
    	try{teamMasterService.saveTeamMasterFromSMZCompanyYC(myc);}catch(Exception e) {log.error(e.getMessage()); }//班组信息（盐城企业版）
    	try{projectWorkerService.saveProjectPersonnelFromSMZCompanyYC(myc);}catch(Exception e) {log.error(e.getMessage()); }//项目人员（盐城企业版）
      
        return SUCCESS;
    }
}
