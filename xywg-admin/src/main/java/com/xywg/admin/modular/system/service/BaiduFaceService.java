package com.xywg.admin.modular.system.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;


/**
 * 百度人脸service
 * @author hh cao
 * @date 2018-03-23
 */
public interface BaiduFaceService {
    /**
     * 活人检测
     * @param file
     * @param bo
     * @return
     */
  //  boolean checkFace(MultipartFile file, ByteArrayOutputStream bo);
    boolean checkFace(MultipartFile file);
}
