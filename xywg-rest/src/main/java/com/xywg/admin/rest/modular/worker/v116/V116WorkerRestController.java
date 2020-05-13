package com.xywg.admin.rest.modular.worker.v116;

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
@RequestMapping("/v116/appPersonnel")
@Api(description = "工人控制类")
public class V116WorkerRestController {

	@Autowired
	private IWorkerMasterService workerMasterService;

	@ApiOperation(value = "/v116获取项目下的工人")
	@RequestMapping(value = "/v116GetAppWorkerMasterByProjectCode", method = RequestMethod.GET)
	public R v116GetAppWorkerMasterByProjectCode(@RequestParam String projectCode ,@RequestParam Integer pageNo , @RequestParam Integer pageSize){
		return R.ok(workerMasterService.v116GetAppWorkerMasterByProjectCode(projectCode,pageNo,pageSize));
	}

}
