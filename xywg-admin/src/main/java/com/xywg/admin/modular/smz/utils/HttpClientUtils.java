package com.xywg.admin.modular.smz.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @description HttpClient 工具类
 * 
 * @author chupp
 *
 * @date 2018年4月27日
 *
 */
@SuppressWarnings("deprecation")
public class HttpClientUtils {
	private static Logger log = LoggerFactory.getLogger(HttpClientUtils.class);
	/**
	 * 
	 * @description POST请求
	 * @author chupp
	 * @date 2018年4月27日
	 * @param url
	 * @param params
	 * @return
	 *
	 */
	public static String post(String url, Map<String, Object> params) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;
		HttpPost post = postForm(url, params);
		body = invoke(httpclient, post);
		httpclient.getConnectionManager().shutdown();
		return body;
	}
	
	/**
	 * ###实名制接口对接使用###
	 * @description POST请求（请求头带值）（body中参数格式为json）
	 * @author chupp
	 * @date 2018年4月27日
	 * @param url 请求地址
	 * @param params 请求参数
	 * @param headers 登录信息
	 * @return
	 * @throws UnsupportedEncodingException 
	 *
	 */

	@SuppressWarnings("resource")
	public static String post(String url, Map<String, Object> params, Map<String,String> headers) {
		//url请求地址     parms 请求参数     headers登录信息
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;
		HttpPost post = postForm(url, params);
		for (Map.Entry<String, String> e : headers.entrySet()) {
			post.addHeader(e.getKey(), e.getValue());
        }
		if (params != null && params.size() != 0) {
			try {
	            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
	            for (String key : params.keySet()) {
	                nameValuePairList.add(new BasicNameValuePair(key, (String) params.get(key)));
	            }
	            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
	            formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
	            post.setEntity(formEntity);
			}catch(Exception e) {
				throw new RuntimeException();
			}
        }
		body = invoke(httpclient, post);
		httpclient.getConnectionManager().shutdown();
		return body;
	}

	/**
	 * 
	 * @description GET请求
	 * @author chupp
	 * @date 2018年4月27日
	 * @param url
	 * @return
	 *
	 */
	public static String get(String url) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;
		HttpGet get = new HttpGet(url);
		body = invoke(httpclient, get);
		httpclient.getConnectionManager().shutdown();
		return body;
	}

	private static String invoke(DefaultHttpClient httpclient, HttpUriRequest httpost) {
		HttpResponse response = sendRequest(httpclient, httpost);
		String body = paseResponse(response);
		return body;
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

	private static HttpPost postForm(String url, Map<String, Object> params) {
		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, params.get(key) == null ? null : params.get(key).toString()));
		}
		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
		}
		return httpost;
	}
	
}
