package com.xywg.admin.modular.project.controller;

import com.xywg.admin.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.xywg.admin.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.xywg.admin.modular.project.model.ProjectTrainRecord;
import com.xywg.admin.modular.project.service.IProjectTrainRecordService;

/**
 * 项目培训详情控制器
 *
 * @author wangcw
 * @Date 2018-06-14 10:33:22
 */
@Controller
@RequestMapping("/projectTrainRecord")
public class ProjectTrainRecordController extends BaseController {

    private String PREFIX = "/project/projectTrainRecord/";

    @Autowired
    private IProjectTrainRecordService projectTrainRecordService;

    /**
     * 跳转到项目培训详情首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "projectTrainRecord.html";
    }

    /**
     * 跳转到添加项目培训详情
     */
    @RequestMapping("/projectTrainRecord_add")
    public String projectTrainRecordAdd() {
        return PREFIX + "projectTrainRecord_add.html";
    }

    /**
     * 跳转到修改项目培训详情
     */
    @RequestMapping("/projectTrainRecord_update/{projectTrainRecordId}")
    public String projectTrainRecordUpdate(@PathVariable Integer projectTrainRecordId, Model model) {
        ProjectTrainRecord projectTrainRecord = projectTrainRecordService.selectById(projectTrainRecordId);
        model.addAttribute("item",projectTrainRecord);
        LogObjectHolder.me().set(projectTrainRecord);
        return PREFIX + "projectTrainRecord_edit.html";
    }

    /**
     * 获取项目培训详情列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return projectTrainRecordService.selectList(null);
    }

    /**
     * 新增项目培训详情
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProjectTrainRecord projectTrainRecord) {
        projectTrainRecordService.insert(projectTrainRecord);
        return SUCCESS_TIP;
    }

    /**
     * 删除项目培训详情
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer projectTrainRecordId) {
        projectTrainRecordService.deleteById(projectTrainRecordId);
        return SUCCESS_TIP;
    }

    /**
     * 修改项目培训详情
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ProjectTrainRecord projectTrainRecord) {
        projectTrainRecordService.updateById(projectTrainRecord);
        return SUCCESS_TIP;
    }

    /**
     * 项目培训详情详情
     */
    @RequestMapping(value = "/detail/{projectTrainRecordId}")
    @ResponseBody
    public Object detail(@PathVariable("projectTrainRecordId") Integer projectTrainRecordId) {
        return projectTrainRecordService.selectById(projectTrainRecordId);
    }
}
