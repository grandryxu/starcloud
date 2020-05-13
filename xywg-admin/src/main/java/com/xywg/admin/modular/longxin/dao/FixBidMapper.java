package com.xywg.admin.modular.longxin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.longxin.model.FixBid;
import com.xywg.admin.modular.longxin.model.FixMark;
import com.xywg.admin.modular.longxin.model.InviteBid;

/**
 * @author xuehao.shi
 * 	2019/07/11
 */
public interface FixBidMapper extends BaseMapper<FixBid> {

    List<FixBid> selectList(@Param("map")Map<String, Object> map, Page<FixBid> page);

	Object ensureFixMark(@Param("tenderId")String tenderId, @Param("companyId")String companyId);

}
