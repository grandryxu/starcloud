package com.xywg.admin.modular.company.controller;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.company.model.EmployeeMaster;
import com.xywg.admin.modular.company.model.EmployeeMasterVo;
import com.xywg.admin.modular.company.model.PersonalCertificationsVo;
import com.xywg.admin.modular.company.service.IEmployeeMasterService;
import com.xywg.admin.modular.company.service.IPersonalCertificationsService;
import com.xywg.admin.modular.company.wrapper.EmployeeMasterWarpper;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.system.service.IDictService;
import com.xywg.admin.modular.system.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 从业人员基础信息控制器
 *
 * @author wangcw
 * @Date 2018-05-28 11:10:28
 */
@Controller
@RequestMapping("/employeeMaster")
public class EmployeeMasterController extends BaseController {
    @Autowired
    private IDictService dictService;
    private String PREFIX = "/company/employeeMaster/";

    @Autowired
    private IEmployeeMasterService employeeMasterService;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IPersonalCertificationsService personalCertificationsService;
//    @Autowired
//    private UploadService uploadService;

    /**
     * 跳转到从业人员基础信息首页
     */
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("dictCultureLevelType", dictService.selectByName("文化程度"));
        model.addAttribute("dictGender", dictService.selectByName("性别"));
        model.addAttribute("dictAge", dictService.selectByName("年龄区间"));
        return PREFIX + "employeeMaster.html";
    }

    /**
     * 跳转到添加从业人员基础信息
     */
    @RequestMapping("/employeeMaster_add")
    public String employeeMasterAdd(Model model) {
        String account = ShiroKit.getUser().getAccount();
        model.addAttribute("companys", deptService.getList(account));
        model.addAttribute("dictGender", dictService.selectByName("性别"));
        model.addAttribute("dictNation", dictService.selectByName("民族"));
        model.addAttribute("dictIdCardType", dictService.selectByName("人员证件类型"));
        model.addAttribute("dictProfessionalLevel", dictService.selectByName("职称等级"));
        model.addAttribute("dictWorkTypeCode", dictService.selectByName("工种字典数据"));
        model.addAttribute("dictCultureLevelType", dictService.selectByName("文化程度"));
        return PREFIX + "employeeMaster_add.html";
    }

    /**
     * 跳转到修改从业人员基础信息
     */
    @RequestMapping("/employeeMaster_update/{employeeMasterId}/{organizationCode}")
    public String employeeMasterUpdate(@PathVariable Integer employeeMasterId,@PathVariable String organizationCode, Model model) {
        EmployeeMasterVo employeeMasterVo = employeeMasterService.getById(employeeMasterId,organizationCode);
        String account = ShiroKit.getUser().getAccount();
        model.addAttribute("companys", deptService.getList(account));
        model.addAttribute("dictGender", dictService.selectByName("性别"));
        model.addAttribute("dictNation", dictService.selectByName("民族"));
        model.addAttribute("dictIdCardType", dictService.selectByName("人员证件类型"));
        model.addAttribute("dictProfessionalLevel", dictService.selectByName("职称等级"));
        model.addAttribute("dictCultureLevelType", dictService.selectByName("文化程度"));
        model.addAttribute("item", employeeMasterVo);
        LogObjectHolder.me().set(employeeMasterVo);
        return PREFIX + "employeeMaster_edit.html";
    }

    /**
     * 查看详情
     */
    @RequestMapping("/employeeMaster_view/{employeeMasterId}/{organizationCode}")
    public String workerMasterView(@PathVariable Integer employeeMasterId,@PathVariable String organizationCode, Model model) {
        EmployeeMasterVo employeeMasterVo = employeeMasterService.getById(employeeMasterId,organizationCode);
        List<PersonalCertificationsVo> personalCertifications = personalCertificationsService.getByIdCard(employeeMasterVo.getIdCardNumber(), employeeMasterVo.getIdCardType());
        String account = ShiroKit.getUser().getAccount();
        model.addAttribute("companys", deptService.getList(account));
        model.addAttribute("dictGender", dictService.selectByName("性别"));
        model.addAttribute("dictNation", dictService.selectByName("民族"));
        model.addAttribute("dictIdCardType", dictService.selectByName("人员证件类型"));
        model.addAttribute("dictProfessionalLevel", dictService.selectByName("职称等级"));
        model.addAttribute("dictCultureLevelType", dictService.selectByName("文化程度"));
        model.addAttribute("dictCertificationTypeCode", dictService.selectByName("证书类型"));
        model.addAttribute("employees", employeeMasterService.getEmployees());
        model.addAttribute("item", employeeMasterVo);
        model.addAttribute("item1", personalCertifications);
        LogObjectHolder.me().set(employeeMasterVo);
        return PREFIX + "employeeMaster_info.html";
    }

    /**
     * 获取从业人员基础信息列表
     */
    @RequestMapping(value = "/pmList")
    @ResponseBody
    public Object selectInJobEmployees(@RequestParam Map<String, Object> map) {

        Page<EmployeeMaster> page = new PageFactory<EmployeeMaster>().defaultPage();
        map.put("deptId", ShiroKit.getUser().getDeptId());
        List<Map<String, Object>> employees = employeeMasterService.selectInJobEmployees(page, map);
        page.setRecords((List<EmployeeMaster>) new EmployeeMasterWarpper(employees).warp());
        return super.packForBT(page);
    }
    /**
     * 获取从业人员基础信息列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map) {

        Page<EmployeeMaster> page = new PageFactory<EmployeeMaster>().defaultPage();
        map.put("deptId", ShiroKit.getUser().getDeptId());
        List<Map<String, Object>> employees = employeeMasterService.selectEmployees(page, map);
        page.setRecords((List<EmployeeMaster>) new EmployeeMasterWarpper(employees).warp());
        return super.packForBT(page);
    }

    /**
     * 获取从业人员基础信息列表
     */
    @RequestMapping(value = "/getListBySubContractor")
    @ResponseBody
    public Object getListBySubContractor(@RequestParam Map<String, Object> map) {
        Page<EmployeeMaster> page = new PageFactory<EmployeeMaster>().defaultPage();
        List<Map<String, Object>> employees = employeeMasterService.getListBySubContractor(page, map);
        page.setRecords((List<EmployeeMaster>) new EmployeeMasterWarpper(employees).warp());
        return super.packForBT(page);
    }

    /**
     * 新增从业人员基础信息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(EmployeeMasterVo employeeMasterVo) {
        employeeMasterService.addEmployee(employeeMasterVo);
        return SUCCESS_TIP;
    }

    /**
     * 删除从业人员基础信息
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Map<String, Object> map) {
        employeeMasterService.deleteByIds(map);
        return SUCCESS_TIP;
    }


    /**
     * 新增从业人员基础信息
     */
    @RequestMapping(value = "/hire")
    @ResponseBody
    public Object hire(EmployeeMaster employeeMaster) {
        employeeMasterService.hire(employeeMaster);
        return SUCCESS_TIP;
    }
    /**
     * 入职
     */
    @RequestMapping(value = "/termination")
    @ResponseBody
    public Object termination(EmployeeMaster employeeMaster) {
        employeeMasterService.termination(employeeMaster);
        return SUCCESS_TIP;
    }
    /**
     * 离职
     */
    @RequestMapping(value = "/retire")
    @ResponseBody
    public Object retire(EmployeeMaster employeeMaster) {
        employeeMasterService.retire(employeeMaster);
        return SUCCESS_TIP;
    }

    /**
     * 退休
     */
    @RequestMapping(value = "/getProfessionalType")
    @ResponseBody
    public Object getProfessionalType(Integer id) {
        if (id == 1) {
            return dictService.selectByName("初级职称人员类别");
        } else if (id == 2) {
            return dictService.selectByName("中级职称人员类别");
        } else {
            return dictService.selectByName("高级职称人员类别");
        }
    }

    /**
     * 修改从业人员基础信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(EmployeeMasterVo employeeMasterVo) {
        employeeMasterService.editById(employeeMasterVo);
        return SUCCESS_TIP;
    }

    /**
     * 从业人员基础信息详情
     */
    @RequestMapping(value = "/detail/{employeeMasterId}")
    @ResponseBody
    public Object detail(@PathVariable("employeeMasterId") Integer employeeMasterId) {
        return employeeMasterService.selectById(employeeMasterId);
    }

    /**
     * 根据证件类型和证件号获取从业人员信息
     */
    @RequestMapping(method = RequestMethod.POST, path = "/searchEmployee")
    @ResponseBody
    public Object searchEmployee(HttpServletRequest request) {
        String idCardNumber = request.getParameter("idCardNumber");
        String idCardType = request.getParameter("idCardType");
        if (idCardType == null && idCardType == "") {
            idCardType = "0";
        }
        List<Map<String, Object>> employeeMaster = employeeMasterService.searchEmployee(idCardNumber, Integer.valueOf(idCardType));
        return new EmployeeMasterWarpper(employeeMaster).warp();
    }

    /**
     * 导出
     *
     * @param res
     * @param req
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public void downloadFile(HttpServletResponse res, HttpServletRequest req, @RequestParam Map<String, Object> params) {
        employeeMasterService.export(res,req,params);
    }

    /**
     * excel导入
     */
    @RequestMapping(method = RequestMethod.POST, path = "/excelUpload")
    @ResponseBody
    public String excelUpload(@RequestPart("file") MultipartFile excelFile, HttpServletRequest request) throws Exception {
        return employeeMasterService.Import(excelFile,request);
    }

}
