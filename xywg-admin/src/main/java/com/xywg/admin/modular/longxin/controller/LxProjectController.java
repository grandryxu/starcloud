package com.xywg.admin.modular.longxin.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.flow.entity.Process;
import com.xywg.admin.flow.service.ProcessService;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.company.service.ISubContractorService;
import com.xywg.admin.modular.longxin.model.LxTenderProcessRelation;
import com.xywg.admin.modular.longxin.service.LxProjectService;
import com.xywg.admin.modular.longxin.service.LxTenderProcessRelationService;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.model.vo.ProjectMasterVo;
import com.xywg.admin.modular.project.service.IProjectAddressService;
import com.xywg.admin.modular.project.service.IProjectMasterService;
import com.xywg.admin.modular.project.wrapper.ProjectMasterWrapper;
import com.xywg.admin.modular.system.service.IDictService;
import com.xywg.admin.modular.team.service.ITeamMasterService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by tcw on 2019/7/9.
 */
@Controller
@RequestMapping("/lxProject")
public class LxProjectController  extends BaseController {

    private String PREFIX = "/longxin/projectMaster/";

    @Autowired
    private ITeamMasterService teamMasterService;

    @Autowired
    private IDictService dictService;

    @Autowired
    private LxProjectService lxProjectService;

    @Autowired
    private ISubContractorService subContractorService;



    @Autowired
    private IProjectMasterService projectMasterService;


    @Autowired
    private IProjectAddressService projectAddressService;




    @Autowired
    private ProcessService processService;
    
