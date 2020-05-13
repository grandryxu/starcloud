package com.xywg.admin.rest.modular.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @description 二维码消息推送控制类
 * 
 * @author chupp
 *
 * @date 2018年7月27日
 *
 */
@RestController
@RequestMapping("qr")
@Api(description = "二维码消息推送控制类")
public class QRController extends BaseController {
	
	@Autowired
    protected IWorkerMasterService workerMasterService;
	
	/**
	 * 
	 * @description 二维码消息推送
	 * （String类型）
	 * 01:您所选项目并非当前工地
	 * 02:考勤成功
	 * 08:二维码失效（已超时）
	 * 09:二维码失效（已使用）
	 * @author chupp
	 * @date 2018年7月27日
	 * @param messageType
	 * @param idCardType
	 * @param idCardNumber
	 *
	 */
    @ApiOperation(value = "二维码消息推送", notes = "二维码消息推送")
    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public void sendMessage(@RequestParam String messageType,@RequestParam String idCardType,@RequestParam String idCardNumber) {
    	workerMasterService.sendMessageFromQr(messageType,idCardType,idCardNumber);
    }

}
