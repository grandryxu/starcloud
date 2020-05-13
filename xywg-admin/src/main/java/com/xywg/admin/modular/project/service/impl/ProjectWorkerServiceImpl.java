package com.xywg.admin.modular.project.service.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.util.DateUtil;
import com.xywg.admin.modular.health.model.WorkerHealth;
import com.xywg.admin.modular.health.service.IWorkerHealthService;
import com.xywg.admin.modular.project.model.*;
import com.xywg.admin.modular.projectSubContractor.dao.ProjectSubContractorMapper;
import com.xywg.admin.modular.projectSubContractor.model.ProjectSubContractor;
import com.xywg.admin.modular.smz.dao.SmzLwtMapper;
import com.xywg.admin.modular.smz.model.ProjectPersonnelSmz;
import com.xywg.admin.modular.smz.model.SmzCallBack;
import com.xywg.admin.modular.smz.model.SmzLwt;
import com.xywg.admin.modular.smz.utils.HttpClientUtils;
import com.xywg.admin.modular.system.service.AccountProjectService;
import com.xywg.admin.modular.worker.model.ContractFile;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.company.dao.ContractorWorkerMapper;
import com.xywg.admin.modular.company.dao.SubContractorMapper;
import com.xywg.admin.modular.company.model.ContractorWorker;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.company.model.dto.ContractorWorkerDto;
import com.xywg.admin.modular.project.dao.ProjectMasterMapper;
import com.xywg.admin.modular.project.dao.ProjectWorkerMapper;
import com.xywg.admin.modular.project.model.vo.ProjectPositionVo;
import com.xywg.admin.modular.project.service.IProjectWorkerService;
import com.xywg.admin.modular.system.dao.DeptMapper;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.team.dao.TeamMasterMapper;
import com.xywg.admin.modular.team.model.TeamMaster;
import com.xywg.admin.modular.team.model.TeamMemberShip;
import com.xywg.admin.modular.worker.dao.WorkerMasterMapper;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * <p>
 * 项目中工人信息 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-28
 */
@Service
public class ProjectWorkerServiceImpl extends ServiceImpl<ProjectWorkerMapper, ProjectWorker> implements IProjectWorkerService {
    @Autowired
    private ProjectSubContractorMapper projectSubContractorMapper;
    @Autowired
    private ProjectWorkerMapper mapper;
    @Resource
    private ProjectMasterMapper projectMasterMapper;
    @Autowired
    private TeamMasterMapper teamMapper;
    @Resource
    private DeptMapper deptMapper;
    @Autowired
    private ContractorWorkerMapper contractorWorkerMapper;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private WorkerMasterMapper workerMapper;

    @Autowired
    private SubContractorMapper subContractorMapper;
    @Autowired
    private IWorkerMasterService workerMasterService;
    @Autowired
    private WorkerMasterMapper workerMasterMapper;

    @Autowired
    private SmzLwtMapper smzLwtMapper;

    @Autowired
    private AccountProjectService accountProjectService;

    @Autowired
    private IWorkerHealthService workerHealthService;

    private static final Log LOG = LogFactory.getLog(ProjectWorkerServiceImpl.class);
    private static Properties systemParams = new Properties();

