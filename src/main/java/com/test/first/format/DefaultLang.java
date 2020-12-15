package com.test.first.format;

import lombok.Getter;


@Getter
public enum DefaultLang implements Lang {
    SCC(0,"成功"),
    ERR(99,"失败");

    private Integer code;
    private String info;

    DefaultLang(Integer code, String info){
        this.code = code;
        this.info = info;
    }
}
