package com.xywg.admin.modular.project.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.project.model.Injury;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 工伤管理 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-21
 */
public interface IInjuryService extends IService<Injury> {
	

    /**
     * 查询考勤记录列表
     * @author duanfen
     * @param page
     * @param map
     * @return
     */
    List<Map<String,Object>> selectList(Page page , Map map);
	
    
	/**
	 * 新增工伤记录
	 * 
	 * @author: duanfen
	 * @version: 2018年6月21日 上午11:14:06
	 */
    int saveInjury(Injury injury);
    

	/**
     * 根据id查询工伤信息
 	 * 
 	 * @author: duanfen
 	 * @version: 2018年6月22日 下午3:50:36
      */
     Map<String, Object> findById(Long id);

	/**
      * 根据id修改工伤信息
 	  * 
 	  * @author: duanfen
 	  * @version: 2018年6月22日 下午3:51:18
      */
     int updateInjury(Injury injury);

     /**
      *@Description:批量删除
      *@Author xieshuaishuai
      *@Date 2018/7/19 9:40
      */
     void deleteByIds(Map<String,Object> map);


}
