package com.xywg.admin.modular.longxin.controller;

import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.modular.longxin.service.InviteBidService;
import com.xywg.admin.modular.longxin.service.LxOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tcw on 2019/7/9.
 */
@Controller
@RequestMapping("/lxOrder")
public class LxOrderController extends BaseController {

    private String PREFIX = "/longxin/lxOrder/";

    @Autowired
    private LxOrderService lxOrderService;
    
    @Autowired
    private InviteBidService inviteBidService;


    @RequestMapping("/index")
    public String index(){
        return PREFIX + "index.html";
    }

    @RequestMapping("/regFlow")
    public String regFlow(){
        return PREFIX + "regFlow.html";
    }

    @RequestMapping("/regFlowHis")
    public String regFlowHis(){
        return PREFIX + "regFlowHis.html";
    }


    @RequestMapping("/indexSub")
    public String indexSub(){
        return PREFIX + "indexSub.html";
    }

    @RequestMapping("/history")
    public String history(){
        return PREFIX + "history.html";
    }

    @RequestMapping("/historySub")
    public String historySub(){
        return PREFIX + "historySub.html";
    }

    @RequestMapping("/historyReg")
    public String historyReg(){
        return PREFIX + "historyReg.html";
    }


    /**
     * 获取所有的待办招标任务
     * @return
     */
    @RequestMapping("getAll")
    @ResponseBody
    public Object getAll(){
        return  super.packForBT(lxOrderService.getAll());
    }

    /**
     * 获取所有的企业审批待办招标任务
     * @return
     */
    @RequestMapping("getAllReg")
    @ResponseBody
    public Object getAllReg(){
        return  super.packForBT(lxOrderService.getAllReg());
    }
    /**
     * 获取招标历史任务
     * @param request
     * @return
     */
    @RequestMapping("getAllHistory")
    @ResponseBody
    public Object getAllHistory(HttpServletRequest request){
        request.setAttribute("pageSize","100000");
        return  super.packForBT(lxOrderService.getAllHistory());
    }

    /**
     * 获取招标历史任务
     * @param request
     * @return
     */
    @RequestMapping("getAllHistorySub")
    @ResponseBody
    public Object getAllHistorySub(HttpServletRequest request){
        request.setAttribute("pageSize","100000");
        return  super.packForBT(lxOrderService.getAllHistorySub());
    }

    /**
     * 获取企业审批历史任务
     */
    @RequestMapping("getAllHistoryReg")
    @ResponseBody
    public Object getAllHistoryReg(HttpServletRequest request){
        request.setAttribute("pageSize","100000");
        return  super.packForBT(lxOrderService.getAllHistoryReg());
    }

    @RequestMapping("getAllSub")
    @ResponseBody
    public Object getAllSub(){
        return  super.packForBT(inviteBidService.getAllSub());
    }



    @RequestMapping("shenhe")
    @ResponseBody
    public Object shenhe(String orderId,Boolean result ,String bussId){
        System.out.println(orderId+result+bussId);
        return  lxOrderService.shenhe(orderId,result,bussId);
    }

    @RequestMapping("shenheReg")
    @ResponseBody
    public Object shenheReg(String orderId,Boolean result ,String bussId, String socialCreditNumber){
        return  lxOrderService.shenheReg(orderId,result,bussId, socialCreditNumber);
    }
    
    
    @RequestMapping("shenheFix")
    @ResponseBody
    public Object shenheFix(String orderId,Boolean result ,String bussId){
        return  lxOrderService.shenheFix(orderId,result,bussId);
    }


}
