package com.xywg.admin.modular.company.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.common.constant.Const;
import com.xywg.admin.core.common.constant.state.ManagerStatus;
import com.xywg.admin.core.common.exception.BizExceptionEnum;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.util.ExcelImportUtils;
import com.xywg.admin.core.util.MD5Util;
import com.xywg.admin.modular.company.dao.CooperationSubContractorMapper;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.company.model.SubContractorExcel;
import com.xywg.admin.modular.company.service.ICooperationSubContractorService;
import com.xywg.admin.modular.projectSubContractor.dao.ProjectSubContractorMapper;
import com.xywg.admin.modular.smz.model.IfaLabor;
import com.xywg.admin.modular.smz.service.IfaLaborService;
import com.xywg.admin.modular.system.dao.DeptMapper;
import com.xywg.admin.modular.system.model.User;
import com.xywg.admin.modular.system.service.IAreaService;
import com.xywg.admin.modular.system.service.IDictService;
import com.xywg.admin.modular.system.service.IUserService;
import com.xywg.admin.modular.system.service.IWorkKindService;
import com.xywg.admin.modular.system.transfer.UserDto;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
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
@Service
public class CooperationSubContractorServiceImpl extends ServiceImpl<CooperationSubContractorMapper, SubContractor> implements ICooperationSubContractorService {

    @Autowired
    private IfaLaborService ifaLaborService;
    @Resource
    private IWorkKindService workKindService;
    @Resource
    private CooperationSubContractorMapper cooperationSubContractorMapper;
    @Resource
    private DeptMapper deptMapper;
    @Resource
    private ProjectSubContractorMapper projectSubContractorMapper;

    @Autowired
    private IDictService dictService;

    @Autowired
    private IAreaService areaService;
    @Autowired
    private IUserService userService;

    @Override
    public List<Map<String, Object>> selectList(Page page, Map map) {
        map.put("deptId", ShiroKit.getUser().getDeptId());
        return cooperationSubContractorMapper.list(page, map);
    }

    @Override
    public int changeState(Map<String, Object> map) {
        return cooperationSubContractorMapper.changeState(map);
    }

    /**
     * 查询所有承包商
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> selectGeneralContractors(String generalContractorName) {
        return cooperationSubContractorMapper.selectGeneralContractors(generalContractorName);
    }

    @Override
    public Integer addBussSubContractorConstruction(String constructionCode) {
        String socialCreditNumber = deptMapper.selectById(ShiroKit.getUser().getDeptId()).getSocialCreditNumber();
        return projectSubContractorMapper.addBussSubContractorConstruction(ShiroKit.getUser().getName(), socialCreditNumber, constructionCode);
    }

    /**
     * 获取所有的参建公司 无条件
     *
     * @param projectCode
     * @return
     */
    @Override
    public List<Map<String, Object>> getList(String projectCode) {
        return baseMapper.getList(projectCode, ShiroKit.getUser().getDeptId());
    }

    @Override
    public List<Map<String, Object>> getAllOtherCooperationSubContractor(Long projectMasterId) {
        return baseMapper.getAllOtherCooperationSubContractor(projectMasterId);
    }

    @Override
    public List<Map<String, Object>> selectList(Map<String, Object> map) {
        map.put("deptId", ShiroKit.getUser().getDeptId());
        return cooperationSubContractorMapper.listNoPage(map);
    }

