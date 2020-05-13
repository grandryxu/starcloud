package com.xywg.admin.modular.system.controller;

import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.modular.system.model.PartitionedProject;
import com.xywg.admin.modular.system.service.IPartitionedProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分部工程控制器
 *
 * @author wangcw
 * @Date 2018-07-18 14:36:27
 */
@Controller
@RequestMapping("/partitionedProject")
public class PartitionedProjectController extends BaseController {

    private String PREFIX = "/sysPartitionedProject/partitionedProject/";

    @Autowired
    private IPartitionedProjectService partitionedProjectService;

    /**
     * 跳转到分部工程首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "partitionedProject.html";
    }

    /**
     * 跳转到添加分部工程
     */
    @RequestMapping("/partitionedProject_add")
    public String partitionedProjectAdd() {
        return PREFIX + "partitionedProject_add.html";
    }

    /**
     * 跳转到修改分部工程
     */
    @RequestMapping("/partitionedProject_update/{partitionedProjectId}")
    public String partitionedProjectUpdate(@PathVariable Integer partitionedProjectId, Model model) {
        PartitionedProject partitionedProject = partitionedProjectService.selectById(partitionedProjectId);
        model.addAttribute("item",partitionedProject);
        LogObjectHolder.me().set(partitionedProject);
        return PREFIX + "partitionedProject_edit.html";
    }

    /**
     * 获取分部工程列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return partitionedProjectService.selectList(null);
    }

    /**
     * 新增分部工程
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PartitionedProject partitionedProject) {
        partitionedProjectService.insert(partitionedProject);
        return SUCCESS_TIP;
    }

    /**
     * 删除分部工程
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer partitionedProjectId) {
        partitionedProjectService.deleteById(partitionedProjectId);
        return SUCCESS_TIP;
    }

    /**
     * 修改分部工程
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PartitionedProject partitionedProject) {
        partitionedProjectService.updateById(partitionedProject);
        return SUCCESS_TIP;
    }

    /**
     * 分部工程详情
     */
    @RequestMapping(value = "/detail/{partitionedProjectId}")
    @ResponseBody
    public Object detail(@PathVariable("partitionedProjectId") Integer partitionedProjectId) {
        return partitionedProjectService.selectById(partitionedProjectId);
    }

    /**
     * 根据名称获取其子分布工程
     * @param num
     * @return List<PartitionedProject>
     * @author 蔡伟
     */
    @RequestMapping("/getChildrenByNum")
    @ResponseBody
    public List<PartitionedProject> getChildrenByNum(@RequestParam String num){
        return this.partitionedProjectService.getChildrenByNum(num);
    }
}
