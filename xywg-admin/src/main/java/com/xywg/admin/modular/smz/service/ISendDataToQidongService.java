package com.xywg.admin.modular.smz.service;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public interface ISendDataToQidongService {



    /**
     * 登录
     * @return
     */
    JSONObject login(Map<String, Object> map);

    /**
     * 获取项目
     * @return
     */
    Map<String, Object> project(Map<String, Object> map);


    /**
     * 获取企业项目的班组
     * @param map
     * @return
     */
    Map<String, Object> teamMaster(Map<String, Object> map);

    /**
     * 获取企业项目设备
     * @param map
     * @return
     */
    Map<String, Object> device(Map<String, Object> map);

    /**
     * 获取项目人员信息
     * @param map
     * @return
     */
    Map<String, Object> workerMaster(Map<String, Object> map);

    /**
     * 根据组织机构代码和id获取项目人员关系
     * @param map
     * @return
     */
    Map<String, Object> projectWorker(Map<String, Object> map);

    /**
     * 根据组织机构代码和id获取考勤记录
     * @param map
     * @return
     */
    Map<String, Object> deviceRecord(Map<String, Object> map);


    Map<String, Object> projectAddress(Map<String, Object> map);
}
