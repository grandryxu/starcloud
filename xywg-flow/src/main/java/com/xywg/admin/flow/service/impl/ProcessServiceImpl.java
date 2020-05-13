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
import com.xywg.admin.flow.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author 严鹏
 * @date 2019/3/31
 */
@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private ProcessMapper processMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Transactional
    @Override
    public int insert(Process process,Integer userId,Integer deptId,List<Integer> roleIds,String projectId) throws Exception {
        // 解析process 确保符合格式
        if(StringUtils.isEmpty(process.getProcessName()) || StringUtils.isEmpty(process.getVars())){
            throw new Exception("缺少参数");
        }
        JSONObject object = JSONObject.parseObject(process.getVars());
        Set<String> keySets = object.keySet();
        for(String key : keySets){
            JSONObject node = JSONObject.parseObject(object.get(key).toString());
            if(!node.containsKey("prev") || !node.containsKey("next") || !node.containsKey("par") || !node.containsKey("type")){
                throw new Exception("缺少参数");
            }
        }

        // 校验名称
        if(processMapper.findCountByProcessName(process) > 0){
            throw new Exception("流程名称重复");
        }

        // 通过完整性检查，插入数据
        process.setId(UUID.randomUUID().toString());
        process.setCreateTime(new Date());
        process.setCreateUser(userId);
        process.setDeptId(deptId);
        process.setIsDel(0);

        process.setVersion(1);
        process.setProjectId(projectId);

        return processMapper.insert(process);
    }


    @Transactional
    @Override
    public int update(Process process,Integer userId,Integer deptId,List<Integer> roleIds) throws Exception {

        JSONObject object = JSONObject.parseObject(process.getVars());
        Set<String> keySets = object.keySet();
        // 通过完整性检查，插入数据
        process.setId(UUID.randomUUID().toString());
        process.setCreateTime(new Date());
        process.setCreateUser(userId);
        process.setDeptId(deptId);
        process.setIsDel(0);
        processMapper.insert(process);
        return 1;
    }
    @Transactional
    @Override
    public Order start(String processId,Integer userId,Integer deptId,List<Integer> roleIds) throws Exception {

        // 检查流程版本号是否最新
        Boolean isMax = processMapper.isMaxVersionByProcessId(processId);
        if(!isMax){
            throw new Exception("请发起最新的流程");
        }
        // 取出当前对应的流程 并发起申请
        Process process = processMapper.selectById(processId);
        // 若传入dept不为空，则对process进行覆盖
        JSONObject object = JSONObject.parseObject(process.getVars());
        Set<String> keySets = object.keySet();
        keySets.forEach(key->{
            JSONObject node = JSONObject.parseObject(object.get(key).toString());
            // 找出前一个节点为空的节点，即为初始节点
            if (node.getString("prev").isEmpty()) {
                String nextNodeName = node.getString("next");
                if(!nextNodeName.isEmpty()){
                    // 找到下一个节点
                    JSONObject next = JSONObject.parseObject(object.get(nextNodeName).toString());
//                    // 将部门注入
//                    next.getJSONObject("par").put("dept",Collections.singleton(deptId));
//                    next.getJSONObject("par").put("user",Collections.singleton(userId));
                    // 再把这个节点放回去
                    object.put(nextNodeName,next.toJSONString());
                }
            }
        });
        // 覆盖原来的节点信息
        process.setVars(object.toJSONString());
        Order o = createOrder(process,userId,deptId);
        // TODO 导入外部设置，覆盖当前的候选人员
        orderMapper.insert(o);
        return o;
    }

    @Override
    public List<Process> getAll() {
        Map<String,Object> params = new HashMap<>();
        params.put("isDel",0);
        params.put("type",0);
        return processMapper.getAll(params);
    }

    @Override
    public Process getOne(Process process,Integer userId,Integer deptId,List<Integer> roleIds) {
        return processMapper.getOne(process);
    }
    @Override
    public Process getOneMax(Process process,Integer userId,Integer deptId,List<Integer> roleIds) {
        return processMapper.getOneMax(process);
    }


    @Override
    public List<Map<String,Object>> getOneDetail(String processId) {
        List<Map<String,Object>> list = new LinkedList<>();
        Process process = new Process();
        process.setId(processId);
        process = processMapper.getOne(process);
        String vars = process.getVars();
        Map<String,Object> nodes = string2Map(vars);
        String firstKey = null;
        for(String key : nodes.keySet()){
            Map<String,Object> node = string2Map(nodes.get(key).toString());
            if (node.get("prev").toString().isEmpty()) {
                firstKey = key;
            }
            nodes.put(key,node);
        }
        String next = firstKey;
        while(!next.isEmpty()){
            Map<String,Object> m = (Map<String, Object>) nodes.get(next);
            Map<String,Object> detail = string2Map(m.get("par").toString());
            detail.put("name",next);
            list.add(detail);
            next = m.get("next").toString();
        }
        return list;
    }

    @Override
    public void delete1(String id) {

    }

    private Order createOrder(Process process,Integer userId,Integer deptId) {
        Date now = new Date();
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setSn(Order.createSN());
        order.setCreateTime(now);
        order.setCreateUser(userId);
        order.setDeptId(deptId);
        order.setIsDel(0);
        order.setProcessId(process.getId());
        String vars = process.getVars();
        Map<String,Object> nodeMap = string2Map(vars);

        // 找出起始节点
        String firstNodeKey = nodeMap.keySet().stream().filter(k->{
            Map<String,Object> v = string2Map(nodeMap.get(k).toString());
            return StringUtils.isEmpty(v.get("prev").toString());
        }).findAny().get();

        // 存入order的参数
        JSONObject nodes = new JSONObject();

        Map<String,Object> fn = string2Map(nodeMap.get(firstNodeKey).toString());
        fn.put("finished","yes");
        fn.put("createTime",DateFormatCom.getDateFormat());
        fn.put("updateTime",DateFormatCom.getDateFormat());
        fn.put("result",true);
        nodes.put(firstNodeKey,fn);

        // 如果next不为空，则有下一个节点，否则流程结束
        if(!StringUtils.isEmpty(fn.get("next"))){
            Map<String,Object> sn = string2Map(nodeMap.get(fn.get("next").toString()).toString());
            sn.put("finished","no");
            sn.put("createTime",DateFormatCom.getDateFormat());
            nodes.put(fn.get("next").toString(),sn);
            order.setOrderStatus(Order.STATUS_ON);
        }else{
            order.setOrderStatus(Order.STATUS_FINISH);
        }
        order.setVars(JSON.toJSONString(nodes));
        return order;
    }

    private Map<String,Object> string2Map(String vars){
        return JSONObject.parseObject(vars,new TypeReference<Map<String, Object>>(){});
    }


	@Override
	public List<Process> getAllNotByType() {
		Map<String,Object> params = new HashMap<>();
        params.put("isDel",0);
        return processMapper.getAll(params);
	}

    @Override
    public List<Process> selectList(Map<String, Object> map, Page<Process> page) {
        return processMapper.selectList(map,page);
    }
}
