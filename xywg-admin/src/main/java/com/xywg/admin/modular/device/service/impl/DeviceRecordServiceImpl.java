package com.xywg.admin.modular.device.service.impl;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.SocketException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.xywg.admin.core.util.DateUtil;
import com.xywg.admin.core.util.ImageUtils;
import com.xywg.admin.core.util.PoiUtil;
import com.xywg.admin.core.util.SMSUtils;
import com.xywg.admin.core.util.SmzUtils;
import com.xywg.admin.modular.report.service.IDeviceReportService;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.org.bouncycastle.crypto.RuntimeCryptoException;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.config.handler.RemoteDTO;
import com.xywg.admin.config.handler.ZbusHandler;
import com.xywg.admin.core.common.exception.BizExceptionEnum;
import com.xywg.admin.core.excel.ExcelUtils;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.shiro.ShiroUser;
import com.xywg.admin.core.util.FTPUtils;
import com.xywg.admin.modular.company.dao.SubContractorMapper;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.company.utils.ServletsUtils;
import com.xywg.admin.modular.device.dao.DeviceMapper;
import com.xywg.admin.modular.device.dao.DeviceRecordMapper;
import com.xywg.admin.modular.device.model.DeviceRecord;
import com.xywg.admin.modular.device.model.DeviceRecordExcel;
import com.xywg.admin.modular.device.model.dto.DeviceRecordDto;
import com.xywg.admin.modular.device.model.dto.DeviceRecordDto2;
import com.xywg.admin.modular.device.service.IDeviceRecordService;
import com.xywg.admin.modular.device.utils.MathRadius;
import com.xywg.admin.modular.device.utils.RedisUtil;
import com.xywg.admin.modular.project.dao.ProjectMasterMapper;
import com.xywg.admin.modular.project.dao.ProjectWorkerMapper;
import com.xywg.admin.modular.project.model.AppProjectListByPersonVo;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.model.ProjectWorker;
import com.xywg.admin.modular.project.model.vo.ProjectPositionVo;
import com.xywg.admin.modular.project.service.IProjectWorkerService;
import com.xywg.admin.modular.projectSubContractor.dao.ProjectSubContractorMapper;
import com.xywg.admin.modular.projectSubContractor.model.ProjectSubContractor;
import com.xywg.admin.modular.report.dao.DeviceReportMapper;
import com.xywg.admin.modular.report.model.DeviceReport;
import com.xywg.admin.modular.smz.dao.SmzLwtMapper;
import com.xywg.admin.modular.smz.model.DeviceRecordMo;
import com.xywg.admin.modular.smz.model.DeviceRecordSmz;
import com.xywg.admin.modular.smz.model.IfaLabor;
import com.xywg.admin.modular.smz.model.SmzCallBack;
import com.xywg.admin.modular.smz.model.SmzLwt;
import com.xywg.admin.modular.smz.service.IfaLaborService;
import com.xywg.admin.modular.smz.utils.Base64;
import com.xywg.admin.modular.smz.utils.Constant;
import com.xywg.admin.modular.smz.utils.DateUtils;
import com.xywg.admin.modular.smz.utils.FTPClientUtil;
import com.xywg.admin.modular.smz.utils.FileUtil;
import com.xywg.admin.modular.smz.utils.HttpClientUtils;
import com.xywg.admin.modular.smz.utils.ImageUtil;
import com.xywg.admin.modular.system.dao.DeptMapper;
import com.xywg.admin.modular.system.dao.DictMapper;
import com.xywg.admin.modular.system.service.AccountProjectService;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.team.dao.TeamMasterMapper;
import com.xywg.admin.modular.team.model.TeamMaster;
import com.xywg.admin.modular.worker.dao.WorkerMasterMapper;
import com.xywg.admin.modular.worker.model.WorkerMaster;

import cn.hutool.json.JSONUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * <p>
 * 考勤记录表 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-30
 */
