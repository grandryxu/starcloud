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
import org.springframework.scheduling.annotation.Scheduled;
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
 * 南通通用数据
 * 拉实名制数据
 *
 *
 * @author jln
 * @date 2019/4/11
 */
@JobHandler(value = "SmzNtGeneralDataJobHandler")
@Component
public class SmzNtGeneralDataJobHandler extends IJobHandler {
    private static Logger log = LoggerFactory.getLogger(SmzNtGeneralDataJobHandler.class);
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
    private static final Log LOG = LogFactory.getLog(SmzNtGeneralDataJobHandler.class);

    private static Properties systemParams = new Properties();

    /**
     * 加载配置文件
     */
    static {
        try {
            systemParams.load(SmzNtGeneralDataJobHandler.class.getClassLoader().getResourceAsStream("smz.properties"));
        } catch (IOException e) {
            LOG.error("smz.properties" + "配置文件加载失败");
        }
    }

    /**
     * @param key
     * @return
     * @description 获取配置文件具体信息
     * @author jln
     * @date 2019年4月11日
     */
    protected String getSystemStringParam(String key) {
        return systemParams.getProperty(key);
    }

    /**
     * @return
     * @description 登录实名制获取token
     * @author jln
     * @date 2019年4月11日
     */
    @SuppressWarnings("unchecked")
    protected Map<String, String> loginSMZ() {
        String httpUrlPrefix = this.getSystemStringParam("httpUrlPrefix");
        String loginUrl = this.getSystemStringParam("loginUrl");
        String userName = this.getSystemStringParam("userName");
        String password = this.getSystemStringParam("password");
        Map<String, Object> map = new HashMap<>();
        map.put("userName", userName);
        map.put("password", password);
        String result = HttpClientUtils.post(httpUrlPrefix + loginUrl, map);
        Map<String, Object> m = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(result), Map.class);
        Map<String, String> headers = new HashMap<>();
        headers.put("token", (String) m.get("token"));
        return headers;
    }

    /**
     * 登陆实名制 姜丽娜处 2019/4/18
     *
     * @return
     */
	/*protected Map<String, String> loginSMZForInfo() {
		String httpUrlPrefix = this.getSystemStringParam("httpUrlPrefixLogin");
		String loginUrl = this.getSystemStringParam("httpUrlPrefixLoginUrl");
		String userName = this.getSystemStringParam("httpUrlPrefixLoginUser");
		String password = this.getSystemStringParam("httpUrlPrefixLoginPass");
		Map<String,Object> map = new HashMap<>();
		map.put("userName", userName);
		map.put("password", password);
		String result = HttpClientUtils.post(httpUrlPrefix + loginUrl, map);
		Map<String,Object> m = (Map<String,Object>) JSONObject.toBean(JSONObject.fromObject(result), Map.class);
		Map<String,String> headers = new HashMap<>();
		headers.put("token", (String) m.get("token"));
		return headers;
	}*/

//    @Override
    public ReturnT<String> execute(String s) throws Exception {
        Map<String, String> mnt = loginSMZ();
        //获取项目id
        try {
            projectMasterService.saveCompanyFromSMZTY(mnt);
        } catch (Exception e) {
            log.error(e.getMessage());
        }//企业下面的项目id（南通）
        try {
            projectMasterService.saveProjectFromSMZByCor(mnt);
        } catch (Exception e) {
            log.error(e.getMessage());
        }//企业下面的项目id（南通）
        //    try {
        //        projectMasterService.saveProjectFromSMZTY(mnt);
        //    } catch (Exception e) {
        //        log.error(e.getMessage());
        //    }//通过项目id项目信息（南通）
        try {
            deviceService.saveDeviceFromSMZTY(mnt);
        } catch (Exception e) {
            log.error(e.getMessage());
        }//考勤设备（南通）
        try {
            teamMasterService.saveTeamMasterFromSMZTY(mnt);
        } catch (Exception e) {
            log.error(e.getMessage());
        }//班组信息（南通）
        try {
            projectWorkerService.saveProjectWorkerInformation(mnt);
        } catch (Exception e) {
            log.error(e.getMessage());
        }//项目人员
        //  try{entryExitHistoryService.savePersonJoinFromSMZTY(mnt);}catch(Exception e) {log.error(e.getMessage()); }//进退场（南通）
        try {
            deviceRecordServiceImpl.saveDeviceRecordFromSMZTY(mnt);
        } catch (Exception e) {
            log.error(e.getMessage());
        }//考勤（南通）
        return null;
//        return SUCCESS;
    }


    public ReturnT<String> execute1(String s) throws Exception {
//    	Map<String, String> mnt = loginSMZ();
        //try{projectMasterService.saveCompanyFromSMZTY(mnt);}catch(Exception e) {log.error(e.getMessage()); }//企业下面的项目id（南通）
        //try{projectMasterService.saveProjectFromSMZByCor(mnt);}catch(Exception e) {log.error(e.getMessage()); }//企业下面的项目id（南通）
        //try{projectMasterService.saveProjectFromSMZTY(mnt);}catch(Exception e) {log.error(e.getMessage()); }//通过项目id项目信息（南通）
        //try{deviceService.saveDeviceFromSMZTY(mnt);}catch(Exception e) {log.error(e.getMessage()); }//考勤设备（南通）
        //try{teamMasterService.saveTeamMasterFromSMZTY(mnt);}catch(Exception e) {log.error(e.getMessage()); }//班组信息（南通）
//        try{projectWorkerService.saveProjectWorkerInformation(mnt);}catch(Exception e) {log.error(e.getMessage()); }//项目人员（姜丽娜）
//        try{entryExitHistoryService.savePersonJoinFromSMZTY(mnt);}catch(Exception e) {log.error(e.getMessage()); }//进退场（南通）
//        try{deviceRecordServiceImpl.saveOldDeviceRecordFromSMZTY(mnt);}catch(Exception e) {log.error(e.getMessage()); }//考勤（南通）        
        return null;
//        return SUCCESS;
    }
}
