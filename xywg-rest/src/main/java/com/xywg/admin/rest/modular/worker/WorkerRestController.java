package com.xywg.admin.rest.modular.worker;

import com.xywg.admin.modular.worker.model.*;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;
import com.xywg.admin.rest.common.persistence.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * @description 工人控制类
 * 
 * @author wangshibo
 *
 * @date 2018年5月28日
 *
 */
@RestController
@RequestMapping("/appPersonnel")
@Api(value = "WorkerRestController",description = "工人控制类")
public class WorkerRestController {

	@Autowired
	private IWorkerMasterService workerMasterService;

	/**
	 * 工人申请加入班组 2018年5月30日 上午9:55:38
	 *
	 * @author wangshibo
	 *
	 */
	@ApiOperation(value = "工人申请加入班组", notes = "")
	@RequestMapping(value = "/addClass", method = RequestMethod.POST)
	public R addClass(@RequestBody AppAddClassDto addClassDto) {
		boolean addClass = workerMasterService.addClass(addClassDto);
		if (addClass) {
			return R.ok();
		} else {
			return R.error("已经申请过该班组");
		}
	}
	
	/**
	 * 工人进退场
	 * 2018年5月30日
	 *下午2:51:32
	 *@author wangshibo
	 *
	 */
	@ApiOperation(value = "工人进退场", notes = "")
	@RequestMapping(value = "/changePersonJoinStatus", method = RequestMethod.POST)
	public R changePersonJoinStatus(@RequestBody AppPersonJoinStatusDto personJoinStatusDto) {
		workerMasterService.changePersonJoinStatus(personJoinStatusDto);
		return R.ok();
	}

	/**
	 * 工人同意加入班组
	 */
	@ApiOperation(value = "工人同意加入班组", notes = "")
	@RequestMapping(value = "/workerAgreeAddTeam",method = RequestMethod.GET )
	public R workerAgreeAddTeam(@RequestParam Integer messageId) {
		workerMasterService.workerAgreeAddTeam(messageId);
		return R.ok();
	}

	/**
	 * 工人拒绝加入班组
	 */
	@ApiOperation(value = "工人拒绝加入班组", notes = "")
	@RequestMapping(value = "/workerDisagreeAddTeam",method = RequestMethod.GET)
	public R workerDisagreeAddTeam(@RequestParam Integer messageId) {
		workerMasterService.workerDisagreeAddTeam(messageId);
		return R.ok();
	}

	/**
	 * 获取工人详细信息
	 * @param idCardType
	 * @param idCardNumber
	 * @author yuanyang
	 * @return
	 */
	@ApiOperation(value = "获取工人详细信息")
	@GetMapping(value = "/getById")
	public R getById(@RequestParam Integer idCardType,@RequestParam String idCardNumber){
		return R.ok(workerMasterService.getById(idCardType,idCardNumber));
	}

	/**
	 * 班组长邀请工人加入班组
	 * 2018年6月5日
	 *下午2:19:08
	 *@author wangshibo
	 *
	 */
	@ApiOperation(value = "班组长邀请工人加入班组", notes = "")
	@RequestMapping(value = "/invitationAddTeam", method = RequestMethod.POST)
	public R invitationAddTeam(@RequestBody AppInvitationAddTeamDto addTeamDto) {
		boolean invitationAddTeam = workerMasterService.invitationAddTeam(addTeamDto);
		if (invitationAddTeam) {
			return R.ok();
		} else {
			return R.error("已经邀请过该工人");
		}
	}

	/**
	 * 班组长同意工人加入班组 xieshuaishuai
	 */
	@ApiOperation(value = "/班组长同意工人加入班组")
	@RequestMapping(value = "teamAgreeWorkerAdd", method = RequestMethod.GET)
	public R teamAgreeWorkerAdd(@RequestParam Integer messageId){
		workerMasterService.teamAgreeWorkerAdd(messageId);
		return R.ok();
	}

	/**
	 * 班组长拒绝工人加入班组 xieshuaishuai
	 */
	@ApiOperation(value = "/班组长拒绝工人加入班组")
	@RequestMapping(value = "teamDisagreeWorkerAdd", method = RequestMethod.GET)
	public R teamDisagreeWorkerAdd(@RequestParam Integer messageId){
		workerMasterService.teamDisagreeWorkerAdd(messageId);
		return R.ok();
	}

	@ApiOperation(value = "/修改工种")
	@RequestMapping(value = "/updateWorkKind", method = RequestMethod.POST)
	public R updateWorkKind(@RequestBody AppUpdateWorkerKindDto appUpdateWorkerKindDto){
		workerMasterService.updateWorkKind(appUpdateWorkerKindDto);
		return R.ok();
	}
	@ApiOperation(value = "/新增资格证书")
	@RequestMapping(value = "/addCertifications", method = RequestMethod.POST)
	public R addCertifications(@RequestBody AppAddCertificationsDto AppAddCertificationsDto){
		workerMasterService.addCertifications(AppAddCertificationsDto);
		return R.ok();
	}

	@ApiOperation(value = "/获取项目下的工人")
	@RequestMapping(value = "/getAppWorkerMasterByProjectCode", method = RequestMethod.GET)
	public R getAppWorkerMasterByProjectCode(@RequestParam String projectCode){

		return R.ok(workerMasterService.getAppWorkerMasterByProjectCode(projectCode));
	}

	/**
	 * 工人同意加入班组
	 */
	@ApiOperation(value = "扫二维码加入工人", notes = "")
	@RequestMapping(value = "/addWorkerByQr",method = RequestMethod.GET )
	public R addWorkerByQr(@RequestParam String projectCode,@RequestParam Integer idCardType,@RequestParam String idCardNumber,@RequestParam Integer teamId) {
		workerMasterService.addWorkerByQr(projectCode,idCardType,idCardNumber,teamId);
		return R.ok();
	}


}
