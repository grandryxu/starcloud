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
import com.xywg.admin.modular.bad.model.WorkerGoodRecords;
import com.xywg.admin.modular.bad.model.WorkerGoodRecordsVO;
import com.xywg.admin.modular.bad.service.IWorkerBadRecordsService;
import com.xywg.admin.modular.bad.service.IWorkerGoodRecordsService;
import com.xywg.admin.modular.bad.warpper.WorkerGoodRecordsWarpper;
import com.xywg.admin.modular.system.service.IDictService;

/**
 * 工人奖励记录控制器
 *
 * @author shily
 * @Date 2018-06-05 08:50:17
 */
@Controller
@RequestMapping("/workerGoodRecords")
public class WorkerGoodRecordsController extends BaseController {

    private String PREFIX = "/bad/workerGoodRecords/";

    @Autowired
    private IWorkerGoodRecordsService workerGoodRecordsService;
    
    @Autowired
    private IWorkerBadRecordsService workerBadRecordsService;

    @Autowired
    private IDictService dictService;

    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;
    
    /**
     * 跳转到工人奖励记录首页
     */
    @RequestMapping("")
    public String index(Model model) {
    	model.addAttribute("dictGoodRecordLevelType", dictService.selectByName("奖励级别"));
        return PREFIX + "workerGoodRecords.html";
    }

    /**
     * 跳转到添加工人奖励记录
     */
    @RequestMapping("/workerGoodRecords_add")
    public String workerGoodRecordsAdd(Model model) {
    	model.addAttribute("dictIdCardType", dictService.selectByName("人员证件类型"));
    	model.addAttribute("dictGoodRecordLevelType", dictService.selectByName("奖励级别"));
    	model.addAttribute("dictGoodRecordTypeCode", dictService.selectByName("工人奖励类别"));
    	model.addAttribute("workers",workerBadRecordsService.getWorkers());
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        return PREFIX + "workerGoodRecords_add.html";
    }

    /**
     * 跳转到修改工人奖励记录
     */
    @RequestMapping("/workerGoodRecords_update/{workerGoodRecordsId}")
    public String workerGoodRecordsUpdate(@PathVariable Integer workerGoodRecordsId, Model model) {
        WorkerGoodRecordsVO workerGoodRecords = workerGoodRecordsService.selectWorkerGoodRecordsById(workerGoodRecordsId);
        model.addAttribute("dictIdCardType", dictService.selectByName("人员证件类型"));
    	model.addAttribute("dictGoodRecordLevelType", dictService.selectByName("奖励级别"));
    	model.addAttribute("dictGoodRecordTypeCode", dictService.selectByName("工人奖励类别"));
    	model.addAttribute("workers",workerBadRecordsService.getWorkers());
        model.addAttribute("item",workerGoodRecords);
        LogObjectHolder.me().set(workerGoodRecords);
        return PREFIX + "workerGoodRecords_edit.html";
    }
    
    /**
     * 查看详情
     */
    @RequestMapping("/workerGoodRecords_view/{workerGoodRecordsId}")
    public String workerGoodRecordsView(@PathVariable Integer workerGoodRecordsId, Model model) {
    	WorkerGoodRecordsVO workerGoodRecords = workerGoodRecordsService.selectWorkerGoodRecordsById(workerGoodRecordsId);
    	model.addAttribute("dictIdCardType", dictService.selectByName("人员证件类型"));
     	model.addAttribute("dictGoodRecordLevelType", dictService.selectByName("奖励级别"));
     	model.addAttribute("dictGoodRecordTypeCode", dictService.selectByName("工人奖励类别"));
     	model.addAttribute("workers",workerBadRecordsService.getWorkers());
        model.addAttribute("item", workerGoodRecords);
        LogObjectHolder.me().set(workerGoodRecords);
        return PREFIX + "workerGoodRecords_info.html";
    }

    /**
     * 获取工人奖励记录列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map) {
    	 Page<WorkerGoodRecords> page = new PageFactory<WorkerGoodRecords>().defaultPage();
         List<Map<String, Object>> workerGoodRecords = workerGoodRecordsService.selectWorkerGoodRecords(page, map);
         page.setRecords((List<WorkerGoodRecords>) new WorkerGoodRecordsWarpper(workerGoodRecords).warp());
         return super.packForBT(page);
    }

    /**
     * 获取工人奖励记录列表
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getListByIdCard")
    @ResponseBody
    public Object getListByIdCard(@RequestParam Map<String, Object> map) {
        Page<WorkerGoodRecords> page = new PageFactory<WorkerGoodRecords>().defaultPage();
        List<Map<String, Object>> workerGoodRecords = workerGoodRecordsService.getListByIdCard(page, map);
        page.setRecords((List<WorkerGoodRecords>) new WorkerGoodRecordsWarpper(workerGoodRecords).warp());
        return super.packForBT(page);
    }

    /**
     * 新增工人奖励记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(WorkerGoodRecords workerGoodRecords) {
        workerGoodRecordsService.insert(workerGoodRecords);
        return SUCCESS_TIP;
    }

    /**
     * 删除工人奖励记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long workerGoodRecordsId) {
        workerGoodRecordsService.deleteById(workerGoodRecordsId);
        return SUCCESS_TIP;
    }

    /**
     * 修改工人奖励记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(WorkerGoodRecords workerGoodRecords) {
        workerGoodRecordsService.updateById(workerGoodRecords);
        return SUCCESS_TIP;
    }

    /**
     * 工人奖励记录详情
     */
    @RequestMapping(value = "/detail/{workerGoodRecordsId}")
    @ResponseBody
    public Object detail(@PathVariable("workerGoodRecordsId") Integer workerGoodRecordsId) {
        return workerGoodRecordsService.selectById(workerGoodRecordsId);
    }
    
    /**
     * 班组名称下拉框onchange事件
     */
    @RequestMapping(value = "/teamDetail/{id}")
    @ResponseBody
    public Object teamDetail(@PathVariable("id") Integer id) {
        return workerGoodRecordsService.selectTeamInfoById(id);
    }
}
