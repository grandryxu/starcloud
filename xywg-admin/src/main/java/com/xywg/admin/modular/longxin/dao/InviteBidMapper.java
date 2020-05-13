package com.xywg.admin.modular.longxin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.longxin.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author xuehao.shi
 * 	2019/07/11
 */
public interface InviteBidMapper extends BaseMapper<InviteBid> {

    List<InviteBid> selectList(@Param("map")Map<String, Object> map, Page<InviteBid> page);

	Integer insertFix(FixMark fixMark);
	
	Integer updatePrice(FixMark fixMark);

	Integer fixStatus(FixMark fixMark);

	List<InviteBid> selectByBid(@Param("bidId")String bidId);

	void addPingbiao(BidPingbiao bp);

	void updateBidById(InviteBid ib);

	String getPriceByInviteId(@Param("bidId")String bidId);

	List<InviteBid> reportlist(@Param("map")Map<String, Object> map, Page<InviteBid> page);

	List<InviteMonthReport> monthReportData(@Param("map")Map<String, Object> map);

	List<BidPrice> priceCompareData(@Param("map")Map<String, Object> map);
}
