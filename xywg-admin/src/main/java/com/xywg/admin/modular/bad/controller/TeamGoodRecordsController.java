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
 * 班组奖励记录控制器
 *
 * @author shily
 * @Date 2018-06-05 08:50:17
 */
@Controller
@RequestMapping("/teamGoodRecords")
public class TeamGoodRecordsController extends BaseController {

    private String PREFIX = "/bad/teamGoodRecords/";

    @Autowired
    private IWorkerGoodRecordsService workerGoodRecordsService;
    
    @Autowired
    private IWorkerBadRecordsService workerBadRecordsService;

    @Autowired
    private IDictService dictService;

    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;
    
    /**
     * 跳转到班组奖励记录首页
     */
    @RequestMapping("")
    public String index(Model model) {
    	model.addAttribute("dictGoodRecordLevelType", dictService.selectByName("奖励级别"));
        return PREFIX + "teamGoodRecords.html";
    }

    /**
     * 跳转到添加班组奖励记录
     */
    @RequestMapping("/teamGoodRecords_add")
    public String teamGoodRecordsAdd(Model model) {
    	model.addAttribute("dictIdCardType", dictService.selectByName("人员证件类型"));
    	model.addAttribute("dictGoodRecordLevelType", dictService.selectByName("奖励级别"));
    	model.addAttribute("dictGoodRecordTypeCode", dictService.selectByName("工人奖励类别"));
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        model.addAttribute("teams",workerBadRecordsService.getTeams());
        return PREFIX + "teamGoodRecords_add.html";
    }

    /**
     * 跳转到修改班组奖励记录
     */
    @RequestMapping("/teamGoodRecords_update/{teamGoodRecordsId}")
    public String teamGoodRecordsUpdate(@PathVariable Integer teamGoodRecordsId, Model model) {
    	WorkerGoodRecordsVO workerGoodRecords = workerGoodRecordsService.selectTeamGoodRecordsById(teamGoodRecordsId);
        model.addAttribute("dictIdCardType", dictService.selectByName("人员证件类型"));
    	model.addAttribute("dictGoodRecordLevelType", dictService.selectByName("奖励级别"));
    	model.addAttribute("dictGoodRecordTypeCode", dictService.selectByName("工人奖励类别"));
    	model.addAttribute("teams",workerBadRecordsService.getTeams());
        model.addAttribute("item",workerGoodRecords);
        LogObjectHolder.me().set(workerGoodRecords);
        return PREFIX + "teamGoodRecords_edit.html";
    }
    
    /**
     * 查看详情
     */
    @RequestMapping("/teamGoodRecords_view/{teamGoodRecordsId}")
    public String teamGoodRecordsView(@PathVariable Integer teamGoodRecordsId, Model model) {
    	WorkerGoodRecordsVO workerGoodRecords = workerGoodRecordsService.selectTeamGoodRecordsById(teamGoodRecordsId);
    	model.addAttribute("dictIdCardType", dictService.selectByName("人员证件类型"));
     	model.addAttribute("dictGoodRecordLevelType", dictService.selectByName("奖励级别"));
     	model.addAttribute("dictGoodRecordTypeCode", dictService.selectByName("工人奖励类别"));
     	model.addAttribute("teams",workerBadRecordsService.getTeams());
        model.addAttribute("item", workerGoodRecords);
        LogObjectHolder.me().set(workerGoodRecords);
        return PREFIX + "teamGoodRecords_info.html";
    }

    /**
     * 获取班组奖励记录列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map) {
    	 Page<WorkerGoodRecords> page = new PageFactory<WorkerGoodRecords>().defaultPage();
         List<Map<String, Object>> teamGoodRecords = workerGoodRecordsService.selectTeamGoodRecords(page, map);
         page.setRecords((List<WorkerGoodRecords>) new WorkerGoodRecordsWarpper(teamGoodRecords).warp());
         return super.packForBT(page);
    }

    /**
     * 新增班组奖励记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(WorkerGoodRecords workerGoodRecords) {
        workerGoodRecordsService.insert(workerGoodRecords);
        return SUCCESS_TIP;
    }

    /**
     * 删除班组奖励记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long teamGoodRecordsId) {
        workerGoodRecordsService.deleteById(teamGoodRecordsId);
        return SUCCESS_TIP;
    }

    /**
     * 修改班组奖励记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(WorkerGoodRecords workerGoodRecords) {
        workerGoodRecordsService.updateById(workerGoodRecords);
        return SUCCESS_TIP;
    }

    /**
     * 班组奖励记录详情
     */
    @RequestMapping(value = "/detail/{teamGoodRecordsId}")
    @ResponseBody
    public Object detail(@PathVariable("teamGoodRecordsId") Integer teamGoodRecordsId) {
        return workerGoodRecordsService.selectById(teamGoodRecordsId);
    }
    
}
