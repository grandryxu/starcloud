package com.xywg.admin.modular.message.service.impl;

import com.xywg.admin.core.util.PushUtil;
import com.xywg.admin.modular.message.model.Message;
import com.xywg.admin.modular.faceUtils.FileUtil;
import com.xywg.admin.modular.message.dao.MessageMapper;
import com.xywg.admin.modular.message.model.MessageDto;
import com.xywg.admin.modular.message.service.IMessageService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-29
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

	private static Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);
    @Resource
    private MessageMapper messageMapper;
    @Override
    public int getUnreadCount(Integer kind,Integer receiveId) {
        return messageMapper.getUnreadCount(kind,receiveId).size();
    }

    @Override
    public int del(Integer id) {
        return messageMapper.del(id);
    }

    @Override
    public List<Map<String,Object>> getMessageList(Integer kind,Integer receiveId) {
        return messageMapper.getMessageList(kind,receiveId);
    }

    @Override
    public boolean pushMessage(String equipment, String content) {
        try {
            PushUtil.getInstance().advancedPush(1, equipment, content);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

	@Override
	public List<Map<String, Object>> getMessageListV116(Integer kind, Integer receiveId, Integer pageNo,
			Integer pageSize) {
		return messageMapper.getMessageListV116(kind,receiveId,(pageNo-1)*pageSize,pageSize);
	}
}
