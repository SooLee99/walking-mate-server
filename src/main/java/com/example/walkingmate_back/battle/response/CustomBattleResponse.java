package com.example.walkingmate_back.battle.response;


import com.example.walkingmate_back.battle.dto.BattleResponseDTO;
import com.example.walkingmate_back.team.dto.TeamMemberResponseDTO;
import com.example.walkingmate_back.team.entity.TeamMember;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.util.Map;

// CustomBattleResponse 클래스 생성 (2023-09-17 이수)
@Data
public class CustomBattleResponse {
    private Map<String, Object> userTeamMember;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BattleResponseDTO battleResponses;

}