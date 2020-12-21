package com.test.first.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author: lh
 * @version: 2020/12/18
 * @description:  execl 工具类
 */

public class ExcelUitl {
    private final static String execl_2003 = ".xls";
    private final static String execl_2007 = ".xlsx";

    private static XSSFCellStyle fontStyle;

    public static void exportExecl(){

    }


    /**
     * 利用反射  根据属性名获取属性值
     * */
    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 导出
     * @param header 表单头
     * @param list  数据列表
     * @param Option 其他配置
     * @param path 保存地址
     * @return
     */
    public static Workbook createWorkBook(List<Map<String,Object>> header, List<String[]> list, Map<String,Object> Option, String path){
        Workbook wb = new HSSFWorkbook();
        Integer len = list.size()/1000+1;

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBold(true);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(BorderStyle.THIN);
        cs.setBorderRight(BorderStyle.THIN);
        cs.setBorderTop(BorderStyle.THIN);
        cs.setBorderBottom(BorderStyle.THIN);
        cs.setAlignment(HorizontalAlignment.CENTER);

        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(BorderStyle.THIN);
        cs2.setBorderRight(BorderStyle.THIN);
        cs2.setBorderTop(BorderStyle.THIN);
        cs2.setBorderBottom(BorderStyle.THIN);
        cs2.setAlignment(HorizontalAlignment.CENTER);

        Sheet sheet = wb.createSheet();

        //第几行
        short r=0;
        // 创建第一行
        Row row = sheet.createRow(r);

         //创建表头
        //设置列名
        for(short i=0;i<header.size();i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(header.get(i).get("val").toString());
            cell.setCellStyle(cs);
            sheet.setColumnWidth(i,Integer.parseInt(header.get(i).get("len").toString())*256);
        }
        ++r;

        for (short j=0;j<list.size();j++,r++){
            Row row1 = sheet.createRow(r);
            String [] rows = list.get(j);
            for (short k = 0;k<rows.length;k++){
                Cell cell = row1.createCell(k);
                cell.setCellValue(rows[k]);
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }
}
