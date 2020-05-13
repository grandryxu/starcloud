package com.xywg.admin.modular.device.service.impl;

import cn.hutool.json.JSONUtil;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.config.handler.RemoteDTO;
import com.xywg.admin.config.handler.ZbusHandler;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.util.DateUtil;
import com.xywg.admin.core.util.ImageUtils;
import com.xywg.admin.modular.device.model.Device;
import com.xywg.admin.modular.device.model.DeviceRecord;
import com.xywg.admin.modular.device.model.DeviceType;
import com.xywg.admin.modular.company.dao.SubContractorMapper;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.device.dao.DeviceMapper;
import com.xywg.admin.modular.device.dao.DeviceRecordExceptionDataMapper;
import com.xywg.admin.modular.device.dao.DeviceRecordMapper;
import com.xywg.admin.modular.device.dao.DeviceTypeMapper;
import com.xywg.admin.modular.device.service.IDeviceService;
import com.xywg.admin.modular.mina.TheApp;
import com.xywg.admin.modular.mina.Utils;
import com.xywg.admin.modular.project.dao.ProjectMasterMapper;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.service.IProjectMasterService;
import com.xywg.admin.modular.smz.dao.SmzLwtMapper;
import com.xywg.admin.modular.smz.model.DeviceMo;
import com.xywg.admin.modular.smz.model.DeviceSmz;
import com.xywg.admin.modular.smz.model.IfaLabor;
import com.xywg.admin.modular.smz.model.SmzCallBack;
import com.xywg.admin.modular.smz.model.SmzLwt;
import com.xywg.admin.modular.smz.service.IfaLaborService;
import com.xywg.admin.modular.smz.utils.FTPClientUtil;
import com.xywg.admin.modular.smz.utils.HttpClientUtils;
import com.xywg.admin.modular.system.service.AccountProjectService;
import com.xywg.admin.modular.system.service.IUserService;
import com.xywg.admin.modular.team.dao.TeamMasterMapper;
import com.xywg.admin.modular.team.model.TeamMaster;
import com.xywg.admin.modular.worker.dao.WorkerMasterMapper;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import com.xywg.admin.modular.worker.service.impl.WorkerMasterServiceImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.google.common.collect.Lists;
import org.testcontainers.shaded.com.google.common.collect.Maps;

