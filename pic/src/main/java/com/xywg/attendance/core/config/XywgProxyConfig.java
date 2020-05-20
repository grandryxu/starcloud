package com.xywg.attendance.core.config;

import com.google.common.collect.ImmutableMap;
import com.xywg.attendance.common.utils.Constant;
import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;
import java.util.Map;

/**
 * 反向代理
 * @author hh cao
 * @date 2018/7/17
 */
@Configuration
public class XywgProxyConfig {

	@Value("${ftp.host}")
	private String host;
	@Value("${ftp.port}")
	private String port;
	@Value("${ftp.username}")
	private String username;
	@Value("${ftp.password}")
	private String password;
	@Bean
	public Servlet proxyServlet() {
		return new ProxyServlet();
	}
	
	@Bean
	public ServletRegistrationBean servletRegistration() {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(proxyServlet(), "/lwt/public/*");
		Map<String, String> params = ImmutableMap.of(
				"targetUri", "http://"+ host +"/",
				"log", "true");
		registrationBean.setInitParameters(params);
		return registrationBean;
	}
}
