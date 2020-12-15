package com.test.first.conf;

import com.test.first.format.DefaultLang;
import com.test.first.format.Lang;
import lombok.Data;

import java.io.Serializable;

@Data
public class R<E> implements Serializable {
    private Integer code;

    private String info;

    private E data;

    public R(Lang lang){
        code = lang.getCode();
        info = lang.getInfo();
    }

    public R(Integer code, String info){
        this.code = code;
        this.info = info;
    }

    public R(E data, Lang lang){
        this.data = data;
        code = lang.getCode();
        info = lang.getInfo();
    }

    public static <E> R<E> ok(){ return  new R<>(DefaultLang.SCC); }

    public static <E> R<E> err(){ return  new R<>(DefaultLang.ERR); }

    public static <E> R<E> of(E data){ return  new R<>(data,DefaultLang.SCC); }

    public static <E> R<E> of(Lang lang){ return  new R<>(lang); }

    public static <E> R<E> of(Lang lang,E data){
        return  new R<>(data,lang);
    }

    @Override
    public String toString() {
        String dataStr = "";
        if (data instanceof String) {
            dataStr = (String) data;
        } else {
            dataStr = String.valueOf(data);
        }
        return String.format("{\"code\":\"%d\",\"info\":\"%s\",\"data\":%s}", code, info, dataStr);
    }
}
