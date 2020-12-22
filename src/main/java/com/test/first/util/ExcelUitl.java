package com.test.first.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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

    public static List<List<Object>> importExecl(InputStream inputStream,String fileName) throws IOException {
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if(execl_2003.equals(fileType)){
            wb = new HSSFWorkbook(inputStream);
        }else if(execl_2007.equals(fileType)){
            wb = new XSSFWorkbook(inputStream);
        }
        return getExcelList(wb);
    }

    public static  List<List<Object>> getExcelList(Workbook wb) throws IOException {
        List<List<Object>> list = new ArrayList<List<Object>>();
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        //遍历Excel中所有的sheet
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            sheet = wb.getSheetAt(i);
            if(sheet==null){continue;}

            //遍历当前sheet中的所有行
            for (int j = sheet.getFirstRowNum(); j < sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if(row==null||row.getFirstCellNum()==j){continue;}

                //遍历所有的列
                List<Object> li = new ArrayList<Object>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    li.add(getCellValue(cell));
                }
                list.add(li);
            }
        }
        wb.close();
        return list;
    }


    /**
     * 描述：对表格中数值进行格式化
     * @param cell
     * @return
     */
    public static  Object getCellValue(Cell cell){
        Object value = null;
        DecimalFormat df = new DecimalFormat("0");  //格式化number String字符
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");  //日期格式化
        DecimalFormat df2 = new DecimalFormat("0.00");  //格式化数字

        switch (cell.getCellType()) {
            case STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC:
                if("General".equals(cell.getCellStyle().getDataFormatString())){
                    value = df.format(cell.getNumericCellValue());
                }else if("m/d/yy".equals(cell.getCellStyle().getDataFormatString())){
                    value = sdf.format(cell.getDateCellValue());
                }else{
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case BLANK:
                value = "";
                break;
            default:
                break;
        }
        return value;
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
