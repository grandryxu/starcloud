package com.xywg.admin.core.util;


import java.io.*;
import java.util.UUID;

/**
 * wkhtmltopdf工具类
 *
 * 约定：
 *      1. 插件安装位置，在Windows系统中将插件安装在D盘根目录下（D:/）, 在Linux系统中将插件安装在opt目录下（/opt）
 *
 * 注意：
 *      1. wkhtmltopdf的Linux版本中，解压后，默认的文件名为"wkhtmltox"，为了统一起见，一律将解压后的文件名，重命名为"wkhtmltopdf"（命令：mv wkhtmltox wkhtmltopdf）
 *
 * Created by kagome on 2016/7/26.
 */
public class CustomWKHtmlToPdfUtil {
    // 临时目录的路径
    public static final String TEMP_DIR_PATH = "wordTemporary/";

/*    static {
        // 生成临时目录
        new File(TEMP_DIR_PATH).mkdirs();
    }*/

    /**
     * 将HTML文件内容输出为PDF文件
     *
     * @param htmlFilePath HTML文件路径
     * @param pdfFilePath  PDF文件路径
     * @return pdfFtp路
     */
    public static String htmlToPdf(String htmlFilePath, String pdfFilePath) {
        try {
            Process process = Runtime.getRuntime().exec(getCommand(htmlFilePath, pdfFilePath));
            new Thread(new ClearBufferThread(process.getInputStream())).start();
            new Thread(new ClearBufferThread(process.getErrorStream())).start();
            process.waitFor();
            //生成文件删除
            new File(htmlFilePath).delete();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pdfFilePath;
    }


    /**
     * 将HTML字符串转换为HTML文件
     *
     * @param htmlStr HTML字符串
     * @return HTML文件的绝对路径
     */
    public static String strToHtmlFile(String htmlStr) {
        OutputStream outputStream = null;
        try {
            if(!new File(TEMP_DIR_PATH).isDirectory()){
                new File(TEMP_DIR_PATH).mkdirs();
            }
            String htmlFilePath = TEMP_DIR_PATH + UUID.randomUUID().toString() + ".html";
            outputStream = new FileOutputStream(htmlFilePath);
            outputStream.write(htmlStr.getBytes("UTF-8"));
            return htmlFilePath;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                    outputStream = null;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 获得HTML转PDF的命令语句
     *
     * @param htmlFilePath HTML文件路径
     * @param pdfFilePath  PDF文件路径
     * @return HTML转PDF的命令语句
     */
    private static String getCommand(String htmlFilePath, String pdfFilePath) {
        String osName = System.getProperty("os.name");
        String wkhtmltopdfPath = findExecutable();
        // Windows
        if (osName.startsWith("Windows")) {
            return String.format(wkhtmltopdfPath+" %s %s", htmlFilePath, pdfFilePath);
        }
        // Linux
        else {
            return String.format(wkhtmltopdfPath+" %s %s", htmlFilePath, pdfFilePath);
        }
    }

    public static String findExecutable() {
        String text = "";
        try {
            String osname = System.getProperty("os.name").toLowerCase();

            String cmd = osname.contains("windows") ? "where wkhtmltopdf" : "which wkhtmltopdf";

            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();

            BufferedReader bReader=new BufferedReader(new InputStreamReader(p.getInputStream(),"gbk"));
            String line=null;
            while((line=bReader.readLine())!=null){
                text += line;
            }
            if (text.isEmpty()){
                throw new RuntimeException("wkhtmltopdf command was not found in your classpath. " +
                        "Verify its installation or initialize wrapper configurations with correct path/to/wkhtmltopdf");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return text;
    }

    public static void checkWkHtmlToPdfServer() {
        String text = "";
        try {
            String osname = System.getProperty("os.name").toLowerCase();

            String cmd = osname.contains("windows") ? "where wkhtmltopdf" : "which wkhtmltopdf";

            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();

            BufferedReader bReader=new BufferedReader(new InputStreamReader(p.getInputStream(),"gbk"));
            String line=null;
            while((line=bReader.readLine())!=null){
                text += line;
            }
            if (text.isEmpty()){
                System.err.println("wkhtmltopdf command was not found in your classpath. " +
                        "Verify its installation or initialize wrapper configurations with correct path/to/wkhtmltopdf");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}