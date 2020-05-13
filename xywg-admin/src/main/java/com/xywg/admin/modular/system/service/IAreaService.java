package com.xywg.admin.modular.system.service;

import com.xywg.admin.modular.system.model.Area;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.system.model.dto.AreaDto;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-23
 */
public interface IAreaService extends IService<Area> {

    /**
     * 获取籍贯
     * @return
     * @author yuanyang
     */
    List<Area> getBirthPlaceCode();

    /**
     * 根据shortname获取id
     * @param shortname
     * @author yuanyang
     * @return
     */
    String getIdByShortName(String shortname);

    /**
     * 根据区id获取区详情
     * @param areaCode
     * @return
     */
    String getAreaNameById(int areaCode);

    /**
     * 根据区id获取区详情
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
