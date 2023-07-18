package com.example.walkingmate_back.team.controller;

import com.example.walkingmate_back.main.entity.Message;
import com.example.walkingmate_back.team.dto.TeamMemberRequestDTO;
import com.example.walkingmate_back.team.service.TeamMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *    멤버 가입, 삭제
 *
 *   @version          1.00 / 2023.07.18
 *   @author           전우진
 */

//@Controller
@RestController
@RequestMapping("/team")
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

    public TeamMemberController(TeamMemberService teamMemberService) {
        this.teamMemberService = teamMemberService;
    }

    // 멤버 가입
    @PostMapping("/{teamId}/member/save")
    public ResponseEntity<Message> saveMember(@PathVariable Long teamId, @RequestBody TeamMemberRequestDTO teamMemberRequestDTO){

        return teamMemberService.saveMember(teamId, teamMemberRequestDTO);
    }

    // 팀 멤버 삭제
    @DeleteMapping("/{teamId}/member")
    public ResponseEntity<Message> deleteTeamMember(@PathVariable Long teamId) {
        String userId = "ccc";

        return teamMemberService.deleteTeamMember(teamId, userId);
    }


}
