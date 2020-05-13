package com.xywg.admin.modular.team.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.base.tips.ErrorTip;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import com.xywg.admin.modular.team.model.TeamEvaluation;
import com.xywg.admin.modular.team.model.TeamMaster;
import com.xywg.admin.modular.team.service.ITeamMasterService;
import com.xywg.admin.modular.team.warpper.TeamNumberWarpper;
import com.xywg.admin.modular.team.warpper.TeamWarpper;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import com.xywg.admin.modular.worker.warpper.WorkerWarpper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 班组管理控制器
 *
 * @author wangcw
 * @Date 2018-05-22 18:23:56
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/teamMaster")
public class TeamMasterController extends BaseController {

    private String PREFIX = "/team/teamMaster/";

    @Autowired
    private ITeamMasterService teamMasterService;

    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;

    /**
     * 跳转到班组管理首页
     */
    @RequestMapping("")
    public String index(Model model) {
        String toggleProjectCode = ShiroKit.getSessionAttr("toggleProjectCode");
        if(toggleProjectCode!=null){
            model.addAttribute("projectCode",toggleProjectCode);
        }
        return PREFIX + "teamMaster.html";
    }


    @RequestMapping("/teamMasterCount")
    public String teamMasterCount(Model model){
        model.addAttribute("isEnterprise",ShiroKit.getUser().getIsEnterprise());
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        model.addAttribute("projectCode", ShiroKit.getSessionAttr("toggleProjectCode"));
        return PREFIX + "teamMaster_count.html";
    }



    /**
     * 跳转到添加班组管理
     * @auth wangcw
     */
    @RequestMapping("/teamMaster_add/{projectCode}")
    public String teamMasterAdd(@PathVariable("projectCode")String projectCode,Model model) {
        model.addAttribute("companys",teamMasterService.getUnitContractorList(projectCode));
        return PREFIX + "teamMaster_add.html";
    }

    /**
     * 跳转到修改班组管理
     */
    @RequestMapping("/teamMaster_update/{teamMasterId}")
    public String teamMasterUpdate(@PathVariable Integer teamMasterId, Model model) {
        TeamMaster teamMaster = teamMasterService.getById(teamMasterId);
        model.addAttribute("item",teamMaster);
        LogObjectHolder.me().set(teamMaster);
        return PREFIX + "teamMaster_edit.html";
    }

    /**
     * 跳转到评价页面
     */
    @RequestMapping("/teamMaster_evaluation/{teamMasterId}")
    public String teamMasterEvaluation(@PathVariable Integer teamMasterId, Model model){
    	TeamMaster teamMaster = teamMasterService.getById(teamMasterId);
        model.addAttribute("item",teamMaster);
        LogObjectHolder.me().set(teamMaster);
        return PREFIX+"teamMaster_evaluation.html";
    }
    
    /**
     * 跳转到班组详情
     */
    @RequestMapping("/teamMaster_info/{teamMasterId}")
    public String teamMasterInfo(@PathVariable Integer teamMasterId, Model model) {
        TeamMaster teamMaster = teamMasterService.getById(teamMasterId);
        model.addAttribute("item",teamMaster);
        LogObjectHolder.me().set(teamMaster);
        return PREFIX + "teamMaster_info.html";
    }

    
    
    /**
     * 获取班组评价列表
     */
    @RequestMapping(value = "/teamMasterEvaluationList")
    @ResponseBody
    public Object teamMasterEvaluationList(@RequestParam Map<String,Object> map) {
    	Page<TeamEvaluation> page = new PageFactory<TeamEvaluation>().defaultPage();
    	List<Map<String, Object>> teamEvaluation = teamMasterService.getTeamEvaluationsByTeam(page,map,page.getOrderByField(), page.isAsc());
    	page.setRecords((List<TeamEvaluation>)new TeamWarpper(teamEvaluation).warp());
    	 return super.packForBT(page);
    }
    
