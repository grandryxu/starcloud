package com.xywg.admin.task;

import java.io.IOException;
import java.util.*;

import com.sun.org.apache.bcel.internal.generic.I2F;
import com.xywg.admin.core.util.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xywg.admin.modular.company.service.ISubContractorService;
import com.xywg.admin.modular.device.service.IDeviceRecordService;
import com.xywg.admin.modular.device.service.IDeviceService;
import com.xywg.admin.modular.project.service.IProjectMasterService;
import com.xywg.admin.modular.project.service.IProjectTrainingService;
import com.xywg.admin.modular.smz.model.IfaLabor;
import com.xywg.admin.modular.smz.service.IfaLaborService;
import com.xywg.admin.modular.smz.utils.Constant;
import com.xywg.admin.modular.smz.utils.HttpClientUtils;
import com.xywg.admin.modular.system.service.IWorkKindService;
import com.xywg.admin.modular.team.service.ITeamMasterService;
import com.xywg.admin.modular.worker.service.IWorkerContractRuleService;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;

import net.sf.json.JSONObject;
/*
* 发送数据到实名制
* */
@JobHandler(value="SendDataTolfaTaskHandler")
@Component
public class SendDataTolfaTaskHandler extends IJobHandler {

	@Autowired
	private IfaLaborService ifaLaborService;

	@Autowired
	private ISubContractorService subContractorService;

	@Autowired
	private IProjectMasterService projectMasterService;

	@Autowired
	private IWorkerMasterService workerMasterService;

	@Autowired
	private ITeamMasterService teamMasterService;

	@Autowired
	private IWorkerContractRuleService workerContractRuleService;

	@Autowired
	private IDeviceService deviceService;

	@Autowired
	private IDeviceRecordService deviceRecordService;

	@Autowired
	private IProjectTrainingService projectTrainingService;
	
	@Autowired
	private IWorkKindService workKindService;

	private static final Log LOG = LogFactory.getLog(SendDataTolfaTaskHandler.class);
	
	private static Properties systemParams = new Properties();
	
	/**
	 * 加载配置文件
	 */
	static {
		try {
			systemParams.load(SendDataTolfaTaskHandler.class.getClassLoader().getResourceAsStream("smz.properties"));
		} catch (IOException e) {
			LOG.error("smz.properties" + "配置文件加载失败");
		}
	}
	
	/**
	 *
	 * @description 获取配置文件具体信息
	 * @author chupp
	 * @date 2018年4月27日
	 * @param key
	 * @return
	 *
	 */
	protected String getSystemStringParam(String key) {
		return systemParams.getProperty(key);
	}
	
	/**
	 * 
	 * @description 登录实名制获取token
	 * @author chupp
	 * @date 2019年1月20日
	 * @return
	 *
	 */
	@SuppressWarnings("unchecked")
	protected Map<String,String> loginSMZ() {
		String httpUrlPrefix = this.getSystemStringParam("httpUrlPrefix");
		String loginUrl = this.getSystemStringParam("loginUrl");
		String userName = this.getSystemStringParam("userName");
		String password = this.getSystemStringParam("password");
		Map<String,Object> map = new HashMap<>();
		map.put("userName", userName);
		map.put("password", password);
		String result = HttpClientUtils.post(httpUrlPrefix + loginUrl, map);
		Map<String,Object> m = (Map<String,Object>) JSONObject.toBean(JSONObject.fromObject(result), Map.class);
		Map<String,String> headers = new HashMap<>();
		headers.put("token", (String) m.get("token"));
		return headers;
	}

