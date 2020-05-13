package com.xywg.admin.modular.worker.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.common.exception.BizExceptionEnum;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.bad.service.IWorkerBadRecordsService;
import com.xywg.admin.modular.project.model.ProjectWorker;
import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import com.xywg.admin.modular.project.service.IProjectWorkerService;
import com.xywg.admin.modular.system.service.IAreaService;
import com.xywg.admin.modular.system.service.IDictService;
import com.xywg.admin.modular.system.service.UploadService;
import com.xywg.admin.modular.team.model.TeamMaster;
import com.xywg.admin.modular.team.service.ITeamMasterService;
import com.xywg.admin.modular.worker.model.NationVo;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import com.xywg.admin.modular.worker.model.WorkerMasterVO;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;
import com.xywg.admin.modular.worker.warpper.WorkerWarpper;
import com.xywg.admin.modular.worker.warpper.WorkerWarpperVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工人管理控制器
 *
 * @author wangcw
 * @Date 2018-05-22 15:24:44
 */
@Controller
@RequestMapping("/workerMaster")
public class WorkerMasterController extends BaseController {

    private String PREFIX = "/worker/workerMaster/";

    @Autowired
    private IWorkerBadRecordsService workerBadRecordsService;
    @Autowired
    private IDictService dictService;

    @Autowired
    private IWorkerMasterService workerMasterService;
    @Autowired
    private IAreaService iAreaService;
    @Autowired
    private IProjectWorkerService projectWorkerService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;
    @Autowired
    private ITeamMasterService teamMasterService;

