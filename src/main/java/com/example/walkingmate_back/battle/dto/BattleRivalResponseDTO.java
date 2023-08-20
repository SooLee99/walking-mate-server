package com.example.walkingmate_back.battle.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BattleRivalResponseDTO {

    private Long teamId;  // 팀 id

    private String leaderName; // 팀장 이름

    private String tear;  // 팀 티어

    private String intro; // 팀 소개

    private String teamName;  // 팀 이름

    private int peopleNum;  // 팀 인원

    private int step;  // 걸음 수

    @Builder
    public BattleRivalResponseDTO(Long teamId, String leaderName, String tear, String intro, String teamName, int peopleNum, int step) {
        this.teamId=teamId;
        this.leaderName=leaderName;
        this.tear=tear;
        this.intro=intro;
        this.teamName=teamName;
        this.peopleNum=peopleNum;
        this.step=step;
    }
}
