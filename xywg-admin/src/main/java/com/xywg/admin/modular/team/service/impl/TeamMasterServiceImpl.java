package com.xywg.admin.modular.team.service.impl;

import cn.hutool.json.JSONUtil;

import com.alibaba.druid.support.logging.Log; 
import com.alibaba.druid.support.logging.LogFactory;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.config.handler.RemoteDTO;
import com.xywg.admin.config.handler.ZbusHandler;
import com.xywg.admin.core.common.exception.BizExceptionEnum;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.util.DateUtil;
import com.xywg.admin.modular.device.model.Type;
import com.xywg.admin.modular.company.dao.ContractorWorkerMapper;
import com.xywg.admin.modular.company.dao.EntryExitHistoryMapper;
import com.xywg.admin.modular.company.dao.SubContractorMapper;
import com.xywg.admin.modular.company.model.ContractorWorker;
import com.xywg.admin.modular.company.model.EntryExitHistory;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.company.service.IContractorWorkerService;
import com.xywg.admin.modular.device.dao.DeviceMapper;
import com.xywg.admin.modular.device.dao.DeviceRecordMapper;
import com.xywg.admin.modular.device.model.DeviceCommand;
import com.xywg.admin.modular.device.model.DeviceRecord;
import com.xywg.admin.modular.device.service.IDeviceCommandService;
import com.xywg.admin.modular.faceUtils.Identity;
import com.xywg.admin.modular.led.service.UserProjectForLedService;
import com.xywg.admin.modular.project.dao.ProjectMasterMapper;
import com.xywg.admin.modular.project.dao.ProjectWorkerMapper;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.model.ProjectWorker;
import com.xywg.admin.modular.project.service.IProjectMasterService;
import com.xywg.admin.modular.project.service.IProjectWorkerService;
import com.xywg.admin.modular.projectSubContractor.dao.ProjectSubContractorMapper;
import com.xywg.admin.modular.projectSubContractor.model.ProjectSubContractor;
import com.xywg.admin.modular.smz.dao.IfaLaborMapper;
import com.xywg.admin.modular.smz.dao.SmzLwtMapper;
import com.xywg.admin.modular.smz.model.IfaLabor;
import com.xywg.admin.modular.smz.model.ProjectPersonnelSmz;
import com.xywg.admin.modular.smz.model.SmzCallBack;
import com.xywg.admin.modular.smz.model.SmzLwt;
import com.xywg.admin.modular.smz.model.TeamMo;
import com.xywg.admin.modular.smz.model.TeamSmz;
import com.xywg.admin.modular.smz.utils.Base64;
import com.xywg.admin.modular.smz.utils.Constant;
import com.xywg.admin.modular.smz.utils.DateUtils;
import com.xywg.admin.modular.smz.utils.FTPClientUtil;
import com.xywg.admin.modular.smz.utils.FileUtil;
import com.xywg.admin.modular.smz.utils.HttpClientUtils;
import com.xywg.admin.modular.system.dao.DeptMapper;
import com.xywg.admin.modular.system.dao.FileInfoMapper;
import com.xywg.admin.modular.system.model.FileInfo;
import com.xywg.admin.modular.system.service.AccountProjectService;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.system.service.IDictService;
import com.xywg.admin.modular.system.service.IUserService;
import com.xywg.admin.modular.team.dao.TeamMasterMapper;
import com.xywg.admin.modular.team.model.*;
import com.xywg.admin.modular.team.service.ITeamMasterService;
import com.xywg.admin.modular.worker.dao.ContractFileMapper;
import com.xywg.admin.modular.worker.dao.WorkerContractRuleMapper;
import com.xywg.admin.modular.worker.dao.WorkerMasterMapper;
import com.xywg.admin.modular.worker.model.ContractFile;
import com.xywg.admin.modular.worker.model.ContractFileParam;
import com.xywg.admin.modular.worker.model.WorkerContractRule;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 班组基础信息 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-22
 */
@Service
public class TeamMasterServiceImpl extends ServiceImpl<TeamMasterMapper, TeamMaster> implements ITeamMasterService {
    private static Logger log = LoggerFactory.getLogger(TeamMasterServiceImpl.class);
    @Autowired
    protected UserProjectForLedService ledService;
    @Autowired
    protected TeamMasterMapper mapper;
    @Autowired
    protected ProjectWorkerMapper projectWorkerMapper;
    @Autowired
    protected EntryExitHistoryMapper entryExitHistoryMapper;

    @Autowired
    protected IWorkerMasterService workerMasterService;

    @Autowired
    protected IProjectMasterService projectMasterService;

    @Autowired
    protected ProjectSubContractorMapper projectSubContractorMapper;

    @Autowired
    protected FileInfoMapper fileInfoMapper;

    @Autowired
    protected ContractFileMapper contractFileMapper;

    @Autowired
    protected WorkerContractRuleMapper workerContractRuleMapper;

    @Autowired
    protected IUserService userService;

    @Autowired
    protected IDictService dictService;

    @Autowired
    protected WorkerMasterMapper workerMapper;

    @Autowired
    protected IContractorWorkerService contractorWorkerService;

    @Autowired
    protected ContractorWorkerMapper contractorWorkerMapper;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private ProjectMasterMapper projectMasterMapper;

    @Autowired
    private DeviceRecordMapper deviceRecordMapper;

    @Autowired
    private SubContractorMapper subContractorMapper;

    @Autowired
    private SmzLwtMapper smzLwtMapper;
    @Autowired
    private IfaLaborMapper ifaLaborMapper;
    @Autowired
    private AccountProjectService accountProjectService;

    @Autowired
    private IDeviceCommandService deviceCommandService;
    

    @Autowired
    private IProjectWorkerService projectWorkerService;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    protected DeptMapper deptMapper;

    private static final Log LOG = LogFactory.getLog(TeamMasterServiceImpl.class);
    private static Properties systemParams = new Properties();

    /**
     * 加载配置文件
     */
    static {
        try {
            systemParams.load(TeamMasterServiceImpl.class.getClassLoader().getResourceAsStream("smz.properties"));
        } catch (IOException e) {
            LOG.error("smz.properties" + "配置文件加载失败");
        }
    }

