package com.example.walkingmate_back.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    private String id;
    private String pw;
    private String name;
    private String phone;
    private String birth;

}
