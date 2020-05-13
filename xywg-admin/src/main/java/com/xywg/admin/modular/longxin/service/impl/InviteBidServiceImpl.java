package com.xywg.admin.modular.longxin.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.util.DateUtils;
import com.xywg.admin.flow.entity.Order;
import com.xywg.admin.flow.service.OrderService;
import com.xywg.admin.modular.longxin.dao.InviteBidMapper;
import com.xywg.admin.modular.longxin.dao.TenderMapper;
import com.xywg.admin.modular.longxin.model.*;
import com.xywg.admin.modular.longxin.service.InviteBidService;
import com.xywg.admin.modular.longxin.service.LxTenderProcessRelationService;
import com.xywg.admin.modular.system.dao.UserMapper;
import com.xywg.admin.modular.system.model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tcw on 2019/7/9.
 */
@Service
public class InviteBidServiceImpl extends ServiceImpl<InviteBidMapper, InviteBid> implements InviteBidService{
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private LxTenderProcessRelationService lxTenderProcessRelationService;

	@Autowired
	private TenderMapper tenderMapper;

    @Override
    public List<InviteBid> selectList(Map<String, Object> map, Page<InviteBid> page) {
        return this.baseMapper.selectList(map,page);
    }

	@Override
	@Transactional
	public Integer insertFix(FixMark fixMark) {
		//邀请表状态改为已参与竞标
		this.baseMapper.fixStatus(fixMark);
		//插入fixmark
		Integer inStep = this.baseMapper.updatePrice(fixMark);
		if(inStep == 0) {
			this.baseMapper.insertFix(fixMark);
		}
		return inStep;
	}

	@Override
	public List<InviteBid> selectByBid(String bidId) {
		return this.baseMapper.selectByBid(bidId);
	}

	@Override
	public Map addPingbiao(BidPingbiao bp) {
		Map map = new HashMap<>();
		map.put("code","200");
		map.put("message","新增成功");

		this.baseMapper.addPingbiao(bp);
		return map;
	}

	@Override
	public void updateBidById(InviteBid ib) {
		this.baseMapper.updateBidById(ib);
	}

	@Override
	public Page<LxOrder<InviteBid>> getAllSub() {
        User param = new User();
        param.setId(ShiroKit.getUser().getId());
        User user = userMapper.selectOne(param);
        List l = new ArrayList();
        l.add(Integer.parseInt(user.getRoleid()));

        //获取所有代办流程
        Page<Order> page =  orderService.findActiveOrders( new Order(),user.getId(),user.getDeptid() ,l);
        List<Order >  orderList = page.getRecords();
        List<LxOrder<InviteBid>>  lxOrders = new ArrayList<>();
        for (Order order : orderList) {
            LxOrder<InviteBid> lxod = new LxOrder<>();
            lxod.setOrder(order);
            LxTenderProcessRelation lpr = new LxTenderProcessRelation();
            lpr.setProcessId(order.getId());
			lpr.setType(2);
            InviteBid lxtd= lxTenderProcessRelationService.getInviteById(lpr);
            if(lxtd!=null){
                lxod.setBusiness(lxtd);
                lxOrders.add(lxod);
            }
        }
        Page<LxOrder<InviteBid>>  lxpage =new Page<>();
        lxpage.setRecords(lxOrders);
        return lxpage;
    }

	@Override
	public String getPriceByInviteId(String bidId) {
		return this.baseMapper.getPriceByInviteId(bidId);
	}

	@Override
	public List<InviteBid> reportlist(Map<String, Object> map, Page<InviteBid> page) {
		return this.baseMapper.reportlist(map,page);
	}

    @Override
    public List<InviteMonthReport> monthReportData(Map<String, Object> map) {
    	String year = MapUtils.getString(map, "reportYear");
        map.put("list", DateUtils.getMonthList(year));
        return baseMapper.monthReportData(map);
    }

	@Override
	public Map priceCompareData(Map<String, Object> map) {
		TenderResultBean tender = tenderMapper.getById((String) map.get("tenderId"));
		List<BidPrice> bidPrices = baseMapper.priceCompareData(map);

		List<List<String>> prices = new ArrayList<>();
		if(!tender.getPriceTemp().isEmpty()) {
			JSONArray retArr = JSONArray.fromObject(tender.getPriceTemp());
			for (int i = 0; i < retArr.size(); i++) {
				List<String> price = new ArrayList<>();
				JSONObject jsonObject = retArr.getJSONObject(i);
				price.add(jsonObject.getString("name"));

				for (BidPrice bidPrice : bidPrices) {
					String priceStr = bidPrice.getBidPrice();
					String[] pricesArr = priceStr.split("\\|");
					price.add(pricesArr[i]);
				}
				prices.add(price);
			}
		}

		List<String> companyList = new ArrayList<>();
		companyList.add("参建单位");
		for (BidPrice bidPrice : bidPrices) {
			companyList.add(bidPrice.getCompanyName());
		}

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("companyList", companyList);
		resultMap.put("prices", prices);

		return resultMap;
	}

}
