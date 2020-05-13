package com.xywg.admin.modular.longxin.controller;

import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.modular.system.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * ftp上传企业材料
 */
@RequestMapping(value = "/lxftp")
@Controller
public class LxFtpController {

    @Autowired
    private UploadService uploadService;
    
    private final Long myKb = 1024L;

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
    
   

}
