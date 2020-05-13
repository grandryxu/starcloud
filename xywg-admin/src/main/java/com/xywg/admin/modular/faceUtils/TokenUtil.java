package com.xywg.admin.modular.faceUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;

import com.xywg.admin.core.util.DateUtil;

import java.util.Hashtable;

/**
 * 用户缓存信息
 * @author duanfen
 *
 */
public class TokenUtil {

	private static Hashtable<String, String> accessToken = new Hashtable<String, String>();

	//存放安全帽检测token
	private static Hashtable<String, String> accessHatDetectToken = new Hashtable<String, String>();


	/**
	 * 缓存一个token
	 */
	public static void putToken(String token) {
		accessToken.put("accessToken", token);
		accessToken.put("time", DateUtil.getDay());
	}

	/**
	 * 缓存一个token
	 */
	public static String getToken() {
		long day = 0L;
		String token = accessToken.get("accessToken");
		String time = accessToken.get("time");
		// 判断时间是否为空
		if (StringUtils.isNotBlank(time)) {
			String nowTime = DateUtil.getDay();
			// 计算上次token缓存了多少天
			day = DateUtil.getDaySub(time, nowTime);
		}
		// token为空/缓存时间为空/缓存token超过了10天，重新获取
		if (StringUtils.isEmpty(token) || StringUtils.isEmpty(time) || day > 10) {
			token = AuthServiceUtil.getAuth();
			putToken(token);
		}
		return token;
	}
	/**
	 * 判断当前账号是否有该权限
	 * 
	 * @param permission
	 * @return
	 */
	public static boolean isPermitted( String permission ){
		return SecurityUtils.getSubject().isPermitted( permission );
	}

	//安全帽检测获取token
	/**
	 * 缓存一个token
	 */
	public static void putHatDetectToken(String token){
		accessHatDetectToken.put("accessHatDetectToken", token );
	}


	/**
	 * 缓存一个token
	 */
	public static String getHatDetectToken(){
		String token = accessHatDetectToken.get("accessHatDetectToken");
		//token为空，重新获取
		if(StringUtils.isEmpty(token)){
			token = AuthServiceUtil.getHatDetectAuth();
			putHatDetectToken(token);
		}
		return token;
	}
}
