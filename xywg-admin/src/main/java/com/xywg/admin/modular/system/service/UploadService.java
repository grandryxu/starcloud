package com.xywg.admin.modular.system.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

/**
 * @author hjy
 * @date 2018/5/30
 */
public interface UploadService {

    /**
     *
     * @param file
     * @param needCompress  是否需要压缩
     * @return
     */
    String upload(MultipartFile file,Integer needCompress);

    String upload(MultipartFile file);

    /**
     * 单个文件上传到远程服务器
     * @param file
     * @return 存储地址
     */
    String saveFileToFTP(MultipartFile file);

    /**
     * 单个文件上传到远程服务器
     * @param in
     * @return 存储地址
     */
    String saveFileToFTP(MultipartFile file,InputStream in);

    /**
     * 单个文件上传到远程服务器
     * @param file
     * @return 存储地址
     */
    String saveFileToFTP(File file);

    /**
     * 上传文件
     * @param file
     * @param fileType  允许上传的文件格式后缀，逗号分隔
     * @param maxSize
     * @return
     */
    Map<String,String> fileHandle(MultipartFile file,String fileType,Integer maxSize);

}
