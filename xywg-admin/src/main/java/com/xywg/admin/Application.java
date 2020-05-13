package com.xywg.admin;

import com.xywg.admin.modular.device.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;



/**
 * SpringBoot方式启动类
 *
 * @author wangcw
 * @Date 2017/5/21 12:06
 */
@Profile("pc")
@SpringBootApplication
@ImportResource({"classpath:/spring/application-*.xml"})
public class Application {

    private final static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        logger.info("Application is success!");
    }
}