    @Autowired
    private LxTenderProcessRelationService lxTenderProcessRelationService;
    

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
     * 获取项目信息列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map) {

        System.out.println(map.toString());
        Page<ProjectMaster> page = new PageFactory<ProjectMaster>().defaultPage();
        map.put("deptId", ShiroKit.getUser().getDeptId());
        List<Map<String, Object>> list = lxProjectService.selectList(map, page);
        page.setRecords((List<ProjectMaster>) new ProjectMasterWrapper(list).warp());
        return super.packForBT(page);
    }


    /**
     * 删除项目信息
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long projectMasterId) {
       Long num= lxProjectService.selectNumber(projectMasterId);

       if (num==0){
           lxProjectService.deleteByIds(projectMasterId);
           return SUCCESS_TIP;
       }else {

           return ERROR;
       }

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
     * 跳转到添加项目流程
     */
    @RequestMapping("/projectMaster_flow/{projectMasterId}")
    public String projectMasterAddFlow(@PathVariable Integer projectMasterId,Model model) {	
    	ProjectMaster pm = lxProjectService.selectById2(projectMasterId + "");
    	LxTenderProcessRelation paramL = new LxTenderProcessRelation();
    	paramL.setBussId(projectMasterId + "");
    	paramL.setType(1);
    	//查询 招标文件流程id
        String flowProcessId = lxTenderProcessRelationService.getFlowRelation(paramL);
        //查询 定标流程id
    	String fixProcessId = lxTenderProcessRelationService.getFixRelation(paramL);
        model.addAttribute("project",pm);
        if(StringUtils.isEmpty(flowProcessId)) {
            model.addAttribute("addFlag",true);
        }else {
            model.addAttribute("addFlag",false);
        }
        //获取所有流程
        List<Process> processList = processService.getAll();
        String html = "";
        if(StringUtils.isEmpty(flowProcessId)){
            if(processList != null) {
                //循环流程
                for (Process process : processList) {
                    if(process != null) {
                        if(!StringUtils.isEmpty(flowProcessId)) {

                            //找到招标文件的流程
                            if(flowProcessId.equals(process.getId())) {
                                //设置为选择
                                html = html + "<option selected='selected' value= '"+process.getId()+"'>"+process.getDisplayName()+ "</option>";
                            }else {
                                html = html + "<option value= '"+process.getId()+"'>"+process.getDisplayName()+ "</option>";
                            }
                            //如果没绑定流程，就把这条流程选择放进去
                        }else {
                            html = html + "<option value= '"+process.getId()+"'>"+process.getDisplayName()+ "</option>";
                        }
                    }
                }
            }
            model.addAttribute("process",html);
            String fixHtml = "";
            for (Process process : processList) {
                if(process != null) {
                    if(!StringUtils.isEmpty(fixProcessId)) {
                        if(fixProcessId.equals(process.getId())) {

                            fixHtml = fixHtml + "<option selected='selected' value= '"+process.getId()+"'>"+process.getDisplayName()+ "</option>";
                        }else {
                            fixHtml = fixHtml + "<option value= '"+process.getId()+"'>"+process.getDisplayName()+ "</option>";
                        }
                    }else {
                        fixHtml = fixHtml + "<option value= '"+process.getId()+"'>"+process.getDisplayName()+ "</option>";
                    }}

            }
            model.addAttribute("fixProcess",fixHtml);
            //存在則不允許添加

            //绑定好了流程
        }else{
            String bindedHtml = "";
            String bindedHtmlFx = "";
            Process process    = new Process();
            Process processfx  = new Process();
            process.setId(flowProcessId);
            process = processService.getOne(process,null,null,null);
            processfx.setId(fixProcessId);
            processfx = processService.getOne(processfx,null,null,null);
            //自定义流程
            if(process.getType()==1){
                model.addAttribute("flowMode","自定义流程");
            }else{//默认流程
                model.addAttribute("flowMode","默认流程");
            }

            //自定义流程
            if(processfx.getType()==1 ){
                model.addAttribute("flowModeFix","自定义流程");
            }else{//默认流程
                model.addAttribute("flowModeFix","默认流程");
            }
            bindedHtmlFx = bindedHtmlFx+   "<option  selected='selected' value= '"+processfx.getId()+"'>"+processfx.getDisplayName()+ "</option>";
            model.addAttribute("fixProcess",bindedHtmlFx);
            bindedHtml = bindedHtml+       "<option  selected='selected' value= '"+process.getId()+"'>"+process.getDisplayName()+ "</option>";
            model.addAttribute("process",bindedHtml);

        }

        return PREFIX + "projectMaster_flow.html";
    }




    @RequestMapping("/projectMaster_mac/{projectMasterId}")
    public String projectMasterForMac(@PathVariable Integer projectMasterId,Model model) {
      Map<String,Object> projectMaster=  lxProjectService.selectProjectCodeByProjectId(projectMasterId);
        String projectCode = projectMaster.get("projectCode").toString();
        model.addAttribute("projectCode",projectCode);
        return PREFIX + "projectMaster_mac.html";
    }


        /**
         * 项目流程绑定
         */
    @RequestMapping("/flow/add")
    @ResponseBody
    public Object bindFlow(String projectId, String flag,String processId, String ids,String fixFlag,String fixProcessId,String fixIds)   {
       return lxProjectService.flowAdd( projectId,  flag, processId,  ids, fixFlag, fixProcessId, fixIds);
    }

    /*修改状态*/



    @RequestMapping("/updateStatus")
    public void updateStatus(@RequestParam Long projectMasterId){
        lxProjectService.updateStatus(projectMasterId);
    }

    /**
     * 跳转发布招标信息
     *@Author tcw
     *@Date 11:05  2019/7/10
     */
    @RequestMapping("tendering")
    public String tendering(Model model,String id){
        ProjectMaster pm = lxProjectService.selectById2(id);
        model.addAttribute("project",pm);
        /*UUID uuid = UUID.randomUUID();*/
        Date date = new Date();
        String uuid = String.valueOf(date.getTime());
        DateFormat bf = new SimpleDateFormat("yyyyMMdd");
        String format = bf.format(date);
        model.addAttribute("uuid","ZBXX"+format+uuid);
//        List<Process> processList = processService.getAll();
//        String html = "";
//        for (Process process : processList) {
//                html = html + "<option value= '"+process.getId()+"'>"+process.getDisplayName()+ "</option>";
//        }
//        model.addAttribute("process",html);
        return PREFIX + "tendering.html";
    }

    @RequestMapping("/tender/uploadFtp")
    @ResponseBody
    public Object uploadFtp(MultipartFile[] files){
        return lxProjectService.uploadFtp(files);
    }

    @RequestMapping("/tender/upload")
    @ResponseBody
    public Object uploadTender(@RequestParam("file")MultipartFile[] file,String id,String fileName,String resume,
                                            String proId,String priceTemp,String deadline
                                                ,String startTime,String flag,String ids,String tenderCode,String tenderType)   {

       return lxProjectService.uploadTender(file ,id ,fileName,resume,proId,priceTemp,deadline,startTime,flag,ids,tenderCode, tenderType);
    }


    /**
     * 跳转到修改项目信息
     */
    @RequestMapping("/projectMaster_update/{projectMasterId}")
    public String projectMasterUpdate(@PathVariable Integer projectMasterId, Model model) {
        model.addAttribute("projectActivityType", dictService.selectByName("项目活动类型"));
        model.addAttribute("projectCategory", dictService.selectByName("项目分类"));
        model.addAttribute("projectStatus", dictService.selectByName("项目状态"));
        model.addAttribute("contractorType", dictService.selectByName("参建类型"));
        model.addAttribute("deviceType", dictService.selectByName("考勤类型"));
        Map<String, Object> projectMaster =  lxProjectService.selectById(Long.valueOf(projectMasterId));
        model.addAttribute("projectAddressList", JSON.toJSONString(lxProjectService.selectAddressByProjectCode(projectMaster.get("projectCode").toString())));
        model.addAttribute("item", projectMaster);
        LogObjectHolder.me().set(projectMaster);
        return PREFIX + "projectMaster_edit.html";
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
        Map<String, Object> projectMaster = lxProjectService.selectById(projectMasterId);
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
     */
    @RequestMapping("/subContractor_add_search/{id}")
    public String subContractorAddSearch(Model model ,@PathVariable String id)  {
         model.addAttribute("id", id);
        return PREFIX + "subContractor_add_search.html";
    }


    /**
     * 获取所有企业 除自己以及自己的参建公司
     */
    @RequestMapping(value = "/getAllSubContractor")
    @ResponseBody
    public Object getAllSubContractor(@RequestParam Map<String,Object> map) {
        return subContractorService.getAllSubContractor(map);
    }



    @RequestMapping("/companyList/{zbId}")
    @ResponseBody
    public Object selectCompanyList(@RequestParam Map<String,Object> map, @PathVariable String zbId  ) {
        Page<SubContractor> page = new PageFactory<SubContractor>().defaultPage();
        List<SubContractor> list = this.lxProjectService.selectCompanyList(map, page,zbId);
        page.setRecords(list);
        return super.packForBT(page);
    }


    /*邀标所选企业*/
    @RequestMapping("/insertInvite")
    @ResponseBody
    public void insertInvite(@RequestParam(value = "zbId") String zbId  ,@RequestParam(value = "companyIdList") String companyIdList ) {

        String[] bStrings = companyIdList.split(",");
        List<String> strings = Arrays.asList(bStrings);
        lxProjectService.insertInvite(zbId,strings);

    }

    /*邀标所有企业*/
    @RequestMapping("/insertInviteAll")
    @ResponseBody
    public void insertInviteAll(@RequestParam(value = "zbId") String zbId){
        List<String> companyIds=this.lxProjectService.selectCompanyListId(zbId);
        lxProjectService.insertInvite(zbId,companyIds);
    }

    @RequestMapping("/yb/{id}")
    public String yb(Model model ,@PathVariable String id)  {
        model.addAttribute("tenderId", id);
        return PREFIX + "yb.html";
    }



    /*已邀标企业*/
    @RequestMapping("/companyListAndYb/{zbId}")
    @ResponseBody
    public Object selectCompanyListAndYb(@RequestParam Map<String,Object> map, @PathVariable String zbId  ) {
        Page<SubContractor> page = new PageFactory<SubContractor>().defaultPage();
        List<SubContractor> list = this.lxProjectService.selectCompanyListAndYb(map, page,zbId);
        page.setRecords(list);
        return super.packForBT(page);
    }


    /**
     */
    @RequestMapping("/selectCompanyAndYb/{zbId}")
    public String selectCompanyAndYb(Model model ,@PathVariable(value = "zbId") String zbId)  {
        model.addAttribute("id", zbId);
        return PREFIX + "selectCompanyAndYb.html";
    }



    /*定标*/
    @RequestMapping("/companyDbList/{projectId}")
    @ResponseBody
    public Object companyDbList(@RequestParam Map<String,Object> map,@PathVariable(value = "projectId") String projectId){
        Page<SubContractor> page = new PageFactory<SubContractor>().defaultPage();
        List<SubContractor> list = this.lxProjectService.companyDbList(map, page,projectId);
        page.setRecords(list);
        return super.packForBT(page);
    }


    /**
     * 新增项目信息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProjectMasterVo projectMaster) {

       Long num= lxProjectService.selectProjectManCode(projectMaster.getProjectManCode());
       if (num==0) {
           projectMasterService.insert(projectMaster);
           return SUCCESS_TIP;
       }else {
           return ERROR;
       }
    }
    @RequestMapping(value = "/selectProjectManCode")
    @ResponseBody
    public Object selectProjectManCode( @RequestParam(value = "projectManCode") String projectManCode  ) {
        return lxProjectService.selectProjectManCode(projectManCode);
    }


    @RequestMapping(value = "/getIdByProjectCode")
    @ResponseBody
    public Object getIdByProjectCode( @RequestParam Map<String,Object> map  ) {
        return lxProjectService.getIdByProjectCode(map.get("projectCode"));
    }

}
