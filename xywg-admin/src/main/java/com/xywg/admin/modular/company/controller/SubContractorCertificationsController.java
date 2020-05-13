package com.xywg.admin.modular.company.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.company.model.SubContractorCertifications;
import com.xywg.admin.modular.company.service.ISubContractorCertificationsService;
import com.xywg.admin.modular.company.service.ISubContractorService;
import com.xywg.admin.modular.company.wrapper.SubContractorCertificationWarpper;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.system.service.IDictService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 企业资质控制器
 *
 * @author wangcw
 * @Date 2018-05-27 10:08:01
 */
@Controller
@RequestMapping("/subContractorCertifications")
public class SubContractorCertificationsController extends BaseController {

    private String PREFIX = "/company/subContractor/";
    @Autowired
    private IDeptService deptService;
    @Autowired
    private ISubContractorCertificationsService subContractorCertificationsService;
    @Autowired
    private ISubContractorService subContractorService;
    @Autowired
    private IDictService dictService;

    /**
     * 跳转到企业资质首页
     */
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("certificationType",dictService.selectByName("证书类型"));
        model.addAttribute("certificationStatus",dictService.selectByName("企业资质证书状态"));
        return PREFIX + "subContractorCertifications.html";
    }
    /**
     * 跳转到企业资质首页
     */
    @RequestMapping("/index")
    public String list(Model model) {
        model.addAttribute("certificationType",dictService.selectByName("证书类型"));
        model.addAttribute("certificationStatus",dictService.selectByName("企业资质证书状态"));
        model.addAttribute("qualificationStatus",dictService.selectByName("企业资质状态"));
        return "/company/subContractorCertifications/subContractorCertifications.html";
    }

    /**
     * 跳转到添加企业资质(tab页)
     */
    @RequestMapping("/subContractorCertifications_add/{organizationCode}")
    public String subContractorCertificationsAdd(@PathVariable String organizationCode,Model model) {
        model.addAttribute("certificationType",dictService.selectByName("证书类型"));
        model.addAttribute("qualificationStatus",dictService.selectByName("企业资质状态"));
        model.addAttribute("certificationStatus",dictService.selectByName("企业资质证书状态"));
        model.addAttribute("item",organizationCode);
        LogObjectHolder.me().set(organizationCode);
        return PREFIX + "subContractorCertifications_add.html";
    }
    /**
     * 跳转到添加企业资质
     */
    @RequestMapping("/subContractorCertifications_addSubContractorCertifications")
    public String addSubContractorCertifications(Model model) {
        String account = ShiroKit.getUser().getAccount();
        model.addAttribute("companys", deptService.getList(account));
        model.addAttribute("certificationType",dictService.selectByName("证书类型"));
        model.addAttribute("qualificationStatus",dictService.selectByName("企业资质状态"));
        model.addAttribute("certificationStatus",dictService.selectByName("企业资质证书状态"));
        return "/company/subContractorCertifications/subContractorCertifications_add.html";
    }

    /**
     * 跳转到修改企业资质
     */
    @RequestMapping("/subContractorCertifications_update/{subContractorCertificationsId}")
    public String subContractorCertificationsUpdate(@PathVariable Integer subContractorCertificationsId, Model model) {
        String account = ShiroKit.getUser().getAccount();
        model.addAttribute("companys", deptService.getList(account));
        model.addAttribute("certificationType",dictService.selectByName("证书类型"));
        model.addAttribute("qualificationStatus",dictService.selectByName("企业资质状态"));
        model.addAttribute("certificationStatus",dictService.selectByName("企业资质证书状态"));
        SubContractorCertifications subContractorCertifications = subContractorCertificationsService.selectById(subContractorCertificationsId);
        model.addAttribute("item",subContractorCertifications);
        LogObjectHolder.me().set(subContractorCertifications);
        return  "/company/subContractorCertifications/subContractorCertifications_edit.html";
    }

    /**
     * 跳转到修改企业资质
     */
    @RequestMapping("/subContractorCertifications_info/{subContractorCertificationsId}")
    public String subContractorCertificationsDetail(@PathVariable Integer subContractorCertificationsId, Model model) {
        String account = ShiroKit.getUser().getAccount();
        model.addAttribute("companys", deptService.getList(account));
        model.addAttribute("certificationType",dictService.selectByName("证书类型"));
        model.addAttribute("qualificationStatus",dictService.selectByName("企业资质状态"));
        model.addAttribute("certificationStatus",dictService.selectByName("企业资质证书状态"));
        SubContractorCertifications subContractorCertifications = subContractorCertificationsService.selectById(subContractorCertificationsId);
        model.addAttribute("item",subContractorCertifications);
        LogObjectHolder.me().set(subContractorCertifications);
        return  "/company/subContractorCertifications/subContractorCertifications_info.html";
    }

    /**
     * 获取企业资质列表（tab）
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String,Object> map,Model model) {
        model.addAttribute("certificationType",dictService.selectByName("证书类型"));
        model.addAttribute("certificationStatus",dictService.selectByName("企业资质证书状态"));
        model.addAttribute("qualificationStatus",dictService.selectByName("企业资质状态"));
        Page<Map<String,Object>> page = new PageFactory<Map<String,Object>>().defaultPage();
        List<Map<String,Object>> list=subContractorCertificationsService.getSubContractorCertifications(map,page);
        page.setRecords((List<Map<String, Object>>) new SubContractorCertificationWarpper(list).warp());
        return super.packForBT(page);
    }


    /**
     * 获取企业资质列表
     */
    @RequestMapping(value = "/SubContractorCertificationsList")
    @ResponseBody
    public Object SubContractorCertificationsList(@RequestParam Map<String,Object> map,Model model) {
        model.addAttribute("certificationType",dictService.selectByName("证书类型"));
        model.addAttribute("certificationStatus",dictService.selectByName("企业资质证书状态"));
        model.addAttribute("qualificationStatus",dictService.selectByName("企业资质状态"));
        Page<Map<String,Object>> page = new PageFactory<Map<String,Object>>().defaultPage();
        List<Map<String,Object>> list=subContractorCertificationsService.getSubContractorCertificationsList(map,page);
        page.setRecords((List<Map<String, Object>>) new SubContractorCertificationWarpper(list).warp());
        return super.packForBT(page);
    }
    /**
     *
     * @param request
     * @param binder
     */
    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }


    /**
     * 新增企业资质
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(SubContractorCertifications subContractorCertifications) {
        subContractorCertificationsService.insert(subContractorCertifications);
        return SUCCESS_TIP;
    }

    /**
     * 删除企业资质(tab页)
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long subContractorCertificationsId) {
        subContractorCertificationsService.del(subContractorCertificationsId);
        return SUCCESS_TIP;
    }
    /**
     * 删除企业资质
     */
    @RequestMapping(value = "/deleteSubContractorCertifications")
    @ResponseBody
    public Object deleteSubContractorCertifications(@RequestParam Map<String,Object> map) {
        subContractorCertificationsService.deleteSubContractorCertifications(map);
        return SUCCESS_TIP;
    }

    /**
     * 修改企业资质
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SubContractorCertifications subContractorCertifications) {
        subContractorCertificationsService.updateById(subContractorCertifications);
        return SUCCESS_TIP;
    }

    /**
     * 企业资质详情
     */
    @RequestMapping(value = "/detail/{subContractorCertificationsId}")
    @ResponseBody
    public Object detail(@PathVariable("subContractorCertificationsId") Integer subContractorCertificationsId) {
        return subContractorCertificationsService.selectById(subContractorCertificationsId);
    }
}
