package com.baitap.jwt_nimbus_demo.dto;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;

    private String password;

}