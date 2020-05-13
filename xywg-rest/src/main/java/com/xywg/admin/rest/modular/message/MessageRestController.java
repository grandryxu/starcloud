package com.xywg.admin.rest.modular.message;

import com.xywg.admin.modular.message.model.Message;
import com.xywg.admin.modular.message.service.IMessageService;
import com.xywg.admin.rest.common.persistence.model.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/5/30 11:10
 */
@RestController
@RequestMapping("appMessage")
@Api(description = "消息管理")
public class MessageRestController {

    @Autowired
    private IMessageService messageService;

    /**
     * 获取未读消息数
     */
    @ApiOperation(value = "获取未读消息数")
    @RequestMapping(value = "/getUnreadCount",method = RequestMethod.GET)
    public R getUnreadCount(@RequestParam Integer kind,@RequestParam Integer receiveId){
        return R.ok(messageService.getUnreadCount(kind, receiveId));
    }

    /**
     * 根据id删除消息
     */
    @ApiOperation(value = "删除消息")
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public R del(@RequestBody Message message){
        messageService.del(message.getId().intValue());
        return R.ok();
    }

    @ApiOperation(value = "获取消息列表")
    @RequestMapping(value = "/getMessageList",method = RequestMethod.GET)
    public R getMessageList(@RequestParam Integer kind,@RequestParam Integer receiveId){
        return R.ok(messageService.getMessageList(kind,receiveId));
    }

}
