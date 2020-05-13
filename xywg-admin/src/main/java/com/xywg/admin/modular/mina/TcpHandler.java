 package com.xywg.admin.modular.mina;
 
 import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xywg.admin.modular.device.model.Device;
import com.xywg.admin.modular.device.model.DeviceCommand;
import com.xywg.admin.modular.device.model.DeviceRecord;
import com.xywg.admin.modular.device.service.IDeviceCommandService;
import com.xywg.admin.modular.device.service.IDeviceService;
import com.xywg.admin.modular.face.model.PersonData;
import com.xywg.admin.modular.face.service.IPersonDataService;
import com.xywg.admin.modular.worker.model.WorkerMaster;

 @Service
 public class TcpHandler extends IoHandlerAdapter
 {
	 
   private static final Object locker = new Object();
   protected static Map<String, DeviceCommand> upgradeMap = new HashMap();
   private static  Logger logger = LoggerFactory.getLogger(TcpHandler.class);
 
   @Autowired
   private IDeviceService deviceService;
   
   @Autowired
   private IDeviceCommandService deviceCommandService;
   
   @Autowired
   private IPersonDataService personDataService;
 
   public void sessionIdle(IoSession session, IdleStatus status) throws Exception { session.close(false); }
   @Override
   public void exceptionCaught(IoSession session, Throwable cause)
     throws Exception
   {
     super.exceptionCaught(session, cause);
     session.close(false);
   }
   
   /**
    * 
    * @Description: 接收消息
    * @author cxl  
    * @date 2017年12月29日 下午2:38:45
    */
   @Override
   public void messageReceived(IoSession session, Object message) throws Exception {
     String msg = ((String)message).trim();
     this.logger.debug("<< " + msg);
     CommandToken token;
     try {
       token = CommandToken.parse(msg);
     } catch (Exception e) {
       this.logger.error(e.getMessage());
       this.logger.error("" + msg);
       session.close(true);
       return;
     }
 
     String out = null;
     Device device;
     switch (token) {
     //在考勤机修改用户的时候,先发请求信息
     case PostEmployee:
       device = getDeviceInfo(session, msg);
       if (device != null)
         out = "Return(result=\"success\")"; break;
     //在考勤机修改用户的时候,发送具体信息
     case Employee:
         out = saveEmployee(session, msg);
         break;
     //在考勤机考勤用户的时候,先发请求信息
     case PostRecord:
         device = getDeviceInfo(session, msg);
         if (device != null)
           out = "Return(result=\"success\" postphoto=\"true\")"; break;
     //在考勤机考勤用户的时候,先发具体信息
     case Record:
       out = saveRecord(session, msg);
       break;
     //根据设备id返回命令文本(向考勤机发送命令)
     case GetRequest:
         device = getDeviceInfo(session, msg);
         if (device != null)
         {
           out = getRequest(session, device);
         }
         break;
     //考勤机如返回"quit时则断开连接"
     case Return:
         out = processCommand(session, msg);
         break;
     case SysUpgrade:
         processUpgradeFailed(msg);
     default:
       session.close(true);
       return;
     }
 
     if (out == null) {
       session.close(true);
       return;
     }
     session.write(out);
     this.logger.debug(">> " + out);
   }
 
   /**
    * 
   * @param @param session
   * @param @param msg
   * @param @return 
   * @Description: 获取设备具体信息
   * @author cxl  
   * @date 2017年12月29日 下午2:39:22
    */
   private Device getDeviceInfo(IoSession session, String msg)
   {
     String sn = Utils.getValue(msg, "sn");
     if (Utils.isEmpty(sn)) {
       this.logger.error("设备SN缺失");
       return null;
     }
 
     Device device = this.deviceService.deviceHeartbeatArrived(sn);
     if (device == null) {
       this.logger.error(sn + "：设备不在系统中");
       return null;
     }
 
     TheApp.cacheDevice(device.getId());
     device.setSn(sn);
     session.setAttribute("device", device);
     return device;
   }
 
   /**
    * 
   * @param @param session
   * @param @param msg
   * @param @return
   * @param @throws ParseException 
   * @Description: 保存设备信息至文件
   * @author cxl  
   * @date 2017年12月29日 下午2:39:50
    */
   private String saveRecord(IoSession session, String msg) throws ParseException
   {
	 Device device = (Device)session.getAttribute("device");
     if (device == null) {
       this.logger.error("设备信息丢失3");
       return null;
     }
 
     String userId = Utils.getValue(msg, "id");
     if (Utils.isEmpty(userId)) {
       this.logger.error("人员ID缺失");
       return "Return(result=\"failed\")";
     }
 
     String time = Utils.getValue(msg, "time");
     if (Utils.isEmpty(time)) {
       this.logger.error("考勤时间缺失");
       return "Return(result=\"failed\")";
     }
     String photo = Utils.getValue(msg, "photo");
     DeviceRecord record = new DeviceRecord();
     SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
     record.setTime(sdf.parse(time));
     if (record.getTime() == null) {
       this.logger.error("解析考勤时间失败");
       return "Return(result=\"failed\")";
     }
 
     byte[] blob = Utils.toBlob(photo);
     if ((blob != null) && (blob.length > 0)) {
       record.setPhoto(Utils.saveFile(TheApp.getDeviceRecordPath(), ".jpg", blob, record.getTime()));
     }
     WorkerMaster personnel = new WorkerMaster();
     personnel.setWorkerName(Utils.getValue(msg, "name"));
     personnel.setIdCardNumber(userId);
     record.setPerson(personnel);
     record.setDevice(device);
     if (TheApp.cacheRecord(record)) {
       return "Return(result=\"success\")";
     }
     return "Return(result=\"failed\")";
   }
   
   
   private String saveEmployee(IoSession session, String msg) 
   {
	 Device device = (Device)session.getAttribute("device");
     if (device == null) {
       this.logger.error("设备信息丢失2");
       return null;
     }

     String userId = Utils.getValue(msg, "id");
     if (Utils.isEmpty(userId)) {
       this.logger.error("人员ID缺失");
       return "Return(result=\"failed\")";
     }
 
     String algVersion = Utils.getValue(msg, "alg_edition");
     if (Utils.isEmpty(algVersion)) {
       if (Utils.isEmpty(device.getAlgVersion())) {
         this.logger.error("模板算法版本缺失");
         return "Return(result=\"failed\")";
       }
       algVersion = device.getAlgVersion();
     }
 
     if (Utils.isEmpty(Utils.getKeyValue(msg, "face_data"))) {
       this.logger.error("特征数据缺失");
       return "Return(result=\"failed\")";
     }
 
     String face = Utils.getFaceData(msg);
     if (Utils.isEmpty(face)) {
       this.logger.error("获取模板信息失败");
       return "Return(result=\"failed\")";
     }
     PersonData data = new PersonData();
     data.setUserName(Utils.getValue(msg, "name"));
     data.setDeviceSn(device.getSn());
     data.setTxFace(face);
     data.setAlgVersion(algVersion);
     this.personDataService.savePersonData(data,userId);
     return "Return(result=\"success\")";
   }
   
   private String getRequest(IoSession session, Device device)
   {
     if (upgradeMap.get(device.getSn()) != null) {
       return "CheckUpgradeStatus()";
     }
 
     List commands = this.deviceCommandService.getDeviceCommand(device.getSn());
     if (Utils.isEmpty(commands)) {
       return "Quit()";
     }
 
     session.setAttribute("commands", commands);
     return makeCommand(commands);
   }
   
   private String processCommand(IoSession session, String msg)
   {
	 Device device = (Device)session.getAttribute("device");
     if (device == null) {
       this.logger.error("设备信息丢失1");
       return "Quit()";
     }
 
     String out = processUpgradeCommand(session, msg, device.getSn());
     if (out != null) {
       return out;
     }
 
     List commands = (List)session.getAttribute("commands");
     if (Utils.isEmpty(commands)) {
       this.logger.error(device.getId() + "：命令丢失");
       return "Quit()";
     }
 
     DeviceCommand cmd = (DeviceCommand)commands.remove(0);
     if (Utils.hasSuccessValue(msg)) {
       cmd.setState(1L);
 
       if (cmd.getType().intValue() == DeviceCommand.Type.GetInfo.getValue()) {
         device.setVersion(Utils.getValue(msg, "edition"));
         String ip = Utils.getValue(msg, "ip");
         if ((ip != null) && (ip.length() == 12) && (ip.indexOf(46) == -1)) {
           try {
             device.setIp(Integer.parseInt(ip.substring(0, 3)) + "." + Integer.parseInt(ip.substring(3, 6)) + "." + Integer.parseInt(ip.substring(6, 9)) + "." + Integer.parseInt(ip.substring(9, 12)));
           }
           catch (Exception e)
           {
             device.setIp(ip.substring(0, 3) + "." + ip.substring(3, 6) + "." + ip.substring(6, 9) + "." + ip.substring(9, 12));
           }
         }
         else {
           device.setIp(ip);
         }
         device.setMac(Utils.getValue(msg, "mac"));
         device.setTypeName(Utils.getValue(msg, "type"));
         String algVersion = Utils.getValue(msg, "alg_edition");
         if (!Utils.isEmpty(algVersion)) {
           device.setAlgVersion(algVersion);
         }
         this.deviceService.setDeviceInfo(device);
       } else if (cmd.getType().intValue() == DeviceCommand.Type.Upgrade.getValue()) {
         session.removeAttribute("commands");
         synchronized (locker) {
           upgradeMap.put(device.getSn(), cmd);
         }
         return "Quit()";
       }
     } else {
       String reason = Utils.getValue(msg, "reason");
       if (reason != null) {
         cmd.setDescription("失败原因：" + reason);
       }
       cmd.setState(-1L);
     }
 
     this.deviceCommandService.setCommandState(cmd);
 
     if (Utils.isEmpty(commands)) {
       session.setAttribute("device", null);
       return "Quit()";
     }
 
     return makeCommand(commands);
   }
   
   private String processUpgradeCommand(IoSession session, String msg, String sn)
   {
	 DeviceCommand cmd = (DeviceCommand)upgradeMap.get(sn);
     if (cmd == null) {
       return null;
     }
 
     int state = 0;
     String reason = null;
     if (!Utils.hasSuccessValue(msg)) {
       state = -1;
       reason = Utils.getValue(msg, "reason");
     } else {
       String status = Utils.getValue(msg, "status");
       if (status == null) {
         state = -1;
         reason = Utils.getValue(msg, "reason");
       } else if (status.equals("100")) {
         state = 1;
       }
     }
 
     if (state != 0) {
       upgradeMap.remove(sn);
       cmd.setState((long)state);
       if (reason != null) {
         cmd.setDescription("失败原因：" + reason);
       }
       this.deviceCommandService.setCommandState(cmd);
       if (state == 1) {
         this.deviceService.askDeviceInfo(sn);
         return "Quit()";
       }
     }
 
     return "Quit()";
   }
   
   private void processUpgradeFailed(String msg)
   {
     String sn = Utils.getValue(msg, "sn");
     if (Utils.isEmpty(sn)) {
       return;
     }
 
     DeviceCommand cmd = (DeviceCommand)upgradeMap.get(sn);
     if (cmd == null) {
       return;
     }
 
     String result = Utils.getValue(msg, "result");
     if (result != null) {
       if (result.toUpperCase().equals("SUCCESS")) {
         return;
       }
       upgradeMap.remove(sn);
       cmd.setState(-1L);
       this.deviceCommandService.setCommandState(cmd);
     }
   }
   
   private String makeCommand(List<DeviceCommand> commands)
   {
     Iterator iterator = commands.iterator();
     if (iterator.hasNext()) {
       DeviceCommand command = (DeviceCommand)iterator.next();
       String text = command.toString();
       if (Utils.isEmpty(text)) {
         command.setState(-1L);
         this.deviceCommandService.setCommandState(command);
         iterator.remove();
       }
       command.setDescription(null);
       return text;
     }
 
     return "Quit()";
   }
 }

