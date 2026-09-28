package com.baitap.jwt_nimbus_demo.service;


import com.baitap.jwt_nimbus_demo.entity.User;

import java.util.List;

public interface UserService {

    User save(User user);

    User findByUsername(String username);

    List<User> findAll();

}