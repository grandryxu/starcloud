package com.xywg.admin.modular.bad.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.bad.model.WorkerGoodRecords;
import com.xywg.admin.modular.bad.model.WorkerGoodRecordsVO;
import com.xywg.admin.modular.team.model.TeamMasterVO;

/**
 * <p>
 * 工人奖励记录信息 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-05
 */
public interface WorkerGoodRecordsMapper extends BaseMapper<WorkerGoodRecords> {

	/**
	 * 获取工人奖励记录列表
	 * @param page
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectWorkerGoodRecords(@Param("page") Page<WorkerGoodRecords> page, @Param("map") Map<String, Object> map);
	
	/**
	 * 获取班组奖励记录列表
	 * @param page
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectTeamGoodRecords(@Param("page") Page<WorkerGoodRecords> page, @Param("map") Map<String, Object> map);
	
	/**
	 * 查询工人奖励详情
	 * @param id
	 * @return
	 */
	WorkerGoodRecordsVO selectWorkerGoodRecordsById(@Param("id") Integer id);
	
	/**
	 * 查询班组奖励详情
	 * @param id
	 * @return
	 */
	WorkerGoodRecordsVO selectTeamGoodRecordsById(@Param("id") Integer id);
	
	/**
	 * 根据班组id查询班组信息
	 * @param id
	 * @return
	 */
	TeamMasterVO selectTeamInfoById(@Param("id") Integer id);

	/**
	 * getListByIdCard
	 * @param idCardNumber
	 * @param idCardType
	 * @author yuanyang
	 * @return
	 */
    WorkerGoodRecordsVO getByIdCard(@Param("idCardNumber")String idCardNumber,@Param("idCardType") Integer idCardType);

	/**
	 * getListByIdCard(tab页)
	 * @param page
	 * @param map
	 * @return
	 * @author yuanyang
	 */
    List<Map<String,Object>> getListByIdCard(@Param("page") Page<WorkerGoodRecords> page, @Param("map") Map<String, Object> map);
}
