package com.xywg.admin.modular.system.dao;

import com.xywg.admin.modular.system.model.SysPath;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * * @Package com.xywg.admin.modular.system.dao
 * * @Description: app请求地址mapper
 * * @author caiwei
 * * @date 2018/10/30
 **/
@Mapper
public interface SysPathMapper {

    /**
     * 获取所有的app请求地址
     * @return
     */
    List<SysPath> selectAllSysPaths();
}
