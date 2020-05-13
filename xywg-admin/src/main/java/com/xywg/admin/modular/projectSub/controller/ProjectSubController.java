package com.xywg.admin.modular.projectSub.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.wrapper.ProjectMasterWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.xywg.admin.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.xywg.admin.modular.projectSub.model.ProjectSub;
import com.xywg.admin.modular.projectSub.service.IProjectSubService;

import java.util.List;
import java.util.Map;
/**
 * 劳务分包控制器
 *
 * @author wangcw
 * @Date 2018-07-10 08:52:49
 */
@Controller
@RequestMapping("/projectSub")
public class ProjectSubController extends BaseController {

    private String PREFIX = "/projectSub/projectSub/";

    @Autowired
    private IProjectSubService projectSubService;

    /**
     * 跳转到劳务分包首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "projectSub.html";
    }

    /**
     * 跳转到添加劳务分包
     */
    @RequestMapping("/projectSub_add")
    public String projectSubAdd() {
        return PREFIX + "projectSub_add.html";
    }

    /**
     * 跳转到修改劳务分包
     */
    @RequestMapping("/projectSub_update/{projectSubId}")
    public String projectSubUpdate(@PathVariable Integer projectSubId, Model model) {
        ProjectSub projectSub = projectSubService.selectById(projectSubId);
        model.addAttribute("item",projectSub);
        LogObjectHolder.me().set(projectSub);
        return PREFIX + "projectSub_edit.html";
    }

    /**
     * 获取劳务分包列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String,Object> map) {
        Page<ProjectSub> page = new PageFactory<ProjectSub>().defaultPage();
        List<ProjectSub> list= projectSubService.selectList(map,page);
        page.setRecords(list);
        return super.packForBT(page);
    }

    /**
     * 新增劳务分包
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProjectSub projectSub) {
        projectSubService.insert(projectSub);
        return SUCCESS_TIP;
    }

    /**
     * 新增一条默认劳务分包
     */
    @RequestMapping(value = "/addDefault")
    @ResponseBody
    public Object addDefault(String projectCode) {
        //获取一条默认的劳务分包信息
        ProjectSub projectSub = this.projectSubService.getDefaultProjectSub();
        projectSub.setProjectCode(projectCode);
        projectSubService.insert(projectSub);
        return SUCCESS_TIP;
    }


    /**
     * 删除劳务分包
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long projectSubId) {
        projectSubService.deleteById(projectSubId);
        return SUCCESS_TIP;
    }

    /**
     * 修改劳务分包
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@RequestParam Map<String,Object> map) {
        ProjectSub projectSub = new ProjectSub(Long.valueOf(map.get("id").toString()),map.get("subId").toString(),map.get("childNumId").toString(),map.get("projectCode").toString());
        projectSubService.updateById(projectSub);
        return SUCCESS_TIP;
    }

    /**
     * 劳务分包详情
     */
    @RequestMapping(value = "/detail/{projectSubId}")
    @ResponseBody
    public Object detail(@PathVariable("projectSubId") Integer projectSubId) {
        return projectSubService.selectById(projectSubId);
    }
}
