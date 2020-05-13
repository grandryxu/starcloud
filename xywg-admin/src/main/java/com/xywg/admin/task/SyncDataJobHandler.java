package com.xywg.admin.task;

import com.alibaba.fastjson.JSON;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xywg.admin.modular.bad.service.IWorkerBlackListService;
import com.xywg.admin.modular.company.service.*;
import com.xywg.admin.modular.device.service.*;
import com.xywg.admin.modular.project.service.IProjectMasterService;
import com.xywg.admin.modular.project.service.IProjectWorkerService;
import com.xywg.admin.modular.smz.utils.HttpClientUtils;
import com.xywg.admin.modular.system.service.BannerService;
import com.xywg.admin.modular.system.service.ITeamMemberService;
import com.xywg.admin.modular.system.service.IWorkKindService;
import com.xywg.admin.modular.team.service.ITeamMasterService;
import com.xywg.admin.modular.wages.service.IAccountDetailService;
import com.xywg.admin.modular.wages.service.IAccountService;
import com.xywg.admin.modular.worker.service.IContractFileService;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 获取劳务通数据
 */
//@JobHandler(value="SyncDataJobHandler")
@Component
public class SyncDataJobHandler extends IJobHandler {

    @Autowired
    private IProjectMasterService projectMasterService;

    @Autowired
    private ISubContractorService subContractorService;
    @Autowired
    private ITeamMasterService teamMasterService;
    @Autowired
    private ITeamMemberService teamMemberService;
    @Autowired
    private IDeviceRecordService deviceRecordService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IAccountDetailService accountDetailService;
    @Autowired
    private IWorkKindService workKindService;
    @Autowired
    private IWorkerBlackListService workerBlackListService;
    @Autowired
    private IProjectWorkerService projectWorkerService;
    @Autowired
    private IWorkerMasterService workerMasterService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private ICompanyEmployeService companyEmployeService;
    @Autowired
    private IConstructionEvaluateService constructionEvaluateService;
    @Autowired
    private IContractFileService contractFileService;
    @Autowired
    private IContractorWorkerService contractorWorkerService;
    @Autowired
    private IDeviceService deviceService;
    @Autowired
    private IDeviceCommandService deviceCommandService;
    @Autowired
    private IDeviceQrService deviceQrService;
    @Autowired
    private IDeviceRecordExceptionDataService deviceRecordExceptionDataService;
    @Autowired
    private IDeviceTypeService deviceTypeService;
    @Autowired
    private IEmployeeMasterService employeeMasterService;
    @Autowired
    private IEntryExitHistoryService entryExitHistoryService;

    @Value("${sync.url}")
    private String url;


	@Override
    public ReturnT<String> execute(String param) throws Exception {
// 接收数据形式为  {表名：[add:[{},{},{}],update:[],delete:[]]}
        System.out.println("同步数据开始......");
        String jsonMap = HttpClientUtils.get(url);
        Map<String, Object> mapType = JSON.parseObject(jsonMap, Map.class);
        for (String key : mapType.keySet()) {

            syncProjectMaster(mapType,key);
            syncSubContractor(mapType,key);
            syncTeamMaster(mapType,key);
            syncTeamMember(mapType,key);
            syncDeviceRecord(mapType,key);
            syncAccount(mapType,key);
            syncAccountDetail(mapType,key);
            syncWorkKind(mapType,key);
            syncWorkerBlackList(mapType,key);
            syncProjectWorker(mapType,key);
            syncWorkerMaster(mapType,key);
            syncBanner(mapType,key);
            syncCompanyEmploye(mapType,key);
            syncConstructionEvaluate(mapType,key);
            //syncContractFile(mapType,key);
            syncContractorWorker(mapType,key);
            syncDevice(mapType,key);
            syncDeviceCommand(mapType,key);
            syncDeviceQr(mapType,key);
            syncDeviceRecordExceptionData(mapType,key);
            syncDeviceType(mapType,key);
            syncEmployeeMaster(mapType,key);
            syncEntryExitHistory(mapType,key);

        }

        System.out.println("同步数据结束......");


		return SUCCESS;
	}


