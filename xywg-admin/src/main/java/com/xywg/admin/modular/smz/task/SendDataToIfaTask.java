package com.xywg.admin.modular.smz.task;

import com.xywg.admin.modular.company.service.ISubContractorService;
import com.xywg.admin.modular.device.service.IDeviceRecordService;
import com.xywg.admin.modular.device.service.IDeviceService;
import com.xywg.admin.modular.project.service.IProjectMasterService;
import com.xywg.admin.modular.project.service.IProjectTrainingService;
import com.xywg.admin.modular.smz.dao.IfaLaborMapper;
import com.xywg.admin.modular.smz.model.IfaLabor;
import com.xywg.admin.modular.smz.service.IfaLaborService;
import com.xywg.admin.modular.team.service.ITeamMasterService;
import com.xywg.admin.modular.worker.service.IWorkerContractRuleService;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;

/**
 * 给实名制发送数据
 * 
 * @author duanfen
 * @date 2018年4月28日上午11:00:03
 **/

@Configuration
@EnableScheduling
public class SendDataToIfaTask {

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

	//@SuppressWarnings({"unchecked", "rawtypes"})
	//@Scheduled(cron = "0 */1 * * * ?")
	public void record() throws Exception {
//		// 发送公司数据对接到实名制
//		List<Long> subContractorList=ifaLaborService.getIdList("buss_sub_contractor");
//		if(subContractorList.size()>0){
//			boolean flag=subContractorService.getCompanyFromLabor(subContractorList);
//			if(flag){
//				ifaLaborService.del("buss_sub_contractor");
//			}
//		}
//
//
//		//发送项目数据对接到实名制
//		List<Long> projectList=ifaLaborService.getIdList("buss_project_master");
//		if(projectList.size()>0){
//			boolean flag=projectMasterService.getProjectFromLabor(projectList);
//			if(flag){
//				ifaLaborService.del("buss_project_master");
//			}
//		}
//
//
//		//发送班组数据对接到实名制
//		List<Long> teamList = ifaLaborService.getIdList("buss_team_master");
//		if (teamList.size() > 0) {
//			boolean flag = teamMasterService.getTeamFromLabor(teamList);
//			if (flag) {
//				ifaLaborService.del("buss_team_master");
//			}
//		}
//
//		//发送工人项目关系数据对接到实名制
//		List<Long> personList=ifaLaborService.getIdList("buss_project_worker");
//		if(personList.size()>0){
//			boolean flag=workerMasterService.getPersonFromLabor(personList);
//			if(flag){
//				ifaLaborService.del("buss_project_worker");
//			}
//		}
//
//		//发送工人劳动合同数据对接到实名制
//		List<Long> workerContractorList=ifaLaborService.getIdList("buss_worker_contract_rule");
//		if(workerContractorList.size()>0){
//			boolean flag=workerContractRuleService.getWorkeContractorFromLabor(workerContractorList);
//			if(flag){
//				ifaLaborService.del("buss_worker_contract_rule");
//			}
//		}
//
//		//发送考勤设备数据对接到实名制
//		List<Long> deviceList=ifaLaborService.getIdList("buss_device");
//		if(deviceList.size()>0){
//			boolean flag=deviceService.getDeviceFromLabor(deviceList);
//			if(flag){
//				ifaLaborService.del("buss_device");
//			}
//		}
//
////		//发送考勤数据对接到实名制
//		List<Long> deviceRecordList=ifaLaborService.getIdList("buss_device_record");
//		if(deviceRecordList.size()>0){
//			int lastNumber=ifaLaborService.getLastNumber("buss_device_record");
//			boolean b = deviceRecordService.getDeviceRecordFromLabor(lastNumber,map);
//		}else{
//			IfaLabor ifaLabor=new IfaLabor("buss_device_record", (long) 0);
//			ifaLaborService.insert(ifaLabor);
//		}
//
//		//发送培训数据对接到实名制
//		List<Long> trainList=ifaLaborService.getIdList("buss_project_training");
//		if(trainList.size()>0){
//			boolean flag=projectTrainingService.getProjectTrainFromLabor(trainList);
//			if(flag){
//				ifaLaborService.del("buss_project_training");
//			}
//		}
//
//		//发送培训附件数据对接到实名制
//		List<Long> trainFileList=ifaLaborService.getIdList("buss_project_training_file");
//		if(trainFileList.size()>0){
//			boolean flag=projectTrainingService.getProjectTrainFileFromLabor(trainFileList);
//			if (flag){
//				ifaLaborService.del("buss_project_training_file");
//			}
//		}
//
//		//发送培训工人关系数据到实名制
//		List<Long> trainWorkerList=ifaLaborService.getIdList("buss_project_training_person");
//		if(trainWorkerList.size()>0){
//			boolean flag=projectTrainingService.getProjectTrainWorkerFromLabor(trainWorkerList);
//			if(flag){
//				ifaLaborService.del("buss_project_training_person");
//			}
//		}
//
//
//		//发送盐城考勤数据到实名制对接到实名制
//		List<Long> deviceRecordListYC=ifaLaborService.getIdList("buss_device_record_yc");
//		if(deviceRecordListYC.size()>0){
//			int lastNumber=ifaLaborService.getLastNumber("buss_device_record_yc");
//			boolean b = deviceRecordService.getDeviceRecordFromLaborYC(lastNumber);
//		}else {
//			IfaLabor ifaLabor = new IfaLabor("buss_device_record_yc", (long) 0);
//			ifaLaborService.insert(ifaLabor);
//		}
	}
}