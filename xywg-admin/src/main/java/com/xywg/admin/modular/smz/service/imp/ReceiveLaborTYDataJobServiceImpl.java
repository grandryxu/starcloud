package com.xywg.admin.modular.smz.service.imp;

import cn.hutool.json.JSONUtil;
import com.xywg.admin.config.handler.RemoteDTO;
import com.xywg.admin.config.handler.ZbusHandler;
import com.xywg.admin.core.util.DateUtils;
import com.xywg.admin.modular.company.dao.ContractorWorkerMapper;
import com.xywg.admin.modular.company.dao.EntryExitHistoryMapper;
import com.xywg.admin.modular.company.model.ContractorWorker;
import com.xywg.admin.modular.company.model.EntryExitHistory;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.device.dao.DeviceMapper;
import com.xywg.admin.modular.device.dao.DeviceRecordMapper;
import com.xywg.admin.modular.device.model.Device;
import com.xywg.admin.modular.device.model.DeviceRecord;
import com.xywg.admin.modular.device.utils.RedisUtil;
import com.xywg.admin.modular.project.dao.ProjectAddressMapper;
import com.xywg.admin.modular.project.dao.ProjectMasterMapper;
import com.xywg.admin.modular.project.dao.ProjectWorkerMapper;
import com.xywg.admin.modular.project.model.ProjectAddress;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.model.ProjectWorker;
import com.xywg.admin.modular.project.service.impl.ProjectMasterServiceImpl;
import com.xywg.admin.modular.projectSubContractor.dao.ProjectSubContractorMapper;
import com.xywg.admin.modular.projectSubContractor.model.ProjectSubContractor;
import com.xywg.admin.modular.report.dao.DeviceReportMapper;
import com.xywg.admin.modular.smz.model.IfaLabor;
import com.xywg.admin.modular.smz.service.IReceiveLaborTYDataJobService;
import com.xywg.admin.modular.smz.service.IfaLaborService;
import com.xywg.admin.modular.smz.ty.model.HttpClientUtilsLabor;
import com.xywg.admin.modular.system.dao.DeptMapper;
import com.xywg.admin.modular.team.dao.TeamMasterMapper;
import com.xywg.admin.modular.team.model.TeamMaster;
import com.xywg.admin.modular.team.model.TeamMemberShip;
import com.xywg.admin.modular.worker.dao.WorkerMasterMapper;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ReceiveLaborTYDataJobServiceImpl implements IReceiveLaborTYDataJobService {
    @Autowired
    private RedisUtil redis;


    @Resource
    private ProjectMasterMapper projectMasterMapper;
    @Autowired
    private ProjectAddressMapper projectAddressMapper;
    @Autowired
    private ProjectMasterServiceImpl projectMasterService;
    @Resource
    private ProjectSubContractorMapper projectSubContractorMapper;

    @Resource
    private IfaLaborService ifaLaborService;

//    @Resource
//    private DeptMapper deptMapper;
//
//    @Resource
//    private DeviceReportMapper deviceReportMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private TeamMasterMapper teamMasterMapper;

    @Autowired
    private WorkerMasterMapper workerMasterMapper;

    @Autowired
    private ProjectWorkerMapper projectWorkerMapper;

    @Autowired
    private ContractorWorkerMapper contractorWorkerMapper;

    @Autowired
    private DeviceRecordMapper deviceRecordMapper;

    @Autowired
    private EntryExitHistoryMapper entryExitHistoryMapper;

    /**
     * 龙信建筑集团编号
     */
    private static final String ORGANIZATION_CODE = "91320684138774279B";
    //启东组织机构代码
   /* public static final String ORGANIZATION_CODE = "9132068113885448XD";*/

    private static final Log LOG = LogFactory.getLog(ProjectMasterServiceImpl.class);
    private static Properties systemParams = new Properties();
    private static Properties systemParamsTY = new Properties();  //劳务通通用的配置属性
    private static final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    /**
     * 加载配置文件
     */
    static {
        try {
            systemParams.load(ProjectMasterServiceImpl.class.getClassLoader().getResourceAsStream("smz.properties"));
        } catch (IOException e) {
            LOG.error("smz.properties" + "配置文件加载失败");
        }
    }

    /**
     * 劳务通通用的配置属性
     */
    static {
        try {
            systemParamsTY.load(ProjectMasterServiceImpl.class.getClassLoader().getResourceAsStream("laborty.properties"));
        } catch (IOException e) {
            LOG.error("smz.properties" + "配置文件加载失败");
        }
    }


    /**
     * @param key
     * @return
     * @description 获取配置文件具体信息
     * @author chupp
     * @date 2018年4月27日
     */
    protected String getSystemStringParam(String key) {
        return systemParams.getProperty(key);
    }

    /**
     * 获取通用的文件具体信息
     */
    protected String getSystemStringParamLaborTY(String key) {
        return systemParamsTY.getProperty(key);
    }



    @SuppressWarnings("unchecked")
    @Override
    public void saveProjectFromLaborTYNew(Map<String, Object> hashMap) {
        // 获取redis存的项目书
        Object projectId = redis.get(ORGANIZATION_CODE + "project");
        Long id = projectId == null ? 0L : Long.valueOf(String.valueOf(projectId));
        hashMap.put("organizationCode", ORGANIZATION_CODE);
        hashMap.put("id", id);
        String url = getSystemStringParamLaborTY("httpUrlPrefixNew") + getSystemStringParamLaborTY("receiveProject");
        String jsonResult = HttpClientUtilsLabor.post(url, hashMap);
        // 返回数据解析
        Map<String, Object> result = JSONUtil.toBean(jsonResult, Map.class);
        if(result.get("project") != null) {
            // 存在新的项目数据
            JSONArray array = JSONArray.fromObject(result.get("project"));
            List<ProjectMaster> projectList = JSONArray.toList(array, new ProjectMaster(), new JsonConfig());
            Long projectIds = 0L;
            for (ProjectMaster pt : projectList) {
                //通用中对应的项目id
                Long tyId = pt.getId();
                ProjectMaster projectInfo = projectMasterMapper.getProjectByProjectCode(pt.getProjectCode());
                //如果这个项目不存在，则新增
                if(projectInfo == null){
                    //如果是南通的则将是否传数据给实名制置为1
                    // if("3206".equals(pt.getAreaCode().substring(0, 4))){
                    //     pt.setIsSynchro(1);
                    // }
                    //如果不存在新增
                    pt.setCreateUser("laborTY");
                    pt.setCreateDate(new Date());
                    this.projectMasterMapper.insert(pt);
                    //将项目在南通地区下面的项目保存到ifa_labor表中
//                    if("3206".equals(pt.getAreaCode().substring(0, 4))){
//                        IfaLabor ifaLabor = new IfaLabor();
//                        ifaLabor.setId(pt.getId());
//                        ifaLabor.setTableName("buss_project_master");
//                        this.ifaLaborService.insert(ifaLabor);
//                    }
                    // 保存项目公司关系
                    ProjectSubContractor projectSubContractor = new ProjectSubContractor();
                    projectSubContractor.setProjectCode(pt.getProjectCode());
                    projectSubContractor.setOrganizationCode(ORGANIZATION_CODE);
                    projectSubContractor.setContractorType(16);
                    projectSubContractorMapper.insert(projectSubContractor);

                }
                redis.set(ORGANIZATION_CODE + "project",tyId);
            /*    if (projectIds < tyId) {
                    projectIds = tyId;
                }*/
            }
            //redis.set(ORGANIZATION_CODE + "project",projectIds);
        }
    }
/*
    @Override
    public void saveProjectFromLaborTY() {
        // TODO Auto-generated method stub

    }*/

    @SuppressWarnings("unchecked")
    @Override
    public void saveDeviceFromLaborTY(Map<String, Object> mnt) {
        // 获取redis存的设备
        Object deviceId = redis.get(ORGANIZATION_CODE + "device");
        Long id = deviceId == null ? 0L : Long.valueOf(String.valueOf(deviceId));
        mnt.put("organizationCode", ORGANIZATION_CODE);
        mnt.put("id", id);
        String url = getSystemStringParamLaborTY("httpUrlPrefixNew") + getSystemStringParamLaborTY("receiveDevice");
        String jsonResult = HttpClientUtilsLabor.post(url, mnt);
        // 返回数据解析
        Map<String, Object> result = JSONUtil.toBean(jsonResult, Map.class);
        if(result.get("device") != null) {
            // 存在新的项目数据
            JSONArray array = JSONArray.fromObject(result.get("device"));
            List<Device> deviceList = JSONArray.toList(array, new Device(), new JsonConfig());
            Long maxId = 0L;
            Long oldId = 0L;
            for(Device ds : deviceList){
                oldId = ds.getId();
                //根据项目的projectcode去找对应的是否同步实名制标志
                ProjectMaster project = this.projectMasterMapper.getProjectByProjectCode(ds.getProjectCode());
                if(project != null) {
                    //判断设备在项目是否存在
                    int count = this.deviceMapper.getDeviceByProjectCodeAndSn(ds.getSn(), ds.getProjectCode());
                    if(count > 0) {
                        continue;
                    }
                    ds.setCreateDate(new Date());
                    ds.setCreateUser("laborTY");
                    // 保存通用设备数据
                    this.deviceMapper.insert(ds);
                    //如果是要传送给实名制的话，则将数据存到ifa_labor
//                    if(project.getIsSynchro() == 1){
//                        IfaLabor ifaLabor = new IfaLabor();
//                        ifaLabor.setId(ds.getId());
//                        ifaLabor.setTableName("buss_device");
//                        this.ifaLaborService.insert(ifaLabor);
//                    }
                    redis.set(ORGANIZATION_CODE + "device",oldId);
                   /* if (maxId < oldId) {
                        maxId = oldId;
                    }*/
                }
            }
            //redis.set(ORGANIZATION_CODE + "device",maxId);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void saveTeamMasterFromLaborTY(Map<String, Object> mnt) {
	//redis.set(ORGANIZATION_CODE + "team",11043);
        // 获取redis存的设备
        Object deviceId = redis.get(ORGANIZATION_CODE + "team");
        Long teamId = deviceId == null ? 0L : Long.valueOf(String.valueOf(deviceId));
        mnt.put("organizationCode", ORGANIZATION_CODE);
        mnt.put("id", teamId);
        //根据关系主键表的项目去取考勤设备信息
        String url = getSystemStringParamLaborTY("httpUrlPrefixNew") + getSystemStringParamLaborTY("receiveTeamMaster");
        String jsonResult = HttpClientUtilsLabor.post(url, mnt);
        if(StringUtils.isBlank(jsonResult)) {
            return;
        }
        Map<String, Object> result = JSONUtil.toBean(jsonResult, Map.class);
        if(result.get("data") != null) {
            // 存在新的项目数据
            JSONArray array = JSONArray.fromObject(result.get("data"));
            //存在新的班组信息
            if (array.size() > 0) {
                List<TeamMaster> teamList = JSONArray.toList(array, new TeamMaster(), new JsonConfig());
                Long id = 0L;
                Long maxId = 0L;
                for(TeamMaster tm : teamList){
                    id =Long.valueOf(tm.getId());
                    //保存通用的班组id跟启东的班组id的对应关系
                  /*  if (maxId < id) {
                        maxId = id;
                    }*/
                    ProjectMaster project = this.projectMasterMapper.getProjectByProjectCode(tm.getProjectCode());

                    //判断项目是否存在
                    if(project != null) {
                        //判断班组是否存在
                        TeamMaster team = this.teamMasterMapper.getByName(tm.getTeamName(), tm.getProjectCode());
                        if(team != null) {
                            continue;
                        }
                        tm.setCreateDate(new java.util.Date());
                        tm.setCreateUser("laborTY");
                        this.teamMasterMapper.insert(tm);
                        //如果是要传送给实名制，将数据存到ifa_labor
//                        if(project.getIsSynchro() == 1){
//                            IfaLabor ifaLabor = new IfaLabor();
//                            ifaLabor.setId(Long.valueOf(tm.getId()));
//                            ifaLabor.setTableName("buss_team_master");
//                            this.ifaLaborService.insert(ifaLabor);
//                        }

                        redis.set(ORGANIZATION_CODE + "team",id);
                    }

                }
               //    redis.set(ORGANIZATION_CODE + "team",maxId);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void saveWorkerFromLaborTY(Map<String, Object> mnt) {
       // redis.set(ORGANIZATION_CODE + "worker",101080);
    //    redis.set(ORGANIZATION_CODE + "worker",113810);
        // 获取redis存的设备
        Object workerId = redis.get(ORGANIZATION_CODE + "worker");
//		Long workerMasterId= 0L;
        Long workerMasterId = workerId == null ? 0L : Long.valueOf(String.valueOf(workerId));
        mnt.put("organizationCode", ORGANIZATION_CODE);
        mnt.put("id", workerMasterId);
        //根据关系主键表的项目去取考勤设备信息
        String url = getSystemStringParamLaborTY("httpUrlPrefixNew") + getSystemStringParamLaborTY("receiveWorkerMaster");
        String jsonResult = HttpClientUtilsLabor.post(url, mnt);
        if(StringUtils.isBlank(jsonResult)) {
            return;
        }
        Map<String, Object> result = JSONUtil.toBean(jsonResult, Map.class);
        if(result.get("data") != null) {

            // 存在新的项目数据
            JSONArray array = JSONArray.fromObject(result.get("data"));
            List<WorkerMaster> wlist = JSONArray.toList(array, new WorkerMaster(), new JsonConfig());
            Long id = 0L;
            Long maxId = 0L;

            for(WorkerMaster wm : wlist){
                id = wm.getId();
                WorkerMaster w = new WorkerMaster();
                //根据身份证号查询看这个人员基础信息是否已经有了
                WorkerMaster chwm = workerMasterMapper.getWorkerByIdCard(wm.getIdCardNumber(), wm.getIdCardType());
                if(chwm == null){
                    w = wm;
                    w.setCreateDate(new Date());
                    w.setCreateUser("laborTY");
                    this.workerMasterMapper.insert(w);
                    redis.set(ORGANIZATION_CODE + "worker",id);
                }
             /*   if (maxId < id) {
                    maxId = id;
                }*/
            }
            //redis.set(ORGANIZATION_CODE + "worker",maxId);
        }
    }

    @Override
    public void saveProjectWorkerFromLaborTY(Map<String, Object> mnt) throws Exception  {
       // redis.set(ORGANIZATION_CODE + "pw",101080);

	//redis.set(ORGANIZATION_CODE + "pw",113810);

        Object deviceId = redis.get(ORGANIZATION_CODE + "pw");
        Long teamId = deviceId == null ? 0L : Long.valueOf(String.valueOf(deviceId));
        mnt.put("organizationCode", ORGANIZATION_CODE);
        mnt.put("id", teamId);
        String url = getSystemStringParamLaborTY("httpUrlPrefixNew") + getSystemStringParamLaborTY("receiveProjectWorker");
        String jsonResult = HttpClientUtilsLabor.post(url, mnt);
        if(StringUtils.isBlank(jsonResult)){
            return;
        }
        Map<String, Object> result = JSONUtil.toBean(jsonResult, Map.class);
        if(result.get("data") != null) {
            // 存在新的项目数据
            JSONArray array = JSONArray.fromObject(result.get("data"));
            if(array.size() > 0){
                List<ProjectWorker> plist = JSONArray.toList(array, new ProjectWorker(), new JsonConfig());
                Long id = 0L;
                Long maxId = 0L;//用来存放工人项目关系的最大值
                for(ProjectWorker ps : plist){
                    String projectCode = ps.getProjectCode();
                    //根据项目的projectcode去找对应的是否同步实名制标志
                    ProjectMaster project = this.projectMasterMapper.getProjectByProjectCode(projectCode);
                    id = ps.getId();
                    //如果有对应的班组主键关系
                    if (ps.getTeamSysNo()!=null&& ps.getTeamSysNo()!=0) {
                        TeamMaster chTm = teamMasterMapper.selectByProjectCode(projectCode, ps.getTeamSysNo());
                        if (chTm == null) {
                            LOG.info("没有对应的班组信息");
                        }
                    }
                    //根据传的人员证件号码以及证件类型判断人有没有
                    WorkerMaster chWm ;
                    chWm = workerMasterMapper.getWorkerByIdCard(ps.getIdCardNumber(), Integer.valueOf(ps.getIdCardType()));
                    //如果存在，则证明人之前给过了
                    if(chWm == null){
                        LOG.info("没有对应的身份证号为"+ps.getIdCardNumber()+"通用人员基础信息");
                        saveWorkerFromLaborTYByOne(mnt,ps.getIdCardNumber());

                    }
                    redis.set(ORGANIZATION_CODE + "pw",id);
                    if(this.projectWorkerMapper.getPersonInfo(projectCode, Integer.valueOf(ps.getIdCardType()), ps.getIdCardNumber()) != null) {
                        continue;
                    }
                   ps.setProjectCode(project.getProjectCode());
                    this.projectWorkerMapper.insert(ps);
                    //如果是要传送给实名制的，则将数据存到ifa_labor表
                 /*   if(project.getIsSynchro() == 1){
                        IfaLabor ifaLabor = new IfaLabor();
                        ifaLabor.setId(Long.valueOf(ps.getId()));
                        ifaLabor.setTableName("buss_project_worker");
                        this.ifaLaborService.insert(ifaLabor);
                    }*/
                    //人员班组关系保存
                    TeamMemberShip tms = new TeamMemberShip();
                    tms.setIdCardType(1);
                    tms.setIdCardNumber(ps.getIdCardNumber());
                    tms.setTeamSysNo(Integer.valueOf(ps.getTeamSysNo()));
                    tms.setIsDel(0);
                    tms.setIsSign(0);
                    tms.setTeamWorkerType(ps.getTeamWorkerType());
                    teamMasterMapper.addMember(tms);

                    //人员进退场关系
                    EntryExitHistory eeh = new EntryExitHistory(projectCode,ps.getOrganizationCode(), ps.getIdCardType(), ps.getIdCardNumber());
                    //判断是进场或退场
                    if(ps.getJoinStatus() ==2) {
                        eeh.setType(1);
                        eeh.setDate(ps.getEntryTime());
                    }else {
                        eeh.setType(0);
                        eeh.setDate(ps.getExitTime());
                    }
                    entryExitHistoryMapper.insert(eeh);
                    //保存公司工人关系
                    try {
                        if (contractorWorkerMapper.getByIdCardAndOrganization(chWm.getIdCardNumber(), 1, ps.getOrganizationCode()) == null) {
                            ContractorWorker contractorWorker = new ContractorWorker();
                            contractorWorker.setIdCardType(chWm.getIdCardType());
                            contractorWorker.setIdCardNumber(chWm.getIdCardNumber());
                            contractorWorker.setWorkName(chWm.getWorkerName());
                            contractorWorker.setOrganizationCode(ps.getOrganizationCode());
                            contractorWorkerMapper.insert(contractorWorker);
                        }
                    } catch (Exception e) {
                        LOG.info("没有公司工人关系信息");
                    }
                 /*   if (maxId < id ) {
                        maxId = id;
                    }*/
                }
               // redis.set(ORGANIZATION_CODE + "pw",maxId);
            }
        }
    }

/*    @Override
    public void savePersonJoinFromLaborTY(Map<String, Object> mnt) {

    }*/
    @SuppressWarnings("unchecked")
    @Override
    public void saveDeviceRecordFromLaborTY(Map<String, Object> mnt)  throws Exception{
     /*   String suffix = DateUtils.getDay();
        if(suffix.equals("01")) {
                redis.set(ORGANIZATION_CODE + "record",1);
        }*/
	//	redis.set(ORGANIZATION_CODE + "record","0");
   //     redis.set(ORGANIZATION_CODE + "record",229591);
        Object deviceId = redis.get(ORGANIZATION_CODE + "record");


        Long recordId = deviceId == null ? 0L : Long.valueOf(String.valueOf(deviceId));

        String day= DateUtils.getDay();
        LOG.info("当天日期="+day);
        if (day.equals("01") && recordId > 5000000) {
            recordId = 0L;
        }
        mnt.put("organizationCode", ORGANIZATION_CODE);
        mnt.put("id", recordId);
        String url = getSystemStringParamLaborTY("httpUrlPrefixNew") + getSystemStringParamLaborTY("receiveDeviceRecord");
        String jsonResult = HttpClientUtilsLabor.post(url, mnt);
        if(StringUtils.isBlank(jsonResult)){
            return;
        }
        Map<String, Object> result = JSONUtil.toBean(jsonResult, Map.class);
        if(result.get("data") != null) {
            // 存在新的项目数据
            JSONArray array = JSONArray.fromObject(result.get("data"));
            //根据关系主键表的项目去取考勤设备信息
            if(array.size() > 0){
                Long id = 0L;
                Long maxId = 0L;

                List<DeviceRecord> deviceList = JSONArray.toList(array, new DeviceRecord(), new JsonConfig());
                for(DeviceRecord dr : deviceList){
                    id = dr.getId();

                    //先看对应的项目下面的人员的基础信息是否存在
                    WorkerMaster chWm = workerMasterMapper.getWorkerByIdCard(dr.getIdCardNumber(),dr.getIdCardType());
                    if(chWm == null){
                        LOG.info("没有对应的身份证号为"+dr.getIdCardNumber()+"通用人员基础信息");
                        saveWorkerFromLaborTYByOne(mnt,dr.getIdCardNumber());


                    }
                    //根据设备序列号看考勤设备在不在
//					Device device = deviceMapper.getByDeviceSn(dr.getDeviceSn());
//					if(device == null){
//						throw new RuntimeException("没有对应的设备序列号为"+dr.getDeviceSn()+"的考勤设备");
//					}
                    //根据身份证号以及项目编号查询人员是否存在
                    List<Map<String, Object>> chProjectWorker = projectWorkerMapper.getForFaceUse(
                            (String) dr.getProjectCode(), dr.getIdCardType(), dr.getIdCardNumber());
                    if (chProjectWorker.size() == 0) {

                       // throw new RuntimeException("没有对应的项目为"+dr.getProjectCode()+"的身份证号为"+dr.getIdCardNumber()+"的项目人员信息");

                        saveProjectWorkerFromLaborTYOne(mnt,dr.getIdCardNumber(),dr.getProjectCode());
                    }
                    dr.setCreateDate(new Date());
                    dr.setCreateUser("laborTY");
                    deviceRecordMapper.insert(dr);
                    redis.set(ORGANIZATION_CODE + "record",id);
                  /*  if (maxId < id) {
                        maxId = id;
                    }*/
                }
             //   redis.set(ORGANIZATION_CODE + "record",maxId);
            }
        }
    }




    public void saveProjectAddress(Map<String, Object> hashMap) {
        // 获取redis存的项目地址
        Object projectId = redis.get(ORGANIZATION_CODE + "projectAddress");
        Long id = projectId == null ? 0L : Long.valueOf(String.valueOf(projectId));
        hashMap.put("organizationCode", ORGANIZATION_CODE);
        hashMap.put("id", id);
        String url = getSystemStringParamLaborTY("httpUrlPrefixNew") + getSystemStringParamLaborTY("receiveProjectAddress");
        String jsonResult = HttpClientUtilsLabor.post(url, hashMap);
        if (StringUtils.isBlank(jsonResult)) {
            return;
        }
        // 返回数据解析
        Map<String, Object> result = JSONUtil.toBean(jsonResult, Map.class);
        if(result.get("projectAddress")!=null){
            JSONArray array = JSONArray.fromObject(result.get("projectAddress"));
            if (array.size()>0){
                Long ids = 0L;
                Long maxId=0L;
                List<ProjectAddress> projectAddressList=JSONArray.toList(array,new ProjectAddress(),new JsonConfig());
                for (ProjectAddress projectAddress : projectAddressList) {
                    ids=projectAddress.getId();
                   ProjectAddress projectAddressMap = projectAddressMapper.getProjectAddressByProjectCode(projectAddress.getProjectCode());
                   if (projectAddressMap==null){
                       projectAddressMap.setCreateDate(new Date());
                       projectAddressMap.setCreateUser("laborTY");
                       projectAddressMapper.insert(projectAddress);
                       if (maxId < ids) {
                           maxId = ids;
                       }
                   }
                }
                redis.set(ORGANIZATION_CODE + "projectAddress",maxId);
            }
        }

        //  redis.set();
    }


    /**
     * 获取单个人员信息
     * @param mnt
     * @param idCardNumber
     */
    @SuppressWarnings("unchecked")
    public void saveWorkerFromLaborTYByOne(Map<String, Object> mnt,String idCardNumber) {
        mnt.put("organizationCode", ORGANIZATION_CODE);
        mnt.put("idCardNumber", idCardNumber);
        //根据关系主键表的项目去取考勤设备信息
        String url = getSystemStringParamLaborTY("httpUrlPrefixNew") + getSystemStringParamLaborTY("receiveWorkerMasterOne");
        String jsonResult = HttpClientUtilsLabor.post(url, mnt);
        if(StringUtils.isBlank(jsonResult)) {
            return;
        }
        Map<String, Object> result = JSONUtil.toBean(jsonResult, Map.class);
        if(result.get("data") != null) {
            // 存在新的项目数据
            JSONArray array = JSONArray.fromObject(result.get("data"));
            List<WorkerMaster> wlist = JSONArray.toList(array, new WorkerMaster(), new JsonConfig());
            for(WorkerMaster wm : wlist){
                //根据身份证号查询看这个人员基础信息是否已经有了
                if(workerMasterMapper.getWorkerByIdCard(wm.getIdCardNumber(), wm.getIdCardType()) == null){
                    wm.setCreateDate(new Date());
                    wm.setCreateUser("laborTY");
                    this.workerMasterMapper.insert(wm);
                }
            }
        }
    }



    /**
     * 获取单个项目人员关系
     * @param mnt
     * @param idCardNumber
     * @param projectCode
     * @throws Exception
     */
    public void saveProjectWorkerFromLaborTYOne(Map<String, Object> mnt,String idCardNumber,String projectCode) throws Exception  {
        mnt.put("organizationCode", ORGANIZATION_CODE);
        mnt.put("idCardNumber", idCardNumber);
        mnt.put("projectCode", projectCode);
        String url = getSystemStringParamLaborTY("httpUrlPrefixNew") + getSystemStringParamLaborTY("receiveProjectWorkerOne");
        String jsonResult = HttpClientUtilsLabor.post(url, mnt);
        if(StringUtils.isBlank(jsonResult)){
            return;
        }
        Map<String, Object> result = JSONUtil.toBean(jsonResult, Map.class);
        if(result.get("data") != null) {
            // 存在新的项目数据
            JSONArray array = JSONArray.fromObject(result.get("data"));
            if(array.size() > 0){
                List<ProjectWorker> plist = JSONArray.toList(array, new ProjectWorker(), new JsonConfig());
                for(ProjectWorker ps : plist){
                    //根据项目的projectcode去找对应的是否同步实名制标志
                    ProjectMaster project = this.projectMasterMapper.getProjectByProjectCode(projectCode);
                    //如果有对应的班组主键关系
                    if(ps.getTeamSysNo() != null && ps.getTeamSysNo() != 0) {
                        List<TeamMaster> chTm = teamMasterMapper.selectByProjectCode1(projectCode,ps.getTeamSysNo());
                        if(chTm.size() > 0 ){
                            ps.setTeamSysNo(chTm.get(0).getTeamSysNo());
                        }
                    }
                    //根据传的人员证件号码以及证件类型判断人有没有
                    WorkerMaster chWm = workerMasterMapper.getWorkerByIdCard(ps.getIdCardNumber(), Integer.valueOf(ps.getIdCardType()));
                    //如果人员基本信息不存在，重新拉取单个人员信息
                    if(chWm == null){
                        saveWorkerFromLaborTYByOne(mnt,ps.getIdCardNumber());
                    }
                    if(this.projectWorkerMapper.getProjectWorkerInfoBy(projectCode, Integer.valueOf(ps.getIdCardType()), ps.getIdCardNumber()).size() > 0) {
                        return;
                    }
                    ps.setProjectCode(project.getProjectCode());
                    this.projectWorkerMapper.insert(ps);
                    //如果是要传送给实名制的，则将数据存到ifa_labor表
//					if(project.getIsSynchro() == 1){
//						IfaLabor ifaLabor = new IfaLabor();
//						ifaLabor.setId(Long.valueOf(ps.getId()));
//						ifaLabor.setTableName("buss_project_worker");
//						this.ifaLaborService.insert(ifaLabor);
//					}
                    //人员班组关系保存
                    TeamMemberShip tms = new TeamMemberShip();
                    tms.setIdCardType(1);
                    tms.setIdCardNumber(ps.getIdCardNumber());
                    tms.setTeamSysNo(Integer.valueOf(ps.getTeamSysNo()));
                    tms.setIsDel(0);
                    tms.setIsSign(0);
                    tms.setTeamWorkerType(ps.getTeamWorkerType());
                    teamMasterMapper.addMember(tms);
                    //人员进退场关系
                    EntryExitHistory eeh = new EntryExitHistory(projectCode,ps.getOrganizationCode(), ps.getIdCardType(), ps.getIdCardNumber());
                    //判断是进场或退场
                    if(ps.getJoinStatus() ==2) {
                        eeh.setType(1);
                        eeh.setDate(ps.getEntryTime());
                    }else {
                        eeh.setType(0);
                        eeh.setDate(ps.getExitTime());
                    }
                    entryExitHistoryMapper.insert(eeh);
                    //保存公司工人关系
                    if (contractorWorkerMapper.getByIdCardAndOrganization(chWm.getIdCardNumber(), 1, ps.getOrganizationCode()) == null) {
                        ContractorWorker contractorWorker = new ContractorWorker();
                        contractorWorker.setIdCardType(chWm.getIdCardType());
                        contractorWorker.setIdCardNumber(chWm.getIdCardNumber());
                        contractorWorker.setWorkName(chWm.getWorkerName());
                        contractorWorker.setOrganizationCode(ps.getOrganizationCode());
                        contractorWorkerMapper.insert(contractorWorker);
                    }
                }
            }
        }
    }
}
