package com.xywg.admin.modular.wages.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.base.tips.ErrorTip;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import com.xywg.admin.modular.wages.model.Account;
import com.xywg.admin.modular.wages.service.IAccountService;
import com.xywg.admin.modular.wages.wrapper.AccountWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 计工单控制器
 *
 * @author wangcw
 * @Date 2018-06-01 10:11:04
 */
@Controller
@RequestMapping("/account")
public class AccountController extends BaseController {

    private String PREFIX = "/wages/account/";

    @Autowired
    private IAccountService accountService;
    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;
    /**
     * 跳转到计工单首页
     */
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        return PREFIX + "account.html";
    }

    /**
     * 跳转到添加计工单
     */
    @RequestMapping("/account_add")
    public String accountAdd() {
        return "/wages/accountDetail/" + "accountDetail_add.html";
    }

    /**
     * 跳转到修改计工单
     */
    @RequestMapping("/account_update/{accountId}")
    public String accountUpdate(@PathVariable Integer accountId, Model model) {
        Account account = accountService.getById(accountId);
        model.addAttribute("item",account);
        LogObjectHolder.me().set(account);
        return PREFIX + "account_edit.html";
    }
    /**
     * 跳转到选择工人页面
     */
    @RequestMapping(value = "account_workerList")
    public String workerList(Model model,@RequestParam String projectCode,@RequestParam String team){
        model.addAttribute("projectCode",projectCode);
        model.addAttribute("team",team);
        return PREFIX + "account_workerList.html";
    }
    /**
     * 跳转到详情页面
     */
    @RequestMapping("/account_view/{accountId}")
    public String accountView(@PathVariable Integer accountId, Model model){
        Account account = accountService.getById(accountId);
        model.addAttribute("item",account);
        LogObjectHolder.me().set(account);
        return PREFIX + "account_view.html";
    }

    /**
     * 获取计工单列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String,Object> map) {
        Page<Account> page = new PageFactory<Account>().defaultPage();
        List<Map<String,Object>> subCons= accountService.getList(map,page);
        page.setRecords((List<Account>) new AccountWarpper(subCons).warp());
        return super.packForBT(page);
    }

    /**
     * 新增计工单
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Account account) {
        accountService.insert(account);
        return SUCCESS_TIP;
    }

    /**
     * 删除计工单
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer accountId) {
        if(accountService.del(accountId)==0){
            return new ErrorTip(600,"已生成工资单不能删除！");
        }else{
            return SUCCESS_TIP;
        }
    }

    /**
     * 修改计工单
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Account account) {
        accountService.updateById(account);
        return SUCCESS_TIP;
    }

    /**
     * 计工单详情
     */
    @RequestMapping(value = "/detail/{accountId}")
    @ResponseBody
    public Object detail(@PathVariable("accountId") Integer accountId) {
        return accountService.selectById(accountId);
    }

    /**
     * 根据项目code和班组no查询计工单
     */
    @RequestMapping(value = "/getAccountListByTeamNo")
    @ResponseBody
    public List<Map<String,Object>> getAccountListByTeamNo(@RequestParam Map<String,Object> map){
        return accountService.getAccountListByTeamNo(map);
    }



}
