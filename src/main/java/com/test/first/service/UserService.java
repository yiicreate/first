package com.test.first.service;


import com.test.first.entity.User;

public interface UserService {

    User findByUserName(String userName);

    User setUser(User user);
}