    private void syncEntryExitHistory(Map<String, Object> mapType, String key) {
        if (mapType.get("buss_entry_exit_history")!=null){
            Map<String, List> bussEntryExitHistoryTable = JSON.parseObject(mapType.get("buss_entry_exit_history").toString(), Map.class);
            if (bussEntryExitHistoryTable.get("ADD").size()!=0){
                List<Object> addList = bussEntryExitHistoryTable.get("ADD");
                entryExitHistoryService.addEntryExitHistory(addList);
            }
        }
    }

    private void syncEmployeeMaster(Map<String, Object> mapType, String key) {
        if (mapType.get("buss_employee_master")!=null){
            Map<String, List> bussEmployeeMasterTable = JSON.parseObject(mapType.get("buss_employee_master").toString(), Map.class);
            if (bussEmployeeMasterTable.get("ADD").size()!=0){
                List<Object> addList = bussEmployeeMasterTable.get("ADD");
                employeeMasterService.addEmployeeMaster(addList);
            }
        }
    }

    private void syncDeviceType(Map<String, Object> mapType, String key) {
        if (mapType.get("buss_device_type")!=null){
            Map<String, List> bussDeviceTypeTable = JSON.parseObject(mapType.get("buss_device_type").toString(), Map.class);
            if (bussDeviceTypeTable.get("ADD").size()!=0){
                List<Object> addList = bussDeviceTypeTable.get("ADD");
                deviceTypeService.addDeviceType(addList);
            }
        }
    }

    private void syncDeviceRecordExceptionData(Map<String, Object> mapType, String key) {
        if (mapType.get("buss_device_record_exception_data")!=null){
            Map<String, List> bussDeviceRecordExceptionDataTable = JSON.parseObject(mapType.get("buss_device_record_exception_data").toString(), Map.class);
            if (bussDeviceRecordExceptionDataTable.get("ADD").size()!=0){
                List<Object> addList=bussDeviceRecordExceptionDataTable.get("ADD");
                deviceRecordExceptionDataService.addDeviceRecordExceptionData(addList);
            }
        }
    }

    private void syncDeviceQr(Map<String, Object> mapType, String key) {
        if (mapType.get("buss_device_qr")!=null){
            Map<String, List> bussDeviceQrTable = JSON.parseObject(mapType.get("buss_device_qr").toString(), Map.class);
            if (bussDeviceQrTable.get("ADD").size()!=0){
                List<Object> addList=bussDeviceQrTable.get("ADD");
                deviceQrService.addDeviceQr(addList);
            }
        }
    }

    private void syncDeviceCommand(Map<String, Object> mapType, String key) {
        if (mapType.get("buss_device_command")!=null){
            Map<String, List> bussDeviceCommandTable = JSON.parseObject(mapType.get("buss_device_command").toString(), Map.class);
            if (bussDeviceCommandTable.get("ADD").size()!=0){
                List<Object> addList = bussDeviceCommandTable.get("ADD");
                deviceCommandService.addDeviceCommand(addList);
            }
        }
    }

    private void syncDevice(Map<String, Object> mapType, String key) {
        if(mapType.get("buss_device")!=null){
            Map<String, List> bussDeviceTable = JSON.parseObject(mapType.get("buss_device").toString(), Map.class);
            if(bussDeviceTable.get("ADD").size()!=0){
                List<Object> addList = bussDeviceTable.get("ADD");
                deviceService.addDevice(addList);
            }
        }
    }

    private void syncContractorWorker(Map<String, Object> mapType, String key) {
        if (mapType.get("buss_contractor_worker") != null){
            Map<String, List> bussContractorCorkerTable = JSON.parseObject(mapType.get("buss_contractor_worker").toString(), Map.class);
            if(bussContractorCorkerTable.get("ADD").size()!=0){
                List<Object> addList = bussContractorCorkerTable.get("ADD");
                contractorWorkerService.addcontractorWorker(addList);
            }
        }
    }

    private void syncContractFile(Map<String, Object> mapType, String key) {
        if (mapType.get("buss_contract_file") != null){
            Map<String, List> bussContractFileTable = JSON.parseObject(mapType.get("buss_contract_file").toString(), Map.class);
            if(bussContractFileTable.get("ADD").size()!=0){
                List<Object> addList = bussContractFileTable.get("ADD");
                contractFileService.addContractFile(addList);
            }
        }
    }

