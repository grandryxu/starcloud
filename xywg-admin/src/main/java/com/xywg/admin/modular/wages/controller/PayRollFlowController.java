package com.xywg.admin.modular.wages.controller;

import com.xywg.admin.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.xywg.admin.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.xywg.admin.modular.wages.model.PayRollFlow;
import com.xywg.admin.modular.wages.service.IPayRollFlowService;

/**
 * 工资流水控制器
 *
 * @author wangcw
 * @Date 2018-06-01 10:14:56
 */
@Controller
@RequestMapping("/payRollFlow")
public class PayRollFlowController extends BaseController {

    private String PREFIX = "/wages/payRollFlow/";

    @Autowired
    private IPayRollFlowService payRollFlowService;

    /**
     * 跳转到工资流水首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "payRollFlow.html";
    }

    /**
     * 跳转到添加工资流水
     */
    @RequestMapping("/payRollFlow_add")
    public String payRollFlowAdd() {
        return PREFIX + "payRollFlow_add.html";
    }

    /**
     * 跳转到修改工资流水
     */
    @RequestMapping("/payRollFlow_update/{payRollFlowId}")
    public String payRollFlowUpdate(@PathVariable Integer payRollFlowId, Model model) {
        PayRollFlow payRollFlow = payRollFlowService.selectById(payRollFlowId);
        model.addAttribute("item",payRollFlow);
        LogObjectHolder.me().set(payRollFlow);
        return PREFIX + "payRollFlow_edit.html";
    }

    /**
     * 获取工资流水列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return payRollFlowService.selectList(null);
    }

    /**
     * 新增工资流水
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PayRollFlow payRollFlow) {
        payRollFlowService.insert(payRollFlow);
        return SUCCESS_TIP;
    }

    /**
     * 删除工资流水
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer payRollFlowId) {
        payRollFlowService.deleteById(payRollFlowId);
        return SUCCESS_TIP;
    }

    /**
     * 修改工资流水
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PayRollFlow payRollFlow) {
        payRollFlowService.updateById(payRollFlow);
        return SUCCESS_TIP;
    }

    /**
     * 工资流水详情
     */
    @RequestMapping(value = "/detail/{payRollFlowId}")
    @ResponseBody
    public Object detail(@PathVariable("payRollFlowId") Integer payRollFlowId) {
        return payRollFlowService.selectById(payRollFlowId);
    }

    /**
     *
     */
    @RequestMapping(value = "/getPayRollFlowByIdCardAndIdNumber")
    @ResponseBody
    public Object getPayRollFlowByIdCardAndIdNumber(@RequestParam String idCard ,@RequestParam String idNumber) {
        return payRollFlowService.getPayRollFlowByIdCardAndIdNumber(idCard , idNumber);
    }
}
