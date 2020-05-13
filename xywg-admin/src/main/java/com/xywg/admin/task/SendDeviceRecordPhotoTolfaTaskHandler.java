package com.xywg.admin.task;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.xywg.admin.core.util.DateUtils;
import com.xywg.admin.modular.smz.model.IfaLabor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xywg.admin.modular.device.service.IDeviceRecordService;
import com.xywg.admin.modular.smz.service.IfaLaborService;
import com.xywg.admin.modular.smz.utils.Constant;
import com.xywg.admin.modular.smz.utils.HttpClientUtils;
import net.sf.json.JSONObject;

/**
 * 发送图片数据给实名制
 * @author duanfen
 * 
 *
 */
@JobHandler(value="SendDeviceRecordPhotoTolfaTaskHandler")
@Component
public class SendDeviceRecordPhotoTolfaTaskHandler extends IJobHandler {

	@Autowired
	private IfaLaborService ifaLaborService;

	@Autowired
	private IDeviceRecordService deviceRecordService;

	private static final Log LOG = LogFactory.getLog(SendDeviceRecordPhotoTolfaTaskHandler.class);
	
	private static Properties systemParams = new Properties();
	
	/**
	 * 加载配置文件
	 */
	static {
		try {
			systemParams.load(SendDeviceRecordPhotoTolfaTaskHandler.class.getClassLoader().getResourceAsStream("smz.properties"));
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
	protected Map<String,String> loginSMZ() throws Exception {
		String httpUrlPrefix = this.getSystemStringParam("httpUrlPrefixImage");
		String loginUrl = this.getSystemStringParam("loginUrlImage");
		String userName = this.getSystemStringParam("userNameImage");
		String password = this.getSystemStringParam("passwordImage");
		Map<String,Object> map = new HashMap<>();
		map.put("userName", userName);
		map.put("password", password);


		Map<String, String> ref = new HashMap<>();
		ref.put("Referer", this.getSystemStringParam("httpUrlReferer"));

		// 发送信息
		String result = HttpClientUtils.post(httpUrlPrefix + loginUrl, map, ref);
		//String result = HttpClientUtils.post(httpUrlPrefix + loginUrl, map);

		if (!result.contains("token")) {
			throw new Exception("登陆实名制失败");
		}
		Map<String,Object> m = (Map<String,Object>) JSONObject.toBean(JSONObject.fromObject(result), Map.class);
		Map<String,String> headers = new HashMap<>();
		headers.put("token", (String) m.get("token"));
		return headers;
	}
	
    @Override
    public ReturnT<String> execute(String s) throws Exception {
    	Map<String, String> m = loginSMZ();

		//发送考勤数据对接到实名制
	/*	int recordImage=ifaLaborService.getLastNumberByTableName(Constant.BUSS_DEVICE_RECORD_IMAGE);
		deviceRecordService.getDeviceRecordImageFromLabor((Long) recordImage,m);*/

		m.put("Referer", this.getSystemStringParam("httpUrlReferer"));
		String pres = DateUtils.getPreMonth();
		// 获取当月的后缀
			String suffix = DateUtils.getTableSuffix();
		// 每个月只有一号处理上个月数据，2号以后不在处理
		String day = DateUtils.getDay();
		LOG.info("当天日期=" + day);
		if (day.equals("01")) {
			// 处理上个月的数据
			dealBySuffix(m, pres);
		}
		// 处理当月数据
		dealBySuffix(m, suffix);
        return SUCCESS;
    }


	public void dealBySuffix(Map<String, String> m, String suffix) throws Exception {
		String deviceRecord = Constant.BUSS_DEVICE_RECORD_IMAGE + "_" + suffix;
		// 当月没有数据新增一条当月记录
		Long lastNumber = ifaLaborService.getLastNumber(deviceRecord);
		if (lastNumber == null) {
			lastNumber = 0L;
			IfaLabor ifa = new IfaLabor(deviceRecord, 0L);
			this.ifaLaborService.insert(ifa);
		}
		deviceRecordService.getDeviceRecordImageFromLabor(lastNumber, m, deviceRecord);
	}

}
