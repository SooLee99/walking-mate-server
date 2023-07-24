package com.example.walkingmate_back.history.dto;

import lombok.Data;

@Data
public class BuyHistoryRequestDTO {
    private int money;  // 구매 금액

    private int coin;  // 구매 코인
}
