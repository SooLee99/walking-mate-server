package com.example.walkingmate_back.history.dto;

import lombok.Data;

@Data
public class RunRecordRequestDTO {

    private String date;  // 러닝 날짜

    private int step;  // 걸음 수

    private double distance;  // 거리


}
