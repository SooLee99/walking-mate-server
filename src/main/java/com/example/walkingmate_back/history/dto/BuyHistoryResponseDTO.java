package com.example.walkingmate_back.history.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BuyHistoryResponseDTO {

    private Long id; // 번호 (자동 증가)

    private String userId;  // 사용자 id

    private String date;  // 날짜

    private int money;  // 금액

    private int coin;  // 코인

    @Builder
    public BuyHistoryResponseDTO(Long id, String userId, String date, int coin, int money) {
        this.id=id;
        this.userId=userId;
        this.date=date;
        this.coin=coin;
        this.money=money;
    }
}
