package com.baitap.jwt_jjwt_demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.baitap.jwt_jjwt_demo.entity.User;
import com.baitap.jwt_jjwt_demo.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repository;

    @GetMapping
    public List<User> getAllUsers(){

        return repository.findAll();

    }

    @GetMapping("/me")
    public String getCurrentUser(Authentication authentication){

        return authentication.getName();

    }

}