    /**
     * 跳转到工人管理首页
     */
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("birthPlaceCode", iAreaService.getBirthPlaceCode());
        model.addAttribute("dictCultureLevelType", dictService.selectByName("文化程度"));
        model.addAttribute("dictGender", dictService.selectByName("性别"));
        model.addAttribute("dictWorkTypeCode", dictService.selectByName("工种字典数据"));
        model.addAttribute("dictAge", dictService.selectByName("年龄区间"));
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        return PREFIX + "workerMaster.html";
    }

    /**
     * 跳转到添加工人管理
     */
    @RequestMapping("/workerMaster_add")
    public String workerMasterAdd(Model model) {
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        model.addAttribute("dictGender", dictService.selectByName("性别"));
        model.addAttribute("dictNation", dictService.selectByName("民族"));
        model.addAttribute("dictIdCardType", dictService.selectByName("人员证件类型"));
        model.addAttribute("dictPoliticsType", dictService.selectByName("政治面貌"));
        model.addAttribute("dictWorkTypeCode", dictService.selectByName("工种字典数据"));
        model.addAttribute("dictCultureLevelType", dictService.selectByName("文化程度"));
        model.addAttribute("dictBirthPlaceCode", iAreaService.getBirthPlaceCode());
        model.addAttribute("isEnterprise",ShiroKit.getUser().getIsEnterprise());
        model.addAttribute("projectCode", ShiroKit.getSessionAttr("toggleProjectCode"));
        List<TeamMaster> tms = teamMasterService.getTeamMasterByProjectCode(ShiroKit.getSessionAttr("toggleProjectCode"));
        model.addAttribute("teams",tms);
        return PREFIX + "workerMaster_add.html";
    }

    /**
     * 跳转到拍照页面
     * @return
     */
    @RequestMapping("/workerMaster_monitor")
    public String workerMasterMonitor() {
        return PREFIX + "workerMaster_monitor.html";
    }

    /**
     * 跳转到支持h5 canvas
     * @return
     */
    @RequestMapping(value = "/workerMaster_monitor_h5")
    public String workerMasterMonitorH5() {
        return PREFIX + "workerMaster_monitor_h5.html";
    }

    /**
     * 跳转到修改工人管理
     */
    @RequestMapping("/workerMaster_update/{workerMasterId}")
    public String workerMasterUpdate(@PathVariable Integer workerMasterId, @RequestParam(required = false) Long pwId, Model model) {
        WorkerMaster workerMaster = workerMasterService.selectById(workerMasterId);
        if(pwId != null){
            ProjectWorker projectWorker = projectWorkerService.selectById(pwId);
            if(projectWorker != null){
                workerMaster.setProjectCode(projectWorker.getProjectCode());
                workerMaster.setTeamSysNo(projectWorker.getTeamSysNo().toString());
                workerMaster.setIsTeamLeader(projectWorker.getTeamWorkerType()==0?1:0);
                workerMaster.setCardNumber(projectWorker.getCardNumber());
                workerMaster.setWorkTypeCode(projectWorker.getWorkTypeCode());
                List<TeamMaster> tms = teamMasterService.getTeamMasterByProjectCode(projectWorker.getProjectCode());
                model.addAttribute("teams",tms);
            }
            model.addAttribute("teamSysNo",projectWorker.getTeamSysNo());
        }
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        model.addAttribute("workers", workerBadRecordsService.getWorkers());
        model.addAttribute("dictGender", dictService.selectByName("性别"));
        model.addAttribute("dictNation", dictService.selectByName("民族"));
        model.addAttribute("dictIdCardType", dictService.selectByName("人员证件类型"));
        model.addAttribute("dictPoliticsType", dictService.selectByName("政治面貌"));
        model.addAttribute("dictWorkTypeCode", dictService.selectByName("工种字典数据"));
        model.addAttribute("dictCultureLevelType", dictService.selectByName("文化程度"));
        model.addAttribute("dictBirthPlaceCode", iAreaService.getBirthPlaceCode());
        model.addAttribute("isEnterprise",ShiroKit.getUser().getIsEnterprise());
        model.addAttribute("pwId",pwId);
        model.addAttribute("item", workerMaster);
        if(!model.containsAttribute("teams")) {
        	model.addAttribute("teams",new ArrayList());
        }
        LogObjectHolder.me().set(workerMaster);
        return PREFIX + "workerMaster_edit.html";
    }

    /**
     * 弹出工人搜索弹框
     */
    @RequestMapping("/openAddWorkerSearchDialog")
    public String openAddWorkerSearchDialog() {
        return PREFIX + "worker_add_search.html";
    }

    /**
     * 查看详情
     */
    @RequestMapping("/workerMaster_view/{workerMasterId}")
    public String workerMasterView(@PathVariable Integer workerMasterId, @RequestParam(required = false) Long pwId, Model model) {
        WorkerMaster workerMaster/* = workerMasterService.selectById(workerMasterId)*/;
        if (workerMasterService.selectById(workerMasterId)==null){
            workerMaster = new WorkerMaster();
        }else {
            workerMaster = workerMasterService.selectById(workerMasterId);
        }
        if(pwId != null){
            ProjectWorker projectWorker = projectWorkerService.selectById(pwId);
//            if(projectWorker != null){
//                workerMaster.setProjectCode(projectWorker.getProjectCode());
//                workerMaster.setTeamSysNo(projectWorker.getTeamSysNo().toString());
//            }
            if(projectWorker != null){
                Integer teamSysNo = projectWorker.getTeamSysNo();
                String teamSysNoToString = String.valueOf(teamSysNo);

                workerMaster.setTeamSysNo(teamSysNoToString);
                workerMaster.setIsTeamLeader(projectWorker.getTeamWorkerType()==0 ? 1:0);
                workerMaster.setCardNumber(projectWorker.getCardNumber());
                workerMaster.setWorkTypeCode(projectWorker.getWorkTypeCode());
            }

          model.addAttribute("project", projectWorkerService.queryProjectInfoById(pwId));
        }else {
        	Map<String, Object> map = new HashMap<>();
        	map.put("projectName", "");
        	map.put("teamName", "");
            map.put("cardNumber", "");
        	model.addAttribute("project", map);
        }
//        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        model.addAttribute("dictGoodRecordLevelType", dictService.selectByName("奖励级别"));
        model.addAttribute("workers", workerBadRecordsService.getWorkers());
        model.addAttribute("dictGender", dictService.selectByName("性别"));
        model.addAttribute("dictNation", dictService.selectByName("民族"));
        model.addAttribute("dictIdCardType", dictService.selectByName("人员证件类型"));
        model.addAttribute("dictPoliticsType", dictService.selectByName("政治面貌"));
        model.addAttribute("dictWorkTypeCode", dictService.selectByName("工种字典数据"));
        model.addAttribute("dictCultureLevelType", dictService.selectByName("文化程度"));
        model.addAttribute("dictBirthPlaceCode", iAreaService.getBirthPlaceCode());
        model.addAttribute("dictBadRecordLevelType", dictService.selectByName("事件级别"));
        model.addAttribute("item", workerMaster);
        System.out.println("######################门禁卡号为："+workerMaster.getCardNumber());
        LogObjectHolder.me().set(workerMaster);
        return PREFIX + "workerMaster_info.html";
    }


    /**
     * 获取工人管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map) {

        Page<WorkerMaster> page = new PageFactory<WorkerMaster>().defaultPage();
        List<Map<String, Object>> workers = workerMasterService.selectWorkers(page, map);
        page.setRecords((List<WorkerMaster>) new WorkerWarpper(workers).warp());
        return super.packForBT(page);
    }

    /**
     * 项目切换 获取工人管理列表
     *
     * @author 蔡伟
     */
    @RequestMapping(value = "/toggleList")
    @ResponseBody
    public Object toggleList(@RequestParam Map<String, Object> map) {
        Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
        map.put("projectCode", ShiroKit.getSessionAttr("toggleProjectCode"));
        List<Map<String, Object>> workers = workerMasterService.getAll(page, map);
        page.setRecords((List<Map<String, Object>>) new WorkerWarpper(workers).warp());
        return super.packForBT(page);
    }


    /**
     * 获取工人所有列表
     *
     * @author 蔡伟
     */
    @RequestMapping(value = "/getAll")
    @ResponseBody
    public Object getAll(@RequestParam Map<String, Object> map) {
        Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
        List<Map<String, Object>> workers = workerMasterService.getAll(page, map);
        page.setRecords((List<Map<String, Object>>) new WorkerWarpper(workers).warp());
        return super.packForBT(page);
    }

    /**
     * (上传图片(上传到项目的webapp/static/img))
     * <p>
     * 重新定义上传路径,保存到FTP 远程静态服务器上
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public String upload(@RequestPart("file") MultipartFile picture) {
        try {
            /*String fileSavePath = gunsProperties.getFileUploadPath();
            picture.transferTo(new File(fileSavePath + pictureName));*/

            //重新定义上传路径,保存到FTP 远程静态服务器上
            return uploadService.saveFileToFTP(picture);
        } catch (Exception e) {
            throw new XywgException(BizExceptionEnum.UPLOAD_ERROR);
        }
    }


    /**
     * 新增工人管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(WorkerMasterVO workerMaster) {
        workerMasterService.insert(workerMaster);
        return SUCCESS_TIP;
    }

    /**
     * 删除工人管理
     */
