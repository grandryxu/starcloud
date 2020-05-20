package com.xywg.attendance.modular.system.dao;

import com.xywg.attendance.common.utils.TableSeg;
import com.xywg.attendance.modular.system.model.Record;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 考勤表 Mapper 接口
 * </p>
 *
 * @author z
 * @since 2019-02-25
 */
@TableSeg(shardType = "MONTH", tableName = "buss_device_record", shardBy = "", shardByTable = "")
public interface RecordMapper extends BaseMapper<Record> {

}
