package com.xywg.admin.modular.project.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.common.exception.BizExceptionEnum;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.modular.project.model.ProjectTraining;
import com.xywg.admin.modular.project.model.ProjectTrainingVo;
import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import com.xywg.admin.modular.project.service.IProjectTrainingService;
import com.xywg.admin.modular.project.wrapper.ProjectDetailWarpper;
import com.xywg.admin.modular.project.wrapper.ProjectTrainingWrapper;
import com.xywg.admin.modular.project.wrapper.WorkerTrainRecordWarpper;
import com.xywg.admin.modular.system.service.IDictService;
import com.xywg.admin.modular.system.service.UploadService;
import com.xywg.admin.modular.wages.service.SettlementService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 培训记录控制器
 *
 * @author wangcw
 * @Date 2018-06-14 10:23:03
 */
@Controller
@RequestMapping("/projectTraining")
public class ProjectTrainingController extends BaseController {


    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;

    @Autowired
    private IDictService dictService;

    private String PREFIX = "/project/projectTraining/";

    @Autowired
    private IProjectTrainingService projectTrainingService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private SettlementService settlementService;

    /**
     * 跳转到培训记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "projectTraining.html";
    }

    /**
     * 跳转到添加培训记录
     */
    @RequestMapping("/projectTraining_add")
    public String projectTrainingAdd(Model model) {
        model.addAttribute("trainingTypeCode",dictService.selectByName("项目培训类型"));
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        return PREFIX + "projectTraining_add.html";
    }

    /**
     * 跳转到培训记录选择工人页面
     */
    @RequestMapping("/projectTraining_workerList")
    public String projectTraining_workerList(String projectCode,Model model) {
        model.addAttribute("projectCode", projectCode);
        return PREFIX + "projectTraining_workerList.html";
    }

    /**
     * 跳转到修改培训记录
     */
    @RequestMapping("/projectTraining_update/{projectTrainingId}")
    public String projectTrainingUpdate(@PathVariable Integer projectTrainingId, Model model) {
        ProjectTraining projectTraining = projectTrainingService.selectById(projectTrainingId);
        model.addAttribute("item",projectTraining);
        LogObjectHolder.me().set(projectTraining);
        return PREFIX + "projectTraining_edit.html";
    }

    /**
     * 跳转到双击查看
     */
    @RequestMapping(value = "/projectTraining_view/{projectTrainingId}")
    public String projectTrainingView(@PathVariable Integer projectTrainingId, Model model){
        ProjectTrainingVo projectTraining = projectTrainingService.getOneById(projectTrainingId);
        model.addAttribute("trainingTypeCode",dictService.selectByName("项目培训类型"));
        model.addAttribute("item",projectTraining);
        return PREFIX + "projectTraining_view.html";
    }

    /**
     * 跳转到培训页面
     */
    @RequestMapping("/projectTraining_train")
    public String projectTrainingUpdate(@RequestParam Map<String,Object> map, Model model) {
        model.addAttribute("trainingTypeCode",dictService.selectByName("项目培训类型"));
        model.addAttribute("projectCode",map.get("projectCode"));
        return PREFIX + "projectTraining_train.html";
    }


    /**
     * 跳转到培训附件页面
     */
    @RequestMapping("/projectTraining_file/{projectTrainingId}")
    public String projectTrainingfileFile(@PathVariable Long projectTrainingId, Model model) {
        model.addAttribute("id",projectTrainingId);
        return PREFIX + "projectTraining_file.html";
    }


