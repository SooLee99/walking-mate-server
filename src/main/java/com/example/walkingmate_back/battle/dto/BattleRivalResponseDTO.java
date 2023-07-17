package com.example.walkingmate_back.battle.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BattleRivalResponseDTO {

    private Long teamId;  // 팀 id

    private int step;  // 걸음 수

    public BattleRivalResponseDTO(Long teamId, int step) {
        this.teamId=teamId;
        this.step=step;
    }
}
