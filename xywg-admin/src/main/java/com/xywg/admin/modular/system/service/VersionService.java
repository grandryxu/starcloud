package com.xywg.admin.modular.system.service;

import com.xywg.admin.modular.system.model.Version;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * @author hjy
 * @date 2018/5/31
 */
@Service
public interface VersionService {


    /**
     * 根据不同的条件获取版本信息
     * @param version
     * @return
     */
    Version getVersionService(Version version);


    /**
     * 获取最新版本
     * @param phoneType
     * @param kind
     * @return
     */
    Version getLatestVersionInfo(@Param("phoneType") Integer  phoneType  , @Param("kind")Integer  kind );


}
