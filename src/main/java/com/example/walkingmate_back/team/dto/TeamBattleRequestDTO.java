package com.example.walkingmate_back.team.dto;

import lombok.Data;

@Data
public class TeamBattleRequestDTO {

    private Long teamId;  // 팀 id

    private String battleDate;  // 대결 날짜

    private String victory; // 승리 여부

    private int betStep;  // 대결 걸음 수
}
