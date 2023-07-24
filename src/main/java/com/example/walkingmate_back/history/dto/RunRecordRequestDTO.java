package com.example.walkingmate_back.history.dto;

import lombok.Data;

@Data
public class RunRecordRequestDTO {

    private int step;  // 걸음 수

    private double distance;  // 거리

}