    @Override
    public Integer updateContractInfo(ContractFile contractFile) {
        return this.baseMapper.updateContractInfo(contractFile);
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
//        Map<String, Object> m = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(result), Map.class);
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
//        Map<String, Object> m = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(result), Map.class);
//        Map<String, String> headers = new HashMap<>();
//        headers.put("token", (String) m.get("token"));
//        return headers;
//    }

    /**
     * @description 保存实名制班组数据（盐城）
     * @author chupp
     * @date 2018年7月11日
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void saveTeamMasterFromSMZYC(Map<String, String> myc) {
        //获取实名制班组数据
        String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefixYC")
                + getSystemStringParam("saveTeamMasterFromSMZYC"), new HashMap<String, Object>(), myc);
        Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
        //存在新的班组信息
        if (map.get("group") != null) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("group"));
            List<TeamSmz> teamList = JSONArray.toList(jsonArray, new TeamSmz(), new JsonConfig());
            List<SmzLwt> slList = smzLwtMapper.getLwtIdBySmzId(0, "Company");
            if (slList.size() == 0) throw new RuntimeException("实名制接口企业数据为空");
            SubContractor subContractor = subContractorMapper.selectById(slList.get(0).getLwtId());
            if (subContractor == null) throw new RuntimeException("实名制接口企业数据对象为空");
            String id = "0";
            if (teamList.size() > 0) {
                for (TeamSmz ts : teamList) {
                    long smzId = Long.parseLong(ts.getId());
                    TeamMaster tm = new TeamMaster();
                    slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(ts.getProjectId()), "ProjectYC");
                    if (slList.size() == 0) throw new RuntimeException("实名制接口班组接口项目信息为空");
                    Map<String, Object> chProject = projectMasterMapper.selectById(slList.get(0).getLwtId());
                    if (chProject == null) throw new RuntimeException("实名制接口班组接口项目对象为空");
                    tm.setProjectCode((String) chProject.get("projectCode"));
                    tm.setOrganizationCode(subContractor.getOrganizationCode());
                    tm.setTeamName(ts.getName());
                    tm.setStatus(1);
                    tm.setIsDel(0);
                    this.baseMapper.insert(tm);
                    tm.setTeamSysNo(tm.getId());
                    this.baseMapper.updateById(tm);
                    long lwtId = tm.getId();
                    SmzLwt sl = new SmzLwt();
                    sl.setSmzId(smzId);
                    sl.setLwtId(lwtId);
                    sl.setTableName("ClassYC");
                    smzLwtMapper.saveSmzLwt(sl);
                    if (Long.parseLong(id) < smzId) {
                        id = String.valueOf(smzId);
                    }
                }
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("code", "1");
                m.put("no", id);
                //响应保存成功信息
                HttpClientUtils.post(getSystemStringParam("httpUrlPrefixYC")
                        + getSystemStringParam("saveTeamMasterFromSMZResponseYC"), m, myc);
            }
        }
    }

    /**
     * @description 获取实名制劳动合同（盐城）
     * @author chupp
     * @date 2018年7月13日
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void saveLaborContractFromSMZYC(Map<String, String> myc) {
        // 获取实名制劳动合同
        String jsonResult = HttpClientUtils.post(
                getSystemStringParam("httpUrlPrefixYC") + getSystemStringParam("saveContractFromSMZYC"),
                new HashMap<String, Object>(), myc);
        Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
        // 存在新的劳动合同
        if (map.get("workerContract") != null) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("workerContract"));
            List<ProjectPersonnelSmz> ppsList = JSONArray.toList(jsonArray, new ProjectPersonnelSmz(), new JsonConfig());
            String id = "0";
            List<SmzLwt> slList = smzLwtMapper.getLwtIdBySmzId(0, "Company");
            if (slList.size() == 0) throw new RuntimeException("实名制接口企业数据为空");
            SubContractor subContractor = subContractorMapper.selectById(slList.get(0).getLwtId());
            if (subContractor == null) throw new RuntimeException("实名制接口企业数据对象为空");
            if (ppsList.size() > 0) {
                //获取FTP连接
                Random ra = new Random();
                String ran = "" + ra.nextInt(10) + ra.nextInt(10);
                String storeFilePath = "lwtgb_smz" + FileUtil.SEPARATOR
                        + DateUtils.getCurrentDate("yyyy") + FileUtil.SEPARATOR
                        + DateUtils.getCurrentDate("MM") + FileUtil.SEPARATOR
                        + DateUtils.getCurrentDate("dd") + FileUtil.SEPARATOR
                        + DateUtils.getCurrentDate("HH") + FileUtil.SEPARATOR
                        + ran + FileUtil.SEPARATOR;
                FTPClient connectFtp = null;
                try {
                    connectFtp = FTPClientUtil.connectFtp(Constant.FTP_HOST, Constant.FTP_PORT, Constant.FTP_USERNAME, Constant.FTP_PASS_WORD, storeFilePath);
                } catch (SocketException e) {
                    log.error(e.getMessage());
                    throw new RuntimeException("实名制接口培训附件FTP连接错误");
                } catch (IOException e) {
                    log.error(e.getMessage());
                    throw new RuntimeException("实名制接口培训附件FTP连接错误");
                }
                try {
                    for (ProjectPersonnelSmz pps : ppsList) {
                        long smzId = Long.parseLong(pps.getId());
                        String contractCode = UUID.randomUUID().toString();
                        String path = "";
                        //保存劳动合同
                        try {
                            if (pps.getWinBinddingFile() != null) {
                                String newName = UUID.randomUUID().toString().replace("-", "") + "." + pps.getHzm();
                                FTPClientUtil.uploadFile(new ByteArrayInputStream(Base64.decode(pps.getWinBinddingFile())),
                                        new String((newName).getBytes("utf-8"), "iso-8859-1"), connectFtp);
                                path = storeFilePath + newName;
                            } else {
                                throw new RuntimeException("实名制接口劳动合同为空");
                            }
                        } catch (IOException e) {
                            log.error(e.getMessage());
                            throw new RuntimeException("实名制接口劳动合同转换错误");
                        }
                        ContractFile cf = new ContractFile();
                        WorkerContractRule wcr = new WorkerContractRule();
                        slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(pps.getProjectId()), "ProjectYC");
                        if (slList.size() == 0) throw new RuntimeException("实名制接口合同所属项目为空");
                        Map<String, Object> chProject = projectMasterMapper.selectById(slList.get(0).getLwtId());
                        if (chProject == null) throw new RuntimeException("实名制接口合同所属项目对象为空");
                        List<Map<String, Object>> chProjectWorker = projectWorkerMapper.getForFaceUse((String) chProject.get("projectCode"), 1, pps.getIdcardNumber());
                        if (chProjectWorker.size() == 0) throw new RuntimeException("实名制接口考勤记录人员项目关系为空");
                        if (chProjectWorker.get(0).get("team_sys_no") == null || chProjectWorker.get(0).get("team_sys_no").equals(""))
                            throw new RuntimeException("实名制接口合同接口班组编号为空");
                        cf.setProjectCode((String) chProject.get("projectCode"));
                        cf.setOrganizationCode(subContractor.getOrganizationCode());
                        cf.setContractCode(contractCode);
                        cf.setIdCardType("1");
                        cf.setIdCardNumber(pps.getIdcardNumber());
                        cf.setFileName("劳动合同");
                        cf.setFilePath(path);
                        cf.setFacePath("smz");
                        cf.setIsDel(0);
                        contractFileMapper.insert(cf);
                        wcr.setProjectCode((String) chProject.get("projectCode"));
                        wcr.setOrganizationCode(subContractor.getOrganizationCode());
                        wcr.setIdCardType("1");
                        wcr.setIdCardNumber(pps.getIdcardNumber());
                        wcr.setContractCode(contractCode);
                        if (pps.getContractPeriodtype() != null)
                            wcr.setContractPeriodType(Integer.parseInt(pps.getContractPeriodtype()));
                        wcr.setStartDate(pps.getStartDate());
                        wcr.setEndDate(pps.getEndDate());
                        wcr.setIsDel(0);
                        workerContractRuleMapper.insert(wcr);
                        // 更新签署标记（班组工人关系表）
                        List<Map<String, Object>> mapList = this.baseMapper.getMemberJoinStatus((int) chProjectWorker.get(0).get("team_sys_no"),
                                1, pps.getIdcardNumber());
                        if (mapList.size() != 1) {
                            throw new XywgException(BizExceptionEnum.JOIN_TEAM_ERROR);
                        }
                        this.baseMapper.updateTeamSign(String.valueOf(mapList.get(0).get("id")));
                        // 更新签署标记（项目工人关系表）
                        ProjectWorker pw = projectWorkerMapper.getByWorkerAndTeam((int) chProjectWorker.get(0).get("team_sys_no"),
                                "1", pps.getIdcardNumber());
                        if (pw == null) {
                            throw new XywgException(BizExceptionEnum.JOIN_TEAM_ERROR);
                        }
                        projectWorkerMapper.updateTeamSign(String.valueOf(pw.getId()));
                        if (Long.parseLong(id) < smzId) {
                            id = String.valueOf(smzId);
                        }
                    }
                } finally {
                    if (connectFtp != null && connectFtp.isConnected()) {
                        try {
                            connectFtp.logout();
                        } catch (IOException e) {
                            log.error(e.getMessage());
                            connectFtp = null;
                        }
                    }
                }
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("code", "1");
                m.put("no", id);
                // 响应保存成功信息
                HttpClientUtils.post(getSystemStringParam("httpUrlPrefixYC")
                        + getSystemStringParam("saveContractFromSMZResponseYC"), m, myc);
            }
        }
    }

    @Override
    public boolean getTeamFromLabor(List<Long> ids, Map<String, String> m) {
        List<TeamMo> list = mapper.getTeamFromLabor(ids);
        if (list.size() > 0) {
            Map<String, Object> map = new HashMap<String, Object>();
            String json = JSONArray.fromObject(list).toString();
            map.put("laborGroup", json);
            String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefix") + getSystemStringParam("saveTeamFromLabor"), map, m);
            System.out.println(jsonResult);
            Map<String, String> result = JSONUtil.toBean(jsonResult, Map.class);
            if ("-2".equals(result.get("code"))) {
                return false;
            } else {
                return true;
            }
        }
        return false;

    }

    @Override
    public int setIsdel(Integer userId) {
        return mapper.setIsdel(userId);
    }

    @Override
    public TeamMaster getById(Integer teamId) {
        return mapper.getById(teamId);
    }

    @Override
    public List<Map<String, Object>> getTeams(Page<TeamMaster> page, Map<String, Object> map, String string, boolean b) {
        map.put("deptId", ShiroKit.getUser().getDeptId());
        Integer deptId = ShiroKit.getUser().getDeptId();
        // 获取公司以及子公司社会信用代码集合
        List<String> list = deptService.getOrganizationCodeByDeptId(deptId);
        map.put("projectCodes", accountProjectService.getProjectCodes());
        return mapper.getTeams(page, map, list, string, b, accountProjectService.getSwitchType());
    }

    @Override
    public List<Map<String, Object>> getMemberByTeamCode(Page<WorkerMaster> page, Map<String, Object> map,
                                                         String string, boolean b) {
        return mapper.getMemberByTeamCode(page, map, string, b);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int setTeamLeader(Integer memberId) {
        Map<String, Object> teamMemberByTMid = mapper.getTeamMemberByTMid(memberId);
        /**
         * 先撤销原先的班组长
         */
        mapper.deleteLeader((Integer) teamMemberByTMid.get("teamSysNo"));
        int setTeamLeader = mapper.setTeamLeader(memberId);
        /**
         * 修改班组联系人信息
         */
        WorkerMaster workerByIdCard = workerMapper.getWorkerByIdCard(teamMemberByTMid.get("idCardNumber").toString(), Integer.valueOf(teamMemberByTMid.get("idCardType").toString()));
        mapper.updateLeaderInfo(workerByIdCard.getWorkerName(), workerByIdCard.getIdCardNumber(), workerByIdCard.getCellPhone(), (Integer) teamMemberByTMid.get("teamSysNo"));
        return setTeamLeader;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> addMember(Map<String, Object> map) {
    	  Map<String, Object> returnMap = new HashMap<>();
    	  List<Object> workers = new ArrayList<Object>();
    	  boolean flag = false;
          String idStrs = (String) map.get("idStrs");
          if (idStrs.contains(",")) {
              idStrs = idStrs.substring(1);
          }
          String teamCodeStr = (String) map.get("teamCode");
          Integer teamCode = Integer.valueOf(teamCodeStr);
          TeamMaster byteamCode = mapper.getByteamCode(teamCode);
          
          //判断是否是班组长
          int teamWorkType = 0;
          if(map.get("teamWorkType") != null) {
          	teamWorkType = Integer.valueOf((String)map.get("teamWorkType"));
          }
          
          String face =(String)map.get("face");
          String cardNumber= (String)map.get("cardNumber");
          String from= (String)map.get("from");
          
          
          /**
           * 获取当前时间 类型为sql格式的date
           */
          Date time = new Date(new java.util.Date().getTime());
          /**
           * 先查出所选中的所有人
           */
          List<WorkerMaster> selectWorkers = mapper.selectWorkers(idStrs);
          //判断有没有人已经加入过班组所在项目
          for (WorkerMaster workerMaster : selectWorkers) {
              boolean workerInProjectStatus = this.getWorkerInProjectStatus(byteamCode.getProjectCode(), workerMaster.getIdCardType(), workerMaster.getIdCardNumber());
              if (workerInProjectStatus == true) {
                  flag = true;
              }
              //无人脸时查看数据库是否有人脸
              if(StringUtils.isBlank(face) && face != null) {
              	face = workerMaster.getFace();
              }
          }
          if (flag) {
  				throw new XywgException(711, "项目中已存在相关人员！");
          } else {
              /**
               * 插入班组关系表
               */
              for (WorkerMaster workerMaster : selectWorkers) {
                  TeamMemberShip teamMemberShip = new TeamMemberShip(teamCode, workerMaster.getIdCardType(), workerMaster.getIdCardNumber());
                  teamMemberShip.setTeamWorkerType(teamWorkType);
                  mapper.addMember(teamMemberShip);
              }
              /**
               * 插入项目关系表
               */
              
              for (WorkerMaster workerMaster : selectWorkers) {
                  /**
                   * 插关系
                   */
                  ProjectWorker projectWorker = new ProjectWorker(byteamCode.getProjectCode(), byteamCode.getOrganizationCode(), teamCode, workerMaster.getIdCardType().toString(), workerMaster.getIdCardNumber(), 2, workerMaster.getWorkTypeCode(), workerMaster.getCellPhone(), 0, 2);
                  if(map.get("teamWorkType") != null) {
                  	projectWorker.setTeamWorkerType(Integer.parseInt(map.get("teamWorkType")+""));
                  }
                   //查询考勤机考勤方式的编码
                   Integer num = dictService.selectNumByName("考勤类型", "考勤机");
                   ProjectMaster projectByProjectCode = projectMasterService.getProjectByProjectCode(byteamCode.getProjectCode());
                   //如果考勤类型是考勤机则进场状态修改为待进场
                   if (projectByProjectCode != null ) {
                       /*&& projectByProjectCode.getDeviceType().equals(num)*/
                      /**
                       * 判断是否录入了人脸，已录入为进场，未录入待进场
                       */
                        projectWorker.setJoinStatus(1);
                        if(StringUtils.isNotBlank(face) || StringUtils.isNotBlank(cardNumber)) {
                      	  //人员进场
                      	  projectWorker.setJoinStatus(2);
                       }
                   }else {
                	   /**
                        * 插入人脸数据
                        */
                       workerMasterService.inputFaceAndProjectCode(workerMaster.getId().toString(), byteamCode.getProjectCode());
                   }
                   //判断卡是否绑定
                   if(StringUtils.isNotBlank(cardNumber)) {
                   	  projectWorkerService.getCountByCardNumber(cardNumber,null);
                   	  projectWorker.setCardNumber(cardNumber);
                   }     
                  projectWorker.setEntryTime(time);
                  projectWorkerMapper.insert(projectWorker);
                  //插入与实名制对接接口表 xss
                  if (projectByProjectCode.getIsSynchro() == 1) {
                      IfaLabor ifaLabor = new IfaLabor("buss_project_worker", projectWorker.getId());
                      ifaLaborMapper.insert(ifaLabor);
                  }
                  workers.add(projectWorker);
              }

              if (!"worker".equals(map.get("from"))) {
                  /**
                   * 插入公司工人关系表
                   */
                  for (WorkerMaster workerMaster : selectWorkers) {
                      if (contractorWorkerMapper.getByIdCardAndOrganization(workerMaster.getIdCardNumber(),
                              dictService.selectNumByName("人员证件类型", "居民身份证"), byteamCode.getOrganizationCode()) == null) {
                          ContractorWorker contractorWorker = new ContractorWorker(workerMaster.getIdCardType(), workerMaster.getIdCardNumber(), workerMaster.getWorkerName(), byteamCode.getOrganizationCode());
                          contractorWorkerService.insert(contractorWorker);
                      }
                  }
              }

              /**
               * 生成进场履历
               */
              for (WorkerMaster workerMaster : selectWorkers) {
                  EntryExitHistory entryExitHistory = new EntryExitHistory(byteamCode.getProjectCode(), byteamCode.getOrganizationCode(), workerMaster.getIdCardType().toString(), workerMaster.getIdCardNumber(), time, 0);
                  entryExitHistoryMapper.insert(entryExitHistory);
              }
          }
          returnMap.put("ok", true);
          returnMap.put("projectWorker", workers);
          dispatchUser(selectWorkers,byteamCode.getProjectCode(),face,cardNumber,from);
          return returnMap;
    }
    
    public void dispatchUser(List<WorkerMaster> selectWorkers,String projectCode,String face ,String cardNumber,String from) {
    	if(StringUtils.isNotBlank(from) && from.equals("worker") ) {
    		return;
    	}
		if(StringUtils.isNotBlank(face) || StringUtils.isNotBlank(cardNumber)) {
	    	for (WorkerMaster workerMaster : selectWorkers) {
	    		workerMaster.setProjectCode(projectCode);
	   	  		this.workerMasterService.dispatchUser(workerMaster);
	    	}
    	 }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteMember(Integer memberId) {
        /**
         * 删除成员需要删除班组关系，并且删除项目关系
         */
        boolean flag = true;
        TeamMemberShip memberByTMid = mapper.getMemberByTMid(memberId);
        ProjectWorker projectWorker = projectWorkerMapper.getByWorkerAndTeam(memberByTMid.getTeamSysNo(), memberByTMid
                .getIdCardType().toString(), memberByTMid.getIdCardNumber());
        if (projectWorker != null) {
            if (projectWorker.getJoinStatus() == 2) {
                flag = false;
            } else {
                mapper.deleteProjectWorker(projectWorker.getId());
                mapper.deleteMember(memberId);
            }
        }
        return flag;
    }

    @Override
    public List<Map<String, Object>> getUnteamWorker(Page<WorkerMaster> page, Map<String, Object> map,
                                                     String orderByField, boolean asc) {
    	map.put("depts", deptService.getUserDeptAndSubdivisionOrganizationCode());
        List<Map<String, Object>> unteamWorker = mapper.getUnteamWorker(page, map, orderByField, asc);
//        for (Map<String, Object> map2 : unteamWorker) {
//            List<Map<String, Object>> searchWorkerBlack = workerMapper.searchWorkerBlack(
//                    Integer.valueOf(map2.get("idCardType").toString()), map2.get("idCardNumber").toString());
//            if (searchWorkerBlack.size() > 0) {
//                map2.put("isBlack", 1);
//            } else {
//                map2.put("isBlack", 0);
//            }
//        }
        return unteamWorker;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insert(TeamMaster entity) {
        if (mapper.getByName(entity.getTeamName(), entity.getProjectCode()) != null) {
            throw new XywgException(711, "该项目下存在同名班组");
        } else {
            Integer insert = baseMapper.insert(entity);
            /**
             * 添加结束后修改班组编号
             */
            mapper.updateAfterInsert(entity.getId());
            if (entity.getLeaderId() != null && entity.getLeaderId().toString() != "") {
                /**
                 * 将班组长添加进项目，并设置为班组长
                 */
                WorkerMaster workerMaster = workerMasterService.selectById(entity.getLeaderId());
                // 与班组，项目添加关系
                /**
                 * 获取当前时间 类型为sql格式的date
                 */
                Date time = new Date(new java.util.Date().getTime());
                TeamMaster byteamCode = mapper.getByteamCode(mapper.getById(entity.getId()).getTeamSysNo());
                /**
                 * 插入班组关系表
                 */
                TeamMemberShip teamMemberShip = new TeamMemberShip(mapper.getById(entity.getId()).getTeamSysNo(), dictService.selectNumByName("人员证件类型", "居民身份证"), workerMaster.getIdCardNumber());
                teamMemberShip.setTeamWorkerType(0);
                mapper.addMember(teamMemberShip);
                /**
                 * 插入人脸数据
                 */
                workerMasterService.inputFaceAndProjectCode(entity.getLeaderId().toString(),
                        byteamCode.getProjectCode());
                /**
                 * 插入项目关系表
                 */
                ProjectWorker projectWorker = new ProjectWorker(byteamCode.getProjectCode(), byteamCode.getOrganizationCode(), byteamCode.getTeamSysNo(), dictService.selectNumByName("人员证件类型", "居民身份证").toString(), workerMaster.getIdCardNumber(), 2, workerMaster.getWorkTypeCode().toString(), workerMaster.getCellPhone(), 0, 2);
                //查询考勤机考勤方式的编码
                Integer num = dictService.selectNumByName("考勤类型", "考勤机");
                ProjectMaster projectByProjectCode = projectMasterService.getProjectByProjectCode(byteamCode.getProjectCode());
                //如果考勤类型是考勤机则进场状态修改为待进场
                if (projectByProjectCode.getDeviceType().equals(num)) {
                    projectWorker.setJoinStatus(1);
                }
                projectWorker.setEntryTime(time);
                projectWorkerMapper.insert(projectWorker);
                //插入与实名制对接接口表 xss
                if (projectByProjectCode.getIsSynchro() == 1) {
                    IfaLabor ifaLabor = new IfaLabor("buss_project_worker", projectWorker.getId());
                    ifaLaborMapper.insert(ifaLabor);
                }

                /**
                 * 插入公司工人关系表
                 */
                if (contractorWorkerMapper.getByIdCardAndOrganization(workerMaster.getIdCardNumber(),
                        dictService.selectNumByName("人员证件类型", "居民身份证"), byteamCode.getOrganizationCode()) == null) {
                    ContractorWorker contractorWorker = new ContractorWorker(dictService.selectNumByName("人员证件类型", "居民身份证"), workerMaster.getIdCardNumber(), workerMaster.getWorkerName(), byteamCode.getOrganizationCode());
                    contractorWorkerService.insert(contractorWorker);
                }
                /**
                 * 生成进场履历
                 */
                EntryExitHistory entryExitHistory = new EntryExitHistory(byteamCode.getProjectCode(), byteamCode.getOrganizationCode(), dictService.selectNumByName("人员证件类型", "居民身份证").toString(), workerMaster.getIdCardNumber(), time, 0);
                entryExitHistoryMapper.insert(entryExitHistory);
            }
            ProjectMaster project = projectMasterService.getProjectByProjectCode(entity.getProjectCode());
            /**
             *插入劳务通与实名制对接表 xss
             */
            if (project.getIsSynchro() == 1) {
                IfaLabor ifaLabor = new IfaLabor("buss_team_master", Long.valueOf(entity.getId()));
                ifaLaborMapper.insert(ifaLabor);
            }

            return retBool(insert);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteById(Serializable id) {
        /**
         * 删除前要判断该班组下是否有员工
         */
        List<WorkerMaster> memberByTeamId = mapper.getTeamMemberByTeamCode(mapper.getById((Integer) id).getTeamSysNo());
        if (memberByTeamId.size() > 0) {
            return false;
        } else {
            return SqlHelper.delBool(baseMapper.deleteById(id));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void workerJoin(String memberIds) {
        String[] result = memberIds.split(",");
        for (String memberId : result) {
            TeamMemberShip memberByTMid = mapper.getMemberByTMid(Integer.parseInt(memberId));
            //判断是否有人已进场，已进场的不给再次进场
            ProjectWorker byWorkerAndTeam = projectWorkerMapper.getByWorkerAndTeam(memberByTMid.getTeamSysNo(), memberByTMid.getIdCardType().toString(), memberByTMid.getIdCardNumber());
            if (byWorkerAndTeam.getJoinStatus() == 2) {
                throw new XywgException(711, "存在已进场的人员，请重新选择");
            }
            //判断项目是否是考勤机考勤项目，如果是则判断是否有人脸模版
            Integer num = dictService.selectNumByName("考勤类型", "考勤机");
            ProjectMaster projectByProjectCode = projectMasterService.getProjectByProjectCode(byWorkerAndTeam.getProjectCode());
            if (projectByProjectCode.getDeviceType().equals(num)) {
                WorkerMaster workerByIdCard = workerMasterService.getWorkerByIdCard(memberByTMid.getIdCardNumber(), memberByTMid.getIdCardType());
                //判断是否录入人脸
                if (StringUtils.isBlank(workerByIdCard.getFace())) {
                    throw new XywgException(711, workerByIdCard.getWorkerName() + "请先录入或重新选择人员");
                } else {
                    //获取项目下所有考勤机的ids
                    List<String> deviceIds = this.deviceMapper.getAllDeviceSnsByProjectCode(byWorkerAndTeam.getProjectCode());
                    if (deviceIds.size() == 0) {
                        throw new XywgException(600, "进场失败,请先为项目配置考勤机");
                    }
                    List<String> workerIds = new ArrayList<String>();
                    workerIds.add(workerByIdCard.getIdCardNumber());
                    try {
                    	DeviceCommand command = new DeviceCommand(deviceIds, Type.DispatchUser.getValue(), workerIds);
                        this.deviceCommandService.executeCommand(command);
                    } catch (Exception e) {
                        throw new XywgException(600, "考勤命令下发异常" + e.getMessage());
                    }
                }
                workerByIdCard.setProjectCode(byWorkerAndTeam.getProjectCode());
                dispatchUser(workerByIdCard);
            }
            mapper.workerJoin(memberByTMid.getIdCardType().toString(), memberByTMid.getIdCardNumber(), memberByTMid.getTeamSysNo());
            /* 生成进退场履历 */
            /**
             * 获取当前时间 类型为sql格式的date
             */
            Date time = new Date(new java.util.Date().getTime());
            TeamMaster byteamCode = mapper.getByteamCode(memberByTMid.getTeamSysNo());
            EntryExitHistory entryExitHistory = new EntryExitHistory(byteamCode.getProjectCode(), byteamCode.getOrganizationCode(), memberByTMid.getIdCardType().toString(), memberByTMid.getIdCardNumber(), time, 0);
            entryExitHistoryMapper.insert(entryExitHistory);
            //完成调用zbus推送给智慧工地
            ProjectMaster projectMaster = projectMasterMapper.getProjectByProjectCode(byWorkerAndTeam.getProjectCode());
            if(projectMaster.getIsSynchro().intValue() == 1) {
                //项目已经加入到智慧工地
                Map<String, Object> map = new HashMap<>();
                map.put("relationUserId", byWorkerAndTeam.getId());
                //map.put("relationProjectId", byWorkerAndTeam.getProjectCode());
                map.put("relationProjectId", projectMaster.getId());
                map.put("enterStatus", "进场");
                map.put("enterTime", DateUtil.format(time, "yyyy-MM-dd HH:mm:ss"));
                map.put("platform", 2);
                RemoteDTO dto = new RemoteDTO();
                Map<String, Object> mm = new HashMap<>();
                mm.put("record", map);
                mm.put("operationFlag", "insertRecordExternalWorker");
                dto.setBody(mm);
               // ZbusHandler.send(JSONObject.fromObject(dto).toString());
            }
        }
    }

    /**
     * 给考勤机下发项目下所有人脸
     *
     * @param entity
     */
    private void dispatchUser(WorkerMaster entity) {
        //人脸下发
        List<String> deviceSns = deviceMapper.getAllDeviceSnsByProjectCode(entity.getProjectCode());
        List<String> userIds = new ArrayList<String>();
        userIds.add(entity.getIdCardNumber()+","+entity.getIdCardType());
        DeviceCommand deviceCommand = new DeviceCommand(deviceSns, 7, userIds);
        deviceCommandService.dispatchUser(deviceCommand);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void workerOut(String memberIds) {
        String[] result = memberIds.split(",");
        String imeis = "";
        for (String memberId : result) {
            TeamMemberShip memberByTMid = mapper.getMemberByTMid(Integer.parseInt(memberId));
            //查询imei并拼接
            String imei = projectWorkerMapper.getShImeiByPerson(memberByTMid.getTeamSysNo(), memberByTMid.getIdCardType(), memberByTMid.getIdCardNumber());
            if (imei != null && imei != "") {
                imeis += imei + ",";
            }
            //判断是否有人已退场或待进场，只有已进场的才能退场
            ProjectWorker byWorkerAndTeam = projectWorkerMapper.getByWorkerAndTeam(memberByTMid.getTeamSysNo(), memberByTMid.getIdCardType().toString(), memberByTMid.getIdCardNumber());
            if (byWorkerAndTeam.getJoinStatus() != 2) {
                throw new XywgException(711, "存在未进场的人员,无法退场,请重新选择");
            }
            //退场 删除人脸模板
            //获取项目下所有考勤机的ids
            List<String> deviceIds = this.deviceMapper.getAllDeviceSnsByProjectCode(byWorkerAndTeam.getProjectCode());
            if (deviceIds.size() > 0) {
                List<String> workerIds = new ArrayList<String>();
                workerIds.add(byWorkerAndTeam.getIdCardNumber());
                try {
                	DeviceCommand device = new DeviceCommand(deviceIds, Type.DeleteUser.getValue(), workerIds);
                    this.deviceCommandService.executeCommand(device);
                } catch (Exception e) {
                    throw new XywgException(600, "考勤命令下发异常" + e.getMessage());
                }
            }

            mapper.workerOut(memberByTMid.getIdCardType().toString(), memberByTMid.getIdCardNumber(), null, memberByTMid.getTeamSysNo());
            /* 生成进退场履历 */
            /**
             * 获取当前时间 类型为sql格式的date
             */
            Date time = new Date(new java.util.Date().getTime());
            TeamMaster byteamCode = mapper.getByteamCode(memberByTMid.getTeamSysNo());
            EntryExitHistory entryExitHistory = new EntryExitHistory(byteamCode.getProjectCode(), byteamCode.getOrganizationCode(), memberByTMid.getIdCardType().toString(), memberByTMid.getIdCardNumber(), time, 1);
            entryExitHistoryMapper.insert(entryExitHistory);

            //完成调用zbus推送给智慧工地
            ProjectMaster projectMaster = projectMasterMapper.getProjectByProjectCode(byWorkerAndTeam.getProjectCode());
            if(projectMaster.getIsSynchro().intValue() == 1) {
                //已经同步的项目 信息进行推送
                Map<String, Object> map = new HashMap<>();
                map.put("relationUserId", byWorkerAndTeam.getId());
                //map.put("relationProjectId", byWorkerAndTeam.getProjectCode());
                map.put("relationProjectId", projectMaster.getId());
                map.put("enterStatus", "退场");
                map.put("enterTime", DateUtil.format(time, "yyyy-MM-dd HH:mm:ss"));
                map.put("platform", 2);
                RemoteDTO dto = new RemoteDTO();
                Map<String, Object> mm = new HashMap<>();
                mm.put("record", map);
                mm.put("operationFlag", "insertRecordExternalWorker");
                dto.setBody(mm);
               // ZbusHandler.send(JSONObject.fromObject(dto).toString());
            }
        }
        //修改安全帽状态
        if (imeis.length() > 0) {
            imeis = imeis.substring(0, imeis.length() - 1);
            mapper.updateShImeiStatus(imeis);
        }
    }

    @Override
    public List<AppGetTeamInfoVo> getTeamInfoLike(String teamLeaderName, Integer idCardType, String idCardNumber) {
        List<AppGetTeamInfoVo> getTeamInfoVos = new ArrayList<AppGetTeamInfoVo>();
        List<TeamCodeAndWorkerIdVo> teamCodeAndWorkerIdByWorkerName = mapper
                .getTeamCodeAndWorkerIdByWorkerName(teamLeaderName);
        if (teamCodeAndWorkerIdByWorkerName != null) {
            for (TeamCodeAndWorkerIdVo teamCodeAndWorkerIdVo : teamCodeAndWorkerIdByWorkerName) {
                AppGetTeamInfoVo getTeamInfoVo = new AppGetTeamInfoVo();
                TeamMaster byteamCode = mapper.getByteamCode(teamCodeAndWorkerIdVo.getTeamCode());
                WorkerMaster workerById = mapper.getWorkerById(teamCodeAndWorkerIdVo.getWorkerId());
                // 查询该工人是否加入该班组所在的项目
                List<Map<String, Object>> memberJoinStatus = mapper.getWorkerInProjectStatus(
                        byteamCode.getProjectCode(), idCardType, idCardNumber);
                if (memberJoinStatus.size() > 0) {
                    getTeamInfoVo.setJoinStatus(1);
                } else {
                    getTeamInfoVo.setJoinStatus(0);
                }

                getTeamInfoVo.setId(byteamCode.getId());
                getTeamInfoVo.setHeadImage(workerById.getHeadImage());

                if (projectMasterService.getProjectByProjectCode(byteamCode.getProjectCode()) != null) {
                    getTeamInfoVo.setProjectName(projectMasterService.getProjectByProjectCode(
                            byteamCode.getProjectCode()).getProjectName());
                }
                getTeamInfoVo.setTeamLeaderName(workerById.getWorkerName());
                getTeamInfoVo.setTeamName(byteamCode.getTeamName());
                getTeamInfoVo.setTeamLeaderId(mapper.getUserIdByPhone(workerById.getCellPhone()));
                getTeamInfoVos.add(getTeamInfoVo);
            }

        }
        return getTeamInfoVos;
    }

    @Override
    public List<AppTeamMemberVo> getByTeamId(Integer id, Integer joinStatus) {
        return mapper.getAppMemberByTeamCode(mapper.getById(id).getTeamSysNo(), joinStatus);
    }

    @Override
    public void changeTeamJoinStatus(AppTeamJoinStatusDto appJoinStatusDto) {

        /**
         * 获取班组信息
         */
        TeamMaster byId = mapper.getById(appJoinStatusDto.getTeamId());
        /**
         * 获取班组成员
         */
        List<AppTeamMemberVo> appMemberByTeamId = mapper.getAppMemberByTeamCode(
                mapper.getById(appJoinStatusDto.getTeamId()).getTeamSysNo(), null);
        Integer pWidOfTeamLeader = mapper.getPWidOfTeamLeader(byId.getTeamSysNo());
        /**
         * 获取当前时间 类型为sql格式的date
         */
        Date time = new Date(new java.util.Date().getTime());
        EntryExitHistory entryExitHistory = new EntryExitHistory();
        /* 判断进场还是退场 */
        if (appJoinStatusDto.getJoinStatus() == 2) {
            entryExitHistory.setType(0);
            mapper.teamJoin(appJoinStatusDto.getProjectCode(), byId.getTeamSysNo().toString(), pWidOfTeamLeader);
        } else {
            entryExitHistory.setType(1);
            mapper.teamOut(appJoinStatusDto.getProjectCode(), byId.getTeamSysNo().toString(),
                    appJoinStatusDto.getEvalute(), pWidOfTeamLeader);
        }
        /**
         * 班组每个人都要生成进退场履历
         */
        for (AppTeamMemberVo appTeamMemberVo : appMemberByTeamId) {
            if (appTeamMemberVo.getWorkerType() == 1) {
                entryExitHistory.setOrganizationCode(byId.getOrganizationCode());
                entryExitHistory.setProjectCode(appJoinStatusDto.getProjectCode());
                entryExitHistory.setIdCardType(appTeamMemberVo.getIdCardType().toString());
                entryExitHistory.setIdCardNumber(appTeamMemberVo.getIdCardNumber());
                entryExitHistory.setDate(time);
                entryExitHistoryMapper.insert(entryExitHistory);
            }
        }

    }

    @Override
    public List<AppTeamMemberVo> getPersonByTeamId(Integer teamId, String projectCode) {
        List<AppTeamMemberVo> appMemberByTeamId = mapper.getAppMemberByTeamCode(mapper.getById(teamId).getTeamSysNo(),
                null);
        DeviceRecord deviceRecord = new DeviceRecord();
        for (AppTeamMemberVo appTeamMemberVo : appMemberByTeamId) {
            deviceRecord.setProjectCode(projectCode);
            deviceRecord.setIdCardType(appTeamMemberVo.getIdCardType());
            deviceRecord.setIdCardNumber(appTeamMemberVo.getIdCardNumber());
            appTeamMemberVo.setRecordCount(deviceRecordMapper.getAllRecordByProject(deviceRecord));
        }
        return appMemberByTeamId;
    }

    @Override
    public List<AppTeamMemberVo> v116GetPersonByTeamId(Integer teamId, String projectCode, Integer pageNo, Integer pageSize) {
        List<AppTeamMemberVo> appMemberByTeamId = mapper.v116GetAppMemberByTeamCode(mapper.getById(teamId).getTeamSysNo(),
                null, (pageNo - 1) * pageSize, pageSize);
        DeviceRecord deviceRecord = new DeviceRecord();
        for (AppTeamMemberVo appTeamMemberVo : appMemberByTeamId) {
            deviceRecord.setProjectCode(projectCode);
            deviceRecord.setIdCardType(appTeamMemberVo.getIdCardType());
            deviceRecord.setIdCardNumber(appTeamMemberVo.getIdCardNumber());
            appTeamMemberVo.setRecordCount(deviceRecordMapper.getAllRecordByProject(deviceRecord));
        }
        return appMemberByTeamId;
    }


    @Override
    public List<Map<String, Object>> getList(Map<String, Object> map) {
        map.put("deptId", ShiroKit.getUser().getDeptId());
        map.put("projectCodes", accountProjectService.getProjectCodes());
        return mapper.getList(map);
    }

    @Override
    public List<WorkerMaster> getTeamMemberByTeamCode(Integer teamCode) {
        return mapper.getTeamMemberByTeamCode(teamCode);
    }

    @Override
    public List<AppWorkerByKeyVo> getWorkerByKeyAndTeamId(Integer teamId, String key) {
        List<AppWorkerByKeyVo> workerByKey = mapper.getWorkerByKey(key, mapper.getById(teamId).getProjectCode());
        TeamMaster byId = mapper.getById(teamId);
        for (AppWorkerByKeyVo appWorkerByKeyVo : workerByKey) {
            // 查询是否已加入班组所在的项目
            List<ProjectWorker> inProject = projectWorkerMapper.isInProject(byId.getProjectCode(), appWorkerByKeyVo.getIdCardType(),
                    appWorkerByKeyVo.getIdCardNumber());
            // 查询是否在黑名单
            List<Map<String, Object>> searchWorkerBlack = workerMapper.searchWorkerBlack(
                    appWorkerByKeyVo.getIdCardType(), appWorkerByKeyVo.getIdCardNumber());
            if (inProject.size() > 0) {
                appWorkerByKeyVo.setEntryFlag(1);
            } else {
                appWorkerByKeyVo.setEntryFlag(0);
            }
            if (searchWorkerBlack.size() > 0) {
                appWorkerByKeyVo.setIsBlack(1);
            } else {
                appWorkerByKeyVo.setIsBlack(0);
            }
        }
        return workerByKey;
    }

    @Override
    public List<AppWorkerByKeyVo> v116GetWorkerByKeyAndTeamId(Integer teamId, String key, Integer pageNo, Integer pageSize) {
        List<AppWorkerByKeyVo> workerByKey = mapper.v116GetWorkerByKey(key, mapper.getById(teamId).getProjectCode(), (pageNo - 1) * pageSize, pageSize);
        TeamMaster byId = mapper.getById(teamId);
        for (AppWorkerByKeyVo appWorkerByKeyVo : workerByKey) {
            // 查询是否已加入班组所在的项目
            List<ProjectWorker> inProject = projectWorkerMapper.isInProject(byId.getProjectCode(), appWorkerByKeyVo.getIdCardType(),
                    appWorkerByKeyVo.getIdCardNumber());
            // 查询是否在黑名单
            List<Map<String, Object>> searchWorkerBlack = workerMapper.searchWorkerBlack(
                    appWorkerByKeyVo.getIdCardType(), appWorkerByKeyVo.getIdCardNumber());
            if (inProject.size() > 0) {
                appWorkerByKeyVo.setEntryFlag(1);
            } else {
                appWorkerByKeyVo.setEntryFlag(0);
            }
            if (searchWorkerBlack.size() > 0) {
                appWorkerByKeyVo.setIsBlack(1);
            } else {
                appWorkerByKeyVo.setIsBlack(0);
            }
        }
        return workerByKey;
    }

    @Override
    public List<TeamMaster> getTeamMasterByProjectCode(String projectId) {
    	 Integer deptId = ShiroKit.getUser().getDeptId();
        // 获取公司以及子公司社会信用代码集合
        List<String> list = deptService.getOrganizationCodeByDeptId(deptId);
        return mapper.getTeamMasterByProjectCode(projectId,list);
    }

    @Override
    public List<AppTeamInfoVo> getAppTeamsInfo(String organizationCode, String projectCode) {

        List<AppTeamInfoVo> appTeamsByProject = null;
        int contractorType = 0;
        List<ProjectSubContractor> list = projectSubContractorMapper.getListByProjectCodeAndOrganizationCode(projectCode, organizationCode);
        if (null != list && list.size() != 0) {
            contractorType = list.get(0).getContractorType();
        }
        if (contractorType != 16) {
            appTeamsByProject = mapper.getAppTeamsByProject(projectCode, organizationCode);
        } else {
            appTeamsByProject = mapper.getAppTeamsByProject(projectCode, null);
        }

        for (AppTeamInfoVo appTeamInfoVo : appTeamsByProject) {
            appTeamInfoVo.setCount(mapper.getMemberCountByTeamCode(mapper.getById(appTeamInfoVo.getTeamId())
                    .getTeamSysNo()));
        }
        return appTeamsByProject;
    }

    /**
     * @param teamMemberShip
     * @return
     * @description 查询劳动合同
     * @author chupp
     * @date 2018年6月8日
     * @see com.xywg.admin.modular.team.service.ITeamMasterService#getLaborContract(com.xywg.admin.modular.team.model.TeamMemberShip)
     */
    @Override
    public Map<String, Object> getLaborContract(TeamMemberShip teamMemberShip) {
        Map<String, Object> map = new HashMap<>();
        int isSign = 0;
        String path = null;
        String projectCode = null;
        String organizationCode = null;
        String fileName = null;

        // 获取班组工人关系数据
        List<Map<String, Object>> mapList = this.baseMapper.getMemberJoinStatus(teamMemberShip.getTeamSysNo(),
                teamMemberShip.getIdCardType(), teamMemberShip.getIdCardNumber());
        if (mapList.size() == 0) {

            throw new XywgException(BizExceptionEnum.JOIN_TEAM_ERROR);
        }

        // 未签合同

        TeamMaster tm = this.baseMapper.getTeamInfoByTeamSysNo(teamMemberShip.getTeamSysNo());

        if (mapList.get(0).get("is_sign") == null
                || Integer.parseInt(String.valueOf(mapList.get(0).get("is_sign"))) == 0) {
            // 未签合同
            if (tm.getProjectCode() == null || tm.getProjectCode().isEmpty() || tm.getOrganizationCode() == null
                    || tm.getOrganizationCode().isEmpty()) {

                throw new XywgException(BizExceptionEnum.TEAM_INFO_ERROR);
            }

            List<ProjectSubContractor> pscList = projectSubContractorMapper.getListByProjectCodeAndOrganizationCode(
                    tm.getProjectCode(), tm.getOrganizationCode());
            if (pscList.size() == 0) {

                throw new XywgException(BizExceptionEnum.JOIN_PROJECT_ERROR);
            }
            List<FileInfo> fileInfoList = fileInfoMapper.getByTableNameAndId("buss_project_sub_contractor", pscList
                    .get(0).getId());
            if (fileInfoList.size() != 0) {
                isSign = 0;
                path = fileInfoList.get(0).getPath();
                fileName = fileInfoList.get(0).getFileName();
                projectCode = tm.getProjectCode();
                organizationCode = tm.getOrganizationCode();
            } else {
                throw new XywgException(BizExceptionEnum.CONTRACT_NOT_UPLOAD);
            }
        } else if (Integer.parseInt(String.valueOf(mapList.get(0).get("is_sign"))) == 1) {
            // 已签合同
            List<ContractFile> cfList = contractFileMapper.getContractFile(tm.getProjectCode(),
                    tm.getOrganizationCode(), teamMemberShip.getIdCardType(), teamMemberShip.getIdCardNumber());
            if (cfList.size() == 0) {
                throw new XywgException(BizExceptionEnum.CONTRACT_SIGN_ERROR);
            }
            isSign = 1;
            path = cfList.get(0).getFilePath();
            fileName = cfList.get(0).getFileName();
            projectCode = tm.getProjectCode();
            organizationCode = tm.getOrganizationCode();
        } else {
            throw new XywgException(BizExceptionEnum.CONTRACT_SIGN_ERROR);
        }
        map.put("isSign", isSign);
        map.put("path", path);
        map.put("projectCode", projectCode);
        map.put("organizationCode", organizationCode);
        map.put("fileName", fileName);
        return map;
    }

    /**
     * @param contractFile
     * @description 保存劳动合同
     * @author chupp
     * @date 2018年6月8日
     * @see com.xywg.admin.modular.team.service.ITeamMasterService#saveLaborContract(com.xywg.admin.modular.worker.model.ContractFile)
     */
    @Override
    @Transactional
    public void saveLaborContract(ContractFileParam contractFile) {
        String teamSysNo = contractFile.getContractCode();
        int contractPeriodType = contractFile.getIsDel();
        // 保存合同附件
        String contractCode = UUID.randomUUID().toString();
        ContractFile cf = new ContractFile();
        cf.setProjectCode(contractFile.getProjectCode());
        cf.setOrganizationCode(contractFile.getOrganizationCode());
        cf.setIdCardNumber(contractFile.getIdCardNumber());
        cf.setIdCardType(contractFile.getIdCardType());
        cf.setContractCode(contractCode);
        cf.setFileName(contractFile.getFileName());
        cf.setFilePath(contractFile.getFilePath());
        cf.setFacePath(contractFile.getFacePath());
        cf.setIsDel(0);
        contractFileMapper.insert(cf);
        // 保存合同信息
        WorkerContractRule wcr = new WorkerContractRule();
        wcr.setProjectCode(contractFile.getProjectCode());
        wcr.setOrganizationCode(contractFile.getOrganizationCode());
        wcr.setIdCardType(contractFile.getIdCardType());
        wcr.setIdCardNumber(contractFile.getIdCardNumber());
        wcr.setContractCode(contractCode);
        wcr.setContractPeriodType(contractPeriodType);
        wcr.setStartDate(contractFile.getStartDate());
        wcr.setEndDate(contractFile.getEndDate());
        workerContractRuleMapper.insert(wcr);

        //在与实名制对接工具表中插一条数据
        IfaLabor ifaLabor = new IfaLabor("buss_worker_contract_rule", wcr.getId());
        ifaLaborMapper.insert(ifaLabor);

        // 更新签署标记（班组工人关系表）
        List<Map<String, Object>> mapList = this.baseMapper.getMemberJoinStatus(Integer.parseInt(teamSysNo),
                Integer.parseInt(contractFile.getIdCardType()), contractFile.getIdCardNumber());
        if (mapList.size() != 1) {

            throw new XywgException(BizExceptionEnum.JOIN_TEAM_ERROR);
        }
        this.baseMapper.updateTeamSign(String.valueOf(mapList.get(0).get("id")));
        // 更新签署标记（项目工人关系表）
        ProjectWorker pw = projectWorkerMapper.getByWorkerAndTeam(Integer.parseInt(teamSysNo),
                contractFile.getIdCardType(), contractFile.getIdCardNumber());
        if (pw == null) {
            throw new XywgException(BizExceptionEnum.JOIN_TEAM_ERROR);
        }
        projectWorkerMapper.updateTeamSign(String.valueOf(pw.getId()));
    }

    @Override
    @Transactional
    public void teamLeaderAddWorker(AppTeamAddWorkerDto addWorkerDto) {
        // 先判断工人在系统中是否存在
        WorkerMaster workerByIdCard = workerMasterService.getWorkerByIdCard(addWorkerDto.getIdCardNumber(),
                dictService.selectNumByName("人员证件类型", "居民身份证"));
        if (workerByIdCard != null) {
            throw new XywgException(BizExceptionEnum.WORKER_EXIST);
        } else {
            if (workerMasterService.getByCellPhone(addWorkerDto.getCellPhone()) != null) {
                throw new XywgException(BizExceptionEnum.PHONE_EXIST);
            } else {
                // user表插数据
                userService.addWorkerAccountPC(addWorkerDto.getCellPhone(), 1, addWorkerDto.getIdCardNumber());
                // worker表插数据
                WorkerMaster workerMaster = new WorkerMaster();
                workerMaster.setWorkerName(addWorkerDto.getName());
                workerMaster.setCellPhone(addWorkerDto.getCellPhone());
                workerMaster.setAddress(addWorkerDto.getAddress());
                workerMaster.setBirthday(addWorkerDto.getBirthday());
                workerMaster.setIdCardType(dictService.selectNumByName("人员证件类型", "居民身份证"));
                workerMaster.setIdCardNumber(addWorkerDto.getIdCardNumber());
                /**
                 * 阿里实名
                 */
                Identity.validByCard(workerMaster.getIdCardNumber(), workerMaster.getWorkerName());
                workerMaster.setIdImage(addWorkerDto.getIdImage());
                workerMaster.setBirthPlaceCode(addWorkerDto.getIdCardNumber().substring(0, 2) + "0000");
                workerMaster.setIsAuth(1);
                workerMaster.setWorkTypeCode(addWorkerDto.getWorkKind().toString());
                workerMaster.setGender(dictService.selectNumByName("性别", addWorkerDto.getGender()));
                workerMaster.setNation(dictService.selectNumByName("民族", addWorkerDto.getNation()).toString());
                workerMapper.insert(workerMaster);
//                boolean flag = workerMasterService.insertLocalFace(workerMaster.getId().toString(), null,
//                        workerMaster.getIdImage(), "", "0");
//                if (!flag) {
//                    throw new XywgException(BizExceptionEnum.AUTH_PERSONNEL_FAILED);
//                }
                // 与班组，项目添加关系
                /**
                 * 获取当前时间 类型为sql格式的date
                 */
                Date time = new Date(new java.util.Date().getTime());
                TeamMaster byteamCode = mapper.getByteamCode(mapper.getById(addWorkerDto.getTeamId()).getTeamSysNo());
                /**
                 * 插入班组关系表
                 */
                TeamMemberShip teamMemberShip = new TeamMemberShip(mapper.getById(addWorkerDto.getTeamId()).getTeamSysNo(), dictService.selectNumByName("人员证件类型", "居民身份证"), addWorkerDto.getIdCardNumber());
                teamMemberShip.setTeamWorkerType(1);
                mapper.addMember(teamMemberShip);
                /**
                 * 插入人脸数据
                 */
                workerMasterService.inputFaceAndProjectCode(workerMaster.getId().toString(),
                        byteamCode.getProjectCode());
                /**
                 * 插入项目关系表
                 */
                ProjectWorker projectWorker = new ProjectWorker(byteamCode.getProjectCode(), byteamCode.getOrganizationCode(), byteamCode.getTeamSysNo(), dictService.selectNumByName("人员证件类型", "居民身份证").toString(), addWorkerDto.getIdCardNumber(), 2, addWorkerDto.getWorkKind().toString(), addWorkerDto.getCellPhone(), 0, 2);
                projectWorker.setEntryTime(time);
                projectWorkerMapper.insert(projectWorker);
                //插入与实名制对接接口表 xss
                ProjectMaster project = projectMasterService.getProjectByProjectCode(byteamCode.getProjectCode());
                if (project.getIsSynchro() == 1) {
                    IfaLabor ifaLabor = new IfaLabor("buss_project_worker", projectWorker.getId());
                    ifaLaborMapper.insert(ifaLabor);
                }

                /**
                 * 插入公司工人关系表
                 */
                if (contractorWorkerMapper.getByIdCardAndOrganization(workerMaster.getIdCardNumber(),
                        dictService.selectNumByName("人员证件类型", "居民身份证"), byteamCode.getOrganizationCode()) == null) {
                    ContractorWorker contractorWorker = new ContractorWorker(dictService.selectNumByName("人员证件类型", "居民身份证"), addWorkerDto.getIdCardNumber(), addWorkerDto.getName(), byteamCode.getOrganizationCode());
                    contractorWorkerService.insert(contractorWorker);
                }
                /**
                 * 生成进场履历
                 */
                EntryExitHistory entryExitHistory = new EntryExitHistory(byteamCode.getProjectCode(), byteamCode.getOrganizationCode(), dictService.selectNumByName("人员证件类型", "居民身份证").toString(), addWorkerDto.getIdCardNumber(), time, 0);
                entryExitHistoryMapper.insert(entryExitHistory);
            }
        }
    }

    @Override
    public List<AppWorkKindVo> getWorkKinds() {
        return mapper.getWorkKinds();
    }

    @Override
    public List<Map<String, Object>> getProjectListByTeamer(AppTeamerDto appTeamerDto) {
        return mapper.getProjectListByTeamer(appTeamerDto);
    }

    @Override
    public List<Map<String, Object>> v116GetProjectListByTeamer(AppTeamerDto appTeamerDto) {
        appTeamerDto.setIndex((appTeamerDto.getPageNo() - 1) * appTeamerDto.getPageSize());
        return mapper.v116GetProjectListByTeamer(appTeamerDto);
    }

    @Override
    public List<Map<String, Object>> v1111GetProjectListByTeamer(AppTeamerDto appTeamerDto) {
        return mapper.v1111GetProjectListByTeamer(appTeamerDto);
    }

    @Override
    public List<UnitConstruct> getUnitContractorList(String projectCode) {
        Integer deptId = ShiroKit.getUser().getDeptId();
        // 获取公司以及子公司社会信用代码集合
        List<String> list = deptService.getOrganizationCodeByDeptId(deptId);
        return mapper.getUnitContractorList(projectCode, list);
    }

    @Override
    public boolean getWorkerInProjectStatus(String projectCode, Integer idCardType, String idCardNumber) {
        boolean flag = false;
        // 查询该工人是否加入该班组所在的项目
        List<Map<String, Object>> memberJoinStatus = mapper.getWorkerInProjectStatus(projectCode, idCardType, idCardNumber);
        if (memberJoinStatus.size() > 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    @Transactional
    public void update(TeamMaster teamMaster) {
        /**
         * 判断班组名是否重名
         */
        if (mapper.checkNameForEdit(teamMaster) != null) {
            throw new XywgException(711, "该项目下存在同名班组");
        } else {
            mapper.updateTeam(teamMaster);
        }
    }


    @Override
    public void addEvaluate(TeamEvaluation teamEvaluation) {
        mapper.addEvaluate(teamEvaluation);
    }

    @Override
    public List<Map<String, Object>> getAllList(Map<String, Object> map) {
        return mapper.getAllList(map);
    }


    @Override
    public List<Map<String, Object>> getTeamEvaluationsByTeam(
            Page<TeamEvaluation> page, Map<String, Object> map,
            String orderByField, boolean asc) {
        Integer teamId = Integer.valueOf(map.get("teamId").toString());
        map.put("teamCode", mapper.getById(teamId).getTeamSysNo());
        return mapper.getTeamEvaluationsByTeam(page, map, orderByField, asc);
    }

    /**
     * @description 获取实名制班组信息（盐城企业版）
     * @author chupp
     * @date 2018年7月18日
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void saveTeamMasterFromSMZCompanyYC(Map<String, String> myc) {
        //获取实名制班组数据
        String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefixYC")
                + getSystemStringParam("saveTeamMasterFromSMZCompany"), new HashMap<String, Object>(), myc);
        Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
        //存在新的班组信息
        if (map.get("group") != null) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("group"));
            List<TeamSmz> teamList = JSONArray.toList(jsonArray, new TeamSmz(), new JsonConfig());
            String id = "0";
            if (teamList.size() > 0) {
                for (TeamSmz ts : teamList) {
                    long smzId = Long.parseLong(ts.getId());
                    TeamMaster tm = new TeamMaster();
                    List<SmzLwt> slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(ts.getComId()), "CompanyYC");
                    if (slList.size() == 0) throw new RuntimeException("实名制接口（盐城企业版）班组企业数据为空" + ts.getComId());
                    SubContractor subContractor = subContractorMapper.selectById(slList.get(0).getLwtId());
                    if (subContractor == null)
                        throw new RuntimeException("实名制接口（盐城企业版）班组企业数据对象为空" + slList.get(0).getLwtId());
                    slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(ts.getProjectId()), "ProjectCompanyYC");
                    if (slList.size() == 0) throw new RuntimeException("实名制接口（盐城企业版）班组接口项目信息为空" + ts.getProjectId());
                    Map<String, Object> chProject = projectMasterMapper.selectById(slList.get(0).getLwtId());
                    if (chProject == null)
                        throw new RuntimeException("实名制接口（盐城企业版）班组接口项目对象为空" + slList.get(0).getLwtId());
                    tm.setProjectCode((String) chProject.get("projectCode"));
                    tm.setOrganizationCode(subContractor.getOrganizationCode());
                    tm.setTeamName(ts.getName());
                    tm.setStatus(1);
                    tm.setIsDel(0);
                    this.baseMapper.insert(tm);
                    tm.setTeamSysNo(tm.getId());
                    this.baseMapper.updateById(tm);
                    long lwtId = tm.getId();
                    SmzLwt sl = new SmzLwt();
                    sl.setSmzId(smzId);
                    sl.setLwtId(lwtId);
                    sl.setTableName("ClassCompanyYC");
                    smzLwtMapper.saveSmzLwt(sl);
                    if (Long.parseLong(id) < smzId) {
                        id = String.valueOf(smzId);
                    }
                }
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("code", "1");
                m.put("no", id);
                //响应保存成功信息
                HttpClientUtils.post(getSystemStringParam("httpUrlPrefixYC")
                        + getSystemStringParam("saveTeamMasterFromSMZCompanyResponse"), m, myc);
            }
        }
    }


    /**
     * @description 保存实名制班组数据（南通）
     * @author chupp
     * @date 2018年7月26日
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void saveTeamMasterFromSMZNT(Map<String, String> mnt, List<SubContractor> list) {
        //获取实名制班组数据
        String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")
                + getSystemStringParam("saveTeamMasterFromSMZ"), new HashMap<String, Object>(), mnt);
        Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
        //存在新的班组信息
        if (map.get("group") != null) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("group"));
            List<TeamSmz> teamList = JSONArray.toList(jsonArray, new TeamSmz(), new JsonConfig());
            SubContractor subContractor = null;
            List<SmzLwt> slList = null;
            String id = "0";
            if (teamList.size() > 0) {
                for (TeamSmz ts : teamList) {
                    if (ts.getComId().equals("0")) {
                        subContractor = list.get(0);
                    } else if (ts.getComId().equals("00")) {
                        subContractor = list.get(1);
                    } else if (ts.getComId().equals("000")) {
                        subContractor = list.get(2);
                    }
                    long smzId = Long.parseLong(ts.getId());
                    TeamMaster tm = new TeamMaster();
                    slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(ts.getProjectId()), "Project");
                    if (slList.size() == 0) throw new RuntimeException("实名制接口班组接口项目信息为空");
                    Map<String, Object> chProject = projectMasterMapper.selectById(slList.get(0).getLwtId());
                    if (chProject == null) throw new RuntimeException("实名制接口班组接口项目对象为空");
                    tm.setProjectCode((String) chProject.get("projectCode"));
                    tm.setOrganizationCode(subContractor.getOrganizationCode());
                    tm.setTeamName(ts.getName());
                    tm.setStatus(1);
                    tm.setIsDel(0);
                    this.baseMapper.insert(tm);
                    tm.setTeamSysNo(tm.getId());
                    this.baseMapper.updateById(tm);
                    long lwtId = tm.getId();
                    SmzLwt sl = new SmzLwt();
                    sl.setSmzId(smzId);
                    sl.setLwtId(lwtId);
                    sl.setTableName("Class");
                    smzLwtMapper.saveSmzLwt(sl);
                    if (Long.parseLong(id) < smzId) {
                        id = String.valueOf(smzId);
                    }
                }
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("code", "1");
                m.put("no", id);
                //响应保存成功信息
                HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")
                        + getSystemStringParam("saveTeamMasterFromSMZResponse"), m, mnt);
            }
        }
    }

    /**
     * @description 获取实名制劳动合同（南通）
     * @author chupp
     * @date 2018年7月26日
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void saveLaborContractFromSMZNT(Map<String, String> mnt, List<SubContractor> list) {
        // 获取实名制劳动合同
        String jsonResult = HttpClientUtils.post(
                getSystemStringParam("httpUrlPrefix") + getSystemStringParam("saveContractFromSMZ"),
                new HashMap<String, Object>(), mnt);
        Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
        // 存在新的劳动合同
        if (map.get("workerContract") != null) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("workerContract"));
            List<ProjectPersonnelSmz> ppsList = JSONArray.toList(jsonArray, new ProjectPersonnelSmz(), new JsonConfig());
            String id = "0";
            SubContractor subContractor = null;
            List<SmzLwt> slList = null;
            if (ppsList.size() > 0) {
                //获取FTP连接
                Random ra = new Random();
                String ran = "" + ra.nextInt(10) + ra.nextInt(10);
                String storeFilePath = "lwtgb_smz" + FileUtil.SEPARATOR
                        + DateUtils.getCurrentDate("yyyy") + FileUtil.SEPARATOR
                        + DateUtils.getCurrentDate("MM") + FileUtil.SEPARATOR
                        + DateUtils.getCurrentDate("dd") + FileUtil.SEPARATOR
                        + DateUtils.getCurrentDate("HH") + FileUtil.SEPARATOR
                        + ran + FileUtil.SEPARATOR;
                FTPClient connectFtp = null;
                try {
                    connectFtp = FTPClientUtil.connectFtp(Constant.FTP_HOST, Constant.FTP_PORT, Constant.FTP_USERNAME, Constant.FTP_PASS_WORD, storeFilePath);
                } catch (SocketException e) {
                    log.error(e.getMessage());
                    throw new RuntimeException("实名制接口培训附件FTP连接错误");
                } catch (IOException e) {
                    log.error(e.getMessage());
                    throw new RuntimeException("实名制接口培训附件FTP连接错误");
                }
                try {
                    for (ProjectPersonnelSmz pps : ppsList) {
                        if (pps.getComId().equals("0")) {
                            subContractor = list.get(0);
                        } else if (pps.getComId().equals("00")) {
                            subContractor = list.get(1);
                        } else if (pps.getComId().equals("000")) {
                            subContractor = list.get(2);
                        }
                        long smzId = Long.parseLong(pps.getId());
                        String contractCode = UUID.randomUUID().toString();
                        String path = "";
                        //保存劳动合同
                        try {
                            if (pps.getWinBinddingFile() != null) {
                                String newName = UUID.randomUUID().toString().replace("-", "") + "." + pps.getHzm();
                                FTPClientUtil.uploadFile(new ByteArrayInputStream(Base64.decode(pps.getWinBinddingFile())),
                                        new String((newName).getBytes("utf-8"), "iso-8859-1"), connectFtp);
                                path = storeFilePath + newName;
                            } else {
                                throw new RuntimeException("实名制接口劳动合同为空");
                            }
                        } catch (IOException e) {
                            log.error(e.getMessage());
                            throw new RuntimeException("实名制接口劳动合同转换错误");
                        }
                        ContractFile cf = new ContractFile();
                        WorkerContractRule wcr = new WorkerContractRule();
                        slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(pps.getProjectId()), "Project");
                        if (slList.size() == 0) throw new RuntimeException("实名制接口合同所属项目为空");
                        Map<String, Object> chProject = projectMasterMapper.selectById(slList.get(0).getLwtId());
                        if (chProject == null) throw new RuntimeException("实名制接口合同所属项目对象为空");
                        List<Map<String, Object>> chProjectWorker = projectWorkerMapper.getForFaceUse((String) chProject.get("projectCode"), 1, pps.getIdcardNumber());
                        if (chProjectWorker.size() == 0) throw new RuntimeException("实名制接口考勤记录人员项目关系为空");
                        if (chProjectWorker.get(0).get("team_sys_no") == null || chProjectWorker.get(0).get("team_sys_no").equals(""))
                            throw new RuntimeException("实名制接口合同接口班组编号为空");
                        cf.setProjectCode((String) chProject.get("projectCode"));
                        cf.setOrganizationCode(subContractor.getOrganizationCode());
                        cf.setContractCode(contractCode);
                        cf.setIdCardType("1");
                        cf.setIdCardNumber(pps.getIdcardNumber());
                        cf.setFileName("劳动合同");
                        cf.setFilePath(path);
                        cf.setFacePath("smz");
                        cf.setIsDel(0);
                        contractFileMapper.insert(cf);
                        wcr.setProjectCode((String) chProject.get("projectCode"));
                        wcr.setOrganizationCode(subContractor.getOrganizationCode());
                        wcr.setIdCardType("1");
                        wcr.setIdCardNumber(pps.getIdcardNumber());
                        wcr.setContractCode(contractCode);
                        if (pps.getContractPeriodtype() != null)
                            wcr.setContractPeriodType(Integer.parseInt(pps.getContractPeriodtype()));
                        wcr.setStartDate(pps.getStartDate());
                        wcr.setEndDate(pps.getEndDate());
                        wcr.setIsDel(0);
                        workerContractRuleMapper.insert(wcr);
                        // 更新签署标记（班组工人关系表）
                        List<Map<String, Object>> mapList = this.baseMapper.getMemberJoinStatus((int) chProjectWorker.get(0).get("team_sys_no"),
                                1, pps.getIdcardNumber());
                        if (mapList.size() != 1) {
                            throw new XywgException(BizExceptionEnum.JOIN_TEAM_ERROR);
                        }
                        this.baseMapper.updateTeamSign(String.valueOf(mapList.get(0).get("id")));
                        // 更新签署标记（项目工人关系表）
                        ProjectWorker pw = projectWorkerMapper.getByWorkerAndTeam((int) chProjectWorker.get(0).get("team_sys_no"),
                                "1", pps.getIdcardNumber());
                        if (pw == null) {
                            throw new XywgException(BizExceptionEnum.JOIN_TEAM_ERROR);
                        }
                        projectWorkerMapper.updateTeamSign(String.valueOf(pw.getId()));
                        if (Long.parseLong(id) < smzId) {
                            id = String.valueOf(smzId);
                        }
                    }
                } finally {
                    if (connectFtp != null && connectFtp.isConnected()) {
                        try {
                            connectFtp.logout();
                        } catch (IOException e) {
                            log.error(e.getMessage());
                            connectFtp = null;
                        }
                    }
                }
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("code", "1");
                m.put("no", id);
                // 响应保存成功信息
                HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")
                        + getSystemStringParam("saveContractFromSMZResponse"), m, mnt);
            }
        }
    }
    
    
  

    @Override
    public List<Map<String, Object>> getTeamMemberByProjectCodeAndTeamCode(Map<String, Object> map) {
        return this.baseMapper.getTeamMemberByProjectCodeAndTeamCode(map);
    }

    @Override
    public List<Map<String, Object>> getTeamMemberByProjectCodeAndTeamCodePages(Page<Map<String, Object>> page, Map<String, Object> map) {
        return this.baseMapper.getTeamMemberByProjectCodeAndTeamCodePages(page, map);
    }

    @Override
    public List<Map<String, Object>> getWorkerListByTeam(Map<String, Object> map, Page page) {
        return baseMapper.getWorkerListByTeam(map, page);
    }

    @Override
    public List<AppTeamInfoVo> v116GetAppTeamsInfo(String organizationCode, String projectCode, Integer pageNo, Integer pageSize) {

        List<AppTeamInfoVo> appTeamsByProject = null;
        int contractorType = 0;
        List<ProjectSubContractor> list = projectSubContractorMapper.getListByProjectCodeAndOrganizationCode(projectCode, organizationCode);
        if (null != list && list.size() != 0) {
            contractorType = list.get(0).getContractorType();
        }
        if (contractorType != 16) {
            appTeamsByProject = mapper.v116GetAppTeamsInfo(projectCode, organizationCode, (pageNo - 1) * pageSize, pageSize);
        } else {
            appTeamsByProject = mapper.v116GetAppTeamsInfo(projectCode, null, (pageNo - 1) * pageSize, pageSize);
        }

        for (AppTeamInfoVo appTeamInfoVo : appTeamsByProject) {
            appTeamInfoVo.setCount(mapper.getMemberCountByTeamCode(mapper.getById(appTeamInfoVo.getTeamId())
                    .getTeamSysNo()));
        }
        return appTeamsByProject;
    }

    @Override
    public List<AppTeamMemberVo> v116getByTeamId(Integer id, Integer joinStatus, Integer pageNo, Integer pageSize) {
        return mapper.v116GetAppMemberByTeamCode(mapper.getById(id).getTeamSysNo(), joinStatus, (pageNo - 1) * pageSize, pageSize);
    }
    
    @Override
    public Integer setTeamLeaderByTeamSysNo(Integer teamSysNo, String idCardNumber, String workerName) {
        return this.baseMapper.setTeamLeaderByTeamSysNo(teamSysNo,idCardNumber,workerName);
    }

    @Override
    public Integer removeTeamLeader(Integer teamSysNo) {
        return this.baseMapper.removeTeamLeader(teamSysNo);
    }

    @Override
    public void updateByOldTeamSysNo(int oldTeamSysNo, TeamMemberShip member) {
      this.baseMapper.updateByOldTeamSysNo(oldTeamSysNo, member);
    }
	
	@SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void saveTeamMasterFromSMZTY(Map<String, String> mnt) {
		List<String> registerNo = new ArrayList<String>();
		registerNo = this.projectMasterMapper.getRegisterNo();
		for(int j=0;j<registerNo.size();j++){
			//获取实名制班组数据
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			//查找已经存在的最大值
			Long maxId = this.smzLwtMapper.getProjectMaxId(registerNo.get(j));
			if(maxId == null){
				maxId = Long.valueOf(0);
			}
			List<SmzCallBack> scbList = new ArrayList<SmzCallBack>();
	    	//获取实名制项目数据
			List<String> projectId = this.projectMasterMapper.getProjectId(maxId,registerNo.get(j));
			//单独从项目拉取只给实名制项目id的时候放开 上面不用管
//			List<String> projectId = new ArrayList<String>();
//			projectId.add("2910");
			for(int n=0;n<projectId.size();n++){
				SmzCallBack scb = new SmzCallBack();
				hashMap.put("projectIdNew", projectId.get(n));
				String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")
		                + getSystemStringParam("saveTeamMasterFromSMZTY"),hashMap, mnt);
		        Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
		        //存在新的班组信息
		        if (map.get("group") != null) {
		        	JSONArray jsonArray = JSONArray.fromObject(map.get("group"));
		            List<TeamSmz> teamList = JSONArray.toList(jsonArray, new TeamSmz(), new JsonConfig());
		            String subContractor = null;
		            List<SmzLwt> slList = null;
		            String id = "0";
		            if (teamList.size() > 0) {
		                for (TeamSmz ts : teamList) {
		                    
		                    long smzId = Long.parseLong(ts.getId());
		                    TeamMaster tm = new TeamMaster();
		                    slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(ts.getProjectId()), "Project");
		                    if (slList.size() == 0) throw new RuntimeException("实名制接口班组接口项目信息为空");
		                    Map<String, Object> chProject = projectMasterMapper.selectById(slList.get(0).getLwtId());
		                    if (chProject == null) throw new RuntimeException("实名制接口班组接口项目对象为空");
		                    tm.setProjectCode((String) chProject.get("projectCode"));
		                    //根据项目id查找社会统一信用代码
		                    subContractor = baseMapper.queryOrgnationCode(slList.get(0).getLwtId());
		                    tm.setOrganizationCode(subContractor);
		                    tm.setTeamName(ts.getName());
		                    if(ts.getTeamLeader() != null && !"".equals(ts.getTeamLeader())){
		                    	tm.setTeamLeader(ts.getTeamLeader());
		                    }
		                    if(ts.getTeamIdNumber() != null && !"".equals(ts.getTeamIdNumber())){
		                    	tm.setTeamLeaderIdNumber(ts.getTeamIdNumber());
		                    }
		                    if(ts.getResponseIdNumber() != null && !"".equals(ts.getResponseIdNumber())){
		                    	tm.setResponsiblePersonIdNumber(ts.getResponseIdNumber());
		                    }
		                    tm.setStatus(1);
		                    tm.setIsDel(0);
		                    this.baseMapper.insert(tm);
		                    tm.setTeamSysNo(tm.getId());
		                    this.baseMapper.updateById(tm);
		                    long lwtId = tm.getId();
		                    SmzLwt sl = new SmzLwt();
		                    sl.setSmzId(smzId);
		                    sl.setLwtId(lwtId);
		                    sl.setTableName("Class");
		                    smzLwtMapper.saveSmzLwt(sl);
		                    if (Long.parseLong(id) < smzId) {
		                        id = String.valueOf(smzId);
		                    }
		                }
		            }
		            scb.setMaxId(id);
		            scb.setProjectId(projectId.get(n));
		            scbList.add(scb);
			}
	        
	        }
			Map<String, Object> m = new HashMap<String, Object>();
			String json = JSONArray.fromObject(scbList).toString();
            m.put("code", "1");
            m.put("scbList", json); 
            //响应保存成功信息
            HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")
                    + getSystemStringParam("saveTeamMasterFromSMZResponseTY"), m, mnt);
		}
    }

    public boolean insertObj(TeamMaster teamMaster){
        return this.baseMapper.insertObj(teamMaster);
    }
	
    @Override
    public void validateProJectAndWorker(String oldProjectCode, String projectCode, int oldTeamSysNo, TeamMemberShip member) {
       boolean flag = false;
       if(!StringUtils.isEmpty(projectCode)&&!StringUtils.isEmpty(oldProjectCode)&&member!=null&&!projectCode.equals(oldProjectCode)) {
    	   flag = getWorkerInProjectStatus(projectCode, member.getIdCardType(), member.getIdCardNumber());
       }
       if(flag) {
    	   throw new XywgException(711, "项目中已存在相关人员！");
       }
    }


    @Override
    public void addTeamMasterList(List<Object> addList) {
        for (Object o : addList) {
            TeamMaster teamMaster = new TeamMaster();
            stringToDateException();
            try {
                //拷贝为projectMaster对象
                BeanUtils.copyProperties(teamMaster, o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            Map<String,Long> map=  baseMapper.selectIdByTeamSysNo(teamMaster.getTeamSysNo());
            if (map.get("num")==0){
                //teamMaster.setId( Integer.valueOf(map.get("id").toString()));
                teamMaster.setId(null);
                if (mapper.getByName(teamMaster.getTeamName(), teamMaster.getProjectCode()) != null) {
                    throw new XywgException(711, "该项目下存在同名班组");
                }else {
                    baseMapper.insert(teamMaster);
                }

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
    public List<TeamMaster> queryTeamMasterById(String organizationCode, Long id ,String projectCode) {
        return this.baseMapper.queryTeamMasterById(organizationCode,id,projectCode);
    }

    @Override
    public List<Map<String, Object>> getTeamMasterByProjectCodeAndOrgCode(Map<String, Object> map, Page<TeamMaster> page) {
        String deptId = ShiroKit.getSessionAttr("toggleDeptId").toString();
        if (!"1".equals(deptId)) {
            String orgCode = deptMapper.selectOrgCodeByDeptId(deptId);
            map.put("orgCode",orgCode);
        }
       return baseMapper.getTeamMasterByProjectCodeAndOrgCode(map,page);

    }
}
