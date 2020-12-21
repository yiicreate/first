package com.test.first.service.imp;

import com.test.first.entity.User;
import com.test.first.repository.UserRep;
import com.test.first.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRep userRep;

    @Override
    public User findByUserName(String userName) {
        return  userRep.findByUserName(userName);
    }

    @Override
    public User setUser(User user) {
        return userRep.save(user);
    }

    @Override
    public List<User> findAllLists() {
        return userRep.findAll();
    }

}
