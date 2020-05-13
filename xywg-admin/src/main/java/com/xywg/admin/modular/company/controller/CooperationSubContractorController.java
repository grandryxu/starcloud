package com.xywg.admin.modular.company.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.excel.ExcelUtils;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.core.util.DateUtils;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.company.model.vo.SubContractorVo;
import com.xywg.admin.modular.company.service.ICooperationSubContractorService;
import com.xywg.admin.modular.company.service.ISubContractorService;
import com.xywg.admin.modular.company.utils.ServletsUtils;
import com.xywg.admin.modular.company.wrapper.SubContractorWarpper;
import com.xywg.admin.modular.system.service.IDictService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 企业信息控制器
 *
 * @author wangcw
 * @Date 2018-05-22 15:25:55
 */
@Controller
@RequestMapping("/cooperationSubContractor")
public class CooperationSubContractorController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(CooperationSubContractorController.class);
    private String PREFIX = "/company/cooperationSubContractor/";

    @Autowired
    private ICooperationSubContractorService cooperationSubContractorService;
    @Autowired
    private IDictService dictService;

    @Autowired
    private ISubContractorService subContractorService;


    /**
     * 跳转到企业信息首页
     */
    @RequestMapping("")
    public String index(Model model, @RequestParam Map<String, Object> map) {
        model.addAttribute("organizationType", dictService.selectByName("单位性质"));
        model.addAttribute("businessStatus", dictService.selectByName("经营状态"));
        model.addAttribute("status", dictService.selectByName("状态"));
        model.addAttribute("projectCode", map.get("projectCode"));
        model.addAttribute("projectName", map.get("projectName"));
        model.addAttribute("pageType", map.get("pageType"));
        return PREFIX + "cooperationSubContractor.html";
    }

    /**
     * 跳转到添加企业信息
     */
    @RequestMapping("/cooperationSubContractor_add")
    public String subContractorAdd(Model model) {
        model.addAttribute("subOrganizationType", dictService.selectByName("单位性质"));
        model.addAttribute("subBusinessStatus", dictService.selectByName("经营状态"));
        model.addAttribute("subRepresentativeIdcardType", dictService.selectByName("人员证件类型"));
        model.addAttribute("subCapitalCurrency", dictService.selectByName("币种"));
        model.addAttribute("subEconomicNature", dictService.selectByName("企业经济性质"));
        model.addAttribute("subEnterpriseMarketType", dictService.selectByName("企业市域类别"));
        return PREFIX + "cooperationSubContractor_add.html";
    }

    /**
     * 跳转到修改企业信息
     */
    @RequestMapping("/cooperationSubContractor_update/{subContractorId}")
    public String subContractorUpdate(@PathVariable Integer subContractorId, Model model) {
        SubContractor subContractor = cooperationSubContractorService.selectById(subContractorId);
        model.addAttribute("item", subContractor);
        model.addAttribute("organizationType", dictService.selectByName("单位性质"));
        model.addAttribute("businessStatus", dictService.selectByName("经营状态"));
        model.addAttribute("representativeIdcardType", dictService.selectByName("人员证件类型"));
        model.addAttribute("economicNature", dictService.selectByName("企业经济性质"));
        model.addAttribute("enterpriseMarketType", dictService.selectByName("企业市域类别"));
        LogObjectHolder.me().set(subContractor);
        return PREFIX + "cooperationSubContractor_edit.html";
    }

    /**
     * 跳转到双击查看页面
     */
    @RequestMapping("/cooperationSubContractor_view/{subContractorId}")
    public String subContractorView(@PathVariable Integer subContractorId, Model model) {
        SubContractor subContractor = cooperationSubContractorService.selectById(subContractorId);
        model.addAttribute("item", subContractor);
        model.addAttribute("organizationType", dictService.selectByName("单位性质"));
        model.addAttribute("businessStatus", dictService.selectByName("经营状态"));
        model.addAttribute("representativeIdcardType", dictService.selectByName("人员证件类型"));
        model.addAttribute("economicNature", dictService.selectByName("企业经济性质"));
        model.addAttribute("enterpriseMarketType", dictService.selectByName("企业市域类别"));
        LogObjectHolder.me().set(subContractor);
        return PREFIX + "cooperationSubContractor_info.html";
    }

    /**
     * 获取参建企业信息列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map) {
        Page<SubContractor> page = new PageFactory<SubContractor>().defaultPage();
        List<Map<String, Object>> subCons = cooperationSubContractorService.selectList(page, map);
        page.setRecords((List<SubContractor>) new SubContractorWarpper(subCons).warp());
        return super.packForBT(page);
    }

    /**
     * 新增企业信息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(SubContractor subContractor) {

        subContractor.setCompanyName(subContractor.getCompanyName().replace("& #40;", "(").replace("& #41;", ")"));
        //cooperationSubContractorService.insert(subContractor);
        subContractorService.insert(subContractor);
        return SUCCESS_TIP;
    }

    /**
     * 新增企业参建单位关系
     */
    @RequestMapping(value = "/addBussSubContractorConstruction")
    @ResponseBody
    public Object addBussSubContractorConstruction(String constructionCode) {
        cooperationSubContractorService.addBussSubContractorConstruction(constructionCode);
        return SUCCESS_TIP;
    }

    /**
     * 删除企业信息
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long subContractorId) {
        cooperationSubContractorService.deleteById(subContractorId);
        return SUCCESS_TIP;
    }

    /**
     * 切换状态
     */
    @RequestMapping("/changeState")
    @ResponseBody
    public Object changeState(@RequestParam Map<String, Object> map) {
        cooperationSubContractorService.changeState(map);
        return SUCCESS_TIP;
    }

    /**
     * 修改企业信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SubContractor subContractor) {
        cooperationSubContractorService.updateById(subContractor);
        return SUCCESS_TIP;
    }

    /**
     * 企业信息详情
     */
    @RequestMapping(value = "/detail/{subContractorId}")
    @ResponseBody
    public Object detail(@PathVariable("subContractorId") Integer subContractorId) {
        return cooperationSubContractorService.selectById(subContractorId);
    }

    /**
     * 查询所有承包商
     */
    @RequestMapping(value = "/selectGeneralContractors/{generalContractorName}")
    @ResponseBody
    public Object selectGeneralContractors(@PathVariable("generalContractorName") String generalContractorName) {
        return cooperationSubContractorService.selectGeneralContractors(generalContractorName);
    }

    /**
     * 获取登陆集团的所有的参建公司 排除该项目已有的参建公司
     */
    @RequestMapping(value = "/getList")
    @ResponseBody
    public Object getList(@RequestParam String projectCode) {
        return cooperationSubContractorService.getList(projectCode);
    }

    /**
     * 导出
     *
     * @param res
     * @param req
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public void downloadFile(HttpServletResponse res, HttpServletRequest req, @RequestParam Map<String, Object> params) {
        List<Map<String, Object>> subs = this.cooperationSubContractorService.selectList(params);
        //构建下载文件时的文件名
        String fileName = "参建公司一览" + DateUtils.getDate("yyyyMMddHHmmss");
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
            res.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"+.xlsx");
            os = res.getOutputStream();
            ExcelUtils.getInstance().exportObjects2Excel(subs, SubContractorVo.class, true, os);
        } catch (Exception e) {
            log.error(e.getMessage());
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

    /**
     * excel导入
     */
    @RequestMapping(method = RequestMethod.POST, path = "/excelUpload")
    @ResponseBody
    public String excelUpload(@RequestPart("file") MultipartFile excelFile, HttpServletRequest request) throws Exception {
        return this.cooperationSubContractorService.excelUpload(excelFile, request);
    }

    /*
    *  重置企业账号
    *
    */
    @RequestMapping(value = "/updateContractorPassword")
    @ResponseBody
    public Object updateContractorPassword(@RequestParam Map<String, Object> map) {
        cooperationSubContractorService.selectCompanyById(map.get("id"));
        return SUCCESS_TIP;
    }
}
