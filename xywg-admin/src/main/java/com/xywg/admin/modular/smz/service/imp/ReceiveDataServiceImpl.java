package com.xywg.admin.modular.smz.service.imp;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xywg.admin.core.common.exception.BizExceptionEnum;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.support.HttpKit;
import com.xywg.admin.core.util.DateUtil;
import com.xywg.admin.modular.company.dao.ContractorWorkerMapper;
import com.xywg.admin.modular.company.dao.SubContractorMapper;
import com.xywg.admin.modular.company.model.ContractorWorker;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.device.dao.DeviceMapper;
import com.xywg.admin.modular.device.dao.DeviceRecordMapper;
import com.xywg.admin.modular.device.model.Device;
import com.xywg.admin.modular.device.model.DeviceRecord;
import com.xywg.admin.modular.device.utils.RedisUtil;
import com.xywg.admin.modular.project.dao.ProjectMasterMapper;
import com.xywg.admin.modular.project.dao.ProjectWorkerMapper;
import com.xywg.admin.modular.project.model.PorjectWorkerDto;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.model.ProjectWorker;
import com.xywg.admin.modular.projectSubContractor.dao.ProjectSubContractorMapper;
import com.xywg.admin.modular.projectSubContractor.model.ProjectSubContractor;
import com.xywg.admin.modular.report.dao.DeviceReportMapper;
import com.xywg.admin.modular.smz.service.IReceiveDataService;
import com.xywg.admin.modular.system.dao.AreaMapper;
import com.xywg.admin.modular.system.dao.DictMapper;
import com.xywg.admin.modular.team.dao.TeamMasterMapper;
import com.xywg.admin.modular.team.model.TeamMaster;
import com.xywg.admin.modular.team.model.TeamMemberShip;
import com.xywg.admin.modular.worker.dao.WorkerMasterMapper;
import com.xywg.admin.modular.worker.model.WorkerMaster;

/**
 * <p>
 * Title: ReceiveDataServiceImpl
 * </p>
 * <p>
 * Description:处理对外接口数据
 * </p>
 *
 * @author duanfen
 * @date 2019年7月23日
 */
@Service
public class ReceiveDataServiceImpl implements IReceiveDataService {
    @Autowired
    protected ProjectMasterMapper projectMasterMapper;

    @Autowired
    protected WorkerMasterMapper workerMasterMapper;

    @Autowired
    protected ProjectWorkerMapper projectWorkerMapper;

    @Autowired
    protected DeviceRecordMapper deviceRecordMapper;

    @Autowired
    protected DeviceReportMapper deviceReportMapper;

    @Autowired
    protected DeviceMapper deviceMapper;

    @Autowired
    private TeamMasterMapper teamMapper;

    @Autowired
    private ContractorWorkerMapper contractorWorkerMapper;

    @Autowired
    private ProjectSubContractorMapper projectSubContractorMapper;

    @Autowired
    private SubContractorMapper subContractorMapper;

    @Autowired
    private DictMapper dictMapper;

    @Autowired
    private AreaMapper areaMapper;

    @Autowired
    private RedisUtil redisUtil;

    private static final Log log = LogFactory.getLog(ReceiveDataServiceImpl.class);

    @Override
    public void saveCompany(SubContractor contract) {
        String username = getName();
        log.info(username + "-----调用企业新增开始！");
        String organizationCode = contract.getOrganizationCode();
        // 判断社会统一信用代码是否为空
        isOrganizationCodeNull(organizationCode);

        String companyName = contract.getCompanyName();
        if (StringUtils.isBlank(companyName)) {
            throw new XywgException(BizExceptionEnum.COMPANY_NAME_NOT_NULL);
        }
        // 判断企业是否存在
        if (subContractorMapper.getSubContractorByOrganizationCode(organizationCode) != null) {
            throw new XywgException(BizExceptionEnum.ORGANIZATION_ALREADY_EXISTS);
        }
        String areaCode = contract.getAreaCode();
        //areaCodeValid(areaCode);

        String phone = contract.getPhoneNumber();
        // 判断电话号码长度
        if (StringUtils.isNotBlank(phone)) {
            phoneValid(phone);
        }
        String cellPhone = contract.getContactPeopleCellPhone();
        // 判断联系人电话号码长度
        if (StringUtils.isNotBlank(cellPhone)) {
            phoneValid(cellPhone);
        }

        String representativeName = contract.getRepresentativeName();
        // 判断法人姓名是否为空
        if (StringUtils.isBlank(representativeName)) {
            throw new XywgException(BizExceptionEnum.REPRESENTATIVE_NAME_NOT_NULL);
        }
        // 判断法人身份证号
        String idCardNumber = contract.getRepresentativeIdcardNumber();
        idCardNumberValid(idCardNumber);
        contract.setRepresentativeIdcardType(1);
        contract.setSocialCreditNumber(organizationCode);
        contract.setOrganizationType("16");
        contract.setBizRegisterCode(organizationCode);
        contract.setIsAudit(1);
        contract.setCreateUser(username);
        contract.setCreateDate(new Date());
        this.subContractorMapper.insert(contract);
        log.info(username + "-----调用企业新增结束！");
    }