    /**
     * 获取班组管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String,Object> map) {
    	Page<TeamMaster> page = new PageFactory<TeamMaster>().defaultPage();
    	List<Map<String, Object>> teams = teamMasterService.getTeams(page,map,page.getOrderByField(), page.isAsc());
    	page.setRecords((List<TeamMaster>)new TeamWarpper(teams).warp());
    	 return super.packForBT(page);
    }

    /**
     * 获取班组统计列表
     * @param teamMaster
     * @param leaderId
     * @return
     */
    @RequestMapping(value = "/teamMasterListCount")
    @ResponseBody
    public Object  teamMasterListCount(@RequestParam Map<String,Object> map){
        Page<TeamMaster> page = new PageFactory<TeamMaster>().defaultPage();
        List<Map<String, Object>> teams=  teamMasterService.getTeamMasterByProjectCodeAndOrgCode(map,page);
        page.setRecords((List<TeamMaster>)new TeamWarpper(teams).warp());
        return super.packForBT(page);
    }



    /**
     * 新增班组管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(TeamMaster teamMaster, String leaderId) {
        teamMasterService.insert(teamMaster);
        Integer id = teamMaster.getId();
        if(leaderId != null && !"".equals(leaderId)){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("teamCode", id + "");
            map.put("idStrs", leaderId);
            map.put("teamWorkType", "0");
            teamMasterService.addMember(map);
        }
        return SUCCESS_TIP;
    }
    /**
     * 新增班组管理
     */
    @RequestMapping(value = "addEvaluate")
    @ResponseBody
    public Object addEvaluate(TeamEvaluation teamEvaluation) {
    	teamMasterService.addEvaluate(teamEvaluation);
    	return SUCCESS_TIP;
    }

    /**
     * 删除班组管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer teamMasterId) {
    	if (teamMasterService.deleteById(teamMasterId)) {
    		return SUCCESS_TIP;
		} else {
			return new ErrorTip(400, "删除失败，请检查班组下是否存在员工");
		}
    }

    /**
     * 修改班组管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(TeamMaster teamMaster) {
        teamMasterService.update(teamMaster);
        return SUCCESS_TIP;
    }

    /**
     * 班组管理详情
     */
    @RequestMapping(value = "/detail/{teamMasterId}")
    @ResponseBody
    public Object detail(@PathVariable("teamMasterId") Integer teamMasterId) {
        return teamMasterService.getById(teamMasterId);
    }
    
    /**
     * 获取班组下的工人
     */
    @RequestMapping(value = "/getMemberByTeamId")
    @ResponseBody
    public Object getMemberByTeamId(@RequestParam Map<String,Object> map) {
    	Page<WorkerMaster> page = new PageFactory<WorkerMaster>().defaultPage();
    	List<Map<String, Object>> members = teamMasterService.getMemberByTeamCode(page,map,page.getOrderByField(), page.isAsc());
    	page.setRecords((List<WorkerMaster>)new WorkerWarpper(members).warp());
    	 return super.packForBT(page);
    }

    /**
     * 设置班组长
     */
    @RequestMapping(value = "/setTeamLeader")
    @ResponseBody
    public Object setTeamLeader(@RequestParam Integer memberId) {
        teamMasterService.setTeamLeader(memberId);
        return SUCCESS_TIP;
    }

    /**
     * 班组添加工人
     */
    @RequestMapping(value = "/addMember")
    @ResponseBody
    public Object addMember(@RequestParam Map<String,Object> map) {
    	teamMasterService.addMember(map);
    	return SUCCESS_TIP;
    }
    
    /**
     * 工人移除班组
     */
    @RequestMapping(value = "/deleteMember")
    @ResponseBody
    public Object deleteMember(@RequestParam Integer memberId) {
    	if (teamMasterService.deleteMember(memberId)) {
    		return SUCCESS_TIP;
		} else {
			return new ErrorTip(400, "员工未退场，无法删除");
		}
    	
    }
    
    /**
     * 获取没有班组的工人
     */
    @RequestMapping(value = "/getUnteamWorker")
    @ResponseBody
    public Object getUnteamWorker(@RequestParam Map<String,Object> map) {
    	Page<WorkerMaster> page = new PageFactory<WorkerMaster>().defaultPage();
    	List<Map<String, Object>> members = teamMasterService.getUnteamWorker(page,map,page.getOrderByField(), page.isAsc());
    	page.setRecords((List<WorkerMaster>)new WorkerWarpper(members).warp());
    	 return super.packForBT(page);
    }
    

    /**
     * 跳转到班组
     */
    @RequestMapping("/toTeamMember/{teamCode}")
    public String toTeamMember(@PathVariable("teamCode") Integer teamCode,Model model) {
    	model.addAttribute("teamCode", teamCode);
        return PREFIX + "teamMember.html";
    }
    

