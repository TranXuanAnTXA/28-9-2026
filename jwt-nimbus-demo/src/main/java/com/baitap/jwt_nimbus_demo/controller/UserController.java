package com.baitap.jwt_nimbus_demo.controller;

import com.baitap.jwt_nimbus_demo.entity.User;
import com.baitap.jwt_nimbus_demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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