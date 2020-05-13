package com.xywg.admin.modular.company.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.modular.company.model.PersonalCertifications;
import com.xywg.admin.modular.company.model.PersonalCertificationsVo;
import com.xywg.admin.modular.company.service.IEmployeeMasterService;
import com.xywg.admin.modular.company.service.IPersonalCertificationsService;
import com.xywg.admin.modular.company.wrapper.PersonalCertificationWarpper;
import com.xywg.admin.modular.system.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 人员资格证书控制器
 *
 * @author wangcw
 * @Date 2018-05-29 16:12:40
 */
@Controller
@RequestMapping("/personalCertifications")
public class PersonalCertificationsController extends BaseController {
    @Autowired
    private IDictService dictService;
    private String PREFIX = "/company/personalCertifications/";

    @Autowired
    private IPersonalCertificationsService personalCertificationsService;
    @Autowired
    private IEmployeeMasterService employeeMasterService;

    /**
     * 跳转到人员资格证书首页
     */
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("dictCertificationTypeCode", dictService.selectByName("证书类型"));
        return PREFIX + "personalCertifications.html";
    }

    /**
     * 跳转到添加人员资格证书
     */
    @RequestMapping("/personalCertifications_add")
    public String personalCertificationsAdd(Model model) {
        model.addAttribute("dictIdCardType", dictService.selectByName("人员证件类型"));
        model.addAttribute("dictCertificationTypeCode", dictService.selectByName("证书类型"));
        model.addAttribute("employees", employeeMasterService.getEmployees());

        return PREFIX + "personalCertifications_add.html";
    }

    /**
     * 跳转到从业人员添加人员资格证书
     */
    @RequestMapping("/personalCertifications_addEm/{idCardNumber}/{idCardTypeValue}")
    public String personalCertificationsAddEm(@PathVariable Integer idCardTypeValue,@PathVariable String idCardNumber,Model model) {
        model.addAttribute("item",employeeMasterService.getEmployeeByIdCard(idCardNumber,idCardTypeValue));
        model.addAttribute("dictIdCardType", dictService.selectByName("人员证件类型"));
        model.addAttribute("dictCertificationLevelType", dictService.selectByName("证书等级"));
        model.addAttribute("dictCertificationTypeCode", dictService.selectByName("证书类型"));
        return PREFIX + "personalCertifications_add.html";
    }

    /**
     * 跳转到修改人员资格证书
     */
    @RequestMapping("/personalCertifications_update/{personalCertificationsId}")
    public String personalCertificationsUpdate(@PathVariable Integer personalCertificationsId, Model model) {
        PersonalCertificationsVo personalCertifications = personalCertificationsService.getById(personalCertificationsId);
        model.addAttribute("dictIdCardType", dictService.selectByName("人员证件类型"));
        model.addAttribute("dictCertificationTypeCode", dictService.selectByName("证书类型"));
        model.addAttribute("dictCertificationLevelType", dictService.selectByName("证书等级"));
        model.addAttribute("employees", employeeMasterService.getEmployees());
        model.addAttribute("item", personalCertifications);
        LogObjectHolder.me().set(personalCertifications);
        return PREFIX + "personalCertifications_edit.html";
    }

    /**
     * 查看详情
     */
    @RequestMapping("/personalCertifications_view/{personalCertificationsId}")
    public String workerMasterView(@PathVariable Integer personalCertificationsId, Model model) {
        PersonalCertificationsVo personalCertifications = personalCertificationsService.getById(personalCertificationsId);
        model.addAttribute("dictIdCardType", dictService.selectByName("人员证件类型"));
        model.addAttribute("dictCertificationTypeCode", dictService.selectByName("证书类型"));
        model.addAttribute("dictCertificationLevelType", dictService.selectByName("证书等级"));
        model.addAttribute("employees", employeeMasterService.getEmployees());
        model.addAttribute("item", personalCertifications);
        return PREFIX + "personalCertifications_info.html";
    }

    /**
     * 查看详情(工人管理tab页)
     */
    @RequestMapping("/getPersonalCertificationsInfo")
    public String getPersonalCertificationsInfo( Model model,@RequestParam Map<String,Object> map) {
        List<PersonalCertificationsVo> personalCertifications = personalCertificationsService.getByIdCard(map.get("idCardNumber").toString(),Integer.parseInt(String.valueOf(map.get("idCardType"))));
        model.addAttribute("idCardTypeName", dictService.selectByName("人员证件类型"));
        model.addAttribute("certificationTypeCodeName", dictService.selectByName("证书类型"));
        model.addAttribute("employees", employeeMasterService.getEmployees());
        model.addAttribute("item", personalCertifications);
        return PREFIX + "personalCertifications_info.html";
    }

    /**
     * 获取人员资格证书列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map) {

        Page<PersonalCertificationsVo> page = new PageFactory<PersonalCertificationsVo>().defaultPage();
        List<Map<String, Object>> personalCertifications = personalCertificationsService.selectPersonalCertifications(page, map);
        page.setRecords((List<PersonalCertificationsVo>) new PersonalCertificationWarpper(personalCertifications).warp());
        return super.packForBT(page);
    }

    /**
     * 获取某人员资格证书列表(从业人员管理tab页)
     */
    @RequestMapping(value = "/getListByIdCard")
    @ResponseBody
    public Object getListByIdCard(@RequestParam Map<String, Object> map) {

        Page<PersonalCertificationsVo> page = new PageFactory<PersonalCertificationsVo>().defaultPage();
        List<Map<String, Object>> personalCertifications = personalCertificationsService.getListByIdCard(page, map);
        page.setRecords((List<PersonalCertificationsVo>) new PersonalCertificationWarpper(personalCertifications).warp());
        return super.packForBT(page);
    }

    /**
     * 新增人员资格证书
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PersonalCertifications personalCertifications) {
        personalCertifications.setType(2);//从业人员type为2
        personalCertificationsService.insert(personalCertifications);
        return SUCCESS_TIP;
    }

    /**
     * 删除人员资格证书
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Map<String, Object> map) {
        personalCertificationsService.deleteByIds(map);
        return SUCCESS_TIP;
    }

    /**
     * 修改人员资格证书
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PersonalCertifications personalCertifications) {
        personalCertificationsService.updateById(personalCertifications);
        return SUCCESS_TIP;
    }

    /**
     * 人员资格证书详情
     */
    @RequestMapping(value = "/detail/{personalCertificationsId}")
    @ResponseBody
    public Object detail(@PathVariable("personalCertificationsId") Integer personalCertificationsId) {
        return personalCertificationsService.selectById(personalCertificationsId);
    }

    /**
     * 获取职称下拉选择
     */
    @RequestMapping(value = "/getJobType")
    @ResponseBody
    public Object getJobType(Integer id) {
        if (id == 14001) {
            return dictService.selectByName("安管证书专业");
        } else if (id == 14002) {
            return dictService.selectByName("岗位证书专业");
        } else if (id == 14003) {
            return dictService.selectByName("特种作业证书");
        } else if (id == 14004) {
            return dictService.selectByName("职称证书专业");
        } else if (id == 14005) {
            return dictService.selectByName("执业注册证书专业");
        } else if (id == 14006) {
            return dictService.selectByName("技术工人资格证书");
        } else {
            return dictService.selectByName("职业技能证书");
        }
    }
    /**
     * 获取工人资格证书
     */
    @RequestMapping(value = "/getWorkerCertifications")
    @ResponseBody
    public Object getWorkerCertifications(@RequestParam Map<String, Object> map) {
        return  personalCertificationsService.getWorkerCertifications(map);


    }

}
