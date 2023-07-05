package com.example.walkingmate_back.team.entity;

import com.example.walkingmate_back.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@IdClass(TeamMemberId.class)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teamMember")
public class TeamMember {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId") // 외래키 설정
    private UserEntity user;  // 사용자 id

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamId") // 외래키 설정
    private Team team;  // 팀 id

    @Column
    private boolean teamLeader; // 팀 리더 여부

}
