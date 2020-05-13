package com.xywg.admin.rest;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;


/**
 * @author wangcw
 */
@SpringBootApplication
@ComponentScan(value="com.xywg.admin")
@ImportResource({"classpath*:**/spring/application-*.xml"})
public class RestApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }
}
