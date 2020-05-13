package com.xywg.admin.modular.company.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.company.model.EmployeeMaster;
import com.xywg.admin.modular.company.model.EmployeeMasterVo;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 从业人员基础信息 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-28
 */
public interface IEmployeeMasterService extends IService<EmployeeMaster> {
    /**
     * 新增
     * @param entity
     * @return
     * @author yuanyang
     */
    @Transactional(rollbackFor = Exception.class)
    boolean addEmployee(EmployeeMasterVo entity);

    /**
     * 导入新增
     * @param entity
     * @param i
     * @return
     * @author yuanyang
     */
    @Transactional(rollbackFor = Exception.class)
    boolean importEmployee(EmployeeMasterVo entity,Integer i);
    /**
     * 编辑从业人员信息
     * @param employeeMasterVo
     * @return
     * @author yuanyang
     */
    @Transactional(rollbackFor = Exception.class)
    boolean editById(EmployeeMasterVo employeeMasterVo);

    /**
     * 删除
     * @param map
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    boolean deleteByIds(Map<String,Object> map);

    /**
     * 获取从业人员列表
     * @param map
     * @author yuanyang
     * @return
     */
    List<Map<String, Object>> selectEmployees(Page<EmployeeMaster> page, Map<String, Object> map);

    /**
     * 获取从业人员列表(不分页)
     * @param map
     * @author yuanyang
     * @return
     */
    List<Map<String, Object>> selectEmployeesList(Map<String, Object> map);
    /**
     * 根据id获取从业人员详情
     * @param companyEmployeId
     * @param organizationCode
     * @author yuanyang
     * @return
     */
    EmployeeMasterVo getById(Integer companyEmployeId,String organizationCode);

    /**
     * 根据证件类型和证件号查询从业人员基本信息
     * @param idCardNumber
     * @param idCardType
     * @author yuanyang
     * @return
     */
    EmployeeMaster getEmployeeByIdCard(String idCardNumber,Integer idCardType);

    /**
     * 根据证件类型和证件号查询工人基本信息(返回类型List<Map<String, Object>>)
     * @param idCardNumber
     * @param idCardType
     * @author yuanyang
     * @return
     */
    List<Map<String, Object>> searchEmployee(String idCardNumber,Integer idCardType);

    /**
     * 获取从业人员姓名(下拉框)
     * @return
     * @yuanyang
     */
    List<Map<String, Object>> getEmployees();

    /**
     * 根据手机号查询从业人员信息
     * @param cellPhone
     * @return
     * @author yuanyang
     */
    EmployeeMaster getByCellPhone(String cellPhone);


    List<Map<String,Object>> getListBySubContractor(Page<EmployeeMaster> page, Map<String, Object> map);

    /**
     * 导入
     * @param excelFile
     * @param request
     * @param response
     * @return
     */
//    String excelUpload(MultipartFile excelFile, HttpServletRequest request, HttpServletResponse response) throws Exception;

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
     * 导出
     * @param res
     * @param req
     * @param params
     * @author yuanyang
     */
    void export(HttpServletResponse res, HttpServletRequest req, Map<String, Object> params);

    /**
     * 查询在职的从业人员列表
     * @param page
     * @param map
     * @author yuanyang
     * @return
     */
    List<Map<String,Object>> selectInJobEmployees(Page<EmployeeMaster> page, Map<String, Object> map);

    /**
     * 导入
     * @param excelFile
     * @param request
     * @param response
     * @return
     */
    String Import (MultipartFile excelFile, HttpServletRequest request) throws Exception;

    void addEmployeeMaster(List<Object> addList);
}
