 package com.xywg.admin.modular.mina;
 
 import java.util.regex.Pattern;
 
 public enum CommandToken
 {
   PostEmployee(1, makePattern("PostEmployee"), "请求上传人员"), 
   Employee(2, makePattern("Employee"), "上传的人员数据"),
   PostRecord(3, makePattern("PostRecord"), "请求上传卡点"),
   Record(4, makePattern("Record"), "上传的卡点数据"), 
   GetRequest(5, makePattern("GetRequest"), "请求命令"), 
   Return(6, makePattern("Return"), "返回结果"), 
   Quit(7, makePattern("Quit"), "退出连接"), 
   SysUpgrade(8, makePattern("SysUpgrade"), "升级失败");
 
   private int value;
   private Pattern pattern;
   private String description;
 
   private CommandToken(int value, Pattern pattern, String descripton) { this.value = value;
     this.pattern = pattern;
     this.description = descripton; }
 
   public int getValue()
   {
     return this.value;
   }
 
   public Pattern getPattern() {
     return this.pattern;
   }
 
   public String getDescription() {
     return this.description;
   }
 
   public static CommandToken parse(String value)
     throws Exception
   {
     CommandToken[] vs = values();
     for (CommandToken s : vs) {
       if (s.pattern.matcher(value).matches()) {
         return s;
       }
     }
 
     throw new Exception("未知命令");
   }
 
   private static Pattern makePattern(String key)
   {
     return Pattern.compile("\\b" + key + "\\s*\\([^\\)]*\\)");
   }
 
   public String toString()
   {
     return this.description;
   }
 }

