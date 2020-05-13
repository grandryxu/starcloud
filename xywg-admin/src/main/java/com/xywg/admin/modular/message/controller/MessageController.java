package com.xywg.admin.modular.message.controller;

import com.xywg.admin.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.xywg.admin.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.xywg.admin.modular.message.model.Message;
import com.xywg.admin.modular.message.service.IMessageService;

/**
 * 消息管理控制器
 *
 * @author wangcw
 * @Date 2018-05-29 10:28:33
 */
@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {

    private String PREFIX = "/message/message/";

    @Autowired
    private IMessageService messageService;

    /**
     * 跳转到消息管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "message.html";
    }

    /**
     * 跳转到添加消息管理
     */
    @RequestMapping("/message_add")
    public String messageAdd() {
        return PREFIX + "message_add.html";
    }

    /**
     * 跳转到修改消息管理
     */
    @RequestMapping("/message_update/{messageId}")
    public String messageUpdate(@PathVariable Integer messageId, Model model) {
        Message message = messageService.selectById(messageId);
        model.addAttribute("item",message);
        LogObjectHolder.me().set(message);
        return PREFIX + "message_edit.html";
    }

    /**
     * 获取消息管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return messageService.selectList(null);
    }

    /**
     * 新增消息管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Message message) {
        messageService.insert(message);
        return SUCCESS_TIP;
    }

    /**
     * 删除消息管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer messageId) {
        messageService.deleteById(messageId);
        return SUCCESS_TIP;
    }

    /**
     * 修改消息管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Message message) {
        messageService.updateById(message);
        return SUCCESS_TIP;
    }

    /**
     * 消息管理详情
     */
    @RequestMapping(value = "/detail/{messageId}")
    @ResponseBody
    public Object detail(@PathVariable("messageId") Integer messageId) {
        return messageService.selectById(messageId);
    }

    /**
     *@Description:消息推送接口
     *@Author xieshuaishuai
     *@Date 2018/7/25 15:15
     */
    @RequestMapping(value = "/pushMessage")
    @ResponseBody
    public Object pushMessage(@RequestParam String equipment,@RequestParam String content){//equipment 手机设备号 content 推送消息内容
        return messageService.pushMessage(equipment,content);
    }
}