    @Override
    @Transactional
    public String saveProject(ProjectMaster project) {
        String username = getName();
        log.info(username + "-----调用项目新增开始！");
        // json转换，判断json是否为空
        if (project == null) {
            throw new XywgException(BizExceptionEnum.PROJECT_NOT_EXISTS);
        }
        String uuid = UUID.randomUUID().toString();
        String organizationCode = project.getContractorOrgCode();
        // 判断社会统一信用代码是否为空
        isOrganizationCodeNull(organizationCode);
        // 查询企业信息
        String companyName = isCompanyExist(organizationCode);
        String projectName = project.getProjectName();
        // 判断项目名称是否为空
        projectNameNull(projectName);
        // 根据项目名称判重
        hasProjectMasterByName(projectName);

        String projectActivityType = project.getProjectActivityType();
        // 判断项目活动类型系统是否存在
        if (StringUtils.isBlank(projectActivityType)
                || dictMapper.queryWorkTypeByNum(projectActivityType, null, "项目活动类型") <= 0) {
            throw new XywgException(BizExceptionEnum.PROJECT_ACTIVITY_TYPE_ERROR);
        }

        String projectCategory = project.getProjectCategory();
        // 判断项目分类系统是否存在
        if (StringUtils.isBlank(projectCategory) || dictMapper.queryWorkTypeByNum(projectCategory, null, "项目分类") <= 0) {
            throw new XywgException(BizExceptionEnum.PROJECT_CATEGROY_ERROR);
        }

        String areaCode = project.getAreaCode();
        // 项目区域不能为空
//		areaCodeValid(areaCode);
        String projectStatus = project.getProjectStatus();
        // 判断项目状态统是否存在
        if (StringUtils.isBlank(projectStatus) || dictMapper.queryWorkTypeByNum(projectStatus, null, "项目状态") <= 0) {
            throw new XywgException(BizExceptionEnum.PROJECT_STATUS_ERROR);
        }

        // 验证建设单位社会统一信用代码长度
        String buildOrganizationCode = project.getBuildCorporationCode();
        buildOrganizationCodeError(buildOrganizationCode);

        String phone = project.getBuildPhone();
        // 判断电话号码长度
        if (StringUtils.isNotBlank(phone)) {
            phoneValid(phone);
        }
        // 保存实名制项目数据
        project.setProjectCode(uuid);
        project.setGeneralContractorName(companyName);
        project.setDeviceType(3);
        project.setStatus(1);
        project.setIsSynchro(0);
        project.setIsWatermark(0);
        project.setVirtualId(null);
        project.setIsDel(0);
        project.setProjectActivityType("1");
        project.setIsMajorProject(1);
        project.setCreateUser(username);
        project.setCreateDate(new Date());
        this.projectMasterMapper.insert(project);
        // 保存项目公司关系
        ProjectSubContractor projectSubContractor = new ProjectSubContractor(uuid, organizationCode,
                project.getBankName(), project.getBankNumber());
        projectSubContractorMapper.insert(projectSubContractor);
        log.info(username + "-----调用项目新增结束！");
        return uuid;
    }

