package com.xywg.admin.modular.worker.util;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.modular.system.service.IAreaService;
import com.xywg.admin.modular.system.service.IDictService;
import com.xywg.admin.modular.worker.model.WorkerMasterImport;
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


/**
 * Excel 操作工具类
 *
 * @author trunks
 */
@Component
public final class ExcelUtil {
    @Autowired
    protected IDictService dictService;

    @Autowired
    protected IAreaService iAreaService;
    /**
     * Excel中文标题和JavaBean属性映射
     */
    final static Map<String, String> _titleMap = new HashMap<String, String>();

    static {
        _titleMap.put("姓名", "workerName");
        _titleMap.put("手机号码", "cellPhone");
        _titleMap.put("出生日期", "birthday");
        _titleMap.put("当前工种", "workTypeCode");
        _titleMap.put("证件类型", "idCardType");
        _titleMap.put("证件编号", "idCardNumber");
        _titleMap.put("性别", "gender");
        _titleMap.put("民族", "nation");
        _titleMap.put("籍贯", "birthPlaceCode");
        _titleMap.put("地址", "address");
        _titleMap.put("政治面貌", "politicsType");
        _titleMap.put("文化程度", "cultureLevelType");
        _titleMap.put("是否加入工会", "isJoined");
        _titleMap.put("加入工会时间", "joinedTime");
        _titleMap.put("是否有重大病史", "hasBadMedicalHistory");
        _titleMap.put("开始工作日期", "workDate");
        _titleMap.put("紧急联系人姓名", "urgentContractName");
        _titleMap.put("紧急联系人联系电话", "urgentContractCellphone");
        _titleMap.put("备注", "note");
    }

    /**
     * 获取Excel内容
     *
     * @param filename        struts2自带的文件上传，文件名会改变，所以需要手动传入原始文件名
     * @param beforeDataCount 上传之前grid中的数据量
     */
    public List<WorkerMasterImport> getContent(FileInputStream excel, String filename, int beforeDataCount) throws Exception {
        Cell cell;
        Object cellValue;
        String cellTitle;
        WorkerMasterImport workerMasterImport;
        Workbook workbook = null;
        List<WorkerMasterImport> auctions = new ArrayList<WorkerMasterImport>();
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
                return auctions;
            }

            for (int i = 0; i < titleRow.getLastCellNum(); i++) {
                titles.add(StringUtils.trim(ExcelUtil.getCellValue(titleRow.getCell(i))));
            }
            //处理内容行
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    workerMasterImport = new WorkerMasterImport();
//                    workermaster.setId(beforeDataCount);
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        cell = row.getCell(j);
                        cellTitle = _titleMap.get(titles.get(j));
                        cellValue = getValue(cell, j, i, getKey(_titleMap, cellTitle));
                        if (StringUtils.isNotBlank(cellTitle) && StringUtils.isNotBlank(cellValue.toString())) {
                            PropertyUtils.setProperty(workerMasterImport, cellTitle, cellValue);
                        }
                    }
                    auctions.add(workerMasterImport);
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            excel.close();
        }

        return auctions;
    }

    //根据value获取 key (标题名称)
    public  String getKey(Map map, String value) {
        String title="";
        for (Object key : map.keySet()) {
            if (map.get(key).equals(value)) {
                title= key.toString();
            }
        }
        return title;
    }

    /**
     * 读取excel的值
     *
     * @return
     */
    public Object getValue(Cell cell, int columnIndex, int rowIndex, String title) {
        String[] checkColumn = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        if (Arrays.asList(checkColumn).contains(String.valueOf(columnIndex)) && cell == null) {
            nullErrorMsg(rowIndex, title);
        }
        if (columnIndex == 2 || columnIndex == 13 || columnIndex == 15) {
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            return cell.getDateCellValue();
        } else if (columnIndex == 3) {
            return dictService.selectNumByName("工种字典数据", cell.getStringCellValue()).toString();
        } else if (columnIndex == 4) {
            return dictService.selectNumByName("人员证件类型", cell.getStringCellValue());
        } else if (columnIndex == 6) {
            return Integer.valueOf(cell.getStringCellValue().equals("男") ? 1 : 0);
        } else if (columnIndex == 7) {
            return dictService.selectNumByName("民族", cell.getStringCellValue()).toString();
        } else if (columnIndex == 8) {
            return iAreaService.getIdByShortName(cell.getStringCellValue());
        } else if (columnIndex == 10) {
            return dictService.selectNumByName("政治面貌", cell.getStringCellValue());
        } else if (columnIndex == 11) {
            return dictService.selectNumByName("文化程度", cell.getStringCellValue());
        } else if (columnIndex == 12) {
            return Integer.valueOf(cell.getStringCellValue().equals("是") ? 1 : 0);
        } else if (columnIndex == 14) {
            return Integer.valueOf(cell.getStringCellValue().equals("是") ? 1 : 0);
        } else {
            return String.valueOf(cell.getStringCellValue());
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
    public static void nullErrorMsg(int rowIndex, String title) {
        throw new XywgException(600, ("第" + rowIndex + "行" + title + "不能为空"));
    }

    /**
     * 验证信息
     *
     * @param rowIndex 行号（从1开始）
     * @param title    列名
     */
    public static String errorMsg(int rowIndex, String title) {
        return MessageFormat.format("第[{0}]行\"{1}\"", rowIndex, title);
    }

    /**
     * 数字错误信息
     *
     * @param rowIndex 行号（从1开始）
     * @param title    列名
     */
    public static String notNumberErrorMsg(int rowIndex, String title) {
        return MessageFormat.format("第[{0}]行\"{1}\"应该是数字", rowIndex, title);
    }

//    /**
//     * 负数提示信息
//     *
//     * @param rowIndex 行号（从1开始）
//     * @param title    列名
//     */
//    public static String numberErrorMsg(int rowIndex, String title) {
//        return MessageFormat.format("第[{0}]行\"{1}\"应该是正数", rowIndex, title);
}

