package com.xywg.admin.modular.project.service.impl;


import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.project.dao.ProjectMasterMapper;
import com.xywg.admin.modular.project.dao.ProjectTrainRecordMapper;
import com.xywg.admin.modular.project.dao.ProjectTrainingMapper;
import com.xywg.admin.modular.project.model.ProjectTraining;
import com.xywg.admin.modular.project.model.ProjectTrainingVo;
import com.xywg.admin.modular.project.service.IProjectTrainingService;
import com.xywg.admin.modular.smz.dao.SmzLwtMapper;
import com.xywg.admin.modular.smz.model.*;
import com.xywg.admin.modular.smz.service.IfaLaborService;
import com.xywg.admin.modular.smz.utils.Base64;
import com.xywg.admin.modular.smz.utils.*;
import com.xywg.admin.modular.system.dao.FileInfoMapper;
import com.xywg.admin.modular.system.model.FileInfo;
import com.xywg.admin.modular.wages.model.dto.PictureDto;
import com.xywg.admin.modular.system.service.AccountProjectService;
import com.xywg.admin.modular.system.service.IFileInfoService;
import com.xywg.admin.modular.system.service.UploadService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.net.SocketException;
import java.util.*;

/**
 * <p>
 * 项目中的培训记录 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-14
 */
@Service
public class ProjectTrainingServiceImpl extends ServiceImpl<ProjectTrainingMapper, ProjectTraining> implements IProjectTrainingService {
	private static  Logger log = LoggerFactory.getLogger(ProjectTrainingServiceImpl.class);

	@Autowired
    private IfaLaborService ifaLaborService;
    @Resource
    private FileInfoMapper fileInfoMapper;

    @Resource
    private ProjectTrainRecordMapper projectTrainRecordMapper;

    @Autowired
    private ProjectMasterMapper projectMasterMapper;

    @Autowired
    private AccountProjectService accountProjectService;
    

    @Autowired
    private UploadService uploadService;

    @Autowired
    private IFileInfoService fileInfoService;

    @Autowired
    private SmzLwtMapper smzLwtMapper;
    private static final Log LOG = LogFactory.getLog(ProjectTrainingServiceImpl.class);
    private static Properties systemParams = new Properties();

