package com.xywg.admin.modular.wages.util;

import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.modular.company.dao.SubContractorMapper;
import com.xywg.admin.modular.company.model.EmployeeMasterVo;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.system.service.IAreaService;
import com.xywg.admin.modular.system.service.IDictService;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Excel 操作工具类
 *
 * @author trunks
 */
@Component
public final class PayRollDetailByOrderExcelUtil {
    @Autowired
    protected IWorkerMasterService wokerMasterService;
    @Autowired
    protected IDictService dictService;
    @Resource
    protected SubContractorMapper subContractorMapper;
    @Autowired
    protected IAreaService iAreaService;
    /**
     * Excel中文标题和JavaBean属性映射
     */
    final static Map<String, String> _titleMap = new HashMap<String, String>();

    static String professional = "";//职称等级（静态变量）

    static {
        _titleMap.put("姓名", "workerName");
        _titleMap.put("证件类型", "idCardType");
        _titleMap.put("证件编号", "idCardNumber");
        _titleMap.put("工种(元)", "workTypeCode");
        _titleMap.put("基本工资(元)", "amount");
        _titleMap.put("奖励(元)", "rewardAmount");
        _titleMap.put("惩罚(元)", "punishAmount");
        _titleMap.put("应发工资(元)", "payAmount");
        _titleMap.put("实发工资(元)", "actualAmount");
        _titleMap.put("剩余工资(元)", "balanceAmount");
    }

    /**
     * 获取Excel内容
     *
     * @param filename        struts2自带的文件上传，文件名会改变，所以需要手动传入原始文件名
     * @param beforeDataCount 上传之前grid中的数据量
     */
    public List<Map<String, Object>> getContent(FileInputStream excel, String filename, int beforeDataCount) throws Exception {
        Cell cell;
        Object cellValue;
        String cellTitle;
        EmployeeMasterVo employeeMasterVo;
        Workbook workbook = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<String> titles = new ArrayList<String>();

        try {
            if (filename.endsWith(".xls")) {
                workbook = new HSSFWorkbook(new POIFSFileSystem(excel));
            } else if (filename.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(excel);
            }
            Sheet sheet = workbook.getSheetAt(0);

            //处理标题行
            Row titleRow = sheet.getRow(0);
            if (titleRow == null) {
                return list;
            }

            for (int i = 0; i < titleRow.getLastCellNum(); i++) {
                titles.add(StringUtils.trim(PayRollDetailByOrderExcelUtil.getCellValue(titleRow.getCell(i))));
            }
            //处理内容行
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row != null) {
                    Map<String, Object> auctions = new HashMap<>();
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        cell = row.getCell(j);
                        cellTitle = _titleMap.get(titles.get(j));
                        cellValue = getValue(cell, j, i, getKey(_titleMap, cellTitle));
                        if (StringUtils.isNotBlank(cellTitle) && StringUtils.isNotBlank(cellValue.toString())) {
                            auctions.put(_titleMap.get(titles.get(j)), cellValue);
                        }
                        if (j == 1) {
                            auctions.put("idCardName", cell.getStringCellValue());
                        }
                        if (j == 3) {
                            auctions.put("workKindName", cell.getStringCellValue());
                        }
                    }
                    //判断人员数据书否真实
                    String idCardNumber = auctions.get("idCardNumber").toString();
                    int idCardType = Integer.valueOf(auctions.get("idCardType").toString());
                    WorkerMaster wm = wokerMasterService.getWorkerByIdCard(idCardNumber, idCardType);
                    if (wm == null || !(wm.getWorkerName().equals(auctions.get("workerName")))) {
                        throw new XywgException(600, "第" + i + "行人员信息错误");
                    }
                    list.add(auctions);
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            excel.close();
        }

        return list;
    }

    //根据value获取 key (标题名称)
    public String getKey(Map map, String value) {
        String title = "";
        for (Object key : map.keySet()) {
            if (map.get(key).equals(value)) {
                title = key.toString();
            }
        }
        return title;
    }

    /**
     * 判断是否为日期格式
     *
     * @param str
     * @return
     */
    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (Exception e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 读取excel的值
     *
     * @return
     */
    public Object getValue(Cell cell, int columnIndex, int rowIndex, String title) {
        String[] checkColumn = {"0", "1", "2", "3"}; //不能为空的标题列
        if (Arrays.asList(checkColumn).contains(String.valueOf(columnIndex)) && cell == null) {
            throw new XywgException(600, nullErrorMsg(rowIndex, title));//如果为空值，抛出异常
        }
        if (columnIndex == 1) {
            return dictService.selectNumByName("人员证件类型", cell.getStringCellValue());
        } else if (columnIndex == 3) {
            return dictService.selectNumByName("工种字典数据", cell.getStringCellValue()).toString();
        } else if (columnIndex == 0 || columnIndex == 2) {
            return String.valueOf(cell.getStringCellValue());
        } else {
            if (cell == null) {
                return 0;
            }
//            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            int type = cell.getCellType();
            if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
                throw new XywgException(600, isNotNumeric(rowIndex, title));//数字验证
            }
            return cell.getNumericCellValue();
        }
    }

    /**
     * 读取Excel单元格值
     */
    public static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        Object result = null;
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC: //数字
                //使用BigDecimal，防止被科学计数法
                result = new BigDecimal(cell.getNumericCellValue()).toString();
                break;
            case HSSFCell.CELL_TYPE_STRING: //字符串
                result = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN: //Boolean
                result = cell.getBooleanCellValue();
                break;
            case HSSFCell.CELL_TYPE_FORMULA: //公式
                result = cell.getCellFormula();
                break;
            case HSSFCell.CELL_TYPE_BLANK: //空值
                result = "";
                break;
            case HSSFCell.CELL_TYPE_ERROR: //故障
                result = "";
                break;
            default:
                result = "";
                break;
        }

        return StringUtils.trim(String.valueOf(result));
    }

    /**
     * 空验证错误信息
     *
     * @param rowIndex 行号（从1开始）
     * @param title    列名
     */
    public static String nullErrorMsg(int rowIndex, String title) {
        return "第" + rowIndex + "行" + title + "不能为空";
    }

    /**
     * 验证信息
     *
     * @param rowIndex 行号（从1开始）
     * @param title    列名
     */
    public static String errorMsg(int rowIndex, String title) {
        return "第" + rowIndex + "行" + title + "格式错误";
    }
    /**
     * 必须为数字
     *
     * @param rowIndex 行号（从1开始）
     * @param title    列名
     */
    public static String isNotNumeric(int rowIndex, String title) {
        return "第" + rowIndex + "行" + title + "必须为大于0的数字";
    }

}

