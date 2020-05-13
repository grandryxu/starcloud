package com.xywg.admin.config;

import com.xxl.job.core.executor.XxlJobExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XxlJobConfig {


    private static final String JOB_PROPERTIES_PREFIX = "xywg.job.";

    @Value("${"+ JOB_PROPERTIES_PREFIX +"admin.addresses}")
    private String addresses;
    @Value("${"+ JOB_PROPERTIES_PREFIX +"executor.appname}")
    private String appname;
    @Value("${"+ JOB_PROPERTIES_PREFIX +"executor.ip}")
    private String ip;
    @Value("${"+ JOB_PROPERTIES_PREFIX +"executor.port}")
    private Integer port;
    @Value("${"+ JOB_PROPERTIES_PREFIX +"executor.logpath}")
    private String logpath;
    @Value("${"+ JOB_PROPERTIES_PREFIX +"executor.logretentiondays}")
    private Integer logretentiondays;
    @Value("${"+ JOB_PROPERTIES_PREFIX +"accessToken}")
    private String accessToken;

    @Bean(initMethod = "start", destroyMethod = "destroy")
    public XxlJobExecutor xxlJobExecutor() {
        XxlJobExecutor xxlJobSpringExecutor = new XxlJobExecutor();
        xxlJobSpringExecutor.setAdminAddresses(addresses);
        xxlJobSpringExecutor.setAppName(appname);
        xxlJobSpringExecutor.setIp(ip);
        xxlJobSpringExecutor.setPort(port);
        xxlJobSpringExecutor.setAccessToken(accessToken);
        xxlJobSpringExecutor.setLogPath(logpath);
        xxlJobSpringExecutor.setLogRetentionDays(logretentiondays);
        return xxlJobSpringExecutor;
    }


}