@Service
public class DeviceRecordServiceImpl extends ServiceImpl<DeviceRecordMapper, DeviceRecord>
		implements IDeviceRecordService {
	private static Logger log = LoggerFactory.getLogger(DeviceRecordServiceImpl.class);
	@Resource
	protected ProjectMasterMapper projectMasterMapper;

	@Resource
	protected WorkerMasterMapper workerMasterMapper;

	@Resource
	protected ProjectWorkerMapper projectWorkerMapper;

	@Resource
	protected DeviceRecordMapper deviceRecordMapper;

	@Resource
	protected DeviceReportMapper deviceReportMapper;

	@Resource
	protected DeviceMapper deviceMapper;

	@Resource
	private DeptMapper deptMapper;

	@Autowired
	private DictMapper dictMapper;

	@Autowired
    private IDeptService deptService;
	@Autowired
	private IProjectWorkerService projectWorkerService;
	@Autowired
	private ProjectSubContractorMapper projectSubContractorMapper;

	@Autowired
	private SubContractorMapper subContractorMapper;

	@Autowired
	private SmzLwtMapper smzLwtMapper;

	@Autowired
	private IfaLaborService ifaLaborService;

	@Autowired
	private AccountProjectService accountProjectService;
	@Autowired
	private IDeviceReportService deviceReportService;
	
	@Autowired
    public RedisUtil redisUtil;

	private static final Log LOG = LogFactory.getLog(DeviceRecordServiceImpl.class);
	private static Properties systemParams = new Properties();

	@Autowired
	private TeamMasterMapper teamMasterMapper;

	@Value("${spring.mvc.view.imageHost:http://localhost:8030/labor/}")
	private String iconPrev;


	/**
	 * 加载配置文件
	 */
	static {
		try {
			systemParams.load(DeviceRecordServiceImpl.class.getClassLoader().getResourceAsStream("smz.properties"));
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

	// /**
	// *
	// * @description 登录实名制获取token
	// * @author chupp
	// * @date 2018年4月27日
	// * @return
	// *
	// */
	// @SuppressWarnings("unchecked")
	// protected Map<String,String> loginSMZ() {
	// String httpUrlPrefix = this.getSystemStringParam("httpUrlPrefix");
	// String loginUrl = this.getSystemStringParam("loginUrl");
	// String userName = this.getSystemStringParam("userName");
	// String password = this.getSystemStringParam("password");
	// Map<String,Object> map = new HashMap<>();
	// map.put("userName", userName);
	// map.put("password", password);
	// String result = HttpClientUtils.post(httpUrlPrefix + loginUrl, map);
	// Map<String,Object> m = (Map<String,Object>)
	// net.sf.json.JSONObject.toBean(net.sf.json.JSONObject.fromObject(result),
	// Map.class);
	// Map<String,String> headers = new HashMap<>();
	// headers.put("token", (String) m.get("token"));
	// return headers;
	// }
	//
	// /**
	// *
	// * @description 登录实名制获取token
	// * @author chupp
	// * @date 2018年4月27日
	// * @return
	// *
	// */
	// @SuppressWarnings("unchecked")
	// protected Map<String,String> loginSMZYC() {
	// String httpUrlPrefix = this.getSystemStringParam("httpUrlPrefixYC");
	// String loginUrl = this.getSystemStringParam("loginUrlYC");
	// String userName = this.getSystemStringParam("userNameYC");
	// String password = this.getSystemStringParam("passwordYC");
	// Map<String,Object> map = new HashMap<>();
	// map.put("userName", userName);
	// map.put("password", password);
	// String result = HttpClientUtils.post(httpUrlPrefix + loginUrl, map);
	// Map<String,Object> m = (Map<String,Object>)
	// net.sf.json.JSONObject.toBean(net.sf.json.JSONObject.fromObject(result),
	// Map.class);
	// Map<String,String> headers = new HashMap<>();
	// headers.put("token", (String) m.get("token"));
	// return headers;
	// }

	/**
	 * 
	 * @description 获取实名制考勤数据（盐城）
	 * @author chupp
	 * @date 2018年7月5日
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void saveDeviceRecordFromSMZYC(Map<String, String> myc) {
		// 获取实名制考勤数据
		String jsonResult = HttpClientUtils.post(
				getSystemStringParam("httpUrlPrefixYC") + getSystemStringParam("saveDeviceRecordFromSMZYC"),
				new HashMap<String, Object>(), myc);
		Map<String, Object> map = (Map<String, Object>) net.sf.json.JSONObject
				.toBean(net.sf.json.JSONObject.fromObject(jsonResult), Map.class);
		// 存在新的考勤数据
		if (map.get("record") != null) {
			JSONArray jsonArray = JSONArray.fromObject(map.get("record"));
			List<DeviceRecordSmz> drsList = JSONArray.toList(jsonArray, new DeviceRecordSmz(), new JsonConfig());
			String id = "0";
			List<SmzLwt> slList = smzLwtMapper.getLwtIdBySmzId(0, "Company");
			if (slList.size() == 0)
				throw new RuntimeException("实名制接口企业数据为空");
			SubContractor subContractor = subContractorMapper.selectById(slList.get(0).getLwtId());
			if (subContractor == null)
				throw new RuntimeException("实名制接口企业数据对象为空");
			if (drsList.size() > 0) {
				// 获取FTP连接
				Random ra = new Random();
				String ran = "" + ra.nextInt(10) + ra.nextInt(10);
				String storeFilePath = "lwtgb_smz" + FileUtil.SEPARATOR + DateUtils.getCurrentDate("yyyy")
						+ FileUtil.SEPARATOR + DateUtils.getCurrentDate("MM") + FileUtil.SEPARATOR
						+ DateUtils.getCurrentDate("dd") + FileUtil.SEPARATOR + DateUtils.getCurrentDate("HH")
						+ FileUtil.SEPARATOR + ran + FileUtil.SEPARATOR;
				String iconStoreFilePath = "icon" + FileUtil.SEPARATOR + "lwtgb_smz" + FileUtil.SEPARATOR
						+ DateUtils.getCurrentDate("yyyy") + FileUtil.SEPARATOR + DateUtils.getCurrentDate("MM")
						+ FileUtil.SEPARATOR + DateUtils.getCurrentDate("dd") + FileUtil.SEPARATOR
						+ DateUtils.getCurrentDate("HH") + FileUtil.SEPARATOR + ran + FileUtil.SEPARATOR;
				FTPClient connectFtp = null;
				FTPClient iconConnectFtp = null;
				try {
					connectFtp = FTPClientUtil.connectFtp(Constant.FTP_HOST, Constant.FTP_PORT, Constant.FTP_USERNAME,
							Constant.FTP_PASS_WORD, storeFilePath);
					iconConnectFtp = FTPClientUtil.connectFtp(Constant.FTP_HOST, Constant.FTP_PORT,
							Constant.FTP_USERNAME, Constant.FTP_PASS_WORD, iconStoreFilePath);
				} catch (SocketException e) {
					log.error(e.getMessage());
					throw new RuntimeException("实名制接口考勤数据FTP连接错误");
				} catch (IOException e) {
					log.error(e.getMessage());
					throw new RuntimeException("实名制接口考勤数据FTP连接错误");
				}
				try {
					for (DeviceRecordSmz drs : drsList) {
						long smzId = Long.parseLong(drs.getId());
						DeviceRecord dr = new DeviceRecord();
						dr.setOrganizationCode(subContractor.getOrganizationCode());
						slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(drs.getProjectId()), "ProjectYC");
						if (slList.size() == 0)
							throw new RuntimeException("实名制接口考勤记录所属项目为空");
						Map<String, Object> chProject = projectMasterMapper.selectById(slList.get(0).getLwtId());
						if (chProject == null)
							throw new RuntimeException("实名制接口考勤记录所属项目对象为空");
						dr.setProjectCode((String) chProject.get("projectCode"));
						dr.setIdCardType(1);
						slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(drs.getUserId()), "PersonnelYC");
						if (slList.size() == 0)
							throw new RuntimeException("实名制接口考勤记录人员为空");
						WorkerMaster wm = workerMasterMapper.selectById(slList.get(0).getLwtId());
						if (wm == null)
							throw new RuntimeException("实名制接口考勤记录人员对象为空");
						dr.setIdCardNumber(wm.getIdCardNumber());
						List<Map<String, Object>> chProjectWorker = projectWorkerMapper
								.getForFaceUse((String) chProject.get("projectCode"), 1, wm.getIdCardNumber());
						if (chProjectWorker.size() == 0)
							throw new RuntimeException("实名制接口考勤记录人员项目关系为空");
						if (chProjectWorker.get(0).get("team_sys_no") != null)
							dr.setTeamSysNo((int) chProjectWorker.get(0).get("team_sys_no"));
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						try {
							dr.setTime(sdf.parse(drs.getTime().replace("T", " ")));
						} catch (ParseException e) {
							log.error(e.getMessage());
							throw new RuntimeCryptoException("实名制接口考勤时间转换错误");
						}
						dr.setType("00".equals(drs.getType()) ? 1 : 0);
						dr.setSource(4);
						dr.setIsValid(1);
						dr.setDeviceType(3);
						dr.setIsDeal("0");
						// 保存考勤图片
						FileInputStream fileInputStream = null;
						try {
							if (drs.getPhoto() != null) {
								String newName = UUID.randomUUID().toString().replace("-", "") + ".jpg";
								FTPClientUtil.uploadFile(new ByteArrayInputStream(Base64.decode(drs.getPhoto())),
										new String((newName).getBytes("utf-8"), "iso-8859-1"), connectFtp);
								String removeFilePath = FTPUtils.saveFileFromInputStream(
										new ByteArrayInputStream(Base64.decode(drs.getPhoto())),
										FileUtil.SEPARATOR + iconStoreFilePath, newName);
								FTPUtils.zoomImage(new ByteArrayInputStream(Base64.decode(drs.getPhoto())),
										removeFilePath, 200, 200);
								fileInputStream = new FileInputStream(removeFilePath);
								FTPClientUtil.uploadFile(fileInputStream,
										new String((newName).getBytes("utf-8"), "iso-8859-1"), iconConnectFtp);
								dr.setPhoto(FileUtil.SEPARATOR + storeFilePath + newName);
								dr.setIconPhoto(FileUtil.SEPARATOR + iconStoreFilePath + newName);
							} else {
								dr.setPhoto(null);
								dr.setIconPhoto(null);
							}
						} catch (Exception e) {
							log.error(e.getMessage());
							throw new RuntimeException("实名制接口考勤图片转换错误");
						} finally {
							if (fileInputStream != null) {
								try {
									fileInputStream.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									log.error(e.getMessage());
								}
								fileInputStream = null;
							}
						}
						// 保存考勤数据
						this.baseMapper.insert(dr);
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
					if (iconConnectFtp != null && iconConnectFtp.isConnected()) {
						try {
							iconConnectFtp.logout();
						} catch (IOException e) {
							log.error(e.getMessage());
							iconConnectFtp = null;
						}
					}
				}
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("code", "1");
				m.put("no", id);
				// 响应保存成功信息
				HttpClientUtils.post(getSystemStringParam("httpUrlPrefixYC")
						+ getSystemStringParam("saveDeviceRecordFromSMZResponseYC"), m, myc);
			}
		}
	}

	/**
	 * 
	 * @description 手机考勤(考勤第三接口)
	 * @author chupp
	 * @date 2018年5月30日
	 * @param deviceRecord
	 * @see com.xywg.admin.modular.device.service.IDeviceRecordService#record(com.xywg.admin.modular.device.model.DeviceRecord)
	 *
	 */
	@Override
	@Transactional
	public void record(DeviceRecord deviceRecord) throws IOException {
		String now = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		// ProjectMaster pm =
		// projectMasterMapper.getProjectByProjectCode(deviceRecord.getProjectCode());
		// if (pm == null || pm.getLat() == null || pm.getLng() == null ||
		// pm.getRadius() == null) {
		// throw new XywgException(BizExceptionEnum.LAT_LNG_ERROR);
		// }
		boolean flag = false;
		List<ProjectPositionVo> positionList = projectMasterMapper
				.getProjectPositionList(deviceRecord.getProjectCode());
		if (positionList.size() == 0) {
			throw new XywgException(BizExceptionEnum.PROJECT_INFO_NOT_OK);
		}
		// 考勤范围检验
		for (ProjectPositionVo ppv : positionList) {
			flag = MathRadius.isRecord(deviceRecord.getLat(), deviceRecord.getLng(), ppv.getLat(), ppv.getLng(),
					ppv.getRadius());
			if (flag)
				break;
		}
		// 人员项目关系检验
		List<ProjectWorker> projectWorkerList = projectWorkerMapper.isInProject(deviceRecord.getProjectCode(),
				deviceRecord.getIdCardType(), deviceRecord.getIdCardNumber());
		if (projectWorkerList.size() == 0) {
			throw new XywgException(BizExceptionEnum.NOT_JOIN_PROJECT);
		}
		ProjectWorker projectWorker = projectWorkerList.get(0);
		// List<ProjectWorker> projectWorkerList =
		// projectWorkerMapper.getProjectWorkerList(
		// deviceRecord.getIdCardNumber(), deviceRecord.getIdCardType().toString());
		// ProjectWorker projectWorker = null;
		// for (ProjectWorker pw : projectWorkerList) {
		// if (pw.getProjectCode().equals(deviceRecord.getProjectCode())) {
		// projectWorker = pw;
		// break;
		// }
		// }
		// if (projectWorker == null) {
		// throw new XywgException(BizExceptionEnum.NO_JOIN_PROJECT);
		// }
		if (projectWorker.getJoinStatus() == null || 2 != projectWorker.getJoinStatus()) {
			throw new XywgException(BizExceptionEnum.NO_JOIN);
		}
		deviceRecord.setTeamSysNo(projectWorker.getTeamSysNo());
		deviceRecord.setIsValid(flag ? 1 : 0);
		deviceRecord.setOrganizationCode(projectWorker.getOrganizationCode());
		deviceRecord.setTime(new Date());
		deviceRecord.setType(1);
		deviceRecord.setSource(deviceRecord.getSource());
		// 判断是否要增加水印
		// 根据项目里面的人员身份证号找到人员的姓名
		Long id = null;
		String workerName = this.deviceRecordMapper.queryUserNameByParam(id, projectWorker.getIdCardNumber());
		// 增加工人姓名
		deviceRecord.setWorkerName(workerName);
		deviceRecord.setIconPhoto("/icon/" + deviceRecord.getPhoto());
		// 判断是否要增加水印
		ProjectMaster p = projectMasterMapper.getProjectByProjectCode(deviceRecord.getProjectCode());
		if (p.getIsWatermark().intValue() == 1) {
			// 添加水印
			FTPClient ftpClient = com.xywg.admin.modular.faceUtils.FTPClientUtil.connectFtp();
			String fileDir = deviceRecord.getPhoto().substring(0, deviceRecord.getPhoto().lastIndexOf("/") + 1);
			if (ftpClient.changeWorkingDirectory(fileDir)) {
				String fileName = deviceRecord.getPhoto().substring(deviceRecord.getPhoto().lastIndexOf("/") + 1,
						deviceRecord.getPhoto().length());
				InputStream is = ftpClient.retrieveFileStream(fileName);
				// 图片压缩的demo ImageUtils.compressImage(is, file.getSize());
				// 图片加水印的demo
				byte[] b = ImageUtils.addWatermark(is, now, null, 0.7f);
				is.close();
				ftpClient.completePendingCommand();
				FTPClientUtil.uploadFile(new ByteArrayInputStream(b),
						new String((fileName).getBytes("utf-8"), "iso-8859-1"), ftpClient);
			}
		}
		this.baseMapper.insert(deviceRecord);

		sendToZbus(deviceRecord);
	}

	private void sendToZbus(DeviceRecord deviceRecord) {
		//完成调用zbus推送给智慧工地
		ProjectMaster projectMaster = projectMasterMapper.getProjectByProjectCode(deviceRecord.getProjectCode());
		if(projectMaster.getIsSynchro().intValue() == 1) {
			WorkerMaster workerMaster = workerMasterMapper.getWorkerByIdCardNumber(deviceRecord.getIdCardNumber());
			ProjectWorker pw = projectWorkerMapper.getProjectWorkerByUserInfo(deviceRecord.getProjectCode(),
					workerMaster.getIdCardType(), workerMaster.getIdCardNumber());
			TeamMaster teamMaster = teamMasterMapper.getTeamInfoByTeamSysNo(deviceRecord.getTeamSysNo());
			Map<String, Object> map = new HashMap<>();
			map.put("relationProjectId", projectMaster.getId());
			map.put("deviceNo", "");
			map.put("relationUserId", "");
			map.put("name", workerMaster.getWorkerName());
			map.put("identityCode", deviceRecord.getIdCardNumber());
			map.put("nativePlace", workerMaster.getBirthPlaceCode());
			map.put("workType", dictMapper.selectNameByCode("工种字典数据", Integer.parseInt(workerMaster.getWorkTypeCode())));
			map.put("team", teamMaster.getTeamName());
			map.put("attendanceTime", deviceRecord.getRecordTime());
			map.put("platform", 2);
			RemoteDTO dto = new RemoteDTO();
			Map<String, Object> mm = new HashMap<>();
			mm.put("record", map);
			mm.put("operationFlag", "insertExternalAttendanceDetail");
			dto.setBody(mm);
			ZbusHandler.send(JSONObject.fromObject(dto).toString());
		}
	}

	/**
	 * 
	 * @description 查询工人某天考勤列表
	 * @author chupp
	 * @date 2018年5月31日
	 * @param deviceRecord
	 * @param nowDate
	 * @return
	 * @see com.xywg.admin.modular.device.service.IDeviceRecordService#getDayRecord(com.xywg.admin.modular.device.model.DeviceRecord)
	 *
	 */
	@Override
	public List<DeviceRecordDto> getDayRecord(DeviceRecord deviceRecord, String nowDate) {
		return this.baseMapper.getDayRecord(deviceRecord, nowDate);
	}

	/**
	 *
	 * @description 查询工人某天考勤列表
	 * @author cw
	 * @date 2018年5月31日
	 * @param deviceRecord
	 * @param nowDate
	 * @return
	 * @see com.xywg.admin.modular.device.service.IDeviceRecordService#v116GetDayRecord(com.xywg.admin.modular.device.model.DeviceRecord)
	 *
	 */
	@Override
	public List<DeviceRecordDto> v116GetDayRecord(DeviceRecordDto2 deviceRecord, String nowDate) {
		deviceRecord.setIndex((deviceRecord.getPageNo() - 1) * deviceRecord.getPageSize());
		return this.baseMapper.v116GetDayRecord(deviceRecord, nowDate);
	}

	/**
	 * 
	 * @description 查询工人历史考勤记录
	 * @author chupp
	 * @date 2018年5月31日
	 * @param deviceRecord
	 * @return
	 * @see com.xywg.admin.modular.device.service.IDeviceRecordService#getRecordsByAll(com.xywg.admin.modular.device.model.DeviceRecord)
	 *
	 */
	@Override
	public List<DeviceRecordDto> getRecordsByAll(DeviceRecord deviceRecord) {
		return this.baseMapper.getRecordsByAll(deviceRecord);
	}

	/**
	 * 
	 * @description 获取某人在项目下总共出勤天数
	 * @author chupp
	 * @date 2018年5月31日
	 * @param deviceRecord
	 * @return
	 * @see com.xywg.admin.modular.device.service.IDeviceRecordService#getAllRecordByProject(com.xywg.admin.modular.device.model.DeviceRecord)
	 *
	 */
	@Override
	public Integer getAllRecordByProject(DeviceRecord deviceRecord) {
		return this.baseMapper.getAllRecordByProject(deviceRecord);
	}

	/**
	 * 
	 * @description 查询工人在某个项目里的月考勤列表
	 * @author chupp
	 * @date 2018年5月31日
	 * @param deviceRecord
	 * @param month
	 * @return
	 * @see com.xywg.admin.modular.device.service.IDeviceRecordService#getMonthRecords(com.xywg.admin.modular.device.model.DeviceRecord,
	 *      java.lang.String)
	 *
	 */
	@Override
	public List<String> getMonthRecords(DeviceRecord deviceRecord, String month) {
		return this.baseMapper.getMonthRecords(deviceRecord, month);
	}

	/**
	 *
	 * @description 查询工人在某个项目里的月考勤列表
	 * @author cw
	 * @date 2018年9月17日
	 * @param deviceRecord
	 * @param month
	 * @return
	 * @see com.xywg.admin.modular.device.service.IDeviceRecordService#v116GetMonthRecords(com.xywg.admin.modular.device.model.DeviceRecord,
	 *      java.lang.String)
	 *
	 */
	@Override
	public List<String> v116GetMonthRecords(DeviceRecordDto2 deviceRecord, String month) {
		deviceRecord.setIndex((deviceRecord.getPageNo() - 1) * deviceRecord.getPageSize());
		return this.baseMapper.v116GetMonthRecords(deviceRecord, month);
	}

	/**
	 * 
	 * @description 获取公司的某项目的今日考勤人数
	 * @author chupp
	 * @date 2018年5月31日
	 * @param deviceRecord
	 * @return
	 * @see com.xywg.admin.modular.device.service.IDeviceRecordService#getWorkerTotalCount(com.xywg.admin.modular.device.model.DeviceRecord)
	 *
	 */
	@Override
	public Integer getWorkerTotalCount(DeviceRecord deviceRecord) {
		return this.baseMapper.getWorkerTotalCountReport(deviceRecord, accountProjectService.getSwitchType(),
				ShiroKit.getUser() != null ? ShiroKit.getUser().getDeptId() : null);
	}

	/**
	 * 
	 * @description 根据考勤记录ID更新是否有效
	 * @author chupp
	 * @date 2018年6月4日
	 * @param id
	 * @param flag
	 * @see com.xywg.admin.modular.device.service.IDeviceRecordService#updateRecordById(java.lang.Long,
	 *      int)
	 *
	 */
	@Override
	@Transactional
	public void updateRecordById(Long id, int flag) {
		this.baseMapper.updateRecordById(id, flag);
	}

	/**
	 * 
	 * @description 获取日期区间考勤天数
	 * @author chupp
	 * @date 2018年6月4日
	 * @param workerList
	 * @param projectCode
	 * @param endDate
	 * @return
	 * @see com.xywg.admin.modular.device.service.IDeviceRecordService#getDaySectionRecord(java.util.List,
	 *      java.lang.String, java.lang.String)
	 *
	 */
	@Override
	public List<DeviceRecordDto> getDaySectionRecord(List<Map<String, String>> workerList, String projectCode,
			String endDate) {
		return this.baseMapper.getDaySectionRecord(workerList, projectCode, endDate);
	}

	/**
	 * 
	 * @description 获取公司的某项目的最近7天出勤人数
	 * @author chupp
	 * @date 2018年6月4日
	 * @param organizationCode
	 * @param projectCode
	 * @return
	 * @see com.xywg.admin.modular.device.service.IDeviceRecordService#getTotalNumFromSevenDays(java.lang.String,
	 *      java.lang.String)
	 *
	 */
	@Override
	public List<DeviceRecordDto> getTotalNumFromSevenDays(String organizationCode, String projectCode) {
		List<String> ocList = deptService.getAPPUserDeptAndSubdivisionOrganizationCode(organizationCode);
		List<ProjectSubContractor> list = projectSubContractorMapper
				.getListByProjectCodeAndOrganizationCodeFromCompany(projectCode, ocList);
		if (list.size() == 0) {
			throw new XywgException(BizExceptionEnum.NOT_JOIN_PROJECT);
		} else {
			boolean flag = false;
			for (ProjectSubContractor psc : list) {
				if (psc.getContractorType() == 16) {
					flag = true;
				}
			}
			if (flag) {
				return this.baseMapper.getTotalNumFromSevenDays(null, projectCode);
			} else {
				return this.baseMapper.getTotalNumFromSevenDaysFromCompany(ocList, projectCode);
			}
		}
	}

	/**
	 * 
	 * @description 获取班组人员考勤记录
	 * @author chupp
	 * @date 2018年6月4日
	 * @param idCardType
	 * @param idCardNumber
	 * @param teamMemeber
	 * @return
	 * @see com.xywg.admin.modular.device.service.IDeviceRecordService#getByClass(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 *
	 */
	@Override
	public List<DeviceRecordDto> getByClass(String idCardType, String idCardNumber, String teamMemeber) {
		return this.baseMapper.getByClass(idCardType, idCardNumber, teamMemeber);
	}

	/**
	 * 
	 * @description 获取班组指定人员历史考勤记录
	 * @author chupp
	 * @date 2018年6月4日
	 * @param idCardType
	 * @param idCardNumber
	 * @param teamMemeberIdCardType
	 * @param teamMemeberIdCardNumber
	 * @return
	 * @see com.xywg.admin.modular.device.service.IDeviceRecordService#getByClassMemeber(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 *
	 */
	@Override
	public List<DeviceRecordDto> getByClassMemeber(String idCardType, String idCardNumber, String teamMemeberIdCardType,
			String teamMemeberIdCardNumber) {
		return this.baseMapper.getByClassMemeber(idCardType, idCardNumber, teamMemeberIdCardType,
				teamMemeberIdCardNumber);
	}

	@Override
	public List<AppProjectListByPersonVo> getProjectsByUserId(Integer userId) {
		return deviceRecordMapper.getProjectsByUserId(userId);
	}

	@Override
	public int saveDevice(DeviceRecordDto record) {
		return this.deviceRecordMapper.saveDevice(record);
	}

	/**
	 * 
	 * @description 查询工人某天考勤信息
	 * @author chupp
	 * @date 2018年6月20日
	 * @param id
	 * @param nowDate
	 * @return
	 * @see com.xywg.admin.modular.device.service.IDeviceRecordService#getDayRecords(java.lang.Long,
	 *      java.lang.String)
	 *
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> getDayRecords(Long id, String nowDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		DeviceReport dr = deviceReportMapper.selectById(id);
		map.put("nowDate", dr.getTime() + "-" + nowDate);
		map.put("workerName", dr.getWorkerName());
		map.put("idCardType", dictMapper.selectNameByCode("人员证件类型", dr.getIdCardType()));
		map.put("idCardNumber", dr.getIdCardNumber());
		Class clazz = DeviceReport.class;
		Method method;
		try {
			method = clazz.getDeclaredMethod("getDay" + nowDate);
			Double d = (Double) method.invoke(dr);
			map.put("totalTime", d > 0 ? d : 0.0);
		} catch (Exception e) {
			map.put("totalTime", 0.0);
		}
		DeviceRecord deviceRecord = new DeviceRecord();
		deviceRecord.setIdCardType(dr.getIdCardType());
		deviceRecord.setIdCardNumber(dr.getIdCardNumber());
		deviceRecord.setProjectCode(dr.getProjectCode());
		List<DeviceRecordDto> drList = this.getDayRecord(deviceRecord, dr.getTime() + "-" + nowDate);
		if (drList.size() > 0) {
			map.put("endTime", drList.get(0).getTime().substring(11, 19));
			map.put("startTime", drList.get(drList.size() - 1).getTime().substring(11, 19));
		} else {
			map.put("endTime", "");
			map.put("startTime", "");
		}
		for (DeviceRecordDto drd : drList) {
			drd.setTime(drd.getTime().substring(0, drd.getTime().length() - 2));
		}
		map.put("deviceRecordList", drList);
		return map;
	}

	//分表后查询考勤数据
	@Override
	public List<Map<String, Object>> selectList(Page page, Map map) {
		map.put("deptId", ShiroKit.getUser().getDeptId());
		if (null == map.get("startDate") || map.get("startDate").equals("")
				|| map.get("startDate").toString().equals("") && null == map.get("endDate")) {
			Date date = new Date();
			map.put("startDate", DateUtil.getBeforeDayDate("-30") + " 00:00:00");
			map.put("endDate", DateUtil.format(date, "yyyy-MM-dd") + " 23:59:59");
		}

		// 根据现有sql，执行两条sql效率要高于一条
		if (accountProjectService.getSwitchType().getSwitchType() == null
				|| accountProjectService.getSwitchType().getSwitchType().intValue() == 0
				|| accountProjectService.getSwitchType().getIsGeneralContractor() == null
				|| accountProjectService.getSwitchType().getIsGeneralContractor().intValue() == 1) {
			List<String> list = deviceRecordMapper.findOrgCodes(Integer.parseInt(map.get("deptId").toString()));
			map.put("orgList", list);
		}

		// 由于涉及分表，需要特殊处理
		String startDate = map.get("startDate").toString();
		String endDate = map.get("endDate").toString();
		// ==>1、by startDate + endDate 计算出涉及到的分表后缀
		int total = 0;
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		int limit = page.getSize();
		int start = page.getCurrent() <= 1 ? 0 : (page.getCurrent() - 1) * limit;

		boolean flag = true;
		try {
			List<String> suffixArr = getTableSuffixByDate(startDate, endDate);
			Collections.reverse(suffixArr);
			List<Map<String, Object>> suffixParams = new ArrayList<Map<String, Object>>();
			for (String suffix : suffixArr) {
				// by表后缀算出每个表的 count + offset + limit
				map.put("tableSuffix", suffix);
				int maxRow = deviceRecordMapper.listCount(map, accountProjectService.getSwitchType());

				total += maxRow;
				if (flag) {
					if (start > maxRow) {
						start = start - maxRow;
					} else {
						// 判断需不需要查下个分表
						if ((maxRow - start) >= limit) {
							Map<String, Object> suffixMap = new HashMap<String, Object>();
							suffixMap.put("tableSuffix", suffix);
							suffixMap.put("start", start);
							suffixMap.put("limit", limit);
							suffixParams.add(suffixMap);
							flag = false;
						} else {
							Map<String, Object> suffixMap = new HashMap<String, Object>();
							suffixMap.put("tableSuffix", suffix);
							suffixMap.put("start", start);
							suffixMap.put("limit", (maxRow - start));
							suffixParams.add(suffixMap);
							limit = limit - (maxRow - start);
							start = 0;
						}
					}
				}
				// map.put("pageSize", page.getSize());
				// map.put("startIndex", page.getCurrent() <= 1 ? 0 : (page.getCurrent() - 1) *
				// 10);

			}

			for (Map<String, Object> m : suffixParams) {
				String tableSuffix = m.get("tableSuffix").toString();
				int startI = Integer.parseInt(m.get("start").toString());
				int limitI = Integer.parseInt(m.get("limit").toString());
				map.put("tableSuffix", tableSuffix);map.put("pageSize", limitI);
				map.put("startIndex", startI);
				map.put("iconPrev",iconPrev);
				List<Map<String, Object>> suffixRet = deviceRecordMapper.getList(map,accountProjectService.getSwitchType(),page);
				ret.addAll(suffixRet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		page.setTotal(total);
		return ret;
	}


	public List<String> getTableSuffixByDate(String startDate, String endDate) throws Exception {
		List<String> tableSuffixArr = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date start = null;
		Date end = null;
		if (StringUtils.isEmpty(startDate)) {
			start = new Date();
		} else {
			start = sdf.parse(startDate);
		}

		if (StringUtils.isEmpty(endDate)) {
			end = new Date();
		} else {
			end = sdf.parse(endDate);
		}

		Calendar from = Calendar.getInstance();
		from.setTime(start);
		Calendar to = Calendar.getInstance();
		to.setTime(end);

		int fromYear = from.get(Calendar.YEAR);
		int fromMonth = from.get(Calendar.MONTH) + 1;

		int toYear = to.get(Calendar.YEAR);
		int toMonth = to.get(Calendar.MONTH) + 1;

		if (fromYear == toYear) {
			tableSuffixArr.add(fromYear + "_" + intToString(fromMonth, 2));
			// 同年
			int countMonth = toMonth - fromMonth;
			while (countMonth > 0) {
				fromMonth += 1;
				tableSuffixArr.add(fromYear + "_" + intToString(fromMonth, 2));
				countMonth--;
			}
		} else {
			// 不是同一年toYear > fromYear
			// 开始年份
			while (fromMonth < 13) {
				tableSuffixArr.add(fromYear + "_" + intToString(fromMonth, 2));
				fromMonth++;
			}
			// 中间年份
			int countYear = toYear - fromYear;
			while (countYear > 1) {
				int nowYear = fromYear + 1;
				int months = 1;
				while (months < 13) {
					tableSuffixArr.add(nowYear + "_" + intToString(months, 2));
					months++;
				}
				countYear--;
			}

			int months = 0;
			// 结束年份
			while (toMonth > 0) {
				months = months + 1;
				tableSuffixArr.add(toYear + "_" + intToString(months, 2));
				toMonth--;
			}
		}
		return tableSuffixArr;
	}

	public static String intToString(int n, int l) {
		DecimalFormat decimalFormat = new DecimalFormat(initString('0', l));
		return decimalFormat.format(n);
	}

	public static String initString(char ch, int length) {
		if (length < 0)
			return "";
		char chars[] = new char[length];
		for (int i = 0; i < length; i++)
			chars[i] = ch;
		return new String(chars);
	}


	@Override
	public int saveDeviceRecord(DeviceRecord deviceRecord) {
		// 根据人员的id或者身份证号找出当前人的姓名
		String IdCardNumber = null;
		String workerName = this.deviceRecordMapper.queryUserNameByParam(deviceRecord.getId(), IdCardNumber);
		deviceRecord.setWorkerName(workerName);
		deviceRecord.setSource(7);
		deviceRecord.setType(0);
		deviceRecord.setIsValid(1);
		deviceRecord.setIsDeal("0");
		ShiroUser user = ShiroKit.getUser();
		deviceRecord.setCreateUser(user.getName());
		int flag = this.deviceRecordMapper.insertRepair(deviceRecord);
		WorkerMaster workerMaster = workerMasterMapper.selectById(deviceRecord.getId());
		deviceRecord.setIdCardNumber(workerMaster.getIdCardNumber());
		deviceRecord.setIdCardType(workerMaster.getIdCardType());
		sendToZbus(deviceRecord);
		return flag;
	}

	/**
	 * 
	 * @description 获取今日考勤人数
	 * @author chupp
	 * @date 2018年6月21日
	 * @return
	 * @see com.xywg.admin.modular.device.service.IDeviceRecordService#getTotalDevice()
	 *
	 */
	@Override
	public int getTotalDevice() {
		Integer deptId = ShiroKit.getUser().getDeptId();
		if (deptId == null || deptId == 0) {
			return -1;
		}
		List<String> list = deptService.getOrganizationCodeByDeptId(deptId);
		return this.baseMapper.getTotalDevice(list, new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
				accountProjectService.getProjectCodes());
	}

	/**
	 * 
	 * @description 获取项目当天考勤情况
	 * @author chupp
	 * @date 2018年6月22日
	 * @return
	 * @see com.xywg.admin.modular.device.service.IDeviceRecordService#getProjectDevice()
	 *
	 */
	@Override
	public List<DeviceRecordDto> getProjectDevice() {
		Integer deptId = ShiroKit.getUser().getDeptId();
		if (deptId == null || deptId == 0) {
			return new ArrayList<DeviceRecordDto>();
		} else {
			List<String> list = deptService.getOrganizationCodeByDeptId(deptId);
			return this.baseMapper.getProjectDevice(list, new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
					accountProjectService.getProjectCodes());
		}
	}

	@Override
	public Map<String, Object> findById(Long id,String tableName) {
		Map<String, Object> map = new HashMap<>();
		map.put("tableSuffix",tableName);
		map.put("id",id);
		return this.deviceRecordMapper.findById(map);
	}

	/**
	 * 
	 * @description 获取公司的某项目的今日考勤人数(手机端)
	 * @author chupp
	 * @date 2018年6月28日
	 * @param deviceRecord
	 * @return
	 * @see com.xywg.admin.modular.device.service.IDeviceRecordService#getWorkerTotalCountFromPhone(com.xywg.admin.modular.device.model.DeviceRecord)
	 *
	 */
	@Override
	public Integer getWorkerTotalCountFromPhone(DeviceRecord deviceRecord) {
		List<String> ocList = deptService
				.getAPPUserDeptAndSubdivisionOrganizationCode(deviceRecord.getOrganizationCode());
		List<ProjectSubContractor> list = projectSubContractorMapper
				.getListByProjectCodeAndOrganizationCodeFromCompany(deviceRecord.getProjectCode(), ocList);
		if (list.size() == 0) {
			return 0;
		} else {
			boolean flag = false;
			for (ProjectSubContractor psc : list) {
				if (psc.getContractorType() == 16)
					flag = true;
			}
			if (flag) {
				deviceRecord.setOrganizationCode(null);
				return this.baseMapper.getWorkerTotalCount(deviceRecord);
			} else {
				return this.baseMapper.getWorkerTotalCountFromCompany(ocList, deviceRecord.getProjectCode());
			}
		}
	}

	/**
	 * 
	 * @description 项目考勤详情
	 * @author chupp
	 * @date 2018年7月9日
	 * @param page
	 * @param map
	 * @return
	 * @see com.xywg.admin.modular.device.service.IDeviceRecordService#getDeviceRecordByProjectCode(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getDeviceRecordByProjectCode(Page page, Map map) {
		map.put("deptId", ShiroKit.getUser().getDeptId());
		return deviceRecordMapper.getDeviceRecordByProjectCode(page, map);
	}


	@Transactional
	public boolean getDeviceRecordFromLabor(Long lastNumber, Map<String, String> m) throws Exception {
		List<DeviceRecordMo> list = deviceRecordMapper.getDeviceRecordFromLabor(lastNumber);
		// 调用发送解析代码
		String jsonResult = sendDeviceRecordToSmz(list, m);
		// 返回数据解析
		if (StringUtils.isNotBlank(jsonResult)) {
			// 返回数据解析
			Map<String, Object> result = JSONUtil.toBean(jsonResult, Map.class);
			// 获取返回的最大值
			if (result.containsKey("record")) {
				ifaLaborService.updateNumber(Long.valueOf(result.get("record").toString()),
						Constant.BUSS_DEVICE_RECORD);
			}
			if ("-2".equals(result.get("code"))) {
				return false;
			} else {
				return true;
			}
		}
		// }else {
		return false;
		// }
	}

	
//	@Override
//	@Transactional
//	public boolean getDeviceRecordFromLabor(int lastNumber, Map<String, String> m) throws Exception {
//		Object id = redisUtil.get("jinlan_deviceRecord");
//		int deviceId = id == null ? 0 : Integer.valueOf(String.valueOf(id));
//		List<DeviceRecordMo> list = deviceRecordMapper.getDeviceRecordFromLabor(deviceId);
//		// 调用发送解析代码
//		String jsonResult = sendDeviceRecordToSmz(list, m);
//		// 返回数据解析
//		if (StringUtils.isNotBlank(jsonResult)) {
//			// 返回数据解析
//			Map<String, Object> result = JSONUtil.toBean(jsonResult, Map.class);
//			// 获取返回的最大值
//			if (result.containsKey("record")) {
//				redisUtil.set("jinlan_deviceRecord", result.get("record"));
////				ifaLaborService.updateNumber(Long.valueOf(result.get("record").toString()),
////						Constant.BUSS_DEVICE_RECORD);
//			}
//			if ("-2".equals(result.get("code"))) {
//				return false;
//			} else {
//				return true;
//			}
//		}
//		// }else {
//		return false;
//		// }
//	}
	
	/**
	 * 发送单个考勤记录
	 * 
	 * @param lastNumber
	 * @param m
	 * @return boolean
	 * @throws Exception
	 */
	@Transactional
	public boolean getDeviceRecordFromLaborToSMZ(Long id) {
		// 查询发送的数据
		List<DeviceRecordMo> list = deviceRecordMapper.getDeviceRecordByIdToSmz(id);
		// 登录实名制
		Map<String, String> login = SmzUtils.loginSMZ();
		// 调用发送解析代码
		String jsonResult = sendDeviceRecordToSmz(list, login);

		if (StringUtils.isNotBlank(jsonResult)) {
			// 返回数据解析
			Map<String, Object> result = JSONUtil.toBean(jsonResult, Map.class);
			if ("-2".equals(result.get("code"))) {
				return false;
			} else {
				return true;
			}
		}
		return false;

	}
	

	/**
	 * 抽取发送实名制代码
	 * <p>
	 * Title: sendDeviceRecordToSmz
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @author duanfen
	 * @date 2019年6月24日
	 */
	@Transactional
	public String sendDeviceRecordToSmz(List<DeviceRecordMo> list, Map<String, String> m) {
		String jsonResult = null;
		if (list.size() > 0) {
/*			for (DeviceRecordMo d : list) {
				if (d.getDeviceId() == null && d.getProjectCode() != null && !d.getProjectCode().isEmpty()) {
					ProjectMaster pm = projectMasterMapper.getProjectByProjectCode(d.getProjectCode());
					if (pm != null && pm.getVirtualId() != null) {
						Long chLong = pm.getVirtualId();
						d.setDeviceId(chLong);
						deviceRecordMapper.updateSnList(d.getProjectCode(), chLong);
					}
				}

			}*/
			Map<String, Object> map = new HashMap<String, Object>();
			String json = JSONArray.fromObject(list).toString();
			map.put("lablorRecord", json);
			jsonResult = HttpClientUtils.post(getSystemStringParam(Constant.HTTP_URL_PREFIX)
					+ getSystemStringParam(Constant.SAVE_DEVICE_RECORD_FROM_LABOR), map, m);
			System.out.println(jsonResult);
		} else {
			return null;
		}
		return jsonResult;
	}


//	@Override
//	@Transactional
//	public boolean getDeviceRecordImageFromLabor(int lastNumber, Map<String, String> m) throws Exception {
//		List<DeviceRecordMo> list = deviceRecordMapper.getDeviceRecordImageFromLabor(lastNumber);
//        for(DeviceRecordMo d:list){
//        	String photo=d.getPhoto();
//        	if(StringUtils.isNotBlank(photo)) {
//        		d.setPhoto(ImageUtil.GetImageStr(imageLocalPath+photo));
//        	}
//        }
//		if (list.size() > 0) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			//发送给实名制数据转json
//			String json = JSONArray.fromObject(list).toString();
//			map.put("lablorRecord", json);
//			//发送信息
//			String jsonResult = HttpClientUtils.post(getSystemStringParam(Constant.HTTP_URL_PREFIX_IMAGE)
//					+ getSystemStringParam("saveDeviceRecordImage"), map, m);
// 			System.out.println(jsonResult);
//			//判断是否有返回
//			if(StringUtils.isNotBlank(jsonResult)) {
//				//解析返回数据
//				Map<String, Object> result = JSONUtil.toBean(jsonResult, Map.class);
//				// 拿到考勤ID,调用发送接口，重新发送考勤记录
//				if (jsonResult.contains("deviceRecordId")) {
//					// 获取返回的考勤编号
//					Long deviceRecordId = SmzUtils.textToLong(jsonResult, result);
//					getDeviceRecordFromLaborToSMZ(deviceRecordId);
//				}
//
//				if(result.containsKey("record")){
//				
//					//获取返回的最大值
//					Long numId = Long.valueOf(result.get("record").toString());
//		        	List<Long> deviceRecordList=ifaLaborService.getIdList(Constant.BUSS_DEVICE_RECORD_IMAGE);
//		        	//修改数据库最大值
//		        	if(deviceRecordList.size() > 0) {
//		        		ifaLaborService.updateNumber(Long.valueOf(result.get("record").toString()),Constant.BUSS_DEVICE_RECORD_IMAGE);
//		        	}else {
//		        		ifaLaborService.insert(new IfaLabor(Constant.BUSS_DEVICE_RECORD_IMAGE, numId));
//		        	}
//				}
//				if ("-2".equals(result.get("code"))) {
//					return false;
//				} else {
//					return true;
//				}
//			}
//        }
//		return false;
//	}

	
/*	@Override
	@Transactional
	public boolean getDeviceRecordImageFromLabor(Long lastNumber, Map<String, String> m) throws Exception {
		Object id = redisUtil.get("jinlan_deviceRecord_image");
		int deviceId = id == null ? 0 : Integer.valueOf(String.valueOf(id));
		List<DeviceRecordMo> list = deviceRecordMapper.getDeviceRecordImageFromLabor(deviceId);
        for(DeviceRecordMo d:list){
        	String photo=d.getPhoto();
        	if(StringUtils.isNotBlank(photo)) {
        		d.setPhoto(ImageUtil.GetImageStr(imageLocalPath+photo));
        	}
        }
		if (list.size() > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			//发送给实名制数据转json
			String json = JSONArray.fromObject(list).toString();
			map.put("lablorRecord", json);
			//发送信息
			String jsonResult = HttpClientUtils.post(getSystemStringParam(Constant.HTTP_URL_PREFIX_IMAGE)
					+ getSystemStringParam("saveDeviceRecordImage"), map, m);
 			System.out.println(jsonResult);
			//判断是否有返回
			if(StringUtils.isNotBlank(jsonResult)) {
				//解析返回数据
				Map<String, Object> result = JSONUtil.toBean(jsonResult, Map.class);
				// 拿到考勤ID,调用发送接口，重新发送考勤记录
				if (jsonResult.contains("deviceRecordId")) {
					// 获取返回的考勤编号
					Long deviceRecordId = SmzUtils.textToLong(jsonResult, result);
					getDeviceRecordFromLaborToSMZ(deviceRecordId);
				}
				if(result.containsKey("record")){
					//获取返回的最大值
					Long numId = Long.valueOf(result.get("record").toString());
					redisUtil.set("jinlan_deviceRecord_image", numId);
//		        	List<Long> deviceRecordList=ifaLaborService.getIdList(Constant.BUSS_DEVICE_RECORD_IMAGE);
//		        	//修改数据库最大值
//		        	if(deviceRecordList.size() > 0) {
//		        		ifaLaborService.updateNumber(Long.valueOf(result.get("record").toString()),Constant.BUSS_DEVICE_RECORD_IMAGE);
//		        	}else {
//		        		ifaLaborService.insert(new IfaLabor(Constant.BUSS_DEVICE_RECORD_IMAGE, numId));
//		        	}
				}
				if ("-2".equals(result.get("code"))) {
					return false;
				} else {
					return true;
				}
			}
        }
		return false;
	}*/


	@Override
	public void overTime(DeviceReport deviceReport) {
		if (null == deviceReport.getOverTime()) {
			deviceReport.setOverTime(0d);
		}
		baseMapper.overTime(deviceReport);
		deviceReportService.countTotalTime(deviceReport);
	}

	@Override
	public void setData(DeviceReport deviceReport) {
		if (null == deviceReport.getOverTime()) {
			deviceReport.setOverTime(0d);
		}
		baseMapper.setData(deviceReport);
		deviceReportService.countTotalTime(deviceReport);
	}

	@Value("${spring.mvc.view.imageLocalPath}")
	private String imageLocalPath;

	@Override
	public boolean getDeviceRecordFromLaborYC(int lastNumber, Map<String, String> m) throws Exception {
		List<DeviceRecordMo> list = deviceRecordMapper.getDeviceRecordFromLaborYC(lastNumber);
		for (DeviceRecordMo d : list) {
			String photo = d.getPhoto();
			d.setPhoto(ImageUtil.GetImageStr(imageLocalPath + photo));
		}
		if (list.size() > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			String json = JSONArray.fromObject(list).toString();
			map.put("laborRecord", json);
			String jsonResult = HttpClientUtils.post(
					getSystemStringParam("httpUrlPrefixYC") + getSystemStringParam("saveDeviceRecordFromLaborYC"), map,
					m);
			System.out.println(jsonResult);
			Map<String, Object> result = JSONUtil.toBean(jsonResult, Map.class);
			ifaLaborService.updateNumber(Long.valueOf(result.get("record").toString()), "buss_device_record_yc");
			if ("-2".equals(result.get("code"))) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @description 获取实名制考勤数据（南通）
	 * @author chupp
	 * @date 2018年7月26日
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void saveDeviceRecordFromSMZNT(Map<String, String> mnt, List<SubContractor> list) {
		// 获取实名制考勤数据
		String jsonResult = HttpClientUtils.post(
				getSystemStringParam("httpUrlPrefix") + getSystemStringParam("saveDeviceRecordFromSMZ"),
				new HashMap<String, Object>(), mnt);
		Map<String, Object> map = (Map<String, Object>) net.sf.json.JSONObject
				.toBean(net.sf.json.JSONObject.fromObject(jsonResult), Map.class);
		// 存在新的考勤数据
		if (map.get("record") != null) {
			JSONArray jsonArray = JSONArray.fromObject(map.get("record"));
			List<DeviceRecordSmz> drsList = JSONArray.toList(jsonArray, new DeviceRecordSmz(), new JsonConfig());
			String id = "0";
			SubContractor subContractor = null;
			List<SmzLwt> slList = null;
			if (drsList.size() > 0) {
				for (DeviceRecordSmz drs : drsList) {
					if (drs.getComId().equals("0")) {
						subContractor = list.get(0);
					} else if (drs.getComId().equals("00")) {
						subContractor = list.get(1);
					} else if (drs.getComId().equals("000")) {
						subContractor = list.get(2);
					}
					long smzId = Long.parseLong(drs.getId());
					DeviceRecord dr = new DeviceRecord();
					dr.setOrganizationCode(subContractor.getOrganizationCode());
					slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(drs.getProjectId()), "Project");
					if (slList.size() == 0 && slList == null) {
						throw new RuntimeException("实名制接口考勤记录所属项目为空");
					}else {
						Long lwtId= slList.get(0).getLwtId();
						System.err.println("lwt_id = "+lwtId);
						Map<String, Object> chProject = projectMasterMapper.selectById(lwtId);
						if (chProject == null) {
							throw new RuntimeException("实名制接口考勤记录所属项目对象为空");
						}else {
							dr.setProjectCode((String) chProject.get("projectCode"));
							dr.setIdCardType(1);
							slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(drs.getUserId()), "Personnel");
							if (slList.size() != 0  && slList != null) {
								WorkerMaster wm = workerMasterMapper.selectById(slList.get(0).getLwtId());
								if (wm == null) {
									throw new RuntimeException("实名制接口考勤记录人员对象为空");
								}else {
									dr.setIdCardNumber(wm.getIdCardNumber());
									List<Map<String, Object>> chProjectWorker = projectWorkerMapper
											.getForFaceUse((String) chProject.get("projectCode"), 1, wm.getIdCardNumber());
									if (chProjectWorker.size() == 0) {
										throw new RuntimeException("实名制接口考勤记录人员项目关系为空");
									}else {
										if (chProjectWorker.get(0).get("team_sys_no") != null)
											dr.setTeamSysNo((int) chProjectWorker.get(0).get("team_sys_no"));
										SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
										try {
											dr.setTime(sdf.parse(drs.getTime().replace("T", " ")));
										} catch (ParseException e) {
											log.error(e.getMessage());
											throw new RuntimeCryptoException("实名制接口考勤时间转换错误");
										}
										dr.setType("00".equals(drs.getType()) ? 1 : 0);
										dr.setSource(4);
										dr.setIsValid(1);
										dr.setDeviceType(3);
										dr.setIsDeal("0");
										// 保存考勤图片
										FileInputStream fileInputStream = null;
										dr.setPhoto(drs.getPhoto());
										// 保存考勤数据
										this.baseMapper.insert(dr);
										if (Long.parseLong(id) < smzId) {
											id = String.valueOf(smzId);
										}
									}
								}
							}
						}
					}
//							throw new RuntimeException("实名制接口考勤记录人员为空");
				}
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("code", "1");
				m.put("no", id);
				// 响应保存成功信息
				HttpClientUtils.post(
						getSystemStringParam("httpUrlPrefix") + getSystemStringParam("saveDeviceRecordFromSMZResponse"),
						m, mnt);
		      }
		}
	}

	@Override
	public List<DeviceRecordDto> getByClassMemeberV116(String idCardType, String idCardNumber,
			String teamMemeberIdCardType, String teamMemeberIdCardNumber, Integer pageSize, Integer pageNo) {
		return this.baseMapper.getByClassMemeberV116(idCardType, idCardNumber, teamMemeberIdCardType,
				teamMemeberIdCardNumber, pageSize, (pageNo - 1) * pageSize);
	}

	@Override
	public List<DeviceRecordDto> getByClassV116(String idCardType, String idCardNumber, String teamMemeber,
			Integer pageSize, Integer pageNo) {
		return this.baseMapper.getByClassV116(idCardType, idCardNumber, teamMemeber, pageSize, (pageNo - 1) * pageSize);
	}

	@Override
	public List<String> getMonthRecordsV116(DeviceRecordDto2 deviceRecord, String month, Integer pageSize,
			Integer pageNo) {
		return this.baseMapper.getMonthRecordsV116(deviceRecord, month, pageSize, (pageNo - 1) * pageSize);
	}

	@Override
	public List<DeviceRecordDto> getDayRecordV116(DeviceRecordDto2 deviceRecord, String nowDate, Integer pageSize,
			Integer pageNo) {
		return this.baseMapper.getDayRecordV116(deviceRecord, nowDate, pageSize, (pageNo - 1) * pageSize);
	}

	@Override
	public List<DeviceRecordDto> getRecordsByAllV116(DeviceRecordDto2 deviceRecord) {
		deviceRecord.setPageNo((deviceRecord.getPageNo() - 1) * deviceRecord.getPageSize());
		return this.baseMapper.getRecordsByAllV116(deviceRecord);
	}

	//分表后导出考勤数据
	@Override
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
		map.put("deptId", ShiroKit.getUser().getDeptId());
		map.put("projectCode", ShiroKit.getSessionAttr("toggleProjectCode"));

        // 由于涉及分表，需要特殊处理
        String startDate = map.get("startDate").toString();
        String endDate = map.get("endDate").toString();
        List<String> suffixArr = null;
        try {
            suffixArr = getTableSuffixByDate(startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.reverse(suffixArr);
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (String suffix : suffixArr) {
            // by表后缀算出每个表的 count + offset + limit
            map.put("tableSuffix", suffix);
            List<Map<String, Object>> r = this.baseMapper.getExportExcel(map,
                    accountProjectService.getSwitchType());
            resultList.addAll(r);
        }
		// 构建下载文件时的文件名
		String fileName = "考勤列表" + DateUtils.getDate("yyyyMMddHHmmss");
		boolean isMSIE = ServletsUtils.isMSBrowser(request);
		// BufferedInputStream bis = null;
		OutputStream os = null;
		try {
			if (isMSIE) {
				// IE浏览器的乱码问题解决
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} else {
				// 万能乱码问题解决
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"+.xlsx");
			os = response.getOutputStream();
			ExcelUtils.getInstance().exportObjects2Excel(resultList, DeviceRecordExcel.class, true, os);
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			// if (bis != null) {
			// try {
			// bis.close();
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			// }
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void saveDeviceRecordFromSMZTY(Map<String, String> mnt) {
		List<String> registerNo = new ArrayList<String>();
		registerNo = this.projectMasterMapper.getRegisterNo();
		for (int j = 0; j < registerNo.size(); j++) {
			// 获取实名制考勤数据
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			// 查找已经存在的最大值
			Long maxId = this.smzLwtMapper.getProjectMaxId(registerNo.get(j));
			if (maxId == null) {
				maxId = Long.valueOf(0);
			}
			List<SmzCallBack> scbList = new ArrayList<SmzCallBack>();
			// 获取实名制项目数据
			List<String> projectId = this.projectMasterMapper.getProjectId(maxId, registerNo.get(j));
			for (int n = 0; n < projectId.size(); n++) {
				SmzCallBack scb = new SmzCallBack();
				hashMap.put("projectIdNew", projectId.get(n));
				String jsonResult = HttpClientUtils.post(
						getSystemStringParam("httpUrlPrefix") + getSystemStringParam("saveDeviceRecordFromSMZTY"),
						hashMap, mnt);
				Map<String, Object> map = (Map<String, Object>) net.sf.json.JSONObject
						.toBean(net.sf.json.JSONObject.fromObject(jsonResult), Map.class);
				// 存在新的考勤数据
				if (map.get("record") != null) {
					JSONArray jsonArray = JSONArray.fromObject(map.get("record"));
					List<DeviceRecordSmz> drsList = JSONArray.toList(jsonArray, new DeviceRecordSmz(),
							new JsonConfig());
					String id = "0";
					String subContractor = null;
					List<SmzLwt> slList = null;
					if (drsList.size() > 0) {
						// 获取FTP连接
						/*
						 * Random ra = new Random(); String ran = "" + ra.nextInt(10) + ra.nextInt(10);
						 * String storeFilePath = "lwtgb_smz" + FileUtil.SEPARATOR +
						 * DateUtils.getCurrentDate("yyyy") + FileUtil.SEPARATOR +
						 * DateUtils.getCurrentDate("MM") + FileUtil.SEPARATOR +
						 * DateUtils.getCurrentDate("dd") + FileUtil.SEPARATOR +
						 * DateUtils.getCurrentDate("HH") + FileUtil.SEPARATOR + ran +
						 * FileUtil.SEPARATOR; String iconStoreFilePath = "icon" + FileUtil.SEPARATOR +
						 * "lwtgb_smz" + FileUtil.SEPARATOR + DateUtils.getCurrentDate("yyyy") +
						 * FileUtil.SEPARATOR + DateUtils.getCurrentDate("MM") + FileUtil.SEPARATOR +
						 * DateUtils.getCurrentDate("dd") + FileUtil.SEPARATOR +
						 * DateUtils.getCurrentDate("HH") + FileUtil.SEPARATOR + ran +
						 * FileUtil.SEPARATOR; FTPClient connectFtp = null; FTPClient iconConnectFtp =
						 * null; try { connectFtp = FTPClientUtil.connectFtp(Constant.FTP_HOST,
						 * Constant.FTP_PORT, Constant.FTP_USERNAME, Constant.FTP_PASS_WORD,
						 * storeFilePath); iconConnectFtp = FTPClientUtil.connectFtp(Constant.FTP_HOST,
						 * Constant.FTP_PORT, Constant.FTP_USERNAME, Constant.FTP_PASS_WORD,
						 * iconStoreFilePath); } catch (SocketException e) { log.error(e.getMessage());
						 * throw new RuntimeException("实名制接口考勤数据FTP连接错误"); } catch (IOException e) {
						 * log.error(e.getMessage()); throw new RuntimeException("实名制接口考勤数据FTP连接错误"); }
						 */
						// try {
						for (DeviceRecordSmz drs : drsList) {

							long smzId = Long.parseLong(drs.getId());
							DeviceRecord dr = new DeviceRecord();
							slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(drs.getProjectId()), "Project");
							if (slList.size() == 0) {
								throw new RuntimeException("实名制接口考勤记录所属项目为空");
							} else {
								Map<String, Object> chProject = projectMasterMapper
										.selectById(slList.get(0).getLwtId());
								if (chProject == null) {
									throw new RuntimeException("实名制接口考勤记录所属项目对象为空");
								} else {
									// 根据项目id查找社会统一信用代码
									subContractor = teamMasterMapper.queryOrgnationCode(slList.get(0).getLwtId());
									dr.setOrganizationCode(subContractor);
									dr.setProjectCode((String) chProject.get("projectCode"));
									dr.setIdCardType(1);
									slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(drs.getUserId()), "PersonnelTY");
									//如果在personnelTY里面存在，检查意识在工人码表里面是不是存在值，不存在的话则重新
									/*WorkerMaster wm1 = new WorkerMaster();
									for(int m = 0;m<slList.size();m++){
										 wm1 = workerMasterMapper.selectById(slList.get(m).getLwtId());
										if(wm1 != null){
											break;
										}
									}
									if(wm1 == null){
										slList.clear();
									}*/
									//如果personnelTY下面没有关系 看personnel下面有没有
									if(slList.size() == 0){
										slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(drs.getUserId()), "Personnel");
									}
									//如果还是没有，则重新获取改人员的数据
									if(slList.size() == 0){
										//重新加人员数据
										this.projectWorkerService.saveProjectWorkerInformationNew(drs.getUserId(),drs.getProjectId(),mnt);
										throw new RuntimeException("已重新加载实名制接口考勤记录人员!");
//										LOG.error(drs.getUserId()+"idid");
//										 
									}else{
										WorkerMaster wm = new WorkerMaster();
										//如果查出两条相应的数据实名制的人员id，则查找两个是否都要对应的劳务通的id
										for(int i =0;i<slList.size();i++){
											 wm = workerMasterMapper.selectById(slList.get(i).getLwtId());
											if(wm != null){
												break;
											}
										}
										if(wm == null){
											throw new RuntimeException("实名制接口考勤记录人员对象为空");
											// LOG.error(slList.get(0).getLwtId()+"sidid");
										} else {
											dr.setIdCardNumber(wm.getIdCardNumber());
											dr.setWorkerName(drs.getWorkerName()); // 增加工人姓名
											List<Map<String, Object>> chProjectWorker = projectWorkerMapper
													.getForFaceUse((String) chProject.get("projectCode"), 1,
															wm.getIdCardNumber());
											if (chProjectWorker.size() == 0) {
												throw new RuntimeException("实名制接口考勤记录人员项目关系为空");
											} else {
												if (chProjectWorker.get(0).get("team_sys_no") != null)
													dr.setTeamSysNo((int) chProjectWorker.get(0).get("team_sys_no"));
												SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
												try {
													dr.setTime(sdf.parse(drs.getTime().replace("T", " ")));
												} catch (ParseException e) {
													log.error(e.getMessage());
													throw new RuntimeCryptoException("实名制接口考勤时间转换错误");
												}
												dr.setType("00".equals(drs.getType()) ? 1 : 0);
												dr.setSource(4);
												dr.setIsValid(1);
												dr.setDeviceType(3);
												dr.setIsDeal("0");
												dr.setPhoto(drs.getPhoto());
												dr.setCreateDate(new Date());
												dr.setCreateUser("smz");
												dr.setWorkerName(drs.getWorkerName());
												// 保存考勤图片
												/*
												 * FileInputStream fileInputStream = null; try { if(drs.getPhoto() !=
												 * null) { String newName = UUID.randomUUID().toString().replace("-",
												 * "") + ".jpg"; FTPClientUtil.uploadFile(new
												 * ByteArrayInputStream(Base64.decode(drs.getPhoto())), new
												 * String((newName).getBytes("utf-8"),"iso-8859-1"),connectFtp); String
												 * removeFilePath = FTPUtils.saveFileFromInputStream(new
												 * ByteArrayInputStream(Base64.decode(drs.getPhoto())),FileUtil.
												 * SEPARATOR + iconStoreFilePath,newName); FTPUtils.zoomImage(new
												 * ByteArrayInputStream(Base64.decode(drs.getPhoto())),removeFilePath,
												 * 200,200); fileInputStream = new FileInputStream(removeFilePath);
												 * FTPClientUtil.uploadFile(fileInputStream, new
												 * String((newName).getBytes("utf-8"),"iso-8859-1"),iconConnectFtp);
												 * dr.setPhoto(FileUtil.SEPARATOR + storeFilePath + newName);
												 * dr.setIconPhoto(FileUtil.SEPARATOR + iconStoreFilePath + newName);
												 * }else { dr.setPhoto(null); dr.setIconPhoto(null); } } catch(Exception
												 * e) { log.error(e.getMessage()); throw new
												 * RuntimeException("实名制接口考勤图片转换错误"); } finally { if(fileInputStream !=
												 * null) { try { fileInputStream.close(); } catch (IOException e) { //
												 * TODO Auto-generated catch block log.error(e.getMessage()); }
												 * fileInputStream = null; } }
												 */
												// 保存考勤数据
												this.baseMapper.insert(dr);
												if (Long.parseLong(id) < smzId) {
													id = String.valueOf(smzId);
												}
											}
										}

										/*
										 * } finally { if (connectFtp != null && connectFtp.isConnected()) { try {
										 * connectFtp.logout(); } catch (IOException e) { log.error(e.getMessage());
										 * connectFtp = null; } } if (iconConnectFtp != null &&
										 * iconConnectFtp.isConnected()) { try { iconConnectFtp.logout(); } catch
										 * (IOException e) { log.error(e.getMessage()); iconConnectFtp = null; } } }
										 */
									}

								}
							}
							scb.setMaxId(id);
							scb.setProjectId(projectId.get(n));
							scbList.add(scb);
						}
					}
				}
			}
			Map<String, Object> m = new HashMap<String, Object>();
			String json = JSONArray.fromObject(scbList).toString();
			m.put("code", "1");
			m.put("scbList", json);
			// 响应保存成功信息
			HttpClientUtils.post(
					getSystemStringParam("httpUrlPrefix") + getSystemStringParam("saveDeviceRecordFromSMZResponseTY"),
					m, mnt);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void saveOldDeviceRecordFromSMZTY(Map<String, String> mnt) {
		List<String> registerNo = new ArrayList<String>();
		registerNo = this.projectMasterMapper.getRegisterNo();
		for (int j = 0; j < registerNo.size(); j++) {
			// 获取实名制考勤数据
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			// 查找已经存在的最大值
			Long maxId = this.smzLwtMapper.getProjectMaxId(registerNo.get(j));
			if (maxId == null) {
				maxId = Long.valueOf(0);
			}
			List<SmzCallBack> scbList = new ArrayList<SmzCallBack>();
			// 获取实名制项目数据
			List<String> projectId = this.projectMasterMapper.getProjectIdNew(maxId, registerNo.get(j));
			for (int n = 0; n < projectId.size(); n++) {
				SmzCallBack scb = new SmzCallBack();
				hashMap.put("projectIdNew", projectId.get(n));
				String jsonResult = HttpClientUtils.post(
						getSystemStringParam("httpUrlPrefix") + getSystemStringParam("saveOldDeviceRecordFromSMZTY"),
						hashMap, mnt);
				Map<String, Object> map = (Map<String, Object>) net.sf.json.JSONObject
						.toBean(net.sf.json.JSONObject.fromObject(jsonResult), Map.class);
				// 存在新的考勤数据
				if (map.get("record") != null) {
					JSONArray jsonArray = JSONArray.fromObject(map.get("record"));
					List<DeviceRecordSmz> drsList = JSONArray.toList(jsonArray, new DeviceRecordSmz(),
							new JsonConfig());
					String id = "0";
					String subContractor = null;
					List<SmzLwt> slList = null;
					if (drsList.size() > 0) {
						// 获取FTP连接
						/*
						 * Random ra = new Random(); String ran = "" + ra.nextInt(10) + ra.nextInt(10);
						 * String storeFilePath = "lwtgb_smz" + FileUtil.SEPARATOR +
						 * DateUtils.getCurrentDate("yyyy") + FileUtil.SEPARATOR +
						 * DateUtils.getCurrentDate("MM") + FileUtil.SEPARATOR +
						 * DateUtils.getCurrentDate("dd") + FileUtil.SEPARATOR +
						 * DateUtils.getCurrentDate("HH") + FileUtil.SEPARATOR + ran +
						 * FileUtil.SEPARATOR; String iconStoreFilePath = "icon" + FileUtil.SEPARATOR +
						 * "lwtgb_smz" + FileUtil.SEPARATOR + DateUtils.getCurrentDate("yyyy") +
						 * FileUtil.SEPARATOR + DateUtils.getCurrentDate("MM") + FileUtil.SEPARATOR +
						 * DateUtils.getCurrentDate("dd") + FileUtil.SEPARATOR +
						 * DateUtils.getCurrentDate("HH") + FileUtil.SEPARATOR + ran +
						 * FileUtil.SEPARATOR; FTPClient connectFtp = null; FTPClient iconConnectFtp =
						 * null; try { connectFtp = FTPClientUtil.connectFtp(Constant.FTP_HOST,
						 * Constant.FTP_PORT, Constant.FTP_USERNAME, Constant.FTP_PASS_WORD,
						 * storeFilePath); iconConnectFtp = FTPClientUtil.connectFtp(Constant.FTP_HOST,
						 * Constant.FTP_PORT, Constant.FTP_USERNAME, Constant.FTP_PASS_WORD,
						 * iconStoreFilePath); } catch (SocketException e) { log.error(e.getMessage());
						 * throw new RuntimeException("实名制接口考勤数据FTP连接错误"); } catch (IOException e) {
						 * log.error(e.getMessage()); throw new RuntimeException("实名制接口考勤数据FTP连接错误"); }
						 */
						// try {
						for (DeviceRecordSmz drs : drsList) {

							long smzId = Long.parseLong(drs.getId());
							DeviceRecord dr = new DeviceRecord();
							slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(drs.getProjectId()), "Project");
							if (slList.size() == 0) {
								// throw new RuntimeException("实名制接口考勤记录所属项目为空");
							} else {
								Map<String, Object> chProject = projectMasterMapper
										.selectById(slList.get(0).getLwtId());
								if (chProject == null) {
									// throw new RuntimeException("实名制接口考勤记录所属项目对象为空");
								} else {
									// 根据项目id查找社会统一信用代码
									subContractor = teamMasterMapper.queryOrgnationCode(slList.get(0).getLwtId());
									dr.setOrganizationCode(subContractor);
									dr.setProjectCode((String) chProject.get("projectCode"));
									dr.setIdCardType(1);
									slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(drs.getUserId()),
											"PersonnelTY");
									if (slList.size() == 0) {
										// throw new RuntimeException("实名制接口考勤记录人员为空");
									} else {
										WorkerMaster wm = workerMasterMapper.selectById(slList.get(0).getLwtId());
										if (wm == null) {
											// throw new RuntimeException("实名制接口考勤记录人员对象为空");
										} else {
											dr.setIdCardNumber(wm.getIdCardNumber());
											dr.setWorkerName(drs.getWorkerName()); // 增加工人姓名
											List<Map<String, Object>> chProjectWorker = projectWorkerMapper
													.getForFaceUse((String) chProject.get("projectCode"), 1,
															wm.getIdCardNumber());
											if (chProjectWorker.size() == 0) {
												// throw new RuntimeException("实名制接口考勤记录人员项目关系为空");
											} else {
												if (chProjectWorker.get(0).get("team_sys_no") != null)
													dr.setTeamSysNo((int) chProjectWorker.get(0).get("team_sys_no"));
												SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
												try {
													dr.setTime(sdf.parse(drs.getTime().replace("T", " ")));
												} catch (ParseException e) {
													log.error(e.getMessage());
													throw new RuntimeCryptoException("实名制接口考勤时间转换错误");
												}
												dr.setType("00".equals(drs.getType()) ? 1 : 0);
												dr.setSource(4);
												dr.setIsValid(1);
												dr.setDeviceType(3);
												dr.setIsDeal("0");
												dr.setPhoto(drs.getPhoto());
												dr.setCreateDate(new Date());
												dr.setCreateUser("smz");
												dr.setWorkerName(drs.getWorkerName());
												// 保存考勤图片
												/*
												 * FileInputStream fileInputStream = null; try { if(drs.getPhoto() !=
												 * null) { String newName = UUID.randomUUID().toString().replace("-",
												 * "") + ".jpg"; FTPClientUtil.uploadFile(new
												 * ByteArrayInputStream(Base64.decode(drs.getPhoto())), new
												 * String((newName).getBytes("utf-8"),"iso-8859-1"),connectFtp); String
												 * removeFilePath = FTPUtils.saveFileFromInputStream(new
												 * ByteArrayInputStream(Base64.decode(drs.getPhoto())),FileUtil.
												 * SEPARATOR + iconStoreFilePath,newName); FTPUtils.zoomImage(new
												 * ByteArrayInputStream(Base64.decode(drs.getPhoto())),removeFilePath,
												 * 200,200); fileInputStream = new FileInputStream(removeFilePath);
												 * FTPClientUtil.uploadFile(fileInputStream, new
												 * String((newName).getBytes("utf-8"),"iso-8859-1"),iconConnectFtp);
												 * dr.setPhoto(FileUtil.SEPARATOR + storeFilePath + newName);
												 * dr.setIconPhoto(FileUtil.SEPARATOR + iconStoreFilePath + newName);
												 * }else { dr.setPhoto(null); dr.setIconPhoto(null); } } catch(Exception
												 * e) { log.error(e.getMessage()); throw new
												 * RuntimeException("实名制接口考勤图片转换错误"); } finally { if(fileInputStream !=
												 * null) { try { fileInputStream.close(); } catch (IOException e) { //
												 * TODO Auto-generated catch block log.error(e.getMessage()); }
												 * fileInputStream = null; } }
												 */
												// 保存考勤数据
												this.baseMapper.insert(dr);
												if (Long.parseLong(id) < smzId) {
													id = String.valueOf(smzId);
												}
											}
										}
										/*
										 * } finally { if (connectFtp != null && connectFtp.isConnected()) { try {
										 * connectFtp.logout(); } catch (IOException e) { log.error(e.getMessage());
										 * connectFtp = null; } } if (iconConnectFtp != null &&
										 * iconConnectFtp.isConnected()) { try { iconConnectFtp.logout(); } catch
										 * (IOException e) { log.error(e.getMessage()); iconConnectFtp = null; } } }
										 */
									}
								}
							}
							scb.setMaxId(id);
							scb.setProjectId(projectId.get(n));
							scbList.add(scb);
						}
					}
				}
			}
			Map<String, Object> m = new HashMap<String, Object>();
			String json = JSONArray.fromObject(scbList).toString();
			m.put("code", "1");
			m.put("scbList", json);
			// 响应保存成功信息
			HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")
					+ getSystemStringParam("saveOldDeviceRecordFromSMZResponseTY"), m, mnt);
		}
	}

	@Override
	public List<Map<String, Object>> getReport(Page page, Map<String, Object> params) {
		params.put("ocList", deptService.getOrganizationCodeByDeptId(ShiroKit.getUser().getDeptId()));
		return this.baseMapper.getReport(page, params);
	}

	@Override
	public void exportReport(Map<String, Object> params, HttpServletResponse response) {
		params.put("ocList", deptService.getOrganizationCodeByDeptId(ShiroKit.getUser().getDeptId()));
		List<Map<String, Object>> list = this.baseMapper.getReport(params);
		Map<String, String[]> columns = new HashMap<>();
		String[] keys = new String[] { "projectName", "teamName", "workerName", "idCardType", "idCardNumber",
				"searchMonth", "attendanceDay" };
		String[] values = new String[] { "项目", "班组", "工人姓名", "证件类型", "证件编号", "月份", "出勤天数" };
		columns.put("keys", keys);
		columns.put("values", values);
		try {
			PoiUtil.exportExcel("出勤天数统计", columns, list, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Map<String, Object>> getAllForTest(Map<String, Object> params) {
		return this.baseMapper.getAllForTest(params);
	}

	@Override
	public int getAllForTestCount(Map<String, Object> params) {
		return this.baseMapper.getAllForTestCount(params);
	}

	@Transactional
	@Override
	public void updatePic(List<Map<String, Object>> list) {
		this.baseMapper.updatePic(list);
	}


	@Override
	public void addDeviceRecord(List<Object> addList) {
		for (Object o : addList) {
			//DeviceRecord deviceRecord = new DeviceRecord();
			DeviceRecord deviceRecord = JSON.parseObject(o.toString(), DeviceRecord.class);
			stringToDateException();
		/*	try {
				//拷贝为projectMaster对象
				BeanUtils.copyProperties(deviceRecord, o);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}*/
			deviceRecord.setId(null);
			insert(deviceRecord);
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
	public List<DeviceRecord> queryDeviceRecordById(String organizationCode, Long id, String projectyCode) {
		return this.deviceRecordMapper.queryDeviceRecordById(organizationCode,id,projectyCode);
	}

	@Override
	@Transactional
	public boolean getDeviceRecordFromLabor(Long lastNumber, Map<String, String> m, String tableName) throws Exception {
		List<DeviceRecordMo> list = deviceRecordMapper.getDeviceRecordFromLabor(lastNumber);
		// 调用发送解析代码
		String jsonResult = sendDeviceRecordToSmz(list, m);
		// 返回数据解析
		if (StringUtils.isNotBlank(jsonResult)) {
			// 返回数据解析
			Map<String, Object> result = JSONUtil.toBean(jsonResult, Map.class);

			// 拿到考勤ID,调用发送接口，重新发送考勤记录
			if (jsonResult.contains("实名制系统不存在该人员信息")) {
				// 获取返回的考勤编号
				String idCard = SmzUtils.receiveName(jsonResult, result);
				// getDeviceRecordFromLaborToSMZ(deviceRecordId);
			}

			// 获取返回的最大值
			if (result.containsKey("record")) {
				String max = (String) result.get("record");
				Long count = Long.valueOf(max.substring(6).toString());
				//ifaLaborService.updateNumber(Long.valueOf(result.get("record").toString()), tableName);
				ifaLaborService.updateNumber(count, tableName);
			}
			if ("-2".equals(result.get("code"))) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}


	@Override
	@Transactional
	public boolean getDeviceRecordImageFromLabor(Long lastNumber, Map<String, String> m, String tableName)
			throws Exception {
		List<DeviceRecordMo> list = deviceRecordMapper.getDeviceRecordImageFromLabor(lastNumber);
		for (int i = 0; i < list.size(); i++) {
			DeviceRecordMo d = list.get(i);
			String photo = d.getPhoto();
			// 考勤图片地址不为空
			if (StringUtils.isNotBlank(photo)) {
				// 图片转换不为空
				String image = ImageUtil.GetImageStr(imageLocalPath + photo);
				if (StringUtils.isNotBlank(image)) {
					d.setPhoto(image);
				} else {
					list.remove(i);
				}
			}
		}
		if (list.size() > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			// 发送给实名制数据转json
			String json = JSONArray.fromObject(list).toString();
			map.put("lablorRecord", json);
			// 发送信息
			String jsonResult = HttpClientUtils.post(getSystemStringParam(Constant.HTTP_URL_PREFIX_IMAGE)
					+ getSystemStringParam("saveDeviceRecordImage"), map, m);
			System.out.println(jsonResult);
			// 判断是否有返回
			if (StringUtils.isNotBlank(jsonResult)) {
				// 解析返回数据
				Map<String, Object> result = JSONUtil.toBean(jsonResult, Map.class);
				// 拿到考勤ID,调用发送接口，重新发送考勤记录
				if (jsonResult.contains("deviceRecordId")) {
					// 获取返回的考勤编号
					Long deviceRecordId = SmzUtils.textToLong(jsonResult, result);
					getDeviceRecordFromLaborToSMZ(deviceRecordId);
				}

				if (result.containsKey("record")) {
					// 获取返回的最大值
					// Long numId = Long.valueOf(result.get("record").toString());
					// List<Long> deviceRecordList = ifaLaborService.getIdList(tableName);
					// 修改数据库最大值
					// if (deviceRecordList.size() > 0) {
					ifaLaborService.updateNumber(Long.valueOf(result.get("record").toString()), tableName);
					// } else {
					// ifaLaborService.insert(new IfaLabor(tableName, numId));
					// }
				}
				if ("-2".equals(result.get("code"))) {
					return false;
				} else {
					return true;
				}
			}
		}
		return false;

	}

}