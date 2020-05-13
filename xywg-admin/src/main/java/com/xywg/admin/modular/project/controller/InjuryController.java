package com.xywg.admin.modular.project.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.modular.project.model.Injury;
import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import com.xywg.admin.modular.project.service.IInjuryService;
import com.xywg.admin.modular.project.wrapper.InjuryWrapper;
import com.xywg.admin.modular.system.model.FileInfo;
import com.xywg.admin.modular.system.service.IDictService;
import com.xywg.admin.modular.system.service.IFileInfoService;
import com.xywg.admin.modular.system.service.UploadService;
import com.xywg.admin.modular.wages.service.SettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工伤管理控制器
 *
 * @author wangcw
 * @Date 2018-06-21 22:22:38
 */
@Controller
@RequestMapping("/injury")
public class InjuryController extends BaseController {

    private String PREFIX = "/project/injury/";

    @Autowired
    private IInjuryService injuryService;
    
    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;
    
    @Autowired
    private IDictService dictService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private IFileInfoService fileInfoService;
    @Autowired
    private SettlementService settlementService;


    /**
     * 跳转到工伤管理首页
     */
    @RequestMapping("")
    public String index(Model model) {
    	model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
		model.addAttribute("type", dictService.selectByName("工伤类型"));
        return PREFIX + "injury.html";
    }

    /**
     * 跳转到添加工伤管理
     */
    @RequestMapping("/injury_add")
    public String injuryAdd(Model model) {
    	model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
		model.addAttribute("types", dictService.selectByName("工伤类型"));
        return PREFIX + "injury_add.html";
    }

    /**
     * 跳转到修改工伤管理
     */
    @RequestMapping("/injury_update/{injuryId}")
    public String injuryUpdate(@PathVariable Long injuryId, Model model) {
        Map<String, Object> injury = injuryService.findById(injuryId);
        model.addAttribute("item",injury);
    	model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
		model.addAttribute("types", dictService.selectByName("工伤类型"));
        LogObjectHolder.me().set(injury);
        return PREFIX + "injury_edit.html";
    }
    


    /**
     * 获取工伤管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map) {
    	Page<Injury> page = new PageFactory<Injury>().defaultPage();
		List<Map<String, Object>> devices = injuryService.selectList(page, map);
		page.setRecords((List<Injury>) new InjuryWrapper(devices).warp());
		return super.packForBT(page);
    }

    /**
     * 新增工伤管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Injury injury) {
        injuryService.saveInjury(injury);
        return SUCCESS_TIP;
    }

    /**
     * 删除工伤管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Map<String,Object> map) {
        injuryService.deleteByIds(map);
        return SUCCESS_TIP;
    }

    /**
     * 修改工伤管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Injury injury) {
        injuryService.updateInjury(injury);
        return SUCCESS_TIP;
    }


    /**
     * 工伤管理详情
     */
    @RequestMapping("/detail/{injuryId}")
    public String detail(@PathVariable Long injuryId, Model model) {
        Map<String, Object> injury = injuryService.findById(injuryId);
        model.addAttribute("item",injury);
        return PREFIX + "injury_detail.html";
    }

    /**
     * 上传附件
     */
    @RequestMapping(value = "/uploadAccessory", method = RequestMethod.POST)
    @ResponseBody
    public Object upload(@RequestParam("file") MultipartFile files, @RequestParam("settlementCode") String settlementCodeId) {
        //允许上传的文件类型
        String fileType = "gif,png,bmp,jpeg,jpg,tiff,pcx,tga,exif,fpx,svg,psd,cdr,pcd,dxf,ufo,eps,ai,raw,wmf,webp";
        //允许上传的文件最大大小(10M,单位为byte)
        int maxSize = 10 * 1024 * 1024;
        List<FileInfo> fileInfoList = new ArrayList<>();
        //重新定义上传路径和文件名称,保存到FTP 远程静态服务器上,返回基础文件地址
        Map<String, String> resMap = uploadService.fileHandle(files,fileType,maxSize);
        if (resMap.containsKey("path")) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(files.getOriginalFilename());
            fileInfo.setPath(resMap.get("path"));
            fileInfo.setRelevanceId(Long.parseLong(settlementCodeId));
            //buss_settlement 结算单表
            fileInfo.setType("buss_injury");
            fileInfoList.add(fileInfo);
            //保存结算单和附件的关联关系到数据库表:buss_file_info
            fileInfoService.insertPatch(fileInfoList);
            return SUCCESS_TIP;
        } else {
            return resMap;
        }
    }
    /**
     * 查看附件图片
     */
    @RequestMapping(value = "/getAccessoryPicture")
    @ResponseBody
    public Object getAccessoryPicture(@RequestParam String id) {
        return settlementService.getAccessoryPicture(Long.parseLong(id),"工伤附件" ,"buss_injury");
    }
}
