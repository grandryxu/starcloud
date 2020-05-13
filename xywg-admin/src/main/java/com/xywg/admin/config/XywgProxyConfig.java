package com.xywg.admin.config;

import com.google.common.collect.ImmutableMap;
import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.servlet.Servlet;
import java.util.Map;

/**
 * Created by wangcw on 2018/7/7.
 */
@Profile("dev")
@Configuration
public class XywgProxyConfig {
	@Value("${spring.mvc.view.imagePath}")
    private String imagePath;

    @Value("${spring.mvc.view.imageLocalPath}")
    private String imageLocalPath;


    @Bean
    public Servlet baiduProxyServlet(){
        return new ProxyServlet();
    }


    @Bean
    public ServletRegistrationBean smzServletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(baiduProxyServlet(), imagePath+ "*");
        Map<String, String> params = ImmutableMap.of(
                "targetUri",imageLocalPath ,
                "log", "true");
        registrationBean.setInitParameters(params);
        registrationBean.setName("smzLabor");
        return registrationBean;
    }
    
}
