package com.xywg.admin.modular.faceUtils;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 获取token类
* @author duanfen
* @date 2018年1月10日 上午11:29:08 
*
 */
public class AuthServiceUtil {
	private static Logger logger = LoggerFactory.getLogger(AuthServiceUtil.class);
    /**
     * 获取权限token
     * @return 返回示例：
     */
    public static String getAuth() {
        // 官网获取的 API Key 更新为你注册的
        String clientId = "BgQuMCBk3Cw7prS4VO8uhOEg";
        // 官网获取的 Secret Key 更新为你注册的
        String clientSecret = "HLrpb7NXV39AxTV2Yy1zsdKmlmcnny5T";
        return getAuth(clientId, clientSecret);
    }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * grant_type为固定参数
     */
    public static String getAuth(String ak, String sk) {
        String getAccessTokenUrl = Constant.BAIDU_TOKEN_URL+ "grant_type=client_credentials"
                + "&client_id=" + ak + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果处理
             */
            JSONObject jsonObject = new JSONObject(result);
            String accessToken = jsonObject.getString("access_token");
            TokenUtil.putToken(accessToken);
            return accessToken;
        } catch (Exception e) {
            logger.error("获取token失败:{}   "+e.getMessage());
            e.printStackTrace(System.err);
        }
        return null;
    }

    //安全帽检测获取token
    /**
     * 获取权限token
     * @return 返回示例：
     */
    public static String getHatDetectAuth() {
        // 官网获取的 API Key 更新为你注册的
        //安全帽检测
        String clientId = "MfyTw38WeVW26qS10sKB6aum";
        // 官网获取的 Secret Key 更新为你注册的
        String clientSecret = "m1gCRMn6nN2DltjUYPTbz0PZ63RuGXhO";
        return getHatDetectAuth(clientId, clientSecret);
    }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * grant_type为固定参数
     */
    public static String getHatDetectAuth(String ak, String sk) {
        String getAccessTokenUrl = Constant.BAIDU_TOKEN_URL+ "grant_type=client_credentials"
                + "&client_id=" + ak + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果处理
             */
            JSONObject jsonObject = new JSONObject(result);
            String accessHatDetectToken = jsonObject.getString("access_token");
            TokenUtil.putHatDetectToken(accessHatDetectToken);
            return accessHatDetectToken;
        } catch (Exception e) {
            logger.error("获取token失败:{}   "+e.getMessage());
            e.printStackTrace(System.err);
        }
        return null;
    }
}