    /**
     * 加载配置文件
     */
    static {
        try {
            systemParams.load(ProjectTrainingServiceImpl.class.getClassLoader().getResourceAsStream("smz.properties"));
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
     * @description 获取实名制培训信息（盐城）
     * @author chupp
     * @date 2018年7月4日
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void saveTrainFromSMZYC(Map<String, String> myc) {
        //获取实名制培训数据
        String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefixYC")
                + getSystemStringParam("saveTrainFromSMZYC"), new HashMap<String, Object>(), myc);
        Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
        //存在新的培训信息
        if (map.get("train") != null) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("train"));
            List<TrainSmz> trainList = JSONArray.toList(jsonArray, new TrainSmz(), new JsonConfig());
            List<SmzLwt> slList = null;
            String id = "0";
            if (trainList.size() > 0) {
                for (TrainSmz ts : trainList) {
                    long smzId = Long.parseLong(ts.getId());
                    ProjectTraining pt = new ProjectTraining();
                    slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(ts.getProjectId()), "ProjectYC");
                    if (slList.size() == 0) throw new RuntimeException("实名制接口培训所属项目信息为空");
                    Map<String, Object> chProject = projectMasterMapper.selectById(slList.get(0).getLwtId());
                    if (chProject == null) throw new RuntimeException("实名制接口培训所属项目对象为空");
                    pt.setProjectCode((String) chProject.get("projectCode"));
                    if (ts.getTrainDate() != null) {
                        pt.setTrainingTime(java.sql.Date.valueOf(ts.getTrainDate().substring(0, 10)));
                    } else {
                        pt.setTrainingTime(new java.sql.Date(System.currentTimeMillis()));
                    }
                    if (ts.getTrainDuration() != null) {
                        pt.setTrainingDuration(Integer.parseInt(ts.getTrainDuration()));
                    } else {
                        pt.setTrainingDuration(0);
                    }
                    if (ts.getTrainTypeCode() != null) {
                        pt.setTrainingTypeCode("" + Integer.parseInt(ts.getTrainTypeCode()));
                    } else {
                        pt.setTrainingTypeCode("3001");
                    }
                    pt.setTrainingName(ts.getTrainName());
                    pt.setTrainer(ts.getTrainner());
                    pt.setDescription(ts.getTrainContent());
                    pt.setIsDel(0);
                    //保存实名制培训数据
                    this.baseMapper.insert(pt);
                    //保存主键关联表
                    long lwtId = pt.getId();
                    SmzLwt sl = new SmzLwt();
                    sl.setSmzId(smzId);
                    sl.setLwtId(lwtId);
                    sl.setTableName("TrainYC");
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
                        + getSystemStringParam("saveTrainFromSMZResponseYC"), m, myc);
            }
        }
    }

    /**
     * @description 获取实名制培训附件信息（盐城）
     * @author chupp
     * @date 2018年7月13日
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void saveTrainAttachmentFromSMZYC(Map<String, String> myc) {
        //获取实名制培训附件数据
        String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefixYC")
                + getSystemStringParam("saveTrainAttachmentFromSMZYC"), new HashMap<String, Object>(), myc);
        Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
        //存在新的培训附件信息
        if (map.get("train") != null) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("train"));
            List<TrainSmz> trainList = JSONArray.toList(jsonArray, new TrainSmz(), new JsonConfig());
            List<SmzLwt> slList = null;
            String id = "0";
            if (trainList.size() > 0) {
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
                    for (TrainSmz ts : trainList) {
                        long smzId = Long.parseLong(ts.getId());
                        slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(ts.getTrainId()), "TrainYC");
                        if (slList.size() == 0) throw new RuntimeException("实名制接口培训附件所属培训信息为空");
                        ProjectTraining chpt = this.baseMapper.selectById(slList.get(0).getLwtId());
                        if (chpt == null) throw new RuntimeException("实名制接口培训附件所属培训信息对象为空");
                        String path = "";
                        //保存附件
                        try {
                            if (ts.getAttentPath() != null) {
                                String newName = UUID.randomUUID().toString().replace("-", "") + "." + ts.getHzm();
                                FTPClientUtil.uploadFile(new ByteArrayInputStream(Base64.decode(ts.getAttentPath())),
                                        new String((newName).getBytes("utf-8"), "iso-8859-1"), connectFtp);
                                path = storeFilePath + newName;
                            } else {
                                throw new RuntimeException("实名制接口培训附件为空");
                            }
                        } catch (IOException e) {
                            log.error(e.getMessage());
                            throw new RuntimeException("实名制接口培训附件附件转换错误");
                        }
//                        Map<String, Object> m = new HashMap<>();
//                        m.put("relevanceId", chpt.getId());
//                        m.put("fileName", ts.getAttentName());
//                        m.put("path", path);
//                        m.put("type", "buss_project_training");
//                        fileInfoMapper.insertFileInfo(m);
    	                FileInfo fileInfo = new FileInfo(chpt.getId(),ts.getAttentName(),path,Constant.BUSS_PROJECT_TRAINING);
    		            fileInfoService.inserFileInfo(fileInfo);
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
                //响应保存成功信息
                HttpClientUtils.post(getSystemStringParam("httpUrlPrefixYC")
                        + getSystemStringParam("saveTrainAttachmentFromSMZResponseYC"), m, myc);
            }
        }
    }

    @Override
    public Boolean getProjectTrainFromLabor(List<Long> ids, Map<String, String> m) {
        List<ProjectTrainMo> list = baseMapper.getProjectTrainFromLabor(ids);
        if(list.size() > 0) {
        	 Map<String, Object> map = new HashMap<String, Object>();
             String json = JSONArray.fromObject(list).toString();
             map.put("lablorTrain", json);
             String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefix") + getSystemStringParam("saveTrainFromLabor"), map, m);
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

    @Value("${spring.mvc.view.imageLocalPath}")
    private String imageLocalPath;
    @Override
    public Boolean getProjectTrainFileFromLabor(List<Long> ids, Map<String, String> m) {
        List<ProjectTrainFileMo> list = baseMapper.getProjectTrainFileFromLabor(ids);
        if(list.size() > 0) {
            for (ProjectTrainFileMo p : list) {
                String pPath = p.getAttement();
                try {
                    p.setAttement(ImageUtil.GetImageStr(imageLocalPath + pPath));
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
            Map<String, Object> map = new HashMap<String, Object>();
            String json = JSONArray.fromObject(list).toString();
            map.put("lablorTrainAttement", json);
            String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefix") + getSystemStringParam("saveTrainFileFromLabor"), map, m);
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
    public Boolean getProjectTrainWorkerFromLabor(List<Long> ids, Map<String, String> m) {
        List<ProjectTrainWorkerMo> list = baseMapper.getProjectTrainWorkerFromLabor(ids);
        if(list.size() > 0) {
        	 Map<String, Object> map = new HashMap<String, Object>();
             String json = JSONArray.fromObject(list).toString();
             map.put("lablorTrainWorker", json);
             String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefix") + getSystemStringParam("saveTrainWorkerFromLabor"), map, m);
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
    public void deleteByIds(Map<String, Object> map) {
        map.put("updateUser", ShiroKit.getUser().getName());
        baseMapper.deleteByIds(map);
    }

    @Override
	public PictureDto getAccessoryPicture(Long projectTrainId, String title, String tableName) {
		return null;
	}

	@Override
    @Transactional(readOnly = false)
    public int insertProjectTrain(ProjectTrainingVo projectTrainingVo) {
        //首先插培训主表
        ProjectTraining projectTraining = new ProjectTraining(projectTrainingVo.getProjectCode(),projectTrainingVo.getDescription(),projectTrainingVo.getTrainingName(),projectTrainingVo.getTrainingTime(),
                projectTrainingVo.getTrainingDuration(),projectTrainingVo.getTrainingTypeCode(),projectTrainingVo.getTrainer(),ShiroKit.getUser().getName(),new java.sql.Date(new java.util.Date().getTime()));
        baseMapper.insertProjectTrain(projectTraining);
        IfaLabor ifaLabor = new IfaLabor("buss_project_training", projectTraining.getId());
        ifaLaborService.insert(ifaLabor);
        if (projectTrainingVo.getFilePath() != null) {
            IfaLabor ifaLaborFileInfo = new IfaLabor("buss_project_training_file", projectTraining.getId());
            ifaLaborService.insert(ifaLaborFileInfo);
        }
        IfaLabor ifaLaborPerson = new IfaLabor("buss_project_training_person", projectTraining.getId());
        ifaLaborService.insert(ifaLaborPerson);
        //然后插培训详情表
        List<Map<String, Object>> list = projectTrainingVo.getDetailList();
        projectTrainRecordMapper.insertProjectRecord(list, projectTraining.getId());
        if (projectTrainingVo.getFilePath() != null) {
            //插fileinfo表
//            Map<String, Object> map = new HashMap<>();
//            map.put("relevanceId", projectTraining.getId());
//            map.put("fileName", projectTrainingVo.getFileName());
//            map.put("path", projectTrainingVo.getFilePath());
//            map.put("type", "buss_project_training");
//            fileInfoMapper.insertFileInfo(map);
            FileInfo fileInfo = new FileInfo(projectTraining.getId(),projectTrainingVo.getFileName(),projectTrainingVo.getFilePath(),Constant.BUSS_PROJECT_TRAINING);
            fileInfoService.inserFileInfo(fileInfo);
        }
        return 0;
    }

    @Override
    public List<Map<String, Object>> list(Page page, Map<String, Object> map) {
        map.put("deptId", ShiroKit.getUser().getDeptId());
        map.put("projectCodes", accountProjectService.getProjectCodes());
        return baseMapper.list(page, map , accountProjectService.getSwitchType());
    }

    @Override
    public List<Map<String, Object>> getTrainRecordList(Integer idCardType, String idCardNumber, Page page) {
        return projectTrainRecordMapper.getTrainRecordList(idCardType, idCardNumber, page);
    }

    @Override
    public ProjectTrainingVo getOneById(Integer id) {
        return baseMapper.getOneById(id);
    }

    @Override
    public List<Map<String, Object>> getWorkerList(Integer id, Page page) {
        return baseMapper.getWorkerList(id, page);
    }

	@Override
	public List<Map<String, Object>> getProjectFileList(Long id) {
		return baseMapper.getProjectFileList(id);
	}

	/**
	 * 
	 * @description 获取实名制培训信息（南通）
	 * @author chupp
	 * @date 2018年7月26日
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void saveTrainFromSMZNT(Map<String, String> mnt) {
		//获取实名制培训数据
        String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")
                + getSystemStringParam("saveTrainFromSMZ"), new HashMap<String, Object>(), mnt);
        Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
        //存在新的培训信息
        if (map.get("train") != null) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("train"));
            List<TrainSmz> trainList = JSONArray.toList(jsonArray, new TrainSmz(), new JsonConfig());
            List<SmzLwt> slList = null;
            String id = "0";
            if (trainList.size() > 0) {
                for (TrainSmz ts : trainList) {
                    long smzId = Long.parseLong(ts.getId());
                    ProjectTraining pt = new ProjectTraining();
                    slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(ts.getProjectId()), "Project");
                    if (slList.size() == 0) throw new RuntimeException("实名制接口培训所属项目信息为空");
                    Map<String, Object> chProject = projectMasterMapper.selectById(slList.get(0).getLwtId());
                    if (chProject == null) throw new RuntimeException("实名制接口培训所属项目对象为空");
                    pt.setProjectCode((String) chProject.get("projectCode"));
                    if (ts.getTrainDate() != null) {
                        pt.setTrainingTime(java.sql.Date.valueOf(ts.getTrainDate().substring(0, 10)));
                    } else {
                        pt.setTrainingTime(new java.sql.Date(System.currentTimeMillis()));
                    }
                    if (ts.getTrainDuration() != null) {
                        pt.setTrainingDuration(Integer.parseInt(ts.getTrainDuration()));
                    } else {
                        pt.setTrainingDuration(0);
                    }
                    if (ts.getTrainTypeCode() != null) {
                        pt.setTrainingTypeCode("" + Integer.parseInt(ts.getTrainTypeCode()));
                    } else {
                        pt.setTrainingTypeCode("3001");
                    }
                    pt.setTrainingName(ts.getTrainName());
                    pt.setTrainer(ts.getTrainner());
                    pt.setDescription(ts.getTrainContent());
                    pt.setIsDel(0);
                    //保存实名制培训数据
                    this.baseMapper.insert(pt);
                    //保存主键关联表
                    long lwtId = pt.getId();
                    SmzLwt sl = new SmzLwt();
                    sl.setSmzId(smzId);
                    sl.setLwtId(lwtId);
                    sl.setTableName("Train");
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
                        + getSystemStringParam("saveTrainFromSMZResponse"), m, mnt);
            }
        }
	}

	/**
	 * 
	 * @description 获取实名制培训附件信息（南通）
	 * @author chupp
	 * @date 2018年7月26日
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void saveTrainAttachmentFromSMZNT(Map<String, String> mnt) {
		//获取实名制培训附件数据
        String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")
                + getSystemStringParam("saveTrainAttachmentFromSMZ"), new HashMap<String, Object>(), mnt);
        Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
        //存在新的培训附件信息
        if (map.get("train") != null) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("train"));
            List<TrainSmz> trainList = JSONArray.toList(jsonArray, new TrainSmz(), new JsonConfig());
            List<SmzLwt> slList = null;
            String id = "0";
            if (trainList.size() > 0) {
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
                    for (TrainSmz ts : trainList) {
                        long smzId = Long.parseLong(ts.getId());
                        slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(ts.getTrainId()), "Train");
                        if (slList.size() == 0) throw new RuntimeException("实名制接口培训附件所属培训信息为空");
                        ProjectTraining chpt = this.baseMapper.selectById(slList.get(0).getLwtId());
                        if (chpt == null) throw new RuntimeException("实名制接口培训附件所属培训信息对象为空");
                        String path = "";
                        //保存附件
                        try {
                            if (ts.getAttentPath() != null) {
                                String newName = UUID.randomUUID().toString().replace("-", "") + "." + ts.getHzm();
                                FTPClientUtil.uploadFile(new ByteArrayInputStream(Base64.decode(ts.getAttentPath())),
                                        new String((newName).getBytes("utf-8"), "iso-8859-1"), connectFtp);
                                path = storeFilePath + newName;
                            } else {
                                throw new RuntimeException("实名制接口培训附件为空");
                            }
                        } catch (IOException e) {
                            log.error(e.getMessage());
                            throw new RuntimeException("实名制接口培训附件附件转换错误");
                        }
//                        Map<String, Object> m = new HashMap<>();
//                        m.put("relevanceId", chpt.getId());
//                        m.put("fileName", ts.getAttentName());
//                        m.put("path", path);
//                        m.put("type", "buss_project_training");
//                        fileInfoMapper.insertFileInfo(m);
                        FileInfo fileInfo = new FileInfo(chpt.getId(), ts.getAttentName(),path,Constant.BUSS_PROJECT_TRAINING);
                        fileInfoService.inserFileInfo(fileInfo);
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
                //响应保存成功信息
                HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")
                        + getSystemStringParam("saveTrainAttachmentFromSMZResponse"), m, mnt);
            }
        }
	}

	@Override
	public Object upload(MultipartFile[] files, String settlementCodeId) {
		  String fileType = "pdf,jpg,pdf,png,doc,docx,bmp,jpeg,gif,xls,xlsx,txt";
	        //允许上传的文件最大大小(10M,单位为byte)
	        int maxSize = 10 * 1024 * 1024;

//	        List<FileInfo> fileInfoList = new ArrayList<>();
	        Map<String, String> resMap = null;
	        for (MultipartFile f : files) {
	            //重新定义上传路径和文件名称,保存到FTP 远程静态服务器上,返回基础文件地址
	            resMap = uploadService.fileHandle(f,fileType,maxSize);
	            if (resMap.containsKey("path")) {
	            	Long id = Long.parseLong(settlementCodeId);
	                FileInfo fileInfo = new FileInfo(id,f.getOriginalFilename(),resMap.get("path"),Constant.BUSS_PROJECT_TRAINING);
//	                fileInfoList.add(fileInfo);
	                fileInfoService.inserFileInfo(fileInfo);
	                //同步到同步表中
                    IfaLabor ifaLaborFileInfo = new IfaLabor(Constant.BUSS_PROJECT_TRAINING_FILE, fileInfo.getId().longValue());
                    ifaLaborService.insert(ifaLaborFileInfo);
	            } else {
	                break;
	            }
	        }
//	        if(fileInfoList.size()>0){
//	        	fileInfoService.insertPatch(fileInfoList);
//	        }
	        return resMap;
	}

}
