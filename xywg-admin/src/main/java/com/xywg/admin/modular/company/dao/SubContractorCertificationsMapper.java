package com.xywg.admin.modular.company.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.company.model.SubContractorCertifications;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-27
 */
public interface SubContractorCertificationsMapper extends BaseMapper<SubContractorCertifications> {

    List<Map<String,Object>> getSubContractorCertifications(@Param("map") Map<String,Object> map, @Param("page")Page page);

    List<Map<String,Object>> getSubContractorCertificationsList(@Param("map") Map<String,Object> map, @Param("page")Page page);

    void del(@Param("id") Long id);

    void deleteSubContractorCertifications(@Param("map")Map<String, Object> map);

    void updateStatus();
}