    /**
     * 跳转到新增成员
     */
    @RequestMapping("/toAddMember/{teamCode}")
    public String toAddMember(@PathVariable("teamCode") Integer teamCode,Model model) {
    	model.addAttribute("teamCode", teamCode);
        return PREFIX + "addTeamMember.html";
    }
    
    

    /**
     * 批量进场
     */
    @RequestMapping(value = "/workerJoin")
    @ResponseBody
    public Object workerJoin(@RequestParam String memberIds) {
        teamMasterService.workerJoin(memberIds);
        return SUCCESS_TIP;
    }
    

    /**
     * 批量退场
     */
    @RequestMapping(value = "/workerOut")
    @ResponseBody
    public Object workerOut(@RequestParam String memberIds) {
        teamMasterService.workerOut(memberIds);
        return SUCCESS_TIP;
    }

    /**
     * 获取班组管理列表 无分页 登陆公司下班组
     * @author 蔡伟
     */
    @RequestMapping(value = "/getList")
    @ResponseBody
    public Object getList(@RequestParam Map<String,Object> map) {
        List<Map<String, Object>> teams = teamMasterService.getList(map);
        return teams;
    }

    /**
     * 获取班组管理列表 无分页 所有班组
     * @author 蔡伟
     */
    @RequestMapping(value = "/getAllList")
    @ResponseBody
    public Object getAllList(@RequestParam Map<String,Object> map) {
        List<Map<String, Object>> teams = teamMasterService.getAllList(map);
        return teams;
    }
    /**
     * 获取班组下所有人 不分页
     * @auth xiess
     */
    @RequestMapping(value = "/getTeamMemberByTeamCode")
    @ResponseBody
    public Object getTeamMemberByTeamCode(@RequestParam Integer teamSysNo){
        return teamMasterService.getTeamMemberByTeamCode(teamSysNo);
    }

    /**
     * 获取班组下所有人 无分页
     * @auth 蔡伟
     */
    @RequestMapping(value = "/getTeamMemberByProjectCodeAndTeamCode")
    @ResponseBody
    public Object getTeamMemberByProjectCodeAndTeamCode(@RequestParam Map<String,Object> map){
        return teamMasterService.getTeamMemberByProjectCodeAndTeamCode(map);
    }

    /**
     * 获取班组下所有人 分页
     * @auth 蔡伟
     */
    @RequestMapping(value = "/getTeamMemberByProjectCodeAndTeamCodePages")
    @ResponseBody
    public Object getTeamMemberByProjectCodeAndTeamCodePages(@RequestParam Map<String,Object> map){
        Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
        List<Map<String, Object>> members = teamMasterService.getTeamMemberByProjectCodeAndTeamCodePages(page,map);
        page.setRecords((List<Map<String, Object>>)new WorkerWarpper(members).warp());
        return super.packForBT(page);
    }

    /**
     * 获取参建单位下拉框
     * 2018年6月22日
     *上午9:43:22
     *@author wangshibo
     *
     */
    @RequestMapping(value = "/getUnitContractorList/{projectCode}")
    @ResponseBody
    public Object getUnitContractorList(@PathVariable("projectCode")String projectCode){
    	return teamMasterService.getUnitContractorList(projectCode);
    }

    /**
     *@Description:获取班组下工人
     *@Author xieshuaishuai
     *@Date 2018/7/26 10:56
     */
    @RequestMapping(value = ("getWorkerListByTeam"))
    @ResponseBody
    public Object getWorkerListByTeam(@RequestParam Map<String,Object> map){
        Page<WorkerMaster> page=new PageFactory<WorkerMaster>().defaultPage();
        List<Map<String,Object>> workerList=teamMasterService.getWorkerListByTeam(map,page);
        page.setRecords((List<WorkerMaster>) new TeamNumberWarpper(workerList).warp());
        return super.packForBT(page);
    }

    /*
    * 选择项目添加班组页面
    * */
    @RequestMapping(value = ("/addTeamView/{projectCode}"))
    public Object addTeamView(@PathVariable("projectCode")String projectCode,Model model){
        model.addAttribute("companys",teamMasterService.getUnitContractorList(projectCode));
        return PREFIX + "addTeamView.html";
    }
}
