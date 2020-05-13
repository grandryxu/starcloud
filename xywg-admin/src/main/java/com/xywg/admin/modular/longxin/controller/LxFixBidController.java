package com.xywg.admin.modular.longxin.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.flow.entity.Process;
import com.xywg.admin.flow.service.ProcessService;
import com.xywg.admin.modular.longxin.model.FixBid;
import com.xywg.admin.modular.longxin.model.InviteBid;
import com.xywg.admin.modular.longxin.model.LxTenderFile;
import com.xywg.admin.modular.longxin.model.TenderResultBean;
import com.xywg.admin.modular.longxin.service.FixBidService;
import com.xywg.admin.modular.longxin.service.InviteBidService;
import com.xywg.admin.modular.longxin.service.TenderingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author xuehao.shi
 * 2019/07/11
 */
@Controller
@RequestMapping("/lxFixBid")
public class LxFixBidController  extends BaseController {


    private String PREFIX = "/longxin/fix/";

    @Value("${spring.mvc.view.imageLocalPath}")
    private String path;

    @Autowired
    private TenderingService tenderingService;
    
    @Autowired
    private FixBidService fixBidService;
    
    @Autowired
    private InviteBidService inviteBidService;
    
    @Autowired
    private ProcessService processService;
    

    /**
     * 跳转到招标管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "fixBid.html";
    }
  


    /**
     * 获取发布的招标的列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String,Object> map) {
        Page<FixBid> page = new PageFactory<FixBid>().defaultPage();
        List<FixBid> list = this.fixBidService.selectList(map, page);
        page.setRecords(list);
        return super.packForBT(page);
    }
    
    /**
     * 跳转到定标详情
     */
    @RequestMapping(value = "/detail")
    public String detail(@RequestParam Map<String,Object> map,Model model) {
    	String bidId = map.get("bidId").toString();
    	Boolean flag = Boolean.valueOf(map.get("flag").toString());
    	TenderResultBean tender = tenderingService.getById(bidId);
    	List<LxTenderFile> files = tenderingService.getFileById(bidId);
    	List<InviteBid> invs = inviteBidService.selectByBid(bidId);
    	List<Process> processList = processService.getAll();
    	for (InviteBid inv : invs) {
			if(inv.getStatus().equals("5")||inv.getStatus().equals("4")) {
				model.addAttribute("selectCom",inv);
			}
		}
        model.addAttribute("process",processList);
    	model.addAttribute("tender", tender);
    	model.addAttribute("files", files);
    	model.addAttribute("companys", invs);
    	model.addAttribute("path", path);
    	model.addAttribute("flag", flag);
        return PREFIX + "fixBidDetail.html";
    }
    
    
    
    
    /**
     * 定标
     */
    @RequestMapping(value = "/ensureFixMark")
    @ResponseBody
    public Object ensureFixMark(@RequestParam Map<String,Object> map,Model model) {
    	String companyId = map.get("companyId").toString();
    	String tenderId = map.get("tenderId").toString();
    	String id = map.get("id").toString();
    	fixBidService.ensureFixMark(tenderId,companyId,id);
    	return SUCCESS;
    }

}
