package com.xywg.admin.modular.longxin.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.excel.ExcelUtils;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.util.DateUtils;
import com.xywg.admin.modular.company.model.EmployeeMasterExport;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.company.utils.ServletsUtils;
import com.xywg.admin.modular.company.wrapper.EmployeeMasterWarpper;
import com.xywg.admin.modular.longxin.model.FixMark;
import com.xywg.admin.modular.longxin.model.InviteBid;
import com.xywg.admin.modular.longxin.model.LxTender;
import com.xywg.admin.modular.longxin.model.LxTenderFile;
import com.xywg.admin.modular.longxin.model.TenderResultBean;
import com.xywg.admin.modular.longxin.service.InviteBidService;
import com.xywg.admin.modular.longxin.service.LxProjectService;
import com.xywg.admin.modular.longxin.service.TenderingService;
import com.xywg.admin.modular.system.model.Dict;
import com.xywg.admin.modular.system.model.User;
import com.xywg.admin.modular.system.service.IDictService;

import com.xywg.admin.modular.system.service.IUserService;
import net.sf.json.JSONArray;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author xuehao.shi
 * 2019/07/11
 */
@Controller
@RequestMapping("/lxInviteBid")
public class LxInviteBidController  extends BaseController {


    private String PREFIX = "/longxin/invite/";

    @Value("${spring.mvc.view.imageLocalPath}")
    private String path;

    @Autowired
    private TenderingService tenderingService;
    
    @Autowired
    private InviteBidService inviteBidService;
    
    @Autowired
    private IDictService dictService;

    @Autowired
    private IUserService userService;

    /**
     * 跳转到招标管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "inviteBid.html";
    }
    
    /**
     * 跳转到填写竞标价格页面
     */
    @RequestMapping("/price_add")
    public String subContractorAdd(@RequestParam Map<String,Object> map,Model model) {
    	String bidId = map.get("bidId").toString();
    	String tenderId = map.get("tenderId").toString();
    	Boolean flag = Boolean.valueOf(map.get("flag").toString());
    	TenderResultBean lt = tenderingService.getById(tenderId);
    	model.addAttribute("bidId",bidId);
    	model.addAttribute("flag",flag);
    	if(!lt.getPriceTemp().isEmpty()) {
    		JSONArray retArr = JSONArray.fromObject(lt.getPriceTemp());
    		model.addAttribute("priceTemp",retArr);
    		model.addAttribute("priceTempSize",retArr.size());
    	}else {
    		model.addAttribute("priceTemp",new ArrayList());
    	}
    	model.addAttribute("priceSize", 0);
    	//获取price
        String priceStr = inviteBidService.getPriceByInviteId(bidId);
        if(!StringUtils.isEmpty(priceStr)) {
        	String[] prices = priceStr.split("\\|");
        	model.addAttribute("prices", Arrays.asList(prices));
        	model.addAttribute("priceSize", prices.length);
        }else {
        	model.addAttribute("prices", new ArrayList());
        	model.addAttribute("priceSize", 0);
        }
    	
    	String fileStr="";
        List<LxTenderFile>  fileList = tenderingService.getFileById(tenderId);
        lt.setFiles(fileList);
        for (LxTenderFile lxTenderFile : fileList) {
            fileStr = fileStr + "<li> <a target='_blank' href='"+ path +"/"+lxTenderFile.getFilePath() +"'>"+lxTenderFile.getFileName()+" </a></li>";
        }
        model.addAttribute("tb",lt);
        model.addAttribute("fileStr",fileStr);
        
        List<Dict> dicts = dictService.selectByName("项目活动类型");
        for (Dict dict : dicts) {
			if(lt.getProjectType().toString().equals(dict.getNum().toString())) {
				model.addAttribute("projectActivityType", dict.getName());
			}
		}
    	
        return PREFIX + "invite_price_add_view.html";
    }
    
