package com.xywg.admin.task;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xywg.admin.core.util.FTPUtils;
import com.xywg.admin.core.util.ImageUtils;
import com.xywg.admin.modular.device.dao.DeviceRecordMapper;
import com.xywg.admin.modular.device.service.IDeviceRecordService;
import com.xywg.admin.modular.smz.utils.FTPClientUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时任务，用来处理实名制拉取的政务中心数据的时间水印问题，平常不开启
 */
@JobHandler(value="TestJobHandler")
@Component
public class TestJobHandler extends IJobHandler {

    private Logger logger = Logger.getLogger(TestJobHandler.class);

    @Autowired
    private IDeviceRecordService deviceRecordService;

    @Override
    public ReturnT<String> execute(String s)throws Exception {
        //连接ftp 暂定生产
        FTPClient ftpClient = FTPClientUtil.connectFtp("192.168.10.124", 21, "jack", "qwer1234");
        //连接ftp 暂定本地
       FTPClient localFtp = FTPClientUtil.connectFtp("192.168.1.209", 21, "labor", "123456");
        //获取需要处理的数据
        Map<String, Object> params = new HashMap<>();
        params.put("orgCode", "9132068113885448XD");
        params.put("time", "2019-05");
        int maxRow = deviceRecordService.getAllForTestCount(params); //最大条数
        if(maxRow>0) {
            int maxI = maxRow%200>0?(maxRow/200) + 1 : maxRow/200;
            for(int i=1; i<=maxI; i++) {
                params.put("startIndex", 0);
                List<Map<String, Object>> list = deviceRecordService.getAllForTest(params);
                System.out.println("当前i=="+i);
                final int[] bb = new int[]{i==1?0:(i-1)*200};
                final List<Map<String,Object>> mList = new ArrayList<>();
                list.forEach(model -> {
                    //开始处理数据
                    try{
                        //获取photo进行处理
                        String photo = model.get("photo").toString();
                        if(StringUtil.isNotBlank(photo)) {
                            String filePath = photo.substring(0, photo.lastIndexOf("/"));
                            String fileName = photo.substring(photo.lastIndexOf("/")+1, photo.length());
                            if(filePath.startsWith("/")) {
                                filePath = filePath.substring(1, filePath.length());
                            }
                            if(filePath.endsWith("/")) {
                                filePath = filePath.substring(0, filePath.length()-1);
                            }
                            //处理实名制路劲209   201903
                            filePath = filePath.replaceAll("records", "201905");
                            //String[] rt=ftpClient.doCommandAsStrings("pwd","")
                            if(localFtp.changeWorkingDirectory(filePath)) {
                                FTPFile[] files = localFtp.listFiles(fileName);
                                if(null != files && files.length > 0) {
                                    FTPFile file = files[0];
                                    if(file.getSize() > 0) {
                                        InputStream is = localFtp.retrieveFileStream(fileName);
                                        //图片压缩的demo ImageUtils.compressImage(is, file.getSize());
                                        //图片加水印的demo
                                        byte[] b = ImageUtils.addWatermark(is, model.get("recordTime").toString(), null,0.7f);
                                        is.close();
                                        localFtp.completePendingCommand();
                                        //修改完成之后回传到10.124
                                        //构建上传到劳务痛的文件夹  201903/20190302/08/1d5304be1.jpg
                                        //lwtgb_smz/2019/04/24/14/34/1debdfffa47a4f439d2a8efd6d9129be.jpg
                                        filePath = "lwtgb_smz/"+filePath.substring(7, 11)+"/"+filePath.substring(11, 13)+"/"+filePath.substring(13, 18);
                                        //创建文件夹并上传
                                        FTPClientUtil.createDirecroty(filePath, ftpClient);
                                        FTPClientUtil.uploadFile(new ByteArrayInputStream(b),
                                                new String((fileName).getBytes("utf-8"),"iso-8859-1"),ftpClient);
                                        System.out.println("ok=="+bb[0]);
                                        //完成之后开始修改
                                        Map<String, Object> mm = new HashMap<>();
                                        mm.put("id", Integer.parseInt(model.get("id").toString()));
                                        mm.put("photo", "/"+filePath+"/"+fileName);
                                        mList.add(mm);
                                    }
                                }
                                //切回根目录
                                //localFtp.changeWorkingDirectory("/home/labor");
                                localFtp.changeWorkingDirectory("/");
                                ftpClient.changeWorkingDirectory("/");
                                bb[0] = bb[0]+1;
                            }
                        }
                    }catch (Exception e) {
                        System.out.println("转换失败"+model.get("photo").toString());
                        logger.error("转换失败"+model.get("photo").toString());
                        try{
                            ftpClient.changeWorkingDirectory("/");
                            localFtp.changeWorkingDirectory("/");
                            //localFtp.changeWorkingDirectory("/home/labor");
                        }catch (IOException el) {
                            el.printStackTrace();
                        }
                        System.out.println(model.get("id")+"=====path=="+model.get("photo"));
                        e.printStackTrace();
                    }
                });
                //修改到库
                if(null != mList && mList.size()>0) {
                    deviceRecordService.updatePic(mList);
                }
                mList.clear();  //清空
                System.out.println("修改完成");
            }
        }
        return SUCCESS;
    }

    public static void main(String[] args) {
        String a = "201903/20190302/08/1d5304be1.jpg";
        System.out.println(a.substring(7, 11)+"/"+a.substring(11, 13)+"/"+a.substring(13, 18));
    }
}
