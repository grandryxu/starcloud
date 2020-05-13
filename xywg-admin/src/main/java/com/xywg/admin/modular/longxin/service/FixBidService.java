package com.xywg.admin.modular.longxin.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.longxin.model.FixBid;
import com.xywg.admin.modular.longxin.model.FixMark;
import com.xywg.admin.modular.longxin.model.InviteBid;
import com.xywg.admin.modular.longxin.model.LxTender;
import com.xywg.admin.modular.longxin.model.LxTenderFile;
import com.xywg.admin.modular.longxin.model.TenderResultBean;

import java.util.List;
import java.util.Map;

/**
 * Created by tcw on 2019/7/9.
 */
public interface FixBidService extends IService<FixBid> {



    /**
     * 分页查询
     *@Author tcw
     *@Date 18:36  2019/7/9
     */
    List<FixBid> selectList(Map<String, Object> map, Page<FixBid> page);

    /**
     * 定标
     * @param tenderId
     * @param companyId
     * @param id 
     * @return 
     */
	Map ensureFixMark(String tenderId, String companyId, String id);



}
