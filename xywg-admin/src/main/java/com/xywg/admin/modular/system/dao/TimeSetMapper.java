package com.xywg.admin.modular.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.system.model.TimeSet;
import com.xywg.admin.modular.system.model.TimeSetVO;

/**
 * <p>
 * 时间设置表 Mapper 接口
 * </p>
 *
 * @author shily
 * @since 2018-06-22
 */
public interface TimeSetMapper extends BaseMapper<TimeSet> {

	/**
	 * 查询项目名称下拉列表
	 * @return
	 */
	List<Map<String,Object>> getProjects(@Param("list") List<String> list);
	
	/**
	 * 查询打卡区间
	 * @param timeSet
	 * @return
	 */
	TimeSetVO getTime(@Param("obj") TimeSet timeSet);
	
	/**
	 * 启用
	 * @param timeSetId
	 * @return
	 */
	int enable(@Param("id") Long timeSetId);
	
	/**
	 * 禁用
	 * @param timeSetId
	 * @return
	 */
	int disable(@Param("map") Map<String,Object> map);
	
	/**
	 * 通过id查询时间设置
	 * @param timeSetId
	 * @return
	 */
	TimeSetVO selectTimeSetById(@Param("timeSetId") Integer timeSetId);
	
	/**
	 * 获取打卡信息
	 * @return
	 */
	List<TimeSet> getTimeSet();
	
	/**
	 * 新增时间设置
	 * @param list
	 * @return
	 */
	int addTimeSet( @Param("projectCode") String projectCode,@Param("createUser") String createUser);
	
	/**
	 * 查询时间设置分页列表
	 * @param page
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectTimeSetList(@Param("page") Page<TimeSetVO> page, @Param("map") Map<String, Object> map);

	/**
	 *@Description:更新操作
	 *@Author xieshuaishuai
	 *@Date 2018/7/23 14:35
	 */
	void updateTimeSet(TimeSet timeSet);

	List<TimeSet> getTimeByProject(@Param("projectCode") String projectCode);
}
