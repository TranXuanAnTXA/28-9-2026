package com.baitap.jwt_jjwt_demo.service;

import java.util.List;

import com.baitap.jwt_jjwt_demo.entity.User;

public interface UserService {

    User save(User user);

    User findByUsername(String username);

    List<User> findAll();

    User register(User user);

}