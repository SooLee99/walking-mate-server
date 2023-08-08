package com.example.walkingmate_back.team.dto;

import com.example.walkingmate_back.team.entity.Team;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
public class TeamBattleResponseDTO {

    private Long id; // 팀 대결 기록 번호 (자동 증가)

    private Long teamId;  // 팀 id

    private LocalDate battleDate;  // 대결 날짜

    private String victory; // 승리 여부

    private int betStep;  // 대결 걸음 수

    @Builder
    public TeamBattleResponseDTO(Long id, Long teamId, LocalDate battleDate, String victory, int betStep) {
        this.id=id;
        this.teamId=teamId;
        this.battleDate=battleDate;
        this.victory=victory;
        this.betStep=betStep;
    }
}
