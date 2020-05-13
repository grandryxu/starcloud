package com.xywg.admin.modular.device.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.device.model.DeviceRecord;
import com.xywg.admin.modular.device.model.DeviceRecordExceptionData;
import com.xywg.admin.modular.face.model.PersonData;

import java.util.List;
import java.util.Map;

import com.xywg.admin.modular.system.model.Banner;
import com.xywg.admin.modular.system.model.SwitchType;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 异常考勤记录表 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-12
 */
public interface DeviceRecordExceptionDataMapper extends BaseMapper<DeviceRecordExceptionData> {

	/**
     * 
     * @param @param paramList 
     * @Description: 删除相似
     * @author cxl  
     * @date 2018年1月17日 上午10:56:33
     */
	void removeExceptionFaceLike(@Param("t") PersonData data);

	/**
     * 
     * @param @param paramList 
     * @Description: 插入异常模版
     * @author cxl  
     * @date 2018年1月17日 上午10:52:06
     */
	void saveExceptionData(@Param("t") PersonData data);

	/**
     * 保存停用时上传的数据
     * @param recordExceptionDataList
     */
	void saveDisabledRecord(List<DeviceRecordExceptionData> recordExceptionDataList);

	/**
     * 
     * @param @param record 
     * @Description: 未查询到人
     * @author cxl  
     * @date 2018年1月18日 下午3:08:45
     */
	void saveExceptionRecord(List<DeviceRecord> record);

	/**
	 * 
	 * @param @param paramList 
	 * @Description: 插入异常考勤
	 * @author cxl  
	 * @date 2018年1月17日 上午10:22:06
	 */
	void saveRecord(@Param("deviceRecordExceptionDatas")List<DeviceRecord> abNormalRecordSave);

	/**
	 * 分页查询考勤异常列表
	 * @param page
	 * @param map
	 * @param switchType
     * @return
	 */
    List<Map<String,Object>> selectDeviceExceptions(@Param("page") Page<Banner> page, @Param("map") Map<String, Object> map, @Param("switchType") SwitchType switchType);

	/**
	 * 单条异常详情
	 * @param id
	 * @return
	 * @author yuanyang
	 */
    Map<String,Object> getExceptionDeviceById(Integer id);

	/**
	 * 根据证件类型和证件号查询异常记录
	 * @param integer
	 * @param idCardNumber
	 * @return
	 * @author yuanyang
	 */
	List<DeviceRecordExceptionData> selectByIdCard(@Param("idCardType")Integer idCardType,@Param("idCardNumber") String idCardNumber,@Param("exceptionIdCardType")Integer exceptionIdCardType,@Param("exceptionIdCardNumber") String exceptionIdCardNumber);

    /**
     * 删除异常考勤
     * @param exceptionIdCardType
     * @param exceptionIdCardNumber
     */
    void deleteExceptionDevices(@Param("exceptionIdCardType")Integer exceptionIdCardType, @Param("exceptionIdCardNumber")String exceptionIdCardNumber,@Param("updateUser")String updateUser);

	/**
	 * 删除单条记录
	 * @param id
	 */
	void deleteExceptionDeviceById(@Param("id")Integer id,@Param("updateUser")String updateUser);

	/**
	 * 批量删除
	 * @param map
	 */
    void deleteByIds(@Param("map")Map<String, Object> map);
}
