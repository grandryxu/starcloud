package com.xywg.admin.flow.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.flow.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 严鹏
 * @date 2019/3/31
 */
@Mapper
public interface OrderMapper{

    List<Order> findActiveOrders(@Param("page") Page page, @Param("order") Order order);

    List<Order> findHistoryOrders(@Param("page") Page page, @Param("order") Order order);

    List<Order> findOrders(@Param("page") Page page,@Param("order") Order order);

    int insert(Order order);

    Order selectById(String orderId);

    int updateById(Order order);
}
