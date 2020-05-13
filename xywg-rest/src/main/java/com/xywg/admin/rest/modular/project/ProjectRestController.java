package com.xywg.admin.rest.modular.project;

import com.xywg.admin.modular.project.model.*;
import com.xywg.admin.modular.project.service.IProjectMasterService;
import com.xywg.admin.modular.project.service.IProjectWorkerService;
import com.xywg.admin.modular.projectSubContractor.service.IProjectSubContractorService;
import com.xywg.admin.modular.team.model.AppTeamInfoVo;
import com.xywg.admin.modular.team.service.ITeamMasterService;
import com.xywg.admin.rest.common.persistence.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yuanyang
 * @description 公司控制类
 * @date 2018年5月31日
 */
@RestController
@RequestMapping("/appProject")
@Api(description = "项目控制类")
public class ProjectRestController {

    @Autowired
    private ITeamMasterService teamService;
    @Autowired
    private IProjectWorkerService projectWorkerService;
    @Autowired
    private IProjectSubContractorService projectSubContractorService;
    @Autowired
    private IProjectMasterService projectMasterService;

    /**
     * 获取公司的项目列表
     * @return
     * @author yuanyang
     */
    @ApiOperation(value = "获取公司的项目列表")
    @GetMapping(value = "/getProjectsByCompany")
    public R getProjectsByCompany(@RequestParam String organizationCode, @RequestParam Integer projectType, @RequestParam(required = false) String key, @RequestParam(required = false) Integer projectStatus,@RequestParam Integer id) {
        return R.ok(projectSubContractorService.getProjectsByCompany(organizationCode, projectType, key, projectStatus, id));
    }

    /**
     * 获取公司的某项目的工人年龄分布
     *
     * @param organizationCode
     * @param projectCode
     * @return
     * @author yuanyang
     */
    @ApiOperation(value = "获取公司的某项目的工人年龄分布")
    @GetMapping(value = "/getAgeCount")
    public R getAgeCount(@RequestParam String organizationCode, @RequestParam String projectCode) {
        return R.ok(projectWorkerService.getAgeCount(organizationCode, projectCode));
    }


    /**
     * 获取公司的某项目的工人性别分布
     *
     * @param organizationCode
     * @param projectCode
     * @return
     * @author yuanyang
     */
    @ApiOperation(value = "获取公司的某项目的工人性别分布")
    @GetMapping(value = "/getGenderCount")
    public R getGenderCount(@RequestParam String organizationCode, @RequestParam String projectCode) {
        return R.ok(projectWorkerService.getGenderCount(organizationCode, projectCode));
    }

    /**
     * 获取公司的某项目的工人性别分布
     *
     * @param organizationCode
     * @param projectCode
     * @return
     * @author yuanyang
     */
    @ApiOperation(value = "获取公司的某项目的工人工种分布")
    @GetMapping(value = "/getWorkTypeCount")
    public R getWorkTypeCount(@RequestParam String organizationCode, @RequestParam String projectCode) {
        return R.ok(projectWorkerService.getWorkTypeCount(organizationCode, projectCode));
    }

    /**
     * 获取公司的某项目的班组数
     *
     * @param organizationCode
     * @param projectCode
     * @return
     * @author yuanyang
     */
    @ApiOperation(value = "获取公司的某项目的班组数")
    @GetMapping(value = "/getTeamCount")
    public R getTeamCount(@RequestParam String organizationCode, @RequestParam String projectCode) {
        return R.ok(projectWorkerService.getTeamCount(organizationCode, projectCode));
    }

    /**
     * 获取项目统计个数
     *
     * @param organizationCode
     * @return
     * @author yuanyang
     */
    @ApiOperation(value = "获取项目统计个数")
    @GetMapping(value = "/getProjectCount")
    public R getProjectCount(@RequestParam(required=false) String organizationCode, @RequestParam(required=false) String type, @RequestParam(required=false) String account) {
        return R.ok(projectSubContractorService.getProjectCount(organizationCode, type, account));
    }

    /**
     * 获取公司的某项目的进场人数
     *
     * @param organizationCode
     * @param projectCode
     * @return
     * @author yuanyang
     */
    @ApiOperation(value = "获取公司的某项目的进场人数")
    @GetMapping(value = "/getJoinedCount")
    public R getJoinedCount(@RequestParam(required=false) String organizationCode, @RequestParam String projectCode) {
        return R.ok(projectWorkerService.getJoinedCount(organizationCode, projectCode));
    }


