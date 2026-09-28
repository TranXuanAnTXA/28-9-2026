package com.baitap.jwt_nimbus_demo.service_impl;

import com.baitap.jwt_nimbus_demo.dto.LoginRequest;
import com.baitap.jwt_nimbus_demo.dto.LoginResponse;
import com.baitap.jwt_nimbus_demo.dto.RegisterRequest;
import com.baitap.jwt_nimbus_demo.entity.User;
import com.baitap.jwt_nimbus_demo.repository.UserRepository;
import com.baitap.jwt_nimbus_demo.service.AuthenticationService;
import com.baitap.jwt_nimbus_demo.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public User register(RegisterRequest request) {

        if(repository.existsByUsername(request.getUsername())){
            throw new RuntimeException("Username already exists");
        }

        if(repository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .email(request.getEmail())
                .role("USER")
                .enabled(true)
                .build();

        return repository.save(user);

    }

    @Override
    public LoginResponse login(LoginRequest request) throws Exception {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // 1. Tìm thông tin User từ database dựa vào username
        User user = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Truyền đối tượng User vào generateToken
        String token = jwtService.generateToken(user);

        return LoginResponse.builder()
                .token(token)
                .build();

    }
}