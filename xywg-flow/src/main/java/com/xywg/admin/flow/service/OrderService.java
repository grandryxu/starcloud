package com.xywg.admin.flow.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.flow.entity.Order;

import java.util.List;

/**
 * @author 严鹏
 * @date 2019/3/31
 */
public interface OrderService {

    /**
     * 查找待办任务
     * @param order 查询条件 sn
     * @return
     */
    Page<Order> findActiveOrders(Order order,Integer userId,Integer deptId,List<Integer> roleIds);

    /**
     * 获取当前用户所有流程的id
     * @return
     */
    List<String> findActiveOrderIds(Integer userId, Integer deptId, List<Integer> roleIds);

    /**
     * 获取当前用户历史流程
     * @return
     */
    Page<String> findHistoryOrderIds(Integer userId,Integer deptId,List<Integer> roleIds);



    Order execute(String orderId, String comment, Boolean result, Integer userId,Integer deptId,List<Integer> roleIds) throws Exception;

    /**
     * 获取当前用户的已办
     */
    Page<Order> findHistoryOrders(Order order,Integer userId,Integer deptId,List<Integer> roleIds);

    Page<Order> findOrders(Order order,Integer userId,Integer deptId,List<Integer> roleIds);

    Order findOne(Order order);


    Order getOrderByOrderId(String orderId);

    Boolean dropByOrderId(String orderId,Integer userId,Integer deptId,List<Integer> roleIds) throws Exception;


    /**
     * 不要调用此接口，不要手动更改order表的数据
     */
    @Deprecated
    int updateByOrder(Order order);
}