    /**
     * 获取参建公司的某项目的参建基本信息
     *
     * @param organizationCode
     * @param projectCode
     * @return
     * @author yuanyang
     */
    @ApiOperation(value = "获取参建公司的某项目的参建基本信息")
    @GetMapping(value = "/getCooperationProject")
    public R getCooperationProject(@RequestParam(required=false) String organizationCode, @RequestParam(required=false) String projectCode) {
        return R.ok(projectSubContractorService.getCooperationProject(organizationCode, projectCode));
    }

    /**
     * 获取公司的某项目的参建单位信息列表
     *
     * @param projectCode
     * @return
     * @author yuanyang
     */
    @ApiOperation(value = "获取公司的某项目的参建单位信息列表")
    @GetMapping(value = "/getCooperationProjectList")
    public R getCooperationProjectList(@RequestParam String projectCode) {
        return R.ok(projectSubContractorService.getCooperationProjectList(projectCode));
    }

    /**
     * 获取我的关注项目列表
     *
     * @param id
     * @return
     * @author yuanyang
     */
    @ApiOperation(value = "获取我的关注项目列表")
    @GetMapping(value = "/getFollowProjectList")
    public R getFollowProjectList(@RequestParam Integer id,@RequestParam String organizationCode) {
        return R.ok(projectMasterService.getFollowProjectList(id,organizationCode));
    }

    /**
     * 获取指定人所在项目信息列表
     * 2018年6月5日
     * 上午9:41:04
     *
     * @author wangshibo
     */
    @ApiOperation(value = "获取指定人所在项目信息列表")
    @GetMapping(value = "/getProjectListByPerson")
    public R getProjectListByPerson(@RequestParam("idCardType") Integer idCardType, @RequestParam("idCardNumber") String idCardNumber, @RequestParam(value = "projectStatus", required = false) Integer projectStatus) {
        List<AppProjectListByPersonVo> list = projectWorkerService.getProjectListByPerson(idCardType, idCardNumber, projectStatus);
        return R.ok(list);
    }

    /**
     * 收藏项目
     * 2018年6月5日
     * 上午9:44:20
     *
     * @author wangshibo
     */
    @ApiOperation(value = "收藏项目")
    @PostMapping(value = "/concernProject")
    public R concernProject(@RequestBody AppConcernProjectDto appConcernProjectDto) {
        projectWorkerService.concernProject(appConcernProjectDto.getProjectCode(), appConcernProjectDto.getIsCollect(), appConcernProjectDto.getUserId());
        return R.ok();
    }

    /**
     * 获取工人信息
     * 2018年6月6日
     * 上午9:57:17
     *
     * @author wangshibo
     */
    @ApiOperation(value = "获取工人信息")
    @GetMapping(value = "/getPersonInfo")
    public R getPersonInfo(@RequestParam String idCardNumber, @RequestParam String projectCode, @RequestParam Integer idCardType) {
        AppPersonVo person = projectWorkerService.getPersonInfo(idCardNumber, projectCode, idCardType);
        return R.ok(person);
    }

    /**
     * 获取进场工人列表
     * 2018年6月6日
     * 下午4:15:53
     *
     * @author wangshibo
     */
    @ApiOperation(value = "获取进场工人列表")
    @GetMapping(value = "/getProjectJoinPerson")
    public R getProjectJoinPerson(@RequestParam String organizationCode, @RequestParam String projectCode) {
        List<AppProjectJoinPerson> list = projectWorkerService.getProjectJoinPerson(organizationCode, projectCode);
        return R.ok(list);
    }

    /**
     * 获取项目中工人籍贯统计
     * 2018年6月7日
     * 上午8:57:18
     *
     * @author wangshibo
     */
    @ApiOperation(value = "获取项目中工人籍贯统计")
    @GetMapping(value = "/getBirthPlaceCount")
    public R getBirthPlaceCount(@RequestParam(value = "organizationCode") String organizationCode, @RequestParam(value = "projectCode") String projectCode) {
        List<AppBirthPlaceCountVo> list = projectWorkerService.getBirthPlaceCount(organizationCode, projectCode);
        return R.ok(list);
    }

    /**
     * 获取项目信息
     * 2018年6月7日
     * 上午11:14:07
     *
     * @author wangshibo
     */
    @ApiOperation(value = "获取项目信息")
    @GetMapping(value = "/getProjectInfo")
    public R getProjectInfo(@RequestParam String organizationCode, @RequestParam String projectCode) {

        return R.ok(projectWorkerService.getProjectInfo(organizationCode, projectCode));
    }