    /**
     * 加载配置文件
     */
    static {
        try {
            systemParams.load(ProjectWorkerServiceImpl.class.getClassLoader().getResourceAsStream("smz.properties"));
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

//    /**
//     * @return
//     * @description 登录实名制获取token
//     * @author chupp
//     * @date 2018年4月27日
//     */
//    @SuppressWarnings("unchecked")
//    protected Map<String, String> loginSMZ() {
//        String httpUrlPrefix = this.getSystemStringParam("httpUrlPrefix");
//        String loginUrl = this.getSystemStringParam("loginUrl");
//        String userName = this.getSystemStringParam("userName");
//        String password = this.getSystemStringParam("password");
//        Map<String, Object> map = new HashMap<>();
//        map.put("userName", userName);
//        map.put("password", password);
//        String result = HttpClientUtils.post(httpUrlPrefix + loginUrl, map);
//        Map<String, Object> m = (Map<String, Object>) net.sf.json.JSONObject.toBean(net.sf.json.JSONObject.fromObject(result), Map.class);
//        Map<String, String> headers = new HashMap<>();
//        headers.put("token", (String) m.get("token"));
//        return headers;
//    }
//
//    /**
//     * @return
//     * @description 登录实名制获取token
//     * @author chupp
//     * @date 2018年4月27日
//     */
//    @SuppressWarnings("unchecked")
//    protected Map<String, String> loginSMZYC() {
//        String httpUrlPrefix = this.getSystemStringParam("httpUrlPrefixYC");
//        String loginUrl = this.getSystemStringParam("loginUrlYC");
//        String userName = this.getSystemStringParam("userNameYC");
//        String password = this.getSystemStringParam("passwordYC");
//        Map<String, Object> map = new HashMap<>();
//        map.put("userName", userName);
//        map.put("password", password);
//        String result = HttpClientUtils.post(httpUrlPrefix + loginUrl, map);
//        Map<String, Object> m = (Map<String, Object>) net.sf.json.JSONObject.toBean(net.sf.json.JSONObject.fromObject(result), Map.class);
//        Map<String, String> headers = new HashMap<>();
//        headers.put("token", (String) m.get("token"));
//        return headers;
//    }

    /**
     * 查询该关系下是否存在班组 若有删除拒绝
     *
     * @param projectSubContractorId
     * @return
     * @author 蔡伟
     */
    @Override
    public List<ProjectWorker> getProjectWorkersByProjectSubContractorId(Long projectSubContractorId) {
        return this.baseMapper.getProjectWorkersByProjectSubContractorId(projectSubContractorId);
    }

    /**
     * @description 获取实名制项目人员关系（盐城）
     * @author chupp
     * @date 2018年7月5日
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void saveProjectPersonnelFromSMZYC(Map<String, String> myc) {
        // 获取实名制工人、项目关系信息
        String jsonResult = HttpClientUtils.post(
                getSystemStringParam("httpUrlPrefixYC") + getSystemStringParam("saveProjectPersonnelFromSMZYC"),
                new HashMap<String, Object>(), myc);
        Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
        // 存在新的工人、项目关系信息
        if (map.get("workerproject") != null) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("workerproject"));
            List<ProjectPersonnelSmz> ppsList = JSONArray.toList(jsonArray, new ProjectPersonnelSmz(), new JsonConfig());
            String id = "0";
            List<SmzLwt> slList = smzLwtMapper.getLwtIdBySmzId(0, "Company");
            if (slList.size() == 0) throw new RuntimeException("实名制接口企业数据为空");
            SubContractor subContractor = subContractorMapper.selectById(slList.get(0).getLwtId());
            if (subContractor == null) throw new RuntimeException("实名制接口企业数据对象为空");
            if (ppsList.size() > 0) {
                for (ProjectPersonnelSmz pps : ppsList) {
                    long smzId = Long.parseLong(pps.getId());
                    //人员项目关系保存
                    ProjectWorker pw = new ProjectWorker();
                    slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(pps.getProjectId()), "ProjectYC");
                    if (slList.size() == 0) throw new RuntimeException("实名制接口项目人员所属项目为空");
                    Map<String, Object> chProject = projectMasterMapper.selectById(slList.get(0).getLwtId());
                    if (chProject == null) throw new RuntimeException("实名制接口项目人员所属项目对象为空");
                    pw.setProjectCode((String) chProject.get("projectCode"));
                    pw.setOrganizationCode(subContractor.getOrganizationCode());
                    slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(pps.getClassid()), "ClassYC");
                    if (slList.size() == 0) throw new RuntimeException("实名制接口项目人员所属班组为空");
                    TeamMaster chTm = teamMapper.selectById(slList.get(0).getLwtId());
                    if (chTm == null) throw new RuntimeException("实名制接口项目人员所属班组对象为空");
                    pw.setTeamSysNo(chTm.getTeamSysNo());
                    pw.setIdCardType("1");
                    slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(pps.getUserId()), "PersonnelYC");
                    if (slList.size() == 0) throw new RuntimeException("实名制接口项目人员所属工人为空");
                    WorkerMaster chWm = workerMasterMapper.selectById(slList.get(0).getLwtId());
                    if (chWm == null) throw new RuntimeException("实名制接口项目人员所属工人对象为空");
                    pw.setIdCardNumber(chWm.getIdCardNumber());
                    if ("待进场".equals(pps.getJoinStatus())) {
                        pw.setJoinStatus(1);
                    } else if ("进场".equals(pps.getJoinStatus())) {
                        pw.setJoinStatus(2);
                    } else if ("退场".equals(pps.getJoinStatus())) {
                        pw.setJoinStatus(3);
                    }
                    pw.setWorkTypeCode(chWm.getWorkTypeCode());
                    pw.setHasContract(0);
                    pw.setWorkerRole(2);
                    pw.setIsDel(0);
                    this.baseMapper.insert(pw);
                    workerMasterService.inputFaceAndProjectCode(String.valueOf(slList.get(0).getLwtId()), (String) chProject.get("projectCode"));
                    //人员班组关系保存
                    TeamMemberShip tms = new TeamMemberShip();
                    tms.setIdCardType(1);
                    tms.setIdCardNumber(chWm.getIdCardNumber());
                    tms.setTeamSysNo(chTm.getTeamSysNo());
                    tms.setIsDel(0);
                    tms.setIsSign(0);
                    tms.setTeamWorkerType(1);
                    teamMapper.addMember(tms);
                    //保存公司工人关系
                    if (contractorWorkerMapper.getByIdCardAndOrganization(chWm.getIdCardNumber(), 1, subContractor.getOrganizationCode()) == null) {
                        ContractorWorker contractorWorker = new ContractorWorker();
                        contractorWorker.setIdCardType(1);
                        contractorWorker.setIdCardNumber(chWm.getIdCardNumber());
                        contractorWorker.setWorkName(chWm.getWorkerName());
                        contractorWorker.setOrganizationCode(subContractor.getOrganizationCode());
                        contractorWorker.setContractorName(subContractor.getCompanyName());
                        contractorWorkerMapper.insert(contractorWorker);
                    }
                    if (Long.parseLong(id) < smzId) {
                        id = String.valueOf(smzId);
                    }
                }
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("code", "1");
                m.put("no", id);
                // 响应保存成功信息
                HttpClientUtils.post(getSystemStringParam("httpUrlPrefixYC")
                        + getSystemStringParam("saveProjectPersonnelFromSMZResponseYC"), m, myc);
            }
        }
    }

    /**
     * @param idCardNumber
     * @param idCardType
     * @return
     * @description 获取工人在建项目列表（考勤第一接口）
     * @author chupp
     * @date 2018年5月30日
     * @see com.xywg.admin.modular.project.service.IProjectWorkerService#getProjectList(java.lang.String, java.lang.String)
     */
    @Override
    public List<Map<String, Object>> getProjectWorkerList(String idCardNumber, String idCardType) {
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        //获取工人进场项目列表
        List<ProjectWorker> projectWorkerList = this.baseMapper.getProjectWorkerListFromDevice(idCardNumber, idCardType);
        for (ProjectWorker pw : projectWorkerList) {
            Map<String, Object> map = new HashMap<String, Object>();
            ProjectMaster pm = projectMasterMapper.getProjectByProjectCode(pw.getProjectCode());
            if (pm != null && 0 == pm.getIsDel() && 1 == pm.getStatus() && "3".equals(pm.getProjectStatus())) {
                if (pm.getDeviceType() == 1 || pm.getDeviceType() == 2 || pm.getDeviceType() == 5 || pm.getDeviceType() == 6) {
                    map.put("projectId", pm.getId());
                    map.put("deviceType", pm.getDeviceType());
                    map.put("projectCode", pm.getProjectCode());
                    map.put("projectName", pm.getProjectName());
                    List<ProjectPositionVo> positionList = projectMasterMapper.getProjectPositionList(pw.getProjectCode());
                    map.put("positionList", positionList);
                    map.put("lng", pm.getLng() == null ? 0 : pm.getLng());
                    map.put("lat", pm.getLat() == null ? 0 : pm.getLat());
                    map.put("radius", pm.getRadius() == null ? 0 : pm.getRadius());
                    listMap.add(map);
                }
            }
        }
        return listMap;
    }

    @Override
    public AppProjectCountDto getAgeCount(String organizationCode, String projectCode) {

        int contractorType = 0;
        List<ProjectSubContractor> list = projectSubContractorMapper.getListByProjectCodeAndOrganizationCode(projectCode, organizationCode);
        if (null != list && list.size() != 0) {
            contractorType = list.get(0).getContractorType();
        }
        if (contractorType != 16) {
            return this.baseMapper.getAgeCount(organizationCode, projectCode);
        } else {
            return this.baseMapper.getAgeCount(null, projectCode);
        }


    }

    @Override
    public AppProjectCountDto getGenderCount(String organizationCode, String projectCode) {
        int contractorType = 0;
        List<ProjectSubContractor> list = projectSubContractorMapper.getListByProjectCodeAndOrganizationCode(projectCode, organizationCode);
        if (null != list && list.size() != 0) {
            contractorType = list.get(0).getContractorType();
        }
        if (contractorType != 16) {
            return this.baseMapper.getGenderCount(organizationCode, projectCode);
        } else {
            return this.baseMapper.getGenderCount(null, projectCode);
        }


    }

    @Override
    public List<AppProjectCountDto> getWorkTypeCount(String organizationCode, String projectCode) {

        int contractorType = 0;
        List<ProjectSubContractor> list = projectSubContractorMapper.getListByProjectCodeAndOrganizationCode(projectCode, organizationCode);
        if (null != list && list.size() != 0) {
            contractorType = list.get(0).getContractorType();
        }
        if (contractorType != 16) {
            return this.baseMapper.getWorkTypeCount(organizationCode, projectCode);
        } else {
            return this.baseMapper.getWorkTypeCount(null, projectCode);
        }

    }


    @Override
    public AppProjectCountDto getTeamCount(String organizationCode,  String projectCode) {
        int contractorType=0;
        List<String> ocList = deptService.getAPPUserDeptAndSubdivisionOrganizationCode(organizationCode);
        List<ProjectSubContractor>  list =projectSubContractorMapper.getListByProjectCodeAndOrganizationCode(projectCode,organizationCode);
        if(null!=list&&list.size()!=0){
            contractorType=list.get(0).getContractorType();
        }
        if(contractorType!=16){
            return this.baseMapper.getTeamCount(ocList,projectCode);
        }else{
            return this.baseMapper.getTeamCount(null,projectCode);
        }
    }

    @Override
    public Integer updateContractInfo(ContractFile contractFile) {
        return this.baseMapper.updateContractInfo(contractFile);
    }

    @Override
    public AppProjectCountDto getJoinedCount(String organizationCode, String projectCode) {
        int contractorType=0;
        List<String> ocList = deptService.getAPPUserDeptAndSubdivisionOrganizationCode(organizationCode);
        List<ProjectSubContractor>  list =projectSubContractorMapper.getListByProjectCodeAndOrganizationCode(projectCode,organizationCode);
        if(null!=list&&list.size()!=0){
            contractorType=list.get(0).getContractorType();
        }
        if(contractorType!=16){
            return this.baseMapper.getJoinedCount(ocList,projectCode);
        }else{
            return this.baseMapper.getJoinedCount(null,projectCode);
        }
    }


    @Override
    public AppProjectCountDto getTeamCountPC(String organizationCode, String projectCode) {
        return this.baseMapper.getTeamCountPC(organizationCode,ShiroKit.getUser()!=null?ShiroKit.getUser().getDeptId():null, projectCode, accountProjectService.getSwitchType());
    }

    @Override
    public AppProjectCountDto getJoinedCountPC(String organizationCode, String projectCode) {
        return this.baseMapper.getJoinedCountPC(organizationCode,ShiroKit.getUser()!=null?ShiroKit.getUser().getDeptId():null, projectCode, accountProjectService.getSwitchType());
    }

    @Override
    public List<AppProjectListByPersonVo> getProjectListByPerson(Integer idCardType, String idCardNumber,
                                                                 Integer projectStatus) {
        return mapper.getProjectListByPerson(idCardType, idCardNumber, projectStatus);
    }

    @Override
    public List<AppProjectListByPersonVo> v116GetProjectListByPerson(Integer idCardType, String idCardNumber,
                                                                 Integer projectStatus,Integer pageNo , Integer pageSize) {
        return mapper.v116GetProjectListByPerson(idCardType, idCardNumber, projectStatus,(pageNo-1)*pageSize,pageSize);
    }

    @Transactional
    @Override
    public void concernProject(String projectCode, Integer isCollect, Integer userId) {
        if (isCollect == 1) {
            //先判断是否是首次关注
            if (mapper.searchConcern(projectCode, userId).size() > 0) {
                //存在关系，修改状态
                mapper.concernAgain(projectCode, userId);
            } else {
                //不存在，添加关系
                mapper.concernProject(projectCode, userId);
            }
        } else {
            //取消关注
            mapper.cancleConcern(projectCode, userId);
        }

    }

    @Override
    public AppPersonVo getPersonInfo(String idCardNumber, String projectCode, Integer idCardType) {
        AppPersonVo personInfo = mapper.getPersonInfo(projectCode, idCardType, idCardNumber);
        personInfo.setCount(teamMapper.getWorkerRecordDays(projectCode, idCardType, idCardNumber));
        return personInfo;
    }

    @Override
    public List<AppProjectJoinPerson> getProjectJoinPerson(String organizationCode, String projectCode) {

        int contractorType = 0;
        List<ProjectSubContractor> list = projectSubContractorMapper.getListByProjectCodeAndOrganizationCode(projectCode, organizationCode);
        if (null != list && list.size() != 0) {
            contractorType = list.get(0).getContractorType();
        }
        if (contractorType != 16) {
            return mapper.getProjectJoinPerson(projectCode, organizationCode);
        } else {
            return mapper.getProjectJoinPerson(projectCode, null);
        }


    }

    @Override
    public List<AppProjectJoinPerson> v116GetProjectJoinPerson(String organizationCode, String projectCode,Integer pageNo , Integer pageSize) {
        int contractorType = 0;
        List<ProjectSubContractor> list = projectSubContractorMapper.getListByProjectCodeAndOrganizationCode(projectCode, organizationCode);
        if (null != list && list.size() != 0) {
            contractorType = list.get(0).getContractorType();
        }
        if (contractorType != 16) {
            return mapper.v116GetProjectJoinPerson(projectCode, organizationCode,(pageNo-1)*pageSize,pageSize);
        } else {
            return mapper.v116GetProjectJoinPerson(projectCode, null,(pageNo-1)*pageSize,pageSize);
        }


    }



    @Override
    public List<AppBirthPlaceCountVo> getBirthPlaceCount(String organizationCode, String projectCode) {


        int contractorType=0;
        List<ProjectSubContractor>  list =projectSubContractorMapper.getListByProjectCodeAndOrganizationCode(projectCode,organizationCode);
        if(null!=list&&list.size()!=0){
            contractorType=list.get(0).getContractorType();
        }
        if(contractorType!=16){
            return mapper.getBirthPlaceCount(organizationCode,projectCode);
        }else{
            return mapper.getBirthPlaceCount(null,projectCode);
        }

    }

    @Override
    public List<AppBirthPlaceCountVo> getBirthPlaceCountPC(String organizationCode, String projectCode) {
        return mapper.getBirthPlaceCountPC(ShiroKit.getUser().getDeptId(), projectCode, accountProjectService.getSwitchType());

    }

    @Override
    public AppProjectInfoVo getProjectInfo(String organizationCode, String projectCode) {
        AppProjectInfoVo peoject = mapper.getProjectInfo(projectCode, organizationCode);
        peoject.setProjectCategory(ConstantFactory.me().projectCategory(Integer.parseInt(peoject.getProjectCategory())));
        return peoject;
    }

    @Override
    public List<AppPersonVo> getPersonsByTeam(Integer teamId) {
        return mapper.getPersonsByTeam(teamId);
    }

    @Override
    public List<AppAttenWorkerVo> getAttendanceWorkerToday(String projectCode, String organizationCode) {

        int contractorType = 0;
        List<ProjectSubContractor> list = projectSubContractorMapper.getListByProjectCodeAndOrganizationCode(projectCode, organizationCode);
        if (null != list && list.size() != 0) {
            contractorType = list.get(0).getContractorType();
        }
        if (contractorType != 16) {
            return mapper.getAttendanceWorkerToday(projectCode, organizationCode);
        } else {
            return mapper.getAttendanceWorkerToday(projectCode, null);
        }
    }

    @Override
    public List<AppAttenWorkerVo> v116GetAttendanceWorkerToday(String projectCode, String organizationCode,Integer pageNo , Integer pageSize) {

        int contractorType = 0;
        List<ProjectSubContractor> list = projectSubContractorMapper.getListByProjectCodeAndOrganizationCode(projectCode, organizationCode);
        if (null != list && list.size() != 0) {
            contractorType = list.get(0).getContractorType();
        }
        if (contractorType != 16) {
            return mapper.v116GetAttendanceWorkerToday(projectCode, organizationCode,(pageNo-1)*pageSize,pageSize
            );
        } else {
            return mapper.v116GetAttendanceWorkerToday(projectCode, null,(pageNo-1)*pageSize,pageSize
            );
        }
    }

    @Override
    public List<Map<String, Object>> getListByIdCard(Page<ProjectWorker> page, Map<String, Object> map) {
        return this.baseMapper.getListByIdCard(page, map);
    }

    @Override
    public List<ProjectWorker> queryByProject(String projectCode,String team,String workKind, String keys) {
        return this.baseMapper.queryByProject(projectCode,team,workKind, keys);
    }

    @Override
    public List<Map<String, Object>> getWorkerListByProject(Map map, Page page) {
        return baseMapper.getWorkerListByProject(map, ShiroKit.getUser().getDeptId(), page);
    }

    /**
     * @return
     * @description 获取进场工人数
     * @author chupp
     * @date 2018年6月21日
     * @see com.xywg.admin.modular.project.service.IProjectWorkerService#getTotalJoin()
     */
    @Override
    public int getTotalJoin() {
        Integer deptId = ShiroKit.getUser().getDeptId();
        if (deptId == null || deptId == 0) {
            return -1;
        }
        List<String> list = deptService.getOrganizationCodeByDeptId(deptId);
        return this.baseMapper.getTotalJoin(list, accountProjectService.getProjectCodes());
    }

    /**
     * @return
     * @description 获取项目人员分布
     * @author chupp
     * @date 2018年6月22日
     * @see com.xywg.admin.modular.project.service.IProjectWorkerService#getProjectJoinRange()
     */
    @Override
    public List<ContractorWorkerDto> getProjectJoinRange() {
        Integer deptId = ShiroKit.getUser().getDeptId();
        if (deptId == null || deptId == 0) {
            return new ArrayList<>();
        }
        List<String> list = deptService.getOrganizationCodeByDeptId(deptId);
        return this.baseMapper.getProjectJoinRange(list, accountProjectService.getProjectCodes());
    }

    @Override
    public boolean getForFaceUse(Integer workerId, String projectCode) {
        boolean flag = false;
        WorkerMaster selectById = workerMapper.selectById(workerId);
        List<Map<String, Object>> forFaceUse = mapper.getForFaceUse(projectCode, selectById.getIdCardType(), selectById.getIdCardNumber());
        if (forFaceUse.size() > 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public void updateShImei(String projectCode, String helmetCode, Integer workerId) {
        WorkerMaster selectById = workerMapper.selectById(workerId);
        mapper.updateShImei(projectCode, helmetCode, selectById.getIdCardType(), selectById.getIdCardNumber());

    }

    /**
     * @description 获取实名制项目工人关系（盐城企业版）
     * @author chupp
     * @date 2018年7月18日
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void saveProjectPersonnelFromSMZCompanyYC(Map<String, String> myc) {
        // 获取实名制工人、项目关系信息
        String jsonResult = HttpClientUtils.post(
                getSystemStringParam("httpUrlPrefixYC") + getSystemStringParam("saveProjectPersonnelFromSMZCompany"),
                new HashMap<String, Object>(), myc);
        Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
        // 存在新的工人、项目关系信息
        if (map.get("workerproject") != null) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("workerproject"));
            List<ProjectPersonnelSmz> ppsList = JSONArray.toList(jsonArray, new ProjectPersonnelSmz(), new JsonConfig());
            String id = "0";
            if (ppsList.size() > 0) {
                for (ProjectPersonnelSmz pps : ppsList) {
                    long smzId = Long.parseLong(pps.getId());
                    //人员项目关系保存
                    ProjectWorker pw = new ProjectWorker();
                    List<SmzLwt> slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(pps.getComId()), "CompanyYC");
                    if (slList.size() == 0) throw new RuntimeException("实名制接口（盐城企业版）项目人员所属企业数据为空" + pps.getComId());
                    SubContractor subContractor = subContractorMapper.selectById(slList.get(0).getLwtId());
                    if (subContractor == null)
                        throw new RuntimeException("实名制接口（盐城企业版）项目人员所属企业数据对象为空" + slList.get(0).getLwtId());
                    slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(pps.getProjectId()), "ProjectCompanyYC");
                    if (slList.size() == 0) throw new RuntimeException("实名制接口（盐城企业版）项目人员所属项目为空" + pps.getProjectId());
                    Map<String, Object> chProject = projectMasterMapper.selectById(slList.get(0).getLwtId());
                    if (chProject == null)
                        throw new RuntimeException("实名制接口（盐城企业版）项目人员所属项目对象为空" + slList.get(0).getLwtId());
                    pw.setProjectCode((String) chProject.get("projectCode"));
                    pw.setOrganizationCode(subContractor.getOrganizationCode());
                    slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(pps.getClassid()), "ClassCompanyYC");
                    if (slList.size() == 0) throw new RuntimeException("实名制接口（盐城企业版）项目人员所属班组为空" + pps.getClassid());
                    TeamMaster chTm = teamMapper.selectById(slList.get(0).getLwtId());
                    if (chTm == null) throw new RuntimeException("实名制接口（盐城企业版）项目人员所属班组对象为空" + slList.get(0).getLwtId());
                    pw.setTeamSysNo(chTm.getTeamSysNo());
                    pw.setIdCardType("1");
                    slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(pps.getUserId()), "PersonnelYC");
                    if (slList.size() == 0) throw new RuntimeException("实名制接口项目人员所属工人为空");
                    WorkerMaster chWm = workerMasterMapper.selectById(slList.get(0).getLwtId());
                    if (chWm == null) throw new RuntimeException("实名制接口项目人员所属工人对象为空");
                    pw.setIdCardNumber(chWm.getIdCardNumber());
                    if ("待进场".equals(pps.getJoinStatus())) {
                        pw.setJoinStatus(1);
                    } else if ("进场".equals(pps.getJoinStatus())) {
                        pw.setJoinStatus(2);
                    } else if ("退场".equals(pps.getJoinStatus())) {
                        pw.setJoinStatus(3);
                    }
                    pw.setWorkTypeCode(chWm.getWorkTypeCode());
                    pw.setHasContract(0);
                    pw.setWorkerRole(2);
                    pw.setIsDel(0);
                    this.baseMapper.insert(pw);
                    workerMasterService.inputFaceAndProjectCode(String.valueOf(slList.get(0).getLwtId()), (String) chProject.get("projectCode"));
                    //人员班组关系保存
                    TeamMemberShip tms = new TeamMemberShip();
                    tms.setIdCardType(1);
                    tms.setIdCardNumber(chWm.getIdCardNumber());
                    tms.setTeamSysNo(chTm.getTeamSysNo());
                    tms.setIsDel(0);
                    tms.setIsSign(0);
                    tms.setTeamWorkerType(1);
                    teamMapper.addMember(tms);
                    //保存公司工人关系
                    if (contractorWorkerMapper.getByIdCardAndOrganization(chWm.getIdCardNumber(), 1, subContractor.getOrganizationCode()) == null) {
                        ContractorWorker contractorWorker = new ContractorWorker();
                        contractorWorker.setIdCardType(1);
                        contractorWorker.setIdCardNumber(chWm.getIdCardNumber());
                        contractorWorker.setWorkName(chWm.getWorkerName());
                        contractorWorker.setOrganizationCode(subContractor.getOrganizationCode());
                        contractorWorker.setContractorName(subContractor.getCompanyName());
                        contractorWorkerMapper.insert(contractorWorker);
                    }
                    if (Long.parseLong(id) < smzId) {
                        id = String.valueOf(smzId);
                    }
                }
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("code", "1");
                m.put("no", id);
                // 响应保存成功信息
                HttpClientUtils.post(getSystemStringParam("httpUrlPrefixYC")
                        + getSystemStringParam("saveProjectPersonnelFromSMZCompanyResponse"), m, myc);
            }
        }
    }

    /**
     * 获取当前选择项目下工人年龄分部
     *
     * @param projectCode
     * @return
     * @author 蔡伟
     */
    @Override
    public List<ContractorWorkerDto> getAgeRange(String projectCode) {
        return this.baseMapper.getAgeRange(projectCode, accountProjectService.getSwitchType(), ShiroKit.getUser().getDeptId());
    }

    @Override
    @Transactional
    public void saveProjectWorkerInformation(Map<String, String> mnt) {
        //获取需要传递的参数 id实名制id  lwtId劳务通id
        List<Map<String, Object>> idList = projectMasterMapper.getIsReceiveProjectId();
        List<SmzCallBack> scbList = new ArrayList<SmzCallBack>();
        //获取实名制工人、项目关系信息  实名制项目id
        idList.forEach(map -> {
            //看了之前的代码要判断是否已经同步过实名制信息，这里直接是从和实名制关联表里面去拿id的，所以不存在项目同步问题
            Map<String, Object> params = new HashMap<>();
            params.put("projectIdNew", map.get("id").toString());
            String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefixInformationUrl") +
                    getSystemStringParam("httpUrlPrefixInformationMethod"), params, mnt);
            Map<String, Object> returnMap = (Map<String, Object>)JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
            if(null != returnMap.get("code") && returnMap.get("code").toString().equals("0") && null != returnMap.get("workerproject")) {
            	SmzCallBack scb = new SmzCallBack();
                JSONArray jsonArray = JSONArray.fromObject(returnMap.get("workerproject"));
                List<Map<String, Object>> array = JSONArray.toList(jsonArray, new HashMap<>(), new JsonConfig());
                final Map<String, Long> finalMap = new HashMap<>();
                finalMap.put("id", 0L);
                //开始获取每一条数据, 人员信息
                array.forEach(model -> {
                    //保存人员和项目关系
                    ProjectWorker pw = new ProjectWorker();
                    pw.setProjectCode(map.get("projectCode").toString());
                    pw.setOrganizationCode(map.get("orgCode").toString());
                    //classid 代表班组
                    List<SmzLwt> slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(model.get("classid").toString()), "Class");
                    TeamMaster chTm = null;
                    if (slList.size() == 0) {
                        //新增劳务通班组信息
                        TeamMaster team = new TeamMaster();
                        //team.setTeamSysNo();  和保存之后的id相同
                        team.setIsDel(0);
                        team.setStatus(1);
                        team.setOrganizationCode(map.get("orgCode").toString());
                        team.setProjectCode(map.get("projectCode").toString());
                        team.setTeamName(null == model.get("classNo")?"":model.get("classNo").toString());
                        team.setTeamLeader(null == model.get("teamLeader")?"":model.get("teamLeader").toString());
                        team.setContact(null == model.get("contact")?"":model.get("contact").toString());
                        team.setTeamLeaderIdNumber(null == model.get("teamLeaderIdNumber")?"":model.get("teamLeaderIdNumber").toString());
                        team.setResponsiblePersonIdNumber(null == model.get("responsiblePersonIdNumber")?"":model.get("responsiblePersonIdNumber").toString());
                        teamMapper.insert(team);
                        //保存之后
                        team.setTeamSysNo(team.getId());
                        teamMapper.updateById(team);
                        chTm = team;
                        //如果没有班组就要插入班组 劳务通和实名制关系
                        SmzLwt lwt = new SmzLwt();
                        lwt.setSmzId(Long.parseLong(null == model.get("classid")?"":model.get("classid").toString()));
                        lwt.setTableName("Class");
                        lwt.setLwtId((long)team.getId());
                        smzLwtMapper.insert(lwt);
                    }else{
                        chTm = teamMapper.selectById(slList.get(0).getLwtId());
                    }
                    pw.setTeamSysNo(chTm.getTeamSysNo());
                    pw.setIdCardType("1");
                    //身份证号
                    slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(model.get("userId").toString()), "PersonnelTY");
                    WorkerMaster chWm = null;
                    if (slList.size() == 0) {
                    	//先根据人员身份证号查找这个人是否存在，如果存在的话直接将找到的劳务通的人员id与当前实名制的id重新保存成新的标记形式
                    	WorkerMaster wm = this.workerMapper.getWorkerByIdCardNumber(model.get("idcardNumber").toString());
                    	if(wm == null){
                    		//如果工人是空的话添加工人
                            WorkerMaster worker = new WorkerMaster();
                            worker.setWorkerName(model.get("name").toString());
                            worker.setCellPhone(null == model.get("mobile")? "" : model.get("mobile").toString());
                            worker.setIdCardType(1);
                            worker.setIdCardNumber(model.get("idcardNumber").toString());
                            //实名制的男女跟国标是反的
                            if(Integer.parseInt(model.get("gender").toString()) == 1){
                            	worker.setGender(0);
                            }else if(Integer.parseInt(model.get("gender").toString()) == 0){
                            	worker.setGender(1);
                            }
                            
                            worker.setIsFace(0);
                            worker.setIsAuth(0);
                            worker.setIsDel(0);
                            worker.setIsBlack(0);
                            worker.setAddress(model.get("homeAddress").toString());
//                            worker.setWorkTypeCode(null == model.get("workKind")? "" : model.get("workKind").toString());
                            //去掉kindCode前面的0
                            if(model.get("kindCode") != null){
                            	String str = model.get("kindCode").toString();
                                String newStr = str.replaceAll("^(0+)", "");
                                worker.setWorkTypeCode(newStr);
                            }
                            try{
                                worker.setBirthday(null == model.get("birthday")?null:new SimpleDateFormat("yyyyMMdd").parse(model.get("birthday").toString()));
                            }catch (Exception e) {
                               e.printStackTrace();
                            }
                            worker.setCreateUser("smz");
                            worker.setCreateDate(new Date());
                            workerMapper.insert(worker);
                            //插入实名制和劳务通用户关系表
                            SmzLwt lwt = new SmzLwt();
                            lwt.setSmzId(Long.parseLong(model.get("userId").toString()));
                            lwt.setTableName("PersonnelTY");
                            lwt.setLwtId(worker.getId());
                            smzLwtMapper.insert(lwt);
                            slList.add(lwt);
                            chWm = worker;
                    	}else{
                    		//插入实名制和劳务通用户关系表
                            SmzLwt lwt = new SmzLwt();
                            lwt.setSmzId(Long.parseLong(model.get("userId").toString()));
                            lwt.setTableName("PersonnelTY");
                            lwt.setLwtId(wm.getId());
                            smzLwtMapper.insert(lwt);
                            slList.add(lwt);
                            chWm = wm;
                    	}
                        
                    }else{
                        chWm = workerMasterMapper.selectById(slList.get(0).getLwtId());
                    }
                    pw.setIdCardNumber(chWm.getIdCardNumber());
                    pw.setWorkTypeCode(chWm.getWorkTypeCode());
                    //人员进退场
                    if ("待进场".equals(model.get("joinStatus").toString())) {
                        pw.setJoinStatus(1);
                    } else if ("进场".equals(model.get("joinStatus").toString())) {
                        pw.setJoinStatus(2);
                    } else if ("退场".equals(model.get("joinStatus").toString())) {
                        pw.setJoinStatus(3);
                    }
                    pw.setHasContract(0);
                    pw.setWorkerRole(2);
                    pw.setIsDel(0);
                    this.baseMapper.insert(pw);
                    //workerMasterService.inputFaceAndProjectCode(String.valueOf(slList.get(0).getLwtId()), map.get("projectCode").toString());
                    //人员班组关系保存
                    TeamMemberShip tms = new TeamMemberShip();
                    tms.setIdCardType(1);
                    tms.setIdCardNumber(chWm.getIdCardNumber());
                    tms.setTeamSysNo(chTm.getTeamSysNo());
                    tms.setIsDel(0);
                    tms.setIsSign(0);
                    tms.setTeamWorkerType(1);
                    teamMapper.addMember(tms);
                    //保存公司工人关系
                    if (contractorWorkerMapper.getByIdCardAndOrganization(chWm.getIdCardNumber(), 1, map.get("orgCode").toString()) == null) {
                        ContractorWorker contractorWorker = new ContractorWorker();
                        contractorWorker.setIdCardType(1);
                        contractorWorker.setIdCardNumber(chWm.getIdCardNumber());
                        contractorWorker.setWorkName(chWm.getWorkerName());
                        contractorWorker.setOrganizationCode(map.get("orgCode").toString());
                        contractorWorker.setContractorName(map.get("orgName").toString());
                        contractorWorkerMapper.insert(contractorWorker);
                    }
                    if (finalMap.get("id") < Long.parseLong(model.get("id").toString())) {
                        finalMap.put("id", Long.parseLong(model.get("id").toString()));
                    }
                });
                System.out.println("11111111111");
                scb.setMaxId(finalMap.get("id").toString());
                scb.setProjectId(map.get("id").toString());
                scbList.add(scb);
            }
        });
        //一个项目完成发送一个完成标志给实名制 TODO 此处暂时没有拿到同步成功接口，之后问姜丽娜，先完成其他模块
        Map<String, Object> m = new HashMap<>();
        String json = JSONArray.fromObject(scbList).toString();
        m.put("code", "1");
        m.put("scbList", json);
        // 响应保存成功信息
        HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")
                + getSystemStringParam("httpUrlPrefixInformationReturn"), m, mnt);
    }

