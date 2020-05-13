package com.xywg.admin.modular.bad.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.bad.model.WorkerGoodRecords;
import com.xywg.admin.modular.bad.model.WorkerGoodRecordsVO;
import com.xywg.admin.modular.team.model.TeamMasterVO;
import com.xywg.admin.modular.worker.model.WorkerMasterVO;

/**
 * <p>
 * 工人奖励记录信息 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-05
 */
public interface IWorkerGoodRecordsService extends IService<WorkerGoodRecords> {

	/**
	 * 获取工人奖励列表
	 * @param page
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectWorkerGoodRecords(Page<WorkerGoodRecords> page,Map<String, Object> map);
	
	/**
	 * 获取班组奖励列表
	 * @param page
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectTeamGoodRecords(Page<WorkerGoodRecords> page,Map<String, Object> map);
	
	/**
	 * 查询工人奖励详情
	 * @param id
	 * @return
	 */
	WorkerGoodRecordsVO selectWorkerGoodRecordsById(Integer id);
	
	/**
	 * 查询班组奖励详情
	 * @param id
	 * @return
	 */
	WorkerGoodRecordsVO selectTeamGoodRecordsById(Integer id);
	
	/**
	 * 根据班组id查询班组信息
	 * @param id
	 * @return
	 */
	TeamMasterVO selectTeamInfoById(Integer id);

	/**
	 * 根据证件类型和证件号查询奖励信息
	 * @param idCardNumber
	 * @param idCardType
	 * @author yuanyang
	 * @return
	 */
	WorkerGoodRecordsVO getByIdCard(String idCardNumber,Integer idCardType);

	/**
	 * 根据证件类型和证件号查询奖励信息(tab页)
	 * @param page
	 * @param map
	 * @return
	 * @author yuanyang
	 */
    List<Map<String,Object>> getListByIdCard(Page<WorkerGoodRecords> page, Map<String, Object> map);
}
