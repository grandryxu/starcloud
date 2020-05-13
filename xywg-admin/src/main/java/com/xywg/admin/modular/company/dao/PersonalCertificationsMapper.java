package com.xywg.admin.modular.company.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.company.model.PersonalCertifications;
import com.xywg.admin.modular.company.model.PersonalCertificationsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-29
 */
public interface PersonalCertificationsMapper extends BaseMapper<PersonalCertifications> {

    /**
     * 根据id查找
     * @param id
     * @return
     * @author yuanyang
     */
    PersonalCertificationsVo getById(@Param("id") Integer id);

    /**
     * 列表查询
     * @param page
     * @param map
     * @return
     * @author yuanyang
     */
    List<Map<String,Object>> selectPersonalCertifications(@Param("page")Page<PersonalCertificationsVo> page, @Param("map")Map<String, Object> map);

    /**
     * 根据证件号和证件类型查询
     * @param idCardNumber
     * @param idCardType
     * @return
     * @author yuanyang
     */
    List<PersonalCertificationsVo> getByIdCard(@Param("idCardNumber")String idCardNumber, @Param("idCardType")Integer idCardType);

    /**
     * 查看从业人员的资格证书列表
     * @param page
     * @param map
     * @return
     * @author yuanyang
     */
    List<Map<String,Object>> getListByIdCard(@Param("page")Page<PersonalCertificationsVo> page, @Param("map")Map<String, Object> map);

    /**
     * 修改证书状态(定时任务)
     */
    void updateStatus();

    boolean deleteByIds(@Param("map")Map<String, Object> map);

    /**
     * 获取工人资格证书
     * @param map
     * @return
     * @author yuanyang
     */
    List<PersonalCertifications> getWorkerCertifications(Map<String, Object> map);
}
