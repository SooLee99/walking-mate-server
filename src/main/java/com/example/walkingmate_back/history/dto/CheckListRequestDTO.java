package com.example.walkingmate_back.history.dto;

import lombok.Data;

@Data
public class CheckListRequestDTO {

    private String userId;  // 사용자 id

    private String date;  // 작성 날짜

    private boolean checked;  // 체크 여부

    private String content;  // 내용

}
