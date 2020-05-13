package com.xywg.admin.modular.longxin.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.longxin.model.LxCompanyEvaluate;
import com.xywg.admin.modular.longxin.model.LxEvaluate;
import com.xywg.admin.modular.longxin.service.LxCompanyEvaluateService;
import com.xywg.admin.modular.longxin.service.LxEvaluateService;
import com.xywg.admin.modular.projectSubContractor.service.IProjectSubContractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/lxEvaluate")
public class LxEvaluateController extends BaseController {

    private String PREFIX = "/longxin/evaluate/";


    @Autowired
    private LxEvaluateService lxEvaluateService;
    @Autowired
    private IProjectSubContractorService projectSubContractorService;

    @Autowired
    private LxCompanyEvaluateService lxCompanyEvaluateService;

    /**
     * 跳转到评价信息页面
     */
    @RequestMapping("")
    public String index(Model model) {
        return PREFIX + "evaluate.html";
    }

    /**
     * 获取评价信息列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object selectEvaluatelist(@RequestParam Map<String, Object> map) {
        Page<LxEvaluate> page = new PageFactory<LxEvaluate>().defaultPage();
        List<LxEvaluate> lxEvaluateList = lxEvaluateService.selectEvaluatelist(page,map);
        page.setRecords(lxEvaluateList);
        return super.packForBT(page);
    }

    /*
     * 删除评价信息
     * */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object deleteById(@RequestParam Long id) {

        lxEvaluateService.selectIdDelete(id);
        return SUCCESS_TIP;
    }

    /*跳转评价信息页面
     * */
    @RequestMapping("/lxEvaluate_add")
    public String evaluateAddModel(Model model) {
        return PREFIX + "lxEvaluate_add.html";
    }


    /*判断是否有此评价项*/
    @RequestMapping(value = "/selectItems")
    @ResponseBody
    public Object selectItems(@RequestParam Map<String, Object> map) {
        Long num = this.lxEvaluateService.selectItems(map.get("items"));
        return num;
    }
    /*
    * 判断是否有此评价项排除自己
    *
    * */
    @RequestMapping(value = "/selectItemsOutItem")
    @ResponseBody
    public Object selectItemsOutItem(@RequestParam Map<String, Object> map) {
      Long num = this.lxEvaluateService.selectItemsOutItem(map.get("items"));
        return num;
    }


    /*
     * 添加评价信息*/
    @RequestMapping("/add")
    @ResponseBody
    public Object evaluateAdd(LxEvaluate lxEvaluate) {
        String items = lxEvaluate.getItems();
        lxEvaluate.setCreateTime(new Date());
        lxEvaluateService.insert(lxEvaluate);
        return SUCCESS_TIP;
    }

    /*跳转到修改页面*/
    @RequestMapping("/update/{id}")
    public String evaluateAddModel(Model model,@PathVariable String id){
       LxEvaluate lxEvaluate= lxEvaluateService.selectId( Long.parseLong(id));
        model.addAttribute("id", lxEvaluate.getId());
        model.addAttribute("items", lxEvaluate.getItems());
        model.addAttribute("type", lxEvaluate.getType());
        model.addAttribute("max", lxEvaluate.getMax());
        return PREFIX + "lxEvaluate_edit.html";
    }

    /**
     * 修改评价信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(LxEvaluate lxEvaluate) {
        lxEvaluateService.updateById(lxEvaluate);
        return SUCCESS_TIP;
    }

    /**
     * 跳转到评价页面
     */
    @RequestMapping("/projectSubContractor_evaluation/{projectSubContractorId}")
    public String projectSubContractorEvaluation(Model model, @PathVariable Integer projectSubContractorId) {
        Map<String, Object> projectSubContractor = projectSubContractorService.getContractorById(projectSubContractorId);

        model.addAttribute("item", projectSubContractor);
        LogObjectHolder.me().set(projectSubContractor);
        return PREFIX + "projectSubContractor_evaluation.html";
    }


    /**
     * 根据分数查询等级
     */
    @RequestMapping(value = "/selectStar")
    @ResponseBody
    public Object selectStar(@RequestParam Integer score) {
        String star = lxEvaluateService.selectStar(score);
        return star;
    }


    /*公司评价信息入库*/
    @RequestMapping("/companyEvaluateAdd")
    @ResponseBody
    public Object companyEvaluateAdd(LxCompanyEvaluate lxCompanyEvaluate) {

        lxCompanyEvaluateService.insert(lxCompanyEvaluate);
        return SUCCESS_TIP;
    }


    /*评价分数*/
    @RequestMapping(value = "/updateScore")
    @ResponseBody
    public Object updateScore(@RequestParam Map<String, Object> map) {
        lxEvaluateService.updateScore(map.get("projectCode"),map.get("organizationCode"), map.get("score"));
        return  SUCCESS_TIP;
    }

    @RequestMapping(value = "/getScoreByCompanyAndProject")
    @ResponseBody
    public Object getScoreByCompanyAndProject(@RequestParam Map<String, Object> map) {
        Page<LxEvaluate> page = new PageFactory<LxEvaluate>().defaultPage();
        List<LxEvaluate>lxEvaluateList= lxEvaluateService.getScoreByCompanyAndProject(map.get("projectCode"),map.get("organizationCode"),page);
        page.setRecords(lxEvaluateList);
        return super.packForBT(page);
    }

}
