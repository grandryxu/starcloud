package com.xywg.admin.modular.device.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.xywg.admin.modular.device.model.PersonPosition;

/**
 * 考勤dto
 */
public class DeviceRecordDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7295033703863411463L;

	private String year;
	private String totalNum;
	private List<ProjectMasterDto> projects;
	private String idCardType;
	private String idCardNumber;
	private String workerName;
	private String date;
	private Integer count;
	private String time;
	private String iconPhoto;
	private String photo;
	private String recordUserName;
	private String deviceWorkerName;
	private String address;
	private String map;
	private String isValid;
	private String id;
	private String headImage;
	private String cellPhone;
	private String isAuth;
	private String projectsCount;
	private String bfb;
	private String deviceType;

	/*** @author: duanfen添加 2018年6月19日 下午3:01:23 */
	/**
	 * 承包单位组织机构代码
	 */
	private String organizationCode;

	/**
	 * 项目编号
	 */
	private String projectCode;
	
	/**
	 * 项目名称
	 */
	private String projectName;
	
	
	/**
	 * 项目名称
	 */
	private String teamName;

	/**
	 * 班组编号 外部系统与平台对接创建班组后，由系统返回该编号
	 */
	private Integer teamSysNo;

	/**
	 * 经度
	 */
	private BigDecimal lng;
	/**
	 * 纬度
	 */
	private BigDecimal lat;

	/**
	*打卡类型
	*/
	private Integer type;

	/** 
	 * 打卡来源
	 * */
	private Integer source;
	
	/**
	 * 安全帽编号
	 * */
	private String imei;
	
	/**
	 * 考勤时间
	 * */
	private Date recordTime;

	
	public String getBfb() {
		return bfb;
	}

	public void setBfb(String bfb) {
		this.bfb = bfb;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public List<ProjectMasterDto> getProjects() {
		return projects;
	}

	public void setProjects(List<ProjectMasterDto> projects) {
		this.projects = projects;
	}

	public String getIdCardType() {
		return idCardType;
	}

	public void setIdCardType(String idCardType) {
		this.idCardType = idCardType;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getIconPhoto() {
		return iconPhoto;
	}

	public void setIconPhoto(String iconPhoto) {
		this.iconPhoto = iconPhoto;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getRecordUserName() {
		return recordUserName;
	}

	public void setRecordUserName(String recordUserName) {
		this.recordUserName = recordUserName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(String isAuth) {
		this.isAuth = isAuth;
	}

	public String getProjectsCount() {
		return projectsCount;
	}

	public void setProjectsCount(String projectsCount) {
		this.projectsCount = projectsCount;
	}

	public Integer getTeamSysNo() {
		return teamSysNo;
	}

	public void setTeamSysNo(Integer teamSysNo) {
		this.teamSysNo = teamSysNo;
	}

	public BigDecimal getLng() {
		return lng;
	}

	public void setLng(BigDecimal lng) {
		this.lng = lng;
	}

	public BigDecimal getLat() {
		return lat;
	}

	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}
	
	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}
	
	 public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    
    
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectCode;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

	public String getDeviceWorkerName() {
		return deviceWorkerName;
	}

	public void setDeviceWorkerName(String deviceWorkerName) {
		this.deviceWorkerName = deviceWorkerName;
	}

	/**
	 * 手机考勤记录
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param person
	 * @param map
	 */
	public DeviceRecordDto(WorkerDto person, PersonPosition pp) {
		this.organizationCode = pp.getProject().getContractorOrgCode();
		this.projectCode = pp.getProject().getProjectCode();
		this.teamSysNo =person.getTeamSysNo();
		this.idCardType = pp.getIdCardType() + "";
		this.idCardNumber = pp.getIdCardNumber();
		this.lat = pp.getLat();
		this.lng = pp.getLng();
		this.type = 1;
		this.source = 2;
		this.recordTime = pp.getTime();
		this.imei= pp.getImei();
	}
	
	public DeviceRecordDto() {
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
}
