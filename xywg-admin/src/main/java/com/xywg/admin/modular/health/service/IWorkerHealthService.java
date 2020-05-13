package com.xywg.admin.modular.health.service;

import com.xywg.admin.modular.health.model.WorkerHealth;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 健康信息 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-08-17
 */
public interface IWorkerHealthService extends IService<WorkerHealth> {

	/**
	 * 
	 * @description 查询当前登录工人最新的健康信息
	 * @author chupp
	 * @date 2018年8月20日
	 * @param idCardType
	 * @param idCardNumber
	 * @return
	 *
	 */
	Object getNowHealth(String idCardType, String idCardNumber);

	/**
	 * 
	 * @description 根据日期查询工人的历史健康信息
	 * @author chupp
	 * @date 2018年8月20日
	 * @param idCardType
	 * @param idCardNumber
	 * @param nowDate
	 * @param pageNo
	 * @param pageSize
	 * @return
	 *
	 */
	Object getHealthListByDate(String idCardType, String idCardNumber, String nowDate, Integer pageNo,
			Integer pageSize);

	/**
	 *
	 * @description 查询当前登录工人最新的健康信息
	 * @author chupp
	 * @date 2018年8月20日
	 * @param idCardType
	 * @param idCardNumber
	 * @return
	 *
	 */
	WorkerHealth getLastHealth(String idCardType, String idCardNumber);

}
