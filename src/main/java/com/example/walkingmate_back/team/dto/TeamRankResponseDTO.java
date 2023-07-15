package com.example.walkingmate_back.team.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeamRankResponseDTO {

    private Long teamId; // 팀 id

    private String tear;  // 팀 티어

    private int coin;  // 팀 코인

    public TeamRankResponseDTO(Long teamId, int coin, String tear) {
        this.teamId=teamId;
        this.coin=coin;
        this.tear=tear;
    }
}
