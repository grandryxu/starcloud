package com.xywg.admin.modular.face.dao;

import com.xywg.admin.modular.face.model.PersonData;
import com.xywg.admin.modular.worker.model.WorkerMaster;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 人脸模型表 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-30
 */
public interface PersonDataMapper extends BaseMapper<PersonData> {

	/**
	 * 
	 * @description 删除人脸模型
	 * @author chupp
	 * @date 2018年5月30日
	 * @param workerMaster
	 *
	 */
	void deletePersonData(@Param("w") WorkerMaster workerMaster);

	/**
	 * 
	 * @description 新增人脸模型
	 * @author chupp
	 * @date 2018年5月30日
	 * @param workerMaster
	 *
	 */
	void savePersonData(@Param("w") WorkerMaster workerMaster);

	/**
	 * 
	 * @param @param paramLong
	 * @param @param paramString 
	 * @Description: 删除userData
	 * @author cxl  
	 * @date 2018年1月4日 上午8:58:22
	 */
	void deleteData(@Param("wm") WorkerMaster wm,@Param("a")  String algVersion);

	
	void create(@Param("p") PersonData data);

}
