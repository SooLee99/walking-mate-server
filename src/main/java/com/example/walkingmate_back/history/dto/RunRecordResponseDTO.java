package com.example.walkingmate_back.history.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RunRecordResponseDTO {

    private Long id; // 번호

    private String userId;  // 사용자 id

    private String date;  // 러닝 날짜

    private int step;  // 걸음 수

    private double distance;  // 거리

    private long time; // 시간

    @Builder
    public RunRecordResponseDTO(Long id, String userId, String date, int step, double distance, long time, LocalDateTime regTime, LocalDateTime updateTime) {
        this.id=id;
        this.userId=userId;
        this.date=date;
        this.step=step;
        this.distance=distance;
        this.time=time;
    }
}
