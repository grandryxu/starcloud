package com.xywg.admin.modular.health.controller;

import com.xywg.admin.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.xywg.admin.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.xywg.admin.modular.health.model.WorkerHealth;
import com.xywg.admin.modular.health.service.IWorkerHealthService;

/**
 * 健康信息控制器
 *
 * @author wangcw
 * @Date 2018-08-17 15:57:16
 */
@Controller
@RequestMapping("/workerHealth")
public class WorkerHealthController extends BaseController {

    private String PREFIX = "/health/workerHealth/";

    @Autowired
    private IWorkerHealthService workerHealthService;

    /**
     * 跳转到健康信息首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "workerHealth.html";
    }

    /**
     * 跳转到添加健康信息
     */
    @RequestMapping("/workerHealth_add")
    public String workerHealthAdd() {
        return PREFIX + "workerHealth_add.html";
    }

    /**
     * 跳转到修改健康信息
     */
    @RequestMapping("/workerHealth_update/{workerHealthId}")
    public String workerHealthUpdate(@PathVariable Integer workerHealthId, Model model) {
        WorkerHealth workerHealth = workerHealthService.selectById(workerHealthId);
        model.addAttribute("item",workerHealth);
        LogObjectHolder.me().set(workerHealth);
        return PREFIX + "workerHealth_edit.html";
    }

    /**
     * 获取健康信息列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return workerHealthService.selectList(null);
    }

    /**
     * 新增健康信息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(WorkerHealth workerHealth) {
        workerHealthService.insert(workerHealth);
        return SUCCESS_TIP;
    }

    /**
     * 删除健康信息
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer workerHealthId) {
        workerHealthService.deleteById(workerHealthId);
        return SUCCESS_TIP;
    }

    /**
     * 修改健康信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(WorkerHealth workerHealth) {
        workerHealthService.updateById(workerHealth);
        return SUCCESS_TIP;
    }

    /**
     * 健康信息详情
     */
    @RequestMapping(value = "/detail/{workerHealthId}")
    @ResponseBody
    public Object detail(@PathVariable("workerHealthId") Integer workerHealthId) {
        return workerHealthService.selectById(workerHealthId);
    }
}
