package com.xywg.admin.modular.health.service.impl;

import com.xywg.admin.modular.health.model.WorkerHealth;
import com.xywg.admin.modular.health.dao.WorkerHealthMapper;
import com.xywg.admin.modular.health.service.IWorkerHealthService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 健康信息 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-08-17
 */
@Service
public class WorkerHealthServiceImpl extends ServiceImpl<WorkerHealthMapper, WorkerHealth> implements IWorkerHealthService {
	
	@Autowired
    private WorkerHealthMapper healthMapper;
    
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
	@Override
	public Object getNowHealth(String idCardType, String idCardNumber) {
		return healthMapper.getNowHealth(idCardType,idCardNumber);
	}

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
	@Override
	public Object getHealthListByDate(String idCardType, String idCardNumber, String nowDate, Integer pageNo,
			Integer pageSize) {
		return healthMapper.getHealthListByDate(idCardType,idCardNumber,nowDate,(pageNo-1)*pageSize,pageSize);
	}

	@Override
	public WorkerHealth getLastHealth(String idCardType, String idCardNumber) {
		return healthMapper.getLastHealth(idCardType,idCardNumber);
	}

}
