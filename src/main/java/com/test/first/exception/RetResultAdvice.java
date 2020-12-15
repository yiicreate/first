package com.test.first.exception;

import com.test.first.conf.R;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class RetResultAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        System.out.println("1");
        if(body instanceof R){
            return  body;
        }

        if(body instanceof String){
            return body;
        }

        return R.of(body);
    }

    @ExceptionHandler
    public R handler(Exception ex){
        System.out.println("2");
        R res = R.err();
        res.setInfo(ex.getMessage());
        res.setCode(500);
        if(ex instanceof HttpRequestMethodNotSupportedException){
            res.setCode(404);
        }
        return  res;
    }
}
