package com.xywg.admin.modular.project.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.config.handler.RemoteDTO;
import com.xywg.admin.config.handler.ZbusHandler;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.base.tips.Tip;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.company.service.ICooperationSubContractorService;
import com.xywg.admin.modular.company.service.ISubContractorService;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.model.vo.ProjectMasterVo;
import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import com.xywg.admin.modular.project.service.IProjectAddressService;
import com.xywg.admin.modular.project.service.IProjectMasterService;
import com.xywg.admin.modular.project.service.IProjectWorkerService;
import com.xywg.admin.modular.project.wrapper.ProjectMasterWrapper;
import com.xywg.admin.modular.system.service.IAreaService;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.system.service.IDictService;
import com.xywg.admin.modular.system.service.IFileInfoService;
import com.xywg.admin.modular.team.service.ITeamMasterService;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目信息控制器
 *
 * @author wangcw
 * @Date 2018-05-23 16:30:36
 */
@Controller
@RequestMapping("/projectMaster")
public class ProjectMasterController extends BaseController {

    private String PREFIX = "/project/projectMaster/";

    @Autowired
    private IProjectMasterService projectMasterService;

    @Autowired
    private IDictService dictService;

    @Autowired
    private ISubContractorService subContractorService;

    @Autowired
    private ICooperationSubContractorService cooperationSubContractorService;

    @Autowired
    private ITeamMasterService teamMasterService;

    @Autowired
    private IAreaService iAreaService;

    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;

    @Autowired
    private IFileInfoService fileInfoService;

    @Autowired
    private IProjectAddressService projectAddressService;
    
    @Autowired
    private IProjectWorkerService projectWorkerService;

