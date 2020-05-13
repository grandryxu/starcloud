package com.xywg.admin.modular.longxin.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.longxin.model.InviteBid;
import com.xywg.admin.modular.longxin.model.LxTenderFile;
import com.xywg.admin.modular.longxin.model.LxTenderProcessRelation;
import com.xywg.admin.modular.longxin.model.TenderResultBean;
import com.xywg.admin.modular.longxin.service.InviteBidService;
import com.xywg.admin.modular.longxin.service.LxTenderProcessRelationService;
import com.xywg.admin.modular.longxin.service.TenderingService;
import com.xywg.admin.modular.system.model.Dict;
import com.xywg.admin.modular.system.service.IDictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tcw on 2019/7/9.
 */
@Controller
@RequestMapping("/lx/tendering")
public class TenderingController  extends BaseController {

    @Value("${spring.mvc.view.imageLocalPath}")
    private String path;

    private String PREFIX = "/longxin/tendering/";

    @Autowired
    private IDictService dictService;
    
    @Autowired
    private LxTenderProcessRelationService lxTenderProcessRelationService;


    @Autowired
    private TenderingService tenderingService;

    @Autowired
    private InviteBidService inviteBidService;

    @RequestMapping("delete")
    @ResponseBody
    public Object deleteTender(String id){

        return tenderingService.deleteTender(id);


    }

    @RequestMapping("setType")
    @ResponseBody
    public Object setType(String id,String type){

        return tenderingService.setType(id,type);
    }

    
    
    @RequestMapping("changeStatus")
    @ResponseBody
    public Object changeStatus(String id,String status){

        return tenderingService.updateManStatus(id,status);
    }
    
    @RequestMapping("changeFlow")
    @ResponseBody
    public Object changeFlow(String id,String status){

        return tenderingService.updateFlowStatus(id,status);
    }

    /**
     * 跳转到招标信息首页
     */
    @RequestMapping("index2")
    public String index2(Model model) {
        model.addAttribute("projectStatus", dictService.selectByName("状态"));
        model.addAttribute("contractorType", dictService.selectByName("参建类型"));
        model.addAttribute("deviceType", dictService.selectByName("考勤类型"));
        model.addAttribute("projectStatus", dictService.selectByName("项目状态"));
        model.addAttribute("isEnterprise", ShiroKit.getUser().getIsEnterprise());
        return PREFIX + "projectMaster2.html";

    }

    /**
     * 跳转到招标管理首页
     */
    @RequestMapping("")
    public String index(Model model,String id) {
        model.addAttribute("id",id);
        return PREFIX + "tendering.html";
    }
    
    
    /**
     *验证是否绑定
     */
    @RequestMapping("/isBind")
    @ResponseBody
    public Object isBind(Model model,String id) {
        LxTenderProcessRelation paramL = new LxTenderProcessRelation();
        paramL.setBussId(id);
        paramL.setType(1);
        String processId = lxTenderProcessRelationService.getFlowRelation(paramL);
        Map ret = new HashMap();
        if(StringUtils.isEmpty(processId)) {
        	ret.put("code", "500");
        	ret.put("message", "请先绑定项目流程");
		}else {
			ret.put("code", "200");
		}
        return ret;
    }


    /**
     * 获取发布的招标的列表
     */
    @RequestMapping(value = "/getAll")
    @ResponseBody
    public Object list(@RequestParam Map<String,Object> map ) {
        Page<TenderResultBean> page = new PageFactory<TenderResultBean>().defaultPage();
        List<TenderResultBean> list = this.tenderingService.selectList1(map, page);
        page.setRecords(list);
        return super.packForBT(page);
    }


    @RequestMapping(value="/view")
    public String view(String id,Model model) throws Exception{
        String fileStr="";
        TenderResultBean tb = tenderingService.getById(id);
        List<LxTenderFile>  fileList = tenderingService.getFileById(id);
        tb.setFiles(fileList);
        for (LxTenderFile lxTenderFile : fileList) {
            fileStr = fileStr + "<li> <a target='_blank' href='"+ path +"/"+lxTenderFile.getFilePath() +"'>"+lxTenderFile.getFileName()+" </a></li>";
        }
        model.addAttribute("tb",tb);
        model.addAttribute("fileStr",fileStr);
        Map map = new HashMap<>();
        map.put("tenderId",id);
        List<InviteBid> lbs = inviteBidService.selectList(map,new Page<>());
        model.addAttribute("inviteBid",lbs);
        model.addAttribute("ibSize",lbs.size());
        model.addAttribute("bidCompany","");
        //计算是否定标
        model.addAttribute("isFix",false);
        for (InviteBid inviteBid : lbs) {
			if(inviteBid.getStatus().equals("5")) {
				model.addAttribute("bidCompany",inviteBid.getCompanyName());
				model.addAttribute("isFix",true);
				break;
			}
		}
        List<Dict> dicts = dictService.selectByName("项目活动类型");
        for (Dict dict : dicts) {
			if(tb.getProjectType().toString().equals(dict.getNum().toString())) {
				model.addAttribute("projectActivityType", dict.getName());
			}
		}
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //计算是否开标
        String startStr = tb.getStartTime();
        Date date = simpleDateFormat.parse(startStr);
        if(date.before(new Date())) {
        	model.addAttribute("isBid", true);
        }else {
        	model.addAttribute("isBid", false);
        }

        return PREFIX + "tenderDetail.html";
    }

