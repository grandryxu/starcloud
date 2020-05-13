package com.xywg.admin.flow.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.flow.entity.Order;
import com.xywg.admin.flow.entity.Process;
import com.xywg.admin.flow.service.ProcessService;
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
@RequestMapping("/process")
public class ProcessController{

    private static final Object SUCCESS_TIP = "操作成功";
    private static final String ERROR_TIP = "操作失败";
    private ProcessService processService;

    @Autowired
    public void setProcessService(ProcessService processService) {
        this.processService = processService;
    }

    /**
     * 保存一条流程
     * @param process 流程
     * @return 一个对象
     */
    @PostMapping("/insert")
    @ResponseBody
    public Object insert(Process process){
        try {
            int res = processService.insert(process,null,null,null,null);
            return res > 0 ? SUCCESS_TIP : ERROR_TIP;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR_TIP;
        }
    }


    /**
     * 发起申请
     * 仅做示例及测试，不要直接调用该方法
     * @param process 带id的流程
     * @return 发起的申请
     */
    @PostMapping("/start")
    @ResponseBody
    public Object start(Process process){
        try {
            return processService.start(process.getId(), 1, 1, Collections.singletonList(1));
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR_TIP;
        }
    }

    /**
     * 获取所有的流程
     * @return
     */
    @GetMapping("/getAll")
    @ResponseBody
    public Object findAll(){
        return processService.getAll();
    }

    /**
     * 获取所有的流程
     * @return
     */
    @GetMapping("/getOne")
    @ResponseBody
    public Object findOne(Process process){

        return processService.getOne(process,null,null,null);
    }



}