    @Override
    @Transactional
    public void saveDevice(Device device) {
        String username = getName();
        log.info(username + "-----调用设备新增开始！");
        // json转换
        String projectCode = device.getProjectCode();
        // 判断项目编号是否为空
        isProjectCodeNull(projectCode);
        // 查询项目是否存在
        ProjectMaster project = hasProjectMasterByCode(projectCode);
        // 判断设备号是否为空
        String sn = device.getSn();
        if (StringUtils.isBlank(sn)) {
            throw new XywgException(BizExceptionEnum.SN_NOT_NULL);
        }
        // 判断该设备是否已存在
        if (this.deviceMapper.getByDeviceSn(sn) != null) {
            throw new XywgException(BizExceptionEnum.SN_ALREADY_EXISTS);
        }
        // 判断名称是否为空
        String name = device.getName();
        if (StringUtils.isBlank(name)) {
            throw new XywgException(BizExceptionEnum.DEVICE_NAME_NOT_NULL);
        }
        // 判断状态是否为空
        if (device.getState() == null) {
            throw new XywgException(BizExceptionEnum.STATE_TYPE_CODE_IS_NULL);
        }
        // 判断出入类型是否为空
        if (StringUtils.isBlank(name)) {
            throw new XywgException(BizExceptionEnum.TYPE_IS_NULL);
        }
        device.setOrganizationCode(project.getContractorOrgCode());
        device.setCreateUser(username);
        device.setCreateDate(new Date());
        // 保存实名制设备数据
        this.deviceMapper.insert(device);
        log.info(username + "-----调用设备新增结束！");
    }

    @Override
    @Transactional
    public void saveTeamMaster(TeamMaster teamMaster) {
        String username = getName();
        log.info(username + "-----调用班组新增开始！");
        // json转换
        String projectCode = teamMaster.getProjectCode();
        // 判断项目编号是否为空
        isProjectCodeNull(projectCode);
        // 查询项目是否存在
        ProjectMaster project = this.hasProjectMasterByCode(projectCode);
        String teamName = teamMaster.getTeamName();
        if (StringUtils.isBlank(teamName)) {
            throw new XywgException(BizExceptionEnum.TEAM_NAME_IS_NULL);
        }
        // 判断项目下班组是否存在
        if (this.teamMapper.getByName(teamName, projectCode) != null) {
            throw new XywgException(BizExceptionEnum.TEAM_ALREADY_EXISTS);
        }
        teamMaster.setOrganizationCode(project.getContractorOrgCode());
        teamMaster.setCreateUser(username);
        teamMaster.setCreateDate(new Date());
        this.teamMapper.insert(teamMaster);
        teamMaster.setTeamSysNo(teamMaster.getId());
        this.teamMapper.updateById(teamMaster);
        log.info(username + "-----调用班组新增结束！");
    }

    @Override
    @Transactional
    public String saveProjectPersonnel(PorjectWorkerDto person) {
        String username = getName();
        log.info(username + "-----调用项目人员关系新增开始！");
        String uuid = UUID.randomUUID().toString();
        String projectCode = person.getProjectCode();
        // 判断项目编号是否为空
        isProjectCodeNull(projectCode);
        // 查询项目是否存在
        ProjectMaster project = this.hasProjectMasterByCode(projectCode);
        String organizationCode = project.getContractorOrgCode();

        String idCardNumber = person.getIdCardNumber();

        idCardNumberValid(idCardNumber);

        String teamName = person.getTeamName();
        // 判断班组名称是否为空
        if (teamName == null) {
            throw new XywgException(BizExceptionEnum.TEAM_NAME_IS_NULL);
        }
        // 判断项目下班组是否存在
        TeamMaster team = this.teamMapper.getByName(teamName, projectCode);
        if (team == null) {
            throw new XywgException(BizExceptionEnum.TEAM_IS_NUll);
        }

        WorkerMaster chWm = this.workerMasterMapper.getWorkerByIdCard(idCardNumber, 1);
        // 工人基础信息没有就新增
        if (chWm == null) {
            chWm = workerAdd(person, idCardNumber, username);
        }

        Integer joinStatus = person.getJoinStatus();
        if (joinStatus != 1 && joinStatus != 2 && joinStatus != 3 && joinStatus == null) {
            throw new XywgException(BizExceptionEnum.JOIN_STATUS_ERROR);
        }

        Date entryTime = person.getEntryTime();
        // 判断进场时间是否为空
        if (entryTime == null) {
            throw new XywgException(BizExceptionEnum.ENTRYTIME_IS_NULL);
        }
        // 判断项目人员关系是否已存在
        if (this.projectWorkerMapper.getPersonInfo(projectCode, 1, idCardNumber) != null) {
            throw new XywgException(BizExceptionEnum.PROJECT_WORKER_RELATION);
        }

        // 人员项目关系保存
        ProjectWorker pw = new ProjectWorker(projectCode, organizationCode, team.getTeamSysNo(), "1", idCardNumber,
                joinStatus, chWm.getWorkTypeCode(), chWm.getCellPhone(), 0, 2);
        pw.setUuid(uuid);
        pw.setIsDel(0);
        pw.setCreateUser(username);
        pw.setCreateDate(new Date());
        this.projectWorkerMapper.insert(pw);

        // 人员班组关系保存
        int teamWorkType = 1;
        // 判断是否是班组长
        if (Objects.equals(team.getTeamLeaderIdNumber(), idCardNumber)) {
            teamWorkType = 0;
        }
        TeamMemberShip tms = new TeamMemberShip(team.getTeamSysNo(), 1, idCardNumber, teamWorkType);
        teamMapper.addMember(tms);
        // 保存公司工人关系
        if (contractorWorkerMapper.getByIdCardAndOrganization(chWm.getIdCardNumber(), 1, organizationCode) == null) {
            ContractorWorker contractorWorker = new ContractorWorker();
            contractorWorker.setIdCardType(1);
            contractorWorker.setIdCardNumber(chWm.getIdCardNumber());
            contractorWorker.setWorkName(chWm.getWorkerName());
            contractorWorker.setOrganizationCode(organizationCode);
            contractorWorkerMapper.insert(contractorWorker);
        }
        log.info(username + "-----调用项目人员关系新增结束！");
        return uuid;
    }

