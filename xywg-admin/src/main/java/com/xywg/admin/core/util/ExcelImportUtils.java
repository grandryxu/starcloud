package com.xywg.admin.core.util;


import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.modular.system.service.IAreaService;
import com.xywg.admin.modular.system.service.IDictService;
import com.xywg.admin.modular.worker.model.WorkerMasterImport;
import com.xywg.admin.modular.worker.util.WorkerMasterExcelUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangcw
 * FTP  上传下载  删除 工具类
 * 属性值由配置文件配置
 */
@SuppressWarnings("all")
public class ExcelImportUtils {
    /**
     * 读取的最大列数
     */

    public final static String value = "value";

    public final static String columnIndex = "columnIndex";

    public final static String rowIndex = "rowIndex";

    public final static String title = "title";


    @Autowired
    protected IDictService dictService;

    @Autowired
    protected IAreaService iAreaService;

    /**
     * @param excel      文件流
     * @param filename   文件名称
     * @param titleMap   标题行
     * @param maxCellNum 最大有效列（从第1列开始）
     * @return 每一行String类型数据，包含列，行，标题
     * @throws Exception
     * @author yuanyang
     */
    public static List<Map<String, Map<String, Object>>> getContent(FileInputStream excel, String filename, Map<String, String> titleMap, int maxCellNum) throws Exception {

        Cell cell;
        String cellValue;
        String cellTitle;
        Map<String, Map<String, Object>> cellMap;
        Workbook workbook = null;
        List<Map<String, Map<String, Object>>> list = new ArrayList<>();
        List<String> titles = new ArrayList<String>();

        try {
            if (filename.endsWith(".xls")) {
                workbook = new HSSFWorkbook(new POIFSFileSystem(excel));
            } else if (filename.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(excel);
            } else {
                throw new XywgException(801, "上传文件格式错误，请上传Excel文件!");
            }
            Sheet sheet = workbook.getSheetAt(0);

            //处理标题行
            Row titleRow = sheet.getRow(0);
            if (titleRow == null) {
                return list;
            }

            for (int i = 0; i < titleRow.getLastCellNum(); i++) {
                titles.add(StringUtils.trim(ExcelImportUtils.getCellValue(titleRow.getCell(i))));
            }
            //处理内容行
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    boolean flag = false;
                    for (int j = 0; j < maxCellNum; j++) {
                        cell = row.getCell(j);
                        //有效列
                        if ((cell == null || (cell.getCellType() == 1 && cell.getStringCellValue().trim().equals("")))) {

                        } else {
                            flag = true;
                        }
                    }
                    if (flag == false) { //没有有效数据
                        continue;
                    }
                    cellMap = new HashMap<>();
//                    workermaster.setId(beforeDataCount);
                    for (int j = 0; j < maxCellNum; j++) {
                        Map<String, Object> map = new HashedMap();
                        cell = row.getCell(j);
                        cellTitle = titleMap.get(titles.get(j));
                        cellValue = getCellValue(cell);
                        if (StringUtils.isNotBlank(cellTitle)) {
                            map.put(value, cellValue);
                            map.put(columnIndex, j);
                            map.put(rowIndex, i);
                            map.put(title, getKey(titleMap, cellTitle));
                            cellMap.put(cellTitle, map);
                        }
                    }
                    list.add(cellMap);
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
    public static String getKey(Map map, String value) {
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
    public static Date isValidDate(String str, int rowIndex, String title) {
        Date date = null;
        if (str.equals("")) {
            return date;
        }
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            date = format.parse(str);
        } catch (Exception e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
            throw new XywgException(600, ExcelImportUtils.errorMsg(rowIndex, title));
        }
        return date;
    }

    //数字验证正则表达式
    public static boolean isNumeric(String str) {
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }
    //手机号验证
    public static boolean isPhone(String str) {
        String reg = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";

        if (str.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(reg);
            Matcher m = p.matcher(str);
            boolean isMatch = m.matches();
            if (isMatch) {
                return true;
            } else {
                return false;
            }
        }
    }
    //文本长度校验
    public static void lenthCheck(int maxLength,String str,int rowIndex,String title){
        if (str.length()>maxLength){
            throw new XywgException(801, ExcelImportUtils.errorLength(rowIndex,title,maxLength));
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

    /**
     * 长度错误
     * @param rowIndex
     * @param title
     * @return
     */
    public static String errorLength(int rowIndex, String title,int maxLength) {
        return "第" + rowIndex + "行" + title + "长度最大不能超过"+maxLength+"字";
    }


    /**
     * MultipartFile转F
     *
     * @param file
     * @param request
     * @return
     */
    public static File multipartFileToFile(MultipartFile file, HttpServletRequest request) {
        String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdir();
        }
//        String path = filePath + file.getOriginalFilename();
    	String fileName = file.getOriginalFilename();
		if (fileName.contains("\\")) {
			fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
		}
		String path = filePath + fileName;
        File tempFile = null;
        //save to the /upload path
        tempFile = new File(path);
        try {
            file.transferTo(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }

}
