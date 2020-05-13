package com.xywg.admin.modular.company.service.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.org.bouncycastle.crypto.RuntimeCryptoException;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.modular.company.dao.EntryExitHistoryMapper;
import com.xywg.admin.modular.company.dao.SubContractorMapper;
import com.xywg.admin.modular.company.model.EntryExitHistory;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.company.service.IEntryExitHistoryService;
import com.xywg.admin.modular.project.dao.ProjectMasterMapper;
import com.xywg.admin.modular.smz.dao.SmzLwtMapper;
import com.xywg.admin.modular.smz.model.PersonJoinSmz;
import com.xywg.admin.modular.smz.model.SmzCallBack;
import com.xywg.admin.modular.smz.model.SmzLwt;
import com.xywg.admin.modular.smz.utils.HttpClientUtils;
import com.xywg.admin.modular.team.dao.TeamMasterMapper;
import com.xywg.admin.modular.worker.dao.WorkerMasterMapper;
import com.xywg.admin.modular.worker.model.WorkerMaster;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * <p>
 * 进退场记录表 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-28
 */
@Service
public class EntryExitHistoryServiceImpl extends ServiceImpl<EntryExitHistoryMapper, EntryExitHistory> implements IEntryExitHistoryService {
	private static Logger log = LoggerFactory.getLogger(EntryExitHistoryServiceImpl.class);
	@Autowired
	private WorkerMasterMapper workerMasterMapper;
	
	@Autowired
	private ProjectMasterMapper projectMasterMapper;
	
	@Autowired
    private SubContractorMapper subContractorMapper;
    
    @Autowired
    private SmzLwtMapper smzLwtMapper;
    @Autowired
    private TeamMasterMapper teamMasterMapper;
    private static final Log LOG = LogFactory.getLog(EntryExitHistoryServiceImpl.class);
    private static Properties systemParams = new Properties();
	
