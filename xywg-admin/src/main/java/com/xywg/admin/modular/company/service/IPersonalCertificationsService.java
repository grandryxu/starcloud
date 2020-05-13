package com.xywg.admin.modular.company.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.company.model.PersonalCertifications;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.company.model.PersonalCertificationsVo;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-29
 */
public interface IPersonalCertificationsService extends IService<PersonalCertifications> {
    /**
     * 根据id查询
     * @param id
     * @return
     * @author yuanyang
     */
    PersonalCertificationsVo getById(Integer id);

    /**
     * 列表查询
     * @param page
     * @param map
     * @author yuanyang
     * @return
     */
    List<Map<String,Object>> selectPersonalCertifications(Page<PersonalCertificationsVo> page, Map<String, Object> map);

    /**
     * 根据证件编号和证件类型查询
     * @param idCardNumber
     * @param idCardType
     * @return
     * @author yuanyang
     */
    List<PersonalCertificationsVo> getByIdCard(String idCardNumber, Integer idCardType);

    /**
     * 查看从业人员的资格证书列表
     * @param page
     * @param map
     * @return
     * @author yuanyang
     */
    List<Map<String,Object>> getListByIdCard(Page<PersonalCertificationsVo> page, Map<String, Object> map);

    /**
     * 修改所有证书状态(定时任务)
     */
    void updateStatus();

    /**
     * 删除
     * @param map
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    boolean deleteByIds(Map<String,Object> map);

    /**
     * 获取工人资格证书
     * @param map
     * @return
     * @author yuanyang
     */
    List<PersonalCertifications> getWorkerCertifications(Map<String, Object> map);
}
