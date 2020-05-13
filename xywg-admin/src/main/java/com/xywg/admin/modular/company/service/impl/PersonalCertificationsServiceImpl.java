package com.xywg.admin.modular.company.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.company.model.PersonalCertifications;
import com.xywg.admin.modular.company.dao.PersonalCertificationsMapper;
import com.xywg.admin.modular.company.model.PersonalCertificationsVo;
import com.xywg.admin.modular.company.service.IPersonalCertificationsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-29
 */
@Service
public class PersonalCertificationsServiceImpl extends ServiceImpl<PersonalCertificationsMapper, PersonalCertifications> implements IPersonalCertificationsService {

    @Override
    public PersonalCertificationsVo getById(Integer id) {
        return this.baseMapper.getById(id);
    }

    @Override
    public List<Map<String, Object>> selectPersonalCertifications(Page<PersonalCertificationsVo> page, Map<String, Object> map) {
        return this.baseMapper.selectPersonalCertifications(page,map);
    }

    @Override
    public List<PersonalCertificationsVo> getByIdCard(String idCardNumber, Integer idCardType) {
        return this.baseMapper.getByIdCard(idCardNumber,idCardType);
    }

    @Override
    public List<Map<String, Object>> getListByIdCard(Page<PersonalCertificationsVo> page, Map<String, Object> map) {
        return this.baseMapper.getListByIdCard(page,map);
    }

    @Override
    public void updateStatus() {
         this.baseMapper.updateStatus();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByIds(Map<String, Object> map) {
        return this.baseMapper.deleteByIds(map);
    }

    @Override
    public List<PersonalCertifications> getWorkerCertifications(Map<String, Object> map) {
        return this.baseMapper.getWorkerCertifications(map);
    }
}
