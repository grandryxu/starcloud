package com.xywg.attendance.modular.system.service;

import com.xywg.attendance.modular.system.model.BussPersonData;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 人脸模型表 服务类
 * </p>
 *
 * @author z
 * @since 2019-03-05
 */
public interface IBussPersonDataService extends IService<BussPersonData> {

    List<BussPersonData> selectByTypeAndNumberIn(List<Map<String, Object>> params);
}
