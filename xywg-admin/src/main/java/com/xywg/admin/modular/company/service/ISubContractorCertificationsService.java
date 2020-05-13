package com.xywg.admin.modular.company.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.company.model.SubContractorCertifications;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-27
 */
public interface ISubContractorCertificationsService extends IService<SubContractorCertifications> {
    List<Map<String,Object>> getSubContractorCertifications(Map<String,Object> map, Page page);

    List<Map<String,Object>> getSubContractorCertificationsList(Map<String,Object> map, Page page);

    void del(Long id);

    void deleteSubContractorCertifications(Map<String, Object> map);

    /**
     * 定时任务
     */
    void updateStatus();
}
