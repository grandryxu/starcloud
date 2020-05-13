package com.xywg.admin.modular.company.controller;

import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.modular.company.utils.ServletsUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * Created by wangcw on 2018/5/23.
 */
public class ExcelDemoController extends BaseController {
	private static  Logger log = LoggerFactory.getLogger(ExcelDemoController.class);
    @RequestMapping(value = "/downloadReport", method = RequestMethod.GET)
    @ResponseBody
    public void downloadFile(HttpServletResponse res, HttpServletRequest req) {
        String djxh = req.getParameter("djxh")+"";
        int year = Integer.parseInt(req.getParameter("year"));

        //构建下载文件时的文件名
//        String filename = filepath.substring(filepath.lastIndexOf("/") + 1);
        boolean isMSIE = ServletsUtils.isMSBrowser(req);

//        BufferedInputStream bis = null;
        OutputStream os = null;
        String fileName = "";
        try {
            if (isMSIE) {
                //IE浏览器的乱码问题解决
                fileName = URLEncoder.encode("try", "UTF-8");
            } else {
                //万能乱码问题解决
                fileName = new String("try".getBytes("UTF-8"), "ISO-8859-1");
            }
            res.setContentType("application/vnd.ms-excel");
            //res.setHeader("Content-disposition", "attachment;filename=\"" + 文件名 + "\"+.xlsx");
            os = res.getOutputStream();
            //ExcelUtils.getInstance().exportObjects2Excel(/**数据列表*/,ExportExcel.class,true,os);

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
//            if (bis != null) {
//                try {
//                    bis.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }
}