	@Override
	public ReturnT<String> execute(String s) throws Exception {
		Map<String, String> m = loginSMZ();
    	if (m.get("token")!=null) {
			// 发送公司数据对接到实名制
/*		List<Long> subContractorList=ifaLaborService.getIdList("buss_sub_contractor");
		if(subContractorList.size()>0){
			boolean flag=subContractorService.getCompanyFromLabor(subContractorList,m);
			if(flag){
				ifaLaborService.del(Constant.BUSS_SUB_CONTRACTOR);
			}
		}*/


			//发送项目数据对接到实名制
			List<Long> projectList = ifaLaborService.getIdList("buss_project_master");
			if (projectList.size() > 0) {
				boolean flag = projectMasterService.getProjectFromLabor(projectList, m);
				if (flag) {
					ifaLaborService.del(Constant.BUSS_PROJECT_MASTER);
				}
			}



			//发送考勤数据对接到实名制
	/*		List<Long> deviceRecordList = ifaLaborService.getIdList("buss_device_record");
			if (deviceRecordList.size() > 0) {
				int lastNumber = ifaLaborService.getLastNumber("buss_device_record");
				deviceRecordService.getDeviceRecordFromLabor(lastNumber, m);
			} else {
				IfaLabor ifaLabor = new IfaLabor("buss_device_record", (long) 0);
				ifaLaborService.insert(ifaLabor);
			}*/

			//发送班组数据对接到实名制
			List<Long> teamList = ifaLaborService.getIdList(Constant.BUSS_TEAM_MASTER);
			if (teamList.size() > 0) {
				boolean flag = teamMasterService.getTeamFromLabor(teamList, m);
				if (flag) {
					ifaLaborService.del(Constant.BUSS_TEAM_MASTER);
				}
			}

			//发送考勤设备数据对接到实名制
			List<Long> deviceList = ifaLaborService.getIdList(Constant.BUSS_DEVICE);
			if (deviceList.size() > 0) {
				boolean flag = deviceService.getDeviceFromLabor(deviceList, m);
				if (flag) {
					ifaLaborService.del(Constant.BUSS_DEVICE);
				}
			}
			//发送工种
		//	List<Long> workKindList = ifaLaborService.getIdList(Constant.BUSS_WORK_KIND);
		//	if (workKindList.size() > 0) {
		//		boolean flag = workKindService.getWorkKindSendSMZ(m);
		//		if (flag) {
		//			ifaLaborService.del(Constant.BUSS_WORK_KIND);
		//		}
		//	}

			// 发送工人项目关系数据对接到实名制
			List<Long> personList = workerMasterService.getPersonFromLabor(m);
			if (personList != null && personList.size() > 0) {


				// 新增到人员图片处理标记
				ifaLaborService.batchInsert(Constant.BUSS_PROJECT_WORKER_IMAGE, personList);
				// 删除已发送人员
				ifaLaborService.del(Constant.BUSS_PROJECT_WORKER, personList);
			}
			//由于分表 修改后
			sendDeviceRecordNew(m);
		}
			return SUCCESS;
    }


	/**
	 * 由于分表 by新的查询考勤数据
	 *
	 * @param m
	 */
	private void sendDeviceRecordNew(Map<String, String> m) throws Exception {
		String pres = DateUtils.getPreMonth();
		// 获取当月的后缀
		String suffix = DateUtils.getTableSuffix();
		// 每个月只有一号处理上个月数据，2号以后不在处理
		// String day = DateUtils.getDay();
		// LOG.info("当天日期=" + day);
		// if (day.equals("01")) {
		// // 处理上个月的数据
		// dealBySuffix(m, pres);
		// }
		// 处理当月数据
		dealBySuffix(m, suffix);
	}

	/**
	 * 处理考勤数据
	 * <p>
	 * Title: dealBySuffix
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 *
	 * @author duanfen
	 * @date 2019年9月2日
	 */
	private void dealBySuffix(Map<String, String> m, String suffix) throws Exception {
		String deviceRecord = Constant.BUSS_DEVICE_RECORD + "_" + suffix;
		// 当月没有数据新增一条当月记录
		Long lastNumber = ifaLaborService.getLastNumber(deviceRecord);
		if (lastNumber == null) {
			lastNumber = 0L;
			IfaLabor ifa = new IfaLabor(deviceRecord, lastNumber);
			this.ifaLaborService.insert(ifa);
		}
			deviceRecordService.getDeviceRecordFromLabor(lastNumber, m, deviceRecord);
	}

	private void sendDeviceRecordOld(Map<String, String> m) throws Exception {
		Long lastNumber = ifaLaborService.getLastNumber(Constant.BUSS_DEVICE_RECORD);
		if (lastNumber != 0) {
			deviceRecordService.getDeviceRecordFromLabor(lastNumber, m, Constant.BUSS_DEVICE_RECORD);
		} else {
			IfaLabor ifaLabor = new IfaLabor(Constant.BUSS_DEVICE_RECORD, (long) 0);
			ifaLaborService.insert(ifaLabor);
		}
	}
}
