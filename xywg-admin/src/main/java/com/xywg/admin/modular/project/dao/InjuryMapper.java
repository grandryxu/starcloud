package com.xywg.admin.modular.project.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.project.model.Injury;
import com.xywg.admin.modular.system.model.SwitchType;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 工伤管理 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-21
 */
public interface InjuryMapper extends BaseMapper<Injury> {

	/**
	 * 获取工伤列表
	 * 
	 * @author: duanfen
	 * @version: 2018年6月21日 下午2:31:16
	 */
    List<Map<String,Object>> list(@Param("page") Page page, @Param("map") Map map, @Param("switchType") SwitchType switchType);

	/**
 	 * 新增工伤记录
 	 * 
 	 * @author: duanfen
 	 * @version: 2018年6月21日 上午11:14:06
 	 */
     int saveInjury(@Param("t")Injury injury);

	/**
     * 根据id查询工伤信息
 	 * 
 	 * @author: duanfen
 	 * @version: 2018年6月22日 下午3:50:36
      */
     Map<String, Object> findById(@Param("id") Long id);

	/**
      * 根据id修改工伤信息
 	  * 
 	  * @author: duanfen
 	  * @version: 2018年6月22日 下午3:51:18
      */
     int updateInjury(@Param("t")Injury injury);

     /**
      *@Description:批量删除
      *@Author xieshuaishuai
      *@Date 2018/7/19 9:36
      */
     void deleteByIds(@Param("map") Map<String, Object> map);

	List<Injury> getZrInjuryInfo();

}
