package com.xywg.admin.modular.longxin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.flow.entity.Order;
import com.xywg.admin.flow.service.ProcessService;
import com.xywg.admin.modular.longxin.dao.FixBidMapper;
import com.xywg.admin.modular.longxin.dao.InviteBidMapper;
import com.xywg.admin.modular.longxin.model.FixBid;
import com.xywg.admin.modular.longxin.model.FixMark;
import com.xywg.admin.modular.longxin.model.InviteBid;
import com.xywg.admin.modular.longxin.model.LxTenderProcessRelation;
import com.xywg.admin.modular.longxin.model.TenderResultBean;
import com.xywg.admin.modular.longxin.service.FixBidService;
import com.xywg.admin.modular.longxin.service.InviteBidService;
import com.xywg.admin.modular.longxin.service.LxTenderProcessRelationService;
import com.xywg.admin.modular.longxin.service.TenderingService;
import com.xywg.admin.modular.system.dao.UserMapper;
import com.xywg.admin.modular.system.model.User;

/**
 * Created by tcw on 2019/7/9.
 */
@Service
public class FixBidServiceImpl extends ServiceImpl<FixBidMapper, FixBid> implements FixBidService{
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private ProcessService processService;
	
	@Autowired
	private LxTenderProcessRelationService lxTenderProcessRelationService;
	@Autowired
	private TenderingService tenderingService;
	

    @Override
    public List<FixBid> selectList(Map<String, Object> map, Page<FixBid> page) {
        return this.baseMapper.selectList(map,page);
    }

	@Override
	public Map ensureFixMark(String tenderId, String companyId, String id) {
		//添加流程 		by项目查出processId
		Map ret = new HashMap();
				TenderResultBean tenderResultBean = tenderingService.getById(tenderId);
				String projectId = tenderResultBean.getProjectId();
				LxTenderProcessRelation paramL = new LxTenderProcessRelation();
				paramL.setBussId(projectId);
				paramL.setType(1);
				String processId = lxTenderProcessRelationService.getFixRelation(paramL);
				if(StringUtils.isEmpty(processId)) {
					ret.put("code", "500");
					ret.put("message", "请绑定项目流程");
					return ret;
				}
				User param = new User();
		        param.setId(ShiroKit.getUser().getId());
		        User user = userMapper.selectOne(param);
		        List l = new ArrayList();
		        l.add(Integer.parseInt(user.getRoleid()));
		        
				Order order = new Order();
		        //插入流程
		        try {
		            order=   processService.start(processId,Integer.parseInt(user.getId().toString()),Integer.parseInt(user.getDeptid().toString()),l);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        LxTenderProcessRelation lpr = new LxTenderProcessRelation();
		        lpr.setBussId(id);
		        lpr.setProcessId(order.getId());
		        lpr.setType(2);
		        lxTenderProcessRelationService.addFixRelation(lpr);
		
		 this.baseMapper.ensureFixMark(tenderId,companyId);
		return ret;
	}





}
