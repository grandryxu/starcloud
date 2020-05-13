package com.xywg.admin.modular.device.subtable.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.modular.device.model.SafetyHelmet;
import com.xywg.admin.modular.device.subtable.TableSeg;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 	表操作
 * </p>
 *
 * @author xuehao.shi
 * @since 2019-07-29
 */
@TableSeg(shardType = "MONTH", tableName = "buss_device_record", shardBy = "", shardByTable = "")
public interface TableMapper extends BaseMapper<SafetyHelmet> {
	
	Integer isExistTable(@Param("tableName") String tableName, @Param("suffixSchema") String suffixSchema);

	Integer createTable(@Param("tableName") String tableName);
}
