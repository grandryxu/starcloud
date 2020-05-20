package com.xywg.attendance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author hjy
 */
@SpringBootApplication
public class AttendanceReceiveApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AttendanceReceiveApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(AttendanceReceiveApplication.class, args);
       /* NettyServer server = ApplicationContextProvider.getBean(NettyServer.class);
        server.run();*/
    }

}
