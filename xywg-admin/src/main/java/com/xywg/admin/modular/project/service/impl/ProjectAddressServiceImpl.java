package com.xywg.admin.modular.project.service.impl;

import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.project.model.ProjectAddress;
import com.xywg.admin.modular.project.dao.ProjectAddressMapper;
import com.xywg.admin.modular.project.model.vo.ProjectAddressVo;
import com.xywg.admin.modular.project.service.IProjectAddressService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.modular.projectSubContractor.model.ProjectSubContractor;
import com.xywg.admin.modular.projectSubContractor.service.IProjectSubContractorService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 项目地址 服务实现类
 * </p>
 *
 * @author caiwei123
 * @since 2018-08-10
 */
@Service
public class ProjectAddressServiceImpl extends ServiceImpl<ProjectAddressMapper, ProjectAddress> implements IProjectAddressService {

    @Autowired
    private IProjectSubContractorService projectSubContractorService;

    @Override
    public List<ProjectAddress> selectAddressByProjectCode(String projectCode) {
        return this.baseMapper.selectAddressByProjectCode(projectCode);
    }

    @Override
    public Boolean removeAddressWithProjectCode(String projectCode) {
        return this.baseMapper.removeAddressWithProjectCode(projectCode);
    }

    @Override
    public List<ProjectAddressVo> getProjectAddressByToggleDeptId() {
        String toggleDeptId = (String)ShiroKit.getSession().getAttribute("toggleDeptId");
        if(StringUtils.isBlank(toggleDeptId)) {
        	throw new XywgException(600, "请先登录!");
        }
//        String toggleDeptId = ShiroKit.getSession().getAttribute("toggleDeptId")==null?"":ShiroKit.getSession().getAttribute("toggleDeptId").toString();
        //获取登陆公司下所有的项目公司关系 并对其进行筛选  一个项目下有多条记录的保留总包记录
        List<ProjectSubContractor> projectSubContractorList = projectSubContractorService.getGroupByProjectSubContractorByDeptId(toggleDeptId);
        Map<String,Long> idMap = new HashMap<String,Long>();
        List<Long> idList = new ArrayList<Long>();
        if(projectSubContractorList.size()==0){
            return new ArrayList<ProjectAddressVo>();
        }else{
            for (ProjectSubContractor p : projectSubContractorList){
                if(idMap.containsKey(p.getProjectCode())){
                    //已经有项目code
                    if(p.getContractorType() == 16){
                        idMap.put(p.getProjectCode(),p.getId());
                    }
                }else{
                    //没有项目code
                    idMap.put(p.getProjectCode(),p.getId());
                }
            }
            for(String key:idMap.keySet()){
                idList.add(idMap.get(key));
            }
            return this.baseMapper.getProjectAddressByToggleDeptId(toggleDeptId,idList);
        }
    }


    @Override
    public List<ProjectAddress> queryProjectAddressById(String organizationCode, Long id) {
        return this.baseMapper.queryProjectAddressById(organizationCode,id);
    }
}
