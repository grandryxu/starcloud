package com.xywg.admin.modular.zr.service;

import com.xywg.admin.modular.company.dao.SubContractorMapper;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.device.dao.DeviceMapper;
import com.xywg.admin.modular.device.dao.DeviceRecordMapper;
import com.xywg.admin.modular.device.model.Device;
import com.xywg.admin.modular.device.utils.RedisUtil;
import com.xywg.admin.modular.project.dao.InjuryMapper;
import com.xywg.admin.modular.project.dao.ProjectMasterMapper;
import com.xywg.admin.modular.project.model.Injury;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.service.impl.ProjectMasterServiceImpl;
import com.xywg.admin.modular.smz.model.DeviceRecordMo;
import com.xywg.admin.modular.smz.model.PersonMo;
import com.xywg.admin.modular.worker.dao.WorkerMasterMapper;
import com.xywg.admin.modular.zr.model.SmzZrRelationTpm;
import com.xywg.admin.modular.zr.model.ZrCompanyTpm;
import com.xywg.admin.modular.zr.model.ZrDeviceTpm;
import com.xywg.admin.modular.zr.model.ZrInjuryTpm;
import com.xywg.admin.modular.zr.model.ZrProjectTpm;
import com.xywg.admin.modular.zr.model.ZrRecordTpm;
import com.xywg.admin.modular.zr.model.ZrUserTpm;
import com.xywg.admin.modular.zr.utils.HttpClientUtils;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

/**
 * @Description:
 * @Author jln
 */
@Service
public class ZrInterfaceServiceImpl implements IZrInterfaceService{

	@Autowired
	private SubContractorMapper subContractorMapper;

	@Autowired
	private ProjectMasterMapper projectMasterMapper;
	@Autowired
	private DeviceMapper deviceMapper;
	@Autowired
	private WorkerMasterMapper workerMasterMapper;
	@Autowired
	private DeviceRecordMapper deviceRecordMapper;
	@Autowired
	private InjuryMapper injuryMapper;
	
    @Autowired
    public RedisUtil redisUtil;
	
	private static final Log LOG = LogFactory.getLog(ProjectMasterServiceImpl.class);
    private static Properties systemParams = new Properties();
	/**
     * 加载配置文件
     */
    static {
        try {
            systemParams.load(ProjectMasterServiceImpl.class.getClassLoader().getResourceAsStream("zr.properties"));
        } catch (IOException e) {
            LOG.error("zr.properties" + "配置文件加载失败");
        }
    }

