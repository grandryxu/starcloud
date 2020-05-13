package com.xywg.admin.rest.modular.team.v116;

import com.xywg.admin.modular.team.model.*;
import com.xywg.admin.modular.team.service.ITeamMasterService;
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
@RequestMapping("/v116/appTeam")
@Api(description = "班组控制类")
public class V116TeamRestController {
	
	@Autowired
	ITeamMasterService teamMasterService;

	/**
	 * 根据班组长查询其所有项目
	 * xieshuaishuai
	 */
	@ApiOperation(value = "v116根据班组长查询其所有项目")
	@RequestMapping(value = "/v116GetProjectListByTeamer",method = RequestMethod.POST)
	public R v116GetProjectListByTeamer(@RequestBody AppTeamerDto appTeamerDto){
		return R.ok(teamMasterService.v116GetProjectListByTeamer(appTeamerDto));
	}

	/**
	 * 获取班组人员列表分页
	 * 2018年5月31日
	 *下午4:16:42
	 *@author wangshibo
	 *
	 */
	@ApiOperation(value = "获取班组人员列表", notes = "")
	@RequestMapping(value = "/getByTeam", method = RequestMethod.GET)
	public R v116getByTeam(@RequestParam Integer id,@RequestParam(required=false) Integer joinStatus,@RequestParam Integer pageNo,@RequestParam Integer pageSize) {
		List<AppTeamMemberVo> teamMemberVos = teamMasterService.v116getByTeamId(id,joinStatus,pageNo,pageSize);
		return R.ok(teamMemberVos);
	}

	/**
	 * 根据班组id和项目code获取人员信息列表班组长用
	 * @param teamId
	 * @param projectCode
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value = "v116根据班组id和项目code获取人员信息列表班组长用")
	@RequestMapping(value = "/getPersonByTeamId", method = RequestMethod.GET)
	public R v116GetPersonByTeamId(@RequestParam Integer teamId,@RequestParam String projectCode,@RequestParam Integer pageNo , @RequestParam Integer pageSize) {
		List<AppTeamMemberVo> teamMemberVos = teamMasterService.v116GetPersonByTeamId(teamId,projectCode,pageNo,pageSize);
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
	public R getWorkerByKeyAndTeamId(@RequestParam Integer teamId,@RequestParam(required=false) String key ,@RequestParam Integer pageNo , @RequestParam Integer pageSize) {
		List<AppWorkerByKeyVo> list = teamMasterService.v116GetWorkerByKeyAndTeamId(teamId,key,pageNo,pageSize);
		return R.ok(list);
	}


}
