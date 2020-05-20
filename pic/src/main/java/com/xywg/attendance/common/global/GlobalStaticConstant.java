package com.xywg.attendance.common.global;

/**
 * @author jingyun_hu
 * @date 2018/10/28
 * 全局静态常量类
 */
public class GlobalStaticConstant {

    /**
     * rabbitMq 消息topic
     */
    public static final String RABBITMQ_TOPIC_NAME_MQ= "xywg.lwt.public.attendance.mq.topic.message";

    /**
     * mongodb 原始数据时Key
     */
    public static final String MONGODB_ORIGINAL_DATA= "mongodb_original_data";
    /**
     * mongodb 接受数据时错误Key
     */
    public static final String MONGODB_ORIGINAL_DATA_ERROR= "mongodb_original_data_error";

    /**
     * separator
     */
    public static final String  SEPARATOR_EQUAL_SIGN="=";
    public static final String  SEPARATOR_WITH="&";




    //设备在线
    public static final int DEVICE_STATE_ON_LINE = 1;
    //设备离线
    public static final int DEVICE_STATE_OFF_LINE = 0;
    //设备启用状态 启用
    public static final int DEVICE_STATE_ENABLED = 1;
    //设备启用状态 未启用
    public static final int DEVICE_STATE_NOT_ENABLED = 0;

    public static final String  HTTP_METHOD_POST="post";

    public static final String  HTTP_METHOD_GET="get";

    public static final  String DEVICE_URL_A="/iclock/cdata";

    //上传更新信息
    public static final  String updateUrl="/iclock/getrequest";

    //上传考勤
    public static final String postAttend="&table=ATTLOG";
    //url的变量
    public static  String attendUrl="";
    //考勤图片保存
    public static final String SAVEPIC="savePic";


    /**
     *设备获取命令redis的key 的前缀(这个key必须要和web端下发命令时存储到redis中的保持一致,否则获取不到web端下发的命令)
     */
//    public static final String DEVICE_GET_COMMAND_REDIS_KEY= "xywg.zk.public.getCommand.";
    public static final String DEVICE_GET_COMMAND_REDIS_KEY= "lwt.zk.getCommand.liyang.common.";

}
