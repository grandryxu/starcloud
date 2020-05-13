package com.xywg.admin.rest.modular.face;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xywg.admin.modular.worker.model.WorkerMaster;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;
import com.xywg.admin.rest.common.persistence.model.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @description 人脸控制类
 * 
 * @author chupp
 *
 * @date 2018年5月28日
 *
 */
@RestController
@RequestMapping("/faceRest")
@Api(description = "人脸控制")
public class FaceRestController {

	@Autowired
    private IWorkerMasterService workerMasterService;

	/**
	 * 
	 * @description 工人实名认证
	 * @author chupp
	 * @date 2018年5月28日
	 * @param workerMaster
	 * @return
	 *
	 */
	@ApiOperation(value="工人实名认证", notes="")
    @RequestMapping(value = "authPerson",method = RequestMethod.POST)
    public R authPerson(@RequestBody WorkerMaster workerMaster) {
    	workerMasterService.authPerson(workerMaster);
    	return R.ok();
    }
	
	/**
	 * 
	 * @description 人脸录入
	 * @author chupp
	 * @date 2018年5月29日
	 * @param workerMaster
	 * @return
	 *
	 */
	@ApiOperation(value="人脸录入", notes="")
    @RequestMapping(value = "input",method = RequestMethod.POST)
    public R input(@RequestBody WorkerMaster workerMaster) {
    	workerMasterService.input(workerMaster);
    	return R.ok();
    }
	
	/**
	 * 
	 * @description 人脸图片查找信息
	 * @author chupp
	 * @date 2018年5月30日
	 * @param projectCode
	 * @param images
	 * @param sourceType
	 * @return
	 *
	 */
	@ApiOperation(value="人脸图片查找信息", notes="")
    @RequestMapping(value = "findFace",method = RequestMethod.GET)
    public R findFace(@RequestParam String projectCode,@RequestParam String images,@RequestParam int sourceType) {
    	return R.ok(workerMasterService.findFace(projectCode,images,sourceType));
    }
	
	/**
	 * 
	 * @description 人脸对比
	 * @author chupp
	 * @date 2018年8月2日
	 * @param workerMaster
	 * @return
	 *
	 */
	@ApiOperation(value="人脸对比", notes="")
    @RequestMapping(value = "compareFace",method = RequestMethod.POST)
    public R compareFace(@RequestBody WorkerMaster workerMaster) {
    	return R.ok(workerMasterService.compareFace(workerMaster));
    }
}
