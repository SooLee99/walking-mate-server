package com.example.walkingmate_back.team.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamRequestDTO {

    @NotBlank(message = "팀 이름은 필수 항목입니다.")
    private String name;  // 팀 이름

    private String intro;  // 팀 소개
    
    private int peopleNum;  // 팀 인원

    private String state;  // 팀 경쟁 상태
}