	/**
	 * 加载配置文件
	 */
	static {
		try {
			systemParams.load(EntryExitHistoryServiceImpl.class.getClassLoader().getResourceAsStream("smz.properties"));
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
	
//	/**
//	 * 
//	 * @description 登录实名制获取token
//	 * @author chupp
//	 * @date 2018年4月27日
//	 * @return
//	 *
//	 */
//	@SuppressWarnings("unchecked")
//	protected Map<String,String> loginSMZ() {
//		String httpUrlPrefix = this.getSystemStringParam("httpUrlPrefix");
//		String loginUrl = this.getSystemStringParam("loginUrl");
//		String userName = this.getSystemStringParam("userName");
//		String password = this.getSystemStringParam("password");
//		Map<String,Object> map = new HashMap<>();
//		map.put("userName", userName);
//		map.put("password", password);
//		String result = HttpClientUtils.post(httpUrlPrefix + loginUrl, map);
//		Map<String,Object> m = (Map<String,Object>) net.sf.json.JSONObject.toBean(net.sf.json.JSONObject.fromObject(result), Map.class);
//		Map<String,String> headers = new HashMap<>();
//		headers.put("token", (String) m.get("token"));
//		return headers;
//	}
//	
//	/**
//	 * 
//	 * @description 登录实名制获取token
//	 * @author chupp
//	 * @date 2018年4月27日
//	 * @return
//	 *
//	 */
//	@SuppressWarnings("unchecked")
//	protected Map<String,String> loginSMZYC() {
//		String httpUrlPrefix = this.getSystemStringParam("httpUrlPrefixYC");
//		String loginUrl = this.getSystemStringParam("loginUrlYC");
//		String userName = this.getSystemStringParam("userNameYC");
//		String password = this.getSystemStringParam("passwordYC");
//		Map<String,Object> map = new HashMap<>();
//		map.put("userName", userName);
//		map.put("password", password);
//		String result = HttpClientUtils.post(httpUrlPrefix + loginUrl, map);
//		Map<String,Object> m = (Map<String,Object>) net.sf.json.JSONObject.toBean(net.sf.json.JSONObject.fromObject(result), Map.class);
//		Map<String,String> headers = new HashMap<>();
//		headers.put("token", (String) m.get("token"));
//		return headers;
//	}
	
	/**
	 * 
	 * @description 获取实名制进退场信息（盐城）
	 * @author chupp
	 * @date 2018年7月11日
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void savePersonJoinFromSMZYC(Map<String, String> myc) {
		//获取实名制进退场履历信息
		String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefixYC")
				+ getSystemStringParam("savePersonJoinFromSMZYC"), new HashMap<String,Object>(), myc);
		Map<String,Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
		//存在新的进退场履历信息
		if(map.get("projectJoin") != null) {
			JSONArray jsonArray = JSONArray.fromObject(map.get("projectJoin"));
			List<PersonJoinSmz> pjList = JSONArray.toList(jsonArray, new PersonJoinSmz(), new JsonConfig());
			String id = "0";
			List<SmzLwt> slList = smzLwtMapper.getLwtIdBySmzId(0, "Company");
			if(slList.size() == 0) throw new RuntimeException("实名制接口企业数据为空");
			SubContractor subContractor = subContractorMapper.selectById(slList.get(0).getLwtId());
			if(subContractor == null) throw new RuntimeException("实名制接口企业数据对象为空");
			if(pjList.size() > 0) {
				for(PersonJoinSmz pj : pjList) {
					long smzId = Long.parseLong(pj.getId());
					EntryExitHistory eeh = new EntryExitHistory();
					slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(pj.getProjectId()), "ProjectYC");
					if(slList.size() == 0) throw new RuntimeException("实名制接口进退场履历所属项目为空");
					Map<String, Object> chProject = projectMasterMapper.selectById(slList.get(0).getLwtId());
					if(chProject == null) throw new RuntimeException("实名制接口进退场履历所属项目对象为空");
					eeh.setProjectCode((String)chProject.get("projectCode"));
					eeh.setOrganizationCode(subContractor.getOrganizationCode());
					eeh.setIdCardType("1");
					slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(pj.getUserId()), "PersonnelYC");
					if(slList.size() != 0) {
						WorkerMaster wm = workerMasterMapper.selectById(slList.get(0).getLwtId());
						if(wm == null) throw new RuntimeException("实名制接口进退场履历所属工人对象为空");
						eeh.setIdCardNumber(wm.getIdCardNumber());
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						try {
							eeh.setDate(sdf.parse(pj.getCreateDate().replace("T", " ")));
						} catch (ParseException e) {
							log.error(e.getMessage());
							throw new RuntimeCryptoException("实名制接口进退场履历时间转换错误");
						}
						if("进场".equals(pj.getJoinStatus())) {
							eeh.setType(0);
						}else {
							eeh.setType(1);
						}
						eeh.setIsDel(0);
						this.baseMapper.insert(eeh);
						if(Long.parseLong(id) < smzId) {
							id = String.valueOf(smzId);
						}
					}
//						throw new RuntimeException("实名制接口进退场履历所属工人为空");

				}
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("code", "1");
				m.put("no", id);
				//响应保存成功信息
				HttpClientUtils.post(getSystemStringParam("httpUrlPrefixYC")
						+ getSystemStringParam("savePersonJoinFromSMZResponseYC"), m, myc);
			}
		}
	}

	/**
	 * 
	 * @description 获取实名制进退场信息（南通）
	 * @author chupp
	 * @date 2018年7月26日
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void savePersonJoinFromSMZNT(Map<String, String> mnt, List<SubContractor> list) {
		//获取实名制进退场履历信息
		String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")
				+ getSystemStringParam("savePersonJoinFromSMZ"), new HashMap<String,Object>(), mnt);
		Map<String,Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
		//存在新的进退场履历信息
		if(map.get("projectJoin") != null) {
			JSONArray jsonArray = JSONArray.fromObject(map.get("projectJoin"));
			List<PersonJoinSmz> pjList = JSONArray.toList(jsonArray, new PersonJoinSmz(), new JsonConfig());
			String id = "0";
			SubContractor subContractor = null;
            List<SmzLwt> slList = null;
			if(pjList.size() > 0) {
				for(PersonJoinSmz pj : pjList) {
					if(pj.getComId().equals("0")) {
                		subContractor = list.get(0);
                	}else if(pj.getComId().equals("00")) {
                		subContractor = list.get(1);
                	}else if(pj.getComId().equals("000")) {
                		subContractor = list.get(2);
                	}
					long smzId = Long.parseLong(pj.getId());
					EntryExitHistory eeh = new EntryExitHistory();
					slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(pj.getProjectId()), "Project");
					if(slList.size() == 0) throw new RuntimeException("实名制接口进退场履历所属项目为空");
					Map<String, Object> chProject = projectMasterMapper.selectById(slList.get(0).getLwtId());
					if(chProject == null) throw new RuntimeException("实名制接口进退场履历所属项目对象为空");
					eeh.setProjectCode((String)chProject.get("projectCode"));
					eeh.setOrganizationCode(subContractor.getOrganizationCode());
					eeh.setIdCardType("1");
					slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(pj.getUserId()), "Personnel");
					if(slList.size() == 0) throw new RuntimeException("实名制接口进退场履历所属工人为空");
					WorkerMaster wm = workerMasterMapper.selectById(slList.get(0).getLwtId());
					if(wm == null) throw new RuntimeException("实名制接口进退场履历所属工人对象为空");
					eeh.setIdCardNumber(wm.getIdCardNumber());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						eeh.setDate(sdf.parse(pj.getCreateDate().replace("T", " ")));
					} catch (ParseException e) {
						log.error(e.getMessage());
						throw new RuntimeCryptoException("实名制接口进退场履历时间转换错误");
					}
					if("进场".equals(pj.getJoinStatus())) {
						eeh.setType(0);
					}else {
						eeh.setType(1);
					}
					eeh.setIsDel(0);
					this.baseMapper.insert(eeh);
					if(Long.parseLong(id) < smzId) {
						id = String.valueOf(smzId);
					}
				}
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("code", "1");
				m.put("no", id);
				//响应保存成功信息
				HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")
						+ getSystemStringParam("savePersonJoinFromSMZResponse"), m, mnt);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void savePersonJoinFromSMZTY(Map<String, String> mnt) {
		List<String> registerNo = new ArrayList<String>();
		registerNo = this.projectMasterMapper.getRegisterNo();
		for(int j=0;j<registerNo.size();j++){
			//获取实名制进退场履历信息
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			//查找已经存在的最大值
			Long maxId = this.smzLwtMapper.getProjectMaxId(null);
			if(maxId == null){
				maxId = Long.valueOf(0);
			}
			List<SmzCallBack> scbList = new ArrayList<SmzCallBack>();
	    	//获取实名制项目数据
			List<String> projectId = this.projectMasterMapper.getProjectId(maxId,registerNo.get(j));
			for(int n=0;n<projectId.size();n++){
				SmzCallBack scb = new SmzCallBack();
				hashMap.put("projectIdNew", projectId.get(n));
				String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")
						+ getSystemStringParam("savePersonJoinFromSMZTY"), hashMap, mnt);
				Map<String,Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
				//存在新的进退场履历信息
				if(map.get("projectJoin") != null) {
					JSONArray jsonArray = JSONArray.fromObject(map.get("projectJoin"));
					List<PersonJoinSmz> pjList = JSONArray.toList(jsonArray, new PersonJoinSmz(), new JsonConfig());
					String id = "0";
					String subContractor = null;
		            List<SmzLwt> slList = null;
					if(pjList.size() > 0) {
						for(PersonJoinSmz pj : pjList) {
							
							long smzId = Long.parseLong(pj.getId());
							EntryExitHistory eeh = new EntryExitHistory();
							slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(pj.getProjectId()), "Project");
							if(slList.size() == 0) throw new RuntimeException("实名制接口进退场履历所属项目为空");
							Map<String, Object> chProject = projectMasterMapper.selectById(slList.get(0).getLwtId());
							if(chProject == null) throw new RuntimeException("实名制接口进退场履历所属项目对象为空");
							eeh.setProjectCode((String)chProject.get("projectCode"));
							//根据项目id查找社会统一信用代码
		                    subContractor = teamMasterMapper.queryOrgnationCode(slList.get(0).getLwtId());
							eeh.setOrganizationCode(subContractor);
							eeh.setIdCardType("1");
							slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(pj.getUserId()), "PersonnelTY");
							//如果在表中没找到，则去原来的里面找
							if(slList.size() == 0) {
								slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(pj.getUserId()), "Personnel");
//								throw new RuntimeException("实名制接口进退场履历所属工人为空");
							}
							WorkerMaster wm = workerMasterMapper.selectById(slList.get(0).getLwtId());
							if(wm == null) {
								for(int i =0;i<slList.size();i++){
									 wm = workerMasterMapper.selectById(slList.get(i).getLwtId());
									if(wm != null){
										break;
									}
								}
								throw new RuntimeException("实名制接口进退场履历所属工人对象为空");
							}
								
							eeh.setIdCardNumber(wm.getIdCardNumber());
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							try {
								eeh.setDate(sdf.parse(pj.getCreateDate().replace("T", " ")));
							} catch (ParseException e) {
								log.error(e.getMessage());
								throw new RuntimeCryptoException("实名制接口进退场履历时间转换错误");
							}
							if("进场".equals(pj.getJoinStatus())) {
								eeh.setType(0);
							}else {
								eeh.setType(1);
							}
							eeh.setIsDel(0);
							this.baseMapper.insert(eeh);
							if(Long.parseLong(id) < smzId) {
								id = String.valueOf(smzId);
							}
						}
						scb.setMaxId(id);
						scb.setProjectId(projectId.get(n));
						scbList.add(scb);
					}
				}
			}
			Map<String,Object> m = new HashMap<String,Object>();
			String json = JSONArray.fromObject(scbList).toString();
			m.put("code", "1");
			m.put("scbList", json);
			//响应保存成功信息
			HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")
					+ getSystemStringParam("savePersonJoinFromSMZResponseTY"), m, mnt);
		}
		
	}

	@Override
	public void batchDeleteExitByPwId(List<String> list) {
		this.baseMapper.batchDeleteExitByPwId(list);
	}


	@Override
	public void addEntryExitHistory(List<Object> addList) {
		for (Object o : addList) {
			EntryExitHistory entryExitHistory = new EntryExitHistory();

			stringToDateException();
			try {
				BeanUtils.copyProperties(entryExitHistory, o);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			entryExitHistory.setId(null);
			insert(entryExitHistory);

		}
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
		}, java.util.Date.class);
	}
}
