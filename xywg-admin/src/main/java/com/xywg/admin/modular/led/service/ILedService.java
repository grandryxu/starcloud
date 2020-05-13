package com.xywg.admin.modular.led.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.led.model.Led;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * tv 服务类
 * </p>
 *
 * @author cw123
 * @since 2018-09-20
 */
public interface ILedService extends IService<Led> {

    /**
     * 批量删除led
     * @param ids
     * @return Integer
     * @author 蔡伟
     */
    Integer deletes(String ids);

    /**
     * 分页查询led
     * @param map
     * @param page
     * @return List<Led>
     * @author 蔡伟
     */
    List<Led> selectList(Map<String, Object> map, Page<Led> page);
}
