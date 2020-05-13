 package com.xywg.admin.modular.mina;
 
 import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
 
 public class Utils
 {
	 private static Logger log = LoggerFactory.getLogger(Utils.class);
   private static final Pattern SUCCESS_PATTERN = Pattern.compile("\\bReturn\\s*\\(.*\\bresult\\s*=\\s*\"success\"[^\\)]*\\)");
 
   private static String[] chars = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
   
   private static FTPClient ftp = null;
 
   public static boolean isEmpty(String str)
   {
     return (str == null) || (str.length() == 0);
   }
 
   public static String trim(String str)
   {
     if (isEmpty(str)) {
       return null;
     }
 
     return str.trim();
   }
 
   public static boolean isBlank(String str)
   {
     int strLen;
     if ((str == null) || ((strLen = str.length()) == 0))
       return true;
     for (int i = 0; i < strLen; i++) {
       if (!Character.isWhitespace(str.charAt(i))) {
         return false;
       }
     }
     return true;
   }
 
   public static String getSHAString(String str)
   {
     if ((str == null) || (str.length() == 0))
       return null;
     MessageDigest sha;
     try
     {
       sha = MessageDigest.getInstance("SHA");
     } catch (NoSuchAlgorithmException e) {
       System.out.println("加密算法SHA不存在！");
       log.error(e.getMessage());
       return str;
     }
     return toHexString(sha.digest(str.getBytes()));
   }
 
   public static String getMD5(byte[] data)
   {
     MessageDigest md5;
     try
     {
       md5 = MessageDigest.getInstance("MD5");
     } catch (NoSuchAlgorithmException e) {
       System.out.println("加密算法MD5不存在！");
       log.error(e.getMessage());
       return null;
     }
     md5.update(data);
     return toHexString(md5.digest());
   }
 
   public static byte[] toBlob(String source)
   {
     if (isBlank(source)) {
       return null;
     }
 
     return Base64.decode(source);
   }
 
   public static boolean isSame(String strA, String strB)
   {
     if (((strA == null) && (strB != null)) || ((strA != null) && (strB == null))) {
       return false;
     }
 
     if ((strA == null) && (strB == null)) {
       return true;
     }
 
     return strA.equals(strB);
   }
 
   public static String getValue(String source, String key)
   {
     if (isEmpty(source)) {
       return null;
     }
 
     Pattern p = Pattern.compile(new StringBuilder().append("\\b").append(key).append("\\s*=\\s*\"([^\"]+)\"").toString());
     Matcher m = p.matcher(source);
     if (m.find()) {
       return m.group(1);
     }
 
     return null;
   }
 
   public static List<String> getKeyValue(String source, String key)
   {
     if (isEmpty(source)) {
       return null;
     }
     Pattern p = Pattern.compile(new StringBuilder().append("(\\b").append(key).append("\\s*=\\s*\"[^\"]+\")").toString());
     Matcher m = p.matcher(source);
     List<String> result = new ArrayList<String>();
     while (m.find()) {
       result.add(m.group(1));
     }
 
     return result;
   }
 
   public static String getFaceData(String source)
   {
     if (isEmpty(source)) {
       return null;
     }
     Pattern p = Pattern.compile("(\\b(?!name\\s*=)(?!id\\s*=)\\w+\\s*=\\s*\"[^\"]+\")");
     Matcher m = p.matcher(source);
     StringBuilder builder = new StringBuilder();
     while (m.find()) {
       builder.append(m.group(1)).append(" ");
     }
 
     return builder.toString();
   }
 
   public static boolean hasSuccessValue(String source)
   {
     return SUCCESS_PATTERN.matcher(source).matches();
   }
 
   public static String getRandomString()
   {
     StringBuffer shortBuffer = new StringBuffer();
     String uuid = UUID.randomUUID().toString().replaceAll(Pattern.quote("-"), "");
     for (int i = 0; i < 8; i++) {
       String str = uuid.substring(i * 4, i * 4 + 4);
       int x = Integer.parseInt(str, 16);
       shortBuffer.append(chars[(x % 16)]);
     }
     return shortBuffer.toString();
   }
 
   private static String toHexString(byte[] bytes)
   {
     char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
 
     char[] ob = new char[bytes.length << 1];
     for (int i = 0; i < bytes.length; i++) {
       ob[(i << 1)] = Digit[(bytes[i] >>> 4 & 0xF)];
       ob[((i << 1) + 1)] = Digit[(bytes[i] & 0xF)];
     }
     return new String(ob);
   }
 
   public static boolean isEmpty(Collection list)
   {
     return (list == null) || (list.size() == 0);
   }
 
   public static Properties loadProperties(File file)
   {
     Properties properties = new Properties();
     InputStream inputStream = null;
     try {
       inputStream = new FileInputStream(file);
       properties.load(inputStream);
     } catch (Exception e) {
       return null;
     } finally {
       if (inputStream != null)
         try {
           inputStream.close();
         }
         catch (Exception e) {
         }
     }
     return properties;
   }
 
   private static String saveFile(String path, String name, String postfix, File src, String check)
     throws Exception
   {
     MessageDigest md5 = null;
     if (!isEmpty(check)) {
       try {
         md5 = MessageDigest.getInstance("MD5");
       } catch (NoSuchAlgorithmException e) {
         System.out.println("加密算法MD5不存在！");
         log.error(e.getMessage());
       }
 
     }
 
     StringBuilder filePath = new StringBuilder(path).append(File.separator).append(getRandomString());
 
     if (name != null)
       name = new StringBuilder().append(File.separator).append(name).toString();
     else {
       name = "";
     }
 
     if (postfix != null)
       name = new StringBuilder().append(name).append(postfix).toString();
     File file;
     for (int i = 1; ; i++) {
       file = new File(TheApp.getRootPath(new StringBuilder().append(filePath.toString()).append(i).append(name).toString()));
       if (!file.exists()) {
         filePath.append(i).append(name);
         break;
       }
 
     }
 
     if (!file.getParentFile().exists()) {
       file.getParentFile().mkdirs();
       if (!file.getParentFile().exists()) {
         System.out.println(new StringBuilder().append("创建文件目录失败：").append(filePath).toString());
         return null;
       }
     }
 
     byte[] data = new byte[512];
 
     boolean needCheck = (!isEmpty(check)) && (md5 != null);
     try (FileInputStream in = new FileInputStream(src);
    		 FileOutputStream out = new FileOutputStream(file);){
       int len;
       while ((len = in.read(data)) > 0) {
         out.write(data, 0, len);
         if (needCheck) {
           md5.update(data, 0, len);
         }
       }
       out.flush();
     } catch (Exception e) {
       System.out.println(new StringBuilder().append("写文件失败：").append(filePath).toString());
       boolean delete = file.delete();
       if(!delete) {
    	   log.info("" + delete);
       }
       return null;
     } /*finally {
       close(out);
       close(in);
     }*/
 
     if ((needCheck) && 
       (!isSame(check, toHexString(md5.digest())))) {
       boolean delete = file.delete();
       if(!delete) {
    	   log.info("" + delete);
       }
       throw new Exception("文件校验失败");
     }
 
     return filePath.toString().replaceAll(Pattern.quote("\\"), "/");
   }
 
   public static void DeleteFile(String path) throws Exception {
     File file = new File(TheApp.getRootPath(path));
     if ((file.exists()) && 
       (!file.delete()))
       throw new Exception("删除文件失败");
   }
 
   public static String saveFileWithCheck(String path, String postfix, File src, String check)
     throws Exception
   {
     return saveFile(path, null, postfix, src, check);
   }
 
   public static String saveFileWithName(String path, String name, File src)
     throws Exception
   {
     return saveFile(path, name, null, src, null);
   }
 
   public static String saveFileWithPostfix(String path, String postfix, File src)
   {
     try
     {
       return saveFile(path, null, postfix, src, null);
     } catch (Exception e) {
       log.error(e.getMessage());
     }return null;
   }
 
   public static String saveFile(String path, String postfix, byte[] blob, Date date)
   {
     StringBuilder filePath = new StringBuilder(path).append(FileUtil.SEPARATOR);
     if (date != null) {
       DateFormat format = new SimpleDateFormat("yyyyMMdd");
       filePath.append(format.format(date)).append(FileUtil.SEPARATOR);
     }
     String filePath2 = filePath+"";
     String fileName = getRandomString();
     filePath.append(fileName);
     InputStream in = new ByteArrayInputStream(blob); 
     try {
		ftp = FTPClientUtil.connectFtp(Constant.FTP_HOST, Constant.FTP_PORT, Constant.FTP_USERNAME, Constant.FTP_PASS_WORD);
		FTPClientUtil.uploadFile(filePath2+"", in, fileName+".jpg", ftp);
	} catch (Exception e) {
		log.error(e.getMessage());
		System.out.println(new StringBuilder().append("写文件失败：").append(filePath).toString());
	}finally {
	       close(in);
	}
     return filePath.toString().replaceAll(Pattern.quote("\\"), "/")+".jpg";
   }
 
   public static void close(Closeable closeable)
   {
     if (closeable == null) {
       return;
     }
     try
     {
       closeable.close();
     } catch (Exception e) {
       System.out.println(new StringBuilder().append("关闭失败：").append(e.getMessage()).toString());
       log.error(e.getMessage());
     }
   }
 
   public static Timestamp startOfDay(Date day, int diff)
   {
     return correctDate(day, diff, 0, true);
   }
 
   public static Timestamp startOfDay(Date day)
   {
     return correctDate(day, 0, 0, true);
   }
 
   public static Timestamp endOfDay(Date day, int diff)
   {
     return correctDate(day, diff, 0, false);
   }
 
   public static Timestamp endOfDay(Date day)
   {
     return correctDate(day, 0, 0, false);
   }
 
   public static Timestamp startOfMonth(Date day, int diff)
   {
     return correctDate(day, diff, 1, true);
   }
 
   public static Timestamp startOfMonth(Date day)
   {
     return correctDate(day, 0, 1, true);
   }
 
   public static Timestamp endOfMonth(Date day, int diff)
   {
     return correctDate(day, diff, 1, false);
   }
 
   public static Timestamp endOfMonth(Date day)
   {
     return correctDate(day, 0, 1, false);
   }
 
   public static Timestamp startOfYear(Date day)
   {
     return correctDate(day, 0, 2, true);
   }
 
   public static Timestamp endOfYear(Date day)
   {
     return correctDate(day, 0, 2, false);
   }
 
   private static Timestamp correctDate(Date date, int diff, int type, boolean isStart)
   {
     Calendar calendar = Calendar.getInstance();
     if (date != null) {
       calendar.setTime(date);
     }
     if (type == 0) {
       if (diff != 0)
         calendar.set(5, calendar.get(5) + diff);
     }
     else if (type == 1) {
       if (diff != 0) {
         calendar.set(2, calendar.get(2) + diff);
       }
 
       if (!isStart) {
         calendar.set(2, calendar.get(2) + 1);
         calendar.set(5, 0);
       } else {
         calendar.set(5, 1);
       }
     } else {
       if (diff != 0) {
         calendar.set(1, calendar.get(1) + diff);
       }
 
       if (isStart) {
         calendar.set(2, 0);
         calendar.set(5, 1);
       } else {
         calendar.set(2, 11);
         calendar.set(5, 31);
       }
     }
 
     if (isStart) {
       calendar.set(11, 0);
       calendar.set(12, 0);
       calendar.set(13, 0);
       calendar.set(14, 0);
     } else {
       calendar.set(11, 23);
       calendar.set(12, 59);
       calendar.set(13, 59);
       calendar.set(14, 999);
     }
     return new Timestamp(calendar.getTimeInMillis());
   }
 
   public static Timestamp startOfTime(Date day, Date time, Integer diff)
   {
     return correctTime(day, time, diff, true);
   }
 
   public static Timestamp endOfTime(Date day, Date time, Integer diff)
   {
     return correctTime(day, time, diff, false);
   }
 
   private static Timestamp correctTime(Date day, Date time, Integer diff, boolean isStart)
   {
     if ((day == null) || (time == null)) {
       return null;
     }
     Calendar timeCalendar = Calendar.getInstance();
     timeCalendar.setTime(time);
 
     Calendar dayCalendar = Calendar.getInstance();
     dayCalendar.setTime(day);
     dayCalendar.set(11, timeCalendar.get(11));
     dayCalendar.set(12, timeCalendar.get(12));
 
     if ((diff != null) && (diff.intValue() != 0)) {
       dayCalendar.set(6, dayCalendar.get(6) + diff.intValue());
     }
 
     if (isStart) {
       dayCalendar.set(13, 0);
       dayCalendar.set(14, 0);
     } else {
       dayCalendar.set(13, 59);
       dayCalendar.set(14, 999);
     }
 
     return new Timestamp(dayCalendar.getTimeInMillis());
   }
 
   public static int getDayDiff(Date start, Date end)
   {
     if ((start == null) || (end == null)) {
       return 0;
     }
 
     Calendar sc = Calendar.getInstance();
     sc.setTime(start);
     Calendar ec = Calendar.getInstance();
     ec.setTime(end);
     return ec.get(6) - sc.get(6) + 1;
   }
 
   public static List<Date> getDatesBetween(Date start, Date end)
   {
     SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
     List<Date> list = new ArrayList<Date>();
     try {
       start = fmt.parse(fmt.format(start));
       end = fmt.parse(fmt.format(end));
       Date date = start;
       while (date.getTime() <= end.getTime()) {
         list.add(date);
         date = new Date(date.getTime() + 86400000L);
       }
     }
     catch (Exception e) {
     }
     return list;
   }
 
   public static Date minTime(Date time1, Date time2)
   {
     if ((time1 == null) || (time2 == null)) {
       return time1 == null ? time2 : time1;
     }
 
     return time1.before(time2) ? time1 : time2;
   }
 
   public static Date maxTime(Date time1, Date time2)
   {
     if ((time1 == null) || (time2 == null)) {
       return time1 == null ? time2 : time1;
     }
 
     return time1.before(time2) ? time2 : time1;
   }
 
   public static void setHttpResponse(HttpServletResponse res, String str)
   {
     res.setContentType("text/html;charset=utf-8");
     ServletOutputStream outputStream = null;
     try {
       outputStream = res.getOutputStream();
       byte[] data = new StringBuilder().append("<html><body><textarea>{").append(str).append("}</textarea></body></html>").toString().getBytes("UTF-8");
       outputStream.write(data);
       res.setHeader("Content-Length", Integer.toString(data.length));
       outputStream.flush();
       res.flushBuffer();
     } catch (Exception e) {
     }
     finally {
       if (outputStream != null)
         try {
           outputStream.close();
         }
         catch (Exception e)
         {
         }
     }
   }
 
   public static <T> String Java2Json(T obj)
   {
     JsonConfig config = new JsonConfig();
     config.setJsonPropertyFilter(new PropertyFilter()
     {
       public boolean apply(Object source, String name, Object value)
       {
         return value == null;
       }
     });
     return JSONObject.fromObject(obj, config).toString();
   }
 
   public static <T> T Json2Java(String json, Class<T> clazz)
   {
     return (T)JSONObject.toBean(JSONObject.fromObject(json), clazz);
   }

 
   @Deprecated
   private static void checkField(StringBuilder description, String name, String oldStr, String newStr)
   {
     if (isSame(oldStr, newStr)) {
       return;
     }
 
     description.append("<br/>").append(name).append("(").append(oldStr != null ? oldStr : "空").append(")变更为：").append(newStr != null ? oldStr : "空").append("；");
   }
 
   private static String dateString(Date day)
   {
     if (day == null) {
       return null;
     }
     DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
     return format.format(day);
   }
 
   public static String numberString(Number data)
   {
     if (data == null) {
       return null;
     }
 
     if (((data instanceof Float)) || ((data instanceof Double))) {
       return String.format("%.2f", new Object[] { data });
     }
 
     return data.toString();
   }
 

 
   public static int validSystemState()
   {
	            return 99;

    
//     for (CodeInfo code : codes) {
//       if (macSet.contains(code.mac))
//       {
//         short[] rst = checkCode(code.key, genDeviceId(code.mac));
//         if ((rst != null) && (rst[2] >= 0))
//         {
//           if (rst[2] == 99) {
//             if (rst[0] < 0) {
//               TheApp.setSystemDeadLine(rst[0]);
//               TheApp.setMaxDeviceCount(rst[1]);
//               state = 99;
//               if (rst[1] == -1)
//                 break;
//             }
//             else {
//               Calendar cal = Calendar.getInstance();
//               cal.set(1, 2014);
//               cal.set(6, rst[0]);
//               if (cal.getTime().before(new Date())) {
//                 if (state < 0)
//                   state = 0;
//               }
//               else {
//                 if (state <= 0) {
//                   TheApp.setSystemDeadLine(rst[0]);
//                   TheApp.setMaxDeviceCount(rst[1]);
//                 } else if ((TheApp.getMaxDeviceCount() != -1) && (rst[1] > TheApp.getMaxDeviceCount())) {
//                   TheApp.setSystemDeadLine(rst[0]);
//                   TheApp.setMaxDeviceCount(rst[1]);
//                 }
//                 state = 99;
//                 if (rst[1] == -1)
//                   break;
//               }
//             }
//           }
//         }
//       }
//     }
     //return state;
   }
 
/*   public static List<CodeInfo> parseRegFile(File regFile) throws Exception
   {
     Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(regFile);
     List<Node> cards = getChildNodeByTagName(document.getDocumentElement(), "card");
     if (isEmpty(cards)) {
       return null;
     }
 
     List<CodeInfo> codes = new ArrayList<CodeInfo>();
     for (Node node : cards) {
       List mac = getChildNodeByTagName(node, "mac");
       if (!isEmpty(mac))
       {
         List key = getChildNodeByTagName(node, "key");
         if (!isEmpty(key))
         {
           CodeInfo info = new CodeInfo();
           info.mac = formatMac(((Node)mac.get(0)).getTextContent());
           if (!isEmpty(info.mac))
           {
             info.key = ((Node)key.get(0)).getTextContent();
             codes.add(info);
           }
         }
       }
     }
     return codes;
   }*/
 
   private static String formatMac(String textContent)
   {
     if (textContent == null) {
       return null;
     }
 
     char[] raw = textContent.toCharArray();
     char[] out = new char[raw.length];
     int len = 0;
     for (char c : raw) {
       if (((c >= 'a') && (c <= 'f')) || ((c >= 'A') && (c <= 'F')) || ((c >= '0') && (c <= '9'))) {
         out[(len++)] = c;
       }
     }
 
     if (len == 0) {
       return null;
     }
 
     return new String(out, 0, len).toUpperCase();
   }
 
   private static List<Node> getChildNodeByTagName(Node node, String name)
   {
     List<Node> nodes = new ArrayList<Node>();
     NodeList children = node.getChildNodes();
     int len = children.getLength();
     for (int i = 0; i < len; i++) {
       Node n = children.item(i);
       if ((n.getNodeType() == 1) && (n.getNodeName().equalsIgnoreCase(name))) {
         nodes.add(n);
       }
     }
 
     return nodes;
   }
 
   public static String getLocalIp()
   {
     try
     {
       Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
       while (netInterfaces.hasMoreElements()) {
         NetworkInterface ni = (NetworkInterface)netInterfaces.nextElement();
         Enumeration ips = ni.getInetAddresses();
         while (ips.hasMoreElements()) {
           InetAddress address = (InetAddress)ips.nextElement();
           if ((!address.isLinkLocalAddress()) && (!address.isLoopbackAddress()))
           {
             String ip = address.getHostAddress();
             if ((ip != null) && (!ip.contains(":")) && (!ip.contains("%")))
               return ip;
           }
         }
       }
     } catch (Exception e) {
       return null;
     }
     return null;
   }
 
   public static Set<String> getLocalMac()
   {
     Set<String> macList = new HashSet<String>();
     try {
       Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
       while (netInterfaces.hasMoreElements()) {
         NetworkInterface ni = (NetworkInterface)netInterfaces.nextElement();
         if (!ni.isLoopback())
         {
           byte[] mac = ni.getHardwareAddress();
           if (mac != null)
           {
             StringBuilder builder = new StringBuilder();
             int len = mac.length;
             for (int i = 0; i < len; i++) {
               builder.append(String.format("%02X", new Object[] { Byte.valueOf(mac[i]) }));
             }
             macList.add(builder.toString());
           }
         }
       } } catch (Exception e) { return macList; }
 
     return macList;
   }
 
   public static <T> void sortList(List<T> list, final String sortField, final String sortMode)
   {
     Collections.sort(list, new Comparator<T>()
     {
       public int compare(T o1, T o2) {
         try {
           Class clazz = o1.getClass();
           Field field = clazz.getDeclaredField(sortField);
           field.setAccessible(true);
           String typeName = field.getType().getName().toLowerCase();
 
           Object v1 = field.get(o1);
           Object v2 = field.get(o2);
 
           boolean ASC_order = (sortMode == null) || ("ASC".equalsIgnoreCase(sortMode));
 
           if (typeName.endsWith("string")) {
             String value1 = v1.toString();
             String value2 = v2.toString();
             return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
           }
           if (typeName.endsWith("short")) {
             Short value1 = Short.valueOf(Short.parseShort(v1.toString()));
             Short value2 = Short.valueOf(Short.parseShort(v2.toString()));
             return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
           }
           if (typeName.endsWith("byte")) {
             Byte value1 = Byte.valueOf(Byte.parseByte(v1.toString()));
             Byte value2 = Byte.valueOf(Byte.parseByte(v2.toString()));
             return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
           }
           if (typeName.endsWith("char")) {
             Integer value1 = Integer.valueOf(v1.toString().charAt(0));
             Integer value2 = Integer.valueOf(v2.toString().charAt(0));
             return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
           }
           if ((typeName.endsWith("int")) || (typeName.endsWith("integer"))) {
             Integer value1 = Integer.valueOf(Integer.parseInt(v1.toString()));
             Integer value2 = Integer.valueOf(Integer.parseInt(v2.toString()));
             return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
           }
           if (typeName.endsWith("long")) {
             Long value1 = Long.valueOf(Long.parseLong(v1.toString()));
             Long value2 = Long.valueOf(Long.parseLong(v2.toString()));
             return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
           }
           if (typeName.endsWith("float")) {
             Float value1 = Float.valueOf(Float.parseFloat(v1.toString()));
             Float value2 = Float.valueOf(Float.parseFloat(v2.toString()));
             return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
           }
           if (typeName.endsWith("double")) {
             Double value1 = Double.valueOf(Double.parseDouble(v1.toString()));
             Double value2 = Double.valueOf(Double.parseDouble(v2.toString()));
             return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
           }
           if (typeName.endsWith("boolean")) {
             Boolean value1 = Boolean.valueOf(Boolean.parseBoolean(v1.toString()));
             Boolean value2 = Boolean.valueOf(Boolean.parseBoolean(v2.toString()));
             return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
           }
           if (typeName.endsWith("date")) {
             Date value1 = (Date)v1;
             Date value2 = (Date)v2;
             return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
           }
           if (typeName.endsWith("timestamp")) {
             Timestamp value1 = (Timestamp)v1;
             Timestamp value2 = (Timestamp)v2;
             return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
           }
 
           Method method = field.getType().getDeclaredMethod("compareTo", new Class[] { field.getType() });
           method.setAccessible(true);
           int result = ((Integer)method.invoke(v1, new Object[] { v2 })).intValue();
           return ASC_order ? result : result * -1;
         }
         catch (Exception e)
         {
           String err = e.getLocalizedMessage();
           System.out.println(err);
         }
 
         return 0;
       }
     });
   }
 }

