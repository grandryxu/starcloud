package com.xywg.attendance.modular.system.service.impl;

import com.xywg.attendance.modular.system.model.BussPersonData;
import com.xywg.attendance.modular.system.dao.BussPersonDataMapper;
import com.xywg.attendance.modular.system.service.IBussPersonDataService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 人脸模型表 服务实现类
 * </p>
 *
 * @author z
 * @since 2019-03-05
 */
@Service
public class BussPersonDataServiceImpl extends ServiceImpl<BussPersonDataMapper, BussPersonData> implements IBussPersonDataService {

    @Override
    public List<BussPersonData> selectByTypeAndNumberIn(List<Map<String, Object>> params) {
        return this.baseMapper.selectByTypeAndNumberIn(params);
    }
}
