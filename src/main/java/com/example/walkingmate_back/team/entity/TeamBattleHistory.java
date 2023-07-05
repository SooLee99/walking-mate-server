package com.example.walkingmate_back.team.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teamBattleHistory")
public class TeamBattleHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 팀 대결 기록 번호 (자동 증가)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamId") // 외래키 설정
    private Team team;  // 팀 id

    @Column
    private Date battleDate;  // 대결 날짜

    @Column
    private boolean victory; // 승리 여부

    @Column
    private int betStep;  // 대결 걸음 수
}
