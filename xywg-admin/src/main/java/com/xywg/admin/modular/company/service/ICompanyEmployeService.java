package com.xywg.admin.modular.company.service;


import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.company.model.CompanyEmploye;
import com.xywg.admin.modular.company.model.ContractorWorker;
import com.xywg.admin.modular.company.model.EmployeeMasterVo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 企业从业人员聘用关系表 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-28
 */
public interface ICompanyEmployeService extends IService<CompanyEmploye> {
    /**
     * 根据人员id修改
     * @param companyEmploye
     * @return
     * @author yuanyang
     */
    boolean edit(CompanyEmploye companyEmploye);

    /**
     * 根据从业人员id删除关联数据
     * @param id
     * @return
     */
    boolean deleteByEmployeeIds(Map<String,Object> map);

    /**
     * 根据身份证查询是否存在
     * @param idCardNumber
     * @param idCardType
     * @return
     */
    CompanyEmploye getByIdCard(String idCardNumber, Integer idCardType,String organizationCode);

    void addcompanyEmploye(List<Object> addList);
}
