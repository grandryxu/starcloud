package com.xywg.admin.modular.device.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.shiro.ShiroUser;
import com.xywg.admin.modular.device.model.Device;
import com.xywg.admin.modular.device.model.DeviceCommand;
import com.xywg.admin.modular.device.service.IDeviceCommandService;
import com.xywg.admin.modular.device.wrapper.DeviceWarpper;

/**
 * 设备命令控制器
 *
 * @author wangcw
 * @Date 2018-06-12 15:23:08
 */
@Controller
@RequestMapping("/deviceCommand")
public class DeviceCommandController extends BaseController {

	private String PREFIX = "/device/deviceCommand/";

	@Autowired
	private IDeviceCommandService deviceCommandService;

	/**
	 * 跳转到设备命令首页
	 */
	@RequestMapping("")
	public String index() {
		return PREFIX + "deviceCommand.html";
	}

	/**
	 * 跳转到添加设备命令
	 */
	@RequestMapping("/deviceCommand_add")
	public String deviceCommandAdd() {
		return PREFIX + "deviceCommand_add.html";
	}

	/**
	 * 跳转到修改设备命令
	 */
	@RequestMapping("/deviceCommand_update/{deviceCommandId}")
	public String deviceCommandUpdate(@PathVariable Integer deviceCommandId, Model model) {
		DeviceCommand deviceCommand = deviceCommandService.selectById(deviceCommandId);
		model.addAttribute("item", deviceCommand);
		LogObjectHolder.me().set(deviceCommand);
		return PREFIX + "deviceCommand_edit.html";
	}

	/**
	 * 获取设备命令列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(String condition) {
		return deviceCommandService.selectList(null);
	}

	/**
	 * 新增设备命令
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(DeviceCommand deviceCommand) {
		deviceCommandService.insert(deviceCommand);
		return SUCCESS_TIP;
	}

	/**
	 * 删除设备命令
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(@RequestParam Integer deviceCommandId) {
		deviceCommandService.deleteById(deviceCommandId);
		return SUCCESS_TIP;
	}

	/**
	 * 修改设备命令
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Object update(DeviceCommand deviceCommand) {
		deviceCommandService.updateById(deviceCommand);
		return SUCCESS_TIP;
	}

	/**
	 * 设备命令详情
	 */
	@RequestMapping(value = "/detail/{deviceCommandId}")
	@ResponseBody
	public Object detail(@PathVariable("deviceCommandId") Integer deviceCommandId) {
		return deviceCommandService.selectById(deviceCommandId);
	}

	/**
	 * 
	 * @param @param
	 *            command
	 * @param @return
	 * @Description: 执行命令
	 * @author cxl
	 * @date 2018年1月5日 下午2:20:44
	 */
	@RequestMapping(value = "/executeCommand")
	@ResponseBody
//	jln public Object executeCommand(@RequestParam String deviceIds,@RequestParam int type,@RequestParam String arr)throws Exception {
	public Object executeCommand(@RequestParam String deviceIds,@RequestParam int type,@RequestParam String workerId)throws Exception {	
		List<String> list = JSON.parseArray(deviceIds, String.class);
		List<String> workerIds =  null;
		/**工人id集合不为空转换成list格式*/
		if(StringUtils.isNotEmpty(workerId)) {
			workerIds = JSON.parseArray(workerId, String.class);
		}
		DeviceCommand deviceCommand = new DeviceCommand(list,type,workerIds);
		deviceCommandService.executeCommand(deviceCommand);

		return SUCCESS_TIP;
	}

	/**
	 * 
	 * @param @param
	 *            id
	 * @param @return
	 * @param @throws
	 *            Exception
	 * @Description: 撤销命令
	 * @author cxl
	 * @date 2018年1月5日 下午2:29:54
	 */
	@PostMapping("/cancelCommand")
	@ResponseBody
	public Object cancelCommand(Long id) throws Exception {
        ShiroUser user = ShiroKit.getUser();
		deviceCommandService.cancelCommand(id, user);
		return SUCCESS_TIP;

	}

	/**
	 * 
	 * @param @param
	 *            params
	 * @param @return
	 * @Description: 查询历史命令
	 * @author cxl
	 * @date 2018年1月8日 下午7:06:23
	 */
	@PostMapping("/queryCommand")
	@ResponseBody
	public Object queryCommand(@RequestParam Map<String, Object> params) {
       Page<Device> page = new PageFactory<Device>().defaultPage();
       List<DeviceCommand> deviceCommands = deviceCommandService.queryCommand(params);
       page.setRecords((List<Device>) new DeviceWarpper(deviceCommands).warp());
       return super.packForBT(page);
	}




	/**
	 * 跳转到查询历史命令
	 */
	@RequestMapping("/queryCommandBySeriesHtml")
	public String queryCommandBySeriesHtml(Model model,String projectCode,String deviceSns) {
		model.addAttribute("projectCode",projectCode);
		model.addAttribute("deviceSns",deviceSns);
		return PREFIX + "queryCommandBySeriesHtml.html";
	}



	/**
	 * @Description: 查询历史命令
	 * @author hjy
	 * @date 2018年6月25日 下午3:06:23
	 */
	@PostMapping("/queryCommandBySeries")
	@ResponseBody
	public Object queryCommandBySeries(@RequestParam Map<String, Object> params) {
		Page<DeviceCommand> page = new PageFactory<DeviceCommand>().defaultPage();
		List<DeviceCommand> deviceCommands = deviceCommandService.queryCommandBySeries(page,params);
		page.setRecords(deviceCommands);
		return super.packForBT(page);
	}

}