    /**
     * 获取培训记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String,Object> map) {
        Page<ProjectTraining> page = new PageFactory<ProjectTraining>().defaultPage();
        List<Map<String,Object>> list=projectTrainingService.list(page,map);
        page.setRecords((List<ProjectTraining>) new ProjectTrainingWrapper(list).warp());
        return super.packForBT(page);
    }

    /**
     * 新增培训记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProjectTrainingVo projectTrainingVo) {
        projectTrainingService.insertProjectTrain(projectTrainingVo);
        return SUCCESS_TIP;
    }

    /**
     * 删除培训记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Map<String,Object> map) {
        projectTrainingService.deleteByIds(map);
        return SUCCESS_TIP;
    }

    /**
     * 修改培训记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ProjectTraining projectTraining) {
        projectTrainingService.updateById(projectTraining);
        return SUCCESS_TIP;
    }

    /**
     * 培训记录详情
     */
    @RequestMapping(value = "/detail/{projectTrainingId}")
    @ResponseBody
    public Object detail(@PathVariable("projectTrainingId") Integer projectTrainingId) {
        return projectTrainingService.selectById(projectTrainingId);
    }

    /**
     * 获取一个人的培训记录
     */
    @RequestMapping(value = "/getTrainRecordList")
    @ResponseBody
    public Object getTrainRecordList(@RequestParam Integer idCardType,@RequestParam String idCardNumber){
        Page<Map<String,Object>> page = new PageFactory<Map<String,Object>>().defaultPage();
        List<Map<String,Object>> list= projectTrainingService.getTrainRecordList(idCardType,idCardNumber,page);
        page.setRecords((List<Map<String, Object>>) new WorkerTrainRecordWarpper(list).warp());
        return super.packForBT(page);
    }

    /**
     * 获取单条
     */
    @RequestMapping(value = "/getOneById")
    @ResponseBody
    public Object getOneById(@RequestParam Integer id){
        return projectTrainingService.getOneById(id);
    }

    /**
     * 获取单条
     */
    @RequestMapping(value = "/getWorkerList")
    @ResponseBody
    public Object getWorkerList(@RequestParam Integer id){
        Page<Map<String,Object>> page = new PageFactory<Map<String,Object>>().defaultPage();
        List<Map<String,Object>> list=projectTrainingService.getWorkerList(id,page);
        page.setRecords((List<Map<String, Object>>) new ProjectDetailWarpper(list).warp());
        return super.packForBT(page);
    }


    /**
     * (上传PDF)
     * @auth wangcw
     * 重新定义上传路径,保存到FTP 远程静态服务器上
     */
    @RequestMapping(method = RequestMethod.POST, path = "/uploadFile")
    @ResponseBody
    public Object uploadFile(@RequestPart("file") MultipartFile picture) {
        String uploadFilePath = picture.getOriginalFilename();
        //String fileName = uploadFilePath.substring(uploadFilePath.lastIndexOf("/"),uploadFilePath.lastIndexOf("."));
        String fileName = uploadFilePath;
        try {
            //重新定义上传路径,保存到FTP 远程静态服务器上
            String path = uploadService.saveFileToFTP(picture);
            Map map = new HashedMap();
            map.put("path",path);
            map.put("fileName",fileName);
            return map;
        } catch (Exception e) {
            throw new XywgException(BizExceptionEnum.UPLOAD_ERROR);
        }
    }

    /**
     * 查看附件图片
     */
    @RequestMapping(value = "/getAccessoryPicture")
    @ResponseBody
    public Object getAccessoryPicture(@RequestParam String id) {
        return settlementService.getAccessoryPicture(Long.parseLong(id),"培训附件" ,"buss_project_training");
    }

    /**
     * 上传附件
     */
    @RequestMapping(value = "/uploadAccessory", method = RequestMethod.POST)
    @ResponseBody
    public Object upload(@RequestParam("file") MultipartFile[] files, @RequestParam("settlementCode") String settlementCodeId) {
        return this.projectTrainingService.upload(files, settlementCodeId);
    }

    /**
     *@Description:获取单条培训得附件列表
     *@Author xieshuaishuai
     *@Date 2018/7/21 9:40
     */
    @RequestMapping(value = "getProjectFileList/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Object getProjectFileList(@PathVariable Long id){
        return projectTrainingService.getProjectFileList(id);
    }
}

