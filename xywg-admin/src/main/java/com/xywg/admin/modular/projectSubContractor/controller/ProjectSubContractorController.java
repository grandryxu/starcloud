package com.xywg.admin.modular.projectSubContractor.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.company.service.ICooperationSubContractorService;
import com.xywg.admin.modular.company.service.ISubContractorService;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.model.ProjectWorker;
import com.xywg.admin.modular.project.service.IProjectMasterService;
import com.xywg.admin.modular.project.service.IProjectWorkerService;
import com.xywg.admin.modular.projectSubContractor.model.ProjectSubContractor;
import com.xywg.admin.modular.projectSubContractor.projectSubContractorWrapper.ProjectSubContractorWrapper;
import com.xywg.admin.modular.projectSubContractor.service.IProjectSubContractorService;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.system.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 项目参建单位控制器
 *
 * @author wangcw
 * @Date 2018-05-23 16:31:15
 */
@Controller
@RequestMapping("/projectSubContractor")
public class ProjectSubContractorController extends BaseController {

    private String PREFIX = "/project/projectSubContractor/";

    @Autowired
    private IProjectSubContractorService projectSubContractorService;
    @Autowired
    private IDictService dictService;
    @Autowired
    private ICooperationSubContractorService cooperationSubContractorService;
    @Autowired
    private IProjectWorkerService projectWorkerService;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IProjectMasterService projectMasterService;
    @Autowired
    private ISubContractorService subContractorService;

