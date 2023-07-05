package com.example.walkingmate_back.history.entity;

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
public class RunRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 러닝 기록 번호 (자동 증가)

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId") // 외래키 설정
    private UserEntity user;  // 사용자 id

    @Id
    @Column
    private Date date;  // 러닝 날짜

    @Column
    private int step;  // 걸음 수

    @Column
    private LocalDateTime created_at;  // 시간
    private LocalDateTime updated_at;
    @PrePersist
    public void PrePersist() {
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdate() {
        this.updated_at = LocalDateTime.now();
    }

    @Column
    private double distance;  // 거리
}
