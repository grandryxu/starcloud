package com.xywg.admin.modular.faceUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 人脸查找
 * 
 * @author duanfen
 * @date 2018年1月10日 下午3:33:23
 *
 */
public class FaceIdentify {

	/**
	 * 调用本地的人脸识别的服务
	 * add by wangpeixu 20180306
	 * @param filePath
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public static String identifyLocal(String filePath, String groupId) throws Exception {
		//构造json参数 项目id ， 图片路径
		Map<String,String> map = new HashMap<>(10);
		map.put("projectId",groupId);
		StringBuilder stringBuilder = new StringBuilder("/");
		String str = stringBuilder.append(filePath).toString();
		map.put("imgs",str);
		JSONObject paramJson = new JSONObject(map);
		String param = paramJson.toString();
		String result = HttpUtil.postLocal(Constant.lOCAL_IDENTIFY_URL, param);
		// 获取返回score值
		JSONObject json = new JSONObject(result);
		if("0".equals(json.get("code").toString())) {
			throw new Exception("人脸查找失败!");
		}
		Double score = Double.valueOf(json.get("score").toString());
		BigDecimal scoreDecimal = BigDecimal.valueOf(score.doubleValue());
		if (scoreDecimal.compareTo(BigDecimal.ZERO) > 0) {
			// 以0.85作为阈值，0.85以上为同一个人
			if (scoreDecimal.compareTo(Constant.LOCAL_MATCH_SCORE) < 0) {
				throw new Exception("人脸不清晰，请重新录入!");
			}
		}
		return json.get("personID").toString();
	}
	
	
	public static String identify(Long projectId,String imgs) throws Exception {
		// 本地文件路径
//		byte[] imgData = FileUtil.getFile(filePath);
//		if (imgData.length == 0) {
//			throw new XYException("找不到图片");
//		}
////		byte[] imgData = FileUtil.readFileByBytes(filePath);
//		String imgStr = Base64Util.encode(imgData);
//		String imgParam = URLEncoder.encode(imgStr, "UTF-8");
//		String param = "group_id=" + groupId + "&user_top_num=" + "1" + "&face_top_num=" + "1" + "&images=" + imgParam;
//		String result = HttpUtil.post(Constant.BAIDU_IDENTIFY_URL, UserCache.getToken(), param);
//		// 获取返回score值
//		JSONObject json = new JSONObject(result);
//		if(!json.has("result")) {
//			throw new XYException("人脸查找失败");
//		}
//		JSONArray jsonarr = json.getJSONArray("result");
//		JSONObject resultJson = jsonarr.getJSONObject(0);
//		JSONArray scores = (JSONArray) resultJson.get("scores");
//		BigDecimal score = new BigDecimal(scores.get(0).toString());
//		if (score.compareTo(BigDecimal.ZERO) > 0) {
//			// 以80分作为阈值，80分以上为通一个人
//			if (score.compareTo(Constant.BAIDU_MATCH_SCORE) < 0) {
//				throw new XYException("人脸不清晰，请重新录入");
//			}
//		}
		return null;
}

	public static void main(String[] args) throws Exception {
//		System.err
//				.println(FaceIdentify
//						.identify(
//								"/2018-05-14/facein2547977589.809655b181286b953c492fa5aee00e6ee6d9ff.jpg",
//								"86"));
//		FaceIdentify.hatDetect("C:/Users/admin/Desktop/255/1526903547459ae5c2fb572054c2892431c3586ddfeb5.jpg");
		FaceIdentify.identify("C:/Users/admin/Desktop/255/4.jpg","C_123784","0");
	}

//	/**
//	 * 调用百度人脸识别服务
//	 * @param filePath
//	 * @param groupId
//	 * @return
//	 * @throws Exception
//	 */
//	public static String identify(String filePath, String groupId,String isHatDetect) throws Exception {
//		// 本地文件路径
//		byte[] imgData = FileUtil.getFile(filePath);
//		if (imgData.length == 0) {
//			throw new Exception("找不到图片, 请重试！");
//		}
//		String imgStr = Base64Util.encode(imgData);
//		//人脸查找之前先做安全帽的识别
//		if("1".equals(isHatDetect) && !hatDetect(imgStr)){
//			throw new Exception("考勤失败, 为了您的安全, 请正确佩戴安全帽！");
//		}
//		String imgParam = URLEncoder.encode(imgStr, "UTF-8");
//		String param = "group_id=" + groupId + "&user_top_num=" + "1" + "&face_top_num=" + "1" + "&images=" + imgParam;
//		String result = HttpUtil.post(Constant.BAIDU_IDENTIFY_URL, TokenUtil.getToken(), param);
//		// 获取返回score值
//		JSONObject json = new JSONObject(result);
//		if(!json.has("result")) {
//			throw new Exception("考勤失败, 人脸不清晰, 请重新录入！");
//		}
//		JSONArray jsonarr = json.getJSONArray("result");
//		JSONObject resultJson = jsonarr.getJSONObject(0);
//		JSONArray scores = (JSONArray) resultJson.get("scores");
//		BigDecimal score = new BigDecimal(scores.get(0).toString());
//		if (score.compareTo(BigDecimal.ZERO) > 0) {
//			// 以80分作为阈值，80分以上为通一个人
//			if (score.compareTo(Constant.BAIDU_MATCH_SCORE) < 0) {
//				throw new Exception("考勤失败, 人脸不清晰, 请重新录入！");
//			}
//		}
//		return resultJson.getString("uid");
//	}