    /**
     * 跳转到填写竞标价格页面
     */
    @RequestMapping("/price_add_view")
    public String subContractorAddView(@RequestParam Map<String,Object> map,Model model) {
    	String bidId = map.get("bidId").toString();
    	String tenderId = map.get("tenderId").toString();
    	Boolean flag = Boolean.valueOf(map.get("flag").toString());
    	TenderResultBean lt = tenderingService.getById(tenderId);
    	model.addAttribute("bidId",bidId);
    	model.addAttribute("flag",flag);
    	if(!lt.getPriceTemp().isEmpty()) {
    		JSONArray retArr = JSONArray.fromObject(lt.getPriceTemp());
    		model.addAttribute("priceTemp",retArr);
    		model.addAttribute("priceTempSize",retArr.size());
    	}else {
    		model.addAttribute("priceTemp",new ArrayList());
    	}
    	model.addAttribute("priceSize", 0);
    	if(flag) {
    		//获取price
        	String priceStr = inviteBidService.getPriceByInviteId(bidId);
        	String[] prices = priceStr.split("\\|");
        	model.addAttribute("prices", Arrays.asList(prices));
        	model.addAttribute("priceSize", prices.length);
    	}
    	
    	String fileStr="";
        List<LxTenderFile>  fileList = tenderingService.getFileById(tenderId);
        lt.setFiles(fileList);
        for (LxTenderFile lxTenderFile : fileList) {
            fileStr = fileStr + "<li> <a target='_blank' href='"+ path +"/"+lxTenderFile.getFilePath() +"'>"+lxTenderFile.getFileName()+" </a></li>";
        }
        model.addAttribute("tb",lt);
        model.addAttribute("fileStr",fileStr);
        
        List<Dict> dicts = dictService.selectByName("项目活动类型");
        for (Dict dict : dicts) {
			if(lt.getProjectType().toString().equals(dict.getNum().toString())) {
				model.addAttribute("projectActivityType", dict.getName());
			}
		}
    	
        return PREFIX + "invite_price_add.html";
    }
    
    /**
     * 新增定标信息
     */
    @RequestMapping(value = "/addFixMark")
    @ResponseBody
    public Object add(FixMark fixMark) {
    	Integer res = inviteBidService.insertFix(fixMark);
        return res;
    }


    /**
     * 获取发布的招标的列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String,Object> map) {
        Page<InviteBid> page = new PageFactory<InviteBid>().defaultPage();
        String account = ShiroKit.getUser().getAccount();
        map.put("account", account);
        map.put("remark", "邀标");
        List<InviteBid> list = this.inviteBidService.selectList(map, page);
        page.setRecords(list);
        return super.packForBT(page);
    }


    @RequestMapping(value="/view")
    public String view(String id,Model model){
        String fileStr="";
        TenderResultBean tb = tenderingService.getById(id);
        List<LxTenderFile>  fileList = tenderingService.getFileById(id);
        tb.setFiles(fileList);
        for (LxTenderFile lxTenderFile : fileList) {
            fileStr = fileStr + "<li>"+lxTenderFile.getFileName()+" </li>";
        }
        model.addAttribute("tb",tb);
        model.addAttribute("fileStr",fileStr);
        return PREFIX + "tenderDetail.html";
    }

    /**
     * 跳转到招标投标
     */
    @RequestMapping("/tender")
    public String tender(Model model) {
        User user = userService.getByAccount(ShiroKit.getUser().getAccount());
        model.addAttribute("flowStatus", StringUtils.isNotBlank(user.getFlowStatus()) ? user.getFlowStatus() : "0");
        return PREFIX + "invite_tender.html";
    }

    /**
     *  参与投标
     */
    @RequestMapping(value = "/bid", method = RequestMethod.POST)
    @ResponseBody
    public Object bid(@RequestParam String tenderId) {
        String account =  ShiroKit.getUser().getAccount();
        return tenderingService.bid(tenderId, account);
    }

    /**
     * 获取招标投标列表
     */
    @RequestMapping(value = "/tenderlist")
    @ResponseBody
    public Object tenderlist(@RequestParam Map<String,Object> map) {
        Page<InviteBid> page = new PageFactory<InviteBid>().defaultPage();
        String account = ShiroKit.getUser().getAccount();
        map.put("account", account);
        map.put("remark", "投标");
        List<InviteBid> list = this.inviteBidService.selectList(map, page);
        page.setRecords(list);
        return super.packForBT(page);
    }

    /**
     * 跳转到招标统计
     */
    @RequestMapping("/report")
    public String report() {
        return PREFIX + "invite_report.html";
    }

