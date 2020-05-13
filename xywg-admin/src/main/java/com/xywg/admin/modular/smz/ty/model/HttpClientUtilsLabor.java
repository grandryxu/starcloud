package com.xywg.admin.modular.smz.ty.model;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class HttpClientUtilsLabor {


    private static Logger log = LoggerFactory.getLogger(HttpClientUtilsLabor.class);
    private static final CloseableHttpClient httpclient = HttpClients.createDefault();
    private static final String userAgent = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36";

    /**
     *
     * @description POST请求
     * @author jln
     * @param url
     * @param params
     * @return
     *
     */
    public static String post(String url, Map<String, Object> params) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String body = null;
        HttpPost post = postFormJSON(url, params);
        body = invoke(httpclient, post);
        httpclient.getConnectionManager().shutdown();
        return body;
    }


    private static String invoke(DefaultHttpClient httpclient, HttpUriRequest httpost) {
        HttpResponse response = sendRequest(httpclient, httpost);
        String body = paseResponse(response);
        return body;
    }

    private static HttpPost postFormJSON(String url, Map<String, Object> params) {
        HttpPost httpost = new HttpPost(url);
        StringEntity entity = new StringEntity(JSONObject.fromObject(params).toString(),"utf-8");//解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpost.setEntity(entity);
        return httpost;
    }



    private static String paseResponse(HttpResponse response) {
        HttpEntity entity = response.getEntity();
        String body = null;
        try {
            body = EntityUtils.toString(entity);
        } catch (ParseException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return body;
    }

    private static HttpResponse sendRequest(DefaultHttpClient httpclient, HttpUriRequest httpost) {
        HttpResponse response = null;
        try {
            response = httpclient.execute(httpost);
        } catch (ClientProtocolException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return response;
    }
}
