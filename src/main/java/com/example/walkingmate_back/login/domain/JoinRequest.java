package com.example.walkingmate_back.login.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinRequest {

    private String id;
    private String pw;
    private String name;
    private String phone;
    private Date birth;

}
