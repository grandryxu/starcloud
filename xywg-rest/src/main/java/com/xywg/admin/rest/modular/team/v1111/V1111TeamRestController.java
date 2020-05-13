package com.xywg.admin.rest.modular.team.v1111;

import com.xywg.admin.modular.team.model.AppTeamMemberVo;
import com.xywg.admin.modular.team.model.AppTeamerDto;
import com.xywg.admin.modular.team.model.AppWorkerByKeyVo;
import com.xywg.admin.modular.team.service.ITeamMasterService;
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
@RequestMapping("/v1111/appTeam")
@Api(description = "班组控制类")
public class V1111TeamRestController {
	
	@Autowired
	ITeamMasterService teamMasterService;

	/**
	 * 根据用户id查询其所有项目
	 * xieshuaishuai
	 */
	@ApiOperation(value = "根据班组长查询其所有项目")
	@RequestMapping(value = "/getProjectListByTeamer",method = RequestMethod.GET)
	public R v116GetProjectListByTeamer(@RequestBody AppTeamerDto appTeamerDto){
		return R.ok(teamMasterService.v1111GetProjectListByTeamer(appTeamerDto));
	}

}
