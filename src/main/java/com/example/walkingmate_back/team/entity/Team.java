package com.example.walkingmate_back.team.entity;

import com.example.walkingmate_back.battle.entity.BattleRival;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
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
    private String intro;  // 팀 소개

    @Column
    private int peopleNum;  // 팀 인원

    @Column
    private String state;  // 팀 경쟁 상태

    @Column
    private LocalDate date; // 팀 생성 날짜

    @OneToOne(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private TeamRank teamRank;

    @OneToMany(mappedBy = "team", orphanRemoval = true)
    private List<TeamMember> teamMembers;

    @JsonIgnore
    @OneToMany(mappedBy = "team", orphanRemoval = true)
    private List<TeamBattleHistory> teamBattleHistorys;

    @JsonIgnore
    @OneToMany(mappedBy = "team", orphanRemoval = true)
    private List<BattleRival> battleRivals;

    public Team(String name, String intro, int peopleNum, String state, LocalDate lc) {
        this.name=name;
        this.intro=intro;
        this.peopleNum=peopleNum;
        this.state=state;
        this.date=lc;
    }

    public void updateState(String state) {
        this.state=state;
    }
}
