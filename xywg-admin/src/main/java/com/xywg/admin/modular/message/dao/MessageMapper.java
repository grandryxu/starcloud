package com.xywg.admin.modular.message.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.modular.message.model.Message;
import com.xywg.admin.modular.message.model.MessageDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 消息表 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-29
 */
public interface MessageMapper extends BaseMapper<Message> {
    List<Message> getUnreadCount(@Param("kind")Integer kind,@Param("receiveId") Integer receiveId);

    int del(@Param("id") Integer id);

    void changeIsRead(@Param("id") Integer id);

    Message getMessageById(@Param("id") Integer id);

    int createMessage(Message message);

    List<Map<String,Object>> getMessageList(@Param("kind")Integer kind, @Param("receiveId") Integer receiveId);

	List<Map<String, Object>> getMessageListV116(@Param("kind")Integer kind, @Param("receiveId") Integer receiveId, @Param("pn") int pageNo, @Param("ps") int pageSize);
}
