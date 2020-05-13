package com.xywg.admin.modular.project.service.impl;

import com.xywg.admin.modular.project.model.Injury;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.project.dao.InjuryMapper;
import com.xywg.admin.modular.project.service.IInjuryService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

import com.xywg.admin.modular.system.service.AccountProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 工伤管理 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-21
 */
@Service
public class InjuryServiceImpl extends ServiceImpl<InjuryMapper, Injury> implements IInjuryService {
	
	@Autowired
	private InjuryMapper injuryMapper;
	@Autowired
	private AccountProjectService accountProjectService;
	@Override
	public List<Map<String, Object>> selectList(Page page, Map map) {
		map.put("deptId", ShiroKit.getUser().getDeptId());
		map.put("projectCodes",accountProjectService.getProjectCodes());
        return injuryMapper.list(page, map ,accountProjectService.getSwitchType());
	}

	@Override
	public int saveInjury(Injury injury) {
		injury.setCreateUser(ShiroKit.getUser().getName());
		return this.injuryMapper.saveInjury(injury);
	}

	@Override
	public Map<String, Object> findById(Long id) {
		return this.injuryMapper.findById(id);
	}

	@Override
	public int updateInjury(Injury injury) {
		return this.injuryMapper.updateInjury(injury);
	}

	@Override
	public void deleteByIds(Map<String, Object> map) {
		map.put("updateUser", ShiroKit.getUser().getName());
		injuryMapper.deleteByIds(map);
	}

}
