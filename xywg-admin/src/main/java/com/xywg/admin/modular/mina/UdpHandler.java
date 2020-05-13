 package com.xywg.admin.modular.mina;
 
 import java.util.regex.Pattern;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xywg.admin.modular.device.model.Device;
import com.xywg.admin.modular.device.service.IDeviceCommandService;
import com.xywg.admin.modular.device.service.IDeviceService;
 
 @Service
 public class UdpHandler extends IoHandlerAdapter
 {
   private static final Pattern PATTERN = Pattern.compile("\\bDevStatus\\s*\\([^\\)]+\\)");
   private static  Logger logger = LoggerFactory.getLogger(UdpHandler.class);
 
   @Autowired
   private IDeviceService deviceService;
   
   @Autowired
   private IDeviceCommandService deviceCommandService;
 
   public void sessionIdle(IoSession session, IdleStatus status) throws Exception { session.close(false); }
 
   public void messageReceived(IoSession session, Object message) throws Exception
   {
     String msg = ((String)message).trim();
     this.logger.debug("<< " + msg);
 
     if (!PATTERN.matcher(msg).matches()) {
       this.logger.error("心跳数据格式错误");
       session.close(true);
       return;
     }
 
     String sn = Utils.getValue(msg, "sn");
     if (Utils.isEmpty(sn)) {
       this.logger.error("设备SN缺失");
       session.close(true);
       return;
     }
 
     Device device = this.deviceService.deviceHeartbeatArrived(sn);
     if (device == null) {
       this.logger.error(sn + "：设备不在系统中");
       return;
     }
 
     TheApp.cacheDevice(device.getId());
     
     if ((TcpHandler.upgradeMap.get(sn) != null) || (this.deviceCommandService.getDeviceCommandCount(device.getSn()) > 0)) {
         session.write("PostRequest()");
         this.logger.debug(">> PostRequest()");
       }
   }
 }

