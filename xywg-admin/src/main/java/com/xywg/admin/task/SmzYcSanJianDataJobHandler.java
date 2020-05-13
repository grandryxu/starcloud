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
import com.xywg.admin.modular.company.service.IEntryExitHistoryService;
import com.xywg.admin.modular.device.service.IDeviceRecordService;
import com.xywg.admin.modular.device.service.IDeviceService;
import com.xywg.admin.modular.project.service.IProjectMasterService;
import com.xywg.admin.modular.project.service.IProjectTrainRecordService;
import com.xywg.admin.modular.project.service.IProjectTrainingService;
import com.xywg.admin.modular.project.service.IProjectWorkerService;
import com.xywg.admin.modular.smz.utils.HttpClientUtils;
import com.xywg.admin.modular.team.service.ITeamMasterService;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;

import net.sf.json.JSONObject;

/**
 *
 * 南通三建数据（盐城版不包含另外两家公司）
 *
 * @author chupp
 * @date 2018/7/26
 */
@JobHandler(value="SmzYcSanJianDataJobHandler")
@Component
public class SmzYcSanJianDataJobHandler extends IJobHandler {
	private static Logger log = LoggerFactory.getLogger(SmzYcSanJianDataJobHandler.class);
	@Autowired
    private IWorkerMasterService workerMasterService;
    @Autowired
    private IProjectMasterService projectMasterService;
    @Autowired
    private IDeviceService deviceService;
    @Autowired
    private IProjectTrainingService projectTrainingService;

    @Autowired
    private IProjectTrainRecordService projectTrainRecordService;

    @Autowired
    private IDeviceRecordService deviceRecordServiceImpl;

    @Autowired
    private IProjectWorkerService projectWorkerService;

    @Autowired
    private ITeamMasterService teamMasterService;
    @Autowired
    private IEntryExitHistoryService entryExitHistoryService;
    
    private static final Log LOG = LogFactory.getLog(SmzYcSanJianDataJobHandler.class);
	
	private static Properties systemParams = new Properties();
	
	/**
	 * 加载配置文件
	 */
	static {
		try {
			systemParams.load(SmzYcSanJianDataJobHandler.class.getClassLoader().getResourceAsStream("smz.properties"));
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
    	
        try{projectMasterService.saveProjectFromSMZYC(myc);}catch(Exception e) {log.error(e.getMessage()); }//项目信息（盐城）
        try{deviceService.saveDeviceFromSMZYC(myc);}catch(Exception e) {log.error(e.getMessage()); }//考勤设备（盐城）
        try{projectTrainingService.saveTrainFromSMZYC(myc);}catch(Exception e) {log.error(e.getMessage()); }//培训信息（盐城）
        try{workerMasterService.savePersonnelFromSMZYC(myc);}catch(Exception e) {log.error(e.getMessage()); }//人员信息（盐城）
        try{teamMasterService.saveTeamMasterFromSMZYC(myc);}catch(Exception e) {log.error(e.getMessage()); }//班组信息（盐城）
        try{projectTrainingService.saveTrainAttachmentFromSMZYC(myc);}catch(Exception e) {log.error(e.getMessage()); }//培训附件（盐城）
        try{projectTrainRecordService.saveTrainRecordFromSMZYC(myc);}catch(Exception e) {log.error(e.getMessage()); }//项目培训记录（盐城）
        try{projectWorkerService.saveProjectPersonnelFromSMZYC(myc);}catch(Exception e) {log.error(e.getMessage()); }//项目人员（盐城）
        try{teamMasterService.saveLaborContractFromSMZYC(myc);}catch(Exception e) {log.error(e.getMessage()); }//劳动合同（盐城）
        try{entryExitHistoryService.savePersonJoinFromSMZYC(myc);}catch(Exception e) {log.error(e.getMessage()); }//进退场（盐城）
        try{deviceRecordServiceImpl.saveDeviceRecordFromSMZYC(myc);}catch(Exception e) {log.error(e.getMessage()); }//考勤（盐城）
      
        return SUCCESS;
    }
}
