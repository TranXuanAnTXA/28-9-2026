package com.baitap.jwt_jjwt_demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test/hello")
    public String hello() {
        return "Xin chào! JWT hợp lệ.";
    }

}