    private void syncConstructionEvaluate(Map<String, Object> mapType, String key) {
        if (mapType.get("buss_construction_evaluate") != null){
            Map<String, List> bussConstructionEvaluateTable = JSON.parseObject(mapType.get("buss_construction_evaluate").toString(), Map.class);
            if(bussConstructionEvaluateTable.get("ADD").size()!=0){
                List<Object> addList = bussConstructionEvaluateTable.get("ADD");
                constructionEvaluateService.addConstructionEvaluate(addList);
            }
        }
    }

    private void syncCompanyEmploye(Map<String, Object> mapType, String key) {
        if (mapType.get("buss_company_employe") != null) {
            Map<String, List> bussCompanyEmployeTable = JSON.parseObject(mapType.get("buss_company_employe").toString(), Map.class);
            if(bussCompanyEmployeTable.get("ADD").size()!=0){
                List<Object> addList = bussCompanyEmployeTable.get("ADD");
                companyEmployeService.addcompanyEmploye(addList);
            }
        }
    }

    private void syncBanner(Map<String, Object> mapType, String key) {
        if (mapType.get("buss_banner") != null){
            Map<String, List> bussBannerTable = JSON.parseObject(mapType.get("buss_banner").toString(), Map.class);
            if (bussBannerTable.get("ADD").size()!=0){
                List<Object> addList  = bussBannerTable.get("ADD");
                bannerService.addBanner(addList);
            }
        }
    }

    private void syncWorkerMaster(Map<String, Object> mapType, String key) {
        if (mapType.get("buss_worker_master") != null) {
            if ("buss_worker_master".equals(key)) {
                Map<String, List> bussWorkMasterTable = JSON.parseObject(mapType.get("buss_worker_master").toString(), Map.class);
                if (bussWorkMasterTable.get("ADD").size()!=0){
                    List<Object> addList = bussWorkMasterTable.get("ADD");
                    workerMasterService.addWorkerMaster(addList);
                }
            }
        }
    }

    private void syncProjectWorker(Map<String, Object> mapType, String key) {
        if (mapType.get("buss_project_worker") != null) {
            if ("buss_project_worker".equals(key)) {
                Map<String, List> bussProjectWorkerTable = JSON.parseObject(mapType.get("buss_project_worker").toString(), Map.class);
                if (bussProjectWorkerTable.get("ADD").size()!=0) {
                    List<Object> addList = bussProjectWorkerTable.get("ADD");
                    projectWorkerService.addBussProjectWorker(addList);
                }
            }
        }
    }

    private void syncWorkerBlackList(Map<String, Object> mapType, String key) {
        if (mapType.get("buss_worker_black_list") != null) {
            if ("buss_worker_black_list".equals(key)) {
                Map<String, List> bussWorkerBlackListTable = JSON.parseObject(mapType.get("buss_worker_black_list").toString(), Map.class);
                if (bussWorkerBlackListTable.get("ADD").size()!=0){
                    List<Object> addList = bussWorkerBlackListTable.get("ADD");
                    workerBlackListService.addWorkerBlackList(addList);
                }
            }
        }
    }

    private void syncWorkKind(Map<String, Object> mapType, String key) {
        if (mapType.get("buss_work_kind") != null) {
            if ("buss_work_kind".equals(key)) {
                Map<String, List> bussWorkKindTable = JSON.parseObject(mapType.get("buss_work_kind").toString(), Map.class);
                for (String addOrUpdate : bussWorkKindTable.keySet()) {
                    if ("ADD".equals(addOrUpdate)) {
                        List<Object> addList = bussWorkKindTable.get("ADD");
                        workKindService.addWorkKind(addList);
                    }
                }
            }
        }
    }

