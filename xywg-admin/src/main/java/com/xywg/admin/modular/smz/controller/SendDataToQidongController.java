package com.xywg.admin.modular.smz.controller;

import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.modular.smz.service.ISendDataToQidongService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
/*
* 对外数据接口
* */

//@Controller
@RequestMapping("/lxLongXin")
public class SendDataToQidongController extends BaseController {

    @Autowired
    private ISendDataToQidongService service;

    /**
     * 登录
     *
     * @Title: login
     * @return: JSONObject
     */
    @PostMapping(value = "/login")
    @ResponseBody
    public JSONObject login(@RequestBody Map<String, Object> map) {
        return service.login(map);
    }

    /**
     * 获取企业项目信息
     *
     * @param map
     * @return
     */
    @PostMapping(value = "/project")
    @ResponseBody
    public Map<String, Object> project(@RequestBody Map<String, Object> map) {
        return service.project(map);
    }

    /**
     * 获取企业项目的班组
     *
     * @param map
     * @return
     */
    @PostMapping(value = "/teamMaster")
    @ResponseBody
    public Map<String, Object> teamMaster(@RequestBody Map<String, Object> map) {
        return service.teamMaster(map);
    }

    /**
     * 获取企业项目的设备列表
     *
     */
    @PostMapping(value = "/device")
    @ResponseBody
    public Map<String,Object> device(@RequestBody Map<String,Object>map){
        return service.device(map);
    }

    /**
     * 获取工人基础信息
     * @param map
     * @return
     */
    @PostMapping(value = "/workerMaster")
    @ResponseBody
    public Map<String,Object>workerMaster(@RequestBody Map<String,Object>map){
        return service.workerMaster(map);
    }

    /**
     *项目工人关系
     * @param map
     * @return
     */
    @PostMapping(value = "/projectWorker")
    @ResponseBody
    public Map<String,Object>projectWorker(@RequestBody Map<String,Object>map){
        return service.projectWorker(map);
    }

    /**
     * 考勤记录
     * @param map
     * @return
     */
    @PostMapping(value = "/deviceRecord")
    @ResponseBody
    public Map<String,Object>deviceRecord(@RequestBody Map<String,Object> map){
        return service.deviceRecord(map);
    }




    @PostMapping(value = "/projectAddress")
    @ResponseBody
    public Map<String,Object> projectAddress(@RequestBody Map<String,Object>map){
        return service.projectAddress(map);
    }



}
