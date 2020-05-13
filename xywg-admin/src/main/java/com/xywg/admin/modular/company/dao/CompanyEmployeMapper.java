package com.xywg.admin.modular.company.dao;

import com.xywg.admin.modular.company.model.CompanyEmploye;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.modular.company.model.ContractorWorker;
import com.xywg.admin.modular.company.model.EmployeeMasterVo;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>
 * 企业从业人员聘用关系表 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-24
 */
public interface CompanyEmployeMapper extends BaseMapper<CompanyEmploye> {

    /**
     * 根据人员id修改
     * @param companyEmploye
     * @return
     * @author yuanyang
     */
    boolean edit(@Param("t") CompanyEmploye companyEmploye);

    /**
     * 根据从业人员id删除关联数据(批量)
     * @param id
     * @returns
     * @author yuanyang
     */
    boolean deleteByEmployeeIds(@Param("map") Map<String,Object> map);

    /**
     * 根据从业人员id删除关联数据
     * @param id
     * @return
     * @author yuanyang
     */
    boolean deleteByEmployeeId( Long id);

    /**
     * 根据身份证查询是否存在
     * @param idCardNumber
     * @param idCardType
     * @return
     */
    CompanyEmploye getByIdCard(@Param("idCardNumber") String idCardNumber, @Param("idCardType") Integer idCardType, @Param("organizationCode") String organizationCode);

    /**
     * 更改删除状态
     * @param employeSysNo
     */
    void updateIsDel(Integer employeSysNo);

    /**
     *根据系统编号查询入职状态
     * @param employeSysNo
     * @return
     */
    CompanyEmploye getJobStatusBySysNo(@Param("employeSysNo")Integer employeSysNo,@Param("organizationCode")String organizationCode);

    Map<String, Long> selectIdByOrgCodeAndEmploySysNo(@Param("organizationCode") String organizationCode, @Param("employeSysNo") Integer employeSysNo);
}
