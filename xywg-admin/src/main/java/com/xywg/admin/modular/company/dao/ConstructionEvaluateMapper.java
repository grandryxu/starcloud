package com.xywg.admin.modular.company.dao;

import com.xywg.admin.modular.company.model.ConstructionEvaluate;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 参建单位评价 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-28
 */
public interface ConstructionEvaluateMapper extends BaseMapper<ConstructionEvaluate> {
    List<Map<String,Object>> list(@Param("map") Map<String,Object> map);
}
