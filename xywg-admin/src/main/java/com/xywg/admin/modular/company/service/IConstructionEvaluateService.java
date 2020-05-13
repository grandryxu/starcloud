package com.xywg.admin.modular.company.service;

import com.xywg.admin.modular.company.model.ConstructionEvaluate;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 参建单位评价 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-28
 */
public interface IConstructionEvaluateService extends IService<ConstructionEvaluate> {
List<Map<String,Object>> list(Map<String,Object> map);

    void addConstructionEvaluate(List<Object> addList);
}
