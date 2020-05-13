package com.xywg.admin.modular.longxin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.modular.longxin.dao.LxTenderProcessRelationMapper;
import com.xywg.admin.modular.longxin.model.InviteBid;
import com.xywg.admin.modular.longxin.model.LxTender;
import com.xywg.admin.modular.longxin.model.LxTenderProcessRelation;
import com.xywg.admin.modular.longxin.service.LxTenderProcessRelationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tcw on 2019/7/9.
 */
@Service
public class LxTenderProcessRelationServiceImpl extends ServiceImpl<LxTenderProcessRelationMapper, LxTenderProcessRelation> implements LxTenderProcessRelationService{


    @Override
    public void addRelation(LxTenderProcessRelation lpr) {
        this.baseMapper.addRelation(lpr);
    }

	@Override
	public void addFixRelation(LxTenderProcessRelation lpr) {
		this.baseMapper.addFixRelation(lpr);
	}

	@Override
	public InviteBid getInviteById(LxTenderProcessRelation lpr) {
		return this.baseMapper.getInviteById(lpr);
	}

	@Override
	public List<LxTenderProcessRelation> getTenderRelationByBussId(LxTender tender) {
		return this.baseMapper.geteTenderRelationByBussId(tender);
	}

	@Override
	public String getFixRelation(LxTenderProcessRelation paramL) {
		return this.baseMapper.getFixRelation(paramL);
	}

	@Override
	public String getFlowRelation(LxTenderProcessRelation paramL) {
		return this.baseMapper.getFlowRelation(paramL);
	}
}
