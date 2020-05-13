package com.xywg.admin.modular.worker.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.eclipse.jetty.util.StringUtil;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.config.handler.RemoteDTO;
import com.xywg.admin.config.handler.ZbusHandler;
import com.xywg.admin.core.common.exception.BizExceptionEnum;
import com.xywg.admin.core.excel.ExcelUtils;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.util.DateUtil;
import com.xywg.admin.core.util.ExcelImportUtils;
import com.xywg.admin.core.util.IdcardUtils;
import com.xywg.admin.core.util.PushUtil;
import com.xywg.admin.core.util.SMSUtils;
import com.xywg.admin.core.util.SmzUtils;
import com.xywg.admin.modular.company.dao.ContractorWorkerMapper;
import com.xywg.admin.modular.company.dao.EntryExitHistoryMapper;
import com.xywg.admin.modular.company.dao.SubContractorMapper;
import com.xywg.admin.modular.company.model.ContractorWorker;
import com.xywg.admin.modular.company.model.EntryExitHistory;
import com.xywg.admin.modular.company.service.IEntryExitHistoryService;
import com.xywg.admin.modular.company.utils.ServletsUtils;
import com.xywg.admin.modular.device.dao.DeviceMapper;
import com.xywg.admin.modular.device.model.DeviceCommand;
import com.xywg.admin.modular.device.service.IDeviceCommandService;
import com.xywg.admin.modular.device.utils.RedisUtil;
import com.xywg.admin.modular.face.dao.PersonDataMapper;
import com.xywg.admin.modular.faceUtils.FaceAdd;
import com.xywg.admin.modular.faceUtils.FaceAddModel;
import com.xywg.admin.modular.faceUtils.FaceDetect;
import com.xywg.admin.modular.faceUtils.FaceIdentify;
import com.xywg.admin.modular.faceUtils.FaceMatch;
import com.xywg.admin.modular.faceUtils.FaceUpdate;
import com.xywg.admin.modular.faceUtils.HttpUtil;
import com.xywg.admin.modular.faceUtils.Identity;
import com.xywg.admin.modular.message.dao.MessageMapper;
import com.xywg.admin.modular.message.model.Message;
import com.xywg.admin.modular.project.dao.ProjectMasterMapper;
import com.xywg.admin.modular.project.dao.ProjectWorkerMapper;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.model.ProjectWorker;
import com.xywg.admin.modular.project.service.IProjectWorkerService;
import com.xywg.admin.modular.smz.dao.IfaLaborMapper;
import com.xywg.admin.modular.smz.dao.SmzLwtMapper;
import com.xywg.admin.modular.smz.model.IfaLabor;
import com.xywg.admin.modular.smz.model.PersonMo;
import com.xywg.admin.modular.smz.model.PersonnelSmz;
import com.xywg.admin.modular.smz.model.SmzLwt;
import com.xywg.admin.modular.smz.utils.Base64;
import com.xywg.admin.modular.smz.utils.Constant;
import com.xywg.admin.modular.smz.utils.DateUtils;
import com.xywg.admin.modular.smz.utils.FTPClientUtil;
import com.xywg.admin.modular.smz.utils.FileUtil;
import com.xywg.admin.modular.smz.utils.HttpClientUtils;
import com.xywg.admin.modular.smz.utils.ImageUtil;
import com.xywg.admin.modular.system.dao.DeptMapper;
import com.xywg.admin.modular.system.dao.DictMapper;
import com.xywg.admin.modular.system.dao.UserMapper;
import com.xywg.admin.modular.system.model.Dept;
import com.xywg.admin.modular.system.model.User;
import com.xywg.admin.modular.system.service.AccountProjectService;
import com.xywg.admin.modular.system.service.IAreaService;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.system.service.IDictService;
import com.xywg.admin.modular.system.service.ITeamMemberService;
import com.xywg.admin.modular.system.service.IUserService;
import com.xywg.admin.modular.team.dao.TeamMasterMapper;
import com.xywg.admin.modular.team.model.TeamMaster;
import com.xywg.admin.modular.team.model.TeamMemberShip;
import com.xywg.admin.modular.team.service.ITeamMasterService;
import com.xywg.admin.modular.worker.dao.WorkerMasterMapper;
import com.xywg.admin.modular.worker.model.AppAddCertificationsDto;
import com.xywg.admin.modular.worker.model.AppAddClassDto;
import com.xywg.admin.modular.worker.model.AppInvitationAddTeamDto;
import com.xywg.admin.modular.worker.model.AppPersonJoinStatusDto;
import com.xywg.admin.modular.worker.model.AppUpdateWorkerKindDto;
import com.xywg.admin.modular.worker.model.AppWorkerJoinDto;
import com.xywg.admin.modular.worker.model.AppWorkerMasterDto;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import com.xywg.admin.modular.worker.model.WorkerMasterEntrExport;
import com.xywg.admin.modular.worker.model.WorkerMasterImport;
import com.xywg.admin.modular.worker.model.WorkerMasterProExport;
import com.xywg.admin.modular.worker.model.WorkerMasterVO;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;
//import com.xywg.admin.modular.worker.util.WorkerColUtil;
import com.xywg.admin.modular.worker.util.WorkerMasterExcelUtil;
import com.xywg.admin.modular.worker.warpper.WorkerWarpper;

import cn.hutool.json.JSONUtil;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * <p>
 * 工人实名基础信息 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-22
 */