    private void syncAccountDetail(Map<String, Object> mapType, String key) {
        if (mapType.get("buss_account_detail") != null) {
            if ("buss_account_detail".equals(key)) {
                Map<String, List> bussAccountDetailTable = JSON.parseObject(mapType.get("buss_account_detail").toString(), Map.class);
                for (String addOrUpdate : bussAccountDetailTable.keySet()) {
                    if ("ADD".equals(addOrUpdate)) {
                        List<Object> addList = bussAccountDetailTable.get("ADD");
                        accountDetailService.addAccountDetail(addList);
                    }
                }
            }
        }
    }

    private void syncAccount(Map<String, Object> mapType, String key) {
        if (mapType.get("buss_account") != null) {
            if ("buss_account".equals(key)) {
                Map<String, List> bussAccountTable = JSON.parseObject(mapType.get("buss_account").toString(), Map.class);
                for (String addOrUpdate : bussAccountTable.keySet()) {
                    if ("ADD".equals(addOrUpdate)) {
                        List<Object> addList = bussAccountTable.get("ADD");
                        accountService.addAccount(addList);
                    }
                }
            }
        }
    }

    private void syncDeviceRecord(Map<String, Object> mapType, String key) {
        if (mapType.get("buss_device_record") != null) {
            if ("buss_device_record".equals(key)) {
                Map<String, List> bussDeviceRecordTable = JSON.parseObject(mapType.get("buss_device_record").toString(), Map.class);
                for (String addOrUpdate : bussDeviceRecordTable.keySet()) {
                    if ("ADD".equals(addOrUpdate)) {
                        List<Object> addList = bussDeviceRecordTable.get("ADD");
                        deviceRecordService.addDeviceRecord(addList);
                    }
                }
            }
        }
    }

    private void syncTeamMember(Map<String, Object> mapType, String key) {
        if (mapType.get("buss_team_member") != null) {
            if ("buss_team_member".equals(key)) {
                Map<String, List> bussTeamMemberTable = JSON.parseObject(mapType.get("buss_team_member").toString(), Map.class);
                for (String addOrUpdate : bussTeamMemberTable.keySet()) {
                    if ("ADD".equals(addOrUpdate)) {
                        List<Object> addList = bussTeamMemberTable.get("ADD");
                        teamMemberService.addTeamMemberList(addList);
                    }
                }
            }
        }
    }

    private void syncTeamMaster(Map<String, Object> mapType, String key) {
        if (mapType.get("buss_team_master") != null) {
            if ("buss_team_master".equals(key)) {
                Map<String, List> bussTeamMasterTable = JSON.parseObject(mapType.get("buss_team_master").toString(), Map.class);
                for (String addOrUpdate : bussTeamMasterTable.keySet()) {
                    if ("ADD".equals(addOrUpdate)) {
                        List<Object> addList = bussTeamMasterTable.get("ADD");
                        teamMasterService.addTeamMasterList(addList);
                    }
                }
            }
        }
    }

    private void syncSubContractor(Map<String, Object> mapType, String key) {
        if (mapType.get("buss_sub_contractor") != null) {
            if ("buss_sub_contractor".equals(key)) {
                Map<String, List> bussSubContractorTable = JSON.parseObject(mapType.get("buss_sub_contractor").toString(), Map.class);
                for (String addOrUpdate : bussSubContractorTable.keySet()) {
                    if ("ADD".equals(addOrUpdate)) {
                        List<Object> addList = bussSubContractorTable.get("ADD");
                        subContractorService.addContractorList(addList);
                    }
                }
            }
        }
    }

    private void syncProjectMaster(Map<String, Object> mapType, String key) {
        if (mapType.get("buss_project_master") != null) {
            if ("buss_project_master".equals(key)) {
                Map<String, List> bussProjectMasterTable = JSON.parseObject(mapType.get("buss_project_master").toString(), Map.class);
                for (String addOrUpdate : bussProjectMasterTable.keySet()) {
                    if ("ADD".equals(addOrUpdate)) {
                        List<Object> addList = bussProjectMasterTable.get("ADD");
                        projectMasterService.addProjectMasterList(addList);
                    } else if ("UPDATE".equals(addOrUpdate)) {
                        List<Object> updateList = bussProjectMasterTable.get("UPDATE");
                        projectMasterService.updateProjectMasterList(updateList);
                    }
                }
            }
        }

    }

}