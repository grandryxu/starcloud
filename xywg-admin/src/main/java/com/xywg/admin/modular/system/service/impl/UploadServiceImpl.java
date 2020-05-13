package com.xywg.admin.modular.system.service.impl;

import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.util.DateUtils;
import com.xywg.admin.core.util.FTPUtils;
import com.xywg.admin.modular.system.service.UploadService;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.MultipartConfigElement;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author hjy
 * @date 2018/5/30
 */
@Service
@SuppressWarnings("all")
public class UploadServiceImpl implements UploadService {
    /**
     * @param file
     * @param needCompress 是否需要压缩
     * @return
     */
    @Override
    public String upload(MultipartFile file, Integer needCompress) {
        BufferedOutputStream stream = null;

        String uploadFilePath = file.getOriginalFilename();
        // 截取上传文件的后缀
        String uploadFileSuffix = uploadFilePath.substring(uploadFilePath.lastIndexOf('.') + 1, uploadFilePath.length());
        //重新定义文件名
        String fileName = UUID.randomUUID().toString() + "." + uploadFileSuffix;
        //定义存储路径
        String path = "xywg-rest/xywgFile/" + DateUtils.getDate("yyyyMMdd") + "/" + fileName;
        File newfiles = new File(path);

        /*if (needCompress == 0) {*/
        try {
            if (!newfiles.getParentFile().exists()) {
                newfiles.getParentFile().mkdirs();
                newfiles.createNewFile();
            }
            stream = new BufferedOutputStream(new FileOutputStream(newfiles));
            byte[] bytes = file.getBytes();
            stream.write(bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*} */
        return path;
    }

    /**
     * 保存文件到远程静态资源服务器
     *
     * @return 访问地址
     */
    @Override
    public String saveFileToFTP(File file) {

        String uploadFilePath = file.getName();
        // 截取上传文件的后缀
        String uploadFileSuffix = uploadFilePath.substring(uploadFilePath.lastIndexOf('.') + 1, uploadFilePath.length());
        //重新定义文件名
        String originalFilename = file.getName();
        String fileName = originalFilename.substring(0, originalFilename.lastIndexOf(".")) + System.currentTimeMillis() + "." + uploadFileSuffix;
        String basePath = "/" + DateUtils.getDate("yyyy-MM-dd") + "/face/";
        String path = basePath + fileName;
        Boolean flag = false;
        try {
            flag = FTPUtils.uploadFile(basePath, fileName, new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (flag) {
            return path;
        } else {
            throw new XywgException(600, "上传文件失败!");
        }
    }

    /**
     * @param file
     * @return
     */
    @Override
    public String upload(MultipartFile file) {
        BufferedOutputStream stream = null;
        String uploadFilePath = file.getOriginalFilename();
        // 截取上传文件的后缀
        String uploadFileSuffix = uploadFilePath.substring(uploadFilePath.lastIndexOf('.') + 1, uploadFilePath.length());
        //重新定义文件名
        String fileName = UUID.randomUUID().toString() + "." + uploadFileSuffix;
        //定义存储路径
        String path = "xywgFile/" + DateUtils.getDate("yyyyMMdd") + "/" + fileName;
        File newfiles = new File(path);
        try {
            if (!newfiles.getParentFile().exists()) {
                newfiles.getParentFile().mkdirs();
                newfiles.createNewFile();
            }
            stream = new BufferedOutputStream(new FileOutputStream(newfiles));
            byte[] bytes = file.getBytes();
            stream.write(bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return path;
    }

    /**
     * 保存文件到远程静态资源服务器
     *
     * @return 访问地址
     */
    @Override
    public String saveFileToFTP(MultipartFile file) {

        String uploadFilePath = file.getOriginalFilename();
        // 截取上传文件的后缀
        String uploadFileSuffix = uploadFilePath.substring(uploadFilePath.lastIndexOf('.') + 1, uploadFilePath.length());
        //重新定义文件名
        String fileName = "" + UUID.randomUUID().toString() + "." + uploadFileSuffix;
        String basePath = DateUtils.getDate("yyyy/M/d") + "/face/";
        String path = basePath + fileName;
        Boolean flag = false;
        try {

            flag = FTPUtils.uploadFile(basePath, fileName, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (flag) {
            return path;
        } else {
            throw new XywgException(600, "上传文件失败!");
        }
    }

    /**
     * 保存文件到远程静态资源服务器
     *
     * @return 访问地址
     */
    @Override
    public String saveFileToFTP(MultipartFile file, InputStream in) {

        String uploadFilePath = file.getOriginalFilename();
        // 截取上传文件的后缀
        String uploadFileSuffix = uploadFilePath.substring(uploadFilePath.lastIndexOf('.') + 1, uploadFilePath.length());
        //重新定义文件名
        String fileName = "" + UUID.randomUUID().toString() + "." + uploadFileSuffix;
        String basePath = DateUtils.getDate("yyyy/M/d") + "/face/";
        String path = basePath + fileName;
        Boolean flag = false;
        flag = FTPUtils.uploadFile(basePath, fileName, in);
        if (flag) {
            return path;
        } else {
            throw new XywgException(600, "上传文件失败!");
        }
    }


    @Override
    public Map<String, String> fileHandle(MultipartFile file, String fileType, Integer maxSize) {
        Map<String, String> resMap = new HashMap<>();

        //允许上传的文件类型
        //String fileType = "gif,png,bmp,jpeg,jpg,tiff,pcx,tga,exif,fpx,svg,psd,cdr,pcd,dxf,ufo,eps,ai,raw,wmf,webp";
        //允许上传的文件最大大小(50M,单位为byte)
        // int maxSize = 10 * 1024 * 1024;
        if (file.getSize() > maxSize) {
            resMap.put("code", "600");
            resMap.put("message", "文件:" + file.getOriginalFilename() + "太大,文件最大为" + ((maxSize / 1024) / 1024) + "M!");
            return resMap;
        }
        String[] types = fileType.split(",");

        String uploadFilePath = file.getOriginalFilename();
        // 截取上传文件的后缀
        String uploadFileSuffix = uploadFilePath.substring(uploadFilePath.lastIndexOf('.') + 1, uploadFilePath.length());
        boolean flagType = Arrays.asList(types).contains(uploadFileSuffix.toLowerCase());
        if (flagType == false) {
            resMap.put("code", "600");
            resMap.put("message", "不支持的文件格式！");
            return resMap;
        }

        //重新定义文件名
        String fileName = "" + UUID.randomUUID().toString() + "." + uploadFileSuffix;
        String basePath = DateUtils.getDate("yyyy/M/d") + "/face/";
        String path = basePath + fileName;
        Boolean flagFtp = false;
        try {
            flagFtp = FTPUtils.uploadFile(basePath, fileName, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (flagFtp) {
            resMap.put("code", "200");
            resMap.put("path", path);
            resMap.put("message", "上传成功!");
            return resMap;
        } else {
            resMap.put("code", "600");
            resMap.put("message", "文件:" + file.getOriginalFilename() + "上传失败,请稍后再试!");
            return resMap;
        }
    }

    /**
     * 文件上传临时路径
     */
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //判断路径是否存在

        File targetFile = new File("lxtmp");

        if (!targetFile.exists()) {

            targetFile.mkdirs();

        }
        factory.setLocation(targetFile.getAbsolutePath());
        return factory.createMultipartConfig();
    }

/*    //显示声明CommonsMultipartResolver为mutipartResolver
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setResolveLazily(true);//resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
        resolver.setMaxInMemorySize(1);
        resolver.setMaxUploadSize(50*1024*1024);//上传文件大小 50M 50*1024*1024
        return resolver;
    }*/

}
