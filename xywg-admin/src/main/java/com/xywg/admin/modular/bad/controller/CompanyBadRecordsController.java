package com.xywg.admin.modular.bad.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.flow.service.ProcessService;
import com.xywg.admin.modular.bad.model.WorkerBadRecords;
import com.xywg.admin.modular.bad.model.WorkerBadRecordsVO;
import com.xywg.admin.modular.bad.service.IWorkerBadRecordsService;
import com.xywg.admin.modular.bad.service.IWorkerBlackListService;
import com.xywg.admin.modular.bad.warpper.WorkerBadRecordsWarpper;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.system.service.AccountProjectService;
import com.xywg.admin.modular.system.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 企业不良记录控制器
 *
 * @author shily
 * @Date 2018-05-30 10:40:46
 */
@Controller
@RequestMapping("/companyBadRecords")
public class CompanyBadRecordsController extends BaseController {

    private String PREFIX = "/bad/companyBadRecords/";

    @Autowired
    private IWorkerBadRecordsService workerBadRecordsService;

    @Autowired
    private IWorkerBlackListService workerBlackListService;

    @Autowired
    private IDictService dictService;

    @Autowired
    private AccountProjectService accountProjectService;

    @Autowired
    private ProcessService processService;

    /**
     * 跳转到不良记录首页
     */
    @RequestMapping("")
    public String index(Model model) {
    	 model.addAttribute("companys",workerBlackListService.getCompanys());
    	 model.addAttribute("dictBadRecordLevelType", dictService.selectByName("事件级别"));
        model.addAttribute("switchType",accountProjectService.getSwitchType());
        return PREFIX + "companyBadRecords.html";
    }

    /**
     * 跳转到企业不良记录审核首页
     */
    @RequestMapping("/reviewCompanyBadRecords")
    public String indexReviewCompany(Model model) {
    	 model.addAttribute("dictBadRecordLevelType", dictService.selectByName("事件级别"));
        return "/bad/reviewBadRecords/reviewCompanyBadRecords.html";
    }


    /**
     * 跳转到添加不良记录
     */
    @RequestMapping("/companyBadRecords_add")
    public String companyBadRecordsAdd(Model model) {
    	model.addAttribute("projects",workerBlackListService.getProjects());
    	model.addAttribute("dictBadRecordLevelType", dictService.selectByName("事件级别"));
    	model.addAttribute("dictBadRecordTypeCode", dictService.selectByName("不良记录类别"));
        return PREFIX + "companyBadRecords_add.html";
    }

    /**
     * 跳转到修改不良记录
     */
    @RequestMapping("/companyBadRecords_update/{companyBadRecordsId}")
    public String companyBadRecordsUpdate(@PathVariable Integer companyBadRecordsId, Model model) {
        WorkerBadRecordsVO teamBadRecords = workerBadRecordsService.selectCompanyBadRecordsById(companyBadRecordsId);
    	model.addAttribute("dictBadRecordLevelType", dictService.selectByName("事件级别"));
    	model.addAttribute("dictBadRecordTypeCode", dictService.selectByName("不良记录类别"));
        model.addAttribute("item",teamBadRecords);
        LogObjectHolder.me().set(teamBadRecords);
        return PREFIX + "companyBadRecords_edit.html";
    }


    /**
     * 跳转到详情页面
     */
    @RequestMapping("/companyBadRecords_view/{companyBadRecordsId}")
    public String companyBadRecordsView(@PathVariable Integer companyBadRecordsId, Model model) {
    	WorkerBadRecordsVO teamBadRecords = workerBadRecordsService.selectCompanyBadRecordsById(companyBadRecordsId);
    	model.addAttribute("dictBadRecordLevelType", dictService.selectByName("事件级别"));
    	model.addAttribute("dictBadRecordTypeCode", dictService.selectByName("不良记录类别"));
        model.addAttribute("item", teamBadRecords);
        LogObjectHolder.me().set(teamBadRecords);
        return PREFIX + "companyBadRecords_info.html";
    }

    /**
     * 获取不良记录列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map) {
    	 Page<WorkerBadRecords> page = new PageFactory<WorkerBadRecords>().defaultPage();
         List<Map<String, Object>> companyBadRecords = workerBadRecordsService.selectCompanyBadRecords(page, map);
         page.setRecords((List<WorkerBadRecords>) new WorkerBadRecordsWarpper(companyBadRecords).warp());
         return super.packForBT(page);
    }

    /**
     * 获取某公司的不良记录列表
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getListBySubContractor")
    @ResponseBody
    public Object getListBySubContractor(@RequestParam Map<String, Object> map) {
        Page<WorkerBadRecords> page = new PageFactory<WorkerBadRecords>().defaultPage();
        List<Map<String, Object>> companyBadRecords = workerBadRecordsService.getListBySubContractor(page, map);
        page.setRecords((List<WorkerBadRecords>) new WorkerBadRecordsWarpper(companyBadRecords).warp());
        return super.packForBT(page);
    }

    /**
     * 新增不良记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(WorkerBadRecords workerBadRecords) {
    	workerBadRecords.setIsAudit(1);
        workerBadRecordsService.insert(workerBadRecords);
        return SUCCESS_TIP;
    }

    /**
     * 删除不良记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long companyBadRecordsId) {
        workerBadRecordsService.deleteById(companyBadRecordsId);
        return SUCCESS_TIP;
    }

    /**
     * 修改不良记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(WorkerBadRecords workerBadRecords) {
        workerBadRecordsService.updateById(workerBadRecords);
        return SUCCESS_TIP;
    }

    /**
     * 不良记录详情
     */
    @RequestMapping(value = "/detail/{companyBadRecordsId}")
    @ResponseBody
    public Object detail(@PathVariable("companyBadRecordsId") Long companyBadRecordsId) {
        return workerBadRecordsService.selectById(companyBadRecordsId);
    }

    /**
     * 根据组织结构代码获取项目列表
     */
    @RequestMapping(value = "/getProjectList/{organizationCode}")
    @ResponseBody
    public Object getProjectList(@PathVariable("organizationCode") String organizationCode) {
    	List<ProjectMaster> v = workerBadRecordsService.getProjectList(organizationCode);
        return v;
    }

    /**
     * 根据项目编号获取所有的参建公司 不包括登陆公司
     */
    @RequestMapping("/getCompanys/{projectCode}")
    @ResponseBody
    public Object getCompanys(@PathVariable String projectCode){
        return this.workerBadRecordsService.getCompanys(projectCode);
    }
    
    
    /**
     * 根据项目编号获取所有的参建公司 不包括登陆公司
     */
    @RequestMapping("/getCompanysAll")
    @ResponseBody
    public Object getCompanysAll(){
        return this.workerBadRecordsService.getCompanysAll();
    }
}
