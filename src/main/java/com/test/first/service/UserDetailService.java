package com.test.first.service;

import com.test.first.entity.User;
import com.test.first.service.imp.UserDetailImp;
import com.test.first.service.imp.UserServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserDetailService implements UserDetailsService {

    @Autowired
    UserServiceImp userServiceImp;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userServiceImp.findByUserName(s);
        if(user == null){
            throw  new UsernameNotFoundException("用户没有找到");
        }
        return new UserDetailImp(user);
    }
}
