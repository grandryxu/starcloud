package com.xywg.admin.modular.company.utils;

import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.modular.company.dao.SubContractorMapper;
import com.xywg.admin.modular.company.model.EmployeeMasterVo;
import com.xywg.admin.modular.company.model.SubContractor;
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
public final class EmployeeMasterExcelUtil {
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

    final static int last_cell_mum = 16;

    static String professional = "";//职称等级（静态变量）

    static {
        _titleMap.put("姓名", "employeeName");
        _titleMap.put("证件类型", "idCardType");
        _titleMap.put("证件号", "idCardNumber");
        _titleMap.put("手机号码", "cellPhone");
        _titleMap.put("证件类型", "idCardType");
        _titleMap.put("性别", "gender");
        _titleMap.put("民族", "nation");
        _titleMap.put("出生日期", "birthday");
        _titleMap.put("住址", "address");
        _titleMap.put("政治面貌", "politicsType");
        _titleMap.put("职称等级", "professionalLevel");
        _titleMap.put("职称", "professionalType");
        _titleMap.put("文化程度", "cultureLevelType");
        _titleMap.put("开始工作日期", "workDate");
        _titleMap.put("公司", "organizationCode");
        _titleMap.put("聘用方式", "jobType");
        _titleMap.put("岗位职务", "job");
        _titleMap.put("备注", "remark");
    }

    /**
     * 获取Excel内容
     *
     * @param filename        struts2自带的文件上传，文件名会改变，所以需要手动传入原始文件名
     * @param beforeDataCount 上传之前grid中的数据量
     */
    public List<EmployeeMasterVo> getContent(FileInputStream excel, String filename, int beforeDataCount) throws Exception {
        Cell cell;
        Object cellValue;
        String cellTitle;
        EmployeeMasterVo employeeMasterVo;
        Workbook workbook = null;
        List<EmployeeMasterVo> auctions = new ArrayList<EmployeeMasterVo>();
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
                titles.add(StringUtils.trim(EmployeeMasterExcelUtil.getCellValue(titleRow.getCell(i))));
            }
            //处理内容行
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    //如果前16列为空值，或者为空字符串，直接跳过不进行解析
                    boolean flag = false;
                    for (int j = 0; j < last_cell_mum; j++) {
                        cell = row.getCell(j);
                        //有效列
                        if (!(cell == null || (cell.getCellType() == 1 && cell.getStringCellValue().trim().equals("")))) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag == false) { //没有有效数据
                        continue;
                    }
                    employeeMasterVo = new EmployeeMasterVo();
//                    workermaster.setId(beforeDataCount);
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        cell = row.getCell(j);
                        cellTitle = _titleMap.get(titles.get(j));
                        cellValue = getValue(cell, j, i, getKey(_titleMap, cellTitle));
                        if (StringUtils.isNotBlank(cellTitle) && StringUtils.isNotBlank(cellValue.toString())) {
                            PropertyUtils.setProperty(employeeMasterVo, cellTitle, cellValue);
                        }
                    }
                    auctions.add(employeeMasterVo);
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
        if (columnIndex > last_cell_mum) {
            return "";
        }
        String[] checkColumn = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "12", "13", "15"}; //不能为空的标题列
        if (Arrays.asList(checkColumn).contains(String.valueOf(columnIndex)) && cell == null) {
            throw new XywgException(600, nullErrorMsg(rowIndex, title));//如果为空值，抛出异常
        }
        if (columnIndex == 6 || columnIndex == 11) { //日期个格式校验
            if (cell == null) {
                return "";
            }
            if (isValidDate(cell.getStringCellValue())) {
                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                return cell.getDateCellValue();
            } else {
                throw new XywgException(600, errorMsg(rowIndex, title));
            }
        } else if (columnIndex == 1) {
            return dictService.selectNumByName("人员证件类型", cell.getStringCellValue());
        } else if (columnIndex == 4) {
            return Integer.valueOf(cell.getStringCellValue().equals("男") ? 1 : 0);
        } else if (columnIndex == 5) {
            return dictService.selectNumByName("民族", cell.getStringCellValue()).toString();
        } else if (columnIndex == 8) {
            professional = cell.getStringCellValue();
            return Integer.valueOf(dictService.selectNumByName("职称等级", cell.getStringCellValue()));
        } else if (columnIndex == 9) {
            if (professional.equals("初级职称")) {
                return Integer.valueOf(dictService.selectNumByName("初级职称人员类别", cell.getStringCellValue()));
            } else if (professional.equals("中级职称")) {
                return Integer.valueOf(dictService.selectNumByName("中级职称人员类别", cell.getStringCellValue()));
            } else {
                return Integer.valueOf(dictService.selectNumByName("高级职称人员类别", cell.getStringCellValue()));
            }
        } else if (columnIndex == 10) {
            return dictService.selectNumByName("文化程度", cell.getStringCellValue());
        } else if (columnIndex == 12) {
            SubContractor subContractor = subContractorMapper.getOrgidByName(cell.getStringCellValue());
            if (subContractor != null) {
                return subContractor.getOrganizationCode();
            } else {
                throw new XywgException(600, "第" + rowIndex + "行" + title + "输入错误");
            }
        } else if (columnIndex == 13) {
            return Integer.valueOf(cell.getStringCellValue().equals("全职") ? 0 : 1);
        } else {
            if (cell == null) {
                return "";
            }
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


}