    /**
     * 招标统计列表
     */
    @RequestMapping(value = "/reportlist")
    @ResponseBody
    public Object reportlist(@RequestParam Map<String,Object> map) {
        Page<InviteBid> page = new PageFactory<InviteBid>().defaultPage();
        List<InviteBid> list = this.inviteBidService.reportlist(map, page);
        page.setRecords(list);
        return super.packForBT(page);
    }

    /**
     * 跳转到月度统计
     */
    @RequestMapping("/monthReport")
    public String monthReport() {
        return PREFIX + "invite_month_report.html";
    }

    /**
     * 月度统计招标数
     */
    @RequestMapping(value = "/monthReportData")
    @ResponseBody
    public Object monthReportData(@RequestParam Map<String,Object> map) {
        return inviteBidService.monthReportData(map);
    }

    /**
     * 报价比对
     */
    @RequestMapping("/priceCompare")
    public String priceCompare(String tenderId, Model model) {
        model.addAttribute("tenderId", tenderId);
        return PREFIX + "priceCompare.html";
    }

    /**
     * 报价比对
     */
    @RequestMapping("/priceCompareDetail")
    public String priceCompareDetail(String tenderId, String priceName, Model model) {
        Map<String, Object> params = new HashMap<>();
        model.addAttribute("tenderId", tenderId);
        model.addAttribute("priceName", priceName);
        params.put("tenderId", tenderId);
        Map priceCompareData = inviteBidService.priceCompareData(params);
        List<String> productList = new ArrayList<>();
        List<String> companyList = (List<String>)priceCompareData.get("companyList");
        List<List<String>> prices = (List<List<String>>)priceCompareData.get("prices");
        Map<String, List<List<String>>> priceTable = new HashMap<>();

        for (List<String> price : prices) {
            productList.add(price.get(0));

            List<List<String>> list = new ArrayList<>();
            List<String> header = new ArrayList<>();
            header.add("参建单位");
            header.add("产品价格");
            list.add(header);
            for (int i = 1; i < companyList.size(); i++) {
                List<String> detail = new ArrayList<>();
                detail.add(companyList.get(i));
                detail.add(price.get(i));
                list.add(detail);
            }
            priceTable.put(price.get(0), list);
        }

        model.addAttribute("productList", productList);
        model.addAttribute("priceTable", JSONObject.fromObject(priceTable));

        return PREFIX + "priceCompareDetail.html";
    }

    /**
     * 报价数据接口
     * @param map
     * @return
     */
    @RequestMapping(value = "/priceCompareData")
    @ResponseBody
    public Object priceCompareData(@RequestParam Map<String,Object> map) {
        return inviteBidService.priceCompareData(map);
    }

    /**
     * 导出
     *
     * @param res
     * @param req
     */
    @RequestMapping(value = "/priceCompare/download", method = RequestMethod.GET)
    @ResponseBody
    public void downloadFile(HttpServletResponse res, HttpServletRequest req, @RequestParam Map<String, Object> params) {
        String tenderId = params.get("tenderId").toString();
        TenderResultBean lt = tenderingService.getById(tenderId);

        Map priceCompareData = inviteBidService.priceCompareData(params);
        List<String> companyList = (List<String>)priceCompareData.get("companyList");
        List<List<String>> prices = (List<List<String>>)priceCompareData.get("prices");
        List<List<String>> list = new ArrayList<>();

        for (List<String> price : prices) {
            List<String> l = new ArrayList<>();
            l.add(price.get(0));
            list.add(l);
            List<String> l2 = new ArrayList<>();
            l2.add("参建单位");
            l2.add("产品价格");
            list.add(l2);
            for (int i = 1; i < companyList.size(); i++) {
                List<String> detail = new ArrayList<>();
                detail.add(companyList.get(i));
                detail.add(price.get(i));
                list.add(detail);
            }
            List<String> l3 = new ArrayList<>();
            l3.add("");
            list.add(l3);
        }

        //构建下载文件时的文件名
        String fileName = lt.getFileName() + "报价对比";
        boolean isMSIE = ServletsUtils.isMSBrowser(req);
//        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            if (isMSIE) {
                //IE浏览器的乱码问题解决
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                //万能乱码问题解决
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }
            res.setContentType("application/vnd.ms-excel");
            res.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\".xlsx");
            os = res.getOutputStream();
            ExcelUtils.getInstance().exportObjects2Excel(list, os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            if (bis != null) {
//                try {
//                    bis.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }
}
