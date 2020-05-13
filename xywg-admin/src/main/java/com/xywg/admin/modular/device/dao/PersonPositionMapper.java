package com.xywg.admin.modular.device.dao;

import com.xywg.admin.modular.device.model.PersonPosition;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 考勤帽定位 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-19
 */
public interface PersonPositionMapper extends BaseMapper<PersonPosition> {
	/**
	 * 修改定位信息中的人员id
	 * 
	 * @param ids
	 * @return int
	 * @author duanfen
	 */
	public int updatePersonPositionInfo();

	/**
	 * 查询未计算数据
	 * 
	 * @return List<PersonPosition>
	 * @author duanfen
	 */
	public List<PersonPosition> findNotCalculate();

	/**
	 * 查询工人最近考勤记录
	 * 
	 * @param personId
	 * @return Boolean
	 * @author duanfen
	 */
	public Boolean findLastRecord(@Param("idCardType") Integer idCardType,@Param("idCardNumber") String	idCardNumber);

	/**
	 * 修改用户当次考勤在圈内还是圈外
	 * 
	 * @param range
	 * @param id
	 * @return int
	 * @author duanfen
	 */
	public int updateRange(@Param("range") boolean range, @Param("id") Long id);
	
	/**
	 * 根据工人和安全帽编号查询
	 * @return
	 */
	List<PersonPosition> queryPositionByImei(Map<String, Object> params);

}