    @Override
    @Transactional
    public boolean insert(SubContractor subContractor) {
        String socialCreditNumber = subContractor.getSocialCreditNumber();
        if ("".equals(socialCreditNumber)) {
            subContractor.setSocialCreditNumber(subContractor.getOrganizationCode());
        } else {
            subContractor.setOrganizationCode(socialCreditNumber);
        }
        //判重 判断公司是否已经添加过
        int count = baseMapper.hasSubContractor(subContractor.getSocialCreditNumber(),subContractor.getCompanyName());
        if (count > 0) {
            throw new XywgException(800, "公司" + subContractor.getCompanyName() + "已存在于系统中");
        }
        try {
            this.baseMapper.insert(subContractor);
        }catch(Exception e){
            throw new XywgException(800,"数据格式有误，请检查数据格式!");
        }
        
        if(subContractor.getIsSynchro()!= null && subContractor.getIsSynchro() == 1) {
            IfaLabor ifaLabor = new IfaLabor("buss_sub_contractor", subContractor.getId());
            ifaLaborService.insert(ifaLabor);
        }
        workKindService.addWorkKinds(socialCreditNumber);
        //添加公司 和 参建公司关系
        return retBool(this.addBussSubContractorConstruction(subContractor.getOrganizationCode()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String excelUpload(MultipartFile excelFile, HttpServletRequest request) throws Exception {
        int n = 0;
        int maxCellNum = 21;
        List<SubContractor> subContractorList = new ArrayList<SubContractor>();
        File file = ExcelImportUtils.multipartFileToFile(excelFile, request); //文件类型专程File类型
        List<Map<String, Map<String, Object>>> list = ExcelImportUtils.getContent(new FileInputStream(file), file.getName(), SubContractor.getTitleMap(), maxCellNum);
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                SubContractorExcel subContractorExcel = new SubContractorExcel();
                Map<String, Map<String, Object>> map = list.get(i);
                for (String key : map.keySet()) {
                    Map<String, Object> valueMap = map.get(key);
                    String value = valueMap.get(ExcelImportUtils.value).toString();//单元格的值
                    int columnIndex = (int) valueMap.get(ExcelImportUtils.columnIndex);//列
                    int rowIndex = (int) valueMap.get(ExcelImportUtils.rowIndex);//行
                    String title = valueMap.get(ExcelImportUtils.title).toString();//标题内容
                    PropertyUtils.setProperty(subContractorExcel, key, getValue(value, columnIndex, rowIndex, title));
                    subContractorExcel.setAreaCode("中国");
                }
                SubContractor subContractor = new SubContractor();
                BeanUtils.copyProperties(subContractorExcel,subContractor);
                //判断成立日期是否为空
                if(subContractorExcel.getEstablishDate()  != null) {
                	subContractor.setEstablishDate(new SimpleDateFormat("yyyy-MM-dd").format(subContractorExcel.getEstablishDate()));
                }
                subContractorList.add(subContractor);
            }
            for (int i = 0; i < subContractorList.size(); i++) {
                this.insertExcel(subContractorList.get(i),i+2);
                n++;
            }
        }

        return "导入" + n + "条数据";
    }

    private void insertExcel(SubContractor subContractor, int i) {
        String preStr = "第"+ (i-1) +"行,";
        if(!subContractor.getCompanyName().matches("^[\\u4e00-\\u9fa5/\\（\\）/\\(\\)]+$")){
            throw new XywgException(801,preStr+"企业名称只能是中文!");
        }
        /*if(!subContractor.getBizRegisterCode().matches("^\\d{15}$")){
            throw new XywgException(801,preStr+"请输入正确的15位工商营业执照注册号");
        }*/
        if(!subContractor.getSocialCreditNumber().matches("^[0-9A-Z]{18}$")){
            throw new XywgException(801,preStr+"请输入正确的社会信用代码或组织机构代码");
        }
       /* if(!"".equals(subContractor.getZipCode()) && !subContractor.getZipCode().matches("^[1-9]\\d{5}$")){
            throw new XywgException(801,preStr+"请输入正确的邮政编码");
        }*/
//        if(!"".equals(subContractor.getRepresentativeIdcardNumber()) && !subContractor.getRepresentativeIdcardNumber().matches("^[a-zA-Z0-9]{0,100}$")){
//            throw new XywgException(801,preStr+"请输入正确的法定代表人证件号码");
//        }
        if(!"".equals(subContractor.getRegistrationCapital().toString()) && !subContractor.getRegistrationCapital().toString().matches("(^[1-9](\\d+)?(\\.\\d{1,6})?$)|(^0$)|(^\\d\\.\\d{1,6}$)")){
            throw new XywgException(801,preStr+"请输入正确的注册资本");
        }
       /* if(!"".equals(subContractor.getFaxNumber()) && !subContractor.getFaxNumber().matches("(^(\\d{3,4}-)?\\d{7,8})$|^((1[0-9][0-9]\\d{8}$))")){
            throw new XywgException(801,preStr+"请输入正确的传真号码");
        }*/
        if(!"".equals(subContractor.getEmail()) && !subContractor.getEmail().matches("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$")){
            throw new XywgException(801,preStr+"请输入正确的企业电子邮箱");
        }
       /* if(!"".equals(subContractor.getContactPeoplePhone()) && !subContractor.getContactPeoplePhone().matches("^[0][1-9]{2,3}-[0-9]{5,10}$")){
            throw new XywgException(801,preStr+"请输入正确的联系人办公电话");
        }*/
//        if(!subContractor.getContactPeopleName().matches("^[\\u4E00-\\u9FA5A-Za-z]+$")){
//            throw new XywgException(801,preStr+"请输入正确的联系人姓名");
//        }
//        if(!subContractor.getContactPeopleCellPhone().matches("(^1[3-9]\\d{9}$)|(^09\\d{8}$)")){
//            throw new XywgException(801,preStr+"请输入正确的联系人手机号码");
//        }
    	subContractor.setCompanyName(subContractor.getCompanyName().replace("& #40;","(" ).replace("& #41;",")"));
        this.insert(subContractor);
    }


    /**
     * 导入解析方法
     *
     * @return
     */
    public Object getValue(String value, int columnIndex, int rowIndex, String title) {
        String[] checkColumn = {"0", "2","3"}; //不能为空的标题列
        if (Arrays.asList(checkColumn).contains(String.valueOf(columnIndex)) && value.equals("")) {
            throw new XywgException(600, ExcelImportUtils.nullErrorMsg(rowIndex, title));//如果为空值，抛出异常
        }
        if (columnIndex == 17) {//日期个格式校验
            return ExcelImportUtils.isValidDate(value,rowIndex,title);
        } else if (columnIndex == 1) {
        	if(StringUtils.isNotBlank(value)) {
        		return dictService.selectNumByName("单位性质", value).toString();
        	}
            return null;
        } else if (columnIndex == 10) {
        	if(StringUtils.isNotBlank(value)) {
        		return dictService.selectNumByName("人员证件类型", value);
        	}
        	return null;
        } else if (columnIndex == 15) {
        	if(StringUtils.isNotBlank(value)) {
        		return new BigDecimal(value);
        	}
        	return BigDecimal.ZERO;
        } else if (columnIndex == 16) {
        	if(StringUtils.isNotBlank(value)) {
        		return String.valueOf(dictService.selectNumByName("币种", value));
        	}
        	return null;
        } else if (columnIndex == 19) {
        	if(StringUtils.isNotBlank(value)) {
        		return String.valueOf(dictService.selectNumByName("经营状态",value));
        	}
        	return null;
        } else {
            return String.valueOf(value);
        }
    }

    @Override
    public void selectCompanyById(Object id) {
          Map<String,Object> company=this.cooperationSubContractorMapper.selectCompanyById(id);
          //更新企业账号和密码
        Map<String,Object> user=userService.selectAccount(company.get("organizationCode"));
        if (Integer.parseInt(user.get("sum").toString())==1){
            //更新密码
            User user1 = userService.selectById(user.get("id").toString());
            user1.setSalt(ShiroKit.getRandomSalt(5));
            user1.setPassword(ShiroKit.md5(Const.DEFAULT_PWD, user1.getSalt()));
            this.userService.updateById(user1);

        }else if (Integer.parseInt(user.get("sum").toString())==0){
            //新增账号和密码
            UserDto user2 = new UserDto();
            user2.setAccount(company.get("organizationCode").toString());
            user2.setPassword("123456");
            user2.setName(company.get("companyName").toString());
            user2.setIsEnterprise(1);
            user2.setDeptid(239);
            user2.setRoleid(139 + "");
            // 判断账号是否重复
            User theUser = userService.getByAccountAndId(company.get("organizationCode").toString(), "");
            if (theUser != null) {
                throw new XywgException(BizExceptionEnum.USER_ALREADY_REG);
            }
            // 完善账号信息
            user2.setSalt(ShiroKit.getRandomSalt(5));
            user2.setPassword(MD5Util.encrypt(user2.getPassword()));
            user2.setPassword(ShiroKit.md5(user2.getPassword(), user2.getSalt()));
            user2.setStatus(ManagerStatus.OK.getCode());
            user2.setUserType(1);
            user2.setCreatetime(new Date());
            user2.setFlowStatus("0");
            if(user2.getStartTime() == null){
                user2.setStartTime(new Date());
            }
            if(user2.getEndTime() == null){
                try {
                    user2.setEndTime(new SimpleDateFormat("yyyy-mm-dd").parse("9999-12-30"));
                } catch (ParseException e) {
                    System.out.println(e);
                }
            }
            userService.saveUserInfo(user2);
        }
    }
}
