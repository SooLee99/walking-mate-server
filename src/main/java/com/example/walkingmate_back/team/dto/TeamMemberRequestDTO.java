package com.example.walkingmate_back.team.dto;

import com.example.walkingmate_back.team.entity.Team;
import com.example.walkingmate_back.user.entity.UserEntity;
import lombok.Data;

@Data
public class TeamMemberRequestDTO {

    private String userId;  // 사용자 id

    private Long teamId;  // 팀 id

    private boolean teamLeader; // 팀 리더 여부
}