    /**
     * 获取项目班组信息
     * 2018年6月7日
     * 下午2:08:58
     *
     * @author wangshibo
     */
    @ApiOperation(value = "获取项目班组信息列表")
    @GetMapping(value = "/getAppTeamsInfo")
    public R getAppTeamsInfo(@RequestParam(value = "organizationCode") String organizationCode, @RequestParam(value = "projectCode") String projectCode) {
        List<AppTeamInfoVo> list = teamService.getAppTeamsInfo(organizationCode, projectCode);
        return R.ok(list);
    }

    /**
     * 获取公司的某项目的某班组下的工人列表
     * 2018年6月11日
     * 上午11:07:10
     *
     * @author wangshibo
     */
    @ApiOperation(value = "获取公司的某项目的某班组下的工人列表")
    @GetMapping(value = "/getPersonsByTeam")
    public R getPersonsByTeam(@RequestParam Integer teamId) {
        List<AppPersonVo> list = projectWorkerService.getPersonsByTeam(teamId);
        return R.ok(list);
    }

    /**
     * 获取公司的某项目的今日考勤人员列表
     * 2018年6月11日
     * 下午5:20:59
     *
     * @author wangshibo
     */
    @ApiOperation(value = "获取公司的某项目的今日考勤人员列表")
    @GetMapping(value = "/getAttendanceWorkerToday")
    public R getAttendanceWorkerToday(@RequestParam(value = "organizationCode") String organizationCode, @RequestParam(value = "projectCode") String projectCode) {
        return R.ok(projectWorkerService.getAttendanceWorkerToday(projectCode, organizationCode));
    }

    /**
     * app企业端根据登录者获取项目列表
     * 2018年6月19日
     * 下午2:49:46
     *
     * @author wangshibo
     */
    @ApiOperation(value = "app企业端根据登录者获取项目列表")
    @GetMapping(value = "/getProjectsByUserId")
    public R getProjectsByUserId(@RequestParam Long userId) {
        List<AppProjectListByPersonVo> list = projectMasterService.getProjectsByUserId(userId);
        return R.ok(list);
    }
    
    /**
     * app企业端根据登录者获取项目列表
     * 2018年6月19日
     * 下午2:49:46
     *
     * @author wangshibo
     */
    @ApiOperation(value = "app企业端根据登录者获取在建可手机考勤项目列表")
    @GetMapping(value = "/getOndoingProjectsByUserId")
    public R getOndoingProjectsByUserId(@RequestParam Long userId) {
    	List<AppProjectListByPersonVo> list = projectMasterService.getOndoingProjectsByUserId(userId);
    	return R.ok(list);
    }

    @ApiOperation(value = "获取有安全帽员工的信息")
    @GetMapping(value = "/getWorkHeadInfoList")
    public R getWorkHeadInfoList() {
        List<WorkInfoHead> list = projectWorkerService.getWorkInfoHeadList();
        return R.ok(list);
    }
    
    /**
	 * 班组工种当日考勤统计
	 */
	@ApiOperation(value = "班组工种当日考勤统计", notes = "")
	@RequestMapping(value = "/attenceReport",method = RequestMethod.POST )
	public R addWorkerByQr(@RequestBody Map<String,String> map) {
		List resultList = new ArrayList<>();
		String projectCode = map.get("projectCode");
		String type = map.get("type");
		Integer pageNo = Integer.parseInt(map.get("pageNo"));
		Integer pageSize = Integer.parseInt(map.get("pageSize"));
		List list = projectWorkerService.listReport(projectCode,type);
		if((pageNo-1)*pageSize<list.size()) {
			resultList = list.subList((pageNo-1)*pageSize, pageNo*pageSize>list.size()?list.size():pageNo*pageSize);
		}
		
		
		return R.ok(resultList);
	}

    /**
     * 班组工种当日考勤统计
     * 即使没有也需要统计班组和工种
     */
    @ApiOperation(value = "班组工种当日考勤统计", notes = "")
    @RequestMapping(value = "/attenceReportEver",method = RequestMethod.POST )
    public R attenceReportEver(@RequestBody Map<String,String> map) {
        List resultList = new ArrayList<>();
        String projectCode = map.get("projectCode");
        String type = map.get("type");
        Integer pageNo = Integer.parseInt(map.get("pageNo"));
        Integer pageSize = Integer.parseInt(map.get("pageSize"));
        List list = projectWorkerService.listReportEver(projectCode,type);
        if((pageNo-1)*pageSize<list.size()) {
            resultList = list.subList((pageNo-1)*pageSize, pageNo*pageSize>list.size()?list.size():pageNo*pageSize);
        }
        return R.ok(resultList);
    }

}
