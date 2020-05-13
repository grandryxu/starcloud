package com.xywg.admin.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.modular.system.model.Area;
import com.xywg.admin.modular.system.model.dto.AreaDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-23
 */
public interface AreaMapper extends BaseMapper<Area> {
    /**
     * 获取籍贯
     * @return
     * @author yuanyang
     */
    List<Area> getBirthPlaceCode();

    /**
     * 根据id获取省名
     * @return
     */
    String getShortNameById(@Param("id") Integer id);

    /**
     * 根据shortname获取id
     * @param shortname
     * @author yuanyang
     * @return
     */
    String getIdByShortName(@Param("shortname")String shortname );

    /**
     * 根据区id获取详情
     * @param areaCode
     * @return
     */
    String getAreaNameById(int areaCode);

    /**
     * 根据名称获取areaCode
     * @param areaName
     * @return
     */
    String getCodeByAreaName(String areaName);

    /**
     * 获取所有的省市区
     * @return List<AreaDto>
     * @author 蔡伟
     */
    List<AreaDto> getAreasApp();
}
