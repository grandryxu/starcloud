 package com.xywg.admin.modular.mina;
 
 import java.nio.ByteOrder;
 import org.apache.mina.core.buffer.IoBuffer;
 import org.apache.mina.core.session.IoSession;
 import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
 import org.apache.mina.filter.codec.ProtocolDecoderOutput;
 
 public class MessageDecoder extends CumulativeProtocolDecoder
 {
   protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
     throws Exception
   {
     in.order(ByteOrder.LITTLE_ENDIAN);
     int start = 0;
     while (in.hasRemaining()) {
       int length = in.indexOf((byte)41) + 1 - start;
       if (length < 1) {
         return false;
       }
 
       byte[] bytes = new byte[length];
       in.get(bytes);
       start += length;
       String data = new String(bytes, "GBK");
       out.write(data);
     }
 
     return !in.hasRemaining();
   }
 }

