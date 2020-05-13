package com.xywg.admin.modular.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import com.xywg.admin.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;

import com.xywg.admin.modular.bad.model.WorkerBadRecords;
import com.xywg.admin.modular.bad.warpper.WorkerBadRecordsWarpper;
import com.xywg.admin.modular.system.model.WorkKind;
import com.xywg.admin.modular.system.service.IWorkKindService;
import com.xywg.admin.modular.system.warpper.WorkKindWarpper;

/**
 * 控制器
 *
 * @author wangcw
 * @Date 2018-06-05 10:45:04
 */
@Controller
@RequestMapping("/workKind")
public class WorkKindController extends BaseController {

    private String PREFIX = "/system/workKind/";

    @Autowired
    private IWorkKindService workKindService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "workKind.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/workKind_add")
    public String workKindAdd() {
        return PREFIX + "workKind_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/workKind_update/{workKindId}")
    public String workKindUpdate(@PathVariable Long workKindId, Model model) {
        WorkKind workKind = workKindService.selectWorkKindById(workKindId);
        model.addAttribute("item",workKind);
        LogObjectHolder.me().set(workKind);
        return PREFIX + "workKind_edit.html";
    }

    /**
     * 获取列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map) {
    	 Page<WorkKind> page = new PageFactory<WorkKind>().defaultPage();
         List<Map<String, Object>> workKind = workKindService.selectWorkKind(page, map);
         page.setRecords((List<WorkKind>) new WorkKindWarpper(workKind).warp());
         return super.packForBT(page);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(WorkKind workKind) {
        workKindService.insert(workKind);
        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long workKindId) {
        workKindService.deleteById(workKindId);
        return SUCCESS_TIP;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(WorkKind workKind) {
        workKindService.updateById(workKind);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{workKindId}")
    @ResponseBody
    public Object detail(@PathVariable("workKindId") Integer workKindId) {
        return workKindService.selectById(workKindId);
    }
}
