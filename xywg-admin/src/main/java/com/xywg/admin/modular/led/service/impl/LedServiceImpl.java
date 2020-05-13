package com.xywg.admin.modular.led.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.modular.led.model.Led;
import com.xywg.admin.modular.led.dao.LedMapper;
import com.xywg.admin.modular.led.service.ILedService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * tv 服务实现类
 * </p>
 *
 * @author cw123
 * @since 2018-09-20
 */
@Service
public class LedServiceImpl extends ServiceImpl<LedMapper, Led> implements ILedService {

    @Override
    public boolean insert(Led led) {
        //判重
        Led isHasLed = this.baseMapper.getLedByImei(led);
        if(isHasLed!=null){
            //重复存在
            throw new XywgException(800,"该编号"+ led.getImei() +"在系统中已存在");
        }
        return retBool(this.baseMapper.insert(led));
    }

    @Override
    public boolean updateById(Led led) {
        //判重
        Led isHasLed = this.baseMapper.getLedByImei(led);
        if(isHasLed!=null){
            //重复存在
            throw new XywgException(800,"该编号"+ led.getImei() +"在系统中已存在");
        }
        return retBool(this.baseMapper.updateById(led));
    }

    @Override
    public Integer deletes(String ids) {
        return this.baseMapper.deletes(ids);
    }

    @Override
    public List<Led> selectList(Map<String, Object> map, Page<Led> page) {
        return this.baseMapper.selectList(map,page);
    }
}
