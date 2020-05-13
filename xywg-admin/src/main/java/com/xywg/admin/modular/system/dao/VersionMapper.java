package com.xywg.admin.modular.system.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.system.model.Version;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.modular.system.model.VersionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 版本管理 Mapper 接口
 * </p>
 *
 * @author cw123
 * @since 2018-08-21
 */
public interface VersionMapper extends BaseMapper<Version> {

    /**
     * 根据不同的条件获取版本信息
     * @param version
     * @return
     */
    Version getVersion(Version version);

    /**
     * 获取最新版本
     * @param phoneType
     * @param kind
     * @return
     */
    Version getLatestVersionInfo(@Param("phoneType") Integer  phoneType  ,@Param("kind")Integer  kind );

    /**
     * 获取所有的版本
     * @param page
     * @param map
     * @return
     * @author 蔡伟
     */
    List<VersionVo> selectList(@Param("page") Page<VersionVo> page, @Param("map") Map<String, Object> map);

    /**
     * 根据 app,app端 获取最新的版本
     * @param phoneType
     * @param kind
     * @return
     * @author 蔡伟
     */
    Version selectNewestVersion(@Param("phoneType") Integer phoneType,@Param("kind") Integer kind);

    /**
     * 根据手机类型，端，versionCode 获取版本信息 以及最新下载地址
     * @param phoneType
     * @param kind
     * @param version
     * @return
     * @author 蔡伟
     */
    VersionVo getVersionByPhone(@Param("phoneType") Integer phoneType,@Param("kind") Integer kind, @Param("version") String version);

    /**
     * 版本判重
     * @param version
     * @return
     * @author 蔡伟
     */
    Version hasVersion(Version version);
}
