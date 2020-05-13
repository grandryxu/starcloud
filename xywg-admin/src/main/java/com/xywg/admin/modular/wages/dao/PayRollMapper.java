package com.xywg.admin.modular.wages.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.bad.dto.BadRecordsResultVO;
import com.xywg.admin.modular.system.model.SwitchType;
import com.xywg.admin.modular.wages.model.PayRoll;
import com.xywg.admin.modular.wages.model.PayRollDetailVo;
import com.xywg.admin.modular.wages.model.PayRollFlow;
import com.xywg.admin.modular.wages.model.dto.PayRollDetailDto;
import com.xywg.admin.modular.wages.model.dto.PayRollDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 工资单 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-01
 */
public interface PayRollMapper extends BaseMapper<PayRoll> {

    @Override
    Integer insert(PayRoll payRoll);

    /**
     * 获取工资单列表
     * @param page
     * @param map
     * @param switchType
     * @return
     */
    List<PayRollDetailVo> list(@Param("page") Page<PayRollDetailVo> page, @Param("map") Map<String, Object> map, @Param("switchType") SwitchType switchType);

    /**
     * 根据工资单id获取工资单详情
     *
     * @param page
     * @param map
     * @return
     */
    List<PayRollDetailVo> selectDetailByPayRollCode(@Param("page") Page<PayRollDetailVo> page, @Param("map") Map<String, Object> map);

    /**
     * 批量删除ids
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
     * 批量工资发放
     * @param id
     * @return
     */
    Integer grantPayRoll(Long id);

    /**
     * 根据项目，工资单类型获取工资单列表
     * @param projectCode
     * @param teamSysNo
     * @param type
     * @return
     */
    List<Map<String,Object>> getPayRollListByProjectCodeAndType(@Param("id") Integer id,@Param("projectCode") String projectCode ,@Param("teamSysNo") String teamSysNo, @Param("type") Integer type);

    /**
     * 根据项目，工资单类型获取工资单列表
     * @param projectCode
     * @param teamSysNo
     * @param type
     * @return
     */
    List<Map<String,Object>> v116GetPayRollListByProjectCodeAndType(@Param("id") Integer id,@Param("projectCode") String projectCode ,@Param("teamSysNo") String teamSysNo, @Param("type") Integer type,@Param("index") Integer index,@Param("pageSize") Integer pageSize);

    /**
     * 新增
     * @param payRollDto
     * @param createUser
     * @return
     */
    Integer addPayRollDto(@Param("payRollDto") PayRollDto payRollDto,@Param("createUser") String createUser);

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
    List<PayRollDetailDto> v116GetPayRollByOrganizationCode(@Param("organizationCode") String organizationCode,@Param("index") Integer index,@Param("pageSize") Integer pageSize);
    
    /**
     * 获取待审核的工资单个数
     * @param organizationCode
     * @return
     */
    BadRecordsResultVO getToAuditPayRollCount(String organizationCode);

    /**
     * 根据工资单id查询流水详情
     * @param id
     * @return
     */
    List<PayRollFlow> getPayRollById(Long id);

    /**
     * 根据payRollCode获取工资单
     * @param payRollCode
     * @return
     */
    PayRoll selectByPayRollCode(String payRollCode);

    /**
     * 工资单发放的时候更新付款人
     * @param payRollDto
     * @return
     */
    Integer updateDetailsPayPerson(PayRollDto payRollDto);

    List<PayRollDetailVo> selectDetailByPayRollCodeNoPage(@Param("map") Map<String, Object> map);
}
