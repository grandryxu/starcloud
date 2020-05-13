package com.xywg.admin.modular.wages.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import com.xywg.admin.modular.system.model.Dict;
import com.xywg.admin.modular.system.model.FileInfo;
import com.xywg.admin.modular.system.service.IFileInfoService;
import com.xywg.admin.modular.system.service.UploadService;
import com.xywg.admin.modular.team.model.TeamMaster;
import com.xywg.admin.modular.team.service.ITeamMasterService;
import com.xywg.admin.modular.wages.model.Settlement;
import com.xywg.admin.modular.wages.model.SettlementDetail;
import com.xywg.admin.modular.wages.model.dto.PictureDto;
import com.xywg.admin.modular.wages.service.SettlementService;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @author hujingyun
 */
@Controller
@RequestMapping("/settlement")
public class SettlementController extends BaseController {

    private String PREFIX = "/wages/settlement/";
    @Autowired
    private SettlementService settlementService;
    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;
    @Autowired
    private ITeamMasterService teamMasterService;
    @Autowired
    private IWorkerMasterService workerMasterService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private IFileInfoService fileInfoService;

    /**
     * 跳转到结算单首页
     */
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        model.addAttribute("isEnterprise", ShiroKit.getUser().getIsEnterprise());
        return PREFIX + "settlement.html";
    }

    /**
     * 跳转到结算单添加页面
     */
    @RequestMapping("settlement_add")
    public String settlementAdd(Model model) {
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        return PREFIX + "settlement_add.html";
    }


    /**
     * 跳转到结算单添加页面
     */
    @RequestMapping("settlement_payroll")
    public String settlementPayroll(Model model) {
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        return PREFIX + "settlement_payroll.html";
    }


    /**
     * 跳转到结算单添加明细页面
     *
     * @auth 王长伟
     */
    @RequestMapping("settlement_add_detail")
    public String settlementAddDetail(Model model) {
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        return PREFIX + "settlement_add_detail.html";
    }

    /**
     * 跳转到结算单编辑页面
     */
    @RequestMapping("settlement_edit")
    public String settlementEdit(Model model, @RequestParam String settlementCode) {
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        model.addAttribute("settlementCode", settlementCode);
        return PREFIX + "settlement_edit.html";
    }


    /**
     * 跳转到工人列表 new
     */
    @RequestMapping("settlement_workerList")
    public String settlementWorkerList(Model model, @RequestParam String projectCode) {
        model.addAttribute("projectCode", projectCode);
        return PREFIX + "settlement_workerList.html";
    }


    /**
     * 跳转到添加结算单工资详情弹窗
     */
    @RequestMapping(value = "/settlementAddDetail")
    public String settlementAddDetail(Model model, @RequestParam Map<String, Object> map) {
        model.addAttribute("workerIdList", map.get("workerIdList"));
        model.addAttribute("projectCode", map.get("projectCode"));
        //Map<String,Object>  newmap = JSON.parseArray(selected, Settlement.class);

        //return workerMasterService.getWorkerMoneyTable(map);
        return PREFIX + "settlement_add_detail.html";
    }


    /**
     * 根据项目Code 获取 班组
     */
    @RequestMapping("/getTeamMasterByProjectCode")
    @ResponseBody
    public List<TeamMaster> getTeamMasterByProjectCode(@RequestParam String projectCode) {
        return teamMasterService.getTeamMasterByProjectCode(projectCode);
    }


    /**
     * 根据条件 获取 工人
     */
    @RequestMapping("/getWorkerMasterByProjectCode")
    @ResponseBody
    public Object getWorkerMasterByProjectCode(@RequestParam Map<String, Object> map) {
        Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
        List<Map<String, Object>> workerMasterList = workerMasterService.getWorkerMasterByProjectCode(page, map);
        page.setRecords(workerMasterList);
        return super.packForBT(page);
    }


    /**
     * 批量删除结算单
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(@RequestParam String selected) {
        List<Settlement> list = JSON.parseArray(selected, Settlement.class);
        settlementService.deleteBatch(list);
        return SUCCESS_TIP;
    }


    /**
     * 结算单list
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map,Model model) {
        Page<Map<String,Object>> page = new PageFactory<Map<String,Object>>().defaultPage();
        List<Map<String,Object>> settlementList = settlementService.getList(page, map);
        page.setRecords(settlementList);
        return super.packForBT(page);
    }


    /**
     * 根据项目和工人信息获取累发工资参数
     */
    @RequestMapping(value = "/getWorkerMoneyTable")
    @ResponseBody
    public Object getWorkerMoneyTable(@RequestParam Map<String, Object> map) {


        return workerMasterService.getWorkerMoneyTable(map);
    }


    /**
     * 双击跳转到结算单详情页面
     */
    @RequestMapping(value = "/detail")
    public String getSettlementDetail(Model model, @RequestParam String settlementCode) {
        model.addAttribute("settlementCode", settlementCode);
        return PREFIX + "settlement_detail.html";
    }


    /**
     * 根据结算单编号,项目和工人信息获取累发工资参数
     */
    @RequestMapping(value = "/getDetailList")
    @ResponseBody
    public Object getDetailList(@RequestParam Map<String, Object> map) {
        Page<SettlementDetail> page = new PageFactory<SettlementDetail>().defaultPage();
        List<SettlementDetail> settlementList = settlementService.getSettlementDetail(page, map);
        page.setRecords(settlementList);

        return super.packForBT(page);
    }


    /**
     * 根据结算单编号,项目和工人信息获取累发工资参数
     */
    @RequestMapping(value = "/getDetailListNoPage")
    @ResponseBody
    public Object getDetailListNoPage(@RequestParam Map<String, Object> map) {
        List<SettlementDetail> settlementList = settlementService.getDetailListNoPage(map);
        return settlementList;
    }

    /**
     * 根据工种输入框输入的值获取工种
     */
    @RequestMapping(value = "/getWorkerType")
    @ResponseBody
    public List<Dict> getWorkerType(String workerTypeName) {
        return settlementService.getWorkerType(workerTypeName);
    }


    /**
     * 工资发放
     */
    @RequestMapping(value = "/payroll")
    @ResponseBody
    public Object payrollBatch(@RequestParam String selected) {
        List<Settlement> list = JSON.parseArray(selected, Settlement.class);
        settlementService.payrollBatch(list);
        return SUCCESS_TIP;
    }

    /**
     * 上传附件
     */
    @RequestMapping(value = "/uploadAccessory", method = RequestMethod.POST)
    @ResponseBody
    public Object upload(@RequestParam(value = "file") MultipartFile[] files, @RequestParam("settlementCode") String settlementCodeId) {
        //允许上传的文件类型
        String fileType = "gif,png,bmp,jpeg,jpg,tiff,pcx,tga,exif,fpx,svg,psd,cdr,pcd,dxf,ufo,eps,ai,raw,wmf,webp";
        //允许上传的文件最大大小(10M,单位为byte)
        int maxSize = 10 * 1024 * 1024;

        List<FileInfo> fileInfoList = new ArrayList<>();
        Map<String, String> resMap = null;
        for (MultipartFile f : files) {
            //重新定义上传路径和文件名称,保存到FTP 远程静态服务器上,返回基础文件地址
            resMap = uploadService.fileHandle(f,fileType,maxSize);
            if (resMap.containsKey("path")) {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setFileName(f.getOriginalFilename());
                fileInfo.setPath(resMap.get("path"));
                fileInfo.setRelevanceId(Long.parseLong(settlementCodeId));
                //buss_settlement 结算单表
                fileInfo.setType("buss_settlement");
                fileInfoList.add(fileInfo);
            } else {
                break;
            }
        }
        if(fileInfoList.size()>0){
            //保存结算单和附件的关联关系到数据库表:buss_file_info
            fileInfoService.insertPatch(fileInfoList);
        }
        return resMap;
    }


    /**
     * 结算单审核
     */
    @RequestMapping(value = "/checkInfo")
    @ResponseBody
    public Object checkInfo(@RequestParam String selected) {
        List<Settlement> list = JSON.parseArray(selected, Settlement.class);
        settlementService.checkInfo(list);
        return SUCCESS_TIP;
    }


    /**
     * 结算单复核
     */
    @RequestMapping(value = "/review")
    @ResponseBody
    public Object review(@RequestParam String selected) {
        List<Settlement> list = JSON.parseArray(selected, Settlement.class);
        settlementService.review(list);
        return SUCCESS_TIP;
    }


    /**
     * 根据结算单编号获取详情
     */
    @RequestMapping(value = "/getSettlementDetailList")
    @ResponseBody
    public Object getSettlementDetailList(@RequestParam Map<String, Object> map) {
        List<SettlementDetail> settlementDetailList = settlementService.getSettlementDetailBySettlementCode(map);
        return settlementDetailList;
    }


    /**
     * 保存结算单明细,以及结算单
     */
    @RequestMapping(value = "/saveSettlement")
    @ResponseBody
    public Object saveSettlement(@RequestParam Map<String, Object> map) {
        String settlementJson = map.get("settlement") + "";
        String settlementJsonArray = map.get("SettlementDetailList") + "";
        if (StrUtil.hasBlank(settlementJson) || StrUtil.hasBlank(settlementJsonArray)) {
            return ERROR;
        }
        Settlement settlement = JSON.parseObject(settlementJson, Settlement.class);
        List<SettlementDetail> list = JSON.parseArray(settlementJsonArray, SettlementDetail.class);
        settlementService.saveSettlement(settlement, list);
        return SUCCESS_TIP;
    }

    /**
     * 更新结算单明细,以及结算单
     */
    @RequestMapping(value = "/updateSettlementDetailBatch")
    @ResponseBody
    public Object updateSettlementDetailBatch(@RequestParam Map<String, Object> map) {
        String settlementJson = map.get("settlement") + "";
        String settlementJsonArray = map.get("SettlementDetailList") + "";
        if (StrUtil.hasBlank(settlementJson) || StrUtil.hasBlank(settlementJsonArray)) {
            return ERROR;
        }
        Settlement settlement = JSON.parseObject(settlementJson, Settlement.class);
        List<SettlementDetail> list = JSON.parseArray(settlementJsonArray, SettlementDetail.class);
        settlementService.updateSettlementDetailBatch(settlement, list);
        return SUCCESS_TIP;
    }


    /**
     * 查看附件图片
     */
    @RequestMapping(value = "/getAccessoryPicture")
    @ResponseBody
    public Object getAccessoryPicture(@RequestParam String id) {
        return settlementService.getAccessoryPicture(Long.parseLong(id), "结算单附件", "buss_settlement");
    }

    /**
     * 删除附件图片
     */
    @RequestMapping(value = "/deleteAccessoryPicture")
    @ResponseBody
    public Object deleteAccessoryPicture(@RequestParam String ids) {
        List<String> list = JSON.parseArray(ids, String.class);
        settlementService.deleteAccessoryPicture(list);
        return  SUCCESS_TIP;
    }

}
