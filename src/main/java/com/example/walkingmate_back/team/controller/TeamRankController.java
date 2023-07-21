package com.example.walkingmate_back.team.controller;

import com.example.walkingmate_back.main.response.DefaultRes;
import com.example.walkingmate_back.main.response.ResponseMessage;
import com.example.walkingmate_back.main.response.StatusEnum;
import com.example.walkingmate_back.team.dto.TeamRankResponseDTO;
import com.example.walkingmate_back.team.service.TeamRankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *    팀 전체 랭킹 조회
 *
 *   @version          1.00 / 2023.07.21
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
    public ResponseEntity<DefaultRes<List<TeamRankResponseDTO>>> SpecificationTeamRank() {
        List<TeamRankResponseDTO> teamRankResponseDTO =  teamRankService.getAllRank();

        if(teamRankResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.WRITE_TEAMRANK, teamRankResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_TEAMRANK, null), HttpStatus.OK);
    }

}
