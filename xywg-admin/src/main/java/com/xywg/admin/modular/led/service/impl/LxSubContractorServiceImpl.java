package com.xywg.admin.modular.led.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.common.constant.state.ManagerStatus;
import com.xywg.admin.core.common.exception.BizExceptionEnum;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.util.MD5Util;
import com.xywg.admin.core.util.ToolUtil;
import com.xywg.admin.flow.entity.Order;
import com.xywg.admin.flow.service.OrderService;
import com.xywg.admin.flow.service.ProcessService;
import com.xywg.admin.modular.company.dao.SubContractorMapper;
import com.xywg.admin.modular.company.model.RegSubContractor;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.company.service.ISubContractorService;
import com.xywg.admin.modular.longxin.dao.BussMaterialMapper;
import com.xywg.admin.modular.longxin.model.BussMaterial;
import com.xywg.admin.modular.longxin.model.LxTenderProcessRelation;
import com.xywg.admin.modular.longxin.service.LxTenderProcessRelationService;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.model.ProjectMasterHis;
import com.xywg.admin.modular.projectSubContractor.dao.ProjectSubContractorMapper;
import com.xywg.admin.modular.smz.dao.SmzLwtMapper;
import com.xywg.admin.modular.smz.model.CompanyMo;
import com.xywg.admin.modular.smz.model.CompanySmz;
import com.xywg.admin.modular.smz.model.IfaLabor;
import com.xywg.admin.modular.smz.model.SmzLwt;
import com.xywg.admin.modular.smz.service.IfaLaborService;
import com.xywg.admin.modular.smz.utils.HttpClientUtils;
import com.xywg.admin.modular.system.dao.UserMapper;
import com.xywg.admin.modular.system.model.Dept;
import com.xywg.admin.modular.system.model.User;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.system.service.IUserService;
import com.xywg.admin.modular.system.service.IWorkKindService;
import com.xywg.admin.modular.system.transfer.UserDto;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 企业表 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-22
 */
@Service("lxSubContractorServiceImpl")
@SuppressWarnings("all")
public class LxSubContractorServiceImpl extends ServiceImpl<SubContractorMapper, SubContractor> implements ISubContractorService {

    @Resource
    private SubContractorMapper subContractorMapper;
    
    @Resource
    private BussMaterialMapper bussMaterialMapper;

	@Autowired
	private IfaLaborService ifaLaborService;
	
    @Resource
    private IWorkKindService workKindService;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IDeptService deptService;
    
    @Autowired
    private ProcessService processService;
    
    @Autowired
    private UserMapper userMapper;
	@Autowired
	private OrderService  orderService;
    
    @Autowired
    private LxTenderProcessRelationService lxTenderProcessRelationService;

    @Autowired
    private SmzLwtMapper smzLwtMapper;

	@Autowired
	private ProjectSubContractorMapper projectSubContractorMapper;

    private static final Log LOG = LogFactory.getLog(LxSubContractorServiceImpl.class);
    private static Properties systemParams = new Properties();



	@Override
	public boolean insertReg(SubContractor subContractor) {

		User theUser = userService.getByAccountAndId(subContractor.getOrganizationCode(), "");
		if (theUser != null) {
			return false;
		}


//		Long bussId = subContractor.getId();
//		Order order = new Order();
//		//插入流程
//		try {
//			order=   processService.start("e5257456-7f98-4bea-9433-d750ab7949cb",Integer.parseInt(user.getId().toString()),Integer.parseInt(user.getDeptid().toString()),l);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		LxTenderProcessRelation processRelation = new LxTenderProcessRelation();
//		processRelation.setType(5);
//		processRelation.setProcessId(order.getId());
//		processRelation.setBussId(bussId.toString());
//		lxTenderProcessRelationService.addRelation(processRelation);





		return  insertRegFlow(subContractor);
	}

	@Override
	public SubContractor selectRegById(String bussId) {
		return this.baseMapper.selectRegById(bussId);
	}

	@Override
	public void deleteRegById(String bussId) {
		this.baseMapper.deleteRegById(bussId);
	}

