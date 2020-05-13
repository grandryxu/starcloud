package com.xywg.admin.modular.company.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.excel.ExcelUtils;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.util.DateUtils;
import com.xywg.admin.core.util.ExcelImportUtils;
import com.xywg.admin.modular.company.dao.CompanyEmployeMapper;
import com.xywg.admin.modular.company.dao.EmployeeMasterMapper;
import com.xywg.admin.modular.company.dao.SubContractorMapper;
import com.xywg.admin.modular.company.model.*;
import com.xywg.admin.modular.company.model.vo.SubContractorVo;
import com.xywg.admin.modular.company.service.ICompanyEmployeService;
import com.xywg.admin.modular.company.service.IEmployeeMasterService;
import com.xywg.admin.modular.company.service.ISubContractorService;
import com.xywg.admin.modular.company.utils.EmployeeMasterExcelUtil;
import com.xywg.admin.modular.company.utils.ServletsUtils;
import com.xywg.admin.modular.company.wrapper.EmployeeMasterWarpper;
import com.xywg.admin.modular.faceUtils.Identity;
import com.xywg.admin.modular.smz.dao.IfaLaborMapper;
import com.xywg.admin.modular.smz.model.IfaLabor;
import com.xywg.admin.modular.system.dao.DeptMapper;
import com.xywg.admin.modular.system.service.IAreaService;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.system.service.IDictService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 从业人员基础信息 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-28
 */
