package com.example.walkingmate_back.user.dto;

import lombok.Data;

@Data
public class UserPwConfirmDTO {

    private String userId;
    private String newPw;
    private String newConfirmPw;
}
