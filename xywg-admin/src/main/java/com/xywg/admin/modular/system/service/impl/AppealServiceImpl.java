package com.xywg.admin.modular.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.system.model.Appeal;
import com.xywg.admin.modular.system.dao.AppealMapper;
import com.xywg.admin.modular.system.model.AppealVo;
import com.xywg.admin.modular.system.model.dto.AppealDto;
import com.xywg.admin.modular.system.service.IAppealService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评价申诉页面 服务实现类
 * </p>
 *
 * @author cw123
 * @since 2018-08-20
 */
@Service
public class AppealServiceImpl extends ServiceImpl<AppealMapper, Appeal> implements IAppealService {

    @Override
    public List<AppealVo> selectList(Map<String, Object> map, Page<AppealVo> page) {
        return this.baseMapper.selectList(map,page);
    }

    @Override
    public Integer operation(AppealDto appealDto) {
        appealDto.setAuditUser(ShiroKit.getUser().getName());
        return this.baseMapper.operation(appealDto);
    }
}
