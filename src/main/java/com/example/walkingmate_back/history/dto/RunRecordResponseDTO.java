package com.example.walkingmate_back.history.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
public class RunRecordResponseDTO {

    private Long id; // 번호

    private String userId;  // 사용자 id

    private double kcal;  // 칼로리

    private int step;  // 걸음 수

    private double distance;  // 거리

    private String date;  // 러닝 날짜

    private long time; // 시간

    private String startTime;  // 시작 시간

    private Date endTime;  // 종료 시간

    @Builder
    public RunRecordResponseDTO(Long id, String userId, double kcal, String date, int step, double distance, long time, String startTime, Date endTime) {
        this.id=id;
        this.userId=userId;
        this.kcal=kcal;
        this.date=date;
        this.step=step;
        this.distance=distance;
        this.time=time;
        this.startTime=startTime;
        this.endTime=endTime;
    }
}
