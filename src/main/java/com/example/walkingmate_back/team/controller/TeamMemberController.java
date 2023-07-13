package com.example.walkingmate_back.team.controller;

import com.example.walkingmate_back.team.dto.TeamMemberRequestDTO;
import com.example.walkingmate_back.team.service.TeamMemberService;
import org.springframework.web.bind.annotation.*;

/**
 *    멤버 가입
 *
 *   @version          1.00 / 2023.07.13
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

    // 팀 생성
    @PostMapping("/{teamId}/member/save")
    public int saveMember(@PathVariable Long teamId, @RequestBody TeamMemberRequestDTO teamMemberRequestDTO){

        return teamMemberService.saveMember(teamId, teamMemberRequestDTO);
    }

}
