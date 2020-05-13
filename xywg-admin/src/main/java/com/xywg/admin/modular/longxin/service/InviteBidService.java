package com.xywg.admin.modular.longxin.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.longxin.model.*;

import java.util.List;
import java.util.Map;

/**
 * Created by tcw on 2019/7/9.
 */
public interface InviteBidService extends IService<InviteBid> {

    /**
     * 分页查询
     *@Author tcw
     *@Date 18:36  2019/7/9
     */
    List<InviteBid> selectList(Map<String, Object> map, Page<InviteBid> page);

	Integer insertFix(FixMark fixMark);

	List<InviteBid> selectByBid(String bidId);

    Map addPingbiao(BidPingbiao bp);

    void updateBidById(InviteBid ib);

	Page getAllSub();

	String getPriceByInviteId(String bidId);

    List<InviteBid> reportlist(Map<String, Object> map, Page<InviteBid> page);

    List<InviteMonthReport> monthReportData(Map<String, Object> map);

    Map priceCompareData(Map<String, Object> map);
}
