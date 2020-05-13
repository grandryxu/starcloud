package com.xywg.admin.core.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * poi导出工具类
 * @author hh cao
 * @date 2019/1/21
 **/
public class PoiUtil {
    /**
     * 导出excel xlsx
     * @param datas key-value型
     * @param fileName 导出的文件名
     * @param columns 导出的列名 (和data的key一致) Keys ['id','name']  values ['编号','名称']
     */
    public static void exportExcel(String fileName, Map<String, String[]> columns, List<Map<String, Object>> datas, HttpServletResponse response)throws Exception {
        //创建workBook
        XSSFWorkbook book = new XSSFWorkbook();
        //创建表格
        Sheet sheet = book.createSheet(fileName);
        //设置默认宽度
        sheet.setDefaultColumnWidth(25);
        //创建样式
        XSSFCellStyle style = book.createCellStyle();
        //自动换行
        style.setWrapText(true);
        XSSFFont font = book.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);
        //开始构建内容
        String[] keys = columns.get("keys");
        for(int i=0; i<=datas.size(); i++) {
            Row row = sheet.createRow(i);
            if(i == 0) {
                //开始构建头
                String[] values = columns.get("values");
                for(int j=0; j<keys.length; j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellStyle(style);
                    cell.setCellValue(values[j]);
                }
            }else{
                Map<String, Object> data = datas.get(i-1);
                for(int j=0; j<keys.length; j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(null == data.get(keys[j])?"":data.get(keys[j]).toString());
                }
            }
        }
        OutputStream out = null;
        try{
            response.setCharacterEncoding("UTF-8");
            //xls response.setContentType("application/vnd.ms-excel");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            //ajax方式下载配合axios response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            //response.setHeader("fileName", new String(fileName.getBytes("UTF-8"), "iso8859-1")+".xlsx");
            response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(fileName+".xlsx", "utf-8"));
            out = response.getOutputStream();
            book.write(out);
        }catch (Exception e) {
            throw new Exception("导出失败");
        }finally {
            if(null != out) {
                out.close();
            }
            if(null != book) {
                book.close();
            }
        }
    }
}