	/**
	 * 加载配置文件
	 */
	static {
		try {
			systemParams.load(LxSubContractorServiceImpl.class.getClassLoader().getResourceAsStream("smz.properties"));
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
        Map<String, String> area = subContractorMapper.getArea(areaId);

        return subContractorMapper.getArea(areaId);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public List<Map<String,Object>> selectList(Page page ,Map map) {
		//不用过滤
//        map.put("deptId", ShiroKit.getUser().getDeptId());
        return subContractorMapper.listSubs(page , map);
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



	@Transactional
	public boolean  insertRegFlow (SubContractor subContractor) {
		RegSubContractor regSubContractor = new RegSubContractor();
		try {
			PropertyUtils.copyProperties(regSubContractor, subContractor);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		String process = regSubContractor.getProcess();
		regSubContractor.setProcess(null);
		String socialCreditNumber = regSubContractor.getSocialCreditNumber();
		if ("".equals(socialCreditNumber)) {
			regSubContractor.setSocialCreditNumber(regSubContractor.getOrganizationCode());
		} else {
			regSubContractor.setOrganizationCode(socialCreditNumber);
		}
		//判重 判断公司是否已经添加过
		int count = baseMapper.hasRegSubContractor(regSubContractor.getSocialCreditNumber(),regSubContractor.getCompanyName());
		if(count > 0){
			throw new XywgException(800, "公司" + regSubContractor.getCompanyName() + "已存在于系统中！");
		}

		//为公司添加工种
		if(StringUtils.isNotEmpty(regSubContractor.getOrganizationCode())) {
			workKindService.addWorkKinds(regSubContractor.getOrganizationCode());
		}


		this.baseMapper.insertRegSubContractor(regSubContractor);
		if(regSubContractor.getIsSynchro()!=null&&regSubContractor.getIsSynchro() == 1 ){
			IfaLabor ifaLabor=new IfaLabor("buss_sub_contractor_reg",regSubContractor.getId());

		//添加公司
/*
=======
		//添加公司入库
>>>>>>> Stashed changes
		this.baseMapper.insert(subContractor);
		//同步到实名制 0不同步 1同步
		if(subContractor.getIsSynchro()!=null&&subContractor.getIsSynchro() == 1 ){
			IfaLabor ifaLabor=new IfaLabor("buss_sub_contractor",subContractor.getId());
*/

			ifaLaborService.insert(ifaLabor);
		}
		//添加企业账号
		addUser(regSubContractor.getSocialCreditNumber(),regSubContractor.getCompanyName(),"0");

		User param = new User();
		param.setId(1);
		User user = userMapper.selectOne(param);
		List l = new ArrayList();
		l.add(Integer.parseInt(user.getRoleid()));
		String  bussId = regSubContractor.getSocialCreditNumber();
		Order order = new Order();
		//插入流程
		try {
			//// TODO: 2019/8/22
			order=   processService.start("727e31cd-28fe-4a3b-aa4b-844bc9995c89",Integer.parseInt(user.getId().toString()),Integer.parseInt(user.getDeptid().toString()),l);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LxTenderProcessRelation processRelation = new LxTenderProcessRelation();
		processRelation.setType(5);
		processRelation.setProcessId(order.getId());
		processRelation.setBussId(bussId.toString());
		lxTenderProcessRelationService.addRelation(processRelation);

		//为企业添加部门
		return true;
	}




    @Override
    @Transactional
    public boolean insert(SubContractor subContractor) {
    	String process = subContractor.getProcess();
    	subContractor.setProcess(null);
    	subContractor.setFlowStatus("1");
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
        if(StringUtils.isNotEmpty(subContractor.getOrganizationCode())) {
        	workKindService.addWorkKinds(subContractor.getOrganizationCode());
        }
		//添加公司
		this.baseMapper.insert(subContractor);
        //是否同步实名制
		if(subContractor.getIsSynchro()!=null&&subContractor.getIsSynchro() == 1 ){
			IfaLabor ifaLabor=new IfaLabor("buss_sub_contractor",subContractor.getId());
			ifaLaborService.insert(ifaLabor);
		}
		
		//添加企业账号
		addUser(subContractor.getSocialCreditNumber(),subContractor.getCompanyName(),"1");
		
		//为企业添加部门
//		addDept(subContractor.getSocialCreditNumber(),subContractor.getCompanyName());
		// 参建单位关联
		String relsocialCreditNumber = deptService.selectById(ShiroKit.getUser().getDeptId()).getSocialCreditNumber();
		projectSubContractorMapper.addBussSubContractorConstruction(ShiroKit.getUser().getName(), relsocialCreditNumber, socialCreditNumber);

        return true;
    }

    private void addDept(String socialCreditNumber, String companyName) {
    	 Dept dept = new Dept();
    	 dept.setSocialCreditNumber(socialCreditNumber);
    	 dept.setSimplename(companyName);
    	 dept.setFullname(companyName);
    	 dept.setPid(239);
    	 if (ToolUtil.isOneEmpty(dept, dept.getSimplename())) {
             throw new XywgException(BizExceptionEnum.REQUEST_NULL);
         }
         //判断该公司是否存在
         Dept hasDept = deptService.hasDeptWithSocialCreditNumber(dept.getSocialCreditNumber());
         if(hasDept != null){
             //该公司已存在
             Integer num = this.deptService.addPid(hasDept.getId(),dept.getPid());
             //将其所有的子公司绑定父公司
             this.deptService.updateAllGrantChildrenByChildId(hasDept.getId(),dept.getPid());
         }else{
             //该公司不存在 新增一条记录
             deptSetPids(dept);
             this.deptService.insert(dept);
         }
  
		
	}
    
    private void deptSetPids(Dept dept) {
        if (ToolUtil.isEmpty(dept.getPid()) || dept.getPid().equals(0)) {
            dept.setPid(0);
            dept.setPids("[0],");
        } else {
            int pid = dept.getPid();
            Dept temp = deptService.selectById(pid);
            String pids = temp.getPids();
            dept.setPid(pid);
            dept.setPids(pids + "[" + pid + "],");
        }
    }

	private void addUser(String account, String companyName,String status) {
    	UserDto user = new UserDto();
    	user.setAccount(account);
    	user.setPassword("123456");
    	user.setName(companyName);
    	user.setIsEnterprise(1);
    	user.setDeptid(239);
    	user.setRoleid(139 + "");
    	 // 判断账号是否重复
        User theUser = userService.getByAccountAndId(account, "");
        if (theUser != null) {
            throw new XywgException(BizExceptionEnum.USER_ALREADY_REG);
        }
        // 完善账号信息
        user.setSalt(ShiroKit.getRandomSalt(5));
        user.setPassword(MD5Util.encrypt(user.getPassword()));
        user.setPassword(ShiroKit.md5(user.getPassword(), user.getSalt()));
        user.setStatus(ManagerStatus.OK.getCode());
        user.setUserType(1);
        user.setCreatetime(new Date());
		user.setFlowStatus(status);
        if(user.getStartTime() == null){
            user.setStartTime(new Date());
        }
        if(user.getEndTime() == null){
            try {
                user.setEndTime(new SimpleDateFormat("yyyy-mm-dd").parse("9999-12-30"));
            } catch (ParseException e) {
            	System.out.println(e);
            }
        }
        userService.saveUserInfo(user);
		
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
	
	public void updateLxById(SubContractor st) {
		String bmStrs = st.getBusinessMaterial();
		this.updateById(st);
		updateMaterials(st.getId(),bmStrs);
	}
    
	private void updateMaterials(Long bussId, String bmStrs) {
		
		//删除历史数据
		if(bussId != null) {
			bussMaterialMapper.deleteByBussId(bussId);
		}
		
		//插入新数据
		if(!StringUtils.isEmpty(bmStrs)) {
			String[] bmArr = bmStrs.split("\\|\\|");
			
			for (String bm : bmArr) {
				bussMaterialMapper.insert(new BussMaterial(bussId,bm));
			}
		}
		
		
	}

	@Override
	public List<ProjectMasterHis> selectHistoryProsa(Integer subContractorId) {
		
		return this.baseMapper.selectHistoryPros(subContractorId);
	}



	@Override
	public List<ProjectMaster> selectHistoryPros(Integer subContractorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubContractor getcompanyNameAndOrgCodeById(Object id) {
		return subContractorMapper.getcompanyNameAndOrgCodeById(id);
	}

	@Override
	public String getCompanyOrgCodeById(Object companyId) {
		return this.baseMapper.getCompanyOrgCodeById(companyId);
	}

	public void updateRegStatus(SubContractor subContractor) {
		this.baseMapper.updateRegStatus(subContractor);
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
	public void addContractorList(List<Object> addList) {
		for (Object o : addList) {
			//SubContractor subContractor = new SubContractor();
			stringToDateException();
			SubContractor subContractor = JSON.parseObject(o.toString(), SubContractor.class);
		/*	try {
				//拷贝为projectMaster对象
				BeanUtils.copyProperties(subContractor, o);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}*/
			Map<String,Long> map= selectContractorbyOrgCode(subContractor.getOrganizationCode());
			if (map.get("num")==0){
				//subContractor.setId(map.get("id"));
				subContractor.setId(null);
				baseMapper.insert(subContractor);
/*                String relsocialCreditNumber = deptService.selectById(ShiroKit.getUser().getDeptId()).getSocialCreditNumber();
                projectSubContractorMapper.addBussSubContractorConstruction(ShiroKit.getUser().getName(), relsocialCreditNumber,subContractor.getSocialCreditNumber());*/
			}
		}
	}
	public Map<String,Long> selectContractorbyOrgCode(String organizationCode) {
	return 	baseMapper.selectContractorbyOrgCode(organizationCode);
	}
}
