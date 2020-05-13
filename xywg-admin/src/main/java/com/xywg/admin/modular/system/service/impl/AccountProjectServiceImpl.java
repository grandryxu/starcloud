package com.xywg.admin.modular.system.service.impl;

import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.service.IProjectMasterService;
import com.xywg.admin.modular.system.dao.AccountProjectMapper;
import com.xywg.admin.modular.system.model.AccountProject;
import com.xywg.admin.modular.system.model.Dept;
import com.xywg.admin.modular.system.model.SwitchType;
import com.xywg.admin.modular.system.service.AccountProjectService;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jingyun_hu
 * @date 2018/7/11
 *
 * 账号与项目关联操作服务实现
 *
 */
@Service
public class AccountProjectServiceImpl implements AccountProjectService{

    @Autowired
    private AccountProjectMapper accountProjectMapper;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private IProjectMasterService projectMasterService;

    @Autowired
    private IUserService userService;


    @Override
    public void add(List<AccountProject> list) {
        accountProjectMapper.insert(list);
    }

    @Override
    public void delete(List<AccountProject> list) {
        accountProjectMapper.delete(list);
    }

    @Override
    public List<ProjectMaster> selectProjectsByAccountAndDeptId(String deptId) {
        return accountProjectMapper.selectProjectsByAccountAndDeptId(deptId , ShiroKit.getUser().getAccount(),userService.getByAccount(ShiroKit.getUser().getAccount()).getIsEnterprise());
    }

    /**
     * 项目切换 , 将切换后的数据存入session中
     *
     * @param deptId
     * @param projectCode   null登陆状态 ""切换公司状态 有值 切换项目状态
     * @return
     */
    @Override
    public void switchProject(String deptId, String projectCode) {
        ShiroKit.setSessionAttr("toggleProjectCode",projectCode);
        ShiroKit.setSessionAttr("toggleDeptId",deptId);

        //公司导航栏
        Dept loginDept = deptService.selectById(ShiroKit.getUser().getDeptId());
        Dept toggleDept = deptService.selectById(deptId);
        String toggleProjectName = "";
        if(!"".equals(projectCode) ){
            //选择项目的情况
            Map<String, Object> toggleProjectMaster = projectMasterService.selectProjectByProjectCode(projectCode);
            toggleProjectName = toggleProjectMaster.get("projectName").toString();
        }

        if("".equals(toggleProjectName)){
            //选公司
            ShiroKit.setSessionAttr("menuName","公司:"+toggleDept.getSimplename());
            ShiroKit.setSessionAttr("breadCrumb",loginDept.getSimplename()+" > "+toggleDept.getSimplename());
        }else{
            //选项目
            ShiroKit.setSessionAttr("menuName","项目:"+toggleProjectName);
            ShiroKit.setSessionAttr("breadCrumb",loginDept.getSimplename()+" > "+toggleDept.getSimplename()+" > "+toggleProjectName);
        }
        ShiroKit.setSessionAttr("isSwitch","1");
    }

    @Override
    public void reset() {
        ShiroKit.setSessionAttr("toggleProjectCode",null);
        ShiroKit.setSessionAttr("toggleDeptId",ShiroKit.getUser().getDeptId().toString());
        ShiroKit.setSessionAttr("projectCodes",null);
        ShiroKit.setSessionAttr("menuName",null);
        ShiroKit.setSessionAttr("breadCrumb",null);
    }

    //获取切换后的所有项目
    @Override
    public List<String> getProjectCodes(){
        String projectCode = ShiroKit.getSessionAttr("toggleProjectCode");
        String deptId = ShiroKit.getSessionAttr("toggleDeptId");
        if("".equals(projectCode) || projectCode == null ){
            //只选公司的情况
            List<String> projectCodes = new ArrayList<>();
            List<ProjectMaster> projectMasterList = accountProjectMapper.selectProjectsByAccountAndDeptId(deptId,ShiroKit.getUser().getAccount(), userService.getByAccount(ShiroKit.getUser().getAccount()).getIsEnterprise());
            for(ProjectMaster p : projectMasterList){
                projectCodes.add(p.getProjectCode());
            }
            return projectCodes;
        }else{
            //选择项目的情况
            List<String> projectCodes = new ArrayList<>();
            projectCodes.add(projectCode);
            return projectCodes;
        }

    }

    /**
     * 登陆Session初始化
     */
    @Override
    public void initSession(){
        ShiroKit.setSessionAttr("toggleDeptId",ShiroKit.getUser().getDeptId().toString());
    }


    /**
     * 获取当前的切换状态
     */
    @Override
    public SwitchType getSwitchType(){
        String toggleProjectCode = ShiroKit.getSessionAttr("toggleProjectCode");
        SwitchType switchType = new SwitchType();
        if(toggleProjectCode == null || "".equals(toggleProjectCode)){
            //企业级
            switchType.setSwitchType(1);
        }else{
            //项目级
            switchType.setSwitchType(0);
            //登陆公司的社会信用代码
            List<String> loginComSocialCreditNumbers = deptService.getUserDeptAndSubdivisionOrganizationCode();
            //切换项目的承包公司
            String pSocialCreditNumber = projectMasterService.selectProjectByProjectCode(toggleProjectCode).get("contractorOrgCode").toString();
            if(loginComSocialCreditNumbers.contains(pSocialCreditNumber)){
                //登陆的用户相对项目为总包
                switchType.setIsGeneralContractor(1);
            }else{
                //登陆的用户相对项目为参建
                switchType.setIsGeneralContractor(0);
            }
        }
        return switchType;
    }

	@Override
	public void updateDefault(Map<String, Object> map) {
		accountProjectMapper.updateDefault(map);
	}

	@Override
	public void addRelationAndDefault(Map<String, Object> map) {
		accountProjectMapper.addRelationAndDefault(map);
	}

	@Override
	public void updateNoDefault(Map<String, Object> map) {
		accountProjectMapper.updateNoDefault(map);
	}

	@Override
	public List<Map<String,String>> getDefaultProject(String account) {
		return accountProjectMapper.getDefaultProject(account);
	}

	@Override
	public int selectIsExistRelationProject(String account) {
		return accountProjectMapper.selectIsExistRelationProject(account);
	}
}
