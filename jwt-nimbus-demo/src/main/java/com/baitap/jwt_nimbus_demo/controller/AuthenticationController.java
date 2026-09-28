package com.baitap.jwt_nimbus_demo.controller;

import com.baitap.jwt_nimbus_demo.dto.LoginRequest;
import com.baitap.jwt_nimbus_demo.dto.LoginResponse;
import com.baitap.jwt_nimbus_demo.dto.RegisterRequest;
import com.baitap.jwt_nimbus_demo.entity.User;
import com.baitap.jwt_nimbus_demo.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public User register(
            @RequestBody RegisterRequest request
    ){

        return authenticationService.register(request);

    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request
    ) throws Exception {

        return authenticationService.login(request);

    }

}