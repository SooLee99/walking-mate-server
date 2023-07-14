package com.example.walkingmate_back.team.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class TeamResponseDTO {

    private Long id;  // 팀 id

    private String name;  // 팀 이름

    private int peopleNum;  // 팀 인원

    private String state;  // 팀 경쟁 상태

    List<TeamMemberResponseDTO> teamMembers = new ArrayList<>(); // 댓글 리스트

    public TeamResponseDTO(Long id, String name, int peopleNum, String state, List<TeamMemberResponseDTO> teamMemberResponseDTOList) {
        this.id=id;
        this.name=name;
        this.peopleNum=peopleNum;
        this.state=state;
        this.teamMembers=teamMemberResponseDTOList;
    }
}
