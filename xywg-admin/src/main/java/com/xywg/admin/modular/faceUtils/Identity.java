package com.xywg.admin.modular.faceUtils;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.xywg.admin.core.common.exception.BizExceptionEnum;
import com.xywg.admin.core.exception.XywgException;

/**
 * 身份实名认证
 * 
 * @author duanfen
 * @date 2018年1月18日 上午9:54:43
 * @重要提示如下: HttpUtils请从 https://github.com/aliyun/api-gateway-demo-sign
 *          -java/blob/master/src
 *          /main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java 下载
 *          相应的依赖请参照 https://github.com/aliyun/api-gateway-demo-sign-java/blob
 *          /master/pom.xml
 */
public class Identity {

//	private static Logger logger = LoggerFactory.getLogger(Identity.class);

	private static final String host = "http://1.api.apistore.cn";

	private static final String path = "/idcard2";

	private static final String method = "GET";

	private static final String appCode = "23655baee8444d2fa72f2d0eeb230741";

	/**
	 * 实名认证(上海加数)
	 * 
	 * @param cardNo
	 *            身份证
	 * @param realName
	 *            名字
	 * @return boolean
	 * @author duanfen
	 * @throws Exception
	 */
	public static Map<String, Object> validByCard(String cardNo, String realName) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		boolean flag = false;
		Map<String, String> headers = new HashMap<String, String>();
		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE
		headers.put("Authorization", "APPCODE " + appCode);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("cardNo", cardNo);
		querys.put("realName", realName);
		// 获取response的body
		HttpResponse response;
		String result = null;
		try {
			response = HttpUtil.doGet(host, path, method, headers,querys);
			result = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			throw new XywgException(BizExceptionEnum.AUTH_PERSONNEL_FAILED);
		}
		if (result == null || result == "") {
			throw new XywgException(BizExceptionEnum.AUTH_PERSONNEL_FAILED);
		}
		Map<String, Object> map;
		try {
			map = JsonUtil.jsonToMap(new JSONObject(result));
			// 判断是否提交成功
			if (map.get("error_code").toString().equals("0")) {
				// 获取返回参数
				map = JsonUtil.jsonToMap(new JSONObject(map.get("result").toString()));
				// 判断是否成功
				if (map.get("isok").toString().equals("1")) {
					flag = true;
				}
			}
		} catch (Exception e) {
			throw new XywgException(BizExceptionEnum.AUTH_PERSONNEL_FAILED);
		}
		String reason = "";
		if (!flag) {
			reason = (String) map.get("reason");
			throw new XywgException(700,reason);
		}
		//返回实名认证结果
		returnMap.put("flag", flag);
		returnMap.put("reason", reason);
		return returnMap;
	}
	
	
	/**
	 * 实名认证(苏州云亿互通信息服务有限公司)
	 * 
	 * @param cardNo
	 *            身份证
	 * @param realName
	 *            名字
	 * @return boolean
	 * @author duanfen
	 * @throws Exception
	 */
//	public static void validByCard(String cardNo, String realName) {
//	    Map<String, String> headers = new HashMap<String, String>();
//	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
//	    headers.put("Authorization", "APPCODE " + Constant.IDENTIFIED_APPCODE);
//	    //根据API的要求，定义相对应的Content-Type
//	    headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//	    Map<String, String> querys = new HashMap<String, String>();
//	    Map<String, String> bodys = new HashMap<String, String>();
//	    bodys.put("idNo", cardNo);
//	    bodys.put("name", realName);
//	    String resultMessage = "";
//	    try {
//	    	HttpResponse response = HttpUtils.doPost(Constant.IDENTIFIED_HOST, Constant.IDENTIFIED_PATH, Constant.IDENTIFIED_METHOD, headers, querys, bodys);
//	    	//获取response的body
//	    	String result = EntityUtils.toString(response.getEntity());
//	    	System.out.println(result);
//	    	if (result == null || result == "") {
//				throw new XywgException(BizExceptionEnum.AUTH_PERSONNEL_FAILED);
//			}
//	    	Map<String, Object> map;
//			map = JsonUtil.jsonToMap(new JSONObject(result));
//			String respCode = (String)map.get("respCode");
//			// 判断返回信息
//			if (!respCode.equals("0000")) {
//				resultMessage = (String)map.get("respMessage");
//				throw new XywgException(700,resultMessage);
//			}
//	    } catch (Exception e) {
//	    	throw new XywgException(700,resultMessage);
//	    }
//	}
//	
	

//	public static void main(String[] args) throws Exception {
//		try {
//			Identity.validByCard("430725197608087720", "曾玉香");
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
