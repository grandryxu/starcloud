package com.xywg.admin.modular.worker.controller;

import com.xywg.admin.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.xywg.admin.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.xywg.admin.modular.worker.model.WorkerContractRule;
import com.xywg.admin.modular.worker.service.IWorkerContractRuleService;

/**
 * 工人合同控制器
 *
 * @author wangcw
 * @Date 2018-06-08 09:19:00
 */
@Controller
@RequestMapping("/workerContractRule")
public class WorkerContractRuleController extends BaseController {

    private String PREFIX = "/worker/workerContractRule/";

    @Autowired
    private IWorkerContractRuleService workerContractRuleService;

    /**
     * 跳转到工人合同首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "workerContractRule.html";
    }

    /**
     * 跳转到添加工人合同
     */
    @RequestMapping("/workerContractRule_add")
    public String workerContractRuleAdd() {
        return PREFIX + "workerContractRule_add.html";
    }

    /**
     * 跳转到修改工人合同
     */
    @RequestMapping("/workerContractRule_update/{workerContractRuleId}")
    public String workerContractRuleUpdate(@PathVariable Integer workerContractRuleId, Model model) {
        WorkerContractRule workerContractRule = workerContractRuleService.selectById(workerContractRuleId);
        model.addAttribute("item",workerContractRule);
        LogObjectHolder.me().set(workerContractRule);
        return PREFIX + "workerContractRule_edit.html";
    }

    /**
     * 获取工人合同列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return workerContractRuleService.selectList(null);
    }

    /**
     * 新增工人合同
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(WorkerContractRule workerContractRule) {
        workerContractRuleService.insert(workerContractRule);
        return SUCCESS_TIP;
    }

    /**
     * 删除工人合同
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer workerContractRuleId) {
        workerContractRuleService.deleteById(workerContractRuleId);
        return SUCCESS_TIP;
    }

    /**
     * 修改工人合同
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(WorkerContractRule workerContractRule) {
        workerContractRuleService.updateById(workerContractRule);
        return SUCCESS_TIP;
    }

    /**
     * 工人合同详情
     */
    @RequestMapping(value = "/detail/{workerContractRuleId}")
    @ResponseBody
    public Object detail(@PathVariable("workerContractRuleId") Integer workerContractRuleId) {
        return workerContractRuleService.selectById(workerContractRuleId);
    }
}