    /**
     * 项目和工人信息关联关系数据
     *
     * @param chWm
     * @param ps
     * @author duanfen
     */
    public WorkerMaster workerAdd(PorjectWorkerDto person, String idCardNumber, String username) {
        String birthPlaceCode = idCardNumber.substring(0, 2) + "0000";
        String workerName = person.getWorkerName();
        // 判断名称是否为空
        if (StringUtils.isBlank(workerName)) {
            throw new XywgException(BizExceptionEnum.WORKER_NAME_IS_NULL);
        }

        String workTypeCode = person.getWorkTypeCode();
        String workTypeName = person.getWorkTypeName();
        // 判断工种是否为空
        if (StringUtils.isBlank(workTypeCode) || StringUtils.isBlank(workTypeName)) {
            throw new XywgException(BizExceptionEnum.WORK_TYPE_CODE_IS_NULL);
        }
        // 判断工种系统是否存在
        if (dictMapper.queryWorkTypeByNum(workTypeCode, workTypeName, "工种字典数据") <= 0) {
            throw new XywgException(BizExceptionEnum.WORK_TYPE_CODE_NOT_EXIST);
        }
        String phone = person.getCellPhone();
        // 判断电话号码是否为空
//        if (StringUtils.isBlank(phone)) {
//            throw new XywgException(BizExceptionEnum.CELL_PHONE_IS_NULL);
//        }
//        // 判断电话号码长度
//        phoneValid(phone);

        String nation = person.getNation();
        // 判断民族系统是否存在
        if (dictMapper.queryWorkTypeByNum(nation, null, "民族") <= 0) {
            throw new XywgException(BizExceptionEnum.NATION_NOT_EXIST);
        }

        // 判断性别是否正确
        Integer gender = person.getGender();
        if (gender == null || gender > 1 || gender < 0) {
            throw new XywgException(BizExceptionEnum.GENDER_ERROR);
        }

        String address = person.getAddress();
        if (StringUtils.isBlank(address)) {
            throw new XywgException(BizExceptionEnum.ADDRESS_IS_NULL);
        }

        // 判断信息是否存在
        WorkerMaster chWm = new WorkerMaster(workerName, idCardNumber, gender, nation, birthPlaceCode,
                person.getAddress(), phone, workTypeCode, person.getStartDate(), person.getEndDate());
        // 生日信息处理
        chWm.setBirthday(DateUtil.parse(idCardNumber.substring(5, 8), "yyyy-MM-dd"));
        chWm.setCreateUser(username);
        chWm.setCreateDate(new Date());
        // 保存工人数据
        this.workerMasterMapper.insert(chWm);
        return chWm;
    }

