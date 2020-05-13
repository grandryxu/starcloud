package com.xywg.admin.modular.company.controller;

import com.xywg.admin.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.xywg.admin.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.xywg.admin.modular.company.model.ContractorWorker;
import com.xywg.admin.modular.company.service.IContractorWorkerService;

/**
 * 企业工人控制器
 *
 * @author wangcw
 * @Date 2018-05-28 16:51:09
 */
@Controller
@RequestMapping("/contractorWorker")
public class ContractorWorkerController extends BaseController {

    private String PREFIX = "/company/contractorWorker/";

    @Autowired
    private IContractorWorkerService contractorWorkerService;

    /**
     * 跳转到企业工人首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "contractorWorker.html";
    }

    /**
     * 跳转到添加企业工人
     */
    @RequestMapping("/contractorWorker_add")
    public String contractorWorkerAdd() {
        return PREFIX + "contractorWorker_add.html";
    }

    /**
     * 跳转到修改企业工人
     */
    @RequestMapping("/contractorWorker_update/{contractorWorkerId}")
    public String contractorWorkerUpdate(@PathVariable Integer contractorWorkerId, Model model) {
        ContractorWorker contractorWorker = contractorWorkerService.selectById(contractorWorkerId);
        model.addAttribute("item",contractorWorker);
        LogObjectHolder.me().set(contractorWorker);
        return PREFIX + "contractorWorker_edit.html";
    }

    /**
     * 获取企业工人列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return contractorWorkerService.selectList(null);
    }

    /**
     * 新增企业工人
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ContractorWorker contractorWorker) {
        contractorWorkerService.insert(contractorWorker);
        return SUCCESS_TIP;
    }

    /**
     * 删除企业工人
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer contractorWorkerId) {
        contractorWorkerService.deleteById(contractorWorkerId);
        return SUCCESS_TIP;
    }

    /**
     * 修改企业工人
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ContractorWorker contractorWorker) {
        contractorWorkerService.updateById(contractorWorker);
        return SUCCESS_TIP;
    }

    /**
     * 企业工人详情
     */
    @RequestMapping(value = "/detail/{contractorWorkerId}")
    @ResponseBody
    public Object detail(@PathVariable("contractorWorkerId") Integer contractorWorkerId) {
        return contractorWorkerService.selectById(contractorWorkerId);
    }
}
