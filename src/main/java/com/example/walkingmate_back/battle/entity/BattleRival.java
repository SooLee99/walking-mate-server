package com.example.walkingmate_back.battle.entity;

import com.example.walkingmate_back.team.entity.Team;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@IdClass(BattleRivalId.class)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "battleRival")
public class BattleRival {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "battleId") // 외래키 설정
    private Battle battle; // 대결 번호

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamId") // 외래키 설정
    private Team team;  // 팀 id

    @Column
    private int step;  // 걸음 수
}
