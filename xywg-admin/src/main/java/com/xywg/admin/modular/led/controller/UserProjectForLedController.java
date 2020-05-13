package com.xywg.admin.modular.led.controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.modular.led.service.UserProjectForLedService;

/**
 * LED数据接口
 *
 * @ClassName: UserProjectForLedController
 * @Description:TODO(LED数据接口)
 * @author: wangshibo
 * @date: 2018年7月17日 上午9:50:53
 * @Copyright: 2018 www.wangshibo.com Inc. All rights reserved.
 * 注意：本内容仅限于江苏星云网格信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/userProject")
public class UserProjectForLedController extends BaseController {
    @Autowired
    public UserProjectForLedService service;

    /**
     * 查询该项目目前的进场人数
     *
     * @throws
     * @Title: getPrjectUserCount
     * @Description: TODO(查询该项目目前的进场人数)
     * @param: @param teamLeaderName
     * @param: @return
     * @return: String
     * @author:wangshibo
     */
    @PostMapping(value = "/getPrjectUserCount")
    @ResponseBody
    public JSONObject getPrjectUserCount(@RequestBody JSONObject json) {
        return service.getPrjectUserCount(json);
    }

    /**
     * 根据身份证号，上传单个人员信息
     *
     * @throws
     * @Title: getUserInfoByIdToLed
     * @Description: TODO(根据身份证号 ， 上传单个人员信息)
     * @param: @param json
     * @param: @return
     * @return: JSONObject
     * @author:wangshibo
     */
    @PostMapping(value = "/getUserInfoByIdToLed")
    @ResponseBody
    public JSONObject getUserInfoByIdToLed(@RequestBody JSONObject json) {
        return service.getUserInfoByIdToLed(json);
    }

    /**
     * 根据项目名称，查询出该项目已进场的人员信息
     *
     * @throws
     * @Title: queryUserInfoByNameToLed
     * @Description: TODO(根据项目名称 ， 查询出该项目已进场的人员信息)
     * @param: @param json
     * @param: @return
     * @return: JSONObject
     * @author:wangshibo
     */
    @PostMapping(value = "/queryUserInfoByNameToLed")
    @ResponseBody
    public JSONArray queryUserInfoByNameToLed(@RequestBody JSONObject json) {
        return service.queryUserInfoByNameToLed(json);
    }

    /**
     * 批量发送人员信息
     *
     * @throws
     * @Title: queryUserInfoToLed
     * @Description: TODO(批量发送人员信息)
     * @param: @return
     * @return: JSONObject
     * @author:wangshibo
     */
    @PostMapping(value = "/queryUserInfoToLed")
    @ResponseBody
    public JSONArray queryUserInfoToLed() {
        return service.queryUserInfoToLed();
    }

    /**
     * 根据项目名称获取考勤统计
     *
     * @throws
     * @Title: getAttendanceByProjectName
     * @Description: TODO(根据项目名称获取考勤统计)
     * @param: @param json
     * @param: @return
     * @return: JSONObject
     * @author:wangshibo
     */
    @PostMapping(value = "getAttendanceByProjectNameToLed")
    @ResponseBody
    public JSONObject getAttendanceByProjectNameToLed(@RequestBody JSONObject json) {
        return service.getAttendanceByProjectNameToLed(json);
    }


    /**
     * 获取某项目的统计数据
     *
     * @throws @author:wangshibo
     * @Title: getWorkerInfo
     */
    @PostMapping(value = "/getProjectPeople")
    @ResponseBody
    public JSONObject getProjectPeople(@RequestBody JSONObject json) {
        return service.getProjectPeopleToLed(json);
    }
    /**
     * 伪登录
     *
     * @throws
     * @Title: login
     * @Description: TODO(伪登录)
     * @param: @return
     * @return: JSONObject
     * @author:wangshibo
     */
    @PostMapping(value = "login")
    @ResponseBody
    public JSONObject logon() {
        return service.login();
    }

}
