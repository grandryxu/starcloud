package com.xywg.admin.modular.company.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.company.model.EmployeeMaster;
import com.xywg.admin.modular.company.model.EmployeeMasterVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 从业人员基础信息 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-24
 */
public interface EmployeeMasterMapper extends BaseMapper<EmployeeMaster> {
    /**
     * 获取从业人员基本信息列表
     *
     * @param map
     * @return
     * @author yuanyang
     */
    List<Map<String, Object>> selectEmployees(@Param("page") Page<EmployeeMaster> page, @Param("map") Map<String, Object> map);

    /**
     * 获取从业人员基本信息列表(不分页)
     *
     * @param map
     * @return
     * @author yuanyang
     */
    List<Map<String, Object>> selectEmployeesList(@Param("map")Map<String, Object> map);


    /**
     * 根据证件类型和证件号查询从业人员基本信息
     * @param idCardNumber
     * @param idCardType
     * @return
     */
    EmployeeMaster getEmployeeByIdCard(@Param("idCardNumber")String idCardNumber,@Param("idCardType")Integer idCardType);

    /**
     * 根据证件类型和证件号查询从业人员基本信息
     * @param idCardNumber
     * @param idCardType
     * @return
     */
    List<Map<String, Object>> searchEmployee(@Param("idCardNumber")String idCardNumber,@Param("idCardType")Integer idCardType);
    /**
     * 根据id获取从业人员详细信息
     * @param companyEmployeId
     * @return
     */
    EmployeeMasterVo getById(@Param("companyEmployeId") Integer companyEmployeId,@Param("organizationCode")String organizationCode);
    /**
     * 获取从业人员姓名(下拉框)
     * @return
     * @author yuanyang
     */
    List<Map<String,Object>> getEmployees();

    /**
     * 新增从业人员
     * @param employeeMaster
     * @return
     * @author
     */
    Integer addEmployee(EmployeeMaster employeeMaster);

    /**
     * 根据手机号查询从业人员基本信息
     * @param cellPhone
     * @return
     * @author yuanyang
     */
    EmployeeMaster getByCellPhone(@Param("cellPhone") String cellPhone);

    /**
     * 获取某公司下的从业人员
     * @param page
     * @param map
     * @return
     */
    List<Map<String,Object>> getListBySubContractor(@Param("page") Page<EmployeeMaster> page,@Param("map") Map<String, Object> map);

    /**
     * 修改从业人员基本信息
     * @param employeeMaster
     */
    void updateEmployee(EmployeeMaster employeeMaster);
    /**
     * 入职
     * @param employeeMaster
     * @author yuanyang
     * @return
     */
    boolean hire(EmployeeMaster employeeMaster);
    /**
     * 离职
     * @param employeeMaster
     * @author yuanyang
     * @return
     */
    boolean termination(EmployeeMaster employeeMaster);
    /**
     * 退休
     * @param employeeMaster
     * @author yuanyang
     * @return
     */
    boolean retire(EmployeeMaster employeeMaster);

    /**
     * 获取在职的从月人员列表
     * @param page
     * @param map
     * @author yuanyang
     * @return
     */
    List<Map<String,Object>> selectInJobEmployees(@Param("page") Page<EmployeeMaster> page, @Param("map") Map<String, Object> map);

    Map<String, Long> selectEmployeeByIdCard(@Param("idCardNumber") String idCardNumber);
}
