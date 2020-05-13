package com.xywg.admin.modular.project.service;

import com.xywg.admin.modular.project.model.ProjectTrainRecord;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 培训人员记录表 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-14
 */
public interface IProjectTrainRecordService extends IService<ProjectTrainRecord> {

	/**
	 * 
	 * @description 获取实名制培训记录（盐城）
	 * @author chupp
	 * @param myc 
	 * @date 2018年7月5日
	 *
	 */
	void saveTrainRecordFromSMZYC(Map<String, String> myc);

	/**
	 * 
	 * @description 获取实名制培训记录（南通）
	 * @author chupp
	 * @param mnt 
	 * @date 2018年7月26日
	 *
	 */
	void saveTrainRecordFromSMZNT(Map<String, String> mnt);

}
