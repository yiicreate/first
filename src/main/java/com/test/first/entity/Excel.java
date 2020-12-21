package com.test.first.entity;

import lombok.Data;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import java.io.Serializable;

/**
 * @author: lh
 * @version: 2020/12/18
 * @description:
 */

@Data
public class Excel implements Serializable {
    private String headTextName;//列头（标题）名
    private String propertyName;//对应字段名
    private Integer cols;//合并单元格数
    private XSSFCellStyle cellStyle;

    public Excel(){

    }
    public Excel(String headTextName, String propertyName){
        this.headTextName = headTextName;
        this.propertyName = propertyName;
    }

    public Excel(String headTextName, String propertyName, Integer cols) {
        super();
        this.headTextName = headTextName;
        this.propertyName = propertyName;
        this.cols = cols;
    }
}
