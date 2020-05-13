package com.xywg.admin.modular.longxin.service;

import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.longxin.model.InviteBid;
import com.xywg.admin.modular.longxin.model.LxTender;
import com.xywg.admin.modular.longxin.model.LxTenderProcessRelation;

import java.util.List;

/**
 * Created by tcw on 2019/7/11.
 */
public interface LxTenderProcessRelationService extends IService<LxTenderProcessRelation> {


    void addRelation(LxTenderProcessRelation lpr);

	void addFixRelation(LxTenderProcessRelation lpr);

	InviteBid getInviteById(LxTenderProcessRelation lpr);

	List<LxTenderProcessRelation> getTenderRelationByBussId(LxTender tender);

	String getFixRelation(LxTenderProcessRelation paramL);

	String getFlowRelation(LxTenderProcessRelation paramL);
}
