package com.example.walkingmate_back.login.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinRequest {

    private String id;      // 아이디
    private String pw;      // 비밀번호
    private String name;    // 이름
    private String phone;   // 전화번호
    private String birth;     // 생년월일
    private int height;     // 키
    private int weight;     // 몸무게

}
