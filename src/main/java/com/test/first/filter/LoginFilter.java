package com.test.first.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.first.conf.R;
import com.test.first.exception.ComException;
import com.test.first.util.ComUitl;
import com.test.first.util.JwtUitl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    private JwtUitl jwtUtil;


    /**
     * 过滤
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        // todo 在这里可以按需求进行过滤，根据源码来修改扩展非常方便
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        try {
            super.doFilter(request, response, chain);
        } catch (RuntimeException e) {
            //业务异常处理
            if(e instanceof ComException){
                Integer code = ((ComException) e).getCode();
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                response.setStatus(200);
                R map = R.err();
                map.setCode(500);
                map.setInfo(e.getMessage());
                out.write(map.toString());
                out.flush();
                out.close();
            }else {
                //继续执行e输出
                e.printStackTrace();
            }

        }
    }

    /**
     * 如果需要进行登陆认证，会在这里进行预处理
     */
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response){
        try {
            // todo 在登陆认证的时候，可以做些其他的验证操作，比如验证码
            return super.attemptAuthentication(request, response);
        }catch (AuthenticationException e){
            String msg = "";
            if (e instanceof LockedException) {
                msg = "账户被锁定，登录失败!";
            } else if (e instanceof BadCredentialsException) {
                msg = "账户名或密码输入错误，登录失败!";
            } else if (e instanceof DisabledException) {
                msg = "账户被禁用，登录失败!";
            } else if (e instanceof AccountExpiredException) {
                msg = "账户已过期，登录失败!";
            } else if (e instanceof CredentialsExpiredException) {
                msg = "密码已过期，登录失败!";
            } else {
                msg = "登录失败!";
            }
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            response.setStatus(200);
            R r = R.err();
            r.setCode(500);
            r.setInfo(msg);
            out.write(r.toString());
            out.flush();
            out.close();
            System.out.println(r);
        }
        return null;
    }

    /**
     * 登陆成功调用，返回 token
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException {
        String token = jwtUtil.generateToken(authResult.getName());
        Map<String,String> map = new HashMap<String, String>();
        map.put(jwtUtil.getHeader(),token);
        R r = R.ok();
        r.setData(ComUitl.mapToJson(map));
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().print(r.toString());
    }


}