    @Override
    @Transactional
    public void saveDeviceRecord(DeviceRecord record) {
        String username = getName();
        log.info(username + "-----调用考勤记录新增开始！");
        // json转换
        String uuid = record.getProjectWorkerId();
        // 判断项目关系是否存在
        if (uuid == null) {
            throw new XywgException(BizExceptionEnum.UUID_IS_NULL);
        }
        // 查询项目人员关系信息，并且判断是否存在
        PorjectWorkerDto pw = this.projectWorkerMapper.queryProjectWorkerByUuid(uuid);
        if (pw == null) {
            throw new XywgException(BizExceptionEnum.UUID_ERROR);
        }
        String sn = record.getDeviceSn();
        // 判断设备是否为空
        if (sn == null) {
            throw new XywgException(BizExceptionEnum.SN_NOT_NULL);
        }
        // 判断设备是否存在
        Device device = this.deviceMapper.getByDeviceSn(sn);
        if (device == null) {
            throw new XywgException(BizExceptionEnum.SN_NOT_EXISTS);
        }
        // 判断考勤时间不能为空
        if (record.getTime() == null) {
            throw new XywgException(BizExceptionEnum.TIME_IS_NULL);
        }
        // 判断考勤类型是否为空或者是否正确，为空默认上班，
        record.setDeviceType(device.getType());
        record.setOrganizationCode(pw.getOrganizationCode());
        record.setProjectCode(pw.getProjectCode());
        record.setTeamSysNo(pw.getTeamSysNo());
        record.setIdCardType(1);
        record.setIdCardNumber(pw.getIdCardNumber());
        record.setWorkerName(pw.getWorkerName());
        record.setSource(4);
        record.setIsValid(1);
        record.setType(1);
        record.setIsDeal("0");
        record.setCreateUser(username);
        record.setCreateDate(new Date());
        // 保存考勤数据
        this.deviceRecordMapper.insert(record);
        log.info(username + "-----调用考勤记录新增结束！");
    }

    /**
     * <p>
     * Title: isOrganizationCodeNull
     * </p>
     * <p>
     * Description:判断社会统一信用代码是否为空
     * </p>
     *
     * @author duanfen
     * @date 2019年7月23日
     */
    public void isOrganizationCodeNull(String organizationCode) {
        // 判断社会统一信用代码是否为空
        if (StringUtils.isBlank(organizationCode)) {
            throw new XywgException(BizExceptionEnum.ORGANIZATION_CODE_NOT_NULL);
        }
        if (organizationCode.length() > 20) {
            throw new XywgException(BizExceptionEnum.ORGANIZATION_CODE_ERROR);
        }
    }

    /**
     * <p>
     * Title: buildOrganizationCodeError
     * </p>
     * <p>
     * Description:判断建设单位社会统一信用代码是否为空
     * </p>
     *
     * @author duanfen
     * @date 2019年7月23日
     */
    public void buildOrganizationCodeError(String organizationCode) {
        // 判断社会统一信用代码是否为空
        if (StringUtils.isNotBlank(organizationCode) && organizationCode.length() > 20) {
            throw new XywgException(BizExceptionEnum.BUILD_ORGANIZATION_CODE_ERROR);
        }
    }

    /**
     * <p>
     * Title: isCompanyExist
     * </p>
     * <p>
     * Description:判断社会统一信用代码是否为空
     * </p>
     *
     * @author duanfen
     * @date 2019年7月23日
     */
    public String isCompanyExist(String organizationCode) {
        // 判断企业是否存在
        Map<String, Object> map = subContractorMapper.getSubContractorByOrganizationCode(organizationCode);
        if (map == null) {
            throw new XywgException(BizExceptionEnum.COMPANY_NOT_EXISTS);
        }
        return (String) map.get("companyName");
    }

    /**
     * <p>
     * Title: projectNameNull
     * </p>
     * <p>
     * Description:判断项目名称是否为空
     * </p>
     *
     * @author duanfen
     * @date 2019年7月23日
     */
    public void projectNameNull(String projectName) {
        if (StringUtils.isBlank(projectName)) {
            throw new XywgException(BizExceptionEnum.PROJECT_NAME_NOT_NULL);
        }
    }

    /**
     * <p>
     * Title: isProjectCodeNull
     * </p>
     * <p>
     * Description:判断项目编号是否为空
     * </p>
     *
     * @author duanfen
     * @date 2019年7月23日
     */
    public void isProjectCodeNull(String projectCode) {
        if (StringUtils.isBlank(projectCode)) {
            throw new XywgException(BizExceptionEnum.PROJECT_CODE_NOT_NULL);
        }
    }

