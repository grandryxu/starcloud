package com.xywg.admin.modular.project.controller;

import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.device.service.IDeviceRecordService;
import com.xywg.admin.modular.project.service.IProjectWorkerService;
import com.xywg.admin.modular.projectSubContractor.service.IProjectSubContractorService;
import com.xywg.admin.modular.system.service.IDeptService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.xywg.admin.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.xywg.admin.modular.project.model.ProjectAddress;
import com.xywg.admin.modular.project.service.IProjectAddressService;

/**
 * 项目工地控制器
 *
 * @author caiwei
 * @Date 2018-08-10 14:35:50
 */
@Controller
@RequestMapping("/projectAddress")
public class ProjectAddressController extends BaseController {

    private String PREFIX = "/projectAddress/";

    @Autowired
    private IProjectAddressService projectAddressService;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IProjectSubContractorService projectSubContractorService;
    @Autowired
    private IProjectWorkerService projectWorkerService;
    @Autowired
    private IDeviceRecordService deviceRecordService;

    /**
     * 跳转到项目工地首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "projectAddress.html";
    }

    /**
     * 跳转到添加项目工地
     */
    @RequestMapping("/projectAddress_add")
    public String projectAddressAdd(@RequestParam String projectCode ,@RequestParam String projectName,Model model) {
        model.addAttribute("projectCode",projectCode);
        model.addAttribute("projectName",projectName);
        return PREFIX + "projectAddress_add.html";
    }

    /**
     * 跳转到修改项目工地
     */
    @RequestMapping("/projectAddress_update/{projectAddressId}")
    public String projectAddressUpdate(@PathVariable Integer projectAddressId, Model model) {
        ProjectAddress projectAddress = projectAddressService.selectById(projectAddressId);
        model.addAttribute("item",projectAddress);
        LogObjectHolder.me().set(projectAddress);
        return PREFIX + "projectAddress_edit.html";
    }

    /**
     * 获取项目工地列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return projectAddressService.selectList(null);
    }

    /**
     * 新增项目工地
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProjectAddress projectAddress) {
        projectAddressService.insert(projectAddress);
        return SUCCESS_TIP;
    }

    /**
     * 删除项目工地
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer projectAddressId) {
        projectAddressService.deleteById(projectAddressId);
        return SUCCESS_TIP;
    }

    /**
     * 修改项目工地
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ProjectAddress projectAddress) {
        projectAddressService.updateById(projectAddress);
        return SUCCESS_TIP;
    }

    /**
     * 项目工地详情
     */
    @RequestMapping(value = "/detail/{projectAddressId}")
    @ResponseBody
    public Object detail(@PathVariable("projectAddressId") Integer projectAddressId) {
        return projectAddressService.selectById(projectAddressId);
    }

    /**
     * 获取当前公司下所有工地地址
     */
    @RequestMapping(value = "/toSubAddress")
    public Object toSubAddress(Model model) {
        model.addAttribute("project",projectAddressService.getProjectAddressByToggleDeptId());
        model.addAttribute("getTotalEntry",projectSubContractorService.getTotalEntry());
        model.addAttribute("getTotalExit",projectSubContractorService.getTotalExit());
        model.addAttribute("getTotalJoin",projectWorkerService.getTotalJoin());
        model.addAttribute("getTotalDevice",deviceRecordService.getTotalDevice());
        //项目切换 切换 菜单导航
        String breadCrumb = "";
        if( ShiroKit.getSessionAttr("breadCrumb") == null){
            String loginDeptName = deptService.selectById(ShiroKit.getUser().getDeptId()).getSimplename();
            breadCrumb = loginDeptName;
        }else{
            breadCrumb = ShiroKit.getSessionAttr("breadCrumb");
        }
        model.addAttribute("breadCrumb",breadCrumb);
        return "/subAddress/subAddress.html";
    }

    /**
     * 获取当前公司下所有工地地址
     */
    @RequestMapping(value = "/getProjectAddressByToggleDeptId")
    @ResponseBody
    public Object getProjectAddressByToggleDeptId() {
        return projectAddressService.getProjectAddressByToggleDeptId();
    }
}
