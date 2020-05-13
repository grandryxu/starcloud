package com.xywg.admin.modular.longxin.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.company.model.RegSubContractor;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.longxin.model.InviteBid;
import com.xywg.admin.modular.longxin.model.LxOrder;
import com.xywg.admin.modular.longxin.model.LxTender;
import com.xywg.admin.modular.system.model.User;

import java.util.Map;

/**
 * Created by tcw on 2019/7/9.
 */
public interface LxOrderService extends IService<LxOrder> {


    Page<LxOrder<LxTender>> getAll();

    Map shenhe(String orderId, Boolean success,String bussId);
    Map shenheReg(String orderId, Boolean success,String bussId, String organizationCode);

    Map shenheFix(String orderId, Boolean success, String bussId);

    Page<LxOrder<LxTender>> getAllHistory();

    Page<LxOrder<InviteBid>> getAllHistorySub();

    Page<LxOrder<RegSubContractor>> getAllHistoryReg();

    Page<LxOrder<RegSubContractor>> getAllReg();
}
