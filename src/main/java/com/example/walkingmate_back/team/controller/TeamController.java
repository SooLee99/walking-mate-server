package com.example.walkingmate_back.team.controller;

import com.example.walkingmate_back.team.dto.TeamRequestDTO;
import com.example.walkingmate_back.team.service.TeamService;
import org.springframework.web.bind.annotation.*;

/**
 *    팀 생성, 삭제
 *
 *   @version          1.00 / 2023.07.13
 *   @author           전우진
 */

//@Controller
@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // 팀 생성
    @PostMapping("/save")
    public int saveTeam(@RequestBody TeamRequestDTO teamRequestDTO){
        String userId = "aaa";

        return teamService.saveTeam(teamRequestDTO, userId);
    }

    // 팀 삭제
    @DeleteMapping("/{teamId}")
    public int deleteTeam(@PathVariable Long teamId) {
        return teamService.deleteTeam(teamId);
    }
}
