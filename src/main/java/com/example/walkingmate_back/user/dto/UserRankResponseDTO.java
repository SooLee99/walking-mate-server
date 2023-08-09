package com.example.walkingmate_back.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRankResponseDTO {

    private String userId;  // 사용자 id

    private String tear;  // 사용자 티어

    private int coin;  // 사용자 코인

    private int runNum;  // 러닝 횟수

    @Builder
    public UserRankResponseDTO(String userId, int coin, String tear, int runNum) {
        this.userId=userId;
        this.coin=coin;
        this.tear=tear;
        this.runNum=runNum;
    }
}
