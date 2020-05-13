package com.xywg.admin.task;

import java.io.IOException;
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
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.company.service.ISubContractorService;
import com.xywg.admin.modular.device.service.IDeviceRecordService;
import com.xywg.admin.modular.device.service.IDeviceService;
import com.xywg.admin.modular.project.service.IProjectMasterService;
import com.xywg.admin.modular.project.service.IProjectTrainingService;
import com.xywg.admin.modular.smz.model.IfaLabor;
import com.xywg.admin.modular.smz.service.IfaLaborService;
import com.xywg.admin.modular.system.service.IWorkKindService;
import com.xywg.admin.modular.team.service.ITeamMasterService;
import com.xywg.admin.modular.worker.service.IWorkerContractRuleService;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;
import com.xywg.admin.modular.zr.model.ZrCompanyTpm;
import com.xywg.admin.modular.zr.service.IZrInterfaceService;
import com.xywg.admin.modular.zr.utils.HttpClientUtils;

import net.sf.json.JSONObject;

@JobHandler(value = "SendDataToZRTaskHandler")
@Component
public class SendDataToZRTaskHandler extends IJobHandler {

    private static Logger log = LoggerFactory.getLogger(SendDataToZRTaskHandler.class);

    private static Properties systemParams = new Properties();
    @Autowired
    private IZrInterfaceService zrInterfaceService;

    /**
     * 加载配置文件
     */
    static {
        try {
            systemParams.load(SendDataToZRTaskHandler.class.getClassLoader().getResourceAsStream("zr.properties"));
        } catch (IOException e) {
            log.error("zr.properties" + "配置文件加载失败");
        }
    }

    /**
     * 获取配置文件信息
     *
     * @param key
     * @return
     * @author jln
     */
    protected String getSystemStringParam(String key) {
        return systemParams.getProperty(key);
    }

    /**
     * @return
     * @description 登录中如获取token
     * @author jln
     */
    @SuppressWarnings("unchecked")
    protected Map<String, String> loginZR() {
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

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        Map<String, String> m = loginZR();
        String stoken = m.get("token");
        System.out.println(stoken);
//    	try{zrInterfaceService.SendCompanyInfo(stoken);}catch(Exception e) {log.error(e.getMessage()); }//企业信息
//    	try{zrInterfaceService.SendProjectInfo(stoken);}catch(Exception e) {log.error(e.getMessage()); }//项目信息
//    	try{zrInterfaceService.SendDeviceInfo(stoken);}catch(Exception e) {log.error(e.getMessage()); }//考勤设备信息
        try {
            zrInterfaceService.SendUserInfo(stoken);
        } catch (Exception e) {
            log.error(e.getMessage());
        }//人员信息
        try {
            zrInterfaceService.SendAttendance(stoken);
        } catch (Exception e) {
            log.error(e.getMessage());
        }//考勤信息
        /*try{zrInterfaceService.SendInjuryInfo(stoken);}catch(Exception e) {log.error(e.getMessage()); }//工伤信息*/
        return null;
    }

}
