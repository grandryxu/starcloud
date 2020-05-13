package com.xywg.admin.modular.wages.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.excel.ExcelUtils;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.util.ExcelImportUtils;
import com.xywg.admin.modular.company.utils.ServletsUtils;
import com.xywg.admin.modular.system.service.AccountProjectService;
import com.xywg.admin.modular.system.service.IDictService;
import com.xywg.admin.modular.team.warpper.TeamNumberWarpper;
import com.xywg.admin.modular.wages.dao.PayRollDetailMapper;
import com.xywg.admin.modular.wages.dao.SettlementMapper;
import com.xywg.admin.modular.wages.model.*;
import com.xywg.admin.modular.wages.model.dto.PayRollDetailDto;
import com.xywg.admin.modular.wages.model.dto.PayRollDto;
import com.xywg.admin.modular.wages.model.dto.PayRollExport;
import com.xywg.admin.modular.wages.service.IAccountService;
import com.xywg.admin.modular.wages.service.IPayRollDetailService;
import com.xywg.admin.modular.wages.service.IPayRollService;
import com.xywg.admin.modular.wages.util.PayRollDetailByOrderExcelUtil;
import com.xywg.admin.modular.wages.util.PayRollDetailByWorkerTypeExcelUtil;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * <p>
 * 工资明细 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-01
 */
@Service
public class PayRollDetailServiceImpl extends ServiceImpl<PayRollDetailMapper, PayRollDetail> implements IPayRollDetailService {
	private static Logger log = LoggerFactory.getLogger(PayRollDetailServiceImpl.class);
    @Autowired
    private IPayRollService payRollService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    protected IDictService dictService;
    @Autowired
    private AccountProjectService accountProjectService;

    @Override
    public List<PayRollDetailVo> getSalaryDetailByWorkerInfo(ParamsToFindSalaryDetail paramsToFindSalaryDetail) {
        return baseMapper.getSalaryDetailByWorkerInfo(paramsToFindSalaryDetail);
    }

    @Override
    public List<PayRollDetailVo> selectList(Page<PayRollDetailVo> page, ParamsToFindSalaryDetail paramsToFindSalaryDetail) {
        return baseMapper.list(page, paramsToFindSalaryDetail);
    }

    @Override
    public Boolean addPayRollAndDetail(PayRollVo payRollVo) {
        payRollVo.setPayMonthDate(payRollVo.getPayMonth());
        payRollVo.setSalaryPerson(Long.valueOf(ShiroKit.getUser().getId()));
        payRollVo.setCreateUser(ShiroKit.getUser().getName());
        payRollVo.setPayRollCode("GZD" + System.currentTimeMillis());
        //新增工资单
        payRollService.insert(payRollVo);
        //新增工资单详情
        baseMapper.insertDetailList(payRollVo.getPayRollDetailList(), payRollVo.getId(), ShiroKit.getUser().getName());
        //更新计工单的salaryId字段
        if (payRollVo.getAccountIds() != null) {
            accountService.updateSalaryId(payRollVo.getAccountIds(), payRollVo.getId());
        }
        return true;
    }

    @Override
    public List<PayRollDetailVo> getDetailList(Page<PayRollDetailVo> page, Map<String, Object> map) {
        map.put("deptId", ShiroKit.getUser().getDeptId());
        return baseMapper.getDetailList(page, map);
    }

    @Override
    public Integer toggleStatus(String ids, Integer status) {
        return baseMapper.toggleStatus(ids, status, Long.valueOf(ShiroKit.getUser().getId()));
    }

    @Override
    public Integer grantPayRoll(Long id, String name) {
        return baseMapper.grantPayRoll(id, name);
    }

    @Override
    public List<Map<String, Object>> getPayRollDetailById(Long id) {
        return baseMapper.getPayRollDetailById(id);
    }

    @Override
    public Integer payRollDetailSign(PayRollDetailDto payRollDetailDto) {
        Integer number = baseMapper.payRollDetailSign(payRollDetailDto);
        //查询工资单是否全部签字
//        PayRollDto payRollDto = baseMapper.isAllSign(payRollDetailDto.getId());
//        if(1 == payRollDto.getIsAllSign()){
//            //全部签字
//            //更新工资单状态
//            baseMapper.updateStatusAfterReview(payRollDto.getId());
//        }
        return number;
    }

