package com.xywg.admin.rest.modular.team;

import com.xywg.admin.modular.team.model.*;
import com.xywg.admin.modular.team.service.ITeamMasterService;
import com.xywg.admin.modular.worker.model.ContractFile;
import com.xywg.admin.modular.worker.model.ContractFileParam;
import com.xywg.admin.rest.common.persistence.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 
 * @author wangshibo
 *	2018年5月31日
 * 上午10:19:02
 */
@RestController
@RequestMapping("/appTeam")
@Api(value = "TeamRestController",description = "班组控制类")
public class TeamRestController {
	
	@Autowired
	ITeamMasterService teamMasterService;
	/**
	 * 根据班组长姓名模糊查询
	 * 2018年5月31日
	 *下午4:16:31
	 *@author wangshibo
	 *
	 */
	@ApiOperation(value = "根据班组长姓名模糊查询", notes = "")
	@RequestMapping(value = "/getTeamInfoLike", method = RequestMethod.GET)
	public R getTeamInfoLike(@RequestParam String teamLeaderName,@RequestParam Integer idCardType,@RequestParam String idCardNumber) {
		List<AppGetTeamInfoVo> teamInfoLike = teamMasterService.getTeamInfoLike(teamLeaderName,idCardType,idCardNumber);
		return R.ok(teamInfoLike);
	}
	/**
	 * 获取班组人员列表
	 * 2018年5月31日
	 *下午4:16:42
	 *@author wangshibo
	 *
	 */
	@ApiOperation(value = "获取班组人员列表", notes = "")
	@RequestMapping(value = "/getByTeam", method = RequestMethod.GET)
	public R getByTeam(@RequestParam Integer id,@RequestParam(required=false) Integer joinStatus) {
		List<AppTeamMemberVo> teamMemberVos = teamMasterService.getByTeamId(id,joinStatus);
		return R.ok(teamMemberVos);
	}
	/**
	 * 班组进退场
	 * 2018年5月31日
	 *下午4:16:48
	 *@author wangshibo
	 *
	 */
	@ApiOperation(value = "班组进退场", notes = "")
	@RequestMapping(value = "/changeTeamJoinStatus", method = RequestMethod.POST)
	public R changeTeamJoinStatus(@RequestBody AppTeamJoinStatusDto appJoinStatusDto) {
		 teamMasterService.changeTeamJoinStatus(appJoinStatusDto);
		return R.ok();
	}
	/**
	 * 根据班组id和项目code获取人员信息列表班组长用
	 * 2018年5月31日
	 *下午4:16:53
	 *@author wangshibo
	 *
	 */
	@ApiOperation(value = "根据班组id和项目code获取人员信息列表班组长用", notes = "")
	@RequestMapping(value = "/getPersonByTeamId", method = RequestMethod.GET)
	public R getPersonByTeamId(@RequestParam Integer teamId,@RequestParam String projectCode) {
		List<AppTeamMemberVo> teamMemberVos = teamMasterService.getPersonByTeamId(teamId,projectCode);
		return R.ok(teamMemberVos);
	}
	
	/**
	 * 班组长查询工人列表
	 * 2018年6月5日
	 *下午4:46:25
	 *@author wangshibo
	 *
	 */
	@ApiOperation(value = "班组长查询工人列表", notes = "")
	@RequestMapping(value = "/getWorkerByKeyAndTeamId", method = RequestMethod.GET)
	public R getWorkerByKeyAndTeamId(@RequestParam Integer teamId,@RequestParam(required=false) String key) {
		List<AppWorkerByKeyVo> list = teamMasterService.getWorkerByKeyAndTeamId(teamId,key);
		return R.ok(list);
	}
	
	/**
	 * 
	 * @description 查询劳动合同
	 * @author chupp
	 * @date 2018年6月8日
	 * @param teamMemberShip
	 * @return
	 *
	 */
	@ApiOperation(value="查询劳动合同", notes="")
    @RequestMapping(value = "getLaborContract",method = RequestMethod.POST)
    public R getLaborContract(@RequestBody TeamMemberShip teamMemberShip) {
    	return R.ok((Object)teamMasterService.getLaborContract(teamMemberShip));
    }

	
	/**
	 * 班组长新增工人
	 * 2018年6月8日
	 *下午4:24:26
	 *@author wangshibo
	 *
	 */
	@ApiOperation(value="班组长新增工人", notes="")
    @RequestMapping(value = "teamLeaderAddWorker",method = RequestMethod.POST)
	public R teamLeaderAddWorker(@RequestBody AppTeamAddWorkerDto addWorkerDto){
		teamMasterService.teamLeaderAddWorker(addWorkerDto);
		return R.ok();
		
	}

	
	/**
	 * 
	 * @description 保存劳动合同
	 * @author chupp
	 * @date 2018年6月8日
	 * @param contractFile
	 * @return
	 *
	 */
	@ApiOperation(value="保存劳动合同", notes="")
    @RequestMapping(value = "saveLaborContract",method = RequestMethod.POST)
    public R saveLaborContract(@RequestBody ContractFileParam contractFile) {
		teamMasterService.saveLaborContract(contractFile);
    	return R.ok();
    }
	
	/**
	 * 获取工种列表
	 * 2018年6月11日
	 *上午10:38:57
	 *@author wangshibo
	 *
	 */
	@ApiOperation(value="获取工种列表", notes="")
    @RequestMapping(value = "getWorkKinds",method = RequestMethod.GET)
    public R getWorkKinds() {
	List<AppWorkKindVo> list =	teamMasterService.getWorkKinds();
    	return R.ok(list);
    }

	/**
	 * 根据班组长查询其所有项目
	 * xieshuaishuai
	 */
	@ApiOperation(value = "根据班组长查询其所有项目")
	@RequestMapping(value = "/getProjectListByTeamer",method = RequestMethod.POST)
	public R getProjectListByTeamerId(@RequestBody AppTeamerDto appTeamerDto){
		return R.ok(teamMasterService.getProjectListByTeamer(appTeamerDto));
	}

}
