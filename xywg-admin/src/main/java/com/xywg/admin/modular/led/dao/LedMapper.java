package com.xywg.admin.modular.led.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.led.model.Led;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * tv Mapper 接口
 * </p>
 *
 * @author cw123
 * @since 2018-09-20
 */
public interface LedMapper extends BaseMapper<Led> {

    /**
     * 根据imei获取Led
     * @param led
     * @return
     */
    Led getLedByImei(Led led);

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
    List<Led> selectList(@Param("map") Map<String, Object> map, Page<Led> page);
}
