package com.xywg.admin.core.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xywg.admin.modular.smz.utils.HttpClientUtils;
import com.xywg.admin.task.SendDataTolfaTaskHandler;

import net.sf.json.JSONObject;

/**
 * 对接
 * <p>
 * Title: SmzUtils
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author duanfen
 * @date 2019年6月24日
 */
public class SmzUtils {
	private static final Log LOG = LogFactory.getLog(SmzUtils.class);

	private static Properties systemParams = new Properties();

	/**
	 * 加载配置文件
	 */
	static {
		try {
			systemParams.load(SendDataTolfaTaskHandler.class.getClassLoader().getResourceAsStream("smz.properties"));
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
	public static String getSystemStringParam(String key) {
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
	public static Map<String, String> loginSMZ() {
		String httpUrlPrefix = getSystemStringParam("httpUrlPrefix");
		String loginUrl = getSystemStringParam("loginUrl");
		String userName = getSystemStringParam("userName");
		String password = getSystemStringParam("password");
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
	 * 登录实名制图片接口
	 * <p>
	 * Title: loginImageSMZ
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @author duanfen
	 * @date 2019年6月25日
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> loginImageSMZ() {
		String httpUrlPrefix = getSystemStringParam("httpUrlPrefixImage");
		String loginUrl = getSystemStringParam("loginUrlImage");
		String userName = getSystemStringParam("userNameImage");
		String password = getSystemStringParam("passwordImage");
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
	 * 截取返回的数据
	 * <p>
	 * Title: textToLong
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @author duanfen
	 * @date 2019年6月25日
	 */
	public static Long textToLong(String jsonResult, Map<String, Object> result) {
		String msg = (String) result.get("msg");
		int start = msg.indexOf(":");
		String b = msg.substring(start + 1);
		Long deviceRecordId = Long.valueOf(b);
		return deviceRecordId;
	}

	public static String receiveName(String jsonResult, Map<String, Object> result) {
		String msg = (String) result.get("msg");
		int start = msg.indexOf(";");
		return msg.substring(start + 1);
	}
}
