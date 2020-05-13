package com.xywg.admin.modular.company.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.company.dao.SubContractorCertificationsMapper;
import com.xywg.admin.modular.company.model.SubContractorCertifications;
import com.xywg.admin.modular.company.service.ISubContractorCertificationsService;
import com.xywg.admin.modular.system.service.IDeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-27
 */
@Service
public class SubContractorCertificationsServiceImpl extends ServiceImpl<SubContractorCertificationsMapper,
        SubContractorCertifications> implements ISubContractorCertificationsService {
    @Resource
    private IDeptService deptService;
    @Resource
    private SubContractorCertificationsMapper contractorCertificationsMapper;

    @Override
    public List<Map<String,Object>> getSubContractorCertifications(Map<String,Object> map, Page page){
        return contractorCertificationsMapper.getSubContractorCertifications(map,page);
    }
    @Override
    public List<Map<String,Object>> getSubContractorCertificationsList(Map<String,Object> map, Page page){
        map.put("depts", deptService.getUserDeptAndSubdivisionOrganizationCode());
        return contractorCertificationsMapper.getSubContractorCertificationsList(map,page);
    }

    @Override
    public void del(Long id) {
        contractorCertificationsMapper.del(id);
    }

    @Override
    public void deleteSubContractorCertifications(Map<String, Object> map) {
        map.put("updateUser", ShiroKit.getUser().getName());
        contractorCertificationsMapper.deleteSubContractorCertifications(map);
    }

    @Override
    public void updateStatus() {
        contractorCertificationsMapper.updateStatus();
    }
}
