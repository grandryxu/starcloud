package com.xywg.admin.modular.device.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.device.model.SafetyHelmet;
import com.xywg.admin.modular.recruitModular.model.RecruitModularParams;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 安全帽管理 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-29
 */
public interface ISafetyHelmetService extends IService<SafetyHelmet> {

    List<Map<String,Object>> selectList(Page page , Map map);

    /**
     * 批量删除安全帽
     * @param ids
     * @return
     */
    Integer deleteSafetyHelmets(String ids);
    int updateSafety(SafetyHelmet safetyHelmet);

    /**
     * 获取项目下未使用的安全帽
     * @param projectCode
     * @return List<SafetyHelmet>
     * @author 蔡伟
     */
    List<SafetyHelmet> getUnusedSafetyHelmet(String projectCode);

    /**
     * 
     * @description 添加安全帽（APP端）
     * @author chupp
     * @date 2018年9月10日
     * @param params
     *
     */
	void addSafetyHelmet(RecruitModularParams params);
	void updateSafetyHelmet(RecruitModularParams params);

	/**
	 * 
	 * @description 绑定安全帽（APP端）
	 * @author chupp
	 * @date 2018年9月10日
	 * @param params
	 *
	 */
	void bindSafetyHelmet(RecruitModularParams params);

	/**
	 * 获取项目下的是所有可用安全帽
	 * @param map
     * @return
	 */
    List<SafetyHelmet> getUnusedHelmetsByProjectCode(Map<String, Object> map);
}
