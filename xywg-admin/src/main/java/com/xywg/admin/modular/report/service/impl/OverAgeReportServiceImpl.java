package com.xywg.admin.modular.report.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;
import com.xywg.admin.core.excel.ExcelUtils;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.util.DateUtils;
import com.xywg.admin.modular.company.utils.ServletsUtils;
import com.xywg.admin.modular.report.dao.OverAgeReportMapper;
import com.xywg.admin.modular.report.dao.WorkKindReportMapper;
import com.xywg.admin.modular.report.model.DeviceReportExportDmo;
import com.xywg.admin.modular.report.model.DeviceReportExportDto;
import com.xywg.admin.modular.report.model.WorkKindReport;
import com.xywg.admin.modular.report.model.WorkerOverAgeReport;
import com.xywg.admin.modular.report.service.IOverAgeReportService;
import com.xywg.admin.modular.report.service.WorkKindReportService;
import com.xywg.admin.modular.system.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 超龄统计实现类
 *
 * @author duanfen
 * @date 2019/6/11
 */
@Service
public class OverAgeReportServiceImpl extends ServiceImpl<OverAgeReportMapper, WorkerOverAgeReport> implements IOverAgeReportService {
    @Autowired
    private IDeptService deptService;
    @Autowired
    private OverAgeReportMapper overAgeReportMapper;
    
    @Override
    public List<WorkerOverAgeReport> getWorkerList(Page<WorkerOverAgeReport> page,Map<String, Object> map) {
    	Integer deptId = 0;
    	Object dept = map.get("deptId");
    	if(dept != null) {
    		deptId = Integer.valueOf((String)dept);
    	}else {
    		deptId = ShiroKit.getUser().getDeptId();
    	}
        //获取公司以及子公司社会信用代码集合
        List<String> list = deptService.getOrganizationCodeByDeptId(deptId);
        map.put("projectCode", ShiroKit.getSessionAttr("toggleProjectCode"));
        map.put("ocList", list);
        return overAgeReportMapper.getWorkerList(page,map);
    }

	@Override
	public void download(HttpServletResponse res, HttpServletRequest req, Map<String, Object> params) {
		Integer deptId = 0;
    	Object dept = params.get("deptId");
    	if(dept != null) {
    		deptId = Integer.valueOf((String)dept);
    	}else {
    		deptId = ShiroKit.getUser().getDeptId();
    	}
        //获取公司以及子公司社会信用代码集合
        List<String> list = deptService.getOrganizationCodeByDeptId(deptId);
        params.put("projectCode", ShiroKit.getSessionAttr("toggleProjectCode"));
        params.put("ocList", list);
        List<WorkerOverAgeReport> overReports = overAgeReportMapper.getExportWorkerList(params);
        
        //构建下载文件时的文件名
        String fileName = "超龄统计" + DateUtils.getDate("yyyyMMddHHmmss");
        boolean isMSIE = ServletsUtils.isMSBrowser(req);
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
            ExcelUtils.getInstance().exportObjects2Excel(overReports, WorkerOverAgeReport.class, true, os);
            
            
        } catch (Exception e) {
        } finally {
        }
	}

}
