package com.xywg.admin.rest.modular.message.v116;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xywg.admin.modular.message.service.IMessageService;
import com.xywg.admin.rest.common.persistence.model.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v116/appMessage")
@Api(description = "消息管理")
public class V116MessageRestController {

    @Autowired
    private IMessageService messageService;

    @ApiOperation(value = "获取消息列表")
    @RequestMapping(value = "/getMessageList",method = RequestMethod.GET)
    public R getMessageList(@RequestParam Integer kind,@RequestParam Integer receiveId,Integer pageNo,Integer pageSize){
        return R.ok(messageService.getMessageListV116(kind,receiveId,pageNo,pageSize));
    }

}
