package com.xywg.admin.modular.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.modular.system.model.Version;
import com.xywg.admin.modular.system.dao.VersionMapper;
import com.xywg.admin.modular.system.model.VersionVo;
import com.xywg.admin.modular.system.service.IVersionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.modular.system.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 版本管理 服务实现类
 * </p>
 *
 * @author cw123
 * @since 2018-08-21
 */
@Service
public class VersionServiceImpl extends ServiceImpl<VersionMapper, Version> implements IVersionService,VersionService {

    @Autowired
    private VersionMapper versionMapper;

    @Override
    public Version getVersionService(Version version) {
        return versionMapper.getVersion(version);
    }

    /**
     * 获取最新版本
     *
     * @param phoneType
     * @param kind
     * @return
     */
    @Override
    public Version getLatestVersionInfo(Integer phoneType, Integer kind) {
        return versionMapper.getLatestVersionInfo(phoneType,kind);
    }

    @Override
    public List<VersionVo> selectList(Page<VersionVo> page, Map<String, Object> map) {
        return this.baseMapper.selectList(page ,map);
    }

    @Override
    public VersionVo getVersionByPhone(Integer phoneType, Integer kind, String version) {
        return this.baseMapper.getVersionByPhone(phoneType,kind,version);
    }

    @Override
    public boolean insert(Version version){
        Version hasVersion = this.baseMapper.hasVersion(version);
        if(hasVersion !=null){
            throw new XywgException(800,"该版本已存在，请更换版本");
        }
        return retBool(this.baseMapper.insert(version));
    };

}
