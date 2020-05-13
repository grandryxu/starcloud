package com.xywg.admin.rest.modular.project.v116;

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

import java.util.List;
import java.util.Map;

/**
 * @author yuanyang
 * @description 公司控制类
 * @date 2018年5月31日
 */
@RestController
@RequestMapping("/v116/appProject")
@Api(description = "项目控制类")
public class V116ProjectRestController {

    @Autowired
    private IProjectMasterService projectMasterService;
    @Autowired
    private IProjectSubContractorService projectSubContractorService;
    @Autowired
    private ITeamMasterService teamService;
    @Autowired
    private IProjectWorkerService projectWorkerService;

    /**
     * 获取我的关注项目列表
     *
     * @param id
     * @return
     * @author cw
     */
    @ApiOperation(value = "v116获取我的关注项目列表")
    @GetMapping(value = "/v116GetFollowProjectList")
    public R v116GetFollowProjectList(@RequestParam Integer id,@RequestParam String organizationCode,@RequestParam Integer pageSize,@RequestParam Integer pageNo) {
        return R.ok(projectMasterService.v116GetFollowProjectList(id,organizationCode,pageSize,pageNo));
    }

    /**
     * 获取公司的项目列表
     * @return
     * @author cw
     */
    @ApiOperation(value = "v116获取公司的项目列表")
    @GetMapping(value = "/v116GetProjectsByCompany")
    public R getProjectsByCompany(@RequestParam(required = false) String organizationCode, @RequestParam(required = false) Integer projectType,
    								@RequestParam(required = false) String key, 
    								@RequestParam(required = false) Integer projectStatus,@RequestParam(required = false) Integer id,
                                  @RequestParam Integer pageNo , @RequestParam Integer pageSize, 
                                  @RequestParam(required = false) String type, @RequestParam(required = false) String account) {
        return R.ok(projectSubContractorService.v116GetProjectsByCompany(organizationCode, projectType, key, projectStatus, id,pageNo,pageSize, type, account));
    }

    /**
     * 获取公司的某项目的参建单位信息列表
     *
     * @param projectCode
     * @return
     * @author yuanyang
     */
    @ApiOperation(value = "v116获取公司的某项目的参建单位信息列表")
    @GetMapping(value = "/v116GetCooperationProjectList")
    public R v116GetCooperationProjectList(@RequestParam String projectCode, @RequestParam Integer pageNo , @RequestParam Integer pageSize) {
        return R.ok(projectSubContractorService.v116GetCooperationProjectList(projectCode,pageNo,pageSize));
    }

    /**
     * 获取项目班组信息
     * 2018年6月7日
     * 下午2:08:58
     *
     * @author wangshibo
     */
    @ApiOperation(value = "v116获取项目班组信息列表")
    @GetMapping(value = "/v116GetAppTeamsInfo")
    public R v116GetAppTeamsInfo(@RequestParam(value = "organizationCode") String organizationCode, @RequestParam(value = "projectCode") String projectCode ,@RequestParam Integer pageNo , @RequestParam Integer pageSize) {
        List<AppTeamInfoVo> list = teamService.v116GetAppTeamsInfo(organizationCode, projectCode,pageNo,pageSize);
        return R.ok(list);
    }

    /**
     * 获取公司的某项目的某班组下的工人列表
     * 2018年6月11日
     * 上午11:07:10
     *
     * @author cw
     */
    @ApiOperation(value = "v116获取公司的某项目的某班组下的工人列表")
    @GetMapping(value = "/v116GetPersonsByTeam")
    public R v116GetPersonsByTeam(@RequestParam Integer teamId ,@RequestParam Integer pageNo , @RequestParam Integer pageSize) {
        List<AppPersonVo> list = projectWorkerService.v116GetPersonsByTeam(teamId,pageNo,pageSize);
        return R.ok(list);
    }
    
