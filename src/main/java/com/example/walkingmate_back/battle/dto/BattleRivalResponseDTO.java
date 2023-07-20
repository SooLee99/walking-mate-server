package com.example.walkingmate_back.battle.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BattleRivalResponseDTO {

    private Long teamId;  // 팀 id

    private String teamName;  // 팀 이름

    private int peopleNum;  // 팀 인원

    private int step;  // 걸음 수

    @Builder
    public BattleRivalResponseDTO(Long teamId, String name, int peopleNum, int step) {
        this.teamId=teamId;
        this.teamName=name;
        this.peopleNum=peopleNum;
        this.step=step;
    }
}
