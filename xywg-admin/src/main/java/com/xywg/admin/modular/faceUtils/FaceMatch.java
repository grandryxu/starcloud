package com.xywg.admin.modular.faceUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FaceMatch {


//    public static void main(String[] args) throws Exception {
//        // 本地文件路径
//        String filePath1 = "C:/Users/admin/Desktop/testfacematch/2222.jpg";
//        String filePath2 = "C:/Users/admin/Desktop/testfacematch/1111.jpg";
//        System.err.println(FaceMatch.match(filePath1, filePath2));
//    }

    /**
     * 人脸对比V3
     * @param filePath1
     * @param filePath2
     * @return
     * @throws Exception
     */
    public static boolean match(String filePath1, String filePath2) throws Exception {
        boolean flag = false;
		byte[] imgData1 = FileUtil.getFile(filePath1);
        byte[] imgData2 = FileUtil.getFile(filePath2);
		if (imgData1.length == 0 || imgData2.length == 0) {
			throw new Exception("找不到图片");
		}
//        byte[] imgData1 = FileUtil.readFileByBytes(filePath1);
//        byte[] imgData2 = FileUtil.readFileByBytes(filePath2);
        String imgStr1 = Base64Util.encode(imgData1);
        String imgStr2 = Base64Util.encode(imgData2);
        //定义一个上传的两张图片的list
        List<Map<String, Object>> images = new ArrayList<>();

        //构造第一张图片的参数
        Map<String,Object> map1 = new HashMap<>(10);
        map1.put("image",imgStr1);
        map1.put("image_type",Constant.IMAGE_TYPE);
        map1.put("quality_control",Constant.QUALITY_CONTROL);
        map1.put("liveness_control","NONE");

        //构造第二张图片的参数
        Map<String,Object> map2 = new HashMap<>(10);
        map2.put("image",imgStr2);
        map2.put("image_type",Constant.IMAGE_TYPE);
        map2.put("quality_control",Constant.QUALITY_CONTROL);
        map2.put("liveness_control","NONE");

        images.add(map1);
        images.add(map2);

        //参数转json，当前版本只支持json格式的参数
        JSONArray paramJsonArray = new JSONArray(images);
        String param = paramJsonArray.toString();

        //获取token，拼接url
        String accessToken = TokenUtil.getToken();
        String requestUrl = Constant.BAIDU_MATCH_URL_V3 + "?access_token=" + accessToken;

        //发送请求
        String result = HttpUtil.postLocal(requestUrl,param);
//        System.out.println(result);
        JSONObject resultJson = new JSONObject(new JSONObject(result).get("result").toString());
//        System.out.println(resultJson);
//        System.out.println(resultJson.get("score").toString());
        //如果两个人的得分大于80分即可说明有很大的可能性是同一个人
        if(new BigDecimal(resultJson.get("score").toString()).compareTo(Constant.BAIDU_MATCH_SCORE)>0){
            flag = true;
        }

        return flag;
    }
}
