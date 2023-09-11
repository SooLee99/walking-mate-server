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
    private double height;
    private double weigth;

    private String userTeam;
    private double BMI;

    public User(String id, String name, String phone, String birth) {
        this.id=id;
        this.name=name;
        this.phone=phone;
        this.birth=birth;
    }
}