import javax.annotation.Resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 考勤机 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-30
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {
	private static Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class);
    @Resource
    private DeviceMapper deviceMapper;

    @Autowired
    private DeviceTypeMapper deviceTypeMapper;

    @Autowired
    private WorkerMasterMapper workerMasterMapper;

    @Autowired
    private DeviceRecordExceptionDataMapper deviceRecordExceptionDataMapper;

    @Autowired
    private DeviceRecordMapper deviceRecordMapper;

    @Autowired
    private ProjectMasterMapper projectMasterMapper;

    @Autowired
    private SubContractorMapper subContractorMapper;

    @Autowired
    private IfaLaborService ifaLaborService;

    @Autowired
    private AccountProjectService accountProjectService;
    @Autowired
    private SmzLwtMapper smzLwtMapper;
    @Autowired
    private IProjectMasterService projectMasterService;
    @Autowired
    private IUserService userService;
    private static final Log LOG = LogFactory.getLog(DeviceServiceImpl.class);
    private static Properties systemParams = new Properties();
    @Autowired
    private TeamMasterMapper teamMasterMapper;
    /**
     * 加载配置文件
     */
    static {
        try {
            systemParams.load(DeviceServiceImpl.class.getClassLoader().getResourceAsStream("smz.properties"));
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
     * @description 获取实名制考勤设备（盐城）
     * @author chupp
     * @date 2018年7月4日
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void saveDeviceFromSMZYC(Map<String, String> myc) {
        //获取实名制考勤设备数据
        String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefixYC")
                + getSystemStringParam("saveDeviceFromSMZYC"), new HashMap<String, Object>(), myc);
        Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
        //存在新的考勤设备信息
        if (map.get("device") != null) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("device"));
            List<DeviceSmz> deviceList = JSONArray.toList(jsonArray, new DeviceSmz(), new JsonConfig());
            List<SmzLwt> slList = smzLwtMapper.getLwtIdBySmzId(0, "Company");
            if (slList.size() == 0) throw new RuntimeException("实名制接口企业数据为空");
            SubContractor subContractor = subContractorMapper.selectById(slList.get(0).getLwtId());
            if (subContractor == null) throw new RuntimeException("实名制接口企业数据对象为空");
            String id = "0";
            if (deviceList.size() > 0) {
                for (DeviceSmz ds : deviceList) {
                    long smzId = Long.parseLong(ds.getId());
                    Device d = new Device();
                    d.setOrganizationCode(subContractor.getOrganizationCode());
                    slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(ds.getProid()), "ProjectYC");
                    if (slList.size() == 0) throw new RuntimeException("实名制接口考勤设备所属项目为空");
                    Map<String, Object> chProject = projectMasterMapper.selectById(slList.get(0).getLwtId());
                    if (chProject == null) throw new RuntimeException("实名制接口考勤设备所属项目对象为空");
                    d.setProjectCode((String) chProject.get("projectCode"));
                    if (ds.getTypeid() != null) d.setTypeId(Long.parseLong(ds.getTypeid()));
                    d.setSn(ds.getSn());
                    d.setName(ds.getName());
                    d.setIp(ds.getIp());
                    d.setMac(ds.getMac());
                    d.setVersion(ds.getVersion());
                    d.setCode(ds.getCode());
                    d.setSecurityKey(ds.getSecurityKey());
                    d.setServerIp(ds.getServerIp());
                    d.setServerPort(ds.getServerPort());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    try {
                        if (ds.getTsTalk() != null) d.setTalk(sdf.parse(ds.getTsTalk()));
                    } catch (ParseException e) {
                    	d.setTalk(new Date());
                    }
                    if (ds.getState() != null) d.setState(Integer.parseInt(ds.getState()));
                    d.setDescription(ds.getDescription());
                    if (ds.getUpdateType() != null) d.setUpdateType(Integer.parseInt(ds.getUpdateType()));
                    d.setStatus(1);
                    if (ds.getDeviceProperty() != null) d.setType(Integer.parseInt(ds.getDeviceProperty()));
                    d.setAlgVersion(ds.getAlgVersion());
                    d.setIsDel(0);
                    //保存实名制设备数据
                    this.baseMapper.insert(d);
                    //保存实名制主键数据
                    long lwtId = d.getId();
                    SmzLwt sl = new SmzLwt();
                    sl.setSmzId(smzId);
                    sl.setLwtId(lwtId);
                    sl.setTableName("DeviceYC");
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
                        + getSystemStringParam("saveDeviceFromSMZResponseYC"), m, myc);
            }
        }
    }

    @Override
    public boolean getDeviceFromLabor(List<Long> ids, Map<String, String> m) {
        List<DeviceMo> list = deviceMapper.getDeviceFromLabor(ids);
        if(list.size() > 0) {
        	 String json = JSONArray.fromObject(list).toString();
             Map<String, Object> map = new HashMap<>();
             map.put("lablorDveice", json);
             String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefix") + getSystemStringParam("saveDeviceFromLabor"), map, m);
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
    public int updateDevice(Device device) {
        //首先判重
        Device hasDevice=baseMapper.checkBySn(device);
        if(hasDevice!=null){
            throw new XywgException(600, "该序列号"+device.getSn() + "在项目"+ projectMasterService.getProjectByProjectCode(hasDevice.getProjectCode()).getProjectName()+"中已存在！");
        }else {
            baseMapper.updateDevice(device);
            //完成调用zbus推送给智慧工地
            ProjectMaster projectMaster = projectMasterService.getProjectByProjectCode(device.getProjectCode());
            if(projectMaster.getIsSynchro().intValue() == 1) {
                Map<String, Object> map = new HashMap<>();
                map.put("relationDeviceId", device.getId());
                map.put("relationProjectId", projectMaster.getId());
                map.put("deviceNo", device.getSn());
                map.put("manufactor", "");
                map.put("platform", 2);
                RemoteDTO dto = new RemoteDTO();
                Map<String, Object> mm = new HashMap<>();
                mm.put("attendance", map);
                mm.put("operationFlag", "updateExternalAttendance");
                dto.setBody(mm);
                ZbusHandler.send(JSONObject.fromObject(dto).toString());
            }
            return 0;
        }
    }

    /**
     * 获取列表
     *
     * @param page
     * @param map
     * @return
     */
    @Override
    public List<Map<String, Object>> selectList(Page page, Map map) {
        map.put("projectCodes", accountProjectService.getProjectCodes());
        map.put("deptId", ShiroKit.getUser().getDeptId());
        map.put("userType",userService.selectById(ShiroKit.getUser().getId()).getUserType());
        return deviceMapper.list(page, map);
    }

    @Transactional
    @Override
    public Integer toggle(String ids, Integer state) {
        String[] idArray = ids.split(",");
        if(state == 1){
            //启用判重
            for(int i=0; i<idArray.length ; i++){
                Device device = this.deviceMapper.checkEnable(Long.valueOf(idArray[i]));
                if(device != null){
                    throw new XywgException(600, "该序列号"+device.getSn() + "在项目"+ projectMasterService.getProjectByProjectCode(device.getProjectCode()).getProjectName()+"中已存在！");
                }else{
                     deviceMapper.toggle(idArray[i], state);
                }
            }
            return idArray.length;
        }else{
            //禁用
            return deviceMapper.toggle(ids, state);
        }
    }

    @Override
    public List<Device> getDevicesByDeviceType(Long menuId) {
        return deviceMapper.getDevicesByDeviceType(menuId);
    }

    /**
     * 新增
     *
     * @param device
     * @return
     */
    @Override
    public boolean insert(Device device) {
        Device hasDevice = deviceMapper.selectDeviceBySn(device);
        if ( hasDevice!= null) {
            throw new XywgException(600, "该序列号"+device.getSn() + "在项目"+ projectMasterService.getProjectByProjectCode(hasDevice.getProjectCode()).getProjectName()+"中已存在！");
        }
        device.setCreateUser(ShiroKit.getUser().getName());
        Boolean flag = retBool(this.baseMapper.insert(device));
        //在与实名制对接接口工具表中插一条数据 xss
        IfaLabor ifaLabor = new IfaLabor("buss_device", device.getId());
        ifaLaborService.insert(ifaLabor);
        return flag;
    }

    /**
     * @Description: 根据sn获取设备信息
     * @author cxl
     * @date 2017年12月29日 下午2:43:04
     */
    @Override
    public Device deviceHeartbeatArrived(String sn) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("sn", sn);
        List<Device> devices = this.deviceMapper.getByDeviceSnMap(params);
        if (!CollectionUtils.isEmpty(devices)) {
            return devices.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void askDeviceInfo(String sn) {
        this.deviceMapper.askDeviceInfo(sn);
    }

    @Override
    public void setDeviceInfo(Device device) {
        device.setTypeId(getDeviceTypeId(String.valueOf(device.getType())));
        this.deviceMapper.setDeviceInfo(device);
    }

    private Long getDeviceTypeId(String type) {
        if (Utils.isEmpty(type)) {
            return null;
        }

        Long typeId = deviceTypeMapper.getDeviceTypeId(type);
        if (typeId == null) {
            DeviceType model = new DeviceType();
            model.setType(type);
            try {
                deviceTypeMapper.addDeviceType(model);
                typeId = model.getId();
            } catch (Exception e) {
                log.error(e.getMessage());
                typeId = deviceTypeMapper.getDeviceTypeId(type);
            }
        }
        return typeId;
    }

    @Override
    public void heartbeat(List<Long> devices) {
        int batch = TheApp.getSqlBatch();
        int size = devices.size();
        int start;
        if (size <= batch) {
            this.deviceMapper.heartbeat(devices);
        } else {
            for (start = 0; start < size; ) {
                int end = start + batch;
                if (end > size) {
                    end = size;
                }
                this.deviceMapper.heartbeat(devices.subList(start, end));
                start = end;
            }
        }
    }

    @Override
    public void saveRecord(List<DeviceRecord> record)throws IOException {
        String now = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        // 存正常考勤
        List<DeviceRecord> recordSave = new ArrayList<DeviceRecord>();
        // 存异常考勤
        List<DeviceRecord> abNormalRecordSave = new ArrayList<DeviceRecord>();
        // 查询考勤的人员信息
        List<WorkerMaster> validUser = workerMasterMapper.getValidUser(record);
        // 判断人员身份为空
        if (validUser.size() > 0) {
            // 查询出的人员放入map
            Map<String, WorkerMaster> userMap = new HashMap<String, WorkerMaster>();
            for (WorkerMaster u : validUser) {
                userMap.put(u.getIdCardNumber(), u);
            }
            // 循环考勤记录
            for (int i = 0; i < record.size(); i++) {
                DeviceRecord r = record.get(i);
                // 取出考勤记录对应的人员
                WorkerMaster wm = userMap.get(r.getPerson().getIdCardNumber());
                if (wm == null) {
                    abNormalRecordSave.add(r);
                    continue;
                }
                r.setPerson(wm);
                // 设置考勤方式
                if (r.getType() == null) {
                    r.setType(1);
                }
                r.setSource(4);
                recordSave.add(r);
                // 当需要新增的记录达到20条或者到最后数据时新增
                FTPClient ftpClient = com.xywg.admin.modular.faceUtils.FTPClientUtil.connectFtp();
                if (recordSave.size() % 20 == 0 || i == record.size() - 1) {
                    //打上水印
                    recordSave.forEach(model -> {
                        //判断是否要增加水印
                        ProjectMaster p = projectMasterMapper.getProjectByProjectCode(model.getProjectCode());
                        if(p.getIsWatermark().intValue() == 1) {
                            //添加水印
                            String fileDir = model.getPhoto().substring(0, model.getPhoto().lastIndexOf("/")+1);
                            try {
                                if(ftpClient.changeWorkingDirectory(fileDir)) {
                                    String fileName = model.getPhoto().substring(model.getPhoto().lastIndexOf("/")+1,
                                            model.getPhoto().length());
                                    InputStream is = ftpClient.retrieveFileStream(fileName);
                                    //图片压缩的demo ImageUtils.compressImage(is, file.getSize());
                                    //图片加水印的demo
                                    byte[] b = ImageUtils.addWatermark(is, now, null,0.7f);
                                    is.close();
                                    ftpClient.completePendingCommand();
                                    FTPClientUtil.uploadFile(new ByteArrayInputStream(b),
                                            new String((fileName).getBytes("utf-8"),"iso-8859-1"),ftpClient);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    deviceRecordMapper.saveRecord(recordSave);
                    recordSave.clear();
                }
            }
//			if (recordSave.size() != 0) {
//				deviceRecordMapper.saveRecord(recordSave);
//				return;
//			}

            if (abNormalRecordSave.size() > 0) {
                this.deviceRecordExceptionDataMapper.saveExceptionRecord(abNormalRecordSave);
                return;
            }
        } else {
            deviceRecordExceptionDataMapper.saveExceptionRecord(record);
        }
    }

    @Override
    public Device selectDeviceById(Long id) {
        return this.deviceMapper.selectDeviceById(id);
    }

    @Override
    public Integer deleteDevices(String ids) {
        //先获取到devices的信息
        List<Long> idList = Arrays.asList(ids.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        List<Map<String, Object>> devices = this.deviceMapper.getDevices(idList);
        //批量删除
        int row = this.deviceMapper.deleteDevices(ids);
        //通知zbus
        devices.forEach(model -> {
            //如果同步的话
            if(model.get("synchro").toString().equals("1")) {
                RemoteDTO dto = new RemoteDTO();
                Map<String, Object> mm = new HashMap<>();
                mm.put("id", model.get("id"));
                mm.put("relationProjectId", model.get("projectId"));
                mm.put("operationFlag", "deleteExternalAttendance");
                dto.setBody(mm);
                ZbusHandler.send(JSONObject.fromObject(dto).toString());
            }
        });
        return row;
    }

    /**
     * 
     * @description 获取实名制考勤设备（南通）
     * @author chupp
     * @date 2018年7月26日
     *
     */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void saveDeviceFromSMZNT(Map<String, String> mnt, List<SubContractor> list) {
		//获取实名制考勤设备数据
        String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")
                + getSystemStringParam("saveDeviceFromSMZ"), new HashMap<String, Object>(), mnt);
        Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
        //存在新的考勤设备信息
        if (map.get("device") != null) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("device"));
            List<DeviceSmz> deviceList = JSONArray.toList(jsonArray, new DeviceSmz(), new JsonConfig());
            String id = "0";
            SubContractor subContractor = null;
            List<SmzLwt> slList = null;
            if (deviceList.size() > 0) {
                for (DeviceSmz ds : deviceList) {
                	if(ds.getComId().equals("0")) {
                		subContractor = list.get(0);
                	}else if(ds.getComId().equals("00")) {
                		subContractor = list.get(1);
                	}else if(ds.getComId().equals("000")) {
                		subContractor = list.get(2);
                	}
                    long smzId = Long.parseLong(ds.getId());
                    Device d = new Device();
                    d.setOrganizationCode(subContractor.getOrganizationCode());
                    slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(ds.getProid()), "Project");
                    if (slList.size() == 0) throw new RuntimeException("实名制接口考勤设备所属项目为空");
                    Map<String, Object> chProject = projectMasterMapper.selectById(slList.get(0).getLwtId());
                    if (chProject == null) throw new RuntimeException("实名制接口考勤设备所属项目对象为空");
                    d.setProjectCode((String) chProject.get("projectCode"));
                    if (ds.getTypeid() != null) d.setTypeId(Long.parseLong(ds.getTypeid()));
                    d.setSn(ds.getSn());
                    d.setName(ds.getName());
                    d.setIp(ds.getIp());
                    d.setMac(ds.getMac());
                    d.setVersion(ds.getVersion());
                    d.setCode(ds.getCode());
                    d.setSecurityKey(ds.getSecurityKey());
                    d.setServerIp(ds.getServerIp());
                    d.setServerPort(ds.getServerPort());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    try {
                        if (ds.getTsTalk() != null) d.setTalk(sdf.parse(ds.getTsTalk()));
                    } catch (ParseException e) {
                    	d.setTalk(new Date());
                    }
                    if (ds.getState() != null) d.setState(Integer.parseInt(ds.getState()));
                    d.setDescription(ds.getDescription());
                    if (ds.getUpdateType() != null) d.setUpdateType(Integer.parseInt(ds.getUpdateType()));
                    d.setStatus(1);
                    if (ds.getDeviceProperty() != null) d.setType(Integer.parseInt(ds.getDeviceProperty()));
                    d.setAlgVersion(ds.getAlgVersion());
                    d.setIsDel(0);
                    //保存实名制设备数据
                    this.baseMapper.insert(d);
                    //保存实名制主键数据
                    long lwtId = d.getId();
                    SmzLwt sl = new SmzLwt();
                    sl.setSmzId(smzId);
                    sl.setLwtId(lwtId);
                    sl.setTableName("Device");
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
                        + getSystemStringParam("saveDeviceFromSMZResponse"), m, mnt);
            }
        }
	}
	

    @Override
    public Device getBySn(String sn) {
        return this.baseMapper.getByDeviceSn(sn);
    }
    
    @SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void saveDeviceFromSMZTY(Map<String, String> mnt) {
    	List<String> registerNo = new ArrayList<String>();
		registerNo = this.projectMasterMapper.getRegisterNo();
		for(int j=0;j<registerNo.size();j++){
			//获取实名制考勤设备数据
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
		                + getSystemStringParam("saveDeviceFromSMZTY"), hashMap, mnt);
		        Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
		        //存在新的考勤设备信息
		        if (map.get("device") != null) {
		            JSONArray jsonArray = JSONArray.fromObject(map.get("device"));
		            List<DeviceSmz> deviceList = JSONArray.toList(jsonArray, new DeviceSmz(), new JsonConfig());
		            String id = "0";
		            String subContractor = null;
		            List<SmzLwt> slList = null;
		            if (deviceList.size() > 0) {
		                for (DeviceSmz ds : deviceList) {
		                	
		                    long smzId = Long.parseLong(ds.getId());
		                    Device d = new Device();
		                    slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(ds.getProid()), "Project");
		                    if (slList.size() == 0) throw new RuntimeException("实名制接口考勤设备所属项目为空");
		                    Map<String, Object> chProject = projectMasterMapper.selectById(slList.get(0).getLwtId());
		                    if (chProject == null) throw new RuntimeException("实名制接口考勤设备所属项目对象为空");
		                    d.setProjectCode((String) chProject.get("projectCode"));
		                  //根据项目id查找社会统一信用代码
		                    subContractor = teamMasterMapper.queryOrgnationCode(slList.get(0).getLwtId());
		                    d.setOrganizationCode(subContractor);
		                    if (ds.getTypeid() != null) d.setTypeId(Long.parseLong(ds.getTypeid()));
		                    d.setSn(ds.getSn());
		                    d.setName(ds.getName());
		                    d.setIp(ds.getIp());
		                    d.setMac(ds.getMac());
		                    d.setVersion(ds.getVersion());
		                    d.setCode(ds.getCode());
		                    d.setSecurityKey(ds.getSecurityKey());
		                    d.setServerIp(ds.getServerIp());
		                    d.setServerPort(ds.getServerPort());
		                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		                    try {
		                        if (ds.getTsTalk() != null) d.setTalk(sdf.parse(ds.getTsTalk()));
		                    } catch (ParseException e) {
		                    	d.setTalk(new Date());
		                    }
		                    if (ds.getState() != null) d.setState(Integer.parseInt(ds.getState()));
		                    d.setDescription(ds.getDescription());
		                    if (ds.getUpdateType() != null) d.setUpdateType(Integer.parseInt(ds.getUpdateType()));
		                    d.setStatus(1);
		                    if (ds.getDeviceProperty() != null) d.setType(Integer.parseInt(ds.getDeviceProperty()));
		                    d.setAlgVersion(ds.getAlgVersion());
		                    d.setIsDel(0);
		                    //保存实名制设备数据
		                    this.baseMapper.insert(d);
		                    //保存实名制主键数据
		                    long lwtId = d.getId();
		                    SmzLwt sl = new SmzLwt();
		                    sl.setSmzId(smzId);
		                    sl.setLwtId(lwtId);
		                    sl.setTableName("Device");
		                    smzLwtMapper.saveSmzLwt(sl);
		                    if (Long.parseLong(id) < smzId) {
		                        id = String.valueOf(smzId);
		                    }
		                }
		                scb.setMaxId(id);
		                scb.setProjectId( projectId.get(n));
		                scbList.add(scb);
		            }
		        }
			}
	        Map<String, Object> m = new HashMap<String, Object>();
			String json = JSONArray.fromObject(scbList).toString();
            m.put("code", "1");
            m.put("scbList", json);
            //响应保存成功信息
            HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")
                    + getSystemStringParam("saveDeviceFromSMZResponseTY"), m, mnt);
		} 
	}

    @Override
    public void addDevice(List<Object> addList) {
        for (Object o : addList) {
            //Device device = new Device();
            stringToDateException();
           Device device = JSON.parseObject(o.toString(), Device.class);
          /*  Map<String, Object> deviceMapToString = JSON.parseObject(o.toString(), Map.class);
            device.setAlgVersion((String) deviceMapToString.get("algVersion"));
            device.setCode((String) deviceMapToString.get("code"));
            device.setCreateDate((Date) deviceMapToString.get("createDate"));
            device.setCreateUser((String) deviceMapToString.get("createUser"));
            device.setDescription((String) deviceMapToString.get("description"));
            device.setId((Long) deviceMapToString.get("id"));
            device.setIp((String) deviceMapToString.get("ip"));
            device.setIsDel((Integer) deviceMapToString.get("isDel"));
            device.setMac((String) deviceMapToString.get("mac"));
            device.setName((String) deviceMapToString.get("name"));
            device.setOrganizationCode((String) deviceMapToString.get("organizationCode"));*/

     /*       try {
             BeanUtils.copyProperties(device, o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }*/
            Map<String,Long>deviceMap=baseMapper.getBySn(device.getSn());
            if (deviceMap.get("num")==0){
                device.setId(null);
                insert(device);
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
    public List<Device> queryDeviceById(String organizationCode, Long id,String projectCode) {
        return this.baseMapper.queryDeviceById(organizationCode,id,projectCode);
    }
}
