package com.xywg.admin.modular.report.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.excel.ExcelUtils;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.util.DateUtils;
import com.xywg.admin.modular.company.utils.ServletsUtils;
import com.xywg.admin.modular.report.dao.PersonJoinReportMapper;
import com.xywg.admin.modular.report.model.PersonJoinReport;
import com.xywg.admin.modular.report.model.dto.PersonJoinReportVo;
import com.xywg.admin.modular.report.service.PersonJoinReportService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 实现类
 * @author wangshibo
 *	2018年6月20日
 * 下午3:47:33
 */
@Service
public class PersonJoinReportServiceImpl extends ServiceImpl<PersonJoinReportMapper, PersonJoinReport> implements PersonJoinReportService {
	private static Logger log = LoggerFactory.getLogger(PersonJoinReportServiceImpl.class);
	@Autowired
    private IDeptService deptService;

	@Override
	public List<PersonJoinReport> getReportList(Page<PersonJoinReport> page, Map<String, Object> map) {
		Integer deptId;
		String now = DateUtils.getDate()+" 00:00:00";
		try{
			deptId = Integer.parseInt(map.get("deptId").toString());
		}catch(NumberFormatException e){
			deptId = 0;
		}
		if (deptId == 0) {
            deptId = ShiroKit.getUser().getDeptId();
        }
	        //获取公司以及子公司社会信用代码集合
	        List<String> list = deptService.getOrganizationCodeByDeptId(deptId);

				List<PersonJoinReport> reportList = baseMapper.getReportList(page,list,now);
				return reportList;
	}

	@Override
	public List<Map<String, Object>> getPersonJoinReportsList(Map<String, Object> params) {
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
		List<Map<String, Object>> personJoinReports = baseMapper.getPersonJoinReportsList(list);
		return personJoinReports;
	}

    @Override
    public void download(HttpServletResponse res, HttpServletRequest req, Map<String, Object> params) {

        List<Map<String, Object>> personJoinReports = this.getPersonJoinReportsList(params);
        //构建下载文件时的文件名
        String fileName = "人员进退场统计" + DateUtils.getDate("yyyyMMddHHmmss");
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
            ExcelUtils.getInstance().exportObjects2Excel(personJoinReports, PersonJoinReportVo.class, true, os);
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
