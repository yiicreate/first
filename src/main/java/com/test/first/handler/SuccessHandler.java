package com.test.first.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class SuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth) throws IOException, ServletException {
        Object principal = auth.getPrincipal();
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        resp.setStatus(200);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        map.put("msg", principal);
        ObjectMapper om = new ObjectMapper();
        out.write(om.writeValueAsString(map));
        out.flush();
        out.close();
    }
}
