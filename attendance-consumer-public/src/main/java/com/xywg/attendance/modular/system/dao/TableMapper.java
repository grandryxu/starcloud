package com.xywg.attendance.modular.system.dao;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.attendance.modular.system.model.Record;

/**
 * <p>
 * 	表操作
 * </p>
 *
 * @author xuehao.shi
 * @since 2019-07-29
 */
public interface TableMapper extends BaseMapper<Record> {
	
	Integer isExistTable(@Param("tableName")String tableName,  @Param("suffixSchema")String suffixSchema);
	
	Integer createTable(@Param("tableName")String tableName);
}
