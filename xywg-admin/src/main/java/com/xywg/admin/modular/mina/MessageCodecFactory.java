 package com.xywg.admin.modular.mina;
 
 import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.springframework.stereotype.Component;
 
 @Component("messageCodecFactory")
 public class MessageCodecFactory
   implements ProtocolCodecFactory
 {
   private final MessageEncoder encoder;
   private final MessageDecoder decoder;
 
   public MessageCodecFactory()
   {
     this.encoder = new MessageEncoder();
     this.decoder = new MessageDecoder();
   }
 
   public ProtocolEncoder getEncoder(IoSession session) throws Exception
   {
     return this.encoder;
   }
 
   public ProtocolDecoder getDecoder(IoSession session) throws Exception
   {
     return this.decoder;
   }
 }

