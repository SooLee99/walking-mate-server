package com.example.walkingmate_back.team.entity;

import com.example.walkingmate_back.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@IdClass(TeamMemberId.class)
@AllArgsConstructor
@Table(name = "teamMember")
@Slf4j
public class TeamMember {
    public TeamMember() {
        log.info("현재 TeamMember 테이블 지나감~!");
    }

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId") // 외래키 설정
    @JsonBackReference
    @ToString.Exclude
    private UserEntity user;  // 사용자 id

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamId") // 외래키 설정
    @JsonBackReference
    @ToString.Exclude
    private Team team;  // 팀 id

    @Column
    @ToString.Include
    private boolean teamLeader; // 팀 리더 여부

    // 기존의 toString() 메서드는 유지하되, 순환 참조가 발생하지 않도록 주의합니다.
    @Override
    public String toString() {
        return "TeamMember{" +
                "user=" + (user != null ? user.getId() : "null") +
                ", team=" + (team != null ? team.getId() : "null") +
                ", teamLeader=" + teamLeader +
                '}';
    }

    public Map<String, Object> toJson() {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", user != null ? user.getId() : null);
        jsonMap.put("team", team != null ? team.getId() : null);
        jsonMap.put("teamLeader", teamLeader);
        return jsonMap;

    }
}