package com.test.first.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.first.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {
    @GetMapping("/admin/hello")
    public String admin() {
        return "hello admin";
    }

    @GetMapping("/admin/self")
    public Object self() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping("/guest/hello")
    public String user() {
        return "hello user";
    }

    @PostMapping("/guest/hello")
    public String user1() {
        return "hello user1";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/login1")
    public String login(){

        return  "login";
    }
}
