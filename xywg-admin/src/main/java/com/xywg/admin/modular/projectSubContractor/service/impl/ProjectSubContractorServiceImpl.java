package com.xywg.admin.modular.projectSubContractor.service.impl;

import com.baomidou.mybatisplus.plugins.Page;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.modular.projectSubContractor.dao.ProjectSubContractorMapper;
import com.xywg.admin.modular.projectSubContractor.model.AppProjectSubContractorDto;

import com.xywg.admin.core.shiro.ShiroKit;

import com.xywg.admin.modular.projectSubContractor.model.ProjectSubContractor;
import com.xywg.admin.modular.projectSubContractor.service.IProjectSubContractorService;
import com.xywg.admin.modular.system.dao.DeptMapper;
import com.xywg.admin.modular.system.service.AccountProjectService;
import com.xywg.admin.modular.system.service.IDeptService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 项目参建企业信息 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-23
 */
@Service
public class ProjectSubContractorServiceImpl extends ServiceImpl<ProjectSubContractorMapper, ProjectSubContractor> implements IProjectSubContractorService {


    @Resource
    private ProjectSubContractorMapper projectSubContractorMapper;
    @Resource
    private DeptMapper deptMapper;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private AccountProjectService accountProjectService;

    @Override
    public List<Map<String, Object>> getList(Map<String,Object> map, Page page) {
        map.put("deptId",ShiroKit.getUser().getDeptId());
        map.put("projectCodes",accountProjectService.getProjectCodes());
        List<Map<String, Object>> list = projectSubContractorMapper.getList(map, page);
        return list;
    }

    @Override
    public Map<String, Object> getById(Integer projectSubContractorId) {
        return projectSubContractorMapper.getById(projectSubContractorId);
    }

    @Override
    public int changeState(Map<String,Object> map){
        return projectSubContractorMapper.changeState(map);
    }

    @Override
    public Map<String,Object> getCompanyById(Integer id){
        return projectSubContractorMapper.getCompanyById(id);
    }

    @Override
    public boolean insert(ProjectSubContractor projectSubContractor){
        retBool(this.baseMapper.insert(projectSubContractor));
        projectSubContractorMapper.addBussSubContractorConstruction(ShiroKit.getUser().getName(),deptMapper.selectById(ShiroKit.getUser().getDeptId()).getSocialCreditNumber(),projectSubContractor.getOrganizationCode());
        return true;
    }

    @Override
    public List<AppProjectSubContractorDto> getProjectsByCompany( String organizationCode, Integer projectType, String key,Integer projectStatus,Integer id) {
        /*List<String> organizationCodes=deptMapper.getAPPUserDeptAndSubdivisionOrganizationCode(organizationCode);*/
        return this.baseMapper.getProjectsByCompany(organizationCode,projectType,key,projectStatus,id);
    }

    @Override
    public List<AppProjectSubContractorDto> v116GetProjectsByCompany( String organizationCode, Integer projectType, String key,Integer projectStatus,Integer id,Integer pageNo ,Integer pageSize, String type, String account) {
        /*List<String> organizationCodes=deptMapper.getAPPUserDeptAndSubdivisionOrganizationCode(organizationCode);*/
        return this.baseMapper.v116GetProjectsByCompany(organizationCode,projectType,key,projectStatus,id,(pageNo-1)*pageSize,pageSize, type, account);
    }


    @Override
    public AppProjectSubContractorDto getProjectCount(String organizationCode, String type, String account) {
        return this.baseMapper.getProjectCount(organizationCode, type, account);
    }

    @Override
    public AppProjectSubContractorDto getCooperationProject(String organizationCode, String projectCode) {
        return this.baseMapper.getCooperationProject(organizationCode,projectCode);
    }

    @Override
    public Integer toggleJoinStatus(String projectCode, Integer status, String organizationCodes) {
        List<String> organizationCodesList = new ArrayList<String>();
        String[] organizationCodeArray = organizationCodes.split(",");
        for (int i = 0; i < organizationCodeArray.length; i++) {
            organizationCodesList.add(organizationCodeArray[i]);
        }
        return projectSubContractorMapper.toggleJoinStatus(projectCode , status , organizationCodesList);
    }

    @Override
    public Integer deleteByProjectCodeAndOrganizationCodes(String projectCode, String organizationCodes) {
        List<String> organizationCodeList = new ArrayList<String>();
        String[] organizationCodeArray = organizationCodes.split(",");
        for(int i=0;i<organizationCodeArray.length;i++){
            organizationCodeList.add(organizationCodeArray[i]);
        }
        return projectSubContractorMapper.deleteByProjectCodeAndOrganizationCodes(projectCode ,organizationCodeList );
    }

    @Override
    public List<AppProjectSubContractorDto> getCooperationProjectList(String projectCode) {
        return this.baseMapper.getCooperationProjectList(projectCode);
    }

    @Override
    public List<AppProjectSubContractorDto> v116GetCooperationProjectList(String projectCode,Integer pageNo , Integer pageSize) {
        return this.baseMapper.v116GetCooperationProjectList(projectCode,(pageNo-1)*pageSize,pageSize);
    }

    @Override
    public List<ProjectSubContractor> getListByProjectCodeAndOrganizationCode(String projectCode, String organizationCode) {
        return projectSubContractorMapper.getListByProjectCodeAndOrganizationCode(projectCode,organizationCode);
    }

    /**
     * 
     * @description 获取进场项目数
     * @author chupp
     * @date 2018年6月21日
     * @return
     * @see com.xywg.admin.modular.projectSubContractor.service.IProjectSubContractorService#getTotalEntry()
     *
     */
	@Override
	public int getTotalEntry() {
		Integer deptId = ShiroKit.getUser().getDeptId();
		if(deptId == null || deptId == 0) {
			return -1;
		}
		List<String> list = deptService.getOrganizationCodeByDeptId(deptId);
		return this.baseMapper.getTotalEntry(list,accountProjectService.getProjectCodes());
	}
	
	/**
     * 
     * @description 获取退场项目数
     * @author chupp
     * @date 2018年6月21日
     * @return
     * @see com.xywg.admin.modular.projectSubContractor.service.IProjectSubContractorService#getTotalEntry()
     *
     */
	@Override
	public int getTotalExit() {
		Integer deptId = ShiroKit.getUser().getDeptId();
		if(deptId == null || deptId == 0) {
			return -1;
		}
		List<String> list = deptService.getOrganizationCodeByDeptId(deptId);
		return this.baseMapper.getTotalExit(list,accountProjectService.getProjectCodes());
	}
    @Override
    public boolean setPm(Map<String, Object> map) {
        return this.baseMapper.setPm(map);
    }

    @Override
    public Integer toggleJoinStatusWithIds(String ids, Integer status) {
        return this.baseMapper.toggleJoinStatusWithIds(ids ,status);
    }

    @Override
    public List<ProjectSubContractor> getGroupByProjectSubContractorByDeptId(String toggleDeptId) {
        return this.baseMapper.getGroupByProjectSubContractorByDeptId(toggleDeptId);
    }

    @Override
    public Map<String, Object> getContractorById(Integer projectSubContractorId) {

        return this.baseMapper.getContractorById(projectSubContractorId);
    }


    @Override
    public Long selectProjectCodeAndOrjCode(Object projectCode, String companyOrgcode) {

        return this.baseMapper.selectProjectCodeAndOrjCode(projectCode,companyOrgcode);
    }
}
