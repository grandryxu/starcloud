package com.xywg.admin.rest.modular.blackList;

import com.xywg.admin.modular.bad.dto.WorkerBlackDto;
import com.xywg.admin.modular.bad.model.WorkerBlackList;
import com.xywg.admin.modular.bad.service.IWorkerBlackListService;
import com.xywg.admin.rest.common.persistence.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 黑名单控制器
 * 
 * @author cw
 * @date 2018年10月24日
 */
@RestController
@Api(description = "黑名单接口")
@RequestMapping("/blackListApp")
public class BlackRestController {

	 @Autowired
	 private IWorkerBlackListService blackListService;

	/**
	 * 获取不良记录信息列表
	 * 
	 * @param type
	 * @return
	 */
	@ApiOperation(value = "获取黑名单列表")
	@GetMapping(value = "/getBlackListApp")
	public R getBlackListApp(@RequestParam Integer type , @RequestParam Integer pageNo , @RequestParam Integer pageSize){
		List<WorkerBlackDto> list=blackListService.getBlackListApp(type,pageNo,pageSize);
		return R.ok(list);
	}

	/**
	 * 新增黑名单
	 *
	 * @param workerBlackList
	 * @return
	 */
	@ApiOperation(value = "新增黑名单")
	@PostMapping(value = "/addBlackApp")
	public R addBlackApp(@RequestBody WorkerBlackList workerBlackList){
		workerBlackList.setType(1);
		this.blackListService.insert(workerBlackList);
		return R.ok();
	}

	/**
	 * 根据项目获取班组 总包获取全部 参建获取本公司下
	 * @param organizationCode 登陆账号公司
	 * @param projectCode
	 * @return
	 */
	@ApiOperation(value = "根据项目获取班组 总包获取全部 参建获取本公司下")
	@GetMapping(value = "/getTeamsByProjectCodeApp")
	public R getTeamsByProjectCodeApp(@RequestParam String organizationCode, @RequestParam String projectCode){
		return R.ok(this.blackListService.getTeamsByProjectCodeApp(organizationCode , projectCode));
	}

	/**
	 * 根据班组获取工人 总包获取全部 参建获取本公司下
	 * @param organizationCode 登陆账号公司
	 * @param projectCode
	 * @param teamSysNo
	 * @return
	 */
	@ApiOperation(value = "根据班组获取工人 总包获取全部 参建获取本公司下")
	@GetMapping(value = "/getWorkersByTeamSysNoApp")
	public R getWorkersByTeamSysNoApp(@RequestParam String organizationCode, @RequestParam String projectCode ,@RequestParam String teamSysNo){
		return R.ok(this.blackListService.getWorkersByTeamSysNoApp(organizationCode , projectCode ,teamSysNo));
	}
}
