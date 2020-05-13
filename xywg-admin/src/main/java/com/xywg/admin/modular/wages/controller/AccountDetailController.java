package com.xywg.admin.modular.wages.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import com.xywg.admin.modular.wages.model.AccountDetail;
import com.xywg.admin.modular.wages.model.AccountVo;
import com.xywg.admin.modular.wages.model.PayRollDetailVo;
import com.xywg.admin.modular.wages.service.IAccountDetailService;
import com.xywg.admin.modular.wages.wrapper.AccountDetailWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 计工单详情控制器
 *
 * @author wangcw
 * @Date 2018-06-01 10:13:13
 */
@Controller
@RequestMapping("/accountDetail")
public class AccountDetailController extends BaseController {

    private String PREFIX = "/wages/accountDetail/";

    @Autowired
    private IAccountDetailService accountDetailService;
    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;
    /**
     * 跳转到计工单详情首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "accountDetail.html";
    }

    /**
     * 跳转到添加计工单详情
     */
    @RequestMapping("/accountDetail_add")
    public String accountDetailAdd(Model model) {
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        return PREFIX + "accountDetail_add.html";
    }

    /**
     * 跳转到修改计工单详情
     */
    @RequestMapping("/accountDetail_update/{accountDetailId}")
    public String accountDetailUpdate(@PathVariable Integer accountDetailId, Model model) {
        AccountDetail accountDetail = accountDetailService.selectById(accountDetailId);
        model.addAttribute("item",accountDetail);
        LogObjectHolder.me().set(accountDetail);
        return PREFIX + "accountDetail_edit.html";
    }



    /**
     * 新增计工单详情
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(AccountDetail accountDetail) {
        accountDetailService.insert(accountDetail);
        return SUCCESS_TIP;
    }

    /**
     * 删除计工单详情
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer accountDetailId) {
        accountDetailService.deleteById(accountDetailId);
        return SUCCESS_TIP;
    }

    /**
     * 修改计工单详情
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(AccountDetail accountDetail) {
        accountDetailService.updateById(accountDetail);
        return SUCCESS_TIP;
    }

    /**
     * 计工单详情详情
     */
    @RequestMapping(value = "/detail/{accountDetailId}")
    @ResponseBody
    public Object detail(@PathVariable("accountDetailId") Integer accountDetailId) {
        return accountDetailService.selectById(accountDetailId);
    }

    /**
     * 根据计工单ids获取计工单详情 无分页
     */
    @RequestMapping(value = "/getList")
    @ResponseBody
    public Object getList(@RequestParam Map map) {
        return accountDetailService.getList(map);
    }

    /**
     * 根据计工单ids获取计工单详情 分页
     */
    @RequestMapping(value = "/getListPages")
    @ResponseBody
    public Object getListPages(@RequestParam Map map) {
        Page<PayRollDetailVo> page = new PageFactory<PayRollDetailVo>().defaultPage();
        if(map.get("ids") == null){
            return super.packForBT(page);
        }
        List<PayRollDetailVo> payRollDetails= accountDetailService.selectList(page ,map);
        page.setRecords(payRollDetails);
        return super.packForBT(page);
    }

    /**
     * 根据计工单ids获取计工单详情 分页
     */
    @RequestMapping(value = "/getListNoPages")
    @ResponseBody
    public Object getListNoPages(@RequestParam Map map) {
        if(map.get("ids") == null){
            return new ArrayList<Object>();
        }
        List<PayRollDetailVo> payRollDetails= accountDetailService.getListNoPages(map);
        return payRollDetails;
    }

    @RequestMapping(value = "/addAccountAndAccountDetail")
    @ResponseBody
    public Object addAccountAndAccountDetail(AccountVo accountVo){
        List<Map> list = JSON.parseArray(accountVo.getAccountDetailListStr(),Map.class);
        accountVo.setAccountDetailList(list);
        return accountDetailService.addAccountAndAccountDetail(accountVo);
    }

    @RequestMapping(value = "/getAccountDetailByAccountId")
    @ResponseBody
    public Object getAccountDetailByAccountId(@RequestParam Map<String,Object> map){
        Page<Map<String,Object>> page = new PageFactory<Map<String,Object>>().defaultPage();
        List<Map<String,Object>> list=accountDetailService.getAccountDetailByAccountId(map,page);
        page.setRecords((List<Map<String, Object>>) new AccountDetailWarpper(list).warp());
        return super.packForBT(page);
    }

    @RequestMapping(value = "/getAccountDetailByAccountIdNoPage")
    @ResponseBody
    public Object getAccountDetailByAccountIdNoPage(@RequestParam Map<String,Object> map){
        List<Map<String,Object>> list=accountDetailService.getAccountDetailByAccountIdNoPage(map);
        return (List<Map<String, Object>>) new AccountDetailWarpper(list).warp();
    }


    @RequestMapping(value = "/updateDetail")
    @ResponseBody
    public Object updateDetail(AccountDetail accountDetail){
        return accountDetailService.updateDetail(accountDetail);
    }
}
