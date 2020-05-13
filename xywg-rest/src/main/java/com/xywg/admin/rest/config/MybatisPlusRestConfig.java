package com.xywg.admin.rest.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * MybatisPlus配置
 *
 * @author wangcw
 * @Date 2017年8月23日12:51:41
 */
//@Configuration
//@MapperScan(basePackages = {"com.xywg.admin.modular.*.dao"})
public class MybatisPlusRestConfig {

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
