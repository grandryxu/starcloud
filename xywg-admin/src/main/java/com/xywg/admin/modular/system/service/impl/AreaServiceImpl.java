package com.xywg.admin.modular.system.service.impl;

import com.xywg.admin.modular.system.model.Area;
import com.xywg.admin.modular.system.dao.AreaMapper;
import com.xywg.admin.modular.system.model.dto.AreaDto;
import com.xywg.admin.modular.system.service.IAreaService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-23
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements IAreaService {

    @Override
    public List<Area> getBirthPlaceCode() {
        return this.baseMapper.getBirthPlaceCode();
    }

    @Override
    public String getIdByShortName(String shortname) {
        return this.baseMapper.getIdByShortName(shortname);
    }

    @Override
    public String getAreaNameById(int areaCode) {
        return this.baseMapper.getAreaNameById(areaCode);
    }

    @Override
    public String getCodeByAreaName(String areaName) {
        return this.baseMapper.getCodeByAreaName(areaName);
    }

    @Override
    public List<AreaDto> getAreasApp() {
        return this.baseMapper.getAreasApp();
    }
}