    /**
     * <p>
     * Title: hasProjectMasterByName
     * </p>
     * <p>
     * Description:根据项目名称判重
     * </p>
     *
     * @author duanfen
     * @date 2019年7月23日
     */
    public void hasProjectMasterByName(String projectName) {
        List<Map<String, Object>> hasProjectMaster = projectMasterMapper.getByProjectName(projectName);
        if (hasProjectMaster.size() > 0) {
            throw new XywgException(BizExceptionEnum.PROJECT_ALREADY_EXISTS);
        }
    }

    /**
     * <p>
     * Title: areaCodeValid
     * </p>
     * <p>
     * Description:验证地区是否为空
     * </p>
     *
     * @author duanfen
     * @date 2019年7月25日
     */
    public void areaCodeValid(String areaCode) {
        // 判断地区是否为空
        if (StringUtils.isBlank(areaCode)) {
            throw new XywgException(BizExceptionEnum.AREA_CODE_NOT_NULL);
        }
        // 判断区域是否为数字
        if (!isPositiveInteger(areaCode)) {
            throw new XywgException(BizExceptionEnum.AREA_CODE_ERROR);
        }
        // 判断区域是否存在
        if (this.areaMapper.getShortNameById(Integer.valueOf(areaCode)) == null) {
            throw new XywgException(BizExceptionEnum.AREA_CODE_ERROR);
        }
    }

    /**
     * <p>
     * Title: areaCodeValid
     * </p>
     * <p>
     * Description:验证手机号长度
     * </p>
     *
     * @author duanfen
     * @date 2019年7月25日
     */
    public void phoneValid(String phone) {
        // 判断电话号码长度
        if (phone.length() < 11 || phone.length() > 13) {
            throw new XywgException(BizExceptionEnum.CELL_PHONE_NOT_VALID);
        }
    }

    /**
     * <p>
     * Title: hasProjectMasterByCode
     * </p>
     * <p>
     * Description:根据编号查询项目
     * </p>
     *
     * @author duanfen
     * @date 2019年7月23日
     */
    public ProjectMaster hasProjectMasterByCode(String projectCode) {
        ProjectMaster hasProjectMaster = projectMasterMapper.getProjectByProjectCode(projectCode);
        if (hasProjectMaster == null) {
            throw new XywgException(BizExceptionEnum.PROJECT_NOT_EXIST);
        }
        return hasProjectMaster;
    }

    /**
     * 获取登录账号
     * <p>
     * Title: getName
     * </p>
     * <p>
     * Description:
     * </p>
     *
     * @author duanfen
     * @date 2019年7月24日
     */
    public String getName() {
        String tokens = HttpKit.getRequest().getHeader("token");
        // 判断账号是否有值
        if (StringUtils.isBlank(tokens)) {
            throw new XywgException(BizExceptionEnum.ACCOUNT_ERROR);
        }
        Object username = redisUtil.get(tokens);
        // 判断账号是否存在
        if (username == null) {
            throw new XywgException(BizExceptionEnum.USER_NOT_EXISTED);
        }
        return (String) username;
    }

    public final static String REGEX_POSITIVE_INTEGER = "^\\+?[1-9]\\d*$"; //$NON-NLS-1$

    /**
     * 正整数[1,MAX)
     *
     * @param orginal
     * @return boolean
     * @Description: 是否为正整数
     */
    public static boolean isPositiveInteger(String orginal) {
        Pattern pattern = Pattern.compile(REGEX_POSITIVE_INTEGER);
        Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();

    }

    /**
     * <p>
     * Title: IdCardNumberValid
     * </p>
     * <p>
     * Description:判断身份证
     * </p>
     *
     * @author duanfen
     * @date 2019年7月25日
     */
    public void idCardNumberValid(String idCardNumber) {
        // 判断身份证号身份为空
        if (StringUtils.isBlank(idCardNumber)) {
            throw new XywgException(BizExceptionEnum.ID_CARD_NUMBER_IS_NULL);
        }
        // 判断身份证号长度
        if (idCardNumber.length() != 15 && idCardNumber.length() != 18) {
            throw new XywgException(BizExceptionEnum.ID_CARD_NUMBER_ERROR);
        }

    }

}
