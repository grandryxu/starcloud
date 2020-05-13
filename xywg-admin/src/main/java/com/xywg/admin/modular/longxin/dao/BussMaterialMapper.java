package com.xywg.admin.modular.longxin.dao;

import com.xywg.admin.modular.company.model.SubContractor;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.modular.longxin.model.BussMaterial;

/**
 * <p>
 * 企业材料表 Mapper 接口
 * </p>
 *
 * @author xuehao.shi
 * @since
 */
public interface BussMaterialMapper extends BaseMapper<BussMaterial> {
	
	void deleteByBussId(@Param("bussinessId")Long bussId);

}
