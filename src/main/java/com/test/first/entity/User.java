package com.test.first.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String userName;

    private String passWord;

    private String name;

    private Integer sex;

    private Long createTime;

    public String getCreateTime(){
        Long timeStamp = this.createTime*1000;  //获取当前时间戳
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return  sdf.format(new Date(Long.parseLong(String.valueOf(timeStamp))));
    }
}
