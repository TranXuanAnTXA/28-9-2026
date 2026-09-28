package com.baitap.jwt_jjwt_demo.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.baitap.jwt_jjwt_demo.dto.AuthResponse;
import com.baitap.jwt_jjwt_demo.dto.LoginRequest;
import com.baitap.jwt_jjwt_demo.dto.RegisterRequest;
import com.baitap.jwt_jjwt_demo.entity.User;
import com.baitap.jwt_jjwt_demo.service.JwtService;
import com.baitap.jwt_jjwt_demo.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public User register(@Valid @RequestBody RegisterRequest request) {

        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .build();

        return userService.register(user);
    }

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody LoginRequest request) {

        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword())

        );

        User user = userService.findByUsername(request.getUsername());

        String token = jwtService.generateToken(user);

        return new AuthResponse(token);

    }

}