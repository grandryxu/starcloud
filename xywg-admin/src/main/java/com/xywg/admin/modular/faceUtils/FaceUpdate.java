package com.xywg.admin.modular.faceUtils;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 人脸更新
 * add by wangpeixu
 * 20180604
 */
public class FaceUpdate {


    private static Logger logger = Logger.getLogger(FaceUpdate.class);
    /**
     * 人脸更新
     * @param face
     * @return
     * @throws Exception
     */
    public static boolean update(FaceAddModel face) throws Exception{
        boolean flag = false;
        // 本地文件路径
		byte[] imgData = FileUtil.getFile(face.getImage());
		if (imgData.length == 0) {
			throw new Exception("找不到图片");
		}
//        byte[] imgData = FileUtil.readFileByBytes(face.getImage());
        String imgStr = Base64Util.encode(imgData);
        //构造参数
        Map<String,String> map = new HashMap<>(10);
        map.put("image",imgStr);
        map.put("image_type", Constant.IMAGE_TYPE);
        map.put("group_id",face.getGroupId());
        map.put("user_id",face.getUserId());
        map.put("user_info",face.getUserInfo());
        map.put("quality_control",Constant.QUALITY_CONTROL);
        map.put("liveness_control",Constant.LIVENESS_CONTROL);
        JSONObject paramJson = new JSONObject(map);
        String param = paramJson.toString();
        //获取token，拼接url
        String accessToken = TokenUtil.getToken();
        String requestUrl = Constant.BAIDU_UPDATE_URL_V3 + "?access_token=" + accessToken;
        //发送请求
        String result = HttpUtil.postLocal(requestUrl,param);
//        System.out.println(result);
        logger.info("人脸更新返回:"+result);
        JSONObject jsonObject = new JSONObject(result);
        //根据error_code判断是否执行成功
        if ("0".equals(jsonObject.get("error_code").toString())) {
            flag = true;
        }
        return flag;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(FaceUpdate.update(new FaceAddModel("P_232340002", "C_123784", "C:/Users/admin/Desktop/255/5.jpg", "name:cdc", "BASE64")));
    }
}