package com.xywg.admin.modular.company.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.company.dao.SubContractorMapper;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.company.service.ISubContractorService;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.model.ProjectMasterHis;
import com.xywg.admin.modular.smz.dao.SmzLwtMapper;
import com.xywg.admin.modular.smz.model.CompanyMo;
import com.xywg.admin.modular.smz.model.CompanySmz;
import com.xywg.admin.modular.smz.model.IfaLabor;
import com.xywg.admin.modular.smz.model.SmzLwt;
import com.xywg.admin.modular.smz.service.IfaLaborService;
import com.xywg.admin.modular.smz.utils.HttpClientUtils;
import com.xywg.admin.modular.system.service.IWorkKindService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <p>
 * 企业表 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-22
 */
//@Service
@SuppressWarnings("all")
public class SubContractorServiceImpl extends ServiceImpl<SubContractorMapper, SubContractor> implements ISubContractorService {

    @Resource
    private SubContractorMapper subContractorMapper;

	@Autowired
	private IfaLaborService ifaLaborService;
    @Resource
    private IWorkKindService workKindService;

    @Autowired
    private SmzLwtMapper smzLwtMapper;
    private static final Log LOG = LogFactory.getLog(SubContractorServiceImpl.class);
    private static Properties systemParams = new Properties();
	
