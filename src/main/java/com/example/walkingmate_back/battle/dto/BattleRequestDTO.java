package com.example.walkingmate_back.battle.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class BattleRequestDTO {
    private LocalDate startDate;  // 시작 날짜

    private Date createdDate;  // 생성 날짜
}
