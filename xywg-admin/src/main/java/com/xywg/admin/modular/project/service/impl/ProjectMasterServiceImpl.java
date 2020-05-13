package com.xywg.admin.modular.project.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.config.handler.RemoteDTO;
//import com.xywg.admin.config.handler.ZbusHandler;
import com.xywg.admin.core.base.tips.ErrorTip;
import com.xywg.admin.core.base.tips.SuccessTip;
import com.xywg.admin.core.base.tips.Tip;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;
import com.xywg.admin.core.common.exception.BizExceptionEnum;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.support.BeanKit;
import com.xywg.admin.modular.company.dao.SubContractorMapper;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.device.dao.DeviceMapper;
import com.xywg.admin.modular.device.model.Device;
import com.xywg.admin.modular.device.service.IDeviceService;
import com.xywg.admin.modular.mina.Utils;
import com.xywg.admin.modular.project.dao.ProjectMasterMapper;
import com.xywg.admin.modular.project.model.AppProjectListByPersonVo;
import com.xywg.admin.modular.project.model.AppProjectMasterDto;
import com.xywg.admin.modular.project.model.ProjectAddress;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.model.vo.ProjectMasterVo;
import com.xywg.admin.modular.project.service.IProjectAddressService;
import com.xywg.admin.modular.project.service.IProjectMasterService;
import com.xywg.admin.modular.projectSubContractor.dao.ProjectSubContractorMapper;
import com.xywg.admin.modular.projectSubContractor.model.ProjectSubContractor;
import com.xywg.admin.modular.report.dao.DeviceReportMapper;
import com.xywg.admin.modular.smz.dao.IfaLaborMapper;
import com.xywg.admin.modular.smz.dao.SmzLwtMapper;
import com.xywg.admin.modular.smz.model.IfaLabor;
import com.xywg.admin.modular.smz.model.ProjectMo;
import com.xywg.admin.modular.smz.model.ProjectSmz;
import com.xywg.admin.modular.smz.model.SmzCorProject;
import com.xywg.admin.modular.smz.model.SmzLwt;
import com.xywg.admin.modular.smz.service.IfaLaborService;
import com.xywg.admin.modular.smz.utils.HttpClientUtils;
import com.xywg.admin.modular.system.dao.DeptMapper;
import com.xywg.admin.modular.system.dao.FileInfoMapper;
import com.xywg.admin.modular.system.model.FileInfo;
import com.xywg.admin.modular.system.service.*;
import com.xywg.admin.modular.zr.model.ZrCompanyTpm;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 * 项目基础信息 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-23
 */
@Service
public class ProjectMasterServiceImpl extends ServiceImpl<ProjectMasterMapper, ProjectMaster> implements IProjectMasterService {
    @Resource
    private ProjectMasterMapper projectMasterMapper;
    @Resource
    private ProjectSubContractorMapper projectSubContractorMapper;

    @Resource
    private IfaLaborService ifaLaborService;
    @Autowired
    private UploadService uploadService;

    @Resource
    private DeptMapper deptMapper;

    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Autowired
    private IUserService userService;

    @Autowired
    private SubContractorMapper subContractorMapper;

    @Autowired
    private AccountProjectService accountProjectService;

    @Autowired
    private ITimeSetService timeSetService;

    @Autowired
    private IProjectAddressService projectAddressService;

    @Autowired
    private IDictService dictService;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private SmzLwtMapper smzLwtMapper;
    @Resource
    private DeviceReportMapper deviceReportMapper;
    private static final Log LOG = LogFactory.getLog(ProjectMasterServiceImpl.class);
    private static Properties systemParams = new Properties();
    private static final String VIRTUAL_DEVICE_PRE = "81263150808";

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