@SuppressWarnings("all")
@Service
public class WorkerMasterServiceImpl extends ServiceImpl<WorkerMasterMapper, WorkerMaster>
		implements IWorkerMasterService {
	@Autowired
	protected IDictService dictService;
	@Autowired
	protected IAreaService iAreaService;
	@Autowired
	protected WorkerMasterExcelUtil workerMasterExcelUtilexcelUtil;
	@Autowired
	protected PersonDataMapper personDataMapper;

	@Autowired
	protected WorkerMasterMapper workerMasterMapper;

	@Autowired
	protected ITeamMasterService teamService;

	@Autowired
	protected DeptMapper deptMapper;
	@Resource
	private MessageMapper messageMapper;
	@Autowired
	private ContractorWorkerMapper contractorWorkerMapper;
	@Resource
	private TeamMasterMapper teamMasterMapper;

	@Resource
	private ProjectWorkerMapper projectWorkerMapper;

	@Resource
	private IProjectWorkerService projectWorkerService;

	@Resource
	private ProjectMasterMapper projectMasterMapper;

	@Resource
	private EntryExitHistoryMapper entryExitHistoryMapper;

	@Autowired
	protected UserMapper userMapper;

	@Autowired
	protected IUserService userService;

	@Autowired
	protected DictMapper dictMapper;

	@Autowired
	private IDeptService deptService;

	@Autowired
	private ITeamMasterService teamMasterService;

	@Autowired
	private SubContractorMapper subContractorMapper;

	@Autowired
	private IfaLaborMapper ifaLaborMapper;

	@Autowired
	private SmzLwtMapper smzLwtMapper;

	@Autowired
	private AccountProjectService accountProjectService;

	@Autowired
	private TeamMasterMapper teamMapper;

	@Autowired
	private DeviceMapper deviceMapper;

	@Autowired
	private IDeviceCommandService deviceCommandService;

	@Autowired
	private ITeamMemberService teamMemberService;
	
	@Autowired
    public RedisUtil redisUtil;
	@Autowired
	private IEntryExitHistoryService entryExitHistoryService;
	private static final Log LOG = LogFactory.getLog(WorkerMasterServiceImpl.class);
	private static Properties systemParams = new Properties();

	private static final String EXPORTCOL = "cols";

	/**
	 * 加载配置文件
	 */
	static {
		try {
			systemParams.load(WorkerMasterServiceImpl.class.getClassLoader().getResourceAsStream("smz.properties"));
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


	@Value("${spring.mvc.view.imageLocalPath}")
	private String imageLocalPath;

/*	@Override
	public boolean getPersonFromLabor(List<Long> ids, Map<String, String> m) {
		List<PersonMo> list = workerMasterMapper.getPersonFromLabor(ids);
		if (list.size() > 0) {
			for (PersonMo p : list) {
				String pNation = p.getNation();
				String pBirthday = p.getBirthday();
				String pWorkeType = p.getKindCode();
				// String pImage = p.getImage();
				if (StringUtils.isNotBlank(pBirthday)) {
					pBirthday = pBirthday.replace("-", "");
					p.setBirthday(pBirthday);
				}
				if (StringUtils.isNotBlank(pNation) && pNation.length() == 1) {
					pNation = "0" + pNation;
					p.setNation(pNation);
				}
				if (StringUtils.isNotBlank(pWorkeType) && pWorkeType.length() == 2) {
					p.setKindCode("0" + pWorkeType);
				}
				// if(StringUtils.isNotBlank(pImage)) {
				// try {
				// p.setImage(ImageUtil.GetImageStr(imageLocalPath + pImage));
				// } catch (Exception e) {
				// e.printStackTrace();
				// }
				// }
			}
			String json = JSONArray.fromObject(list).toString();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("laborUser", json);
			String jsonResult = HttpClientUtils
					.post(getSystemStringParam("httpUrlPrefix") + getSystemStringParam("saveManagerFromLabor"), map, m);
			System.err.println("worker send = " + jsonResult);
			Map<String, String> result = JSONUtil.toBean(jsonResult, Map.class);
			if ("-2".equals(result.get("code"))) {
				return false;
			} else {
				return true;
			}
		}
		return false;

	}*/

/*	@Override
	public boolean getPersonImageFromLabor(int number, Map<String, String> m) {
		List<PersonMo> list = workerMasterMapper.getPersonImageToLabor(number);
		if (list.size() > 0) {
			for (PersonMo p : list) {
				String pImage = p.getImage();
				if (StringUtils.isNotBlank(pImage)) {
					try {
						String image = ImageUtil.GetImageStr(imageLocalPath + pImage);
						p.setImage(image);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			String json = JSONArray.fromObject(list).toString();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("laborUser", json);
			String jsonResult = HttpClientUtils
					.post(getSystemStringParam("httpUrlPrefix") + getSystemStringParam("saveLablarUserImage"), map, m);
			System.err.println("jsonResult = " + jsonResult);
			Map<String, String> result = JSONUtil.toBean(jsonResult, Map.class);
			if (result.containsKey("record")) {
				System.err.println("record =" + result.get("record"));
				Long numId = Long.valueOf(result.get("record").toString());
				List<Long> deviceRecordList = ifaLaborMapper.getIdList(Constant.BUSS_PROJECT_WORKER_IMAGE);
				if (deviceRecordList.size() > 0) {
					ifaLaborMapper.updateNumber(numId, Constant.BUSS_PROJECT_WORKER_IMAGE);
				} else {
					ifaLaborMapper.insert(new IfaLabor(Constant.BUSS_PROJECT_WORKER_IMAGE, numId));
				}
			}
			if ("-2".equals(result.get("code"))) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}*/

	/**
	 * @description 获取实名制工人数据（盐城）
	 * @author chupp
	 * @date 2018年7月4日
	 */
	@Override
	@Transactional
	public void savePersonnelFromSMZYC(Map<String, String> myc) {
		// 获取实名制工人数据
		String jsonResult = HttpClientUtils.post(
				getSystemStringParam("httpUrlPrefixYC") + getSystemStringParam("savePersonnelFromSMZYC"),
				new HashMap<String, Object>(), myc);
		if (jsonResult.contains("404出错啦") || jsonResult.contains("Sorry, Page Not Found")) {
			return;
		}
		Map<String, Object> map = (Map<String, Object>) net.sf.json.JSONObject
				.toBean(net.sf.json.JSONObject.fromObject(jsonResult), Map.class);
		// 存在新的工人数据
		if (map.get("worker") != null) {
			JSONArray jsonArray = JSONArray.fromObject(map.get("worker"));
			List<PersonnelSmz> personnelList = JSONArray.toList(jsonArray, new PersonnelSmz(), new JsonConfig());
			String id = "0";
			if (personnelList.size() > 0) {
				// 获取FTP连接
				Random ra = new Random();
				String ran = "" + ra.nextInt(10) + ra.nextInt(10);
				String storeFilePath = "lwtgb_smz" + FileUtil.SEPARATOR + DateUtils.getCurrentDate("yyyy")
						+ FileUtil.SEPARATOR + DateUtils.getCurrentDate("MM") + FileUtil.SEPARATOR
						+ DateUtils.getCurrentDate("dd") + FileUtil.SEPARATOR + DateUtils.getCurrentDate("HH")
						+ FileUtil.SEPARATOR + ran + FileUtil.SEPARATOR;
				FTPClient connectFtp = null;
				try {
					connectFtp = FTPClientUtil.connectFtp(Constant.FTP_HOST, Constant.FTP_PORT, Constant.FTP_USERNAME,
							Constant.FTP_PASS_WORD, storeFilePath);
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("实名制接口(盐城)工人数据FTP连接错误");
				}
				try {
					for (PersonnelSmz ps : personnelList) {
						long smzId = Long.parseLong(ps.getId());
						long lwtId = 0L;
						WorkerMaster chWm = this.baseMapper.getWorkerByIdCard(ps.getUserId(), 1);
						if (chWm == null) {
							WorkerMaster wm = new WorkerMaster();
							wm.setWorkerName(ps.getName());
							wm.setIdCardType(1);
							wm.setIdCardNumber(ps.getUserId());
							if (ps.getGender() != null) {
								wm.setGender(Integer.parseInt(ps.getGender()) == 0 ? 1 : 0);
							} else {
								wm.setGender(0);
							}
							if (ps.getNation() == null) {
								wm.setNation("1");
							} else {
								wm.setNation("" + Integer.parseInt(ps.getNation()));
							}
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
							try {
								if (ps.getBirthday() == null) {
									wm.setBirthday(sdf.parse(ps.getUserId().substring(6, 14)));
								} else {
									wm.setBirthday(sdf.parse(ps.getBirthday()));
								}
							} catch (ParseException e) {
								try {
									wm.setBirthday(sdf.parse(ps.getUserId().substring(6, 14)));
								} catch (ParseException e1) {
									e1.printStackTrace();
								}
							}
							wm.setBirthPlaceCode(ps.getUserId().substring(0, 2) + "0000");
							wm.setAddress(ps.getHomeAddress());
							if (ps.getMobile() != null) {
								wm.setCellPhone(ps.getMobile());// 手机号码无实际意义
							} else {
								wm.setCellPhone("123");
							}
							if (ps.getKindCode() != null) {
								wm.setWorkTypeCode("" + Integer.parseInt(ps.getKindCode()));
							} else {
								wm.setWorkTypeCode("10");
							}
							wm.setIsAuth(3);
							// 保存身份证人脸图片
							try {
								if (ps.getPhoto() != null) {
									String newName = UUID.randomUUID().toString().replace("-", "") + ".jpg";
									FTPClientUtil.uploadFile(new ByteArrayInputStream(Base64.decode(ps.getPhoto())),
											new String((newName).getBytes("utf-8"), "iso-8859-1"), connectFtp);
									wm.setHeadImage(storeFilePath + newName);
								} else {
									wm.setHeadImage("smz");
								}
							} catch (IOException e) {
								e.printStackTrace();
								throw new RuntimeException("实名制接口(盐城)工人数据身份证图片转换错误");
							}
							// 保存实名制工人数据
							this.baseMapper.insert(wm);
							lwtId = wm.getId();
						} else {
							lwtId = chWm.getId();
						}
						// 保存主键关联表
						SmzLwt sl = new SmzLwt();
						sl.setSmzId(smzId);
						sl.setLwtId(lwtId);
						sl.setTableName("PersonnelYC");
						smzLwtMapper.saveSmzLwt(sl);
						if (Long.parseLong(id) < smzId) {
							id = String.valueOf(smzId);
						}
					}
				} finally {
					if (connectFtp != null && connectFtp.isConnected()) {
						try {
							connectFtp.logout();
						} catch (IOException e) {
							e.printStackTrace();
							connectFtp = null;
						}
					}
				}
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("code", "1");
				m.put("no", id);
				// 响应保存成功信息
				HttpClientUtils.post(getSystemStringParam("httpUrlPrefixYC")
						+ getSystemStringParam("savePersonnelFromSMZResponseYC"), m, myc);
			}
		}
	}

/*	@Override
	public boolean getPersonFromLabor(List<Long> ids, Map<String, String> m) {
		return false;
	}*/

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean insert(WorkerMasterVO entity) {
		// 判断人员是否存在
		boolean isAdd = false;
		Date now = new Date(); // 当前时间
		Long id = this.baseMapper.getWorkerCountByIdCard(entity.getIdCardNumber(), entity.getIdCardType());
		entity.setId(id);
		// 判断是否已存在
		// if(id !=null && "".equals(entity.getProjectCode()) &&
		// "".equals(entity.getTeamSysNo())) {
		// throw new XywgException(600, "工人已存在");
		// }
		HashMap<String, Object> member = new HashMap<String, Object>(16);
		// 新增并且生成账号
		// 设置人脸图片和已经录入人脸
		if (StringUtil.isNotBlank(entity.getHeadImage())) {
			entity.setIsFace(1);
//			entity.setFace(entity.getHeadImage());
		}
		// 判断人是否存在
		if (id == null) {
			if (StringUtils.isNotBlank(entity.getCellPhone())) {
				if (this.baseMapper.getByCellPhone(entity.getCellPhone()) != null) {
					throw new XywgException(600, "手机号已被使用");
				}
			}
			// 判断是否需要做实名认证
			if (entity.getIdCardType() == 1 && entity.getIsNeedCheck()) {
				// 判断身份证是否有效
				if (!IdcardUtils.checkIdCardNum(entity.getIdCardNumber(), now, entity.getStartTime(),
						entity.getEndTime())) {
					throw new XywgException(600, "身份证已过期");
				}
				Identity.validByCard(entity.getIdCardNumber(), entity.getWorkerName());
				entity.setIsAuth(2);
			} else {
				entity.setIsAuth(0);
			}
			this.baseMapper.insert(entity);
			userService.addWorkerAccountPC(entity.getCellPhone(), entity.getIdCardType(), entity.getIdCardNumber());
			id = entity.getId();
			isAdd = true;

		} else if (StringUtil.isNotBlank(entity.getHeadImage())) {
			entity.setId(id);
			this.baseMapper.updateWorkerFace(entity);
		}

		String organizationCode = "";
		if ("".equals(entity.getTeamSysNo())) {
			organizationCode = deptService.selectById(ShiroKit.getUser().getDeptId()).getSocialCreditNumber();
		} else {
			int teamSysNo = Integer.parseInt(entity.getTeamSysNo());
			// 往teamMember插入数据
			// TeamMemberShip teamMember = new TeamMemberShip(teamSysNo, 1,
			// entity.getIdCardNumber());
			if (entity.getIsTeamLeader() == 1) {
				// 是班组老板 给班组设置班组老板
				teamMasterService.removeTeamLeader(teamSysNo);
				teamMasterService.setTeamLeaderByTeamSysNo(teamSysNo, entity.getIdCardNumber(), entity.getWorkerName());
				// teamMember.setTeamWorkerType(0);
				member.put("teamWorkType", "0");
			} else {
				member.put("teamWorkType", "1");
			}

			TeamMaster teamMaster = teamMasterMapper.getTeamInfoByTeamSysNo(teamSysNo);
			organizationCode = teamMaster.getOrganizationCode();// 公司企业代码
		}

		// 人员加入当前登陆人公司
		ContractorWorker cw = contractorWorkerMapper.getByIdCard(entity.getIdCardNumber(), entity.getIdCardType(),
				organizationCode);// 判断该公司是否存在该人员
		if (cw == null) {
			ContractorWorker contractorWorker = new ContractorWorker();
			contractorWorker.setIdCardType(entity.getIdCardType());
			contractorWorker.setIdCardNumber(entity.getIdCardNumber());
			contractorWorker.setWorkName(entity.getWorkerName());
			contractorWorker.setOrganizationCode(organizationCode);
			contractorWorkerMapper.insert(contractorWorker);
		} else if ((cw.getIsDel() == 1)) {// 如果已经被删除
			contractorWorkerMapper.updateIsDel(cw.getId());
		}

		boolean isEnter = false;
		 ProjectWorker projectWorker = null;
		// 判断是否选择项目班组
		if (!"".equals(entity.getProjectCode()) && !"".equals(entity.getTeamSysNo())) {
			member.put("teamCode", entity.getTeamSysNo());
			member.put("idStrs", id + "");
			member.put("from", "worker");
			member.put("cardNumber", entity.getCardNumber());
			member.put("cardType", entity.getIdCardType());
			member.put("face", entity.getHeadImage());
			Map<String, Object> returnMap = teamMasterService.addMember(member);
			 if(!returnMap.isEmpty()&&(boolean)returnMap.get("ok")) {
			 projectWorker = JSON.parseObject(JSON.parseArray(JSON.toJSONString(returnMap.get("projectWorker"))).get(0).toString(),ProjectWorker.class) ;
			 }
			// 插入进场之后处理进场信息
			 isEnter = true;
			if (StringUtils.isNotBlank(entity.getHeadImage()) || StringUtils.isNotBlank(entity.getCardNumber())) {
				dispatchUser(entity);
			}
		}

		boolean bl = retBool(baseMapper.updateById(entity));

		// 先判断所加入的项目是否已经同步给了智慧工地
		 if(StringUtils.isNotBlank(entity.getProjectCode())) {
		 ProjectMaster projectMaster = null;
		 projectMaster = projectMasterMapper.getProjectByProjectCode(entity.getProjectCode());
		 if(projectMaster.getIsSynchro().intValue() == 1) {
		 //已经同步过，进行数据构建推送
		 //事物没有提交所以无法获取到，这里要手动拼装
		 Map<String, Object> model = new HashMap<>();
		 model.put("relationWorkerId", entity.getId());
		 model.put("name", entity.getWorkerName());
		 if(isEnter) {
		 //待进场暂时用进场取代，之后可能要改，暂时保留
		 if(projectWorker.getJoinStatus() != null) {
		 model.put("enterStatus", projectWorker.getJoinStatus().intValue() == 1? "进场"
		 : projectWorker.getJoinStatus().intValue() == 2 ? "进场" : "退场");
		 }
		 model.put("enterTime", DateUtil.format(projectWorker.getEntryTime(),
		 "yyyy-MM-dd HH:mm:ss"));
		 }else{
		 model.put("enterStatus", "");
		 model.put("enterTime", "");
		 }
		 model.put("peopleType", 0);
		 model.put("documentType", entity.getIdCardType());
		 model.put("identityCode", entity.getIdCardNumber());
		 model.put("birthDate", DateUtil.format(entity.getBirthday(), "yyyy-MM-dd"));
		 model.put("sex", entity.getGender());
		 model.put("nation", entity.getNation());
		 model.put("telephoneNumber", entity.getCellPhone());
		 model.put("politicalStatus", entity.getPoliticsType());
		 model.put("homeAddress", entity.getAddress());
		 model.put("tempAddre", "");
		 model.put("isAdd", entity.getIsJoined());
		 model.put("addTime", entity.getJoinedTime());
		 model.put("educationLevel", entity.getCultureLevelType());
		 model.put("isSickness", entity.getHasBadMedicalHistory());
		 model.put("emergencyPeople", entity.getUrgentContractName());
		 model.put("emergencyTel", entity.getUrgentContractCellphone());
		 model.put("workStartTime", entity.getWorkDate());
		 model.put("accommodationType", "");
		 if(StringUtil.isNotBlank(entity.getProjectCode())) {
		 model.put("relationProjectId", projectMaster.getId()); //项目id
		 }
		 model.put("contractorType", 1);
		 model.put("subProjectId", 0);
		 model.put("relationWorkId", entity.getWorkTypeCode()); //工种id
		 if(StringUtil.isNotBlank(entity.getTeamSysNo())) {
		 TeamMaster teamMaster =
		 teamMasterMapper.getTeamInfoByTeamSysNo(Integer.parseInt(entity.getTeamSysNo()));
		 model.put("relationTeamId", teamMaster.getId()); //班组id
		 }
		 model.put("platform", 2);
		 List<Map<String, Object>> listM = new ArrayList<>();
		 listM.add(model);
		 //发送到zbus
		// sendToZbus(listM, isAdd);
		
		 if(isEnter) {
		 //处理进场
		 Map<String, Object> map = new HashMap<>();
		 map.put("relationUserId", entity.getId());
		 //map.put("relationProjectId", byWorkerAndTeam.getProjectCode());
		 map.put("relationProjectId", projectMaster.getId());
		 map.put("enterStatus", projectWorker.getJoinStatus().intValue() == 1? "进场" :
		 projectWorker.getJoinStatus().intValue() == 2 ? "进场" : "退场");
		 map.put("enterTime", DateUtil.format(projectWorker.getEntryTime(),
		 "yyyy-MM-dd HH:mm:ss"));
		 map.put("platform", 2);
		 RemoteDTO dto = new RemoteDTO();
		 Map<String, Object> mm = new HashMap<>();
		 mm.put("record", map);
		 mm.put("operationFlag", "insertRecordExternalWorker");
		 dto.setBody(mm);
		 //ZbusHandler.send(net.sf.json.JSONObject.fromObject(dto).toString());
		 }
		 }
		 }

		return true;

	}

	 private void sendToZbus(List<Map<String, Object>> mmList, boolean isAdd) {
	 //新增成功推送给智慧工地
	 //新增完成调用zbus推送给智慧工地
	 RemoteDTO dto = new RemoteDTO();
	 mmList.forEach(mm -> {
	 //获取班组信息
	 if(null != mm.get("relationTeamId") &&
	 StringUtil.isNotBlank(mm.get("relationTeamId").toString())) {
	 TeamMaster team =
	 teamMasterMapper.getByteamCode(Integer.parseInt(mm.get("relationTeamId").toString()));
	 Map<String, Object> teamMap = new HashMap<>();
	 teamMap.put("platform", 2);
	 teamMap.put("name", team.getTeamName());
	 teamMap.put("relationProjectId", mm.get("relationProjectId").toString());
	 teamMap.put("relationTeamId", mm.get("relationTeamId").toString());
	 mm.put("projectMasterTeam", teamMap);
	 }
	 //获取工种信息
	 String dictName = dictMapper.selectNameByCode("工种字典数据",
	 Integer.parseInt(mm.get("relationWorkId").toString()));
	 Map<String, Object> dictMap = new HashMap<>();
	 dictMap.put("platform", 2);
	 dictMap.put("name", dictName);
	 dictMap.put("relationWorkId", mm.get("relationWorkerId").toString());
	 dictMap.put("type", 1);
	 mm.put("projectMasterWorkType", dictMap);
	 Map<String, Object> mmm = new HashMap<>();
	 mmm.put("operationFlag",
	 isAdd?"insertExternalWorker":"updateExternalWorker");
	 mmm.put("worker", mm);
	 dto.setBody(mmm);
	 ZbusHandler.send(net.sf.json.JSONObject.fromObject(dto).toString());
	 });
	 }

	/**
	 * 给考勤机下发项目下所有人脸
	 *
	 * @param entity
	 */
	@Override
	public void dispatchUser(WorkerMaster entity) {
		// 人脸下发
		List<String> deviceSns = deviceMapper.getAllDeviceSnsByProjectCode(entity.getProjectCode());
		List<String> userIds = new ArrayList<String>();
		userIds.add(entity.getIdCardNumber() + "," + entity.getIdCardType());
		DeviceCommand deviceCommand = new DeviceCommand(deviceSns, 7, userIds);
		deviceCommandService.dispatchUser(deviceCommand);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean importWorker(WorkerMasterImport entity, Integer i) {
		if (this.baseMapper.getWorkerByIdCard(entity.getIdCardNumber(), entity.getIdCardType()) == null) {
			if (StringUtils.isNotBlank(entity.getCellPhone())) {
				if (this.baseMapper.getByCellPhone(entity.getCellPhone()) != null) {
					throw new XywgException(600, "第" + i + "条记录手机号已存在");
				}
			}
			if (entity.getIdCardType() == 1) {
				try {
					Identity.validByCard(entity.getIdCardNumber(), entity.getWorkerName());
					entity.setIsAuth(2);
				} catch (Exception e) {
					throw new XywgException(600, "第" + i + "条记录身份证和姓名不匹配");
				}
			}
			WorkerMaster workerMaster = new WorkerMaster();
			BeanUtils.copyProperties(entity, workerMaster);
			this.baseMapper.insert(workerMaster);
			userService.addWorkerAccountPC(entity.getCellPhone(), entity.getIdCardType(), entity.getIdCardNumber());
		}
		int deptId = ShiroKit.getUser().getDeptId();
		Dept dept = deptMapper.selectById(deptId);
		String organizationCode = dept.getSocialCreditNumber();// 公司企业代码
		ContractorWorker cw = contractorWorkerMapper.getByIdCard(entity.getIdCardNumber(), entity.getIdCardType(),
				organizationCode);// 判断该公司是否存在该人员
		if (cw == null) {
			ContractorWorker contractorWorker = new ContractorWorker();
			contractorWorker.setIdCardType(entity.getIdCardType());
			contractorWorker.setIdCardNumber(entity.getIdCardNumber());
			contractorWorker.setWorkName(entity.getWorkerName());
			contractorWorker.setOrganizationCode(organizationCode);
			contractorWorkerMapper.insert(contractorWorker);
			return true;
		}
		if ((cw.getIsDel() == 1)) {// 如果已经被删除
			contractorWorkerMapper.updateIsDel(cw.getId());
			return true;
		} else {
			throw new XywgException(600, "第" + i + "条记录人员已存在");
		}
	}

	@Override
	public List<Map<String, Object>> selectWorkers(Page<WorkerMaster> page, Map<String, Object> map) {
		// hujingyun 根据社会信用代码过滤数据权限(只能显示自己部门和下级部门的信息)
		String deptId = ShiroKit.getSessionAttr("toggleDeptId").toString();
		if (!"1".equals(deptId)&&!"239".equals(deptId)){
		String orgCode	=deptMapper.selectOrgCodeByDeptId(deptId);
		map.put("orgCode",orgCode);
		}
		map.put("depts", deptService.getUserDeptAndSubdivisionOrganizationCode());
		map.put("projectCodes", accountProjectService.getProjectCodes());
		map.put("isEnterprise", ShiroKit.getUser().getIsEnterprise());
		return this.baseMapper.selectWorkers(page, map);
	}

	@Override
	public List<Map<String, Object>> selectWorkersList(Map<String, Object> map) {
		map.put("depts", deptService.getUserDeptAndSubdivisionOrganizationCode());
		map.put("projectCodes", accountProjectService.getProjectCodes());
		map.put("isEnterprise", ShiroKit.getUser().getIsEnterprise());
		return this.baseMapper.selectWorkersList(map);
	}

	@Override
	public WorkerMaster getWorkerByIdCard(String idCardNumber, Integer idCardType) {
		return this.baseMapper.getWorkerByIdCard(idCardNumber, idCardType);
	}

	@Override
	public List<Map<String, Object>> searchWorker(String idCardNumber, Integer idCardType) {
		return this.baseMapper.searchWorker(idCardNumber, idCardType);
	}

	/**
	 * @param workerMaster
	 * @description 工人实名认证
	 * @author chupp
	 * @date 2018年5月28日
	 */
	@Override
	@Transactional
	public void authPerson(WorkerMaster workerMaster) {
		Date now = new Date();
		// 字典转换
		workerMaster.setNation(String.valueOf(dictMapper.selectNumByName("民族", workerMaster.getNation())));
		workerMaster.setGender(dictMapper.selectNumByName("性别", workerMaster.getNote()));
		// 查询用户信息
		User user = userMapper.selectById(workerMaster.getId());
		if (user == null || user.getPhone() == null || user.getPhone().isEmpty()) {
			throw new XywgException(BizExceptionEnum.NO_THIS_USER);
		}
		// 判断身份证是否有效
		if (null != workerMaster.getStartTime() && !workerMaster.getStartTime().toString().equals("")
				&& null != workerMaster.getEndTime() && workerMaster.getEndTime().toString().equals("")
				&& !IdcardUtils.checkIdCardNum(workerMaster.getIdCardNumber(), now, workerMaster.getStartTime(),
						workerMaster.getEndTime())) {
			throw new XywgException(600, "身份证已过期");
		}

		WorkerMaster wm = this.baseMapper.getByCellPhone(user.getPhone());
		if (wm != null) {// PC端所添加人员（已实名认证，无本地人脸库录入）
			// if (2 == wm.getIsAuth() &&
			// wm.getIdCardNumber().equals(workerMaster.getIdCardNumber())
			// && wm.getWorkerName().equals(workerMaster.getWorkerName())) {
			workerMaster.setId(wm.getId());
			workerMaster.setIdCardType(1);
			workerMaster.setBirthPlaceCode(workerMaster.getIdCardNumber().substring(0, 2) + "0000");
			workerMaster.setCellPhone(user.getPhone());
			workerMaster.setIsAuth(1);
			workerMaster.setIsFace(0);
			// 更新工人信息
			if (this.baseMapper.updateById(workerMaster) <= 0) {
				throw new XywgException(BizExceptionEnum.AUTH_PERSONNEL_FAILED);
			}
			// } else {
			// throw new XywgException(BizExceptionEnum.ID_CARD_INFO_NOT_SAME);
			// }
		} else {
			WorkerMaster chWm = this.baseMapper.getWorkerByIdCard(workerMaster.getIdCardNumber(), 1);
			if (chWm != null) {
				/*
				 * if (chWm.getIsAuth() == 3) {//实名制接口人员 workerMaster.setId(chWm.getId());
				 * workerMaster.setIdCardType(1);
				 * workerMaster.setBirthPlaceCode(workerMaster.getIdCardNumber().substring(0, 2)
				 * + "0000"); workerMaster.setCellPhone(user.getPhone());
				 * workerMaster.setIsAuth(1); workerMaster.setIsFace(0); //更新工人信息 if
				 * (this.baseMapper.updateById(workerMaster) <= 0) { throw new
				 * XywgException(BizExceptionEnum.AUTH_PERSONNEL_FAILED); } }else { throw new
				 * XywgException(BizExceptionEnum.ID_CARD_NUMBER_HAS_USED); }
				 */
				workerMaster.setId(chWm.getId());
				workerMaster.setIdCardType(1);
				workerMaster.setBirthPlaceCode(workerMaster.getIdCardNumber().substring(0, 2) + "0000");
				workerMaster.setCellPhone(user.getPhone());
				workerMaster.setIsAuth(1);
				workerMaster.setIsFace(0);
				// 更新工人信息
				if (this.baseMapper.updateById(workerMaster) <= 0) {
					throw new XywgException(BizExceptionEnum.AUTH_PERSONNEL_FAILED);
				}
				// 修改user表身份证号
				// 先判断该身份证是否存在老账号，有的话删除
				User byIdCard = userMapper.getByIdCard(workerMaster.getIdCardNumber(), user.getId());
				if (byIdCard != null) {
					userMapper.delById(byIdCard.getId());
				}
				userMapper.updateIdCard(user.getId(), workerMaster.getIdCardNumber());

			} else {// APP端所注册人员
				Identity.validByCard(workerMaster.getIdCardNumber(), workerMaster.getWorkerName());
				workerMaster.setId(null);
				workerMaster.setIdCardType(1);
				workerMaster.setBirthPlaceCode(workerMaster.getIdCardNumber().substring(0, 2) + "0000");
				workerMaster.setCellPhone(user.getPhone());
				workerMaster.setIsAuth(1);
				workerMaster.setIsFace(0);
				workerMaster.setCreateDate(new Date());
				workerMaster.setUpdateDate(new Date());
				// 保存工人信息
				this.baseMapper.insert(workerMaster);
				// 修改新注册用户的身份证信息
				// 先判断该身份证是否存在老账号，有的话删除
				User byIdCard = userMapper.getByIdCard(workerMaster.getIdCardNumber(), user.getId());
				if (byIdCard != null) {
					userMapper.delById(byIdCard.getId());
				}
				userMapper.updateIdCard(user.getId(), workerMaster.getIdCardNumber());
			}
		}
		// 上海大学
		// boolean flag = this.insertLocalFace(workerMaster.getId().toString(), null,
		// workerMaster.getIdImage(), "", "0");
		// if (!flag) {
		// throw new XywgException(BizExceptionEnum.AUTH_PERSONNEL_FAILED);
		// }
	}

	/**
	 * @param workerMaster
	 * @description 人脸录入
	 * @author chupp
	 * @date 2018年5月29日
	 * @see com.xywg.admin.modular.worker.service.IWorkerMasterService#input(com.xywg.admin.modular.worker.model.WorkerMaster,
	 *      java.lang.String, int)
	 */
	@Override
	@Transactional
	public void input(WorkerMaster workerMaster) {
		WorkerMaster wm = this.getWorkerByIdCard(workerMaster.getIdCardNumber(), workerMaster.getIdCardType());
		if (wm == null) {
			throw new XywgException(BizExceptionEnum.WORKER_NO_EXIST);
		}
		// 试处理百度图片录入
		String[] split = workerMaster.getFace().split(",");
		StringBuilder face = new StringBuilder();
		boolean flag = false;
		int index = -1;
		for (int i = 0; !flag && i < 6; i++) {
			try {
				this.inputFaceAndProjectCodeByBD(String.valueOf(wm.getId()), wm.getWorkerName(), split[i],
						UUID.randomUUID().toString().replace("-", ""));
				flag = true;
				face.append(split[i]);
				index = i;
				break;
			} catch (Exception e) {
				continue;
			}
		}
		if (!flag) {
			throw new XywgException(BizExceptionEnum.INPUT_FACE_FAILED);
		}
		for (int i = 0; i < 6; i++) {
			if (i == index)
				continue;
			face.append(",");
			face.append(split[i]);
		}
		workerMaster.setFace(face.toString());
		// 百度人脸处理
		List<ProjectWorker> projectWorkerList = projectWorkerMapper.getProjectWorkerList(wm.getIdCardNumber(),
				String.valueOf(wm.getIdCardType()));
		for (ProjectWorker pw : projectWorkerList) {
			try {
				ProjectMaster pm = projectMasterMapper.getProjectByProjectCode(pw.getProjectCode());
				if (pm != null && 0 == pm.getIsDel() && !"4".equals(pm.getProjectStatus())) {
					if (wm.getIsFace() != null && wm.getIsFace() == 1) {
						FaceUpdate.update(new FaceAddModel(String.valueOf(wm.getId()), String.valueOf(pm.getId()),
								workerMaster.getFace().split(",")[0], wm.getWorkerName(), ""));
					} else {
						this.inputFaceAndProjectCodeByBD(String.valueOf(wm.getId()), wm.getWorkerName(),
								workerMaster.getFace().split(",")[0], String.valueOf(pm.getId()));
					}
				}
			} catch (Exception e) {
				throw new XywgException(BizExceptionEnum.INPUT_FACE_FAILED);
			}
		}
		// 上海大学人脸录入
		this.inputBySH(wm, workerMaster);
	}

	/**
	 * @param wm
	 * @param workerMaster
	 * @description 人脸录入（上海大学）
	 * @author chupp
	 * @date 2018年6月11日
	 */
	public void inputBySH(WorkerMaster wm, WorkerMaster workerMaster) {
		// 录入本地人脸库（上海大学）
		// boolean flag = this.insertLocalFace(wm.getId().toString(), null,
		// workerMaster.getFace(), wm.getWorkerName(), "1");
		// if (flag) {
		wm.setIsFace(1);
		wm.setFace(workerMaster.getFace());
		if (this.baseMapper.updateById(wm) > 0) {
			personDataMapper.deletePersonData(workerMaster);
			personDataMapper.savePersonData(workerMaster);
		} else {
			throw new XywgException(BizExceptionEnum.INPUT_FACE_FAILED);
		}
		// } else {
		// throw new XywgException(BizExceptionEnum.INPUT_FACE_FAILED);
		// }
	}

	/**
	 * @param workerId
	 * @param projectCode
	 * @description 录入人脸、项目编号依赖关系（包含百度、上海大学）
	 * @author chupp
	 * @date 2018年5月30日
	 * @see com.xywg.admin.modular.worker.service.IWorkerMasterService#inputFaceAndProjectCode(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	@Transactional
	public void inputFaceAndProjectCode(String workerId, String projectCode) {
		try {
			ProjectMaster pm = projectMasterMapper.getProjectByProjectCode(projectCode);
			if (pm == null) {
				throw new XywgException(BizExceptionEnum.PROJECT_NO_EXIST);
			}
			// 上海大学
			// this.inputFaceAndProjectCodeBySH(workerId, String.valueOf(pm.getId()));
			// 百度
			WorkerMaster wm = this.baseMapper.selectById(workerId);
			if (wm == null) {
				throw new XywgException(BizExceptionEnum.WORKER_NO_EXIST);
			}
			if (wm.getIsFace() != null && wm.getIsFace() == 1) {
				boolean flag = projectWorkerService.getForFaceUse(Integer.parseInt(workerId), projectCode);
				if (!flag) {
					this.inputFaceAndProjectCodeByBD(workerId, wm.getWorkerName(), wm.getFace().split(",")[0],
							String.valueOf(pm.getId()));
				} else {
					try {
						FaceUpdate.update(new FaceAddModel(String.valueOf(wm.getId()), String.valueOf(pm.getId()),
								wm.getFace().split(",")[0], wm.getWorkerName(), ""));
					} catch (Exception e) {
						throw new XywgException(BizExceptionEnum.INPUT_FACE_FAILED);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new XywgException(BizExceptionEnum.REPEAT_INPUT_FACE);
		}
	}

	/**
	 * @param workerId
	 * @param projectId
	 * @description 录入人脸、项目编号依赖关系（上海大学）
	 * @author chupp
	 * @date 2018年6月11日
	 */
	public void inputFaceAndProjectCodeBySH(String workerId, String projectId) {
		// 上海大学
		Map<String, String> map = new HashMap<String, String>();
		map.put("personId", workerId);
		map.put("projectId", projectId);
		JSONObject json = new JSONObject(map);
		String param = json.toString();
		String result;
		String resultCode;
		try {
			result = HttpUtil.postLocal(Constant.LOCAL_ENTERPROJECT_URL, param);
			resultCode = new JSONObject(result).get("code").toString();
		} catch (Exception e) {
			throw new XywgException(BizExceptionEnum.FACE_PROJECT_CODE_FAILED);
		}
		if (!"1".equals(resultCode)) {
			throw new XywgException(BizExceptionEnum.FACE_PROJECT_RESULT_ERROR);
		}
	}

	/**
	 * @param projectId
	 * @description 录入人脸、项目编号依赖关系（百度）
	 * @author chupp
	 * @date 2018年6月11日
	 */
	public void inputFaceAndProjectCodeByBD(String workerId, String workerName, String face, String projectId) {
		boolean faceFlag;
		try {
			faceFlag = FaceDetect.detect(face);
		} catch (Exception e) {
			throw new XywgException(BizExceptionEnum.FACE_PROJECT_CODE_FAILED);
		}
		if (!faceFlag) {
			throw new XywgException(BizExceptionEnum.FACE_PROJECT_CODE_FAILED);
		}
		boolean flag = this.insertFace(workerId, projectId, face, workerName, "");
		if (!flag) {
			throw new XywgException(BizExceptionEnum.FACE_PROJECT_CODE_FAILED);
		}
	}

	/**
	 * @param workerId
	 * @param projectId
	 * @param idImage
	 * @param name
	 * @param actionType
	 * @return
	 * @description 录入百度人脸库
	 * @author chupp
	 * @date 2018年5月30日
	 */
	public boolean insertFace(String workerId, String projectId, String idImage, String name, String actionType) {
		FaceAddModel face = new FaceAddModel(workerId, projectId, idImage, name, actionType);
		try {
			return FaceAdd.add(face);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 工人同意加入班组 xieshuaishuai
	 *
	 * @param id
	 */
	@Override
	@Transactional(readOnly = false)
	public void workerAgreeAddTeam(Integer id) {

		// 首先把未读改为已读
		messageMapper.changeIsRead(id);
		// 然后在人员班组关系表中插入一条数据
		Message message = messageMapper.getMessageById(id);
		Integer teamSysNo = message.getTeamSysNo();
		Long idCardType = message.getIdCardType();
		String idCardNumber = message.getIdCardNumber();
		String projectCode = message.getProjectCode();
		Long sendId = message.getSendId();
		Long receiveId = message.getReceiveId();
		String teamName = teamMasterMapper.getTeamInfoByTeamSysNo(teamSysNo).getTeamName();
		String workerName = this.workerMasterMapper
				.searchWorkerByIdCardTypeAndIdCardNumber(idCardNumber, idCardType.intValue()).getWorkerName();
		Long workerId = this.workerMasterMapper
				.searchWorkerByIdCardTypeAndIdCardNumber(idCardNumber, idCardType.intValue()).getId();
		String content = "你已加入了" + teamName + "班组";
		String resultContent = workerName + "已加入了" + teamName + "班组";
		TeamMemberShip teamMemberShip = new TeamMemberShip(teamSysNo, idCardType.intValue(), idCardNumber);
		teamMemberShip.setTeamWorkerType(1);
		if (teamMasterService.getWorkerInProjectStatus(projectCode, Integer.parseInt(String.valueOf(idCardType)),
				idCardNumber)) {
			throw new XywgException(771, "你已在该项目中！");
		} else {
			teamMasterMapper.addMember(teamMemberShip);
			// 调插入人脸的方法
			inputFaceAndProjectCode(workerId.toString(), projectCode);
			// 再在人员项目关系表中插入一条数据
			Map<String, Object> map = new HashMap<>();
			map.put("idCardType", idCardType);
			map.put("idCardNumber", idCardNumber);
			map.put("teamSysNo", teamSysNo);
			String workCode = workerMasterMapper.getWorkTypeById(map);// 查这个人的工种
			String organizationCode = teamMasterMapper.getOrganizationCodeByTeamCode(map);

			/**
			 * 获取当前时间 类型为sql格式的date
			 */
			Date time = new Date(new java.util.Date().getTime());
			ProjectWorker projectWorker = new ProjectWorker(projectCode, organizationCode, teamSysNo,
					idCardType.toString(), idCardNumber, workCode, 2, 0, 2, time);
			projectWorkerMapper.insert(projectWorker);

			// 插入与实名制对接接口表 xss
			IfaLabor ifaLabor = new IfaLabor("buss_project_worker", projectWorker.getId());
			ifaLaborMapper.insert(ifaLabor);

			// 接着在人员进退场历史插入一条数据
			EntryExitHistory entryExitHistory = new EntryExitHistory(projectCode, organizationCode,
					idCardType.toString(), idCardNumber, 0);
			entryExitHistoryMapper.addHistry(entryExitHistory);

			// 往公司工人关系表插一条数据
			if (contractorWorkerMapper.getByIdCardAndOrganization(idCardNumber,
					Integer.parseInt(String.valueOf(idCardType)), organizationCode) == null) {
				ContractorWorker contractorWorker = new ContractorWorker(idCardType.intValue(), idCardNumber,
						workerName, organizationCode);
				contractorWorkerMapper.insert(contractorWorker);
			}
			// 创建二条回复数据 班组长和自己各一条
			String receiveEquipment = workerMasterMapper.getEquipmentByUserId(message.getSendId());
			String returnEquipment = workerMasterMapper.getEquipmentByUserId(message.getReceiveId());
			Message resultMessage = new Message(projectCode, teamSysNo, idCardType, idCardNumber, sendId, receiveId, 1,
					0, 2, content);// 工人自己的
			resultMessage.setEquipment(returnEquipment);
			Message returnMessage = new Message(projectCode, teamSysNo, idCardType, idCardNumber, receiveId, sendId, 1,
					0, 2, resultContent);// 给班组长的
			returnMessage.setEquipment(receiveEquipment);
			messageMapper.createMessage(resultMessage);
			messageMapper.createMessage(returnMessage);
			// 在与实名制对接的表中插一条数据
			try {
				PushUtil.getInstance().advancedPush(1, returnMessage.getEquipment(), returnMessage.getContent());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 班组长扫码加工人
	 *
	 * @param projectCode
	 * @param idCardType
	 * @param idCardNumber
	 * @param teamSysNo
	 * @author yuanyang
	 */
	@Override
	@Transactional(readOnly = false)
	public void addWorkerByQr(String projectCode, Integer idCardType, String idCardNumber, Integer teamSysNo) {
		// 根据身份证获取工人信息
		WorkerMaster wm = this.workerMasterMapper.searchWorkerByIdCardTypeAndIdCardNumber(idCardNumber, idCardType);
		Long workerId = wm.getId();
		String workerName = wm.getWorkerName();
		// Long workerId =
		// this.workerMasterMapper.searchWorkerByIdCardTypeAndIdCardNumber(idCardNumber,
		// idCardType).getId();
		// String workerName =
		// this.workerMasterMapper.searchWorkerByIdCardTypeAndIdCardNumber(idCardNumber,
		// idCardType.intValue()).getWorkerName();
		TeamMemberShip teamMemberShip = new TeamMemberShip(teamSysNo, idCardType, idCardNumber);
		teamMemberShip.setTeamWorkerType(1);
		// 在人员班组关系表中插入一条数据

		if (teamMasterService.getWorkerInProjectStatus(projectCode, idCardType, idCardNumber)) {
			throw new XywgException(771, "此工人已在该项目中！");
		} else {
			teamMasterMapper.addMember(teamMemberShip);
			// 调插入人脸的方法
			inputFaceAndProjectCode(workerId.toString(), projectCode);
			// 再在人员项目关系表中插入一条数据
			Map<String, Object> map = new HashMap<>();
			map.put("idCardType", idCardType);
			map.put("idCardNumber", idCardNumber);
			map.put("teamSysNo", teamSysNo);
			String workCode = workerMasterMapper.getWorkTypeById(map);// 查这个人的工种
			String organizationCode = teamMasterMapper.getOrganizationCodeByTeamCode(map);

			/**
			 * 获取当前时间 类型为sql格式的date
			 */
			// Date time = new Date(new java.util.Date().getTime());
			ProjectWorker projectWorker = new ProjectWorker(projectCode, organizationCode, teamSysNo,
					idCardType.toString(), idCardNumber, workCode, 2, 0, 2, new Date());
			projectWorkerMapper.insert(projectWorker);

			// 插入与实名制对接接口表 xss
			IfaLabor ifaLabor = new IfaLabor("buss_project_worker", projectWorker.getId());
			ifaLaborMapper.insert(ifaLabor);

			// 接着在人员进退场历史插入一条数据
			EntryExitHistory entryExitHistory = new EntryExitHistory(projectCode, organizationCode,
					idCardType.toString(), idCardNumber, 0);
			entryExitHistoryMapper.addHistry(entryExitHistory);

			// 往公司工人关系表插一条数据
			if (contractorWorkerMapper.getByIdCardAndOrganization(idCardNumber,
					Integer.parseInt(String.valueOf(idCardType)), organizationCode) == null) {
				ContractorWorker contractorWorker = new ContractorWorker(idCardType.intValue(), idCardNumber,
						workerName, organizationCode);
				contractorWorkerMapper.insert(contractorWorker);
			}
		}
	}

	/**
	 * 班组长同意加入班组 xieshuaishuai
	 *
	 * @param id
	 */
	@Override
	@Transactional(readOnly = false)
	public void teamAgreeWorkerAdd(Integer id) {
		// 首先把未读改为已读
		messageMapper.changeIsRead(id);
		// 然后在人员班组关系表中插入一条数据
		Message message = messageMapper.getMessageById(id);
		Integer teamSysNo = message.getTeamSysNo();
		Long idCardType = message.getIdCardType();
		String idCardNumber = message.getIdCardNumber();
		String projectCode = message.getProjectCode();
		Long sendId = message.getSendId();
		Long receiveId = message.getReceiveId();
		String teamName = teamMasterMapper.getTeamInfoByTeamSysNo(teamSysNo).getTeamName();
		String workerName = this.workerMasterMapper
				.searchWorkerByIdCardTypeAndIdCardNumber(idCardNumber, idCardType.intValue()).getWorkerName();
		Long workerId = this.workerMasterMapper
				.searchWorkerByIdCardTypeAndIdCardNumber(idCardNumber, idCardType.intValue()).getId();
		String content = "你已加入了" + teamName + "班组";
		String resultContent = workerName + "已加入了" + teamName + "班组";
		TeamMemberShip teamMemberShip = new TeamMemberShip(teamSysNo, idCardType.intValue(), idCardNumber);
		teamMemberShip.setTeamWorkerType(1);
		if (teamMasterService.getWorkerInProjectStatus(projectCode, Integer.parseInt(String.valueOf(idCardType)),
				idCardNumber)) {
			throw new XywgException(771, "你已在该项目中！");
		} else {
			teamMasterMapper.addMember(teamMemberShip);

			// 调插入人脸的方法
			inputFaceAndProjectCode(workerId.toString(), projectCode);

			// 再在人员项目关系表中插入一条数据
			Map<String, Object> map = new HashMap<>();
			map.put("idCardType", idCardType);
			map.put("idCardNumber", idCardNumber);
			map.put("teamSysNo", teamSysNo);
			String workCode = workerMasterMapper.getWorkTypeById(map);// 查这个人的工种
			String organizationCode = teamMasterMapper.getOrganizationCodeByTeamCode(map);

			/**
			 * 获取当前时间 类型为sql格式的date
			 */
			Date time = new Date(new java.util.Date().getTime());
			ProjectWorker projectWorker = new ProjectWorker(projectCode, organizationCode, teamSysNo,
					idCardType.toString(), idCardNumber, workCode, 2, 0, 2, time);
			projectWorkerMapper.insert(projectWorker);

			// 在与实名制对接的表中插一条数据
			IfaLabor ifaLabor = new IfaLabor("buss_project_worker", projectWorker.getId());
			ifaLaborMapper.insert(ifaLabor);

			// 往公司工人关系表插一条数据
			if (contractorWorkerMapper.getByIdCardAndOrganization(idCardNumber,
					Integer.parseInt(String.valueOf(idCardType)), organizationCode) == null) {
				ContractorWorker contractorWorker = new ContractorWorker(idCardType.intValue(), idCardNumber,
						workerName, organizationCode);
				contractorWorkerMapper.insert(contractorWorker);
			}
			// 接着在人员进退场历史插入一条数据
			EntryExitHistory entryExitHistory = new EntryExitHistory(projectCode, organizationCode,
					idCardType.toString(), idCardNumber, 0);
			entryExitHistoryMapper.addHistry(entryExitHistory);
			// 创建二条回复数据 班组长和自己各一条
			String receiveEquipment = workerMasterMapper.getEquipmentByUserId(message.getSendId());
			String returnEquipment = workerMasterMapper.getEquipmentByUserId(message.getReceiveId());
			Message resultMessage = new Message(projectCode, teamSysNo, idCardType, idCardNumber, receiveId, sendId, 1,
					0, 2, content);// 工人自己的
			resultMessage.setEquipment(receiveEquipment);
			Message returnMessage = new Message(projectCode, teamSysNo, idCardType, idCardNumber, sendId, receiveId, 1,
					0, 2, resultContent);// 给班组长的
			returnMessage.setEquipment(returnEquipment);
			messageMapper.createMessage(resultMessage);
			messageMapper.createMessage(returnMessage);

			try {
				PushUtil.getInstance().advancedPush(1, resultMessage.getEquipment(), resultMessage.getContent());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 工人拒绝加入班组 xieshuaishuai
	 *
	 * @param id
	 */
	@Override
	@Transactional(readOnly = false)
	public void workerDisagreeAddTeam(Integer id) {
		// 首先把未读改为已读
		messageMapper.changeIsRead(id);
		// 创建一条回复数据
		Message message = messageMapper.getMessageById(id);
		Integer teamSysNo = message.getTeamSysNo();
		Long idCardType = message.getIdCardType();
		Long sendId = message.getSendId();
		Long receiveId = message.getReceiveId();
		String idCardNumber = message.getIdCardNumber();
		String projectCode = message.getProjectCode();
		String teamName = teamMasterMapper.getTeamInfoByTeamSysNo(teamSysNo).getTeamName();
		String workerName = this.workerMasterMapper
				.searchWorkerByIdCardTypeAndIdCardNumber(idCardNumber, idCardType.intValue()).getWorkerName();
		String content = "你拒绝加入了" + teamName + "班组";
		String resultContent = workerName + "拒绝加入了" + teamName + "班组";
		String receiveEquipment = workerMasterMapper.getEquipmentByUserId(message.getSendId());
		String returnEquipment = workerMasterMapper.getEquipmentByUserId(message.getReceiveId());
		// 创建一条回复数据
		Message resultMessage = new Message(projectCode, teamSysNo, idCardType, idCardNumber, sendId, receiveId, 1, 0,
				2, content);// 工人自己的
		resultMessage.setEquipment(returnEquipment);
		Message returnMessage = new Message(projectCode, teamSysNo, idCardType, idCardNumber, receiveId, sendId, 1, 0,
				2, resultContent);// 给班组长的
		returnMessage.setEquipment(receiveEquipment);
		messageMapper.createMessage(resultMessage);
		messageMapper.createMessage(returnMessage);
		try {
			PushUtil.getInstance().advancedPush(1, returnMessage.getEquipment(), returnMessage.getContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 班组长拒绝工人加入班组
	 *
	 * @param id
	 */
	@Override
	@Transactional(readOnly = false)
	public void teamDisagreeWorkerAdd(Integer id) {
		// 首先把未读改为已读
		messageMapper.changeIsRead(id);
		// 创建一条回复数据
		Message message = messageMapper.getMessageById(id);
		Integer teamSysNo = message.getTeamSysNo();
		Long idCardType = message.getIdCardType();
		Long sendId = message.getSendId();
		Long receiveId = message.getReceiveId();
		String idCardNumber = message.getIdCardNumber();
		String projectCode = message.getProjectCode();
		String teamName = teamMasterMapper.getTeamInfoByTeamSysNo(teamSysNo).getTeamName();
		String workerName = this.workerMasterMapper
				.searchWorkerByIdCardTypeAndIdCardNumber(idCardNumber, idCardType.intValue()).getWorkerName();
		String content = "你被拒绝加入了" + teamName + "班组";
		String resultContent = "你拒绝了" + workerName + "加入了" + teamName + "班组";
		String receiveEquipment = workerMasterMapper.getEquipmentByUserId(message.getSendId());
		String sendEquipment = workerMasterMapper.getEquipmentByUserId(message.getReceiveId());
		// 创建一条回复数据
		Message resultMessage = new Message(projectCode, teamSysNo, idCardType, idCardNumber, receiveId, sendId, 1, 0,
				2, content);// 工人自己的
		resultMessage.setEquipment(receiveEquipment);
		Message returnMessage = new Message(projectCode, teamSysNo, idCardType, idCardNumber, sendId, receiveId, 1, 0,
				2, resultContent);// 给班组长的
		returnMessage.setEquipment(sendEquipment);
		messageMapper.createMessage(resultMessage);
		messageMapper.createMessage(returnMessage);
		try {
			PushUtil.getInstance().advancedPush(1, resultMessage.getEquipment(), resultMessage.getContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * APP工人申请加入班组 2018年5月29日 下午3:55:08
	 *
	 * @author wangshibo
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean addClass(AppAddClassDto addClassDto) {
		TeamMaster byId = teamService.getById(addClassDto.getTeamId());
		// 判断工人是否加入过项目
		if (projectWorkerService.isInProject(byId.getProjectCode(), addClassDto.getIdCardType(),
				addClassDto.getIdCardNumber())) {
			throw new XywgException(703, "您已加入此项目！");
		}
		Message message = new Message();
		message.setProjectCode(byId.getProjectCode());
		message.setTeamSysNo(byId.getTeamSysNo());
		message.setSendId(addClassDto.getSendId().longValue());
		message.setReceiveId(addClassDto.getReceiveId().longValue());
		message.setIdCardType(addClassDto.getIdCardType().longValue());
		message.setIdCardNumber(addClassDto.getIdCardNumber());
		message.setEquipment(workerMasterMapper.getEquipmentByUserId(addClassDto.getReceiveId().longValue()));
		String cotent = this.getWorkerByIdCard(addClassDto.getIdCardNumber(), addClassDto.getIdCardType())
				.getWorkerName() + "申请加入" + byId.getTeamName();
		message.setContent(cotent);

		if (workerMasterMapper.searchAddClass(message).size() > 0) {
			return false;
		} else {
			workerMasterMapper.addClass(message);
			try {
				PushUtil.getInstance().advancedPush(1, message.getEquipment(), message.getContent());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}

	}

	/**
	 * @param workerId
	 * @param projectId
	 * @param idImage
	 * @param name
	 * @param actionType
	 * @return
	 * @description 录入本地人脸库
	 * @author chupp
	 * @date 2018年5月29日
	 */
	@Override
	public boolean insertLocalFace(String workerId, String projectId, String idImage, String name, String actionType) {
		FaceAddModel face = new FaceAddModel(workerId, projectId, idImage, name, actionType);
		try {
			return FaceAdd.addLocalFace(face);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean addCertifications(AppAddCertificationsDto appAddCertificationsDto) {
		return this.workerMasterMapper.addCertifications(appAddCertificationsDto);
	}

	@Override
	public List<Map<String, Object>> getAll(Page<Map<String, Object>> page, Map<String, Object> map) {
		map.put("deptId", ShiroKit.getUser().getDeptId());
		return this.baseMapper.getAll(page, map, accountProjectService.getSwitchType());
	}

	/**
	 * app工人进退场（wangshibo）
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void changePersonJoinStatus(AppPersonJoinStatusDto personJoinStatusDto) {
		/**
		 * 获取所在班组
		 */
		TeamMaster byId = teamService.getById(personJoinStatusDto.getTeamId());
		/**
		 * 获取当前时间 类型为sql格式的date
		 */
		Date time = new Date(new java.util.Date().getTime());
		EntryExitHistory entryExitHistory = new EntryExitHistory();
		List<AppWorkerJoinDto> workerList = personJoinStatusDto.getWorkerList();
		/* 判断是进场还是退场 */
		if (personJoinStatusDto.getJoinStatus() == 2) {
			entryExitHistory.setType(0);
			for (AppWorkerJoinDto appWorkerJoinDto : workerList) {
				teamMasterMapper.workerJoin(appWorkerJoinDto.getIdCardType().toString(),
						appWorkerJoinDto.getIdCardNumber(), byId.getTeamSysNo());
			}
		} else {
			entryExitHistory.setType(1);
			for (AppWorkerJoinDto appWorkerJoinDto : workerList) {
				teamMasterMapper.workerOut(appWorkerJoinDto.getIdCardType().toString(),
						appWorkerJoinDto.getIdCardNumber(), personJoinStatusDto.getEvalute(), byId.getTeamSysNo());
			}
		}
		/* 生成进退场履历 */
		entryExitHistory.setOrganizationCode(byId.getOrganizationCode());
		entryExitHistory.setProjectCode(personJoinStatusDto.getProjectCode());
		entryExitHistory.setDate(time);
		for (AppWorkerJoinDto appWorkerJoinDto : workerList) {
			entryExitHistory.setIdCardType(appWorkerJoinDto.getIdCardType().toString());
			entryExitHistory.setIdCardNumber(appWorkerJoinDto.getIdCardNumber());
			entryExitHistoryMapper.insert(entryExitHistory);
		}
	}

	/**
	 * 根据证件类型和证件编号更新
	 *
	 * @param workerMaster
	 */
	@Override
	public void updateByIdCardTypeAndIdCardNumber(WorkerMaster workerMaster) {
		workerMasterMapper.updateByIdCardTypeAndIdCardNumber(workerMaster);
	}

	@Override
	public void updateEquipmentById(Map<String, String> map) {
		workerMasterMapper.updateEquipmentById(map);
	}

	/**
	 * @param projectCode
	 * @param images
	 * @param sourceType
	 * @return
	 * @description 人脸图片查找信息（考勤第二接口）
	 * @author chupp
	 * @date 2018年5月30日
	 * @see com.xywg.admin.modular.worker.service.IWorkerMasterService#findFace(java.lang.String,
	 *      java.lang.String, int)
	 */
	@Override
	public WorkerMaster findFace(String projectCode, String images, int sourceType) {
		ProjectMaster pm = projectMasterMapper.getProjectByProjectCode(projectCode);
		if (pm == null) {
			throw new XywgException(BizExceptionEnum.PROJECT_NO_EXIST);
		}
		try {
			String workerId = null;
			try {
				if (sourceType == 0) {// 百度
					workerId = FaceIdentify.identify(images, String.valueOf(pm.getId()), "0");
				} else if (sourceType == 1) {// 上海大学
					workerId = FaceIdentify.identifyLocal(images, String.valueOf(pm.getId()));
				} else {
					LOG.info("sourceType:" + sourceType);
				}
			} catch (Exception e) {
				throw new XywgException(BizExceptionEnum.FIND_FACE_FAILED);
			}
			if (workerId == null) {
				throw new XywgException(BizExceptionEnum.FACE_GET_NO_EXIST);
			}
			WorkerMaster wm = workerMasterMapper.selectById(workerId);
			if (wm == null) {
				throw new XywgException(BizExceptionEnum.FIND_FACE_WORKER_NO_EXIST);
			} else {
				return wm;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new XywgException(BizExceptionEnum.FIND_FACE_FAILED);
		}
	}

	/**
	 * 获取工人详细信息
	 *
	 * @param idCardType   证件类型
	 * @param idCardNumber 证件号
	 * @return
	 */
	@Override
	public AppWorkerMasterDto getById(Integer idCardType, String idCardNumber) {
		AppWorkerMasterDto appWorkerMasterDto = workerMasterMapper.getById(idCardType, idCardNumber);
		return appWorkerMasterDto;
	}

	/**
	 * 获取工人详细信息
	 *
	 * @param idCardType   证件类型
	 * @param idCardNumber 证件号
	 * @return
	 */
	@Override
	public AppWorkerMasterDto v1111GetById(String projectCode, Integer idCardType, String idCardNumber) {
		AppWorkerMasterDto appWorkerMasterDto = workerMasterMapper.v1111GetById(projectCode, idCardType, idCardNumber);
		return appWorkerMasterDto;
	}

	@Override
	@Transactional
	public boolean invitationAddTeam(AppInvitationAddTeamDto addTeamDto) {
		TeamMaster byId = teamService.getById(addTeamDto.getTeamId());
		// 判断工人是否加入过项目
		if (projectWorkerService.isInProject(byId.getProjectCode(), addTeamDto.getIdCardType(),
				addTeamDto.getIdCardNumber())) {
			throw new XywgException(703, "该工人已加入此项目！");
		}
		Message message = new Message();
		message.setProjectCode(byId.getProjectCode());
		message.setTeamSysNo(byId.getTeamSysNo());
		message.setIdCardType(addTeamDto.getIdCardType().longValue());
		message.setIdCardNumber(addTeamDto.getIdCardNumber());
		message.setSendId(addTeamDto.getSendId().longValue());
		message.setReceiveId(addTeamDto.getReceiveId().longValue());
		message.setEquipment(workerMasterMapper.getEquipmentByUserId(addTeamDto.getReceiveId().longValue()));
		String cotent = this
				.getWorkerByIdCard(addTeamDto.getTeamLeaderIdCardNumber(), addTeamDto.getTeamLeaderIdCardType())
				.getWorkerName() + "邀请你加入" + byId.getTeamName();
		message.setContent(cotent);
		if (workerMasterMapper.searchInvitation(message).size() > 0) {
			return false;
		} else {
			workerMasterMapper.invitationAddTeam(message);
			try {
				PushUtil.getInstance().advancedPush(1, message.getEquipment(), message.getContent());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
	}

	@Override
	public List<Map<String, Object>> getWorkerMasterByProjectCode(Page<Map<String, Object>> page,
			Map<String, Object> map) {
		// 添加企业信用代码集合
		List<String> organizationCodeList = deptService.getUserDeptAndSubdivisionOrganizationCode();
		map.put("organizationCodeList", organizationCodeList);
		return workerMasterMapper.getWorkerMasterByProjectCode(page, map);
	}

	@Override
	public List<Map<String, Object>> getAppWorkerMasterByProjectCode(String projectCode) {
		return workerMasterMapper.getAppWorkerMasterByProjectCode(projectCode);
	}

	@Override
	public List<Map<String, Object>> v116GetAppWorkerMasterByProjectCode(String projectCode, Integer pageNo,
			Integer pageSize) {
		return workerMasterMapper.v116GetAppWorkerMasterByProjectCode(projectCode, (pageNo - 1) * pageSize, pageSize);
	}

	@Override
	public List<Map<String, Object>> getWorkerMoneyTable(Map<String, Object> map) {
		if (map.containsKey("workerIdList") && map.containsKey("projectCode")) {
			List<String> idsList = com.alibaba.fastjson.JSON.parseArray(map.get("workerIdList").toString(),
					String.class);
			if (idsList.size() > 0) {
				map.put("workerIdList", idsList);
				return workerMasterMapper.getWorkerMoneyTable(map);
			}
		}
		return new ArrayList<Map<String, Object>>();
	}

	@Override
	public WorkerMaster getByCellPhone(String cellPhone) {
		return this.baseMapper.getByCellPhone(cellPhone);
	}

	/**
	 * hujingyun 根据工人id删除工人信息和工人登录信息
	 *
	 * @param map
	 */
	@Override
	@Transactional
	public void deleteWorkerInfoAndLoginInfo(Map<String, Object> map) {
		// 删除工人公司关联表
		this.baseMapper.delWorker(map);
		// 根据手机号删除用户信息表
		// userMapper.deleteUserByPhone(workerMaster.getCellPhone());
	}

	@Override
    public void deletes(List<String> idList) {
		if (idList != null) {
			// 批量删除工人班组关系
			this.teamMemberService.batchDeleteByPwId(idList);
			// 批量删除进退场关系
			this.entryExitHistoryService.batchDeleteExitByPwId(idList);
			// 批量删除工人项目关系
			this.projectWorkerMapper.batchDeleteInfoById(idList);
		}
	}

	/**
	 * 根据项目id班组编号查询出人员列表 xieshuaishuai
	 */
	@Override
	public List<Map<String, Object>> getPersonListByTeamCode(Map<String, Object> map, Page page) {
		return workerMasterMapper.getPersonListByTeamCode(map, page);
	}

	@Override
	public void updateWorkKind(AppUpdateWorkerKindDto appUpdateWorkerKindDto) {
		workerMasterMapper.updateWorkKind(appUpdateWorkerKindDto);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateById(WorkerMaster entity) {
		if (StringUtils.isNotBlank(entity.getCellPhone())) {
			WorkerMaster e = this.baseMapper.getByCellPhone(entity.getCellPhone());
			if (e != null && !(e.getIdCardNumber().equals(entity.getIdCardNumber())
					&& e.getIdCardType() == entity.getIdCardType())) {
				throw new XywgException(600, "手机号已被使用");
			}
			// 根据身份证修改工人号码
			this.baseMapper.updateAccountByIdCardNumber(entity.getCellPhone(), entity.getIdCardNumber(),
					entity.getIdCardType());
		}
		ProjectWorker projectWorker = new ProjectWorker();
		int teamSysNo = 0;
		TeamMemberShip member = null;
		HashMap<String, Object> memberMap = new HashMap<String, Object>(16);
//		if (StringUtils.isNotBlank(entity.getFace())) {
////			entity.setFace(entity.getHeadImage());
//			entity.setIsFace(1);
//			projectWorker.setJoinStatus(2);
//		}
		if (StringUtils.isNotBlank(entity.getFace())) {
			entity.setIsFace(1);
			projectWorker.setJoinStatus(2);
		}
		if (StringUtils.isNotBlank(entity.getTeamSysNo())) {
			teamSysNo = Integer.valueOf(entity.getTeamSysNo());
			member = new TeamMemberShip(teamSysNo, 1, entity.getIdCardNumber());
			if (entity.getIsTeamLeader() == 1) {
				// 是班组老板 给班组设置班组老板
				teamMasterService.removeTeamLeader(teamSysNo);
				teamMasterService.setTeamLeaderByTeamSysNo(teamSysNo, entity.getIdCardNumber(), entity.getWorkerName());
				member.setTeamWorkerType(0);
				memberMap.put("teamWorkType", "0");
			} else {
				memberMap.put("teamWorkType", "1");
				member.setTeamWorkerType(1);
			}
			// 班组有修改改掉老班组
			if (entity.getIsTeamLeader() == 1) {
				if (entity.getTeamSysNo() == null) {
					throw new XywgException(800, "请选择班组不能为空");
				}
				// 是班组老板 给班组设置班组老板
				teamMasterService.removeTeamLeader(teamSysNo);
				// 是班组老板 给班组设置班组老板
				teamMasterService.setTeamLeaderByTeamSysNo(teamSysNo, entity.getIdCardNumber(), entity.getWorkerName());
				member.setTeamWorkerType(0);
				projectWorker.setTeamWorkerType(0);
			} else {
				member.setTeamWorkerType(1);
				projectWorker.setTeamWorkerType(1);
			}
		}
		// 判断之前是否有记录
		if (entity.getPwId() != null) {
			// 根据工人项目id和身份证号查询老的班组编号
			Integer oldTeamSysNo = teamMapper.getOldTeamSysNoByProjectId(entity.getPwId(), entity.getIdCardNumber(),
					entity.getIdCardType());
			// 工种 劳务公司修改project_worker表字段

			// 判断卡是否绑定
			if (StringUtils.isNotBlank(entity.getCardNumber())) {
				String cardNumber = entity.getCardNumber();
				projectWorkerService.getCountByCardNumber(cardNumber, entity.getPwId());
				projectWorker.setCardNumber(cardNumber);
			}
			projectWorker.setId(entity.getPwId());
			projectWorker.setWorkTypeCode(entity.getWorkTypeCode());
			projectWorker.setProjectCode(entity.getProjectCode());
			projectWorker.setTeamSysNo(teamSysNo);
			projectWorker.setIsDel(0);

			ProjectWorker oldPw = projectWorkerService.selectById(projectWorker.getId());
			this.teamMasterService.validateProJectAndWorker(oldPw.getProjectCode(), projectWorker.getProjectCode(),
					oldTeamSysNo, member);

			this.projectWorkerService.updateById(projectWorker);
			this.teamMasterService.updateByOldTeamSysNo(oldTeamSysNo, member);
			dispatchUser(entity);
		} else if (!"".equals(entity.getProjectCode()) && !"".equals(entity.getTeamSysNo())) {
			TeamMaster teamMaster = teamMasterMapper.getTeamInfoByTeamSysNo(teamSysNo);
			memberMap.put("teamCode", entity.getTeamSysNo());
			memberMap.put("idStrs", entity.getId().toString());
			memberMap.put("from", "worker");
			teamMasterService.addMember(memberMap);
		}
		// WorkerMaster w = this.getWorkerByIdCard(entity.getIdCardNumber(),
		// entity.getIdCardType());
		// userService.updateAccount(entity.getCellPhone(), w.getCellPhone());//修改账号
		// 修改人脸
		// if(!entity.getFace().equals(entity.getHeadImage())) {
		// }

		boolean bl = retBool(baseMapper.updateById(entity));
		 if(bl) {
		 ProjectMaster projectMaster =
		 projectMasterMapper.getProjectByProjectCode(entity.getProjectCode());
		 if(projectMaster.getIsSynchro().intValue() == 1) {
		 //修改成功之后插入zbus
		 sendToZbus(this.baseMapper.findById(entity.getId()), false);
		 }
		 }
		return bl;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String Import(MultipartFile excelFile, HttpServletRequest request) throws Exception {
		int n = 0;
		int maxCellNum = 19;
		List<WorkerMasterImport> WorkerList = new ArrayList<WorkerMasterImport>();
		File file = ExcelImportUtils.multipartFileToFile(excelFile, request); // 文件类型专程File类型
		List<Map<String, Map<String, Object>>> list = ExcelImportUtils.getContent(new FileInputStream(file),
				file.getName(), WorkerMasterImport.getTitleMap(), maxCellNum);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				WorkerMasterImport workerMasterImport = new WorkerMasterImport();
				Map<String, Map<String, Object>> map = list.get(i);
				for (String key : map.keySet()) {
					Map<String, Object> valueMap = map.get(key);
					String value = valueMap.get(ExcelImportUtils.value).toString();// 单元格的值
					int columnIndex = (int) valueMap.get(ExcelImportUtils.columnIndex);// 列
					int rowIndex = (int) valueMap.get(ExcelImportUtils.rowIndex);// 行
					String title = valueMap.get(ExcelImportUtils.title).toString();// 标题内容
					PropertyUtils.setProperty(workerMasterImport, key, getValue(value, columnIndex, rowIndex, title));
				}
				WorkerList.add(workerMasterImport);
			}
			for (int i = 0; i < WorkerList.size(); i++) {
				this.importWorker(WorkerList.get(i), i + 1);
				n++;
			}
		}

		return "导入" + n + "条数据";
	}

	/**
	 * 导入解析方法
	 *
	 * @return
	 */
	public Object getValue(String value, int columnIndex, int rowIndex, String title) {
		String[] checkColumn = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" }; // 不能为空的标题列
		if (Arrays.asList(checkColumn).contains(String.valueOf(columnIndex)) && value.equals("")) {
			throw new XywgException(600, ExcelImportUtils.nullErrorMsg(rowIndex, title));// 如果为空值，抛出异常
		}
		if (columnIndex == 2 || columnIndex == 13 || columnIndex == 15) {// 日期个格式校验
			return ExcelImportUtils.isValidDate(value, rowIndex, title);
		}
		if (columnIndex == 0) {
			ExcelImportUtils.lenthCheck(25, value, rowIndex, title);
			return value;
		} else if (columnIndex == 1) {
			if (ExcelImportUtils.isPhone(value)) {
				return value;
			} else {
				throw new XywgException(600, ExcelImportUtils.errorMsg(rowIndex, title));// 手机号不符合规范
			}
		} else if (columnIndex == 3) {
			if (StringUtils.isNotBlank(value)) {
				return dictService.selectNumByName("工种字典数据", value).toString();
			}
			return null;
		} else if (columnIndex == 4) {
			if (StringUtils.isNotBlank(value)) {
				return dictService.selectNumByName("人员证件类型", value);
			}
			return null;
		} else if (columnIndex == 5) {
			ExcelImportUtils.lenthCheck(20, value, rowIndex, title);
			return value;
		} else if (columnIndex == 6) {
			if (StringUtils.isNotBlank(value)) {
				return Integer.valueOf(value.equals("男") ? 0 : 1);
			}
			return 0;
		} else if (columnIndex == 7) {
			if (StringUtils.isNotBlank(value)) {
				return dictService.selectNumByName("民族", value).toString();
			}
			return null;
		} else if (columnIndex == 8) {
			return iAreaService.getIdByShortName(value);
		} else if (columnIndex == 9) {
			ExcelImportUtils.lenthCheck(100, value, rowIndex, title);
			return value;
		} else if (columnIndex == 10) {
			if (StringUtils.isNotBlank(value)) {
				return dictService.selectNumByName("政治面貌", value);
			}
			return null;
		} else if (columnIndex == 11) {
			if (StringUtils.isNotBlank(value)) {
				return dictService.selectNumByName("文化程度", value);
			}
			return null;
		} else if (columnIndex == 12) {
			if (StringUtils.isNotBlank(value)) {
				return Integer.valueOf(value.equals("是") ? 1 : 0);
			}
			return 0;
		} else if (columnIndex == 14) {
			if (StringUtils.isNotBlank(value)) {
				return Integer.valueOf(value.equals("是") ? 1 : 0);
			}
			return 0;
		} else if (columnIndex == 16) {
			ExcelImportUtils.lenthCheck(25, value, rowIndex, title);
			return value;
		} else if (columnIndex == 17) {
			if (ExcelImportUtils.isPhone(value)) {
				return value;
			} else {
				throw new XywgException(600, ExcelImportUtils.errorMsg(rowIndex, title));// 手机号不符合规范
			}
		} else if (columnIndex == 18) {
			ExcelImportUtils.lenthCheck(100, value, rowIndex, title);
			return value;
		} else {
			return String.valueOf(value);
		}
	}

	/**
	 * @description 获取实名制工人数据（南通）
	 * @author chupp
	 * @date 2018年7月26日
	 */
	@Override
	@Transactional
	public void savePersonnelFromSMZNT(Map<String, String> mnt) {
		// 获取实名制工人数据
		String jsonResult = HttpClientUtils.post(
				getSystemStringParam("httpUrlPrefix") + getSystemStringParam("savePersonnelFromSMZ"),
				new HashMap<String, Object>(), mnt);
		Map<String, Object> map = (Map<String, Object>) net.sf.json.JSONObject
				.toBean(net.sf.json.JSONObject.fromObject(jsonResult), Map.class);
		// 存在新的工人数据
		if (map.get("worker") != null) {
			JSONArray jsonArray = JSONArray.fromObject(map.get("worker"));
			List<PersonnelSmz> personnelList = JSONArray.toList(jsonArray, new PersonnelSmz(), new JsonConfig());
			String id = "0";
			if (personnelList.size() > 0) {
				// 获取FTP连接
				Random ra = new Random();
				String ran = "" + ra.nextInt(10) + ra.nextInt(10);
				String storeFilePath = "lwtgb_smz" + FileUtil.SEPARATOR + DateUtils.getCurrentDate("yyyy")
						+ FileUtil.SEPARATOR + DateUtils.getCurrentDate("MM") + FileUtil.SEPARATOR
						+ DateUtils.getCurrentDate("dd") + FileUtil.SEPARATOR + DateUtils.getCurrentDate("HH")
						+ FileUtil.SEPARATOR + ran + FileUtil.SEPARATOR;
				FTPClient connectFtp = null;
				try {
					connectFtp = FTPClientUtil.connectFtp(Constant.FTP_HOST, Constant.FTP_PORT, Constant.FTP_USERNAME,
							Constant.FTP_PASS_WORD, storeFilePath);
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("实名制接口(南通)工人数据FTP连接错误");
				}
				try {
					for (PersonnelSmz ps : personnelList) {
						long smzId = Long.parseLong(ps.getId());
						long lwtId = 0L;
						System.err.println("userId = "+ps.getUserId());
						WorkerMaster chWm = this.baseMapper.getWorkerByIdCard(ps.getUserId(), 1);
						if (chWm == null) {
							WorkerMaster wm = new WorkerMaster();
							wm.setWorkerName(ps.getName());
							wm.setIdCardType(1);
							wm.setIdCardNumber(ps.getUserId());
							if (ps.getGender() != null) {
								System.err.println("gender ="+ps.getGender());
								wm.setGender(ps.getGender() == "0" ? 1 : 0);
							} else {
								wm.setGender(0);
							}
							if (ps.getNation() == null) {
								wm.setNation("1");
							} else {
								System.err.println("nation ="+ps.getNation());
								wm.setNation(ps.getNation());
							}
							if (ps.getBirthday() == null) {
								wm.setBirthday(DateUtils.parseStandardDate(ps.getUserId().substring(6, 14)));
							} else {
								wm.setBirthday(DateUtils.parseStandardDate(ps.getBirthday()));
							}
							wm.setBirthPlaceCode(ps.getUserId().substring(0, 2) + "0000");
							wm.setAddress(ps.getHomeAddress());
							if (ps.getMobile() != null) {
								System.err.println("mobile ="+ps.getMobile());
								wm.setCellPhone(ps.getMobile());// 手机号码无实际意义
							} else {
								wm.setCellPhone("123");
							}
							if (ps.getKindCode() != null) {
								System.err.println("kindcode ="+ps.getKindCode());
								wm.setWorkTypeCode(ps.getKindCode());
							} else {
								wm.setWorkTypeCode("10");
							}
							wm.setIsAuth(3);
							wm.setCreateUser("smz");
							wm.setCreateDate(new Date());
							// 保存身份证人脸图片
							try {
								if (ps.getPhoto() != null) {
									String newName = UUID.randomUUID().toString().replace("-", "") + ".jpg";
									FTPClientUtil.uploadFile(new ByteArrayInputStream(Base64.decode(ps.getPhoto())),
											new String((newName).getBytes("utf-8"), "iso-8859-1"), connectFtp);
									wm.setHeadImage(storeFilePath + newName);
								} else {
									wm.setHeadImage("smz");
								}
							} catch (IOException e) {
								e.printStackTrace();
								throw new RuntimeException("实名制接口(南通)工人数据身份证图片转换错误");
							}
							// 保存实名制工人数据
							this.baseMapper.insert(wm);
							lwtId = wm.getId();
						} else {
							lwtId = chWm.getId();
						}
						// 保存主键关联表
						SmzLwt sl = new SmzLwt();
						sl.setSmzId(smzId);
						sl.setLwtId(lwtId);
						sl.setTableName("Personnel");
						smzLwtMapper.saveSmzLwt(sl);
						System.err.println("id = "+Long.parseLong(id));
						if (Long.parseLong(id) < smzId) {
							id = String.valueOf(smzId);
						}
					}
				} finally {
					if (connectFtp != null && connectFtp.isConnected()) {
						try {
							connectFtp.logout();
						} catch (IOException e) {
							e.printStackTrace();
							connectFtp = null;
						}
					}
				}
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("code", "1");
				m.put("no", id);
				// 响应保存成功信息
				HttpClientUtils.post(
						getSystemStringParam("httpUrlPrefix") + getSystemStringParam("savePersonnelFromSMZResponse"), m,
						mnt);
			}
		}
	}

	@Override
	public void download(HttpServletResponse res, HttpServletRequest req, Map<String, Object> params) {
		List<Map<String, Object>> workers = this.selectWorkersList(params);
		workers = (List<Map<String, Object>>) new WorkerWarpper(workers).warp();

//		workers = WorkerColUtil.getColsByIf(workers, params.get(EXPORTCOL).toString());
		// for (Map map : workers) {
		// map.put("gender", ConstantFactory.me().getDictsByName("性别", (Integer)
		// map.get("gender")));
		// map.put("idCardType", ConstantFactory.me().getDictsByName("人员证件类型", (Integer)
		// map.get("idCardType")));
		// map.put("cultureLevelType", ConstantFactory.me().getDictsByName("文化程度",
		// (Integer) map.get("cultureLevelType")));
		// if (map.get("workTypeCode") != null && map.get("workTypeCode") != "") {
		// map.put("workTypeCode", ConstantFactory.me().getDictsByName("工种字典数据",
		// Integer.parseInt(String.valueOf(map.get("workTypeCode")))));
		// } else {
		// map.put("workTypeCode", "");
		// }
		// map.put("politicsType", ConstantFactory.me().getDictsByName("政治面貌", (Integer)
		// map.get("politicsType")));
		// map.put("nation", ConstantFactory.me().getDictsByName("民族",
		// Integer.parseInt(String.valueOf(map.get("nation")))));
		// map.put("birthPlaceCode",
		// ConstantFactory.me().getAreaName(Integer.parseInt(String.valueOf(map.get("birthPlaceCode")))));
		// map.put("isAuth", (Integer) map.get("isAuth") == 0 ? "否" : "是");
		// map.put("isFace", (Integer) map.get("isFace") == 1 ? "是" : "否");
		// map.put("isJoined", (Integer) map.get("isJoined") == 1 ? "是" : "否");
		// map.put("hasBadMedicalHistory", (Integer) map.get("hasBadMedicalHistory") ==
		// 1 ? "是" : "否");
		// }
		// 构建下载文件时的文件名
		String fileName = "人员一览" + com.xywg.admin.core.util.DateUtils.getDate("yyyyMMddHHmmss");
		boolean isMSIE = ServletsUtils.isMSBrowser(req);
		BufferedInputStream bis = null;
		OutputStream os = null;
		try {
			if (isMSIE) {
				// IE浏览器的乱码问题解决
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} else {
				// 万能乱码问题解决
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}
			res.setContentType("application/vnd.ms-excel");
			res.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"+.xlsx");
			os = res.getOutputStream();

			ExcelUtils.getInstance().exportObjects2Excel(workers,
					ShiroKit.getUser().getIsEnterprise() == 1 ? WorkerMasterEntrExport.class
							: WorkerMasterProExport.class,
					true, os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param messageType
	 * @param idCardType
	 * @param idCardNumber
	 * @description 二维码消息推送 （String类型） 01:您所选项目并非当前工地 02:考勤成功 08:二维码失效（已超时）
	 *              09:二维码失效（已使用）
	 * @author chupp
	 * @date 2018年7月31日
	 */
	@Override
	public void sendMessageFromQr(String messageType, String idCardType, String idCardNumber) {
		WorkerMaster wm = workerMasterMapper.getWorkerByIdCard(idCardNumber, Integer.parseInt(idCardType));
		if (wm != null && wm.getCellPhone() != null && !wm.getCellPhone().isEmpty()) {
			User user = userMapper.getByAccountAPP(wm.getCellPhone(), "0");
			if (user != null && user.getEquipment() != null && !user.getEquipment().isEmpty()) {
				try {
					LOG.info("-----------推送开始");
					PushUtil.getInstance().advancedPushUnder(1, user.getEquipment(), messageType);
					LOG.info("-----------推送结束");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param workerMaster
	 * @return
	 * @description 人脸对比
	 * @author chupp
	 * @date 2018年8月2日
	 */
	@Override
	public Object compareFace(WorkerMaster workerMaster) {
		WorkerMaster chWm = workerMasterMapper.getWorkerByIdCard(workerMaster.getIdCardNumber(),
				workerMaster.getIdCardType());
		if (chWm == null || chWm.getFace() == null || chWm.getFace().isEmpty()) {
			throw new XywgException(BizExceptionEnum.WORKER_NO_EXIST);
		}
		String[] split = chWm.getFace().split(",");
		if (split.length == 0) {
			throw new XywgException(BizExceptionEnum.REPEAT_INPUT_FACE);
		}
		try {
			return FaceMatch.match(split[0], workerMaster.getIdImage()) ? 1 : 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<Map<String, Object>> getPersonListByTeamCodeNoPage(Map<String, Object> map) {
		return this.baseMapper.getPersonListByTeamCodeNoPage(map);
	}

	@Override
	public List<Map<String, Object>> queryWorkerByPosition(Map<String, Object> map, Page page) {
		map.put("deptId", ShiroKit.getUser().getDeptId());
		return this.baseMapper.queryWorkerByPosition(map, page);
	}

	@Override
	public List<WorkerMaster> getWorkerListByProjectCode(String projectCode) {
		return this.baseMapper.getWorkerListByProjectCode(projectCode);
	}

	@Override
	public WorkerMaster getWorkerByProjectWorker(String idCardNumber, Integer idCardType, String projectCode) {
		return this.baseMapper.getWorkerByProjectWorker(idCardNumber, idCardType, projectCode);
	}

	@Override
	public boolean sendPersonToSmzForJl( Map<String, String> m) {
		Object id = redisUtil.get("jinlan_user");
		Long personId = id == null ? 0L :Long.valueOf(String.valueOf(id));
		List<PersonMo> list = workerMasterMapper.sendPersonToSmzForJl(personId);
		if(list.size() == 0) {
			return false;
		}
		String json = JSONArray.fromObject(list).toString();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("laborUser", json);
		String jsonResult = HttpClientUtils
				.post(getSystemStringParam("httpUrlPrefix") + getSystemStringParam("sendJinglanWorker"), map, m);
		System.err.println("jsonResult = " + jsonResult);
		Map<String, Object> result = JSONUtil.toBean(jsonResult, Map.class);
		//获取实名制没有的信息单独发送
		if (jsonResult.contains("maxRelationId")) {
			Long numId =  SmzUtils.textToLong(jsonResult, result);
			getPersonFromLaborToOne(numId,m);
		}
		//拿到最大值放到redis ，
		if (result.containsKey("maxRecord")) {
			System.err.println("record =" + result.get("maxRecord"));
			Long numId = Long.valueOf(result.get("maxRecord").toString());
			redisUtil.set("jinlan_user",numId);
		}
		 
		
		if ("-2".equals(result.get("code"))) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean getPersonFromLaborToOne(Long id, Map<String, String> m) {
		List<PersonMo> list = workerMasterMapper.getPersonFromLaborToOne(id);
		if (list.size() > 0) {
			for (PersonMo p : list) {
				String pNation = p.getNation();
				String pBirthday = p.getBirthday();
				String pWorkeType = p.getKindCode();
				// String pImage = p.getImage();
				if (StringUtils.isNotBlank(pBirthday)) {
					pBirthday = pBirthday.replace("-", "");
					p.setBirthday(pBirthday);
				}
				if (StringUtils.isNotBlank(pNation) && pNation.length() == 1) {
					pNation = "0" + pNation;
					p.setNation(pNation);
				}
				if (StringUtils.isNotBlank(pWorkeType) && pWorkeType.length() == 2) {
					p.setKindCode("0" + pWorkeType);
				}
			}
			String json = JSONArray.fromObject(list).toString();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("laborUser", json);
			String jsonResult = HttpClientUtils
					.post(getSystemStringParam("httpUrlPrefix") + getSystemStringParam("saveManagerFromLabor"), map, m);
			System.err.println("worker send = " + jsonResult);
			Map<String, String> result = JSONUtil.toBean(jsonResult, Map.class);
			if ("-2".equals(result.get("code"))) {
				return false;
			} else {
				return true;
			}
		}
		return false;

	}
	
	
	

	// @Override
	// public boolean getPersonImageToLabor(int number, Map<String, String> m) {
	// List<PersonMo> list = workerMasterMapper.getPersonImageToLabor(number);
	// if (list.size() > 0) {
	// for (PersonMo p : list) {
	// String pImage = p.getImage();
	// if (StringUtils.isNotBlank(pImage)) {
	// try {
	// p.setImage(ImageUtil.GetImageStr(imageLocalPath + pImage));
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// String json = JSONArray.fromObject(list).toString();
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("laborUser", json);
	// String jsonResult =
	// HttpClientUtils.post("http://192.168.20.190:8080/erp-web/labor/addLablarUserImage.whtml",
	// map, m);
	// Map<String, String> result = JSONUtil.toBean(jsonResult, Map.class);
	// if (result.containsKey("record")) {
	// System.err.println("record =" + result.get("record"));
	// Long numId = Long.valueOf(result.get("record").toString());
	// List<Long> deviceRecordList =
	// ifaLaborMapper.getIdList("worker_master_photo");
	// if (deviceRecordList.size() > 0) {
	// ifaLaborMapper.updateNumber(numId, "worker_master_photo");
	// } else {
	// ifaLaborMapper.insert(new IfaLabor("worker_master_photo", numId));
	// }
	//
	// }
	// if ("-2".equals(result.get("code"))) {
	// return false;
	// } else {
	// return true;
	// }
	//
	// }
	// return false;
	// }


	@Override
	public void addWorkerMaster(List<Object> addList) {
		for (Object o : addList) {
			WorkerMaster workerMaster = new WorkerMaster();
			stringToDateException();
			try {
				org.apache.commons.beanutils.BeanUtils.copyProperties(workerMaster, o);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			Map<String,Long> workerMasterMap=baseMapper.selectIdByIdcard(workerMaster.getIdCardNumber());
			if (workerMasterMap.get("num")==0){
				insert(workerMaster);
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
	public List<WorkerMaster> queryWorkerMasterById(String organizationCode, Long id) {
		return this.baseMapper.queryWorkerMasterById(organizationCode,id) ;
	}



	@Override
	public List<Long> getPersonFromLabor(Map<String, String> m) {
		List<PersonMo> list = workerMasterMapper.getPersonFromLabor();
		if (list.size() > 0) {
			List<Long> ids = new ArrayList<Long>();
			for (PersonMo p : list) {
				ids.add(p.getId());
				String pNation = p.getNation();
				String pBirthday = p.getBirthday();
				String pWorkeType = p.getKindCode();
				if (StringUtils.isNotBlank(pBirthday)) {
					pBirthday = pBirthday.replace("-", "");
				} else {
					pBirthday = p.getIdCard().substring(6, 14);
				}
				p.setBirthday(pBirthday);
				// 判断民族格式
				if (StringUtils.isNotBlank(pNation) && pNation.length() == 1) {
					pNation = "0" + pNation;
				} else if (StringUtils.isBlank(pNation)) {
					pNation = "01";
				}
				if(p.getComId()==467L){
					p.setComId(100L);
				}
				p.setNation(pNation);
				// 判断人员工种
				if (StringUtils.isNotBlank(pWorkeType) && pWorkeType.length() == 2) {
					p.setKindCode("0" + pWorkeType);
				} else if (StringUtils.isBlank(pWorkeType)) {
					p.setKindCode("1000");
					p.setWorkTypeName("其他");
				}
			}
			String json = JSONArray.fromObject(list).toString();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("laborUser", json);
			String jsonResult = HttpClientUtils
					.post(getSystemStringParam("httpUrlPrefix") + getSystemStringParam("saveManagerFromLabor"), map, m);
			System.err.println("worker send = " + jsonResult);
			Map<String, String> result = JSONUtil.toBean(jsonResult, Map.class);
			if ("-2".equals(result.get("code"))) {
				return null;
			} else {
				return ids;
			}
		}
		return null;
	}

	@Override
	public boolean getPersonImageFromLabor(Map<String, String> m) {
		List<PersonMo> list = workerMasterMapper.getPersonImageToLabor();
		List<Long> ids = new ArrayList<Long>();
		if (list.size() > 0) {
			for (PersonMo p : list) {
		/*		if(p.getComId()==null||p.getComId()==467L){
					p.setComId(100L);
				}*/
				ids.add(p.getLwtRelationId());
				String pImage = p.getImage();
				if (StringUtils.isNotBlank(pImage)) {
					try {
						String image = ImageUtil.GetImageStr("http://lxjs.jsxywg.cn/labor/" + pImage);
						p.setImage(image);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
			String json = JSONArray.fromObject(list).toString();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("laborUser", json);
			String jsonResult = HttpClientUtils
					.post(getSystemStringParam("httpUrlPrefix") + getSystemStringParam("saveLablarUserImage"), map, m);
			System.err.println("jsonResult = " + jsonResult);
			Map<String, String> result = JSONUtil.toBean(jsonResult, Map.class);
			// if (result.containsKey("record")) {
			// System.err.println("record =" + result.get("record"));
			// Long numId = Long.valueOf(result.get("record").toString());
			// List<Long> deviceRecordList =
			// ifaLaborMapper.getIdList(Constant.BUSS_PROJECT_WORKER_IMAGE);
			// if (deviceRecordList.size() > 0) {
			// ifaLaborMapper.updateNumber(numId, Constant.BUSS_PROJECT_WORKER_IMAGE);
			// } else {
			// ifaLaborMapper.insert(new IfaLabor(Constant.BUSS_PROJECT_WORKER_IMAGE,
			// numId));
			// }
			// }
			if ("-2".equals(result.get("code"))) {
				return false;
			} else {
				ifaLaborMapper.delbatch(Constant.BUSS_PROJECT_WORKER_IMAGE, ids);
			}
		}
		return false;
	}

}
