package com.xywg.admin.modular.wages.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.bad.dto.BadRecordsResultVO;
import com.xywg.admin.modular.system.model.Dict;
import com.xywg.admin.modular.system.model.SwitchType;
import com.xywg.admin.modular.wages.model.Settlement;
import com.xywg.admin.modular.wages.model.SettlementDetail;
import com.xywg.admin.modular.wages.model.dto.PayrollSettlementDetail;
import com.xywg.admin.modular.wages.model.dto.SettlementDetailDto;
import com.xywg.admin.modular.wages.model.dto.SettlementDto;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface SettlementMapper extends BaseMapper<Settlement> {

    Settlement selectById(Long id);

    List<Map<String,Object>> getList(@Param("page") Page<Map<String, Object>> page, @Param("map") Map map, @Param("switchType") SwitchType switchType);

    void  deleteBatch(List<Settlement> list);

    void  deleteBatchDetail(List<Settlement> list);

    List<Dict> getWorkerType(@Param("workerTypeName") String workerTypeName);

    List<SettlementDetail> getSettlementDetail(@Param("page") Page<SettlementDetail> page,@Param("map")Map<String, Object> map);

    /**
     * 根据项目获取结算单列表
     * @param projectCode ,teamSysNo
     * @param id
     * @return
     */
    List<SettlementDto> getSettlementListByProjectCodeAndTeamSysNo(@Param("projectCode") String projectCode, @Param("id") Integer id);

    /**
     * 根据项目获取结算单列表
     * @param projectCode ,teamSysNo
     * @param id
     * @return
     */
    List<SettlementDto> v116GetSettlementListByProjectCodeAndSysTeamNo(@Param("projectCode") String projectCode, @Param("id") Integer id,@Param("index") Integer index,@Param("pageSize") Integer pageSize);
    List<SettlementDto> v116GetSettlementListByProjectCodeAndSysTeamNo2(@Param("projectCode") String projectCode, @Param("id") Integer id,@Param("index") Integer index,@Param("pageSize") Integer pageSize);

    /**
     * 批量发放工资
     * @param list
     */
    void payrollBatch(List<Settlement> list);

    /*
     *结算单审核
     */
    void checkInfo(@Param("list")List<Settlement> list,@Param("userName") String userName);


    /*
     *复核
     */
    void review(@Param("list")List<Settlement> list,@Param("userName") String userName);


    /**
     * 根据项目获取结算单列表
     * @param id
     * @return
     */
    List<Map<String,Object>> getSettlementDetailById(Long id);
    List<Map<String,Object>> getSettlementDetailById2(@Param("id")Long id,@Param("uid")Long uid);

    /**
     * 结算单签字
     * @param settlementDetailDto
     * @return
     */
    Integer settlementDetailDtoSign(SettlementDetailDto settlementDetailDto);

    /**
     * 批量新增结算单详情
     * @param detailList
     * @param settlementCode
     * @return
     */
    Integer insertSettlementDetailDto(@Param("detailList") List<SettlementDetailDto> detailList,@Param("settlementCode") String settlementCode);
    Integer insertSettlementDetailDto2(@Param("detailList") List<SettlementDetailDto> detailList,@Param("settlementCode") String settlementCode,@Param("tsn")Integer teamSysNo);

    /**
     * 新增结算单
     */
    @Override
    Integer insert(Settlement settlement);


    /**
     * 根据结算单code查询 结算单详细列表
     * @param map
     * @return
     */
    List<SettlementDetail>  getSettlementDetailBySettlementCode(Map<String,Object> map);

    /**
     * 保存结算单
     * @param settlement
     */
    void saveSettlement(Settlement settlement);
    void  updateSettlement(Settlement settlement);

    /**
     * 批量保存结算单明细数据
     * @param list
     */
    void saveSettlementDetailBatch( List<SettlementDetail> list);

    void updateSettlementDetail(SettlementDetail settlementDetail);

    /**
     * 获取待我审批的结算单列表
     * @param organizationCode
     * @return List<SettlementDto>
     */
    List<SettlementDto> getSettlementDtoByOrganizationCode(String organizationCode);

    /**
     * 获取待我审批的结算单列表
     * @param organizationCode
     * @return List<SettlementDto>
     */
    List<SettlementDto> v116GetSettlementDtoByOrganizationCode(@Param("organizationCode") String organizationCode,@Param("index") Integer index,@Param("pageSize") Integer pageSize);

    /**
     * 获取待审批的结算单个数
     * @param organizationCode
     * @return
     */
    BadRecordsResultVO getToAuditSettlementCount(String organizationCode);

    /**
     * 审核结算单
     * @param settlementDto
     * @return
     */
    Integer examineSettlementDto(SettlementDto settlementDto);

    /**
     * 根据项目编号和工人信息获取结算单详细
     * @param projectCode
     * @param idCardType
     * @param idCardNumber
     * @return
     */

    List<PayrollSettlementDetail> getSettlementDetailListByWorkerAndProjectCode(
            @Param("projectCode")String projectCode,@Param("idCardType") String idCardType,@Param("idCardNumber") String idCardNumber);


    void updateSettlementDetailStatus(@Param("settlementCodeList")List<String>  settlementCodeList,@Param("userName")String userName);

    /**
     * 新增结算单时批量更新工资单
     * @return
     */
    Integer updatePayRollDetails(String ids);

    Integer updateDetailsPayPerson(SettlementDto settlementDto);

    List<SettlementDetail> getDetailListNoPage(@Param("map") Map<String, Object> map);
}
