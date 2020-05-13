package com.xywg.admin.modular.report.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.core.excel.ExcelUtils;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.util.DateUtils;
import com.xywg.admin.modular.company.utils.ServletsUtils;
import com.xywg.admin.modular.report.dao.PayrollReportMapper;
import com.xywg.admin.modular.report.model.PayrollReport;
import com.xywg.admin.modular.report.model.WorkerPayrollDetailReport;
import com.xywg.admin.modular.report.model.dto.PayrollReportDto;
import com.xywg.admin.modular.report.model.dto.PersonJoinReportVo;
import com.xywg.admin.modular.report.service.PayrollReportService;
import com.xywg.admin.modular.system.service.IDeptService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 区域统计项目service实现类
 *
 * @author jingyun_hu
 * @date 2018/6/19
 */
@Service
public class PayrollReportServiceImpl  implements PayrollReportService {
	private static Logger log = LoggerFactory.getLogger(PayrollReportServiceImpl.class);

    @Autowired
    private PayrollReportMapper payrollReportMapper;
    @Autowired
    private IDeptService deptService;

    /**
     * 根据部门id(公司id)查询工资统计列表
     */
    @Override
    public List<PayrollReport> getReportListByDeptId(Page<PayrollReport> page,Integer deptId) {

        if (deptId == null) {
            deptId = ShiroKit.getUser().getDeptId();
        }
        //获取公司以及子公司社会信用代码集合
        List<String> list = deptService.getOrganizationCodeByDeptId(deptId);

        return payrollReportMapper.getReportListByOrganizationCodeList(page,list);
    }


    /**
     * 根据项目编号查询和工人信息 查询工人工资明细
     */
    @Override
    public List<WorkerPayrollDetailReport> getDetailByProjectCodeAndWorkerInfo(Page<WorkerPayrollDetailReport> page, Map<String,Object> map) {
        return payrollReportMapper.getDetailByProjectCodeAndWorkerInfo(page,map);
    }

    @Override
    public List<Map<String, Object>> getExportList(Map<String, Object> params) {
        Integer deptId;
        try{
            deptId = Integer.parseInt(params.get("deptId").toString());
        }catch(NumberFormatException e){
            deptId = 0;
        }
        if (deptId == 0) {
            deptId = ShiroKit.getUser().getDeptId();
        }
        //获取公司以及子公司社会信用代码集合
        List<String> list = deptService.getOrganizationCodeByDeptId(deptId);
        List<Map<String,Object>> payList=payrollReportMapper.getExportList(list);
        return payList;
    }

    /**
     *@Description:
     *@Author xieshuaishuai
     *@Date 2018/7/5 20:23
     */
    @Override
    public void export(HttpServletResponse res, HttpServletRequest req, Map<String, Object> params) {
        List<Map<String,Object>> list=this.getExportList(params);
        //构建下载文件时的文件名
        String fileName = "工资统计" + DateUtils.getDate("yyyyMMddHHmmss");
        boolean isMSIE = ServletsUtils.isMSBrowser(req);
//        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            if (isMSIE) {
                //IE浏览器的乱码问题解决
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                //万能乱码问题解决
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }
            res.setContentType("application/vnd.ms-excel");
            res.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"+.xlsx");
            os = res.getOutputStream();
            ExcelUtils.getInstance().exportObjects2Excel(list, PayrollReportDto.class, true, os);
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