    @Override
    public void updateSynchro(long id, int synchro) {
        this.baseMapper.updateSynchro(id, synchro);
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

    @Override
    public boolean getProjectFromLabor(List<Long> ids, Map<String, String> m) {
        List<ProjectMo> list = projectMasterMapper.getProjectFromLabor(ids);
        if (list.size() > 0) {
            for (ProjectMo p : list) {
                String projectActivityType = p.getProjectActivityType();
                int type = p.getType();
                //项目活动类型
                if (projectActivityType != "99") {
                    p.setProjectActivityType("0" + projectActivityType);
                }
                if (type == 99) {
                    p.setType(5);
                }
                if(p.getComId()==467L){
                    p.setComId(100L);
                }
                //施工状态
                String status = p.getProjectProgess();
                if (status != null) {
                    p.setProjectProgess("00" + status);
                }
            if ("0".equals(p.getIsRealcode())){

                p.setContractorOrgcode("91320684138774279B");
                p.setGeneralContractorCode("91320684138774279B");

            }

            }
            Map<String, Object> map = new HashMap<>();
            String json = JSONArray.fromObject(list).toString();
            map.put("lablorProject", json);
            String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefix") + getSystemStringParam("saveProjectFromLabor"), map, m);
            System.out.println(jsonResult);
            System.out.println(jsonResult+"--------------------------------------------");
            Map<String, String> result = JSONUtil.toBean(jsonResult, Map.class);
            if ("-2".equals(result.get("code"))) {
                return false;
            } else {
                return true;
            }
        }
        return false;

    }


    /**
     * @description 获取实名制项目数据（盐城）
     * @author chupp
     * @date 2018年7月3日
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void saveProjectFromSMZYC(Map<String, String> myc) {
        //获取实名制项目数据
        String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefixYC")
                + getSystemStringParam("saveProjectFromSMZYC"), new HashMap<String, Object>(), myc);
        Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
        //存在新的项目数据
        if (map.get("project") != null) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("project"));
            List<ProjectSmz> projectList = JSONArray.toList(jsonArray, new ProjectSmz(), new JsonConfig());
            String id = "0";
            List<SmzLwt> slList = smzLwtMapper.getLwtIdBySmzId(0, "Company");
            if (slList.size() == 0) throw new RuntimeException("实名制接口企业数据为空");
            SubContractor subContractor = subContractorMapper.selectById(slList.get(0).getLwtId());
            if (subContractor == null) throw new RuntimeException("实名制接口企业数据对象为空");
            if (projectList.size() > 0) {
                for (ProjectSmz ps : projectList) {
                    long smzId = Long.parseLong(ps.getId());
                    //保存实名制项目数据
                    ProjectMaster pm = new ProjectMaster();
                    pm.setProjectCode("PROJECT" + System.currentTimeMillis());
                    pm.setBuildProjectCode(pm.getProjectCode());
                    pm.setContractorOrgCode(subContractor.getOrganizationCode());
                    pm.setGeneralContractorCode(subContractor.getOrganizationCode());
                    pm.setGeneralContractorName(subContractor.getCompanyName());
                    pm.setProjectName(ps.getName());
                    if (ps.getProjectActivityType() != null) {
                        pm.setProjectActivityType("" + Integer.parseInt(ps.getProjectActivityType()));
                    } else {
                        pm.setProjectActivityType("99");
                    }
                    pm.setProjectDescription(ps.getDescription());
                    if (ps.getProjectTypeNew() != null) {
                        pm.setProjectCategory("" + Integer.parseInt(ps.getProjectTypeNew()));
                    } else {
                        pm.setProjectCategory("99");
                    }
                    pm.setIsMajorProject(1);
                    if (ps.getConstructorName() != null) {
                        pm.setOwnerName(ps.getConstructorName());
                    } else {
                        pm.setOwnerName("smz");
                    }
                    if (ps.getContractorOrgcode() != null) {
                        pm.setBuildCorporationCode(ps.getContractorOrgcode());
                    } else {
                        pm.setBuildCorporationCode("smz");
                    }
                    if (ps.getCompanyCode() != null) {
                        pm.setBuilderLicenceNum(ps.getCompanyCode());
                    } else {
                        pm.setBuilderLicenceNum("smz");
                    }
                    pm.setAreaCode("320600");
                    if (ps.getPlace() != null) {
                        pm.setAddress(ps.getPlace());
                    } else {
                        pm.setAddress("smz");
                    }
                    if (ps.getBuildingCost() != null) {
                        pm.setTotalContractAmt(new BigDecimal(ps.getBuildingCost()));
                    } else {
                        pm.setTotalContractAmt(new BigDecimal(0));
                    }
                    if (ps.getBuildingSize() != null) {
                        pm.setBuildingArea(new BigDecimal(ps.getBuildingSize()));
                    } else {
                        pm.setBuildingArea(new BigDecimal(0));
                    }
                    if (ps.getBegin() != null) {
                        pm.setStartDate(ps.getBegin().substring(0, 10));
                    } else {
                        Date currentTime = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String dateString = formatter.format(currentTime);
                        pm.setStartDate(dateString);
                    }
                    if (ps.getEnd() != null) {
                        pm.setCompleteDate(ps.getEnd().substring(0, 10));
                    } else {
                        Date currentTime = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String dateString = formatter.format(currentTime);
                        pm.setCompleteDate(dateString);
                    }
                    if (ps.getProjectProgressNew() != null) {
                        pm.setProjectStatus("" + Integer.parseInt(ps.getProjectProgressNew()));
                    } else {
                        pm.setProjectStatus("1");
                    }
                    if (ps.getLng() != null) {
                        pm.setLng(new BigDecimal(ps.getLng()));
                    } else {
                        pm.setLng(new BigDecimal(0));
                    }
                    if (ps.getLat() != null) {
                        pm.setLat(new BigDecimal(ps.getLat()));
                    } else {
                        pm.setLat(new BigDecimal(0));
                    }
                    pm.setRadius(2000.00);
                    pm.setDeviceType(4);
                    pm.setStatus(1);
                    pm.setIsDel(0);
                    this.baseMapper.insert(pm);
                    //保存项目公司关系
                    ProjectSubContractor projectSubContractor = new ProjectSubContractor();
                    projectSubContractor.setProjectCode(pm.getProjectCode());
                    projectSubContractor.setOrganizationCode(pm.getContractorOrgCode());
                    projectSubContractor.setContractorType(16);
                    projectSubContractorMapper.insert(projectSubContractor);
                    //保存主键关联表
                    long lwtId = pm.getId();
                    SmzLwt sl = new SmzLwt();
                    sl.setSmzId(smzId);
                    sl.setLwtId(lwtId);
                    sl.setTableName("ProjectYC");
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
                        + getSystemStringParam("saveProjectFromSMZResponseYC"), m, myc);
            }
        }
    }

    @Override
    public List<Map<String, Object>> selectList(Map<String, Object> map, Page page) {
       /* String account = ShiroKit.getUser().getAccount();*/
        //是否是企业级 0：否 1：是
 /*    Integer result =  baseMapper.selectIsEnterprise(account);
     if (1==result){*/
        map.put("projectCodes", accountProjectService.getProjectCodes());
   /*  }else if (0==result){
         List<String> projectCodes=  accountProjectService.getProCode(account);
         map.put("projectCodes",projectCodes);
     }*/
      List<Map<String, Object>> list = projectMasterMapper.list(map, page);
      for (Map<String, Object> projectMasterMap : list) {
          projectMasterMap.put("projectStatusName", ConstantFactory.me().getDictsByName("项目状态",
                  projectMasterMap.get("projectStatus") == null ? -1 : Integer.parseInt(projectMasterMap.get("projectStatus").toString())));
      }
      return list;
    }

    @Override
    public List<Map<String, Object>> getByProjectName(String projectName) {
        return projectMasterMapper.getByProjectName(projectName);
    }
    /*@Override
    @Transactional(readOnly = false)
    public boolean insert(ProjectMaster projectMaster) {
        //根据项目名称判重
        ProjectMaster hasProjectMaster = baseMapper.selectProjectMasterByName(projectMaster);
        if (hasProjectMaster != null) {
            throw new XywgException(800, "该项目名称已存在，请使用其他名称!");
        }
        if (projectMaster.getIsSynchro() == 1) {
            //传送考勤机给SMZ
            //查询字典表里考勤机的num
            Integer deviceNum = dictService.selectNumByName("考勤类型", "考勤机");
            if (deviceNum == null) {
                throw new XywgException(300, "字典表中缺少考勤类型—考勤机");
            }
            if (!deviceNum.equals(projectMaster.getDeviceType())) {
                //如果考勤类型不为考勤机，添加虚拟考勤机
                //1.查询已有的虚拟考勤机最大sn
                String maxSn = this.baseMapper.selectMaxVirtualDeviceSn();
                //2.生成下一条sn
                String newSn = "";
                if (maxSn == null) {
                    newSn = VIRTUAL_DEVICE_PRE + "000001";
                } else {
                    newSn = this.getNewDviceSn(maxSn);
                }
                //3.生成虚拟考勤机
                Device device = new Device(newSn, projectMaster.getContractorOrgCode(), projectMaster.getProjectCode());
                deviceService.insert(device);
                projectMaster.setVirtualId(device.getId());
            }
        }
        Date date = new Date();
        projectMaster.setCreateDate(date);
        projectMaster.setCreateUser(ShiroKit.getUser().getName());
        projectMaster.setUpdateDate(date);
        projectMaster.setUpdateUser(ShiroKit.getUser().getName());
        retBool(this.baseMapper.insert(projectMaster));
        //新增项目工地地址
*//*        List<Map<String, Object>> projectAddress = projectMaster.getProjectAddressList();
        if (projectAddress != null) {
            for (Map<String, Object> map : projectAddress) {
                map.put("projectCode", projectMaster.getProjectCode());
                ProjectAddress savedProjectAddress = BeanKit.mapToBean(map, ProjectAddress.class);
                savedProjectAddress.setRadius(Double.valueOf(map.get("radius").toString()));
                projectAddressService.insert(savedProjectAddress);
            }
        }*//*
        if (projectMaster.getIsSynchro() == 1) {
            IfaLabor ifaLabor = new IfaLabor("buss_project_master", projectMaster.getId());
            ifaLaborService.insert(ifaLabor);
        }
        ProjectSubContractor projectSubContractor = new ProjectSubContractor();
        projectSubContractor.setProjectCode(projectMaster.getProjectCode());
        projectSubContractor.setOrganizationCode(projectMaster.getContractorOrgCode());
        projectSubContractor.setContractorType(16);
        projectSubContractorMapper.insert(projectSubContractor);

        return  false;
    }*/

