package com.xywg.admin.modular.health.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.modular.health.model.WorkerHealth;

/**
 * <p>
 * 健康信息 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-08-17
 */
public interface WorkerHealthMapper extends BaseMapper<WorkerHealth> {

	WorkerHealth getNowHealth(@Param("idCardType") String idCardType, @Param("idCardNumber") String idCardNumber);

	List<WorkerHealth> getHealthListByDate(@Param("idCardType") String idCardType, @Param("idCardNumber")String idCardNumber, 
			@Param("nowDate")String nowDate, @Param("pageNo")Integer pageNo,@Param("pageSize")Integer pageSize);

	WorkerHealth getLastHealth(@Param("idCardType") String idCardType, @Param("idCardNumber") String idCardNumber);
}
