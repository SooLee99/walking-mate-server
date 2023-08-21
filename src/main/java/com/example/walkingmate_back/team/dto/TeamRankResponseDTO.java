package com.example.walkingmate_back.team.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeamRankResponseDTO {

    private Long teamId; // 팀 id

    private String tier;  // 팀 티어

    private int coin;  // 팀 코인

    private int winNum;  // 이긴 횟수

    @Builder
    public TeamRankResponseDTO(Long teamId, int coin, String tier, int winNum) {
        this.teamId=teamId;
        this.coin=coin;
        this.tier = tier;
        this.winNum=winNum;
    }
}
