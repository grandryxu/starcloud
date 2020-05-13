package com.xywg.admin.modular.report.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;
import com.xywg.admin.core.excel.ExcelUtils;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.util.DateUtils;
import com.xywg.admin.modular.company.dao.SubContractorMapper;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.company.utils.ServletsUtils;
import com.xywg.admin.modular.device.dao.DeviceRecordMapper;
import com.xywg.admin.modular.project.dao.ProjectMasterMapper;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.report.controller.IWorkerReportController;
import com.xywg.admin.modular.report.dao.DeviceReportMapper;
import com.xywg.admin.modular.report.model.DeviceReport;
import com.xywg.admin.modular.report.model.DeviceReportExport;
import com.xywg.admin.modular.report.model.DeviceReportExportDmo;
import com.xywg.admin.modular.report.model.DeviceReportExportDto;
import com.xywg.admin.modular.report.model.DeviceReportNewExport;
import com.xywg.admin.modular.report.model.DeviceReportVo;
import com.xywg.admin.modular.report.model.dto.DeviceReportDto;
import com.xywg.admin.modular.report.service.IDeviceReportService;
import com.xywg.admin.modular.system.dao.TimeSetMapper;
import com.xywg.admin.modular.system.model.TimeSet;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.team.dao.TeamMasterMapper;
import com.xywg.admin.modular.team.model.TeamMaster;
import com.xywg.admin.modular.worker.dao.WorkerMasterMapper;
import com.xywg.admin.modular.worker.model.WorkerMaster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 考勤统计表 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-16
 */
@Service
public class DeviceReportServiceImpl extends ServiceImpl<DeviceReportMapper, DeviceReport> implements IDeviceReportService {
	private static  Logger log = LoggerFactory.getLogger(DeviceReportServiceImpl.class);
    @Resource
    private DeviceRecordMapper deviceRecordMapper;

    @Resource
    private SubContractorMapper subContractorMapper;

    @Resource
    private ProjectMasterMapper projectMasterMapper;

    @Resource
    private TeamMasterMapper teamMasterMapper;

    @Resource
    private WorkerMasterMapper workerMasterMapper;

    @Autowired
    private IDeptService deptService;
    
    @Resource
    private TimeSetMapper timeSetMapper;

    /**
     * @description 考勤统计
     * @author chupp
     * @date 2018年6月16日
     * @see com.xywg.admin.modular.report.service.IDeviceReportService#deviceReportDeal()
     */
    @Override
    public void deviceReportDeal() {
        //获取所有未处理考勤记录
        List<DeviceReport> list = deviceRecordMapper.getRecordListByIsDeal();
        //依次处理每条考勤记录
        for (DeviceReport deviceReport : list) {
            deviceReport.setTime(deviceReport.getTime().substring(0, 10));
            //按照5属性查询考勤记录,公司ID,项目ID,班组ID,人员ID,时间
            List<DeviceReportDto> drrlist = deviceRecordMapper.getRecordListBy5Fields(deviceReport);
            boolean flag = false;
            //记录是否处理
            for (DeviceReportDto drr : drrlist) {
                if (drr.getDeal() == null || drr.getDeal().equals("") || 1 != Integer.parseInt(drr.getDeal())) flag = true;
            }
            //处理考勤记录
            if (flag) {
                try {
                    this.dealDeviceRecord(deviceReport, drrlist);
                } catch (Exception e) {
                	log.error(e.getMessage());
                }
            }
        }
    }

