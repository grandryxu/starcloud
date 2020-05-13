package com.xywg.admin.modular.system.service.impl;

import com.xywg.admin.modular.system.dao.SysPathMapper;
import com.xywg.admin.modular.system.model.SysPath;
import com.xywg.admin.modular.system.service.ISysPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * * @Package com.xywg.admin.modular.system.service.impl
 * * @Description: app请求地址实现类
 * * @author caiwei
 * * @date 2018/10/30
 **/
@Service
public class SysPathServiceImpl implements ISysPathService{

    @Autowired
    private SysPathMapper sysPathMapper;

    @Override
    public List<SysPath> selectAllSysPaths() {
        return this.sysPathMapper.selectAllSysPaths();
    }
}
