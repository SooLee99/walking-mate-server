package com.example.walkingmate_back.history.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RunRecordAVGDTO {
    private int step;  // 걸음 수

    private double distance;  // 거리

    private int num;  // 횟수

    @Builder
    public RunRecordAVGDTO(int num, int step, double distance) {
        this.num=num;
        this.step=step;
        this.distance=distance;
    }
}
