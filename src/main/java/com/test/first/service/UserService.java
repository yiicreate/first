package com.test.first.service;


import com.test.first.entity.User;

import java.util.List;

public interface UserService {

    User findByUserName(String userName);

    User setUser(User user);

    List<User> findAllLists();
}
