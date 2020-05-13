package com.xywg.admin.flow.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.flow.common.DateFormatCom;
import com.xywg.admin.flow.entity.Order;
import com.xywg.admin.flow.entity.Process;
import com.xywg.admin.flow.mapper.OrderMapper;
import com.xywg.admin.flow.mapper.ProcessMapper;
import com.xywg.admin.flow.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static java.util.Optional.empty;

/**
 * @author 严鹏
 * @date 2019/3/31
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProcessMapper processMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private HttpServletRequest request;


    @Override
    public Page<Order> findActiveOrders(Order order,Integer userId,Integer deptId,List<Integer> roleIds) {
        Page page = initPage(request);
        order.setCUserId(userId);
        order.setCDeptId(deptId);
        order.setCRoleIds(roleIds);
        List<Order> res = orderMapper.findActiveOrders(page,order);
        res.forEach(o -> {
            Map.Entry<String,Object> aNode = getCurrNodeFromOrder(o);
            o.setVars(JSON.toJSONString(aNode));
        });
        return new Page<Order>().setRecords(res);
    }

    @Override
    public Page<Order> findHistoryOrders(Order order,Integer userId,Integer deptId,List<Integer> roleIds) {
        Page page = initPage(request);
        order.setCUserId(userId);
        List<Order> res = orderMapper.findHistoryOrders(page,order);
        res.forEach(o -> {
            Map.Entry<String,Object> aNode = getLatestNodeFromOrder(o,userId);
            o.setVars(JSON.toJSONString(aNode));
            });
        return new Page<Order>().setRecords(res);
    }

    @Override
    public Page<String> findHistoryOrderIds(Integer userId,Integer deptId,List<Integer> roleIds) {
        Order order =  new Order();
        Page page = initPage(request);
        order.setCUserId(userId);
        List<String> orderIds = new ArrayList<>();
        List<Order> res = orderMapper.findHistoryOrders(page,order);
        res.forEach(o -> {
            Map.Entry<String,Object> aNode = getLatestNodeFromOrder(o,userId);
            o.setVars(JSON.toJSONString(aNode));
            orderIds.add(o.getId());
        });
        return new Page<String>().setRecords(orderIds);
    }



    @Transactional
    @Override
    public Order execute(String orderId, String comment, Boolean result,Integer userId,Integer deptId,List<Integer> roleIds) throws Exception {
        Order order = orderMapper.selectById(orderId);
        Boolean nodeResult = result;
        // 当前节点
        Map.Entry<String,Object> aNode = getCurrNodeFromOrder(order);
        if(aNode != null){
            JSONObject node = JSONObject.parseObject(aNode.getValue().toString());
            JSONObject par = node.getJSONObject("par");
            // 鉴权
            List<Integer> jUser = par.getJSONArray("user").toJavaList(Integer.class);
            List<Integer> jDept = par.getJSONArray("dept").toJavaList(Integer.class);
            List<Integer> jRole = par.getJSONArray("role").toJavaList(Integer.class);

            // id、部门、角色 均不在可见范围内，抛异常
            if(!jUser.contains(userId) && !jDept.contains(deptId)){
                boolean contains = false;
                for(Integer roleId : roleIds){
                    if(jRole.contains(roleId)){
                        contains = true;
                        break;
                    }
                }
                if(!contains){
                    throw new Exception("当前人员无权执行此流程");
                }
            }

            // 判断节点类型
            Integer type = node.getInteger("type");
            node.put("updateTime",DateFormatCom.getDateFormat());
            node.put("updateUser",userId);
            // 若是或签
            if(type == 2){
                JSONObject jsonResult =  node.getJSONObject("result");
                // 若已审批过，抛异常
                if(jsonResult != null && jsonResult.containsKey(userId.toString())){
                    throw new Exception("当前人员已执行过审批");
                }

                // 有权限继续

                if(jsonResult == null){
                    jsonResult = new JSONObject();
                }
                jsonResult.put(userId.toString(),result);
                node.put("result",jsonResult);
                JSONObject commentObj = node.getJSONObject("comment");
                if(commentObj == null){
                    commentObj = new JSONObject();
                }
                commentObj.put(userId.toString(),comment);
                node.put("comment",commentObj);
                // 判断该节点是否结束
                // 查询该任务的所有参与人员
                node.put("finished","yes");
                for(Integer id : jUser){
                    if(!jsonResult.keySet().contains(id.toString())){
                        node.put("finished","no");
                    }
                }
                // 若有不同意的 直接置为false
                for(String key : jsonResult.keySet()){
                    if(!jsonResult.getBoolean(key)){
                        nodeResult = false;
                        node.put("finished","yes");
                        break;
                    }
                }
            }else{
                // 有权限继续
                node.put("finished","yes");
                node.put("result",result);
                node.put("comment",comment);
            }
            // 存入order的参数
            JSONObject nodes = JSONObject.parseObject(order.getVars());
            nodes.put(aNode.getKey(),node);
            String next = node.getString("next");
            Process p = processMapper.selectById(order.getProcessId());
            if(p == null){
                throw new Exception("流程不存在或已删除");
            }
            if("yes".equals(node.getString("finished"))){
                // 若当前节点完成了 进行下一步的判断 否则只更新本节点
                // 通过 且 有下一步
                if(nodeResult && !StringUtils.isEmpty(next) ){
                    JSONObject vars = JSONObject.parseObject(p.getVars());
                    JSONObject nextNode = vars.getJSONObject(next);
//                    if(!StringUtils.isEmpty(deptId)){
//                        nextNode.getJSONObject("par").put("dept",Collections.singleton(deptId));
//                    }
                    nextNode.put("finished","no");
                    nextNode.put("createTime",DateFormatCom.getDateFormat());
                    nodes.put(next,nextNode);
                }else {
                    // 不通过 或者 没有下一步 都是直接结束流程
                    order.setOrderStatus(result ? Order.STATUS_FINISH : Order.STATUS_DROP);
                    order.setUpdateTime(new Date());
                    order.setUpdateUser(userId);
                }
            }else{
                // 当前节点未完成
                order.setUpdateTime(new Date());
                order.setUpdateUser(userId);
            }

            order.setVars(JSON.toJSONString(nodes));
            orderMapper.updateById(order);
            return order;

        }else{
            throw new Exception("未找到当前任务");
        }
    }



    @Override
    public Page<Order> findOrders(Order order,Integer userId,Integer deptId,List<Integer> roleIds) {
        Page page = initPage(request);
        order.setCreateUser(userId);
        return new Page<Order>().setRecords(orderMapper.findOrders(page,order));
    }

    @Override
    public Order findOne(Order order) {
        return orderMapper.selectById(order.getId());
    }

    private Map<String,Object> string2Map(String vars){
        return JSONObject.parseObject(vars,new TypeReference<Map<String, Object>>(){});
    }


    private Map.Entry<String,Object> getCurrNodeFromOrder(Order o) {
        Map<String,Object> vars = string2Map(o.getVars());
        Optional<Map.Entry<String, Object>> aNode = vars
                .entrySet()
                .stream()
                .filter(m->{
                    Map<String,Object> node = string2Map(m.getValue().toString());
                    return ("no".equals(node.get("finished").toString().toLowerCase()));
                })
                .findAny();
        return aNode.orElseGet(null);
    }


    private Map.Entry<String,Object> getLatestNodeFromOrder(Order o,Integer userId) {
        Map<String,Object> vars = string2Map(o.getVars());
        Optional<Map.Entry<String,Object>> aNode = vars.entrySet().stream().filter(m->{
            Map<String,Object> node = string2Map(m.getValue().toString());
            return !StringUtils.isEmpty(node.get("updateUser")) && userId.equals(Integer.parseInt(node.get("updateUser").toString()));
        }).sorted(this::compareTime).findFirst();

        return aNode.orElseGet(null);
    }

    private int compareTime(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
        Map<String,Object> m1 = string2Map(o1.getValue().toString());
        Map<String,Object> m2 = string2Map(o2.getValue().toString());
        if(!m1.containsKey("updateTime")){
            return -1;
        }else if(!m2.containsKey("updateTime")){
            return 1;
        }else{
            Date d1 = DateFormatCom.StrToDate(m1.get("updateTime").toString());
            Date d2 = DateFormatCom.StrToDate(m2.get("updateTime").toString());
            return d1.after(d2) ? 1 : -1;
        }
    }


    @Override
    public Order getOrderByOrderId(String orderId) {
        return orderMapper.selectById(orderId);
    }

    @Override
    public Boolean dropByOrderId(String orderId, Integer userId, Integer deptId, List<Integer> roleIds) throws Exception {
        Order order = orderMapper.selectById(orderId);
        // 当前节点
        Map.Entry<String,Object> aNode = getFirstNodeFromOrder(order);
        if(aNode != null){
            JSONObject node = JSONObject.parseObject(aNode.getValue().toString());
            JSONObject par = node.getJSONObject("par");
            // 鉴权
            List<Integer> jUser = par.getJSONArray("user").toJavaList(Integer.class);
            List<Integer> jDept = par.getJSONArray("dept").toJavaList(Integer.class);
            List<Integer> jRole = par.getJSONArray("role").toJavaList(Integer.class);

            // id、部门、角色 均不在可见范围内，抛异常
            if(!jUser.contains(userId) && !jDept.contains(deptId)){
                boolean contains = false;
                for(Integer roleId : roleIds){
                    if(jRole.contains(roleId)){
                        contains = true;
                        break;
                    }
                }
                if(!contains){
                    throw new Exception("当前人员无权撤回");
                }
            }
            // 有权限 就直接将order置为finished
            order.setOrderStatus(Order.STATUS_CANCEL);
            return true;
        }else{
            throw new Exception("未找到当前任务");
        }
    }


    @Override
    public int updateByOrder(Order order) {
        return orderMapper.updateById(order);
    }

    @Override
    public List<String> findActiveOrderIds(Integer userId, Integer deptId, List<Integer> roleIds) {
        Order order = new Order();
        order.setCUserId(userId);
        order.setCDeptId(Integer.valueOf(deptId));
        order.setCRoleIds(roleIds);
        Page page = initPage(request);
        List<String> orderIds = new ArrayList<>();
        List<Order> res = orderMapper.findActiveOrders(page, order);
        if(res==null){
            return null;
        }else {
            res.forEach(o -> {
                Map.Entry<String,Object> aNode = getCurrNodeFromOrder(o);
                o.setVars(JSON.toJSONString(aNode));
                orderIds.add(o.getId());
            });
            return orderIds;
        }
    }

    /**
     * 意见历史详情
     * @param main
     * @return
     */
    public List<Map<String,Object>> getCommentHistoryDetail(String main) {
        OrderServiceImpl orderService = new OrderServiceImpl();
         com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(main);
        Optional<Map.Entry<String, Object>> aNode = jsonObject
                .entrySet()
                .stream()
                .filter(m->{
                    Map<String,Object> node = orderService.string2Map(m.getValue().toString());
                    return ("".equals(node.get("prev")));
                })
                .findAny();
        Map.Entry<String,Object> firstNode = aNode.orElseGet(null);
        Map<String,Object> node = orderService.string2Map(firstNode.getValue().toString());
        String key = (String) node.get("next");
        List<Map<String,Object>> resultList = new ArrayList<>();
        Map<String,Object> item = orderService.getHistoryOrderInfo(jsonObject,key);
        while (item!=null){
            resultList.add(item);
            String nextKey = item.get("next").toString();
            if("".equals(nextKey)){
                break;
            }
            item = orderService.getHistoryOrderInfo(jsonObject,nextKey);
        }

        return  resultList;
    }

    public Map<String,Object> getHistoryOrderInfo(JSONObject jsonObject,String name){
        Optional<Map.Entry<String, Object>>aNode  = jsonObject
                .entrySet()
                .stream()
                .filter(m-> (name.equals(m.getKey())))
                .findAny();
        if(empty().equals(aNode)){
            return null;
        }
        Map.Entry<String,Object> node = aNode.orElseGet(null);
        if(node==null){
            return null;
        }
        Map<String,Object> mapNode = string2Map(node.getValue().toString());
        return mapNode;
    }


    private Page initPage(HttpServletRequest request) {
        Page page = new Page();
        page.setLimit(10);
        page.setOffset(0);

        if(request.getAttribute("pageSize") != null){
            System.out.println(request.getAttribute("pageSize"));
            page.setSize(Integer.parseInt(request.getAttribute("pageSize").toString()));
            if(request.getAttribute("pageNo") != null){
                page.setOffset(page.getLimit() * Integer.parseInt(request.getAttribute("pageNo").toString()));
            }
        }
        return page;
    }


    private Map.Entry<String,Object> getFirstNodeFromOrder(Order o) {
        Map<String,Object> vars = string2Map(o.getVars());
        Optional<Map.Entry<String, Object>> aNode = vars
                .entrySet()
                .stream()
                .filter(m->{
                    Map<String,Object> node = string2Map(m.getValue().toString());
                    return ("".equals(node.get("prev").toString().toLowerCase()));
                })
                .findAny();
        return aNode.orElseGet(null);
    }

}
