package com.xywg.admin.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.xywg.admin.modular.company.dao.SubContractorMapper;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.company.service.IEntryExitHistoryService;
import com.xywg.admin.modular.device.service.IDeviceRecordService;
import com.xywg.admin.modular.device.service.IDeviceService;
import com.xywg.admin.modular.project.service.IProjectMasterService;
import com.xywg.admin.modular.project.service.IProjectTrainRecordService;
import com.xywg.admin.modular.project.service.IProjectTrainingService;
import com.xywg.admin.modular.project.service.IProjectWorkerService;
import com.xywg.admin.modular.smz.utils.HttpClientUtils;
import com.xywg.admin.modular.team.service.ITeamMasterService;
import com.xywg.admin.modular.wages.wrapper.AccountWarpper;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;

import net.sf.json.JSONObject;

/**
 *
 * 南通三建数据（包含另外两家公司）
 *
 * @author chupp
 * @date 2018/7/26
 */
@JobHandler(value="SmzNtSanJianDataJobHandler")
@Component
public class SmzNtSanJianDataJobHandler extends IJobHandler {
	private static  Logger log = LoggerFactory.getLogger(SmzNtSanJianDataJobHandler.class);
	@Autowired
    private IWorkerMasterService workerMasterService;
    @Autowired
    private SubContractorMapper subContractorMapper;
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
    private static final Log LOG = LogFactory.getLog(SmzNtSanJianDataJobHandler.class);
	
	private static Properties systemParams = new Properties();
	
	/**
	 * 加载配置文件
	 */
	static {
		try {
			systemParams.load(SmzNtSanJianDataJobHandler.class.getClassLoader().getResourceAsStream("smz.properties"));
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
    	Map<String, String> mnt = loginSMZ();
    	
    	SubContractor s1 = subContractorMapper.selectById(119);
    	SubContractor s2 = subContractorMapper.selectById(163);
    	SubContractor s3 = subContractorMapper.selectById(164);
    	List<SubContractor> list = new ArrayList<SubContractor>();
    	list.add(s1);
    	list.add(s2);
    	list.add(s3);
    	
//        try{projectMasterService.saveProjectFromSMZNT(mnt,list);}catch(Exception e) {log.error(e.getMessage()); }//项目信息（南通）
//        try{deviceService.saveDeviceFromSMZNT(mnt,list);}catch(Exception e) {log.error(e.getMessage()); }//考勤设备（南通）
//        try{projectTrainingService.saveTrainFromSMZNT(mnt);}catch(Exception e) {log.error(e.getMessage()); }//培训信息（南通）
        try{workerMasterService.savePersonnelFromSMZNT(mnt);}catch(Exception e) {log.error(e.getMessage()); }//人员信息（南通）
//        try{teamMasterService.saveTeamMasterFromSMZNT(mnt,list);}catch(Exception e) {log.error(e.getMessage()); }//班组信息（南通）
//        try{projectTrainingService.saveTrainAttachmentFromSMZNT(mnt);}catch(Exception e) {log.error(e.getMessage()); }//培训附件（南通）
//        try{projectTrainRecordService.saveTrainRecordFromSMZNT(mnt);}catch(Exception e) {log.error(e.getMessage()); }//项目培训记录（南通）
//        try{projectWorkerService.saveProjectPersonnelFromSMZNT(mnt,list);}catch(Exception e) {log.error(e.getMessage()); }//项目人员（南通）
//        try{teamMasterService.saveLaborContractFromSMZNT(mnt,list);}catch(Exception e) {log.error(e.getMessage()); }//劳动合同（南通）
//        try{entryExitHistoryService.savePersonJoinFromSMZNT(mnt,list);}catch(Exception e) {log.error(e.getMessage()); }//进退场（南通）
//        try{deviceRecordServiceImpl.saveDeviceRecordFromSMZNT(mnt,list);}catch(Exception e) {log.error(e.getMessage()); }//考勤（南通）

        return SUCCESS;
    }
}
