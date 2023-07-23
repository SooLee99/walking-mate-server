package com.example.walkingmate_back.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginRequest {
    private String userName;
    private String password;
}