package com.xywg.admin.modular.system.controller;

import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.modular.system.service.BaiduFaceService;
import com.xywg.admin.modular.system.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * ftp上传/百度人脸检测
 */
@RequestMapping(value = "/ftp")
@Controller
public class FtpController {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private BaiduFaceService faceService;
    
    private final Long myKb = 1024L;

    /**
     * 上传文件
     * @param file
     * @return
     */
/*
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object upload(@RequestPart("file")MultipartFile file) {
        ByteArrayOutputStream bo = getByteArrayOutputStream(file);
        try {
            Map<String, Object> returnMap = new HashMap<>();
            if(!file.isEmpty()&& (myKb * 50) < file.getSize()) {
            	returnMap.put("code", 413);
                returnMap.put("message", "请确保图片不大于50kb！");
                return returnMap;
            }
            //活体检测
            if(!faceService.checkFace(file,bo)) {
                returnMap.put("code", 400);
                returnMap.put("message", "请确保是非图片拍照，并且只能有一个人脸在拍照框内");
                return returnMap;
            }
            String fileName = file.getOriginalFilename();
            //重新定义上传路径,保存到FTP 远程静态服务器上

            String path = uploadService.saveFileToFTP(file,new ByteArrayInputStream(bo.toByteArray()));
            returnMap.put("code", 200);
            returnMap.put("fileName", fileName);
            returnMap.put("path", path);
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new XywgException(501, "上传失败");
        }
    }
*/
    /**
     * 上传文件
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object upload(@RequestPart("file")MultipartFile file) {
        try {
            Map<String, Object> returnMap = new HashMap<>();
            if(!file.isEmpty()&& (myKb * 50) < file.getSize()) {
                returnMap.put("code", 413);
                returnMap.put("message", "请确保图片不大于50kb！");
                return returnMap;
            }
            //活体检测
            faceService.checkFace(file);
            String fileName = file.getOriginalFilename();
            //重新定义上传路径,保存到FTP 远程静态服务器上
            String path = uploadService.saveFileToFTP(file);
            returnMap.put("code", 200);
            returnMap.put("fileName", fileName);
            returnMap.put("path", path);
            return returnMap;
        } catch (Exception e) {
            throw new XywgException(501, e.getMessage());
        }
    }

    /**
     * 上传文件
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadIdCard", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadIdCard(@RequestPart("file")MultipartFile file) {
        try {
            Map<String, Object> returnMap = new HashMap<>();
            String fileName = file.getOriginalFilename();
            //重新定义上传路径,保存到FTP 远程静态服务器上
            String path = uploadService.saveFileToFTP(file);
            returnMap.put("code", 200);
            returnMap.put("fileName", fileName);
            returnMap.put("path", path);
            return returnMap;
        } catch (Exception e) {
            throw new XywgException(501, "上传失败");
        }
    }

    /*private ByteArrayOutputStream getByteArrayOutputStream(MultipartFile multipartFile){
        //获取客户端InputStream对象
        InputStream input= null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            input = multipartFile.getInputStream();
            //将InputStream对象转换成ByteArrayOutputStream
            byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > -1 ) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       return byteArrayOutputStream;
    }*/
    /**
     * 火炼检测
     * @param file
     * @return
     */
    @RequestMapping(value = "/checkFace", method = RequestMethod.POST)
    @ResponseBody
    public Object checkFace(@RequestPart("file")MultipartFile file) {
        return faceService.checkFace(file);
    }
}
