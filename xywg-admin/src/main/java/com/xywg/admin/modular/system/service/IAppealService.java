package com.xywg.admin.modular.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.system.model.Appeal;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.system.model.AppealVo;
import com.xywg.admin.modular.system.model.dto.AppealDto;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评价申诉页面 服务类
 * </p>
 *
 * @author cw123
 * @since 2018-08-20
 */
public interface IAppealService extends IService<Appeal> {

    /**
     * 获取所有的申诉
     * @param map
     * @param page
     * @return List<AppealVo>
     * @author 蔡伟
     */
    List<AppealVo> selectList(Map<String, Object> map, Page<AppealVo> page);

    /**
     * 通过 驳回操作
     * @param appealDto
     * @return Integer
     */
    Integer operation(AppealDto appealDto);
}
