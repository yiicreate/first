package com.test.first.filter;

import com.test.first.util.JwtUitl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    private JwtUitl jwtUtil;

    /**
     * 过滤
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // todo 在这里可以按需求进行过滤，根据源码来修改扩展非常方便
        super.doFilter(request, response, chain);
    }

    /**
     * 如果需要进行登陆认证，会在这里进行预处理
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        // todo 在登陆认证的时候，可以做些其他的验证操作，比如验证码
        return super.attemptAuthentication(request, response);
    }

    /**
     * 登陆成功调用，返回 token
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException {
        String token = jwtUtil.generateToken(authResult.getName());
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().print(token);
    }
}