	/**
	 * 调用百度人脸识别服务v3
	 * @param filePath
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public static String identify(String filePath, String groupId,String isHatDetect) throws Exception {
		String userId = null;
		// 本地文件路径
		byte[] imgData = FileUtil.getFile(filePath);
		if (imgData.length == 0) {
			throw new Exception("找不到图片, 请重试！");
		}
//		byte[] imgData = FileUtil.readFileByBytes(filePath);
		String imgStr = Base64Util.encode(imgData);
		//人脸查找之前先做安全帽的识别
		if("1".equals(isHatDetect) && !hatDetect(imgStr)){
			throw new Exception("考勤失败, 为了您的安全, 请正确佩戴安全帽！");
		}
		//构造参数
		Map<String,String> map = new HashMap<>(10);
		map.put("image",imgStr);
		map.put("image_type",Constant.IMAGE_TYPE);
		map.put("group_id_list",groupId);
		map.put("quality_control",Constant.QUALITY_CONTROL);
		map.put("liveness_control",Constant.LIVENESS_CONTROL);
		JSONObject paramJson = new JSONObject(map);
		String param = paramJson.toString();
		//获取token，拼接url
		String accessToken = TokenUtil.getToken();
		String requestUrl = Constant.BAIDU_IDENTIFY_URL_V3 + "?access_token=" + accessToken;
		//发送请求
		String result = HttpUtil.postLocal(requestUrl,param);
		JSONObject jsonObject = new JSONObject(result);
		//如果请求成功，那么就获取user_id
		if("0".equals(jsonObject.get("error_code").toString())){
			JSONObject resultJson = (JSONObject) jsonObject.get("result");
			JSONArray userList = resultJson.getJSONArray("user_list");
			BigDecimal score = new BigDecimal(((JSONObject)userList.get(0)).get("score").toString());
			if (score.compareTo(BigDecimal.ZERO) > 0) {
				// 以80分作为阈值，80分以上为通一个人
				if (score.compareTo(Constant.BAIDU_MATCH_SCORE) < 0) {
					throw new Exception("考勤失败, 人脸不清晰, 请重新录入！");
				}
				//返回查找到的人的userId
				userId = ((JSONObject)userList.get(0)).getString("user_id");
			}
		}
		return userId;
	}

	/**
	 * 安全帽检测
	 * add by wangpeixu
	 * 20180525
	 * @param imgStr
	 * @return
	 * @throws Exception
	 */
	public static boolean hatDetect(String imgStr) throws Exception{
		boolean flag = false;
		try {
			//准备参数
			Map<String,String> map = new HashMap<>(10);
			map.put("image",imgStr);
			map.put("top_num","2");
			JSONObject paramJson = new JSONObject(map);
			String param = paramJson.toString();
			//获取token
			String accessToken = TokenUtil.getHatDetectToken();
			String requestUrl = Constant.BAIDU_HAT_DETECT_URL + "?access_token=" + accessToken;
			//发送请求
			String result = HttpUtil.postLocal(requestUrl,param);
			//处理返回结果
			JSONObject resultJson = new JSONObject(result);
			JSONArray jsonArray = resultJson.getJSONArray("results");
			JSONObject json = jsonArray.getJSONObject(0);
			String name = json.get("name").toString();
			if(!"no".equals(name)){
				flag = true;
			}
			return flag;
		}catch (Exception e){
			throw new Exception("安全帽检测失败，请重试！");
		}
	}
}