    @Override
    @Transactional(readOnly = false)
    public boolean insert(ProjectMasterVo projectMaster) {
        //根据项目名称判重
        ProjectMaster hasProjectMaster = baseMapper.selectProjectMasterByName(projectMaster);
        if (hasProjectMaster != null) {
            throw new XywgException(800, "该项目名称已存在，请使用其他名称!");
        }
        if (projectMaster.getIsSynchro() == 1) {
            //传送考勤机给SMZ
            //查询字典表里考勤机的num
            Integer deviceNum = dictService.selectNumByName("考勤类型", "考勤机");
            if (deviceNum == null) {
                throw new XywgException(300, "字典表中缺少考勤类型—考勤机");
            }
            if (!deviceNum.equals(projectMaster.getDeviceType())) {
                //如果考勤类型不为考勤机，添加虚拟考勤机
                //1.查询已有的虚拟考勤机最大sn
                String maxSn = this.baseMapper.selectMaxVirtualDeviceSn();
                //2.生成下一条sn
                String newSn = "";
                if (maxSn == null) {
                    newSn = VIRTUAL_DEVICE_PRE + "000001";
                } else {
                    newSn = this.getNewDviceSn(maxSn);
                }
                //3.生成虚拟考勤机
                Device device = new Device(newSn, projectMaster.getContractorOrgCode(), projectMaster.getProjectCode());
                deviceService.insert(device);
                projectMaster.setVirtualId(device.getId());
            }
        }
        Date date = new Date();
        projectMaster.setCreateDate(date);
        projectMaster.setCreateUser(ShiroKit.getUser().getName());
        projectMaster.setUpdateDate(date);
        projectMaster.setUpdateUser(ShiroKit.getUser().getName());
        retBool(this.baseMapper.insert(projectMaster));
        //新增项目工地地址
        List<Map<String, Object>> projectAddress = projectMaster.getProjectAddressList();
        if (projectAddress != null) {
            for (Map<String, Object> map : projectAddress) {
                map.put("projectCode", projectMaster.getProjectCode());
                ProjectAddress savedProjectAddress = BeanKit.mapToBean(map, ProjectAddress.class);
                savedProjectAddress.setRadius(Double.valueOf(map.get("radius").toString()));
                projectAddressService.insert(savedProjectAddress);
            }
        }
        //添加到同步实名制的中间表中
        if (projectMaster.getIsSynchro() == 1) {
            IfaLabor ifaLabor = new IfaLabor("buss_project_master", projectMaster.getId());
            ifaLaborService.insert(ifaLabor);
        }
        ProjectSubContractor projectSubContractor = new ProjectSubContractor();
        projectSubContractor.setProjectCode(projectMaster.getProjectCode());
        projectSubContractor.setOrganizationCode(projectMaster.getContractorOrgCode());
        projectSubContractor.setContractorType(16);
        projectSubContractorMapper.insert(projectSubContractor);
        //调用时间设置接口 插入项目的打卡区间
        timeSetService.addTimeSet(projectMaster.getProjectCode(), ShiroKit.getUser().getName());
        //新增完成调用zbus推送给智慧工地
//        RemoteDTO<Map<String, Object>> dto = new RemoteDTO();
//        Map<String, Object> mm = this.baseMapper.selectByIdForZH(projectMaster.getId());
//        Map<String, Object> project = new HashMap<>();
//        project.put("operationFlag", "insertExternalProject");
//        //构建org
//        Map<String, Object> map = new HashMap<>();
//        SubContractor contractor = subContractorMapper.hasSubContractorByOrganizationCode(mm.get("orgCode").toString());
//        map.put("name", contractor.getCompanyName());
//        map.put("instroduction", "");
//        map.put("platform", 2);
//        map.put("relationOrgId", contractor.getId());
//        project.put("org", map);
//        //projectInfo
//        mm.remove("orgCode");
//        project.put("projectInfo", mm);
//        dto.setBody(project);
    /*    int state = ZbusHandler.send(JSONObject.fromObject(dto).toString());
        if (state == 200) {
            //修改projectMaster的is_synchro  1已经同步给智慧工地
            this.baseMapper.updateSynchro(projectMaster.getId(), 1);
        }*/
        return false;
    }

    /**
     * 生成最新的Sn
     */
    public String getNewDviceSn(String rawSn) {
        return this.generatorSn(rawSn);
    }

    ;

    public String generatorSn(String maxSn) {
        String newNumber = new DecimalFormat("000000").format(new BigDecimal(maxSn.replace(VIRTUAL_DEVICE_PRE, "")).add(new BigDecimal(1)));
        String newMaxSn = VIRTUAL_DEVICE_PRE + newNumber;
        Device device = deviceMapper.selectDeviceBySn(new Device(newMaxSn));
        if (device == null) {
            return newMaxSn;
        } else {
            return this.generatorSn(newMaxSn);
        }
    }

    /**
     * 根据项目编号获取项目
     *
     * @param projectCode
     * @return
     */
    @Override
    public ProjectMaster getProjectByProjectCode(String projectCode) {
        return projectMasterMapper.getProjectByProjectCode(projectCode);
    }

    @Override
    public Integer toggleProjectStatus(String ids, String projectStatus) {
        List<Long> idList = new ArrayList<Long>();
        String[] idArray = ids.split(",");
        for (int i = 0; i < idArray.length; i++) {
            idList.add(Long.valueOf(idArray[i]));
        }
        return projectMasterMapper.toggleProjectStatus(idList, projectStatus);
    }

    @Override
    public List<AppProjectMasterDto> getFollowProjectList(Integer id, String organizationCode) {
        return projectMasterMapper.getFollowProjectList(id, organizationCode);
    }

    @Override
    public List<ProjectMaster> getList(Map<String, Object> map) {
        map.put("deptId", ShiroKit.getUser().getDeptId());
        return projectMasterMapper.getList(map);
    }

    /**
     * @param files
     * @param projectCode
     * @param organizationCode
     * @description 上传劳动合同
     * @author chupp
     * @date 2018年6月7日
     * @see com.xywg.admin.modular.project.service.IProjectMasterService#(org.springframework.web.multipart.MultipartFile, java.lang.String)
     */
    @Override
    @Transactional
    public Tip uploadLaborContract(MultipartFile[] files, String projectCode, String organizationCode) {
        for (MultipartFile contractFile : files) {
            if (projectCode != null && !projectCode.isEmpty() && contractFile != null
                    && contractFile.getOriginalFilename() != null && !contractFile.getOriginalFilename().isEmpty()) {
                String fileName = contractFile.getOriginalFilename();
                //文件格式校验
                if (!fileName.substring(fileName.lastIndexOf(".")).equalsIgnoreCase(".pdf")) {
                    return new ErrorTip(BizExceptionEnum.FILE_FORMAT_ERROR);
                }
                //获取relevanceId
                List<ProjectSubContractor> pscList = projectSubContractorMapper.getListByProjectCodeAndOrganizationCode(projectCode, organizationCode);
                if (pscList.size() == 0) {
                    return new ErrorTip(BizExceptionEnum.DATA_ERROR);
                }
                ProjectSubContractor psc = pscList.get(0);
                psc.setHasContract("1");
                projectSubContractorMapper.updateById(psc);
                Long relevanceId = psc.getId();
                //上传文件
                String path = uploadService.saveFileToFTP(contractFile);

                FileInfo fi = new FileInfo();
                fi.setRelevanceId(relevanceId);
                fi.setPath(path);
                fi.setFileName(fileName);
                fi.setType("buss_project_sub_contractor");
                fileInfoMapper.insert(fi);

            } else {
                return new ErrorTip(BizExceptionEnum.UPLOAD_ERROR);
            }
        }
        return new SuccessTip();
    }

    @Override
    public List<AppProjectListByPersonVo> getProjectsByUserId(Long userId) {
        return projectMasterMapper.getProjectsByUserId(userService.selectById(userId).getDeptid());
    }

