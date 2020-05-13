package com.xywg.admin.modular.longxin.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.longxin.model.LxTender;
import com.xywg.admin.modular.longxin.model.LxTenderFile;
import com.xywg.admin.modular.longxin.model.TenderResultBean;

import java.util.List;
import java.util.Map;

/**
 * Created by tcw on 2019/7/9.
 */
public interface TenderingService extends IService<LxTender> {
    /**
     * 分页查询
     *@Author tcw
     *@Date 18:36  2019/7/9
     */
   List<TenderResultBean> selectList(Map<String, Object> map, Page<TenderResultBean> page);

   List<TenderResultBean> selectList1(Map<String, Object> map, Page<TenderResultBean> page);

    void saveFile(List<LxTenderFile> fileList);


    LxTender saveTender(LxTender td);

    TenderResultBean getById(String id);

    List<LxTenderFile> getFileById(String id);

    void updateStatus(LxTender tender);

	void updateStatusFix(LxTender tender);

    Map deleteTender(String id);

	Object updateManStatus(String id, String status);

	Object updateFlowStatus(String id, String status);

	List<TenderResultBean> selectListFix(Map<String, Object> map, Page<TenderResultBean> page);
	
	List<TenderResultBean> selectListTender(Map<String, Object> map, Page<TenderResultBean> page);

    Object setType(String id, String type);

    Object bid(String tenderId, String account);

    Integer selectType(String id);


    List<Map<String, Object>> getTenderList(Map<String, Object> map);

    Map<String, Object> tenderDetails(String tenderCode);

    List<Map<String, Object>> tenderFile(String tenderCode);

    List<Map<String, Object>> winningBidList(Map<String, Object> map);

    Map<String, Object> winningBidDetails(Map<String, Object> map);


    Map<String, Object> selectionList(Map<String, Object> map);

    Map<String, Object> candidateBidList(Map<String, Object> map);

    Map<String, Object> tenderDetail(Map<String, Object> map);

    Map<String, Object> toDoTasks(Map<String, Object> map);

    Map<String, Object> updateEvaluationBid(Map<String, Object> map);

    Map<String, Object> submitbid(Map<String, Object> map);

    Map<String, Object> reviewCalibration(Map<String, Object> map);

    Map<String, Object> missionDetails(Map<String, Object> map);

    Map<String, Object> currentBidOrRejectedBid(Map<String, Object> map);

    List<Map<String, Object>> announcementGetAllGetTenderList(Map<String, Object> map);
}
