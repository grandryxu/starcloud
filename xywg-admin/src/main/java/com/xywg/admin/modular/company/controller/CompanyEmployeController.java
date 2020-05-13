package com.xywg.admin.modular.company.controller;

import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.modular.company.model.CompanyEmploye;
import com.xywg.admin.modular.company.service.ICompanyEmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 企业从业人员控制器
 *
 * @author wangcw
 * @Date 2018-05-28 11:12:04
 */
@Controller
@RequestMapping("/companyEmploye")
public class CompanyEmployeController extends BaseController {

    private String PREFIX = "/company/companyEmploye/";

    @Autowired
    private ICompanyEmployeService companyEmployeService;

    /**
     * 跳转到企业从业人员首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "companyEmploye.html";
    }

    /**
     * 跳转到添加企业从业人员
     */
    @RequestMapping("/companyEmploye_add")
    public String companyEmployeAdd() {
        return PREFIX + "companyEmploye_add.html";
    }

    /**
     * 跳转到修改企业从业人员
     */
    @RequestMapping("/companyEmploye_update/{companyEmployeId}")
    public String companyEmployeUpdate(@PathVariable Integer companyEmployeId, Model model) {
        CompanyEmploye companyEmploye = companyEmployeService.selectById(companyEmployeId);
        model.addAttribute("item",companyEmploye);
        LogObjectHolder.me().set(companyEmploye);
        return PREFIX + "companyEmploye_edit.html";
    }

    /**
     * 获取企业从业人员列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return companyEmployeService.selectList(null);
    }

    /**
     * 新增企业从业人员
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CompanyEmploye companyEmploye) {
        companyEmployeService.insert(companyEmploye);
        return SUCCESS_TIP;
    }

    /**
     * 删除企业从业人员
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long companyEmployeId) {
        companyEmployeService.deleteById(companyEmployeId);
        return SUCCESS_TIP;
    }

    /**
     * 修改企业从业人员
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CompanyEmploye companyEmploye) {
        companyEmployeService.updateById(companyEmploye);
        return SUCCESS_TIP;
    }

    /**
     * 企业从业人员详情
     */
    @RequestMapping(value = "/detail/{companyEmployeId}")
    @ResponseBody
    public Object detail(@PathVariable("companyEmployeId") Integer companyEmployeId) {
        return companyEmployeService.selectById(companyEmployeId);
    }
}
