package com.baitap.jwt_jjwt_demo.service;

import com.baitap.jwt_jjwt_demo.dto.LoginRequest;
import com.baitap.jwt_jjwt_demo.dto.LoginResponse;
import com.baitap.jwt_jjwt_demo.dto.RegisterRequest;
import com.baitap.jwt_jjwt_demo.entity.User;

public interface AuthenticationService {

    User register(RegisterRequest request);

    LoginResponse login(LoginRequest request) throws Exception;

}