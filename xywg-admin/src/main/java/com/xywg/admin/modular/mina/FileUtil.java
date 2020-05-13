package com.xywg.admin.modular.mina;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xywg.admin.modular.message.service.impl.MessageServiceImpl;

/**
 * 
 * 文件处理类
 * 
 * @author duanfen
 * @date 2017年11月28日 下午4:16:21
 *
 */
public class FileUtil {
	private static Logger log = LoggerFactory.getLogger(FileUtil.class);
	/**
	 * 功能：Java读取txt文件的内容 步骤：1：先获得文件句柄 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
	 * 3：读取到输入流后，需要读取生成字节流 4：一行一行的输出。readline()。 备注：需要考虑的是异常情况
	 * 
	 * @param filePath
	 */
	public static String readTxtFile(String filePath) {
		StringBuilder buffer = new StringBuilder();
		InputStreamReader read = null;
		BufferedReader bufferedReader = null;
		try {
			String encoding = "UTF8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					buffer.append(lineTxt);
				}
//				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			log.error(e.getMessage());
		} finally {
			if(bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.error(e.getMessage());
				}
				bufferedReader = null;
			}
			if(read != null) {
				try {
					read.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.error(e.getMessage());
				}
				read = null;
			}
		}

		return buffer.toString();
	}
	
	public static final String SEPARATOR = "/";
}
