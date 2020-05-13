package com.xywg.admin.modular.device.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.base.tips.ErrorTip;
import com.xywg.admin.core.base.tips.Tip;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.common.exception.BizExceptionEnum;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.modular.device.model.DeviceQr;
import com.xywg.admin.modular.device.service.IDeviceQrService;
import com.xywg.admin.modular.device.wrapper.DeviceRecordQrWarpper;
import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import com.xywg.admin.modular.system.service.IDictService;
import com.xywg.admin.modular.system.service.UploadService;
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
 * 二维码管理控制器
 *
 * @author wangcw
 * @Date 2018-05-30 16:24:42
 */
@Controller
@RequestMapping("/deviceQr")
public class DeviceQrController extends BaseController {

    private String PREFIX = "/device/deviceQr/";
    @Autowired
    private UploadService uploadService;

    @Autowired
    private IDeviceQrService deviceQrService;

    @Autowired
    private IDictService dictService;

    @Autowired
    private ICooperationProjectMasterService cooperationProjectMasterService;

    /**
     * 跳转到二维码管理首页
     */
    @RequestMapping("")
    public String index(@RequestParam Map<String, Object> map, Model model) {
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        model.addAttribute("projectCode", map.get("projectCode"));
        model.addAttribute("projectName", map.get("projectName"));
        model.addAttribute("projectStatus", map.get("projectStatus"));
        model.addAttribute("versions", deviceQrService.selectVersions());
        return PREFIX + "deviceQr.html";
    }

    /**
     * 跳转到添加二维码管理
     */
    @RequestMapping("/deviceQr_add")
    public String deviceQrAdd(@RequestParam Map<String, Object> map, Model model) {
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        model.addAttribute("projectCode", map.get("projectCode"));
        model.addAttribute("projectName", map.get("projectName"));
        model.addAttribute("state", dictService.selectByName("设备状态"));
        return PREFIX + "deviceQr_add.html";
    }


    /**
     * 跳转到修改二维码管理 s
     */
    @RequestMapping("/deviceQr_update/{deviceQrId}")
    public String deviceQrUpdate(@PathVariable Long deviceQrId, Model model) {
        DeviceQr deviceQr = deviceQrService.getOneById(deviceQrId);
        model.addAttribute("project", cooperationProjectMasterService.getList(new HashMap<>()));
        model.addAttribute("item", deviceQr);
        LogObjectHolder.me().set(deviceQr);
        return PREFIX + "deviceQr_edit.html";
    }

    /**
     * 获取二维码管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map) {
        Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
        List<Map<String, Object>> list = deviceQrService.list(map, page);
        page.setRecords((List<Map<String, Object>>) new DeviceRecordQrWarpper(list).warp());
        return super.packForBT(page);
    }

    /**
     * 新增二维码管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(DeviceQr deviceQr) {
        int i = deviceQrService.saveDeviceQr(deviceQr);
        if (i == 1) {
            return SUCCESS_TIP;
        } else {
            return new ErrorTip(600, "该序列号已存在！");
        }

    }

    /**
     * 删除二维码管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Map<String, Object> map) {
        deviceQrService.deleteByIds(map);
        return SUCCESS_TIP;
    }

    /**
     * 修改二维码管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(DeviceQr deviceQr) {
        int i = deviceQrService.updateDeviceQr(deviceQr);
        if (i == 0) {
            return new ErrorTip(600, "该序列号已存在！");
        } else {
            return SUCCESS_TIP;
        }
    }

    /**
     * 二维码管理详情
     */
    @RequestMapping(value = "/detail/{deviceQrId}")
    @ResponseBody
    public Object detail(@PathVariable("deviceQrId") Integer deviceQrId) {
        return deviceQrService.selectById(deviceQrId);
    }

    /**
     * @param map
     * @return
     * @description 终端代码升级
     * @author chupp
     * @date 2018年8月6日
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upCode")
    @ResponseBody
    public Tip upCode(@RequestParam Map<String, Object> map) {
        return deviceQrService.upCode(map);
    }

    /**
     * 
     * @description 远程重启
     * @author chupp
     * @date 2018年11月13日
     * @param map
     * @return
     *
     */
    @RequestMapping(method = RequestMethod.POST, path = "/remoteRestart")
    @ResponseBody
    public Tip remoteRestart(@RequestParam Map<String, Object> map) {
        return deviceQrService.remoteRestart(map);
    }
    
    /**
     * (上传PDF)
     *
     * @auth yuanyang
     * 重新定义上传路径,保存到FTP 远程静态服务器上
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public Object upload(@RequestPart("file") MultipartFile file) {
        String uploadFilePath = file.getOriginalFilename();
        //String fileName = uploadFilePath.substring(uploadFilePath.lastIndexOf("/"),uploadFilePath.lastIndexOf("."));
        String fileName = uploadFilePath;
        if (fileName.endsWith(".bin")&&fileName.indexOf("qr_controller_update_")==0) {
            try {
                String name=fileName.substring(21,fileName.indexOf(".bin"));

                //重新定义上传路径,保存到FTP 远程静态服务器上
                String path = uploadService.saveFileToFTP(file);
                Map<String, String> map = new HashedMap();
                map.put("path", path);
                map.put("name", name);
                deviceQrService.upload(map);
                return SUCCESS_TIP;
            } catch (Exception e) {
                throw new XywgException(501, "上传失败");
            }

        } else {
            throw new XywgException(801, "上传文件格式错误，请上传bin文件!");
        }

    }


    /**
     * 获取版本列表
     */
    @RequestMapping(value = "/getVersion")
    @ResponseBody
    public Object getVersion() {
        return deviceQrService.selectVersions();
    }
}
