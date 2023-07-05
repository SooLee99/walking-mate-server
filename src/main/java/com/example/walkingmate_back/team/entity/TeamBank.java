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
@Table(name = "teamBank")
public class TeamBank {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamId") // 외래키 설정
    private Team team;  // 팀 id

    @Column
    private String tear;  // 팀 티어

    @Column
    private int coin;  // 팀 코인
}
