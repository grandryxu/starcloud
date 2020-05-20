package com.xywg.attendance.modular.system.dao;

import com.xywg.attendance.modular.system.model.BussPersonData;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 人脸模型表 Mapper 接口
 * </p>
 *
 * @author z
 * @since 2019-03-05
 */
public interface BussPersonDataMapper extends BaseMapper<BussPersonData> {

    List<BussPersonData> selectByTypeAndNumberIn(List<Map<String,Object>> params);
}
