package com.example.walkingmate_back.history.entity;

import com.example.walkingmate_back.main.entity.BaseTimeEntity;
import com.example.walkingmate_back.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "runRecord")
public class RunRecord extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId") // 외래키 설정
    private UserEntity user;  // 사용자 id

    @Column
    private LocalDate date;  // 러닝 날짜

    @Column
    private int step;  // 걸음 수

    // 시간은 따로 BaseTime으로 빼둠

    @Column
    private double distance;  // 거리

    public RunRecord(UserEntity userEntity, LocalDate now, int step, double distance) {
        this.user=userEntity;
        this.date=now;
        this.step=step;
        this.distance=distance;
    }
}
