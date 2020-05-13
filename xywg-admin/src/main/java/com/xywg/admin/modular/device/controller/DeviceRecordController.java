package com.xywg.admin.modular.device.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.device.model.DeviceRecord;
import com.xywg.admin.modular.device.model.DeviceRecordExt;
import com.xywg.admin.modular.device.service.IDeviceRecordService;
import com.xywg.admin.modular.device.wrapper.DeviceRecordWarpper;
import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import com.xywg.admin.modular.system.service.IDictService;

/**
 * 考勤记录控制器
 *
 * @author wangcw
 * @Date 2018-05-30 13:59:58
 */
@Controller
@RequestMapping("/deviceRecord")
public class DeviceRecordController extends BaseController {

	private String PREFIX = "/deviceRecord/deviceRecord/";

	@Autowired
	private IDeviceRecordService deviceRecordService;
	
    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;
    
    @Autowired
    private IDictService dictService;

	/**
	 * 跳转到考勤记录首页
	 */
	@RequestMapping("")
	public String index(Model model) {
		model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
		model.addAttribute("source", dictService.selectByName("考勤类型"));
		return PREFIX + "deviceRecord.html";
	}

	/**
	 * 跳转到添加考勤记录
	 */
	@RequestMapping("/deviceRecord_add")
	public String deviceRecordAdd(Model model) {
		model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
		return PREFIX + "deviceRecord_add.html";
	}

	/**
	 * 跳转到修改考勤记录
	 */
	@RequestMapping("/deviceRecord_update/{deviceRecordId}")
	public String deviceRecordUpdate(@PathVariable Integer deviceRecordId, Model model) {
		DeviceRecord deviceRecord = deviceRecordService.selectById(deviceRecordId);
		model.addAttribute("item", deviceRecord);
		LogObjectHolder.me().set(deviceRecord);
		return PREFIX + "deviceRecord_edit.html";
	}

	/**
	 * 获取考勤记录列表
	 * 
	 * @author: duanfen
	 * @version: 2018年6月21日 下午2:31:49
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(@RequestParam Map<String, Object> map) {
		Page<DeviceRecordExt> page = new PageFactory<DeviceRecordExt>().defaultPage();
		List<Map<String, Object>> devices = deviceRecordService.selectList(page, map);
		page.setRecords((List<DeviceRecordExt>) new DeviceRecordWarpper(devices).warp());
		return super.packForBT(page);
	}

	/**
	 * 获取考勤记录列表
	 *
	 * @author: caiwei
	 * @version: 2018年7月18日
	 */
	@RequestMapping(value = "/toggleList")
	@ResponseBody
	public Object toggleList(@RequestParam Map<String, Object> map) {
		Page<DeviceRecord> page = new PageFactory<DeviceRecord>().defaultPage();
		map.put("projectCode", ShiroKit.getSessionAttr("toggleProjectCode"));
		List<Map<String, Object>> devices = deviceRecordService.selectList(page, map);
		page.setRecords((List<DeviceRecord>) new DeviceRecordWarpper(devices).warp());
		return super.packForBT(page);
	}

	/**
	 * 新增考勤记录
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(DeviceRecord deviceRecord) {
		deviceRecordService.insert(deviceRecord);
		return SUCCESS_TIP;
	}

	/**
	 * 删除考勤记录
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(@RequestParam Integer deviceRecordId) {
		deviceRecordService.deleteById(deviceRecordId);
		return SUCCESS_TIP;
	}

	/**
	 * 修改考勤记录
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Object update(DeviceRecord deviceRecord) {
		deviceRecordService.updateById(deviceRecord);
		return SUCCESS_TIP;
	}

	/**
	 * 考勤记录详情
	 */
	@RequestMapping(value = "/detail/{deviceRecordId}/{tableName}")
	public Object detail(@PathVariable("deviceRecordId") Long deviceRecordId,@PathVariable("tableName")String tableName, Model model) {
		model.addAttribute("item", deviceRecordService.findById(deviceRecordId,tableName));
		return PREFIX + "deviceRecord_detail.html";
	}

	/**
	 * 
	 * @description 查询工人某天考勤信息
	 * @author chupp
	 * @date 2018年6月20日
	 * @return
	 *
	 */
	@RequestMapping(value = "/getDayRecords")
	public String getDayRecords(Model model,Long sid, String sday) {
		//ModelAndView model = new ModelAndView("/report/deviceReport/" + "deviceReport_detail.html");
		//model.addObject("map", deviceRecordService.getDayRecords(sid, sday));
		model.addAttribute("sid",sid);
		model.addAttribute("sday",sday);
		model.addAttribute("datas",deviceRecordService.getDayRecords(sid, sday));
		return "/report/deviceReport/deviceReport_detail.html";
	}

	@RequestMapping(value = "/getDayRecordsData")
	@ResponseBody
	public Object getDayRecordsData( Long sid, String sday) {
		return deviceRecordService.getDayRecords(sid, sday).get("deviceRecordList");
	}
/**
	 * 
	 * @description 导出
	 * @author chupp
	 * @date 2018年10月22日
	 * @param request
	 * @param response
	 * @param map
	 *
	 */
	@RequestMapping(value = "/exportExcel")
    @ResponseBody
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String,Object> map) {
		deviceRecordService.exportExcel(request,response,map);
    }

	/**
	 * 新增补考勤记录
	 * 
	 * @author: duanfen
	 * @version: 2018年6月21日 下午2:26:59
	 */
	@RequestMapping(value = "/saveDeviceRecord")
	@ResponseBody
	public Object saveDeviceRecord(DeviceRecord deviceRecord)throws IOException {
		deviceRecordService.saveDeviceRecord(deviceRecord);
		return SUCCESS_TIP;
	}
	
	/**
	 * 
	 * @description 项目考勤详情
	 * @author chupp
	 * @date 2018年7月9日
	 * @param map
	 * @return
	 *
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDeviceRecordByProjectCode")
	@ResponseBody
	public Object getDeviceRecordByProjectCode(@RequestParam Map<String, Object> map) {
		Page<DeviceRecord> page = new PageFactory<DeviceRecord>().defaultPage();
		List<Map<String, Object>> devices = deviceRecordService.getDeviceRecordByProjectCode(page, map);
		page.setRecords((List<DeviceRecord>) new DeviceRecordWarpper(devices).warp());
		return super.packForBT(page);
	}
}
