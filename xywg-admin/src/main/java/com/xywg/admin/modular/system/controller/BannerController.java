
package com.xywg.admin.modular.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.core.common.constant.factory.PageFactory;
import com.xywg.admin.core.common.exception.BizExceptionEnum;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.log.LogObjectHolder;
import com.xywg.admin.modular.system.model.Banner;
import com.xywg.admin.modular.system.service.BannerService;
import com.xywg.admin.modular.system.service.UploadService;
import com.xywg.admin.modular.system.warpper.BannerWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 轮播图管理控制器
 *
 * @author wangcw
 * @Date 2018-06-25 10:41:23
 */
@Controller
@RequestMapping("/banner")
public class BannerController extends BaseController {

    private String PREFIX = "/system/banner/";
    @Autowired
    private UploadService uploadService;
    @Autowired
    private BannerService bannerService;

    /**
     * 跳转到轮播图管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "banner.html";
    }

    /**
     * 跳转到添加轮播图管理
     */
    @RequestMapping("/banner_add")
    public String bannerAdd() {
        return PREFIX + "banner_add.html";
    }


    /**
     * 跳转到修改轮播图管理
     */
    @RequestMapping("/banner_order")
    public String bannerOrder()  {
        return PREFIX + "banner_order.html";
    }

    /**
     * 跳转到修改轮播图管理
     */
    @RequestMapping("/banner_update/{bannerId}")
    public String bannerUpdate(@PathVariable Integer bannerId, Model model) {
        Banner banner = bannerService.selectById(bannerId);
        model.addAttribute("item",banner);
        LogObjectHolder.me().set(banner);
        return PREFIX + "banner_edit.html";
    }

    /**
     * 跳转到修改轮播图管理
     */
    @RequestMapping("/banner_view/{bannerId}")
    public String bannerView(@PathVariable Integer bannerId, Model model) {
        Banner banner = bannerService.selectById(bannerId);
        model.addAttribute("item",banner);
        LogObjectHolder.me().set(banner);
        return PREFIX + "banner_info.html";
    }

    /**
     * 获取轮播图管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map) {
        Page<Banner> page = new PageFactory<Banner>().defaultPage();
        List<Map<String, Object>> banners=bannerService.selectBanners(page,map);
        page.setRecords((List<Banner>) new BannerWarpper(banners).warp());
        return super.packForBT(page);
    }


    /**
     * 新增轮播图管理
     */
    @RequestMapping(value = "/updateOrder")
    @ResponseBody
    public Object updateOrder(int[] ids) {
        bannerService.updateOrder(ids);



//        bannerService.insert(banner);
        return SUCCESS_TIP;
    }

    /**
     * 新增轮播图管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Banner banner) {
        bannerService.insert(banner);
        return SUCCESS_TIP;
    }

    /**
     * 根据类型获取轮播图
     */
    @RequestMapping(value = "/getBannersByType")
    @ResponseBody
    public Object getFile(@RequestParam Map<String, Object> map) {
        return bannerService.getBannersByType(map);
    }

    /**
     * 删除轮播图管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Map<String,Object> map) {
        bannerService.deleteByIds(map);
        return SUCCESS_TIP;
    }

    /**
     * 修改轮播图管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Banner banner) {
        bannerService.updateById(banner);
        return SUCCESS_TIP;
    }




    /**
     * (上传图片(上传到项目的webapp/static/img))
     *
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
     * 轮播图管理详情
     */
    @RequestMapping(value = "/detail/{bannerId}")
    @ResponseBody
    public Object detail(@PathVariable("bannerId") Integer bannerId) {

        return bannerService.selectById(bannerId);
    }
}
