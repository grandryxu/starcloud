package com.xywg.admin.modular.face.controller;

import com.xywg.admin.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.xywg.admin.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.xywg.admin.modular.face.model.PersonData;
import com.xywg.admin.modular.face.service.IPersonDataService;

/**
 * 人脸模型控制器
 *
 * @author wangcw
 * @Date 2018-05-30 08:58:58
 */
@Controller
@RequestMapping("/personData")
public class PersonDataController extends BaseController {

    private String PREFIX = "/face/personData/";

    @Autowired
    private IPersonDataService personDataService;

    /**
     * 跳转到人脸模型首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "personData.html";
    }

    /**
     * 跳转到添加人脸模型
     */
    @RequestMapping("/personData_add")
    public String personDataAdd() {

        return PREFIX + "personData_add.html";
    }

    /**
     * 跳转到修改人脸模型
     */
    @RequestMapping("/personData_update/{personDataId}")
    public String personDataUpdate(@PathVariable Integer personDataId, Model model) {
        PersonData personData = personDataService.selectById(personDataId);
        model.addAttribute("item",personData);
        LogObjectHolder.me().set(personData);
        return PREFIX + "personData_edit.html";
    }

    /**
     * 获取人脸模型列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return personDataService.selectList(null);
    }

    /**
     * 新增人脸模型
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PersonData personData) {
        personDataService.insert(personData);
        return SUCCESS_TIP;
    }

    /**
     * 删除人脸模型
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer personDataId) {
        personDataService.deleteById(personDataId);
        return SUCCESS_TIP;
    }

    /**
     * 修改人脸模型
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PersonData personData) {
        personDataService.updateById(personData);
        return SUCCESS_TIP;
    }

    /**
     * 人脸模型详情
     */
    @RequestMapping(value = "/detail/{personDataId}")
    @ResponseBody
    public Object detail(@PathVariable("personDataId") Integer personDataId) {
        return personDataService.selectById(personDataId);
    }
}
