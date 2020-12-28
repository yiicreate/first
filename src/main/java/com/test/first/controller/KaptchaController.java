package com.test.first.controller;

import com.test.first.util.KaptchaUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @author: lh
 * @version: 2020/12/28
 * @description: 验证码
 */

@RestController
@RequestMapping("/guest/")
public class KaptchaController {

    @GetMapping("image")
    public void image(HttpServletRequest request, HttpServletResponse response) throws Exception {
        KaptchaUtil.validateCode(request,response);
    }

    @GetMapping("check")
    public String check(HttpServletRequest request, @RequestParam("code") String code){
        return KaptchaUtil.check(request,code)?"成功":"失败";
    }

}
