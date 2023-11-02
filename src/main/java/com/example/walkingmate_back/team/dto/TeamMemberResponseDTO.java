package com.example.walkingmate_back.team.dto;

import lombok.*;

@Data
@NoArgsConstructor
@Getter   // 정보 확인용 (2023-09-15 이수 작성)
public class TeamMemberResponseDTO {

    private String userId;  // 사용자 id

    private Long teamId;  // 팀 id

    private boolean teamLeader; // 팀 리더 여부

    @Builder
    public TeamMemberResponseDTO(String userId, Long teamId, boolean teamLeader) {
        this.userId=userId;
        this.teamId=teamId;
        this.teamLeader=teamLeader;
    }
}