    @Override
    public List<PayRollDetailDto> getPayRollDetailListByWorkerListAndTeamSysNo(PayRollDto payRollDto) {
        return baseMapper.getPayRollDetailListByWorkerListAndTeamSysNo(payRollDto);
    }

    @Override
    public List<PayRollDetailDto> v116GetPayRollDetailListByWorkerListAndTeamSysNo(PayRollDto payRollDto) {
        payRollDto.setIndex((payRollDto.getPageNo()-1)*payRollDto.getPageSize());
        return baseMapper.v116GetPayRollDetailListByWorkerListAndTeamSysNo(payRollDto);
    }

    @Override
    public List<Map<String,Object>> getPayRollFlowDetailListByProjectCodeAndTeamSysNo(String projectCode, Long id) {
        return baseMapper.getPayRollFlowDetailListByProjectCodeAndTeamSysNo(projectCode, id);
    }

    @Override
    public List<PayRollDetailDto> getPayRollFlowDetailListByWorkerAndTeamSysNo(String teamSysNo, String idCardType, String idCardNumber) {
        return baseMapper.getPayRollFlowDetailListByWorkerAndTeamSysNo(teamSysNo, idCardType, idCardNumber);
    }

    /**
     * 根据项目编号和工人信息获取工资和结算单详细
     *
     * @param projectCode
     * @param idCardType
     * @param idCardNumber
     * @return
     */
    @Override
    public List<PayRollFlow> getPayrollAndSettlementDetailListByWorkerAndProjectCode(String projectCode, String idCardType, String idCardNumber) {
        //获取工资表详细列表
        List<PayRollFlow> payRollFlowList = baseMapper.getPayrollAndSettlementDetailListByWorkerAndProjectCode(projectCode, idCardType, idCardNumber);
        return payRollFlowList;
    }

    /**
     * 根据项目编号和工人信息获取工资和结算单详细
     *
     * @param projectCode
     * @param idCardType
     * @param idCardNumber
     * @return
     */
    @Override
    public List<PayRollFlow> v116GetPayrollAndSettlementDetailListByWorkerAndProjectCode(String projectCode, String idCardType, String idCardNumber,Integer pageNo , Integer pageSize) {
        //获取工资表详细列表
        List<PayRollFlow> payRollFlowList = baseMapper.v116GetPayrollAndSettlementDetailListByWorkerAndProjectCode(projectCode, idCardType, idCardNumber,(pageNo-1)*pageSize,pageSize);
        return payRollFlowList;
    }

    @Override
    public List<Map<String, Object>> getLastPayRoll(String idCardNumber, Integer idCardType) {
        return baseMapper.getLastPayRoll(idCardNumber, idCardType);
    }

    /**
     * 批量更新工资单
     *
     * @param payRollDetailList
     * @return
     */
    @Override
    public Integer updates(List<Map<String, Object>> payRollDetailList) {
        Integer num = 0;
        for (Map<String, Object> map : payRollDetailList) {
            this.baseMapper.updateSingleMap(map);
            num++;
        }
        return num;
    }

    @Override
    public void export(HttpServletResponse res, HttpServletRequest req, Map<String, Object> params) {
        List<Map<String, Object>> payRollDetails = this.getDetailExportList(params);
        for (Map<String, Object> map : payRollDetails) {
            if (null != map.get("saveStatus"))
                map.put("saveStatusName", (Integer) map.get("saveStatus") == 1 ? "暂存" : "提交");
            if (null != map.get("type"))
                map.put("typeName", (Integer) map.get("type") == 1 ? "按工种发放" : "按计工单发放");
            if (null != map.get("status")) {
                if((Integer)map.get("saveStatus") == 2) {
                    if ((Integer) map.get("status") == 2) {
                        map.put("statusName", "分包已审核");
                    } else if ((Integer) map.get("status") == 3) {
                        map.put("statusName", "总包已复核");
                    } else if ((Integer) map.get("status") == 50) {
                        map.put("statusName", "已发放");
                    } else {
                        map.put("statusName", "待审核");
                    }
                }else{
                    map.put("statusName", "");
                }
            }
        }


        //构建下载文件时的文件名
        String fileName = "工资单一览" + com.xywg.admin.core.util.DateUtils.getDate("yyyyMMddHHmmss");
        boolean isMSIE = ServletsUtils.isMSBrowser(req);
        BufferedInputStream bis = null;
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
            ExcelUtils.getInstance().exportObjects2Excel(payRollDetails, PayRollExport.class, true, os);
        } catch (Exception e) {
        	log.error(e.getMessage());
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (Exception e) {
                	log.error(e.getMessage());
                }
            }
        }
    }

    @Override
    public List<Map<String, Object>> getDetailExportList(Map<String, Object> params) {
        params.put("deptId", ShiroKit.getUser().getDeptId());
        params.put("projectCodes", accountProjectService.getProjectCodes());
        return this.baseMapper.getDetailExportList(params);
    }

