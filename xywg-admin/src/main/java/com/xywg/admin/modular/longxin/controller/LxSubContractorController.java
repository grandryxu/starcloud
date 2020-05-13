package com.xywg.admin.modular.longxin.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.flow.entity.Process;
import com.xywg.admin.flow.service.ProcessService;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.company.service.ISubContractorService;
import com.xywg.admin.modular.company.wrapper.SubContractorWarpper;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.model.ProjectMasterHis;
import com.xywg.admin.modular.smz.model.IfaLabor;
import com.xywg.admin.modular.smz.service.IfaLaborService;
import com.xywg.admin.modular.system.service.IDictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 企业信息控制器
 *
 * @author wangcw
 * @Date 2018-05-22 15:25:55
 */
@Controller
@RequestMapping("/lxSubContractor")
public class LxSubContractorController extends BaseController {

    private String PREFIX = "/longxin/lxSubContractor/";

    @Autowired
    @Qualifier("lxSubContractorServiceImpl")
    private ISubContractorService lxSubContractorServiceImpl;
    @Autowired
    private IDictService dictService;
    @Autowired
    private IfaLaborService ifaLaborService;
    @Autowired
    private ProcessService processService;
    /**
     * 跳转到企业信息首页
     */
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("organizationType", dictService.selectByName("单位性质"));
        model.addAttribute("businessStatus", dictService.selectByName("经营状态"));
        model.addAttribute("status",dictService.selectByName("状态"));
        return PREFIX + "lxSubContractor.html";
    }

    /**
     * 跳转到添加企业信息
     */
    @RequestMapping("/lxSubContractor_add")
    public String subContractorAdd(Model model) {
        model.addAttribute("subOrganizationType",dictService.selectByName("单位性质"));
        model.addAttribute("subBusinessStatus",dictService.selectByName("经营状态"));
        model.addAttribute("subRepresentativeIdcardType",dictService.selectByName("人员证件类型"));
        model.addAttribute("subCapitalCurrency",dictService.selectByName("币种"));
        model.addAttribute("subEconomicNature",dictService.selectByName("企业经济性质"));
        model.addAttribute("subEnterpriseMarketType",dictService.selectByName("企业市域类别"));
        
        return PREFIX + "lxSubContractor_add.html";
    }

    /**
     * 弹出搜索企业弹框
     * @auth wangcw
     */
    @RequestMapping("/subContractor_add_search")
    public String subContractorAddSearch() {
        return PREFIX + "subContractor_add_search.html";
    }

    /**
     * 跳转到修改企业信息
     */
    @RequestMapping("/lxSubContractor_update/{subContractorId}")
    public String subContractorUpdate(@PathVariable Integer subContractorId, Model model) {
        SubContractor subContractor = lxSubContractorServiceImpl.selectById(subContractorId);
        model.addAttribute("item", subContractor);
        model.addAttribute("organizationType", dictService.selectByName("单位性质"));
        model.addAttribute("businessStatus", dictService.selectByName("经营状态"));
        model.addAttribute("representativeIdcardType",dictService.selectByName("人员证件类型"));
        model.addAttribute("capitalCurrency",dictService.selectByName("币种"));
        model.addAttribute("economicNature",dictService.selectByName("企业经济性质"));
        model.addAttribute("enterpriseMarketType",dictService.selectByName("企业市域类别"));
        LogObjectHolder.me().set(subContractor);
        return PREFIX + "lxSubContractor_edit.html";
    }





    /**
     * 跳转到双击查看页面
     */
    @RequestMapping("/lxSubContractor_view/{subContractorId}")
    public String subContractorView(@PathVariable Integer subContractorId, Model model) {
        Map<String,Object> subContractor=lxSubContractorServiceImpl.getOneById(subContractorId);
        /*SubContractor subContractor = subContractorService.selectById(subContractorId);*/
        List<ProjectMasterHis> pros = lxSubContractorServiceImpl.selectHistoryProsa(subContractorId);
        if (null== subContractor.get("businessMaterial")) {
            subContractor.put("businessMaterial","没有上传企业材料");
        }
        model.addAttribute("item", subContractor);
        model.addAttribute("organizationType", dictService.selectByName("单位性质"));
        model.addAttribute("businessStatus", dictService.selectByName("经营状态"));
        model.addAttribute("representativeIdcardType",dictService.selectByName("人员证件类型"));
        model.addAttribute("projectStatus",dictService.selectByName("状态"));
        model.addAttribute("contractorType",dictService.selectByName("参建类型"));
        model.addAttribute("certificationType",dictService.selectByName("证书类型"));
        model.addAttribute("certificationStatus",dictService.selectByName("企业资质证书状态"));
        model.addAttribute("capitalCurrency",dictService.selectByName("币种"));
        model.addAttribute("economicNature",dictService.selectByName("企业经济性质"));
        model.addAttribute("enterpriseMarketType",dictService.selectByName("企业市域类别"));
        model.addAttribute("pros", pros);
        
        LogObjectHolder.me().set(subContractor);
        return PREFIX + "lxSubContractor_info.html";
    }

    /**
     * 跳转到新增企业资质页面
     */
    @RequestMapping("/subContractor_addSubContractorCertifications/{organizationCode}")
    public String addSubContractorCertifications(@PathVariable String organizationCode,Model model){
        model.addAttribute("certificationType",dictService.selectByName("证书类型"));
        model.addAttribute("qualificationStatus",dictService.selectByName("企业资质状态"));
        model.addAttribute("certificationStatus",dictService.selectByName("企业资质证书状态"));
        model.addAttribute("item",organizationCode);
        return PREFIX+"subContractorCertifications_add.html";
    }

    /**
     * 获取企业信息列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String,Object> map) {
        Page<SubContractor> page = new PageFactory<SubContractor>().defaultPage();
        List<Map<String,Object>> subCons= lxSubContractorServiceImpl.selectList(page ,map);
        page.setRecords((List<SubContractor>) new SubContractorWarpper(subCons).warp());
        return super.packForBT(page);
    }

    /**
     *  得到省市区
     *  二级联动
     */
    @RequestMapping(value = "/getArea")
    @ResponseBody
    public Object getArea(@RequestParam String areaId) {
        Map<String,String> map=lxSubContractorServiceImpl.getArea(areaId);
        return map==null?"":map;
    }

    /**
     * 新增企业信息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(SubContractor subContractor) {
    	lxSubContractorServiceImpl.insert(subContractor);
        return subContractor.getOrganizationCode();
    }



    /**
     * 删除企业信息
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long subContractorId) {
    	lxSubContractorServiceImpl.deleteById(subContractorId);
        return SUCCESS_TIP;
    }

    /**
     * 切换状态
     */
    @RequestMapping("/changeState")
    @ResponseBody
    public Object changeState(@RequestParam Map<String,Object> map){
        map.put("id",Long.valueOf(map.get("id").toString()));
        lxSubContractorServiceImpl.changeState(map);

        return SUCCESS_TIP;
    }

    /**
     * 修改企业信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SubContractor subContractor) {
        if(subContractor.getIsSynchro()!=null&&subContractor.getIsSynchro() == 1) {
            IfaLabor ifaLabor = new IfaLabor("buss_sub_contractor", subContractor.getId());
            ifaLaborService.insert(ifaLabor);
        }
        subContractor.setCompanyName(subContractor.getCompanyName().replace("& #40;","(" ).replace("& #41;",")"));
        lxSubContractorServiceImpl.updateLxById(subContractor);
        return SUCCESS_TIP;
    }

    /**
     * 企业信息详情
     */
    @RequestMapping(value = "/detail/{subContractorId}")
    @ResponseBody
    public Object detail(@PathVariable("subContractorId") Integer subContractorId) {
        return lxSubContractorServiceImpl.selectById(subContractorId);
    }

    /**
     * 查询所有承包商
     */
    @RequestMapping(value = "/selectGeneralContractors/{generalContractorName}")
    @ResponseBody
    public Object selectGeneralContractors(@PathVariable("generalContractorName") String generalContractorName) {
        return lxSubContractorServiceImpl.selectGeneralContractors(generalContractorName);
    }

    /**
     * 获取当前用户获取该用户下所有企业列表
     */
    @RequestMapping(value = "/getListByCurrUser")
    @ResponseBody
    public Object getList(@RequestParam Map<String,Object> map) {
        return lxSubContractorServiceImpl.getList(map);
    }

    /**
     * 获取所有企业 除自己以及自己的参建公司
     */
    @RequestMapping(value = "/getAllSubContractor")
    @ResponseBody
    public Object getAllSubContractor(@RequestParam Map<String,Object> map) {
        return lxSubContractorServiceImpl.getAllSubContractor(map);
    }

    /**
     * 获取所有企业 除自己以及自己的参建公司 无父公司
     */
    @RequestMapping(value = "/getAllNoParentSubContractor")
    @ResponseBody
    public Object getAllNoParentSubContractor(@RequestParam Map<String,Object> map) {
        return lxSubContractorServiceImpl.getAllNoParentSubContractor(map);
    }

    /**
     * 模糊查询全国企业库中企业信息
     * @auth wangcw
     */
    @RequestMapping(value = "/getSubContractorByCondition")
    @ResponseBody
    public Object getAllSubContractor(@RequestParam("condition") String condition) {
        JSON.toJSON(lxSubContractorServiceImpl.getSubContractorByCondition(condition));
        String json = "abc";
        return lxSubContractorServiceImpl.getSubContractorByCondition(condition);
    }
    /**
     * 切换公司
     * @auth wangcw
     */
    @RequestMapping(value = "/selectCompany", method = RequestMethod.GET)
    public String selectCompany() {
        return PREFIX + "selectCompany.html";
    }

    /**
     * 删除公司参建公司关系
     * @auth wangcw
     */
    @ResponseBody
    @PostMapping(value = "/deleteConstruction")
    public Object deleteConstruction(@RequestParam String organizationCode ) {
        return this.lxSubContractorServiceImpl.deleteConstruction(organizationCode);
    }

}
