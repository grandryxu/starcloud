package com.xywg.admin.modular.company.service;

import com.xywg.admin.modular.company.model.EntryExitHistory;
import com.xywg.admin.modular.company.model.SubContractor;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.project.model.ProjectWorker;

/**
 * <p>
 * 进退场记录表 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-28
 */
public interface IEntryExitHistoryService extends IService<EntryExitHistory> {

	/**
	 * 
	 * @description 获取实名制进退场信息（盐城）
	 * @author chupp
	 * @param myc 
	 * @date 2018年7月11日
	 *
	 */
	void savePersonJoinFromSMZYC(Map<String, String> myc);

	/**
	 * 
	 * @description 获取实名制进退场信息（南通）
	 * @author chupp
	 * @param mnt 
	 * @param list 
	 * @date 2018年7月26日
	 *
	 */
	void savePersonJoinFromSMZNT(Map<String, String> mnt, List<SubContractor> list);

	void savePersonJoinFromSMZTY(Map<String, String> mnt);

	/**
	 * 根据项目工人表id删除进退场表信息
	 * @param list
	 * @author duanfen
	 */
	void batchDeleteExitByPwId (List<String> list);

    void addEntryExitHistory(List<Object> addList);
}
