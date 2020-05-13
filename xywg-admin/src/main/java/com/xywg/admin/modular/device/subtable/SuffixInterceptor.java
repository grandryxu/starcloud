package com.xywg.admin.modular.device.subtable;

import com.baomidou.mybatisplus.MybatisDefaultParameterHandler;
import com.xywg.admin.modular.device.model.DeviceRecord;
import com.xywg.admin.modular.device.subtable.dao.TableMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
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

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 分表拦截器
 * 
 * @author xuehao.shi
 *
 */
@Intercepts({
		@Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class, Integer.class }) })
@Component
public class SuffixInterceptor implements Interceptor {

	@Value("${suffix.schema:gblabor}")
	private String suffixSchema;

	@Autowired
	private ApplicationContext applicationContext;

	private final static Logger logger = LoggerFactory.getLogger(SuffixInterceptor.class);
	private static final String tag = SuffixInterceptor.class.getName();
	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
	private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();

		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY,
				DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
		MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
		BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
		// 获取参数
		// Connection conn = (Connection) invocation.getArgs()[0];
		// DatabaseMetaData metaData = conn.getMetaData();
		// String url = metaData.getURL();

		String mapperId = mappedStatement.getId();
		if (mapperId.contains("TableMapper")) {
			Connection conn = (Connection) invocation.getArgs()[0];
			conn.setReadOnly(false);
			return invocation.proceed();
		}
		String suffix = TableSuffixUtil.getTableSuffix();
		MybatisDefaultParameterHandler prepare = (MybatisDefaultParameterHandler) metaStatementHandler
				.getValue("delegate.parameterHandler");
		Object param = prepare.getParameterObject();
		if (param instanceof Map) {
			Map paramMap = (Map) param;
			if (paramMap.containsKey("map")) {
				Object mapObj = paramMap.get("map");
				if (mapObj instanceof Map) {
					Map map = (Map) mapObj;

					if (map.containsKey("tableSuffix")) {
						String tableSuffix = map.get("tableSuffix").toString();
						if (!StringUtils.isEmpty(tableSuffix)) {
							suffix = tableSuffix;
						}
					}
				}
			}

			// 按time查属于分表
			if (SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
				if (paramMap.containsKey("ew")) {
					Object paramObj = paramMap.get("ew");
					if (paramObj instanceof DeviceRecord) {
						DeviceRecord record = (DeviceRecord) paramObj;

						SimpleDateFormat df = new SimpleDateFormat("yyyy_MM");// 设置日期格式
						String time = df.format(record.getTime());
						if (!StringUtils.isEmpty(time)) {
							suffix = time;
						}
					}
				}
			}
		}


		int index = -1;
		TableMapper tableMapper = (TableMapper) applicationContext.getBean("tableMapper");
		//判断考勤查询考勤表是是否是具体的表
		if(!suffix.contains("buss_device_record_2")) {
			createTable(tableMapper, "buss_device_record" + "_" + suffix);
			index = boundSql.getSql().indexOf("buss_device_record_exception_data");
		}else {
			index = boundSql.getSql().indexOf(suffix);
		}

		String newSql = null;
		//判断是否需要替换
		if (index == -1) {
			newSql = boundSql.getSql().replace("buss_device_record", "buss_device_record" + "_" + suffix);
		}
		if (newSql != null) {
			logger.debug(tag, "分表后SQL =====>" + newSql);
			metaStatementHandler.setValue("delegate.boundSql.sql", newSql);
		}


	/*	TableMapper tableMapper = (TableMapper) applicationContext.getBean("tableMapper");
		createTable(tableMapper, "buss_device_record" + "_" + suffix);
		int index = boundSql.getSql().indexOf("buss_device_record_exception_data");
		String newSql = null;
		if (index == -1) {
			newSql = boundSql.getSql().replace("buss_device_record", "buss_device_record" + "_" + suffix);
		}
		if (newSql != null) {
			logger.debug(tag, "分表后SQL =====>" + newSql);
			metaStatementHandler.setValue("delegate.boundSql.sql", newSql);
		}*/


		/**
		 * by mapper 粒度比较细，但是buss_device_record涉及的mapper太多了，所以全部拦截
		 */

		// if (newSql != null) {
		// logger.debug(tag, "分表后SQL =====>" + newSql);
		// metaStatementHandler.setValue("delegate.boundSql.sql", newSql);

		// String sqlId = mappedStatement.getId();
		//
		// String className = sqlId.substring(0, sqlId.lastIndexOf("."));
		// Class<?> classObj = Class.forName(className);
		//
		// TableSeg tableSeg = classObj.getAnnotation(TableSeg.class);
		// if(null == tableSeg){
		// //不需要分表，直接传递给下一个拦截器处理
		// return invocation.proceed();
		// }
		// String accountMonth = null;
		// if(TableSeg.MONTH_BY.equals(tableSeg.shardType())) {
		// accountMonth = TableSuffixUtil.getTableSuffix();
		// }
		// if(!StringUtils.isEmpty(accountMonth)) {
		// //根据配置获取分表字段，生成分表SQL
		//// createTableSuffix(tableSeg.tableName() + TableSuffixUtil.getTableSuffix());
		// String newSql = boundSql.getSql().replace(tableSeg.tableName(),
		// tableSeg.tableName() + "_" + accountMonth);
		// if (newSql != null) {
		// logger.debug(tag, "分表后SQL =====>" + newSql);
		// metaStatementHandler.setValue("delegate.boundSql.sql", newSql);
		// }
		//
		//
		// }

		// 传递给下一个拦截器处理
		return invocation.proceed();
	}

	private void createTable(TableMapper tableMapper, String tableName) {
		if (tableMapper.isExistTable(tableName, suffixSchema) == 0) {
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

	// 根据配置获取分表的表名后缀
	private String genShardByValue(MetaObject metaStatementHandler, MappedStatement mappedStatement, TableSeg tableSeg,
			BoundSql boundSql) {
		String accountMonth = null;
		Map<String, Object> additionalParameters = (Map<String, Object>) metaStatementHandler
				.getValue("delegate.boundSql.additionalParameters");

		if (null != additionalParameters.get(tableSeg.shardBy())) {
			accountMonth = boundSql.getAdditionalParameter(tableSeg.shardBy()).toString();
		} else {
			Configuration configuration = mappedStatement.getConfiguration();
			String showSql = showSql(configuration, boundSql);
			accountMonth = getShardByValue(showSql, tableSeg);
		}
		return accountMonth;
	}

	// 根据配置获取分表参数值
	public static String getShardByValue(String showSql, TableSeg tableSeg) {
		final String conditionWhere = "where";
		String accountMonth = null;
		if (StringUtils.isBlank(showSql)) {
			return null;
		} else {
			String[] sqlSplit = showSql.toLowerCase().split(conditionWhere);
			if (sqlSplit.length > 1 && sqlSplit[1].contains(tableSeg.shardType())) {
				accountMonth = sqlSplit[1].replace(" ", "").split(tableSeg.shardType())[1].substring(2, 8);
			}
		}
		return accountMonth;
	}

	// 组装查询语句参数
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
		} else {
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
