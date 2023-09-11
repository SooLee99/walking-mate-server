package com.example.walkingmate_back.user.dto;

import lombok.Data;

@Data
public class UserEmailConfirmDTO {

    private int userNumber;  // 사용자가 입력한 인증번호

    private int emailNumber;  // 사용자의 이메일로 전송된 인증번호
}
