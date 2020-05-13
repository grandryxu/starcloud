package com.xywg.admin.modular.recuritWorker.controller;

import com.xywg.admin.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.xywg.admin.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.xywg.admin.modular.recuritWorker.model.RecruitWorker;
import com.xywg.admin.modular.recuritWorker.service.IRecruitWorkerService;

/**
 * 招聘信息控制器
 *
 * @author wangcw
 * @Date 2018-08-17 18:22:46
 */
@Controller
@RequestMapping("recruitWorker")
public class RecruitWorkerController extends BaseController {

    private String PREFIX = "/recuritWorker/recruitWorker/";

    @Autowired
    private IRecruitWorkerService recruitWorkerService;

    /**
     * 跳转到招聘信息首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "recruitWorker.html";
    }

    /**
     * 跳转到添加招聘信息
     */
    @RequestMapping("/recruitWorker_add")
    public String recruitWorkerAdd() {
        return PREFIX + "recruitWorker_add.html";
    }

    /**
     * 跳转到修改招聘信息
     */
    @RequestMapping("/recruitWorker_update/{recruitWorkerId}")
    public String recruitWorkerUpdate(@PathVariable Integer recruitWorkerId, Model model) {
        RecruitWorker recruitWorker = recruitWorkerService.selectById(recruitWorkerId);
        model.addAttribute("item",recruitWorker);
        LogObjectHolder.me().set(recruitWorker);
        return PREFIX + "recruitWorker_edit.html";
    }

    /**
     * 获取招聘信息列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return recruitWorkerService.selectList(null);
    }

    /**
     * 新增招聘信息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(RecruitWorker recruitWorker) {
        recruitWorkerService.insert(recruitWorker);
        return SUCCESS_TIP;
    }

    /**
     * 删除招聘信息
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer recruitWorkerId) {
        recruitWorkerService.deleteById(recruitWorkerId);
        return SUCCESS_TIP;
    }

    /**
     * 修改招聘信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(RecruitWorker recruitWorker) {
        recruitWorkerService.updateById(recruitWorker);
        return SUCCESS_TIP;
    }

    /**
     * 招聘信息详情
     */
    @RequestMapping(value = "/detail/{recruitWorkerId}")
    @ResponseBody
    public Object detail(@PathVariable("recruitWorkerId") Integer recruitWorkerId) {
        return recruitWorkerService.selectById(recruitWorkerId);
    }
}
