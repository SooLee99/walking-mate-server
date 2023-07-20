package com.example.walkingmate_back.battle.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class BattleResponseDTO {

    private Long id; // 대결 번호 (자동 증가)

    private LocalDate startDate;  // 시작 날짜

    private int totalStep;  // 대결 걸음 수

    private String battleCheck;  // 대결 진행 여부

    private List<BattleRivalResponseDTO> battleRivals;

    @Builder
    public BattleResponseDTO(Long id, LocalDate startDate, int totalStep, String battleCheck, List<BattleRivalResponseDTO> battleRivalResponseDTOList) {
        this.id=id;
        this.startDate=startDate;
        this.totalStep=totalStep;
        this.battleCheck=battleCheck;
        this.battleRivals=battleRivalResponseDTOList;
    }
}
