package com.xywg.admin.modular.wages.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.system.model.Dict;
import com.xywg.admin.modular.wages.model.Settlement;
import com.xywg.admin.modular.wages.model.SettlementDetail;
import com.xywg.admin.modular.wages.model.dto.PictureDto;
import com.xywg.admin.modular.wages.model.dto.SettlementDetailDto;
import com.xywg.admin.modular.wages.model.dto.SettlementDto;

import java.util.List;
import java.util.Map;


public interface SettlementService extends IService<Settlement> {

    List<Map<String,Object>> getList(Page<Map<String,Object>> page, Map<String, Object> map);

    void deleteBatch(List<Settlement> list);

    /**
     * 根据工种类型的值模糊搜索
     *
     * @param workerTypeName
     * @return
     */
    List<Dict> getWorkerType(String workerTypeName);


    /**
     * 获取结算单详情
     *
     * @param page
     * @param map
     * @return
     */
    List<SettlementDetail> getSettlementDetail(Page<SettlementDetail> page, Map<String, Object> map);

    /**
     * 根据项目获取结算单列表
     *
     * @param projectCode ,  teamSysNo
     * @param id
     * @return
     */
    List<SettlementDto> getSettlementListByProjectCodeAndTeamSysNo(String projectCode, Integer id);

    /**
     * 根据项目获取结算单列表
     *
     * @param projectCode ,  teamSysNo
     * @param id
     * @return
     */
    List<SettlementDto> v116GetSettlementListByProjectCodeAndSysTeamNo(String projectCode, Integer id,Integer pageNo , Integer pageSize);

    /**
     * 批量发放工资
     *
     * @param list
     */
    void payrollBatch(List<Settlement> list);


    /**
     * 结算单审核
     *
     * @param list
     */
    void checkInfo(List<Settlement> list);

    /**
     * 复核
     */
    void review(List<Settlement> list);

    /**
     * 保存结算单以及明细
     *
     * @param settlement
     * @param settlementDetailList
     * @auth wangcw&hjy
     */
    void saveSettlement(Settlement settlement, List<SettlementDetail> settlementDetailList);

    void updateSettlementDetailBatch(Settlement settlement, List<SettlementDetail> settlementDetailList);

    List<SettlementDetail> getSettlementDetailBySettlementCode(Map<String, Object> map);


    /**
     * 根据结算单详情id获取结算单详情
     *
     * @param id
     * @return
     */
    List<Map<String,Object>> getSettlementDetailById(Long id);
    List<Map<String,Object>> getSettlementDetailById2(Long id,Long uid);

    /**
     * 结算单签字
     *
     * @param settlementDetailDto
     * @return
     */
    Integer settlementDetailDtoSign(SettlementDetailDto settlementDetailDto);

    /**
     * 新增结算单
     *
     * @param settlementDto
     * @return
     */
    Integer addSettlementDto(SettlementDto settlementDto);

    /**
     * 获取待我审批的结算单列表
     *
     * @param organizationCode
     * @return
     */
    List<SettlementDto> getSettlementDtoByOrganizationCode(String organizationCode);

    /**
     * 获取待我审批的结算单列表
     *
     * @param organizationCode
     * @return
     */
    List<SettlementDto> v116GetSettlementDtoByOrganizationCode(String organizationCode,Integer pageNo , Integer pageSize);


    /**
     * 审核结算单
     *
     * @param settlementDto
     * @return
     */
    Integer examineSettlementDto(SettlementDto settlementDto);

    /**
     * 得到附件图片集
     *
     * @return
     */
    PictureDto getAccessoryPicture(Long settlementId , String title , String tableName);

    void deleteAccessoryPicture(List<String> ids);

    List<SettlementDetail> getDetailListNoPage(Map<String, Object> map);
}
