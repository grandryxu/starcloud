package com.xywg.admin.modular.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.base.tips.ErrorTip;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.system.model.ProjectDataNumber;
import com.xywg.admin.modular.system.service.IProjectDataNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/prodataNumber")
public class ProjectDataNumberController extends BaseController {

    private String PREFIX = "/system/prodataNumber/";

    @Autowired
    private IProjectDataNumberService service;

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "prodataNumber.html";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map) {
        Page<ProjectDataNumber> page = new PageFactory<ProjectDataNumber>().defaultPage();
        List<ProjectDataNumber> list = this.service.selectList(page, map);
        page.setRecords(list);
        return super.packForBT(page);
    }

    /**
     * 新增
     *
     * @return
     */
    @RequestMapping("/prodataNumber_add")
    public String prodataNumberAdd() {
        return PREFIX + "prodataNumber_add.html";
    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProjectDataNumber obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("project_name", obj.getProjectName());
        map.put("nt_state", 1);
        if (service.getProjectDataNumberByColumns(map).size() > 0){
            return new ErrorTip(-2, "已存在记录！");
        }
        Date date = new Date();
        obj.setNtState(1);
        obj.setCreateTime(date);
        obj.setCreateUser(ShiroKit.getUser().getName());
        if (service.insert(obj))
            return SUCCESS_TIP;
        else
            return new ErrorTip(-2, "新增异常！");
    }

    /**
     * 修改
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping("/prodataNumber_update/{pid}")
    public String prodataNumberView(@PathVariable long pid, Model model) {
        ProjectDataNumber obj = service.getProjectDataNumberByID(pid);
        model.addAttribute("item", obj);
        LogObjectHolder.me().set(obj);
        return PREFIX + "prodataNumber_edit.html";
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ProjectDataNumber obj) {
        obj.setNtState(1);
        obj.setModityTime(new Date());
        obj.setModifyer(ShiroKit.getUser().getName());
        LogObjectHolder.me().set(obj);
        if (service.updateById(obj))
            return SUCCESS_TIP;
        else
            return new ErrorTip(-2, "修改失败！");

    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String idList) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("updateUser", ShiroKit.getUser().getName());
        map.put("ids", idList);
        if (service.delByIDList(map))
            return SUCCESS_TIP;
        else
            return new ErrorTip(-2, "删除失败！");
    }



}
