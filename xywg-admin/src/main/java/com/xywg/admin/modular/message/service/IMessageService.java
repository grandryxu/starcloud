package com.xywg.admin.modular.message.service;

import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.message.model.Message;
import com.xywg.admin.modular.message.model.MessageDto;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 消息表 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-29
 */
public interface IMessageService extends IService<Message> {
    int getUnreadCount(Integer kind,Integer receiveId);

    int del(Integer id);

    List<Map<String,Object>> getMessageList(Integer kind, Integer receiveId);

    /**
     *@Description:消息推送接口
     *@Author xieshuaishuai
     *@Date 2018/7/25 15:12
     */
    boolean pushMessage(String equipment,String content);

	List<Map<String,Object>> getMessageListV116(Integer kind, Integer receiveId, Integer pageNo, Integer pageSize);
}