    /**
     * 跳转到项目参建单位首页
     */
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("projectStatus",dictService.selectByName("项目状态"));
        model.addAttribute("contractorType",dictService.selectByName("参建类型"));
        return PREFIX + "projectSubContractor.html";
    }


    /**
     * 项目切换 跳转到项目参建单位首页
     */
    @RequestMapping("toggleProjectSubContractorInfo")
    public String toggleProjectSubContractorInfo(Model model) {
        model.addAttribute("projectStatus",dictService.selectByName("项目状态"));
        model.addAttribute("contractorType",dictService.selectByName("参建类型"));
        //获取项目详情
        Map<String,Object> projectMaster = projectMasterService.selectProjectByProjectCode(ShiroKit.getSessionAttr("toggleProjectCode"));
        //查询用户所在公司的社会信用代码
        String socialCreditNumber =  deptService.selectById(ShiroKit.getUser().getDeptId()).getSocialCreditNumber();
        if(socialCreditNumber.equals(projectMaster.get("contractorOrgCode").toString())){
            model.addAttribute("showOperations","true");
        }
        return "/project/toggleProjectSubContractorInfo/toggle_projectSubContractor.html";
    }


    /**
     * 跳转到设置项目经理界面
     */
    @RequestMapping("projectMaster_setPm/{id}")
    public String projectMasterSetPm(@PathVariable String id, Model model) {
        model.addAttribute("id", id);
        return PREFIX + "projectMaster_setPm.html";
    }


    /**
     * 跳转到添加项目参建单位
     */
    @RequestMapping("/projectSubContractor_add")
    public String projectSubContractorAdd(@RequestParam Map map, Model model) {
        model.addAttribute("subContractor",cooperationSubContractorService.getList(map.get("projectCode").toString()));
        model.addAttribute("idcardType",dictService.selectByName("人员证件类型"));
        model.addAttribute("contractorType",dictService.selectByName("参建类型"));
        model.addAttribute("projectCode",map.get("projectCode"));
        model.addAttribute("projectName",map.get("projectName"));
        //项目切换获取ProejctCode projectName
        Object toggleProjectCode = ShiroKit.getSession().getAttribute("toggleProjectCode");
        if( toggleProjectCode!=null && !"".equals(toggleProjectCode.toString())  ){
            model.addAttribute("projectCode",toggleProjectCode.toString());
            model.addAttribute("projectName",projectMasterService.selectProjectByProjectCode(toggleProjectCode.toString()).get("projectName"));
        }
        //专业承包和非专业承包
        model.addAttribute("pageType",map.get("pageType"));
        //总包
        model.addAttribute("page",map.get("page"));
        return PREFIX + "projectSubContractor_add.html";
    }
    /**
     * 跳转到添加项目参建单位
     */
    @RequestMapping("/projectSubContractor_add1")
    public String projectSubContractorAdd1(@RequestParam Map map, Model model) {
        model.addAttribute("subContractor",cooperationSubContractorService.getList(map.get("projectCode").toString()));
        model.addAttribute("idcardType",dictService.selectByName("人员证件类型"));
        model.addAttribute("contractorType",dictService.selectByName("参建类型"));
        model.addAttribute("projectCode",map.get("projectCode"));
        model.addAttribute("projectName",map.get("projectName"));

        SubContractor subContractor= subContractorService.getcompanyNameAndOrgCodeById( map.get("companyId"));
        model.addAttribute("companyName",subContractor.getCompanyName());
        model.addAttribute("orgCode",subContractor.getOrganizationCode());
        //项目切换获取ProejctCode projectName
        Object toggleProjectCode = ShiroKit.getSession().getAttribute("toggleProjectCode");
        if( toggleProjectCode!=null && !"".equals(toggleProjectCode.toString())  ){
            model.addAttribute("projectCode",toggleProjectCode.toString());
            model.addAttribute("projectName",projectMasterService.selectProjectByProjectCode(toggleProjectCode.toString()).get("projectName"));
        }
        //专业承包和非专业承包
        model.addAttribute("pageType",map.get("pageType"));
        //总包
        model.addAttribute("page",map.get("page"));
        return PREFIX + "projectSubContractor_add1.html";
    }
    /**
     * 跳转到双击查看页面
     */
    @RequestMapping("/projectSubContractor_view/{id}")
        public String projectSubContractorView(Model model,@PathVariable Integer id){
            Map<String,Object> map =projectSubContractorService.getCompanyById(id);
            model.addAttribute("item",map);
            model.addAttribute("organizationType", dictService.selectByName("单位性质"));
            model.addAttribute("businessStatus", dictService.selectByName("经营状态"));
            model.addAttribute("representativeIdcardType",dictService.selectByName("人员证件类型"));
            LogObjectHolder.me().set(map);
        return PREFIX + "projectSubContractor_view.html";
    }

    /**
     * 跳转到评价页面
     */
    @RequestMapping("/projectSubContractor_evaluation/{projectSubContractorId}")
    public String projectSubContractorEvaluation(@PathVariable Integer projectSubContractorId, Model model){
        Map<String,Object> projectSubContractor = projectSubContractorService.getById(projectSubContractorId);
       if (projectSubContractor.get("score")==null){
           projectSubContractor.put("score","暂未评价分数");
       }else {
           projectSubContractor.put("score",projectSubContractor.get("score")+"分");
       }
        model.addAttribute("item",projectSubContractor);
        LogObjectHolder.me().set(projectSubContractor);
        return PREFIX+"projectSubContractor_evaluation.html";
    }
    /**
     * 新增项目参建单位
     */
    @RequestMapping(value = "/evaluation")
    @ResponseBody
    public Object evaluation(ProjectSubContractor projectSubContractor) {
        projectSubContractorService.insert(projectSubContractor);
        return SUCCESS_TIP;
    }

    /**
     * 切换进退场状态
     */
    @RequestMapping(value = "/toggleJoinStatus")
    @ResponseBody
    public Object toggleJoinStatus(@RequestParam String projectCode ,@RequestParam  Integer status , @RequestParam String organizationCodes) {
        projectSubContractorService.toggleJoinStatus(projectCode ,status , organizationCodes );
        return SUCCESS_TIP;
    }

    /**
     * 切换进退场状态 根据ids
     */
    @RequestMapping(value = "/toggleJoinStatusWithIds")
    @ResponseBody
    public Object toggleJoinStatusWithIds(@RequestParam String ids , @RequestParam  Integer status) {
        projectSubContractorService.toggleJoinStatusWithIds(ids,status);
        return SUCCESS_TIP;
    }

    /**
     * 跳转到修改项目参建单位
     */
    @RequestMapping("/projectSubContractor_update/{projectSubContractorId}")
    public String projectSubContractorUpdate(@PathVariable Integer projectSubContractorId, Model model) {
        Map<String,Object> projectSubContractor = projectSubContractorService.getById(projectSubContractorId);
        model.addAttribute("item",projectSubContractor);
        model.addAttribute("pmIdcardType", dictService.selectByName("人员证件类型"));
        model.addAttribute("contractorType",dictService.selectByName("参建类型"));
        LogObjectHolder.me().set(projectSubContractor);
        return PREFIX + "projectSubContractor_edit.html";
    }

    /**
     * 获取项目参建单位列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String,Object> map) {
        Page<ProjectSubContractor> page=new PageFactory<ProjectSubContractor>().defaultPage();
        List<Map<String,Object>> list=projectSubContractorService.getList(map,page);
        page.setRecords((List<ProjectSubContractor>) new ProjectSubContractorWrapper(list).warp());
        return super.packForBT(page);
    }
    /**
     *  项目切换 获取项目参建单位列表
     */
    @RequestMapping(value = "/toggleList")
    @ResponseBody
    public Object toggleList(@RequestParam Map<String,Object> map) {
        Page<ProjectSubContractor> page=new PageFactory<ProjectSubContractor>().defaultPage();
        map.put("projectCode",ShiroKit.getSessionAttr("toggleProjectCode"));
        List<Map<String,Object>> list=projectSubContractorService.getList(map,page);
        page.setRecords((List<ProjectSubContractor>) new ProjectSubContractorWrapper(list).warp());
        return super.packForBT(page);
    }



    /**
     * 设置项目经理
     */
    @RequestMapping(value = "/setPm")
    @ResponseBody
    public Object setPm(@RequestParam Map<String, Object> map) {
        return projectSubContractorService.setPm(map);
    }

    /**
     * 新增项目参建单位
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProjectSubContractor projectSubContractor) {
        projectSubContractor.setCreateOrganizationCode(deptService.selectById(ShiroKit.getUser().getDeptId()).getSocialCreditNumber());
        projectSubContractorService.insert(projectSubContractor);
        return SUCCESS_TIP;
    }

    /**
     * 删除项目参建单位
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long projectSubContractorId) {
        //查询该关系下是否存在班组 若有删除拒绝
        List<ProjectWorker> projectWorkerList = projectWorkerService.getProjectWorkersByProjectSubContractorId(projectSubContractorId);
        if(projectWorkerList.size() > 0){
            throw new XywgException(800,"该参建公司下存在班组，删除被拒绝!");
        }
        projectSubContractorService.deleteById(projectSubContractorId);
        return SUCCESS_TIP;
    }

    /**
     * 删除项目参建单位
     */
    @RequestMapping(value = "/deleteByProjectCodeAndOrganizationCodes")
    @ResponseBody
    public Object deleteByProjectCodeAndOrganizationCodes(@RequestParam String projectCode , @RequestParam String organizationCodes) {
        projectSubContractorService.deleteByProjectCodeAndOrganizationCodes(projectCode ,organizationCodes );
        return SUCCESS_TIP;
    }



    /**
     * 修改项目参建单位
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ProjectSubContractor projectSubContractor) {
        projectSubContractorService.updateById(projectSubContractor);
        return SUCCESS_TIP;
    }

    /**
     * 项目参建单位详情
     */
    @RequestMapping(value = "/detail/{projectSubContractorId}")
    @ResponseBody
    public Object detail(@PathVariable("projectSubContractorId") Integer projectSubContractorId) {
        return projectSubContractorService.selectById(projectSubContractorId);
    }

    /**
     * 切换状态
     */
    @RequestMapping(value = "/changeState")
    @ResponseBody
    public Object changeState(@RequestParam Map<String,Object> map){
        projectSubContractorService.changeState(map);
        return SUCCESS_TIP;
    }


    @RequestMapping(value = "/selectProjectCodeAndOrgCode")
    @ResponseBody
    public Object selectProjectCodeAndOrgCode(@RequestParam Map<String,Object> map){
        Object companyId =  map.get("companyId");
        String companyOrgcode=subContractorService.getCompanyOrgCodeById(companyId);
         Long num=projectSubContractorService.selectProjectCodeAndOrjCode(map.get("projectCode"),companyOrgcode);
        return num;
    }
}
