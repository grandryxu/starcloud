package com.xywg.admin.modular.bad.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.modular.bad.model.WorkerBlackList;
import com.xywg.admin.modular.bad.model.WorkerBlackListVO;
import com.xywg.admin.modular.bad.service.IWorkerBadRecordsService;
import com.xywg.admin.modular.bad.service.IWorkerBlackListService;
import com.xywg.admin.modular.bad.warpper.WorkerBlackListWarpper;
import com.xywg.admin.modular.system.service.IDictService;

/**
 * 工人黑名单记录控制器
 *
 * @author shily
 * @Date 2018-06-06 08:57:30
 */
@Controller
@RequestMapping("/workerBlackList")
public class WorkerBlackListController extends BaseController {

    private String PREFIX = "/bad/workerBlackList/";

    @Autowired
    private IWorkerBlackListService workerBlackListService;
    
    @Autowired
    private IWorkerBadRecordsService workerBadRecordsService;
    
    @Autowired
    private IDictService dictService;

    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;
    /**
     * 跳转到工人黑名单记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "workerBlackList.html";
    }

    /**
     * 跳转到添加工人黑名单记录
     */
    @RequestMapping("/workerBlackList_add")
    public String workerBlackListAdd(Model model) {
    	model.addAttribute("dictIdCardType", dictService.selectByName("人员证件类型"));
    	model.addAttribute("workers",workerBadRecordsService.getWorkers());
    	model.addAttribute("dictValidStatus", dictService.selectByName("时间类型"));
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        return PREFIX + "workerBlackList_add.html";
    }

    /**
     * 跳转到修改工人黑名单记录
     */
    @RequestMapping("/workerBlackList_update/{workerBlackListId}")
    public String workerBlackListUpdate(@PathVariable Integer workerBlackListId, Model model) {
        WorkerBlackListVO workerBlackList = workerBlackListService.selectWorkerBlackListById(workerBlackListId);
        model.addAttribute("dictIdCardType", dictService.selectByName("人员证件类型"));
    	model.addAttribute("workers",workerBadRecordsService.getWorkers());
    	model.addAttribute("dictValidStatus", dictService.selectByName("时间类型"));
        model.addAttribute("item",workerBlackList);
        LogObjectHolder.me().set(workerBlackList);
        return PREFIX + "workerBlackList_edit.html";
    }
    
    /**
     * 查看详情
     */
    @RequestMapping("/workerBlackList_view/{workerBlackListId}")
    public String workerBlackListView(@PathVariable Integer workerBlackListId, Model model) {
    	WorkerBlackListVO workerBlackList = workerBlackListService.selectWorkerBlackListById(workerBlackListId);
    	model.addAttribute("dictIdCardType", dictService.selectByName("人员证件类型"));
     	model.addAttribute("workers",workerBadRecordsService.getWorkers());
     	model.addAttribute("dictValidStatus", dictService.selectByName("时间类型"));
        model.addAttribute("item",workerBlackList);
        LogObjectHolder.me().set(workerBlackList);
        return PREFIX + "workerBlackList_info.html";
    }

    /**
     * 获取工人黑名单记录列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map) {
    	 Page<WorkerBlackList> page = new PageFactory<WorkerBlackList>().defaultPage();
         List<Map<String, Object>> WorkerBlackList = workerBlackListService.selectWorkerBlackList(page, map);
         page.setRecords((List<WorkerBlackList>) new WorkerBlackListWarpper(WorkerBlackList).warp());
         return super.packForBT(page);
    }

    /**
     * 新增工人黑名单记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(WorkerBlackList workerBlackList) {
    	workerBlackList.setIsValid(1);
        workerBlackListService.insert(workerBlackList);
        return SUCCESS_TIP;
    }

    /**
     * 删除工人黑名单记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long workerBlackListId) {
        workerBlackListService.deleteById(workerBlackListId);
        return SUCCESS_TIP;
    }

    /**
     * 修改工人黑名单记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(WorkerBlackList workerBlackList) {
        workerBlackListService.updateById(workerBlackList);
        return SUCCESS_TIP;
    }

    /**
     * 工人黑名单记录详情
     */
    @RequestMapping(value = "/detail/{workerBlackListId}")
    @ResponseBody
    public Object detail(@PathVariable("workerBlackListId") Integer workerBlackListId) {
        return workerBlackListService.selectById(workerBlackListId);
    }
    
    /**
     * 工人姓名下拉框onchange事件
     */
    @RequestMapping(value = "/workerDetail/{id}")
    @ResponseBody
    public Object workerDetail(@PathVariable("id") Integer id) {
        return workerBlackListService.selectWorkerInfoById(id);
    }
    
    /**
     * 工人所属项目下拉框onchange事件
     */
    @RequestMapping(value = "/getOwnerNameByProjectCode/{projectCode}")
    @ResponseBody
    public Object getOwnerNameByProjectCode(@PathVariable("projectCode") String projectCode) {
        return workerBlackListService.getOwnerNameByProjectCode(projectCode);
    }
    
    /**
     * 工人所属项目下拉框onchange事件
     */
    @RequestMapping(value = "/getTeamInfoByProjectCode/{projectCode}")
    @ResponseBody
    public Object getTeamInfoByProjectCode(@PathVariable("projectCode") String projectCode) {
        return workerBlackListService.getTeamInfoByProjectCode(projectCode);
    }

    /**
     * 根据班组获取工人 总包获取全部 参建获取本公司下
     * @param projectCode
     * @param teamSysNo
     * @return
     */
    @RequestMapping(value = "/getWorkersByTeamSysNo")
    @ResponseBody
    public Object getWorkersByTeamSysNo(@RequestParam String projectCode ,@RequestParam String teamSysNo){
        return this.workerBlackListService.getWorkersByTeamSysNo(projectCode ,teamSysNo);
    }
}