    @Override
    public List<AppProjectListByPersonVo> v116GetProjectsByUserId(Long userId, Integer pageNo, Integer pageSize) {
        return projectMasterMapper.v116GetProjectsByUserId(userService.selectById(userId).getDeptid(), (pageNo - 1) * pageSize, pageSize);
    }

    @Override
    public Map<String, Object> selectById(Long id) {
        return projectMasterMapper.selectById(id);
    }

    @Override
    public List<AppProjectListByPersonVo> getOndoingProjectsByUserId(Long userId) {
        List<AppProjectListByPersonVo> ondoingProjectsByUserId = projectMasterMapper.getOndoingProjectsByUserId(userService.selectById(userId).getDeptid());
        for (AppProjectListByPersonVo appProjectListByPersonVo : ondoingProjectsByUserId) {
            appProjectListByPersonVo.setPositionList(projectMasterMapper.getProjectPositionList(appProjectListByPersonVo.getProjectCode()));
        }
        return ondoingProjectsByUserId;
    }

    @Override
    public List<AppProjectListByPersonVo> v116GetOndoingProjectsByUserId(Long userId, Integer pageNo, Integer pageSize) {
        List<AppProjectListByPersonVo> ondoingProjectsByUserId = projectMasterMapper.v116GetOndoingProjectsByUserId(userService.selectById(userId).getDeptid(), (pageNo - 1) * pageSize, pageSize);
        for (AppProjectListByPersonVo appProjectListByPersonVo : ondoingProjectsByUserId) {
            appProjectListByPersonVo.setPositionList(projectMasterMapper.getProjectPositionList(appProjectListByPersonVo.getProjectCode()));
        }
        return ondoingProjectsByUserId;
    }

