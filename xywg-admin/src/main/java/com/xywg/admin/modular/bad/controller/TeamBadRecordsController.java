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
import com.xywg.admin.modular.bad.model.WorkerBadRecords;
import com.xywg.admin.modular.bad.model.WorkerBadRecordsVO;
import com.xywg.admin.modular.bad.service.IWorkerBadRecordsService;
import com.xywg.admin.modular.bad.warpper.WorkerBadRecordsWarpper;
import com.xywg.admin.modular.system.service.IDictService;

/**
 * 班组不良记录控制器
 *
 * @author shily
 * @Date 2018-05-30 10:40:46
 */
@Controller
@RequestMapping("/teamBadRecords")
public class TeamBadRecordsController extends BaseController {

    private String PREFIX = "/bad/teamBadRecords/";

    @Autowired
    private IWorkerBadRecordsService workerBadRecordsService;
    
    @Autowired
    private IDictService dictService;

    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;

    /**
     * 跳转到班组不良记录首页
     */
    @RequestMapping("")
    public String index(Model model) {
    	 model.addAttribute("dictBadRecordLevelType", dictService.selectByName("事件级别"));
        return PREFIX + "teamBadRecords.html";
    }

    /**
     * 跳转到班组不良记录审核首页
     */
    @RequestMapping("/reviewTeamBadRecords")
    public String indexReviewWorker(Model model) {
    	 model.addAttribute("dictBadRecordLevelType", dictService.selectByName("事件级别"));
        return "/bad/reviewBadRecords/reviewTeamBadRecords.html";
    }
    
    /**
     * 跳转到添加班组不良记录页面
     */
    @RequestMapping("/teamBadRecords_add")
    public String workerBadRecordsAdd(Model model) {
    	model.addAttribute("dictBadRecordLevelType", dictService.selectByName("事件级别"));
    	model.addAttribute("dictBadRecordTypeCode", dictService.selectByName("不良记录类别"));
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        return PREFIX + "teamBadRecords_add.html";
    }

    /**
     * 跳转到修改班组不良记录页面
     */
    @RequestMapping("/teamBadRecords_update/{teamBadRecordsId}")
    public String teamBadRecordsUpdate(@PathVariable Integer teamBadRecordsId, Model model) {
    	WorkerBadRecordsVO teamBadRecords = workerBadRecordsService.selectTeamBadRecordsById(teamBadRecordsId);
    	model.addAttribute("dictBadRecordLevelType", dictService.selectByName("事件级别"));
    	model.addAttribute("dictBadRecordTypeCode", dictService.selectByName("不良记录类别"));
    	model.addAttribute("teams",workerBadRecordsService.getTeams());
        model.addAttribute("item",teamBadRecords);
        LogObjectHolder.me().set(teamBadRecords);
        return PREFIX + "teamBadRecords_edit.html";
    }
    
    /**
     * 查看详情
     */
    @RequestMapping("/teamBadRecords_view/{teamBadRecordsId}")
    public String teamBadRecordsView(@PathVariable Integer teamBadRecordsId, Model model) {
    	WorkerBadRecordsVO teamBadRecords = workerBadRecordsService.selectTeamBadRecordsById(teamBadRecordsId);
    	model.addAttribute("dictBadRecordLevelType", dictService.selectByName("事件级别"));
    	model.addAttribute("dictBadRecordTypeCode", dictService.selectByName("不良记录类别"));
    	model.addAttribute("teams",workerBadRecordsService.getTeams());
        model.addAttribute("item", teamBadRecords);
        LogObjectHolder.me().set(teamBadRecords);
        return PREFIX + "teamBadRecords_info.html";
    }

    /**
     * 获取班组不良记录列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map) {
    	 Page<WorkerBadRecords> page = new PageFactory<WorkerBadRecords>().defaultPage();
         List<Map<String, Object>> teamBadRecords = workerBadRecordsService.selectTeamBadRecords(page, map);
         page.setRecords((List<WorkerBadRecords>) new WorkerBadRecordsWarpper(teamBadRecords).warp());
         return super.packForBT(page);
    }

    /**
     * 新增班组不良记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(WorkerBadRecords workerBadRecords) {
    	workerBadRecords.setIsAudit(1);
        workerBadRecordsService.insert(workerBadRecords);
        return SUCCESS_TIP;
    }

    /**
     * 删除班组不良记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long teamBadRecordsId) {
        workerBadRecordsService.deleteById(teamBadRecordsId);
        return SUCCESS_TIP;
    }

    /**
     * 修改班组不良记录
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
    @RequestMapping(value = "/detail/{teamBadRecordsId}")
    @ResponseBody
    public Object detail(@PathVariable("teamBadRecordsId") Long teamBadRecordsId) {
        return workerBadRecordsService.selectById(teamBadRecordsId);
    }

    /**
     * 根据项目编号获取班组 若为总包 获取全部 若为参建 获取公司下班组
     */
    @RequestMapping(value = "/getTeamsByProjectCode")
    @ResponseBody
    public Object getTeamsByProjectCode(@RequestParam String projectCode) {
        return workerBadRecordsService.getTeamsByProjectCode(projectCode);
    }
}
