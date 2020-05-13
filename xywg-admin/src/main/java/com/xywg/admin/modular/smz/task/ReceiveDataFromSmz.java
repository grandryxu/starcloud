package com.xywg.admin.modular.smz.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.xywg.admin.modular.company.service.IEntryExitHistoryService;
import com.xywg.admin.modular.company.service.ISubContractorService;
import com.xywg.admin.modular.device.service.IDeviceRecordService;
import com.xywg.admin.modular.device.service.IDeviceService;
import com.xywg.admin.modular.project.service.IProjectMasterService;
import com.xywg.admin.modular.project.service.IProjectTrainRecordService;
import com.xywg.admin.modular.project.service.IProjectTrainingService;
import com.xywg.admin.modular.project.service.IProjectWorkerService;
import com.xywg.admin.modular.team.service.ITeamMasterService;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;

/**
 * 
 * @description 实名制接口接收数据定时器
 * 
 * @author chupp
 *
 * @date 2018年7月2日
 *
 */
@Configuration
@EnableScheduling
public class ReceiveDataFromSmz {
	
	@Autowired
	private ISubContractorService subContractorService;
	
	@Autowired
	private IProjectMasterService projectMasterService;
	
	@Autowired
	private IWorkerMasterService workerMasterService;
	
	@Autowired
	private IDeviceService deviceService;
	
	@Autowired
	private IProjectTrainingService projectTrainingService;
	
	@Autowired
	private IProjectTrainRecordService projectTrainRecordService;
	
	@Autowired
	private IDeviceRecordService deviceRecordServiceImpl;
	
	@Autowired
	private IProjectWorkerService projectWorkerService;
	
	@Autowired
	private ITeamMasterService teamMasterService;
	
	@Autowired
	private IEntryExitHistoryService entryExitHistoryService;
	
//	@Scheduled(cron = "0 */1 * * * ?")
	public void personnelData() {
//		try{workerMasterService.savePersonnelFromSMZYC();}catch(Exception e) {e.printStackTrace();}//人员信息（盐城）
//		try{workerMasterService.savePersonnelFromSMZNT();}catch(Exception e) {e.printStackTrace();}//人员信息（南通）
	}
	
//	@Scheduled(cron = "0 */30 * * * ?")
	public void baseData(){
//		try{subContractorService.saveCompanyFromSMZYC();}catch(Exception e) {e.printStackTrace();}//企业信息
//		try{subContractorService.saveCompanyFromSMZCompanyYC();}catch(Exception e) {e.printStackTrace();}//企业信息（盐城企业版）
//		try{projectMasterService.saveProjectFromSMZYC();}catch(Exception e) {e.printStackTrace();}//项目信息（盐城）
//		try{projectMasterService.saveProjectFromSMZNT();}catch(Exception e) {e.printStackTrace();}//项目信息（南通）
//		try{projectMasterService.saveProjectFromSMZCompanyYC();}catch(Exception e) {e.printStackTrace();}//项目信息（盐城企业版）
//		try{deviceService.saveDeviceFromSMZYC();}catch(Exception e) {e.printStackTrace();}//考勤设备（盐城）
//		try{deviceService.saveDeviceFromSMZNT();}catch(Exception e) {e.printStackTrace();}//考勤设备（南通）
//		try{projectTrainingService.saveTrainFromSMZYC();}catch(Exception e) {e.printStackTrace();}//培训信息（盐城）
//		try{projectTrainingService.saveTrainFromSMZNT();}catch(Exception e) {e.printStackTrace();}//培训信息（南通）
	}
	
//	@Scheduled(cron = "0 */5 * * * ?")
	public void bussData(){
//		try{teamMasterService.saveTeamMasterFromSMZYC();}catch(Exception e) {e.printStackTrace();}//班组信息（盐城）
//		try{teamMasterService.saveTeamMasterFromSMZNT();}catch(Exception e) {e.printStackTrace();}//班组信息（南通）
//		try{teamMasterService.saveTeamMasterFromSMZCompanyYC();}catch(Exception e) {e.printStackTrace();}//班组信息（盐城企业版）
//		try{projectTrainingService.saveTrainAttachmentFromSMZYC();}catch(Exception e) {e.printStackTrace();}//培训附件（盐城）
//		try{projectTrainingService.saveTrainAttachmentFromSMZNT();}catch(Exception e) {e.printStackTrace();}//培训附件（南通）
//		try{projectTrainRecordService.saveTrainRecordFromSMZYC();}catch(Exception e) {e.printStackTrace();}//项目培训记录（盐城）
//		try{projectTrainRecordService.saveTrainRecordFromSMZNT();}catch(Exception e) {e.printStackTrace();}//项目培训记录（南通）
//		try{projectWorkerService.saveProjectPersonnelFromSMZYC();}catch(Exception e) {e.printStackTrace();}//项目人员（盐城）
//		try{projectWorkerService.saveProjectPersonnelFromSMZNT();}catch(Exception e) {e.printStackTrace();}//项目人员（南通）
//		try{projectWorkerService.saveProjectPersonnelFromSMZCompanyYC();}catch(Exception e) {e.printStackTrace();}//项目人员（盐城企业版）
//		try{teamMasterService.saveLaborContractFromSMZYC();}catch(Exception e) {e.printStackTrace();}//劳动合同（盐城）
//		try{teamMasterService.saveLaborContractFromSMZNT();}catch(Exception e) {e.printStackTrace();}//劳动合同（南通）
//		try{entryExitHistoryService.savePersonJoinFromSMZYC();}catch(Exception e) {e.printStackTrace();}//进退场（盐城）
//		try{entryExitHistoryService.savePersonJoinFromSMZNT();}catch(Exception e) {e.printStackTrace();}//进退场（南通）
//		try{deviceRecordServiceImpl.saveDeviceRecordFromSMZYC();}catch(Exception e) {e.printStackTrace();}//考勤（盐城）
//		try{deviceRecordServiceImpl.saveDeviceRecordFromSMZNT();}catch(Exception e) {e.printStackTrace();}//考勤（南通）
	}
	
}
