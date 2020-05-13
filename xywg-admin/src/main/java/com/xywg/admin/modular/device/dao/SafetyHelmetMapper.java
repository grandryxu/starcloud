package com.xywg.admin.modular.device.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.device.model.SafetyHelmet;
import com.xywg.admin.modular.recruitModular.model.RecruitModularParams;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 安全帽管理 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-29
 */
public interface SafetyHelmetMapper extends BaseMapper<SafetyHelmet> {

    /**
     * 获取安全帽列表
     * @param page
     * @param map
     * @return
     */
    List<Map<String,Object>> list(@Param("page") Page page , @Param("map") Map map);

    /**
     * 根据序列号获取安全帽
     * @param safetyHelmet
     * @return
     */
    SafetyHelmet getSafetyHelmetByImei(SafetyHelmet safetyHelmet);

    /**
     * 根据序列号获取安全帽
     * @param safetyHelmet
     * @return
     */
    @Override
    Integer insert(SafetyHelmet safetyHelmet);

    /**
     * 批量删除安全帽
     * @param ids
     * @return
     */
    Integer deleteSafetyHelmets(String ids);

    int updateSafety(@Param("s")SafetyHelmet safetyHelmet);
    List<SafetyHelmet> checkBySn(@Param("s")SafetyHelmet safetyHelmet);

    /**
     * 获取项目下未使用的安全帽
     * @param projectCode
     * @return List<SafetyHelmet>
     * @author 蔡伟
     */
    List<SafetyHelmet> getUnusedSafetyHelmet(String projectCode);

	void updateSafetyHelmet(@Param("p") RecruitModularParams params);

	void updateSafetyHelmetRelation(@Param("i")String imei);

    /**
     * 获取项目下的所有可用安全帽
     * @param map
     * @return
     */
    List<SafetyHelmet> getUnusedHelmetsByProjectCode(Map<String, Object> map);
}