    /**
     * @description 获取实名制项目数据（盐城企业版）
     * @author chupp
     * @date 2018年7月17日
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void saveProjectFromSMZCompanyYC(Map<String, String> myc) {
        //获取实名制项目数据
        String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefixYC")
                + getSystemStringParam("saveProjectFromSMZCompany"), new HashMap<String, Object>(), myc);
        Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
        //存在新的项目数据
        if (map.get("project") != null) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("project"));
            List<ProjectSmz> projectList = JSONArray.toList(jsonArray, new ProjectSmz(), new JsonConfig());
            String id = "0";
            if (projectList.size() > 0) {
                for (ProjectSmz ps : projectList) {
                    long smzId = Long.parseLong(ps.getId());
                    //保存实名制项目数据
                    ProjectMaster pm = new ProjectMaster();
                    pm.setProjectCode("PROJECT" + System.currentTimeMillis());
                    pm.setBuildProjectCode(pm.getProjectCode());
                    List<SmzLwt> slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(ps.getComId()), "CompanyYC");
                    if (slList.size() == 0) throw new RuntimeException("实名制接口（盐城企业版）项目企业数据为空" + ps.getComId());
                    SubContractor subContractor = subContractorMapper.selectById(slList.get(0).getLwtId());
                    if (subContractor == null)
                        throw new RuntimeException("实名制接口（盐城企业版）项目企业数据对象为空" + slList.get(0).getLwtId());
                    pm.setContractorOrgCode(subContractor.getOrganizationCode());
                    pm.setGeneralContractorCode(subContractor.getOrganizationCode());
                    pm.setGeneralContractorName(subContractor.getCompanyName());
                    pm.setProjectName(ps.getName());
                    if (ps.getProjectActivityType() != null) {
                        pm.setProjectActivityType("" + Integer.parseInt(ps.getProjectActivityType()));
                    } else {
                        pm.setProjectActivityType("99");
                    }
                    pm.setProjectDescription(ps.getDescription());
                    if (ps.getProjectTypeNew() != null) {
                        pm.setProjectCategory("" + Integer.parseInt(ps.getProjectTypeNew()));
                    } else {
                        pm.setProjectCategory("99");
                    }
                    pm.setIsMajorProject(1);
                    if (ps.getConstructorName() != null) {
                        pm.setOwnerName(ps.getConstructorName());
                    } else {
                        pm.setOwnerName("smz");
                    }
                    if (ps.getContractorOrgcode() != null) {
                        pm.setBuildCorporationCode(ps.getContractorOrgcode());
                    } else {
                        pm.setBuildCorporationCode("smz");
                    }
                    if (ps.getCompanyCode() != null) {
                        pm.setBuilderLicenceNum(ps.getCompanyCode());
                    } else {
                        pm.setBuilderLicenceNum("smz");
                    }
                    pm.setAreaCode("320600");
                    if (ps.getPlace() != null) {
                        pm.setAddress(ps.getPlace());
                    } else {
                        pm.setAddress("smz");
                    }
                    if (ps.getBuildingCost() != null) {
                        pm.setTotalContractAmt(new BigDecimal(ps.getBuildingCost()));
                    } else {
                        pm.setTotalContractAmt(new BigDecimal(0));
                    }
                    if (ps.getBuildingSize() != null) {
                        pm.setBuildingArea(new BigDecimal(ps.getBuildingSize()));
                    } else {
                        pm.setBuildingArea(new BigDecimal(0));
                    }
                    if (ps.getBegin() != null) {
                        pm.setStartDate(ps.getBegin().substring(0, 10));
                    } else {
                        Date currentTime = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String dateString = formatter.format(currentTime);
                        pm.setStartDate(dateString);
                    }
                    if (ps.getEnd() != null) {
                        pm.setCompleteDate(ps.getEnd().substring(0, 10));
                    } else {
                        Date currentTime = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String dateString = formatter.format(currentTime);
                        pm.setCompleteDate(dateString);
                    }
                    if (ps.getProjectProgressNew() != null) {
                        pm.setProjectStatus("" + Integer.parseInt(ps.getProjectProgressNew()));
                    } else {
                        pm.setProjectStatus("1");
                    }
                    if (ps.getLng() != null) {
                        pm.setLng(new BigDecimal(ps.getLng()));
                    } else {
                        pm.setLng(new BigDecimal(0));
                    }
                    if (ps.getLat() != null) {
                        pm.setLat(new BigDecimal(ps.getLat()));
                    } else {
                        pm.setLat(new BigDecimal(0));
                    }
                    pm.setRadius(2000.00);
                    pm.setDeviceType(4);
                    pm.setStatus(1);
                    pm.setIsDel(0);
                    this.baseMapper.insert(pm);
                    //保存项目公司关系
                    ProjectSubContractor projectSubContractor = new ProjectSubContractor();
                    projectSubContractor.setProjectCode(pm.getProjectCode());
                    projectSubContractor.setOrganizationCode(pm.getContractorOrgCode());
                    projectSubContractor.setContractorType(16);
                    projectSubContractorMapper.insert(projectSubContractor);
                    //保存主键关联表
                    long lwtId = pm.getId();
                    SmzLwt sl = new SmzLwt();
                    sl.setSmzId(smzId);
                    sl.setLwtId(lwtId);
                    sl.setTableName("ProjectCompanyYC");
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
                        + getSystemStringParam("saveProjectFromSMZCompanyResponse"), m, myc);
            }
        }
    }

    @Override
    public Map<String, Object> selectProjectByProjectCode(String projectCode) {
        return projectMasterMapper.selectProjectByProjectCode(projectCode);
    }

    /**
     * @description 获取实名制项目数据（南通）
     * @author chupp
     * @date 2018年7月26日
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void saveProjectFromSMZNT(Map<String, String> mnt, List<SubContractor> list) {
        //获取实名制项目数据
        String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")
                + getSystemStringParam("saveProjectFromSMZ"), new HashMap<String, Object>(), mnt);
        Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
        //存在新的项目数据
        if (map.get("project") != null) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("project"));
            List<ProjectSmz> projectList = JSONArray.toList(jsonArray, new ProjectSmz(), new JsonConfig());
            String id = "0";
            SubContractor subContractor = null;
            if (projectList.size() > 0) {
                for (ProjectSmz ps : projectList) {
                    if (ps.getComId().equals("0")) {
                        subContractor = list.get(0);
                    } else if (ps.getComId().equals("00")) {
                        subContractor = list.get(1);
                    } else if (ps.getComId().equals("000")) {
                        subContractor = list.get(2);
                    }
                    long smzId = Long.parseLong(ps.getId());
                    //保存实名制项目数据
                    ProjectMaster pm = new ProjectMaster();
                    pm.setProjectCode("PROJECT" + System.currentTimeMillis());
                    pm.setBuildProjectCode(pm.getProjectCode());
                    pm.setContractorOrgCode(subContractor.getOrganizationCode());
                    pm.setGeneralContractorCode(subContractor.getOrganizationCode());
                    pm.setGeneralContractorName(subContractor.getCompanyName());
                    pm.setProjectName(ps.getName());
                    if (ps.getProjectActivityType() != null) {
                        pm.setProjectActivityType("" + Integer.parseInt(ps.getProjectActivityType()));
                    } else {
                        pm.setProjectActivityType("99");
                    }
                    pm.setProjectDescription(ps.getDescription());
                    if (ps.getProjectTypeNew() != null) {
                        pm.setProjectCategory("" + Integer.parseInt(ps.getProjectTypeNew()));
                    } else {
                        pm.setProjectCategory("99");
                    }
                    pm.setIsMajorProject(1);
                    if (ps.getConstructorName() != null) {
                        pm.setOwnerName(ps.getConstructorName());
                    } else {
                        pm.setOwnerName("smz");
                    }
                    if (ps.getContractorOrgcode() != null) {
                        pm.setBuildCorporationCode(ps.getContractorOrgcode());
                    } else {
                        pm.setBuildCorporationCode("smz");
                    }
                    if (ps.getCompanyCode() != null) {
                        pm.setBuilderLicenceNum(ps.getCompanyCode());
                    } else {
                        pm.setBuilderLicenceNum("smz");
                    }
                    pm.setAreaCode("320600");
                    if (ps.getPlace() != null) {
                        pm.setAddress(ps.getPlace());
                    } else {
                        pm.setAddress("smz");
                    }
                    if (ps.getBuildingCost() != null) {
                        pm.setTotalContractAmt(new BigDecimal(ps.getBuildingCost()));
                    } else {
                        pm.setTotalContractAmt(new BigDecimal(0));
                    }
                    if (ps.getBuildingSize() != null) {
                        pm.setBuildingArea(new BigDecimal(ps.getBuildingSize()));
                    } else {
                        pm.setBuildingArea(new BigDecimal(0));
                    }
                    if (ps.getBegin() != null) {
                        pm.setStartDate(ps.getBegin().substring(0, 10));
                    } else {
                        Date currentTime = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String dateString = formatter.format(currentTime);
                        pm.setStartDate(dateString);
                    }
                    if (ps.getEnd() != null) {
                        pm.setCompleteDate(ps.getEnd().substring(0, 10));
                    } else {
                        Date currentTime = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String dateString = formatter.format(currentTime);
                        pm.setCompleteDate(dateString);
                    }
                    if (ps.getProjectProgressNew() != null) {
                        pm.setProjectStatus("" + Integer.parseInt(ps.getProjectProgressNew()));
                    } else {
                        pm.setProjectStatus("1");
                    }
                    if (ps.getLng() != null) {
                        pm.setLng(new BigDecimal(ps.getLng()));
                    } else {
                        pm.setLng(new BigDecimal(0));
                    }
                    if (ps.getLat() != null) {
                        pm.setLat(new BigDecimal(ps.getLat()));
                    } else {
                        pm.setLat(new BigDecimal(0));
                    }
                    pm.setRadius(2000.00);
                    pm.setDeviceType(4);
                    pm.setStatus(1);
                    pm.setIsDel(0);
                    this.baseMapper.insert(pm);
                    //保存项目公司关系
                    ProjectSubContractor projectSubContractor = new ProjectSubContractor();
                    projectSubContractor.setProjectCode(pm.getProjectCode());
                    projectSubContractor.setOrganizationCode(pm.getContractorOrgCode());
                    projectSubContractor.setContractorType(16);
                    projectSubContractorMapper.insert(projectSubContractor);
                    //保存主键关联表
                    long lwtId = pm.getId();
                    SmzLwt sl = new SmzLwt();
                    sl.setSmzId(smzId);
                    sl.setLwtId(lwtId);
                    sl.setTableName("Project");
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
                        + getSystemStringParam("saveProjectFromSMZResponse"), m, mnt);
            }
        }
    }


    @Override
    @Transactional
    public boolean updateById(ProjectMasterVo projectMaster) {
        Map<String, Object> pm = this.selectById(projectMaster.getId());
        String projectCode = pm.get("projectCode").toString();
        if ("1".equals(pm.get("isSynchro") == null ? "" : pm.get("isSynchro").toString())) {
            //查询字典表里考勤机的num
            Integer deviceNum = dictService.selectNumByName("考勤类型", "考勤机");
            if (deviceNum == null) {
                throw new XywgException(300, "字典表中缺少考勤类型—考勤机");
            }
            if ((!deviceNum.equals(projectMaster.getDeviceType())) && pm.get("virtualId") == null) {
                //考勤类型不为考勤机且没有虚拟考勤机 则添加虚拟考勤机
                //如果考勤类型不为考勤机，添加虚拟考勤机
                //1.查询已有的虚拟考勤机最大sn
                String maxSn = this.baseMapper.selectMaxVirtualDeviceSn();
                //2.生成下一条sn
                String newSn = "";
                if (maxSn == null) {
                    newSn = VIRTUAL_DEVICE_PRE + "000001";
                } else {
                    newSn = this.getNewDviceSn(maxSn);
                }
                //3.生成虚拟考勤机
                Device device = new Device(newSn, pm.get("contractorOrgCode").toString(), pm.get("projectCode").toString());
                deviceService.insert(device);
                projectMaster.setVirtualId(device.getId());
            }
            //原来是非考勤机类型，修改为考勤机类型需要删除原有的虚拟考勤机
            if (deviceNum.equals(projectMaster.getDeviceType()) && !deviceNum.equals(projectMaster.getOldDeviceType())) {
                if (pm.get("virtualId") != null && !pm.get("virtualId").equals("")) {
                    Device device = new Device();
                    device.setId((Long) pm.get("virtualId"));
                    deviceService.deleteById(device);
                    projectMaster.setVirtualId(0L);
                }
            }
        }
        projectAddressService.removeAddressWithProjectCode(projectCode);
        List<Map<String, Object>> projectAddress = projectMaster.getProjectAddressList();
        ProjectAddress savedProjectAddress = null;
        if (projectAddress != null) {
            for (Map<String, Object> map : projectAddress) {
                map.put("projectCode", projectCode);
                savedProjectAddress = BeanKit.mapToBean(map, ProjectAddress.class);
                savedProjectAddress.setRadius(Double.valueOf(map.get("radius").toString()));
                projectAddressService.insert(savedProjectAddress);
            }
        }
        projectMaster.setLat(savedProjectAddress.getLat());
        projectMaster.setLng(savedProjectAddress.getLng());
        projectMaster.setUpdateDate(new Date());
        projectMaster.setUpdateUser(ShiroKit.getUser().getName());
        this.baseMapper.updateById(projectMaster);
        String projectCodeByContractor = pm.get("projectCode").toString();
        String contractorOrgCode = projectMaster.getContractorOrgCode();
        this.baseMapper.updateByProjectCode(projectCodeByContractor,contractorOrgCode);
       Integer count= baseMapper.selectProjectWorkByProjectCode(projectCodeByContractor);
       if (count>0){
           this.baseMapper.updateOrgCodeByProjectCode(projectCodeByContractor,contractorOrgCode);
       }
        //完成调用zbus推送给智慧工地
//        RemoteDTO<Map<String, Object>> dto = new RemoteDTO();
//        Map<String, Object> project = new HashMap<>();
//        Map<String, Object> mm = this.baseMapper.selectByIdForZH(projectMaster.getId());
//        project.put("projectInfo", mm);
//        project.put("operationFlag", "updateExternalProject");
//        dto.setBody(project);
    /*    int state = ZbusHandler.send(JSONObject.fromObject(dto).toString());
        if (state == 200) {
            this.baseMapper.updateSynchro(projectMaster.getId(), 1);
        }*/
        return true;
    }

    @Override
    public List<AppProjectMasterDto> v116GetFollowProjectList(Integer id, String organizationCode, Integer pageSize, Integer pageNo) {
        return projectMasterMapper.v116GetFollowProjectList(id, organizationCode, (pageNo - 1) * pageSize, pageSize);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setTime(ProjectMaster projectMaster) {
        baseMapper.setTime(projectMaster);
        deviceReportMapper.countTotalTimeByProjectCode(projectMaster.getWorkTime(), projectMaster.getProjectCode());
    }

    @Override
    public ProjectMaster getWorkTimeById(Integer projectMasterId) {
        return baseMapper.getWorkTimeById(projectMasterId);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    //通过项目名称取南通实名制项目的接口
    public void saveProjectFromSMZTY(Map<String, String> mnt) {
        //获取实名制项目数据
        List<ProjectMaster> project = this.projectMasterMapper.getProjectInfo();
        for (int n = 0; n < project.size(); n++) {
            String projectId = null;
            String projectName = project.get(n).getProjectName();
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("projectName", projectName);
            hashMap.put("projectIdNew", projectId);

            String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")
                    + getSystemStringParam("saveProjectFromSMZTY"), hashMap, mnt);
            Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
            //存在新的项目数据
            if (map.get("project") != null) {
                JSONArray jsonArray = JSONArray.fromObject(map.get("project"));
                List<ProjectSmz> projectList = JSONArray.toList(jsonArray, new ProjectSmz(), new JsonConfig());
                String id = "0";
                if (projectList.size() > 0) {
                    for (ProjectSmz ps : projectList) {

                        long smzId = Long.parseLong(ps.getId());
                        //保存实名制项目数据
                        ProjectMaster pm = new ProjectMaster();
                        pm.setProjectCode("PROJECT" + System.currentTimeMillis());
                        pm.setBuildProjectCode(pm.getProjectCode());
	                    /*pm.setContractorOrgCode(subContractor.getOrganizationCode());
	                    pm.setGeneralContractorCode(subContractor.getOrganizationCode());
	                    pm.setGeneralContractorName(subContractor.getCompanyName());*/
                        pm.setProjectName(ps.getName());
                        if (ps.getProjectActivityType() != null) {
                            pm.setProjectActivityType("" + Integer.parseInt(ps.getProjectActivityType()));
                        } else {
                            pm.setProjectActivityType("99");
                        }
                        pm.setProjectDescription(ps.getDescription());
                        if (ps.getProjectTypeNew() != null) {
                            pm.setProjectCategory("" + Integer.parseInt(ps.getProjectTypeNew()));
                        } else {
                            pm.setProjectCategory("99");
                        }
                        pm.setIsMajorProject(1);
                        if (ps.getConstructorName() != null) {
                            pm.setOwnerName(ps.getConstructorName());
                        } else {
                            pm.setOwnerName("smz");
                        }
                        if (ps.getContractorOrgcode() != null) {
                            pm.setBuildCorporationCode(ps.getContractorOrgcode());
                        } else {
                            pm.setBuildCorporationCode("smz");
                        }
                        if (ps.getCompanyCode() != null) {
                            pm.setBuilderLicenceNum(ps.getCompanyCode());
                        } else {
                            pm.setBuilderLicenceNum("smz");
                        }
                        pm.setAreaCode("320600");
                        if (ps.getPlace() != null) {
                            pm.setAddress(ps.getPlace());
                        } else {
                            pm.setAddress("smz");
                        }
                        if (ps.getBuildingCost() != null) {
                            pm.setTotalContractAmt(new BigDecimal(ps.getBuildingCost()));
                        } else {
                            pm.setTotalContractAmt(new BigDecimal(0));
                        }
                        if (ps.getBuildingSize() != null) {
                            pm.setBuildingArea(new BigDecimal(ps.getBuildingSize()));
                        } else {
                            pm.setBuildingArea(new BigDecimal(0));
                        }
                        if (ps.getBegin() != null) {
                            pm.setStartDate(ps.getBegin().substring(0, 10));
                        } else {
                            Date currentTime = new Date();
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            String dateString = formatter.format(currentTime);
                            pm.setStartDate(dateString);
                        }
                        if (ps.getEnd() != null) {
                            pm.setCompleteDate(ps.getEnd().substring(0, 10));
                        } else {
                            Date currentTime = new Date();
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            String dateString = formatter.format(currentTime);
                            pm.setCompleteDate(dateString);
                        }
                        if (ps.getProjectProgressNew() != null) {
                            pm.setProjectStatus("" + Integer.parseInt(ps.getProjectProgressNew()));
                        } else {
                            pm.setProjectStatus("1");
                        }
                        if (ps.getLng() != null) {
                            pm.setLng(new BigDecimal(ps.getLng()));
                        } else {
                            pm.setLng(new BigDecimal(0));
                        }
                        if (ps.getLat() != null) {
                            pm.setLat(new BigDecimal(ps.getLat()));
                        } else {
                            pm.setLat(new BigDecimal(0));
                        }
                        pm.setRadius(2000.00);
                        pm.setDeviceType(4);
                        pm.setStatus(1);
                        pm.setIsDel(0);
                        this.baseMapper.insert(pm);
                        //保存项目公司关系
                        ProjectSubContractor projectSubContractor = new ProjectSubContractor();
                        projectSubContractor.setProjectCode(pm.getProjectCode());
                        projectSubContractor.setOrganizationCode(pm.getContractorOrgCode());
                        projectSubContractor.setContractorType(16);
                        projectSubContractorMapper.insert(projectSubContractor);
                        //保存主键关联表
                        long lwtId = pm.getId();
                        SmzLwt sl = new SmzLwt();
                        sl.setSmzId(smzId);
                        sl.setLwtId(lwtId);
                        sl.setTableName("Project");
                        smzLwtMapper.saveSmzLwt(sl);
                        if (Long.parseLong(id) < smzId) {
                            id = String.valueOf(smzId);
                        }
                    }
	               /* Map<String, Object> m = new HashMap<String, Object>();
	                m.put("code", "1");
	                m.put("no", id);
	                //响应保存成功信息
	                HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")
	                        + getSystemStringParam("saveProjectFromSMZResponse"), m, mnt);*/
                }
            }
        }
    }

    /**
     * 企业下面的项目id
     * @param mnt
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void saveCompanyFromSMZTY(Map<String, String> mnt) {
        //获取实名制企业下面的项目id
        //list<org_code>
        List<String> registerNo = new ArrayList<String>();
        registerNo = this.projectMasterMapper.getRegisterNo();
//		registerNo.add("91320681138854172P");
        for (int n = 0; n < registerNo.size(); n++) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();

            hashMap.put("registerNo", registerNo.get(n));
            //获取实名制企业数据
            String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")
                    + getSystemStringParam("saveCompanyFromSMZTY"), hashMap, mnt);
            Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
            //存在项目id
            if (map.get("list") != null) {
                JSONArray jsonArray = JSONArray.fromObject(map.get("list"));
                List<String> projectIds = JSONArray.toList(jsonArray, String.class, new JsonConfig());
                String maxId = "0";
                if (projectIds.size() > 0) {
                    for (String id : projectIds) {
                        //将得到的项目id和当前企业的社会统一信用代码保存到关系表
                        SmzCorProject sl = new SmzCorProject();
                        sl.setProjectId(Long.valueOf(id));
                        sl.setRegisterNo(registerNo.get(n));
                        smzLwtMapper.saveRegisterNoProjectId(sl);
                    }
                    //获取最后一个项目的id
                    maxId = projectIds.get(projectIds.size() - 1).toString();
                    Map<String, Object> m = new HashMap<String, Object>();
                    m.put("code", "1");
                    m.put("no", maxId);
                    m.put("registerNo", registerNo.get(n));
                    //响应保存成功信息
                    HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")
                            + getSystemStringParam("saveCompanyFromSMZResponseTY"), m, mnt);
                }
            }
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    //直接取南通实名制项目的接口
    public void saveProjectFromSMZByCor(Map<String, String> mnt) {
        List<String> registerNo = new ArrayList<String>();
            registerNo = this.projectMasterMapper.getRegisterNo();
        for (int j = 0; j < registerNo.size(); j++) {
            //查找已经存在的最大值
            Long maxId = this.smzLwtMapper.getProjectMaxId(registerNo.get(j));
            if (maxId == null) {
                maxId = Long.valueOf(0);
            }
            //获取实名制项目数据
            List<String> projectId = this.projectMasterMapper.getProjectInfoByCor(maxId, registerNo.get(j));
            String json = JSONArray.fromObject(projectId).toString();
//			for(int n=0;n<projectId.size();n++){
            String projectName = "";
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("projectName", projectName);
            hashMap.put("projectIdNew", json);
            //通过id或者项目名称获取实名制项目信息接口
            String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")
                    + getSystemStringParam("saveProjectFromSMZByCor"), hashMap, mnt);
            Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
            //存在新的项目数据
            if (map.get("project") != null) {
                JSONArray jsonArray = JSONArray.fromObject(map.get("project"));
                List<ProjectSmz> projectList = JSONArray.toList(jsonArray, new ProjectSmz(), new JsonConfig());
                String id = "0";
                if (projectList.size() > 0) {
                    for (ProjectSmz ps : projectList) {

                        long smzId = Long.parseLong(ps.getId());
                        //保存实名制项目数据
                        ProjectMaster pm = new ProjectMaster();
                        pm.setProjectCode("PROJECT" + System.currentTimeMillis());
                        pm.setBuildProjectCode(pm.getProjectCode());
                        pm.setContractorOrgCode(ps.getRegisterNo());
                        pm.setGeneralContractorCode(ps.getRegisterNo());
                        pm.setGeneralContractorName(ps.getCompanyName());
                        pm.setProjectName(ps.getName());
                        if (ps.getProjectActivityType() != null) {
                            pm.setProjectActivityType("" + Integer.parseInt(ps.getProjectActivityType()));
                        } else {
                            pm.setProjectActivityType("99");
                        }
                        pm.setProjectDescription(ps.getDescription());
                        if (ps.getProjectTypeNew() != null) {
                            pm.setProjectCategory("" + Integer.parseInt(ps.getProjectTypeNew()));
                        } else {
                            pm.setProjectCategory("99");
                        }
                        pm.setIsMajorProject(1);
                        if (ps.getConstructorName() != null) {
                            pm.setOwnerName(ps.getConstructorName());
                        } else {
                            pm.setOwnerName("smz");
                        }
                        if (ps.getContractorOrgcode() != null) {
                            pm.setBuildCorporationCode(ps.getContractorOrgcode());
                        } else {
                            pm.setBuildCorporationCode("smz");
                        }
                        if (ps.getCompanyCode() != null) {
                            pm.setBuilderLicenceNum(ps.getCompanyCode());
                        } else {
                            pm.setBuilderLicenceNum("smz");
                        }
                        pm.setAreaCode("320600");
                        if (ps.getPlace() != null) {
                            pm.setAddress(ps.getPlace());
                        } else {
                            pm.setAddress("smz");
                        }
                        if (ps.getBuildingCost() != null) {
                            pm.setTotalContractAmt(new BigDecimal(ps.getBuildingCost()));
                        } else {
                            pm.setTotalContractAmt(new BigDecimal(0));
                        }
                        if (ps.getBuildingSize() != null) {
                            pm.setBuildingArea(new BigDecimal(ps.getBuildingSize()));
                        } else {
                            pm.setBuildingArea(new BigDecimal(0));
                        }
                        if (ps.getBegin() != null) {
                            pm.setStartDate(ps.getBegin().substring(0, 10));
                        } else {
                            Date currentTime = new Date();
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            String dateString = formatter.format(currentTime);
                            pm.setStartDate(dateString);
                        }
                        if (ps.getEnd() != null) {
                            pm.setCompleteDate(ps.getEnd().substring(0, 10));
                        } else {
                            Date currentTime = new Date();
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            String dateString = formatter.format(currentTime);
                            pm.setCompleteDate(dateString);
                        }
                        if (ps.getProjectProgressNew() != null) {
                            pm.setProjectStatus("" + Integer.parseInt(ps.getProjectProgressNew()));
                        } else {
                            pm.setProjectStatus("1");
                        }
                        if (ps.getLng() != null) {
                            pm.setLng(new BigDecimal(ps.getLng()));
                        } else {
                            pm.setLng(new BigDecimal(0));
                        }
                        if (ps.getLat() != null) {
                            pm.setLat(new BigDecimal(ps.getLat()));
                        } else {
                            pm.setLat(new BigDecimal(0));
                        }
                        pm.setRadius(2000.00);
                        pm.setDeviceType(4);
                        pm.setStatus(1);
                        pm.setIsDel(0);
                        this.baseMapper.insert(pm);
                        //保存项目公司关系
                        ProjectSubContractor projectSubContractor = new ProjectSubContractor();
                        projectSubContractor.setProjectCode(pm.getProjectCode());
                        projectSubContractor.setOrganizationCode(pm.getContractorOrgCode());
                        projectSubContractor.setContractorType(16);
                        projectSubContractorMapper.insert(projectSubContractor);
                        //保存主键关联表
                        long lwtId = pm.getId();
                        SmzLwt sl = new SmzLwt();
                        sl.setSmzId(smzId);
                        sl.setLwtId(lwtId);
                        sl.setTableName("Project");
                        smzLwtMapper.saveSmzLwt(sl);
                        if (Long.parseLong(id) < smzId) {
                            id = String.valueOf(smzId);
                        }
                    }
                    //保存当前存到的最大项目值
                    SmzCorProject scp = new SmzCorProject();
                    scp.setProjectId(Long.valueOf(projectId.get(projectId.size() - 1)));
                    scp.setFixValue("project");
                    scp.setRegisterNo(registerNo.get(j));
                    if (maxId != 0) {
                        smzLwtMapper.updateScp(scp);
                    } else {
                        smzLwtMapper.saveScp(scp);
                    }
                }
	            /*Map<String, Object> m = new HashMap<String, Object>();
	            m.put("code", "1");
	            m.put("no", id);
	            //响应保存成功信息
	            HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")
	                    + getSystemStringParam("saveProjectFromSMZResponse"), m, mnt);*/


            }
