package com.xywg.admin.modular.project.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.model.vo.ProjectMasterVo;
import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import com.xywg.admin.modular.project.service.IProjectMasterService;
import com.xywg.admin.modular.project.wrapper.ProjectMasterWrapper;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.system.service.IDictService;
import com.xywg.admin.modular.system.service.IPartitionedProjectService;
import com.xywg.admin.modular.team.service.ITeamMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目信息控制器
 *
 * @author wangcw
 * @Date 2018-05-23 16:30:36
 */
@Controller
@RequestMapping("/cooperationProjectMaster")
public class CooperationProjectMasterController extends BaseController {

    private String PREFIX = "/project/cooperationProjectMaster/";

    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;

    @Autowired
    private IDictService dictService;

    @Autowired
    private IProjectMasterService projectMasterService;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private ITeamMasterService teamMasterService;

    @Autowired
    private IPartitionedProjectService partitionedProjectService;

    /**
     * 跳转到项目信息首页
     */
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("projectStatus",dictService.selectByName("状态"));
        model.addAttribute("contractorType",dictService.selectByName("参建类型"));
        model.addAttribute("deviceType",dictService.selectByName("考勤类型"));
        model.addAttribute("projectStatus", dictService.selectByName("项目状态"));
        return PREFIX + "cooperationProjectMaster.html";
    }

    /**
     * 跳转到项目信息首页
     */
    @RequestMapping("/listTable")
    public String list(Model model,@RequestParam String pageType) {
        model.addAttribute("projectStatus",dictService.selectByName("状态"));
        model.addAttribute("contractorType",dictService.selectByName("参建类型"));
        model.addAttribute("projectStatus", dictService.selectByName("项目状态"));
        model.addAttribute("pageType",pageType);
        return PREFIX + "cooperationProjectMaster_list.html";
    }


    /**
     * 跳转到添加项目信息
     */
    @RequestMapping("/cooperationProjectMaster_add")
    public String projectMasterAdd(Model model) {
        model.addAttribute("projectActivityType", dictService.selectByName("项目活动类型"));
        model.addAttribute("projectCategory", dictService.selectByName("项目分类"));
        model.addAttribute("projectStatus", dictService.selectByName("项目状态"));
        model.addAttribute("deviceType", dictService.selectByName("考勤类型"));
        return PREFIX + "cooperationProjectMaster_add.html";
    }

    /**
     * 双击查看项目信息
     */
    @RequestMapping("/cooperationProjectMaster_view")
    public String projectMasterInfo(@RequestParam Long projectMasterId,@RequestParam String pageType, Model model) {
    	String loginOrganizationCode = deptService.selectById(ShiroKit.getUser().getDeptId()).getSocialCreditNumber();
        model.addAttribute("loginOrganizationCode", loginOrganizationCode);
        model.addAttribute("projectActivityType", dictService.selectByName("项目活动类型"));
        model.addAttribute("projectCategory", dictService.selectByName("项目分类"));
        model.addAttribute("projectStatus", dictService.selectByName("项目状态"));
        model.addAttribute("deviceType", dictService.selectByName("考勤类型"));
        model.addAttribute("projectSub", partitionedProjectService.getChildrenByNum("022"));
        model.addAttribute("pageType", pageType);
        Map<String,Object> projectMaster = projectMasterService.selectById(projectMasterId);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("projectCode",projectMaster.get("projectCode").toString());
        map.put("organizationCode", loginOrganizationCode);
        model.addAttribute("team",teamMasterService.getAllList(map)) ;
        model.addAttribute("item",projectMaster);
        LogObjectHolder.me().set(projectMaster);
        return PREFIX + "cooperationProjectMaster_info.html";
    }



    /**
     * 跳转到修改项目信息
     */
    @RequestMapping("/cooperationProjectMaster_update/{projectMasterId}")
    public String projectMasterUpdate(@PathVariable Integer projectMasterId, Model model) {
        model.addAttribute("projectActivityType", dictService.selectByName("项目活动类型"));
        model.addAttribute("projectCategory", dictService.selectByName("项目分类"));
        model.addAttribute("projectStatus", dictService.selectByName("项目状态"));
        model.addAttribute("deviceType", dictService.selectByName("考勤类型"));
        ProjectMaster projectMaster = cooperationProjectMasterService.selectById(projectMasterId);
        model.addAttribute("item",projectMaster);
        LogObjectHolder.me().set(projectMaster);
        return PREFIX + "cooperationProjectMaster_edit.html";
    }

    /**
     * 获取项目信息列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String,Object> map) {
        Page<ProjectMaster> page = new PageFactory<ProjectMaster>().defaultPage();
        List<Map<String,Object>> list=cooperationProjectMasterService.selectList(map,page);
        page.setRecords((List<ProjectMaster>) new ProjectMasterWrapper(list).warp());
        return super.packForBT(page);
    }

    /**
     * 新增项目信息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProjectMaster projectMaster) {
        cooperationProjectMasterService.insert(projectMaster);
        return projectMaster;
    }

    /**
     * 删除项目信息
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long projectMasterId) {
        cooperationProjectMasterService.deleteById(projectMasterId);
        return SUCCESS_TIP;
    }

    /**
     * 修改项目信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ProjectMaster projectMaster) {
        cooperationProjectMasterService.updateById(projectMaster);
        return SUCCESS_TIP;
    }

    /**
     * 项目信息详情
     */
    @RequestMapping(value = "/detail/{projectMasterId}")
    @ResponseBody
    public Object detail(@PathVariable("projectMasterId") Integer projectMasterId) {
        return cooperationProjectMasterService.selectById(projectMasterId);
    }

    /**
     * 根据项目名称模糊匹配项目
     */
    @RequestMapping(value = "/getByProjectName/{projectName}")
    @ResponseBody
    public Object getByProjectName(@PathVariable String projectName){
        return cooperationProjectMasterService.getByProjectName(projectName);
    }

    /**
     * 获取所有参建项目 无分页
     */
    @RequestMapping(value = "/getList")
    @ResponseBody
    public Object getList(@RequestParam Map<String,Object> map) {
        return cooperationProjectMasterService.getList(map);
    }

    /**
     * 获取指定公司下所有参建项目 有分页
     */
    @RequestMapping(value = "/getAllList")
    @ResponseBody
    public Object getAllList(@RequestParam Map<String,Object> map) {
        Page<ProjectMasterVo> page = new PageFactory<ProjectMasterVo>().defaultPage();
        List<ProjectMasterVo> list=cooperationProjectMasterService.getAllList(map,page);
        page.setRecords(list);
        return super.packForBT(page);
    }
}


