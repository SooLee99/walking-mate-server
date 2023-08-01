package com.example.walkingmate_back.team.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class TeamResponseDTO {

    private Long id;  // 팀 id

    private String name;  // 팀 이름

    private String intro;  // 팀 소개

    private int peopleNum;  // 팀 인원

    private String state;  // 팀 경쟁 상태

    private String date;  // 팀 생성 날짜

    List<TeamMemberResponseDTO> teamMembers = new ArrayList<>(); // 댓글 리스트
    TeamRankResponseDTO teamRankResponseDTO = new TeamRankResponseDTO();

    @Builder
    public TeamResponseDTO(Long id, String name, String intro, int peopleNum, String state, String date, TeamRankResponseDTO teamRankResponseDTO, List<TeamMemberResponseDTO> teamMemberResponseDTOList) {
        this.id=id;
        this.name=name;
        this.intro=intro;
        this.peopleNum=peopleNum;
        this.state=state;
        this.date=date;
        this.teamRankResponseDTO=teamRankResponseDTO;
        this.teamMembers=teamMemberResponseDTOList;
    }
}
