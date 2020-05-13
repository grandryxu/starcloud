package com.xywg.admin.modular.device.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.device.dao.SafetyHelmetMapper;
import com.xywg.admin.modular.device.model.SafetyHelmet;
import com.xywg.admin.modular.device.service.ISafetyHelmetService;
import com.xywg.admin.modular.project.dao.ProjectWorkerMapper;
import com.xywg.admin.modular.project.model.ProjectWorker;
import com.xywg.admin.modular.recruitModular.model.RecruitModularParams;
import com.xywg.admin.modular.system.service.AccountProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 安全帽管理 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-29
 */
@Service
public class SafetyHelmetServiceImpl extends ServiceImpl<SafetyHelmetMapper, SafetyHelmet> implements ISafetyHelmetService {

    @Resource
    private SafetyHelmetMapper safetyHelmetMapper;
    @Autowired
    private AccountProjectService accountProjectService;
    
    @Autowired
    private ProjectWorkerMapper projectWorkerMapper;
    /**
     * 获取列表
     * @param page
     * @param map
     * @return
     */
    @Override
    public List<Map<String, Object>> selectList(Page page, Map map) {
        map.put("projectCodes", accountProjectService.getProjectCodes());
        map.put("deptId", ShiroKit.getUser().getDeptId());
        return safetyHelmetMapper.list(page , map);
    }

    @Override
    public Integer deleteSafetyHelmets(String ids) {
        return this.baseMapper.deleteSafetyHelmets(ids);
    }

    @Override
    public int updateSafety(SafetyHelmet safetyHelmet) {
        if(baseMapper.checkBySn(safetyHelmet).size()>0){
            return 0;
        }else {
            baseMapper.updateSafety(safetyHelmet);
            return 1;
        }
    }

    @Override
    public List<SafetyHelmet> getUnusedSafetyHelmet(String projectCode) {
        return this.baseMapper.getUnusedSafetyHelmet(projectCode);
    }

    /**
     * 新增安全帽
     * @param safetyHelmet
     * @return
     */
    @Override
    public boolean insert(SafetyHelmet safetyHelmet) {
        SafetyHelmet isSafetyHelmet = safetyHelmetMapper.getSafetyHelmetByImei(safetyHelmet);
        if(isSafetyHelmet != null){
            //判断此序列号安全帽是否存在
            throw new XywgException(600,"该序列号已存在！");
        }
        return retBool(this.baseMapper.insert(safetyHelmet));
    }

    /**
     * 
     * @description 添加安全帽（APP端）
     * @author chupp
     * @date 2018年9月10日
     * @param params
     *
     */
	@Override
	@Transactional
	public void addSafetyHelmet(RecruitModularParams params) {
		SafetyHelmet sh = new SafetyHelmet();
		sh.setImei(params.getImei());
		SafetyHelmet isSafetyHelmet = safetyHelmetMapper.getSafetyHelmetByImei(sh);
        if(isSafetyHelmet != null){
            throw new XywgException(712,"该序列号已存在，请联系客服！");
        }
        sh.setProjectCode(params.getProjectCode());
        sh.setOrganizationCode(params.getOrganizationCode());
        sh.setName(params.getImei());
        sh.setState(1);
        safetyHelmetMapper.insert(sh);
	}
	
	@Override
	@Transactional
	public void updateSafetyHelmet(RecruitModularParams params) {
		safetyHelmetMapper.updateSafetyHelmet(params);
		safetyHelmetMapper.updateSafetyHelmetRelation(params.getImei());
	}

	/**
	 * 
	 * @description 绑定安全帽（APP端）
	 * @author chupp
	 * @date 2018年9月10日
	 * @param params
	 *
	 */
	@Override
	@Transactional
	public void bindSafetyHelmet(RecruitModularParams params) {
		SafetyHelmet safetyHelmet = new SafetyHelmet();
		safetyHelmet.setImei(params.getImei());
		SafetyHelmet sh = safetyHelmetMapper.getSafetyHelmetByImei(safetyHelmet);
		if(sh == null) {
			throw new XywgException(600,"安全帽并未初始化，请联系项目经理！");
		}
		List<ProjectWorker> inProject = projectWorkerMapper.isInProject(sh.getProjectCode(), Integer.parseInt(params.getIdCardType()), params.getIdCardNumber());
		if(inProject.size() == 0) {
			throw new XywgException(600,"未加入该安全帽所在项目！");
		}
		safetyHelmetMapper.updateSafetyHelmetRelation(params.getImei());
		ProjectWorker pw = new ProjectWorker();
		pw.setIdCardNumber(params.getIdCardNumber());
		pw.setIdCardType(params.getIdCardType());
		pw.setProjectCode(sh.getProjectCode());
		pw.setShImei(params.getImei());
		projectWorkerMapper.bindSafetyHelmet(pw);
	}

    @Override
    public List<SafetyHelmet> getUnusedHelmetsByProjectCode(Map<String, Object> map) {
        return this.baseMapper.getUnusedHelmetsByProjectCode(map);
    }
}