//		      }
        }
    }

    @Override
    public List<Map<String, Object>> getListForSetting(Map<String, Object> params, Page page) {
        if (null == params) {
            params = new HashMap<>();
        }
        params.put("status", 1);
        params.put("isDel", 0);
        return baseMapper.getListForSetting(params, page);
    }

    @Override
    public void saveForSetting(Map<String, Object> params) {
        baseMapper.saveForSetting(params);
    }


    @Override
    public String getCompanyOrgCodeById(Object companyId) {
        return baseMapper.getCompanyOrgCodeById(companyId);
    }

    //解决 BeanUtils.copyProperties()的string转date异常
    private void stringToDateException() {
        ConvertUtils.register(new Converter() {
            @Override
            public Object convert(Class type, Object value) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    return simpleDateFormat.parse(value.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }, Date.class);

    }

    @Override
    public void addProjectMasterList(List<Object> addList) {
        for (Object o : addList) {
            //ProjectMaster projectMaster1 = JSON.parseObject(o.toString(), ProjectMaster.class);
            ProjectMaster projectMaster = new ProjectMaster();
            //解决 BeanUtils.copyProperties()的string转date异常
            stringToDateException();
            try {
                //拷贝为projectMaster对象
                BeanUtils.copyProperties(projectMaster, o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //根据projectCode查询
            ProjectMaster projectByProjectCode = getProjectByProjectCode(projectMaster.getProjectCode());
            if (projectByProjectCode == null) {
                //新增
                projectMaster.setId(null);
                insert(projectMaster);
                //新增公司项目中间表
                ProjectSubContractor projectSubContractor = new ProjectSubContractor();
                projectSubContractor.setProjectCode(projectMaster.getProjectCode());
                projectSubContractor.setOrganizationCode(projectMaster.getContractorOrgCode());
                projectSubContractor.setContractorType(16);
                projectSubContractorMapper.insert(projectSubContractor);
                //insert(new ProjectMasterVo());
            } else {
                //根据id修改
                projectMaster.setId(projectByProjectCode.getId());

                if (!projectByProjectCode.equalsAandB(projectMaster)) {
                    updateById(projectMaster);
                }
                /*    updateById();*/
            }

            //开线程发送数据
            singleThreadExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    ProjectMaster projectId = getProjectByProjectCode(projectMaster.getProjectCode());
                    if (projectId.getId() != null) {
                        //新增完成调用zbus推送给智慧工地
                        RemoteDTO<Map<String, Object>> dto = new RemoteDTO();
                        Map<String, Object> mm = baseMapper.selectByIdForZH(projectId.getId());
                        Map<String, Object> project = new HashMap<>();
                        project.put("operationFlag", "insertExternalProject");
                        //构建org
                        Map<String, Object> map = new HashMap<>();
                        SubContractor contractor = subContractorMapper.hasSubContractorByOrganizationCode(mm.get("orgCode").toString());
                        map.put("name", contractor.getCompanyName());
                        map.put("instroduction", "");
                        map.put("platform", 2);
                        map.put("relationOrgId", contractor.getId());
                        project.put("org", map);
                        //projectInfo
                        mm.remove("orgCode");
                        project.put("projectInfo", mm);
                        dto.setBody(project);
                 /*       int state = ZbusHandler.send(JSONObject.fromObject(dto).toString());
                        if (state == 200) {
                            //修改projectMaster的is_synchro  1已经同步给智慧工地
                            baseMapper.updateSynchro(projectId.getId(), 1);
                        }*/
                    }
                }
            });


        }
    }

    @Override
    public void updateProjectMasterList(List<Object> updateList) {
        for (Object o : updateList) {
            ProjectMaster projectMaster = new ProjectMaster();
            //解决 BeanUtils.copyProperties()的string转date异常
            stringToDateException();
            try {
                //拷贝为projectMaster对象
                BeanUtils.copyProperties(projectMaster, o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //根据projectCode查询
            ProjectMaster projectByProjectCode = getProjectByProjectCode(projectMaster.getProjectCode());
            if (projectByProjectCode != null) {
                //更新
                projectMaster.setId(projectByProjectCode.getId());
                //不一样就更新
                if (!projectByProjectCode.equals(projectMaster)) {
                    updateById(projectMaster);
                    //更新同步
                    singleThreadExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            //完成调用zbus推送给智慧工地
                            ProjectMaster projectId = getProjectByProjectCode(projectMaster.getProjectCode());
                            if (projectId.getId()!=null) {
                                RemoteDTO<Map<String, Object>> dto = new RemoteDTO();
                                Map<String, Object> project = new HashMap<>();
                                Map<String, Object> mm = baseMapper.selectByIdForZH(projectId.getId());
                                project.put("projectInfo", mm);
                                project.put("operationFlag", "updateExternalProject");
                                dto.setBody(project);
                              /*  int state = ZbusHandler.send(JSONObject.fromObject(dto).toString());
                                if (state == 200) {
                                    baseMapper.updateSynchro(projectId.getId(), 1);
                                }*/
                            }
                        }
                    });
                }
            }
        }
    }

    /**
     * 同步项目到智慧工地
     * @param pt
     */
    @Override
    public void syncZh(ProjectMaster pt) {

        ProjectMaster projectId = getProjectByProjectCode(pt.getProjectCode());
        if (projectId.getId() != null) {
            //新增完成调用zbus推送给智慧工地
            RemoteDTO<Map<String, Object>> dto = new RemoteDTO();
            Map<String, Object> mm = baseMapper.selectByIdForZH(projectId.getId());
            Map<String, Object> project = new HashMap<>();
            project.put("operationFlag", "insertExternalProject");
            //构建org
            Map<String, Object> map = new HashMap<>();
            SubContractor contractor = subContractorMapper.hasSubContractorByOrganizationCode(mm.get("orgCode").toString());
            map.put("name", contractor.getCompanyName());
            map.put("instroduction", "");
            map.put("platform", 2);
            map.put("relationOrgId", contractor.getId());
            project.put("org", map);
            //projectInfo
            mm.remove("orgCode");
            project.put("projectInfo", mm);
            dto.setBody(project);
           // int state = ZbusHandler.send(JSONObject.fromObject(dto).toString());
        /*    if (state == 200) {
                //修改projectMaster的is_synchro  1已经同步给智慧工地
                baseMapper.updateSynchro(projectId.getId(), 1);
            }*/
        }
    }



    @Override
    public List<ProjectMaster> queryProjectById(String organizationCode, Long id) {
        return this.projectMasterMapper.queryProjectById(organizationCode,id);
    }


    @Override
    public Boolean setMac(String projectCode, String macCode) {
      Integer num =  baseMapper.selectProject(projectCode);
      if (num>0){
          //修改
          baseMapper.updateMac(projectCode,macCode,new Date());
      }else {
          //新增
          baseMapper.serMac(projectCode,macCode,new Date());
      }
        return true;
    }
}
