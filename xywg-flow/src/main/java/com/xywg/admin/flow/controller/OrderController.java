package com.xywg.admin.flow.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.flow.entity.Order;
import com.xywg.admin.flow.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;

/**
 * @author 严鹏
 * @date 2019/3/31
 */
@Controller
@RequestMapping("/order")
public class OrderController  {


    private static final String SUCCESS_TIP = "操作成功";
    private static final String ERROR_TIP = "操作失败";

    private OrderService orderService;
    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }


    /**
     * 获取当前登录人的待办
     * 仅做示例及测试，不要直接调用该方法
     * @param order 带sn的order
     * @return
     */
    @GetMapping("/findActiveOrders")
    @ResponseBody
    public Object findActiveOrders(Order order){
        Page<Order> page = orderService.findActiveOrders(order,1,1,Collections.singletonList(1));
        return page;
    }


    /**
     * 获取当前登录人的已办
     * 仅做示例及测试，不要直接调用该方法
     * @param order 带sn的order
     * @return
     */
    @GetMapping("/findHistoryOrders")
    @ResponseBody
    public Object findHistoryOrders(Order order){
        Page<Order> page = orderService.findHistoryOrders(order,1,null,null);
        return page;
    }

    /**
     * 执行任务
     * 做示例及测试，不要直接调用该方法
     * @param order 带id的order
     * @param result 通过-true 不通过-false
     * @return
     */
    @PostMapping("/execute")
    @ResponseBody
    public Object execute(Order order,String comment,Boolean result){
        try{
            orderService.execute(order.getId(),comment,result,2156,1,Collections.singletonList(1));
            return SUCCESS_TIP;
        }catch (Exception e){
            e.printStackTrace();
            return ERROR_TIP;
        }

    }


    /**
     * 查询已发起的申请
     * 做示例及测试，不要直接调用该方法
     * @param order
     * @return
     */
    @GetMapping("/findOrders")
    @ResponseBody
    public Object findOrders(Order order){
        return orderService.findOrders(order,1,null,null);
    }

    @GetMapping("/findOne")
    @ResponseBody
    public Object findOne(Order order){
        return orderService.findOne(order);
    }
}

