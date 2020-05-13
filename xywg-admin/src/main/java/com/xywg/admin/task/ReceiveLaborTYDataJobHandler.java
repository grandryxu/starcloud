package com.xywg.admin.task;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xywg.admin.modular.smz.service.IReceiveLaborTYDataJobService;
import com.xywg.admin.modular.smz.utils.HttpClientUtils;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.xxl.job.core.biz.model.ReturnT.SUCCESS;
/*
拉通用劳务通考勤数据
*/
@JobHandler(value="ReceiveLaborTYDataJobHandler")
@Component
public class ReceiveLaborTYDataJobHandler extends IJobHandler {

    private static Logger log = LoggerFactory.getLogger(ReceiveLaborTYDataJobHandler.class);

    private static Properties systemParams = new Properties();

    private static final Log LOG = LogFactory.getLog(ReceiveLaborTYDataJobHandler.class);

    @Autowired
    private IReceiveLaborTYDataJobService receiveLaborTYDataJobService;

    /**
     * 加载配置文件
     */
    static {
        try {
            systemParams.load(ReceiveLaborTYDataJobHandler.class.getClassLoader().getResourceAsStream("laborty.properties"));
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
    protected Map<String, Object> loginSMZ() {
        String httpUrlPrefix = this.getSystemStringParam("httpUrlPrefixNew");
        String loginUrl = this.getSystemStringParam("loginUrl");
        String userName = this.getSystemStringParam("userName");
        String password = this.getSystemStringParam("password");
        Map<String, Object> map = new HashMap<>();
        map.put("username", userName);
        map.put("password", password);
        //发送登录信息
        String result = HttpClientUtils.post(httpUrlPrefix + loginUrl, map);
        Map<String, Object> m = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(result), Map.class);
        Map<String, Object> headers = new HashMap<>();
        headers.put("token", (String) m.get("token"));
        return headers;
    }


    public ReturnT<String> execute(String s) throws Exception {
        Map<String, Object> mnt = loginSMZ();
        //项目
        try {
            receiveLaborTYDataJobService.saveProjectFromLaborTYNew(mnt);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        //考勤设备（劳务通通用）
        try {
            receiveLaborTYDataJobService.saveDeviceFromLaborTY(mnt);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        //班组信息（劳务通通用）
        try {
            receiveLaborTYDataJobService.saveTeamMasterFromLaborTY(mnt);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        //人员基础信息（劳务通通用）
        try {
            receiveLaborTYDataJobService.saveWorkerFromLaborTY(mnt);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        //项目人员（劳务通通用）
        try {
            receiveLaborTYDataJobService.saveProjectWorkerFromLaborTY(mnt);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        //考勤（劳务通通用)
        try {
            receiveLaborTYDataJobService.saveDeviceRecordFromLaborTY(mnt);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        //receiveLaborTYDataJobService.(mnt);

        //项目地址

  /*      try {
            receiveLaborTYDataJobService.saveProjectAddress(mnt);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
*/
        return SUCCESS;
    }


}
