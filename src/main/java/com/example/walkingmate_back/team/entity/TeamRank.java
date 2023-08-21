package com.example.walkingmate_back.team.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "teamRank")
public class TeamRank {

    @Id
    @Column(name = "teamId")
    private Long id; // 팀 id

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamId") // 외래키 설정
    private Team team;  // 팀 id

    @Column
    private String tier;  // 팀 티어

    @Column
    private int coin;  // 팀 코인

    @Column
    private int winNum; // 이긴 횟수

    public TeamRank(Long teamId, int coin, String tier, int winNum) {
        this.id=teamId;
        this.coin=coin;
        this.tier = tier;
        this.winNum=winNum;
    }

    public void updateTear(String tier) {
        this.tier =tier;
    }

    public void updateWinNum() {
        this.winNum = winNum + 1;
    }
}