    /**
     * 获取公司的某项目的某工种下的工人列表
     * 2018年6月11日
     * 上午11:07:10
     *
     * @author cw
     */
    @ApiOperation(value = "v116获取公司的某项目的某工种下的工人列表")
    @PostMapping(value = "/v116GetPersonsByWorkType")
    public R v116GetPersonsByWorkType(@RequestBody Map<String,String> map ) {
    	String projectCode = map.get("projectCode");
    	String workType = map.get("workType");
    	Integer pageNo = Integer.parseInt(map.get("pageNo"));
    	Integer pageSize = Integer.parseInt(map.get("pageSize"));
        List<Map> list = projectWorkerService.v116GetPersonsByWorkType(workType,pageNo,pageSize,projectCode);
        return R.ok(list);
    }

    /**
     * 获取进场工人列表
     * 2018年9月18日
     * 下午4:15:53
     *
     * @author cw
     */
    @ApiOperation(value = "v116获取进场工人列表")
    @GetMapping(value = "/v116GetProjectJoinPerson")
    public R v116GetProjectJoinPerson(@RequestParam String organizationCode, @RequestParam String projectCode ,@RequestParam Integer pageNo , @RequestParam Integer pageSize) {
        List<AppProjectJoinPerson> list = projectWorkerService.v116GetProjectJoinPerson(organizationCode, projectCode,pageNo,pageSize);
        return R.ok(list);
    }

    /**
     * 获取公司的某项目的今日考勤人员列表
     * 2018年6月11日
     * 下午5:20:59
     *
     * @author wangshibo
     */
    @ApiOperation(value = "v116获取公司的某项目的今日考勤人员列表")
    @GetMapping(value = "/v116GetAttendanceWorkerToday")
    public R v116GetAttendanceWorkerToday(@RequestParam(value = "organizationCode") String organizationCode, @RequestParam(value = "projectCode") String projectCode ,@RequestParam Integer pageNo , @RequestParam Integer pageSize
    ) {
        return R.ok(projectWorkerService.v116GetAttendanceWorkerToday(projectCode, organizationCode,pageNo,pageSize));
    }

    /**
     * app企业端根据登录者获取项目列表
     * 2018年6月19日
     * 下午2:49:46
     *
     * @author wangshibo
     */
    @ApiOperation(value = "v116app企业端根据登录者获取项目列表")
    @GetMapping(value = "/v116GetProjectsByUserId")
    public R v116GetProjectsByUserId(@RequestParam Long userId ,@RequestParam Integer pageNo , @RequestParam Integer pageSize) {
        List<AppProjectListByPersonVo> list = projectMasterService.v116GetProjectsByUserId(userId,pageNo,pageSize);
        return R.ok(list);
    }

    /**
     * app企业端根据登录者获取项目列表
     * 2018年9月18日
     * 下午2:49:46
     *
     * @author wangshibo
     */
    @ApiOperation(value = "v116app企业端根据登录者获取在建可手机考勤项目列表")
    @GetMapping(value = "/v116GetOndoingProjectsByUserId")
    public R v116GetOndoingProjectsByUserId(@RequestParam Long userId ,@RequestParam Integer pageNo , @RequestParam Integer pageSize) {
        List<AppProjectListByPersonVo> list = projectMasterService.v116GetOndoingProjectsByUserId(userId,pageNo,pageSize);
        return R.ok(list);
    }

    /**
     * 获取指定人所在项目信息列表
     * 2018年6月5日
     * 上午9:41:04
     *
     * @author wangshibo
     */
    @ApiOperation(value = "v116获取指定人所在项目信息列表")
    @GetMapping(value = "/v116GetProjectListByPerson")
    public R v116GetProjectListByPerson(@RequestParam("idCardType") Integer idCardType, @RequestParam("idCardNumber") String idCardNumber, @RequestParam(value = "projectStatus", required = false) Integer projectStatus ,@RequestParam Integer pageNo , @RequestParam Integer pageSize) {
        List<AppProjectListByPersonVo> list = projectWorkerService.v116GetProjectListByPerson(idCardType, idCardNumber, projectStatus,pageNo,pageSize);
        return R.ok(list);
    }

}