    @RequestMapping(value="/homeview")
    public String homeview(String id,Model model) throws Exception{
        String fileStr="";
        TenderResultBean tb = tenderingService.getById(id);
        List<LxTenderFile>  fileList = tenderingService.getFileById(id);
        tb.setFiles(fileList);
        for (LxTenderFile lxTenderFile : fileList) {
            fileStr = fileStr + "<li> <a target='_blank' href='"+ path +"/"+lxTenderFile.getFilePath() +"'>"+lxTenderFile.getFileName()+" </a></li>";
        }
        model.addAttribute("tb",tb);
        model.addAttribute("fileStr",fileStr);
        Map map = new HashMap<>();
        map.put("tenderId",id);
        List<InviteBid> lbs = inviteBidService.selectList(map,new Page<>());
        model.addAttribute("inviteBid",lbs);
        model.addAttribute("ibSize",lbs.size());
        model.addAttribute("bidCompany","");
        //计算是否定标
        model.addAttribute("isFix",false);
        for (InviteBid inviteBid : lbs) {
            if(inviteBid.getStatus().equals("5")) {
                model.addAttribute("bidCompany",inviteBid.getCompanyName());
                model.addAttribute("isFix",true);
                break;
            }
        }
        List<Dict> dicts = dictService.selectByName("项目活动类型");
        for (Dict dict : dicts) {
            if(tb.getProjectType().toString().equals(dict.getNum().toString())) {
                model.addAttribute("projectActivityType", dict.getName());
            }
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //计算是否开标
        String startStr = tb.getStartTime();
        Date date = simpleDateFormat.parse(startStr);
        if(date.before(new Date())) {
            model.addAttribute("isBid", true);
        }else {
            model.addAttribute("isBid", false);
        }

        return PREFIX + "homeTenderDetail.html";
    }
    
    
    @RequestMapping(value="/viewNotice")
    public String viewNotice(String id,Model model) throws Exception{
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fileStr="";
        TenderResultBean tb = tenderingService.getById(id);
        List<LxTenderFile>  fileList = tenderingService.getFileById(id);
        tb.setFiles(fileList);
        for (LxTenderFile lxTenderFile : fileList) {
            fileStr = fileStr + "<li> <a target='_blank' href='"+ path +"/"+lxTenderFile.getFilePath() +"'>"+lxTenderFile.getFileName()+" </a></li>";
        }
        model.addAttribute("tb",tb);
        String priceTemp = tb.getPriceTemp();
        JSONArray arr = JSONObject.parseArray(priceTemp);
        model.addAttribute("priceTemp",arr);
        if(arr != null) {
        	model.addAttribute("priceTempSize",arr.size());
        }else {
        	model.addAttribute("priceTempSize",0);
        }
        model.addAttribute("fileStr",fileStr);
        Map map = new HashMap<>();
        map.put("tenderId",id);
        List<InviteBid> lbs = inviteBidService.selectList(map,new Page<>());
        model.addAttribute("inviteBid",lbs);
        model.addAttribute("bidCompany","");
        model.addAttribute("bidDate","");
        model.addAttribute("bidDate","");
        model.addAttribute("bidAddress","");
        model.addAttribute("contactPeopleName","");
        model.addAttribute("contactPeopleCellPhone","");
        model.addAttribute("priceArr","");
        for (InviteBid inviteBid : lbs) {
			if(inviteBid.getStatus().equals("5")) {
				model.addAttribute("bidCompany",inviteBid.getCompanyName());
				if(inviteBid.getDate()!=null){
					model.addAttribute("bidDate",simpleDateFormat.format(inviteBid.getDate()));
				}
				model.addAttribute("bidAddress",inviteBid.getBidAddress());
				model.addAttribute("contactPeopleName",inviteBid.getContactPeopleName());
				model.addAttribute("contactPeopleCellPhone",inviteBid.getContactPeopleCellPhone());
				String bidPrice = inviteBid.getBidPrice();
				if(!StringUtils.isEmpty(bidPrice)) {
					String[] priceArr = bidPrice.split("\\|");
					model.addAttribute("priceArr",priceArr);
				}
				break;
			}
		}
        List<Dict> dicts = dictService.selectByName("项目活动类型");
        for (Dict dict : dicts) {
			if(tb.getProjectType().toString().equals(dict.getNum().toString())) {
				model.addAttribute("projectActivityType", dict.getName());
			}
		}
        //计算是否开标
        String startStr = tb.getStartTime();
        Date date = simpleDateFormat.parse(startStr);
        if(date.before(new Date())) {
        	model.addAttribute("isBid", true);
        }else {
        	model.addAttribute("isBid", false);
        }

        return PREFIX + "notice.html";
    }


    @RequestMapping("pingbiao")
    public String pingbiao(String id,Model model,String flag){
        model.addAttribute("id",id);
        model.addAttribute("flag",flag);
        return PREFIX + "pingbiao.html";
    }

    @RequestMapping("addPingbiao")
    @ResponseBody
    public Object  addPingbiao(String id,String text,String flag){
        InviteBid ib = new InviteBid();
        ib.setId(id);
        ib.setStatus("3");
        if("1".equals(flag)){
                ib.setPingbiao1(text);
        }else if("2".equals(flag)){
            ib.setPingbiao2(text);
        }else if("3".equals(flag)){
            ib.setPingbiao3(text);
        }
    ib.setFlag(flag);
         inviteBidService.updateBidById(ib);
        Map map = new HashMap<>();
        map.put("code","200");
        map.put("message","新增成功");

        return  map;
    }


    @RequestMapping(value = "/selectType")
    @ResponseBody
    public Object selectType(@RequestParam String id){
        Integer type=tenderingService.selectType(id);
        return type;
    }


}
