package com.xywg.admin.modular.wages.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.wages.model.PayRoll;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.wages.model.PayRollDetailVo;
import com.xywg.admin.modular.wages.model.dto.PayRollDetailDto;
import com.xywg.admin.modular.wages.model.dto.PayRollDto;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 工资单 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-01
 */
public interface IPayRollService extends IService<PayRoll> {

    /**
     * 获取工资单列表
     * @param page
     * @param map
     * @return
     */
    List<PayRollDetailVo> selectList(Page<PayRollDetailVo> page, Map<String, Object> map);

    /**
     * 根据工资单id获取工资单详情列表
     *
     * @param page
     * @param map
     * @return
     */
    List<PayRollDetailVo> selectDetailByPayRollCode(Page<PayRollDetailVo> page, Map<String, Object> map);

    /**
     * 批量删除工资单
     * @param ids
     * @return
     */
    Integer deleteIds(String ids);

    /**
     * 批量提交工资单
     * @param ids
     * @return
     */
    Integer submit(String ids);

    /**
     * 批量发放
     * @param ids
     * @return
     */
    Integer grantPayRoll(String ids);

    /**
     * 根据项目，工资单类型获取工资单列表
     * @param projectCode
     * @param teamSysNo
     * @param type
     * @return
     */
    List<Map<String,Object>> getPayRollListByProjectCodeAndType(Integer id,String projectCode, String teamSysNo, Integer type);

    /**
     * 根据项目，工资单类型获取工资单列表
     * @param projectCode
     * @param teamSysNo
     * @param type
     * @return
     */
    List<Map<String,Object>> v116GetPayRollListByProjectCodeAndType(Integer id,String projectCode, String teamSysNo, Integer type,Integer pageNo , Integer pageSize);

    /**
     * 保存工资单及其详情
     * @param payRollDto
     * @return
     */
    Boolean savePayRollAndDetail(PayRollDto payRollDto);

    /**
     * 审核工资单
     * @param payRollDto
     * @return
     */
    Integer examineSalary(PayRollDto payRollDto);

    /**
     * 获取待我审批的工资单列表
     * @param organizationCode
     * @return
     */
    List<PayRollDetailDto> getPayRollByOrganizationCode(String organizationCode);

    /**
     * 获取待我审批的工资单列表
     * @param organizationCode
     * @return
     */
    List<PayRollDetailDto> v116GetPayRollByOrganizationCode(String organizationCode,Integer pageNo , Integer pageSize);

    /**
     * 根据payRollCode获取工资单
     * @param payRollCode
     * @return
     */
    PayRoll selectByPayRollCode(String payRollCode);

    /**
     * App工资发发
     * @param payRollDto
     * @return
     */
    Integer grantPayRollApp(PayRollDto payRollDto);

    /**
     * 删除工资详情并解锁计工单
     * @param ids
     */
    Integer deleteByIdAndUpdateAccount(String ids);

    List<PayRollDetailVo> selectDetailByPayRollCodeNoPage(Map<String, Object> map);
}
