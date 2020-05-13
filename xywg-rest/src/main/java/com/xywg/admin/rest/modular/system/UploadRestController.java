package com.xywg.admin.rest.modular.system;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.xywg.admin.core.util.FTPUtils;
import com.xywg.admin.rest.common.persistence.model.R;

import cn.hutool.core.io.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author hjy
 * @date 2018/5/30
 */
@RestController
@RequestMapping("file")
@Api(description = "文件上传")
public class UploadRestController {
	private static  Logger logger = LoggerFactory.getLogger(UploadRestController.class);
    @ApiOperation(value = "工人端文件上传", notes = "工人端文件上传")
    @RequestMapping(value = "/springUpload", method = RequestMethod.POST)
    @ResponseBody
    public R uploadImg(HttpServletRequest request, Integer needCompress) throws IOException {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        List<Map> mapList = new ArrayList<>();
        for (MultipartFile file : files){
            String uploadFilePath = file.getOriginalFilename();
            // 截取上传文件的后缀
            String uploadFileSuffix = uploadFilePath.substring(uploadFilePath.lastIndexOf('.') + 1, uploadFilePath.length());
            //重新定义文件名
            String fileName = ""+UUID.randomUUID().toString() + "." + uploadFileSuffix;
            String path = getFtpImagePath();
            String iconPath = getFtpIconPath();
            Map pathMap = new HashedMap();
            pathMap.put("path",path+fileName);
            InputStream iconInput = null;
            try {
                /**
                 * 需要被压缩
                 */
                if( needCompress==1){
                    pathMap.put("icon",iconPath+fileName);
//                    String removeFilePath = FTPUtils.saveFileFromInputStream(file.getInputStream(),path);
                    String removeFilePath = FTPUtils.saveFileFromInputStream(file.getInputStream(), getLocalImagePath() + fileName);
                    FTPUtils.zoomImage(file.getInputStream(),removeFilePath,200,200);
                    iconInput = new FileInputStream(removeFilePath);
                    if(FTPUtils.uploadFile(path,fileName,file.getInputStream())&&
                            FTPUtils.uploadFile(iconPath,fileName,iconInput)){
                        mapList.add(pathMap);
                    }
                    FileUtil.del(path);
                    //可以直接删除临时文件
                }else if(FTPUtils.uploadFile(path,fileName,file.getInputStream())){
                    mapList.add(pathMap);
                }

            } catch (IOException e) {
                logger.info("IOException", e);
            } catch (Exception e) {
                logger.info("Exception", e);
            } finally{
                if(iconInput != null){
                    iconInput.close();
                    iconInput = null;
                }
            }
        }


        return R.ok().put("data",mapList);
    }

    private String getLocalImagePath(){
      Calendar now = Calendar.getInstance();
      String path = now.get(Calendar.YEAR) + "/" + (now.get(Calendar.MONTH) + 1) + "/" + now.get(Calendar.DAY_OF_MONTH) + "/";
      path = path + "face/";
      return path;
    }

    /**
     * 生成图片路径
     * @return
     */
    private String getFtpImagePath(){
        Calendar now = Calendar.getInstance();
        String path = now.get(Calendar.YEAR)+"/"+(now.get(Calendar.MONTH) + 1) +"/"+now.get(Calendar.DAY_OF_MONTH)+"/";
        path+="face/";
        return path;
    }

    /**
     * 生成ICON图片路径
     * @return
     */
    private String getFtpIconPath(){
        Calendar now = Calendar.getInstance();
        String path = "/icon/"+now.get(Calendar.YEAR)+"/"+(now.get(Calendar.MONTH) + 1) +"/"+now.get(Calendar.DAY_OF_MONTH)+"/";
        path+="face/";
        return path;
    }

    public static void main(String[] args) {
        UploadRestController uploadRestController = new UploadRestController();
        String path = uploadRestController.getFtpImagePath();
        System.out.println(path);


    }
}
