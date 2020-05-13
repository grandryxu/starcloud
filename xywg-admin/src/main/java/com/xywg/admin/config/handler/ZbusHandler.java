package com.xywg.admin.config.handler;

import io.zbus.mq.Broker;
import io.zbus.mq.Message;
import io.zbus.mq.Producer;

import java.io.IOException;

/**
 * zbus处理类
 * @author  hh cao
 * @date 2019/3/28
 */
public class ZbusHandler {
    //测试
  // private static final String address = "192.168.1.64:15555";
    //线上
    private static final String address = "192.168.1.187:15555";
    private static final String topic = "topic-lwt-longxin";

    public static int send(String message) {
        try {
            Broker broker = new Broker(address);
            Producer p = new Producer(broker);
            //当确定队列不存在需创建
            p.declareTopic(topic);
            Message msg = new Message();
            msg.setToken("lwt-001");
            //设置消息主题
            msg.setTopic(topic);
            msg.setBody(message);
            Message res = p.publish(msg);
            broker.close();
            return res.getStatus();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 500;
    }
}