    /**
     * @param key
     * @return
     * @description 获取配置文件具体信息
     * @author jln
     */
    protected String getSystemStringParam(String key) {
        return systemParams.getProperty(key);
    }
	@Override
	public boolean SendCompanyInfo(String stoken) {
		//获取企业信息
		SubContractor company = this.subContractorMapper.getCompanyInfo();
		
		ZrCompanyTpm zrCompany = new ZrCompanyTpm();
		zrCompany.setFullName(company.getCompanyName());
		zrCompany.setSimpleName(company.getCompanyName());
		zrCompany.setRegistration(company.getAreaCode());
		zrCompany.setType(1);
		zrCompany.setCode(company.getOrganizationCode());
		zrCompany.setUserId("1");
		zrCompany.setLevel(1);
		zrCompany.setContact(company.getContactPeopleName());
		zrCompany.setContactPhone(company.getContactPeopleCellPhone());
		zrCompany.setDescription(company.getCompanyName());
		zrCompany.setRegisterCapital(Long.valueOf(50000));
		
		String json = JSONObject.fromObject(zrCompany).toString();
		String jsonResult = HttpClientUtils.sendPost(getSystemStringParam("httpUrlPrefix") + getSystemStringParam("SendCompanyInfo"), json, stoken);
		
		JSONObject obj = new JSONObject().fromObject(jsonResult);//将json字符串转换为json对象
		if("10000".equals(obj.getString("code"))){
			System.out.println(obj.getString("id"));
			return true;
		}else{
			return false;
		}
	}
	@Override
	public boolean SendProjectInfo(String stoken) {
		//获取项目信息
		List<ProjectMaster> project = this.projectMasterMapper.getZrProjectInfo();
		for(int i=0;i<project.size();i++){
			ZrProjectTpm zrProject = new ZrProjectTpm();
			zrProject.setName(project.get(i).getProjectName());
			zrProject.setContractNo(project.get(i).getContractNo());
			if("3".equals(project.get(i).getProjectStatus())){
				zrProject.setStatus(1);  //工程状态
			}else{
				zrProject.setStatus(0);
			}
			
			zrProject.setCompanyId("ee6470c27b6b41c185b9eada0de5e520");
//			zrProject.setCompanyId("b7b1510a782d44d7b0dc383de2ecddd4");
			//工程类型
			if("1".equals(project.get(i).getProjectCategory())){
				zrProject.setType(1L);  
			}else if("2".equals(project.get(i).getProjectCategory())){
				zrProject.setType(2L); 
			}else{
				zrProject.setType(1L); 
			}
			zrProject.setCost(project.get(i).getTotalContractAmt());  //工程造价
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        //获取String类型的时间
	        String b = sdf.format(project.get(i).getStartDate());
	        String e = sdf.format(project.get(i).getCompleteDate());
			zrProject.setStartTime(b);
			zrProject.setEndTime(e);
			zrProject.setCivilSite(Integer.valueOf(project.get(i).getIsCivilization()));
			zrProject.setQuality(Integer.valueOf(project.get(i).getIsHighGrade()));
			zrProject.setInvestType(3);  //投资类型
			zrProject.setConstruction(project.get(i).getOwnerName());  //建设单位名称
			zrProject.setBank(project.get(i).getBankName());
			zrProject.setAccount(project.get(i).getBankNumber());
			zrProject.setDescription(project.get(i).getProjectDescription());
			zrProject.setAreaCode(Long.valueOf(project.get(i).getAreaCode()));
			if(project.get(i).getContractorType() == 15){
				zrProject.setContractType(2);  //建设单位
			}else if(project.get(i).getContractorType() == 14){
				  //监理单位
			}else if(project.get(i).getContractorType() == 17){
				  //勘察单位
			}else if(project.get(i).getContractorType() == 18){
				  //设计单位
			}else if(project.get(i).getContractorType() == 67){
				  //其他
			}else {
				zrProject.setContractType(1); //施工单位
			}
			
			zrProject.setAcreage(project.get(i).getBuildingArea());
			zrProject.setContact(project.get(i).getProjectDirector());   //责任人
			zrProject.setContactPhone(project.get(i).getProjectDirectorPhone()); //责任人联系电话
			
			String json = JSONObject.fromObject(zrProject).toString();
			System.out.println(json);
			String jsonResult = HttpClientUtils.sendPost(getSystemStringParam("httpUrlPrefix") + getSystemStringParam("SendProjectInfo"), json, stoken);
			System.out.println(jsonResult);
			JSONObject obj = new JSONObject().fromObject(jsonResult);//将json字符串转换为json对象
			if("10000".equals(obj.getString("code"))){
				System.out.println(obj.getString("id"));
				//先根据实名制id查找是否有对应的中如id
				String old = this.deviceMapper.queryKey(project.get(i).getId(), "project");
				if(old == null){
					//将中如的设备id与我们这边的设备id对应，保存关系
					SmzZrRelationTpm r = new SmzZrRelationTpm();
					r.setLwtId(project.get(i).getId());
					r.setZrId(obj.getString("id"));
					r.setTableName("project");
					this.deviceMapper.addRelation(r);
				}
			}else{
				System.out.println(obj.getString("msg"));
			}
		}
		return true;
	}
	@Override
	public boolean SendDeviceInfo(String stoken) {
		//获取考勤设备信息
		List<Device> devices = this.deviceMapper.getZrDeviceInfo();
		for (Device device : devices) {
			ZrDeviceTpm zrDevice = new ZrDeviceTpm();
			zrDevice.setName(device.getName());
			zrDevice.setProjectId(device.getZrId());
			zrDevice.setSn(device.getSn());
			zrDevice.setType(Long.valueOf(3));
			String json = JSONObject.fromObject(zrDevice).toString();
			String jsonResult = HttpClientUtils.sendPost(getSystemStringParam("httpUrlPrefix") + getSystemStringParam("SendDeviceInfo"), json, stoken);
			System.out.println(jsonResult);
			JSONObject obj = new JSONObject().fromObject(jsonResult);//将json字符串转换为json对象
			if("10000".equals(obj.getString("code"))){
				System.out.println(obj.getString("id"));
				//先根据实名制id查找是否有对应的中如id
				String old = this.deviceMapper.queryKey(device.getId(), "device");
				if(old == null){
					//将中如的设备id与我们这边的设备id对应，保存关系
					SmzZrRelationTpm r = new SmzZrRelationTpm();
					r.setLwtId(device.getId());
					r.setZrId(obj.getString("id"));
					r.setTableName("device");
					try{
						this.deviceMapper.addRelation(r);
					}catch(Exception e){
						e.getMessage();
					}
				}
			}else{
				System.out.println(obj.getString("msg"));
			}
		}
		return true;
		
	}
	@Override
	public boolean SendUserInfo(String stoken) throws Exception {
		//获取缓存的对接中如最大id
		Object id = redisUtil.get("zr_user");
		Long personId = id == null ? 0L :Long.valueOf(String.valueOf(id));
		//获取人员信息
		List<PersonMo> userinfo = this.workerMasterMapper.getZrUserInfo(personId);
		for (PersonMo user : userinfo) {
			ZrUserTpm zrUser = new ZrUserTpm();
			zrUser.setRealName(user.getName());
			zrUser.setGender(user.getGender());
			zrUser.setAreaId(Long.valueOf(user.getBirthPlaceCode()));
			/*SimpleDateFormat s = new SimpleDateFormat();
			String b = s.format(user.getBirthday());*/
			zrUser.setBirthday(user.getBirthday());
			zrUser.setIdNo(user.getIdCard());
			zrUser.setNation(user.getNation());
			zrUser.setEdu(user.getCultureLevelType());
			zrUser.setPhone(Long.valueOf(user.getMobile()));
			zrUser.setMaritalStatus(3);
			zrUser.setTeam(user.getClassName());
			if("240".equals(user.getKindCode()) || "241".equals(user.getKindCode()) || "242".equals(user.getKindCode()) || "320".equals(user.getKindCode())){
				zrUser.setWorkTypeId(Long.valueOf(2)); //木工
			}else if(("210".equals(user.getKindCode())) || ("211".equals(user.getKindCode())) || ("220".equals(user.getKindCode()))){
				zrUser.setWorkTypeId(Long.valueOf(3));  //幕墙
			}else if("10".equals(user.getKindCode()) || "330".equals(user.getKindCode())){
				zrUser.setWorkTypeId(Long.valueOf(4));  //瓦工
			}else if("270".equals((user.getKindCode()))){
				zrUser.setWorkTypeId(Long.valueOf(6));  //焊工
			}else if("130".equals(user.getKindCode()) || "131".equals(user.getKindCode())){
				zrUser.setWorkTypeId(Long.valueOf(7));  //电工
			}else if("110".equals(user.getKindCode())){
				zrUser.setWorkTypeId(Long.valueOf(10));  //管道工
			}else if("20".equals(user.getKindCode())){
				zrUser.setWorkTypeId(Long.valueOf(11));  //钢筋工
			}else if("230".equals(user.getKindCode())){
				zrUser.setWorkTypeId(Long.valueOf(16));  //防水工
			}else if("192".equals(user.getKindCode())){
				zrUser.setWorkTypeId(Long.valueOf(23));  //油漆工
			}else if(("30".equals(user.getKindCode()))){
				zrUser.setWorkTypeId(Long.valueOf(24));  //架子工
			}else if("195".equals(user.getKindCode())){
				zrUser.setWorkTypeId(Long.valueOf(32));  //装饰木工
			}else if(("900".equals(user.getKindCode()))){ //项目管理人员
				zrUser.setWorkTypeId(Long.valueOf(48));  //其他管理人员
			}else if("1000".equals(user.getKindCode())){
				zrUser.setWorkTypeId(Long.valueOf(99));  //其他
			}else{
				zrUser.setWorkTypeId(Long.valueOf(99));  //其他
			}
//						zrUser.setWorkTypeName(user.get(i).getWorkKindName());
			zrUser.setStatus("1");
			zrUser.setProjectId(user.getZrId());
			
			String json = JSONObject.fromObject(zrUser).toString();
			
			String jsonResult = HttpClientUtils.sendPost(getSystemStringParam("httpUrlPrefix") + getSystemStringParam("SendUserInfo"), json, stoken);
			System.out.println(jsonResult);
			JSONObject obj = new JSONObject().fromObject(jsonResult);//将json字符串转换为json对象
			if("10000".equals(obj.getString("code"))){
				System.out.println(obj.getString("id"));
				//先根据实名制id查找是否有对应的中如id
				String old = this.deviceMapper.queryKey(Long.valueOf(user.getPersonId()), "user");
				if(old == null){
					//将中如的人员id与我们这边的人员id对应，保存关系
					SmzZrRelationTpm r = new SmzZrRelationTpm();
					r.setLwtId(Long.valueOf(user.getId()));
					r.setZrId(obj.getString("id"));
					r.setTableName("user");
					this.deviceMapper.addRelation(r);
				}
				if (personId < user.getId()) {
					personId = user.getId();
					redisUtil.set("zr_user", personId);
				}
			}else{
				throw new Exception("数据发送异常"+user.getIdCard()+"="+obj.getString("msg"));
			}
		}
		return true;
		
	}
	@Override
	public boolean SendAttendance(String stoken) throws Exception {
		//获取缓存的对接中如最大id
		Object id = redisUtil.get("zr_device_record");
		Long recordId = id == null ? 0L :Long.valueOf(String.valueOf(id));
		//获取考勤信息
		List<DeviceRecordMo> records = this.deviceRecordMapper.getZrAttendance(recordId);
		for (DeviceRecordMo record : records) {
			ZrRecordTpm zrRecord = new ZrRecordTpm();
			//根据实名制id查找出中如考勤设备id
			String deviceId = this.deviceMapper.queryKey(record.getDeviceId(),"device");
			if(deviceId != null){
				zrRecord.setDeviceId(deviceId.toString());
			}else{
				throw new Exception("没有对应的考勤设备"+record.getDeviceId());
			}
			//根据实名制id查找中如人员id
			String userId = this.deviceMapper.queryKey(record.getPersonId(), "user");
			if(userId != null){
				zrRecord.setProjectMemberId(userId.toString());
			}else{
				throw new Exception("没有对应的人员"+record.getIdCard());
			}
			zrRecord.setType(Long.valueOf(record.getType()));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        //获取String类型的时间
	        String ts = sdf.format(record.getTime());
			zrRecord.setTime(ts);
			if(record.getAddress() != null && !"".equals(record.getAddress())){
				zrRecord.setAddress(record.getAddress());
			}else{
				zrRecord.setAddress("默认考勤地址");
			}
			
			zrRecord.setPointX(record.getLng());
			zrRecord.setPointY(record.getLat());
			String json = JSONObject.fromObject(zrRecord).toString();
			String jsonResult = HttpClientUtils.sendPost(getSystemStringParam("httpUrlPrefix") + getSystemStringParam("SendAttendance"), json, stoken);
			System.out.println(jsonResult);
			JSONObject obj = new JSONObject().fromObject(jsonResult);//将json字符串转换为json对象
			if("10000".equals(obj.getString("code"))){
				System.out.println(obj.getString("id"));
				//先根据实名制id查找是否有对应的中如id
				String old = this.deviceMapper.queryKey(record.getLablorId(), "record");
				if(old == null){
					SmzZrRelationTpm r = new SmzZrRelationTpm();
					r.setLwtId(record.getLablorId());
					r.setZrId(obj.getString("id"));
					r.setTableName("record");
					this.deviceMapper.addRelation(r);
				}
				if (recordId < record.getLablorId()) {
					recordId = record.getLablorId();
					redisUtil.set("zr_device_record", recordId);
				}
			}else{
				throw new Exception("数据发送异常: 人员:"+record.getLablorId()+"="+obj.getString("msg")); 
			}
		}
		return true;
		
	}
	@Override
	public boolean SendInjuryInfo(String stoken) throws Exception {
		//获取工伤信息
		List<Injury> injury = this.injuryMapper.getZrInjuryInfo();
		for(int i=0;i<injury.size();i++){
			ZrInjuryTpm zrInjury = new ZrInjuryTpm();
			//根据实名制id查找中如id
			String userId = this.deviceMapper.queryKey(injury.get(i).getTeamWorker(), "user");
			if(userId != null){
				zrInjury.setProjectMemberId(userId.toString());
			}else{
				throw new Exception("没有对应的人员"+injury.get(i).getIdCardNumber());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        //获取String类型的时间
	        String ts = sdf.format(injury.get(i).getTime());
			zrInjury.setOccurrenceTime(ts);
//			zrInjury.setPlace(injury.get(i).getInjuryAddress());
			zrInjury.setMemo(injury.get(i).getRemark());
			zrInjury.setProjectId("");
			
			String json = JSONObject.fromObject(zrInjury).toString();
			String jsonResult = HttpClientUtils.sendPost(getSystemStringParam("httpUrlPrefix") + getSystemStringParam("SendInjuryInfo"), json, stoken);
			System.out.println(jsonResult);
			JSONObject obj = new JSONObject().fromObject(jsonResult);//将json字符串转换为json对象
			if("10000".equals(obj.getString("code"))){
				System.out.println(obj.getString("id"));
				//先根据实名制id查找是否有对应的中如id
				String old = this.deviceMapper.queryKey(injury.get(i).getId(), "injury");
				if(old == null){
					SmzZrRelationTpm r = new SmzZrRelationTpm();
					r.setLwtId(injury.get(i).getId());
					r.setZrId(obj.getString("id"));
					r.setTableName("injury");
					this.deviceMapper.addRelation(r);
				}
			}else{
				System.out.println(obj.getString("msg"));
			}
		}
		return true;
		
	}
   
}