    @Autowired
    private IDeptService deptService;
    /**
     * 跳转到项目信息首页
     */
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("projectStatus", dictService.selectByName("状态"));
        model.addAttribute("contractorType", dictService.selectByName("参建类型"));
        model.addAttribute("deviceType", dictService.selectByName("考勤类型"));
        model.addAttribute("projectStatus", dictService.selectByName("项目状态"));
        model.addAttribute("isEnterprise", ShiroKit.getUser().getIsEnterprise());
        return PREFIX + "projectMaster.html";
    }


    /**
     * 跳转到添加项目信息
     */
    @RequestMapping("/projectMaster_add")
    public String projectMasterAdd(Model model) {
        model.addAttribute("subContractor", subContractorService.getList(new HashMap<>()));
        model.addAttribute("projectActivityType", dictService.selectByName("项目活动类型"));
        model.addAttribute("projectCategory", dictService.selectByName("项目分类"));
        model.addAttribute("contractorType", dictService.selectByName("参建类型"));
        model.addAttribute("projectStatus", dictService.selectByName("项目状态"));
        model.addAttribute("deviceType", dictService.selectByName("考勤类型"));
        return PREFIX + "projectMaster_add.html";
    }

    /**
     * 双击查看项目信息
     */
    @RequestMapping("/projectMaster_view/{projectMasterId}")
    public String projectMasterInfo(@PathVariable Long projectMasterId, Model model) {
        model.addAttribute("projectId", projectMasterId);
        model.addAttribute("projectActivityType", dictService.selectByName("项目活动类型"));
        model.addAttribute("projectCategory", dictService.selectByName("项目分类"));
        model.addAttribute("projectStatus", dictService.selectByName("项目状态"));
        model.addAttribute("contractorType", dictService.selectByName("参建类型"));
        model.addAttribute("deviceType", dictService.selectByName("考勤类型"));
        Map<String, Object> projectMaster = projectMasterService.selectById(projectMasterId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectCode", projectMaster.get("projectCode").toString());
        model.addAttribute("team", teamMasterService.getAllList(map));
        Object totalAmtObj = projectMaster.get("totalContractAmt");
        if(totalAmtObj != null ) {
        	String str = totalAmtObj.toString();//金额需要截取保留两位小数
        	if(StringUtils.isNotEmpty(str)) {
        		projectMaster.put("totalContractAmt", str.substring(0, str.length() - 4));
        	}
        }
        model.addAttribute("item", projectMaster);
        model.addAttribute("projectAddressList", JSON.toJSONString(projectAddressService.selectAddressByProjectCode(projectMaster.get("projectCode").toString())));
        LogObjectHolder.me().set(projectMaster);
        return PREFIX + "projectMaster_info.html";
    }


    /**
     * 跳转到修改项目信息
     */
    @RequestMapping("/projectMaster_update/{projectMasterId}")
    public String projectMasterUpdate(@PathVariable Integer projectMasterId, Model model) {
        model.addAttribute("subContractor", subContractorService.getList(new HashMap<>()));
        model.addAttribute("projectActivityType", dictService.selectByName("项目活动类型"));
        model.addAttribute("projectCategory", dictService.selectByName("项目分类"));
        model.addAttribute("projectStatus", dictService.selectByName("项目状态"));
        model.addAttribute("contractorType", dictService.selectByName("参建类型"));
        model.addAttribute("deviceType", dictService.selectByName("考勤类型"));
        Map<String, Object> projectMaster = projectMasterService.selectById(Long.valueOf(projectMasterId));
        model.addAttribute("projectAddressList", JSON.toJSONString(projectAddressService.selectAddressByProjectCode(projectMaster.get("projectCode").toString())));
        model.addAttribute("item", projectMaster);
        String companyName=deptService.selectCompanyName(projectMaster.get("contractorOrgCode").toString());

        model.addAttribute("companyName", companyName);
        LogObjectHolder.me().set(projectMaster);
        return PREFIX + "projectMaster_edit.html";
    }

    /**
     * 切换项目 项目详情页面
     */
    @RequestMapping(value = "/toggleProjectInfo")
    public Object toggleProjectInfo(Model model) {
        model.addAttribute("projectActivityType", dictService.selectByName("项目活动类型"));
        model.addAttribute("projectCategory", dictService.selectByName("项目分类"));
        model.addAttribute("projectStatus", dictService.selectByName("项目状态"));
        model.addAttribute("contractorType", dictService.selectByName("参建类型"));
        model.addAttribute("deviceType", dictService.selectByName("考勤类型"));
        Map<String,Object> projectMaster = projectMasterService.selectProjectByProjectCode(ShiroKit.getSessionAttr("toggleProjectCode").toString());
        model.addAttribute("item", projectMaster);
        LogObjectHolder.me().set(projectMaster);
        return "/project/toggleProjectInfo/toggle_projectMaster_info.html";
    }


    /**
     * 切换项目 项目工人页面
     */
    @RequestMapping("/toggleProjectWorker")
    public String toggleProjectWorker (Model model) {
        model.addAttribute("birthPlaceCode", iAreaService.getBirthPlaceCode());
        model.addAttribute("dictCultureLevelType", dictService.selectByName("文化程度"));
        model.addAttribute("dictGender", dictService.selectByName("性别"));
        model.addAttribute("dictWorkTypeCode", dictService.selectByName("工种字典数据"));
        model.addAttribute("dictAge", dictService.selectByName("年龄区间"));
        model.addAttribute("projectCode",ShiroKit.getSession().getAttribute("toggleProjectCode"));
        Object projectCode=ShiroKit.getSession().getAttribute("toggleProjectCode");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("projectCode",projectCode);
        model.addAttribute("team",teamMasterService.getAllList(map)) ;
        model.addAttribute("projectCode",projectCode);
        return "/project/toggleProjectWorker/toggle_project_worker.html";
    }

    /**
     * 项目切换 跳转到考勤记录首页
     */
    @RequestMapping("/toggleProjectDevice")
    public String toggleProjectDevice (Model model) {
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        model.addAttribute("source", dictService.selectByName("考勤类型"));
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("projectCode",ShiroKit.getSessionAttr("toggleProjectCode"));
        model.addAttribute("team",teamMasterService.getAllList(map)) ;
        return "/project/toggleProjectDevice/toggle_device_record.html";
    }


    /**
     * 跳转到pdf列表
     */
    @RequestMapping("/showPdf")
    public String showPdf (@RequestParam Long id,Model model) {
        model.addAttribute("psId",id);
        return "/project/showPdf/show_pdf.html";
    }

    /**
     * 获取pdf列表
     */
    @RequestMapping("/getPdfList")
    @ResponseBody
    public Object getPdfList (@RequestParam Long id,Model model) {
       //根据id获取文件列表
        return fileInfoService.getByTableNameAndId("buss_project_sub_contractor",id);
    }


    /**
     * 获取项目信息列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map) {

        Page<ProjectMaster> page = new PageFactory<ProjectMaster>().defaultPage();
        map.put("deptId", ShiroKit.getUser().getDeptId());
        List<Map<String, Object>> list = projectMasterService.selectList(map, page);
        page.setRecords((List<ProjectMaster>) new ProjectMasterWrapper(list).warp());
        return super.packForBT(page);
    }



    /**
     * 新增项目信息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProjectMasterVo projectMaster) {
        projectMasterService.insert(projectMaster);
        return projectMaster;
    }

    /**
     * 删除项目信息
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long projectMasterId) {
        if (cooperationSubContractorService.getAllOtherCooperationSubContractor(projectMasterId).size() > 0) {
            throw new XywgException(801, "该项目下有其他参建单位,删除被拒绝!");
        }
        boolean bl = projectMasterService.deleteById(projectMasterId);
        if(bl) {
            //完成调用zbus推送给智慧工地
            RemoteDTO dto = new RemoteDTO();
            Map<String, Object> mm = new HashMap<>();
            mm.put("id", projectMasterId);
            mm.put("operationFlag", "deleteExternalProject");
            dto.setBody(JSONObject.fromObject(mm).toString());
            int state = ZbusHandler.send(JSONObject.fromObject(dto).toString());
            if(state == 200) {
                //如果已经删除就把同步状态改成 0
                projectMasterService.updateSynchro(projectMasterId, 0);
            }
        }
        return SUCCESS_TIP;
    }

    /**
     * 修改项目信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ProjectMasterVo projectMaster) {
        projectMasterService.updateById(projectMaster);

        return SUCCESS_TIP;
    }

    /**
     * 设置工时
     */
    @RequestMapping(value = "/setTime")
    @ResponseBody
    public Object setTime(ProjectMaster projectMaster) {
        projectMasterService.setTime(projectMaster);
        return SUCCESS_TIP;
    }

    @RequestMapping(value = "/submitMac")
    @ResponseBody
    public Object setMac(@RequestParam String projectCode,@RequestParam String macCode) {
      Boolean boo=  projectMasterService.setMac(projectCode,macCode);
        return SUCCESS_TIP;
    }

    /**
     * 获取项目计工单位小时
     */
    @RequestMapping(value = "/getWorkTime/{projectMasterId}")
    @ResponseBody
    public Object getWorkTime(@PathVariable("projectMasterId") Integer projectMasterId) {
        return projectMasterService.getWorkTimeById(projectMasterId);
    }

    /**
     * 跳转设置工时页面
     */
    @RequestMapping("/projectMaster_setTime/{projectMasterId}")
    public String projectMasterSetTime(@PathVariable Integer projectMasterId, Model model) {
        ProjectMaster projectMaster = projectMasterService.getWorkTimeById(projectMasterId);
        model.addAttribute("item", projectMaster);
        LogObjectHolder.me().set(projectMaster);
        return PREFIX + "projectMaster_time.html";
    }

    /**
     * 项目信息详情
     */
    @RequestMapping(value = "/detail/{projectMasterId}")
    @ResponseBody
    public Object detail(@PathVariable("projectMasterId") Integer projectMasterId) {
        return projectMasterService.selectById(projectMasterId);
    }

    /**
     * 根据项目名称模糊匹配项目
     */
    @RequestMapping(value = "/getByProjectName/{projectName}")
    @ResponseBody
    public Object getByProjectName(@PathVariable String projectName) {
        return projectMasterService.getByProjectName(projectName);
    }

    /**
     * 切换项目状态
     */
    @RequestMapping(value = "/toggleProjectStatus")
    @ResponseBody
    public Object toggleProjectStatus(@RequestParam String ids, @RequestParam String projectStatus) {
        return projectMasterService.toggleProjectStatus(ids, projectStatus);
    }

    /**
     * 获取项目信息列表
     */
    @RequestMapping(value = "/getList")
    @ResponseBody
    public Object getList(@RequestParam Map<String, Object> map) {
        return projectMasterService.getList(map);

    }

    /**
     * @param files
     * @param projectCode
     * @param cOrganizationCode
     * @return
     * @description 上传劳动合同
     * @author chupp
     * @date 2018年6月7日
     */
    @RequestMapping(method = RequestMethod.POST, path = "/uploadLaborContract")
    @ResponseBody
    public Tip uploadLaborContract(@RequestParam("file") MultipartFile[] files, @RequestParam String projectCode, @RequestParam String cOrganizationCode) {
        return projectMasterService.uploadLaborContract(files, projectCode, cOrganizationCode);
    }
    
    

    /**
     * 跳转设置工时页面
     */
    @RequestMapping("/projectMaster_card/{pwId}")
    public String projectMasterSetCard(@PathVariable Long pwId, Model model) {
        model.addAttribute("cardNumber", projectWorkerService.getCardNumberByPwId(pwId));
        model.addAttribute("pwId", pwId);
        return PREFIX + "projectMaster_card.html";
    }
}


