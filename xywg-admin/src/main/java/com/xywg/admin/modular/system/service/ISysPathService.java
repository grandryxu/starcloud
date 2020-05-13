package com.xywg.admin.modular.system.service;

import com.xywg.admin.modular.system.model.SysPath;

import java.util.List;

/**
 * * @Package com.xywg.admin.modular.system.service
 * * @Description: App请求路径配置类
 * * @author caiwei
 * * @date 2018/10/30
 **/
public interface ISysPathService {

    /**
     * 获取所有的app请求地址
     * @return
     */
    List<SysPath> selectAllSysPaths();
}
