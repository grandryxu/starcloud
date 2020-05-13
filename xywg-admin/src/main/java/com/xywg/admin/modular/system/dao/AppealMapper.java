package com.xywg.admin.modular.system.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.system.model.Appeal;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.modular.system.model.AppealVo;
import com.xywg.admin.modular.system.model.dto.AppealDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评价申诉页面 Mapper 接口
 * </p>
 *
 * @author cw123
 * @since 2018-08-20
 */
public interface AppealMapper extends BaseMapper<Appeal> {

    /**
     * 获取所有的申诉内容
     * @param map
     * @param page
     * @return List<AppealVo>
     * @author 蔡伟
     */
    List<AppealVo> selectList(@Param("map") Map<String, Object> map, @Param("page") Page<AppealVo> page);

    /**
     * 通过 驳回操作
     * @param appealDto
     * @return Integer
     */
    Integer operation(AppealDto appealDto);
}