@Service
public class EmployeeMasterServiceImpl extends ServiceImpl<EmployeeMasterMapper, EmployeeMaster>
        implements IEmployeeMasterService {
	private static Logger log = LoggerFactory.getLogger(EmployeeMasterServiceImpl.class);
    @Resource
    protected SubContractorMapper subContractorMapper;
    @Resource
    private IfaLaborMapper ifaLaborMapper;
    @Autowired
    protected EmployeeMasterExcelUtil employeeMasterExcelUtil;
    @Autowired
    protected IDictService dictService;
    @Autowired
    protected IAreaService iAreaService;
    @Autowired
    protected ICompanyEmployeService companyEmployeService;
    @Resource
    protected CompanyEmployeMapper companyEmployeMapper;
    @Resource
    protected DeptMapper deptMapper;
    @Autowired
    protected IDeptService deptService;
    @Autowired
    private ISubContractorService subContractorService;

    @Transactional(rollbackFor = Exception.class)
    public boolean addEmployee(EmployeeMasterVo entity) {
        CompanyEmploye companyEmploye = new CompanyEmploye();

        EmployeeMaster em = this.baseMapper.getEmployeeByIdCard(entity.getIdCardNumber(), entity.getIdCardType());
        if (em == null) {
            if (StringUtils.isNotBlank(entity.getCellPhone())) {
                if (this.baseMapper.getByCellPhone(entity.getCellPhone()) != null) {
                    throw new XywgException(600, "手机号已被使用");
                }
            }
            EmployeeMaster employeeMaster = new EmployeeMaster(entity.getEmployeeName(), entity.getCellPhone(), entity.getIdCardType(), entity.getIdCardNumber(), entity.getNation()
                    , entity.getGender(), entity.getBirthday(), entity.getAddress(), entity.getCultureLevelType(), entity.getProfessionalType(), entity.getProfessionalLevel(), entity.getHeadImagePath(), entity.getWorkDate());
            if (entity.getIdCardType() == 1) {
                Identity.validByCard(entity.getIdCardNumber(), entity.getEmployeeName());
                employeeMaster.setIsAuth(1);
            } else {
                employeeMaster.setIsAuth(0);
            }
            this.baseMapper.addEmployee(employeeMaster);
            IfaLabor ifaLabor = new IfaLabor("buss_employee_master", employeeMaster.getId().longValue());
            ifaLaborMapper.insert(ifaLabor);
            companyEmploye.setEmployeSysNo(employeeMaster.getId());//系统编号
        } else {
            companyEmploye.setEmployeSysNo(em.getId());
        }
        String organizationCode = entity.getOrganizationCode();
        CompanyEmploye ce = companyEmployeService.getByIdCard(entity.getIdCardNumber(), entity.getIdCardType(), organizationCode);
        if (ce == null) {
            companyEmploye.setValue(companyEmploye, entity.getJobType(), entity.getJob(), entity.getJobStatus(),
                    entity.getTerminationDate(), entity.getRetireDate(), entity.getHireDate(), entity.getRemark(), entity.getOrganizationCode());
            companyEmployeService.insert(companyEmploye);
            return true;
        }
        if (ce.getIsDel() == 1) {
            companyEmployeMapper.updateIsDel(companyEmploye.getEmployeSysNo());
        } else {
            throw new XywgException(600, "人员已存在");
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean importEmployee(EmployeeMasterVo entity, Integer i) {
        CompanyEmploye companyEmploye = new CompanyEmploye();
        EmployeeMaster em = this.baseMapper.getEmployeeByIdCard(entity.getIdCardNumber(), entity.getIdCardType());
        if (em == null) {
            if (StringUtils.isNotBlank(entity.getCellPhone())) {
                if (this.baseMapper.getByCellPhone(entity.getCellPhone()) != null) {
                    throw new XywgException(600, "第" + i + "条记录手机号已存在");
                }
            }
            EmployeeMaster employeeMaster = new EmployeeMaster(entity.getEmployeeName(), entity.getCellPhone(), entity.getIdCardType(), entity.getIdCardNumber(), entity.getNation()
                    , entity.getGender(), entity.getBirthday(), entity.getAddress(), entity.getCultureLevelType(), entity.getProfessionalType(), entity.getProfessionalLevel(), entity.getHeadImagePath(), entity.getWorkDate());
            if (entity.getIdCardType() == 1) {
                try {
                    Identity.validByCard(entity.getIdCardNumber(), entity.getEmployeeName());
                    employeeMaster.setIsAuth(1);
                } catch (Exception e) {
                    throw new XywgException(600, "第" + i + "条记录身份证和姓名不匹配");
                }
            } else {
                employeeMaster.setIsAuth(0);
            }

            this.baseMapper.addEmployee(employeeMaster);
            companyEmploye.setEmployeSysNo(employeeMaster.getId());//系统编号
            IfaLabor ifaLabor = new IfaLabor("buss_employee_master", employeeMaster.getId().longValue());
            ifaLaborMapper.insert(ifaLabor);
        } else {
            companyEmploye.setEmployeSysNo(em.getId());
        }
        CompanyEmploye ce = companyEmployeService.getByIdCard(entity.getIdCardNumber(), entity.getIdCardType(), entity.getOrganizationCode());
        if (ce == null) {
            companyEmploye.setValue(companyEmploye, entity.getJobType(), entity.getJob(), entity.getJobStatus(),
                    entity.getTerminationDate(), entity.getRetireDate(), entity.getHireDate(), entity.getRemark(), entity.getOrganizationCode());
            companyEmployeService.insert(companyEmploye);
            return true;
        }
        if (ce.getIsDel() == 1) {
            companyEmployeMapper.updateIsDel(companyEmploye.getEmployeSysNo());
        } else {
            throw new XywgException(600, "第" + i + "条记录人员已存在");
        }
        return true;
    }

    @Override
    public List<Map<String, Object>> selectEmployees(Page<EmployeeMaster> page, Map<String, Object> map) {
        map.put("depts", deptService.getUserDeptAndSubdivisionOrganizationCode());
        return this.baseMapper.selectEmployees(page, map);
    }

    @Override
    public List<Map<String, Object>> selectEmployeesList(Map<String, Object> map) {
        map.put("depts", deptService.getUserDeptAndSubdivisionOrganizationCode());
        return this.baseMapper.selectEmployeesList(map);
    }

    @Override
    public EmployeeMasterVo getById(Integer companyEmployeId, String organizationCode) {
        return this.baseMapper.getById(companyEmployeId, organizationCode);
    }

    @Override
    public EmployeeMaster getEmployeeByIdCard(String idCardNumber, Integer idCardType) {
        return this.baseMapper.getEmployeeByIdCard(idCardNumber, idCardType);
    }

    @Override
    public List<Map<String, Object>> searchEmployee(String idCardNumber, Integer idCardType) {
        return this.baseMapper.searchEmployee(idCardNumber, idCardType);
    }

    @Override
    public List<Map<String, Object>> getEmployees() {
        return this.baseMapper.getEmployees();
    }

    @Override
    public EmployeeMaster getByCellPhone(String cellPhone) {
        return this.baseMapper.getByCellPhone(cellPhone);
    }

    @Override
    public List<Map<String, Object>> getListBySubContractor(Page<EmployeeMaster> page, Map<String, Object> map) {
        map.put("depts", deptService.getUserDeptAndSubdivisionOrganizationCode());
        return this.baseMapper.getListBySubContractor(page, map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String Import(MultipartFile excelFile, HttpServletRequest request) throws Exception {
        int n = 0;
        int maxCellNum = 16;
        List<EmployeeMasterVo> WorkerList = new ArrayList<EmployeeMasterVo>();
        File file = ExcelImportUtils.multipartFileToFile(excelFile, request); //文件类型专程File类型
        List<Map<String, Map<String, Object>>> list = ExcelImportUtils.getContent(new FileInputStream(file), file.getName(), EmployeeMasterVo.getTitleMap(), maxCellNum);
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                EmployeeMasterVo employeeMasterVo = new EmployeeMasterVo();
                //判断职称等级是否为空（职称查找依赖于职称等级）
                Map<String, Map<String, Object>> map = list.get(i);
                String professionalLevel = null;
                if (map.get("professionalLevel").get(ExcelImportUtils.value) != null) {
                    professionalLevel = map.get("professionalLevel").get(ExcelImportUtils.value).toString();
                    if (professionalLevel.equals("")) {
                        throw new XywgException(600, ExcelImportUtils.nullErrorMsg((Integer) map.get("professionalLevel").get(ExcelImportUtils.rowIndex), map.get("professionalLevel").get(ExcelImportUtils.title).toString()));//如果为空值，抛出异常
                    }
                }
                for (String key : map.keySet()) {
                    Map<String, Object> valueMap = map.get(key);
                    String value = valueMap.get(ExcelImportUtils.value).toString();//单元格的值
                    int columnIndex = (int) valueMap.get(ExcelImportUtils.columnIndex);//列
                    int rowIndex = (int) valueMap.get(ExcelImportUtils.rowIndex);//行
                    String title = valueMap.get(ExcelImportUtils.title).toString();//标题内容
                    PropertyUtils.setProperty(employeeMasterVo, key, getValue(value, columnIndex, rowIndex, title, maxCellNum, professionalLevel));
                }
                WorkerList.add(employeeMasterVo);
            }
            for (int i = 0; i < WorkerList.size(); i++) {
                this.importEmployee(WorkerList.get(i), i + 1);
                n++;
            }
        }

        return "导入" + n + "条数据";
    }

    /**
     * 读取excel的值
     *
     * @return
     */
    public Object getValue(String value, int columnIndex, int rowIndex, String title, int maxCellNum, String professional) {
        if (columnIndex > maxCellNum) {
            return "";
        }
        String[] checkColumn = {"0", "1", "2", "3", "4", "5", "6", "7", "9",  "13", "14"}; //不能为空的标题列
        if (Arrays.asList(checkColumn).contains(String.valueOf(columnIndex)) && value.equals("")) {
            throw new XywgException(600, ExcelImportUtils.nullErrorMsg(rowIndex, title));//如果为空值，抛出异常
        }
        if (columnIndex == 13 || columnIndex == 12) { //日期个格式校验
            if (value == null) {
                return "";
            }
            return ExcelImportUtils.isValidDate(value, rowIndex, title);
        }
        if (columnIndex == 2) {
            ExcelImportUtils.lenthCheck(50, value, rowIndex, title);
            return value;
        }else if (columnIndex == 0) {
            return dictService.selectNumByName("人员证件类型", value);
        }else if (columnIndex == 1) {
            ExcelImportUtils.lenthCheck(20, value, rowIndex, title);
            return value;
        }else if(columnIndex == 10){
        	if(StringUtils.isNotBlank(value)) {
        		 if (ExcelImportUtils.isPhone(value)) {
                     return value;
                 }else{
                     throw new XywgException(600, ExcelImportUtils.errorMsg(rowIndex, title));//手机号不符合规范
                 }
        	}
        	return "";
        } else if (columnIndex == 7) {
            return Integer.valueOf(value.equals("男") ? 1 : 0);
        }
        else if (columnIndex == 9) {
            return dictService.selectNumByName("民族", value).toString();
        } else if (columnIndex ==14) {
            ExcelImportUtils.lenthCheck(200, value, rowIndex, title);
            return value;
        }else if (columnIndex == 5) {
            professional = value;
            return dictService.selectNumByName("职称等级", value);
        } else if (columnIndex == 6) {
            if (professional.equals("初级职称")) {
                return dictService.selectNumByName("初级职称人员类别", value);
            } else if (professional.equals("中级职称")) {
                return dictService.selectNumByName("中级职称人员类别", value);
            } else {
                return dictService.selectNumByName("高级职称人员类别", value);
            }
        } else if (columnIndex == 8) {
            return dictService.selectNumByName("文化程度", value);
        } else if (columnIndex == 3) {
            SubContractor subContractor = subContractorMapper.getOrgidByName(value);
            if (subContractor != null) {
                return subContractor.getOrganizationCode();
            } else {
                throw new XywgException(600, "第" + rowIndex + "行" + title + "输入错误");
            }
        } else if (columnIndex == 4) {
            return Integer.valueOf(value.equals("全职") ? 0 : 1);
        } else if (columnIndex ==11) {
            ExcelImportUtils.lenthCheck(25, value, rowIndex, title);
            return value;
        }else if (columnIndex ==15) {
            ExcelImportUtils.lenthCheck(100, value, rowIndex, title);
            return value;
        }else {
            if (value == null) {
                return "";
            }
            return String.valueOf(value);
        }
    }

    @Override
    public boolean hire(EmployeeMaster employeeMaster) {
        CompanyEmploye ce = companyEmployeMapper.getJobStatusBySysNo(employeeMaster.getId(),employeeMaster.getOrganizationCode());
        if (ce.getJobStatus() == null) {
            return this.baseMapper.hire(employeeMaster);
        } else if (ce.getJobStatus() == 1) {
            throw new XywgException(600, "该员工已入职，请勿重复操作");
        } else if (ce.getJobStatus() == 0) {
            return this.baseMapper.hire(employeeMaster);
        } else {
            throw new XywgException(600, "该员工已退休，不能入职");
        }
    }

    @Override
    public boolean termination(EmployeeMaster employeeMaster) {
        CompanyEmploye ce = companyEmployeMapper.getJobStatusBySysNo(employeeMaster.getId(),employeeMaster.getOrganizationCode());
        if (ce.getJobStatus() == null) {
            throw new XywgException(600, "该员工未入职，请先入职");
        } else if (ce.getJobStatus() == 1) {
            return this.baseMapper.termination(employeeMaster);
        } else if (ce.getJobStatus() == 2){
            throw new XywgException(600, "该员工已退休，不能离职");
        } else {
            throw new XywgException(600, "该员工已离职，请勿重复操作");
        }
    }

    @Override
    public boolean retire(EmployeeMaster employeeMaster) {
        CompanyEmploye ce = companyEmployeMapper.getJobStatusBySysNo(employeeMaster.getId(),employeeMaster.getOrganizationCode());
        if (ce.getJobStatus() == null) {
            throw new XywgException(600, "该员工未入职，请先入职");
        } else if (ce.getJobStatus() == 1) {
            return this.baseMapper.retire(employeeMaster);
        } else if (ce.getJobStatus() == 0) {
            throw new XywgException(600, "离职人员不能进行退休操作");
        } else {
            throw new XywgException(600, "该员工已退休，请勿重复操作");
        }


    }

    @Override
    public void export(HttpServletResponse res, HttpServletRequest req, Map<String, Object> params) {
        List<Map<String, Object>> employees = this.selectEmployeesList(params);
        employees = (List<Map<String, Object>>) new EmployeeMasterWarpper(employees).warp();
        //构建下载文件时的文件名
        String fileName = "在职人员一览" + DateUtils.getDate("yyyyMMddHHmmss");
        boolean isMSIE = ServletsUtils.isMSBrowser(req);
//        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            if (isMSIE) {
                //IE浏览器的乱码问题解决
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                //万能乱码问题解决
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }
            res.setContentType("application/vnd.ms-excel");
            res.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"+.xlsx");
            os = res.getOutputStream();
            ExcelUtils.getInstance().exportObjects2Excel(employees, EmployeeMasterExport.class, true, os);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
//            if (bis != null) {
//                try {
//                    bis.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }

        }
    }

    @Override
    public List<Map<String, Object>> selectInJobEmployees(Page<EmployeeMaster> page, Map<String, Object> map) {
        map.put("depts", deptService.getUserDeptAndSubdivisionOrganizationCode());
        return this.baseMapper.selectInJobEmployees(page, map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByIds(Map<String, Object> map) {
        map.put("updateUser", ShiroKit.getUser().getName());
        return companyEmployeService.deleteByEmployeeIds(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editById(EmployeeMasterVo entity) {
        if (StringUtils.isNotBlank(entity.getCellPhone())) {
            EmployeeMaster e = this.baseMapper.getByCellPhone(entity.getCellPhone());
            if (e != null && !(e.getIdCardNumber().equals(entity.getIdCardNumber()) && e.getIdCardType() == entity.getIdCardType())) {
                throw new XywgException(600, "手机号已被使用");
            }
        }
        CompanyEmploye companyEmploye = new CompanyEmploye();
        EmployeeMaster employeeMaster = new EmployeeMaster(entity.getEmployeeName(), entity.getCellPhone(), entity.getIdCardType(), entity.getIdCardNumber(), entity.getNation()
                , entity.getGender(), entity.getBirthday(), entity.getAddress(), entity.getCultureLevelType(), entity.getProfessionalType(), entity.getProfessionalLevel(), entity.getHeadImagePath(), entity.getWorkDate());
        employeeMaster.setId(entity.getId());
        this.baseMapper.updateById(employeeMaster);
        companyEmploye.setValue(companyEmploye, entity.getJobType(), entity.getJob(), entity.getJobStatus(),
                entity.getTerminationDate(), entity.getRetireDate(), entity.getHireDate(), entity.getRemark(), entity.getOrganizationCode());
        companyEmploye.setEmployeSysNo(entity.getId());
        return companyEmployeService.edit(companyEmploye);
    }


    @Override
    public void addEmployeeMaster(List<Object> addList) {
        for (Object o : addList) {
            EmployeeMaster employeeMaster = new EmployeeMaster();
            stringToDateException();
            try {
              BeanUtils.copyProperties(employeeMaster, o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
           Map<String,Long> employeeMap= baseMapper.selectEmployeeByIdCard(employeeMaster.getIdCardNumber());
            if (employeeMap.get("num")==0){
                employeeMaster.setId(null);
                insert(employeeMaster);
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
}
