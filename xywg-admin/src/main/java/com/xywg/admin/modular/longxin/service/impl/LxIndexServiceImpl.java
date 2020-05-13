package com.xywg.admin.modular.longxin.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.longxin.service.InviteBidService;
import com.xywg.admin.modular.longxin.service.LxIndexService;
import com.xywg.admin.modular.longxin.service.LxOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tcw on 2019/7/9.
 */
@Service
public class LxIndexServiceImpl  implements LxIndexService{

    @Autowired
    private InviteBidService inviteBidService;
        @Autowired
        private LxOrderService lxOrderService;
    @Override
    public Map getApprovalResult() {
        Map  result = new HashMap();
        Map map = new HashMap();
        Page pg=  lxOrderService.getAll();
        Page pg2 = inviteBidService.getAllSub();
        map.put("outEnterpriseTotal",pg.getRecords().size()+ pg2.getRecords().size());
        map.put("tenderFileTotal","2");
        map.put("tenderTotal",pg.getRecords().size());
        map.put("targetTotal",pg2.getRecords().size());
        result.put("data",map);

        return result;
    }
}
