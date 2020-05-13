package com.xywg.admin.modular.faceUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import com.xywg.admin.core.common.exception.BizExceptionEnum;
import com.xywg.admin.core.exception.XywgException;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class FaceDetect {

//    /**
//     * 人脸检测 max_face_num最多处理人脸的数目
//     *
//     * @param filePath
//     * @return String
//     * @author duanfen
//     */
//    public static boolean detect(String filePath) throws Exception {
//        boolean flag = false;
//        byte[] imgData = FileUtil.getFile(filePath);
//        if (imgData.length == 0) {
//        	throw new XywgException(BizExceptionEnum.IMAGE_NO_DISCERN);
//        }
//        String imgStr = Base64Util.encode(imgData);
//        String imgParam = URLEncoder.encode(imgStr, "UTF-8");
//        String param = "max_face_num="
//                + 1
//                + "&face_fields="
//                + "age,beauty,expression,faceshape,gender,glasses,landmark,race,qualities"
//                + "&image=" + imgParam;
//        String result = HttpUtil.post(Constant.BAIDU_DETECT_URL,
//                TokenUtil.getToken(), param);
//        JSONObject json = new JSONObject(result);
//        int resultNum = (int) json.get("result_num");
//        if (resultNum > 0) {
//            flag = true;
//        }
//        return flag;
//    }

//    public static void main(String[] args) throws Exception {
//        // 本地文件路径
//        // String filePath =
//        // "C:/Users/duanfen/Documents/Tencent Files/374445712/FileRecv/MobileFile/IMG_3764.JPG";
//        String filePath = "C:/Users/duanfen/Documents/Tencent Files/374445712/FileRecv/MobileFile/IMG_3663.JPG";
//        System.err.println(FaceDetect.detect(filePath));
//    }

    /**
     * 人脸检测V3
     * @param filePath
     * @return
     * @throws Exception
     */
    public static boolean detect(String filePath) throws Exception {
        boolean flag = false;
        String faceToken = null;
		byte[] imgData = FileUtil.getFile(filePath);
		if (imgData.length == 0) {
			throw new Exception("找不到图片");
		}
//        byte[] imgData = FileUtil.readFileByBytes(filePath);
        String imgStr = Base64Util.encode(imgData);
        //构造参数
        Map<String,String> map = new HashMap<>(10);
        map.put("image",imgStr);
        map.put("image_type",Constant.IMAGE_TYPE);
        map.put("face_field",Constant.FACE_DETECT_FACE_FIELD);
        JSONObject paramJson = new JSONObject(map);
        String param = paramJson.toString();
        //获取token，拼接url
        String accessToken = TokenUtil.getToken();
        String requestUrl = Constant.BAIDU_DETECT_URL_V3 + "?access_token=" + accessToken;
        //发送请求
        String result = HttpUtil.postLocal(requestUrl,param);
        JSONObject json = new JSONObject(result);
        //如果请求成功，获取face_token（当前图片人脸的唯一的标识）
        if("0".equals(json.get("error_code").toString())){
            JSONObject resultJson = (JSONObject) json.get("result");
            JSONArray faceList = resultJson.getJSONArray("face_list");
            faceToken = ((JSONObject)faceList.get(0)).getString("face_token");
//            System.out.println(faceToken);
            flag = true;
        }
        return flag;
    }

}
