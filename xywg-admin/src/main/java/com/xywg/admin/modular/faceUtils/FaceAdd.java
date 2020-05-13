package com.xywg.admin.modular.faceUtils;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 人脸注册
 * 
 * @author duanfen
 * @date 2018年1月11日 上午11:23:57
 *
 */
public class FaceAdd {
	
	
	private static Logger logger = Logger.getLogger(FaceAdd.class);

//	public static boolean add(FaceAddModel face) throws Exception {
//		boolean flag = false;
//		// 本地文件路径
//		byte[] imgData = FileUtil.getFile(face.getImage());
//		if (imgData.length == 0) {
//			throw new Exception("找不到图片");
//		}
////		byte[] imgData = FileUtil.readFileByBytes(face.getImage());
//		String imgStr = Base64Util.encode(imgData);
//		String imgParam = URLEncoder.encode(imgStr, "UTF-8");
//		// 拼接访问地址
//		String param = "uid=" + face.getUid() + "&user_info="
//				+ face.getUserInfo() + "&group_id=" + face.getGroupId()
//				+ "&images=" + imgParam + "&action_type="
//				+ face.getActionType();
//		String result = HttpUtil.post(Constant.BAIDU_ADD_URL,
//				TokenUtil.getToken(), param);
//		logger.info("人脸新增返回:"+result);
//		JSONObject jsonObject = new JSONObject(result);
//		if (!jsonObject.has("error_code")) {
//			flag = true;
//		}
//		return flag;
//	}
	
	/**
	 * 
	* @param @param personId
	* @param @param imgs
	* @param @return
	* @param @throws Exception 
	* @Description: 调用后台人脸录入
	* @author cxl  
	* @date 2018年2月8日 上午9:08:12
	 */
	public static boolean add(Long personId,String imgs) throws Exception {
		boolean flag = true;

		String result = HttpUtil.post(Constant.BAIDU_ADD_URL,
				TokenUtil.getToken(), null);
		logger.info("人脸新增返回:"+result);
		JSONObject jsonObject = new JSONObject(result);
		if (jsonObject.has("error_msg")) {
			flag = false;
		}
		return flag;
	}

	public static void main(String[] args) throws Exception {
		System.err.println(FaceAdd.add(new FaceAddModel("P_232340002", "C_123784", "C:/Users/admin/Desktop/255/4.jpg", "name:cdc", "BASE64")));
	}

	/**
	 * 录入本地的人脸图片
	 * add by wangpeixu
	 * 20180301
	 * @param face
	 * @return
	 * @throws Exception
	 */
	public static boolean addLocalFace(FaceAddModel face) throws Exception {
		boolean flag = false;
		Map<String,String> map = new HashMap<>(10);
		//构造参数 用户id ，项目id，以及图片地址
		map.put("personId",face.getUserId());
		if(face.getGroupId() != null){
			map.put("projectId",face.getGroupId());
		}
//		StringBuilder stringBuilder = new StringBuilder("/");
//		String str = stringBuilder.append(face.getImage()).toString().replace(",",";/");
		StringBuilder stringBuilder = new StringBuilder("");
		String str = stringBuilder.append(face.getImage()).toString().replace(",",";");
		if("1".equals(face.getImageType())){
			map.put("mb_img",str);
		}else{
			map.put("sfz_img",str);
		}
		JSONObject paramJson = new JSONObject(map);
		String param = paramJson.toString();
		String result = HttpUtil.postLocal(Constant.LOCAL_ADD_URL, param);
		logger.info("人脸新增返回:"+result);
		JSONObject jsonObject = new JSONObject(result);
		if ("1".equals(jsonObject.get("code").toString())) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 人脸新增v3
	 * @param face
	 * @return
	 * @throws Exception
	 */
	public static boolean add(FaceAddModel face) throws Exception {
		boolean flag = false;
		// 本地文件路径
		byte[] imgData = FileUtil.getFile(face.getImage());
		if (imgData.length == 0) {
			throw new Exception("找不到图片");
		}
//		byte[] imgData = FileUtil.readFileByBytes(face.getImage());
		String imgStr = Base64Util.encode(imgData);
		//构造参数
		Map<String,String> map = new HashMap<>(10);
		map.put("image",imgStr);
		map.put("image_type",Constant.IMAGE_TYPE);
		map.put("group_id",face.getGroupId());
		map.put("user_id",face.getUserId());
		map.put("user_info",face.getUserInfo());
		map.put("quality_control",Constant.QUALITY_CONTROL);
		map.put("liveness_control",Constant.LIVENESS_CONTROL);
		JSONObject paramJson = new JSONObject(map);
		String param = paramJson.toString();
		//获取token，拼接url
		String accessToken = TokenUtil.getToken();
		String requestUrl = Constant.BAIDU_ADD_URL_V3 + "?access_token=" + accessToken;
		//发送请求
		String result = HttpUtil.postLocal(requestUrl,param);
//		System.out.println(result);
		logger.info("人脸新增返回:"+result);
		JSONObject jsonObject = new JSONObject(result);
		if ("0".equals(jsonObject.get("error_code").toString())) {
			flag = true;
		}
		return flag;
	}

}
