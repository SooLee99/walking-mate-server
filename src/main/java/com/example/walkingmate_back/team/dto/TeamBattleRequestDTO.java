package com.example.walkingmate_back.team.dto;

import com.example.walkingmate_back.team.entity.Team;
import lombok.Data;
import java.util.Date;

@Data
public class TeamBattleRequestDTO {

    private Team team;  // 팀 id

    private String battleDate;  // 대결 날짜

    private boolean victory; // 승리 여부

    private int betStep;  // 대결 걸음 수
}
