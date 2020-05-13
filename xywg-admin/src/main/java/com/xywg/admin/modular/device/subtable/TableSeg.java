package com.xywg.admin.modular.device.subtable;

import java.lang.annotation.*;

/**
 * 分表配置注解类
 * @author xuehao.shi
 *
 */
@Target({ ElementType.TYPE,ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TableSeg {
	public static final String MONTH_BY  ="MONTH";
    //表名
    public String tableName();
    // 分表方式，取模，如%5：表示取5余数，
    // 按时间，如MONTH：表示按月分表
    // 如果不设置，直接根据shardBy值分表
    public String shardType();
    
    //根据什么字段分表 ,多个字段用数学表达表示,如a+b   a-b
    public String shardBy();

    // 根据什么字段分表,多个字段用数学表达表示,如a+b   a-b
    public String shardByTable();

}