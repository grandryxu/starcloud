package com.xywg.attendance.core.config;

import com.xywg.attendance.common.utils.ApplicationContextProvider;
import com.xywg.attendance.modular.netty.NettyServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hjy
 * @date 2019/3/28
 */
@Configuration
public class WebMvcConfig {

    /**
     * 解决跨域问题
     * @param registry
     */
    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                //.allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowedMethods("*")
                .maxAge(3600)
                .allowCredentials(true);
    }*/

}
