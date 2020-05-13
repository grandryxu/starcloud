package com.xywg.admin.modular.smz.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FTPClientUtil {
	
	private static Logger log = LoggerFactory.getLogger(FTPClientUtil.class);
	
	/** 
	* @Title: downloadFile 
	* @Description: 下载图片
	* @author CHENCHEN
	* @param @param response
	* @param @param ftpClient
	* @param @param remotePath
	* @param @throws IOException
	* @return void
	* @date 2018年1月16日
	* @throws 
	*/
	public static void downloadFile(OutputStream outputStream, FTPClient ftpClient, String remotePath) throws IOException {
//		response.setContentType("image/jpeg");
		ftpClient.setBufferSize(1024);
		// 设置文件类型（二进制）
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		ftpClient.retrieveFile(remotePath, outputStream);
	}
	
	/** 
	* @Title: uploadFile 
	* @Description: 上传文件
	* @author CHENCHEN
	* @param @param directory
	* @param @param inputStream
	* @param @param fileName
	* @param @param ftpClient
	* @param @throws IOException
	* @return void
	* @date 2018年1月16日
	* @throws 
	*/
	public static void uploadFile(String directory, InputStream inputStream, String fileName, FTPClient ftpClient) throws IOException {
		ftpClient.makeDirectory(directory);
		ftpClient.changeWorkingDirectory(directory); 
		ftpClient.storeFile(fileName, inputStream);
	}
	
	/**
	 * 
	 * @description 
	 * @author chupp
	 * @date 2018年6月27日
	 * @param inputStream
	 * @param fileName
	 * @param ftpClient
	 * @throws IOException
	 *
	 */
	public static void uploadFile(InputStream inputStream, String fileName, FTPClient ftpClient) {
		try {
			ftpClient.storeFile(fileName, inputStream);
		}catch(Exception e) {
			log.error(e.getMessage());
		}finally {
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error(e.getMessage());
					// TODO Auto-generated catch block
				}
				inputStream = null;
			}
		}
		
	}
	
	/** 
	* @Title: connectFtp 
	* @Description: 连接ftp
	* @author CHENCHEN
	* @param @param host
	* @param @param port
	* @param @param username
	* @param @param password
	* @param @return
	* @param @throws SocketException
	* @param @throws IOException
	* @return FTPClient
	* @date 2018年1月16日
	* @throws 
	*/
	public static FTPClient connectFtp(String host, int port, String username, String password) throws SocketException, IOException {
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect(host, port);
		// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
		ftpClient.login(username, password);// 登录
		int reply = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftpClient.disconnect();
		}
		ftpClient.setBufferSize(1024);
		// 设置文件类型（二进制）
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		ftpClient.enterLocalPassiveMode();
		return ftpClient;
	}
	
	/**
	 * 
	 * @description 
	 * @author chupp
	 * @date 2018年6月28日
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 * @param storeFilePath
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 *
	 */
	public static FTPClient connectFtp(String host, int port, String username, String password, String storeFilePath) throws SocketException, IOException {
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect(host, port);
		// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
		ftpClient.login(username, password);// 登录
		int reply = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftpClient.disconnect();
		}
		ftpClient.setBufferSize(1024);
		// 设置文件类型（二进制）
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		ftpClient.enterLocalPassiveMode();
		// 切换到上传目录
		if (!ftpClient.changeWorkingDirectory(storeFilePath)) {
			// 如果目录不存在创建目录
			String[] dirs = storeFilePath.split("/");
			for (String dir : dirs) {
				if (null == dir || dir.isEmpty()) {
					continue;
				}
				if (!ftpClient.changeWorkingDirectory(dir)) {
					if (ftpClient.makeDirectory(dir)) {
						ftpClient.changeWorkingDirectory(dir);
					}
				}
			}
		}
		return ftpClient;
	}
	
	/** 
	* @Title: disconnect 
	* @Description: 断开ftp连接
	* @author CHENCHEN
	* @param @param ftpClient
	* @param @throws IOException
	* @return void
	* @date 2018年1月16日
	* @throws 
	*/
	public static void disconnect(FTPClient ftpClient) throws IOException {
		if(ftpClient != null && ftpClient.isConnected()) {
			ftpClient.disconnect();
		}
	}
	
//	public static void main(String[] args) {
//		FTPClient ftpClient = new FTPClient();
//		try {
//			int reply;
//			ftpClient.connect("192.168.1.124", 21);
//			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
//			ftpClient.login("alice", "P@ssw0rd");// 登录
//			reply = ftpClient.getReplyCode();
//			if (!FTPReply.isPositiveCompletion(reply)) {
//				ftpClient.disconnect();
//			}
////			response.setContentType("image/jpeg");
//
//			ftpClient.setBufferSize(1024);
//			// 设置文件类型（二进制）
//			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
////			ftpClient.retrieveFile(remotePath, response.getOutputStream());
//			File file = new File("d://teset.txt");
//			FileOutputStream fos = new FileOutputStream(file);
//			ftpClient.retrieveFile("/upload/picture/timg.jpg", fos);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (ftpClient.isConnected()) {
//				try {
//					ftpClient.disconnect();
//				} catch (IOException ioe) {
//				}
//			}
//		}
//	}

	/***
	 * 图片返回byte
	 *
	 * @param ftp
	 * @param path
	 * @return
	 * @throws IOException
	 *             byte[]
	 * @author duanfen
	 */
	public static byte[] readFile(FTPClient ftp, String path) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		FTPClientUtil.downloadFile(bos, ftp, path);
		return bos.toByteArray();
	}

	/**
	 * 创建文件夹
	 * @param remote
	 * @return
	 * @throws IOException
	 */
	public static boolean createDirecroty(String remote, FTPClient ftpClient) throws IOException {
		boolean success = true;
		String directory = remote + "/";
		// 如果远程目录不存在，则递归创建远程服务器目录
		if (!directory.equalsIgnoreCase("/") && !ftpClient.changeWorkingDirectory(new String(directory))) {
			int start = 0;
			int end = 0;
			if (directory.startsWith("/")) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf("/", start);
			String path = "";
			String paths = "";
			while (true) {
				String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
				path = path + "/" + subDirectory;
				if (!existFile(path, ftpClient)) {
					if (makeDirectory(subDirectory, ftpClient)) {
						ftpClient.changeWorkingDirectory(subDirectory);
					} else {
						System.out.println("创建目录[" + subDirectory + "]失败");
						ftpClient.changeWorkingDirectory(subDirectory);
					}
				} else {
					ftpClient.changeWorkingDirectory(subDirectory);
				}

				paths = paths + "/" + subDirectory;
				start = end + 1;
				end = directory.indexOf("/", start);
				// 检查所有目录是否创建完毕
				if (end <= start) {
					break;
				}
			}
		}
		return success;
	}

	/**
	 * 创建单文件夹
	 * @param dir
	 * @param ftpClient
	 * @return
	 */
	public static boolean makeDirectory(String dir, FTPClient ftpClient) {
		boolean flag = true;
		try {
			flag = ftpClient.makeDirectory(dir);
			if (flag) {
				System.out.println("创建文件夹" + dir + " 成功！");

			} else {
				System.out.println("创建文件夹" + dir + " 失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 判断是否存在
	 * @param path
	 * @param ftpClient
	 * @return
	 * @throws IOException
	 */
	public static boolean existFile(String path, FTPClient ftpClient) throws IOException {
		boolean flag = false;
		FTPFile[] ftpFileArr = ftpClient.listFiles(path);
		if (ftpFileArr.length > 0) {
			flag = true;
		}
		return flag;
	}
}
