package com.xywg.admin.modular.wages.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.wages.model.*;
import com.xywg.admin.modular.wages.model.dto.PayRollDetailDto;
import com.xywg.admin.modular.wages.model.dto.PayRollDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 工资明细 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-01
 */
public interface IPayRollDetailService extends IService<PayRollDetail> {

    /**
     * 根据用户idcard number 项目 班组 获取用户考勤及其基本信息
     */
    List<PayRollDetailVo> getSalaryDetailByWorkerInfo(ParamsToFindSalaryDetail paramsToFindSalaryDetail);

    /**
     * 根据用户idcard number 项目 班组 获取用户考勤及其基本信息 分页
     */
    List<PayRollDetailVo> selectList(Page<PayRollDetailVo> page, ParamsToFindSalaryDetail paramsToFindSalaryDetail);

    /**
     * 保存工资单及其详情
     */
    Boolean addPayRollAndDetail(PayRollVo payRollVo);

    /**
     * 获取工资单详情列表
     * @param page
     * @param map
     * @return
     */
    List<PayRollDetailVo> getDetailList(Page<PayRollDetailVo> page, Map<String, Object> map);

    /**
     * 更新审核状态
     * @param ids
     * @param status
     * @return
     */
    Integer toggleStatus(String ids, Integer status);

    /**
     * 发放工资单的时候更新详情状态
     * @param id
     * @param name
     * @return
     */
    Integer grantPayRoll(Long id, String name);

    /**
     * 根据工资单详情id获取工资单详情
     * @param id
     * @return
     */
    List<Map<String,Object>> getPayRollDetailById(Long id);

    /**
     * 工资单签字
     * @param payRollDetailDto
     * @return
     */
    Integer payRollDetailSign(PayRollDetailDto payRollDetailDto);

    /**
     * 获取工人在某班组的工资剩余列表
     * @param
     * @param payRollDto
     * @return
     */
    List<PayRollDetailDto> getPayRollDetailListByWorkerListAndTeamSysNo(PayRollDto payRollDto);

    /**
     * 获取工人在某班组的工资剩余列表
     * @param
     * @param payRollDto
     * @return
     */
    List<PayRollDetailDto> v116GetPayRollDetailListByWorkerListAndTeamSysNo(PayRollDto payRollDto);

    /**
     * 根据项目获取班组长手下工人列表
     * @param projectCode
     * @param id
     * @return
     */
    List<Map<String,Object>> getPayRollFlowDetailListByProjectCodeAndTeamSysNo(String projectCode, Long id);

    /**
     * 获取指定工人在该班组下的工资，结算流水
     * @param teamSysNo
     * @param idCardType
     * @param idCardNumber
     * @return
     */
    List<PayRollDetailDto> getPayRollFlowDetailListByWorkerAndTeamSysNo(String teamSysNo, String idCardType, String idCardNumber);



    /**
     * 根据项目编号和工人信息获取工资和结算单详细
     * @param projectCode
     * @param idCardType
     * @param idCardNumber
     * @return
     */
    List<PayRollFlow> getPayrollAndSettlementDetailListByWorkerAndProjectCode(String projectCode, String idCardType, String idCardNumber);


    /**
     * 根据项目编号和工人信息获取工资和结算单详细
     * @param projectCode
     * @param idCardType
     * @param idCardNumber
     * @return
     */
    List<PayRollFlow> v116GetPayrollAndSettlementDetailListByWorkerAndProjectCode(String projectCode, String idCardType, String idCardNumber,Integer pageNo , Integer pageSize);

    List<Map<String,Object>> getLastPayRoll(String idCardNumber,Integer idCardType);

    /**
     * 批量更新工资单
     * @param payRollDetailList
     * @return
     */
    Integer updates(List<Map<String, Object>> payRollDetailList);

    /**
     * 导出
     * @param res
     * @param req
     * @param params
     * @yuanyang
     */
    void export(HttpServletResponse res, HttpServletRequest req, Map<String, Object> params);

    /**
     * 导出列表查询
     * @param params
     * @return
     * @author yuanyang
     */
    List<Map<String,Object>> getDetailExportList(Map<String, Object> params);

    /**
     * 根据工种导入
     * @param excelFile
     * @param request
     * @return
     */
    Object importByWorkerType(MultipartFile excelFile, HttpServletRequest request) throws  Exception;

    /**
     * 根据工种导入
     * @param excelFile
     * @param request
     * @return
     */
    Object importByOrder(MultipartFile excelFile, HttpServletRequest request) throws  Exception;

    /**
     * 根据用户idcard number 项目 班组 获取用户考勤及其基本信息 无分页
     */
    List<PayRollDetailVo> getSalaryDetailByWorkerInfoNoPages(ParamsToFindSalaryDetail paramsToFindSalaryDetail);

    List<Map<String,Object>> getPayRollFlowDetailListByProjectCodeAndTeamSysNoV116(String projectCode, Long id, Integer pageNo,
			Integer pageSize);

    /**
     * 根据工资单详情id获取工资单详情
     * @param id
     * @return
     */
    List<Map<String,Object>> v116GetPayRollDetailById(Long id,Integer pageNo , Integer pageSize);
}