	/**
	 * 加载配置文件
	 */
	static {
		try {
			systemParams.load(SubContractorServiceImpl.class.getClassLoader().getResourceAsStream("smz.properties"));
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
//		Map<String,Object> m = (Map<String,Object>) JSONObject.toBean(JSONObject.fromObject(result), Map.class);
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
//		Map<String,Object> m = (Map<String,Object>) JSONObject.toBean(JSONObject.fromObject(result), Map.class);
//		Map<String,String> headers = new HashMap<>();
//		headers.put("token", (String) m.get("token"));
//		return headers;
//	}
    
	/**
	 * 
	 * @description 获取实名制企业数据（盐城）
	 * @author chupp
	 * @date 2018年7月2日
	 * @see com.xywg.admin.modular.company.service.ISubContractorService#saveCompanyFromSMZ()
	 *
	 */
	@Override
	@Transactional
	public void saveCompanyFromSMZYC(Map<String, String> myc) {
		//获取实名制企业数据
		String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefixYC")
				+ getSystemStringParam("saveCompanyFromSMZ"), new HashMap<String,Object>(), myc);
		Map<String,Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
		//存在新的企业数据
		if(map.get("corporation") != null) {
			JSONArray jsonArray = JSONArray.fromObject(map.get("corporation"));
			List<CompanySmz> csList = JSONArray.toList(jsonArray, new CompanySmz(), new JsonConfig());
			if(csList.size() > 0) {
				CompanySmz cs = csList.get(0);
				if(cs != null && cs.getCompanyName() != null && !cs.getCompanyName().isEmpty()
						&& cs.getId() != null && !cs.getId().isEmpty()) {
					//按公司代码查询是否存在重复
					if(this.baseMapper.hasSubContractor(cs.getRegisterNo(),cs.getCompanyName()) > 0) {
						String id = "0";
						long smzId = Long.parseLong(cs.getId());
				        //为公司添加工种
				        workKindService.addWorkKinds(cs.getRegisterNo());
						//保存实名制企业数据
						SubContractor sc = new SubContractor();
						sc.setCompanyName(cs.getCompanyName());
						sc.setOrganizationType("174");
						sc.setOrganizationCode(cs.getRegisterNo());
						sc.setBizRegisterCode(cs.getRegisterNo());
						sc.setSocialCreditNumber(cs.getRegisterNo());
						sc.setAreaCode("320600");
						sc.setAddress(cs.getCompanyAddress());
						sc.setZipCode("226000");
						sc.setContactPeopleName(cs.getCompanyContact());
						sc.setContactPeopleCellPhone(cs.getContactPhone());
						sc.setBusinessStatus("1");
						sc.setSource(1);
						sc.setIsAudit(1);
						sc.setStatus(1);
						sc.setIsDel(0);
						this.baseMapper.insert(sc);
						//保存主键关联表
						long lwtId = sc.getId();
						SmzLwt sl = new SmzLwt();
						sl.setSmzId(smzId);
						sl.setLwtId(lwtId);
						sl.setTableName("Company");
						smzLwtMapper.saveSmzLwt(sl);
						if(Long.parseLong(id) < smzId) {
							id = String.valueOf(smzId);
						}
						Map<String,Object> m = new HashMap<String,Object>();
						m.put("code", "1");
						m.put("no", id);
						//响应保存成功信息
						HttpClientUtils.post(getSystemStringParam("httpUrlPrefixYC")
								+ getSystemStringParam("saveCompanyFromSMZResponse"), m, myc);
					}
				}
			}
		}
	}

	@Override
	public boolean getCompanyFromLabor(List<Long> ids,Map<String, String> m) {
		List<CompanyMo> list=subContractorMapper.getCompanyFromLabor(ids);
		Map<String,Object> map=new HashMap<String,Object>();
		String json= JSONArray.fromObject(list).toString();
		map.put("company",json);
		String jsonResult=HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")+getSystemStringParam("saveCompanyFromLabor"),map,m);
		System.out.println(jsonResult);
		Map<String, String> result= JSONUtil.toBean(jsonResult, Map.class);
		if("-2".equals(result.get("code"))){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public Map<String, String> getArea(String areaId) {
		//将第四位以后的所有字符中的0替换为空串
		String flag=areaId.substring(4).replaceAll("0","");
		//如果最终的字符串为空串,说明该areaId 为市一级的id
		if(StringUtils.isBlank(flag)){
			return subContractorMapper.getAreaProvinceCity(areaId);
		}
		return subContractorMapper.getArea(areaId);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public List<Map<String,Object>> selectList(Page page ,Map map) {
        map.put("deptId", ShiroKit.getUser().getDeptId());
        return subContractorMapper.list(page , map);
    }

    @Override
    public int changeState(Map<String,Object> map){
        return subContractorMapper.changeState(map); }

    /**
     * 查询所有承包商
     * @return
     */
    @Override
    public List<Map<String,Object>> selectGeneralContractors(String generalContractorName) {
        return subContractorMapper.selectGeneralContractors(generalContractorName);
    }

    @Override
    public List<SubContractor> getList(Map<String, Object> map) {
        map.put("deptId", ShiroKit.getUser().getDeptId());
        return subContractorMapper.getList(map);
    }

    @Override
    public List<Map<String,Object>> getAllSubContractor(Map<String, Object> map) {
        map.put("deptId", ShiroKit.getUser().getDeptId());
        return subContractorMapper.getAllSubContractor(map);
    }

    @Override
    public List<SubContractor> getSubContractorByCondition(String condition) {
        return subContractorMapper.getSubContractorByCondition(condition);
    }

    @Override
    public SubContractor getByCompanyName(String companyName) {
        return  subContractorMapper.getByCompanyName(companyName);
    }

    @Override
    public Map<String, Object> getOneById(Integer id) {
        return subContractorMapper.getOneById(id);
    }

    @Override
    public Map<String,Object> getSubContractorByOrganizationCode(String organizationCode) {
        return subContractorMapper.getSubContractorByOrganizationCode(organizationCode);
    }

    @Override
    public Integer deleteConstruction(String organizationCode) {
        return subContractorMapper.deleteConstruction(organizationCode,ShiroKit.getUser().getDeptId());
    }

    @Override
    public boolean insert(SubContractor subContractor) {
		String socialCreditNumber = subContractor.getSocialCreditNumber();
		if ("".equals(socialCreditNumber)) {
			subContractor.setSocialCreditNumber(subContractor.getOrganizationCode());
		} else {
			subContractor.setOrganizationCode(socialCreditNumber);
		}
        //判重 判断公司是否已经添加过
       int count = baseMapper.hasSubContractor(subContractor.getSocialCreditNumber(),subContractor.getCompanyName());
        if(count > 0){
			throw new XywgException(800, "公司" + subContractor.getCompanyName() + "已存在于系统中！");
        }

        //为公司添加工种
        workKindService.addWorkKinds(subContractor.getOrganizationCode());
		this.baseMapper.insert(subContractor);
		if(subContractor.getIsSynchro() == 1 ){
			IfaLabor ifaLabor=new IfaLabor("buss_sub_contractor",subContractor.getId());
			ifaLaborService.insert(ifaLabor);
		}
        return true;
    }

	@Override
	public boolean insertReg(SubContractor subContractor) {
		String socialCreditNumber = subContractor.getSocialCreditNumber();
		boolean b = true;
		if ("".equals(socialCreditNumber)) {
			subContractor.setSocialCreditNumber(subContractor.getOrganizationCode());
		} else {
			subContractor.setOrganizationCode(socialCreditNumber);
		}
		int count = baseMapper.hasSubContractor(subContractor.getSocialCreditNumber(),subContractor.getCompanyName());
		if(count > 0){
			b=false;
			throw new XywgException(800, "公司" + subContractor.getCompanyName() + "已存在于系统中！");
		}
		this.baseMapper.insertReg(subContractor);


		return b;
	}

	@Override
	public SubContractor selectRegById(String bussId) {
		return this.baseMapper.selectRegById(bussId);
	}

	@Override
	public void deleteRegById(String bussId) {

	}

	/**
     * 
     * @description 获取实名制企业数据（盐城企业版）
     * @author chupp
     * @date 2018年7月17日
     *
     */
	@Override
	@Transactional
	public void saveCompanyFromSMZCompanyYC(Map<String, String> myc) {
		//获取实名制企业数据
		String jsonResult = HttpClientUtils.post(getSystemStringParam("httpUrlPrefixYC")
				+ getSystemStringParam("saveCompanyFromSMZCompany"), new HashMap<String,Object>(), myc);
		Map<String,Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonResult), Map.class);
		//存在新的企业数据
		if(map.get("corporation") != null) {
			JSONArray jsonArray = JSONArray.fromObject(map.get("corporation"));
			List<CompanySmz> csList = JSONArray.toList(jsonArray, new CompanySmz(), new JsonConfig());
			String id = "0";
			if(csList.size() > 0) {
				for(CompanySmz cs : csList) {
					if(cs != null && cs.getCompanyName() != null && !cs.getCompanyName().isEmpty()
							&& cs.getId() != null && !cs.getId().isEmpty()) {
						//按公司代码查询是否存在重复
						if(this.baseMapper.hasSubContractor(cs.getRegisterNo(),cs.getCompanyName()) > 0) {
							long smzId = Long.parseLong(cs.getId());
					        //为公司添加工种
					        workKindService.addWorkKinds(cs.getRegisterNo());
							//保存实名制企业数据
							SubContractor sc = new SubContractor();
							sc.setCompanyName(cs.getCompanyName());
							sc.setOrganizationType("174");
							sc.setOrganizationCode(cs.getRegisterNo());
							sc.setBizRegisterCode(cs.getRegisterNo());
							sc.setSocialCreditNumber(cs.getRegisterNo());
							sc.setAreaCode("320600");
							sc.setAddress(cs.getCompanyAddress());
							sc.setZipCode("226000");
							sc.setContactPeopleName(cs.getCompanyContact());
							sc.setContactPeopleCellPhone(cs.getContactPhone());
							sc.setBusinessStatus("1");
							sc.setSource(1);
							sc.setIsAudit(1);
							sc.setStatus(1);
							sc.setIsDel(0);
							this.baseMapper.insert(sc);
							//保存主键关联表
							long lwtId = sc.getId();
							SmzLwt sl = new SmzLwt();
							sl.setSmzId(smzId);
							sl.setLwtId(lwtId);
							sl.setTableName("CompanyYC");
							smzLwtMapper.saveSmzLwt(sl);
							if(Long.parseLong(id) < smzId) {
								id = String.valueOf(smzId);
							}
						}
					}
				}
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("code", "1");
				m.put("no", id);
				//响应保存成功信息
				HttpClientUtils.post(getSystemStringParam("httpUrlPrefixYC")
						+ getSystemStringParam("saveCompanyFromSMZCompanyResponse"), m, myc);
			}
		}
	}

	@Override
	public List<Map<String,Object>> getSubContractor() {
		Map<String,Object> map=new HashedMap();
		map.put("deptId", ShiroKit.getUser().getDeptId());
		return subContractorMapper.getSubContractor( map);
	}

	@Override
	public List<Map<String, Object>> getAllNoParentSubContractor(Map<String, Object> map) {
		map.put("deptId", ShiroKit.getUser().getDeptId());
		return this.baseMapper.getAllNoParentSubContractor(map);
	}

	@Override
	public void updateLxById(SubContractor subContractor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ProjectMaster> selectHistoryPros(Integer subContractorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProjectMasterHis> selectHistoryProsa(Integer subContractorId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SubContractor getcompanyNameAndOrgCodeById(Object id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getCompanyOrgCodeById(Object companyId) {
		/*return this.baseMapper.getCompanyOrgCodeById(companyId);*/
		// TODO Auto-generated method stub
		return null;
	}

	public void updateRegStatus(SubContractor subContractor) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addContractorList(List<Object> addList) {
		// TODO Auto-generated method stub
	}
}
