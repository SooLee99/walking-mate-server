package com.example.walkingmate_back.history.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RunRecordResponseDTO {

    private String userId;  // 사용자 id

    private String date;  // 러닝 날짜

    private int step;  // 걸음 수

    private double distance;  // 거리

    public RunRecordResponseDTO(String userId, String date, int step, double distance, LocalDateTime regTime, LocalDateTime updateTime) {
        this.userId=userId;
        this.date=date;
        this.step=step;
        this.distance=distance;
    }
}
