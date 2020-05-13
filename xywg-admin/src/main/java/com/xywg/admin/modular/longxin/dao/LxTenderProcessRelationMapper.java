package com.xywg.admin.modular.longxin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.modular.longxin.model.InviteBid;
import com.xywg.admin.modular.longxin.model.LxTender;
import com.xywg.admin.modular.longxin.model.LxTenderProcessRelation;

import java.util.List;

/**
 * <p>
 * 企业材料表 Mapper 接口
 * </p>
 *
 * @author xuehao.shi
 * @since
 */
public interface LxTenderProcessRelationMapper extends BaseMapper<LxTenderProcessRelation> {


	void addRelation(LxTenderProcessRelation lpr);


	LxTender getTenderById(LxTenderProcessRelation lpr);
	
	LxTender getTenderByIdHis(LxTenderProcessRelation lpr);
	
	InviteBid getInviteById(LxTenderProcessRelation lpr);
	
	InviteBid getInviteByIdHis(LxTenderProcessRelation lpr);


	void addFixRelation(LxTenderProcessRelation lpr);

	List<LxTenderProcessRelation> geteTenderRelationByBussId(LxTender tender);


	String getFixRelation(LxTenderProcessRelation paramL);


	String getFlowRelation(LxTenderProcessRelation paramL);

	LxTenderProcessRelation getRegById(LxTenderProcessRelation lpr);
}