    /**
     * @param deviceReport
     * @param list
     * @throws ParseException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @description 处理考勤记录
     * @author chupp
     * @date 2018年6月16日
     * @see com.xywg.admin.modular.report.service.IDeviceReportService#dealDeviceRecord(com.xywg.admin.modular.report.model.DeviceReport, java.util.List)
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void dealDeviceRecord(DeviceReport deviceReport, List<DeviceReportDto> list)
            throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        String prefixDate = deviceReport.getTime();
        Long[] longs = this.getProjectInOutTime(deviceReport.getProjectCode(), prefixDate);
        Double total = this.getTimeTotal(list, longs);
        //查询是否存在统计记录
        deviceReport.setTime(prefixDate.substring(0, 7));
        List<DeviceReport> drList = this.baseMapper.getDeviceReportBy5Fields(deviceReport);
        if (drList.size() == 0) {
            //根据最优考勤记录生成统计记录
            DeviceReport dr = new DeviceReport();
            dr.setOrganizationCode(deviceReport.getOrganizationCode());
            dr.setProjectCode(deviceReport.getProjectCode());
            dr.setTeamSysNo(deviceReport.getTeamSysNo());
            dr.setIdCardType(deviceReport.getIdCardType());
            dr.setIdCardNumber(deviceReport.getIdCardNumber());
            dr.setTime(prefixDate.substring(0, 7));
            dr.setTotalDate(total == -1 ? 0 : 1);
            dr.setTotalCount(total == -1 ? 0.0 : total);
            SubContractor company = subContractorMapper.hasSubContractorByOrganizationCode(deviceReport.getOrganizationCode());
            if (company != null) dr.setCompanyName(company.getCompanyName());
            ProjectMaster project = projectMasterMapper.getProjectByProjectCode(deviceReport.getProjectCode());
            if (project != null) dr.setProjectName(project.getProjectName());
            if (deviceReport.getTeamSysNo() != null) {
                TeamMaster mclass = teamMasterMapper.getByteamCode(deviceReport.getTeamSysNo());
                if (mclass != null) dr.setTeamName(mclass.getTeamName());
            }
            WorkerMaster personnel = workerMasterMapper.getWorkerByIdCard(deviceReport.getIdCardNumber(), deviceReport.getIdCardType());
            if (personnel != null) {
                dr.setWorkerName(personnel.getWorkerName());
            }
            String fieldName = prefixDate.substring(8);
            Class clazz = DeviceReport.class;
            Method method = clazz.getMethod("setDay" + fieldName, Double.class);
            method.invoke(dr, total);
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH) + 1;
            int day = c.get(Calendar.DATE);
            String indexDate = "";
            if (Integer.parseInt(prefixDate.substring(0, 4)) == year && Integer.parseInt(prefixDate.substring(5, 7)) == month) {
                if (day <= 9) {
                    indexDate = "0" + day;
                } else {
                    indexDate = "" + day;
                }
            } else {
                indexDate = String.valueOf(32);
            }
            for (int i = 1; i < Integer.parseInt(indexDate); i++) {
                if (i == Integer.parseInt(fieldName)) continue;
                String index = "";
                if (i <= 9) {
                    index = "0" + i;
                } else {
                    index = String.valueOf(i);
                }
                method = clazz.getMethod("setDay" + index, Double.class);
                method.invoke(dr, -1.0);
            }
            //设置总工数
            BigDecimal bg = new BigDecimal(dr.getTotalCount()/ projectMasterMapper.getProjectByProjectCode(deviceReport.getProjectCode()).getWorkTime());
            dr.setTotalTime(bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            //新增考勤统计
            this.baseMapper.saveDeviceReport(dr);
        } else {
            DeviceReport dr = drList.get(0);
            String fieldName = prefixDate.substring(8);
            Class clazz = DeviceReport.class;
            Method method = clazz.getMethod("setDay" + fieldName, Double.class);
            method.invoke(dr, total);
            double totalCount = 0.00;
            int totalDate = 0;
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH) + 1;
            int day = c.get(Calendar.DATE);
            String indexDate = "";
            if (Integer.parseInt(prefixDate.substring(0, 4)) == year && Integer.parseInt(prefixDate.substring(5, 7)) == month) {
                if (day <= 9) {
                    indexDate = "0" + day;
                } else {
                    indexDate = "" + day;
                }
            } else {
                indexDate = String.valueOf(31);
            }
            for (int i = 1; i <= Integer.parseInt(indexDate); i++) {
                String index = "";
                if (i <= 9) {
                    index = "0" + i;
                } else {
                    index = String.valueOf(i);
                }
                method = clazz.getDeclaredMethod("getDay" + index);
                Double d = (Double) method.invoke(dr);
                if(d == null) {
                	method = clazz.getMethod("setDay" + index, Double.class);
                    method.invoke(dr, -1.0);
                }else if(d != -1) {
                	totalCount += d;
                    totalDate += 1;
                }
            }
            dr.setTotalCount(totalCount);
            dr.setTotalDate(totalDate);
            //设置总工数
            BigDecimal bg = new BigDecimal((totalCount + dr.getOverTime())/ projectMasterMapper.getProjectByProjectCode(deviceReport.getProjectCode()).getWorkTime());
            dr.setTotalTime(bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            //更新考勤统计
            this.baseMapper.updateDeviceReport(dr);
        }
        //更新考勤记录处理标记
        deviceReport.setTime(prefixDate);
        deviceRecordMapper.updateIsDeal(deviceReport);
    }

    /**
     * @param list
     * @param longs
     * @return
     * @throws ParseException
     * @description 获取考勤时间总和
     * @author chupp
     * @date 2018年6月16日
     */
    @Override
    public Double getTimeTotal(List<DeviceReportDto> list, Long[] longs) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //上班标记、记录
        boolean startFlag = false;
        boolean endFlag = false;
        Long start = null;
        Long end = null;
        //单个闭环统计、总统计
        Long everyTime = null;
        Long totalTime = 0L;
        for (int i = 0; i < list.size(); i++) {
            DeviceReportDto drr = list.get(i);
            //上班考勤
            if (drr.getType() != null && 3 == Integer.parseInt(drr.getType())) {
                if (!startFlag) {
                    //上班标记
                    startFlag = true;
                    start = sdf.parse(drr.getTime()).getTime() - 8 * 60 * 60 * 1000;
                } else if (endFlag) {
                    //处理时间累计
                    totalTime += everyTime;
                    everyTime = null;
                    endFlag = false;
                    end = null;
                    start = sdf.parse(drr.getTime()).getTime() - 8 * 60 * 60 * 1000;
                }
            }
            //下班考勤
            if (startFlag && drr.getType() != null && 4 == Integer.parseInt(drr.getType())) {
                //下班标记
                endFlag = true;
                end = sdf.parse(drr.getTime()).getTime() - 8 * 60 * 60 * 1000;
            }
            //单个闭环时间统计
            if (startFlag && endFlag) {
                //计算有效时间
                everyTime = this.getEveryTime(start, end, longs);
                //处理时间累计
                if (i == list.size() - 1) {
                    totalTime += everyTime;
                    everyTime = null;
                }
            }
        }
        Double total = totalTime / (3600.00 * 1000.00);
        BigDecimal bg = new BigDecimal(total);
        total = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        return total;
    }

    /**
     * @param start
     * @param end
     * @param longs
     * @return
     * @description 获取区间有效考勤时间
     * @author chupp
     * @date 2018年6月16日
     * @see com.xywg.admin.modular.report.service.IDeviceReportService#getEveryTime(java.lang.Long, java.lang.Long, java.lang.Long[])
     */
    @Override
    public Long getEveryTime(Long start, Long end, Long[] longs) {
        //项目标准时间
        Long as = longs[0];
        Long ae = longs[1];
        Long ps = longs[2];
        Long pe = longs[3];
        if (end <= as) {
            return 0L;
        } else if (end <= ae) {
            if (start <= as) {
                return end - as;
            } else {
                return end - start;
            }
        } else if (end <= ps) {
            if (start <= as) {
                return ae - as;
            } else if (start <= ae) {
                return ae - start;
            } else {
                return 0L;
            }
        } else if (end <= pe) {
            if (start <= as) {
                return ae - as + end - ps;
            } else if (start <= ae) {
                return ae - start + end - ps;
            } else if (start <= ps) {
                return end - ps;
            } else {
                return end - start;
            }
        } else {
            if (start <= as) {
                return ae - as + pe - ps;
            } else if (start <= ae) {
                return ae - start + pe - ps;
            } else if (start <= ps) {
                return pe - ps;
            } else if (start <= pe) {
                return pe - start;
            } else {
                return 0L;
            }
        }
    }

    /**
     * @param projectCode
     * @param prefixDate
     * @return
     * @throws ParseException
     * @description 获取项目实际进出时间
     * @author chupp
     * @date 2018年6月16日
     */
    @Override
    public Long[] getProjectInOutTime(String projectCode, String prefixDate) throws ParseException {
        //查询项目标准时间
    	List<TimeSet> timeList = timeSetMapper.getTimeByProject(projectCode);
        Long[] longArray = new Long[4];
        SimpleDateFormat simpleSdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat puSdf = new SimpleDateFormat("HH:mm:ss");
		if(timeList.size() == 0) {
	        longArray[0] = -14400000L + simpleSdf.parse(prefixDate).getTime();
	        longArray[1] = 14400000L + simpleSdf.parse(prefixDate).getTime();
	        longArray[2] = 16200000L + simpleSdf.parse(prefixDate).getTime();
	        longArray[3] = 50400000L + simpleSdf.parse(prefixDate).getTime();
		}else {
			//项目标准时间
			Long as = null;
			Long ae = null;
			Long ps = null;
			Long pe = null;
			for(TimeSet ts : timeList) {
				if(ts.getType().equals("worker_am")) {
					as = puSdf.parse(ts.getStart()).getTime() + simpleSdf.parse(prefixDate).getTime();
					ae = puSdf.parse(ts.getEnd()).getTime() + simpleSdf.parse(prefixDate).getTime();
				}
				if(ts.getType().equals("worker_pm")) {
					ps = puSdf.parse(ts.getStart()).getTime() + simpleSdf.parse(prefixDate).getTime();
					pe = puSdf.parse(ts.getEnd()).getTime() + simpleSdf.parse(prefixDate).getTime();
				}
			}
			if(as == null || ae == null || ps == null || pe == null) {
				longArray[0] = -14400000L + simpleSdf.parse(prefixDate).getTime();
		        longArray[1] = 14400000L + simpleSdf.parse(prefixDate).getTime();
		        longArray[2] = 16200000L + simpleSdf.parse(prefixDate).getTime();
		        longArray[3] = 50400000L + simpleSdf.parse(prefixDate).getTime();
			}else {
				longArray[0] = as;
				longArray[1] = ae;
				longArray[2] = ps;
				longArray[3] = pe;
			}
		}
        return longArray;
    }

    /**
     * @param page
     * @param map
     * @return
     * @description 数据查询
     * @author chupp
     * @date 2018年6月20日
     * @see com.xywg.admin.modular.report.service.IDeviceReportService#getList(com.baomidou.mybatisplus.plugins.Page, java.util.Map)
     */
    @Override
    public List<DeviceReportVo> getList(Page<DeviceReportVo> page, Map<String, Object> map) {
        map.put("ocList", deptService.getOrganizationCodeByDeptId(ShiroKit.getUser().getDeptId()));
        return this.baseMapper.getList(page, map);
    }

    @Override
    public List<Map<String, Object>> getExportList(Map<String, Object> params) {
        params.put("ocList", deptService.getOrganizationCodeByDeptId(ShiroKit.getUser().getDeptId()));
        return this.baseMapper.getExportList(params);
    }

    @Override
    public void download(HttpServletResponse res, HttpServletRequest req, Map<String, Object> params) {
    	Integer isEnterprise = ShiroKit.getUser().getIsEnterprise();
        List<Map<String, Object>> deviceReports = this.getExportList(params);
        for (Map<String, Object> map : deviceReports) {
            map.put("idCardTypeName", ConstantFactory.me().getDictsByName("人员证件类型", (Integer) map.get("idCardType")));
            Iterator it_d = map.entrySet().iterator();
            while (it_d.hasNext()) {
                Map.Entry entry_d = (Map.Entry) it_d.next();
                String key = (String) entry_d.getKey();
                Object value = entry_d.getValue();
                if(value.toString().equals("-1.0")){
                    map.put(key,"0.0");
                }
            }
        }
        //构建下载文件时的文件名
        String fileName = "考勤统计" + DateUtils.getDate("yyyyMMddHHmmss");
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
            if(isEnterprise == 1) {
            	ExcelUtils.getInstance().exportObjects2Excel(deviceReports, DeviceReportExportDto.class, true, os);
            }else {
            	ExcelUtils.getInstance().exportObjects2Excel(deviceReports, DeviceReportExportDmo.class, true, os);
            }
            
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

    @Override
    public void downloadNew(HttpServletResponse res, HttpServletRequest req, Map<String, Object> params) {
        params.put("ocList", deptService.getOrganizationCodeByDeptId(ShiroKit.getUser().getDeptId()));
        List<Map<String, Object>> deviceReports = this.baseMapper.getExportNewList(params);
        for (Map<String, Object> map : deviceReports) {
            map.put("idCardTypeName", ConstantFactory.me().getDictsByName("人员证件类型", (Integer) map.get("idCardType")));
            Iterator it_d = map.entrySet().iterator();
            while (it_d.hasNext()) {
                Map.Entry entry_d = (Map.Entry) it_d.next();
                String key = (String) entry_d.getKey();
                Object value = entry_d.getValue();
                if(value.toString().equals("-1.0")){
                    map.put(key,"0.0");
                }
            }
        }
        //构建下载文件时的文件名
        String fileName = "考勤统计" + DateUtils.getDate("yyyyMMddHHmmss");
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
            ExcelUtils.getInstance().exportObjects2Excel(deviceReports, DeviceReportNewExport.class, true, os);
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

    @Override
    public void countTotalTime(DeviceReport deviceReport) {
        ProjectMaster projectByProjectCode = projectMasterMapper.getProjectByProjectCode(deviceReport.getProjectCode());
        baseMapper.countTotalTime(projectByProjectCode.getWorkTime(),deviceReport.getId());
    }
}
