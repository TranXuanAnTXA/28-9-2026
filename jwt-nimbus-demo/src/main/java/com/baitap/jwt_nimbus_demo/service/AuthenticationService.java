package com.baitap.jwt_nimbus_demo.service;

import com.baitap.jwt_nimbus_demo.dto.LoginRequest;
import com.baitap.jwt_nimbus_demo.dto.LoginResponse;
import com.baitap.jwt_nimbus_demo.dto.RegisterRequest;
import com.baitap.jwt_nimbus_demo.entity.User;

public interface AuthenticationService {

    User register(RegisterRequest request);

    LoginResponse login(LoginRequest request) throws Exception;

}