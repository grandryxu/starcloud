package com.xywg.admin.modular.smz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.modular.smz.model.SmzCorProject;
import com.xywg.admin.modular.smz.model.SmzLwt;

/**
 * 
 * @description 实名制、劳务通主键关系表
 * 
 * @author chupp
 *
 * @date 2018年5月2日
 *
 */
public interface SmzLwtMapper extends BaseMapper<SmzLwt> {

	/**
	 * 
	 * @description 保存实名制、劳务通主键关系
	 * @author chupp
	 * @date 2018年5月2日
	 * @param smzLwt
	 *
	 */
	public void saveSmzLwt(@Param("s") SmzLwt smzLwt);
	
	/**
	 * 
	 * @description 根据实名制ID、表名查询劳务通ID
	 * @author chupp
	 * @date 2018年5月2日
	 * @param smzId
	 * @param tableName
	 * @return
	 *
	 */
	public List<SmzLwt> getLwtIdBySmzId(@Param("s") long smzId, @Param("t") String tableName);

	public void saveRegisterNoProjectId(@Param("s")SmzCorProject sl);

	public Long getProjectMaxId(@Param("registerNo")String registerNo);

	public void saveScp(@Param("s")SmzCorProject scp);

	public void updateScp(@Param("s")SmzCorProject scp);
}
