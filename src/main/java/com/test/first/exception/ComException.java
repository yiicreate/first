package com.test.first.exception;

import com.test.first.format.Lang;
import lombok.Getter;

@Getter
public class ComException extends RuntimeException {
    private final Lang lang;
    private Integer code;

    public ComException(Lang lang){
        super(lang.getInfo());
        this.lang = lang;
        this.code = lang.getCode();
    }
}
