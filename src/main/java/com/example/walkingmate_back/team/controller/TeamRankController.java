package com.example.walkingmate_back.team.controller;

import com.example.walkingmate_back.team.dto.TeamRankResponseDTO;
import com.example.walkingmate_back.team.service.TeamRankService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 *    팀 전체 랭킹 조회
 *
 *   @version          1.00 / 2023.07.15
 *   @author           전우진
 */

//@Controller
@RestController
@RequestMapping("/team/teamRank")
public class TeamRankController {

    private final TeamRankService teamRankService;

    public TeamRankController(TeamRankService teamRankService) {
        this.teamRankService = teamRankService;
    }

    // 전체 랭킹 조회
    @GetMapping("/list")
    public List<TeamRankResponseDTO> SpecificationTeamRank() {
        return teamRankService.getAllRank();
    }

}
