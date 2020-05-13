 package com.xywg.admin.modular.mina;
 
 import org.apache.mina.core.buffer.IoBuffer;
 import org.apache.mina.core.session.IoSession;
 import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
 import org.apache.mina.filter.codec.ProtocolEncoderOutput;
 
 public class MessageEncoder extends ProtocolEncoderAdapter
 {
   public void encode(IoSession session, Object message, ProtocolEncoderOutput out)
     throws Exception
   {
     String msg = (String)message;
     IoBuffer buffer = IoBuffer.wrap(msg.getBytes("GBK"));
     out.write(buffer);
     out.flush();
   }
 }

