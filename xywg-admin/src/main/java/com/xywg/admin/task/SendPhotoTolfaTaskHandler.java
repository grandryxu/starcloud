package com.xywg.admin.task;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xywg.admin.modular.smz.service.IfaLaborService;
import com.xywg.admin.modular.smz.utils.Constant;
import com.xywg.admin.modular.smz.utils.HttpClientUtils;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;
import net.sf.json.JSONObject;

/**
 * 发送图片数据给实名制
 * @author duanfen
 * 
 *
 */
@JobHandler(value="SendPhotoTolfaTaskHandler")
@Component
public class SendPhotoTolfaTaskHandler extends IJobHandler {

	@Autowired
	private IfaLaborService ifaLaborService;

	@Autowired
	private IWorkerMasterService workerMasterService;


	private static final Log LOG = LogFactory.getLog(SendPhotoTolfaTaskHandler.class);
	
	private static Properties systemParams = new Properties();
	
	/**
	 * 加载配置文件
	 */
	static {
		try {
			systemParams.load(SendPhotoTolfaTaskHandler.class.getClassLoader().getResourceAsStream("smz.properties"));
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
	
    @Override
    public ReturnT<String> execute(String s) throws Exception {
    	Map<String, String> m = loginSMZ();

		//发送工人项目关系数据对接到实名制
/*		int lastNumber=ifaLaborService.getLastNumberByTableName(Constant.BUSS_PROJECT_WORKER_IMAGE);
		workerMasterService.getPersonImageFromLabor(lastNumber,m);*/
		workerMasterService.getPersonImageFromLabor(m);
        return SUCCESS;
    }
}
