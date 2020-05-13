package com.xywg.admin.modular.system.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.system.model.TimeSet;
import com.xywg.admin.modular.system.model.TimeSetVO;

/**
 * <p>
 * 时间设置表 服务类
 * </p>
 *
 * @author shily123
 * @since 2018-06-22
 */
public interface ITimeSetService extends IService<TimeSet> {
	
	 /**
     * 获取项目名称(下拉框)
     * @return
     */
   List<Map<String, Object>> getProjects();
   
   /**
    * 获取打卡区间
    * @param timeSet
    * @return
    */
   TimeSetVO getTime(TimeSet timeSet);
   
   /**
    * 启用
    * @param timeSetId
    * @return
    */
   int enable(Long timeSetId);
   
   /**
    * 禁用
    * @param timeSetId
    * @return
    */
   int disable(Map<String,Object> map);
   
   /**
    * 通过id查询时间设置
    * @param timeSetId
    * @return
    */
   TimeSetVO selectTimeSetById(Integer timeSetId);
   
   /**
    * 设置项目的上下班打卡时间(新增项目时调用)
    * @param projectCode
    * @return
    */
   int addTimeSet(String projectCode,String createUser);
   
   /**
    * 查询时间设置列表
    * @param page
    * @param map
    * @return
    */
   List<Map<String, Object>> selectTimeSetList(Page<TimeSetVO> page,Map<String, Object> map);

   /**
    *@Description:更新操作
    *@Author xieshuaishuai
    *@Date 2018/7/23 14:36
    */
   void updateTimeSet(TimeSet timeSet);
	

}
