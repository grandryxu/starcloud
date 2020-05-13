package com.xywg.admin.modular.smz.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import sun.misc.BASE64Encoder;
import com.xywg.admin.core.util.ImageUtils;

/**
 * @Description: 图片转流工具类
 * @Author xieshuaishuai
 * @Date Create in 2018/7/16 11:15
 */
public class ImageUtil {
    // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
    public static String GetImageStr(String imgFile) throws Exception {
        URL url = new URL(imgFile);
        //byte data[]=null;
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(4 * 1000);
        conn.setReadTimeout(4000);
        conn.setUseCaches(false); //不使用缓冲
        InputStream inStream = conn.getInputStream();

        //inputstream只能读取一次   用ByteArrayOutputStream 封装成可重复读取得流 详情参见 https://www.douban.com/note/311552295/
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BufferedInputStream br = new BufferedInputStream(inStream);
        byte[] b = new byte[1024];
        for (int c = 0; (c = br.read(b)) != -1;)
        {
            bos.write(b, 0, c);
        }
        b = null;
        br.close();
        InputStream bis = new ByteArrayInputStream(bos.toByteArray());
        return ImageUtils.compressImage(bis,bis.available() );

       /* //byte[] data = readInputStream(inStream);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[bis.available()];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while( (len=bis.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //inStream.read(outStream.toByteArray());
        inStream.close();
        bis.close();
        //对字节数组Base64编码
        //BASE64Encoder encoder = new BASE64Encoder();'
        outStream.toByteArray();
        return Base64.encode(outStream.toByteArray());//返回Base64编码过的字节数组字符串
*/    }
    
    /**
     * 将网络图片编码为base64
     *
     * @param netPath
     * @return
     */
    public static String encodeNetImageToBase64(String netPath) throws Exception {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        System.out.println("图片的路径为:" + netPath);
        URL url = new URL(netPath);
        //打开链接
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            //设置请求方式为"GET"
            conn.setRequestMethod("GET");
            //超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);
            //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            //创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while ((len = inStream.read(buffer)) != -1) {
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            //关闭输入流
            inStream.close();
            byte[] data = outStream.toByteArray();
            //对字节数组Base64编码
            BASE64Encoder encoder = new BASE64Encoder();
            String a = encoder.encode(data);
            a = a.replaceAll("\r","");
            String base64 = a.replaceAll("\n","");
            System.out.println(base64);
            return base64;//返回Base64编码过的字节数组字符串
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("图片转换失败");
        }
    }
}