//    @Override
//    public Object excelUploadByWorkerType(MultipartFile excelFile, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        List<Map<String,Object>map =new HashMap<>();
//        if (!excelFile.isEmpty()) {
//            String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
//            File dir = new File(filePath);
//            if (!dir.exists()) {
//                dir.mkdir();
//            }
//            String path = filePath + excelFile.getOriginalFilename();
//            File tempFile = null;
//            //save to the /upload path
//            tempFile = new File(path);
//            try {
//                excelFile.transferTo(tempFile);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            map = payRollDetailByWorkerTypeExcelUtil.getContent(new FileInputStream(tempFile), tempFile.getName(), 0);
//        }
//        return map;
//    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object importByWorkerType(MultipartFile excelFile, HttpServletRequest request) throws Exception {
        String[] checkColumn = {"0", "1", "2", "3", "4", "5"}; //不能为空的标题列
        int n = 0;
        int maxCellNum = 12;
        Map<String, String> titleMap = new HashMap<String, String>();
        titleMap.put("姓名", "workerName");
        titleMap.put("证件类型", "idCardType");
        titleMap.put("证件编号", "idCardNumber");
        titleMap.put("工种", "workerType");
        titleMap.put("考勤天数", "days");
        titleMap.put("单价(元)", "price");
        titleMap.put("基本工资(元)", "amount");
        titleMap.put("奖励(元)", "rewardAmount");
        titleMap.put("惩罚(元)", "punishAmount");
        titleMap.put("应发工资(元)", "payAmount");
        titleMap.put("实发工资(元)", "actualAmount");
        titleMap.put("剩余工资(元)", "balanceAmount");
        File file = ExcelImportUtils.multipartFileToFile(excelFile, request); //文件类型专程File类型
        List<Map<String, Map<String, Object>>> list = ExcelImportUtils.getContent(new FileInputStream(file), file.getName(), titleMap, maxCellNum);
        List<Map<String,Object>> listMap= new ArrayList<Map<String,Object>>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Map<String, Object>> map = list.get(i);
                Map<String,Object> rowMap= new HashedMap();
                for (String key : map.keySet()) {
                    Map<String, Object> valueMap = map.get(key);
                    String value = valueMap.get(ExcelImportUtils.value).toString();//单元格的值
                    int columnIndex = (int) valueMap.get(ExcelImportUtils.columnIndex);//列
                    int rowIndex = (int) valueMap.get(ExcelImportUtils.rowIndex);//行
                    String title = valueMap.get(ExcelImportUtils.title).toString();//标题内容
                    if (columnIndex == 1) {
                        rowMap.put("idCardName",value);
                    }
                    if (columnIndex == 3) {
                        rowMap.put("workKindName",value);
                    }
                    Object data= getValue(value, columnIndex, rowIndex, title,checkColumn);
                    rowMap.put(key,data);
                }
                listMap.add(rowMap);
            }
        }

        return listMap;
    }
    /**
     * 读取excel的值
     *
     * @return
     */
    public Object getValue(String value, int columnIndex, int rowIndex, String title,String[] checkColumn) {
        if (Arrays.asList(checkColumn).contains(String.valueOf(columnIndex)) && value .equals("")) {
            throw new XywgException(600, ExcelImportUtils.nullErrorMsg(rowIndex, title));//如果为空值，抛出异常
        }
        if (columnIndex == 1) {
            return dictService.selectNumByName("人员证件类型", value);
        } else if (columnIndex == 3) {
            return dictService.selectNumByName("工种字典数据", value);
        } else if (columnIndex == 0 || columnIndex == 2) {
            return value;
        } else {
            if (value.equals("")) {
                return 0;
            }
            if(!ExcelImportUtils.isNumeric(value)){
                throw new XywgException(600, ExcelImportUtils.isNotNumeric(rowIndex, title));//数字验证
            }
            return value;
        }
    }

