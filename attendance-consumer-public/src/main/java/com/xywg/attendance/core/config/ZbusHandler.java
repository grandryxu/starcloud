package com.xywg.attendance.core.config;

import io.zbus.mq.Broker;
import io.zbus.mq.Message;
import io.zbus.mq.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * zbus处理类
 * @author  hh cao
 * @date 2019/3/28
 */
public class ZbusHandler {
    public static String address;
    private static final String topic = "topic-lwt-liyang";
    private static Logger logger = LoggerFactory.getLogger("");

    public static void send(String message) {
        try {
            logger.info("SEND MESSAGE TO " +  address);
            Broker broker = new Broker(address);
            Producer p = new Producer(broker);
            p.declareTopic(topic);    //当确定队列不存在需创建
            Message msg = new Message();
            msg.setToken("lwt-001");
            msg.setTopic(topic);       //设置消息主题
            msg.setBody(message);
            Message res = p.publish(msg);
            logger.info(res.toString());
            broker.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
