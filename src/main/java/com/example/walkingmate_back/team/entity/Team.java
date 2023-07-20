package com.example.walkingmate_back.team.entity;

import com.example.walkingmate_back.battle.entity.BattleRival;
import com.example.walkingmate_back.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = {"teamMembers", "teamBattleHistorys", "battleRivals"})
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;  // 팀 id

    @Column
    private String name;  // 팀 이름

    @Column
    private int peopleNum;  // 팀 인원

    @Column
    private String state;  // 팀 경쟁 상태

    @OneToMany(mappedBy = "team", orphanRemoval = true)
    private List<UserEntity> user;

    @OneToOne(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private TeamRank teamRank;

    @OneToMany(mappedBy = "team", orphanRemoval = true)
    private List<TeamMember> teamMembers;

    @OneToMany(mappedBy = "team", orphanRemoval = true)
    private List<TeamBattleHistory> teamBattleHistorys;

    @OneToMany(mappedBy = "team", orphanRemoval = true)
    private List<BattleRival> battleRivals;

    public Team(String name, int peopleNum, String state) {
        this.name=name;
        this.peopleNum=peopleNum;
        this.state=state;
    }


}
