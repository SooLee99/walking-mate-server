package com.example.walkingmate_back.team.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    private LocalDate battleDate;  // 대결 날짜

    @Column
    private boolean victory; // 승리 여부

    @Column
    private int betStep;  // 대결 걸음 수

    public TeamBattleHistory(Team team, LocalDate battleDate, int betStep, boolean victory) {
        this.team=team;
        this.battleDate=battleDate;
        this.betStep=betStep;
        this.victory=victory;
    }
}
