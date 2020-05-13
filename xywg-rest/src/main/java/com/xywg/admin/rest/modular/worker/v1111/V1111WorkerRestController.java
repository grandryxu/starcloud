package com.xywg.admin.rest.modular.worker.v1111;

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
@RequestMapping("/v1111/appPersonnel")
@Api(value = "WorkerRestController",description = "工人控制类")
public class V1111WorkerRestController {

	@Autowired
	private IWorkerMasterService workerMasterService;

	/**
	 * 获取工人详细信息
	 * @param projectCode
	 * @param idCardType
	 * @param idCardNumber
	 * @author yuanyang
	 * @return
	 */
	@ApiOperation(value = "获取工人详细信息")
	@GetMapping(value = "/getById")
	public R getById(@RequestParam String projectCode ,@RequestParam Integer idCardType,@RequestParam String idCardNumber){
		return R.ok(workerMasterService.v1111GetById(projectCode,idCardType,idCardNumber));
	}

}
