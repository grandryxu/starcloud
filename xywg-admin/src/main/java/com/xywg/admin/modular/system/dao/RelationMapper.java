package com.xywg.admin.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.modular.longxin.model.InviteBid;
import com.xywg.admin.modular.longxin.model.LxTenderProcessRelation;
import com.xywg.admin.modular.system.model.Relation;

/**
 * <p>
  * 角色和菜单关联表 Mapper 接口
 * </p>
 *
 * @author wangcw
 * @since 2017-07-11
 */
public interface RelationMapper extends BaseMapper<Relation> {

	InviteBid getInviteById(LxTenderProcessRelation lpr);

}