package com.example.walkingmate_back.history.entity;

import com.example.walkingmate_back.history.dto.RunRecordRequestDTO;
import com.example.walkingmate_back.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Time;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "runRecord")
public class RunRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId") // 외래키 설정
    private UserEntity user;  // 사용자 id

    @Column
    private double distance;  // 거리

    @Column
    private int step;  // 걸음 수

    @Column
    private double kcal; // 칼로리

    @Column
    private LocalDate date;  // 러닝 날짜

    @Column
    private long time;  // 러닝 시간

    @Column
    private Time startTime;  // 시작 시간

    @Column
    private Time endTime;  // 종료 시간

    public RunRecord(UserEntity user, LocalDate now, int step, double distance, double kcal, long time, Time startTime) {
        this.user=user;
        this.date=now;
        this.step=step;
        this.distance=distance;
        this.kcal=kcal;
        this.time=time;
        this.startTime=startTime;
    }

    public void update(RunRecordRequestDTO runRecordRequestDTO) {
        this.step = step + runRecordRequestDTO.getStep();
        this.distance = distance + runRecordRequestDTO.getDistance();
        this.kcal = kcal + runRecordRequestDTO.getKcal();
        this.time = time + runRecordRequestDTO.getTime();
        this.endTime = runRecordRequestDTO.getEndTime();
    }
}
