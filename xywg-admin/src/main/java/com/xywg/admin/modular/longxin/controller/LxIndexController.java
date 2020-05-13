package com.xywg.admin.modular.longxin.controller;

import com.xywg.admin.modular.longxin.service.LxIndexService;
import com.xywg.admin.modular.longxin.service.LxOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tcw on 2019/7/16.
 */
@RequestMapping("/lxIndex")
@Controller
public class LxIndexController {

    @Autowired
    private LxOrderService lxOrderService;

    @Autowired
    private LxIndexService indexService;

    @RequestMapping("getApprovalResult")
    @ResponseBody
    public Object getApprovalResult(HttpServletRequest request)
    {
        request.setAttribute("pageSize","100000");
        return indexService.getApprovalResult();
    }
}
