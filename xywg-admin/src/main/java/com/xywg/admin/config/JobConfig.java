package com.xywg.admin.config;

import com.xxl.job.core.executor.XxlJobExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * xxl-job config
 *
 * @author xuxueli 2017-04-28
 */
@Profile("pc")
@Configuration
@ComponentScan(basePackages = "com.xywg.admin.task")
public class JobConfig {

    @Bean(initMethod = "start", destroyMethod = "destroy")
    public XxlJobExecutor xxlJobExecutor() {
        return null;
    }

}