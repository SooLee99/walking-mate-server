package com.example.walkingmate_back.history.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HomeResponseDTO {

    private int step;  // 걸음 수

    private double distance;  // 거리

    private long kcal;  // 칼로리

    @Builder
    public HomeResponseDTO(int step, double distance, long kcal) {
        this.step=step;
        this.distance=distance;
        this.kcal=kcal;
    }
}
