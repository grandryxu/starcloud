package com.xywg.admin.modular.wages.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.wages.model.*;
import com.xywg.admin.modular.wages.model.dto.PayRollDetailDto;
import com.xywg.admin.modular.wages.model.dto.PayRollDto;
import com.xywg.admin.modular.wages.model.dto.PayrollSettlementDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 工资明细 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-01
 */
public interface PayRollDetailMapper extends BaseMapper<PayRollDetail> {

    /**
     * 根据用户idcard number 项目 班组 获取用户考勤及其基本信息
     */
    List<PayRollDetailVo> getSalaryDetailByWorkerInfo(ParamsToFindSalaryDetail paramsToFindSalaryDetail);

    List<PayRollDetailVo> list(@Param("page") Page page , @Param("paramsToFindSalaryDetail") ParamsToFindSalaryDetail paramsToFindSalaryDetail);

    /**
     * 新增工资单详情
     * @param payRollDetailList
     * @param id
     * @param createUser
     * @return
     */
    Integer insertDetailList(@Param("payRollDetailList") List<Map<String, Object>> payRollDetailList, @Param("id") Long id, @Param("createUser")String createUser);

    /**
     * 获取工资单详情列表
     * @param page
     * @param map
     * @return
     */
    List<PayRollDetailVo> getDetailList(@Param("page") Page<PayRollDetailVo> page,@Param("map") Map<String, Object> map);

    /**
     * 更新审核状态
     * @param ids
     * @param status
     * @param userId
     * @return
     */
    Integer toggleStatus(@Param("ids") String ids, @Param("status") Integer status, @Param("userId") Long userId);

    /**
     * 发放工资单的时候更新详情状态
     *
     * @param id
     * @param name
     * @return
     */
    Integer grantPayRoll(@Param("id") Long id,@Param("name") String name);

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
    List<Map<String,Object>> getPayRollFlowDetailListByProjectCodeAndTeamSysNo(@Param("projectCode") String projectCode,@Param("id") Long id);

    /**
     * 获取指定工人在该班组下的工资，结算流水
     * @param teamSysNo
     * @param idCardType
     * @param idCardNumber
     * @return
     */
    List<PayRollDetailDto> getPayRollFlowDetailListByWorkerAndTeamSysNo(@Param("teamSysNo")String teamSysNo,@Param("idCardType") String idCardType, @Param("idCardNumber")String idCardNumber);



    /**
     * 根据项目编号和工人信息获取工资单详细
     * @param projectCode
     * @param idCardType
     * @param idCardNumber
     * @return
     */
    List<PayrollSettlementDetail> getPayrollDetailListByWorkerAndProjectCode(
            @Param("projectCode")String projectCode,@Param("idCardType") String idCardType,@Param("idCardNumber") String idCardNumber);




    /**
     *
     * @param detailList
     * @param id
     * @param createUser
     * @return
     */
    Integer addDetailList(@Param("detailList") List<PayRollDetailDto> detailList,@Param("id") Long id, @Param("createUser") String createUser);

    /**
     * 获取最新工资单
     * @param idCardNumber
     * @param idCardType
     * @return
     */
    List<Map<String,Object>> getLastPayRoll(@Param("idCardNumber") String idCardNumber,@Param("idCardType") Integer idCardType);

    /**
     * 根据 工人证件编号和证件类型获取工资详细中待发工资的id集合
     * @param settlementDetail
     * @return
     */
    List<Long> getPayRollDetailId(SettlementDetail settlementDetail);

    /**
     * 更新工资详细数据状态为数据锁定
     * @param set
     * @param updateUser
     */
    void updatePayStatus(@Param("dataSet")Set<Long> set,@Param("updateUser")String updateUser);

    /**
     * 根据id批量解除工资详细数据的锁定状态
     * @param payStatus  状态
     * @param ids   id集合
     */
    void updateBatchPayStatus(@Param("payStatus")Integer payStatus,@Param("ids")List<String> ids,@Param("updateUser")String updateUser);

    /**
     * 更新工资单状态
     * @param id
     * @return Integer
     */
    Integer updateStatusAfterReview(Long id);

    /**
     * 查询工资单详情对应的工资单是否全部签字
     */
    PayRollDto isAllSign(Integer id);

    /**
     * 获取项目下的流水 某人的
     * @param projectCode
     * @param idCardType
     * @param idCardNumber
     * @return
     */
    List<PayRollFlow> getPayrollAndSettlementDetailListByWorkerAndProjectCode(@Param("projectCode") String projectCode, @Param("idCardType") String idCardType,@Param("idCardNumber") String idCardNumber);

    /**
     * 获取项目下的流水 某人的
     * @param projectCode
     * @param idCardType
     * @param idCardNumber
     * @return
     */
    List<PayRollFlow> v116GetPayrollAndSettlementDetailListByWorkerAndProjectCode(@Param("projectCode") String projectCode, @Param("idCardType") String idCardType,@Param("idCardNumber") String idCardNumber,@Param("index") Integer index,@Param("pageSize") Integer pageSize);


    /**
     * 批量更新中的 更新单个
     * @param map
     * @return
     */
    Integer updateSingleMap(Map<String, Object> map);

    /**
     * 导出查询
     * @param params
     * @return
     * @author yuanyang
     */
    List<Map<String,Object>> getDetailExportList(@Param("map")Map<String, Object> params);

    /**
     * 结算单发放的时候更新工资单详情的pay_status为20
     * @param id
     * @return
     */
    Integer updatePayStatusToTwenty(Long id);

    /**
     * 根据用户idcard number 项目 班组 获取用户考勤及其基本信息 无分页
     * @param paramsToFindSalaryDetail
     * @return
     */
    List<PayRollDetailVo> getSalaryDetailByWorkerInfoNoPages(@Param("paramsToFindSalaryDetail") ParamsToFindSalaryDetail paramsToFindSalaryDetail);

	List<Map<String, Object>> getPayRollFlowDetailListByProjectCodeAndTeamSysNoV116(@Param("projectCode") String projectCode,@Param("id") Long id, 
			@Param("pn") int pageNo,
			@Param("ps") int pageSize);

    /**
     * 根据工资单详情id获取工资单详情
     * @param id
     * @return
     */
    List<Map<String,Object>> v116GetPayRollDetailById(@Param("id") Long id,@Param("index") Integer index,@Param("pageSize") Integer pageSize);
}
