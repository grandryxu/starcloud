package com.xywg.admin.modular.project.service.impl;

import com.xywg.admin.modular.project.model.ProjectTrainRecord;
import com.xywg.admin.modular.project.model.ProjectTraining;
import com.xywg.admin.modular.company.dao.SubContractorMapper;
import com.xywg.admin.modular.project.dao.ProjectMasterMapper;
import com.xywg.admin.modular.project.dao.ProjectTrainRecordMapper;
import com.xywg.admin.modular.project.service.IProjectTrainRecordService;
import com.xywg.admin.modular.smz.dao.SmzLwtMapper;
import com.xywg.admin.modular.smz.model.SmzLwt;
import com.xywg.admin.modular.smz.model.TrainSmz;
import com.xywg.admin.modular.smz.utils.HttpClientUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 培训人员记录表 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-14
 */
@Service
public class ProjectTrainRecordServiceImpl extends ServiceImpl<ProjectTrainRecordMapper, ProjectTrainRecord> implements IProjectTrainRecordService {

    @Autowired
    private ProjectMasterMapper projectMasterMapper;
    
    @Autowired
    private SubContractorMapper subContractorMapper;
    
    @Autowired
    private SmzLwtMapper smzLwtMapper;
    private static final Log LOG = LogFactory.getLog(ProjectTrainRecordServiceImpl.class);
    private static Properties systemParams = new Properties();
	
	/**
	 * 加载配置文件
	 */
	static {
		try {
			systemParams.load(ProjectTrainRecordServiceImpl.class.getClassLoader().getResourceAsStream("smz.properties"));
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
	 * @description 获取实名制培训记录（盐城）
	 * @author chupp
	 * @date 2018年7月5日
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void saveTrainRecordFromSMZYC(Map<String, String> myc) {
		//获取实名制培训记录数据
		String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefixYC")
				+ getSystemStringParam("saveTrainRecordFromSMZYC"), new HashMap<String,Object>(), myc);
		Map<String,Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
		//存在新的培训记录信息
		if(map.get("train") != null) {
			JSONArray jsonArray = JSONArray.fromObject(map.get("train"));
			List<TrainSmz> trainList = JSONArray.toList(jsonArray, new TrainSmz(), new JsonConfig());
			List<SmzLwt> slList = null;
			String id = "0";
			if(trainList.size() > 0) {
				for(TrainSmz ts : trainList) {
					long smzId = Long.parseLong(ts.getId());
					ProjectTrainRecord ptr = new ProjectTrainRecord();
					slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(ts.getTrainRecordId()), "TrainYC");
					if(slList.size() == 0) throw new RuntimeException("实名制接口培训信息为空");
					ptr.setTrainId(slList.get(0).getLwtId());
					ptr.setIdCardType(1L);
					ptr.setIdCardNumber(ts.getIdcardNumber());
					//保存实名制培训数据
					this.baseMapper.insert(ptr);
					if(Long.parseLong(id) < smzId) {
						id = String.valueOf(smzId);
					}
				}
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("code", "1");
				m.put("no", id);
				//响应保存成功信息
				HttpClientUtils.post(getSystemStringParam("httpUrlPrefixYC")
						+ getSystemStringParam("saveTrainRecordFromSMZResponseYC"), m, myc);
			}
		}
	}

	/**
	 * 
	 * @description 获取实名制培训记录（南通）
	 * @author chupp
	 * @date 2018年7月26日
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void saveTrainRecordFromSMZNT(Map<String, String> mnt) {
		//获取实名制培训记录数据
		String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")
				+ getSystemStringParam("saveTrainRecordFromSMZ"), new HashMap<String,Object>(), mnt);
		Map<String,Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
		//存在新的培训记录信息
		if(map.get("train") != null) {
			JSONArray jsonArray = JSONArray.fromObject(map.get("train"));
			List<TrainSmz> trainList = JSONArray.toList(jsonArray, new TrainSmz(), new JsonConfig());
			List<SmzLwt> slList = null;
			String id = "0";
			if(trainList.size() > 0) {
				for(TrainSmz ts : trainList) {
					long smzId = Long.parseLong(ts.getId());
					ProjectTrainRecord ptr = new ProjectTrainRecord();
					slList = smzLwtMapper.getLwtIdBySmzId(Long.parseLong(ts.getTrainRecordId()), "Train");
					if(slList.size() == 0) throw new RuntimeException("实名制接口培训信息为空");
					ptr.setTrainId(slList.get(0).getLwtId());
					ptr.setIdCardType(1L);
					ptr.setIdCardNumber(ts.getIdcardNumber());
					//保存实名制培训数据
					this.baseMapper.insert(ptr);
					if(Long.parseLong(id) < smzId) {
						id = String.valueOf(smzId);
					}
				}
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("code", "1");
				m.put("no", id);
				//响应保存成功信息
				HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")
						+ getSystemStringParam("saveTrainRecordFromSMZResponse"), m, mnt);
			}
		}
	}

}
