package com.baitap.jwt_nimbus_demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "Chúc mừng! Bạn đã gọi thành công API được bảo vệ bằng JWT Token!";
    }
}