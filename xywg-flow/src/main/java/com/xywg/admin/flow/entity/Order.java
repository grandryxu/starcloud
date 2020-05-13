package com.xywg.admin.flow.entity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 严鹏
 * @date 2019/3/31
 */
@Data
public class Order{

    /**
     * 流转中
     */
    public static final Integer STATUS_ON = 0x01;

    /**
     * 已拒绝
     */
    public static final Integer STATUS_DROP = 0x02;

    /**
     * 已通过
     */
    public static final Integer STATUS_FINISH = 0x03;

    /**
     * 已撤销
     */
    public static final Integer STATUS_CANCEL = 0x04;
    /**
     *
     */
    private String id;

    private Date createTime;

    private Integer createUser;

    private Date updateTime;

    private Integer updateUser;

    private Integer deptId;

    private Integer isDel;

    private String processId;

    /**
     * 流水号
     */
        private String sn;

    /**
     * 参数                                                 <br/>
     * {                                                   <br/>
     *     "node1":{                                    // <br/>
     *          "type":1,                               // 节点类型 0-无 1-或签 2-会签
     *          "finished":"no",                        // 完成状态 <br/>
     *          "prev":"",                              // 前一个节点 <br/>
     *          "next":"node2",                         // 后一个节点 <br/>
     *          "par":{"user":[],"role":[],"dept":[]},  // 参与人 <br/>
     *          "createTime":"2019-01-01 12:00:00",     // 到达时间 <br/>
     *          "updateTime":"2019-01-01 13:00:00",     // 处理时间 <br/>
     *          "updateUser":"",                        // 处理人 <br/>
     *          "comment":"",                           // 处理意见 若或签{"userId":"comment"}<br />
     *          "result": true                          // 处理结果 true - 通过 false - 不通过  若或签{"userId":true}<br/>
     *     }                                               <br/>
     * }
     */
    private String vars;

    /**
     * 流程状态
     */
    private Integer orderStatus;

    /**
     * Transient
     * 当前用户id
     */
    private Integer cUserId;

    /**
     * Transient
     * 当前角色id
     */
    private List<Integer> cRoleIds;

    /**
     * Transient
     * 当前部门id
     */
    private Integer cDeptId;


    public static String createSN() {
        Date now = new Date();
        Integer rn = new Random(System.currentTimeMillis()).nextInt(10000);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(now) + String.format("%04d",rn);
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


    private Map<String,Object> string2Map(String vars){
        return JSONObject.parseObject(vars,new TypeReference<Map<String, Object>>(){});
    }

}