//    @Override
//    public Object excelUploadByOrder(MultipartFile excelFile, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        List<Map<String,Object>>list =new ArrayList<>();
//        if (!excelFile.isEmpty()) {
//            String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
//            File dir = new File(filePath);
//            if (!dir.exists()) {
//                dir.mkdir();
//            }
//            String path = filePath + excelFile.getOriginalFilename();
//            File tempFile = null;
//            //save to the /upload path
//            tempFile = new File(path);
//            try {
//                excelFile.transferTo(tempFile);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            list = payRollDetailByOrderExcelUtil.getContent(new FileInputStream(tempFile), tempFile.getName(), 0);
//        }
//        return list;
//    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object importByOrder(MultipartFile excelFile, HttpServletRequest request) throws Exception {
        String[] checkColumn = {"0", "1", "2", "3"}; //不能为空的标题列
        int n = 0;
        int maxCellNum = 10;
        Map<String, String> titleMap = new HashMap<String, String>();
        titleMap.put("姓名", "workerName");
        titleMap.put("证件类型", "idCardType");
        titleMap.put("证件编号", "idCardNumber");
        titleMap.put("工种", "workerType");
        titleMap.put("基本工资(元)", "amount");
        titleMap.put("奖励(元)", "rewardAmount");
        titleMap.put("惩罚(元)", "punishAmount");
        titleMap.put("应发工资(元)", "payAmount");
        titleMap.put("实发工资(元)", "actualAmount");
        titleMap.put("剩余工资(元)", "balanceAmount");
        File file = ExcelImportUtils.multipartFileToFile(excelFile, request); //文件类型专程File类型
        List<Map<String, Map<String, Object>>> list = ExcelImportUtils.getContent(new FileInputStream(file), file.getName(), titleMap, maxCellNum);
        List<Map<String,Object>> listMap= new ArrayList<Map<String,Object>>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Map<String, Object>> map = list.get(i);
                Map<String,Object> rowMap= new HashedMap();
                for (String key : map.keySet()) {
                    Map<String, Object> valueMap = map.get(key);
                    String value = valueMap.get(ExcelImportUtils.value).toString();//单元格的值
                    int columnIndex = (int) valueMap.get(ExcelImportUtils.columnIndex);//列
                    int rowIndex = (int) valueMap.get(ExcelImportUtils.rowIndex);//行
                    String title = valueMap.get(ExcelImportUtils.title).toString();//标题内容
                    if (columnIndex == 1) {
                        rowMap.put("idCardName",value);
                    }
                    if (columnIndex == 3) {
                        rowMap.put("workKindName",value);
                    }
                    Object data= getValue(value, columnIndex, rowIndex, title,checkColumn);
                    rowMap.put(key,data);
                }
                listMap.add(rowMap);
            }
        }

        return listMap;
    }

    @Override
    public List<PayRollDetailVo> getSalaryDetailByWorkerInfoNoPages(ParamsToFindSalaryDetail paramsToFindSalaryDetail) {
        return this.baseMapper.getSalaryDetailByWorkerInfoNoPages(paramsToFindSalaryDetail);
    }

	@Override
	public List<Map<String,Object>> getPayRollFlowDetailListByProjectCodeAndTeamSysNoV116(String projectCode, Long id, Integer pageNo,
			Integer pageSize) {
		return baseMapper.getPayRollFlowDetailListByProjectCodeAndTeamSysNoV116(projectCode, id, (pageNo-1)* pageSize,pageSize);
	}

    @Override
    public List<Map<String, Object>> v116GetPayRollDetailById(Long id,Integer pageNo , Integer pageSize) {
        return baseMapper.v116GetPayRollDetailById(id,(pageNo-1)*pageSize,pageSize);
    }

}
