package com.xywg.admin.modular.bad.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.bad.model.WorkerBadRecords;
import com.xywg.admin.modular.bad.model.WorkerBadRecordsVO;
import com.xywg.admin.modular.bad.service.IWorkerBadRecordsService;
import com.xywg.admin.modular.bad.warpper.WorkerBadRecordsWarpper;
import com.xywg.admin.modular.system.service.IDictService;

/**
 * 工人不良记录控制器
 *
 * @author shily
 * @Date 2018-05-30 10:40:46
 */
@Controller
@RequestMapping("/workerBadRecords")
public class WorkerBadRecordsController extends BaseController {

    private String PREFIX = "/bad/workerBadRecords/";

    @Autowired
    private IWorkerBadRecordsService workerBadRecordsService;
    
    @Autowired
    private IDictService dictService;

    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;

    /**
     * 跳转到工人不良记录首页
     */
    @RequestMapping("")
    public String index(Model model) {
    	 model.addAttribute("dictBadRecordLevelType", dictService.selectByName("事件级别"));
        return PREFIX + "workerBadRecords.html";
    }
    
    /**
     * 跳转到工人不良记录审核首页
     */
    @RequestMapping("/reviewWorkerBadRecords")
    public String indexReviewWorker(Model model) {
    	 model.addAttribute("dictBadRecordLevelType", dictService.selectByName("事件级别"));
        return "/bad/reviewBadRecords/reviewWorkerBadRecords.html";
    }

    /**
     * 跳转到添加工人不良记录
     */
    @RequestMapping("/workerBadRecords_add")
    public String workerBadRecordsAdd(Model model) {
    	model.addAttribute("dictIdCardType", dictService.selectByName("人员证件类型"));
    	model.addAttribute("dictBadRecordLevelType", dictService.selectByName("事件级别"));
    	model.addAttribute("dictBadRecordTypeCode", dictService.selectByName("不良记录类别"));
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        return PREFIX + "workerBadRecords_add.html";
    }

    /**
     * 跳转到修改工人不良记录
     */
    @RequestMapping("/workerBadRecords_update/{workerBadRecordsId}")
    public String workerBadRecordsUpdate(@PathVariable Integer workerBadRecordsId, Model model) {
        WorkerBadRecordsVO workerBadRecords = workerBadRecordsService.selectWorkerBadRecordsById(workerBadRecordsId);
        model.addAttribute("dictIdCardType", dictService.selectByName("人员证件类型"));
    	model.addAttribute("dictBadRecordLevelType", dictService.selectByName("事件级别"));
    	model.addAttribute("dictBadRecordTypeCode", dictService.selectByName("不良记录类别"));
    	model.addAttribute("workers",workerBadRecordsService.getWorkers());
        model.addAttribute("item",workerBadRecords);
        LogObjectHolder.me().set(workerBadRecords);
        return PREFIX + "workerBadRecords_edit.html";
    }
    
    /**
     * 查看详情
     */
    @RequestMapping("/workerBadRecords_view/{workerBadRecordsId}")
    public String workerMasterView(@PathVariable Integer workerBadRecordsId, Model model) {
    	WorkerBadRecordsVO workerBadRecords = workerBadRecordsService.selectWorkerBadRecordsById(workerBadRecordsId);
    	model.addAttribute("dictIdCardType", dictService.selectByName("人员证件类型"));
     	model.addAttribute("dictBadRecordLevelType", dictService.selectByName("事件级别"));
     	model.addAttribute("dictBadRecordTypeCode", dictService.selectByName("不良记录类别"));
     	model.addAttribute("workers",workerBadRecordsService.getWorkers());
        model.addAttribute("item", workerBadRecords);
        LogObjectHolder.me().set(workerBadRecords);
        return PREFIX + "workerBadRecords_info.html";
    }

    /**
     * 获取工人不良记录列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map) {
    	 Page<WorkerBadRecords> page = new PageFactory<WorkerBadRecords>().defaultPage();
         List<Map<String, Object>> workerBadRecords = workerBadRecordsService.selectWorkerBadRecords(page, map);
         page.setRecords((List<WorkerBadRecords>) new WorkerBadRecordsWarpper(workerBadRecords).warp());
         return super.packForBT(page);
    }

    /**
     * 获取不良记录列表(tab页)
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getListByIdCard")
    @ResponseBody
    public Object getListByIdCard(@RequestParam Map<String, Object> map) {
        Page<WorkerBadRecords> page = new PageFactory<WorkerBadRecords>().defaultPage();
        List<Map<String, Object>> workerBadRecords = workerBadRecordsService.getListByIdCard(page, map);
        page.setRecords((List<WorkerBadRecords>) new WorkerBadRecordsWarpper(workerBadRecords).warp());
        return super.packForBT(page);
    }

    /**
     * 新增工人不良记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(WorkerBadRecords workerBadRecords) {
    	workerBadRecords.setIsAudit(1);
        workerBadRecordsService.insert(workerBadRecords);
        return SUCCESS_TIP;
    }

    /**
     * 删除工人不良记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long workerBadRecordsId) {
        workerBadRecordsService.deleteById(workerBadRecordsId);
        return SUCCESS_TIP;
    }

    /**
     * 审核通过
     */
    @RequestMapping(value = "/submitWorkerBadRecords")
    @ResponseBody
    public Object submitWorkerBadRecords(@RequestParam Long workerBadRecordsId) {
    	String auditName = ShiroKit.getUser().getName();
        workerBadRecordsService.submitWorkerBadRecords(workerBadRecordsId, auditName);
        return SUCCESS_TIP;
    }


    /**
     * 审核不通过
     */
    @RequestMapping(value = "/cancelWorkerBadRecords")
    @ResponseBody
    public Object cancelWorkerBadRecords(@RequestParam Long workerBadRecordsId) {
        workerBadRecordsService.cancelWorkerBadRecords(workerBadRecordsId);
        return SUCCESS_TIP;
    }
    
    /**
     * 修改工人不良记录
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
    @RequestMapping(value = "/detail/{workerBadRecordsId}")
    @ResponseBody
    public Object detail(@PathVariable("workerBadRecordsId") Integer workerBadRecordsId) {
        return workerBadRecordsService.selectById(workerBadRecordsId);
    }

    /**
     * 根据项目编号查询工人
     */
    @RequestMapping(value = "/selectWorkersByProjectCode")
    @ResponseBody
    public Object selectWorkersByProjectCode(@RequestParam String projectCode) {
        return workerBadRecordsService.selectWorkersByProjectCode(projectCode);
    }
    
    

}