/*    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Map<String, Object> map) {
        workerMasterService.deleteWorkerInfoAndLoginInfo(map);
        return SUCCESS_TIP;
    }*/
    /**
     * 删除工人管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String ids) {
        List<String> idList = JSON.parseArray(ids, String.class);
        workerMasterService.deletes(idList);
        return SUCCESS_TIP;
    }
    /**
     * 修改工人管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(WorkerMaster workerMaster) {
        workerMasterService.updateById(workerMaster);
        return SUCCESS_TIP;
    }

    /**
     * 工人管理详情
     */
    @RequestMapping(value = "/detail/{workerMasterId}")
    @ResponseBody
    public Object detail(@PathVariable("workerMasterId") Integer workerMasterId) {
        return workerMasterService.selectById(workerMasterId);
    }

    /**
     * 根据证件类型和证件号获取工人信息
     */
    @RequestMapping(method = RequestMethod.POST, path = "/searchWorker")
    @ResponseBody
    public Object searchWorker(HttpServletRequest request) {
        String idCardNumber = request.getParameter("idCardNumber");
        String idCardType = request.getParameter("idCardType");
        if (idCardType == null && idCardType == "") {
            idCardType = "0";
        }
        List<Map<String, Object>> WorkerMaster = workerMasterService.searchWorker(idCardNumber, Integer.valueOf(idCardType));
        return new WorkerWarpper(WorkerMaster).warp();
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
        workerMasterService.download(res, req, params);
    }


    /**
     * excel导入
     */
    @RequestMapping(method = RequestMethod.POST, path = "/excelUpload")
    @ResponseBody
    public String excelUpload(@RequestPart("file") MultipartFile excelFile, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return workerMasterService.Import(excelFile, request);
    }

    /**
     * 根据项目id班组编号查询出人员列表
     * xieshuaishuai
     */
    @RequestMapping(value = "/getPersonListByTeamCode")
    @ResponseBody
    public Object getPersonListByTeamCode(@RequestParam Map<String, Object> map) {
        Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
        List<Map<String, Object>> list = workerMasterService.getPersonListByTeamCode(map, page);
        page.setRecords((List<Map<String, Object>>) new WorkerWarpperVo(list).warp());
        return super.packForBT(page);
    }

    /**
     * 无分页
     * xieshuaishuai
     */
    @RequestMapping(value = "/getPersonListByTeamCodeNoPage")
    @ResponseBody
    public Object getPersonListByTeamCodeNoPage(@RequestParam Map<String, Object> map) {
        List<Map<String, Object>> list = workerMasterService.getPersonListByTeamCodeNoPage(map);
        return new WorkerWarpperVo(list).warp();
    }

    /**
     * 查询民族编码
     */
    @RequestMapping(method = RequestMethod.POST, path = "/getNationNum")
    @ResponseBody
    public Object getNationNum(HttpServletRequest request) throws Exception {
        String name = request.getParameter("nationName");
        Integer num = dictService.selectNumByName("民族", name);
        NationVo nationVo = new NationVo(num);
        return nationVo;
    }
}
