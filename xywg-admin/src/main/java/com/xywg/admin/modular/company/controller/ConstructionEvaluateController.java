package com.xywg.admin.modular.company.controller;

import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.modular.company.model.ConstructionEvaluate;
import com.xywg.admin.modular.company.service.IConstructionEvaluateService;
import com.xywg.admin.modular.company.service.ISubContractorService;
import com.xywg.admin.modular.system.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

/**
 * 企业评价控制器
 *
 * @author wangcw
 * @Date 2018-05-28 17:09:53
 */
@Controller
@RequestMapping("/constructionEvaluate")
public class ConstructionEvaluateController extends BaseController {

    private String PREFIX = "/company/constructionEvaluate/";

    @Autowired
    private IConstructionEvaluateService constructionEvaluateService;
    @Autowired
    private ISubContractorService subContractorService;
    @Autowired
    private IDictService dictService;

    /**
     * 跳转到企业评价首页
     */
    @RequestMapping("")
    public String index(@RequestParam Map map,Model model) {
        model.addAttribute("organizationType", dictService.selectByName("单位性质"));
        model.addAttribute("businessStatus", dictService.selectByName("经营状态"));
        model.addAttribute("representativeIdcardType",dictService.selectByName("人员证件类型"));
        model.addAttribute("projectStatus",dictService.selectByName("状态"));
        model.addAttribute("contractorType",dictService.selectByName("参建类型"));
        model.addAttribute("certificationType",dictService.selectByName("证书类型"));
        model.addAttribute("certificationStatus",dictService.selectByName("企业资质证书状态"));
        model.addAttribute("item",subContractorService.getSubContractorByOrganizationCode(map.get("organizationCode").toString()));
        //model.addAttribute("organizationCode",map.get("organizationCode").toString());
        model.addAttribute("projectCode",map.get("projectCode").toString());
        return PREFIX + "constructionEvaluate.html";
    }

    /**
     * 跳转到添加企业评价
     */
    @RequestMapping("/constructionEvaluate_add")
    public String constructionEvaluateAdd() {
        return PREFIX + "constructionEvaluate_add.html";
    }

    /**
     * 跳转到修改企业评价
     */
    @RequestMapping("/constructionEvaluate_update/{constructionEvaluateId}")
    public String constructionEvaluateUpdate(@PathVariable Integer constructionEvaluateId, Model model) {
        ConstructionEvaluate constructionEvaluate = constructionEvaluateService.selectById(constructionEvaluateId);
        model.addAttribute("item",constructionEvaluate);
        LogObjectHolder.me().set(constructionEvaluate);
        return PREFIX + "constructionEvaluate_edit.html";
    }

    /**
     * 获取企业评价列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String,Object> map) {
        return constructionEvaluateService.list(map);
    }

    /**
     * 新增企业评价
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ConstructionEvaluate constructionEvaluate) {
        constructionEvaluate.setIdCardType(" ");
        constructionEvaluate.setIdCardNumber(" ");
        constructionEvaluate.setCreateDate(new Date());
        constructionEvaluateService.insert(constructionEvaluate);
        return SUCCESS_TIP;
    }

    /**
     * 删除企业评价
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer constructionEvaluateId) {
        constructionEvaluateService.deleteById(constructionEvaluateId);
        return SUCCESS_TIP;
    }

    /**
     * 修改企业评价
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ConstructionEvaluate constructionEvaluate) {
        constructionEvaluateService.updateById(constructionEvaluate);
        return SUCCESS_TIP;
    }

    /**
     * 企业评价详情
     */
    @RequestMapping(value = "/detail/{constructionEvaluateId}")
    @ResponseBody
    public Object detail(@PathVariable("constructionEvaluateId") Integer constructionEvaluateId) {
        return constructionEvaluateService.selectById(constructionEvaluateId);
    }
}
