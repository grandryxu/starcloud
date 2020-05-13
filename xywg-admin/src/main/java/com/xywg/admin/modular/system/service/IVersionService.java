package com.xywg.admin.modular.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.system.model.Version;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.system.model.VersionVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 版本管理 服务类
 * </p>
 *
 * @author cw123
 * @since 2018-08-21
 */
public interface IVersionService extends IService<Version> {

    /**
     * 获取所有的版本
     * @param page
     * @param map
     * @return
     * @author 蔡伟
     */
    List<VersionVo> selectList(Page<VersionVo> page, Map<String, Object> map);

    /**
     * 根据手机类型，端，versionCode 获取版本信息 以及最新下载地址
     * @param phoneType
     * @param kind
     * @param version
     * @return
     */
    VersionVo getVersionByPhone(Integer phoneType, Integer kind, String version);
}
