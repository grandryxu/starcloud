package com.xywg.attendance.business.interceptor;

import java.sql.Connection;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.MybatisDefaultParameterHandler;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xywg.attendance.common.utils.TableSeg;
import com.xywg.attendance.common.utils.TableSuffixUtil;
import com.xywg.attendance.modular.system.dao.TableMapper;
import com.xywg.attendance.modular.system.model.Record;


/**
 * 分表拦截器
 * @author xuehao.shi
 *
 */
@Intercepts({
    @Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class,Integer.class})
})
@Component
public class SuffixInterceptor implements Interceptor {
	
	@Value("${suffix.schema}")
    private String suffixSchema;
	
	@Autowired
    private ApplicationContext applicationContext;
	
	private final static Logger logger = LoggerFactory.getLogger(SuffixInterceptor.class);
    private static final String tag = SuffixInterceptor.class.getName();
    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();
    
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {StatementHandler statementHandler = (StatementHandler) invocation.getTarget();

	MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY); 
    MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
    BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
   
    
    /**
     * by mapper 粒度比较细，但是buss_device_record涉及的mapper太多了，所以全部拦截
     */
    
    String sqlId = mappedStatement.getId();

    String className = sqlId.substring(0, sqlId.lastIndexOf("."));
    Class<?> classObj = Class.forName(className);

    TableSeg tableSeg = classObj.getAnnotation(TableSeg.class);
    if(null == tableSeg){
        //不需要分表，直接传递给下一个拦截器处理
        return invocation.proceed();
    }
    String accountMonth = null;
    if(TableSeg.MONTH_BY.equals(tableSeg.shardType())) {
    	accountMonth = TableSuffixUtil.getTableSuffix();
    	if (SqlCommandType.INSERT.equals(mappedStatement.getSqlCommandType())) {
            //查询 判断参数是否存在打卡时间
    		MybatisDefaultParameterHandler  prepare = (MybatisDefaultParameterHandler) metaStatementHandler.getValue("delegate.parameterHandler");
    	    Object param = prepare.getParameterObject();
    	    if(param instanceof Record) {
    	    	Record record = (Record)param;
    	    	String time = record.getTime();
    	    	if(!StringUtils.isEmpty(time)) {
    	    		accountMonth = TableSuffixUtil.getTableSuffix(time);
    	    	}
    	    }
        }
    	
    	
    	if (SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            //查询 判断参数是否存在打卡时间
    		MybatisDefaultParameterHandler  prepare = (MybatisDefaultParameterHandler) metaStatementHandler.getValue("delegate.parameterHandler");
    	    Object param = prepare.getParameterObject();
    	    if(param instanceof Map) {
    	    	Map paramMap = (Map)param;
    	    	if(paramMap.containsKey("ew")) {
    	    		Object paramObj = paramMap.get("ew");
    	    		if(paramObj instanceof EntityWrapper) {
    	    			EntityWrapper pw = (EntityWrapper)paramObj;
    	    			String time = pw.getParamNameValuePairs().get("MPGENVAL2").toString();
    	    	    	if(!StringUtils.isEmpty(time)) {
    	    	    		accountMonth = TableSuffixUtil.getTableSuffix(time);
    	    	    	}
    	    		}
    	    	}
    	    }
        }
    }
    if(!StringUtils.isEmpty(accountMonth)) {
    	TableMapper tableMapper = (TableMapper) applicationContext.getBean("tableMapper");
        createTable(tableMapper,tableSeg.tableName() + "_" + accountMonth);
    	//根据配置获取分表字段，生成分表SQL
////    	createTableSuffix(tableSeg.tableName() + TableSuffixUtil.getTableSuffix());
    	String newSql = boundSql.getSql().replace(tableSeg.tableName(), tableSeg.tableName() + "_" + accountMonth);
    	if (newSql != null) {
        	logger.debug(tag, "分表后SQL =====>" + newSql);
        	metaStatementHandler.setValue("delegate.boundSql.sql", newSql);
    	}
    	 
    	 
    }

    // 传递给下一个拦截器处理
    return invocation.proceed();
    }
	
	private void createTable(TableMapper tableMapper, String tableName) {
		if(tableMapper.isExistTable(tableName,suffixSchema) == 0) {
			tableMapper.createTable(tableName);
		}
	}





	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
	}

	@Override
	public void setProperties(Properties properties) {
	}
	
	//根据配置获取分表的表名后缀
    private String genShardByValue(MetaObject metaStatementHandler,MappedStatement mappedStatement, TableSeg tableSeg, BoundSql boundSql) {
        String accountMonth = null;
        Map<String, Object> additionalParameters = (Map<String, Object>) metaStatementHandler.getValue("delegate.boundSql.additionalParameters");

        if (null != additionalParameters.get(tableSeg.shardBy())) {
            accountMonth = boundSql.getAdditionalParameter(tableSeg.shardBy()).toString();
        } else {
            Configuration configuration = mappedStatement.getConfiguration();
            String showSql = showSql(configuration,boundSql);
            accountMonth = getShardByValue(showSql,tableSeg);
        }
        return accountMonth;
    }
    
    //根据配置获取分表参数值
    public static String getShardByValue(String showSql,TableSeg tableSeg) {
        final String conditionWhere = "where";
        String accountMonth = null ;
        if(StringUtils.isBlank(showSql)){
            return null;
        }else{
            String[] sqlSplit = showSql.toLowerCase().split(conditionWhere);
            if(sqlSplit.length>1 && sqlSplit[1].contains(tableSeg.shardType())){
                accountMonth = sqlSplit[1].replace(" ","").split(tableSeg.shardType())[1].substring(2,8);
            }
        }
        return accountMonth;
    }
    
  //组装查询语句参数
    public static String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));

            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }else{
            return null;
        }
        return sql;
    }

    private static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }

}
