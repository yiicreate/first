package com.test.first.repository;

import com.test.first.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRep extends JpaRepository<User,Integer> {
    User findAllById(Integer id);

    User findByUserNameAndPassWord(String userName, String passWord);

    User findByUserName(String userName);
}
