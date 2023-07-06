package com.example.walkingmate_back.history.entity;

import com.example.walkingmate_back.main.entity.BaseTimeEntity;
import com.example.walkingmate_back.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@IdClass(RunRecordId.class)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "runRecord")
public class RunRecord extends BaseTimeEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId") // 외래키 설정
    private UserEntity user;  // 사용자 id

    @Id
    @Column
    private Date date;  // 러닝 날짜

    @Column
    private int step;  // 걸음 수

    // 시간은 따로 BaseTime으로 빼둠

    @Column
    private double distance;  // 거리
}
