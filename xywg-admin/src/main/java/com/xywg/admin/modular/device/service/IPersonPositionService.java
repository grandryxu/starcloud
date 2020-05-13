package com.xywg.admin.modular.device.service;

import com.xywg.admin.modular.device.model.PersonPosition;

import java.util.List;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 考勤帽定位 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-19
 */
public interface IPersonPositionService extends IService<PersonPosition> {


	/**
	 * 修改定位信息中的人员id
	 * 
	 * @param ids
	 * @return int
	 * @author duanfen
	 */
	public int updatePersonPositionInfo();

	/**
	 *计算数据,生成考勤
	 * 
	 * @return List<PersonPosition>
	 * @author duanfen
	 */
	public void insertRecord();
	
	
	/**
	 * 根据工人和安全帽编号查询
	 * @param imei
	 * @param idCardNumber
	 * @return
	 */
	List<PersonPosition> queryPositionByImei(String idCardNumber, String imei, String startDate, String endDate);
}