    /**
     * @description 获取实名制项目人员关系（南通）
     * @author chupp
     * @date 2018年7月26日
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void saveProjectPersonnelFromSMZNT(Map<String, String> mnt, List<SubContractor> list) {
        // 获取实名制工人、项目关系信息
        String jsonResult = HttpClientUtils.post(
                getSystemStringParam("httpUrlPrefix") + getSystemStringParam("saveProjectPersonnelFromSMZ"),
                new HashMap<String, Object>(), mnt);
        Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
        // 存在新的工人、项目关系信息
        if (map.get("workerproject") != null) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("workerproject"));
            List<ProjectPersonnelSmz> ppsList = JSONArray.toList(jsonArray, new ProjectPersonnelSmz(), new JsonConfig());
            String id = "0";
            SubContractor subContractor = null;
            List<SmzLwt> slList = null;
            if (ppsList.size() > 0) {
                for (ProjectPersonnelSmz pps : ppsList) {
                	if(pps.getComId().equals("0")) {
                		subContractor = list.get(0);
                	}else if(pps.getComId().equals("00")) {
                		subContractor = list.get(1);
                	}else if(pps.getComId().equals("000")) {
                		subContractor = list.get(2);
                	}
                    long smzId = Long.parseLong(pps.getId());
                    //人员项目关系保存
                    ProjectWorker pw = new ProjectWorker();
                    slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(pps.getProjectId()), "Project");
                    if (slList.size() == 0) throw new RuntimeException("实名制接口项目人员所属项目为空");
                    Map<String, Object> chProject = projectMasterMapper.selectById(slList.get(0).getLwtId());
                    if (chProject == null) {
//                    	throw new RuntimeException("实名制接口项目人员所属项目对象为空");
                    } else {
                        pw.setProjectCode((String) chProject.get("projectCode"));
                        pw.setOrganizationCode(subContractor.getOrganizationCode());
                        slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(pps.getClassid()), "Class");
                        if (slList.size() == 0) throw new RuntimeException("实名制接口项目人员所属班组为空");
                        TeamMaster chTm = teamMapper.selectById(slList.get(0).getLwtId());
                        if (chTm == null) throw new RuntimeException("实名制接口项目人员所属班组对象为空");
                        pw.setTeamSysNo(chTm.getTeamSysNo());
                        pw.setIdCardType("1");
                        slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(pps.getUserId()), "Personnel");
                        if (slList.size() == 0) {
//                        	throw new RuntimeException("实名制接口项目人员所属工人为空");
                        } else {
                        	  WorkerMaster chWm = workerMasterMapper.selectById(slList.get(0).getLwtId());
                              if (chWm == null) {
//                            	  throw new RuntimeException("实名制接口项目人员所属工人对象为空");
                              }else {
                                  pw.setIdCardNumber(chWm.getIdCardNumber());
                                  if ("待进场".equals(pps.getJoinStatus())) {
                                      pw.setJoinStatus(1);
                                  } else if ("进场".equals(pps.getJoinStatus())) {
                                      pw.setJoinStatus(2);
                                  } else if ("退场".equals(pps.getJoinStatus())) {
                                      pw.setJoinStatus(3);
                                  }
                                  pw.setWorkTypeCode(chWm.getWorkTypeCode());
                                  pw.setHasContract(0);
                                  pw.setWorkerRole(2);
                                  pw.setIsDel(0);
                                  this.baseMapper.insert(pw);
                                  workerMasterService.inputFaceAndProjectCode(String.valueOf(slList.get(0).getLwtId()), (String) chProject.get("projectCode"));
                                  //人员班组关系保存
                                  TeamMemberShip tms = new TeamMemberShip();
                                  tms.setIdCardType(1);
                                  tms.setIdCardNumber(chWm.getIdCardNumber());
                                  tms.setTeamSysNo(chTm.getTeamSysNo());
                                  tms.setIsDel(0);
                                  tms.setIsSign(0);
                                  tms.setTeamWorkerType(1);
                                  teamMapper.addMember(tms);
                                  //保存公司工人关系
                                  if (contractorWorkerMapper.getByIdCardAndOrganization(chWm.getIdCardNumber(), 1, subContractor.getOrganizationCode()) == null) {
                                      ContractorWorker contractorWorker = new ContractorWorker();
                                      contractorWorker.setIdCardType(1);
                                      contractorWorker.setIdCardNumber(chWm.getIdCardNumber());
                                      contractorWorker.setWorkName(chWm.getWorkerName());
                                      contractorWorker.setOrganizationCode(subContractor.getOrganizationCode());
                                      contractorWorker.setContractorName(subContractor.getCompanyName());
                                      contractorWorkerMapper.insert(contractorWorker);
                                  }
                                  if (Long.parseLong(id) < smzId) {
                                      id = String.valueOf(smzId);
                                  }
                              }

                        }
                      
                    }

                }
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("code", "1");
                m.put("no", id);
                // 响应保存成功信息
                HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")
                        + getSystemStringParam("saveProjectPersonnelFromSMZResponse"), m, mnt);
            }
        }
    }

    @Override
    public boolean isInProject(String projectCode, Integer idCardType, String idCardNumber) {
        boolean flag = false;
        List<ProjectWorker> inProject = mapper.isInProject(projectCode, idCardType, idCardNumber);
        if (inProject.size() > 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public Integer bindSafetyHelmet(ProjectWorker projectWorker) {
        return this.baseMapper.bindSafetyHelmet(projectWorker);
    }

    @Override
    public List<AppPersonVo> v116GetPersonsByTeam(Integer teamId,Integer pageNo , Integer pageSize) {
        return mapper.v116GetPersonsByTeam(teamId,(pageNo-1)*pageSize,pageSize);
    }

    @Override
    public ProjectWorker getProjectWorkerByUserInfo(String projectCode, Integer idCardType, String idCardNumber) {
        return this.baseMapper.getProjectWorkerByUserInfo(projectCode,idCardType,idCardNumber);
    }

    @Override
    public List<WorkInfoHead> getWorkInfoHeadList() {
        List<WorkInfoHead> workInfoHeadList = mapper.getWorkInfoHeadList();
        for (WorkInfoHead workInfoHead : workInfoHeadList) {
            WorkerHealth lastHealth = workerHealthService.getLastHealth(workInfoHead.getIdCardType(), workInfoHead.getIdCardNumber());
            PersonPosition personLastPosition = mapper.getPersonLastPosition(workInfoHead.getIdCardType(), workInfoHead.getIdCardNumber(), workInfoHead.getShImei());
            if (lastHealth != null){
                workInfoHead.setTemperature(lastHealth.getTemperature());
                workInfoHead.setBloodOxygen(lastHealth.getBloodOxygen());
                workInfoHead.setAttitude(lastHealth.getAttitude());
            }
            if (personLastPosition != null){
                workInfoHead.setLng(personLastPosition.getLng());
                workInfoHead.setLat(personLastPosition.getLat());
            }
        }
        return workInfoHeadList;
    }

	@Override
	public Map<String, Object> queryProjectInfoById(Long id) {
		return this.mapper.queryProjectInfoById(id);
	}

	@Override
	public String getCardNumberByPwId(Long pwId) {
		return this.mapper.getCardNumberByPwId(pwId);
	}

	@Override
	public void updateCardNumber(Long pwId, String cardNumber) {
		//判断卡是否已被绑定
		getCountByCardNumber(cardNumber,null);
		this.mapper.updateCardNumber(pwId, cardNumber);
		ProjectWorker project = this.baseMapper.selectById(pwId);
		
		WorkerMaster worker= new WorkerMaster();
		worker.setProjectCode(project.getProjectCode());
		worker.setIdCardNumber(project.getIdCardNumber());
		worker.setIdCardType(Integer.valueOf(project.getIdCardType()));
		workerMasterService.dispatchUser(worker);
	}

	@Override
	public int getCountByCardNumber(String cardNumber,Long id) {
		int count = 0;
		if(StringUtils.isNotBlank(cardNumber)) {
			count = this.mapper.getCountByCardNumber(cardNumber,id);
			if(count != 0 ) {
				throw new XywgException(600, "该卡已经被绑定，请换卡在试！");
			}
		}
		return 0;
	}

	@Override
	public List<Map<String, Object>> listReport(String projectCode, String type) {
        Date now = new Date();
        //return this.mapper.listReport(projectCode, type, DateUtil.format(now, "yyyy-MM-dd"));
        List<Map<String, Object>> resultList = new ArrayList<>(); 
        //根据条件查询基础数据
        List<Map<String, Object>> list = this.baseMapper.selectTeamReport(projectCode, type, DateUtil.format(now, "yyyy-MM-dd"));
        Map<String,List<String>> teamMap = new HashMap<>();
        Map<String,Map<String,String>> inMap = new HashMap<>();
        //遍历查询结果
        for(Map<String,Object> map : list) {
        	String idCardType = String.valueOf(map.get("id_card_type"));
        	String idCardNumber = String.valueOf(map.get("id_card_number"));
        	String teamName = String.valueOf(map.get("team_name"));
        	String teamSysNo = map.get("team_sys_no") == null?String.valueOf(map.get("work_type_code")):String.valueOf(map.get("team_sys_no"));
        
        	String deviceType = String.valueOf(map.get("type"));
        	String time = String.valueOf(map.get("time"));
        	//以name为key,工人信息为value存入teamMap（分组）
        	String key = teamSysNo + ":" + teamName;
        	if(teamMap.containsKey(key)) {
        		//判断班组下是否已存在该工人
        		if(!teamMap.get(key).contains(idCardType+idCardNumber)) {
        			teamMap.get(key).add(idCardType+idCardNumber);
        		}
			}else {
				//新增分组存入teamMap
				List<String> workerList = new ArrayList<>();
				workerList.add(idCardType+idCardNumber);
				teamMap.put(key,workerList);
			}
        	//在场人员map统计
    		if(inMap.containsKey(key)) {
        		if(!inMap.get(key).containsKey(idCardType+idCardNumber)) {
        			//将时间和考勤类型作为整体存入value
        			inMap.get(key).put(idCardType+idCardNumber,time+deviceType);
        		}else {
        			String temp = inMap.get(key).get(idCardType+idCardNumber);
        			String tempTime = temp.substring(0, temp.length()-1);
        			//如果遍历的工人考勤时间比当前分组下的同一工人的时间更晚，则将该条记录存入map
        			if(time.compareTo(tempTime)>0) {
        				inMap.get(key).put(idCardType+idCardNumber,time+deviceType);
        			}
        		}
			}else {
				Map<String,String> workerMap = new HashMap<>();
				workerMap.put(idCardType+idCardNumber,time+deviceType);
				inMap.put(key,workerMap);
			}
        }
        //遍历分组信息，得到最终结果
        for (Map.Entry<String, List<String>> entry : teamMap.entrySet()) {
        	int count = 0;
        	Map map = new HashMap<>();
        	Map<String,String> workerMap = inMap.get(entry.getKey());
        	for (Map.Entry<String, String> entry1 : workerMap.entrySet()) {
        		//如果该工人的最大考勤时间的考勤类型为3，则视为在场
        		if(entry1.getValue().substring(entry1.getValue().length()-1).equals("3")) {
        			count ++;
        		}
        	}
			/*
			 * if(type.equals("1")) { map.put("team_name", entry.getKey()); }else {
			 * map.put("name", entry.getKey()); }
			 */
        	map.put("teamOrTypeNo", entry.getKey().substring(0, entry.getKey().indexOf(":")));
        	map.put("name", entry.getKey().substring(entry.getKey().indexOf(":")+1));
        	map.put("aCount", entry.getValue().size());
        	map.put("iCount", count);
        	//查询班组下的进场工人总数
        	int totalcount = this.mapper.getWorkerByTeam(projectCode,type,entry.getKey().substring(0, entry.getKey().indexOf(":")));
        	map.put("count", totalcount);
        	resultList.add(map);
        }
        
        return resultList;
    }

    @Override
    public List<Map<String, Object>> listReportEver(String projectCode, String type) {
        Date now = new Date();
        //return this.mapper.listReport(projectCode, type, DateUtil.format(now, "yyyy-MM-dd"));
        List<Map<String, Object>> resultList = new ArrayList<>();
        //根据条件查询基础数据
        List<Map<String, Object>> list = this.baseMapper.selectTeamReport(projectCode, type, DateUtil.format(now, "yyyy-MM-dd"));
        List<Map<String, Object>> oList = this.baseMapper.selectTeamReportEver(projectCode, type, DateUtil.format(now, "yyyy-MM-dd"));
        Map<String,List<String>> teamMap = new HashMap<>();
        Map<String,Map<String,String>> inMap = new HashMap<>();
        //遍历查询结果
        for(Map<String,Object> map : list) {
            String idCardType = String.valueOf(map.get("id_card_type"));
            String idCardNumber = String.valueOf(map.get("id_card_number"));
            String teamName = String.valueOf(map.get("team_name"));
            String teamSysNo = map.get("team_sys_no") == null?String.valueOf(map.get("work_type_code")):String.valueOf(map.get("team_sys_no"));

            String deviceType = String.valueOf(map.get("type"));
            String time = String.valueOf(map.get("time"));
            //以name为key,工人信息为value存入teamMap（分组）
            String key = teamSysNo + ":" + teamName;
            if(teamMap.containsKey(key)) {
                //判断班组下是否已存在该工人
                if(!teamMap.get(key).contains(idCardType+idCardNumber)) {
                    teamMap.get(key).add(idCardType+idCardNumber);
                }
            }else {
                //新增分组存入teamMap
                List<String> workerList = new ArrayList<>();
                workerList.add(idCardType+idCardNumber);
                teamMap.put(key,workerList);
            }
            //在场人员map统计
            if(inMap.containsKey(key)) {
                if(!inMap.get(key).containsKey(idCardType+idCardNumber)) {
                    //将时间和考勤类型作为整体存入value
                    inMap.get(key).put(idCardType+idCardNumber,time+deviceType);
                }else {
                    String temp = inMap.get(key).get(idCardType+idCardNumber);
                    String tempTime = temp.substring(0, temp.length()-1);
                    //如果遍历的工人考勤时间比当前分组下的同一工人的时间更晚，则将该条记录存入map
                    if(time.compareTo(tempTime)>0) {
                        inMap.get(key).put(idCardType+idCardNumber,time+deviceType);
                    }
                }
            }else {
                Map<String,String> workerMap = new HashMap<>();
                workerMap.put(idCardType+idCardNumber,time+deviceType);
                inMap.put(key,workerMap);
            }
        }
        //遍历分组信息，得到最终结果
        for (Map.Entry<String, List<String>> entry : teamMap.entrySet()) {
            int count = 0;
            Map map = new HashMap<>();
            Map<String,String> workerMap = inMap.get(entry.getKey());
            for (Map.Entry<String, String> entry1 : workerMap.entrySet()) {
                //如果该工人的最大考勤时间的考勤类型为3，则视为在场
                if(entry1.getValue().substring(entry1.getValue().length()-1).equals("3")) {
                    count ++;
                }
            }
            /*
             * if(type.equals("1")) { map.put("team_name", entry.getKey()); }else {
             * map.put("name", entry.getKey()); }
             */
            map.put("teamOrTypeNo", entry.getKey().substring(0, entry.getKey().indexOf(":")));
            map.put("name", entry.getKey().substring(entry.getKey().indexOf(":")+1));
            map.put("aCount", entry.getValue().size());
            map.put("iCount", count);
            //查询班组下的进场工人总数
            int totalcount = this.mapper.getWorkerByTeam(projectCode,type,entry.getKey().substring(0, entry.getKey().indexOf(":")));
            map.put("count", totalcount);
            resultList.add(map);
        }
        //遍历其他的查询结果
        for(Map<String,Object> omap : oList) {
            String teamName = String.valueOf(omap.get("team_name"));
            String teamSysNo = omap.get("team_sys_no") == null?String.valueOf(omap.get("work_type_code")):String.valueOf(omap.get("team_sys_no"));
            String scount = String.valueOf(omap.get("scount"));
            Map map = new HashMap<>();
            map.put("teamOrTypeNo", teamSysNo);
            map.put("name", teamName);
            map.put("aCount", 0);
            map.put("iCount", 0);
            map.put("count", scount);
            resultList.add(map);
        }
        return resultList;
    }

	@Override
	public List<Map> v116GetPersonsByWorkType(String workType, Integer pageNo, Integer pageSize,String projectCode) {
		return mapper.v116GetPersonsByWorkType(workType,(pageNo-1)*pageSize,pageSize,projectCode);
	}
	
	
	@Override
    @Transactional
    public void saveProjectWorkerInformationNew(String userId,String projectId,Map<String, String> mnt) {
        //获取需要传递的参数 id实名制id  lwtId劳务通id
        List<Map<String, Object>> idList = projectMasterMapper.getIsReceiveProjectIdNew(projectId);
        List<SmzCallBack> scbList = new ArrayList<SmzCallBack>();
        //获取实名制工人、项目关系信息  实名制项目id
        idList.forEach(map -> {
            //看了之前的代码要判断是否已经同步过实名制信息，这里直接是从和实名制关联表里面去拿id的，所以不存在项目同步问题
            Map<String, Object> params = new HashMap<>();
            params.put("projectIdNew", map.get("id").toString());
            params.put("userIdNew", userId);
            String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefixInformationUrl") +
                    getSystemStringParam("httpUrlPrefixInformationMethod1"), params, mnt);
            Map<String, Object> returnMap = (Map<String, Object>)JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
            if(null != returnMap.get("code") && returnMap.get("code").toString().equals("0") && null != returnMap.get("workerproject")) {
            	SmzCallBack scb = new SmzCallBack();
                JSONArray jsonArray = JSONArray.fromObject(returnMap.get("workerproject"));
                List<Map<String, Object>> array = JSONArray.toList(jsonArray, new HashMap<>(), new JsonConfig());
                //开始获取每一条数据, 人员信息
                array.forEach(model -> {
                    //保存人员和项目关系
                    ProjectWorker pw = new ProjectWorker();
                    pw.setProjectCode(map.get("projectCode").toString());
                    pw.setOrganizationCode(map.get("orgCode").toString());
                    //classid 代表班组
                    List<SmzLwt> slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(model.get("classid").toString()), "Class");
                    TeamMaster chTm = null;
                    if (slList.size() == 0) {
                        //新增劳务通班组信息
                        TeamMaster team = new TeamMaster();
                        //team.setTeamSysNo();  和保存之后的id相同
                        team.setIsDel(0);
                        team.setStatus(1);
                        team.setOrganizationCode(map.get("orgCode").toString());
                        team.setProjectCode(map.get("projectCode").toString());
                        team.setTeamName(null == model.get("classNo")?"":model.get("classNo").toString());
                        team.setTeamLeader(null == model.get("teamLeader")?"":model.get("teamLeader").toString());
                        team.setContact(null == model.get("contact")?"":model.get("contact").toString());
                        team.setTeamLeaderIdNumber(null == model.get("teamLeaderIdNumber")?"":model.get("teamLeaderIdNumber").toString());
                        team.setResponsiblePersonIdNumber(null == model.get("responsiblePersonIdNumber")?"":model.get("responsiblePersonIdNumber").toString());
                        teamMapper.insert(team);
                        //保存之后
                        team.setTeamSysNo(team.getId());
                        teamMapper.updateById(team);
                        chTm = team;
                        //如果没有班组就要插入班组 劳务通和实名制关系
                        SmzLwt lwt = new SmzLwt();
                        lwt.setSmzId(Long.parseLong(null == model.get("classid")?"":model.get("classid").toString()));
                        lwt.setTableName("Class");
                        lwt.setLwtId((long)team.getId());
                        smzLwtMapper.insert(lwt);
                    }else{
                        chTm = teamMapper.selectById(slList.get(0).getLwtId());
                    }
                    pw.setTeamSysNo(chTm.getTeamSysNo());
                    pw.setIdCardType("1");
                    //身份证号
                    slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(model.get("userId").toString()), "PersonnelTY");
                    WorkerMaster chWm = null;
                    if (slList.size() == 0) {
                        //如果工人是空的话添加工人
                        WorkerMaster worker = new WorkerMaster();
                        worker.setWorkerName(model.get("name").toString());
                        worker.setCellPhone(null == model.get("mobile")? "" : model.get("mobile").toString());
                        worker.setIdCardType(1);
                        worker.setIdCardNumber(model.get("idcardNumber").toString());
                        worker.setGender(Integer.parseInt(model.get("gender").toString()));
                        worker.setIsFace(0);
                        worker.setIsAuth(0);
                        worker.setIsDel(0);
                        worker.setIsBlack(0);
//                        worker.setWorkTypeCode(null == model.get("workKind")? "" : model.get("workKind").toString());
                        
                        worker.setWorkTypeCode(null == model.get("kindCode")? "" : model.get("kindCode").toString());
                        
                        try{
                            worker.setBirthday(null == model.get("birthday")?null:new SimpleDateFormat("yyyyMMdd").parse(model.get("birthday").toString()));
                        }catch (Exception e) {
                           e.printStackTrace();
                        }
                        worker.setCreateUser("smz");
                        worker.setCreateDate(new Date());
                        workerMapper.insert(worker);
                        //插入实名制和劳务通用户关系表
                        SmzLwt lwt = new SmzLwt();
                        lwt.setSmzId(Long.parseLong(model.get("userId").toString()));
                        lwt.setTableName("PersonnelTY");
                        lwt.setLwtId(worker.getId());
                        smzLwtMapper.insert(lwt);
                        slList.add(lwt);
                        chWm = worker;
                    }else{
                        chWm = workerMasterMapper.selectById(slList.get(0).getLwtId());
                    }
                    pw.setIdCardNumber(chWm.getIdCardNumber());
                    pw.setWorkTypeCode(chWm.getWorkTypeCode());
                    //人员进退场
                    if ("待进场".equals(model.get("joinStatus").toString())) {
                        pw.setJoinStatus(1);
                    } else if ("进场".equals(model.get("joinStatus").toString())) {
                        pw.setJoinStatus(2);
                    } else if ("退场".equals(model.get("joinStatus").toString())) {
                        pw.setJoinStatus(3);
                    }
                    pw.setHasContract(0);
                    pw.setWorkerRole(2);
                    pw.setIsDel(0);
                    this.baseMapper.insert(pw);
                    //workerMasterService.inputFaceAndProjectCode(String.valueOf(slList.get(0).getLwtId()), map.get("projectCode").toString());
                    //人员班组关系保存
                    TeamMemberShip tms = new TeamMemberShip();
                    tms.setIdCardType(1);
                    tms.setIdCardNumber(chWm.getIdCardNumber());
                    tms.setTeamSysNo(chTm.getTeamSysNo());
                    tms.setIsDel(0);
                    tms.setIsSign(0);
                    tms.setTeamWorkerType(1);
                    teamMapper.addMember(tms);
                    //保存公司工人关系
                    if (contractorWorkerMapper.getByIdCardAndOrganization(chWm.getIdCardNumber(), 1, map.get("orgCode").toString()) == null) {
                        ContractorWorker contractorWorker = new ContractorWorker();
                        contractorWorker.setIdCardType(1);
                        contractorWorker.setIdCardNumber(chWm.getIdCardNumber());
                        contractorWorker.setWorkName(chWm.getWorkerName());
                        contractorWorker.setOrganizationCode(map.get("orgCode").toString());
                        contractorWorker.setContractorName(map.get("orgName").toString());
                        contractorWorkerMapper.insert(contractorWorker);
                    }
                });
            }
        });
    }

    @Override
    public void addBussProjectWorker(List<Object> addList) {
        for (Object o : addList) {
            ProjectWorker projectWorker = new ProjectWorker();
            stringToDateException();
            try {
                BeanUtils.copyProperties(projectWorker, o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            Map<String,Long> projectWorkerMap=baseMapper.selectProjectWorkerByOrgCodeAndPrjCodeAndIdCard(projectWorker.getProjectCode(),projectWorker.getOrganizationCode(),projectWorker.getTeamSysNo(),projectWorker.getIdCardNumber());
            if (projectWorkerMap.get("num")==0){
                projectWorker.setId(null);
                insert(projectWorker);
            }

        }
    }




    //解决 BeanUtils.copyProperties()的string转date异常
    private void stringToDateException() {
        ConvertUtils.register(new Converter() {
            @Override
            public Object convert(Class type, Object value) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    if ("".equals(value.toString())){
                        return null;
                    }
                    return simpleDateFormat.parse(value.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }, java.util.Date.class);
    }

    @Override
    public List<ProjectWorker> queryProjectWorkerById(String organizationCode, Long id, String projectCode) {
        return this.baseMapper.queryProjectWorkerById(organizationCode, id, projectCode);
    }

}
