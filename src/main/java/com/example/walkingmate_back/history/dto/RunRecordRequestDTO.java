package com.example.walkingmate_back.history.dto;

import lombok.Data;
import java.sql.Time;
import java.util.Date;

@Data
public class RunRecordRequestDTO {

    private double distance;  // 거리

    private int step;  // 걸음 수

    private double kcal;  // 칼로리

    private Date date; // 러닝 날짜

    private long time;  // 시간

    private Time startTime;  // 시작 시간

    private Time endTime;  // 종료 시간

}
