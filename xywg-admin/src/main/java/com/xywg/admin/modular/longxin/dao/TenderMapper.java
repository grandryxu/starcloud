package com.xywg.admin.modular.longxin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.longxin.model.LxTender;
import com.xywg.admin.modular.longxin.model.LxTenderFile;
import com.xywg.admin.modular.longxin.model.TenderResultBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by tcw on 2019/7/9.
 */
public interface TenderMapper extends BaseMapper<LxTender> {


    List<TenderResultBean> selectList(@Param("map")Map<String, Object> map, Page<TenderResultBean> page);

    List<TenderResultBean> selectListFix(@Param("map")Map<String, Object> map, Page<TenderResultBean> page);

    void saveFile(List<LxTenderFile> fileList);

    void saveTender(LxTender td);

    TenderResultBean getById(String id);

    List<LxTenderFile> getFileByTenderId(String id);

    void updateStatus(LxTender tender);

	void updateStatusFix(LxTender tender);

    void deleteTender(LxTender tender);

	void updateManStatus(@Param("id")String id, @Param("status")String status);

	void updateFlowStatus(@Param("id")String id, @Param("status")String status);

    List<TenderResultBean> selectList1(@Param("map")Map<String, Object> map, Page<TenderResultBean> page);
    
    List<TenderResultBean> selectListTender(@Param("map")Map<String, Object> map, Page<TenderResultBean> page);

    void setType(LxTender tender);

    Integer selectType(@Param("id") String id);

    List<Map<String, java.lang.Object>> getTenderList(@Param("account") String account, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize, @Param("map") Map<String, java.lang.Object> map);

    Map<String, java.lang.Object> tenderDetails(@Param("tenderCode") String tenderCode);

    List<Map<String, java.lang.Object>> tenderFile(@Param("tenderCode") String tenderCode);

    List<Map<String, java.lang.Object>> winningBidList(@Param("account") String account, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    Map<String, java.lang.Object> winningBidDetails(@Param("tenderCode") String tenderCode);

    Map<String, String> tenderDetailList(@Param("tenderCode") String tenderCode);

    List<Map<String, java.lang.Object>> selectionList(@Param("account") String account, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize, @Param("map") Map<String, java.lang.Object> map);

    List<Map<String, java.lang.Object>> candidateBidList(@Param("tenderCode") String tenderCode);

    Map<String, String> tenderDetail(@Param("tenderCode") String tenderCode);

    List<Map<String, java.lang.Object>> toDoTasks(@Param("account") String account, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize, @Param("type") String type);


    String selectEvaluationBid(@Param("tenderCode") String tenderCode, @Param("unitCode") String unitCode);

    void updateEvaluationBid(@Param("evaluateContent") String evaluateContent, @Param("evaluateTimes") String evaluateTimes, @Param("id") String id);


    Integer submitbid(@Param("tenderCode") String tenderCode, @Param("unitCode") String unitCode);

    Integer updateSubmitbid(@Param("id") Integer id);

    void reviewCalibration(@Param("id") String id);

    Integer selectId(@Param("tenderCode") String tenderCode);

    void reviewCalibrationPass(@Param("toString") String toString);

    Integer currentBid(@Param("tenderCode") String tenderCode);

    Integer RejectedBid(@Param("tenderCode") String tenderCode);

    Map<String, java.lang.Object> selectDetails(@Param("account") String account, @Param("tenderCode") String tenderCode);

    List<Map<String, java.lang.Object>> filePath(@Param("tenderCode") String tenderCode);


    String selectProjectId(String tenderCode);

    Integer canApproval(@Param("account") String account, @Param("projectId") String projectId);

    Integer canApprovalBid(@Param("account") String account, @Param("projectId") String projectId);

    Map<String, String> tenderDetailAndCompany(@Param("tenderCode") String tenderCode, @Param("unitCode") String unitCode);


    Integer reviewCalibrationBid(@Param("id") Integer id);

    Integer reviewCalibrationPassBid(@Param("id") Integer id);

    List<Map<String, java.lang.Object>> announcementGetAllGetTenderList(@Param("account") String account, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    List<Map<String, java.lang.Object>> toDoTasksD(@Param("account") String account, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize, @Param("type") String type);

    List<Map<String,java.lang.Object>> candidateBidListZb(@Param("tenderCode") String tenderCode);

    Integer selectIdZb(@Param("tenderCode") String tenderCode);

    void updateEvaluationBid1(@Param("evaluateContent") String evaluateContent, @Param("id") String id);

    void updateEvaluationBid2(@Param("evaluateContent") String evaluateContent, @Param("id") String id);

    void updateEvaluationBid3(@Param("evaluateContent") String evaluateContent, @Param("id") String id);





    String selectTenderId(@Param("tenderCode") String tenderCode);

    String selectCompamyId(@Param("unitCode") String unitCode);

    void updatestatusPb(@Param("id") String id);

    List<Map<String, Object>> candidateBidListZbPingBiao1(@Param("tenderCode") String tenderCode);

    List<Map<String, Object>> candidateBidListZbDingBiao1(@Param("tenderCode") String tenderCode);

    String getid(@Param("tenderCode") String tenderCode);

    String getCompanyId(@Param("unitCode") String unitCode);

    String getibId(@Param("tendId") String tendId);

    String selectUserIdByUserName(String account);
}
